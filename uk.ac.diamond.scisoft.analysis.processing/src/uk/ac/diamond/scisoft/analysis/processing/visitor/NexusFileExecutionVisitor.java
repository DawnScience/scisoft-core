/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.visitor;

import java.io.File;
import java.io.Serializable;
import java.net.URI;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.dawnsci.analysis.api.persistence.IPersistenceService;
import org.eclipse.dawnsci.analysis.api.persistence.IPersistentNodeFactory;
import org.eclipse.dawnsci.analysis.api.processing.IExecutionVisitor;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.ISavesToFile;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.model.IOperationModel;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.analysis.api.tree.NodeLink;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.tree.TreeFactory;
import org.eclipse.dawnsci.hdf5.nexus.NexusFileHDF5;
import org.eclipse.dawnsci.nexus.NexusConstants;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusFile;
import org.eclipse.dawnsci.nexus.template.NexusTemplate;
import org.eclipse.dawnsci.nexus.template.NexusTemplateService;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.ILazyWriteableDataset;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.LazyWriteableDataset;
import org.eclipse.january.dataset.ShapeUtils;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.dataset.SliceND;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.OriginMetadata;
import org.eclipse.january.metadata.UnitMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.osgi.services.ServiceProvider;
import uk.ac.diamond.scisoft.analysis.io.Utils;
import uk.ac.diamond.scisoft.analysis.processing.IFlushMonitor;
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;

public class NexusFileExecutionVisitor implements IExecutionVisitor, ISavesToFile {
	
	private static IPersistenceService service;
	public void setPersistenceService(IPersistenceService s) { // Injected
		service = s;
	}
	public NexusFileExecutionVisitor() {
		// Used for OSGI
	}

	public static final String RESULTS_GROUP = "result";
	public static final String INTER_GROUP = "intermediate";
	public static final String AUX_GROUP = "auxiliary";
	public static final String SUM_GROUP = "summary";
	public static final String ENTRY = "processed";
	public static final String PROCESS = "process";
	private static final String LIVE = "live";
	private static final String FINISHED = "finished";
	public static final String DATA_NAME = "data";

	private Map<IOperation<?, ?>, AtomicBoolean> firstNotifyMap;
	private Map<IOperation<?, ?>, Integer> positionMap;
	private AtomicBoolean firstNonNullExecution = new AtomicBoolean(true);
	private AtomicBoolean nullReturnSWMRMode = new AtomicBoolean(false);

	private String results;
	private String intermediate;

	private ConcurrentHashMap<String,ConcurrentHashMap<Integer, String[]>> groupAxesNames = new ConcurrentHashMap<String,ConcurrentHashMap<Integer,String[]>>();
	private String filePath;
	private NexusFile nexusFile;
	private long lastFlush = 0;
	
	private String originalFilePath;
	
	private boolean swmring = false;
	private String templatePath;

	private final static Logger logger = LoggerFactory.getLogger(NexusFileExecutionVisitor.class);
	
	public NexusFileExecutionVisitor(String filePath, boolean swmr, String originalFilePath, String templatePath) {
		this.filePath = filePath;
		firstNotifyMap = new ConcurrentHashMap<>();
		positionMap = new ConcurrentHashMap<>();
		this.swmring = swmr;
		this.originalFilePath = originalFilePath;
		this.templatePath = templatePath;
	}
	
	public NexusFileExecutionVisitor(String filePath, boolean swmr) {
		this(filePath,swmr,null, null);
	}
	
	public NexusFileExecutionVisitor(String filePath) {
		this(filePath,false);
	}

	@Override
	public void init(IOperation<? extends IOperationModel, ? extends OperationData>[] series, ILazyDataset data) throws Exception {

		for (int i = 0; i < series.length; i++) {
			firstNotifyMap.put(series[i], new AtomicBoolean(true));
			positionMap.put(series[i], i);
		}

		OriginMetadata origin = null;
		List<SliceFromSeriesMetadata> metadata = data.getMetadata(SliceFromSeriesMetadata.class);
		if (metadata != null && metadata.get(0) != null) origin = metadata.get(0);
		if (new File(filePath).exists() && !swmring) {
			nexusFile = NexusFileHDF5.openNexusFile(filePath);
		} else {
			nexusFile = NexusFileHDF5.createNexusFile(filePath, swmring);
		}
		
		nexusFile.setCacheDataset(true);

		
		initGroups();
		try {
			// don't fail process because of error persisting models
			IPersistentNodeFactory pf = service.getPersistentNodeFactory();
			GroupNode gn = pf.writeOperationsToGroup(series);
			String process = Tree.ROOT + ENTRY + Node.SEPARATOR + PROCESS;
			nexusFile.addNode(process, gn);
			GroupNode or = pf.writeOriginalDataInformation(origin);
			nexusFile.addNode(process + Node.SEPARATOR + "origin", or);
		} catch (Exception e){
			logger.error("Could not persist operations!", e);
		}

		boolean groupCreated = false;
		for (int i = 0; i < series.length; i++) {
			if (series[i].isStoreOutput()) {
				if (!groupCreated) {
					createInterGroup();
					groupCreated = true;
				}

				requireNXclass(intermediate + Node.SEPARATOR + i + "-" + series[i].getName(), NexusConstants.DATA);
			}
		}
		
		lastFlush = System.currentTimeMillis();
	}

	private GroupNode requireNXclass(String name, String NXclass) throws NexusException {
		GroupNode g = nexusFile.getGroup(name, true);
		nexusFile.addAttribute(g, TreeFactory.createAttribute(NexusConstants.NXCLASS, NXclass));
		return g;
	}

	private GroupNode requireNXclass(GroupNode p, String name, String NXclass) throws NexusException {
		GroupNode g = nexusFile.getGroup(p, name, NXclass, true);
		return g;
	}

	/**
	 * Makes entry and result NXdata
	 * @throws Exception
	 */
	private void initGroups() throws Exception {
		String entry = Tree.ROOT + ENTRY;
		GroupNode group = requireNXclass(entry, NexusConstants.ENTRY);
		results = entry + Node.SEPARATOR + RESULTS_GROUP;
		group = requireNXclass(results, NexusConstants.DATA);
		if (swmring) {
			
			group = requireNXclass(entry + Node.SEPARATOR + LIVE, NexusConstants.COLLECTION);
			IDataset dataset = DatasetFactory.zeros(IntegerDataset.class, 1);
			dataset.setName(FINISHED);
			createWriteableLazy(dataset, group);
		}
		if (originalFilePath != null) {
			
			String relativePath = "Not created!";
			
			try {
				Tree tree = ProcessingUtils.getTree(null, originalFilePath);
				if (tree == null) return;
				NodeLink nl = tree.getNodeLink();
				nl.toString();
				Node d = nl.getDestination();
				GroupNode rootgroup = nexusFile.getGroup(Tree.ROOT, false);
				if (d instanceof GroupNode) {
					relativePath = Utils.translateToUnixPath(Paths.get(filePath).getParent().relativize(Paths.get(originalFilePath)).toString());

					GroupNode gn = (GroupNode)d;
					Map<String, GroupNode> groupNodeMap = gn.getGroupNodeMap();
					Set<String> keys = groupNodeMap.keySet();
					for (String key : keys) {
						String updatedName = key; 
						int count = 0;
						while (rootgroup.containsNode(updatedName)) {
							updatedName = key + count++;
						}
						nexusFile.linkExternal(new URI("nxfile", "//" + relativePath, key), Tree.ROOT + updatedName, true);
					}
				}
			} catch (Exception e) {
				logger.error("Could not link original file: {} using relative {}", originalFilePath, relativePath, e);
			}

		}
	}

	/**
	 * Make the intermediate data NXcollection to store data from the middle of the pipeline
	 */
	private void createInterGroup() {
		try {
			intermediate = Tree.ROOT + ENTRY + Node.SEPARATOR + INTER_GROUP;
			requireNXclass(intermediate, NexusConstants.COLLECTION);
		} catch (Exception e) {
			logger.error("Could not create intermediate group: {}", intermediate, e);
		}
	}

	@Override
	public void executed(OperationData result, IMonitor monitor) throws Exception {
		
		if (result == null && !swmring) return;
		
		if (result == null && swmring && !nullReturnSWMRMode.get()) {
			nullReturnSWMRMode.set(true);
			synchronized (nexusFile) {
				if (swmring) {
					nexusFile.activateSwmrMode();
					logger.debug("SWMR-ING");
				}
			}
		}
		
		if (nullReturnSWMRMode.get() || result.getData() == null) {
			flushDatasets(monitor);
			return;
		}

		// not thread-safe but closer
		boolean fNNE = firstNonNullExecution.getAndSet(false);
		
		// write data to file
		final Dataset integrated = DatasetUtils.convertToDataset(result.getData());
		SliceFromSeriesMetadata metadata = integrated.getMetadata(SliceFromSeriesMetadata.class).get(0);
		int[] dataDims = metadata.getDataDimensions();
		int[] shape = metadata.getSubSampledShape();
		Slice[] slices = metadata.getSliceInOutput();
		updateAxes(integrated, slices, shape, dataDims, results,fNNE);
		integrated.setName(DATA_NAME);

		synchronized (nexusFile) {
			appendData(integrated,nexusFile.getGroup(results,false), slices,shape, nexusFile,dataDims);
			if (fNNE){
				GroupNode group = nexusFile.getGroup(results,false);
				nexusFile.addAttribute(group, TreeFactory.createAttribute(NexusConstants.DATA_SIGNAL,integrated.getName()));
				nexusFile.addAttribute(Tree.ROOT, TreeFactory.createAttribute(NexusConstants.DEFAULT,ENTRY));
				nexusFile.addAttribute(Tree.ROOT + Node.SEPARATOR + ENTRY, TreeFactory.createAttribute(NexusConstants.DEFAULT,RESULTS_GROUP));
				
				applyTemplate();
				
				if (swmring) {
					nexusFile.activateSwmrMode();
					logger.debug("SWMR-ING");
				}
			}
			flushDatasets(monitor);
		}
	}

	private void applyTemplate() {
		if (templatePath == null || templatePath.isEmpty()) return;
		NexusTemplateService templateService = ServiceProvider.getService(NexusTemplateService.class);
		try {
			NexusTemplate template = templateService.loadTemplate(templatePath);
			logger.info("Applying template file {}",templatePath);
			template.apply(nexusFile);
		} catch (NexusException e) {
			logger.error("Could not apply template {} to nexus file",templatePath,e);
		}
	}
	
	private void flushDatasets(IMonitor monitor) {
		long time = System.currentTimeMillis();
		if (time - lastFlush > 2000) {
			lastFlush = time;
			nexusFile.flushAllCachedDatasets();
			logger.debug("Flushing");
			if (monitor instanceof IFlushMonitor) {
				((IFlushMonitor)monitor).fileFlushed();
			}
		}
	}

	private static boolean isEmpty(Serializable[] blah) {
		if (blah == null || blah.length == 0) {
			return true;
		}
		for (Serializable s : blah) {
			if (s != null) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void notify(IOperation<? extends IOperationModel, ? extends OperationData> intermediateData, OperationData data) {
		//make groups on first pass

		Serializable[] auxData = data.getAuxData();
		Serializable[] summaryData = data.getSummaryData();
		Map<String, Serializable> configured = data.getConfiguredFields();
		if (!intermediateData.isStoreOutput() && isEmpty(auxData) && isEmpty(summaryData) && configured == null) return;

		boolean first = firstNotifyMap.get(intermediateData).getAndSet(false);

		String position = String.valueOf(positionMap.get(intermediateData));
		String opGroupName = position + "-" + intermediateData.getName();

		SliceFromSeriesMetadata metadata = null;
		try {
			IDataset d = data.getData();
			if (d != null) {
				metadata = d.getMetadata(SliceFromSeriesMetadata.class).get(0);
			}
		} catch (Exception e) {
			logger.error("Cannot access series metadata, contact DAWN support", e);
			return;
		}

		if (metadata != null) {
			int[] dataDims = metadata.getDataDimensions();
			int[] shape = metadata.getSubSampledShape();
			Slice[] slices = metadata.getSliceInOutput();
	
			//if specified to save data, do it
			GroupNode group = null;
			if (intermediateData.isStoreOutput()) {
				try {
					String intermediatePosData = intermediate + Node.SEPARATOR + opGroupName;
					synchronized (nexusFile) {
						group = nexusFile.getGroup(intermediatePosData, true);
					}
					Dataset d = DatasetUtils.convertToDataset(data.getData());
					
					synchronized (nexusFile) {
						d.setName(DATA_NAME);
						appendData(d,group, slices,shape, nexusFile, dataDims);
					}
					if (first){
						synchronized (nexusFile) {
							nexusFile.addAttribute(group, TreeFactory.createAttribute(NexusConstants.DATA_SIGNAL, d.getName()));
						}
					}
					updateAxes(d, slices, shape, dataDims, intermediatePosData,first);
	
				} catch (Exception e) {
					logger.error("Could not append intermediate data", e);
				}
	
			}
	
			//save aux data (should be IDataset, with unit dimensions)
			if (!isEmpty(auxData)) {
				for (int i = 0; i < auxData.length; i++) {
					if (auxData[i] instanceof IDataset) {
						
						try {
							Dataset ds = DatasetUtils.convertToDataset((IDataset) auxData[i]);
							String currentPath = Tree.ROOT + ENTRY + Node.SEPARATOR + AUX_GROUP;
							String dsRelPath = opGroupName + Node.SEPARATOR + ds.getName();
							synchronized (nexusFile) {
								GroupNode auxG = requireNXclass(currentPath, NexusConstants.COLLECTION);
								GroupNode subGroup = requireNXclass(auxG, dsRelPath, NexusConstants.DATA);
								ds.setName(DATA_NAME);
								appendData(ds, subGroup, slices, shape, nexusFile, dataDims);
								if (first) {
									nexusFile.addAttribute(subGroup, TreeFactory.createAttribute(NexusConstants.DATA_SIGNAL, ds.getName()));
								}
							}

							currentPath += Node.SEPARATOR + dsRelPath;
							updateAxes(ds, slices, shape, dataDims, currentPath, first);
						} catch (Exception e) {
							logger.error("Could not append auxiliary data", e);
						}
					}
				}
			}
		}

		if (first && configured != null) {
			String notePath = Tree.ROOT + ENTRY + Node.SEPARATOR + PROCESS + Node.SEPARATOR + position;
			try {
				synchronized (nexusFile) {
					GroupNode nGroup = nexusFile.getGroup(notePath, false);
					writeConfiguredFields(nGroup, configured);
				}
			} catch (NexusException e) {
				logger.error("Could not write auto-configured fields", e);
			}
		}

		// save summary data (should be IDataset)
		if (!isEmpty(summaryData)) {
			for (int i = 0; i < summaryData.length; i++) {
				if (summaryData[i] instanceof IDataset) {
					
					try {
						Dataset ds = DatasetUtils.convertToDataset((IDataset) summaryData[i]).getView(false);
						String currentPath = Tree.ROOT + ENTRY + Node.SEPARATOR + SUM_GROUP;
						synchronized (nexusFile) {
							GroupNode group = null;
							try {
								group = nexusFile.getGroup(currentPath, false);
							} catch (NexusException ne) {
								if (!first && swmring) {
									logger.error("Cannot save any summary data in SWMR mode");
									return;
								}
								group = requireNXclass(currentPath, NexusConstants.COLLECTION);
							}

							currentPath += Node.SEPARATOR + opGroupName + Node.SEPARATOR + ds.getName();
							group = requireNXclass(currentPath, NexusConstants.DATA);
							ds.setName(DATA_NAME);
							writeData(ds, group, nexusFile);
							nexusFile.addAttribute(group, TreeFactory.createAttribute(NexusConstants.DATA_SIGNAL, ds.getName()));
						}
						
						writeAxes(ds, currentPath, true);
					} catch (Exception e) {
						logger.error("Could not append summary data", e);
					}
				}
			}
		}
	}

	private void writeConfiguredFields(GroupNode note, Map<String, Serializable> configuredFields) {
		try {
			IPersistentNodeFactory pf = service.getPersistentNodeFactory();
			GroupNode ac = pf.writeOperationFieldsToGroup(configuredFields);
			String n = ac.getNames().iterator().next();
			nexusFile.addNode(note, n, ac.getGroupNode(n));
		} catch (Exception e){
			logger.error("Could not persist auto-configured fields!", e);
		}
	}

	@Override
	public String getFileName() {
		return filePath;
	}

	private void updateAxes(IDataset data, Slice[] oSlice, int[] oShape, int[] dataDims, String groupName, boolean first) throws Exception {
		
		ConcurrentHashMap<Integer, String[]> axesNames = new ConcurrentHashMap<Integer,String[]>();

		groupAxesNames.putIfAbsent(groupName, axesNames);
		
		axesNames = groupAxesNames.get(groupName);

		Set<Integer> setDims = new HashSet<Integer>(dataDims.length);
		for (int i = 0; i < dataDims.length; i++) {
			setDims.add(dataDims[i]);
		}

		int[] dsShape = data.getShape();
		if (dsShape.length > oShape.length) {
			//1D to 2D
			setDims.add(dsShape.length-1);
		}
		
		if (data.getSize() == 1) setDims.clear();

		String[] axNames = null;
		if (first) {
			axNames = new String[data.getRank()];
			Arrays.fill(axNames, NexusConstants.DATA_AXESEMPTY);
		}
		
		List<AxesMetadata> mList = data.getMetadata(AxesMetadata.class);
		if (mList!= null && !mList.isEmpty()) {
			for (AxesMetadata am : mList) {
				ILazyDataset[] axes = am.getAxes();
				if (axes == null && axesNames != null)  {
					return;
				}

				int rank = axes.length;

				for (int i = 0; i < rank; i++) {
					ILazyDataset[] axis = am.getAxis(i);
					if (axis == null) {
						continue;
					}
					String[] names = new String[axis.length];
					for (int j = 0; j < axis.length; j++) {
						ILazyDataset ax = axis[j];
						if (ax == null) continue;
						String name = ax.getName();
						if (name == null || name.isEmpty()) {
							name = Integer.toString(j);
						}
						names[j] = sanitizeName(name, axesNames);
						axesNames.putIfAbsent(i, names);
						name = axesNames.get(i)[j];
						
						Dataset axDataset = null;
						
						//Can fail, due to upstream bugs
						try {
							axDataset = DatasetUtils.sliceAndConvertLazyDataset(ax);
						} catch (IllegalArgumentException ex) {
							if (name == null) {
								name = "unknown";
							}
							logger.error("Cannot slice dataset {}, try again", name, ex);
						}
						
						if (axDataset == null) continue;
						
						axDataset.setName(name);
						if (axNames != null && j == 0) {
							axNames[i] = name;
						}
						if (setDims.contains(i)) {
							if (first) {
								ILazyDataset error = axDataset.getErrors();
								IDataset e = null;
								if (error != null) {
									e = error.getSlice().squeeze();
									e.setName(axDataset.getName() + NexusConstants.DATA_ERRORS_SUFFIX);
								}
								// TODO update to 2014 axis standard
								synchronized (nexusFile) {
									DataNode dn = nexusFile.createData(nexusFile.getGroup(groupName, true), axDataset.squeeze());
									dn.addAttribute(TreeFactory.createAttribute(NexusConstants.DATA_AXIS, String.valueOf(i+1)));
									nexusFile.addAttribute(nexusFile.getGroup(groupName, true), TreeFactory.createAttribute(names[j]+NexusConstants.DATA_INDICES_SUFFIX, DatasetFactory.createFromObject(i)));
									UnitMetadata unit = axDataset.getFirstMetadata(UnitMetadata.class);
									if (unit != null) {
										nexusFile.addAttribute(dn, TreeFactory.createAttribute(NexusConstants.UNITS,unit.toString()));
									}
									if (e != null) {
										nexusFile.createData(nexusFile.getGroup(groupName, true), e);
										nexusFile.addAttribute(groupName, TreeFactory.createAttribute(axDataset.getName() + NexusConstants.DATA_UNCERTAINTY_SUFFIX, e.getName()));
									}
								}
							}
						} else {
							synchronized (nexusFile) {
								appendSingleValueAxis(axDataset, groupName, oSlice, oShape, i);

								if (first) {
									nexusFile.getData(groupName + Node.SEPARATOR + axDataset.getName()).addAttribute(TreeFactory.createAttribute(NexusConstants.DATA_AXIS, String.valueOf(i+1)));
								}

								ILazyDataset error = axDataset.getErrors();
								if (error != null) {
									Dataset e = DatasetUtils.sliceAndConvertLazyDataset(error);
									e.setName(axDataset.getName() + NexusConstants.DATA_ERRORS_SUFFIX);
									appendSingleValueAxis(e,groupName, oSlice,oShape, i);
								}
							}
						}

					}
				}
				
				if (first && axNames != null) {
					synchronized (nexusFile) {
						GroupNode group = nexusFile.getGroup(groupName, false);
						nexusFile.addAttribute(group, TreeFactory.createAttribute(NexusConstants.DATA_AXES, DatasetFactory.createFromObject(axNames)));
						axNames = null;
					}
				}

			}
		}
	}
	
	private void writeAxes(IDataset data, String groupName, boolean first) throws Exception {
		
		ConcurrentHashMap<Integer, String[]> axesNames = new ConcurrentHashMap<Integer,String[]>();

		groupAxesNames.putIfAbsent(groupName, axesNames);
		
		axesNames = groupAxesNames.get(groupName);

		String[] axNames = null;
		if (first) {
			axNames = new String[data.getRank()];
			Arrays.fill(axNames, NexusConstants.DATA_AXESEMPTY);
		}
		
		List<AxesMetadata> mList = data.getMetadata(AxesMetadata.class);
		if (mList!= null && !mList.isEmpty()) {
			for (AxesMetadata am : mList) {
				ILazyDataset[] axes = am.getAxes();
				if (axes == null && axesNames != null)  {
					return;
				}

				int rank = axes.length;

				for (int i = 0; i < rank; i++) {
					ILazyDataset[] axis = am.getAxis(i);
					if (axis == null) {
						continue;
					}
					String[] names = new String[axis.length];
					for (int j = 0; j < axis.length; j++) {
						ILazyDataset ax = axis[j];
						if (ax == null) continue;
						String name = ax.getName();
						if (name == null || name.isEmpty()) {
							name = Integer.toString(j);
						}
						names[j] = sanitizeName(name, axesNames);
						axesNames.putIfAbsent(i, names);
						name = axesNames.get(i)[j];
						Dataset axDataset = DatasetUtils.sliceAndConvertLazyDataset(ax);
						axDataset.setName(name);
						if (axNames != null && j == 0) {
							axNames[i] = name;
						}
						if (first) {
							ILazyDataset error = axDataset.getErrors();
							IDataset e = null;
							if (error != null) {
								e = error.getSlice();
								e.squeeze();
								if (e.getRank() == 0) {
									e.setShape(1);
								}
								e.setName(axDataset.getName() + NexusConstants.DATA_ERRORS_SUFFIX);
							}
							// TODO update to 2014 axis standard
							synchronized (nexusFile) {
								GroupNode gn = nexusFile.getGroup(groupName, true);
								if (gn.containsDataNode(name)) { // final check as there is a race condition
									continue;
								}
								axDataset.squeeze();
								if (axDataset.getRank() == 0) {
									axDataset.setShape(1);
								}
								DataNode dn = nexusFile.createData(gn, axDataset);
								dn.addAttribute(TreeFactory.createAttribute(NexusConstants.DATA_AXIS, String.valueOf(i + 1))); // FIXME needed???
								Dataset indices = axDataset.getRank() <= 1 ? DatasetFactory.createFromObject(i) :
									DatasetFactory.createRange(IntegerDataset.class, axDataset.getRank());
								nexusFile.addAttribute(nexusFile.getGroup(groupName, true),
										TreeFactory.createAttribute(names[j] + NexusConstants.DATA_INDICES_SUFFIX,
												indices));
								UnitMetadata unit = axDataset.getFirstMetadata(UnitMetadata.class);
								if (unit != null) {
									nexusFile.addAttribute(dn,
											TreeFactory.createAttribute(NexusConstants.UNITS, unit.toString()));
								}
								if (e != null) {
									nexusFile.createData(nexusFile.getGroup(groupName, true), e);
									nexusFile.addAttribute(groupName, TreeFactory.createAttribute(
											axDataset.getName() + NexusConstants.DATA_UNCERTAINTY_SUFFIX, e.getName()));
								}
							}
						}

					}
				}
				
				if (first && axNames != null) {
					synchronized (nexusFile) {
						GroupNode group = nexusFile.getGroup(groupName, false);
						nexusFile.addAttribute(group, TreeFactory.createAttribute(NexusConstants.DATA_AXES, DatasetFactory.createFromObject(axNames)));
						axNames = null;
					}
				}

			}
		}
	}

	private String sanitizeName(String name, ConcurrentHashMap<Integer, String[]> names) {
		//assume only our slicing puts [ in a axis name!
		if (name.contains("[")) {
			name = name.split("\\[")[0];
		}

		if (name.contains(Node.SEPARATOR)) {
			String[] split = name.split(Node.SEPARATOR);
			name = split[split.length-1];
		}
		
		if (name == null || name.isEmpty() || name.equals(DATA_NAME)) {
			name = "axis";
		}
		
		return getNonDuplicateName(name, names);
	}

	private String getNonDuplicateName(String name, ConcurrentHashMap<Integer, String[]> names) {

		boolean duplicateName = true;
		int n = 1;
		while (duplicateName) {
			duplicateName = false;
			for (Entry<Integer, String[]> e : names.entrySet()) {
				for (String s : e.getValue()) {
					if (name.equals(s)) {
						duplicateName = true;
						break;
					}
				}
			}
			
			if (duplicateName) name = name+n++;
		}

		return name;
	}

	/**
	 * Write the data into the correct position, in the correct dataset
	 * @param dataset
	 * @param group
	 * @param oSlice
	 * @param oShape
	 * @param file
	 * @throws Exception
	 */
	private void appendData(Dataset dataset, GroupNode group, Slice[] oSlice, int[] oShape, NexusFile file, int[] dataDims) throws Exception {
		int[] dShape = dataset.getShapeRef();
		if (ShapeUtils.squeezeShape(dShape, false).length == 0) {
			//padding slice and shape does not play nice with single values of rank != 0
			dataset = dataset.getSliceView().squeeze();
			dShape = dataset.getShapeRef();
		}
		
		//determine the dimensions of the original data
		int[] dd = dataDims.clone();
		Arrays.sort(dd);
		//update the slice to reflect the new data shape/rank
		Slice[] sliceOut = getUpdatedSliceArray(oShape, dShape, oSlice, dd);
		//determine shape of full output dataset
		long[] newShape = getNewShape(oShape, dShape, dd);
		
		if (dataset.getRank() == 0) {
			int[] shape = new int[Math.max(1, newShape.length)];
			Arrays.fill(shape, 1);
			dataset.setShape(shape);
			dShape = shape;
		}
		
		//write
		DataNode dn = null;
		boolean append = group.containsDataNode(dataset.getName());
		if (append) {
			dn = file.getData(group,dataset.getName());
		} else {
			createWriteableLazy(dataset, group);
			dn = file.getData(group,dataset.getName());
		}

		ILazyWriteableDataset wds = dn.getWriteableDataset();
		int[] mShape = wds.getMaxShape();
		SliceND s = new SliceND(wds.getShape(), mShape, sliceOut);
		if (append && dShape.length > 1) {
			// check if new auxiliary data has dimensions greater than existing dataset
			boolean crop = false;
			SliceND nSlice = new SliceND(dShape);
			for (int i = 0; i < mShape.length; i++) {
				int l = mShape[i];
				if (l > 0 && dShape[i] > l) { // truncate slice dimensions
					s.setSlice(i, 0, l, 1);
					nSlice.setSlice(i, 0, l, 1);
					crop = true;
				}
			}
			if (crop) {
				dataset = dataset.getSliceView(nSlice);
			}
		}
		wds.setSlice(null, dataset, s);


		ILazyDataset error = dataset.getErrors();

		if (error != null) {
			Dataset e = DatasetUtils.sliceAndConvertLazyDataset(error);
			e.setName(NexusConstants.DATA_ERRORS);
			dn = null;
			if (group.containsDataNode(e.getName())){
				dn = file.getData(group,e.getName());
			} else {
				createWriteableLazy(e, group);
				nexusFile.addAttribute(group, TreeFactory.createAttribute(dataset.getName() + NexusConstants.DATA_UNCERTAINTY_SUFFIX, e.getName()));
				dn = file.getData(group,e.getName());
			}

			ILazyWriteableDataset wdse = dn.getWriteableDataset();
			s = new SliceND(wdse.getShape(), wdse.getMaxShape(), sliceOut);
			wdse.setSlice(null, e, s);
		}
	}


	/**
	 * Write or overwrite the data into the correct position
	 * @param dataset
	 * @param group
	 * @param file
	 * @throws Exception
	 */
	private void writeData(Dataset dataset, GroupNode group, NexusFile file) throws Exception {
		
		//determine shape of full output dataset
		int rank = dataset.getRank();
		if (rank == 0) {
			rank = 1;
			int[] shape = new int[rank];
			Arrays.fill(shape, 1);
			dataset.setShape(shape);
		}

		long[] newShape = new long[rank];
		for (int i = 0; i < rank; i++) {
			newShape[i] = dataset.getShapeRef()[i];
		}

		//write
		DataNode dn = null;
		if (group.containsDataNode(dataset.getName())){
			dn = file.getData(group,dataset.getName());
		} else {
			createWriteableLazy(dataset, group);
			dn = file.getData(group,dataset.getName());
		}

		ILazyWriteableDataset wds = dn.getWriteableDataset();
		wds.setSlice(null, dataset, null);

		ILazyDataset error = dataset.getErrors();

		if (error != null) {
			Dataset e = DatasetUtils.sliceAndConvertLazyDataset(error);
			e.setName(NexusConstants.DATA_ERRORS);
			dn = null;
			if (group.containsDataNode(e.getName())){
				dn = file.getData(group,e.getName());
			} else {
				createWriteableLazy(e, group);
				nexusFile.addAttribute(group, TreeFactory.createAttribute(dataset.getName() + NexusConstants.DATA_UNCERTAINTY_SUFFIX, e.getName()));
				dn = file.getData(group,e.getName());
			}

			ILazyWriteableDataset wdse = dn.getWriteableDataset();
			wdse.setSlice(null, e, null);
		}
	}

	@Override
	public void close() throws Exception {
		if (nexusFile != null) {
			
			if (swmring) {
				DataNode dn = nexusFile.getData(Node.SEPARATOR + ENTRY + Node.SEPARATOR + LIVE + Node.SEPARATOR + FINISHED);
				ILazyWriteableDataset wds = dn.getWriteableDataset();
				wds.setSlice(null, DatasetFactory.ones(IntegerDataset.class, 1), null);
			}
			
			nexusFile.flush();
			nexusFile.close();
		}

	}

	private void appendSingleValueAxis(Dataset dataset, String group, Slice[] oSlice, int[] oShape, int axisDim) throws Exception{
		dataset = dataset.reshape(1);
		DataNode dn = null;
		String dataPath = group + Node.SEPARATOR + dataset.getName();
		try {
			dn = nexusFile.getData(dataPath);
		} catch (Exception e) {
			createWriteableLazy(dataset, nexusFile.getGroup(group, true));
			dn = nexusFile.getData(dataPath);
			nexusFile.addAttribute(group, TreeFactory.createAttribute(dataset.getName() + NexusConstants.DATA_INDICES_SUFFIX, DatasetFactory.createFromObject(axisDim)));
		}
	
		ILazyWriteableDataset wds = dn.getWriteableDataset();
		SliceND s = new SliceND(wds.getShape(), wds.getMaxShape(), oSlice[axisDim]);
		wds.setSlice(null, dataset, s);
	}

	/**
	 * Parse slice array to determine which dimensions are not equal to 1 and assume these are the data dimensions
	 * @param slices
	 * @param shape
	 * @return data dims
	 */
	private int[] getNonSingularDimensions(Slice[] slices, int[] shape) {
		int[] newShape = new SliceND(shape, slices).getShape();

		List<Integer> notOne = new ArrayList<Integer>();

		for (int i = 0; i < newShape.length; i++) if (newShape[i] != 1) notOne.add(i);

		int[] out = new int[notOne.size()];
		for (int i = 0; i < out.length; i++) {
			out[i] = notOne.get(i);
		}

		return out;
	}

	/**
	 * Get a new slice array which reflects the position of the processed data in the full o)utput dataset
	 * @param oShape
	 * @param dsShape
	 * @param oSlice
	 * @param datadims
	 * @return newSlices
	 */
	private Slice[] getUpdatedSliceArray(int[] oShape, int[] dsShape, Slice[] oSlice, int[] datadims) {

		if (ShapeUtils.squeezeShape(dsShape, false).length == 0) {
			List<Slice> l = new LinkedList<Slice>(Arrays.asList(oSlice));
			for (int i =  datadims.length-1; i >= 0; i--) {
				l.remove(datadims[i]);
			}
			return l.toArray(new Slice[l.size()]);

		}

		Arrays.sort(datadims);
		Slice[] out = null;
		int dRank = oShape.length - dsShape.length;
		int[] s = oShape.clone();
		out = oSlice.clone();
		if (dRank == 0) {
			for (int i = 0; i < datadims.length; i++) {
				out[datadims[i]] = new Slice(0, dsShape[datadims[i]], 1);
				s[datadims[i]] = s[datadims[i]];
			}
		} else if (dRank > 0) {
			List<Slice> l = new LinkedList<Slice>(Arrays.asList(out));
			l.remove(datadims[datadims.length-1]);
			out = l.toArray(new Slice[l.size()]);
			out[datadims[0]] = new Slice(0, dsShape[datadims[0]], 1);
		} else if (dRank < 0) {
			for (int i = 0; i < datadims.length; i++) {
				out[datadims[i]] = new Slice(0, dsShape[datadims[i]], 1);
				s[datadims[i]] = s[datadims[i]];
			}

			List<Slice> l = new LinkedList<Slice>(Arrays.asList(out));
			l.add(new Slice(0, dsShape[dsShape.length-1], 1));
			out = l.toArray(new Slice[l.size()]);
		}

		return out;
	}

	/**
	 * Get the expected final shape of the output dataset
	 * @param oShape
	 * @param dsShape
	 * @param dd
	 * @return newShape
	 */
	private long[] getNewShape(int[]oShape, int[] dsShape, int[] dd) {

		if (ShapeUtils.squeezeShape(dsShape, false).length == 0) {
			List<Integer> l = new LinkedList<Integer>();
			for (int i : oShape) l.add(i);
			for (int i =  dd.length-1; i >= 0; i--) {
				l.remove(dd[i]);
			}
			long[] out = new long[l.size()];
			for (int i = 0; i< l.size(); i++) out[i] = l.get(i);
			return out;

		}

		int dRank = oShape.length - dsShape.length;
		long[] out = null;

		if (dRank == 0) {
			out = new long[oShape.length];
			for (int i = 0; i < oShape.length; i++) out[i] = oShape[i];
			for (int i : dd) {
				out[i] = dsShape[i]; 
			}
		} else if (dRank > 0) {
			List<Integer> ll = new LinkedList<Integer>();
			for (int i : oShape) ll.add(i);
			for (int i = 0; i < dd.length ; i++) if (i < dRank) ll.remove(dd[i]);
			for (int i = 0;  i < dRank; i++) ll.set(dd[i], dsShape[dd[i]]);
			out = new long[ll.size()];
			for (int i = 0; i< ll.size(); i++) out[i] = ll.get(i);

		} else if (dRank < 0) {
			List<Integer> ll = new LinkedList<Integer>();
			for (int i : oShape) ll.add(i);
			for (int i = 0; i < dd.length ; i++) ll.set(dd[i], dsShape[dd[i]]);
			for (int i = dRank;  i < 0; i++){
				ll.add(dsShape[dsShape.length+i]);
			}
			out = new long[ll.size()];
			for (int i = 0; i< ll.size(); i++) out[i] = ll.get(i);

		}

		return out;
	}
	
	private void createWriteableLazy(IDataset dataset, GroupNode group) throws Exception {

		Dataset d = DatasetUtils.convertToDataset(dataset);
		int[] mx = determineMaxShape(d.getShape());

		ILazyWriteableDataset lwds = new LazyWriteableDataset(d.getName(), d.getElementClass(),
				d.getShape(), mx, d.getShape(), null);
		
		DataNode dn = nexusFile.createData(group, lwds, NexusFile.COMPRESSION_NONE);
		
		UnitMetadata unit = dataset.getFirstMetadata(UnitMetadata.class);
		if (unit != null) {
			nexusFile.addAttribute(dn, TreeFactory.createAttribute(NexusConstants.UNITS,unit.toString()));
		}
	}

	private int[] determineMaxShape(int[] maxShape) { // by checking for dimensions of 1(!)
		for (int i = 0; i < maxShape.length; i++) {
			if (maxShape[i] == 1) {
				maxShape[i] = ILazyWriteableDataset.UNLIMITED;
			}
		}
		return maxShape;
	}

	@Override
	public void includeLinkTo(String fileName) {
		originalFilePath = fileName;
	}
	@Override
	public void useTemplate(String templateFilePath) {
		templatePath = templateFilePath;
	}

}

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

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.io.ILoaderService;
import org.eclipse.dawnsci.analysis.api.metadata.UnitMetadata;
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
import org.eclipse.dawnsci.analysis.tree.impl.AttributeImpl;
import org.eclipse.dawnsci.hdf5.nexus.NexusFileHDF5;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusFile;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.io.NexusTreeUtils;
import uk.ac.diamond.scisoft.analysis.processing.IFlushMonitor;
import uk.ac.diamond.scisoft.analysis.processing.LocalServiceManager;

public class NexusFileExecutionVisitor implements IExecutionVisitor, ISavesToFile {
	
	private static IPersistenceService service;
	public static void setPersistenceService(IPersistenceService s) { // Injected
		service = s;
	}
	public NexusFileExecutionVisitor() {
		// Used for OSGI
	}

	private final String RESULTS_GROUP = "result";
	private final String INTER_GROUP = "intermediate";
	private final String AUX_GROUP = "auxiliary";
	private final String SUM_GROUP = "summary";
	private final String ENTRY = "entry";
	private final String LIVE = "live";
	private final String FINISHED = "finished";

	private Map<IOperation, AtomicBoolean> firstNotifyMap;
	private Map<IOperation, Integer> positionMap;
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

	private final static Logger logger = LoggerFactory.getLogger(NexusFileExecutionVisitor.class);
	
	public NexusFileExecutionVisitor(String filePath, boolean swmr, String originalFilePath) {
		this.filePath = filePath;
		firstNotifyMap = new ConcurrentHashMap<IOperation, AtomicBoolean>();
		positionMap = new ConcurrentHashMap<IOperation, Integer>();
		this.swmring = swmr;
		this.originalFilePath = originalFilePath;
	}
	
	public NexusFileExecutionVisitor(String filePath, boolean swmr) {
		this(filePath,swmr,null);
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
//		file = HierarchicalDataFactory.getWriter(filePath);
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
			String process = Tree.ROOT + ENTRY + Node.SEPARATOR + "process";
			nexusFile.addNode(process, gn);
			GroupNode or = pf.writeOriginalDataInformation(origin);
			nexusFile.addNode(process + Node.SEPARATOR + "origin", or);
		} catch (Exception e){
			logger.error("Cant persist operations!", e);
		}

		
		boolean groupCreated = false;
		for (int i = 0; i < series.length; i++) {
			if (series[i].isStoreOutput()) {
				if (!groupCreated){
					createInterGroup();
					groupCreated = true;
				}

				requireNXclass(intermediate+ Node.SEPARATOR + i + "-" + series[i].getName(), NX_DATA);
			}
		}
		
		lastFlush = System.currentTimeMillis();
	}

	private static final String NX_ENTRY = "NXentry";
	private static final String NX_SUBENTRY = "NXsubentry";
	private static final String NX_DATA = NexusTreeUtils.NX_DATA;
	private static final String NX_COLLECTION = "NXcollection";

	private GroupNode requireNXclass(String name, String NXclass) throws NexusException {
		GroupNode g = nexusFile.getGroup(name, true);
		nexusFile.addAttribute(g, new AttributeImpl(NexusTreeUtils.NX_CLASS, NXclass));
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
		GroupNode group = requireNXclass(entry, NX_ENTRY);
		results = entry + Node.SEPARATOR + RESULTS_GROUP;
		group = requireNXclass(results, NX_DATA);
		if (swmring) {
			
			group = requireNXclass(entry + Node.SEPARATOR + LIVE, NX_COLLECTION);
			IDataset dataset = DatasetFactory.zeros(IntegerDataset.class, 1);
			dataset.setName(FINISHED);
			createWriteableLazy(dataset, group);
		}
		if (originalFilePath != null) {
			try {
				ILoaderService loaderService = LocalServiceManager.getLoaderService();
				IDataHolder dh = loaderService.getData(originalFilePath, null);
				Tree tree = dh.getTree();
				if (tree == null) return;
				NodeLink nl = tree.getNodeLink();
				nl.toString();
				Node d = nl.getDestination();
				if (d instanceof GroupNode) {
					GroupNode gn = (GroupNode)d;
					Map<String, GroupNode> groupNodeMap = gn.getGroupNodeMap();
					Set<String> keys = groupNodeMap.keySet();
					for (String key : keys) {
						String updatedName = "raw_" + key;
						int count = 0;
						while(keys.contains(updatedName)) updatedName = "raw_" + key + count++;
						nexusFile.linkExternal(new URI("nxfile://"+originalFilePath+"#"+key),Tree.ROOT + updatedName, true);
					}
				}
			} catch (Exception e) {
				logger.error("Could not link original file", e);
			}

		}
	}

	/**
	 * Make the intermediate data NXcollection to store data from the middle of the pipeline
	 */
	private void createInterGroup() {
		try {
			intermediate = Tree.ROOT +ENTRY + Node.SEPARATOR + INTER_GROUP;
			requireNXclass(intermediate, NX_SUBENTRY);
		} catch (Exception e) {
			logger.error(e.getMessage());
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
		
		if (nullReturnSWMRMode.get()) {
			flushDatasets(monitor);
			return;
		}
		
		//not threadsafe but closer
		boolean fNNE = firstNonNullExecution.getAndSet(false);
		
		//Write data to file
		final Dataset integrated = DatasetUtils.convertToDataset(result.getData());
		SliceFromSeriesMetadata metadata = integrated.getMetadata(SliceFromSeriesMetadata.class).get(0);
		int[] dataDims = metadata.getDataDimensions();
		int[] shape = metadata.getSubSampledShape();
		Slice[] slices = metadata.getSliceInOutput();
		updateAxes(integrated, slices, shape, dataDims, results,fNNE);
		integrated.setName("data");
		synchronized (nexusFile) {
			appendData(integrated,nexusFile.getGroup(results,false), slices,shape, nexusFile,dataDims);
			if (fNNE){
				GroupNode group = nexusFile.getGroup(results,false);
				nexusFile.addAttribute(group,new AttributeImpl(NexusTreeUtils.NX_SIGNAL,integrated.getName()));
				if (swmring) {
					nexusFile.activateSwmrMode();
					logger.debug("SWMR-ING");
				}
			}
			flushDatasets(monitor);
		}
	}
	
	private void flushDatasets(IMonitor monitor) {
		long time = System.currentTimeMillis();
		if (time - lastFlush > 2000) {
			lastFlush = time;
			((NexusFileHDF5)nexusFile).flushAllCachedDatasets();
			logger.debug("Flushing");
			if (monitor instanceof IFlushMonitor) {
				((IFlushMonitor)monitor).fileFlushed();
			}
		}
	}

	private static boolean isEmpty(Serializable... blah) {
		return blah == null || blah.length == 0 || blah[0] == null;
	}

	@Override
	public void notify(IOperation<? extends IOperationModel, ? extends OperationData> intermediateData, OperationData data) {
		//make groups on first pass

		Serializable[] auxData = data.getAuxData();
		Serializable[] summaryData = data.getSummaryData();
		if (!intermediateData.isStoreOutput() && isEmpty(auxData) && isEmpty(summaryData)) return;

		boolean first = firstNotifyMap.get(intermediateData).getAndSet(false);

		String position = String.valueOf(positionMap.get(intermediateData));

		SliceFromSeriesMetadata metadata;
		try {
			metadata = data.getData().getMetadata(SliceFromSeriesMetadata.class).get(0);
		} catch (Exception e) {
			logger.error("", "Cannot access series metadata, contact DAWN support");
			return;
		}


		int[] dataDims = metadata.getDataDimensions();
		int[] shape = metadata.getSubSampledShape();
		Slice[] slices = metadata.getSliceInOutput();

		//if specified to save data, do it
		if (intermediateData.isStoreOutput()) {
			try {
				String intermediatePosData = intermediate + Node.SEPARATOR + position + "-" + intermediateData.getName();
				GroupNode group;
				synchronized (nexusFile) {
					group = nexusFile.getGroup(intermediatePosData, true);	
				}
				Dataset d = DatasetUtils.convertToDataset(data.getData());
				
				synchronized (nexusFile) {
					d.setName("data");
					appendData(d,group, slices,shape, nexusFile, dataDims);
				}
				if (first){
					synchronized (nexusFile) {
						nexusFile.addAttribute(group,new AttributeImpl("signal", d.getName()));
					}
				}
				updateAxes(d, slices, shape, dataDims, intermediatePosData,first);

			} catch (Exception e) {
				logger.error(e.getMessage());
			}

		}

		//save aux data (should be IDataset, with unit dimensions)
		if (!isEmpty(auxData)) {
			for (int i = 0; i < auxData.length; i++) {
				if (auxData[i] instanceof IDataset) {
					
					try {
						Dataset ds = DatasetUtils.convertToDataset((IDataset) auxData[i]);
						String dsName = ds.getName();
						GroupNode group;
						String currentGroup = Tree.ROOT + ENTRY + Node.SEPARATOR + AUX_GROUP;
						synchronized (nexusFile) {
							GroupNode auxG = requireNXclass(currentGroup, NX_SUBENTRY);
							group = requireNXclass(auxG, position + "-" + intermediateData.getName() + Node.SEPARATOR + dsName, NX_DATA);
							currentGroup += Node.SEPARATOR + position + "-" + intermediateData.getName();
							ds.setName("data");
							appendData(ds, group, slices, shape, nexusFile, dataDims);
							if (first){
								nexusFile.addAttribute(group,new AttributeImpl("signal", ds.getName()));
							}
						}
						
						updateAxes(ds, slices, shape, dataDims, currentGroup + Node.SEPARATOR + dsName, first);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		}

		//save summary data (should be IDataset, with unit dimensions)
		if (!isEmpty(summaryData)) {
			for (int i = 0; i < summaryData.length; i++) {
				if (summaryData[i] instanceof IDataset) {
					
					try {
						Dataset ds = DatasetUtils.convertToDataset((IDataset) summaryData[i]);
						String dsName = ds.getName();
						GroupNode group;
						String currentPath = Tree.ROOT + ENTRY + Node.SEPARATOR + SUM_GROUP;
						synchronized (nexusFile) {
							group = requireNXclass(currentPath, NX_SUBENTRY);
							String gName = position + "-" + intermediateData.getName();
							currentPath += Node.SEPARATOR + gName + Node.SEPARATOR + dsName;
							group = requireNXclass(currentPath, NX_DATA);
							ds.setName("data");
							writeData(ds, group, nexusFile);
							if (i == 0) {
								nexusFile.addAttribute(group, new AttributeImpl("signal", ds.getName()));
							}
						}
						
						writeAxes(ds, currentPath, true);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
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
		if (dsShape.length > dataDims.length) {
			//1D to 2D
			setDims.add(dsShape.length-1);
		}
		
		if (data.getSize() == 1) setDims.clear();

		String[] axNames = null;
		if (first) {
			axNames = new String[data.getRank()];
			Arrays.fill(axNames,NexusTreeUtils.NX_AXES_EMPTY);
		}
		
		List<AxesMetadata> mList = data.getMetadata(AxesMetadata.class);
		if (mList!= null && !mList.isEmpty()) {
			int count = 0;
			for (AxesMetadata am : mList) {
				ILazyDataset[] axes = am.getAxes();
				if (axes == null && axesNames != null)  {
					return;
				}

				int rank = axes.length;

				for (int i = 0; i< rank; i++) {
					ILazyDataset[] axis = am.getAxis(i);
					if (axis == null) {
						if (axNames != null)  {
							axNames[count++] = ".";
						}

						continue;
					}
					String[] names = new String[axis.length];
					for (int j = 0; j < axis.length; j++) {
						ILazyDataset ax = axis[j];
						if (ax == null) continue;
						String name = ax.getName();
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
							logger.error("Cannot slice dataset " + name + " try again",ex);
						}
						
						if (axDataset == null) continue;
						 
						axDataset.setName(name);
						if (axNames != null && j == 0) axNames[count++] = name;
						if (setDims.contains(i)) {
							if(first) {
								ILazyDataset error = axDataset.getErrors();
								IDataset e = null;
								if (error != null) {
									e = error.getSlice().squeeze();
									e.setName(axDataset.getName() + "_errors");
								}
								
								synchronized (nexusFile) {
									DataNode dn = nexusFile.createData(nexusFile.getGroup(groupName, true), axDataset.squeeze());
									dn.addAttribute(new AttributeImpl("axis", String.valueOf(i+1)));
									nexusFile.addAttribute(nexusFile.getGroup(groupName, true), new AttributeImpl(names[j]+NexusTreeUtils.NX_INDICES_SUFFIX, DatasetFactory.createFromObject(i)));
									UnitMetadata unit = axDataset.getFirstMetadata(UnitMetadata.class);
									if (unit != null) {
										nexusFile.addAttribute(dn,new AttributeImpl(NexusTreeUtils.NX_UNITS,unit.toString()));
									}
									if (e != null) {
										nexusFile.createData(nexusFile.getGroup(groupName, true), e);
										nexusFile.addAttribute(groupName, new AttributeImpl(axDataset.getName() + NexusTreeUtils.NX_UNCERTAINTY_SUFFIX, e.getName()));
									}
								}
							}
						} else {
							synchronized (nexusFile) {
								appendSingleValueAxis(axDataset,groupName, oSlice,oShape, nexusFile,i);

								if (first) {

									nexusFile.getData(groupName + Node.SEPARATOR +axDataset.getName()).addAttribute(new AttributeImpl("axis", String.valueOf(i+1)));

								}

								ILazyDataset error = axDataset.getErrors();

								if (error != null) {
									Dataset e = DatasetUtils.sliceAndConvertLazyDataset(error);
									e.setName(axDataset.getName() + "_errors");

									appendSingleValueAxis(e,groupName, oSlice,oShape, nexusFile,i);
								}
								
							}
						}

					}
				}
				
				if (first && axNames != null) {
					synchronized (nexusFile) {
						GroupNode group = nexusFile.getGroup(groupName, false);
						nexusFile.addAttribute(group, new AttributeImpl("axes", DatasetFactory.createFromObject(axNames)));
//						for (int i = 0; i < axNames.length; i++) {
//							if (axNames[i] != null && !axNames[i].equals(NexusTreeUtils.NX_AXES_EMPTY)) nexusFile.addAttribute(group, new AttributeImpl(axNames[i]+NexusTreeUtils.NX_INDICES_SUFFIX, DatasetFactory.createFromObject(i)));
//						}
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
			Arrays.fill(axNames,NexusTreeUtils.NX_AXES_EMPTY);
		}
		
		List<AxesMetadata> mList = data.getMetadata(AxesMetadata.class);
		if (mList!= null && !mList.isEmpty()) {
			int count = 0;
			for (AxesMetadata am : mList) {
				ILazyDataset[] axes = am.getAxes();
				if (axes == null && axesNames != null)  {
					return;
				}

				int rank = axes.length;

				for (int i = 0; i< rank; i++) {
					ILazyDataset[] axis = am.getAxis(i);
					if (axis == null) {
						if (axNames != null)  {
							axNames[count++] = ".";
						}

						continue;
					}
					String[] names = new String[axis.length];
					for (int j = 0; j < axis.length; j++) {
						ILazyDataset ax = axis[j];
						if (ax == null) continue;
						String name = ax.getName();
						names[j] = sanitizeName(name, axesNames);
						axesNames.putIfAbsent(i, names);
						name = axesNames.get(i)[j];
						Dataset axDataset = DatasetUtils.sliceAndConvertLazyDataset(ax);
						axDataset.setName(name);
						if (axNames != null && j == 0)
							axNames[count++] = name;
						if (first) {
							ILazyDataset error = axDataset.getErrors();
							IDataset e = null;
							if (error != null) {
								e = error.getSlice().squeeze();
								e.setName(axDataset.getName() + "_errors");
							}

							synchronized (nexusFile) {
								GroupNode gn = nexusFile.getGroup(groupName, true);
								if (gn.containsDataNode(name)) { // final check as there is a race condition
									continue;
								}
								DataNode dn = nexusFile.createData(gn, axDataset.squeeze());
								dn.addAttribute(new AttributeImpl("axis", String.valueOf(i + 1))); // FIXME needed???
								nexusFile.addAttribute(nexusFile.getGroup(groupName, true),
										new AttributeImpl(names[j] + NexusTreeUtils.NX_INDICES_SUFFIX,
												DatasetFactory.createFromObject(i)));
								UnitMetadata unit = axDataset.getFirstMetadata(UnitMetadata.class);
								if (unit != null) {
									nexusFile.addAttribute(dn,
											new AttributeImpl(NexusTreeUtils.NX_UNITS, unit.toString()));
								}
								if (e != null) {
									nexusFile.createData(nexusFile.getGroup(groupName, true), e);
									nexusFile.addAttribute(groupName, new AttributeImpl(
											axDataset.getName() + NexusTreeUtils.NX_UNCERTAINTY_SUFFIX, e.getName()));
								}
							}
						}

					}
				}
				
				if (first && axNames != null) {
					synchronized (nexusFile) {
						GroupNode group = nexusFile.getGroup(groupName, false);
						nexusFile.addAttribute(group, new AttributeImpl("axes", DatasetFactory.createFromObject(axNames)));
//						for (int i = 0; i < axNames.length; i++) {
//							if (axNames[i] != null && !axNames[i].equals(NexusTreeUtils.NX_AXES_EMPTY)) nexusFile.addAttribute(group, new AttributeImpl(axNames[i]+NexusTreeUtils.NX_INDICES_SUFFIX, DatasetFactory.createFromObject(i)));
//						}
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
		
		if (name == null || name.isEmpty() || name.equals("data")) {
			name = "axis";
		}
		
		if (name != null) name = getNonDuplicateName(name, names);
		
		return name;
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

	private void appendSingleValueAxis(Dataset dataset, String group, Slice[] oSlice, int[] oShape, NexusFile file, int axisDim) throws Exception{
		dataset = dataset.getSliceView();
		dataset.setShape(1);
		DataNode dn = null;
		try {
			dn = file.getData(group+Node.SEPARATOR+dataset.getName());
		} catch (Exception e) {
			createWriteableLazy(dataset, file.getGroup(group, true));
			dn = file.getData(group+Node.SEPARATOR+dataset.getName());
			nexusFile.addAttribute(group, new AttributeImpl(dataset.getName()+NexusTreeUtils.NX_INDICES_SUFFIX, DatasetFactory.createFromObject(axisDim)));
		}

		ILazyWriteableDataset wds = dn.getWriteableDataset();
		SliceND s = new SliceND(dataset.getShape(),determineMaxShape(dataset),new Slice[]{oSlice[axisDim]});
		wds.setSlice(null, dataset, s);
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
		
		if (ShapeUtils.squeezeShape(dataset.getShape(), false).length == 0) {
			//padding slice and shape does not play nice with single values of rank != 0
			dataset = dataset.getSliceView().squeeze();
//			dataset.setShape(new int[]{1});
		}
		
		//determine the dimensions of the original data
		int[] dd = dataDims.clone();
		Arrays.sort(dd);
		//update the slice to reflect the new data shape/rank
		Slice[] sliceOut = getUpdatedSliceArray( oShape, dataset.getShape(), oSlice, dd);
		//determine shape of full output dataset
		long[] newShape = getNewShape(oShape, dataset.getShape(), dd);
		
		if (dataset.getRank() == 0) {
//			int[] shape = new int[newShape.length];
			int[] shape = newShape.length == 0 ? new int[1] : new int[newShape.length];
			Arrays.fill(shape, 1);
			dataset.setShape(shape);
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
		SliceND s = new SliceND(dataset.getShape(),determineMaxShape(dataset),sliceOut);
		wds.setSlice(null, dataset, s);


		ILazyDataset error = dataset.getErrors();

		if (error != null) {
			Dataset e = DatasetUtils.sliceAndConvertLazyDataset(error);
			e.setName("errors");
			dn = null;
			if (group.containsDataNode(e.getName())){
				dn = file.getData(group,e.getName());
			} else {
				createWriteableLazy(e, group);
				nexusFile.addAttribute(group, new AttributeImpl(dataset.getName() + NexusTreeUtils.NX_UNCERTAINTY_SUFFIX, e.getName()));
				dn = file.getData(group,e.getName());
			}

			ILazyWriteableDataset wdse = dn.getWriteableDataset();
			s = new SliceND(e.getShape(),determineMaxShape(e),sliceOut);
			wdse.setSlice(null, e, s);
		}

		return;
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
		SliceND nslice = new SliceND(dataset.getShapeRef());
		wds.setSlice(null, dataset, nslice);

		ILazyDataset error = dataset.getErrors();

		if (error != null) {
			Dataset e = DatasetUtils.sliceAndConvertLazyDataset(error);
			e.setName("errors");
			dn = null;
			if (group.containsDataNode(e.getName())){
				dn = file.getData(group,e.getName());
			} else {
				createWriteableLazy(e, group);
				nexusFile.addAttribute(group, new AttributeImpl(dataset.getName() + NexusTreeUtils.NX_UNCERTAINTY_SUFFIX, e.getName()));
				dn = file.getData(group,e.getName());
			}

			ILazyWriteableDataset wdse = dn.getWriteableDataset();
			wdse.setSlice(null, e, new SliceND(e.getShapeRef()));
		}

		return;
	}

	@Override
	public void close() throws Exception {
		
		if (nexusFile != null) {
			
			if (swmring) {
				DataNode dn = nexusFile.getData(Node.SEPARATOR + ENTRY + Node.SEPARATOR + LIVE + Node.SEPARATOR + FINISHED);
				dn.getWriteableDataset().setSlice(null, DatasetFactory.ones(IntegerDataset.class, 1), new SliceND(dn.getWriteableDataset().getShape()));
			}
			
			nexusFile.flush();
			nexusFile.close();
		}

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
	 * Get a new slice array which reflects the position of the processed data in the full output dataset
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
		int[] mx = determineMaxShape(d);

		ILazyWriteableDataset lwds = new LazyWriteableDataset(d.getName(), d.getDType(), d.getShape(), mx, d.getShape(), null);
		
		DataNode dn = nexusFile.createData(group, lwds, NexusFile.COMPRESSION_NONE);
		
		UnitMetadata unit = dataset.getFirstMetadata(UnitMetadata.class);
		if (unit != null) {
			nexusFile.addAttribute(dn,new AttributeImpl(NexusTreeUtils.NX_UNITS,unit.toString()));
		}
	}
	
	private int[] determineMaxShape(Dataset d) {
		int[] maxShape = d.getShape().clone();
		for (int i = 0; i < maxShape.length; i++) if (maxShape[i] == 1) maxShape[i] = -1;
		return maxShape;
	}
	@Override
	public void includeLinkTo(String fileName) {
		originalFilePath = fileName;
	}

}

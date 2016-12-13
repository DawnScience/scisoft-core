/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.visitor;

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
import java.util.concurrent.atomic.AtomicInteger;

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
import org.eclipse.dawnsci.nexus.NexusFile;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.ILazyWriteableDataset;
import org.eclipse.january.dataset.LazyWriteableDataset;
import org.eclipse.january.dataset.ShapeUtils;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.dataset.SliceND;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.OriginMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.io.NexusTreeUtils;
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
	private final String ENTRY = "entry";
	private final String LIVE = "live";
	private final String FINISHED = "finished";

	private Map<IOperation, AtomicBoolean> firstNotifyMap;
	private Map<IOperation, Integer> positionMap;
	private AtomicBoolean firstNonNullExecution = new AtomicBoolean(true);

	private String results;
	private String intermediate;
	private String auxiliary;

	private ConcurrentHashMap<String,ConcurrentHashMap<Integer, String[]>> groupAxesNames = new ConcurrentHashMap<String,ConcurrentHashMap<Integer,String[]>>();
	private String filePath;
	private NexusFile nexusFile;
	
	private String originalFilePath;
	
	private boolean swmring = false;
	
	private AtomicInteger count = new AtomicInteger(0);
	private int flushEvery = 50;

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
		nexusFile = NexusFileHDF5.createNexusFile(filePath, swmring);
		
		if (nexusFile instanceof NexusFileHDF5 && swmring) {
			logger.debug("CACHING DATASETS");
			((NexusFileHDF5)nexusFile).setCacheDataset(true);
		}
		
		initGroups();
		try {
			// don't fail process because of error persisting models
			IPersistentNodeFactory pf = service.getPersistentNodeFactory();
			GroupNode gn = pf.writeOperationsToGroup(series);
			nexusFile.addNode("/" + ENTRY+"/process", gn);
			GroupNode or = pf.writeOriginalDataInformation(origin);
			nexusFile.addNode("/" + ENTRY+"/process/origin", or);
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

				GroupNode group2 = nexusFile.getGroup(intermediate+ "/"+i + "-" + series[i].getName(), true);
				nexusFile.addAttribute(group2, new AttributeImpl("NX_class","NXdata"));

			}
		}
		

	}

	/**
	 * Makes entry and result NXdata
	 * @throws Exception
	 */
	private void initGroups() throws Exception {

		GroupNode group = nexusFile.getGroup("/" + ENTRY, true);
		nexusFile.addAttribute(group, new AttributeImpl("NX_class","NXentry"));
		group = nexusFile.getGroup("/" + ENTRY + "/" + RESULTS_GROUP, true);
		nexusFile.addAttribute(group, new AttributeImpl("NX_class","NXdata"));
		results = "/" + ENTRY + "/" + RESULTS_GROUP;
		if (swmring) {
			group = nexusFile.getGroup("/" + ENTRY + "/" + LIVE, true);
			nexusFile.addAttribute(group, new AttributeImpl("NX_class","NXcollection"));
			IDataset dataset = DatasetFactory.zeros(new int[]{1}, Dataset.INT32);
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
						nexusFile.linkExternal(new URI("nxfile://"+originalFilePath+"#"+key),"/"+updatedName , true);
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
			GroupNode group = nexusFile.getGroup("/" +ENTRY + "/" + INTER_GROUP, true);
			intermediate = "/" +ENTRY + "/" + INTER_GROUP;
			nexusFile.addAttribute(group,new AttributeImpl("NX_class","NXsubentry"));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	public void executed(OperationData result, IMonitor monitor) throws Exception {
		
		if (result == null) return;
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
			appendData(integrated,nexusFile.getGroup(results,false), slices,shape, nexusFile);
		}
		if (fNNE){
			synchronized (nexusFile) {
				GroupNode group = nexusFile.getGroup(results,false);
				nexusFile.addAttribute(group,new AttributeImpl(NexusTreeUtils.NX_SIGNAL,integrated.getName()));
				if (nexusFile instanceof NexusFileHDF5 && swmring) {
					((NexusFileHDF5)nexusFile).activateSwmrMode();
					logger.debug("SWMR-ING");
				}
			}
		}
		
		int i = count.getAndIncrement();
		
		if (nexusFile instanceof NexusFileHDF5 && swmring && i%flushEvery == 0) {
			synchronized (nexusFile) {
				((NexusFileHDF5)nexusFile).flushAllCachedDatasets();
			}
		}
		
		
		
		
	}

	@Override
	public void notify(IOperation<? extends IOperationModel, ? extends OperationData> intermeadiateData, OperationData data) {
		//make groups on first pass

		if (!intermeadiateData.isStoreOutput() && (data.getAuxData() == null || data.getAuxData()[0] == null)) return;

		boolean first = firstNotifyMap.get(intermeadiateData).getAndSet(false);

		String position = String.valueOf(positionMap.get(intermeadiateData));

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
		if (intermeadiateData.isStoreOutput()) {
			try {
				GroupNode group;
				synchronized (nexusFile) {
					group = nexusFile.getGroup(intermediate + "/" + position + "-" + intermeadiateData.getName(), true);	
				}
				Dataset d = DatasetUtils.convertToDataset(data.getData());
				
				synchronized (nexusFile) {d.setName("data");
					appendData(d,group, slices,shape, nexusFile);
				}
				if (first){
					synchronized (nexusFile) {
						nexusFile.addAttribute(group,new AttributeImpl("signal",d.getName()));
					}
				}
				updateAxes(d, slices, shape, dataDims, intermediate + "/" + position + "-" + intermeadiateData.getName(),first);

			} catch (Exception e) {
				logger.error(e.getMessage());
			}

		}

		Serializable[] auxData = data.getAuxData();

		//save aux data (should be IDataset, with unit dimensions)
		if (auxData != null && auxData[0] != null) {
			for (int i = 0; i < auxData.length; i++) {
				if (auxData[i] instanceof IDataset) {
					
					try {
						Dataset ds = DatasetUtils.convertToDataset((IDataset) auxData[i]);
						String dsName = ds.getName();
						GroupNode group;
						synchronized (nexusFile) {
							GroupNode auxG = nexusFile.getGroup("/"+ENTRY + "/" + AUX_GROUP, true);
							nexusFile.addAttribute(auxG,new AttributeImpl("NX_class","NXsubentry"));
							group = nexusFile.getGroup(auxG,position + "-" + intermeadiateData.getName() + "/" +dsName ,"NXdata",true);
							GroupNode rg = nexusFile.getGroup("/"+ENTRY + "/" + AUX_GROUP + "/" +position + "-" + intermeadiateData.getName(), false);
							if (first) nexusFile.addAttribute(rg,new AttributeImpl("NX_class","NXsubentry"));
						}
						
						ds.setName("data");
						synchronized (nexusFile) {
							appendData(ds,group, slices,shape, nexusFile);
							if (first){
								nexusFile.addAttribute(group,new AttributeImpl("signal",ds.getName()));
							}
						}
						
						updateAxes(ds, slices, shape, dataDims, "/"+ENTRY + "/" + AUX_GROUP +  "/" + position + "-" + intermeadiateData.getName() +"/"+  dsName, first);
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
		if (first) axNames = new String[data.getRank()];
		
		List<AxesMetadata> mList = data.getMetadata(AxesMetadata.class);
		if (mList!= null && !mList.isEmpty()) {
			int count = 0;
			for (AxesMetadata am : mList) {
				ILazyDataset[] axes = am.getAxes();
				if (axes == null && axesNames != null)  {
					Arrays.fill(axNames,NexusTreeUtils.NX_AXES_EMPTY);
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
						names[j] = santiziseName(name, axesNames);
						axesNames.putIfAbsent(i, names);
						name = axesNames.get(i)[j];
						Dataset axDataset = DatasetUtils.sliceAndConvertLazyDataset(ax);
						axDataset.setName(name);
						if (axNames != null && j == 0) axNames[count++] = name;
						if (setDims.contains(i)) {
							if(first) {
								ILazyDataset error = axDataset.getError();
								IDataset e = null;
								if (error != null) {
									e = error.getSlice().squeeze();
									e.setName(axDataset.getName() + "_errors");
								}
								
								synchronized (nexusFile) {
									DataNode dn = nexusFile.createData(nexusFile.getGroup(groupName, true), axDataset.squeeze());
									dn.addAttribute(new AttributeImpl("axis", String.valueOf(i+1)));
									nexusFile.addAttribute(nexusFile.getGroup(groupName, true), new AttributeImpl(axNames[i]+NexusTreeUtils.NX_INDICES_SUFFIX, DatasetFactory.createFromObject(i)));
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

									nexusFile.getData(groupName +"/" +axDataset.getName()).addAttribute(new AttributeImpl("axis", String.valueOf(i+1)));

								}

								ILazyDataset error = axDataset.getError();

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
	
	private String santiziseName(String name, ConcurrentHashMap<Integer, String[]> names) {
		//assume only our slicing puts [ in a axis name!
		if (name.contains("[")) {
			name = name.split("\\[")[0];
		}

		if (name.contains("/")) {
			String[] split = name.split("/");
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
					if (name.equals(s)) duplicateName = true;
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
			dn = file.getData(group+"/"+dataset.getName());
		} catch (Exception e) {
			createWriteableLazy(dataset, file.getGroup(group, true));
			dn = file.getData(group+"/"+dataset.getName());
			nexusFile.addAttribute(group, new AttributeImpl(dataset.getName()+NexusTreeUtils.NX_INDICES_SUFFIX, DatasetFactory.createFromObject(axisDim)));
		}

		ILazyWriteableDataset wds = dn.getWriteableDataset();
		SliceND s = new SliceND(dataset.getShape(),determineMaxShape(dataset),new Slice[]{oSlice[axisDim]});
		wds.setSlice(null, dataset, s);
//		if (nexusFile instanceof NexusFileHDF5 && swmring) {
//			((NexusFileHDF5)nexusFile).flushCachedDataset(group+"/"+dataset.getName());
//		}
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
	private void appendData(Dataset dataset, GroupNode group, Slice[] oSlice, int[] oShape, NexusFile file) throws Exception {
		
		if (ShapeUtils.squeezeShape(dataset.getShape(), false).length == 0) {
			//padding slice and shape does not play nice with single values of rank != 0
			dataset = dataset.getSliceView().squeeze();
//			dataset.setShape(new int[]{1});
		}
		
		//determine the dimensions of the original data
		int[] dd = getNonSingularDimensions(oSlice, oShape);
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


		ILazyDataset error = dataset.getError();

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

	@Override
	public void close() throws Exception {
		
		if (nexusFile != null) {
			
			if (swmring) {
				DataNode dn = nexusFile.getData(Node.SEPARATOR + ENTRY + Node.SEPARATOR + LIVE + Node.SEPARATOR + FINISHED);
				dn.getWriteableDataset().setSlice(null, DatasetFactory.ones(new int[]{1}, Dataset.INT32), new SliceND(dn.getWriteableDataset().getShape()));
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

}

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyWriteableDataset;
import org.eclipse.dawnsci.analysis.api.dataset.Slice;
import org.eclipse.dawnsci.analysis.api.dataset.SliceND;
import org.eclipse.dawnsci.analysis.api.metadata.AxesMetadata;
import org.eclipse.dawnsci.analysis.api.metadata.OriginMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.persistence.IPersistenceService;
import org.eclipse.dawnsci.analysis.api.processing.IExecutionVisitor;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.model.IOperationModel;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.dataset.impl.AbstractDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.LazyWriteableDataset;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.tree.impl.AttributeImpl;
import org.eclipse.dawnsci.hdf5.nexus.NexusFileHDF5;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NexusFileExecutionVisitor implements IExecutionVisitor {
	
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

	private Map<IOperation, AtomicBoolean> firstNotifyMap;
	private Map<IOperation, Integer> positionMap;
	private AtomicBoolean firstNonNullExecution = new AtomicBoolean(true);

	private String results;
	private String intermediate;
	private String auxiliary;

	private ConcurrentHashMap<String,ConcurrentHashMap<Integer, String[]>> groupAxesNames = new ConcurrentHashMap<String,ConcurrentHashMap<Integer,String[]>>();
	private String filePath;
	private NexusFile nexusFile;
	
	private boolean swmring = false;

	private final static Logger logger = LoggerFactory.getLogger(NexusFileExecutionVisitor.class);
	
	public NexusFileExecutionVisitor(String filePath, boolean swmr) {
		this.filePath = filePath;
		firstNotifyMap = new ConcurrentHashMap<IOperation, AtomicBoolean>();
		positionMap = new ConcurrentHashMap<IOperation, Integer>();
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
		nexusFile = NexusFileHDF5.createNexusFile(filePath, true);
		try {
			// don't fail process because of error persisting models
//			IPersistentFile pf = service.createPersistentFile(file);
//			pf.setOperations(series);
//			pf.setOperationDataOrigin(origin);
		} catch (Exception e){
			logger.error("Cant persist operations!", e);
		}

		initGroups();
		boolean groupCreated = false;
		for (int i = 0; i < series.length; i++) {
			if (series[i].isStoreOutput()) {
				if (!groupCreated){
					createInterGroup();
					groupCreated = true;
				}
//				String group = file.group(i + "-" + series[i].getName(), intermediate);
				GroupNode group2 = nexusFile.getGroup(intermediate+ "/"+i + "-" + series[i].getName(), true);
				group2.addAttribute(new AttributeImpl("NX_class","NXdata"));
//				file.setNexusAttribute(group, "NXdata");
			}
		}
		

	}

	/**
	 * Makes entry and result NXdata
	 * @throws Exception
	 */
	private void initGroups() throws Exception {
//		file.group(ENTRY);
		GroupNode group = nexusFile.getGroup("/" + ENTRY, true);
		group.addAttribute(new AttributeImpl("NX_class","NXentry"));
		
//		results = file.group(RESULTS_GROUP, ENTRY);
//		file.setNexusAttribute(results, "NXdata");
		group = nexusFile.getGroup("/" + ENTRY + "/" + RESULTS_GROUP, true);
		group.addAttribute(new AttributeImpl("NX_class","NXdata"));
		results = "/" + ENTRY + "/" + RESULTS_GROUP;
//		results = group.get
	}

	/**
	 * Make the intermediate data NXcollection to store data from the middle of the pipeline
	 */
	private void createInterGroup() {
		try {
//			intermediate = file.group(INTER_GROUP,ENTRY);
//			file.setNexusAttribute(intermediate, "NXcollection");
			GroupNode group = nexusFile.getGroup("/" +ENTRY + "/" + INTER_GROUP, true);
			intermediate = "/" +ENTRY + "/" + INTER_GROUP;
			group.addAttribute(new AttributeImpl("NX_class","NXsubentry"));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

//	/**
//	 * Makes NXCollection to store the Auxiliary data from each operation (if supplied)
//	 */
//	private void createAuxGroup() {
//		try {
//			GroupNode group = nexusFile.getGroup(ENTRY + "/" + AUX_GROUP, true);
//			group.addAttribute(new AttributeImpl("NX_class","NXsubentry"));
//		} catch (Exception e) {
//			logger.error(e.getMessage());
//		}
//	}

	@Override
	public void executed(OperationData result, IMonitor monitor) throws Exception {
		
		if (result == null) return;
		//not threadsafe but closer
		boolean fNNE = firstNonNullExecution.getAndSet(false);
		
		//Write data to file
		final IDataset integrated = result.getData();
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
				nexusFile.getData(results +"/" +integrated.getName()).addAttribute(new AttributeImpl("signal", String.valueOf(1)));
				if (nexusFile instanceof NexusFileHDF5 && swmring) {
					((NexusFileHDF5)nexusFile).activateSwmrMode();
					logger.debug("SWMR-ING");
				}
			}
		}
		synchronized (nexusFile) {
			nexusFile.flush();
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
				IDataset d = data.getData();
				
				synchronized (nexusFile) {d.setName("data");
					appendData(d,group, slices,shape, nexusFile);
				}
				if (first){
					synchronized (nexusFile) {
						nexusFile.getData(group,d.getName()).addAttribute(new AttributeImpl("signal", String.valueOf(1)));
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
						IDataset ds = (IDataset)auxData[i];
						String dsName = ds.getName();
						GroupNode group;
						synchronized (nexusFile) {
							GroupNode auxG = nexusFile.getGroup("/"+ENTRY + "/" + AUX_GROUP, true);
							auxG.addAttribute(new AttributeImpl("NX_class","NXsubentry"));
							group = nexusFile.getGroup(auxG,position + "-" + intermeadiateData.getName() + "/" +dsName ,"NXdata",true);
//							group = file.group(position + "-" + intermeadiateData.getName(), auxiliary);
//							file.setNexusAttribute(group, "NXCollection");
//							group = file.group(ds.getName(), group);
//							file.setNexusAttribute(group, "NXdata");
						}
						
						ds.setName("data");
						synchronized (nexusFile) {
							appendData(ds,group, slices,shape, nexusFile);
						}
//						if (first){
//							synchronized (nexusFile) {
//								file.setAttribute(group +"/" +ds.getName(), "signal", String.valueOf(1));
//							}
//						}
						
						updateAxes(ds, slices, shape, dataDims, "/"+ENTRY + "/" + AUX_GROUP +  "/" + position + "-" + intermeadiateData.getName() +"/"+  dsName, first);
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
				}
			}
		}
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

		List<AxesMetadata> mList = data.getMetadata(AxesMetadata.class);
		if (mList!= null && !mList.isEmpty()) {

			for (AxesMetadata am : mList) {
				ILazyDataset[] axes = am.getAxes();
				if (axes == null) return;

				int rank = axes.length;

				for (int i = 0; i< rank; i++) {
					ILazyDataset[] axis = am.getAxis(i);
					if (axis == null) continue;
					String[] names = new String[axis.length];
					for (int j = 0; j < axis.length; j++) {
						ILazyDataset ax = axis[j];
						if (ax == null) continue;
						String name = ax.getName();
						names[j] = santiziseName(name);
						axesNames.putIfAbsent(i, names);
						name = axesNames.get(i)[j];
						IDataset axDataset = ax.getSlice();
						axDataset.setName(name);

						if (setDims.contains(i)) {
							if(first) {
								ILazyDataset error = axDataset.getError();
								IDataset e = null;
								if (error != null) {
									e = error.getSlice().squeeze();
									e.setName(axDataset.getName() + "_errors");
								}
								
								synchronized (nexusFile) {
									nexusFile.toString();
									nexusFile.createData(nexusFile.getGroup(groupName, true), axDataset.squeeze()).addAttribute(new AttributeImpl("axis", String.valueOf(i+1)));;
//									nexusFile.createDat
//									String ds = file.createDataset(axDataset.getName(), axDataset.squeeze(), groupName);
//									file.setAttribute(ds, "axis", String.valueOf(i+1));
									if (e != null) nexusFile.createData(nexusFile.getGroup(groupName, true), e.squeeze());
								}
							}
						} else {
							synchronized (nexusFile) {
								appendSingleValueAxis(axDataset,groupName, oSlice,oShape, nexusFile,i);
							}
							if (first) {
								synchronized (nexusFile) {
									nexusFile.getData(groupName +"/" +axDataset.getName()).addAttribute(new AttributeImpl("axis", String.valueOf(i+1)));
								}
							}

							ILazyDataset error = axDataset.getError();

							if (error != null) {
								IDataset e = error.getSlice();
								e.setName(axDataset.getName() + "_errors");
								synchronized (nexusFile) {
									appendSingleValueAxis(e,groupName, oSlice,oShape, nexusFile,i);
								}
							}
						}

					}
				}

			}
		}
	}
	
	private String santiziseName(String name) {
		//assume only our slicing puts [ in a axis name!
		if (name.contains("[")) {
			name = name.split("\\[")[0];
		}

		if (name.contains("/")) {
			name = name.replace("/", "_");
		}

		//sanitize - can't have an axis called data
		if (name.isEmpty() || name.equals("data")) {
			int n = 0;
			while(groupAxesNames.containsKey("axis" + n)) n++;

			name = "axis" +n;
		}
		
		return name;
	}

	private void appendSingleValueAxis(IDataset dataset, String group, Slice[] oSlice, int[] oShape, NexusFile file, int axisDim) throws Exception{
		dataset = dataset.getSliceView();
		dataset.setShape(1);
//		H5Utils.insertDataset(file, group, dataset, new Slice[]{oSlice[axisDim]}, new long[]{oShape[axisDim]});
		DataNode dn = null;
		try {
			dn = file.getData(group+"/"+dataset.getName());
		} catch (Exception e) {
			createWriteableLazy(dataset, file.getGroup(group, true));
			dn = file.getData(group+"/"+dataset.getName());
		}

		ILazyWriteableDataset wds = dn.getWriteableDataset();
		SliceND s = new SliceND(dataset.getShape(),determineMaxShape((Dataset)dataset),new Slice[]{oSlice[axisDim]});
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
	private void appendData(IDataset dataset, GroupNode group, Slice[] oSlice, int[] oShape, NexusFile file) throws Exception {
		
		if (AbstractDataset.squeezeShape(dataset.getShape(), false).length == 0) {
			//padding slice and shape does not play nice with single values of rank != 0
			dataset = dataset.getSliceView().squeeze();
		}
		
		//determine the dimensions of the original data
		int[] dd = getNonSingularDimensions(oSlice, oShape);
		//update the slice to reflect the new data shape/rank
		Slice[] sliceOut = getUpdatedSliceArray( oShape, dataset.getShape(), oSlice, dd);
		//determine shape of full output dataset
		long[] newShape = getNewShape(oShape, dataset.getShape(), dd);
		
		if (dataset.getRank() == 0) {
			int[] shape = new int[newShape.length];
			Arrays.fill(shape, 1);
			dataset.setShape(shape);
		}
		
		//write
//		H5Utils.insertDataset(file, group, dataset, sliceOut, newShape);
		DataNode dn = null;
		try {
			dn = file.getData(group,dataset.getName());
		} catch (Exception e) {
			createWriteableLazy(dataset, group);
			dn = file.getData(group,dataset.getName());
		}

		ILazyWriteableDataset wds = dn.getWriteableDataset();
		SliceND s = new SliceND(dataset.getShape(),determineMaxShape((Dataset)dataset),sliceOut);
		wds.setSlice(null, dataset, s);


		ILazyDataset error = dataset.getError();

		if (error != null) {
			IDataset e = error.getSlice();
			e.setName("errors");
//			H5Utils.insertDataset(file, group, e, sliceOut, newShape);
			dn = null;
			try {
				dn = file.getData(group,e.getName());
			} catch (Exception ex) {
				createWriteableLazy(e, group);
				dn = file.getData(group,e.getName());
			}

			ILazyWriteableDataset wdse = dn.getWriteableDataset();
			s = new SliceND(e.getShape(),determineMaxShape((Dataset)e),sliceOut);
			wdse.setSlice(null, dataset, s);
		}

		return;
	}

	@Override
	public void close() throws Exception {
//		if (file != null) file.close();
		if (nexusFile != null) {
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

		if (AbstractDataset.squeezeShape(dsShape, false).length == 0) {
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

		if (AbstractDataset.squeezeShape(dsShape, false).length == 0) {
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
	
	private void createWriteableLazy(IDataset dataset, GroupNode group) {
//		nexusFile.getData
		Dataset d = DatasetUtils.convertToDataset(dataset);
		int[] mx = determineMaxShape(d);
//		ILazySaver ls = new HDF5LazySaver(null, filePath, group, d.getName(), d.getShape(), d.getSize(),
//				d.getDtype(), true, mx, d.getShape(), null);
		ILazyWriteableDataset lwds = new LazyWriteableDataset(d.getName(), d.getDtype(), d.getShape(), mx, d.getShape(), null);
		try {
			DataNode data = nexusFile.createData(group, lwds);
			data.toString();
		} catch (NexusException e) {
			// TODO Auto-generated catch block
			logger.error("TODO put description of error here", e);
		}
	}
	
	private int[] determineMaxShape(Dataset d) {
		int[] maxShape = d.getShape().clone();
		for (int i = 0; i < maxShape.length; i++) if (maxShape[i] == 1) maxShape[i] = -1;
		return maxShape;
	}

}

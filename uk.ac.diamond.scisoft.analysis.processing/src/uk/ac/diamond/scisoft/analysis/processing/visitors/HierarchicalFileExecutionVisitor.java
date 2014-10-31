/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.visitors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dawb.common.services.IPersistenceService;
import org.dawb.common.services.IPersistentFile;
import org.dawb.common.services.ServiceManager;
import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.dataset.Slice;
import org.eclipse.dawnsci.analysis.api.metadata.AxesMetadata;
import org.eclipse.dawnsci.analysis.api.metadata.OriginMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.IExecutionVisitor;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.model.IOperationModel;
import org.eclipse.dawnsci.analysis.dataset.impl.AbstractDataset;
import org.eclipse.dawnsci.hdf5.H5Utils;
import org.eclipse.dawnsci.hdf5.HierarchicalDataFactory;
import org.eclipse.dawnsci.hdf5.IHierarchicalDataFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Nexus writing implementation of IExecutionVisitor.
 * 
 * Handles writing 2D or 1D (and 0D auxiliary data) including axes to file. The name of the dataset in the file is
 * based on the dataset name.
 * 
 * Also writes all the operation model values to NXProcess
 * 
 * The resulting tree structure of the Nexus file saved will be the following:<br>
 * <pre>
 *     Entry name              |   Class        |    Description          
 * -----------------------------------------------------------------
 * entry                       |   NXentry      |                         
 * entry/result                |   NXdata       | contains data + axes
 * entry/process               |   NXprocess    | contains NXnotes of models
 * entry/intermediate          |   NXcollection | contains NXdatas of intermediate data
 * entry/auxiliary             |   NXcollection | contains NXdatas of auxiliary data
 * 
 * </pre>
 * 
 * @author vdp96513
 *
 */
public class HierarchicalFileExecutionVisitor implements IExecutionVisitor {

	private boolean firstPassDone = false;
	private final String RESULTS_GROUP = "result";
	private final String INTER_GROUP = "intermediate";
	private final String AUX_GROUP = "auxiliary";
	private final String ENTRY = "entry";
	
	private String results;
	private String intermediate;
	private String auxiliary;
	private Map<String,Map<Integer, String>> groupAxesNames = new HashMap<String,Map<Integer,String>>();
	private IOperation<? extends IOperationModel, ? extends OperationData>[] series;
	private String filePath;
	IHierarchicalDataFile file;
	
	private final static Logger logger = LoggerFactory.getLogger(HierarchicalFileExecutionVisitor.class);
	
	public HierarchicalFileExecutionVisitor(String filePath) {
		this.filePath = filePath;
	}
	
	@Override
	public void init(IOperation<? extends IOperationModel, ? extends OperationData>[] series, OriginMetadata origin) throws Exception {
		file = HierarchicalDataFactory.getWriter(filePath);
		IPersistenceService service = (IPersistenceService)ServiceManager.getService(IPersistenceService.class);
		IPersistentFile pf = service.createPersistentFile(file);
		pf.setOperations(series);
		pf.setOperationDataOrigin(origin);
		this.series = series;
		
	}
	
	/**
	 * Makes entry and result NXdata
	 * @throws Exception
	 */
	private void initGroups() throws Exception {
		file.group(ENTRY);
		results = file.group(RESULTS_GROUP, ENTRY);
		file.setNexusAttribute(results, "NXdata");
	}
	
	/**
	 * Make the intermediate data NXcollection to store data from the middle of the pipeline
	 */
	private void createInterGroup() {
		try {
			intermediate = file.group(INTER_GROUP,ENTRY);
			file.setNexusAttribute(intermediate, "NXcollection");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * Makes NXCollection to store the Auxiliary data from each operation (if supplied)
	 */
	private void createAuxGroup() {
		try {
			auxiliary = file.group(AUX_GROUP,ENTRY);
			file.setNexusAttribute(auxiliary, "NXcollection");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	@Override
	public void executed(OperationData result, IMonitor monitor, Slice[] slices, int[] shape, int[] dataDims) throws Exception {
		//Write data to file
		final IDataset integrated = result.getData();
		updateAxes(integrated, slices, shape, dataDims, results);
		integrated.setName("data");
		appendData(integrated,results, slices,shape, file);
		if (!firstPassDone)file.setAttribute(results +"/" +integrated.getName(), "signal", String.valueOf(1));
		firstPassDone = true;
		
	}

	@Override
	public void notify(IOperation<? extends IOperationModel, ? extends OperationData> intermeadiateData, OperationData data, Slice[] slices, int[] shape, int[] dataDims) {
		//make groups on first pass
		if (!firstPassDone) {
			try {
				initGroups();
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		
		//if specified to save data, do it
		if (intermeadiateData.isStoreOutput()) {
			if (intermediate == null) createInterGroup();
			
			try {
				
				int i = 0;
				while (i < series.length && series[i] != intermeadiateData) i++;
				
				String group = file.group(String.valueOf(i) + "-" + intermeadiateData.getName(), intermediate);
				file.setNexusAttribute(group, "NXdata");
				IDataset d = data.getData();
				d.setName("data");
				appendData(d,group, slices,shape, file);
				updateAxes(d, slices, shape, dataDims, group);
				
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			
		}
	
		Serializable[] auxData = data.getAuxData();
		
		int pos = 0;
		while (pos < series.length && series[pos] != intermeadiateData) pos++;
		

		//save aux data (should be IDataset, with unit dimensions)
		if (auxData != null && auxData[0] != null) {
			for (int i = 0; i < auxData.length; i++) {
				if (auxData[i] instanceof IDataset) {
					if (auxiliary == null) createAuxGroup();
					try {
						
						IDataset ds = (IDataset)auxData[i];
						String group = file.group(String.valueOf(pos) + "-" + intermeadiateData.getName(), auxiliary);
						file.setNexusAttribute(group, "NXCollection");
						
						group = file.group(ds.getName(), group);
						file.setNexusAttribute(group, "NXdata");
						
						ds.setName("data");

						appendData(ds,group, slices,shape, file);
						updateAxes(ds, slices, shape, dataDims, group);
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
				}
			}
		}
	}
	
	private void updateAxes(IDataset data, Slice[] oSlice, int[] oShape, int[] dataDims, String groupName) throws Exception {
		
		Map<Integer, String> axesNames = null;
		
		if (groupAxesNames.containsKey(groupName)) {
			axesNames = groupAxesNames.get(groupName);
		} else {
			axesNames = new HashMap<Integer,String>();
			groupAxesNames.put(groupName, axesNames);
		}
		
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
				if (axes != null) {
					for (int i = 0; i < axes.length; i++) {
						ILazyDataset ax = axes[i];
						if (ax != null) {
							if (!firstPassDone && ax != null) {
								String name = ax.getName();
								//assume only our slicing puts [ in a axis name!
								if (name.contains("[")) {
									name = name.split("\\[")[0];
								}
								axesNames.put(i, name);
							}
							IDataset axDataset = ax.getSlice();
							axDataset.setName(axesNames.get(i));
							
							if (setDims.contains(i)) {
								if(!firstPassDone) {
								String ds = file.createDataset(axDataset.getName(), axDataset.squeeze(), groupName);
								file.setAttribute(ds, "axis", String.valueOf(i+1));
								}
							} else {
								appendSingleValueAxis(axDataset,groupName, oSlice,oShape, file,i);
								file.setAttribute(groupName +"/" +axDataset.getName(), "axis", String.valueOf(i+1));
							}
							
						}
					}
				}
			}
		}
	}
	
	private void appendSingleValueAxis(IDataset dataset, String group, Slice[] oSlice, int[] oShape, IHierarchicalDataFile file, int axisDim) throws Exception{
		
		dataset = dataset.getSliceView().squeeze();
		
		H5Utils.insertDataset(file, group, dataset, new Slice[]{oSlice[axisDim]}, new long[]{oShape[axisDim]});
		
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
	private void appendData(IDataset dataset, String group, Slice[] oSlice, int[] oShape, IHierarchicalDataFile file) throws Exception {
		//determine the dimensions of the original data
		int[] dd = getNonSingularDimensions(oSlice, oShape);
		//update the slice to reflect the new data shape/rank
		Slice[] sliceOut = getUpdatedSliceArray( oShape, dataset.getShape(), oSlice, dd);
		//determine shape of full output dataset
		long[] newShape = getNewShape(oShape, dataset.getShape(), dd);
		//write
		H5Utils.insertDataset(file, group, dataset, sliceOut, newShape);
		
		ILazyDataset error = dataset.getError();
		
		if (error != null) {
			IDataset e = error.getSlice();
			e.setName("errors");
			H5Utils.insertDataset(file, group, e, sliceOut, newShape);
		}
		
		return;
	}

	@Override
	public void close() throws Exception {
		if (file != null) file.close();
		
	}

	/**
	 * Parse slice array to determine which dimensions are not equal to 1 and assume these are the data dimensions
	 * @param slices
	 * @param shape
	 * @return datadims
	 */
	private int[] getNonSingularDimensions(Slice[] slices, int[] shape) {
		
		int[] start = new int[slices.length];
		int[] stop = new int[slices.length];
		int[] step = new int[slices.length];
		
		Slice.convertFromSlice(slices, shape, start, stop, step);
		int[] newShape = AbstractDataset.checkSlice(shape,start,stop,start,stop,step);
		
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
	
}

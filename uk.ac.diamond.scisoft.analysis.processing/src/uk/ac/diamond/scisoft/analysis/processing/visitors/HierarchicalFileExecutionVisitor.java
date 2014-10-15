/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.visitors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.IExecutionVisitor;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.model.IOperationModel;
import org.eclipse.dawnsci.hdf5.H5Utils;
import org.eclipse.dawnsci.hdf5.HierarchicalDataFactory;
import org.eclipse.dawnsci.hdf5.IHierarchicalDataFile;

public class HierarchicalFileExecutionVisitor implements IExecutionVisitor {

	private boolean firstPassDone = false;
	private final String RESULTS_GROUP = "result";
	private final String INTER_GROUP = "intermediate";
	private final String ENTRY = "entry";
	
	private String results;
	private String intermediate;
	private Map<String,Map<Integer, String>> groupAxesNames = new HashMap<String,Map<Integer,String>>();
	private IOperation<? extends IOperationModel, ? extends OperationData>[] series;
	private String filePath;
	IHierarchicalDataFile file;
	
	public HierarchicalFileExecutionVisitor(String filePath) {
		this.filePath = filePath;
	}
	
	@Override
	public void init(IOperation<? extends IOperationModel, ? extends OperationData>[] series) throws Exception {
		file = HierarchicalDataFactory.getWriter(filePath);
		IPersistenceService service = (IPersistenceService)ServiceManager.getService(IPersistenceService.class);
		IPersistentFile pf = service.createPersistentFile(file);
		pf.setOperations(series);
		this.series = series;
		
	}
	
	private void initGroups() throws Exception {
		file.group(ENTRY);
		results = file.group(RESULTS_GROUP, ENTRY);
		file.setNexusAttribute(results, "NXdata");
	}
	
	private void createInterGroup() {
		try {
			intermediate = file.group(INTER_GROUP,ENTRY);
			file.setNexusAttribute(intermediate, "NXcollection");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void executed(OperationData result, IMonitor monitor, Slice[] slices, int[] shape, int[] dataDims) throws Exception {
		
		final IDataset integrated = result.getData();
		updateAxes(integrated, slices, shape, dataDims, results);
		integrated.setName("data");
		appendData(integrated,results, slices,shape, file);
		if (!firstPassDone)file.setAttribute(results +"/" +integrated.getName(), "signal", String.valueOf(1));
		firstPassDone = true;
		
	}

	@Override
	public void notify(IOperation<? extends IOperationModel, ? extends OperationData> intermeadiateData, OperationData data, Slice[] slices, int[] shape, int[] dataDims) {
		if (!firstPassDone) {
			try {
				initGroups();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
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
				// TODO Auto-generated catch block
				e.printStackTrace();
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
		for (int i = 0; i < dataDims.length; i++) setDims.add(dataDims[i]);
		
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
								appendData(axDataset,groupName, oSlice,oShape, file);
								file.setAttribute(groupName +"/" +axDataset.getName(), "axis", String.valueOf(i+1));
							}
							
						}
					}
				}
			}
		}
	}
	
	private void appendData(IDataset dataset, String group, Slice[] oSlice, int[] oShape, IHierarchicalDataFile file) throws Exception {
		
		dataset = dataset.getSliceView();
		
		//determine if dataset different rank to slice
		List<Integer> dimList = new ArrayList<Integer>();
		for (int i = 0; i < oSlice.length; i++) {
			if (oSlice[i].getStop() == null && oSlice[i].getLength() ==-1) {
				dimList.add(i);
			} else {
				int nSteps = oSlice[i].getNumSteps();
				if (nSteps > 1) dimList.add(i);
			}
			
		}
		int dataRank = dataset.squeeze().getRank();
		
		//Make new slice array to deal with new dimensions
		List<Slice> sliceList = new ArrayList<Slice>();
		List<Integer> totalDimList = new ArrayList<Integer>();
		int padCounter = 0;
		int counter = 0;
		for (Slice s: oSlice) {
			
			if (dimList.contains(counter)) {
				
				if (padCounter < dataRank) {
					sliceList.add(new Slice(0,dataset.getShape()[padCounter],1));
					totalDimList.add(dataset.getShape()[padCounter]);
					padCounter++;
				} else {
					counter++;
					continue;
				}
				
				
			}else {
				sliceList.add(s);
				totalDimList.add(oShape[counter]);
			}
			
			counter++;
		}
		
		Slice[] sliceOut = new Slice[sliceList.size()];
		sliceList.toArray(sliceOut);
		
		long[] newShape = new long[totalDimList.size()];
		for (int i = 0; i < newShape.length; i++) newShape[i] = totalDimList.get(i);
		
		H5Utils.insertDataset(file, group, dataset, sliceOut, newShape);
		
		return;
	}

	@Override
	public void close() throws Exception {
		if (file != null) file.close();
		
	}

}

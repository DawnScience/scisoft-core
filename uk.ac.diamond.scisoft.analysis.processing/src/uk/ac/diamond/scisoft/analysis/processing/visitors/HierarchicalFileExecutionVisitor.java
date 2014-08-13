package uk.ac.diamond.scisoft.analysis.processing.visitors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.dawnsci.hdf5.H5Utils;
import org.eclipse.dawnsci.hdf5.HierarchicalDataFactory;
import org.eclipse.dawnsci.hdf5.IHierarchicalDataFile;

import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.ILazyDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Slice;
import uk.ac.diamond.scisoft.analysis.metadata.AxesMetadata;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;
import uk.ac.diamond.scisoft.analysis.processing.IExecutionVisitor;
import uk.ac.diamond.scisoft.analysis.processing.IOperation;
import uk.ac.diamond.scisoft.analysis.processing.OperationData;

public class HierarchicalFileExecutionVisitor implements IExecutionVisitor {

	private boolean firstPassDone = false;
	private final String PROCESS_GROUP = "process";
	private final String RESULTS_GROUP = "result";
	private final String ENTRY = "entry";
	
	private String process;
	private String results;
	private Map<Integer, String> axesNames = new HashMap<Integer,String>();
	
	private int count = 0;
	private String filePath;
	IHierarchicalDataFile file;
	
	public HierarchicalFileExecutionVisitor(String filePath) {
		this.filePath = filePath;
	}
	
	@Override
	public void init() throws Exception {
		file = HierarchicalDataFactory.getWriter(filePath);
	}
	
	private void initGroups() throws Exception {
		file.group(ENTRY);
		process = file.group(PROCESS_GROUP, ENTRY);
		results = file.group(RESULTS_GROUP, ENTRY);
	}
	
	@Override
	public void executed(OperationData result, IMonitor monitor, Slice[] slices, int[] shape, int[] dataDims) throws Exception {
		
		final IDataset integrated = result.getData();
		updateAxes(integrated, slices, shape, dataDims);
		integrated.setName("integrated");
		appendData(integrated,results, slices,shape, file);
		firstPassDone = true;
		
	}

	@Override
	public void notify(IOperation intermeadiateData, OperationData data, Slice[] slices, int[] shape, int[] dataDims) {
		if (!firstPassDone)
			try {
				initGroups();
				file.createStringDataset(Integer.toString(count++), intermeadiateData.getClass().getName(), process);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	
	private void updateAxes(IDataset data, Slice[] oSlice, int[] oShape, int[] dataDims) throws Exception {
		
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
							if (!firstPassDone && ax != null) axesNames.put(i, ax.getName());
							IDataset axDataset = ax.getSlice();
							axDataset.setName(axesNames.get(i));
							
							if (setDims.contains(i) && !firstPassDone) {
								file.createDataset(axDataset.getName(), axDataset, results);
							} else {
								appendData(axDataset,results, oSlice,oShape, file);
							}
							
						}
					}
				}
			}
		}
	}
	
	private void appendData(IDataset dataset, String group, Slice[] oSlice, int[] oShape, IHierarchicalDataFile file) throws Exception {
		
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
	public void passDataThroughUnmodified(IOperation... operations) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isRequiredToModifyData(IOperation operation) {
		// TODO Auto-generated method stub
		return true;
	}

}

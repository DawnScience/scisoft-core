package uk.ac.diamond.scisoft.analysis.processing.operations;


import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.IExportOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.RunningAverage;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;

public class AveragingOperation<T extends EmptyModel> extends AbstractOperation<EmptyModel, OperationData> implements IExportOperation{

	private RunningAverage average;
	private ILazyDataset parent;
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.AveragingOperation";
	}
	
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		SliceFromSeriesMetadata ssm = getSliceSeriesMetadata(input);
		
		if (ssm == null) throw new OperationException(this, "Pipeline metadata not present!");
		
		if (parent != ssm.getSourceInfo().getParent()) {
			parent = ssm.getSourceInfo().getParent();
			average = null;
		}
		
		
		if (average == null) {
			average = new RunningAverage(input);
		} else {
			average.update(input);
		}
		
		if (average.getCount() == ssm.getTotalSlices()) {
			IDataset out = average.getCurrentAverage();
			copyMetadata(input, out);
			out.clearMetadata(SliceFromSeriesMetadata.class);
			average = null;
			SliceFromSeriesMetadata outsmm = ssm.clone();
			for (int i = 0; i < ssm.getParent().getRank(); i++) {
				
				if (!outsmm.isDataDimension(i)) outsmm.reducedDimensionToSingular(i);
				
			}
			out.setMetadata(outsmm);
			
			return new OperationData(out);
		}
		
		return null;
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.ANY;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.SAME;
	}

}

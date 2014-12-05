package uk.ac.diamond.scisoft.analysis.processing.operations;


import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.dataset.Slice;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.IExportOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;

public class AveragingOperation extends AbstractOperation<EmptyModel, OperationData> implements IExportOperation{

	private Dataset average;
	private ILazyDataset parent;
	private int counter;
	
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
			counter = 0;
		}
		
		
		Dataset d = DatasetUtils.cast(input,Dataset.FLOAT64);
		
		if (average == null) {
			average = d;
			counter++;
		} else {
			d.isubtract(average);
			d.idivide(counter++);
			average.iadd(d);
		}
		
		if (counter == ssm.getShapeInfo().getTotalSlices()) {
			IDataset out = average;
			average = null;
			counter = 0;
			SliceFromSeriesMetadata outsmm = ssm.clone();
			for (int i = 0; i < ssm.getParent().getRank(); i++) {
				
				if (!outsmm.getShapeInfo().isDataDimension(i)) outsmm.reducedDimensionToSingular(i);
				
			}
			out.setMetadata(outsmm);
			return new OperationData(out);
		}
		
		return null;
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}

}

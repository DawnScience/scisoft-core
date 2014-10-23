package uk.ac.diamond.scisoft.analysis.processing.operations.twod;

import java.util.List;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.AbstractOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.Comparisons;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;

import uk.ac.diamond.scisoft.analysis.dataset.function.Downsample;

public class DownsampleImageOperation extends AbstractOperation<DownsampleImageModel, OperationData> {

	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.DownsampleImageOperation";
	}

	@Override
	public OperationData execute(IDataset slice, IMonitor monitor)
			throws OperationException {
		
		ILazyDataset mask = getFirstMask(slice);
		
		if (mask != null) {
			Dataset m = (Dataset)mask.getSlice();
			((Dataset)slice).setByBoolean(Double.NaN, Comparisons.logicalNot(m));
		}
		
		Downsample downsample = new Downsample(((DownsampleImageModel)model).getDownsampleMode(), new int[] {((DownsampleImageModel)model).getDownsampleSize(),((DownsampleImageModel)model).getDownsampleSize()});
		
		List<Dataset> out = downsample.value(slice);
		
		return new OperationData(out.get(0));
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

package uk.ac.diamond.scisoft.analysis.processing.operations.oned;

import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.EmptyModel;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.Maths;

@Atomic
public class StandardNormalVariate extends AbstractOperation<EmptyModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.oned.StandardNormalVariate";
	}
	
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		Dataset values = DatasetUtils.convertToDataset(input);
		double mean = (Double) values.mean(true);
		double std = values.stdDeviation().doubleValue();
		Dataset output = Maths.subtract(input,mean);
		output.idivide(std);
		
		ILazyDataset el = input.getError();
		IDataset eb = null;
		if (el != null) {
			try {
				eb = el.getSlice();
			} catch (DatasetException e) {
				throw new OperationException(this, e);
			}
		}
		//transfer axes
		copyMetadata(input, output);
		
		if (eb != null) {
			output.setError(Maths.divide(eb, std));
		}
		
		return new OperationData(output);
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.ONE;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}

}

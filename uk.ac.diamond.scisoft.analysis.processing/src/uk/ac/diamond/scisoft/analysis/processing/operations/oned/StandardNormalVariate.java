package uk.ac.diamond.scisoft.analysis.processing.operations.oned;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.EmptyModel;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

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
		if (el != null) eb = el.getSlice();
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

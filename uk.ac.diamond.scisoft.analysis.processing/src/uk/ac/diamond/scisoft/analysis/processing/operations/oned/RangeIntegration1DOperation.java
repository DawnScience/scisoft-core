package uk.ac.diamond.scisoft.analysis.processing.operations.oned;

import java.io.Serializable;
import java.util.Arrays;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.AbstractOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;


public class RangeIntegration1DOperation extends AbstractOperation<RangeIntegration1DModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.RangeIntegration1DOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.ONE;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}
	
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		ILazyDataset[] firstAxes = getFirstAxes(input);
		IDataset axis = null;
		
		if (firstAxes == null || firstAxes[0] == null) {
			axis = DatasetFactory.createRange(input.getSize(), Dataset.INT32);
		} else {
			axis = firstAxes[0].getSlice();
		}
		
		double[] integrationRange = model.getIntegrationRange();
		int[] indexes = new int[2];
		if (integrationRange != null) {
			integrationRange = integrationRange.clone();
			Arrays.sort(integrationRange);
			indexes = new int[2];
			indexes[0]= DatasetUtils.findIndexGreaterThanOrEqualTo((Dataset)axis, integrationRange[0]);
			indexes[1]= DatasetUtils.findIndexGreaterThanOrEqualTo((Dataset)axis, integrationRange[1]);
			Arrays.sort(indexes);
		} else {
			indexes[0] = 0;
			indexes[1] = axis.getSize()-1;
		}
		
		
		double out = 0;
		
		for (int i = indexes[0]; i < indexes[1]; i++) {
			
			double val = (input.getDouble(i)+input.getDouble(i+1))/2;
			val = val * (axis.getDouble(i+1)+axis.getDouble(i));
			
			out += val;
			
		}
		
		if (model.isSubtractBaseline()) {
			
			double val = (input.getDouble(indexes[0]) + input.getDouble(indexes[1]))/2;
			val = val * (axis.getDouble(indexes[0])+axis.getDouble(indexes[1]));
			out -= val;
		}
		
		DoubleDataset aux = new DoubleDataset(new double[]{out}, new int[]{1});
		aux.setName("integrated");
		aux.squeeze();
		
		return new OperationData(input,new Serializable[]{aux});
	}

	
}

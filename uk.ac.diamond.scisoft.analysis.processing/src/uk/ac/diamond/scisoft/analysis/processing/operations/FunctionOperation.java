package uk.ac.diamond.scisoft.analysis.processing.operations;

import java.lang.reflect.InvocationTargetException;

import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.fitting.functions.IFunction;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;
import uk.ac.diamond.scisoft.analysis.processing.AbstractOperation;
import uk.ac.diamond.scisoft.analysis.processing.OperationData;
import uk.ac.diamond.scisoft.analysis.processing.OperationException;
import uk.ac.diamond.scisoft.analysis.processing.OperationRank;

public class FunctionOperation extends AbstractOperation<FunctionModel, OperationData> {

	private IFunction      function;

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.functionOperation";
	}
	
	@Override
    public String getName() {
		return "Function";
	}


	@Override
	public OperationData execute(IDataset slice, IMonitor monitor) throws OperationException {
		return new OperationData(function.calculateValues(slice));
	}

	@Override
	public void setModel(FunctionModel model) {
		super.setModel(model);
		try {
			this.function = (IFunction)model.get("function");
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			function = null;
		}
	}
	
	public OperationRank getInputRank() {
		return OperationRank.ANY; 
	}
	public OperationRank getOutputRank() {
		return OperationRank.SAME;
	}

}

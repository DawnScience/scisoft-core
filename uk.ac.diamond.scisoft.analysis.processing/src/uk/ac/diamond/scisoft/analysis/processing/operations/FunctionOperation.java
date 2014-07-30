package uk.ac.diamond.scisoft.analysis.processing.operations;

import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.fitting.functions.IFunction;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;
import uk.ac.diamond.scisoft.analysis.processing.AbstractOperation;
import uk.ac.diamond.scisoft.analysis.processing.OperationData;
import uk.ac.diamond.scisoft.analysis.processing.OperationException;
import uk.ac.diamond.scisoft.analysis.processing.OperationRank;
import uk.ac.diamond.scisoft.analysis.processing.model.IOperationModel;

public class FunctionOperation extends AbstractOperation {

	private IFunction      function;

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.fuctionOperation";
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
	public void setModel(IOperationModel model) throws Exception {
				
		this.function = (IFunction)model.get("function");
	}
	
	public OperationRank getInputRank() {
		return OperationRank.ANY; 
	}
	public OperationRank getOutputRank() {
		return OperationRank.SAME;
	}

}

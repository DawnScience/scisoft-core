package uk.ac.diamond.scisoft.analysis.processing.operations;

import uk.ac.diamond.scisoft.analysis.fitting.functions.IFunction;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;
import uk.ac.diamond.scisoft.analysis.processing.IOperation;
import uk.ac.diamond.scisoft.analysis.processing.IRichDataset;
import uk.ac.diamond.scisoft.analysis.processing.OperationData;
import uk.ac.diamond.scisoft.analysis.processing.OperationException;
import uk.ac.diamond.scisoft.analysis.processing.OperationRank;
import uk.ac.diamond.scisoft.analysis.processing.model.IOperationModel;

public class FunctionOperation implements IOperation {

    private IRichDataset   dataset;
	private IFunction      function;

	@Override
	public String getOperationDescription() {
		return "An operation able to run different functions on datasets.";
	}

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.fuctionOperation";
	}
	
	@Override
    public String getName() {
		return "Function";
	}


	@Override
	public void setDataset(IRichDataset... data) throws IllegalArgumentException {
		
		if (data.length!=1) throw new IllegalArgumentException("The function operation can only operate on one dataset at a time!");
		this.dataset = data[0];
	}

	@Override
	public OperationData execute(OperationData slice, IMonitor monitor) throws OperationException {
		return new OperationData(function.calculateValues(slice.getData()));
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

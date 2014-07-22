package uk.ac.diamond.scisoft.analysis.processing.operations;

import java.io.Serializable;

import uk.ac.diamond.scisoft.analysis.fitting.functions.IFunction;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;
import uk.ac.diamond.scisoft.analysis.processing.IOperation;
import uk.ac.diamond.scisoft.analysis.processing.IRichDataset;
import uk.ac.diamond.scisoft.analysis.processing.OperationData;
import uk.ac.diamond.scisoft.analysis.processing.OperationException;
import uk.ac.diamond.scisoft.analysis.processing.OperationRank;

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
	public void setDataset(IRichDataset... data) throws IllegalArgumentException {
		
		if (data.length!=1) throw new IllegalArgumentException("The function operation can only operate on one dataset at a time!");
		this.dataset = data[0];
	}

	@Override
	public OperationData execute(OperationData slice, IMonitor monitor) throws OperationException {
		return new OperationData(function.calculateValues(slice.getData()));
	}

	@Override
	public void setParameters(Serializable... parameters) throws IllegalArgumentException {
		
		if (parameters.length!=1) throw new IllegalArgumentException("The parameters accepted must a single function!");
		
		this.function = (IFunction)parameters[0];
	}
	
	public OperationRank getInputRank() {
		return OperationRank.ANY; 
	}
	public OperationRank getOutputRank() {
		return OperationRank.SAME;
	}

}

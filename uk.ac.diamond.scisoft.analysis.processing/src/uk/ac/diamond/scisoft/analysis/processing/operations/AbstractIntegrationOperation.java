package uk.ac.diamond.scisoft.analysis.processing.operations;

import java.io.Serializable;

import uk.ac.diamond.scisoft.analysis.processing.IOperation;
import uk.ac.diamond.scisoft.analysis.processing.IRichDataset;
import uk.ac.diamond.scisoft.analysis.processing.OperationRank;

public abstract class AbstractIntegrationOperation implements IOperation {

	protected IRichDataset data;
	@Override
	public String getOperationDescription() {
		return getClass().getSimpleName();
	}

	@Override
	public void setDataset(IRichDataset... data) throws IllegalArgumentException {
		if (data.length!=1) throw new IllegalArgumentException("You can only set one or one datasets for "+getClass().getSimpleName());
		this.data = data[0];
	}

	@Override
	public void setParameters(Serializable... parameters) throws IllegalArgumentException {
		throw new IllegalArgumentException("Parameters are not accepted for "+getClass().getSimpleName());
	}
	
	public OperationRank getInputRank() {
		return OperationRank.TWO; // Images
	}
	public OperationRank getOutputRank() {
		return OperationRank.ONE; // Addition for instance
	}
}

package uk.ac.diamond.scisoft.analysis.processing.operations;

import java.io.Serializable;

import uk.ac.diamond.scisoft.analysis.processing.IOperation;
import uk.ac.diamond.scisoft.analysis.processing.IRichDataset;
import uk.ac.diamond.scisoft.analysis.processing.OperationRank;
import uk.ac.diamond.scisoft.analysis.roi.IROI;

public abstract class AbstractIntegrationOperation implements IOperation {

	protected IRichDataset data;
	private IROI region;
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
		if (parameters.length!=1) throw new IllegalArgumentException("You may optionally set the region to use with an integration operation, one region only!");
		this.region = (IROI)parameters[0];
	}
	
	protected IROI getRegion() {
		if (region!=null) return region;
		return data.getRegions().get(0); // Might throw exception but all AbstractIntegrationOperation must have a region!
	}
	
	public OperationRank getInputRank() {
		return OperationRank.TWO; // Images
	}
	public OperationRank getOutputRank() {
		return OperationRank.ONE; // Addition for instance
	}
}

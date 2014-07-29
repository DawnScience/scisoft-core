package uk.ac.diamond.scisoft.analysis.processing.operations;

import uk.ac.diamond.scisoft.analysis.processing.AbstractOperation;
import uk.ac.diamond.scisoft.analysis.processing.IRichDataset;
import uk.ac.diamond.scisoft.analysis.processing.OperationRank;
import uk.ac.diamond.scisoft.analysis.processing.model.IOperationModel;
import uk.ac.diamond.scisoft.analysis.roi.IROI;

public abstract class AbstractIntegrationOperation extends AbstractOperation {

	protected IRichDataset data;
	private IROI region;

	
	@Override
	public void setDataset(IRichDataset... data) throws IllegalArgumentException {
		if (data.length!=1) throw new IllegalArgumentException("You can only set one or one datasets for "+getClass().getSimpleName());
		this.data = data[0];
	}

	@Override
	public void setModel(IOperationModel model) throws Exception {
		this.region = (IROI)model.get("region");
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

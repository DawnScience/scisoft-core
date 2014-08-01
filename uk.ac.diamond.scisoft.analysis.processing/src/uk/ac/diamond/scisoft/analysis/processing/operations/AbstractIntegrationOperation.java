package uk.ac.diamond.scisoft.analysis.processing.operations;

import uk.ac.diamond.scisoft.analysis.processing.AbstractOperation;
import uk.ac.diamond.scisoft.analysis.processing.OperationRank;
import uk.ac.diamond.scisoft.analysis.processing.model.IOperationModel;
import uk.ac.diamond.scisoft.analysis.roi.IROI;

public abstract class AbstractIntegrationOperation extends AbstractOperation {

	private IROI region;


	@Override
	public void setModel(IOperationModel model) throws Exception {
		super.setModel(model);
		this.region = (IROI)model.get("region");
	}
	
	protected IROI getRegion() {
		return region;
	}
	
	public OperationRank getInputRank() {
		return OperationRank.TWO; // Images
	}
	public OperationRank getOutputRank() {
		return OperationRank.ONE; // Addition for instance
	}
}

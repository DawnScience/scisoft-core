package uk.ac.diamond.scisoft.analysis.processing.operations;

import uk.ac.diamond.scisoft.analysis.processing.AbstractOperation;
import uk.ac.diamond.scisoft.analysis.processing.OperationRank;
import uk.ac.diamond.scisoft.analysis.processing.model.IOperationModel;
import uk.ac.diamond.scisoft.analysis.processing.model.OperationModelField;
import uk.ac.diamond.scisoft.analysis.roi.IROI;

public abstract class AbstractIntegrationOperation extends AbstractOperation {

	@OperationModelField(hint="The region to use with the operation.\n\nClick the '...' button to open the region dialog.")
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

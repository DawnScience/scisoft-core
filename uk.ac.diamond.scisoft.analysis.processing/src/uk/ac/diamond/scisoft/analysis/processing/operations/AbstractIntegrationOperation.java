package uk.ac.diamond.scisoft.analysis.processing.operations;

import java.lang.reflect.InvocationTargetException;

import uk.ac.diamond.scisoft.analysis.processing.AbstractOperation;
import uk.ac.diamond.scisoft.analysis.processing.OperationData;
import uk.ac.diamond.scisoft.analysis.processing.OperationRank;
import uk.ac.diamond.scisoft.analysis.processing.model.OperationModelField;
import uk.ac.diamond.scisoft.analysis.roi.IROI;

public abstract class AbstractIntegrationOperation<T extends IntegrationModel> extends AbstractOperation<T, OperationData> {

	@OperationModelField(hint="The region to use with the operation.\n\nClick the '...' button to open the region dialog.")
	private IROI region;


	@Override
	public void setModel(T model) {
		super.setModel(model);
		try {
			this.region = (IROI)model.get("region");
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			region = null;
		}
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

package uk.ac.diamond.scisoft.analysis.processing.operations;

import uk.ac.diamond.scisoft.analysis.processing.model.OperationModelField;
import uk.ac.diamond.scisoft.analysis.roi.IROI;
import uk.ac.diamond.scisoft.analysis.roi.RectangularROI;

public class BoxIntegrationModel extends IntegrationModel {

	@OperationModelField(label="Box Region", hint="The region to use with the operation.\n\nClick the '...' button to open the region dialog.")
	protected IROI region;

	public BoxIntegrationModel() {
		super();
		setRegion(new RectangularROI(0d, 0d, 10d, 10d, 0d));
	}
}

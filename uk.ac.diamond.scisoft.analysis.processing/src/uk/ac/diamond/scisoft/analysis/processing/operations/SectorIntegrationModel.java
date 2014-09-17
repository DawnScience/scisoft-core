package uk.ac.diamond.scisoft.analysis.processing.operations;

import uk.ac.diamond.scisoft.analysis.processing.model.OperationModelField;
import uk.ac.diamond.scisoft.analysis.roi.IROI;
import uk.ac.diamond.scisoft.analysis.roi.SectorROI;

public class SectorIntegrationModel extends IntegrationModel {

	@OperationModelField(label="Sector Region", hint="The region to use with the operation.\n\nClick the '...' button to open the region dialog.")
	protected IROI region;

	public SectorIntegrationModel() {
		super();
		setRegion(new SectorROI(10, 100, 10, 10));
	}

	public SectorIntegrationModel(IROI sector) {
		super();
		setRegion(sector);
	}
}

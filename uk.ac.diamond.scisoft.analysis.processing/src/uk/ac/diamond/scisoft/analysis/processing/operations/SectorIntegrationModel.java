package uk.ac.diamond.scisoft.analysis.processing.operations;

import uk.ac.diamond.scisoft.analysis.processing.model.OperationModelField;
import uk.ac.diamond.scisoft.analysis.roi.IROI;
import uk.ac.diamond.scisoft.analysis.roi.SectorROI;

public class SectorIntegrationModel extends IntegrationModel {

	@OperationModelField(label="Sector Region")
	protected IROI region;

	public SectorIntegrationModel() {
		super();
		setRegion(new SectorROI(10, 100, 10, 10));
	}
}

package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;
import org.eclipse.dawnsci.analysis.api.roi.IROI;
import org.eclipse.dawnsci.analysis.dataset.roi.SectorROI;

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

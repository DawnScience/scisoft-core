package uk.ac.diamond.scisoft.analysis.processing.operations;

import uk.ac.diamond.scisoft.analysis.roi.SectorROI;

public class SectorIntegrationModel extends IntegrationModel {

	public SectorIntegrationModel() {
		super();
		this.region = new SectorROI(10, 100, 10, 10);
	}
}

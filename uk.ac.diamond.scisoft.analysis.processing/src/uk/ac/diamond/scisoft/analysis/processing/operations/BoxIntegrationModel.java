package uk.ac.diamond.scisoft.analysis.processing.operations;

import uk.ac.diamond.scisoft.analysis.roi.RectangularROI;

public class BoxIntegrationModel extends IntegrationModel {

	public BoxIntegrationModel() {
		super();
		this.region = new RectangularROI(0d, 0d, 10d, 10d, 0d);
	}
}

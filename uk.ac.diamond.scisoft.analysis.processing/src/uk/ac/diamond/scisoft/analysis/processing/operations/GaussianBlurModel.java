package uk.ac.diamond.scisoft.analysis.processing.operations;

import uk.ac.diamond.scisoft.analysis.processing.model.AbstractOperationModel;

public class GaussianBlurModel extends AbstractOperationModel {

	int gaussianWidth = 3;

	public int getGaussianWidth() {
		return gaussianWidth;
	}

	public void setGaussianWidth(int gaussianWidth) {
		firePropertyChange("gaussianWidth", this.gaussianWidth, this.gaussianWidth = gaussianWidth);
	}
	
}

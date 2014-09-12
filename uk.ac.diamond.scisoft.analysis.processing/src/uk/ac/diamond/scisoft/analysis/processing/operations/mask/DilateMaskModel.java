package uk.ac.diamond.scisoft.analysis.processing.operations.mask;

import uk.ac.diamond.scisoft.analysis.processing.model.AbstractOperationModel;

public class DilateMaskModel extends AbstractOperationModel {

	int numberOfPixelsToDilate = 1;

	public int getNumberOfPixelsToDilate() {
		return numberOfPixelsToDilate;
	}

	public void setNumberOfPixelsToDilate(int numberOfPixelsToDilate) {
		firePropertyChange("pixelsToDilate", this.numberOfPixelsToDilate, this.numberOfPixelsToDilate = numberOfPixelsToDilate);
	}
	
}

package uk.ac.diamond.scisoft.analysis.processing.operations.mask;

import uk.ac.diamond.scisoft.analysis.processing.model.AbstractOperationModel;
import uk.ac.diamond.scisoft.analysis.processing.model.OperationModelField;

public class DilateMaskModel extends AbstractOperationModel {

	@OperationModelField(hint="Increase size of masked areas but a set number of pixels", label = "Set dilation size")
	private int numberOfPixelsToDilate = 1;

	public int getNumberOfPixelsToDilate() {
		return numberOfPixelsToDilate;
	}

	public void setNumberOfPixelsToDilate(int numberOfPixelsToDilate) {
		firePropertyChange("pixelsToDilate", this.numberOfPixelsToDilate, this.numberOfPixelsToDilate = numberOfPixelsToDilate);
	}
	
}

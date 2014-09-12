package uk.ac.diamond.scisoft.analysis.processing.operations.image;

import uk.ac.diamond.scisoft.analysis.processing.model.AbstractOperationModel;

public class KernelWidthModel extends AbstractOperationModel {

	private int width = 3;

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		firePropertyChange("width", this.width, this.width = width);
	}
	
}

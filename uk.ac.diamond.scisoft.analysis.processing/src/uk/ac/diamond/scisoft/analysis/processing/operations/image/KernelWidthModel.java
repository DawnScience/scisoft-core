package uk.ac.diamond.scisoft.analysis.processing.operations.image;

import uk.ac.diamond.scisoft.analysis.processing.model.AbstractOperationModel;
import uk.ac.diamond.scisoft.analysis.processing.model.FileType;
import uk.ac.diamond.scisoft.analysis.processing.model.OperationModelField;

public class KernelWidthModel extends AbstractOperationModel {

	@OperationModelField(hint="Set width of filter kernel in pixels", label = "Set Width")
	private int width = 3;

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		firePropertyChange("width", this.width, this.width = width);
	}
	
}

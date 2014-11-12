package uk.ac.diamond.scisoft.analysis.processing.operations.twod;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

import uk.ac.diamond.scisoft.analysis.dataset.function.DownsampleMode;

public class DownsampleImageModel extends AbstractOperationModel {

	@OperationModelField(label = "Set downsample mode")
	private DownsampleMode downsampleMode = DownsampleMode.MEAN;
	@OperationModelField(label = "Set downsample size X")
	private int downsampleSizeX = 2;
	@OperationModelField(label = "Set downsample size Y")
	private int downsampleSizeY = 2;
	public DownsampleMode getDownsampleMode() {
		return downsampleMode;
	}
	public void setDownsampleMode(DownsampleMode downsampleMode) {
		firePropertyChange("downsampleMode", this.downsampleMode, this.downsampleMode = downsampleMode);
	}
	public int getDownsampleSizeX() {
		return downsampleSizeX;
	}
	public void setDownsampleSizeX(int downsampleSizeX) {
		firePropertyChange("downsampleSizeX", this.downsampleSizeX, this.downsampleSizeX = downsampleSizeX);
	}
	
	public int getDownsampleSizeY() {
		return downsampleSizeY;
	}
	public void setDownsampleSizeY(int downsampleSizeY) {
		firePropertyChange("downsampleSizeY", this.downsampleSizeY, this.downsampleSizeY = downsampleSizeY);
	}
	
	
}

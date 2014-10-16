package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;

import uk.ac.diamond.scisoft.analysis.dataset.function.DownsampleMode;

public class DownsampleImageModel extends AbstractOperationModel {

	private DownsampleMode downsampleMode = DownsampleMode.MEAN;
	private int downsampleSize = 2;
	public DownsampleMode getDownsampleMode() {
		return downsampleMode;
	}
	public void setDownsampleMode(DownsampleMode downsampleMode) {
		firePropertyChange("downsampleMode", this.downsampleMode, this.downsampleMode = downsampleMode);
	}
	public int getDownsampleSize() {
		return downsampleSize;
	}
	public void setDownsampleSize(int downsampleSize) {
		this.downsampleSize = downsampleSize;
		firePropertyChange("downsampleSize", this.downsampleSize, this.downsampleSize = downsampleSize);
	}
	
	
}

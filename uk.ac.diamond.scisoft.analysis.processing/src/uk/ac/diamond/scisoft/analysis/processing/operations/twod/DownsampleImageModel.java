package uk.ac.diamond.scisoft.analysis.processing.operations.twod;

import org.eclipse.dawnsci.analysis.api.downsample.DownsampleMode;
import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

import uk.ac.diamond.scisoft.analysis.dataset.function.DownsampleDatatype;

public class DownsampleImageModel extends AbstractOperationModel {

	@OperationModelField(label = "Downsample mode", hint = "The mode, choose from a list of alternative modes.")
	private DownsampleMode downsampleMode = DownsampleMode.MEAN;
	@OperationModelField(label = "Downsample size X", hint = "The width of the downsample box in pixels.")
	private int downsampleSizeX = 2;
	@OperationModelField(label = "Downsample size Y", hint = "The height of the downsample box in pixels.")
	private int downsampleSizeY = 2;
	@OperationModelField(label = "Downsample Output datatype", hint = "Datatype of the downsample output.")
	private DownsampleDatatype downSampleDatatype = DownsampleDatatype.FLOAT32;

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
	
	public DownsampleDatatype getDownSampleDatatype() {
		return downSampleDatatype;
	}
	public void setDownsampleDatatype(DownsampleDatatype downSampleDatatype) {
		firePropertyChange("downSampleDatatype", this.downSampleDatatype, this.downSampleDatatype = downSampleDatatype);
	}
}

package uk.ac.diamond.scisoft.analysis.processing.operations.image;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class RotateImageModel extends AbstractOperationModel {

	enum AngleUnit {
		RADIAN,
		DEGREE;
	}
	@OperationModelField(label = "Angle", hint = "The image will be rotated by the angle provided around the centre of the dataset (x=xMax/2, y=yMax/2)")
	private double angle = 0;
	@OperationModelField(label = "Unit", hint = "Angle unit")
	private AngleUnit unit = AngleUnit.DEGREE;
	@OperationModelField(label = "Resize", hint = "Resize resulting bounding box")
	private boolean resize = false;

	public double getAngle() {
		return angle;
	}
	public void setAngle(double angle) {
		firePropertyChange("rotationAngle", this.angle, this.angle = angle);
	}
	public AngleUnit getUnit() {
		return unit;
	}
	public void setUnit(AngleUnit unit) {
		firePropertyChange("angleUnit", this.unit, this.unit = unit);
	}
	public boolean isResize() {
		return resize;
	}
	public void setResize(boolean resize) {
		firePropertyChange("resize", this.resize, this.resize = resize);
	}
}

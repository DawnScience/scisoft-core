package uk.ac.diamond.scisoft.analysis.processing.operations.image;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class AffineTransformImageModel extends AbstractOperationModel {

	@OperationModelField(label = "a11")
	private double a11 = 1;
	@OperationModelField(label = "a12")
	private double a12 = 0;
	@OperationModelField(label = "a21")
	private double a21 = 0;
	@OperationModelField(label = "a22")
	private double a22 = 1;
	@OperationModelField(label = "dx")
	private double dx = 0;
	@OperationModelField(label = "dy")
	private double dy = 0;
	@OperationModelField(label = "Resize", hint = "Resize resulting bounding box")
	private boolean resize = false;

	public double getA11() {
		return a11;
	}
	public void setA11(double a11) {
		firePropertyChange("a11", this.a11, this.a11 = a11);
	}

	public double getA12() {
		return a12;
	}
	public void setA12(double a12) {
		firePropertyChange("a12", this.a12, this.a12 = a12);
	}

	public double getA21() {
		return a21;
	}
	public void setA21(double a21) {
		firePropertyChange("a21", this.a21, this.a21 = a21);
	}

	public double getA22() {
		return a22;
	}
	public void setA22(double a22) {
		firePropertyChange("a22", this.a22, this.a22 = a22);
	}

	public double getDx() {
		return dx;
	}
	public void setDx(double dx) {
		firePropertyChange("dx", this.dx, this.dx = dx);
	}

	public double getDy() {
		return dy;
	}
	public void setDy(double dy) {
		firePropertyChange("dy", this.dy, this.dy = dy);
	}

	public boolean isResize() {
		return resize;
	}
	public void setResize(boolean resize) {
		firePropertyChange("resize", this.resize, this.resize = resize);
	}
}

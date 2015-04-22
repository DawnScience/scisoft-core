/*-
 * Copyright (c) 2011-2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations.image;

import org.eclipse.dawnsci.analysis.api.image.ImageThresholdType;
import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class ThresholdImageModel extends AbstractOperationModel {

	@OperationModelField(label = "Type", hint = "Type of thresholding algorithm")
	private ImageThresholdType type = ImageThresholdType.GLOBAL_CUSTOM;
	@OperationModelField(label = "Threshold", hint = "Value used for GLOBAL_CUSTOM thresholding", enableif="type==\"GLOBAL_CUSTOM\"")
	private float threshold = 0;
	@OperationModelField(label = "Radius", hint = "Used for adaptive thresholding algorithms that are computed "
			+ "using a local square region centered on each pixel.")
	private int radius = 0;
	@OperationModelField(label = "Down", hint = "If 'down' is true, then pixels with "
			+ "values <= to 'threshold' are set to 1 and the others set to 0. "
			+ "If 'down' is false, then pixels with values >= to 'threshold' are "
			+ "set to 1 and the others set to 0.")
	private boolean down = true;

	public float getThreshold() {
		return threshold;
	}
	public void setThreshold(float threshold) {
		firePropertyChange("thresholdValue", this.threshold, this.threshold = threshold);
	}
	public ImageThresholdType getType() {
		return type;
	}
	public void setType(ImageThresholdType type) {
		firePropertyChange("thresholdType", this.type, this.type = type);
	}
	public int getRadius() {
		return radius;
	}
	public void setRadius(int radius) {
		firePropertyChange("radius", this.radius, this.radius = radius);
	}
	public boolean isDown() {
		return down;
	}
	public void setDown(boolean down) {
		firePropertyChange("down", this.down, this.down = down);
	}
}

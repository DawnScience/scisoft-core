/*-
 * Copyright 2019 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

// @author Andrew McCluskey

package uk.ac.diamond.scisoft.analysis.processing.operations.backgroundsubtraction;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class GaussianBackgroundSubtractionModel extends AbstractOperationModel {

	@OperationModelField(label="Non-uniform background?", hint="Would you like to fit a non-uniform background? This will fit a straight line gradient background")
	private boolean uniformBackground = false;
	
	public boolean isUniformBackground() {
		return uniformBackground;
	}
	
	public void setDoAverage(boolean uniformBackground) {
		firePropertyChange("uniformBackground", this.uniformBackground, this.uniformBackground = uniformBackground);
	}
}

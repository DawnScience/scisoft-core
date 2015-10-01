/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.oned;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class SplineBaselineModel extends AbstractOperationModel {

	@OperationModelField(label = "Control point x-values",hint="Enter a list of comma-separated x values for the spline control points.")
	double[] xControlPoints = null;
	@OperationModelField(label = "Control point y-values",hint="Enter a list of comma-separated y values for the spline control points.")
	double[] yControlPoints = null;
	
	public double[] getXControlPoints() {
		return xControlPoints;
	}

	public void setXControlPoints(double[] xControlPoints) {
		firePropertyChange("xControlPoints", this.xControlPoints, this.xControlPoints = xControlPoints);
	}
	
	public double[] getYControlPoints() {
		return yControlPoints;
	}

	public void setYControlPoints(double[] yControlPoints) {
		firePropertyChange("yControlPoints", this.yControlPoints, this.yControlPoints = yControlPoints);
	}
	
}

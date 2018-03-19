/*-
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


package uk.ac.diamond.scisoft.analysis.processing.operations.externaldata;


import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;


public class SubtractMatchedIntensityModel extends SubtractWithProcessingModel {
	
	
	@OperationModelField(label = "Q-range for autoscaling",hint="Two values, start and end, separated by a comma i.e. 2,4.The values should match the axis selected (i.e. q, 2 theta, pixel).If you delete the text, the range is cleared and the whole image used.", fieldPosition = 7)
	double[] qScalingRange = null;
	
	
	public double[] getQScalingRange() {
		return qScalingRange;
	}
	
	
	public void setQScalingRange(double[] qScalingRange) {
		firePropertyChange("qScalingRange", this.qScalingRange, this.qScalingRange = qScalingRange);
	}
}
/*-
 * Copyright 2021 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

// @author Tim Snow


package uk.ac.diamond.scisoft.analysis.processing.operations.twod;


import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

import uk.ac.diamond.scisoft.analysis.processing.MathematicalOperators;


public class FrameMathsFromPeakModel extends CropAroundPeak2DModel {

	
	@OperationModelField(label = "Mathematical Operator", hint="The mathematical operation to perform on the frame from the integrated peak value.", fieldPosition = 3)
	private MathematicalOperators mathematicalOperator = MathematicalOperators.ADD;
	
	public MathematicalOperators getMathematicalOperator() {
		return mathematicalOperator;
	}
	
	public void setMathematicalOperator(MathematicalOperators mathematicalOperator) {
		firePropertyChange("mathematicalOperator", this.mathematicalOperator, this.mathematicalOperator = mathematicalOperator);
	}

}
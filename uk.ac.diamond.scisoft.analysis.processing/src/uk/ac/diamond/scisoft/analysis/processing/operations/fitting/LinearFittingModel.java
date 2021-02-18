/*-
 * Copyright (c) 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


package uk.ac.diamond.scisoft.analysis.processing.operations.fitting;


// Imports from org.eclipse.dawnsci
import org.eclipse.dawnsci.analysis.api.processing.model.RangeType;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;
import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;


// @author Tim Snow


// A straightforward y = mx + c line fitter
public class LinearFittingModel extends AbstractOperationModel {


	// Get the region of interest for where to perform the fitting
	@OperationModelField(rangevalue = RangeType.XRANGE, label = "Plot region of interest", hint = "Two q values, start and end, separated by a comma, for example 0.02,0.2. The values should match the axis. If you delete the text the whole dataset will be evaluated", fieldPosition = 1)
	private double[] fittingRange = null;

	// Setting up the getters and setters along the way
	public double[] getFittingRange() {
		return fittingRange;
	}
	public void setFittingRange(double [] fittingRange) {
		firePropertyChange("fittingRange", this.fittingRange, this.fittingRange = fittingRange);
	}

	@OperationModelField(label = "Transpose coordinates", description = "Determines whether to fit y vs x or x vs y")
	private boolean transpose = false;

	/**
	 * @return true if fit to x vs y
	 */
	public boolean getTranspose() {
		return transpose;
	}

	public void setTranspose(boolean transpose) {
		firePropertyChange("transpose", this.transpose, this.transpose = transpose);
	}
}
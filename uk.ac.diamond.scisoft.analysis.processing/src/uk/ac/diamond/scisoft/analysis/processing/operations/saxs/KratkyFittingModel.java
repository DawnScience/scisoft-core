/*-
 * Copyright (c) 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


package uk.ac.diamond.scisoft.analysis.processing.operations.saxs;


// Imports from org.eclipse.dawnsci
import org.eclipse.dawnsci.analysis.api.processing.model.RangeType;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;
import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;


// @author Tim Snow


// The operation to take a region of reduced SAXS data, obtain a Kratky plot and fit, as well as
// information that, ultimately, provides information about the shape of the molecule
public class KratkyFittingModel extends AbstractOperationModel {


	// Get the region of interest for where to perform the Guinier analysis
	@OperationModelField(rangevalue = RangeType.XRANGE, label = "Plot region of interest", hint = "Two q values, start and end, separated by a comma, for example 0.02,0.2. The values should match the axis. If you delete the text the whole dataset will be evaluated", fieldPosition = 1)
	private double[] kratkyRange = null;

	// Setting up the getters and setters along the way
	public double[] getKratkyRange() {
		return kratkyRange;
	}
	public void setKratkyRange(double [] kratkyRange) {
		firePropertyChange("kratkyRange", this.kratkyRange, this.kratkyRange = kratkyRange);
	}
}
/*-
 * Copyright (c) 2017 Diamond Light Source Ltd.
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


//@author Tim Snow, adapted from original plug-in set by Tim Spain.


// A processing plugin to calculate the mean thickness of mineral crystals, for more information see:
//
// P. Fratzl, S. Schreiber and K. Klaushofer, Connective Tissue Research, 1996, 14, 247-254, DOI: 10.3109/03008209609005268
//
public class TParameterModel extends AbstractOperationModel {

		// Get the region of interest for where to perform the Guinier analysis
	@OperationModelField(rangevalue = RangeType.XRANGE, label = "Porod region of interest", hint = "Two q values, start and end, separated by a comma, for example 0.02,0.2. The values should match the axis. If you delete the text the whole dataset will be evaluated", fieldPosition = 2)
	private double[] porodRange = new double[]{0.15, 0.20};

	// Setting up the getters and setters along the way
	public double[] getPorodRange() {
		return porodRange;
	}
	public void setPorodRange(double [] porodRange) {
		firePropertyChange("porodRange", this.porodRange, this.porodRange = porodRange);
	}

	// Get the region of interest for where to perform the Guinier analysis
	@OperationModelField(rangevalue = RangeType.XRANGE, label = "Kratky region of interest", hint = "Two q values, start and end, separated by a comma, for example 0.02,0.2. The values should match the axis. If you delete the text the whole dataset will be evaluated", fieldPosition = 1)
	private double[] kratkyRange = new double[]{0.05, 0.10};

	// Setting up the getters and setters along the way
	public double[] getKratkyRange() {
		return kratkyRange;
	}
	public void setKratkyRange(double [] kratkyRange) {
		firePropertyChange("kratkyRange", this.kratkyRange, this.kratkyRange = kratkyRange);
	}
}
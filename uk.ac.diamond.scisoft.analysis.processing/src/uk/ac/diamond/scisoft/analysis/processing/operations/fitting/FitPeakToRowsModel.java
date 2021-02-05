/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.fitting;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;
import org.eclipse.dawnsci.analysis.api.processing.model.RangeType;

import uk.ac.diamond.scisoft.analysis.fitting.functions.PeakType;

public class FitPeakToRowsModel extends AbstractOperationModel {

	static final String INVALID = "invalid";
	public enum ResultParameter {
		POSITION("Position"),
		FWHM("Width"),
		AREA("Area"),
		P3(INVALID),
		P4(INVALID);

		private String name;

		ResultParameter(String name) {
			this.name = name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	@OperationModelField(rangevalue = RangeType.XRANGE, label = "Fit range", description = "Range of x to fit across", hint = "Two values, start and end, separated by a comma, for example 2,4. The values should match the axis . If you delete the text, the range is cleared and the whole range used.")
	double[] fitRange = null;
	
	public double[] getFitRange() {
		return fitRange;
	}
	public void setFitRange(double[] fitRange) {
		firePropertyChange("fitRange", this.fitRange, this.fitRange = fitRange);
	}

	@OperationModelField(label = "Peak function", description = "Function used to fit peak")
	PeakType peakType = PeakType.GAUSSIAN;

	/**
	 * @return peak function type
	 */
	public PeakType getPeakType() {
		return peakType;
	}

	public static final String PEAK_TYPE = "peakType";

	public void setPeakType(PeakType peakType) {
		firePropertyChange(PEAK_TYPE, this.peakType, this.peakType = peakType);
	}

	@OperationModelField(label = "Result parameter", description = "Which fit parameter to use as result")
	ResultParameter resultParameter = ResultParameter.POSITION;

	/**
	 * @return choice of parameter as result
	 */
	public ResultParameter getResultParameter() {
		return resultParameter;
	}

	public void setResultParameter(ResultParameter resultParameter) {
		firePropertyChange("resultParameter", this.resultParameter, this.resultParameter = resultParameter);
	}
}

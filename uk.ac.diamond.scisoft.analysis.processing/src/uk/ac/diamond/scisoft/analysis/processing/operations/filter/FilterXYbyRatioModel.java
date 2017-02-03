/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.filter;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;
import org.eclipse.dawnsci.analysis.api.processing.model.RangeType;

public class FilterXYbyRatioModel extends AbstractOperationModel {

	@OperationModelField(rangevalue = RangeType.XRANGE,label = "Numerator range",hint="Two values, start and end, separated by a comma, for example 2,4. The values should match the axis . If you delete the text, the range is cleared and the whole range used.")
	double[] numeratorRange = null;
	
	@OperationModelField(rangevalue = RangeType.XRANGE,label = "Denomenator range",hint="Two values, start and end, separated by a comma, for example 2,4. The values should match the axis . If you delete the text, the range is cleared and the whole range used.")
	double[] denominatorRange = null;
	
	@OperationModelField(label = "Threshold",hint="Threshold value to filter data against")
	double threshold = 1;
	
	@OperationModelField(label = "Filter above",hint="Filter data above threshold instead of below")
	boolean filterAboveThreshold = false;

	public double[] getNumeratorRange() {
		return numeratorRange;
	}

	public void setNumeratorRange(double[] numeratorRange) {
		firePropertyChange("numeratorRange", this.numeratorRange, this.numeratorRange = numeratorRange);
	}

	public double[] getDenominatorRange() {
		return denominatorRange;
	}

	public void setDenominatorRange(double[] denominatorRange) {
		firePropertyChange("denominatorRange", this.denominatorRange, this.denominatorRange = denominatorRange);
	}

	public double getThreshold() {
		return threshold;
	}

	public void setThreshold(double threshold) {
		firePropertyChange("threshold", this.threshold, this.threshold = threshold);
	}

	public boolean isFilterAboveThreshold() {
		return filterAboveThreshold;
	}

	public void setFilterAboveThreshold(boolean filterAboveThreshold) {
		firePropertyChange("filterAboveThreshold", this.filterAboveThreshold, this.filterAboveThreshold = filterAboveThreshold);
	}
	
}

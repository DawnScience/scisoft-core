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

public class Rebinning1DModel extends AbstractOperationModel {

	@OperationModelField(label = "Min", hint = "Minimum value to use in rebinning, leave blank to use the min from the first dataset")
	Double min  = null;
	@OperationModelField(label = "Max", hint = "Maximum value to use in rebinning, leave blank to use the max from the first dataset")
	Double max = null;
	@OperationModelField(label = "Number of bins", hint = "Number of bins to rebin the data into, leave blank to use the number of bins from the first dataset")
	Integer numberOfBins = null;
	
	public Double getMin() {
		return min;
	}
	public void setMin(Double min) {
		firePropertyChange("min", this.min, this.min = min);
	}
	public Double getMax() {
		return max;
	}
	public void setMax(Double max) {
		firePropertyChange("max", this.max, this.max = max);
	}
	public Integer getNumberOfBins() {
		return numberOfBins;
	}
	public void setNumberOfBins(Integer numberOfBins) {
		firePropertyChange("numberOfBins", this.numberOfBins, this.numberOfBins = numberOfBins);
	}
}

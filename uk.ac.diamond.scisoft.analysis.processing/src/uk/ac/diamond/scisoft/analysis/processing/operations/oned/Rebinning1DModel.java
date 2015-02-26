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

public class Rebinning1DModel extends AbstractOperationModel {

	Double min  = null;
	Double max = null;
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

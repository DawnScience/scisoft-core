/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.twod;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class IntegrateImageMatchedAxesModel extends AbstractOperationModel {

	
	@OperationModelField(hint="Number of bins to integrate the data into, can be left blank", label = "Number of bins")
	Integer nBins = null;
	
	@OperationModelField(label = "Range",hint="Two values, start and end, separated by a comma i.e. 2,4.The values should be the same as the input image axes.If you delete the text, the range is cleared and the whole image used.")
	double[] range = null;

	public Integer getnBins() {
		return nBins;
	}

	public void setnBins(Integer nBins) {
		firePropertyChange("nBins", this.nBins, this.nBins = nBins);
	}

	public double[] getRange() {
		return range;
	}

	public void setRange(double[] range) {
		firePropertyChange("range", this.range, this.range = range);
	}
	
}

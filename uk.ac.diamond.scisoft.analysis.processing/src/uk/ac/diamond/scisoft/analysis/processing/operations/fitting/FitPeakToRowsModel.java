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

public class FitPeakToRowsModel extends AbstractOperationModel {

	@OperationModelField(rangevalue = RangeType.XRANGE,label = "Set fitting range",hint="Two values, start and end, separated by a comma, for example 2,4. The values should match the axis . If you delete the text, the range is cleared and the whole range used.")
	double[] fitRange = null;
	
	public double[] getFitRange() {
		return fitRange;
	}
	public void setFitRange(double[] fitRange) {
		firePropertyChange("fitRange", this.fitRange, this.fitRange = fitRange);
	}	
}

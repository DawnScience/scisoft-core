/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.oned;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;
import org.eclipse.dawnsci.analysis.api.processing.model.RangeType;

public class XRangeModel extends AbstractOperationModel {
	
	@OperationModelField(rangevalue = RangeType.XRANGE,label = "Set range",hint="Two values, start and end, separated by a comma, for example 2,4. The values should match the axis . If you delete the text, the range is cleared and the whole range used.")
	double[] range = null;
	
	public double[] getRange() {
		return range;
	}
	public void setRange(double[] range) {
		firePropertyChange("range", this.range, this.range = range);
	}

}

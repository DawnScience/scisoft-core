/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.roiprofile;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;
import org.eclipse.dawnsci.analysis.api.processing.model.RangeType;

public class SubtractIntegratedXRegionsModel extends AbstractOperationModel {

	@OperationModelField(rangevalue = RangeType.XRANGE,label = "Set signal range",hint="Please set two values, start and end, separated by a comma, for example 2,4. The values should match the axis . If you delete the text, the range is cleared and the whole range used.")
	double[] signal = null;
	
	@OperationModelField(rangevalue = RangeType.XRANGE,label = "Set background 1 range",hint="Please set two values, start and end, separated by a comma, for example 2,4. The values should match the axis . If you delete the text, the range is cleared and the whole range used.")
	double[] background0 = null;
	
	@OperationModelField(rangevalue = RangeType.XRANGE,label = "Set background 2 range",hint="Please set two values, start and end, separated by a comma, for example 2,4. The values should match the axis . If you delete the text, the range is cleared and the whole range used.")
	double[] background1 = null;
	
	@OperationModelField(label = "Use full image for ratio",hint="Ratio of background subtracted/un-subtracted also output, check to ratio against full image, rather than unsubtracted signal.")
	boolean useFullFrameForRatio = false;
	
	public boolean isUseFullFrameForRatio() {
		return useFullFrameForRatio;
	}

	public void setUseFullFrameForRatio(boolean useFullFrameForRatio) {
		firePropertyChange("useFullFrameForRatio", this.useFullFrameForRatio, this.useFullFrameForRatio = useFullFrameForRatio);
	}

	public double[] getSignal() {
		return signal;
	}

	public void setSignal(double[] signal) {
		firePropertyChange("signal", this.signal, this.signal = signal);
	}

	public double[] getBackground0() {
		return background0;
	}

	public void setBackground0(double[] background0) {
		firePropertyChange("background0", this.background0, this.background0 = background0);
	}

	public double[] getBackground1() {
		return background1;
	}

	public void setBackground1(double[] background1) {
		firePropertyChange("background1", this.background1, this.background1 = background1);
	}
	
	
	
}

/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.reflectivityandsxrd;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;

import uk.ac.diamond.scisoft.analysis.processing.operations.roiprofile.BoxIntegration.Direction;

public class Polynomial2DReflectivityModel extends AbstractOperationModel {


	@OperationModelField(label="beam height", hint = "beam height" )
	private double beamHeight = 1;

	@OperationModelField(label="footprint", hint = "footprint of smaple in mm" )
	private double footprint = 1;
	
	@OperationModelField(label="angular fudge factor", hint = "angular fudge factor" )
	private double angularFudgeFactor = 1;

	public double getAngularFudgeFactor() {
		return angularFudgeFactor;
	}
	
	public void setAngularFudgeFactor(double angularFudgeFactor) {
		firePropertyChange("angularFudgeFactor", this.angularFudgeFactor, this.angularFudgeFactor= angularFudgeFactor);
	}

	public double getFootprint() {
		return footprint;
	}

	public void setFootprint(double footprint) {
		firePropertyChange("footprint", this.footprint, this.footprint= footprint);
	}

	public double getBeamHeight() {
		return beamHeight;
	}

	public void setBeamHeight(double beamHeight) {
		firePropertyChange("beamHeight", this.beamHeight, this.beamHeight= beamHeight);
	}
	

	//
	
}
//TEST
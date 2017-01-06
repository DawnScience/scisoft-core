/*-
 * Copyright (c) 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.FileType;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;
import org.eclipse.dawnsci.analysis.api.processing.model.RangeType;


// More information and the equation for the Degree of Crystallinity operation can be found in:
// The SAXS Guide - 3rd Edition - Heimo Schnablegger & Yashveer Singh - ISBN 9783900323882 - Published by Anton Paar GmbH, Austria
// Section 5.4.10 - Degree of Crystallinity


// @author Tim Snow

// The model for a DAWN process to perform a Degree of Crystallinity calculation on a given reduced dataset
public class DegreeOfCrystallinityModel extends AbstractOperationModel {

	// @OperationModelField annotations for the UI element creation
	// Get the location of the background file
	@OperationModelField(hint="Amorphous background scan file", file = FileType.EXISTING_FILE, label = "Amorphous background file", fieldPosition = 1)
	private String backgroundScanFilePath = "";
	
	// Set up the getter...
	public String getBackgroundScanFilePath() {
		return backgroundScanFilePath;
	}

	// and setter.
	public void setBackgroundScanFilePath(String backgroundScanFilePath) {
		firePropertyChange("backgroundScanFilePath", this.backgroundScanFilePath, this.backgroundScanFilePath = backgroundScanFilePath);
	}


	// @OperationModelField annotations for the UI element creation
	// Get the internal filepath of the reduced background data
	@OperationModelField(dataset = "backgroundScanFilePath", label = "Background data scan", fieldPosition = 2)
	private String backgroundDataPath = "/entry/result/data";
	
	// Set up the getter...
	public String getBackgroundDataPath() {
		return backgroundDataPath;
	}

	// and setter.
	public void setBackgroundDataPath(String backgroundDataPath) {
		firePropertyChange("backgroundDataPath", this.backgroundDataPath, this.backgroundDataPath = backgroundDataPath);
	}

	
	//@OperationModelField annotations for the UI setup
	// Finally, the range of interest
	@OperationModelField(rangevalue = RangeType.XRANGE, label = "Set integration range", hint="Two values, start and end, separated by a comma, for example 2,4. The values should match the axis . If you delete the text, the range is cleared and the whole range used.", fieldPosition = 3)
	double[] integrationRange = null;

	
	// Now the getters and setters
	public double[] getIntegrationRange() {
		return integrationRange;
	}

	public void setIntegrationRange(double[] integrationRange) {
		firePropertyChange("integrationRange", this.integrationRange, this.integrationRange = integrationRange);
	}
}
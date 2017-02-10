/*-
 * Copyright (c) 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


package uk.ac.diamond.scisoft.analysis.processing.operations.backgroundsubtraction;


// Imports from org.eclipse.dawnsci
import org.eclipse.dawnsci.analysis.api.processing.model.FileType;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;
import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;


// More information and the equation for this background subtraction routine can be found in:
// Everything SAXS: small-angle scattering pattern collection and correction
// B. R. Pauw, Journal of Physics: Condensed Matter, 2013, 25, 383201. 
// DOI: 10.1088/0953-8984/25/38/383201


// @author Tim Snow


// The model for a DAWN processing plugin to perform background subtraction on a scattered diffraction pattern
public class Pauw1DBackgroundSubtractionModel extends AbstractOperationModel {


	// @OperationModelField annotations for the UI element creation
	// Get the location of the background file
	@OperationModelField(hint="Enter the path to the original background file", file = FileType.EXISTING_FILE, label = "Reduced absolute scan file", fieldPosition = 0)
	private String absoluteScanFilepath = "Glassy Carbon.nxs";
	
	// Set up the getter...
	public String getAbsoluteScanFilepath() {
		return absoluteScanFilepath;
	}

	// and setter.
	public void setAbsoluteScanFilepath(String absoluteScanFilepath) {
		firePropertyChange("absoluteScanFilepath", this.absoluteScanFilepath, this.absoluteScanFilepath = absoluteScanFilepath);
	}


	// @OperationModelField annotations for the UI element creation
	// Get the location of the background file
	@OperationModelField(hint="Enter the path to the original background file", file = FileType.EXISTING_FILE, label = "Original background file", fieldPosition = 1)
	private String backgroundFilePath = "Cell Background.nxs";
	
	// Set up the getter...
	public String getBackgroundFilePath() {
		return backgroundFilePath;
	}

	// and setter.
	public void setBackgroundFilePath(String backgroundFilePath) {
		firePropertyChange("backgroundFilePath", this.backgroundFilePath, this.backgroundFilePath = backgroundFilePath);
	}


	// @OperationModelField annotations for the UI element creation
	// Get the location of the background file
	@OperationModelField(hint="Enter the path to the reduced background file", file = FileType.EXISTING_FILE, label = "Reduced background file", fieldPosition = 2)
	private String reducedBackgroundFilePath = "/scratch/Dawn/runtime-uk.ac.diamond.dawn.product/I22 Workspace/Data/i22-361563_processed_161117_164518.nxs";
	
	// Set up the getter...
	public String getReducedBackgroundFilePath() {
		return reducedBackgroundFilePath;
	}

	// and setter.
	public void setReducedBackgroundFilePath(String reducedBackgroundFilePath) {
		firePropertyChange("reducedBackgroundFilePath", this.reducedBackgroundFilePath, this.reducedBackgroundFilePath = reducedBackgroundFilePath);
	}


	// @OperationModelField annotations for the UI element creation
	// Get the internal filepath of the background thickness
	@OperationModelField(dataset = "getGlassyCarbonFilepath", label = "Absolute intensity scan path", fieldPosition = 3)
	private String glassyCarbonData = "/entry/result/data";
	
	// Set up the getter...
	public String getGlassyCarbonData() {
		return glassyCarbonData;
	}

	// and setter.
	public void setGlassyCarbonData(String glassyCarbonData) {
		firePropertyChange("glassyCarbonData", this.glassyCarbonData, this.glassyCarbonData = glassyCarbonData);
	}


	// @OperationModelField annotations for the UI element creation
	// Get the internal filepath of the background thickness
	@OperationModelField(dataset = "reducedBackgroundFilePath", label = "Reduced background data path", fieldPosition = 4)
	private String reducedBackgroundData = "/entry/result/data";
	
	// Set up the getter...
	public String getReducedBackgroundData() {
		return reducedBackgroundData;
	}

	// and setter.
	public void setReducedBackgroundData(String reducedBackgroundData) {
		firePropertyChange("reducedBackgroundData", this.reducedBackgroundData, this.reducedBackgroundData = reducedBackgroundData);
	}

	
	// @OperationModelField annotations for the UI element creation
	// Get the internal filepath of the sample thickness
	@OperationModelField(dataset = "filePath", label = "Thickness path", fieldPosition = 5)
	private String thicknessPath = "/entry1/sample/thickness";
	
	// Set up the getter...
	public String getThicknessPath() {
		return thicknessPath;
	}

	// and setter.
	public void setThicknessPath(String thicknessPath) {
		firePropertyChange("thicknessPath", this.thicknessPath, this.thicknessPath = thicknessPath);
	}


	// @OperationModelField annotations for the UI element creation
	// Get the internal filepath of the sample I_0
	@OperationModelField(dataset = "filePath", label = "I(0) path",fieldPosition = 6)
	private String iZeroPath = "/entry1/I0/data";
	
	// Set up the getter...
	public String getIZeroPath() {
		return iZeroPath;
	}

	// and setter.
	public void setIZeroPath(String iZeroPath) {
		firePropertyChange("iZeroPath", this.iZeroPath, this.iZeroPath = iZeroPath);
	}


	// @OperationModelField annotations for the UI element creation
	// Get the internal filepath of the sample I_t
	@OperationModelField(dataset = "filePath", label = "I(t) path",fieldPosition = 7)
	private String iTransmissionPath = "/entry1/It/data";
	
	// Set up the getter...
	public String getITransmissionPath() {
		return iTransmissionPath;
	}

	// and setter.
	public void setITransmissionPath(String iTransmissionPath) {
		firePropertyChange("iTransmissionPath", this.iTransmissionPath, this.iTransmissionPath = iTransmissionPath);
	}
}
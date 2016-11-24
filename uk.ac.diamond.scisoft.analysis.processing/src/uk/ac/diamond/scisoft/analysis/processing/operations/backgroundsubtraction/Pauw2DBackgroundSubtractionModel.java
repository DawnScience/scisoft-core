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
public class Pauw2DBackgroundSubtractionModel extends AbstractOperationModel {


	// @OperationModelField annotations for the UI element creation
	// Get the location of the background file
	@OperationModelField(hint="Absolute intensity scan, e.g. glassy carbon, file path", file = FileType.EXISTING_FILE, label = "Absolute scan file", fieldPosition = 0)
	private String absoluteScanFilePath = "/scratch/Dawn/runtime-uk.ac.diamond.dawn.product/I22 Workspace/Andy/Pauw/Glassy Carbon.nxs";
	
	// Set up the getter...
	public String getAbsoluteScanFilePath() {
		return absoluteScanFilePath;
	}

	// and setter.
	public void setAbsoluteScanFilePath(String absoluteScanFilePath) {
		firePropertyChange("absoluteScanFilePath", this.absoluteScanFilePath, this.absoluteScanFilePath = absoluteScanFilePath);
	}


	// @OperationModelField annotations for the UI element creation
	// Get the location of the background file
	@OperationModelField(hint="Background intensity scan, e.g. cell with solvent, file path", file = FileType.EXISTING_FILE, label = "Background scan file", fieldPosition = 1)
	private String backgroundScanFilePath = "/scratch/Dawn/runtime-uk.ac.diamond.dawn.product/I22 Workspace/Andy/Pauw/Average Background.nxs";
	
	// Set up the getter...
	public String getBackgroundScanFilePath() {
		return backgroundScanFilePath;
	}

	// and setter.
	public void setBackgroundScanFilePath(String backgroundScanFilePath) {
		firePropertyChange("backgroundScanFilePath", this.backgroundScanFilePath, this.backgroundScanFilePath = backgroundScanFilePath);
	}


	// @OperationModelField annotations for the UI element creation
	// Get the internal filepath of the sample thickness
	@OperationModelField(dataset = "backgroundScanFilePath", label = "Original file path", fieldPosition = 2)
	private String originalFilePath = "/entry/process/origin/path";
	
	// Set up the getter...
	public String getOriginalFilePath() {
		return originalFilePath;
	}

	// and setter.
	public void setOriginalFilePath(String originalFilePath) {
		firePropertyChange("originalFilePath", this.originalFilePath, this.originalFilePath = originalFilePath);
	}
	

	// @OperationModelField annotations for the UI element creation
	// Get the internal filepath of the sample thickness
	@OperationModelField(dataset = "backgroundScanFilePath", label = "Processed data path", fieldPosition = 3)
	private String processedDataPath = "/entry/result/data";
	
	// Set up the getter...
	public String getProcessedDataPath() {
		return processedDataPath;
	}

	// and setter.
	public void setProcessedDataPath(String processedDataPath) {
		firePropertyChange("processedDataPath", this.processedDataPath, this.processedDataPath = processedDataPath);
	}

	
	// @OperationModelField annotations for the UI element creation
	// Get the internal filepath of the sample thickness
	@OperationModelField(dataset = "backgroundScanFilePath", label = "Processed error data path", fieldPosition = 4)
	private String processedErrorPath = "/entry/result/error";
	
	// Set up the getter...
	public String getProcessedErrorPath() {
		return processedDataPath;
	}

	// and setter.
	public void setProcessedErrorPath(String processedErrorPath) {
		firePropertyChange("processedErrorPath", this.processedErrorPath, this.processedErrorPath = processedErrorPath);
	}
	
	
	// @OperationModelField annotations for the UI element creation
	// Get the internal filepath of the background thickness
	@OperationModelField(dataset = "filePath", label = "Detector image path", fieldPosition = 5)
	private String detectorDataPath = "/entry1/detector/data";
	
	// Set up the getter...
	public String getDetectorDataPath() {
		return detectorDataPath;
	}

	// and setter.
	public void setDetectorDataPath(String detectorDataPath) {
		firePropertyChange("detectorDataPath", this.detectorDataPath, this.detectorDataPath = detectorDataPath);
	}
	
	
	// @OperationModelField annotations for the UI element creation
	// Get the internal filepath of the sample thickness
	@OperationModelField(dataset = "filePath", label = "Scan time path", fieldPosition = 6)
	private String scanTimePath = "/entry1/detector/count_time";
	
	// Set up the getter...
	public String getScanTimePath() {
		return scanTimePath;
	}

	// and setter.
	public void setScanTimePath(String scanTimePath) {
		firePropertyChange("scanTimePath", this.scanTimePath, this.scanTimePath = scanTimePath);
	}


	// @OperationModelField annotations for the UI element creation
	// Get the internal filepath of the sample thickness
	@OperationModelField(dataset = "filePath", label = "Sample thickness path", fieldPosition = 7)
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
	@OperationModelField(dataset = "filePath", label = "I(0) path",fieldPosition = 8)
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
	@OperationModelField(dataset = "filePath", label = "I(t) path",fieldPosition = 9)
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
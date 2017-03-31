/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.operations;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.FileType;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

/**
 * Model for defining the detector for XPDF processing.
 * 
 * @author Timothy Spain, timothy.spain@diamond.ac.uk
 *
 */
public class XPDFDefineDetectorModel extends AbstractOperationModel {

	@OperationModelField(hint="Enter a name for the detector", label = "Detector Name")
	private String detectorName = "Perkin Elmer";
	@OperationModelField(hint="Enter the IUPAC formula detector material", label = "Detector Material")
	private String detectorMaterial = "CsI";
	@OperationModelField(hint="Enter the detector material density",label = "Detector Density", unit = "g cm⁻³" )
	private double density = 4.51;
	@OperationModelField(hint="Enter the detector thickness",label = "Detector Thickness", unit = "mm" )
	private double thickness = 0.5;
	@OperationModelField(hint="Enter the solid angle subtended by the detector",label = "Solid Angle", unit = "sr" )
	private double solidAngle = 0.1;
	@OperationModelField(hint="Detector angle. 0°:■ , ±45°:◆", label = "Detector Angle", unit="°")
	private double detectorAngle = 0.0;
	@OperationModelField(hint="Path to the calibration NeXus file", file = FileType.EXISTING_FILE, label = "Calibration File:")
	private String filePath = "";

	public String getDetectorName() {
		return detectorName;
	}
	public void setDetectorName(String detectorName) {
		firePropertyChange("detectorName", this.detectorName, this.detectorName = detectorName);
	}
	public String getDetectorMaterial() {
		return detectorMaterial;
	}
	public void setDetectorMaterial(String detectorMaterial) {
		firePropertyChange("detectorMaterial", this.detectorMaterial, this.detectorMaterial = detectorMaterial);
	}
	public double getDensity() {
		return density;
	}
	public void setDensity(double density) {
		firePropertyChange("density", this.density, this.density = density);
	}
	public double getThickness() {
		return thickness;
	}
	public void setThickness(double thickness) {
		firePropertyChange("thickness", this.thickness, this.thickness = thickness);
	}
	public double getSolidAngle() {
		return solidAngle;
	}
	public void setSolidAngle(double solidAngle) {
		firePropertyChange("solidAngle", this.solidAngle, this.solidAngle = solidAngle);
	}

	public double getDetectorAngle() {
		return this.detectorAngle;
	}
	public void setDetectorAngle(double detectorAngle) {
		double detentDetectorAngle = (Math.abs(detectorAngle) < 22.5) ? 0.0 : 45.0*Math.signum(detectorAngle);
		firePropertyChange("detectorAngle", this.detectorAngle, this.detectorAngle = detentDetectorAngle);
	}
	
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		firePropertyChange("filePath", this.filePath, this.filePath = filePath);
	}

}

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

public class XPDFInsertSampleMetadataModel extends AbstractOperationModel {

	
	// TODO: CHange defaults to real defaults, rather than ceria SRM defaults
	@OperationModelField(hint="Enter the path to the file containing the error values", file = FileType.EXISTING_FILE, label = "Data errors xy File")
	private String errorFilePath = "/scratch/dawn_diamond_ws/runtime-uk.ac.diamond.dawn.product/data/ceria_dean_data/CeO2/CeO2.error.xy";
	@OperationModelField(hint="Enter a counting time for the experiment",label = "Counting Time (s)" )
	private double countingTime = 240.0;
	@OperationModelField(hint="Enter the flux relative to the monitor",label = "Monitor Relative Flux" )
	private double monitorRelativeFlux = 1.0;
	@OperationModelField(hint="Enter an identifying name for the sample", label = "Sample Identifier")
	private String sampleName = "Ceria SRM";
	@OperationModelField(hint="Enter the IUPAC formula for the sample material", label = "Sample Material")
	private String material = "CeO2";
	@OperationModelField(hint="Enter the sample material density",label = "Sample Density (g/cm^3)" )
	private double density = 7.65;
	@OperationModelField(hint="Enter the sample packing fraction",label = "Sample Packing Fraction" )
	private double packingFraction = 0.6;
	@OperationModelField(hint="Enter the shape of the sample", label = "Sample Shape")
	private String shape = "cylinder";
	@OperationModelField(hint="Enter an inner distance for the sample shape",label = "Sample Inner Distance (mm)" )
	private double inner = 0.0;
	@OperationModelField(hint="Enter an outer distance for the sample shape",label = "Sample Outer Distance (mm)" )
	private double outer = 0.15;
	@OperationModelField(hint="Enter whether the x-axis is angle or momentum transfer",label = "Is x-axis 2θ?" )
	private boolean axisAngle = true;
	
	public String getErrorFilePath() {
		return errorFilePath;
	}

	public void setErrorFilePath(String errorFilePath) {
		firePropertyChange("errorFilePath", this.errorFilePath, this.errorFilePath = errorFilePath);
	}

	public double getCountingTime() {
		return countingTime;
	}

	public void setCountingTime(double countingTime) {
		firePropertyChange("countingTime", this.countingTime, this.countingTime = countingTime);
	}

	public double getMonitorRelativeFlux() {
		return monitorRelativeFlux;
	}

	public void setMonitorRelativeFlux(double monitorRelativeFlux) {
		firePropertyChange("monitorRelativeFlux", this.monitorRelativeFlux, this.monitorRelativeFlux = monitorRelativeFlux);
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		firePropertyChange("material", this.material, this.material = material);
	}

	public double getDensity() {
		return density;
	}

	public void setDensity(double density) {
		firePropertyChange("density", this.density, this.density = density);
	}

	public double getPackingFraction() {
		return packingFraction;
	}

	public void setPackingFraction(double packingFraction) {
		firePropertyChange("packingFraction", this.packingFraction, this.packingFraction = packingFraction);
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		firePropertyChange("shape", this.shape, this.shape = shape);
	}

	public double getInner() {
		return inner;
	}

	public void setInner(double inner) {
		firePropertyChange("inner", this.inner, this.inner = inner);
	}

	public double getOuter() {
		return outer;
	}

	public void setOuter(double outer) {
		firePropertyChange("outer", this.outer, this.outer = outer);
	}

	public String getSampleName() {
		return sampleName;
	}

	public void setSampleName(String sampleName) {
		firePropertyChange("sampleName", this.sampleName, this.sampleName = sampleName);
	}

	public boolean isAxisAngle() {
		return axisAngle;
	}

	public void setAxisAngle(boolean axisAngle) {
		firePropertyChange("axisAngle", this.axisAngle, this.axisAngle = axisAngle);
	}
	
}

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

import uk.ac.diamond.scisoft.xpdf.XPDFGeometryEnum;

public class XPDFInsertContainerMetadataModel extends AbstractOperationModel {

	
	@OperationModelField(hint="Enter the path to the file", file = FileType.EXISTING_FILE, label = "Data File")
	private String filePath = "/scratch/dawn_diamond_ws/runtime-uk.ac.diamond.dawn.product/data/ceriaXPDF/Empty_cap.xy";
	@OperationModelField(hint="Name of the dataset", dataset = "filePath", label = "Dataset")
	private String dataset = "";
	@OperationModelField(hint="Enter the path to the file containing the error values", file = FileType.EXISTING_FILE, label = "Errors File")
	private String errorFilePath = "";
	@OperationModelField(hint="Name of the dataset containing the errors", dataset = "errorFilePath", label = "Errors Dataset")
	private String errorDataset = "";
	@OperationModelField(hint="Enter a counting time for the experiment",label = "Counting Time", unit = "s" )
	private double countingTime = 60.0;
	@OperationModelField(hint="Enter the flux relative to the monitor",label = "Monitor Relative Flux" )
	private double monitorRelativeFlux = 1.0;
	@OperationModelField(hint="Enter an identifying name for the container", label = "Container Identifier")
	private String containerName = "SiO2 capillary";
	@OperationModelField(hint="Enter the IUPAC formula for the container material", label = "Container Material")
	private String material = "SiO2";
	@OperationModelField(hint="Enter the container material density",label = "Container Density", unit = "g/cm⁻³" )
	private double density = 2.65;
	@OperationModelField(hint="Enter the container packing fraction",label = "Container Packing Fraction" )
	private double packingFraction = 1.0;
	@OperationModelField(hint="Enter the shape of the container", label = "Container Shape")
	private XPDFGeometryEnum shape = XPDFGeometryEnum.CYLINDER;
	@OperationModelField(hint="Enter an inner distance for the container shape",label = "Container Inner Distance", unit = "mm" )
	private double inner = 0.5;
	@OperationModelField(hint="Enter an outer distance for the container shape",label = "Container Outer Distance", unit = "mm" )
	private double outer = 0.51;
	@OperationModelField(hint="Enter whether the container exists upstream of the container",label = "Container is Upstream?" )
	private boolean upstream = true;
	@OperationModelField(hint="Enter whether the container exists downstream of the container",label = "Container is Downstream?" )
	private boolean downstream = true;
	@OperationModelField(hint="Angle of the capillary. 0°:|, 90°:—", label = "Container Angle", unit = "°")
	private double containerAngle = 0.0;
	@OperationModelField(hint = "File to read simulated incoherent scattering flux data from ", label = "Incoherent Scattering File", file = FileType.EXISTING_FILE)
	private String incoherentScatteringPath = "";
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		firePropertyChange("filePath", this.filePath, this.filePath = filePath);
	}

	public String getDataset() {
		return dataset;
	}
	
	public void setDataset(String dataset) {
		firePropertyChange("errorDataset", this.dataset, this.dataset = dataset);
	}
	
	public String getErrorFilePath() {
		return errorFilePath;
	}

	public void setErrorFilePath(String errorFilePath) {
		firePropertyChange("errorFilePath", this.errorFilePath, this.errorFilePath = errorFilePath);
	}

	public String getErrorDataset() {
		return errorDataset;
	}
	
	public void setErrorDataset(String errorDataset) {
		firePropertyChange("errorDataset", this.errorDataset, this.errorDataset = errorDataset);
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

	public XPDFGeometryEnum getShape() {
		return shape;
	}

	public void setShape(XPDFGeometryEnum shape) {
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

	public String getContainerName() {
		return containerName;
	}

	public void setContainerName(String containerName) {
		firePropertyChange("containerName", this.containerName, this.containerName = containerName);
	}

	public boolean isUpstream() {
		return upstream;
	}

	public void setUpstream(boolean upstream) {
		firePropertyChange("upstream", this.upstream, this.upstream = upstream);
	}

	public boolean isDownstream() {
		return downstream;
	}

	public void setDownstream(boolean downstream) {
		firePropertyChange("downstream", this.downstream, this.downstream = downstream);
	}

	public double getContainerAngle() {
		return this.containerAngle;
	}
	public void setContainerAngle(double containerAngle) {
		double detentContainerAngle = (containerAngle < 45.0) ? 0.0 : 90.0;
		firePropertyChange("containerAngle", this.containerAngle, this.containerAngle = detentContainerAngle);
	}
	
	public String getIncoherentScatteringPath() {
		return this.incoherentScatteringPath;
	}
	
	public void setIncoherentScatteringPath(String incoherentScatteringPath) {
		firePropertyChange("incoherentScatteringPath", this.incoherentScatteringPath, this.incoherentScatteringPath = incoherentScatteringPath);
	}
}

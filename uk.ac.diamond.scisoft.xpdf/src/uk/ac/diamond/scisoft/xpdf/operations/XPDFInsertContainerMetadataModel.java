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

public class XPDFInsertContainerMetadataModel extends AbstractOperationModel {

	
	// TODO: CHange defaults to real defaults, rather than ceria SRM defaults
	@OperationModelField(hint="Enter the path to the file", file = FileType.EXISTING_FILE, label = "Container xy File")
	private String filePath = "/scratch/dawn_diamond_ws/runtime-uk.ac.diamond.dawn.product/data/ceriaXPDF/Empty_cap.xy";
	@OperationModelField(hint="Enter a counting time for the experiment",label = "Counting Time (s)" )
	private double countingTime = 240.0;
	@OperationModelField(hint="Enter the flux relative to the monitor",label = "Monitor Relative Flux" )
	private double monitorRelativeFlux = 1.0;
	@OperationModelField(hint="Enter an identifying name for the container", label = "Container Identifier")
	private String containerName = "SiO2 capillary";
	@OperationModelField(hint="Enter the IUPAC formula for the container material", label = "Container Material")
	private String material = "SiO2";
	@OperationModelField(hint="Enter the container material density",label = "Container Density (g/cm^3)" )
	private double density = 2.65;
	@OperationModelField(hint="Enter the container packing fraction",label = "Container Packing Fraction" )
	private double packingFraction = 1.0;
	@OperationModelField(hint="Enter the shape of the container", label = "Container Shape")
	private String shape = "cylinder";
	@OperationModelField(hint="Enter an inner distance for the container shape",label = "Container Inner Distance (mm)" )
	private double inner = 0.15;
	@OperationModelField(hint="Enter an outer distance for the container shape",label = "Container Outer Distance (mm)" )
	private double outer = 0.16;
	@OperationModelField(hint="Enter whether the container exists upstream of the container",label = "Container is Upstream?" )
	private boolean upstream = true;
	@OperationModelField(hint="Enter whether the container exists downstream of the container",label = "Container is Downstream?" )
	private boolean downstream = true;
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		firePropertyChange("filePath", this.filePath, this.filePath = filePath);
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

}

/*-
 * Copyright (c) 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


package uk.ac.diamond.scisoft.analysis.processing.operations.saxs;


// Imports from org.eclipse.dawnsci
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;
import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;


// @author Tim Snow


public class SelfAbsorptionCorrectionModel extends AbstractOperationModel {


	// First an enum to determine what geometry our input data is
	enum GeometryType {
		PLATE(1),
		CYLINDER(2);
		
		private final int geometry;
		
		GeometryType(int geometry) {
			this.geometry = geometry;
		}
		
		public int getGeometryType() {
			return this.geometry;
		}
		
		@Override
		public String toString() {
			switch (this.geometry) {
				case 1:		return String.format("Plate geometry");
				case 2:		return String.format(" ");
				default:	return String.format("Error!");
			}
		}
	}


	// Ask the user what the sample geometry is as this will affect the mathematics required for this correction
	@OperationModelField(label = "Sample geometry type", hint = "Plate or cylindrical sample type", fieldPosition = 1)
	private GeometryType geometry = GeometryType.PLATE;

	// Now the getters and setters
	public GeometryType getGeometry() {
		return geometry;
	}

	public void setGeometry(GeometryType geometry) {
		firePropertyChange("geometry", this.geometry, this.geometry = geometry);
	}


	// Get the internal filepath of the sample thickness
	@OperationModelField(dataset = "filePath", label = "Thickness path", fieldPosition = 2)
	private String thicknessPath = "/entry1/sample/thickness";
	
	// Set up the getter...
	public String getThicknessPath() {
		return thicknessPath;
	}

	// and setter.
	public void setThicknessPath(String thicknessPath) {
		firePropertyChange("thicknessPath", this.thicknessPath, this.thicknessPath = thicknessPath);
	}


	// Get the internal filepath of the incoming intensity
	@OperationModelField(dataset = "filePath", label = "I0 readout path", fieldPosition = 3)
	private String i0Path = "/entry1/I0/data";
	
	// Set up the getter...
	public String getI0Path() {
		return i0Path;
	}

	// and setter.
	public void setI0Path(String i0Path) {
		firePropertyChange("i0Path", this.i0Path, this.i0Path = i0Path);
	}


	// Get the internal filepath of the transmitted intensity
	@OperationModelField(dataset = "filePath", label = "It readout path", fieldPosition = 4)
	private String itPath = "/entry1/It/data";
	
	// Set up the getter...
	public String getItPath() {
		return itPath;
	}

	// and setter.
	public void setItPath(String itPath) {
		firePropertyChange("itPath", this.itPath, this.itPath = itPath);
	}
}
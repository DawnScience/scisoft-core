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

// Implements the self-absorption equation given in https://arxiv.org/pdf/1306.0637.pdf (Equation 29)


public class SelfAbsorptionCorrectionModel extends AbstractOperationModel {


	// First an enum to determine what geometry our input data is
	enum GeometryType {
		PLATE("Plate");
		
		private final String name;
		
		private GeometryType(String typeName) {
			name = typeName;
		}
		
		@Override
		public String toString() {
			return name;
		}
	}
	
	
	// Next, an enum to determine whether we have a transmission factor (lab sources) or I0 and It values (synchrotrons)
	enum CorrectionType {
		INTENSITIES("Diode intensities"),
		TRANSMISSION("Transmission factor");
		
		private final String name;
		
		private CorrectionType(String typeName) {
			name = typeName;
		}
		
		@Override
		public String toString() {
			return name;
		}
	}
	
	
	// Ask the user what the sample geometry is as this will affect the mathematics required for this correction
	@OperationModelField(label = "Sample geometry type", hint = "Plate or cylindrical sample type", fieldPosition = 1, visible = false)
	private GeometryType geometry = GeometryType.PLATE;

	// Now the getters and setters
	public GeometryType getGeometry() {
		return geometry;
	}

	public void setGeometry(GeometryType geometry) {
		firePropertyChange("geometry", this.geometry, this.geometry = geometry);
	}


	// Ask the user what values they have to correct the data against
	@OperationModelField(label = "Correction value format", hint = "Correct for self-absorption using an I0 and It value, or just a transmssion factor value", fieldPosition = 3)
	private CorrectionType correctionType = CorrectionType.INTENSITIES;
		
	// Now the getters and setters
	public CorrectionType getCorrectionType() {
		return correctionType;
	}

	public void setCorrectionType(CorrectionType correctionType) {
		firePropertyChange("CorrectionType", this.correctionType, this.correctionType = correctionType);
	}

	
	// Get the internal filepath of the incoming intensity
	@OperationModelField(dataset = "filePath", label = "I0 NeXus path", fieldPosition = 4, enableif = "correctionType == 'INTENSITIES'")
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
	@OperationModelField(dataset = "filePath", label = "It NeXus path", fieldPosition = 5, enableif = "correctionType == 'INTENSITIES'")
	private String itPath = "/entry1/It/data";
	
	// Set up the getter...
	public String getItPath() {
		return itPath;
	}

	// and setter.
	public void setItPath(String itPath) {
		firePropertyChange("itPath", this.itPath, this.itPath = itPath);
	}
	
	
	// Get the internal filepath of the incoming intensity
	@OperationModelField(dataset = "filePath", label = "Transmission factor NeXus path", fieldPosition = 6, enableif = "correctionType == 'TRANSMISSION'")
	private String transmissionPath = "/entry1/transmission/data";
	
	// Set up the getter...
	public String getTransmissionPath() {
		return transmissionPath;
	}

	// and setter.
	public void setTransmissionPath(String transmissionPath) {
		firePropertyChange("transmissionPath", this.transmissionPath, this.transmissionPath = transmissionPath);
	}
}
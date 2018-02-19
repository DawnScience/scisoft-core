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


//@author Tim Snow


public class UsaxsTwoThetaToQModel extends AbstractOperationModel {
	
	
	// Let's present to the user the choices on the kinds of transmission corrections they can do with this plugin
	enum YawUnits {
		RADIANS(1),
		MILLIRADIANS(2),
		MICRORADIANS(3),
		NANORADIANS(4);
		
		
		private final int yawUnits;
		
		
		YawUnits(int yawUnits) {
			this.yawUnits = yawUnits;
		}
		
		
		public int getYawUnits() {
			return this.yawUnits;
		}
		
		
		@Override
		public String toString() {
			switch (this.yawUnits) {
				case 1:		return String.format("Radians");
				case 2:		return String.format("Milliradians");
				case 3:		return String.format("Microradians");
				case 4:		return String.format("Nanoradians");
				default:	return String.format("Error!");
			}
		}
	}
	
	enum qUnits {
		ANGSTROMS(1),
		NANOMETERS(2);
		
		
		private final int qUnits;
		
		
		qUnits(int qUnits) {
			this.qUnits = qUnits;
		}
		
		
		public int getQUnits() {
			return this.qUnits;
		}
		
		
		@Override
		public String toString() {
			switch (this.qUnits) {
				case 1:		return String.format("Angstroms");
				case 2:		return String.format("Nanometers");
				default:	return String.format("Error!");
			}
		}
	}
	
	
	// Which units is yaw in?
	@OperationModelField(label = "What units is yaw in?", hint = "Pick the order of magnitude that the yaw motor was calibrated in", fieldPosition = 1)
	private YawUnits yawAccuracy = YawUnits.MILLIRADIANS;
	
	
	// Now the getters and setters
	public YawUnits getYawAccuracy() {
		return yawAccuracy;
	}
	
	
	public void setYawAccuracy(YawUnits yawAccuracy) {
		firePropertyChange("IntegrationRange", this.yawAccuracy, this.yawAccuracy = yawAccuracy);
	}
	
	
	// Which units does the user want q in?
	@OperationModelField(label = "What units should q be in?", hint = "Pick the units for q", fieldPosition = 2)
	private qUnits qScale = qUnits.ANGSTROMS;
	
	
	// Now the getters and setters
	public qUnits getQScale() {
		return qScale;
	}
	
	
	public void setQScale(qUnits qScale) {
		firePropertyChange("qScale", this.qScale, this.qScale = qScale);
	}
}
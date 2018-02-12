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


public class UsaxsYawToQModel extends AbstractOperationModel {
	
	
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
				case 2:		return String.format("Milli-radians");
				case 3:		return String.format("Micro-radians");
				case 4:		return String.format("Nano-radians");
				default:	return String.format("Error!");
			}
		}
	}
	
	
	// Which transmission correction does the user want to perform?
	@OperationModelField(label = "What units is yaw in?", hint = "Pick the order of magnitude that the yaw motor was calibrated in", fieldPosition = 1)
	private YawUnits yawAccuracy = YawUnits.MILLIRADIANS;
	
	
	// Now the getters and setters
	public YawUnits getYawAccuracy() {
		return yawAccuracy;
	}
	
	
	public void setYawAccuracy(YawUnits yawAccuracy) {
		firePropertyChange("IntegrationRange", this.yawAccuracy, this.yawAccuracy = yawAccuracy);
	}
}
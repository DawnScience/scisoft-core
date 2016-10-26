/*-
 * Copyright (c) 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;
import uk.ac.diamond.scisoft.analysis.processing.operations.IntegrationModel;
import org.eclipse.dawnsci.analysis.dataset.roi.RingROI;
import org.eclipse.dawnsci.analysis.api.roi.IROI;

// More information and the equation for the Herman Orientation Factor can be found in:
// Crystallization and orientation studies in polypropylene/single wall carbon nanotube composite
// A. R. Bhattacharyya, T. Sreekumar, T. Liu, S. Kumar, L. M. Ericson, R. H. Hauge and R. E. Smalley, Polymer, 2003, 44, 2373-2377.
// DOI: 10.1016/S0032-3861(03)00073-9 

// @author Tim Snow

// The model for a DAWN process to perform a Herman Orientation calculation on a given image
public class HermanOrientationModel extends IntegrationModel {


	// Let's give the user a fixed choice on the integration range so they don't go too nuts...
	enum NumberOfPis {
		HALF_PI(1),
		WHOLE_PI(2);
		
		private final int pis;
		
		NumberOfPis(int pis) {
			this.pis = pis;
//			if (piDescriptor == "Half Pi") {
//				this.pis = 1;
//			}
//			else if (piDescriptor == "Pi") {
//				this.pis = 2;
//			}
//			else {
//				this.pis = 2;
//			}
		}
		
		public int getNumberOfPis() {
			return this.pis;
		}
		
		@Override
		public String toString() {
			switch (this.pis) {
				case 1:		return String.format("Half Pi Radians");
				case 2:		return String.format("Pi Radians");
				default:	return String.format("Error!");
			}
		}
	}
	

	// Should we be integrating over a half or one Pi radians?
	@OperationModelField(label = "Integration Range", hint = "Integrate over half the ring (Half Pi) or the whole ring (Pi)", fieldPosition = 1)
	private NumberOfPis integrationRange = NumberOfPis.WHOLE_PI;

	// Now the getters and setters
	public NumberOfPis getIntegrationRange() {
		return integrationRange;
	}

	public void setIntegrationRange(NumberOfPis integrationRange) {
		firePropertyChange("IntegrationRange", this.integrationRange, this.integrationRange = integrationRange);
	}
	
	
	//@OperationModelField annotations for the UI setup
	// First the start angle of the integration N.B. 0 = North, going clockwise.
	@OperationModelField(label = "Start Angle", hint = "A value between zero and 180 degrees, where zero is north and increasing angle goes clockwise", fieldPosition = 2)
	private double integrationStartAngle = 135.00;

	// Now the getters and setters
	public double getIntegrationStartAngle() {
		return integrationStartAngle;
	}

	public void setIntegrationStartAngle(double integrationStartAngle) {
		firePropertyChange("IntegrationStartAngle", this.integrationStartAngle, this.integrationStartAngle = integrationStartAngle);
	}


	// Now let's get the user to tell us where the centre of the beam is and which ring they're interested in evaluating
	public HermanOrientationModel() {
		super();
		setRegion(new RingROI());
	}

	public HermanOrientationModel(IROI region) {
		super();
		//setRegion(sector);
		firePropertyChange("region", this.region, this.region = region);
	}

	
	// Finally, see if the user wants to use a non-standard C value for the calculation
	@OperationModelField(label = "Herman C Value", hint = "Leave this set to 1, unless you have a good reason. See DOI: 10.1016/S0032-3861(03)00073-9 for more information about this variable.", fieldPosition = 3)
	private double hermanCValue = 1.00;

	// Now the getters and setters
	public double getHermanCValue() {
		return hermanCValue;
	}

	public void setHermanCValue(double hermanCValue) {
		firePropertyChange("HermanCValue", this.hermanCValue, this.hermanCValue = hermanCValue);
	}

}
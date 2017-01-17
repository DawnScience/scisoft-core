/*-
 * Copyright (c) 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


package uk.ac.diamond.scisoft.analysis.processing.operations.saxs;


// Imports from org.eclipse.dawnsci
import org.eclipse.dawnsci.analysis.api.processing.model.RangeType;

import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;
import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;


// @author Tim Snow


// The operation to take a region of reduced SAXS data, obtain a Guinier plot and fit, as well as
// information that, ultimately, provides a radius of gyration
public class GuinierFittingModel extends AbstractOperationModel {

	
	// Let's give the user a fixed choice on the integration range so they don't go too nuts...
	enum SampleShape {
		PLAIN(1),
		SPHERE(2),
		ELLIPSE(3),
		CYLINDER(4);
		
		private final int sampleShape;
		
		SampleShape(int sampleShape) {
			this.sampleShape = sampleShape;
		}
		
		public int getSampleShape() {
			return this.sampleShape;
		}
		
		@Override
		public String toString() {
			switch (this.sampleShape) {
				case 1:		return String.format("Sphere");
				case 2:		return String.format("Ellipse");
				case 3:		return String.format("Cylinder");
				default:	return String.format("Error!");
			}
		}
	}
		
	
//	// Should we be evaluating for a sphere, ellipse or cylinder?
//	@OperationModelField(label = "Plot style", hint = "What is the expected shape of the molecule in solution?", fieldPosition = 1)
//	private SampleShape sampleShape = SampleShape.PLAIN;
//
//	// Now the getters and setters
//	public SampleShape getSampleShape() {
//		return sampleShape;
//	}
//
//	public void SetSampleShape(SampleShape sampleShape) {
//		firePropertyChange("SampleShape", this.sampleShape, this.sampleShape = sampleShape);
//	}

	// Get the region of interest for where to perform the Guinier analysis
	@OperationModelField(rangevalue = RangeType.XRANGE, label = "Plot region of interest", hint = "Two q values, start and end, separated by a comma, for example 0.02,0.2. The values should match the axis. If you delete the text the whole dataset will be evaluated", fieldPosition = 2)
	private double[] guinierRange = null;

	// Setting up the getters and setters along the way
	public double[] getGuinierRange() {
		return guinierRange;
	}
	public void setGuinierRange(double [] guinierRange) {
		firePropertyChange("guinierRange", this.guinierRange, this.guinierRange = guinierRange);
	}
	
//	//@OperationModelField annotations for the UI setup
//	// Value 'a' for an ellipse
//	@OperationModelField(label = "First ellipse axis", hint = "A value between zero and 180 degrees, where zero is north and increasing angle goes clockwise", fieldPosition = 3, enableif = "sampleShape==\"CYLINDER\"")
//	private double integrationStartAngle = 135.00;
//
//	// Now the getters and setters
//	public double getIntegrationStartAngle() {
//		return integrationStartAngle;
//	}
//
//	public void setIntegrationStartAngle(double integrationStartAngle) {
//		firePropertyChange("IntegrationStartAngle", this.integrationStartAngle, this.integrationStartAngle = integrationStartAngle);
//	}
//
//
//	//@OperationModelField annotations for the UI setup
//	// Value 'b' for an ellipse
//	@OperationModelField(label = "Second ellipse axis", hint = "A value between zero and 180 degrees, where zero is north and increasing angle goes clockwise", fieldPosition = 3, enableif = "sampleShape==\"CYLINDER\"")
//	private double integrationStartAngle = 135.00;
//
//	// Now the getters and setters
//	public double getIntegrationStartAngle() {
//		return integrationStartAngle;
//	}
//
//	public void setIntegrationStartAngle(double integrationStartAngle) {
//		firePropertyChange("IntegrationStartAngle", this.integrationStartAngle, this.integrationStartAngle = integrationStartAngle);
//	}
//
//
//	//@OperationModelField annotations for the UI setup
//	// Value 'h' for a cylinder
//	@OperationModelField(label = "Cylinder length", hint = "A value between zero and 180 degrees, where zero is north and increasing angle goes clockwise", fieldPosition = 3, enableif = "sampleShape==\"CYLINDER\"")
//	private double integrationStartAngle = 135.00;
//
//	// Now the getters and setters
//	public double getIntegrationStartAngle() {
//		return integrationStartAngle;
//	}
//
//	public void setIntegrationStartAngle(double integrationStartAngle) {
//		firePropertyChange("IntegrationStartAngle", this.integrationStartAngle, this.integrationStartAngle = integrationStartAngle);
//	}
}
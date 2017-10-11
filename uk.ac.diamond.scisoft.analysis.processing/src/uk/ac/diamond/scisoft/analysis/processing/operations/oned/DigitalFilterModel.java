/*
 * Copyright (c) 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


package uk.ac.diamond.scisoft.analysis.processing.operations.oned;


import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;


public class DigitalFilterModel extends AbstractOperationModel {

	// Let's give the user a choice as to whether they want to look at the narrow (peak) features, or broad (background) features
	enum FeatureFilter {
		NARROW(1),
		BROAD(2);
		
		private final int filterType;
		
		FeatureFilter(int filterType) {
			this.filterType = filterType;
		}
		
		public int getFilterType() {
			return this.filterType;
		}
		
		@Override
		public String toString() {
			switch (this.filterType) {
				case 1:		return String.format("Peaks");
				case 2:		return String.format("Background");
				default:	return String.format("Error!");
			}
		}
	}
	

	// The first entry in the model for this plug-in, what feature subset to focus on	
	@OperationModelField(label = "Features to highlight", hint = "Highlight either narrow, peaky, features or broad, background, features in the dataset", fieldPosition = 1)
	private FeatureFilter filterType = FeatureFilter.NARROW;

	// Now the getters and setters
	public FeatureFilter getFilterType() {
		return filterType;
	}

	public void setFilterType(FeatureFilter filterType) {
		firePropertyChange("IntegrationRange", this.filterType, this.filterType = filterType);
	}
	
	
	
	// The second entry in the model for this plug-in, the initial filter width
	@OperationModelField(min=1, hint="This number should be equal to the number of points of the average feature", label = "First filter width", fieldPosition = 2)
	private double firstFilterWidth = 0.00;

	public double getFirstFilterWidth() {
		return firstFilterWidth;
	}

	public void setFirstFilterWidth(double firstFilterWidth) {
		firePropertyChange("firstFilterWidth", this.firstFilterWidth, this.firstFilterWidth = firstFilterWidth);
	}
	
	
	// The third entry in the model for this plug-in, the secondary, narrower, filter width
	@OperationModelField(min=1, hint="This number should be equal to half the number of points of the average feature, or less", label = "Second filter width", fieldPosition = 3)
	private double secondFilterWidth = 0.00;

	public double getSecondFilterWidth() {
		return secondFilterWidth;
	}

	public void setSecondFilterWidth(double secondFilterWidth) {
		firePropertyChange("secondFilterWidth", this.secondFilterWidth, this.secondFilterWidth = secondFilterWidth);
	}
}
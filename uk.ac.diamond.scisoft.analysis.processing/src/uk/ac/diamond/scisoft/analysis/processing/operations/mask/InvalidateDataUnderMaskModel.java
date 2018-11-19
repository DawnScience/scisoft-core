/*-
 * Copyright (c) 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


package uk.ac.diamond.scisoft.analysis.processing.operations.mask;


// Imports from org.eclipse.dawnsci
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;
import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;


public class InvalidateDataUnderMaskModel extends AbstractOperationModel {


	// An enum to determine what invalidation strategy the user wants
	enum InvalidationType {
		ZEROS("Zeros"),
		NANS("NaNs");
		
		private final String invalidation;
		
		InvalidationType(String invalidation) {
			this.invalidation = invalidation;
		}
		
		@Override
		public String toString() {
			return invalidation;
		}
	}


	@OperationModelField(label = "Set value for invalid pixels", hint = "Invalid pixels can be either zero or NaN", fieldPosition = 1)
	private InvalidationType invalidation = InvalidationType.ZEROS;

	public InvalidationType getInvalidation() {
		return invalidation;
	}

	public void setInvalidation(InvalidationType invalidation) {
		firePropertyChange("invalidation", this.invalidation, this.invalidation = invalidation);
	}
}
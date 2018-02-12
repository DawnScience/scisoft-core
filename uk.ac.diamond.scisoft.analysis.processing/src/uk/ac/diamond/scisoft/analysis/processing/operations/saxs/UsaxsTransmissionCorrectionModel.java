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


public class UsaxsTransmissionCorrectionModel extends AbstractOperationModel {
	
	
	// Let's present to the user the choices on the kinds of transmission corrections they can do with this plugin
	enum CorrectionType {
		QZERO(1),
		INTEGRATION(2);
		
		private final int correctionType;
		
		CorrectionType(int correctionType) {
			this.correctionType = correctionType;
		}
		
		public int getCorrectionType() {
			return this.correctionType;
		}
		
		@Override
		public String toString() {
			switch (this.correctionType) {
				case 1:		return String.format("Q-zero value correction");
				case 2:		return String.format("Integral of dataset");
				default:	return String.format("Error!");
			}
		}
	}
	
	
	// Which transmission correction does the user want to perform?
	@OperationModelField(label = "Correction type", hint = "Correct by the intensity recorded at q-zero or as a function of all the radiation recorded", fieldPosition = 1)
	private CorrectionType typePicked = CorrectionType.QZERO;

	// Now the getters and setters
	public CorrectionType getTypePicked() {
		return typePicked;
	}

	public void setTypePicked(CorrectionType typePicked) {
		firePropertyChange("IntegrationRange", this.typePicked, this.typePicked = typePicked);
	}
}
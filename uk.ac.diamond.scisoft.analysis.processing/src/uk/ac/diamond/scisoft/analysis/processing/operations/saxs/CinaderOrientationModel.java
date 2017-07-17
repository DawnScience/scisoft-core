/*-
 * Copyright (c) 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.saxs;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;


// @author Tim Snow

// The model for a DAWN process to perform a Cinader & Burghardt orientation calculation on a given azimuthal xy dataset
public class CinaderOrientationModel extends AbstractOperationModel {


	// Let's give the user a fixed choice on the integration range so they don't go too nuts...
	enum NumberOfSymmetryFolds {
		TWO_FOLD(1),
		FOUR_FOLD(2),
		SIX_FOLD(3),
		EIGHT_FOLD(4);
		
		private final int foldsOfSymmetry;
		
		NumberOfSymmetryFolds(int foldsOfSymmetry) {
			this.foldsOfSymmetry = foldsOfSymmetry;
		}
		
		public int getFoldsOfSymmetry() {
			return this.foldsOfSymmetry;
		}
		
		@Override
		public String toString() {
			switch (this.foldsOfSymmetry) {
				case 1:		return String.format("Two fold");
				case 2:		return String.format("Four fold");
				case 3:		return String.format("Six fold");
				case 4:		return String.format("Eight fold");
				default:	return String.format("Error!");
			}
		}
	}
	
	
	// Should we be investigating two or four fold symmetry?
	@OperationModelField(label = "Number of symmetry folds", hint = "Does the system inspected show two or four fold symmtery?", fieldPosition = 0)
	private NumberOfSymmetryFolds foldsOfSymmetry = NumberOfSymmetryFolds.TWO_FOLD;

	// Now the getters...
	public NumberOfSymmetryFolds getFoldsOfSymmetry() {
		return foldsOfSymmetry;
	}

	//  and setters.
	public void setFoldsOfSymmetry(NumberOfSymmetryFolds foldsOfSymmetry) {
		firePropertyChange("foldsOfSymmetry", this.foldsOfSymmetry, this.foldsOfSymmetry = foldsOfSymmetry);
	}
}
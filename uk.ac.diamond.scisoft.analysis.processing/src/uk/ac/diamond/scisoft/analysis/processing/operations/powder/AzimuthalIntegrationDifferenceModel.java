/*-
 * Copyright (c) 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.powder;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;


public class AzimuthalIntegrationDifferenceModel extends AbstractOperationModel {
	
	
	enum OperationToPerform {
		SUBTRACT(1),
		DIVIDE(2),
		DIFFERENCE(3);
		
		
		private final int operationChoice;
		
		
		OperationToPerform(int operationChoice) {
			this.operationChoice = operationChoice;
		}
		
		
		public int getOperationToPerform() {
			return this.operationChoice;
		}
		
		
		@Override
		public String toString() {
			switch (this.operationChoice) {
				case 1:		return String.format("Subtract Frames (a-b)");
				case 2:		return String.format("Divide Frames (a/b)");
				case 3:		return String.format("Relative Difference of Frames ([a-b]/a)");
				default:	return String.format("Error!");
			}
		}
	}
	
	
	@OperationModelField(label = "Mathematical operator", hint = "Choose whether to subtract the reduced and remapped form of frame A from B or divide A from B", fieldPosition = 1)
	private OperationToPerform operationSelected = OperationToPerform.SUBTRACT;
	
	
	// Now the getters and setters
	public OperationToPerform getOperationSelected() {
		return operationSelected;
	}
	
	
	public void setOperationSelected (OperationToPerform operationSelected) {
		firePropertyChange("operationSelected", this.operationSelected, this.operationSelected = operationSelected);
	}
}
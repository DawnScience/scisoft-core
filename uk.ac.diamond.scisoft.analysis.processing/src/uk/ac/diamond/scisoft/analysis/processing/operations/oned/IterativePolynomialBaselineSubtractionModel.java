/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.oned;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class IterativePolynomialBaselineSubtractionModel extends
		AbstractOperationModel {
	
	@OperationModelField(label = "Polynomial Order", hint = "Polynomial orde to use for baseline")
	private int polynomialOrder = 5;
	@OperationModelField(label = "Number of Iterations", hint = "Number of iterations to use in fitting process")
	private int nIterations = 5;
	public int getPolynomialOrder() {
		return polynomialOrder;
	}
	public void setPolynomialOrder(int polynomialOrder) {
		firePropertyChange("polynomialOrder", this.polynomialOrder, this.polynomialOrder = polynomialOrder);
	}
	public int getnIterations() {
		return nIterations;
	}
	public void setnIterations(int nIterations) {
		firePropertyChange("nIterations", this.nIterations, this.nIterations = nIterations);
	}
	
	

}

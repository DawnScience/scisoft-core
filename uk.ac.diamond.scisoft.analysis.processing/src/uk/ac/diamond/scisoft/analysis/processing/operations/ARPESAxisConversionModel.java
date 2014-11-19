/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;

public class ARPESAxisConversionModel extends AbstractOperationModel {

	private double workFunction = 2.1;

	public double getWorkFunction() {
		return workFunction;
	}

	public void setWorkFunction(double workFunction) {
		this.workFunction = workFunction;
	}
	
}

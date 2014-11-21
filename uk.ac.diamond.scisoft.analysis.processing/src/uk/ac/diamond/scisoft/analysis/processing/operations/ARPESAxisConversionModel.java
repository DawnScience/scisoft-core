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
	private double kxOffset = 0.0;
	private double kyOffset = 0.0;

	public double getWorkFunction() {
		return workFunction;
	}

	public void setWorkFunction(double workFunction) {
		this.workFunction = workFunction;
	}

	public double getKxOffset() {
		return kxOffset;
	}

	public void setKxOffset(double kxOffset) {
		this.kxOffset = kxOffset;
	}

	public double getKyOffset() {
		return kyOffset;
	}

	public void setKyOffset(double kyOffset) {
		this.kyOffset = kyOffset;
	}
	
}

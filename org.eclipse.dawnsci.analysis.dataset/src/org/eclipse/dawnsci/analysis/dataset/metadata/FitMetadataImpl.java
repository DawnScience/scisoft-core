/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.dawnsci.analysis.dataset.metadata;

import org.eclipse.dawnsci.analysis.api.fitting.functions.IFunction;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;

import org.eclipse.dawnsci.analysis.api.metadata.FitMetadata;

public class FitMetadataImpl implements FitMetadata {

	private static final long serialVersionUID = 4010467322599961365L;

	private IFunction fitFunction;

	private Class<? extends IOperation<?, ?>> opClass;

	public FitMetadataImpl(Class<? extends IOperation<?, ?>> opClass) {
		this.opClass = opClass;
	}

	public FitMetadataImpl(FitMetadata fm) {
		opClass = fm.getOperationClass();
		try {
			fitFunction = fm.getFitFunction().copy();
		} catch (Exception e) {
		}
	}

	@Override
	public Class<? extends IOperation<?, ?>> getOperationClass() {
		return opClass;
	}

	@Override
	public FitMetadataImpl clone() {
		return new FitMetadataImpl(this);
	}

	@Override
	public IFunction getFitFunction() {
		return fitFunction;
	}

	/**
	 * 
	 * @param fitFunction
	 */
	public FitMetadata setFitFunction(IFunction fitFunction) {
		try {
			this.fitFunction = fitFunction.copy();
		} catch (Exception e) {
		}
		return this;
	}
}

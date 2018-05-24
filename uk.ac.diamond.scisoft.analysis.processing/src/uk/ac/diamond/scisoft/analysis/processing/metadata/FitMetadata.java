/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.metadata;

import org.eclipse.dawnsci.analysis.api.fitting.functions.IFunction;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.january.metadata.MetadataType;

/**
 * Used to store fit information
 */
public interface FitMetadata extends MetadataType {
	/**
	 * @return operation class that performed the fit
	 */
	Class<? extends IOperation<?, ?>> getOperationClass();

	IFunction getFitFunction();
}

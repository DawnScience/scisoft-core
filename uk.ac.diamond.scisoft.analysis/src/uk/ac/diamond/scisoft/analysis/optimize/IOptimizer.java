/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.optimize;

import java.io.Serializable;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IFunction;

/**
 * The interface for the optimizers
 */
public interface IOptimizer extends Serializable {

	/**
	 * The standard optimize function
	 * 
	 * @param coords
	 *            An array of datasets containing the coordinate positions
	 * @param data
	 *            A dataset containing the data to be fitted to
	 * @param function
	 *            A (possibly composite) function to fit
	 * @throws Exception 
	 */
	public void optimize(IDataset[] coords, IDataset data, IFunction function) throws Exception;
}

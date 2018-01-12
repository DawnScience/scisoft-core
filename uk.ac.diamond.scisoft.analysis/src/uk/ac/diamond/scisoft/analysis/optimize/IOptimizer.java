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

import org.eclipse.dawnsci.analysis.api.fitting.functions.IFunction;
import org.eclipse.january.dataset.IDataset;

/**
 * The interface for the optimizers
 */
public interface IOptimizer extends Serializable {

	/**
	 * Perform least squares optimization
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

	/**
	 * Perform optimization of parameters at given coordinates in function
	 * @param minimize if true then minimize function, else maximize it
	 * @param function
	 *            A (possibly composite) function to fit
	 * @param coords
	 * 
	 * @throws Exception 
	 */
	public void optimize(boolean minimize, IFunction function, double... coords) throws Exception;
}

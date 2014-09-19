/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import java.util.Iterator;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.IndexIterator;

/**
 * <p>Class to provide iteration through a set of coordinates
 * <p>Instantiate an iterator and use it in a while loop:
 * <pre>
 *  CoordinatesIterator iter = new HypergridIterator(values);
 *  double[] coords = iter.getCoordinates(); // this array's values change on each iteration
 *  while (iter.hasNext()) {
 *      value = someFunction(coords);
 *      ...
 *  }
 * </pre>
 */
public abstract class CoordinatesIterator implements Iterator<double[]> {
	protected IDataset[] values;
	protected IndexIterator it;
	protected int[] pos;
	protected double[] coords;
	protected int[] shape;

	@Override
	public double[] next() {
		return coords;
	}

	/**
	 * @return coordinates
	 */
	public double[] getCoordinates() {
		return coords;
	}

	/**
	 * @return shape of iterator (can be null, if not known or applicable)
	 */
	public int[] getShape() {
		return shape;
	}

	@Override
	public void remove() {
	}

	public IDataset[] getValues() {
		return values;
	}

	public void reset() {
		it.reset();
	}
}

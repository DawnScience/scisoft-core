/*-
 * Copyright (c) 2013 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import java.util.Arrays;
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

	/**
	 * Create a coordinates iterator from <tt>m</tt> independent coordinate datasets and
	 * an optional output dataset shape of <tt>n</tt> dimensions.
	 * <p>
	 * The simplest mode (<tt>m</tt>=1) requires a single dataset which can be compound. The output
	 * shape matches the input shape.
	 * <p>
	 * The next mode requires <tt>m</tt> nD datasets possessing the same shape and where each dataset
	 * specifies one of <tt>m</tt> coordinates. The output shape matches the input shape. An exception
	 * is made for matching 1D shapes where a hypergrid is used if no output shape is specified.
	 * <p>
	 * Most general mode has <tt>n = m</tt> and the coordinates evaluated on an nD hypergrid of
	 * flattened input datasets. Its output shape is determined by sizes of the input datasets.
	 *
	 * @param outShape output dataset shape (can be null)
	 * @param coords
	 * @return a coordinates iterator
	 */
	static final public CoordinatesIterator createIterator(int[] outShape, IDataset... coords) {
		if (coords == null || coords.length == 0) {
			throw new IllegalArgumentException("No coordinates given to evaluate function");
		}
	
		CoordinatesIterator it;
		int[] shape = coords[0].getShape();
		if (coords.length == 1) {
			it = coords[0].getElementsPerItem() == 1 ? new DatasetsIterator(coords) : new CoordinateDatasetIterator(coords[0]);
		} else {
			boolean same = true;
			for (int i = 1; i < coords.length; i++) {
				if (!Arrays.equals(shape, coords[i].getShape())) {
					same = false;
					break;
				}
			}
			if (same && shape.length == 1) { // may override for 1D datasets
				same = outShape == null ? false : Arrays.equals(outShape, shape);
			}
	
			it = same ? new DatasetsIterator(coords) : new HypergridIterator(coords);
		}

		if (outShape != null && !Arrays.equals(outShape, it.getShape())) {
			throw new IllegalArgumentException("Iterator created does not match given output shape");
		}
		return it;
	}
}

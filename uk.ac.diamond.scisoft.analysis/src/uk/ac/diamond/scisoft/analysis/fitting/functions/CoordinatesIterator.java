/*-
 * Copyright 2013 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import java.util.Iterator;

import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IndexIterator;

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

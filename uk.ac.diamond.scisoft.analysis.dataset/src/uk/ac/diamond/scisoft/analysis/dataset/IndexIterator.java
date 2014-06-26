/*
 * Copyright 2011 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.dataset;

/**
 * <p>Class to provide iteration through whole data array that backs a dataset</p>
 * <p>Instantiate an iterator and use it in a while loop:
 * <pre>
 *  DoubleDataset ds = (DoubleDataset) DatasetUtils.linSpace(0,10,0.25, Dataset.FLOAT64);
 *  IndexIterator iter = ds.getIterator();
 *  double[] data = ds.getData();
 *  
 *  while (iter.hasNext()) {
 *      data[iter.index] = 1.2;
 *  }
 * </pre>
 * 
 */
public abstract class IndexIterator {
	/**
	 * Index in array
	 */
	public int index;

	/**
	 * @return true if there is another iteration
	 */
	abstract public boolean hasNext();

	/**
	 * @return position indices (nb this is reference not a copy so avoid changing and can be null)
	 */
	abstract public int[] getPos();

	/**
	 * Reset iterator
	 */
	abstract public void reset();

	/**
	 * @return shape of iterator (can be null, if not known or applicable)
	 */
	public int[] getShape() {
		return null;
	}
}

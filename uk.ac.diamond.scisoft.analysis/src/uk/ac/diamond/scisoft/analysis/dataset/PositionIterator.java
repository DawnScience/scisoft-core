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
 * <p>Class to provide iteration through a dataset</p>
 * <p>Instantiate an iterator and use it in a while loop:
 * <pre>
 * AbstractDataset ds = DatasetUtils.linSpace(0,10,0.25, AbstractDataset.FLOAT64);
 * PositionIterator iter = ds.getPositionIterator();
 * int[] pos = iter.getPos()
 *
 * while (iter.hasNext()) {
 *     ds.set(1.2, pos);
 * }
 * </pre>
 *
 */
public class PositionIterator extends IndexIterator {
	final private int[] shape;
	final private int endrank;

	final private boolean[] omit; // axes to miss out

	/**
	 * position in dataset
	 */
	final private int[] pos;
	private boolean once;

	/**
	 * Constructor for an iterator over elements of a dataset that are within
	 * the shape
	 *
	 * @param shape
	 */
	public PositionIterator(int[] shape) {
		this(shape, -1);
	}

	/**
	 * Constructor for an iterator that misses out an axis
	 * @param shape
	 * @param axis missing axis (or -1 for full dataset)
	 */
	public PositionIterator(int[] shape, int axis) {
		this(shape, new int[] {axis});
	}

	/**
	 * Constructor for an iterator that misses out several axes
	 * @param shape
	 * @param axes missing axes
	 */
	public PositionIterator(int[] shape, int[] axes) {
		this.shape = shape;
		endrank = shape.length - 1;
		pos = new int[endrank + 1];

		omit = new boolean[endrank + 1];
		for (int a : axes) {
			if (a >= 0 && a <= endrank) {
				omit[a] = true;
			} else if (a > endrank) {
				throw new IllegalArgumentException("Specified axis exceeds dataset rank");
			}
		}

		reset();
	}

	@Override
	public boolean hasNext() {
		// now move on one position
		if (once) {
			once = false;
			return true;
		}
		int j = endrank;
		for (; j >= 0; j--) {
			if (omit[j]) {
				continue;
			}
			pos[j]++;
			if (pos[j] >= shape[j]) {
				pos[j] = 0;
			} else {
				return true;
			}
		}
		return false;
	}

	@Override
	public int[] getPos() {
		return pos;
	}

	/**
	 * @return omit array - array where true means miss out
	 */
	public boolean[] getOmit() {
		return omit;
	}

	@Override
	public void reset() {
		int j = 0;
		for (; j <= endrank; j++) {
			if (!omit[j])
				break;
		}
		if (j > endrank) {
			once = true;
			return;
		}

		if (omit[endrank]) {
			pos[endrank] = 0;
			
			for (int i = endrank - 1; i >= 0; i--) {
				if (!omit[i]) {
					pos[i]--;
					break;
				}
			}
		} else {
			pos[endrank] = -1;
		}
	}
}

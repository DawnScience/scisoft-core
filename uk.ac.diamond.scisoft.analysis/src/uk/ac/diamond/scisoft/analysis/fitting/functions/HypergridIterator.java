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

import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.PositionIterator;

/**
 * An iterator over a hypergrid whose points have coordinates given by the
 * values in the datasets
 */
public class HypergridIterator extends CoordinatesIterator {

	private int endrank;

	/**
	 * @param values 
	 */
	public HypergridIterator(IDataset... values) {
		endrank = values.length - 1;
		this.values = new IDataset[endrank + 1];
		shape = new int[endrank + 1];

		for (int i = 0; i <= endrank; i++) {
			IDataset v = values[i];
			int size = v.getSize();
			shape[i] = size;
			if (v.getRank() != 1) {
				this.values[i] = DatasetUtils.convertToDataset(v).reshape(size);
			} else {
				this.values[i] = values[i];
			}
		}

		coords = new double[endrank + 1];
		it = new PositionIterator(shape);
		pos = it.getPos();
	}

	@Override
	public boolean hasNext() {
		if (!it.hasNext())
			return false;

		if (pos[endrank] == 0) {
			for (int i = 0; i <= endrank; i++) {
				coords[i] = values[i].getDouble(pos[i]);
			}
		} else {
			coords[endrank] = values[endrank].getDouble(pos[endrank]);
		}
		return true;
	}
}

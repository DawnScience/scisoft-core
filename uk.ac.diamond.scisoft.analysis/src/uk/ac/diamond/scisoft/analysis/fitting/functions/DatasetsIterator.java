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

import java.util.Arrays;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.PositionIterator;

/**
 * An iterator over the common input shape whose points have coordinates given by the
 * values in the datasets
 */
public class DatasetsIterator extends CoordinatesIterator {
	private int rank;

	/**
	 * All these datasets must have the same shape
	 * @param values
	 */
	public DatasetsIterator(IDataset... values) {
		rank = values.length;
		this.values = values;
		shape = values[0].getShape();
		for (int i = 1; i < rank; i++) {
			IDataset v = values[i];
			if (!Arrays.equals(shape, v.getShape())) {
				throw new IllegalArgumentException("All shapes must be the same");
			}
		}

		coords = new double[rank];
		it = new PositionIterator(shape);
		pos = it.getPos();
	}

	@Override
	public boolean hasNext() {
		if (!it.hasNext())
			return false;

		for (int i = 0; i < rank; i++) {
			coords[i] = values[i].getDouble(pos);
		}
		return true;
	}
}

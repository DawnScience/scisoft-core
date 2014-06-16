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

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.CompoundDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.PositionIterator;

/**
 * An iterator over a dataset whose items are coordinates
 */
public class CoordinateDatasetIterator extends CoordinatesIterator {
	private int rank;
	CompoundDataset cvalue;

	/**
	 * A single, possibly compound, dataset
	 * @param value
	 */
	public CoordinateDatasetIterator(IDataset value) {
		if (!(value instanceof CompoundDataset)) {
			int dtype = AbstractDataset.getBestDType(Dataset.ARRAYINT8,
					AbstractDataset.getDTypeFromClass(value.elementClass()));
			cvalue = (CompoundDataset) DatasetUtils.cast(value, dtype);
		} else {
			cvalue = (CompoundDataset) value;
		}
		rank = cvalue.getRank();
		shape = cvalue.getShape();

		coords = new double[rank];
		values = new IDataset[] { cvalue };
		it = new PositionIterator(shape);
		pos = it.getPos();
	}

	@Override
	public boolean hasNext() {
		if (!it.hasNext())
			return false;

		for (int i = 0; i < rank; i++) {
			cvalue.getDoubleArray(coords, pos);
		}
		return true;
	}
}

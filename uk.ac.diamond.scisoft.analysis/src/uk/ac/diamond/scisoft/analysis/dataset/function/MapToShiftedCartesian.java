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

package uk.ac.diamond.scisoft.analysis.dataset.function;


import java.util.ArrayList;
import java.util.List;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Maths;

/**
 * Map a 2D dataset from Cartesian to Cartesian coordinates and return that remapped dataset
 */
public class MapToShiftedCartesian implements DatasetToDatasetFunction {
	double ox0, ox1;

	/**
	 * Set up mapping of rotated 2D dataset
	 * 
	 * @param x0 shift in first dimension
	 * @param x1 shift in second dimension
	 */
	public MapToShiftedCartesian(double x0, double x1) {
		ox0 = x0;
		ox1 = x1;
	}

	/**
	 * The class implements mapping of a Cartesian grid sampled data (pixels) to another Cartesian grid
	 * 
	 * @param datasets
	 *            input 2D dataset
	 * @return a shifted 2D dataset
	 */
	@Override
	public List<AbstractDataset> value(IDataset... datasets) {
		if (datasets.length == 0)
			return null;

		List<AbstractDataset> result = new ArrayList<AbstractDataset>();

		for (IDataset ids : datasets) {
			AbstractDataset ds = DatasetUtils.convertToAbstractDataset(ids);
			// check if input is 2D
			int[] s = ds.getShape();
			if (s.length != 2)
				return null;

			AbstractDataset newmap = AbstractDataset.zeros(s, ds.getDtype());

			double cx0, cx1;
			for (int x0 = 0; x0 < s[0]; x0++) {
				cx0 = x0 - ox0;
				for (int x1 = 0; x1 < s[1]; x1++) {
					cx1 = x1 - ox1;
					newmap.set(Maths.interpolate(ds, cx0, cx1), x0, x1);
				}
			}
			result.add(newmap);
		}
		return result;
	}
}

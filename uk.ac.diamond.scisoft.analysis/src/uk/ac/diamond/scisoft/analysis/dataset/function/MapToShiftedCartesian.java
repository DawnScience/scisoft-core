/*
 * Copyright © 2011 Diamond Light Source Ltd.
 * Contact :  ScientificSoftware@diamond.ac.uk
 * 
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 * 
 * This software is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this software. If not, see <http://www.gnu.org/licenses/>.
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
					newmap.set(Maths.getBilinear(ds, cx0, cx1), x0, x1);
				}
			}
			result.add(newmap);
		}
		return result;
	}
}

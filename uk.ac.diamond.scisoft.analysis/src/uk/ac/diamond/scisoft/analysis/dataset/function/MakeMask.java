/*-
 * Copyright © 2009 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.dataset.function;

import java.util.ArrayList;
import java.util.List;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.BooleanDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IndexIterator;

/**
 * Make a mask given lower and upper threshold values
 */
public class MakeMask implements DatasetToDatasetFunction {
	private double lower, upper; // threshold values

	/**
	 * Create function object with given threshold values
	 * @param low
	 * @param high
	 */
	public MakeMask(double low, double high) {
		lower = low;
		upper = high;
	}
	
	@Override
	public List<AbstractDataset> value(IDataset... datasets) {
		if (datasets.length == 0)
			return null;

		ArrayList<AbstractDataset> result = new ArrayList<AbstractDataset>();
		for (IDataset d : datasets) {
			final AbstractDataset ds = DatasetUtils.convertToAbstractDataset(d);
			final BooleanDataset bs = new BooleanDataset(ds.getShape());
			final IndexIterator it = ds.getIterator();
			int i = 0;
			while (it.hasNext()) {
				double v = ds.getElementDoubleAbs(it.index);
				bs.setAbs(i++, v >= lower && v <= upper);
			}
			result.add(bs);
		}

		return result;
	}
}

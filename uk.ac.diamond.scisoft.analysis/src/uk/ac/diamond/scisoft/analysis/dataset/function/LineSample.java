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
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Maths;

/**
 * Sample along line and return list of one 1D dataset
 *
 */
public class LineSample implements DatasetToDatasetFunction {
	int sx, sy, ex, ey;
	double step;

	/**
	 * Set up line sampling
	 * 
	 * @param sx
	 * @param sy
	 * @param ex
	 * @param ey
	 * @param step 
	 */
	public LineSample(int sx, int sy, int ex, int ey, double step) {
		this.sx = sx;
		this.sy = sy;
		this.ex = ex;
		this.ey = ey;
		this.step = step;
	}

	/**
	 * The class implements pointwise sampling along a given line
	 * 
	 * @param datasets input 2D datasets
	 * @return one 1D dataset
	 */
	@Override
	public List<AbstractDataset> value(IDataset... datasets) {
		if (datasets.length == 0)
			return null;

		List<AbstractDataset> result = new ArrayList<AbstractDataset>();

		for (IDataset ids : datasets) {
			if (ids.getRank() != 2)
				return null;

			double rad = Math.hypot(ex - sx, ey - sy);
			double phi = Math.atan2(ey - sy, ex - sx);

			int nr = ((int) Math.floor(rad / step)) + 1;

			AbstractDataset linsample = AbstractDataset.zeros(new int[] { nr },
					AbstractDataset.getBestFloatDType(ids.elementClass()));

			double x, y;
			for (int i = 0; i < nr; i++) {
				final double r = step * i;
				x = sx + r * Math.cos(phi);
				y = sy + r * Math.sin(phi);
				linsample.setObjectAbs(i, Maths.getBilinear(ids, y, x));
			}

			result.add(linsample);
		}
		return result;
	}
}

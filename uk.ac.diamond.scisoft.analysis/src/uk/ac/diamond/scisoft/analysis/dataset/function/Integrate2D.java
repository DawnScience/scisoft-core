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
import java.util.Arrays;
import java.util.List;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractCompoundDataset;
import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;

/**
 * Integrate 2D dataset and return list of two 1D datasets for individual sums over the two dimensions
 *
 */
public class Integrate2D implements DatasetToDatasetFunction {
	int sx, sy, ex, ey;
	boolean full = false;

	/**
	 * Set up integration over whole 2D dataset
	 * 
	 */
	public Integrate2D() {
		full = true;
	}

	/**
	 * Set up integration over rectangular subset of 2D dataset
	 * 
	 * @param sx
	 * @param sy
	 * @param ex
	 * @param ey
	 */
	public Integrate2D(int sx, int sy, int ex, int ey) {
		this.sx = sx;
		this.sy = sy;
		this.ex = ex;
		this.ey = ey;
	}

	/**
	 * The class implements integrations along each axis of 2D dataset 
	 * 
	 * @param datasets input 2D datasets (only first used)
	 * @return two 1D datasets which are sums over x and y
	 */
	@Override
	public List<AbstractDataset> value(IDataset... datasets) {
		if (datasets.length == 0)
			return null;

		List<AbstractDataset> result = new ArrayList<AbstractDataset>();

		for (IDataset ids : datasets) {
			int[] shape = ids.getShape();
			if (shape.length != 2)
				return null;

			if (full) {
				sx = 0;
				sy = 0;
				ex = shape[1] - 1;
				ey = shape[0] - 1;
			} else {
				if (sx < 0)
					sx = 0;
				if (sx >= shape[1])
					sx = shape[1] - 1;
				if (ex < 0)
					ex = 0;
				if (ex >= shape[1])
					ex = shape[1] - 1;
				if (sy < 0)
					sy = 0;
				if (sy >= shape[0])
					sy = shape[0] - 1;
				if (ey < 0)
					ey = 0;
				if (ey >= shape[0])
					ey = shape[0] - 1;
			}
			int nx = ex - sx + 1;
			int ny = ey - sy + 1;
			if (nx == 0)
				nx = 1;
			if (ny == 0)
				ny = 1;

			final int dtype = AbstractDataset.getBestFloatDType(ids.elementClass());
			final int is = ids.getElementsPerItem();
			AbstractDataset sumy = AbstractDataset.zeros(is, new int[] { nx }, dtype);
			AbstractDataset sumx = AbstractDataset.zeros(is, new int[] { ny }, dtype);

			if (is == 1) {
				double csum;
				for (int b = 0; b < ny; b++) {
					csum = 0.0;
					for (int a = 0; a < nx; a++) {
						final double v = ids.getDouble(b + sy, a + sx);
						csum += v;
						sumy.set(v + sumy.getDouble(a), a);
					}
					sumx.set(csum, b);
				}
			} else {
				final double[] csums = new double[is];
				final double[] xvalues = new double[is];
				final double[] yvalues = new double[is];
				for (int b = 0; b < ny; b++) {
					Arrays.fill(csums, 0.);
					for (int a = 0; a < nx; a++) {
						((AbstractCompoundDataset) ids).getDoubleArray(xvalues, b + sy, a + sx);
						((AbstractCompoundDataset) sumy).getDoubleArray(yvalues, a);
						for (int j = 0; j < is; j++) {
							csums[j] += xvalues[j];
							yvalues[j] += xvalues[j];
						}
						sumy.set(yvalues, a);
					}
					sumx.set(csums, b);
				}
			}
			result.add(sumx);
			result.add(sumy);
		}
		return result;
	}
}

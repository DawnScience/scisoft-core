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
import java.util.Arrays;
import java.util.List;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.CompoundDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetFactory;
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
	public List<Dataset> value(IDataset... datasets) {
		if (datasets.length == 0)
			return null;

		List<Dataset> result = new ArrayList<Dataset>();

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
			Dataset sumy = DatasetFactory.zeros(is, new int[] { nx }, dtype);
			Dataset sumx = DatasetFactory.zeros(is, new int[] { ny }, dtype);

			if (is == 1) {
				double csum;
				for (int b = 0; b < ny; b++) {
					csum = 0.0;
					for (int a = 0; a < nx; a++) {
						final double v = ids.getDouble(b + sy, a + sx);
						if (Double.isNaN(v)) continue; 
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
						((CompoundDataset) ids).getDoubleArray(xvalues, b + sy, a + sx);
						((CompoundDataset) sumy).getDoubleArray(yvalues, a);
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

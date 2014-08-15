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
import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetFactory;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Maths;

/**
 * Sample along line and return list of one 1D dataset
 * 
 * When mapping from image to data, the array value is assumed to be
 * associated with the centre of the pixel, i.e. pixel co-od - 0.5 = data co-ord.
 * 
 * This half pixel offset is handled in this class so does not need to be accounted for else where.
 *
 */
public class LineSample implements DatasetToDatasetFunction {
	double sx, sy, ex, ey;
	double step;
	private double rad;
	private double sp;
	private double cp;

	/**
	 * Set up line sampling
	 * 
	 * @param sx
	 * @param sy
	 * @param ex
	 * @param ey
	 * @param step 
	 */
	public LineSample(double sx, double sy, double ex, double ey, double step) {
		this.sx = sx - 0.5;
		this.sy = sy - 0.5;
		this.ex = ex - 0.5;
		this.ey = ey - 0.5;
		this.step = step;
		rad = Math.hypot(ex - sx, ey - sy);
		double phi = Math.atan2(ey - sy, ex - sx);
		cp = Math.cos(phi);
		sp = Math.sin(phi);
	}

	/**
	 * @param i
	 * @return position of indexed point on line
	 */
	public int[] getPoint(int i) {
		final double r = step * i;
		return new int[] {(int) (sy + r * sp), (int) (sx + r * cp)};
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
			if (ids == null || ids.getRank() != 2)
				return null;

			int nr = ((int) Math.floor(rad / step)) + 1;

			Dataset linsample = DatasetFactory.zeros(new int[] { nr },
					AbstractDataset.getBestFloatDType(ids.elementClass()));

			double x, y;
			for (int i = 0; i < nr; i++) {
				final double r = step * i;
				x = sx + r * cp;
				y = sy + r * sp;
				linsample.setObjectAbs(i, Maths.interpolate(ids, y, x));
			}

			result.add((AbstractDataset) linsample);
		}
		return result;
	}
}

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
import uk.ac.diamond.scisoft.analysis.roi.RectangularROI;

/**
 * Map and integrate a 2D dataset from Cartesian to rotated Cartesian coordinates, sum over each axis
 * and return those sums and the corresponding sums for an unclipped unit version (for clipping compensation)
 */
public class MapToRotatedCartesianAndIntegrate implements DatasetToDatasetFunction {
	int ox, oy;
	int h, w;
	double phi;

	/**
	 * Set up mapping of rotated 2D dataset
	 * 
	 * @param x
	 *            top left x (in pixel coordinates)
	 * @param y
	 *            top left y
	 * @param width size along major axis
	 * @param height size along minor axis
	 * @param angle in degrees
	 *            (clockwise from positive x axis)
	 */
	public MapToRotatedCartesianAndIntegrate(int x, int y, int width, int height, double angle) {
		ox = x;
		oy = y;
		h = height;
		w = width;
		phi = Math.toRadians(angle);
	}

	/**
	 * @param x
	 * @param y
	 * @param width size along major axis
	 * @param height size along minor axis
	 * @param angle
	 * @param isDegrees
	 */
	public MapToRotatedCartesianAndIntegrate(int x, int y, int width, int height, double angle, boolean isDegrees) {
		ox = x;
		oy = y;
		h = height;
		w = width;
		if (isDegrees) {
			phi = Math.toRadians(angle);
		} else {
			phi = angle;	
		}
	}

	/**
	 * @param roi
	 */
	public MapToRotatedCartesianAndIntegrate(RectangularROI roi) {
		int[] pt = roi.getIntPoint();
		int[] len = roi.getIntLengths();
		ox = pt[0];
		oy = pt[1];
		w = len[0];
		h = len[1];
		phi = roi.getAngle();
	}

	/**
	 * The class implements mapping of a Cartesian grid sampled data (pixels) to another Cartesian grid
	 * 
	 * @param datasets
	 *            input 2D dataset
	 * @return two pairs of 1D dataset for column (or minor axis) and row (or major axis) integration of
	 * a rotated dataset (second pair is for unclipped unit version of input dataset)  
	 */
	@Override
	public List<AbstractDataset> value(IDataset... datasets) {
		if (datasets.length == 0)
			return null;

		List<AbstractDataset> result = new ArrayList<AbstractDataset>();

		for (IDataset ids : datasets) {
			if (ids.getRank() != 2)
				return null;

			// work out cosine and sine
			double cp = Math.cos(phi);
			double sp = Math.sin(phi);

			final int dtype = AbstractDataset.getBestFloatDType(ids.elementClass());
			AbstractDataset sumx = AbstractDataset.zeros(new int[] { h }, dtype);
			AbstractDataset sumy = AbstractDataset.zeros(new int[] { w }, dtype);

			double cx, cy;
			double csum;
			for (int y = 0; y < h; y++) {
				csum = 0.0;
				for (int x = 0; x < w; x++) {
					// back transformation from tilted to original coordinates
					cx = ox + x * cp - y * sp;
					cy = oy + x * sp + y * cp;

					final double v = Maths.getBilinear(ids, cy, cx);
					sumy.set(v + sumy.getDouble(x), x);
					csum += v;
				}
				sumx.set(csum, y);
			}

			result.add(sumx);
			result.add(sumy);
			AbstractDataset usumx = AbstractDataset.zeros(new int[] { h }, dtype).fill(w);
			result.add(usumx);
			AbstractDataset usumy = AbstractDataset.zeros(new int[] { w }, dtype).fill(h);
			result.add(usumy);
		}
		return result;
	}
}

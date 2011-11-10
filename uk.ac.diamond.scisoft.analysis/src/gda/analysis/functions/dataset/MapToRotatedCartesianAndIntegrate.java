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

package gda.analysis.functions.dataset;

import gda.analysis.DataSet;
import gda.analysis.utils.DatasetMaths;

import java.util.ArrayList;
import java.util.List;

/**
 * Map a 2D dataset from Cartesian to rotated Cartesian coordinates, sum over each axis
 * and return those sums and the corresponding sums for an unclipped unit version (for clipping compensation)
 */
public class MapToRotatedCartesianAndIntegrate implements IDataSetFunction {
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
	 * @param width
	 * @param height
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
	 * @param width
	 * @param height
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
	 * The class implements mapping of a Cartesian grid sampled data (pixels) to another Cartesian grid
	 * 
	 * @param inDS
	 *            input 2D dataset
	 * @return two pairs of 1D dataset where first in pair is a row sum (sum along each row) and the second is a column sum
	 */
	@Override
	public List<DataSet> execute(DataSet inDS) {
		// check if input is 2D
		int[] dims = inDS.getDimensions();
		if (dims.length != 2)
			return null;

		// work out cosine and sine
		double cp = Math.cos(phi);
		double sp = Math.sin(phi);

		DataSet sumx = new DataSet(h);
		DataSet sumy = new DataSet(w);

		double cx, cy;
		double csum;
		for (int y = 0; y < h; y++) {
			csum = 0.0;
			for (int x = 0; x < w; x++) {
				// back transformation from tilted to original coordinates
				cx = ox + x * cp - y * sp;
				cy = oy + x * sp + y * cp;

				final double v = DatasetMaths.getBilinear(inDS, cy, cx);
				sumy.set(v + sumy.getDouble(x), x);
				csum += v;
			}
			sumx.set(csum, y);
		}

		List<DataSet> result = new ArrayList<DataSet>();
		result.add(sumx);
		result.add(sumy);

		DataSet usumx = new DataSet(h).fill(w);
		result.add(usumx);
		DataSet usumy = new DataSet(w).fill(h);
		result.add(usumy);
		return result;
	}
}

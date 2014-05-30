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
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Maths;
import uk.ac.diamond.scisoft.analysis.roi.IRectangularROI;

/**
 * Map a 2D dataset from Cartesian to rotated Cartesian coordinates and return that remapped dataset from a rotated
 * rectangle return a dataset and an unclipped unit version (for clipping compensation)
 */
public class MapToRotatedCartesian implements DatasetToDatasetFunction {
	int ox, oy;
	int h, w;
	private double cp;
	private double sp;

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
	public MapToRotatedCartesian(int x, int y, int width, int height, double angle) {
		ox = x;
		oy = y;
		h = height;
		w = width;
		double phi = Math.toRadians(angle);
		cp = Math.cos(phi);
		sp = Math.sin(phi);
	}

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param angle
	 * @param isDegrees
	 */
	public MapToRotatedCartesian(int x, int y, int width, int height, double angle, boolean isDegrees) {
		this(x, y, width, height, isDegrees ? Math.toRadians(angle) : angle);
	}

	/**
	 * @param roi
	 */
	public MapToRotatedCartesian(IRectangularROI roi) {
		this((int) roi.getPointX(), (int) roi.getPointY(), (int) roi.getLength(0), (int) roi.getLength(1), roi.getAngle());
	}

	/**
	 * @param i
	 * @param j
	 * @return position of indexed point on grid
	 */
	public int[] getPoint(int i, int j) {
		return new int[] {(int) (oy + j * sp + i * cp), (int) (ox + j * cp - i * sp)};
	}

	/**
	 * @param pos
	 * @return position of point on grid
	 */
	public int[] getPoint(int[] pos) {
		return getPoint(pos[0], pos[1]);
	}

	/**
	 * The class implements mapping of a Cartesian grid sampled data (pixels) to another Cartesian grid
	 * 
	 * @param datasets
	 *            input 2D dataset
	 * @return two 2D dataset where rows label tilted rows and columns label tilted columns
	 */
	@Override
	public List<AbstractDataset> value(IDataset... datasets) {
		if (datasets.length == 0)
			return null;

		List<AbstractDataset> result = new ArrayList<AbstractDataset>();

		for (IDataset ids : datasets) {
			if (ids.getRank() != 2)
				return null;

			int[] os = new int[] { h, w };

			// work out cosine and sine

			AbstractDataset newmap = AbstractDataset.zeros(os, AbstractDataset.getBestFloatDType(ids.elementClass()));
			AbstractDataset unitmap = AbstractDataset.zeros(newmap);

			double cx, cy;
			for (int y = 0; y < h; y++) {
				for (int x = 0; x < w; x++) {
					unitmap.set(1.0, y, x);
					// back transformation from tilted to original coordinates
					cx = ox + x * cp - y * sp;
					cy = oy + x * sp + y * cp;

					newmap.set(Maths.interpolate(ids, cy, cx), y, x);
				}
			}

			result.add(newmap);
			result.add(unitmap);
		}
		return result;
	}
}

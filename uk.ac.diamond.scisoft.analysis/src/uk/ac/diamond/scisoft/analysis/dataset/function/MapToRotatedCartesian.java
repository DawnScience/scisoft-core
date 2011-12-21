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
import uk.ac.diamond.scisoft.analysis.roi.RectangularROI;

/**
 * Map a 2D dataset from Cartesian to rotated Cartesian coordinates and return that remapped dataset from a rotated
 * rectangle return a dataset and an unclipped unit version (for clipping compensation)
 */
public class MapToRotatedCartesian implements DatasetToDatasetFunction {
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
	public MapToRotatedCartesian(int x, int y, int width, int height, double angle) {
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
	public MapToRotatedCartesian(int x, int y, int width, int height, double angle, boolean isDegrees) {
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
	public MapToRotatedCartesian(RectangularROI roi) {
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
			double cp = Math.cos(phi);
			double sp = Math.sin(phi);

			AbstractDataset newmap = AbstractDataset.zeros(os, AbstractDataset.getBestFloatDType(ids.elementClass()));
			AbstractDataset unitmap = AbstractDataset.zeros(newmap);

			double cx, cy;
			for (int y = 0; y < h; y++) {
				for (int x = 0; x < w; x++) {
					unitmap.set(1.0, y, x);
					// back transformation from tilted to original coordinates
					cx = ox + x * cp - y * sp;
					cy = oy + x * sp + y * cp;

					newmap.set(Maths.getBilinear(ids, cy, cx), y, x);
				}
			}

			result.add(newmap);
			result.add(unitmap);
		}
		return result;
	}
}

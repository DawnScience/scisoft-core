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
import uk.ac.diamond.scisoft.analysis.roi.RectangularROI;

/**
 * Map and integrate a 2D dataset from Cartesian to rotated Cartesian coordinates, sum over each axis
 * and return those sums and the corresponding sums for an unclipped unit version (for clipping compensation)
 */
public class MapToRotatedCartesianAndIntegrate implements DatasetToDatasetFunction {
	int ox, oy;
	int h, w;
	double phi;
	private double cp;
	private double sp;

	Dataset mask;

	/**
	 * Set detector mask used profile calculations
	 *  
	 * @param mask
	 */
	public void setMask(Dataset mask) {
		this.mask = mask;
	}

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

		// work out cosine and sine
		cp = Math.cos(phi);
		sp = Math.sin(phi);
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

		// work out cosine and sine
		cp = Math.cos(phi);
		sp = Math.sin(phi);
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
		// work out cosine and sine
		cp = Math.cos(phi);
		sp = Math.sin(phi);

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
	public List<Dataset> value(IDataset... datasets) {
		if (datasets.length == 0)
			return null;

		List<Dataset> result = new ArrayList<Dataset>();

		double msk = 1;
		for (IDataset ids : datasets) {
			if (ids.getRank() != 2)
				return null;

			final int dtype = AbstractDataset.getBestFloatDType(ids.elementClass());
			Dataset sumx = DatasetFactory.zeros(new int[] { h }, dtype);
			Dataset sumy = DatasetFactory.zeros(new int[] { w }, dtype);
			Dataset usumx = DatasetFactory.zeros(new int[] { h }, dtype);
			Dataset usumy = DatasetFactory.zeros(new int[] { w }, dtype);

			double cx, cy;
			double csum;
			double cusumx;
			for (int y = 0; y < h; y++) {
				csum = 0.0;
				cusumx = 0.0;
				for (int x = 0; x < w; x++) {
					// back transformation from tilted to original coordinates
					cx = ox + x * cp - y * sp;
					cy = oy + x * sp + y * cp;

					if (mask != null)
						msk = Maths.interpolate(mask, y, x);
					final double v = Maths.interpolate(ids, mask, cy, cx);
					sumy.set(v + sumy.getDouble(x), x);
					usumy.set(msk + usumy.getDouble(x), x);
					csum += v;
					cusumx += msk;
				}
				sumx.set(csum, y);
				usumx.set(cusumx, y);
			}

			result.add(sumx);
			result.add(sumy);
			result.add(usumx);
			result.add(usumy);
		}
		return result;
	}

	/**
	 * 
	 * @param datasets
	 * @return maximum value in 
	 */
	public List<Dataset> maxValue(IDataset... datasets) {
		if (datasets.length == 0)
			return null;

		List<Dataset> result = new ArrayList<Dataset>();

		for (IDataset ids : datasets) {
			if (ids.getRank() != 2)
				return null;

			final int dtype = AbstractDataset.getBestFloatDType(ids.elementClass());
			Dataset mx = DatasetFactory.zeros(new int[] { h }, dtype);
			Dataset my = DatasetFactory.zeros(new int[] { w }, dtype);

			double cx, cy;
			double cmax;
			for (int y = 0; y < h; y++) {
				cmax = Double.NEGATIVE_INFINITY;
				for (int x = 0; x < w; x++) {
					// back transformation from tilted to original coordinates
					cx = ox + x * cp - y * sp;
					cy = oy + x * sp + y * cp;

					final double v = Maths.interpolate(ids, mask, cy, cx);
					my.set(Math.max(v, my.getDouble(x)), x);
					if (v > cmax)
						cmax = v;
				}
				mx.set(cmax, y);
			}

			result.add(mx);
			result.add(my);
		}
		return result;
	}
}

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

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.AbstractDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
import org.eclipse.dawnsci.analysis.dataset.impl.function.DatasetToDatasetFunction;

/**
 * Map a 2D dataset from Cartesian to Polar coordinates and return that remapped dataset
 * and an unclipped unit version (for clipping compensation) 
 * 
 * Cartesian coordinate system is x from left to right and y from top to bottom on the display
 * so corresponding polar coordinate is radius from centre and azimuthal angle clockwise from positive x axis
 */
public class MapToPolar implements DatasetToDatasetFunction {
	double cx, cy;
	double srad, sphi, erad, ephi;

	/**
	 * Set up mapping of annular sector of 2D dataset
	 * 
	 * @param x
	 *            centre x
	 * @param y
	 *            centre y
	 * @param sr
	 *            start radius
	 * @param sp
	 *            start phi in degrees
	 * @param er
	 *            end radius
	 * @param ep
	 *            end phi in degrees
	 */
	public MapToPolar(double x, double y, double sr, double sp, double er, double ep) {
		this(x, y, sr, sp, er, ep, true);
	}

	/**
	 * @param x
	 * @param y
	 * @param sr
	 * @param sp
	 * @param er
	 * @param ep
	 * @param isDegrees
	 */
	public MapToPolar(double x, double y, double sr, double sp, double er, double ep, boolean isDegrees) {
		cx = x;
		cy = y;

		srad = sr;
		erad = er;

		if (isDegrees) {
			sphi = Math.toRadians(sp);
			ephi = Math.toRadians(ep);
		} else {
			sphi = sp;
			ephi = ep;
		}

		if (sphi > ephi) {
			double tphi = sphi;
			sphi = ephi;
			ephi = tphi;
		}
	}

	/**
	 * The class implements mapping of a Cartesian grid sampled data (pixels) to polar grid
	 * 
	 * @param datasets
	 *            input 2D dataset
	 * @return two 2D dataset where rows label radii and columns label azimuthal angles
	 */
	@Override
	public List<Dataset> value(IDataset... datasets) {
		if (datasets.length == 0)
			return null;

		List<Dataset> result = new ArrayList<Dataset>();

		for (IDataset ids : datasets) {
			if (ids.getRank() != 2)
				throw new IllegalArgumentException("operating on 2d arrays only");

			// work out azimuthal resolution as roughly equal to pixel at outer radius
			double dphi = 1.0 / erad;
			int nr = (int) Math.ceil(erad - srad);
			int np = (int) Math.ceil((ephi - sphi) / dphi);
			if (nr == 0)
				nr = 1;
			if (np == 0)
				np = 1;

			Dataset polarmap = DatasetFactory.zeros(new int[] { nr, np },
					AbstractDataset.getBestFloatDType(ids.elementClass()));
			Dataset unitpolarmap = DatasetFactory.zeros(polarmap); // unclipped polar of unit field

			double rad, phi;
			double x, y;
			for (int r = 0; r < nr; r++) {
				rad = srad + r;
				for (int p = 0; p < np; p++) {
					unitpolarmap.set(dphi * rad, r, p);
					phi = sphi + p * dphi;
					x = cx + rad * Math.cos(phi);
					y = cy + rad * Math.sin(phi);
					polarmap.set(dphi * rad * Maths.interpolate(ids, y, x), r, p);
				}
			}

			result.add(polarmap);
			result.add(unitpolarmap);
		}
		return result;
	}
}

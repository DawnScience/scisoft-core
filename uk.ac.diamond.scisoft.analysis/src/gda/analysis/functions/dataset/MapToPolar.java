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
 * Map a 2D dataset from Cartesian to Polar coordinates and return that remapped dataset
 * and an unclipped unit version (for clipping compensation) 
 * 
 * Cartesian coordinate system is x from left to right and y from top to bottom on the display
 * so corresponding polar coordinate is radius from centre and azimuthal angle clockwise from positive x axis
 */
public class MapToPolar implements IDataSetFunction {
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
	 * @param inDS
	 *            input 2D dataset
	 * @return two 2D dataset where rows label radii and columns label azimuthal angles
	 */
	@Override
	public List<DataSet> execute(DataSet inDS) {
		// check if input is 2D
		int[] dims = inDS.getDimensions();
		if (dims.length != 2)
			throw new IllegalArgumentException("operating on 2d arrays only");

		// work out azimuthal resolution as roughly equal to pixel at outer radius
		double dphi = 1.0 / erad;
		int nr = (int) Math.ceil(erad - srad);
		int np = (int) Math.ceil((ephi - sphi) / dphi);
		if (nr == 0)
			nr = 1;
		if (np == 0)
			np = 1;

		DataSet polarmap = new DataSet(nr, np);
		DataSet unitpolarmap = new DataSet(nr, np); // unclipped polar of unit field

		double rad, phi;
		double x, y;
		for (int r = 0; r < nr; r++) {
			rad = srad + r;
			final double rdphi = dphi * rad;
			for (int p = 0; p < np; p++) {
				unitpolarmap.set(rdphi, r, p);
				phi = sphi + p * dphi;
				x = cx + rad * Math.cos(phi);
				y = cy + rad * Math.sin(phi);
				polarmap.set(rdphi * DatasetMaths.getBilinear(inDS, y, x), r, p);
			}
		}

		ArrayList<DataSet> result = new ArrayList<DataSet>();
		result.add(polarmap);
		result.add(unitpolarmap);
		return result;
	}
}

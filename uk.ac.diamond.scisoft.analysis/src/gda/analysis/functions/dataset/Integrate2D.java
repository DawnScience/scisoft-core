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

import java.util.ArrayList;
import java.util.List;

/**
 * Integrate 2D dataset and return list of two 1D datasets for individual sums over the two dimensions
 *
 */
public class Integrate2D implements IDataSetFunction {
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
	 * @param inDS input 2D dataset
	 * @return two 1D datasets which are sums over x and y
	 */
	@Override
	public List<DataSet> execute(DataSet inDS) {
		// check if input is 2D
		int[] dims = inDS.getDimensions();
		if (dims.length != 2) return null;
		
		if (full) {
			sx = 0;
			sy = 0;
			ex = dims[1]-1;
			ey = dims[0]-1;
		}

		if (sx < 0) sx = 0;
		if (sx >= dims[1]) sx = dims[1] - 1;
		if (ex < 0) ex = 0;
		if (ex >= dims[1]) ex = dims[1] - 1;
		if (sy < 0) sy = 0;
		if (sy >= dims[0]) sy = dims[0] - 1;
		if (ey < 0) ey = 0;
		if (ey >= dims[0]) ey = dims[0] - 1;

		int nx = ex - sx + 1;
		int ny = ey - sy + 1;
		if (nx == 0) nx = 1;
		if (ny == 0) ny = 1;

		DataSet sumy = new DataSet(nx);
		DataSet sumx = new DataSet(ny);
		final double[] dsy = sumy.getBuffer();
		final double[] dsx = sumx.getBuffer();
		double csum;
		for (int b=0; b<ny; b++) {
			csum = 0.0;
			for (int a=0; a<nx; a++) {
				final double v = inDS.get(b+sy, a+sx);
				csum += v;
				dsy[a] += v;
			}
			dsx[b] = csum;
		}
		
		List<DataSet> result = new ArrayList<DataSet>();
		result.add(sumx);
		result.add(sumy);
		return result;
	}
}

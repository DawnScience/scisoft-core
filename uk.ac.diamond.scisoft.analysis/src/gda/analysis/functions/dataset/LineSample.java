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
 * Sample along line and return list of one 1D dataset
 *
 */
public class LineSample implements IDataSetFunction {
	int sx, sy, ex, ey;
	double step;

	/**
	 * Set up line sampling
	 * 
	 * @param sx
	 * @param sy
	 * @param ex
	 * @param ey
	 * @param step 
	 */
	public LineSample(int sx, int sy, int ex, int ey, double step) {
		this.sx = sx;
		this.sy = sy;
		this.ex = ex;
		this.ey = ey;
		this.step = step;
	}

	/**
	 * The class implements pointwise sampling along a given line
	 * 
	 * @param inDS input 2D dataset
	 * @return one 1D dataset
	 */
	@Override
	public List<DataSet> execute(DataSet inDS) {
		// check if input is 2D
		int[] dims = inDS.getDimensions();
		if (dims.length != 2) return null;
		
		double rad = Math.hypot(ex-sx, ey-sy);
		double phi = Math.atan2(ey-sy, ex-sx);

		int nr = ((int) Math.floor(rad/step))+1;

		DataSet linsample = new DataSet(nr);

		double x, y;
		for (int r=0; r<nr; r++) {
			x = sx + step*r*Math.cos(phi);
			y = sy + step*r*Math.sin(phi);
			linsample.set(DatasetMaths.getBilinear(inDS, y, x), r);
		}

		ArrayList<DataSet> result = new ArrayList<DataSet>();
		result.add(linsample);
		return result;
	}

}

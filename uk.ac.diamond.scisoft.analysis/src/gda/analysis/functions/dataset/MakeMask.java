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

import uk.ac.diamond.scisoft.analysis.dataset.IndexIterator;

/**
 * Make a mask given lower and upper threshold values
 */
public class MakeMask implements IDataSetFunction {
	private double lower, upper; // threshold values

	/**
	 * Create function object with given threshold values
	 * @param low
	 * @param high
	 */
	public MakeMask(double low, double high) {
		lower = low;
		upper = high;
	}
	
	@Override
	public List<DataSet> execute(DataSet callingDataSet) {
		double[] data = callingDataSet.getBuffer();
		DataSet mask = new DataSet(callingDataSet.getDimensions());
		double[] mdata = mask.getBuffer();
		IndexIterator iter = callingDataSet.getIterator();
		for (int i = 0; iter.hasNext(); i++) {
			if (data[iter.index] >= lower && data[iter.index] <= upper)
				mdata[i] = 1.;
		}
		ArrayList<DataSet> result = new ArrayList<DataSet>();
		result.add(mask);
		return result;
	}
}

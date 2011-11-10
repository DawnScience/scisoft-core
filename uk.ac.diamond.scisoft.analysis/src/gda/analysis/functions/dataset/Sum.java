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
 * Example Code which shows how a function should be written to obey the IDataSetFunction interface.
 *
 */
public class Sum implements IDataSetFunction {

	/**
	 * The class that needs implementing
	 * @param callingDataSet 
	 * @return list of datasets
	 */
	@Override
	public List<DataSet> execute(DataSet callingDataSet) {
		double[] data = {callingDataSet.sum()};
		ArrayList<DataSet> result = new ArrayList<DataSet>();
		result.add(new DataSet(data));
		return result;
	}

}

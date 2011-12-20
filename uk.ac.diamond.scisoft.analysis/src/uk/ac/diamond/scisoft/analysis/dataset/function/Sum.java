/*
 * Copyright © 2011 Diamond Light Source Ltd.
 * Contact :  ScientificSoftware@diamond.ac.uk
 * 
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 * 
 * This software is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this software. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.dataset.function;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math.complex.Complex;

import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;

/**
 * Example of finding the totals in an array of datasets
 */
public class Sum implements DatasetToNumberFunction {

	@Override
	public List<Number> value(IDataset... datasets) {
		if (datasets.length == 0)
			return null;

		List<Number> result = new ArrayList<Number>();
		for (IDataset d : datasets) {
			Object value = DatasetUtils.convertToAbstractDataset(d).sum();
			if (value instanceof Complex) {
				result.add(((Complex) value).getReal());
				result.add(((Complex) value).getImaginary());
			} else if (value instanceof Number)
				result.add((Number) value);
			else {
				throw new IllegalArgumentException("Type of return value from sum not supported");
			}
		}
		return result;
	}

}

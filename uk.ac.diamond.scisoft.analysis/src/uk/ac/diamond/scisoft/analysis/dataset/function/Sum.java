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

import org.apache.commons.math3.complex.Complex;

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

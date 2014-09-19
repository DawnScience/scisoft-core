/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.dataset.function;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.BooleanDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.IndexIterator;
import org.eclipse.dawnsci.analysis.dataset.impl.function.DatasetToDatasetFunction;

/**
 * Make a mask given lower and upper threshold values
 */
public class MakeMask implements DatasetToDatasetFunction {
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
	public List<Dataset> value(IDataset... datasets) {
		if (datasets.length == 0)
			return null;

		List<Dataset> result = new ArrayList<Dataset>();
		for (IDataset d : datasets) {
			final Dataset ds = DatasetUtils.convertToDataset(d);
			final BooleanDataset bs = new BooleanDataset(ds.getShape());
			final IndexIterator it = ds.getIterator();
			int i = 0;
			while (it.hasNext()) {
				double v = ds.getElementDoubleAbs(it.index);
				bs.setAbs(i++, v >= lower && v <= upper);
			}
			result.add(bs);
		}

		return result;
	}
}

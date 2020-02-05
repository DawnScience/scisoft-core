/*-
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.dataset.function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.dawnsci.analysis.dataset.impl.function.DatasetToDatasetFunction;
import org.eclipse.january.dataset.BroadcastIterator;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.InterfaceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Count number of occurrences of each value in a dataset of non-negative integers
 */
public class BinCount implements DatasetToDatasetFunction {
	private static final Logger logger = LoggerFactory.getLogger(BinCount.class);
	private Dataset weights;
	private int minLength;

	public BinCount() {
	}

	/**
	 * Set minimum length of output count datasets
	 * @param minLength
	 */
	public void setMinimumLength(int minLength) {
		this.minLength = minLength;
	}

	/**
	 * Set weights to use to add to count
	 * <p>
	 * Input dataset shapes must match weights shape otherwise null is given
	 * @param weights
	 */
	public void setWeights(IDataset weights) {
		this.weights = DatasetUtils.convertToDataset(weights);
	}

	/**
	 * @param datasets input datasets
	 * @return a list of 1D datasets (or nulls if corresponding input is not integer or does not match weights dataset's shape
	 */
	@Override
	public List<Dataset> value(IDataset... datasets) {
		List<Dataset> result = new ArrayList<Dataset>();
		if (datasets.length == 0) {
			return result;
		}

		int[] wShape = weights == null ? null : weights.getShapeRef();
		for (IDataset ids : datasets) {
			result.add(count(DatasetUtils.convertToDataset(ids), wShape));
		}

		return result;
	}

	private Dataset count(Dataset d, int[] wShape) {
		if (!InterfaceUtils.isInteger(d.getClass())) {
			logger.error("Dataset '{}' is not integer", d.getName());
			return null;
		}
		if (wShape != null && !Arrays.equals(d.getShapeRef(), wShape)) {
			logger.error("Dataset '{}' shape {} does not match weights' shape {}", d.getName(), Arrays.toString(d.getShapeRef()), Arrays.toString(wShape));
			return null;
		}

		int min = d.min(true).intValue();
		if (min < 0) {
			logger.error("Dataset '{}' has negative values", d.getName());
			return null;
		}
		int max = d.max(true).intValue();
		int[] shape = new int[] {Math.max(minLength, max + 1)};

		if (wShape == null) {
			IntegerDataset icount = DatasetFactory.zeros(IntegerDataset.class, shape);
			final IndexIterator it = d.getIterator();
			while (it.hasNext()) {
				int i = (int) d.getElementLongAbs(it.index);
				icount.setAbs(i, icount.getAbs(i) + 1);
			}

			return icount;
		}

		Dataset count = DatasetFactory.zeros(InterfaceUtils.getLargestInterface(weights), shape);
		final BroadcastIterator it = BroadcastIterator.createIterator(d, weights);
		if (it.isOutputDouble()) {
			while (it.hasNext()) {
				int i = (int) it.aDouble;
				count.setObjectAbs(i, count.getElementDoubleAbs(i) + it.bDouble);
			}
		} else {
			while (it.hasNext()) {
				int i = (int) it.aLong;
				count.setObjectAbs(i, count.getElementLongAbs(i) + it.bLong);
			}
		}

		return count;
	}
}

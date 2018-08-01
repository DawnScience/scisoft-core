/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.dataset.function;

import java.text.MessageFormat;

import org.eclipse.dawnsci.analysis.dataset.impl.function.DatasetToDatasetFunction;
import org.eclipse.january.dataset.Comparisons;
import org.eclipse.january.dataset.Comparisons.Monotonicity;
import org.slf4j.Logger;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;

/**
 * Starting block for histogram classes
 */
abstract class HistogramBase implements DatasetToDatasetFunction {

	/**
	 * Holds details about bin edges in a dimension
	 */
	static class BinEdges {
		DoubleDataset origEdges;
		DoubleDataset edges;
		boolean isIncreasing = true;
		boolean hasEqualSpans = true;
		double f = Double.NaN; // multiplier
		double l = Double.NaN; // lowest edge
		double h = Double.NaN; // value just less than highest edge
		int lastBin = 0; // of count dataset

		public BinEdges(IDataset edges) {
			if (edges.getRank() != 1) {
				throw new IllegalArgumentException("Bin edges must be given as 1D dataset");
			} else if (edges.getSize() < 2) {
				throw new IllegalArgumentException("There must be more than one bin edge");
			}

			DoubleDataset b = DatasetUtils.cast(DoubleDataset.class, edges);

			Monotonicity mono = Comparisons.findMonotonicity(b);
			if (mono != Monotonicity.STRICTLY_INCREASING && mono != Monotonicity.STRICTLY_DECREASING) {
				throw new IllegalArgumentException("Bin edges must be strictly increasing or strictly decreasing");
			}

			origEdges = this.edges = b;
			isIncreasing = mono == Monotonicity.STRICTLY_INCREASING;
			if (!isIncreasing) {
				b = this.edges = DatasetUtils.sort(this.edges);
			}

			lastBin = b.getSize() - 2;
			l = b.get();
			h = Math.nextDown(b.get(lastBin + 1));
			if (b.getSize() == 2) {
				hasEqualSpans = true;
				f = 1. / (h - l);
			} else {
				Dataset d = Maths.difference(b, 1, 0);
				hasEqualSpans = Comparisons.allTrue(Comparisons.almostEqualTo(d, d.getDouble(), 1e-8, 1e-8));
				if (hasEqualSpans) {
					f = 1./d.getDouble();
				}
			}
		}

		public BinEdges(int numBins, double lower, double upper) {
			if (lower > upper) {
				throw new IllegalArgumentException("Given lower bound must not be greater than upper bound");
			}

			origEdges = edges = DatasetFactory.createLinearSpace(DoubleDataset.class, lower, upper, numBins + 1);
			l = lower;
			h = Math.nextDown(upper);
			f = numBins / (upper - lower);
			lastBin = numBins - 1;
		}
	}

	static void throwIAException(Logger l, String message, Object... objs) {
		String m = MessageFormat.format(message, objs);
		l.error(m);
		throw new IllegalArgumentException(m);
	}

	protected boolean ignoreOutliers = true;
	protected Dataset weights;

	/**
	 * Set histogram's outliers handling
	 * @param b if true, then ignore values that lie outside minimum and maximum bin edges
	 */
	public void setIgnoreOutliers(boolean b) {
		ignoreOutliers = b;
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
}

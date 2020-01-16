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
import java.util.Arrays;
import java.util.List;

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
 * Find histogram of each dataset and return pairs of 1D integer dataset of bin counts
 * and 1D double dataset of bin edges (including rightmost edge).
 * <p>
 * By default, outliers are ignored.
 */
public class Histogram extends HistogramBase {
	private static final Logger logger = LoggerFactory.getLogger(Histogram.class);

	private int nbins;
	
	private BinEdges binEdges;

	/**
	 * Constructor of the Histogram
	 * @param numBins number of bins
	 */
	public Histogram(int numBins) {
		nbins = numBins;
	}

	/**
	 * Constructor of the Histogram
	 * @param numBins number of bins
	 * @param lower minimum value of histogram range
	 * @param upper maximum value of histogram range
	 */
	public Histogram(int numBins, double lower, double upper) {
		this(numBins);

		binEdges = new BinEdges(numBins, lower, upper);
	}

	/**
	 * Constructor of the Histogram
	 * @param numBins number of bins
	 * @param lower minimum value of histogram range
	 * @param upper maximum value of histogram range
	 * @param ignore if true, outliers will be ignored
	 */
	public Histogram(int numBins, double lower, double upper, boolean ignore) {
		this(numBins, lower, upper);
		ignoreOutliers = ignore;
	}

	/**
	 * Constructor of the Histogram, ignoring outliers
	 * @param edges bin edges including rightmost edge
	 */
	public Histogram(IDataset edges) {
		this(edges, true);
	}

	/**
	 * Constructor of the Histogram
	 * @param edges bin edges including rightmost edge
	 * @param ignore if true, outliers will be ignored
	 */
	public Histogram(IDataset edges, boolean ignore) {
		binEdges = new BinEdges(edges);

		nbins = edges.getSize() - 1;
		ignoreOutliers = ignore;
	}

	/**
	 * Set minimum and maximum edges of histogram bins
	 * @param min
	 * @param max
	 */
	public void setMinMax(double min, double max) {
		binEdges = new BinEdges(nbins, min, max);
	}

	/**
	 * @param datasets input datasets
	 * @return a list of 1D datasets which are histograms and bins
	 */
	@Override
	public List<Dataset> value(IDataset... datasets) {
		List<Dataset> result = new ArrayList<Dataset>();
		if (datasets.length == 0) {
			return result;
		}

		if (binEdges == null || binEdges.hasEqualSpans) {
			for (IDataset ids : datasets) {
				Dataset ds = DatasetUtils.convertToDataset(ids);
				BinEdges be = binEdges;
				if (be == null) {
					be = new BinEdges(nbins, ds.min(true).doubleValue(), ds.max(true).doubleValue());
				}
				final double[] edges = be.edges.getData();
				final double lo = be.l;
				final double hi = be.h;

				final int[] cShape = new int[] { nbins };
				final Dataset count;
				final int[] cData;
				final Dataset w = weights;
				if (w == null) {
					IntegerDataset iCount = DatasetFactory.zeros(IntegerDataset.class, cShape);
					cData = iCount.getData();
					count = iCount;
				} else {
					cData = null;
					count = DatasetFactory.zeros(InterfaceUtils.getLargestInterface(w), cShape);
				}
				if (lo >= hi) {
					count.set(w == null ? ids.getSize() : w.sum(true), be.isIncreasing ? 0 : be.lastBin);
					result.add(count);
					result.add(be.origEdges);
					continue;
				}
				final double f = be.f;

				final BroadcastIterator bit = w == null ? null : BroadcastIterator.createIterator(ds, w);
				if (bit != null) {
					bit.setOutputDouble(true);
				}
				final IndexIterator it = bit == null ? ds.getIterator() : bit;

				while (it.hasNext()) {
					final double val = bit == null ? ds.getElementDoubleAbs(it.index) : bit.aDouble;
					final int p;
					if (val < lo) {
						if (ignoreOutliers) {
							continue;
						}
						p = 0;
					} else if (val >= hi) {
						if (val > edges[nbins] && ignoreOutliers) {
							continue;
						}
						p = nbins - 1;
					} else {
						p = (int) ((val - lo) * f);
					}
					if (bit != null) {
						count.setObjectAbs(p, count.getElementDoubleAbs(p) + bit.bDouble);
					} else if (cData != null) {
						cData[p]++;
					}
				}
				result.add(count);
				result.add(be.origEdges);
			}
		} else {
			final double[] edges = binEdges.edges.getData();
			final double lo = edges[0];
			final double hi = edges[nbins];
			final int[] cShape = new int[] { nbins };
			final Dataset w = weights;

			for (IDataset ids : datasets) {
				Dataset ds = DatasetUtils.convertToDataset(ids);
				final Dataset count;
				final int[] cData;
				if (w == null) {
					IntegerDataset iCount = DatasetFactory.zeros(IntegerDataset.class, cShape);
					cData = iCount.getData();
					count = iCount;
				} else {
					int[] wShape = w.getShapeRef();
					if (!Arrays.equals(ds.getShapeRef(), wShape)) {
						throwIAException(logger, "Dataset '{}' shape {} must be compatible with weights' shape {}", ds.getName(), Arrays.toString(ds.getShapeRef()),
								Arrays.toString(wShape));
					}
					cData = null;
					count = DatasetFactory.zeros(InterfaceUtils.getLargestInterface(w), cShape);
				}
				if (lo >= hi) {
					count.set(w == null ? ids.getSize() : w.sum(true), binEdges.isIncreasing ? 0 : binEdges.lastBin);
					result.add(count);
					result.add(binEdges.origEdges);
					continue;
				}

				final BroadcastIterator bit = w == null ? null : BroadcastIterator.createIterator(ds, w);
				if (bit != null) {
					bit.setOutputDouble(true);
				}
				final IndexIterator it = bit == null ? ds.getIterator() : bit;

				while (it.hasNext()) {
					final double val = bit == null ? ds.getElementDoubleAbs(it.index) : bit.aDouble;
					final int p;
					if (val < lo) {
						if (ignoreOutliers)
							continue;
						p = 0;
					} else if (val >= hi) {
						if (val > hi && ignoreOutliers)
							continue;
						p = binEdges.lastBin;
					} else {
						// search for correct bin
						int b = Arrays.binarySearch(edges, val);
						if (b < 0) { // set to insertion point - 1
							b = -b - 2;
						}
						p = binEdges.isIncreasing ? b : binEdges.lastBin - b;
					}
					if (bit != null) {
						count.setObjectAbs(p, count.getElementDoubleAbs(p) + bit.bDouble);
					} else if (cData != null) {
						cData[p]++;
					}
				}
				result.add(count);
				result.add(binEdges.origEdges);
			}
		}
		return result;
	}
}

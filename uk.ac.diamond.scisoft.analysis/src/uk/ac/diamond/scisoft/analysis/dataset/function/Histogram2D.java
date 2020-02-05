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

import org.eclipse.january.dataset.BroadcastIterator;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.InterfaceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Find histogram of each pair of datasets and return 2-D integer dataset of bin counts
 * and pair of 1D double dataset of bin edges (including rightmost edge).
 * <p>
 * By default, outliers are ignored.
 */
public class Histogram2D extends HistogramNDBase {
	private static final Logger logger = LoggerFactory.getLogger(Histogram2D.class);

	/**
	 * Constructor of the Histogram
	 * @param numBins number of bins
	 */
	public Histogram2D(int... numBins) {
		nbins = numBins;
	}

	/**
	 * Constructor of the Histogram
	 * @param edges number of bins
	 */
	public Histogram2D(IDataset... edges) {
		if (edges.length == 0) {
			throw new IllegalArgumentException("Must given at least one edges dataset");
		}
		setBinEdges(edges);
	}

	@Override
	public List<Dataset> value(IDataset... datasets) {
		List<Dataset> result = new ArrayList<Dataset>();
		if (datasets.length == 0) {
			return result;
		}

		int[] wShape = weights == null ? null : weights.getShapeRef();
		int imax = datasets.length;
		for (int i = 0; i < imax; i += 2) {
			if (i == imax - 1) { // odd number of datasets
				logger.warn("Missing last dataset (shoule be given in pairs)");
				break;
			}
			Dataset x = DatasetUtils.convertToDataset(datasets[i]);
			Dataset y = DatasetUtils.convertToDataset(datasets[i + 1]);
			if (x == null || y == null) {
				throwIAException(logger, "Pairs of datasets must be defined");
			}
			if (!Arrays.equals(x.getShapeRef(), y.getShapeRef())) {
				throwIAException(logger, "Pairs of datasets must match shape: {} cf {}", x.getShapeRef(), y.getShapeRef());
			}
			Dataset[] r = histogram(x, y, wShape);
			for (Dataset d : r) {
				result.add(d);
			}
		}

		return result;
	}

	private Dataset[] histogram(Dataset x, Dataset y, int[] wShape) {
		if (wShape != null) {
			if (!Arrays.equals(x.getShapeRef(), wShape)) {
				throwIAException(logger, "Dataset '{}' shape {} must be compatible with weights' shape {}", x.getName(), Arrays.toString(x.getShapeRef()),
						Arrays.toString(wShape));
			}
		}

		final int dim = 2;
		BinEdges[] be = binEdges;
		if (be == null) {
			if (nbins.length != 1 && nbins.length < dim) {
				throwIAException(logger, "Dataset '{}' second dimension ({}) exceeds number of given number of bin sizes ({})", x.getName(), dim, nbins.length);
			}
			be = new BinEdges[dim];
			Dataset max = makeLimit(x, y, true);
			Dataset min = makeLimit(x, y, false);

			for (int i = 0; i < dim; i++) {
				int nb = nbins.length == 1 ? nbins[0] : nbins[i];
				be[i] = new BinEdges(nb, min.getDouble(i), max.getDouble(i));
			}
		} else if (binEdges.length == 1 && dim > 1) {
			be = new BinEdges[dim];
			for (int i = 0; i < dim; i++) {
				be[i] = binEdges[0];
			}
		} else if (binEdges.length != 1 && binEdges.length < dim) {
			throwIAException(logger, "Dataset '{}' second dimension ({}) exceeds number of given number of bin edges ({})", x.getName(), dim, binEdges.length);
		}

		int[] cShape = new int[dim];
		for (int i = 0; i < dim; i++) {
			cShape[i] = be[i].lastBin + 1;
		}

		Dataset[] result = new Dataset[dim + 1];
		result[0] = histogram(x, y, be, cShape);
		for (int i = 0; i < dim; i++) {
			result[i + 1] = be[i].origEdges;
		}
		return result;
	}

	private Dataset makeLimit(Dataset x, Dataset y, boolean isMax) {
		Dataset limit;
		double[] bLim = isMax ? bMax : bMin;
		if (bLim == null) {
			bLim = isMax ? new double[] { x.max(true).doubleValue(), y.max(true).doubleValue() } :
				new double[] { x.min(true).doubleValue(), y.min(true).doubleValue() };
		}
		if (bLim.length == 1) {
			limit = DatasetFactory.zeros(2);
			limit.fill(bLim[0]);
		} else {
			limit = DatasetFactory.createFromObject(bLim);
		}
		return limit;
	}

	private Dataset histogram(Dataset x, Dataset y, BinEdges[] be, int[] cShape) {
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
		final int dim = cShape.length;
		int[] pos = new int[dim];
		boolean earlyReturn = false;
		for (int i = 0; i < dim; i++) {
			BinEdges e = be[i];
			pos[i] = e.isIncreasing ? 0 : e.lastBin;
			if (e.l >= e.h) {
				earlyReturn = true;
			}
		}
		if (earlyReturn) {
			count.set(w == null ? x.getSize() : w.sum(true), pos);
			return count;
		}

		final BroadcastIterator it = BroadcastIterator.createIterator(x, y, w, false);
		it.setOutputDouble(true);
		double[] values = new double[dim];
		while (it.hasNext()) {
			values[0] = it.aDouble;
			values[1] = it.bDouble;
			boolean use = true;
			for (int i = 0; i < dim; i++) {
				final double v = values[i];
				final BinEdges e = be[i];
				final int n = e.lastBin;
				final double[] edgeData = e.edges.getData();
				final double l = e.l;
				int p;
				if (v < l) {
					if (ignoreOutliers) {
						use = false;
						break;
					}
					p = 0;
				} else if (v >= e.h) {
					if (ignoreOutliers && v > edgeData[n + 1]) {
						use = false;
						break;
					}
					p = n;
				} else {
					double f = e.f;
					if (!Double.isNaN(f)) {
						p = (int) (f * (v - l));
					} else {
						p = Arrays.binarySearch(edgeData, v);
						if (p < 0) {
							p = -p - 2; // set to insertion point - 1
						}
					}
				}
				pos[i] = e.isIncreasing ? p : n - p;
			}

			if (use) {
				int index = count.get1DIndex(pos);
				if (cData != null) {
					cData[index]++;
				} else if (w.hasFloatingPointElements()) {
					count.setObjectAbs(index, count.getElementDoubleAbs(index) + w.getElementDoubleAbs(it.oIndex));
				} else {
					count.setObjectAbs(index, count.getElementLongAbs(index) + w.getElementLongAbs(it.oIndex));
				}
			}
		}

		return count;
	}
}

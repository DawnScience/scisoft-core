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
import org.eclipse.january.dataset.CompoundDataset;
import org.eclipse.january.dataset.DTypeUtils;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.IntegerDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Find histogram of each 2D (n*d) dataset and return d-D integer dataset of bin counts
 * and d 1D double dataset of bin edges (including rightmost edge).
 * <p>
 * By default, outliers are ignored.
 */
public class HistogramND extends HistogramNDBase {
	private static final Logger logger = LoggerFactory.getLogger(HistogramND.class);

	/**
	 * Constructor of the Histogram
	 * @param numBins number of bins in each dimension
	 */
	public HistogramND(int... numBins) {
		nbins = numBins;
	}

	/**
	 * Constructor of the Histogram
	 * @param edges number of bins
	 */
	public HistogramND(IDataset... edges) {
		if (edges == null || edges.length == 0) {
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
		for (IDataset ids : datasets) {
			Dataset ds = DatasetUtils.convertToDataset(ids);
			Dataset[] r = histogram(ds, wShape);
			for (Dataset d : r) {
				result.add(d);
			}
		}

		return result;
	}

	private Dataset[] histogram(Dataset d, int[] wShape) {
		CompoundDataset cd;
		if (d instanceof CompoundDataset) {
			cd = (CompoundDataset) d;
		} else {
			int[] shape = d.getShapeRef();
			int rank = shape.length;
			if (rank > 2) {
				throwIAException(logger, "Dataset '{}' rank must be less than 3", d.getName());
			} else if (rank < 2) {
				shape = new int[] { d.getSize(), 1 }; // TODO replace once fix is in
//				d = d.reshape(d.getSize(), 1);
			}

			d = d.flatten().reshape(shape); // FIXME workaround bug in next method
			cd = DatasetUtils.createCompoundDatasetFromLastAxis(d, true);
		}
		if (wShape != null) {
			if (!Arrays.equals(cd.getShapeRef(), wShape)) {
				throwIAException(logger, "Dataset '{}' shape {} must be compatible with weights' shape {}", d.getName(), Arrays.toString(d.getShapeRef()),
						Arrays.toString(wShape));
			}
		}

		int dim = cd.getElementsPerItem();

		BinEdges[] be = binEdges;
		if (be == null) {
			if (nbins.length != 1 && nbins.length < dim) {
				throwIAException(logger, "Dataset '{}' second dimension ({}) exceeds number of given number of bin sizes ({})", d.getName(), dim, nbins.length);
			}
			be = new BinEdges[dim];
			Dataset max = makeLimit(cd, dim, true);
			Dataset min = makeLimit(cd, dim, false);

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
			throwIAException(logger, "Dataset '{}' second dimension ({}) exceeds number of given number of bin edges ({})", d.getName(), dim, binEdges.length);
		}

		int[] cShape = new int[dim];
		for (int i = 0; i < dim; i++) {
			cShape[i] = be[i].lastBin + 1;
		}

		Dataset[] result = new Dataset[dim + 1];
		result[0] = histogram(cd, be, cShape);
		for (int i = 0; i < dim; i++) {
			result[i + 1] = be[i].origEdges;
		}
		return result;
	}

	private Dataset makeLimit(CompoundDataset cd, int dim, boolean isMax) {
		Dataset d = DatasetUtils.createDatasetFromCompoundDataset(cd, true);
		if (d.getRank() == 1) {
			d.setShape(d.getSize(), 1);
		}
		Dataset limit;
		double[] bLim = isMax ? bMax : bMin;
		if (bLim == null) {
			limit = isMax ? d.max(0, true) : d.min(0, true);
		} else if (bLim.length == 1) {
			limit = DatasetFactory.zeros(dim);
			limit.fill(bLim[0]);
		} else {
			limit = DatasetFactory.createFromObject(bLim);
		}
		return limit;
	}

	private Dataset histogram(CompoundDataset cd, BinEdges[] be, int[] cShape) {
		final Dataset count;
		final int[] cData;
		final Dataset w = weights;
		if (w == null) {
			IntegerDataset iCount = DatasetFactory.zeros(IntegerDataset.class, cShape);
			cData = iCount.getData();
			count = iCount;
		} else {
			cData = null;
			count = DatasetFactory.zeros(cShape, DTypeUtils.getLargestDType(w.getDType()));
		}
		int dim = cShape.length;
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
			count.set(w == null ? cd.getSize() : w.sum(true), pos);
			return count;
		}

		final BroadcastIterator bit = w == null ? null : BroadcastIterator.createIterator(cd, w);
		final IndexIterator it = bit == null ? cd.getIterator() : bit;
		double[] values = new double[dim];
		while (it.hasNext()) {
			cd.getDoubleArrayAbs(bit == null ? it.index : bit.aIndex, values);
			boolean use = true;
			for (int i = 0; i < dim; i++) {
				final double v = values[i];
				final BinEdges e = be[i];
				final double[] edgeData = e.edges.getData();
				final int n = e.lastBin;
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
				if (bit != null) {
					if (bit.isOutputDouble()) {
						count.setObjectAbs(index, count.getElementDoubleAbs(index) + bit.bDouble);
					} else {
						count.setObjectAbs(index, count.getElementLongAbs(index) +  bit.bLong);
					}
				} else if (cData != null) {
					cData[index]++;
				}
			}
		}

		return count;
	}
}

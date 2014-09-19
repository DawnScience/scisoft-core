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
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.IndexIterator;
import org.eclipse.dawnsci.analysis.dataset.impl.RGBDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.SliceIterator;
import org.eclipse.dawnsci.analysis.dataset.impl.function.DatasetToDatasetFunction;

/**
 * Down-sample a dataset by a given bin
 */
public class Downsample implements DatasetToDatasetFunction {
	private DownsampleMode mode;
	private int[] bshape; // bin shape 

	/**
	 * This class down-samples (or subsamples) datasets according to given mode and sample bin shape
	 * 
	 * {@code mode} can be POINT, MEAN, MAXIMUM
	 * @param mode
	 * @param binShape
	 */
	public Downsample(DownsampleMode mode, int... binShape) {
		if (binShape.length == 0) {
			throw new IllegalArgumentException("Zero-dimensional bin not allowed");
		}
		for (int b : binShape) {
			if (b <= 0)
				throw new IllegalArgumentException("Bin side must be greater than zero");
		}
		bshape = binShape;
		this.mode = mode;
	}

	/**
	 * The class implements down-sampling of datasets
	 * 
	 * @param datasets
	 * @return a list of down-sampled datasets
	 */
	@Override
	public List<Dataset> value(IDataset... datasets) {
		if (datasets.length == 0)
			return null;

		List<Dataset> result = new ArrayList<Dataset>();
		int brank = bshape.length;

		for (IDataset idataset : datasets) {
			Dataset dataset = DatasetUtils.convertToDataset(idataset);
			final int[] dshape = dataset.getShape();
			final int drank = dshape.length;
			final int[] lbshape = new int[drank];
			for (int i = 0; i < drank; i++) {
				lbshape[i] = i < brank ? bshape[i] : 1; // adjust binning to dataset shape
			}
			final int[] shape = new int[drank];
			for (int i = 0; i < drank; i++) {
				shape[i] = (dshape[i] + bshape[i] - 1)/bshape[i];
			}

			final Dataset binned;
			if (dataset instanceof RGBDataset) {
				binned = new RGBDataset(shape);
				// set the mode to point whenever RGB datasets are involved for the moment.
				mode = DownsampleMode.POINT;
			} else {
				binned = DatasetFactory.zeros(dataset.getElementsPerItem(), shape, dataset.getDtype());
			}

			final IndexIterator biter = binned.getIterator(true);
			final int[] bpos = biter.getPos();
			final int[] spos = new int[drank];
			final int[] epos = new int[drank];
			final int isize = binned.getElementsPerItem();

			
			// TODO In Java8 switch these loops to using ParallelStreams
			switch (mode) {
			case POINT:
				while (biter.hasNext()) {
					for (int i = 0; i < drank; i++) {
						spos[i] = bshape[i]*bpos[i];
					}

					binned.setObjectAbs(biter.index, dataset.getObject(spos));
				}
				break;
			case MEAN:
				if (isize == 1) {
					while (biter.hasNext()) {
						for (int i = 0; i < drank; i++) {
							spos[i] = bshape[i] * bpos[i];
							epos[i] = spos[i] + bshape[i];
							if (epos[i] > dshape[i]) // ensure bin is within dataset
								epos[i] = dshape[i];
						}
						SliceIterator siter = (SliceIterator) dataset.getSliceIterator(spos, epos, null);

						double mean = 0;
						int num = 0;
						while (siter.hasNext()) {
							final double val = dataset.getElementDoubleAbs(siter.index);
							if (Double.isInfinite(val) || Double.isNaN(val))
								continue;
							num++;
							final double delta = val - mean;
							mean += delta / num;
						}
						binned.setObjectAbs(biter.index, mean);
					}
				} else {
					while (biter.hasNext()) {
						for (int i = 0; i < drank; i++) {
							spos[i] = bshape[i] * bpos[i];
							epos[i] = spos[i] + bshape[i];
							if (epos[i] > dshape[i])
								epos[i] = dshape[i];
						}
						SliceIterator siter = (SliceIterator) dataset.getSliceIterator(spos, epos, null);

						final double[] mean = new double[isize];
						int num = 0;
						while (siter.hasNext()) {
							for (int i = 0; i < isize; i++) {
								final double val = dataset.getElementDoubleAbs(siter.index + i);
								if (Double.isInfinite(val) || Double.isNaN(val))
									continue;
								num++;
								final double delta = val - mean[i];
								mean[i] += delta / num;
							}
						}
						binned.setObjectAbs(biter.index, mean);
					}
				}
				break;
			case MAXIMUM:
				if (isize == 1) {
					while (biter.hasNext()) {
						for (int i = 0; i < drank; i++) {
							spos[i] = bshape[i] * bpos[i];
							epos[i] = spos[i] + bshape[i];
							if (epos[i] > dshape[i])
								epos[i] = dshape[i];
						}
						SliceIterator siter = (SliceIterator) dataset.getSliceIterator(spos, epos, null);

						double max = Double.NEGATIVE_INFINITY;
						while (siter.hasNext()) {
							final double val = dataset.getElementDoubleAbs(siter.index);
							if (Double.isInfinite(val) || Double.isNaN(val))
								continue;
							if (val > max)
								max = val;
						}
						binned.setObjectAbs(biter.index, max);
					}
				} else {
					while (biter.hasNext()) {
						for (int i = 0; i < drank; i++) {
							spos[i] = bshape[i] * bpos[i];
							epos[i] = spos[i] + bshape[i];
							if (epos[i] > dshape[i])
								epos[i] = dshape[i];
						}
						SliceIterator siter = (SliceIterator) dataset.getSliceIterator(spos, epos, null);

						final double[] max = new double[isize];
						for (int i = 0; i < isize; i++)
							max[i] = Double.NEGATIVE_INFINITY;
						while (siter.hasNext()) {
							for (int i = 0; i < isize; i++) {
								final double val = dataset.getElementDoubleAbs(siter.index + i);
								if (Double.isInfinite(val) || Double.isNaN(val))
									continue;
								if (val > max[i])
									max[i] = val;
							}
						}
						binned.setObjectAbs(biter.index, max);
					}
				}
				break;
			case MINIMUM:
				if (isize == 1) {
					while (biter.hasNext()) {
						for (int i = 0; i < drank; i++) {
							spos[i] = bshape[i] * bpos[i];
							epos[i] = spos[i] + bshape[i];
							if (epos[i] > dshape[i])
								epos[i] = dshape[i];
						}
						SliceIterator siter = (SliceIterator) dataset.getSliceIterator(spos, epos, null);

						double min = Double.POSITIVE_INFINITY;
						while (siter.hasNext()) {
							final double val = dataset.getElementDoubleAbs(siter.index);
							if (Double.isInfinite(val) || Double.isNaN(val))
								continue;
							if (val < min)
								min = val;
						}
						binned.setObjectAbs(biter.index, min);
					}
				} else {
					while (biter.hasNext()) {
						for (int i = 0; i < drank; i++) {
							spos[i] = bshape[i] * bpos[i];
							epos[i] = spos[i] + bshape[i];
							if (epos[i] > dshape[i])
								epos[i] = dshape[i];
						}
						SliceIterator siter = (SliceIterator) dataset.getSliceIterator(spos, epos, null);

						final double[] min = new double[isize];
						for (int i = 0; i < isize; i++)
							min[i] = Double.POSITIVE_INFINITY;
						while (siter.hasNext()) {
							for (int i = 0; i < isize; i++) {
								final double val = dataset.getElementDoubleAbs(siter.index + i);
								if (Double.isInfinite(val) || Double.isNaN(val))
									continue;
								if (val < min[i])
									min[i] = val;
							}
						}
						binned.setObjectAbs(biter.index, min);
					}
				}
				break;
			}
			result.add(binned);
		}
		return result;
	}
}

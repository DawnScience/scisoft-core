/*-
 * Copyright © 2010 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.dataset.function;

import java.util.ArrayList;
import java.util.List;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IndexIterator;
import uk.ac.diamond.scisoft.analysis.dataset.RGBDataset;
import uk.ac.diamond.scisoft.analysis.dataset.SliceIterator;

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
	public List<AbstractDataset> value(IDataset... datasets) {
		if (datasets.length == 0)
			return null;

		List<AbstractDataset> result = new ArrayList<AbstractDataset>();
		int brank = bshape.length;

		for (IDataset idataset : datasets) {
			AbstractDataset dataset = DatasetUtils.convertToAbstractDataset(idataset);
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

			final AbstractDataset binned;
			if (dataset instanceof RGBDataset) {
				binned = new RGBDataset(shape);
			} else {
				binned = AbstractDataset.zeros(dataset.getElementsPerItem(), shape, dataset.getDtype());
			}

			final IndexIterator biter = binned.getIterator(true);
			final int[] bpos = biter.getPos();
			final int[] spos = new int[drank];
			final int[] epos = new int[drank];
			final int isize = binned.getElementsPerItem();

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

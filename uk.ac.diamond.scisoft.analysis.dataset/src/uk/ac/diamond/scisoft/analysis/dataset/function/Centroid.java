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

import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.PositionIterator;

/**
 * Find centroid of each dataset and return list of centroids along each dataset's axes
 *
 */
public class Centroid implements DatasetToNumberFunction {
	private Dataset[] bases = null;

	/**
	 * 
	 * @param baseCoordinates is optional array of coordinate values to use as weights.
	 * Defaults to midpoint values of indices
	 */
	public Centroid(Dataset... baseCoordinates) {
		bases = baseCoordinates;
	}

	private void checkCompatibility(IDataset d) {
		final int rank = d.getRank();
		final int[] shape = d.getShape();

		if (rank == bases.length) {
			int i = 0;
			for (; i < rank; i++) {
				if (shape[i] != bases[i].getSize()) {
					break;
				}
			}
			if (i == rank)
				return;
		}
		throw new IllegalArgumentException("Dataset shape does not match given or default coordinate base");
	}

	/**
	 * @param datasets input datasets
	 * @return a list of 1D datasets which are centroids in every dimension
	 */
	@Override
	public List<Double> value(IDataset... datasets) {
		if (datasets.length == 0)
			return null;

		List<Double> result = new ArrayList<Double>();
		for (IDataset ds : datasets) {
			final int rank = ds.getRank();

			final int[] shape = ds.getShape();
			if (bases == null || bases.length == 0) {
				bases = new Dataset[rank];
				for (int i = 0; i < rank; i++) {
					final int len = shape[i];
					final DoubleDataset axis = new DoubleDataset(len);

					bases[i] = axis;
					for (int j = 0; j < len; j++) {
						axis.setAbs(j, j + 0.5);
					}
				}
			} else {
				checkCompatibility(ds);
			}

			final PositionIterator iter = new PositionIterator(ds.getShape());
			final int[] pos = iter.getPos();
			double tsum = 0.0;
			final double[] xsum = new double[rank];

			while (iter.hasNext()) {
				double val = ds.getDouble(pos);
				tsum += val;
				for (int d = 0; d < rank; d++) {
					xsum[d] += bases[d].getElementDoubleAbs(pos[d])*val;
				}
			}

			for (int d = 0; d < rank; d++) {
				result.add(xsum[d] / tsum);
			}

		}

		return result;
	}
}

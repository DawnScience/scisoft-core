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
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.PositionIterator;

/**
 * Find centroid of each dataset and return list of centroids along each dataset's axes
 *
 */
public class Centroid implements DatasetToNumberFunction {
	private AbstractDataset[] bases = null;

	/**
	 * 
	 * @param baseCoordinates is optional array of coordinate values to use as weights.
	 * Defaults to midpoint values of indices
	 */
	public Centroid(AbstractDataset... baseCoordinates) {
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
				bases = new AbstractDataset[rank];
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

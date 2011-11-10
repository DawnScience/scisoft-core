/*-
 * Copyright © 2009 Diamond Light Source Ltd.
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

package gda.analysis.functions.dataset;

import gda.analysis.DataSet;

import java.util.ArrayList;
import java.util.List;

import uk.ac.diamond.scisoft.analysis.dataset.IndexIterator;

/**
 * Find centroid of ND dataset and return a 1D dataset with centroid in N directions
 */
public class CentroidND implements IDataSetFunction {
	private DataSet[] bases = null;

	/**
	 * Set up calculation of centroid over whole dataset
	 * 
	 * @param baseCoordinates is an optional array of coordinate values to use as weights.
	 * The default uses base coordinates at midpoint of index
	 */
	public CentroidND(DataSet... baseCoordinates) {
		bases = baseCoordinates;
	}

	private void checkCompatibility(DataSet d) {
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
	 * The class implements integrations along each axis of dataset 
	 * 
	 * @param inDS input dataset
	 * @return 1D datasets which are centroids in each dimension
	 */
	@Override
	public List<DataSet> execute(DataSet inDS) {
		final int rank = inDS.getRank();
		final int[] shape = inDS.getShape();
		if (bases == null || bases.length == 0) {
			bases = new DataSet[rank];
			for (int i = 0; i < rank; i++) {
				final int len = shape[i];
				final DataSet axis = new DataSet(len);

				bases[i] = axis;
				for (int j = 0; j < len; j++) {
					axis.setAbs(j, j + 0.5);
				}
			}
		} else {
			checkCompatibility(inDS);
		}

		IndexIterator iter = inDS.getIterator(true);
		int[] pos = iter.getPos();
		double tsum = 0.0;
		double[] xsum = new double[rank];

		while (iter.hasNext()) {
			double val = inDS.getAbs(iter.index);
			tsum += val;
			for (int d = 0; d < rank; d++) {
				xsum[d] += bases[d].get(pos[d])*val; // regard ND array as samples on midpoints
			}
		}
		for (int d = 0; d < rank; d++) {
			xsum[d] /= tsum;
		}
		DataSet centroid = new DataSet(xsum);

		ArrayList<DataSet> result = new ArrayList<DataSet>();
		result.add(centroid);
		return result;
	}

}

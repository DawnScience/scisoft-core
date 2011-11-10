/*-
 * Copyright © 2011 Diamond Light Source Ltd.
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
import uk.ac.diamond.scisoft.analysis.dataset.PositionIterator;
import uk.ac.diamond.scisoft.analysis.dataset.Stats;

public class Median implements DatasetToDatasetFunction {
	
	private final int window;

	/**
	 * This class applies median filter to the input datasets
	 * 
	 * @param window 
	 */
	public Median(int window) {
		if (window <= 0) {
			throw new IllegalArgumentException("Non-positive window parameter not allowed");
		}
		
		this.window = window / 2;
	}
	
	@Override
	public List<AbstractDataset> value(IDataset... datasets) {
		
		if (datasets.length == 0)
			return null;

		List<AbstractDataset> result = new ArrayList<AbstractDataset>();
		
		for (IDataset idataset : datasets) {
			AbstractDataset dataset = DatasetUtils.convertToAbstractDataset(idataset);
			final int dt = dataset.getDtype();
			final int is = dataset.getElementsPerItem();
			final int[] ishape = dataset.getShape();
			
			if (ishape.length > 1)
				throw new IllegalArgumentException("Only 1D input datasets are supported");
			
			AbstractDataset filterFunction = AbstractDataset.zeros(is , ishape, dt);
			
			final PositionIterator iterPos = filterFunction.getPositionIterator();
			while (iterPos.hasNext()) {
				int idx = iterPos.getPos()[0];
				int idxStart = Math.max(idx - this.window, 0);
				int idxStop = Math.min(idx + this.window, dataset.getSize()-1);
				AbstractDataset windowSlice = dataset.getSlice(new int[] {idxStart}, new int[] {idxStop}, new int[] {1});
				
				filterFunction.set(Stats.median(windowSlice), iterPos.getPos());
			}
			
			result.add(filterFunction);
		}
		return result;
	}
}

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
import org.eclipse.dawnsci.analysis.dataset.impl.PositionIterator;
import org.eclipse.dawnsci.analysis.dataset.impl.Stats;
import org.eclipse.dawnsci.analysis.dataset.impl.function.DatasetToDatasetFunction;

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
	public List<Dataset> value(IDataset... datasets) {
		
		if (datasets.length == 0)
			return null;

		List<Dataset> result = new ArrayList<Dataset>();
		
		for (IDataset idataset : datasets) {
			Dataset dataset = DatasetUtils.convertToDataset(idataset);
			final int dt = dataset.getDtype();
			final int is = dataset.getElementsPerItem();
			final int[] ishape = dataset.getShape();
			
			if (ishape.length > 1)
				throw new IllegalArgumentException("Only 1D input datasets are supported");
			
			Dataset filtered = DatasetFactory.zeros(is , ishape, dt);
			
			final PositionIterator iterPos = filtered.getPositionIterator();
			final int[] pos = iterPos.getPos();
			final int size = dataset.getSize();
			final int[] start = new int[1];
			final int[] stop = new int[1];
			final int[] step = new int[] {1};
			while (iterPos.hasNext()) {
				int idx = pos[0];
				start[0] = Math.max(idx - this.window, 0);
				stop[0] = Math.min(idx + this.window + 1, size); // exclusive
				
				filtered.set(Stats.median(dataset.getSlice(start, stop, step)), pos);
			}
			
			result.add(filtered);
		}
		return result;
	}
}

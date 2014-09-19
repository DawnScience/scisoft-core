/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.PositionIterator;

/**
 * An iterator over a hypergrid whose points have coordinates given by the
 * values in the datasets
 */
public class HypergridIterator extends CoordinatesIterator {

	private int endrank;

	/**
	 * @param values 
	 */
	public HypergridIterator(IDataset... values) {
		endrank = values.length - 1;
		this.values = new IDataset[endrank + 1];
		shape = new int[endrank + 1];

		for (int i = 0; i <= endrank; i++) {
			IDataset v = values[i];
			int size = v.getSize();
			shape[i] = size;
			if (v.getRank() != 1) {
				this.values[i] = DatasetUtils.convertToDataset(v).reshape(size);
			} else {
				this.values[i] = values[i];
			}
		}

		coords = new double[endrank + 1];
		it = new PositionIterator(shape);
		pos = it.getPos();
	}

	@Override
	public boolean hasNext() {
		if (!it.hasNext())
			return false;

		if (pos[endrank] == 0) {
			for (int i = 0; i <= endrank; i++) {
				coords[i] = values[i].getDouble(pos[i]);
			}
		} else {
			coords[endrank] = values[endrank].getDouble(pos[endrank]);
		}
		return true;
	}
}

/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import java.util.Arrays;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.PositionIterator;

/**
 * An iterator over the common input shape whose points have coordinates given by the
 * values in the datasets
 */
public class DatasetsIterator extends CoordinatesIterator {
	private int rank;

	/**
	 * All these datasets must have the same shape
	 * @param values
	 */
	public DatasetsIterator(IDataset... values) {
		rank = values.length;
		this.values = values;
		shape = values[0].getShape();
		for (int i = 1; i < rank; i++) {
			IDataset v = values[i];
			if (!Arrays.equals(shape, v.getShape())) {
				throw new IllegalArgumentException("All shapes must be the same");
			}
		}

		coords = new double[rank];
		it = new PositionIterator(shape);
		pos = it.getPos();
	}

	@Override
	public boolean hasNext() {
		if (!it.hasNext())
			return false;

		for (int i = 0; i < rank; i++) {
			coords[i] = values[i].getDouble(pos);
		}
		return true;
	}
}

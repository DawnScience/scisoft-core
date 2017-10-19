/*-
 * Copyright (c) 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.IDataset;

/**
 * An iterator over the common input shape whose points have coordinates given by the
 * values in the datasets
 */
public class DatasetIterator extends CoordinatesIterator {
	private Dataset v;

	/**
	 * All these datasets must have the same shape
	 * @param value
	 */
	public DatasetIterator(Dataset value) {
		v = value;
		this.values = new IDataset[] {v};
		shape = v.getShapeRef();

		coords = new double[1];
		it = v.getIterator();
	}

	@Override
	public boolean hasNext() {
		if (!it.hasNext())
			return false;

		coords[0] = v.getElementDoubleAbs(it.index);
		return true;
	}
}

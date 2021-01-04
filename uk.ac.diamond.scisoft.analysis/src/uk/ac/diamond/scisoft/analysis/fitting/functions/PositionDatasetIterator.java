/*-
 * Copyright (c) 2021 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import org.eclipse.january.dataset.CompoundIntegerDataset;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.PositionIterator;

/**
 * An iterator over a dataset whose items are coordinates
 */
public class PositionDatasetIterator extends CoordinatesIterator {
	CompoundIntegerDataset cvalue;

	private int[] position;

	/**
	 * A single, possibly compound, dataset
	 * @param value
	 */
	public PositionDatasetIterator(Dataset value) {
		cvalue = DatasetUtils.cast(CompoundIntegerDataset.class, value);
		shape = cvalue.getShape();

		position = new int[cvalue.getElementsPerItem()];
		values = new IDataset[] { cvalue };
		it = new PositionIterator(shape);
		pos = it.getPos();
	}

	@Override
	public boolean hasNext() {
		if (!it.hasNext())
			return false;

		cvalue.getAbs(cvalue.get1DIndex(pos), position);
		return true;
	}

	@Override
	public int[] getPosition() {
		return position;
	}
}

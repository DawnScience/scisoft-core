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
import org.eclipse.dawnsci.analysis.dataset.impl.AbstractDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.CompoundDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.PositionIterator;

/**
 * An iterator over a dataset whose items are coordinates
 */
public class CoordinateDatasetIterator extends CoordinatesIterator {
	private int rank;
	CompoundDataset cvalue;

	/**
	 * A single, possibly compound, dataset
	 * @param value
	 */
	public CoordinateDatasetIterator(IDataset value) {
		if (!(value instanceof CompoundDataset)) {
			int dtype = AbstractDataset.getBestDType(Dataset.ARRAYINT8,
					AbstractDataset.getDTypeFromClass(value.elementClass()));
			cvalue = (CompoundDataset) DatasetUtils.cast(value, dtype);
		} else {
			cvalue = (CompoundDataset) value;
		}
		rank = cvalue.getRank();
		shape = cvalue.getShape();

		coords = new double[rank];
		values = new IDataset[] { cvalue };
		it = new PositionIterator(shape);
		pos = it.getPos();
	}

	@Override
	public boolean hasNext() {
		if (!it.hasNext())
			return false;

		for (int i = 0; i < rank; i++) {
			cvalue.getDoubleArray(coords, pos);
		}
		return true;
	}
}

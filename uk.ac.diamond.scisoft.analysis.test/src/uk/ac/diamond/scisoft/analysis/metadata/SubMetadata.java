/*
 * Copyright (c) 2014 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.metadata;

import java.util.List;
import java.util.Map;

import org.eclipse.january.dataset.BooleanDataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.ShortDataset;
import org.eclipse.january.metadata.MetadataType;
import org.eclipse.january.metadata.Sliceable;

public class SubMetadata extends SliceableTestMetadata {
	@Sliceable
	ILazyDataset ldb;

	public SubMetadata(ILazyDataset ld, DoubleDataset[] array, List<ShortDataset> list, Map<String, BooleanDataset> map, List<DoubleDataset[]> l2) {
		super(ld, array, list, map, l2);
		ldb = DatasetFactory.zeros(DoubleDataset.class, ld.getShape());
	}

	public ILazyDataset getLazyDataset2() {
		return ldb;
	}

	@Override
	public MetadataType clone() {
		SubMetadata c = new SubMetadata(ldb, getArray(), getList(), getMap(), getListOfArrays());
		c.ldb = ldb;
		return c;
	}
}

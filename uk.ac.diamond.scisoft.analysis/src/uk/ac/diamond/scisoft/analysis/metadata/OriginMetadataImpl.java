/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.metadata;

import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.dataset.Slice;
import org.eclipse.dawnsci.analysis.api.metadata.OriginMetadata;

public class OriginMetadataImpl implements OriginMetadata {
	
	private ILazyDataset parent;
	private Slice[] slice;
	private int[] dataDims;

	public OriginMetadataImpl(ILazyDataset parent, Slice[] slice, int[] dataDims) {
		this.parent = parent;
		this.slice = slice;
		this.dataDims = dataDims;
	}

	public OriginMetadataImpl(OriginMetadataImpl origin) {
		parent = origin.parent == null ? null : origin.parent.getSliceView();
		slice = origin.slice == null ? null : origin.slice.clone();
		dataDims = origin.dataDims == null ? null : origin.dataDims.clone();
	}

	@Override
	public int[] getDataDimensions() {
		return dataDims;
	}

	@Override
	public ILazyDataset getParent() {
		return parent;
	}

	@Override
	public Slice[] getSlice() {
		return slice;
	}
	
	@Override
	public OriginMetadata clone() {
		return new OriginMetadataImpl(this);
	}

}

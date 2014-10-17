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
	private Slice[] viewSlice;
	private Slice[] currentSlice;
	private int[] dataDims;
	
	private String datasetName;
	private String filePath;

	public OriginMetadataImpl(ILazyDataset parent, Slice[] viewSlice, int[] dataDims, String filePath, String datasetName) {
		this.parent = parent;
		this.viewSlice = viewSlice;
		this.dataDims = dataDims;
		this.datasetName = datasetName;
		this.filePath = filePath;
	}

	public OriginMetadataImpl(OriginMetadataImpl origin) {
		parent = origin.parent == null ? null : origin.parent;
		viewSlice = origin.viewSlice == null ? null : origin.viewSlice.clone();
		dataDims = origin.dataDims == null ? null : origin.dataDims.clone();
		datasetName = origin.datasetName == null ? null : new String(origin.datasetName);
		filePath = origin.filePath == null ? null : new String(origin.filePath);
		currentSlice = origin.currentSlice == null ? null : origin.currentSlice.clone();
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
	public Slice[] getCurrentSlice() {
		return currentSlice;
	}
	
	public void setCurrentSlice(Slice[] currentSlice) {
		this.currentSlice = currentSlice;
	}
	
	@Override
	public OriginMetadata clone() {
		return new OriginMetadataImpl(this);
	}

	@Override
	public Slice[] getInitialSlice() {
		return viewSlice;
	}

	@Override
	public String getDatasetName() {
		return datasetName;
	}

	@Override
	public String getFilePath() {
		return filePath;
	}

}

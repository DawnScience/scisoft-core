/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations;

import java.util.Arrays;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.EmptyModel;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceInformation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.metadata.AxesMetadata;

import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;

public class PropagateAllAxesOperation extends AbstractOperation<EmptyModel, OperationData> {


	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.PropagateAllAxesOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.ANY;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.SAME;
	}

	@Override
	protected OperationData process(IDataset input, IMonitor monitor) {
		return new OperationData(input);
	}

	@Override
	public OperationData execute(IDataset slice, IMonitor monitor) {
		
		// sliceseriesmetadata is useless here as it doesnt contain all axes associated with the dataset, just the one selected in
		// the dataset dialog
		// solution -> reopen the file
		String filePath = getSliceSeriesMetadata(slice).getSourceInfo().getFilePath();
		String datasetName = getSliceSeriesMetadata(slice).getSourceInfo().getDatasetName();
		SliceInformation sliceInfo = getSliceSeriesMetadata(slice).getSliceInfo();
		ILazyDataset parent = getSliceSeriesMetadata(slice).getParent();
		
		AxesMetadata metadataParentShort = null; // this one will have full paths to the datasets
		AxesMetadata metadataParentAll = null; // this one will have truncated (basename'd) paths to the datasets, which is not cool...
		
		try {
			metadataParentShort = parent.getMetadata(AxesMetadata.class).get(0);
		} catch (MetadataException e) {
			throw new OperationException(this, e);
		}
		
		IDataHolder dataHolder;
		try {
			dataHolder = LoaderFactory.getData(filePath);
		} catch (Exception e) {
			throw new OperationException(this, e);
		}
		ILazyDataset lazyDataset = dataHolder.getLazyDataset(datasetName);
	
		try {
			metadataParentAll = lazyDataset.getMetadata(AxesMetadata.class).get(0);
		} catch (MetadataException e) {
			throw new OperationException(this, e);
		}
	
		IDataset output = slice.clone();
		AxesMetadata outputMetadata = output.getFirstMetadata(AxesMetadata.class);
		
		for (int i = 0 ; i < parent.getRank() ; i++) {
			ILazyDataset axisShort = metadataParentShort.getAxis(i)[0];
			ILazyDataset[] axisAll = metadataParentAll.getAxis(i);
		
			for (ILazyDataset axis : axisAll) {
				if (getShortDatasetName(axis.getName()).equals(getShortDatasetName(axisShort.getName()))) {
					// axis already present in metadata
					continue;
				}
				if (Arrays.equals(slice.getShape(), axis.getShape())) {
					outputMetadata.addAxis(i, axis);
				} else {
					outputMetadata.addAxis(i, axis.getSliceView(sliceInfo.getSliceFromInput()));
				}
			}
		}
		
		return new OperationData(output);
	}
	
	private static String getShortDatasetName(String longName) {
		int lastIndex = longName.lastIndexOf('/');
		String newName = longName.substring(lastIndex + 1);
		lastIndex = newName.lastIndexOf('[');
		if (lastIndex >= 0)
			newName = newName.substring(0, lastIndex);
		return newName.trim();
	}
}

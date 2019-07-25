/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.externaldata;

import java.io.Serializable;

import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.metadata.IMetadata;

import uk.ac.diamond.scisoft.analysis.processing.LocalServiceManager;
import uk.ac.diamond.scisoft.analysis.utils.ErrorPropagationUtils;

@Atomic
public class FileMetadataNormalisation extends AbstractOperation<FileMetadataModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.externaldata.FileMetadataNormalisation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.ANY;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.SAME;
	}

	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		IDataset output = null;
		try {
			Serializable metaValue;
			IMetadata metadata = input.getFirstMetadata(IMetadata.class);
			if (metadata != null && metadata.getMetaNames().contains(model.getMetadataName())) {
				metaValue = metadata.getMetaValue(model.getMetadataName());
			}
			else {
				SliceFromSeriesMetadata oMetadata = input.getFirstMetadata(SliceFromSeriesMetadata.class);
				String filePath = oMetadata.getFilePath();
				metadata = LocalServiceManager.getLoaderService().getMetadata(filePath, null);
				metaValue = metadata.getMetaValue(model.getMetadataName());
			}
			Dataset parseDouble = DatasetFactory.createFromObject(Double.parseDouble(metaValue.toString()));
			output = ErrorPropagationUtils.divideWithUncertainty(DatasetUtils.convertToDataset(input), parseDouble);
			copyMetadata(input, output);
		} catch (Exception e1) {
			throw new OperationException(this, "Could not read metadata");
		}
		return new OperationData(output);
	}

}

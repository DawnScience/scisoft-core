/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.AbstractOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;

import uk.ac.diamond.scisoft.analysis.io.NexusDiffractionMetaReader;

public class DiffractionMetadataImportOperation extends AbstractOperation<DiffractionMetadataImportModel, OperationData> {

	IDiffractionMetadata metadata;
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.DiffractionMetadataImportOperation";
	}

	@Override
	public OperationData execute(IDataset slice, IMonitor monitor)
			throws OperationException {
		
		if (metadata == null) {
			NexusDiffractionMetaReader reader = new NexusDiffractionMetaReader(((DiffractionMetadataImportModel)model).getFilePath());
			IDiffractionMetadata md = reader.getDiffractionMetadataFromNexus(null);
			if (!reader.isPartialRead()) throw new OperationException(this, "File does not contain metadata");
			metadata = md;
		}
		
		slice.addMetadata(metadata);
		return new OperationData(slice);
	}


	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}

}

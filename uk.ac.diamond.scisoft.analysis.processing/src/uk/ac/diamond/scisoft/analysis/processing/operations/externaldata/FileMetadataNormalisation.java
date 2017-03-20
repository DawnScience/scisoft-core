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
import java.util.List;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.metadata.IMetadata;

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
			IMetadata metadata = input.getFirstMetadata(IMetadata.class);
			Serializable metaValue = metadata.getMetaValue(model.getMetadataName());
			double parseDouble = Double.parseDouble(metaValue.toString());
			output = Maths.divide(input, parseDouble);
			if (input.getErrors() != null) {
				IDataset error = input.getErrors().getSlice();
				output.setErrors(Maths.divide(error, parseDouble));
			}
			copyMetadata(input, output);
		} catch (Exception e) {
			throw new OperationException(this, "Could not read metadata");
		}
		
		return new OperationData(output);
		
	}
	
}

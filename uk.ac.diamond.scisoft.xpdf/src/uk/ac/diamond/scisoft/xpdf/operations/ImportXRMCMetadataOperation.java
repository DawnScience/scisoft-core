/*
 * Copyright (c) 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.xpdf.operations;

import java.io.IOException;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.IDataset;

import uk.ac.diamond.scisoft.xpdf.metadata.XRMCMetadata;
import uk.ac.diamond.scisoft.xpdf.xrmc.XRMCDevice;
import uk.ac.diamond.scisoft.xpdf.xrmc.XRMCMetadataImpl;

public class ImportXRMCMetadataOperation extends AbstractOperation<ImportXRMCMetadataModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.xpdf.operations.ImportXRMCMetadataOperation";
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
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {

		// Read the given input file
		String inputFile = model.getXrmcInputFilePath();
		XRMCMetadata mcMeta;
		try {
			XRMCMetadataImpl mcImpl = new XRMCMetadataImpl(inputFile);
			mcImpl.setDeviceRead(XRMCDevice.DETECTORARRAY, true);
			mcImpl.readData();
			mcMeta = mcImpl;
		} catch (IOException ioe) {
			throw new OperationException(this, "Could not read XRMC input file " + inputFile);
		}
		
		input.setMetadata(mcMeta);
		OperationData opData = new OperationData(input);
		return opData;
	}

}

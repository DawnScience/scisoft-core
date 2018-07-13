/*
 * Copyright (c) 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.operations;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.EmptyModel;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;

import uk.ac.diamond.scisoft.xpdf.metadata.XPDFMetadata;
import uk.ac.diamond.scisoft.xpdf.metadata.XRMCMetadata;
import uk.ac.diamond.scisoft.xpdf.xrmc.XRMCEnergyIntegrator;

public class XRMCEnergyIntegration extends AbstractOperation<EmptyModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.xpdf.operations.XRMCEnergyIntegration";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO; // Default XRMC file are four dimensional: interactions, energy, x, y
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO; // Just an x-y image
	}

	@Override
	protected OperationData process(IDataset input2d, IMonitor monitor) throws OperationException {

		// Fetch and convert the underlying XRMC data from the 2d processing data
		Dataset xrmcData = null;
		try {
			xrmcData = DatasetUtils.convertToDataset(getSliceSeriesMetadata(input2d).getParent().getSlice());
		} catch (DatasetException e) {
			throw new OperationException(this, e.toString());
		}
		if (xrmcData == null || !(xrmcData instanceof Dataset)) {
			throw new OperationException(this, "Could not get underlying XRMC data.");
		}
		Dataset lastScattering = xrmcData.sum(0).squeeze();
		
		XPDFMetadata xpdfMetadata = input2d.getFirstMetadata(XPDFMetadata.class);
		if (!XPDFOperationChecker.hasMetadata(input2d) || !XPDFOperationChecker.hasDetectorMetadata(xpdfMetadata))
			throw new OperationException(this, "XPDF detector metadata not found");
		
		XRMCMetadata xrmcMetadata = input2d.getFirstMetadata(XRMCMetadata.class);
		
		XRMCEnergyIntegrator integrator = new XRMCEnergyIntegrator();
		integrator.setXRMCData(lastScattering);
		integrator.setDetector(xpdfMetadata.getDetector());
		integrator.setXRMCDetector(xrmcMetadata.getDetector(), xrmcMetadata.getSource());

		Dataset counts = integrator.getDetectorCounts();
		
		// Copy metadata: XPDF and XRMC
		counts.addMetadata(xpdfMetadata);
		counts.addMetadata(xrmcMetadata);
		
		return new OperationData(counts);
	}

}

/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.operations;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;

import uk.ac.diamond.scisoft.analysis.processing.operations.powder.AzimuthalPixelIntegrationModel;
import uk.ac.diamond.scisoft.analysis.processing.operations.powder.AzimuthalPixelIntegrationOperation;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFMetadata;

/**
 * An operation to perform azimuthal integration while preserving the {@link XPDFMetadata} metadata associated with the data. 
 * @author Timothy Spain, timothy.spain@diamond.ac.uk
 *
 * @param <T> a subclass of{@link AzimuthalPixalIntegrationModel}.
 */
public class XPDFAzimuthalIntegration<T extends AzimuthalPixelIntegrationModel> extends
		AzimuthalPixelIntegrationOperation<AzimuthalPixelIntegrationModel> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.xpdf.operations.XPDFAzimuthalIntegration";
	}
	
	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
	
		XPDFMetadata xMeta = input.getFirstMetadata(XPDFMetadata.class);
		OperationData superResult = super.process(input, monitor);
		if (xMeta != null)
			superResult.getData().setMetadata(xMeta);
		return superResult;
	}
	
}

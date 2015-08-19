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
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

import uk.ac.diamond.scisoft.analysis.processing.operations.EmptyModel;
import uk.ac.diamond.scisoft.xpdf.XPDFTargetComponent;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFMetadata;

public class XPDFSubtractBackgroundOperation extends
		AbstractOperation<XPDFSubtractBackgroundModel, OperationData> {

	protected OperationData process(IDataset input, IMonitor monitor)
			throws OperationException {

		XPDFMetadata theXPDFMetadata =  null;
		
		IDataset process = new DoubleDataset(DatasetUtils.convertToDataset(input));
		
		copyMetadata(input, process);
		
		try {
			if (process.getMetadata(XPDFMetadata.class) != null &&
				!process.getMetadata(XPDFMetadata.class).isEmpty() &&
				process.getMetadata(XPDFMetadata.class).get(0) != null)
				theXPDFMetadata = process.getMetadata(XPDFMetadata.class).get(0);
		} catch (Exception e) {
			;
		}
		
		if (theXPDFMetadata != null && theXPDFMetadata.getBeam() != null && theXPDFMetadata.getBeam().getTrace() != null) {
			// Dataset trace, from the Sample metadata
			if (theXPDFMetadata.getSample() != null && 
					theXPDFMetadata.getSample().getTrace() != null && 
					!theXPDFMetadata.getSample().getTrace().isBackgroundSubtracted() && 
					model.isSubtractSampleBackground()) {
				// sets the isBackgroundSubtracted flag, but does not subtract the background from the (null) trace
				theXPDFMetadata.getSample().getTrace().subtractBackground(theXPDFMetadata.getBeam().getTrace());
				// Subtract the background from the Dataset
				((Dataset) process).isubtract(theXPDFMetadata.getBeam().getTrace().getTrace());
			}
			
			// For each container metadataset, subtract the background from it
						if (theXPDFMetadata.getContainers() != null && 
					model.isSubtractContainersBackground()) {
				for (XPDFTargetComponent container : theXPDFMetadata.getContainers()) {
					if (container.getTrace() != null)
						container.getTrace().subtractBackground(theXPDFMetadata.getBeam().getTrace());
				}
			}
		}		
		return new OperationData(process);
	}
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.xpdf.operations.XPDFSubtractBackgroundOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.ONE;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}

}

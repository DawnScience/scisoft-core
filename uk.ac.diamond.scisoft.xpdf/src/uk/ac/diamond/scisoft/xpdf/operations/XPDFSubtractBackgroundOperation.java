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
import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

import uk.ac.diamond.scisoft.xpdf.XPDFTargetComponent;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFMetadata;

/**
 * Subtract the background.
 * <p>
 * Subtract the empty beam data from the sample data and from that of any
 * containers present. 
 * @author Timothy Spain timothy.spain@diamond.ac.uk
 *
 */
@Atomic
public class XPDFSubtractBackgroundOperation extends
		AbstractOperation<XPDFSubtractBackgroundModel, OperationData> {

	protected OperationData process(IDataset input, IMonitor monitor)
			throws OperationException {

		XPDFOperationChecker.checkXPDFMetadata(this, input, false, true, false);
		XPDFMetadata theXPDFMetadata =  null;
		
		IDataset process = new DoubleDataset(DatasetUtils.convertToDataset(input));
		
		copyMetadata(input, process);
		
		theXPDFMetadata = process.getFirstMetadata(XPDFMetadata.class);
		
		if (theXPDFMetadata != null && theXPDFMetadata.getBeam() != null && theXPDFMetadata.getBeam().getTrace() != null) {
			// Dataset trace, from the Sample metadata
			if (theXPDFMetadata.getSample() != null && 
					theXPDFMetadata.getSample().getTrace() != null && 
					!theXPDFMetadata.getSample().getTrace().isBackgroundSubtracted() && 
					model.isSubtractSampleBackground()) {
				// sets the isBackgroundSubtracted flag, but does not subtract the background from the (null) trace
				theXPDFMetadata.getSample().setBackground(theXPDFMetadata.getBeam().getTrace());
				// Subtract the background from the Dataset
				((Dataset) process).isubtract(theXPDFMetadata.getBeam().getTrace().getTrace());
				
				// Propagate the errors
				Dataset inputErrors = (input.getError() != null) ? DatasetUtils.convertToDataset(input.getError()) : null;
				Dataset processErrors = null;
				if (inputErrors != null) {
					Dataset	backgroundErrors = 	theXPDFMetadata.getBeam().getTrace().getTrace().getError();	
					processErrors = (backgroundErrors != null) ? 
							Maths.sqrt(Maths.add(Maths.square(inputErrors), Maths.square(backgroundErrors))) :
								inputErrors;
				}
				process.setError(processErrors);
			}
			
			// For each container metadataset, subtract the background from it
						if (theXPDFMetadata.getContainers() != null && 
					model.isSubtractContainersBackground()) {
				for (XPDFTargetComponent container : theXPDFMetadata.getContainers()) {
					if (container.getTrace() != null)
						container.setBackground(theXPDFMetadata.getBeam().getTrace());
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

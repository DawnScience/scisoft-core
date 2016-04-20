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
		
		Dataset process = new DoubleDataset(DatasetUtils.convertToDataset(input));
		
		copyMetadata(input, process);
		
		theXPDFMetadata = process.getFirstMetadata(XPDFMetadata.class);
		
		if (theXPDFMetadata != null && theXPDFMetadata.getEmptyTrace() != null ) {
			// Dataset trace, from the Sample metadata
			if (theXPDFMetadata.getSample() != null && 
					theXPDFMetadata.getSampleTrace() != null && 
					!theXPDFMetadata.getSampleTrace().isBackgroundSubtracted() && 
					model.isSubtractSampleBackground()) {
				// sets the isBackgroundSubtracted flag, but does not subtract the background from the (null) trace
				theXPDFMetadata.getSampleTrace().subtractBackground(theXPDFMetadata.getEmptyTrace());
				// Subtract the background from the Dataset
				process.isubtract(theXPDFMetadata.getEmptyTrace().getTrace());
				
				// Propagate the errors
				Dataset inputErrors = DatasetUtils.sliceAndConvertLazyDataset(input.getError());
				Dataset processErrors = null;
				if (inputErrors != null) {
					Dataset	backgroundErrors = 	theXPDFMetadata.getEmptyTrace().getTrace().getError();	
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
					if (theXPDFMetadata.getContainerTrace(container) != null)
						theXPDFMetadata.getContainerTrace(container).subtractBackground(theXPDFMetadata.getEmptyTrace());
				}
			}
		}
		process.setName("Background Subtracted");
		
		return new OperationData(process);
	}
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.xpdf.operations.XPDFSubtractBackgroundOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.ANY;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.SAME;
	}

}

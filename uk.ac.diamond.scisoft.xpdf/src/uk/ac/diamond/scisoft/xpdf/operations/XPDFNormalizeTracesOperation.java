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
 * Normalize the data.
 * </p><p>
 * Normalize the Dataset from its TargetComponent metadata, and if present, the
 * beam and container traces from their trace metadata
 * </p><p>
 * Also apply the normalization to error data, if present.
 * </p>
 * 
 * @author Timothy Spain (rkl37156) timothy.spain@diamond.ac.uk
 * @since 2015-09-14
 *
 */
@Atomic
public class XPDFNormalizeTracesOperation extends
		AbstractOperation<XPDFNormalizeTracesModel, OperationData> {

	protected OperationData process(IDataset input, IMonitor monitor)
			throws OperationException {

		XPDFMetadata theXPDFMetadata =  null;
		
		IDataset process = new DoubleDataset(DatasetUtils.convertToDataset(input));
		
		copyMetadata(input, process);
		
		theXPDFMetadata = process.getFirstMetadata(XPDFMetadata.class);
		if (theXPDFMetadata == null) throw new OperationException(this, "XPDF metadata not found.");
		
		if (theXPDFMetadata != null) {
			// Dataset trace, from the Sample metadata
			if (theXPDFMetadata.getSample() != null && 
					theXPDFMetadata.getSample().getTrace() != null && 
					!theXPDFMetadata.getSample().getTrace().isNormalized() && 
					model.isNormalizeSample()) {
				// sets the isNormalized flag, but does not normalize the (null) trace
				theXPDFMetadata.getSample().getTrace().normalizeTrace();
				// Normalize the Dataset
				double normer = theXPDFMetadata.getSample().getTrace().getCountingTime()*
						theXPDFMetadata.getSample().getTrace().getMonitorRelativeFlux();
				((Dataset) process).idivide(normer);
			
				// Normalize the errors, if present
				Dataset inputErrors = (input.getError() != null) ? DatasetUtils.convertToDataset(input.getError()) : null;
				if (inputErrors != null) {
					Dataset processErrors = Maths.divide(inputErrors, normer);
					process.setError(processErrors);
				}
			}
			
			// If there is a BeamData metadataset, normalize it
			if (theXPDFMetadata.getBeam() != null && 
					theXPDFMetadata.getBeam().getTrace() != null && 
					model.isNormalizeBeam())
				theXPDFMetadata.getBeam().getTrace().normalizeTrace();
			
			// For each container metadataset, normalize its beam trace
			if (theXPDFMetadata.getContainers() != null && 
					model.isNormalizeContainers()) {
				for (XPDFTargetComponent container : theXPDFMetadata.getContainers()) {
					if (container.getTrace() != null) 
						container.getTrace().normalizeTrace();
				}
			}
		}		
		return new OperationData(process);
	}

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.xpdf.operations.XPDFNormalizeTracesOperation";
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

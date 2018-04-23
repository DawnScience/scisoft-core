/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.operations;

import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;

import uk.ac.diamond.scisoft.analysis.io.DiffractionMetadata;
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
 * @author Timothy Spain timothy.spain@diamond.ac.uk
 * @since 2015-09-14
 *
 */
@Atomic
public class XPDFNormalizeTracesOperation extends
		AbstractOperation<XPDFNormalizeTracesModel, OperationData> {

	protected OperationData process(IDataset input, IMonitor monitor)
			throws OperationException {

		XPDFOperationChecker.checkXPDFMetadata(this, input, true, false, false);
		
		XPDFMetadata theXPDFMetadata =  null;
		
		Dataset process = DatasetUtils.copy(DoubleDataset.class, input);
		
		copyMetadata(input, process);
		
		theXPDFMetadata = process.getFirstMetadata(XPDFMetadata.class);
		
		// Get the per pixel solid angle
		DiffractionMetadata diffMeta = process.getFirstMetadata(DiffractionMetadata.class);
		Dataset solidAngle = (model.isNormalizeByOmega()) ? fullDetectorSolidAngle(diffMeta.getDetector2DProperties()) : null;
		
		if (theXPDFMetadata != null) {
			// Dataset trace, from the Sample metadata
			if (theXPDFMetadata.getSample() != null && 
					theXPDFMetadata.getSampleTrace() != null && 
					!theXPDFMetadata.getSampleTrace().isNormalized() && 
					model.isNormalizeSample()) {
				// sets the isNormalized flag, but does not normalize the (null) trace
				theXPDFMetadata.getSampleTrace().normalizeTrace();
				// Normalize the Dataset
				double normer = theXPDFMetadata.getSampleTrace().getCountingTime()*
						theXPDFMetadata.getSampleTrace().getMonitorRelativeFlux();
				process.idivide(normer);
			
				if (model.isNormalizeByOmega()) process.idivide(solidAngle);

				// Normalize the errors, if present
				Dataset inputErrors;
				try {
					inputErrors = DatasetUtils.sliceAndConvertLazyDataset(input.getErrors());
					if (inputErrors != null) {
						Dataset processErrors = Maths.divide(inputErrors, normer);
						if (model.isNormalizeByOmega()) processErrors.idivide(solidAngle);
						process.setErrors(processErrors);
					}
				} catch (DatasetException e) {
					throw new OperationException(this, e);
				}
			}
			
			// If there is a BeamData metadataset, normalize it
			if (theXPDFMetadata.getBeam() != null && 
					theXPDFMetadata.getEmptyTrace() != null && 
					model.isNormalizeBeam())
				theXPDFMetadata.getEmptyTrace().normalizeTrace();
			
			// For each container metadataset, normalize its beam trace
			if (theXPDFMetadata.getContainers() != null && 
					model.isNormalizeContainers()) {
				for (XPDFTargetComponent container : theXPDFMetadata.getContainers()) {
					if (theXPDFMetadata.getContainerTrace(container) != null) 
						theXPDFMetadata.getContainerTrace(container);
				}
			}
		}
		process.setName("Monitor Normalized");
		return new OperationData(process);
	}

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.xpdf.operations.XPDFNormalizeTracesOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.ANY;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.SAME;
	}

	private Dataset fullDetectorSolidAngle(DetectorProperties dProp) {
		
		int nX = dProp.getPx();
		int nY = dProp.getPy();
		Dataset omega = DatasetFactory.zeros(nX, nY);
		
		for (int i = 0; i < nX; i++) {
			for (int j = 0; j < nY; j++) {
				omega.set(dProp.calculateSolidAngle(i, j), i, j);
			}
		}
		
		return omega;
	}

}

/*
 * Copyright (c) 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.xpdf.operations;

import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.metadata.AxesMetadata;

import uk.ac.diamond.scisoft.analysis.io.NexusDiffractionCalibrationReader;
import uk.ac.diamond.scisoft.xpdf.xrmc.InterpolateIntensityFromGammaDelta;


public class InterpolateXRMCFromGammaDelta extends AbstractOperation<InterpolateXRMCFromGDModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.xpdf.operations.InterpolateXRMCFromGammaDelta";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}

	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		// DetectorProperies of the target detector 
		IDiffractionMetadata dmeta = null;
		String detectorFileName = model.getDetectorFileName();
		try {
			dmeta = NexusDiffractionCalibrationReader.getDiffractionMetadataFromNexus(detectorFileName, null);
		} catch (DatasetException dSE) {
			throw new OperationException(this, "Could not get diffraction metadata from " + detectorFileName + ": " + dSE.toString());
		}
		
		if (dmeta == null || dmeta.getDetector2DProperties() == null) {
			throw new OperationException(this, "Could not get detector metadata from file " + detectorFileName);
		}
		
		DetectorProperties detProp = dmeta.getDetector2DProperties();
		
		// Axes of the source data
		AxesMetadata axMet = input.getFirstMetadata(AxesMetadata.class);
		ILazyDataset[] allAxes = axMet.getAxes();
		// First is gamma, second is delta
		Dataset gamma;
		Dataset delta;
		try {
			gamma = DatasetUtils.sliceAndConvertLazyDataset(allAxes[0]).squeeze();
			delta = DatasetUtils.sliceAndConvertLazyDataset(allAxes[1]).squeeze();
		} catch (DatasetException dE) {
			throw new OperationException(this, "Error obtaining γ, δ axes.");
		}
		
		Dataset detectorIntensity = InterpolateIntensityFromGammaDelta.calculate(detProp, DatasetUtils.convertToDataset(input), gamma, delta); 
		
		return new OperationData(detectorIntensity);
	}

}

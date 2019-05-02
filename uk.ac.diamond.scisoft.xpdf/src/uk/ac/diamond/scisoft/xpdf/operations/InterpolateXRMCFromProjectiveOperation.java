/*-
 * Copyright (c) 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.xpdf.operations;

import javax.vecmath.Vector3d;

import org.apache.commons.math3.analysis.interpolation.PiecewiseBicubicSplineInterpolator;
import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.metadata.AxesMetadata;

import uk.ac.diamond.scisoft.analysis.dataset.function.Interpolation2D;
import uk.ac.diamond.scisoft.analysis.io.NexusDiffractionCalibrationReader;

public class InterpolateXRMCFromProjectiveOperation extends AbstractOperation<InterpolateXRMCFromGDModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.xpdf.operations.InterpolateXRMCFromProjectiveOperation";
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
			if (model.getDetectorDataset() == null) {
				dmeta = NexusDiffractionCalibrationReader.getDiffractionMetadataFromNexus(detectorFileName, null);
			} else {
				dmeta = NexusDiffractionCalibrationReader.getDiffractionMetadataFromNexus(detectorFileName, null, model.getDetectorDataset());
			}
		} catch (DatasetException dSE) {
			throw new OperationException(this, "Could not get diffraction metadata from " + detectorFileName + ": " + dSE.toString());
		}
		
		if (dmeta == null || dmeta.getDetector2DProperties() == null) {
		
			dmeta = getFirstDiffractionMetadata(input);
			if (dmeta == null) {
				throw new OperationException(this, "Could not get detector metadata from file " + detectorFileName + ", and no metadata was provided.");
			}
		}
		
		DetectorProperties detProp = dmeta.getDetector2DProperties();

		// Axes of the source data
		AxesMetadata axMet = input.getFirstMetadata(AxesMetadata.class);
		ILazyDataset[] allAxes = axMet.getAxes();
		// First is yp, second is xp
		Dataset xp;
		Dataset yp;
		try {
			yp = DatasetUtils.sliceAndConvertLazyDataset(allAxes[0]).squeeze();
			xp = DatasetUtils.sliceAndConvertLazyDataset(allAxes[1]).squeeze();
		} catch (DatasetException dE) {
			throw new OperationException(this, "Error obtaining projective coordinate axes.");
		}
		
		// 1. Output coordinates on the output grid
		int[] size = new int[] {detProp.getPx(), detProp.getPy()};
		
		// 2. Input coordinates on the output grid
		Dataset xpArr = DatasetFactory.zeros(size);
		Dataset ypArr = DatasetFactory.zeros(size);
		
		for (int i = 0; i < size[0]; i++) {
			for (int j = 0; j < size[1]; j++) {
				Vector3d r = detProp.pixelPosition(j+0.5, i+0.5);
				xpArr.set(r.x/r.z, i, j);
				ypArr.set(r.y/r.z, i, j);
			}
		}
		
		// 3. Perform the interpolation
		Dataset outputIntensity = Interpolation2D.interpolate2d(xp, yp, input, xpArr, ypArr, new PiecewiseBicubicSplineInterpolator());
		
		// 4. Create the operation data and away!
		return new OperationData(outputIntensity);
	}

}

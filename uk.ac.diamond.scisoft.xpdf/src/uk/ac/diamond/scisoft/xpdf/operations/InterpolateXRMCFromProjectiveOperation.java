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

import uk.ac.diamond.scisoft.analysis.diffraction.powder.GenericPixelIntegrationCache;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegration;
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
			dmeta = NexusDiffractionCalibrationReader.getDiffractionMetadataFromNexus(detectorFileName, null);
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
		
		// TODO: Need output coordinate values on the input grid.
		// 1. Output coordinates on the output grid
		int[] size = new int[] {detProp.getPx(), detProp.getPy()};
		int nBorder = 400;
		int[] borderedSize = new int[] {size[0] + 2*nBorder, size[1] + 2*nBorder};
		Dataset iArr = DatasetFactory.zeros(borderedSize);
		Dataset jArr = DatasetFactory.zeros(borderedSize);
		
		// 2. Input coordinates on the output grid
		Dataset xpArr = DatasetFactory.zeros(borderedSize);
		Dataset ypArr = DatasetFactory.zeros(borderedSize);
		
		for (int i = 0; i < borderedSize[0]; i++) {
			for (int j = 0; j < borderedSize[1]; j++) {
				iArr.set(i-nBorder, i, j);
				jArr.set(j-nBorder, i, j);
				Vector3d r = detProp.pixelPosition(i+0.5-nBorder, j+0.5-nBorder);
				xpArr.set(r.x/r.z, i, j);
				ypArr.set(r.y/r.z, i, j);
			}
		}
		
		// 3. Input coordinate axes (xp & yp)
		
		// 4. PixelIntegrationCache to go from the output grid to the input
		GenericPixelIntegrationCache inFromOutPic = new GenericPixelIntegrationCache(ypArr, xpArr, xp, yp);

		// 5. i,j coordinates of the output detector on the input grid
		Dataset iOutOnIn = PixelIntegration.integrate(iArr, null, inFromOutPic).get(1);
		Dataset jOutOnIn = PixelIntegration.integrate(jArr, null, inFromOutPic).get(1);
		
		// 6. PixelIntegrationCache to go from input to output grid
		GenericPixelIntegrationCache outFromInPic = new GenericPixelIntegrationCache(iOutOnIn, jOutOnIn, DatasetFactory.createRange(size[0]),  DatasetFactory.createRange(size[1]));
		
		// 7. Interpolate the intensity from input to output
		Dataset outputIntensity = PixelIntegration.integrate(input, null, outFromInPic).get(1);
		
		return new OperationData(outputIntensity);
	}

}

/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.mask;

import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.BooleanDataset;
import org.eclipse.january.dataset.Comparisons;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.metadata.MaskMetadata;
import org.eclipse.january.metadata.internal.MaskMetadataImpl;

import uk.ac.diamond.scisoft.analysis.dataset.function.MakeMask;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegrationUtils;
import uk.ac.diamond.scisoft.analysis.io.DiffractionMetadata;
import uk.ac.diamond.scisoft.analysis.roi.XAxis;

/**
 * Mask a 2 dimensional Dataset based on the coordinates of the points 
 */
@Atomic
public class CoordinateMaskOperation extends
		AbstractOperation<CoordinateMaskModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.CoordinateMaskOperation";
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
	protected OperationData process(IDataset input, IMonitor monitor)
			throws OperationException {
		
		// Assume q
		XAxis theAxis = model.getCoordinateType();
		double coordinateRange[] = model.getCoordinateRange();
		//TODO: get data from an external data source, rather than the
		// diffraction calibration, if not present
		DiffractionMetadata diffractionMD = input.getFirstMetadata(DiffractionMetadata.class);

		// Generate the Dataset of the chosen coordinate
		Dataset coordinateArray;
		if (diffractionMD != null) {
			switch (theAxis) {
			case Q:
				coordinateArray = PixelIntegrationUtils.generateQArray(input.getShape(),diffractionMD);
				break;
			case ANGLE:
				// In degrees
				coordinateArray = PixelIntegrationUtils.generate2ThetaArrayRadians(input.getShape(),diffractionMD);
				coordinateArray.imultiply(180/Math.PI);
				break;
			case PIXEL:
			case RESOLUTION:
			default:
				throw new OperationException(this, "This coordinate is not yet supported");
			}
		} else {
			throw new OperationException(this, "Dataset-based masking not yet supported");

//			// Trace metadata
//			String xyFilePath = "";
//			try {
//				xyFilePath = "/home/rkl37156";//model.getFilePath();
//			} catch (Exception e) {
//				throw new OperationException(this, "Could not find " + xyFilePath);
//			}
//
//			// Load the container trace from the designated xy file
////			if (model.getDataset().length() <= 0) throw new OperationException(this, "Undefined dataset");
//			coordinateArray = DatasetUtils.sliceAndConvertLazyDataset(ProcessingUtils.getLazyDataset(this, xyFilePath, "entry1/q"/*model.getDataset()*/));

		}
		
		if (coordinateRange != null) {
		
			MakeMask maskMaker = new MakeMask(coordinateRange[0], coordinateRange[1]);
			// MakeMask makes BooleanDatasets
			BooleanDataset coordinateMask = (BooleanDataset) maskMaker.value(coordinateArray).get(0); 
			// Invert if required
			if (model.isMaskedInside())
				coordinateMask.isubtract(true);


			// Get the input mask, and combine the two masks
			IDataset inputMask = DatasetUtils.convertToDataset(getFirstMask(input));

			if (inputMask == null) {
				inputMask = coordinateMask;
			} else {
				inputMask = Comparisons.logicalAnd(inputMask, coordinateMask);
			}

			MaskMetadata maskMetadata = new MaskMetadataImpl(inputMask);
			input.setMetadata(maskMetadata);
		}		
		return new OperationData(input);
	}

}

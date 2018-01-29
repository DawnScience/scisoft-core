/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.mask;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.BooleanDataset;
import org.eclipse.january.dataset.Comparisons;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.metadata.MaskMetadata;
import org.eclipse.january.metadata.MetadataFactory;

import uk.ac.diamond.scisoft.analysis.dataset.function.MakeMask;
import uk.ac.diamond.scisoft.analysis.diffraction.QSpace;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegrationUtils;
import uk.ac.diamond.scisoft.analysis.io.DiffractionMetadata;
import uk.ac.diamond.scisoft.analysis.roi.XAxis;

/**
 * Mask a 2 dimensional Dataset based on the coordinates of the points 
 */
@Atomic
public class CoordinateMaskOperation extends
		AbstractOperation<CoordinateMaskModel, OperationData> {
	
	private volatile Dataset mask = null;
	private PropertyChangeListener listener;
	private IDiffractionMetadata metadata;
	
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
		
		if (model.getCoordinateRange() == null) return new OperationData(input);
		
		DiffractionMetadata meta = input.getFirstMetadata(DiffractionMetadata.class);
		
		if (meta == null && !model.getCoordinateType().equals(MaskAxis.PIXEL)) throw new OperationException(this, "Does not contain calibration information!");
		
		if (metadata == null) {
			metadata = meta;
			mask = null;
		} else {
			boolean dee = metadata.getDiffractionCrystalEnvironment().equals(meta.getDiffractionCrystalEnvironment());
			boolean dpe = metadata.getDetector2DProperties().equals(meta.getDetector2DProperties());
			
			if (!dpe || !dee) {
				metadata = meta;
				mask = null;
			}
		}
		
		
		Dataset coordinateMask = getMask(input);

		// Get the input mask, and combine the two masks
		IDataset inputMask = DatasetUtils.convertToDataset(getFirstMask(input));

		if (inputMask == null) {
			inputMask = coordinateMask;
		} else {
			inputMask = Comparisons.logicalAnd(inputMask, coordinateMask);
		}

		MaskMetadata maskMetadata;
		try {
			maskMetadata = MetadataFactory.createMetadata(MaskMetadata.class, inputMask);
		} catch (MetadataException e) {
			throw new OperationException(this, e);
		}
		input.setMetadata(maskMetadata);

		return new OperationData(input);
	}

	
	private Dataset getMask(IDataset input) {

		Dataset lmask = mask;
		if (lmask == null) {
			synchronized(this) {
				lmask = mask;
				if (lmask == null) {
					// Assume q
					MaskAxis theAxis = model.getCoordinateType();
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
							coordinateArray = PixelIntegrationUtils.generateRadialArray(input.getShape(), new QSpace(diffractionMD.getDetector2DProperties(), diffractionMD.getDiffractionCrystalEnvironment()), XAxis.ANGLE);
							break;
						case AZIMUTHAL_ANGLE:
							coordinateArray = PixelIntegrationUtils.generateAzimuthalArray(input.getShape(), diffractionMD, false);
							break;
						case PIXEL:
							double[] beamCentre = diffractionMD.getOriginalDetector2DProperties().getBeamCentreCoords();
							coordinateArray = DatasetFactory.zeros(DoubleDataset.class, input.getShape());
							for (int i = 0; i < input.getShape()[0]; i++)
								for (int j = 0; j < input.getShape()[1]; j++)
									// The elements of getBeamCentreCoords are transposed from those of getShape
									coordinateArray.set(Math.sqrt(square(i-beamCentre[1]) + square(j-beamCentre[0])), i, j);
							break;
						default:
							throw new OperationException(this, "This coordinate is not yet supported");
						}
					} else {
						switch (theAxis) {
						case PIXEL:
							double[] beamCentre = new double[]{input.getShape()[0]*0.5, input.getShape()[1]*0.5};
							coordinateArray = DatasetFactory.zeros(DoubleDataset.class, input.getShape());
							for (int i = 0; i < input.getShape()[0]; i++)
								for (int j = 0; j < input.getShape()[1]; j++)
									coordinateArray.set(Math.sqrt(square(i-beamCentre[0]) + square(j-beamCentre[1])), i, j);
						break;
						case ANGLE:
						case AZIMUTHAL_ANGLE:
						case Q:
						default:
							throw new OperationException(this, "Dataset-based masking not yet supported");
						}

					}
					
					MakeMask maskMaker = new MakeMask(coordinateRange[0], coordinateRange[1]);
					// MakeMask makes BooleanDatasets
					BooleanDataset coordinateMask = (BooleanDataset) maskMaker.value(coordinateArray).get(0); 
					// Invert if required
					if (model.isMaskedInside())
						coordinateMask.isubtract(true);
					
					mask = lmask = coordinateMask;
				}
			}
		}
		return lmask;
	}
	
	@Override
	public void setModel(CoordinateMaskModel model) {
		
		super.setModel(model);
		if (listener == null) {
			listener = new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					mask = null;
				}
			};
		} else {
			((AbstractOperationModel)this.model).removePropertyChangeListener(listener);
		}
		
		((AbstractOperationModel)this.model).addPropertyChangeListener(listener);
	}
	
	public enum MaskAxis {
		ANGLE,
		PIXEL,
		Q,
		AZIMUTHAL_ANGLE
	}
	
	private double square(double x) {
		return x*x;
	}
}

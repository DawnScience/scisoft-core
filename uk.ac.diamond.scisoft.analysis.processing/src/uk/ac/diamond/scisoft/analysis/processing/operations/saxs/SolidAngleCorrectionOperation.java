/*-
 * Copyright (c) 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.saxs;



import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
// Imports from org.eclipse
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;

import uk.ac.diamond.scisoft.analysis.diffraction.powder.IPixelIntegrationCache;
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegrationUtils;
import uk.ac.diamond.scisoft.analysis.processing.operations.ErrorPropagationUtils;

import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.EmptyModel;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;


// More information and the equation for the solid angle correction can be found here:
// Small-Angle X-ray Scattering at the ESRF High-Brilliance Beamline 
// Peter Bosecke and Oliver Diat, J. Appl. Cryst., 1997, 30, 867-871. 
// DOI: 10.1107/S0021889897001647 

// @author Tim Snow

public class SolidAngleCorrectionOperation extends AbstractOperation<EmptyModel, OperationData> {


	protected volatile IPixelIntegrationCache cache;
	protected volatile Dataset solidAngleCorrectionFactor;
	protected IDiffractionMetadata metadata;


	// Let's give this process an ID tag
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.saxs.SolidAngleCorrectionOperation";
	}


	// Before we start, let's make sure we know how many dimensions of data are going in...
	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}


	// ...and out
	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}


	// Now let's define the main calculation process
	@Override
	public OperationData process(IDataset dataset, IMonitor monitor) throws OperationException {
		
		IDiffractionMetadata md = getFirstDiffractionMetadata(dataset);

		if (md == null) throw new OperationException(this, "No detector geometry information!");
		
		if (metadata == null) {
			metadata = md;
			cache = null;
			solidAngleCorrection();
		} else {
			boolean dee = metadata.getDiffractionCrystalEnvironment().equals(md.getDiffractionCrystalEnvironment());
			boolean dpe = metadata.getDetector2DProperties().equals(md.getDetector2DProperties());
			
			if (!dpe || !dee) {
				metadata = md;
				cache = null;
				solidAngleCorrection();
			}
		}
		
		Dataset outputDataset = ErrorPropagationUtils.divideWithUncertainty(DatasetUtils.convertToDataset(dataset), this.solidAngleCorrectionFactor);
		copyMetadata(dataset, outputDataset);
		
		return new OperationData(outputDataset);	
	}


	public void solidAngleCorrection() {
		// Get some detector properties
		DetectorProperties detectorProperties = this.metadata.getDetector2DProperties();
		
		// Then extract them
		double verticalPixelSize = detectorProperties.getVPxSize();
		double horizontalPixelSize = detectorProperties.getHPxSize();
		double pixelArea = horizontalPixelSize * verticalPixelSize;
		double sampleToDetectorDistance = detectorProperties.getBeamCentreDistance();
		Dataset twoThetaDataset = PixelIntegrationUtils.generate2ThetaArrayRadians(this.metadata);
		
		// Then do the calculations as shown in the reference paper, equation 6
		Dataset distancesFromBeamcentre = Maths.multiply(Maths.tan(twoThetaDataset), sampleToDetectorDistance);
		Dataset hypotenusesFromBeamcentreSquared = Maths.add(Maths.power(sampleToDetectorDistance, 2.0), Maths.power(distancesFromBeamcentre, 2.0));
		Dataset areaOverHypotenuses = Maths.divide(pixelArea, hypotenusesFromBeamcentreSquared);
		
		// And store the correction factor
		this.solidAngleCorrectionFactor = Maths.multiply(areaOverHypotenuses, Maths.cos(twoThetaDataset));
	}
}
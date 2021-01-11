/*-
 * Copyright 2021 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


package uk.ac.diamond.scisoft.analysis.processing.operations.twod;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

// Imports from org.eclipse
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.IMonitor;


// Imports from uk.ac.diamond
import uk.ac.diamond.scisoft.analysis.diffraction.powder.PixelIntegrationUtils;
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.AttenuationCalculationUtils;


// @author Tim Snow


@Atomic
public class DetectorEfficiencyCalculationOperation extends AbstractOperation<DetectorEfficiencyCalculationModel, OperationData> {

	
	private IDiffractionMetadata metadata;

	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.twod.DetectorEfficiencyCalculationOperation";
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
		// First, find whether the user has manually defined an energy
		double beamEnergy = Double.NaN;

		if (model.isManualEnergyValueUsed()) {
			// If they did let's use that
			beamEnergy = model.getBeamEnergy();
			
			// Or not...
			if (beamEnergy <= 0.00 || Double.isNaN(beamEnergy)) throw new OperationException(this, "Invalid energy value specified!");
		} else {
			try {
				beamEnergy = AttenuationCalculationUtils.getEnergyFromDatasetMetadata(input);
			} catch (Exception error) {
				throw new OperationException(this, error.getMessage(), error);
			}
			
			// Might as well let the UI reflect our findings!
			model.setBeamEnergy(beamEnergy);
		}
		
		// Next, we need the two-theta array and a home for the corrected data
		IDiffractionMetadata md = getFirstDiffractionMetadata(input);
		
		if (metadata == null || !(metadata.getDetector2DProperties().equals(md.getDetector2DProperties()) &&
				metadata.getDiffractionCrystalEnvironment().equals(md.getDiffractionCrystalEnvironment()))) {
			metadata = md;
 		}
		
		Dataset output = DatasetUtils.convertToDataset(input);
		
		// Now we can apply the correction to the dataset
		try {
			Dataset transmissionFactor = DatasetFactory.createFromObject(AttenuationCalculationUtils.getTransmissionFactor(beamEnergy, model.getSensorComposition(), model.getSensorDensity(), model.getSensorThickness()));
			Dataset twoThetaDataset = PixelIntegrationUtils.generate2ThetaArrayRadians(input.getShape(), metadata);
			
			output = PixelIntegrationUtils.detectorTranmissionCorrectionWithUncertainties(output, twoThetaDataset, transmissionFactor);
		} catch (RuntimeException error) {
			throw new OperationException(this, "The attenuation factor could not be calculated");
		}
		
		// Return the dataset with the original metadata
		copyMetadata(input, output);
		
		OperationData outputDataset = new OperationData(output);
		
		if (model.isManualEnergyValueUsed() == false) {
			Map<String, Serializable> parameterMap = new HashMap<String, Serializable>();
			parameterMap.put("beamEnergy", model.getBeamEnergy());
			outputDataset.setConfiguredFields(parameterMap);
		}
		
		return outputDataset;
	}
}

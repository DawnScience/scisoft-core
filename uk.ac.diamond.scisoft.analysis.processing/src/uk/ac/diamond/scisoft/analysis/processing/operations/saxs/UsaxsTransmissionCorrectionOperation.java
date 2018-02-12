/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


package uk.ac.diamond.scisoft.analysis.processing.operations.saxs;


// Imports from uk.ac.diamond.scisoft
import uk.ac.diamond.scisoft.analysis.processing.operations.saxs.UsaxsTransmissionCorrectionModel.CorrectionType;

// Imports from org.eclipse.dawnsci
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

// Imports from org.eclipse.january
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.metadata.AxesMetadata;

// Importing the logger!
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@author Tim Snow


public class UsaxsTransmissionCorrectionOperation extends AbstractOperation<UsaxsTransmissionCorrectionModel, OperationData> {
	
	
	// First, set up a logger
	private static final Logger logger = LoggerFactory.getLogger(UsaxsTransmissionCorrectionOperation.class);
	
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.saxs.UsaxsTransmissionCorrectionOperation";
	}
	
	
	@Override
	public OperationRank getInputRank() {
		return OperationRank.ONE;
	}
	
	
	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}
	
	
	@Override
	protected OperationData process(IDataset input, IMonitor monitor) {
		// First, find out which route the user would like to go down
		CorrectionType correctionToApply = model.getTypePicked();
		Dataset output = null;

		if (correctionToApply == CorrectionType.QZERO) {
		// If we're dividing by q-zero (the maximum recorded intensity), we have to find it!
		double maxIntensity = input.max(null).doubleValue();
		double intensityCorrection = 1.00 / maxIntensity;
		
		// Then, with the value in hand let's divide this from the input data
		output = Maths.multiply(input, intensityCorrection);
		
		} else if (correctionToApply == CorrectionType.INTEGRATION) {
			// If we're dividing by the integrated intensity, first we need the axis data
			IDataset axisDataset = (IDataset) input.getFirstMetadata(AxesMetadata.class).getAxes()[0];
			double integratedIntensity = 0.00;
			
			// Then we can loop through the data and pull out the integrated intensity
			for (int loopIter = 0; loopIter < input.getSize(); loopIter ++) {
				integratedIntensity += input.getDouble(loopIter) * Math.abs(axisDataset.getDouble(loopIter));
			}
			
			// Then, with the value in hand let's divide this from the input data
			output = Maths.divide(input, integratedIntensity);
			
		} else {
			// Oops, something's gone wrong! Better let the user know...
			logger.error("Desired transmission correction option not found!");
		}
		
		// and return it!
		copyMetadata(input, output);
		return new OperationData(output);
	}
}

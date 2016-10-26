/*-
 * Copyright (c) 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations;


//import org.apache.commons.lang.ArrayUtils;

// Imports from org.eclipse
import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.FloatDataset;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MaskMetadata;
import org.eclipse.january.metadata.MetadataFactory;
import org.eclipse.dawnsci.analysis.dataset.roi.RingROI;
import org.eclipse.dawnsci.analysis.dataset.roi.SectorROI;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

// Imports from uk.ac.diamond
import uk.ac.diamond.scisoft.analysis.roi.ROIProfile;
import uk.ac.diamond.scisoft.analysis.processing.operations.HermanOrientationModel.NumberOfPis;


// More information and the equation for the Herman Orientation Factor can be found in:
// Crystallization and orientation studies in polypropylene/single wall carbon nanotube composite
// A. R. Bhattacharyya, T. Sreekumar, T. Liu, S. Kumar, L. M. Ericson, R. H. Hauge and R. E. Smalley, Polymer, 2003, 44, 2373-2377.
// DOI: 10.1016/S0032-3861(03)00073-9 

// @author Tim Snow

// The operation for a DAWN process to perform a Herman Orientation calculation on a given image
public class HermanOrientationOperation extends AbstractOperation<HermanOrientationModel, OperationData> {


	// Let's give this process an ID tag
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.HermanOrientationOperation";
	}


	// Before we start, let's make sure we know how many dimensions of data are going in...
	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}


	// ...and out
	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}


	// Now let's define the main calculation process
	@Override
	public OperationData process(IDataset dataset, IMonitor monitor) throws OperationException {

		// Lets apply an image mask, if present, using NaN's
		Dataset nanMaskDataset = DatasetUtils.convertToDataset(dataset);

		// If there is masking data we shall replaced the masked values by NaN's
		Object nanMaskValue = Double.NaN;

		// Better make sure it's the right type of NaN though
		if (dataset.getFirstMetadata(MaskMetadata.class) != null) {
			if (nanMaskDataset.getClass() == DoubleDataset.class)
				nanMaskValue = Double.NaN;
			else if (nanMaskDataset.getClass() == FloatDataset.class)
				nanMaskValue = Float.NaN;
			else
				nanMaskValue = 0;

			// Loop over the mask and the data and replace masked values by the NaN type chosen above 
			Dataset mask = DatasetUtils.convertToDataset(dataset.getFirstMetadata(MaskMetadata.class).getMask());
			for (IndexIterator iter = nanMaskDataset.getIterator(); iter.hasNext();) {
				if (!(boolean) mask.getElementBooleanAbs(iter.index))
					nanMaskDataset.setObjectAbs(iter.index, nanMaskValue);
			}
		}
		// Now any masked pixel has the value NaN and will not be considered for subsequent evaluation


		// With this in mind, let's move on to the ROI that the HoF calculation will be performed on
		// First, let's check we've got the right kind of ROI
		if (!(model.getRegion() instanceof RingROI)) {
			throw new OperationException(this, new IllegalArgumentException("The ROI must be a ring ROI"));
		}

		SectorROI hermanSector = new SectorROI();
		RingROI modelRingROI = (RingROI) model.getRegion();
		
		// Then we'll set the centre point
		double[] centrePoint = modelRingROI.getPoint();
		hermanSector.setPoint(centrePoint[0], centrePoint[1]);

		// Then set the radius of interest
		double[] radiiPoint = modelRingROI.getRadii();
		hermanSector.setRadii(radiiPoint[0], radiiPoint[1]);
		
		// And finally, let's consider how much of the ring we're going to be evaluating as a function of pi
		NumberOfPis piEnum = model.getIntegrationRange();
		double piMultiplier = ((double) piEnum.getNumberOfPis()) / 2;
		double hermanPiRange = piMultiplier * Math.PI;

		// Before setting the angle to investigate
		double integrationStartInRadians = (Math.PI / 180) * model.getIntegrationStartAngle();
		hermanSector.setAngles(integrationStartInRadians, integrationStartInRadians + hermanPiRange);

		// Ok, with the mask applied and the ROI defined it's time to reduce the data
		Dataset[] reducedDataset = ROIProfile.sector(DatasetUtils.convertToDataset(dataset), null, hermanSector, false, true, false);
		
		// Then we can take the data and turn it into single dataset
		IDataset reducedData = reducedDataset[1];
		
		// Let's find out how many points of data we have to work with
		int dataSize = reducedData.getSize();

		// For plotting later, we should prepare to have the x axis values to hand...
		Dataset xAxisValues = DatasetFactory.createRange(dataSize, Dataset.FLOAT64);
		xAxisValues.setName("Angle (°)");

		// Set up the data value array
		double integrationRadianStep = hermanPiRange / dataSize;
		double fractionNumerator = 0.00;
		double fractionDenominator = 0.00;
		double loopStepRadianValue = 0.00;
		double loopStepDegreeValue = 0.00;

		// Now let's set up the final variables required for the calculation
		double hermanCReciprocal = 1 / model.getHermanCValue();

		// Do the HoF mathematics
		// Level two of the loop, this is to loop through the data points in the frame
		for(int loopIter = 0; loopIter < dataSize; loopIter++) {
			// To save this from being calculated many times
			loopStepRadianValue = integrationStartInRadians + (integrationRadianStep * loopIter);
			loopStepDegreeValue = loopStepRadianValue / (Math.PI / 180);
			// The component parts of the fraction
			fractionNumerator += Math.pow(Math.cos(loopStepRadianValue), 2) * Math.sin(loopStepRadianValue) * reducedData.getDouble(loopIter);
			fractionDenominator += reducedData.getDouble(loopIter) * Math.sin(loopStepRadianValue);
			xAxisValues.set(loopStepDegreeValue, loopIter);
		}
		
		// Perform the calculation for this frame
		double hermanOrientationFactor = hermanCReciprocal * (((3 * (fractionNumerator / fractionDenominator)) - 1) / 2);
		
		// Must move the HoF into a dataset for DAWN
		// First up, let's create a one element dataset of a zero
		int[] datasetSize = {1};
		Dataset hermanOrientationDataset = DatasetFactory.zeros(1, datasetSize, Dataset.FLOAT64);
		
		hermanOrientationDataset.setName("Herman Orientation Factor");
		// Now we can stick in the calculated factor
		hermanOrientationDataset.set(hermanOrientationFactor, 0);

		// Also, as we have the x axis values and the reduced data, let's set up the plot axes for the user to look at 
		AxesMetadata metadata = dataset.getFirstMetadata(AxesMetadata.class);

		// First up, if there's no metadata yet, let's make a space for some
		if (metadata == null) {
			try {
				metadata = MetadataFactory.createMetadata(AxesMetadata.class, 1);
			} catch (MetadataException e) {
				throw new OperationException(this, e);
			}
			metadata.setAxis(0, DatasetFactory.createRange(dataSize, Dataset.FLOAT64));
		}
		
		// If there's no data, or if it's of zero length, let's correct that
		if (metadata.getAxis(0) == null || metadata.getAxis(0).length == 0) {
			metadata.setAxis(0, DatasetFactory.createRange(dataSize, Dataset.FLOAT64));
		}
		
		// Now we can set the axis values to those calculated
		metadata.setAxis(0, xAxisValues);
		// And stick this metadata into the reduced data
		reducedData.setMetadata(metadata);
		// Give the data axis a name too
		reducedData.setName("Intensity (Counts)");		
		
		// Finally, we can create a new OperationData object for DAWN and return the Herman Orientation Factor
		OperationData toReturn = new OperationData();
		// Fill it
		toReturn.setData(reducedData);
		
		toReturn.setAuxData(hermanOrientationDataset);
		// And then return it		
		return toReturn;	
	}
}

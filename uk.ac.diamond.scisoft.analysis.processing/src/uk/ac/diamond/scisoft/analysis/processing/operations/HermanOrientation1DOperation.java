/*-
 * Copyright (c) 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations;


// Imports from java
import java.util.Arrays;

// Imports from org.apache
import org.apache.commons.lang.ArrayUtils;

// Imports from org.eclipse.january
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.MetadataException;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.metadata.MetadataFactory;

// Imports from org.eclipse.dawnsci
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

// Imports from uk.ac.diamond
import uk.ac.diamond.scisoft.analysis.processing.operations.HermanOrientation1DModel.NumberOfPis;


// More information and the equation for the Herman Orientation Factor can be found in:
// Crystallization and orientation studies in polypropylene/single wall carbon nanotube composite
// A. R. Bhattacharyya, T. Sreekumar, T. Liu, S. Kumar, L. M. Ericson, R. H. Hauge and R. E. Smalley, Polymer, 2003, 44, 2373-2377.
// DOI: 10.1016/S0032-3861(03)00073-9 

// @author Tim Snow

// The operation for a DAWN process to perform a Herman Orientation calculation on a given reduced dataset
public class HermanOrientation1DOperation extends AbstractOperation<HermanOrientation1DModel, OperationData> {


	// Let's give this process an ID tag
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.HermanOrientation1DOperation";
	}


	// Before we start, let's make sure we know how many dimensions of data are going in...
	@Override
	public OperationRank getInputRank() {
		return OperationRank.ONE;
	}


	// ...and out
	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}


	// Now let's define the main calculation process
	@Override
	public OperationData process(IDataset originalDataset, IMonitor monitor) throws OperationException {
		// Remove a dimension of the dataset and get the axes attached to it too
		IDataset dataDataset = originalDataset.squeeze();
		IDataset axesDataset = (IDataset) dataDataset.getFirstMetadata(AxesMetadata.class).getAxes()[0];
		
		// Get the length of the dataset
		int dataLength = dataDataset.getSize();
		
		// Let's consider how much of the ring we're going to be evaluating as a function of pi
		NumberOfPis piEnum = model.getIntegrationRange();
		double piMultiplier = ((double) piEnum.getNumberOfPis()) / 2;
		double hermanPiRange = piMultiplier * Math.PI;
		double integrationRadianStep = 0.00;
		
		// Before setting the angle to investigate
		double integrationStartInDegrees = model.getIntegrationStartAngle();
		double integrationStopInDegrees = (180 / Math.PI) * hermanPiRange;
		
		// Prepare to move out of the dataset and into an array
		float axes[] = new float[dataLength];
		float data[] = new float[dataLength];
		int startIndex = 0;
		int endIndex = 0;
		
		// Get the data into the array
		for (int loopIter = 0; loopIter < dataLength; loopIter ++) {
			axes[loopIter] = axesDataset.getFloat(loopIter);
			data[loopIter] = dataDataset.getFloat(loopIter);
		}

		// Find the lower limit
		for (int loopIter = 0; loopIter < dataLength; loopIter ++) {
			if (axes[loopIter] >= integrationStartInDegrees) {
				startIndex = loopIter;
				break;
			}
		}
		
		// Find the upper limit
		if (integrationStopInDegrees >= 360.0) {
			integrationStopInDegrees = integrationStopInDegrees - 360.0;
		}
		
		for (int loopIter = 0; loopIter < dataLength; loopIter ++) {
			if (axes[loopIter] >= integrationStopInDegrees) {
				endIndex = loopIter;
				break;
			}
		}
				
		// Now let's calculate the Herman Orientation Factor
		double hermanOrientationFactor = 0;
		
		if (integrationStopInDegrees <= 360.0) {
			// Either if both limits are within 360 degrees
			data = Arrays.copyOfRange(data, startIndex, endIndex);
			integrationRadianStep = hermanPiRange / data.length;
			hermanOrientationFactor = hermanIntegrator(data, integrationRadianStep);
		}
		else {
			// Or if we have to loop around encompassing two ranges
			data = ArrayUtils.addAll(Arrays.copyOfRange(data, 0, endIndex), Arrays.copyOfRange(data, startIndex, dataLength));
			integrationRadianStep = hermanPiRange / data.length;
			hermanOrientationFactor = hermanIntegrator(data, integrationRadianStep);			
		}
		
		// Must move the HoF into a dataset for DAWN
		// First up, let's create a one element dataset of a zero
		int[] datasetSize = {1};
		Dataset hermanOrientationDataset = DatasetFactory.zeros(1, datasetSize, Dataset.FLOAT64);
		
		hermanOrientationDataset.setName("Herman Orientation Factor");
		// Now we can stick in the calculated factor
		hermanOrientationDataset.set(hermanOrientationFactor, 0);

		// Let's create the new axis data for the HoF plot, in radians
		AxesMetadata metadata;
		
		// A try catch clause is needed for some reason...
		try {
			metadata = MetadataFactory.createMetadata(AxesMetadata.class, 1);
		} catch (MetadataException metadataError) {
			throw new OperationException(this, metadataError);
		}
		
		// Create the axis as a fixed length tied to the data array
		metadata.setAxis(0, DatasetFactory.createRange(0.00, hermanPiRange, integrationRadianStep, Dataset.ARRAYFLOAT64));
		
		// Create the data dataset...
		Dataset a = DatasetFactory.createFromObject(Dataset.ARRAYFLOAT64, data);
		// and stick in the axis metadata
		a.setMetadata(metadata);
		
		// Finally, we can create a new OperationData object for DAWN and return the Herman Orientation Factor
		OperationData toReturn = new OperationData();
		// Fill it
		toReturn.setData(a);
		toReturn.setAuxData(hermanOrientationDataset);
		
		// And then return it		
		return toReturn;	
	}	
	
	
	private double hermanIntegrator(float[] data, double integrationRadianStep){
		// Set up the data value array
		double fractionNumerator = 0.00;
		double fractionDenominator = 0.00;
		double loopStepRadianValue = 0.00;

		// Now let's set up the final variables required for the calculation
		double hermanCReciprocal = 1 / model.getHermanCValue();

		// Do the HoF mathematics
		// Level two of the loop, this is to loop through the data points in the frame
		for(int loopIter = 0; loopIter < data.length; loopIter++) {
			// To save this from being calculated many times
			// The component parts of the fraction
			fractionNumerator += Math.pow(Math.cos(loopStepRadianValue), 2) * Math.sin(loopStepRadianValue) * data[loopIter];
			fractionDenominator += data[loopIter] * Math.sin(loopStepRadianValue);
			loopStepRadianValue += integrationRadianStep;
		}
		
		// Perform the calculation for this frame
		return (hermanCReciprocal * (((3 * (fractionNumerator / fractionDenominator)) - 1) / 2));
	}
}

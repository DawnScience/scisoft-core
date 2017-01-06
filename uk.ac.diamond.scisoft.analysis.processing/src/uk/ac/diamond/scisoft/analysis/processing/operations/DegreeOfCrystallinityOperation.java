/*-
 * Copyright (c) 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations;

// Imports from java
import java.util.Arrays;

//Imports from org.eclipse.dawnsci
import org.eclipse.dawnsci.analysis.dataset.roi.ROISliceUtils;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

//Imports from org.eclipse.january
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.dataset.DatasetFactory;

//Imports from org.eclipse.dawnsci
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;


//More information and the equation for the Degree of Crystallinity operation can be found in:
//The SAXS Guide - 3rd Edition - Heimo Schnablegger & Yashveer Singh - ISBN 9783900323882 - Published by Anton Paar GmbH, Austria
//Section 5.4.10 - Degree of Crystallinity

// @author Tim Snow

// The operation for a DAWN process to perform a Degree of Crystallinity calculation on a given reduced dataset
public class DegreeOfCrystallinityOperation extends AbstractOperation<DegreeOfCrystallinityModel, OperationData> {

	// Let's give this process an ID tag
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.DegreeOfCrystallinityOperation";
	}

	// Before we start, let's make sure we know how many dimensions of data are
	// going in...
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
		// Fetch the background dataset
		IDataset backgroundDataset = ProcessingUtils.getDataset(this, model.getBackgroundScanFilePath(),
				model.getBackgroundDataPath());

		// Remove a dimension of the dataset and get the axes attached to it too
		IDataset dataDataset = originalDataset.squeeze();
		IDataset axesDataset = (IDataset) dataDataset.getFirstMetadata(AxesMetadata.class).getAxes()[0];
		IDataset amorphousDataset = backgroundDataset.squeeze();

		if (backgroundDataset.getSize() != originalDataset.getSize()) {
			System.out.print("Background scan and data scan of different sizes");
			return null;
		}

		// Get the user supplied integration range and prepare an array for the
		// limits
		double[] integrationRange = model.getIntegrationRange();
		int[] indexes = new int[2];

		// If the user HAS supplied some values, get them and match them to the
		// actual axis
		if (integrationRange != null) {
			integrationRange = integrationRange.clone();
			Arrays.sort(integrationRange);
			indexes = new int[2];
			indexes[0] = ROISliceUtils.findPositionOfClosestValueInAxis(axesDataset, integrationRange[0]);
			indexes[1] = ROISliceUtils.findPositionOfClosestValueInAxis(axesDataset, integrationRange[1]);
			Arrays.sort(indexes);
		} else {
			// If the user hasn't, use the whole array
			indexes[0] = 0;
			indexes[1] = axesDataset.getSize() - 1;
		}

		// Prepate the variables for filling
		double qSquaredSampleMinusAmorphousIntegral = 0.00;
		double qSquaredSampleIntegral = 0.00;

		// Do the integrationHerman Orientation Factor
		for (int loopIter = indexes[0]; loopIter < indexes[1]; loopIter++) {
			qSquaredSampleMinusAmorphousIntegral += Math.pow(axesDataset.getDouble(loopIter), 2)
					* (dataDataset.getDouble(loopIter) - amorphousDataset.getDouble(loopIter));
			qSquaredSampleIntegral += Math.pow(axesDataset.getDouble(loopIter), 2) * dataDataset.getDouble(loopIter);
		}

		// Finally, calculate the degree of crystallinity
		double degreeOfCrystallinity = qSquaredSampleMinusAmorphousIntegral / qSquaredSampleIntegral;

		// Must move the DoC into a dataset for DAWN
		// First up, let's create a one element dataset of a zero
		int[] datasetSize = {1};
		Dataset degreeOfCrystallinityDataset = DatasetFactory.zeros(1, datasetSize, Dataset.FLOAT64);
		
		degreeOfCrystallinityDataset.setName("Degree of Crystallinity");
		// Now we can stick in the calculated factor
		degreeOfCrystallinityDataset.set(degreeOfCrystallinity, 0);
		
		// Finally, we can create a new OperationData object for DAWN and return
		// the degree of crystallinity
		OperationData toReturn = new OperationData();
		// Fill it
		toReturn.setData(originalDataset);
		toReturn.setAuxData(degreeOfCrystallinityDataset);
		// And then return it
		return toReturn;
	}
}
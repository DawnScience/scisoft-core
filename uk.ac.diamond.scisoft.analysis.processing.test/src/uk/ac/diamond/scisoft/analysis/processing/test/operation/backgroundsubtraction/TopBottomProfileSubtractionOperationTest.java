/*-
 * Copyright 2020 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

// @author Benedikt Daurer
package uk.ac.diamond.scisoft.analysis.processing.test.operation.backgroundsubtraction;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceInformation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.asserts.TestUtils;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.junit.Test;
import uk.ac.diamond.scisoft.analysis.processing.operations.backgroundsubtraction.TopBottomProfileSubtractionOperation;
import uk.ac.diamond.scisoft.analysis.processing.operations.MetadataUtils;
import uk.ac.diamond.scisoft.analysis.processing.operations.backgroundsubtraction.TopBottomProfileSubtractionModel;

public class TopBottomProfileSubtractionOperationTest {
	
	// Test simple case
	@Test
	public void testProcess2D() {
		TopBottomProfileSubtractionOperation subtractCommonLineOperation = new TopBottomProfileSubtractionOperation();
		
		// Setup the model
		TopBottomProfileSubtractionModel subtractCommonLineModel = new TopBottomProfileSubtractionModel();
		subtractCommonLineModel.setWindowFraction(0.5);
		subtractCommonLineOperation.setModel(subtractCommonLineModel);
		
		// Create test datasets
		Dataset zeroData = DatasetFactory.zeros(21,25);
		Dataset testData = zeroData.clone();
		for (int y = 0; y < 10; y++) {
			for (int x = 5; x < 15; x++) {
				testData.set(2,y,x);
			}
		}
		for (int y = 10; y < 21; y++) {
			for (int x = 5; x < 15; x++) {
				testData.set(3,y,x);
			}
		}
		
		SliceFromSeriesMetadata sliceSeriesMetadata = new SliceFromSeriesMetadata((SliceInformation) null);
		testData.addMetadata(sliceSeriesMetadata);
		IDataset xAxis = DatasetFactory.createRange(25);
		IDataset yAxis = DatasetFactory.createRange(21);
		MetadataUtils.setAxes(testData,yAxis,xAxis);
		testData.addMetadata(sliceSeriesMetadata);
		
		// execute the common line operation
		IMonitor monitor = new IMonitor.Stub();
		OperationData returnedResult  = subtractCommonLineOperation.execute(testData, monitor);
		
		// Check the result
		TestUtils.assertDatasetEquals(zeroData, DatasetUtils.convertToDataset(returnedResult.getData()), true, 1e-5, 1e-5);
	}
	
	
}
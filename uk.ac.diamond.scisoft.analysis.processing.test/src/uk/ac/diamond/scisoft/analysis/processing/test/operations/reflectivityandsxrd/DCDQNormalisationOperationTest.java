/*-
 * Copyright 2019 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

// @author Andrew McCluskey

package uk.ac.diamond.scisoft.analysis.processing.test.operations.reflectivityandsxrd;

import static org.junit.Assert.assertEquals;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceInformation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;
import org.eclipse.january.asserts.TestUtils;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.processing.operations.reflectivityandsxrd.DCDQNormalisationModel;
import uk.ac.diamond.scisoft.analysis.processing.operations.reflectivityandsxrd.DCDQNormalisationOperation;

//Now the testing class
public class DCDQNormalisationOperationTest {
	// First test, let's make sure that the operation ID is as expected
	@Test
	public void testGetId() {
		assertEquals("DCD Q Normalisation Operation ID String not as expected", "uk.ac.diamond.scisoft.analysis.processing.operations.reflectivityandsxrd.DCDQNormalisationOperation", new DCDQNormalisationOperation().getId());
	}
	
	@Test
	public void testGetInputRank() {
		assertEquals(OperationRank.ANY, new DCDQNormalisationOperation().getInputRank());
	}
	
	@Test
	public void testGetOutputRank() {
		assertEquals(OperationRank.SAME, new DCDQNormalisationOperation().getOutputRank());
	}
	
	@Test 
	public void testProcessNice() throws MetadataException {
		DCDQNormalisationOperation dcdQNormalisationOperation = initialiseOperation("testfiles/dcdnormalisationtest.dat", "adc2", "qdcd_");		
		Dataset testData = initialiseTestData();
		IMonitor monitor = new IMonitor.Stub();

		OperationData resultData = dcdQNormalisationOperation.execute(testData, monitor);
		// Now some result data needs to be created
		Dataset expectedData = Maths.divide(DatasetFactory.ones(5), 2.37468765e8 / 2.1884084347272727E8);
		
		TestUtils.assertDatasetEquals(DatasetUtils.convertToDataset(resultData.getData()), expectedData);
	}
	
	@Test 
	public void testProcessOutOfRange() throws MetadataException {
		DCDQNormalisationOperation dcdQNormalisationOperation = initialiseOperation("testfiles/dcdnormalisationtest.dat", "adc2", "qdcd_");		
		Dataset testData = initialiseTestDataOutOfRange();
		IMonitor monitor = new IMonitor.Stub();

		OperationData resultData = dcdQNormalisationOperation.execute(testData, monitor);
		// Now some result data needs to be created
		Dataset expectedData = Maths.divide(DatasetFactory.ones(5), 2.46123243E8 / 2.1884084347272727E8);
		
		TestUtils.assertDatasetEquals(DatasetUtils.convertToDataset(resultData.getData()), expectedData);
	}
	
	@Test(expected = OperationException.class)
	public void testProcessBadIName() throws MetadataException {
		DCDQNormalisationOperation dcdQNormalisationOperation = initialiseOperation("testfiles/dcdnormalisationtest.dat", "not_there", "qdcd_");		
		Dataset testData = initialiseTestData();
		IMonitor monitor = new IMonitor.Stub();

		OperationData resultData = dcdQNormalisationOperation.execute(testData, monitor);
	}
	
	@Test(expected = OperationException.class)
	public void testProcessBadQName() throws MetadataException {
		DCDQNormalisationOperation dcdQNormalisationOperation = initialiseOperation("testfiles/dcdnormalisationtest.dat", "adc2", "not_there");		
		Dataset testData = initialiseTestData();
		IMonitor monitor = new IMonitor.Stub();

		OperationData resultData = dcdQNormalisationOperation.execute(testData, monitor);
	}
	
	@Test(expected = OperationException.class)
	public void testProcessBadFilePath() throws MetadataException {
		DCDQNormalisationOperation dcdQNormalisationOperation = initialiseOperation("testfiles/notafile.dat", "adc2", "qdcd_");
		Dataset testData = initialiseTestData();
		IMonitor monitor = new IMonitor.Stub();

		OperationData resultData = dcdQNormalisationOperation.execute(testData, monitor);
	}
	
	private Dataset initialiseTestData() throws MetadataException {
		Dataset testData = DatasetFactory.ones(5);
		
		AxesMetadata axesMetadata = MetadataFactory.createMetadata(AxesMetadata.class, 1);	
		Dataset sliceAxis = DatasetFactory.createFromObject(0.5);
		axesMetadata.setAxis(0, sliceAxis);	
		SliceFromSeriesMetadata sliceSeriesMetadata = new SliceFromSeriesMetadata((SliceInformation) null);
		testData.addMetadata(sliceSeriesMetadata);
		testData.setMetadata(axesMetadata);
		return testData;
	}
	
	private Dataset initialiseTestDataOutOfRange() throws MetadataException {
		Dataset testData = DatasetFactory.ones(5);
		
		AxesMetadata axesMetadata = MetadataFactory.createMetadata(AxesMetadata.class, 1);	
		Dataset sliceAxis = DatasetFactory.createFromObject(0.0005);
		axesMetadata.setAxis(0, sliceAxis);	
		SliceFromSeriesMetadata sliceSeriesMetadata = new SliceFromSeriesMetadata((SliceInformation) null);
		testData.addMetadata(sliceSeriesMetadata);
		testData.setMetadata(axesMetadata);
		return testData;
	}
	
	private DCDQNormalisationOperation initialiseOperation(String filePath, String iName, String qName) {
		DCDQNormalisationOperation dcdQNormalisationOperation = new DCDQNormalisationOperation();
		
		DCDQNormalisationModel dcdQNormalisationModel = new DCDQNormalisationModel();
		
		dcdQNormalisationModel.setFilePath(filePath);
		dcdQNormalisationModel.setDatasetIName(iName);
		dcdQNormalisationModel.setDatasetQName(qName);
		
		dcdQNormalisationOperation.setModel(dcdQNormalisationModel);
		return dcdQNormalisationOperation;
	}
}
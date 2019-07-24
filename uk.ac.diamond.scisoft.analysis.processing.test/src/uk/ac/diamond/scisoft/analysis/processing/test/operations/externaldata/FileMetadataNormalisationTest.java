/*-
 * Copyright 2019 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

// @author Andrew McCluskey

package uk.ac.diamond.scisoft.analysis.processing.test.operations.externaldata;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceInformation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;
import org.eclipse.january.asserts.TestUtils;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.metadata.IMetadata;
import org.eclipse.january.metadata.MetadataFactory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.processing.operations.externaldata.FileMetadataNormalisation;
import uk.ac.diamond.scisoft.analysis.processing.operations.externaldata.FileMetadataModel;

public class FileMetadataNormalisationTest {
	
	private static transient final Logger logger = LoggerFactory.getLogger(FileMetadataNormalisationTest.class);
	
	@Test
	public void testGetId() {
		assertEquals("File Metadata Normalisation ID String not as expected", "uk.ac.diamond.scisoft.analysis.processing.operations.externaldata.FileMetadataNormalisation", new FileMetadataNormalisation().getId());
	}
	
	@Test
	public void testGetInputRank() {
		assertEquals(OperationRank.ANY, new FileMetadataNormalisation().getInputRank());
	}
	
	@Test
	public void testGetOutputRank() {
		assertEquals(OperationRank.SAME, new FileMetadataNormalisation().getOutputRank());
	}
		
	@Test
	public void testProcessInputMetadata() {
		FileMetadataNormalisation fileMetadataNormalisation = new FileMetadataNormalisation();
		
		IDataset testData = Maths.add(DatasetFactory.zeros(10), 10);
		Map<String,Double> mData = new HashMap<String,Double>();
		mData.put("divisor", 2.);
		IMetadata metadata = null;
		try {
			metadata = MetadataFactory.createMetadata(IMetadata.class, mData);
		} catch (MetadataException e) {
			logger.error("Metadata not created", e);
		}
		testData.setMetadata(metadata);
		SliceFromSeriesMetadata sliceSeriesMetadata = new SliceFromSeriesMetadata((SliceInformation) null);
		testData.addMetadata(sliceSeriesMetadata);
		
		FileMetadataModel fileMetadataModel = new FileMetadataModel();
		fileMetadataModel.setMetadataName("divisor");
		fileMetadataNormalisation.setModel(fileMetadataModel);
		
		// We shall then create a monitor to satisfy the operation arguments
		IMonitor monitor = new IMonitor.Stub();
		
		OperationData returnedResult = fileMetadataNormalisation.execute(testData, monitor);
		IDataset expectedResult = Maths.add(DatasetFactory.zeros(10), 5);
		TestUtils.assertDatasetEquals((Dataset) expectedResult, (Dataset) returnedResult.getData());
	}
	
}

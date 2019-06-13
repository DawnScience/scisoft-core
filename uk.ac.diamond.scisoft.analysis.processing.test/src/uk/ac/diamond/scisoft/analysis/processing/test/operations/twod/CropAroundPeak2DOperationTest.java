/*-
 * Copyright 2019 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

// @author Andrew McCluskey

package uk.ac.diamond.scisoft.analysis.processing.test.operations.twod;

//Import from org.junit
import static org.junit.Assert.assertEquals;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SourceInformation;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;
import org.junit.Test;

//Imports from uk.ac.diamond
import uk.ac.diamond.scisoft.analysis.processing.operations.twod.CropAroundPeak2DOperation;
import uk.ac.diamond.scisoft.analysis.processing.operations.twod.CropAroundPeak2DModel;

//Now the testing class
public class CropAroundPeak2DOperationTest {
	
	// First test, let's make sure that the operation ID is as expected
	@Test
	public void testGetId() {
		assertEquals("Crop Around Peak 2D Operation ID String not as expected", "uk.ac.diamond.scisoft.analysis.processing.operations.twod.CropAroundPeak2DOperation", new CropAroundPeak2DOperation().getId());
	}
	
	@Test
	public void testGetInputRank() {
		assertEquals(OperationRank.TWO, new CropAroundPeak2DOperation().getInputRank());
	}
	
	@Test
	public void testGetOutputRank() {
		assertEquals(OperationRank.TWO, new CropAroundPeak2DOperation().getOutputRank());
	}
		
	// Second test will actually probe the addition with uncertainties
	@Test
	public void testProcess() throws DatasetException {
		// First a relevant operation object will be created
		CropAroundPeak2DOperation cropAroundPeak2DOperation = new CropAroundPeak2DOperation();
		
		// Now some data needs to be made
		Dataset startData = DatasetFactory.ones(500, 500);
		startData.set(100, 50, 50);
		
		Dataset sliceAxis = DatasetFactory.createFromObject(0.008);
		
		AxesMetadata ssmParentAxesMetadata = MetadataFactory.createMetadata(AxesMetadata.class, 1);
		
		ssmParentAxesMetadata.setAxis(0, sliceAxis);
		ILazyDataset ssmParent = DatasetFactory.createLinearSpace(DoubleDataset.class, 0, 10, 10);
		ssmParent.setMetadata(ssmParentAxesMetadata);
		SourceInformation si = new SourceInformation("test", "test", ssmParent);
		SliceFromSeriesMetadata ssm2 = new SliceFromSeriesMetadata(si);
		startData.setMetadata(ssm2);
		
		CropAroundPeak2DModel cropAroundPeak2DModel = new CropAroundPeak2DModel();
		cropAroundPeak2DModel.setXBoxLength(20);
		cropAroundPeak2DModel.setYBoxLength(20);
		cropAroundPeak2DOperation.setModel(cropAroundPeak2DModel);
		
		IMonitor monitor = new IMonitor.Stub();
		OperationData resultOD = cropAroundPeak2DOperation.execute(startData, monitor);
		
		IDataset resultData = resultOD.getData();
		
		assertEquals(20, resultData.getShape()[0]);
		assertEquals(20, resultData.getShape()[1]);
		assertEquals(100., resultData.max(true));
		assertEquals(9, resultData.maxPos(true)[0]);
		assertEquals(9, resultData.maxPos(true)[1]);
	}
}
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
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SourceInformation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;
import org.eclipse.january.asserts.TestUtils;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;
import org.junit.Test;
import uk.ac.diamond.scisoft.analysis.processing.operations.reflectivityandsxrd.GeometricCorrectionModel;
import uk.ac.diamond.scisoft.analysis.processing.operations.reflectivityandsxrd.AreaCorrectionOperation;
public class AreaCorrectionOperationTest {
	
	// Numbers that will be used in the test
	static final double START = 0.;
	static final double STOP = 10.;
	static final int LENGTH = 10;
	static final double TOADD = 10.;	
	static final double TOADDUNCERTAINTY = 0.1;
	
	// First test, let's make sure that the operation ID is as expected
	@Test
	public void testGetId() {
		assertEquals("Area Correction Operation ID String not as expected", "uk.ac.diamond.scisoft.analysis.processing.operations.reflectivityandsxrd.AreaCorrectionOperation", new AreaCorrectionOperation().getId());
	}
	
	@Test
	public void testGetInputRank() {
		assertEquals(OperationRank.ANY, new AreaCorrectionOperation().getInputRank());
	}
	
	@Test
	public void testGetOutputRank() {
		assertEquals(OperationRank.SAME, new AreaCorrectionOperation().getOutputRank());
	}
		
	// Test when the angle is below that required for spill over the data should not change
	@Test
	public void testExecute() throws MetadataException {
		// First a relevant operation object will be created
		AreaCorrectionOperation areaCorrectionOperation = new AreaCorrectionOperation();
		
		// Now some data needs to be made
		IDataset testData = DatasetFactory.ones(LENGTH, LENGTH);
		IDataset testErrors = DatasetFactory.ones(LENGTH, LENGTH);
		testData.setErrors(testErrors);
		
		Dataset sliceAxis = DatasetFactory.createFromObject(0.008);
		
		AxesMetadata ssmParentAxesMetadata = MetadataFactory.createMetadata(AxesMetadata.class, 1);
		
		ssmParentAxesMetadata.setAxis(0, sliceAxis);
		ILazyDataset ssmParent = DatasetFactory.createLinearSpace(DoubleDataset.class, START*10, STOP*10, LENGTH*2);
		ssmParent.setMetadata(ssmParentAxesMetadata);
		SourceInformation si = new SourceInformation("test", "test", ssmParent);
		SliceFromSeriesMetadata ssm2 = new SliceFromSeriesMetadata(si);
		testData.setMetadata(ssm2);
		GeometricCorrectionModel areaCorrectionModel = new GeometricCorrectionModel();
		areaCorrectionModel.setSampleSize(100);
		areaCorrectionModel.setBeamHeight(0.1);
		areaCorrectionOperation.setModel(areaCorrectionModel);
		
		IMonitor monitor = new IMonitor.Stub();
		// And then run the method
		OperationData returnedResult = areaCorrectionOperation.execute(testData, monitor);
		
		TestUtils.assertDatasetEquals((Dataset) Maths.multiply(testData, 0.7951089448829912), (Dataset) returnedResult.getData());
	}
}
/*-
 * Copyright 2019 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

// @author Andrew McCluskey

package uk.ac.diamond.scisoft.analysis.processing.test.operation.backgroundsubtraction;

import static org.junit.Assert.assertEquals;

import org.eclipse.dawnsci.analysis.api.processing.IOperation;
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
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.processing.operations.backgroundsubtraction.GaussianBackgroundSubtractionOperation;
import org.eclipse.dawnsci.analysis.api.processing.model.EmptyModel;

public class GaussianBackgroundSubtractionOperationTest {
	
	@Test
	public void testGetId() {
		assertEquals("Gaussian Background Subtraction ID String not as expected", "uk.ac.diamond.scisoft.analysis.processing.operations.backgroundsubtraction.GaussianBackgroundSubtractionOperation", new GaussianBackgroundSubtractionOperation().getId());
	}
	
	@Test
	public void testGetInputRank() {
		assertEquals(OperationRank.ANY, new GaussianBackgroundSubtractionOperation().getInputRank());
	}
	
	@Test
	public void testGetOutputRank() {
		assertEquals(OperationRank.SAME, new GaussianBackgroundSubtractionOperation().getOutputRank());
	}
		
	// Test of the 1D process, this was compared with values calculated using the 
	// scipy.stats.norm (version 1.1.0) distribution
	@Test
	public void testProcess1D() {
		GaussianBackgroundSubtractionOperation gaussianBackgroundSubtractionOperation = new GaussianBackgroundSubtractionOperation();
	
		double[] calculated = {1.48671951e-06, 2.07440309e-04, 8.42153448e-03, 9.94771388e-02, 3.41892294e-01, 
				3.41892294e-01, 9.94771388e-02, 8.42153448e-03, 2.07440309e-04, 1.48671951e-06};
		// The Offset is assigned as 1
		IDataset testData = Maths.add(DatasetFactory.createFromObject(calculated), 1);
		// The uncertainties are on a similar scale to the data.
		IDataset testErrors = Maths.multiply(DatasetFactory.ones(10), 1e-7);
		testData.setErrors(testErrors);
		SliceFromSeriesMetadata sliceSeriesMetadata = new SliceFromSeriesMetadata((SliceInformation) null);
		testData.addMetadata(sliceSeriesMetadata);
		IDataset xAxis = DatasetFactory.createRange(10);
		AxesMetadata xAxisMetadata;
		try {
			xAxisMetadata = MetadataFactory.createMetadata(AxesMetadata.class, 1);
		} catch (MetadataException e) {
			throw new OperationException((IOperation<?, ?>) this, e);
		}
		xAxisMetadata.setAxis(0, xAxis);
		testData.setMetadata(xAxisMetadata);
		testData.addMetadata(sliceSeriesMetadata);
				
		// The result data should be the testData with the offset of 1 subtracted
		IDataset resultData = Maths.subtract(testData.clone(), 1);
		
		EmptyModel emptyModel = new EmptyModel();
		gaussianBackgroundSubtractionOperation.setModel(emptyModel);
		// We shall then create a monitor to satisfy the operation arguments
		IMonitor monitor = new IMonitor.Stub();
		OperationData returnedResult = gaussianBackgroundSubtractionOperation.execute(testData, monitor);
		
		// Check the result
		// The tolerances on this equals is relatively high to account for the fact that 
		// an optimisation is being tested
		TestUtils.assertDatasetEquals((Dataset) resultData, (Dataset) returnedResult.getData(), true, 1e-5, 1e-5);
	}
	
	// Test of the 1D process, this was compared with values calculated using the 
	// the method outlined at https://github.com/arm61/2d_gaussian
	@Test
	public void testProcess2D() {
		GaussianBackgroundSubtractionOperation gaussianBackgroundSubtractionOperation = new GaussianBackgroundSubtractionOperation();
	
		double[] calculated1 = {29150244650.281933, 130642332846.84921, 215392793018.48633, 
				130642332846.84921, 29150244650.281933};
		double[] calculated2 = {130642332846.84921, 585498315243.1917, 965323526300.5391, 
				585498315243.1917, 130642332846.84921};
		double[] calculated3 = {215392793018.48633, 965323526300.5391, 1591549430918.9534, 
				965323526300.5391, 215392793018.48633};
		double[] calculated4 = {130642332846.84921, 585498315243.1917, 965323526300.5391, 
				585498315243.1917, 130642332846.84921};
		double[] calculated5 = {29150244650.281933, 130642332846.84921, 215392793018.48633, 
				130642332846.84921, 29150244650.281933};
		double[][] calculated = {calculated1, calculated2, calculated3, calculated4, calculated5};
		// The Offset is set as 10.
		IDataset testData = Maths.add(DatasetFactory.createFromObject(calculated), 10);
		IDataset testErrors = DatasetFactory.ones(5, 5);
		testData.setErrors(testErrors);
		SliceFromSeriesMetadata sliceSeriesMetadata = new SliceFromSeriesMetadata((SliceInformation) null);
		testData.addMetadata(sliceSeriesMetadata);
		IDataset xAxis = DatasetFactory.createRange(5);
		IDataset yAxis = DatasetFactory.createRange(5);
		AxesMetadata axisMetadata;
		try {
			axisMetadata = MetadataFactory.createMetadata(AxesMetadata.class, 2);
		} catch (MetadataException e) {
			throw new OperationException((IOperation<?, ?>) this, e);
		}
		axisMetadata.setAxis(0, xAxis);
		axisMetadata.setAxis(1, yAxis);
		testData.setMetadata(axisMetadata);
		testData.setMetadata(axisMetadata);
		testData.addMetadata(sliceSeriesMetadata);
				
		// The result data should be the testData with the offset of 10 subtracted
		IDataset resultData = Maths.subtract(testData.clone(), 10);
		
		EmptyModel emptyModel = new EmptyModel();
		gaussianBackgroundSubtractionOperation.setModel(emptyModel);
		// We shall then create a monitor to satisfy the operation arguments
		IMonitor monitor = new IMonitor.Stub();
		OperationData returnedResult = gaussianBackgroundSubtractionOperation.execute(testData, monitor);
		
		// Check the result
		// The tolerances on this equals is relatively high to account for the fact that 
		// an optimisation is being tested
		TestUtils.assertDatasetEquals((Dataset) resultData, (Dataset) returnedResult.getData(), true, 1e-5, 1e-5);
	}
}

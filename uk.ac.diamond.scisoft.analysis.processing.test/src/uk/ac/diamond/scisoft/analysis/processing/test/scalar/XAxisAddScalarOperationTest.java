/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

// @author Andrew McCluskey

package uk.ac.diamond.scisoft.analysis.processing.test.scalar;

import static org.junit.Assert.assertEquals;

import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceInformation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.processing.scalar.ScalarUncertaintyModel;
import uk.ac.diamond.scisoft.analysis.processing.scalar.XAxisAddScalarOperation;

public class XAxisAddScalarOperationTest {
	
	// Numbers that will be used in the test
	static final double START = 0.;
	static final double STOP = 10.;
	static final int LENGTH = 10;
	static final double TOADD = 10.;	
	static final double TOADDUNCERTAINTY = 0.1;
	
	// First test, let's make sure that the operation ID is as expected
	@Test
	public void testGetId() {
		assertEquals("X Axis Add Scalar Operation ID String not as expected", "uk.ac.diamond.scisoft.analysis.processing.scalar.XAxisAddScalarOperation", new XAxisAddScalarOperation().getId());
	}
	
	@Test
	public void testGetInputRank() {
		assertEquals(OperationRank.ONE, new XAxisAddScalarOperation().getInputRank());
	}
	
	@Test
	public void testGetOutputRank() {
		assertEquals(OperationRank.ONE, new XAxisAddScalarOperation().getOutputRank());
	}
		
	// Second test will actually probe the addition with uncertainties
	@Test
	public void testProcess() {
		// First a relevant operation object will be created
		XAxisAddScalarOperation xaxisOperation = new XAxisAddScalarOperation();
		
		// Now some data needs to be made
		IDataset testData = DatasetFactory.ones(LENGTH);
		IDataset testErrors = DatasetFactory.ones(LENGTH);
		DoubleDataset xAxis = DatasetFactory.createLinearSpace(DoubleDataset.class, START, STOP, LENGTH);
		xAxis.setErrors(testErrors);
		AxesMetadata xAxisMetadata;
		
		SliceFromSeriesMetadata sliceSeriesMetadata = new SliceFromSeriesMetadata((SliceInformation)null);	
		
		try {
			xAxisMetadata = MetadataFactory.createMetadata(AxesMetadata.class, 1);
		} catch (MetadataException e) {
			throw new OperationException((IOperation<?, ?>) this, e);
		}
		xAxisMetadata.setAxis(0, xAxis);
		testData.setMetadata(xAxisMetadata);
		testData.addMetadata(sliceSeriesMetadata);
		
		// Now we tell the ScalarUncertaintyModel what is happening, that we want to shift the x-axis by 10\pm0.1
		ScalarUncertaintyModel scalarUncertaintyModel = new ScalarUncertaintyModel();
		scalarUncertaintyModel.setValue(TOADD);
		scalarUncertaintyModel.setError(TOADDUNCERTAINTY);
		xaxisOperation.setModel(scalarUncertaintyModel);
		
		// Now some result data needs to be created
		IDataset resultData = DatasetFactory.ones(LENGTH);
		IDataset resultErrors = DatasetFactory.ones(LENGTH);
		resultErrors = Maths.add(resultErrors, TOADDUNCERTAINTY);
		DoubleDataset resultXAxis = DatasetFactory.createLinearSpace(DoubleDataset.class, (START + TOADD), (STOP + TOADD), LENGTH);
		resultXAxis.setErrors(resultErrors);
		AxesMetadata resultXAxisMetadata = testData.getFirstMetadata(AxesMetadata.class);
		resultXAxisMetadata.setAxis(0, resultXAxis);
		resultData.setMetadata(resultXAxisMetadata);
		// We shall then create a monitor to satisfy the operation arguments
		IMonitor monitor = new IMonitor.Stub();
		// And then run the method
		OperationData returnedResult = xaxisOperation.execute(testData, monitor);
		// Before checking the result
		assertEquals("Add X Axis Scalar Operation did not execute process as expected", resultData, returnedResult.getData());
	}
}

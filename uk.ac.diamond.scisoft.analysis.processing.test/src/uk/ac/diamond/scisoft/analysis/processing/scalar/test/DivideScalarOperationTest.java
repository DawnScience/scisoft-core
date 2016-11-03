/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.scalar.test;

// Imports from org.eclipse
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceInformation;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.IMonitor;

//Import from org.junit
import static org.junit.Assert.*;
import org.junit.Test;

//Imports from uk.ac.diamond
import uk.ac.diamond.scisoft.analysis.processing.scalar.DivideScalarOperation;
import uk.ac.diamond.scisoft.analysis.processing.scalar.ScalarModel;


// Now the testing class
public class DivideScalarOperationTest {

	// Let's create some static numbers that we will use for the doMaths() test
	private final int start = 0;
	private final int stop = 90;
	private final int length = 10;
	private final int scalarOperator = 10;
	
	// First test, let's make sure that the operation ID is as expected
	@Test
	public void testGetId() {
		assertEquals("Divide Scalar Operation ID String not as expected", "uk.ac.diamond.scisoft.analysis.processing.scalar.DivideScalarOperation", new DivideScalarOperation().getId());
	}
	
	// Then test the doMaths method of the DivideScalarOperation
	@Test
	public void testDoMaths() {
		// First we shall create an object of the operation
		DivideScalarOperation scalarOperation = new DivideScalarOperation();
		
		// We shall set up a dataset with ten values, between 0 and 90
		IDataset testData = DatasetFactory.createLinearSpace(start, stop, length, Dataset.FLOAT64);

		// We shall then tell the ScalarModel that we want to divide the testData by ten and pass this to the operation
		ScalarModel scalarModel = new ScalarModel();
		scalarModel.setValue(scalarOperator);
		scalarOperation.setModel(scalarModel);

		// Then we shall create the dataset, as it should be
		IDataset testResult = DatasetFactory.createLinearSpace((start / scalarOperator), (stop / scalarOperator), length, Dataset.FLOAT64);
		
		// We shall then create a monitor to satisfy the operation arguments
		IMonitor monitor = new IMonitor.Stub();
		
		// And then create (and add) some metadata for the testData to satisfy the operation as well
		SliceFromSeriesMetadata sliceSeriesMetadata = new SliceFromSeriesMetadata((SliceInformation)null);	
		testData.setMetadata(sliceSeriesMetadata);
		
		// And then run the method
		OperationData returnedResult = scalarOperation.execute(testData, monitor);

		// Before checking the result
		assertEquals("Divide Scalar Operation did not exectue doMaths as expected", testResult, returnedResult.getData());
	}
}

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

//Import from org.junit
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.asserts.TestUtils;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;
import org.junit.Test;

//Imports from uk.ac.diamond
import uk.ac.diamond.scisoft.analysis.processing.operations.reflectivityandsxrd.ConvertToQOperation;
import org.eclipse.dawnsci.analysis.api.processing.model.EmptyModel;

//Now the testing class
public class ConvertToQOperationTest {
	
	// Numbers that will be used in the test
	static final double START = 0.;
	static final double STOP = 0.1;
	static final int LENGTH = 10;
	
	// First test, let's make sure that the operation ID is as expected
	@Test
	public void testGetId() {
		assertEquals("Slice Axis Add Scalar Operation ID String not as expected", "uk.ac.diamond.scisoft.analysis.processing.operations.reflectivityandsxrd.ConvertToQOperation", new ConvertToQOperation().getId());
	}
	
	@Test
	public void testGetInputRank() {
		assertEquals(OperationRank.TWO, new ConvertToQOperation().getInputRank());
	}
	
	@Test
	public void testGetOutputRank() {
		assertEquals(OperationRank.TWO, new ConvertToQOperation().getOutputRank());
	}
		
	// Second test will actually probe the addition with uncertainties
	@Test
	public void testProcess() throws DatasetException {
		// First a relevant operation object will be created
		ConvertToQOperation convertToQOperation = new ConvertToQOperation();
		
		// Now some data needs to be made
		IDataset testErrors = Maths.multiply(DatasetFactory.ones(LENGTH), 0.001);
		DoubleDataset sliceAxis = DatasetFactory.createLinearSpace(DoubleDataset.class, START, STOP, LENGTH);
		sliceAxis.setErrors(testErrors);
		
		IDataset testData = DatasetFactory.createLinearSpace(DoubleDataset.class, START*10, STOP*10, LENGTH*LENGTH);
		testData.setShape(1, LENGTH, LENGTH);

		AxesMetadata axesMetadata = MetadataFactory.createMetadata(AxesMetadata.class, 1);	
		axesMetadata.setAxis(0, sliceAxis);	
		testData.setMetadata(axesMetadata);
		
		// Now we tell the ScalarUncertaintyModel what is happening, that we want to shift the x-axis by 10\pm0.1
		EmptyModel emptyModel = new EmptyModel();
		convertToQOperation.setModel(emptyModel);
		
		// Now some result data needs to be created
		List<Double> result = new ArrayList<Double>();
		result.add(0.0);
		result.add(0.0024569061892695592);
		result.add(0.004913812286142029);
		result.add(0.007370718198220324);
		result.add(0.009827623833107368);
		result.add(0.012284529098406088);
		result.add(0.014741433901719432);
		result.add(0.017198338150650364);
		result.add(0.019655241752801863);
		result.add(0.022112144615776944);
		
		// Now some result data needs to be created
		List<Double> resultE = new ArrayList<Double>();
		resultE.add(0.00022112155842021666);
		resultE.add(0.00022112155837863796);
		resultE.add(0.0002211215582539019);
		resultE.add(0.00022112155804600848);
		resultE.add(0.00022112155775495766);
		resultE.add(0.0002211215573807494);
		resultE.add(0.0002211215569233838);
		resultE.add(0.00022112155638286088);
		resultE.add(0.00022112155575918052);
		resultE.add(0.00022112155505234278);
		
		IDataset resultErrors = DatasetFactory.createFromList(resultE);
		
		IDataset resultData = DatasetFactory.createLinearSpace(DoubleDataset.class, START*10, STOP*10, LENGTH*LENGTH);
		resultData.setShape(1, LENGTH, LENGTH);

		Dataset resultQ = DatasetFactory.createFromList(result);
		resultQ.setErrors(resultErrors);

		IMonitor monitor = new IMonitor.Stub();
		// And then run the method
		OperationData returnedResult = convertToQOperation.execute(testData, monitor);
		// Before checking the result
		TestUtils.assertDatasetEquals(DatasetUtils.convertToDataset(resultData), DatasetUtils.convertToDataset(returnedResult.getData()));
		
		IDataset returnedData = returnedResult.getData().getSliceView();
		AxesMetadata axesMetadata2 = returnedData.getFirstMetadata(AxesMetadata.class);
		ILazyDataset [] qAxis2 = axesMetadata2.getAxes();
		Dataset q2 = DatasetUtils.sliceAndConvertLazyDataset(qAxis2[0]);

		TestUtils.assertDatasetEquals(resultQ, q2, true, 1e-8, 1e-8);
		TestUtils.assertDatasetEquals(resultQ.getErrors(), q2.getErrors(), true, 1e-8, 1e-8);
	}
}
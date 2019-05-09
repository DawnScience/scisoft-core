/* Copyright 2019 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

// @author Andrew McCluskey

package uk.ac.diamond.scisoft.analysis.processing.test.scalar;

//Import from org.junit
import static org.junit.Assert.assertEquals;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;
import org.eclipse.january.asserts.TestUtils;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SourceInformation;
import org.junit.Test;

//Imports from uk.ac.diamond
import uk.ac.diamond.scisoft.analysis.processing.scalar.SliceAxisAddScalarOperation;
import uk.ac.diamond.scisoft.analysis.processing.scalar.ScalarUncertaintyModel;

//Now the testing class
public class SliceAxisAddScalarOperationTest {
	
	// Numbers that will be used in the test
	static final double START = 0.;
	static final double STOP = 10.;
	static final int LENGTH = 10;
	static final double TOADD = 10.;	
	static final double TOADDUNCERTAINTY = 0.1;
	
	// First test, let's make sure that the operation ID is as expected
	@Test
	public void testGetId() {
		assertEquals("Slice Axis Add Scalar Operation ID String not as expected", "uk.ac.diamond.scisoft.analysis.processing.scalar.SliceAxisAddScalarOperation", new SliceAxisAddScalarOperation().getId());
	}
		
	// Second test will actually probe the addition with uncertainties
	@Test
	public void testProcess() throws MetadataException {
		// First a relevant operation object will be created
		SliceAxisAddScalarOperation sliceAxisOperation = new SliceAxisAddScalarOperation();
		
		// Now some data needs to be made
		IDataset testErrors = DatasetFactory.ones(LENGTH);
		DoubleDataset sliceAxis = DatasetFactory.createLinearSpace(DoubleDataset.class, START, STOP, LENGTH);
		sliceAxis.setErrors(testErrors);
		
		DoubleDataset testData = DatasetFactory.createLinearSpace(DoubleDataset.class, START*10, STOP*10, LENGTH*2);

		AxesMetadata ssmParentAxesMetadata = MetadataFactory.createMetadata(AxesMetadata.class, 1);
		
		ssmParentAxesMetadata.setAxis(0, sliceAxis);
		ILazyDataset ssmParent = DatasetFactory.createLinearSpace(DoubleDataset.class, START*10, STOP*10, LENGTH*2);
		ssmParent.setMetadata(ssmParentAxesMetadata);
		SourceInformation si = new SourceInformation(null, null, ssmParent);
		SliceFromSeriesMetadata ssm2 = new SliceFromSeriesMetadata(si);
		testData.setMetadata(ssm2);
		
		// Now we tell the ScalarUncertaintyModel what is happening, that we want to shift the x-axis by 10\pm0.1
		ScalarUncertaintyModel scalarUncertaintyModel = new ScalarUncertaintyModel();
		scalarUncertaintyModel.setValue(TOADD);
		scalarUncertaintyModel.setError(TOADDUNCERTAINTY);
		sliceAxisOperation.setModel(scalarUncertaintyModel);
		
		// Now some result data needs to be created
		DoubleDataset resultData = DatasetFactory.createLinearSpace(DoubleDataset.class, START*10, STOP*10, LENGTH*2);

		IDataset resultErrors = DatasetFactory.ones(LENGTH);
		resultErrors = Maths.add(resultErrors, TOADDUNCERTAINTY);
		DoubleDataset resultSliceAxis = DatasetFactory.createLinearSpace(DoubleDataset.class, (START + TOADD), (STOP + TOADD), LENGTH);
		resultSliceAxis.setErrors(resultErrors);
		
		AxesMetadata ssmresultParentAxesMetadata = MetadataFactory.createMetadata(AxesMetadata.class, 1);

		ssmParentAxesMetadata.setAxis(0, sliceAxis);
		ILazyDataset ssmresultParent = DatasetFactory.createLinearSpace(DoubleDataset.class, START*10, STOP*10, LENGTH*2);
		ssmresultParent.setMetadata(ssmresultParentAxesMetadata);
		SourceInformation siresult = new SourceInformation(null, null, ssmresultParent);
		SliceFromSeriesMetadata ssm2result = new SliceFromSeriesMetadata(siresult);
		resultData.setMetadata(ssm2result);
		// We shall then create a monitor to satisfy the operation arguments
		IMonitor monitor = new IMonitor.Stub();
		// And then run the method
		OperationData returnedResult = sliceAxisOperation.execute(testData, monitor);
		// Before checking the result
		TestUtils.assertDatasetEquals(resultData, DatasetUtils.convertToDataset(returnedResult.getData()));
	}
}
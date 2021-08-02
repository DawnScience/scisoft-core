/*-
 * Copyright 2021 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

// @author Tim Snow

package uk.ac.diamond.scisoft.analysis.processing.test.operations.twod;

//Import from org.junit
import static org.junit.Assert.assertEquals;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SourceInformation;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;
import org.junit.Test;

//Imports from uk.ac.diamond
import uk.ac.diamond.scisoft.analysis.processing.operations.twod.FrameMathsFromPeakModel;
import uk.ac.diamond.scisoft.analysis.processing.operations.twod.FrameMathsFromPeakOperation;

//Now the testing class
public class FrameMathsFromPeakOperationTest {
	
	@Test
	public void testProcess() throws DatasetException {
		// Make up some data
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
		IMonitor monitor = new IMonitor.Stub();

		// Then set up the processing operation
		FrameMathsFromPeakOperation frameMathsFromPeakOperation = new FrameMathsFromPeakOperation();
		FrameMathsFromPeakModel frameMathsFromPeakModel = new FrameMathsFromPeakModel();
		frameMathsFromPeakModel.setXBoxLength(20);
		frameMathsFromPeakModel.setYBoxLength(20);
		frameMathsFromPeakOperation.setModel(frameMathsFromPeakModel);

		// Run it against the test data
		OperationData resultOD = frameMathsFromPeakOperation.execute(startData, monitor);
		IDataset resultData = resultOD.getData();
		Dataset resultSum = DatasetUtils.cast(DoubleDataset.class, resultData);
		Double intensitySum = (Double) resultSum.sum(true);

		// Do checks
		assertEquals(500, resultData.getShape()[0]);
		assertEquals(500, resultData.getShape()[1]);
		assertEquals(1.25E8, intensitySum, 1E6);
	}
}
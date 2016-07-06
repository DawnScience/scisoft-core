/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.test;

import static org.junit.Assert.*;

import org.eclipse.dawnsci.analysis.api.dataset.Slice;
import org.eclipse.dawnsci.analysis.api.dataset.SliceND;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceInformation;
import org.junit.Before;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.processing.operations.oned.InterpolateMissingDataModel;
import uk.ac.diamond.scisoft.analysis.processing.operations.oned.InterpolateMissingDataOperation;

public class MissingDataTest {
	private Dataset fullSine;

	@Before
	public void setUp() throws Exception {
		// Make sinus data
		fullSine = Maths.sin(Maths.multiply(2*Math.PI, DoubleDataset.createRange(0.0, 1.0, 0.01)));
	}

	@Test
	public void testProcess() {
		int start = 23, stop = 28, step = 1;
		Slice missingSlice = new Slice(start, stop, step);
		// positive infinity
		Dataset infSine = new DoubleDataset(fullSine);
		infSine.setSlice(Double.POSITIVE_INFINITY, missingSlice);
		infSine.addMetadata(new SliceFromSeriesMetadata(new SliceInformation(new SliceND(infSine.getShape()), new SliceND(infSine.getShape()), new SliceND(infSine.getShape()), infSine.getShape(), 1, 1)));
		// Missing data indicator
		double mdi = 2e30;
		Dataset mdiSine = new DoubleDataset(fullSine);
		mdiSine.setSlice(mdi, missingSlice);
		mdiSine = InterpolateMissingDataOperation.interpolateMissingData(mdiSine, mdi);
		mdiSine.addMetadata(new SliceFromSeriesMetadata(new SliceInformation(new SliceND(infSine.getShape()), new SliceND(infSine.getShape()), new SliceND(infSine.getShape()), infSine.getShape(), 1, 1)));
		// What the answer should be
		Dataset interpolSine = new DoubleDataset(fullSine);
		for (int i = start; i < stop; i+=step) {
			interpolSine.set((i-start+1) * (fullSine.getDouble(stop) - fullSine.getDouble(start-1)) / (stop - (start-1)) + fullSine.getDouble(start-1), i);
		}
		
		AbstractOperation<InterpolateMissingDataModel, OperationData> theOp = new InterpolateMissingDataOperation();
		theOp.setModel(new InterpolateMissingDataModel());
		Dataset processedInfSine = DatasetUtils.convertToDataset(theOp.execute(infSine, null).getData());
		checkAllElements(interpolSine, processedInfSine, "inifity");

		AbstractOperation<InterpolateMissingDataModel, OperationData> theOpWithMDI = new InterpolateMissingDataOperation();
		theOpWithMDI.setModel(new FakeInterpolateMissingDataModel());
		Dataset processedMDISine = DatasetUtils.convertToDataset(theOpWithMDI.execute(mdiSine, null).getData());
		checkAllElements(interpolSine, processedMDISine, "inifity");
		
	}

	@Test
	public void testGetId() {
		assertEquals("ID string has changed", "uk.ac.diamond.scisoft.analysis.processing.operations.oned.InterpolateMissingDataOperation", new InterpolateMissingDataOperation().getId());
	}

	@Test
	public void testGetInputRank() {
		assertEquals("Input should be one dimensional", OperationRank.ONE, new InterpolateMissingDataOperation().getInputRank());
	}

	@Test
	public void testGetOutputRank() {
		assertEquals("Output should be one dimensional", OperationRank.ONE, new InterpolateMissingDataOperation().getOutputRank());
	}

	@Test
	public void testInterpolateMissingData() {
		// Lop off the top of the maximum: missing data around i=25
		int start = 23, stop = 28, step = 1;
		Slice missingSlice = new Slice(start, stop, step);
		// positive infinity
		Dataset infSine = new DoubleDataset(fullSine);
		infSine.setSlice(Double.POSITIVE_INFINITY, missingSlice);
		infSine = InterpolateMissingDataOperation.interpolateMissingData(infSine, null);
		// negative infinity
		Dataset minfSine = new DoubleDataset(fullSine);
		minfSine.setSlice(Double.NEGATIVE_INFINITY, missingSlice);
		minfSine = InterpolateMissingDataOperation.interpolateMissingData(minfSine, null);
		// NaN
		Dataset nanSine = new DoubleDataset(fullSine);
		nanSine.setSlice(Double.NaN, missingSlice);
		nanSine = InterpolateMissingDataOperation.interpolateMissingData(nanSine, null);
		// Missing data indicator
		double mdi = 2e30;
		Dataset mdiSine = new DoubleDataset(fullSine);
		mdiSine.setSlice(mdi, missingSlice);
		mdiSine = InterpolateMissingDataOperation.interpolateMissingData(mdiSine, mdi);
		// What the answer should be
		Dataset interpolSine = new DoubleDataset(fullSine);
		for (int i = start; i < stop; i+=step) {
			interpolSine.set((i-start+1) * (fullSine.getDouble(stop) - fullSine.getDouble(start-1)) / (stop - (start-1)) + fullSine.getDouble(start-1), i);
		}

		for (int i = 0; i < fullSine.getSize(); i++) {
			checkAllElements(interpolSine, infSine, "+infinity");
			checkAllElements(interpolSine, minfSine, "-infinity");
			checkAllElements(interpolSine, nanSine, "NaN");
			checkAllElements(interpolSine, mdiSine, "MDI");
		}
	}

	private static void checkAllElements(Dataset expected, Dataset actual, String name) {
		for (int i = 0; i < expected.getSize(); i++)
			assertEquals("Error in " + name + " missing data interpolation", expected.getDouble(i), actual.getDouble(i), 1e-12);
	}

	@Test
	public void endpointReplacementTest() {
		int firstData = 10, lastData = 87;
		
		Dataset interpolatedStart = new DoubleDataset(fullSine), missingStart = new DoubleDataset(fullSine);
		Slice startSlice = new Slice(0, firstData);
		interpolatedStart.setSlice(fullSine.getDouble(firstData), startSlice);
		missingStart.setSlice(Double.NaN, startSlice);
		missingStart = InterpolateMissingDataOperation.interpolateMissingData(missingStart, null);
		
		Dataset interpolatedFinish = new DoubleDataset(fullSine), missingFinish = new DoubleDataset(fullSine);
		Slice finishSlice = new Slice(lastData+1, fullSine.getSize());
		interpolatedFinish.setSlice(fullSine.getDouble(lastData), finishSlice);
		missingFinish.setSlice(Double.POSITIVE_INFINITY, finishSlice);
		missingFinish = InterpolateMissingDataOperation.interpolateMissingData(missingFinish, null);
		
		checkAllElements(interpolatedStart, missingStart, "starting");
		checkAllElements(interpolatedFinish, missingFinish, "finishing");
		
	}

	
	class FakeInterpolateMissingDataModel extends InterpolateMissingDataModel {
		@Override
		public Double getMdi() {
			return 2e30;
		}
	}
}

/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.dataset.function;

import org.eclipse.dawnsci.analysis.api.roi.IRectangularROI;
import org.eclipse.dawnsci.analysis.dataset.impl.function.DatasetToDatasetFunction;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.junit.Test;

public class RegisterNoisyImageTest extends RegisterImageTest {
	
	private static int LOCAL_MARGIN = 10; // margin for ROI

	@Override
	@Test
	public void testRegisterSynthetic() {
		testRegisterSynthetic(null, 0.09);
	}

	@Override
	@Test
	public void testRegisterSyntheticWithROI() {
		double[] start = new double[] {X_SIZE/START - OBJECT_X - LOCAL_MARGIN, Y_SIZE/START - OBJECT_Y - LOCAL_MARGIN};
		double[] end = start.clone();
		end[0] += 2*OBJECT_X + MAX*X_DELTA + LOCAL_MARGIN;
		end[1] += 2*OBJECT_Y + MAX*Y_DELTA + LOCAL_MARGIN;
		RectangularROI roi = new RectangularROI(start, end);
		System.err.println(roi);
		testRegisterSynthetic(roi, 1.39);
	}

	@Override
	DatasetToDatasetFunction createFunction(IDataset[] data, IRectangularROI roi) {
		RegisterNoisyImage reg = new RegisterNoisyImage();
		DoubleDataset filter = DatasetFactory.ones(DoubleDataset.class, 9, 9);
		filter.imultiply(1./ ((Number) filter.sum()).doubleValue());
		reg.setFilter(filter);
		reg.setRectangle(roi);
		return reg;
	}
}

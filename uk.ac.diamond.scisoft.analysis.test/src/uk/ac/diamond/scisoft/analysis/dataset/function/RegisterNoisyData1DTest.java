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
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Random;
import org.junit.BeforeClass;
import org.junit.Test;

public class RegisterNoisyData1DTest extends RegisterData1DTest {
	@BeforeClass
	public static void setUp() {
		Random.seed(123571L);
	}

	@Override
	@Test
	public void testRegisterSynthetic() {
		testRegisterSynthetic(0.15, 0);
		testRegisterSynthetic(0.8, 0.05); // 10% noise
		testRegisterSynthetic(1.02, 0.1); // 20% noise
	}

	@Override
	@Test
	public void testRegisterSyntheticWithROI() {
		testRegisterSyntheticWithROI(0.05, 0);
		testRegisterSyntheticWithROI(1.2, 0.05); // 10% noise
		testRegisterSyntheticWithROI(2.07, 0.1); // 20% noise
	}

	@Override
	DatasetToDatasetFunction createFunction(IDataset[] data, IRectangularROI roi) {
		RegisterNoisyData1D reg = new RegisterNoisyData1D();
		DoubleDataset filter = DatasetFactory.ones(DoubleDataset.class, 5);
		filter.imultiply(1./ ((Number) filter.sum()).doubleValue());
		reg.setFilter(filter);
		reg.setRectangle(roi);
		return reg;
	}
}

/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.test;

import static org.junit.Assert.assertEquals;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.processing.operations.rixs.RixsBaseOperation;

public class SlopeSummingTest {

	@Test
	public void testSlopeSumming() {
		int rows = 10;
		int cols = 40;
		DoubleDataset image = DatasetFactory.zeros(rows, cols);
		DoubleDataset expected = DatasetFactory.zeros(cols);

		int size = 6;
		int offset = cols/2 - size/2;
		for (int i = 0; i < rows; i++) { // create sloping pattern
			for (int j = 0; j < size; j++) {
				image.setItem(1, i, offset + j + i);
				expected.setItem(expected.get(offset + j) + 1, offset + j);
			}
		}

		Dataset out;
		out = RixsBaseOperation.sumImageAlongSlope(image, -1);
		assertEquals(expected, out);
	}
}

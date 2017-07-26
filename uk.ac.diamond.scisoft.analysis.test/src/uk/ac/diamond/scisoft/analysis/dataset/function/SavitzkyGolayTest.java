/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.dataset.function;

import java.util.Arrays;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.Maths;
import org.junit.Before;
import org.junit.Test;

public class SavitzkyGolayTest {
	Dataset input;
	
	private static void printInfo(Dataset data) {
		System.out.println("Size: " + data.getSize());
		System.out.println("Shape: " + Arrays.toString(data.getShape()));
		//Dataset temp = data.getSlice(null, new int[]{1, data.getShape()[1]} , null).squeeze();
		//System.out.println("First value: " + Arrays.toString(((DoubleDataset) data.getSlice(null, new int[]{1, data.getShape()[1]} , null).squeeze()).getData()));
		try {
			System.out.println("First value: " + data.getSlice(new int[]{0, 0}, new int[]{1, data.getShape()[1]} , null).squeeze().toString(true));
			System.out.println("Second value: " + data.getSlice(new int[]{1, 0}, new int[]{2, data.getShape()[1]} , null).squeeze().toString(true));
			System.out.println("Last value: " + data.getSlice(new int[]{data.getShape()[0]-1, 0}, new int[]{data.getShape()[0], data.getShape()[1]} , null).squeeze().toString(true));
		} catch (Exception e) {
			System.out.println("All values: " + data.toString(true));
		}
	}
	
	@Before
	public void init() {
		input = Maths.sin(DatasetFactory.createRange(DoubleDataset.class, -2.0, 2.0, 0.005).reshape(40, 20));
		
		//printInfo(input);
		
	}
	
	@Test
	public void testFilterIDatasetIntIntInt() {
		Dataset rv = SavitzkyGolay.filter(input, 5, 3, 1);
		printInfo(rv);
	}

}

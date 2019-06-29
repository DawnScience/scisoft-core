/*-
 * Copyright (c) 2014 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.dataset.function;


import org.eclipse.january.asserts.TestUtils;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.junit.Test;

/**
 *
 */
public class MapTo2DTest {
	Dataset d = DatasetFactory.createRange(200).reshape(20, 10);

	/**
	 * 
	 */
	@Test
	public void testMapTo2D() {
		MapTo2D mp;

		mp = new MapTo2D(new double[] {5, 5}, new double[] {1, 0}, new double[] {0, 1}, 5, 5);
		TestUtils.assertDatasetEquals(d.getSliceView(new int[] {5, 5}, new int[] {10, 10}, null), mp.value(d).get(0), 1e-15, 1e-15);

		mp = new MapTo2D(new double[] {5, 3}, new double[] {1, 0}, new double[] {0, 1}, 5, 7);
		TestUtils.assertDatasetEquals(d.getSliceView(new int[] {5, 3}, new int[] {10, 10}, null), mp.value(d).get(0), 1e-15, 1e-15);

		mp = new MapTo2D(new double[] {5, 5}, new double[] {0, 1}, new double[] {1, 0}, 5, 5);
		TestUtils.assertDatasetEquals(d.getSliceView(new int[] {5, 5}, new int[] {10, 10}, null).getTransposedView(), mp.value(d).get(0), 1e-15, 1e-15);

		mp = new MapTo2D(new double[] {5, 3}, new double[] {0, 1}, new double[] {1, 0}, 7, 5);
		TestUtils.assertDatasetEquals(d.getSliceView(new int[] {5, 3}, new int[] {10, 10}, null).getTransposedView(), mp.value(d).get(0), 1e-15, 1e-15);
	}

}

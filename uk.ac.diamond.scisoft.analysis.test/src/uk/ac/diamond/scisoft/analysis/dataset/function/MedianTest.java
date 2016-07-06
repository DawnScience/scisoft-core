/*-
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.dataset.function;

import static org.junit.Assert.assertEquals;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.junit.Test;

public class MedianTest {

	@Test
	public void testExecute() {
		double[] x = {1., 2., 3., 4., 5., 6., 7., 8., 9., 10., 11., 12.};
		
		Dataset d = DatasetFactory.createFromObject(x);
		//Test for single dataset
		Median m = new Median(5);
		Dataset filterResults = m.value(d).get(0);
		//assuming edge cases use smaller, asymmetric window
		assertEquals(filterResults.getDouble(0),2, 1e-8);
		//clear of edge effects
		assertEquals(filterResults.getDouble(5),6, 1e-8);
	}

	
}

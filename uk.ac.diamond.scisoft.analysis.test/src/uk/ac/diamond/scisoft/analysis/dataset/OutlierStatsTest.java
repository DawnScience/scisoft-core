/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.dataset;

import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Outliers;
import org.eclipse.dawnsci.analysis.dataset.impl.Random;
import org.junit.Assert;
import org.junit.Test;

public class OutlierStatsTest {
	
	@Test
	public void testSn() {
		Random.seed(3145);
		DoubleDataset randn = Random.randn(1, 1, new int[]{5000});
		
		long t = System.currentTimeMillis();
		double snNaive = Outliers.snNaive(randn);
		System.out.println(System.currentTimeMillis()-t);
		Assert.assertEquals(0.9978238112683145, snNaive, 1E-10);
		
	}

}

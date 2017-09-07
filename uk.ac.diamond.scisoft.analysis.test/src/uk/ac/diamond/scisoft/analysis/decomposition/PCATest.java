/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.decomposition;

import static org.junit.Assert.*;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.junit.Assert;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;

public class PCATest {

	@Test
	public void test() {
		IDataHolder dh;
		try {
			dh = LoaderFactory.getData("testfiles/iris.csv");
			
		} catch (Exception e) {
			Assert.fail("Could not load test reduced data");
			return;
		}
		
		IDataset[] ds = new IDataset[dh.size()];
		
		for (int i = 0 ; i < dh.size(); i++) {
			ds[i] = dh.getDataset(i);
			ds[i].setShape(new int[]{1, ds[i].getShape()[0]});
		}
		
		Dataset data = DatasetUtils.concatenate(ds, 0).transpose().getSlice();
		
		PCAResult fit = PCA.fit(data, 3);
		IDataset loadings = fit.getLoadings();
		assertArrayEquals(new int[]{3,4}, loadings.getShape());
		
		IDataset vR = fit.getVarianceRatio();
		assertArrayEquals(new int[]{3}, vR.getShape());
		
		assertEquals(0.9246162, vR.getDouble(0),0.0000001);
		assertEquals(0.9246162, vR.getDouble(0),0.05301557);
		
	}

}

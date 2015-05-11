/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction.powder;

import java.util.List;

import junit.framework.Assert;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.io.CBFLoader;
import uk.ac.diamond.scisoft.analysis.io.DataHolder;
import uk.ac.diamond.scisoft.analysis.roi.XAxis;

public class PixelIntegrationThreadTest extends PixelIntegrationThreadTestBase{
	
	IPixelIntegrationCache info;
	IDataset data;
	double testValx;
	double testValy;
	
	@Override
	@Test
	public void testWithTenThreads() {
		data = getData();
		if (data == null) {
			Assert.fail("Could not load test data");
			return;
		}
		
		IDiffractionMetadata meta = getDiffractionMetadata();
		
		PixelIntegrationBean bean = new PixelIntegrationBean();
		bean.setAzimuthalIntegration(true);
		bean.setUsePixelSplitting(true);
		bean.setNumberOfBinsRadial(2000);
		bean.setxAxis(XAxis.ANGLE);
		info = new PixelIntegrationCache(meta, bean);

		long s = System.currentTimeMillis();
		List<Dataset> result = PixelIntegration.integrate(data, null, info);
		System.out.println("one done in: " +(System.currentTimeMillis() - s)/1000.);
		
		testValx = result.get(0).getDouble(100);
		testValy = result.get(1).getDouble(100);
		
		s = System.currentTimeMillis();
		try {
			super.testWithNThreads(10);
		} catch (ScanFileHolderException sfhe) {
			if (((sfhe.getCause() instanceof OutOfMemoryError)) || (sfhe.toString().endsWith("Direct buffer memory")))
				System.out.println("Out of memory: this is common and acceptable for this test");
			else
				Assert.fail("Something other than an out of memory exception was thrown: " + sfhe.toString());
		} catch (Exception e) {
			Assert.fail("Integration failed for reasons other than out of memory: " + e.toString());
		}
		
		System.out.println("ten done in: " +(System.currentTimeMillis() - s)/1000.);
	}
	
	/**
	 * 
	 * 
	 * @throws Exception if the file could not be loaded
	 */
	@Override
	public void doTestOfDataSet(int threadIndex) throws Exception {
		List<Dataset> result = PixelIntegration.integrate(data, null, info);
		assert result.get(0).getDouble(100) == testValx;
		assert result.get(1).getDouble(100) == testValy;
		assert result.get(0).getSize() == 2000;
	}

}

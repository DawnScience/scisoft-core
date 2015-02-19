/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
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
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.roi.XAxis;

public class PixelIntegrationCompare1D extends AbstractPixelIntegrationTestBase {
	
	final static String pathPyFAINonSplitting = "testfiles/pyfai_non_splitting.dat";
	final static String pathPyFAISplitting = "testfiles/pyfai_split.dat";


	@Test
	public void compareNonSplitting() {
		IDataset data = getData();
		if (data == null) {
			Assert.fail("Could not load test data");
			return;
		}
		
		IDiffractionMetadata meta = getDiffractionMetadata();
		NonPixelSplittingIntegration npsi = new NonPixelSplittingIntegration(meta, 2000);
		npsi.setAxisType(XAxis.ANGLE);
		
		List<Dataset> out = npsi.integrate(data);
		
		IDataHolder dh;
		try {
			dh = LoaderFactory.getData(pathPyFAINonSplitting);
			
		} catch (Exception e) {
			Assert.fail("Could not load test reduced data");
			return;
		}

		IDataset x = dh.getDataset("Column_1");
		IDataset y = dh.getDataset("Column_2");
		
		Dataset difx = Maths.subtract(x, out.get(0));
		Dataset dify = Maths.subtract(y, out.get(1));
		
		double xm = difx.max().doubleValue();
		double ym = dify.max().doubleValue();
		
		//Check results are not too different
		Assert.assertEquals(0, xm, 0.00001);
		Assert.assertTrue(ym < 60);
		
	}
	
	@Test
	public void compareSplitting() {
		IDataset data = getData();
		if (data == null) {
			Assert.fail("Could not load test data");
			return;
		}
		
		IDiffractionMetadata meta = getDiffractionMetadata();
		PixelSplittingIntegration npsi = new PixelSplittingIntegration(meta, 2000);

		
		List<Dataset> out = npsi.integrate(data);
		
		IDataHolder dh;
		try {
			dh = LoaderFactory.getData(pathPyFAISplitting);
			
		} catch (Exception e) {
			Assert.fail("Could not load test reduced data");
			return;
		}

		IDataset x = dh.getDataset("Column_1");
		IDataset y = dh.getDataset("Column_2");
		
		Dataset difx = Maths.subtract(x, out.get(0));
		Dataset dify = Maths.subtract(y, out.get(1));
		
		double xm = difx.max().doubleValue();
		double ym = dify.max().doubleValue();
		
		//Check results are not too different
		Assert.assertEquals(0, xm, 0.00001);
		Assert.assertTrue(ym < 60);
		
	}
	
}

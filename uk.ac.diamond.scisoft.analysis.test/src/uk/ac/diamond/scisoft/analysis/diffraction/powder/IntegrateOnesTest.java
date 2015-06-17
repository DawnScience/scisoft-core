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
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.dataset.impl.Comparisons;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.IndexIterator;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.roi.XAxis;

public class IntegrateOnesTest extends AbstractPixelIntegrationTestBase {

	@Test
	public void testNonSplitting1D() {
		IDataset data = getData();
		if (data == null) {
			Assert.fail("Could not load test data");
			return;
		}
		
		IDiffractionMetadata meta = getDiffractionMetadata();
		Dataset ones = DatasetFactory.ones((Dataset)data);
		
		PixelIntegrationBean bean = new PixelIntegrationBean();
		bean.setAzimuthalIntegration(true);
		bean.setUsePixelSplitting(false);
		bean.setxAxis(XAxis.Q);
		PixelIntegrationCache info = new PixelIntegrationCache(meta, bean);
		List<Dataset> out1D = PixelIntegration.integrate(ones, null, info);
		
		Dataset dataset = out1D.get(1);
		Assert.assertTrue(Comparisons.allTrue(Comparisons.equalTo(dataset, 1)));
	}
	
	@Test
	public void testNonSplitting2D() {
		IDataset data = getData();
		if (data == null) {
			Assert.fail("Could not load test data");
			return;
		}
		
		IDiffractionMetadata meta = getDiffractionMetadata();
		Dataset ones = DatasetFactory.ones((Dataset)data);
		
		PixelIntegrationBean bean = new PixelIntegrationBean();
		bean.setAzimuthalIntegration(true);
		bean.setTo1D(false);
		bean.setNumberOfBinsAzimuthal(180);
		bean.setUsePixelSplitting(false);
		bean.setxAxis(XAxis.Q);
		PixelIntegrationCache info = new PixelIntegrationCache(meta, bean);
		List<Dataset> out1D = PixelIntegration.integrate(ones, null, info);
		
		Dataset dataset = out1D.get(1);
		IndexIterator iterator = dataset.getIterator();
		while (iterator.hasNext()) {
			double val = dataset.getElementDoubleAbs(iterator.index);
			if (!(val == 0 || val ==1)) {
				Assert.fail("Non zero or one value found");
			}
		}
	}
	
	@Test
	public void testSplitting1D() {
		IDataset data = getData();
		if (data == null) {
			Assert.fail("Could not load test data");
			return;
		}
		
		IDiffractionMetadata meta = getDiffractionMetadata();
		Dataset ones = DatasetFactory.ones((Dataset)data);
		
		PixelIntegrationBean bean = new PixelIntegrationBean();
		bean.setAzimuthalIntegration(true);
		bean.setUsePixelSplitting(true);
		bean.setxAxis(XAxis.Q);
		PixelIntegrationCache info = new PixelIntegrationCache(meta, bean);
		List<Dataset> out1D = PixelIntegration.integrate(ones, null, info);
		
		Dataset dataset = out1D.get(1);
		Assert.assertTrue(Comparisons.allTrue(Comparisons.equalTo(dataset, 1)));
	}
	
	@Test
	public void testSplitting2D() {
		IDataset data = getData();
		if (data == null) {
			Assert.fail("Could not load test data");
			return;
		}
		
		IDiffractionMetadata meta = getDiffractionMetadata();
		Dataset ones = DatasetFactory.ones((Dataset)data);
		
		PixelIntegrationBean bean = new PixelIntegrationBean();
		bean.setAzimuthalIntegration(true);
		bean.setTo1D(false);
		bean.setNumberOfBinsAzimuthal(180);
		bean.setUsePixelSplitting(true);
		bean.setxAxis(XAxis.Q);
		PixelIntegrationCache info = new PixelIntegrationCache(meta, bean);
		List<Dataset> out1D = PixelIntegration.integrate(ones, null, info);
		
		Dataset dataset = out1D.get(1);
		IndexIterator iterator = dataset.getIterator();
		while (iterator.hasNext()) {
			double val = dataset.getElementDoubleAbs(iterator.index);
			if (!(val == 0 || val ==1)) {
				Assert.fail("Non zero or one value found");
			}
		}
	}
	
}

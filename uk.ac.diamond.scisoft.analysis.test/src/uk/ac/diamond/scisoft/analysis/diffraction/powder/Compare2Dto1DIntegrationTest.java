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
import org.eclipse.dawnsci.analysis.api.dataset.Slice;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.roi.XAxis;

public class Compare2Dto1DIntegrationTest extends AbstractPixelIntegrationTestBase {

	@Test
	public void compareNonSplitting2Dto1D() {
		IDataset data = getData();
		if (data == null) {
			Assert.fail("Could not load test data");
			return;
		}
		
		IDiffractionMetadata meta = getDiffractionMetadata();
		
		PixelIntegrationBean bean = new PixelIntegrationBean();
		bean.setAzimuthalIntegration(true);
		bean.setUsePixelSplitting(false);
		bean.setNumberOfBinsRadial(2000);
		bean.setAzimuthalRange(new double[]{0,90});
		bean.setxAxis(XAxis.Q);
		PixelIntegrationCache info = new PixelIntegrationCache(meta, bean);
		List<Dataset> out1D = PixelIntegration.integrate(data, null, info);
		
		bean = new PixelIntegrationBean();
		bean.setTo1D(false);
		bean.setUsePixelSplitting(false);
		bean.setNumberOfBinsRadial(2000);
		bean.setNumberOfBinsAzimuthal(4);
		bean.setAzimuthalRange(new double[]{0,360});
		bean.setxAxis(XAxis.Q);
		info = new PixelIntegrationCache(meta, bean);
		List<Dataset> out2D = PixelIntegration.integrate(data, null, info);
		
		Dataset q1 = out1D.get(0);
		Dataset q2 = out2D.get(0);
		
		Dataset d1 = out1D.get(1);
		Dataset d2 = out2D.get(1);
		Dataset d21 = d2.getSlice(new Slice(1),null);
		d21.squeeze();
		
		Assert.assertTrue(q1.equals(q2));
		Assert.assertTrue(d21.equals(d1));
		
		bean = new PixelIntegrationBean();
		bean.setAzimuthalIntegration(true);
		bean.setUsePixelSplitting(false);
		bean.setNumberOfBinsRadial(2000);
		bean.setAzimuthalRange(new double[]{270,360});
		bean.setxAxis(XAxis.Q);
		info = new PixelIntegrationCache(meta, bean);
		out1D = PixelIntegration.integrate(data, null, info);
		
		q1 = out1D.get(0);
		d1 = out1D.get(1);
		Dataset d23 = d2.getSlice(new Slice(3,4),null);
		d23.squeeze();
		
		Assert.assertTrue(q1.equals(q2));
		Assert.assertTrue(d23.equals(d1));
	}
	
	@Test
	public void compareSplitting2Dto1D() {
		IDataset data = getData();
		if (data == null) {
			Assert.fail("Could not load test data");
			return;
		}
		
		IDiffractionMetadata meta = getDiffractionMetadata();
		
		PixelIntegrationBean bean = new PixelIntegrationBean();
		bean.setAzimuthalIntegration(true);
		bean.setUsePixelSplitting(true);
		bean.setNumberOfBinsRadial(2000);
		bean.setAzimuthalRange(new double[]{0,90});
		bean.setxAxis(XAxis.Q);
		PixelIntegrationCache info = new PixelIntegrationCache(meta, bean);
		List<Dataset> out1D = PixelIntegration.integrate(data, null, info);
		
		bean = new PixelIntegrationBean();
		bean.setTo1D(false);
		bean.setUsePixelSplitting(true);
		bean.setNumberOfBinsRadial(2000);
		bean.setNumberOfBinsAzimuthal(4);
		bean.setAzimuthalRange(new double[]{0,360});
		bean.setxAxis(XAxis.Q);
		info = new PixelIntegrationCache(meta, bean);
		List<Dataset> out2D = PixelIntegration.integrate(data, null, info);
		
		Dataset q1 = out1D.get(0);
		Dataset q2 = out2D.get(0);
		
		Dataset d1 = out1D.get(1);
		Dataset d2 = out2D.get(1);
		Dataset d21 = d2.getSlice(new Slice(1),null);
		d21.squeeze();
		
		Assert.assertTrue(q1.equals(q2));
		Dataset max = Maths.subtract(d21, d1);
		double test = max.max().doubleValue();
		
		Assert.assertTrue(max.max().doubleValue() < 0.0000000001);
		
		bean = new PixelIntegrationBean();
		bean.setAzimuthalIntegration(true);
		bean.setUsePixelSplitting(true);
		bean.setNumberOfBinsRadial(2000);
		bean.setAzimuthalRange(new double[]{270,360});
		bean.setxAxis(XAxis.Q);
		info = new PixelIntegrationCache(meta, bean);
		out1D = PixelIntegration.integrate(data, null, info);
		
		q1 = out1D.get(0);
		d1 = out1D.get(1);
		Dataset d23 = d2.getSlice(new Slice(3,4),null);
		d23.squeeze();
		
		Assert.assertTrue(q1.equals(q2));
		max = Maths.subtract(d23, d1);
		test = max.max().doubleValue();
		
		Assert.assertTrue(max.max().doubleValue() < 100);
	}
	
	@Test
	public void compareSplitting2Dto2D() {
		IDataset data = getData();
		if (data == null) {
			Assert.fail("Could not load test data");
			return;
		}
		
		IDiffractionMetadata meta = getDiffractionMetadata();
		
		PixelIntegrationBean bean = new PixelIntegrationBean();
		bean.setTo1D(false);
		bean.setUsePixelSplitting(true);
		bean.setNumberOfBinsRadial(2000);
		bean.setNumberOfBinsAzimuthal(4);
		bean.setAzimuthalRange(new double[]{0,360});
		bean.setxAxis(XAxis.Q);
		PixelIntegrationCache info = new PixelIntegrationCache(meta, bean);
		List<Dataset> out2D = PixelIntegration.integrate(data, null, info);
		

		bean = new PixelIntegrationBean();
		bean.setTo1D(false);
		bean.setUsePixelSplitting(true);
		bean.setNumberOfBinsRadial(2000);
		bean.setNumberOfBinsAzimuthal(4);
		bean.setAzimuthalRange(new double[]{90,450});
		bean.setxAxis(XAxis.Q);
		info = new PixelIntegrationCache(meta, bean);
		List<Dataset> out2D90 = PixelIntegration.integrate(data, null, info);
		
		
		Dataset q1 = out2D.get(0);
		Dataset q2 = out2D90.get(0);
		Assert.assertTrue(q1.equals(q2));
		
		Dataset d12 = out2D.get(1).getSlice(new Slice(1,2),null).squeeze();
		Dataset d01 = out2D90.get(1).getSlice(new Slice(1),null).squeeze();
		
		
		Dataset max = Maths.subtract(d12, d01);
		//not identical due to move of discontinuity
		double test = max.max().doubleValue();
		
		Assert.assertTrue(max.max().doubleValue() < 110);
		
	}
	
	@Test
	public void compareNonSplitting2Dto2D() {
		IDataset data = getData();
		if (data == null) {
			Assert.fail("Could not load test data");
			return;
		}
		
		IDiffractionMetadata meta = getDiffractionMetadata();
		
		PixelIntegrationBean bean = new PixelIntegrationBean();
		bean.setTo1D(false);
		bean.setUsePixelSplitting(false);
		bean.setNumberOfBinsRadial(2000);
		bean.setNumberOfBinsAzimuthal(4);
		bean.setAzimuthalRange(new double[]{0,360});
		bean.setxAxis(XAxis.Q);
		PixelIntegrationCache info = new PixelIntegrationCache(meta, bean);
		List<Dataset> out2D = PixelIntegration.integrate(data, null, info);
		

		bean = new PixelIntegrationBean();
		bean.setTo1D(false);
		bean.setUsePixelSplitting(false);
		bean.setNumberOfBinsRadial(2000);
		bean.setNumberOfBinsAzimuthal(4);
		bean.setAzimuthalRange(new double[]{90,450});
		bean.setxAxis(XAxis.Q);
		info = new PixelIntegrationCache(meta, bean);
		List<Dataset> out2D90 = PixelIntegration.integrate(data, null, info);
		
		
		Dataset q1 = out2D.get(0);
		Dataset q2 = out2D90.get(0);
		Assert.assertTrue(q1.equals(q2));
		
		Dataset d12 = out2D.get(1).getSlice(new Slice(1,2),null).squeeze();
		Dataset d01 = out2D90.get(1).getSlice(new Slice(1),null).squeeze();
		
		Assert.assertTrue(d12.equals(d01));
		
	}
	
	
}

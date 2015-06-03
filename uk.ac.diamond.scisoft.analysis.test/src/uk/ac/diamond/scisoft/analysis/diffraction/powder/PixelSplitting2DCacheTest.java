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
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.roi.XAxis;

public class PixelSplitting2DCacheTest extends AbstractPixelIntegrationTestBase {

	@Test
	public void testPixelSplitting2DAzimuthal() {
		
		IDataset data = getData();
		if (data == null) {
			Assert.fail("Could not load test data");
			return;
		}
		
		IDiffractionMetadata meta = getDiffractionMetadata();
//		PixelSplittingIntegration2D npsi = new PixelSplittingIntegration2D(meta,1592,1592);
		
		PixelIntegrationBean bean = new PixelIntegrationBean();
		bean.setUsePixelSplitting(true);
		bean.setTo1D(false);
		IPixelIntegrationCache info = new PixelIntegrationCache(meta, bean);
		
		//first pass
		double firstTime = testWholeImage(data,null,info);
		//second pass
		double secondTime = testWholeImage(data,null,info);
		
		bean.setAzimuthalRange(new double[]{-180,-170});
		info = new PixelIntegrationCache(meta, bean);
		//first pass
		firstTime =testSectionImageAzimuthal(data,null,info);
		//first pass
		secondTime =testSectionImageAzimuthal(data,null,info);
		
		//similar time
		
		bean.setRadialRange(new double[]{1,5});
		info = new PixelIntegrationCache(meta, bean);
		//first pass
		firstTime =testSectionLimitedImageAzimuthal(data,null,info);
		//second pass
		secondTime =testSectionLimitedImageAzimuthal(data,null,info);;
		
		//probably should take a similar time
		//Test different size axes
//		npsi = new PixelSplittingIntegration2D(meta,1592,360);
		bean = new PixelIntegrationBean();
		bean.setUsePixelSplitting(true);
		bean.setTo1D(false);
		bean.setNumberOfBinsAzimuthal(360);
		bean.setNumberOfBinsRadial(1592);;
		info = new PixelIntegrationCache(meta, bean);
		//first pass
		firstTime =testDifferentSizeAxis(data,null,info);
		//first pass
		secondTime =testDifferentSizeAxis(data,null,info);

	}
	
	@Test
	public void testPixelSplittingBinSetting() {
		IDataset data = getData();
		if (data == null) {
			Assert.fail("Could not load test data");
			return;
		}
		
		IDiffractionMetadata meta = getDiffractionMetadata();
		PixelIntegrationBean bean = new PixelIntegrationBean();
		bean.setUsePixelSplitting(true);
		bean.setTo1D(false);
		IPixelIntegrationCache info = new PixelIntegrationCache(meta, bean);
		
		testWholeImage(data,null,info);
		bean.setNumberOfBinsRadial(1000);
		bean.setNumberOfBinsAzimuthal(500);
		info = new PixelIntegrationCache(meta, bean);
		testWholeImage1000Bins(data,null,info);
	}
	
	@Test
	public void testNonPixelSplittingAxis() {
		
		IDataset data = getData();
		if (data == null) {
			Assert.fail("Could not load test data");
			return;
		}
		
		IDiffractionMetadata meta = getDiffractionMetadata();
//		PixelSplittingIntegration2D npsi = new PixelSplittingIntegration2D(meta, 1592,1592);
		PixelIntegrationBean bean = new PixelIntegrationBean();
		bean.setUsePixelSplitting(true);
		bean.setTo1D(false);
		IPixelIntegrationCache info = new PixelIntegrationCache(meta, bean);
		//2theta
		bean.setxAxis(XAxis.ANGLE);
		info = new PixelIntegrationCache(meta, bean);
		List<Dataset> out = PixelIntegration.integrate(data, null, info);
		Assert.assertEquals(41.265791653389, out.get(0).getDouble(1591),0.00001);
		bean.setRadialRange(new double[]{10,30});
		info = new PixelIntegrationCache(meta, bean);
		out = PixelIntegration.integrate(data, null, info);
		Assert.assertEquals(30, out.get(0).getDouble(1591),0.00001);
		bean.setRadialRange(null);
		
		//q
		bean.setxAxis(XAxis.Q);
		info = new PixelIntegrationCache(meta, bean);
		out = PixelIntegration.integrate(data, null, info);
		Assert.assertEquals(10.402591860873361, out.get(0).getDouble(1591),0.00001);
		bean.setRadialRange(new double[]{1,5});
		info = new PixelIntegrationCache(meta, bean);
		out = PixelIntegration.integrate(data, null, info);
		Assert.assertEquals(5, out.get(0).getDouble(1591),0.00001);
		bean.setRadialRange(null);
		
		//d-spacing
		bean.setxAxis(XAxis.RESOLUTION);
		info = new PixelIntegrationCache(meta, bean);
		out = PixelIntegration.integrate(data, null, info);
		Assert.assertEquals(0.6040019056031748, out.get(0).getDouble(1591),0.00001);
		bean.setRadialRange(new double[]{0.6,4});
		info = new PixelIntegrationCache(meta, bean);
		out = PixelIntegration.integrate(data, null, info);
		Assert.assertEquals(0.6, out.get(0).getDouble(1591),0.00001);
		bean.setRadialRange(null);
		//pixel
		bean.setxAxis(XAxis.PIXEL);
		info = new PixelIntegrationCache(meta, bean);
		out = PixelIntegration.integrate(data, null, info);
		Assert.assertEquals(1591.823246256255, out.get(0).getDouble(1591),0.00001);
		bean.setRadialRange(new double[]{100,300});
		info = new PixelIntegrationCache(meta, bean);
		out = PixelIntegration.integrate(data, null, info);
		Assert.assertEquals(300, out.get(0).getDouble(1591),0.00001);
		
	}
	
	private double testWholeImage(IDataset data, IDataset mask, IPixelIntegrationCache info) {
		long before = System.currentTimeMillis();
		List<Dataset> out = PixelIntegration.integrate(data,mask,info);
		long after = System.currentTimeMillis();
		System.out.println("Non pixel splitting (full) in "+(after-before));
		
		if (out.size() != 3) {
			Assert.fail("Incorrect number of datasets returned");
		}
		double max = out.get(1).max().doubleValue();
		double min = out.get(1).min().doubleValue();
		double maxq = out.get(0).max().doubleValue();
		double minq = out.get(0).min().doubleValue();
		double maxa = out.get(2).max().doubleValue();
		double mina = out.get(2).min().doubleValue();
		
		Assert.assertEquals(662878.0, max,0.00001);
		Assert.assertEquals(-26270.607421875, min,0.00001);
		Assert.assertEquals(10.402591860873361, maxq,0.00001);
		Assert.assertEquals(0.007293853017842496, minq,0.00001);
		Assert.assertEquals(179.86865947533707, maxa,0.00001);
		Assert.assertEquals(-179.88694041486792, mina,0.00001);
		
		return after-before;
	}
	
	private double testSectionImageAzimuthal(IDataset data, IDataset mask, IPixelIntegrationCache info) {
		long before = System.currentTimeMillis();
		List<Dataset> out = PixelIntegration.integrate(data,mask,info);
		long after = System.currentTimeMillis();
		System.out.println("Non pixel splitting (section) in "+(after-before));
		
		if (out.size() != 3) {
			Assert.fail("Incorrect number of datasets returned");
		}
		double max = out.get(1).max().doubleValue();
		double min = out.get(1).min().doubleValue();
		double maxq = out.get(0).max().doubleValue();
		double minq = out.get(0).min().doubleValue();
		double maxa = out.get(2).max().doubleValue();
		double mina = out.get(2).min().doubleValue();
		
		Assert.assertEquals(444024.03125, max,0.00001);
		Assert.assertEquals(-8611.45703125, min,0.00001);
		Assert.assertEquals(10.402591860873361, maxq,0.00001);
		Assert.assertEquals(0.007293853017842496, minq,0.00001);
		Assert.assertEquals(-170, maxa,0.00001);
		Assert.assertEquals(-180, mina,0.00001);
		
		return after-before;
	}
	
	private double testSectionLimitedImageAzimuthal(IDataset data, IDataset mask, IPixelIntegrationCache info) {
		long before = System.currentTimeMillis();
		List<Dataset> out = PixelIntegration.integrate(data,mask,info);
		long after = System.currentTimeMillis();
		System.out.println("Non pixel splitting (section, limited q) in "+(after-before));
		
		if (out.size() != 3) {
			Assert.fail("Incorrect number of datasets returned");
		}
		double max = out.get(1).max().doubleValue();
		double min = out.get(1).min().doubleValue();
		double maxq = out.get(0).max().doubleValue();
		double minq = out.get(0).min().doubleValue();
		double maxa = out.get(2).max().doubleValue();
		double mina = out.get(2).min().doubleValue();
		
		Assert.assertEquals(463957.0, max,0.00001);
		Assert.assertEquals(-405.9999694824219, min,0.00001);
		Assert.assertEquals(5, maxq,0.00001);
		Assert.assertEquals(1, minq,0.00001);
		Assert.assertEquals(-170, maxa,0.00001);
		Assert.assertEquals(-180, mina,0.00001);
		return after-before;
	}
	
	private double testDifferentSizeAxis(IDataset data, IDataset mask, IPixelIntegrationCache info) {
		long before = System.currentTimeMillis();
		List<Dataset> out = PixelIntegration.integrate(data,mask,info);
		long after = System.currentTimeMillis();
		System.out.println("Non pixel splitting (different axis) in "+(after-before));
		
		if (out.size() != 3) {
			Assert.fail("Incorrect number of datasets returned");
		}
		double max = out.get(1).max().doubleValue();
		double min = out.get(1).min().doubleValue();
		double maxq = out.get(0).max().doubleValue();
		double minq = out.get(0).min().doubleValue();
		double maxa = out.get(2).max().doubleValue();
		double mina = out.get(2).min().doubleValue();
		
		Assert.assertEquals(610043.375, max,0.00001);
		Assert.assertEquals(-5393.3134765625, min,0.00001);
		Assert.assertEquals(10.402591860873361, maxq,0.00001);
		Assert.assertEquals(0.007293853017842496, minq,0.00001);
		Assert.assertEquals(179.48174445066292, maxa,0.00001);
		Assert.assertEquals(-179.5000253901938, mina,0.00001);
		return after-before;
	}
	
	private double testWholeImage1000Bins(IDataset data, IDataset mask, IPixelIntegrationCache info) {
		long before = System.currentTimeMillis();
		List<Dataset> out = PixelIntegration.integrate(data,mask,info);
		long after = System.currentTimeMillis();
		System.out.println("Non pixel splitting (1000 bins) in "+(after-before));
		
		if (out.size() != 3) {
			Assert.fail("Incorrect number of datasets returned");
		}
		
		if (out.get(0).getSize() != 1000 && out.get(2).getSize()!=500) {
			Assert.fail("Incorrect number of points");
		}
		double max = out.get(1).max().doubleValue();
		double min = out.get(1).min().doubleValue();
		double maxq = out.get(0).max().doubleValue();
		double minq = out.get(0).min().doubleValue();
		Assert.assertEquals(363110.0625, max,0.00001);
		Assert.assertEquals(-2562.922119140625, min,0.00001);
		Assert.assertEquals(10.400657851941666, maxq,0.00001);
		Assert.assertEquals(0.009227861949536547, minq,0.00001);
		
		return after-before;
	}
	
}

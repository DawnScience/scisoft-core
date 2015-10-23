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
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.roi.XAxis;

public class NonPixelSplitting2DCacheTest extends AbstractPixelIntegrationTestBase {

	@Test
	public void testNonPixelSplitting2DAzimuthal() {
		
		IDataset data = getData();
		if (data == null) {
			Assert.fail("Could not load test data");
			return;
		}
		
		IDiffractionMetadata meta = getDiffractionMetadata();
		PixelIntegrationBean bean = new PixelIntegrationBean();
		bean.setTo1D(false);
		bean.setUsePixelSplitting(false);
		bean.setNumberOfBinsAzimuthal(1592);
		bean.setNumberOfBinsRadial(1592);
		IPixelIntegrationCache info = new PixelIntegrationCache(meta, bean);
//		NonPixelSplittingIntegration2D npsi = new NonPixelSplittingIntegration2D(meta,1592,1592);
		
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
		secondTime =testSectionLimitedImageAzimuthal(data,null,info);
		
		//probably should take a similar time
		//Test different size axes
		bean.setNumberOfBinsAzimuthal(360);
		bean.setRadialRange(null);
		bean.setAzimuthalRange(null);
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
		bean.setTo1D(false);
		bean.setUsePixelSplitting(false);
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
		PixelIntegrationBean bean = new PixelIntegrationBean();
		bean.setTo1D(false);
		bean.setUsePixelSplitting(false);
		bean.setNumberOfBinsAzimuthal(1592);
		bean.setNumberOfBinsRadial(1592);
		
		
//		NonPixelSplittingIntegration2D npsi = new NonPixelSplittingIntegration2D(meta, 1592,1592);
		//2theta
		bean.setxAxis(XAxis.ANGLE);
		IPixelIntegrationCache info = new PixelIntegrationCache(meta, bean);
		List<Dataset> out = PixelIntegration.integrate(data,null,info);
		Assert.assertEquals(41.25306201329643, out.get(0).getDouble(1591),0.00001);
//		npsi.setRadialRange(new double[]{10,30});
		bean.setRadialRange(new double[]{10,30});
		info = new PixelIntegrationCache(meta, bean);
		out = PixelIntegration.integrate(data,null,info);
		Assert.assertEquals(30, out.get(0).getDouble(1591),0.00001);
		bean.setRadialRange(null);
		
		//q
		bean.setxAxis(XAxis.Q);
		info = new PixelIntegrationCache(meta, bean);
		out = PixelIntegration.integrate(data,null,info);
		Assert.assertEquals(10.399522783579055, out.get(0).getDouble(1591),0.00001);
		bean.setRadialRange(new double[]{1,5});
		info = new PixelIntegrationCache(meta, bean);
		out = PixelIntegration.integrate(data,null,info);
		Assert.assertEquals(5, out.get(0).getDouble(1591),0.00001);
		bean.setRadialRange(null);
		
		//d-spacing
		bean.setxAxis(XAxis.RESOLUTION);
		info = new PixelIntegrationCache(meta, bean);
		out = PixelIntegration.integrate(data,null,info);
		Assert.assertEquals(0.6041801569107378, out.get(0).getDouble(1591),0.00001);
		bean.setRadialRange(new double[]{0.6,4});
		info = new PixelIntegrationCache(meta, bean);
		out = PixelIntegration.integrate(data,null,info);
		Assert.assertEquals(0.6, out.get(0).getDouble(1591),0.00001);
		bean.setRadialRange(null);
		//pixel
		bean.setxAxis(XAxis.PIXEL);
		info = new PixelIntegrationCache(meta, bean);
		out = PixelIntegration.integrate(data,null,info);
		Assert.assertEquals(1591.1174742301246, out.get(0).getDouble(1591),0.00001);
		bean.setRadialRange(new double[]{100,300});
		info = new PixelIntegrationCache(meta, bean);
		out = PixelIntegration.integrate(data,null,info);
		Assert.assertEquals(300, out.get(0).getDouble(1591),0.00001);
		
	}
	
	private double testWholeImage(IDataset data, IDataset mask, IPixelIntegrationCache info) {
		long before = System.currentTimeMillis();
		List<Dataset> out = PixelIntegration.integrate(data,mask,info);
		long after = System.currentTimeMillis();
		System.out.println("Non pixel splitting 2D (full) in "+(after-before));
		
		if (out.size() != 3) {
			Assert.fail("Incorrect number of datasets returned");
		}
		double max = out.get(1).max().doubleValue();
		double min = out.get(1).min().doubleValue();
		double maxq = out.get(0).max().doubleValue();
		double minq = out.get(0).min().doubleValue();
		double maxa = out.get(2).max().doubleValue();
		double mina = out.get(2).min().doubleValue();
		
		Assert.assertEquals(681098.0, max,0.00001);
//		Assert.assertEquals(-29738.400390625, min,0.00001);
		Assert.assertEquals(10.399522783579055, maxq,0.00001);
		Assert.assertEquals(0.004975613824126736, minq,0.00001);
		Assert.assertEquals(179.84457062163236, maxa,0.00001);
		Assert.assertEquals(-179.88114786702272, mina,0.00001);
		
		return after-before;
	}
	
	private double testSectionImageAzimuthal(IDataset data, IDataset mask, IPixelIntegrationCache info) {
		long before = System.currentTimeMillis();
		List<Dataset> out = PixelIntegration.integrate(data,mask,info);
		long after = System.currentTimeMillis();
		System.out.println("Non pixel splitting 2D (section) in "+(after-before));
		
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
		Assert.assertEquals(-17620.0, min,0.00001);
		Assert.assertEquals(10.399522783579055, maxq,0.00001);
		Assert.assertEquals(0.004975613824126736, minq,0.00001);
		Assert.assertEquals(-170, maxa,0.005);
		Assert.assertEquals(-180, mina,0.005);
		
		return after-before;
	}
	
	private double testSectionLimitedImageAzimuthal(IDataset data, IDataset mask, IPixelIntegrationCache info) {
		long before = System.currentTimeMillis();
		List<Dataset> out = PixelIntegration.integrate(data,mask,info);
		long after = System.currentTimeMillis();
		System.out.println("Non pixel splitting 2D (section, limited q) in "+(after-before));
		
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
		Assert.assertEquals(-406.0, min,0.00001);
		Assert.assertEquals(5, maxq,0.00001);
		Assert.assertEquals(1, minq,0.00001);
		Assert.assertEquals(-170, maxa,0.005);
		Assert.assertEquals(-180, mina,0.005);
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
		
		Assert.assertEquals(630334.25, max,0.00001);
		Assert.assertEquals(-7683.9444444444, min,0.00001);
		Assert.assertEquals(10.399522783579055, maxq,0.00001);
		Assert.assertEquals(0.004975613824126736, minq,0.00001);
		Assert.assertEquals(179.45768773422927, maxa,0.00001);
		Assert.assertEquals(-179.49426497961957, mina,0.00001);
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
		Assert.assertEquals(625602.33333333, max,0.00001);
		Assert.assertEquals(-6840.45, min,0.00001);
		Assert.assertEquals(10.397588914338172, maxq,0.00001);
		Assert.assertEquals(0.006909483065011375, minq,0.00001);
		
		return after-before;
	}
}

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

public class PixelSplittingCacheTest extends AbstractPixelIntegrationTestBase {
	
	@Test
	public void testPixelSplittingAzimuthal() {
		
		IDataset data = getData();
		if (data == null) {
			Assert.fail("Could not load test data");
			return;
		}
		
		IDiffractionMetadata meta = getDiffractionMetadata();
		
//		int[] shape = new int[]{meta.getDetector2DProperties().getPy(), meta.getDetector2DProperties().getPx()};
//		int binTest = AbstractPixelIntegration.calculateNumberOfBins(meta.getDetector2DProperties().getBeamCentreCoords(), shape);
//		
//		Assert.assertEquals(binTest,1592);
//		
		PixelSplittingIntegration npsi = new PixelSplittingIntegration(meta, 1592);
		PixelIntegrationBean bean = new PixelIntegrationBean();
		bean.setUsePixelSplitting(true);
		IPixelIntegrationCache info = new PixelIntegrationCache(meta, bean);
		
		//first pass
		double firstTime = testWholeImageAzimuthal(data,null,info);
		//second pass
		double secondTime = testWholeImageAzimuthal(data,null,info);
		
//		if (firstTime < secondTime) {
//			Assert.fail("Whole image: second run should be faster due to caching, something odd is afoot");
//		}
		
		bean.setAzimuthalRange(new double[]{-180,-170});
		info = new PixelIntegrationCache(meta, bean);
		//first pass
		firstTime =testSectionImageAzimuthal(data,null,info);
		//first pass
		secondTime =testSectionImageAzimuthal(data,null,info);
		
//		if (firstTime < secondTime) {
//			Assert.fail("Sector: Second run should be faster due to caching, something odd is afoot");
//		}
		
		bean.setRadialRange(new double[]{1,5});
		info = new PixelIntegrationCache(meta, bean);
		//first pass
		firstTime =testSectionLimitedImageAzimuthal(data,null,info);
		//second pass
		secondTime =testSectionLimitedImageAzimuthal(data,null,info);
		
		//probably should take a similar time
		bean.setRadialRange(null);
		bean.setAzimuthalRange(null);
		Dataset mask = getMask(data.getShape());
//		npsi.setMask(mask);
		info = new PixelIntegrationCache(meta, bean);
		firstTime =testMask(data,mask,info);
		secondTime =testMask(data,mask,info);

	}
	
	@Test
	public void testPixelSplittingRadial() {
		
		IDataset data = getData();
		if (data == null) {
			Assert.fail("Could not load test data");
			return;
		}
		
		IDiffractionMetadata meta = getDiffractionMetadata();
//		PixelSplittingIntegration npsi = new PixelSplittingIntegration(meta, 1592);
//		npsi.setAzimuthalIntegration(false);
		PixelIntegrationBean bean = new PixelIntegrationBean();
		bean.setUsePixelSplitting(true);
		bean.setAzimuthalIntegration(false);
		bean.setNumberOfBinsRadial(1592);
		IPixelIntegrationCache info = new PixelIntegrationCache(meta, bean);
		
		//first pass
		double firstTime = testWholeImageRadial(data,null,info);
		//second pass
		double secondTime = testWholeImageRadial(data,null,info);
		
		bean.setRadialRange(new double[]{1,5});
		info = new PixelIntegrationCache(meta, bean);
		//first pass
		firstTime =testLimitedRadial(data,null,info);
		//first pass
		secondTime =testLimitedRadial(data,null,info);
		
		if (firstTime < secondTime) {
			Assert.fail("Sector: Second run should be faster due to caching, something odd is afoot");
		}
		
		bean.setAzimuthalRange(new double[]{-180,-170});
		info = new PixelIntegrationCache(meta, bean);
		//first pass
		firstTime =testSectorLimitedRadial(data,null,info);
		//second pass
		secondTime =testSectorLimitedRadial(data,null,info);
		
		//probably should take a similar time
	}
	
	@Test
	public void testPixelSplittingBinSetting() {
		IDataset data = getData();
		if (data == null) {
			Assert.fail("Could not load test data");
			return;
		}
		
		IDiffractionMetadata meta = getDiffractionMetadata();
		PixelSplittingIntegration npsi = new PixelSplittingIntegration(meta);
		PixelIntegrationBean bean = new PixelIntegrationBean();
		bean.setUsePixelSplitting(true);
		bean.setNumberOfBinsRadial(1592);
		IPixelIntegrationCache info = new PixelIntegrationCache(meta, bean);
		
		testWholeImageAzimuthal(data,null,info);
		bean.setNumberOfBinsRadial(1000);
		info = new PixelIntegrationCache(meta, bean);
		testWholeImageAzimuthal1000Bins(data,null,info);
	}
	
	@Test
	public void testPixelSplittingAxis() {
		
		IDataset data = getData();
		if (data == null) {
			Assert.fail("Could not load test data");
			return;
		}
		
		IDiffractionMetadata meta = getDiffractionMetadata();
//		PixelSplittingIntegration npsi = new PixelSplittingIntegration(meta, 1592);
		PixelIntegrationBean bean = new PixelIntegrationBean();
		bean.setUsePixelSplitting(true);
		bean.setNumberOfBinsRadial(1592);
		
		//2theta
		bean.setxAxis(XAxis.ANGLE);
		IPixelIntegrationCache info = new PixelIntegrationCache(meta, bean);
		List<Dataset> out = PixelIntegration.integrate(data, null, info);
		bean.setRadialRange(new double[]{10,30});
		info = new PixelIntegrationCache(meta, bean);
		out = PixelIntegration.integrate(data, null, info);
		Assert.assertEquals(30, out.get(0).getDouble(1591),0.00001);
		bean.setRadialRange(null);
		
		//q
		bean.setxAxis(XAxis.Q);
		info = new PixelIntegrationCache(meta, bean);
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
	
	private double testWholeImageAzimuthal(IDataset data, IDataset mask, IPixelIntegrationCache info) {
		long before = System.currentTimeMillis();
		List<Dataset> out = PixelIntegration.integrate(data,mask,info);
		long after = System.currentTimeMillis();
		System.out.println("pixel splitting (full) in "+(after-before));
		
		if (out.size() != 2) {
			
			Assert.fail("Incorrect number of datasets returned");
		}
		double max = out.get(1).max().doubleValue();
		double min = out.get(1).min().doubleValue();
		double maxq = out.get(0).max().doubleValue();
		double minq = out.get(0).min().doubleValue();
		Assert.assertEquals(356896.1287301564, max,0.00001);
		Assert.assertEquals(136.58182845762153, min,0.00001);
		Assert.assertEquals(10.402591860873361, maxq,0.00001);
		Assert.assertEquals(0.007293853017842496, minq,0.00001);
		
		return after-before;
	}
	
	private double testSectionImageAzimuthal(IDataset data, IDataset mask, IPixelIntegrationCache info) {
		long before = System.currentTimeMillis();
		List<Dataset> out = PixelIntegration.integrate(data,mask,info);
		long after = System.currentTimeMillis();
		System.out.println("pixel splitting (section) in "+(after-before));
		
		if (out.size() != 2) {
			Assert.fail("Incorrect number of datasets returned");
		}
		double max = out.get(1).max().doubleValue();
		double min = out.get(1).min().doubleValue();
		double maxq = out.get(0).max().doubleValue();
		double minq = out.get(0).min().doubleValue();
		Assert.assertEquals(336830.76975107513, max,0.00001);
		Assert.assertEquals(0, min,0.00001);
		Assert.assertEquals(10.402591860873361, maxq,0.00001);
		Assert.assertEquals(0.007293853017842496, minq,0.00001);
		return after-before;
	}
	
	private double testSectionLimitedImageAzimuthal(IDataset data, IDataset mask, IPixelIntegrationCache info) {
		long before = System.currentTimeMillis();
		List<Dataset> out = PixelIntegration.integrate(data,mask,info);
		long after = System.currentTimeMillis();
		System.out.println("pixel splitting (section, limited q) in "+(after-before));
		
		if (out.size() != 2) {
			Assert.fail("Incorrect number of datasets returned");
		}
		double max = out.get(1).max().doubleValue();
		double min = out.get(1).min().doubleValue();
		double maxq = out.get(0).max().doubleValue();
		double minq = out.get(0).min().doubleValue();
		Assert.assertEquals(370268.363719988, max,0.00001);
		Assert.assertEquals(834.8830331973187, min,0.00001);
		Assert.assertEquals(5, maxq,0.00001);
		Assert.assertEquals(1, minq,0.00001);
		return after-before;
	}
	
	private double testWholeImageRadial(IDataset data, IDataset mask, IPixelIntegrationCache info) {
		long before = System.currentTimeMillis();
		List<Dataset> out = PixelIntegration.integrate(data,mask,info);
		long after = System.currentTimeMillis();
		System.out.println("pixel splitting (radial full) in "+(after-before));
		
		if (out.size() != 2) {
			
			Assert.fail("Incorrect number of datasets returned");
		}
		double max = out.get(1).max().doubleValue();
		double min = out.get(1).min().doubleValue();
		double maxq = out.get(0).max().doubleValue();
		double minq = out.get(0).min().doubleValue();
		Assert.assertEquals(4003.5619624141973, max,0.00001);
		Assert.assertEquals(2004.8229765125104, min,0.00001);
		Assert.assertEquals(179.86865947533707, maxq,0.00001);
		Assert.assertEquals(-179.88694041486792, minq,0.00001);
		
		return after-before;
	}
	
	private double testLimitedRadial(IDataset data, IDataset mask, IPixelIntegrationCache info) {
		long before = System.currentTimeMillis();
		List<Dataset> out = PixelIntegration.integrate(data,mask,info);
		long after = System.currentTimeMillis();
		System.out.println("pixel splitting (radial limited) in "+(after-before));
		
		if (out.size() != 2) {
			
			Assert.fail("Incorrect number of datasets returned");
		}
		double max = out.get(1).max().doubleValue();
		double min = out.get(1).min().doubleValue();
		double maxq = out.get(0).max().doubleValue();
		double minq = out.get(0).min().doubleValue();
		Assert.assertEquals(5346.04087202132, max,0.00001);
		Assert.assertEquals(3801.9952775754186, min,0.00001);
		Assert.assertEquals(179.86865947533707, maxq,0.00001);
		Assert.assertEquals(-179.88694041486792, minq,0.00001);
		
		return after-before;
	}
	
	private double testSectorLimitedRadial(IDataset data, IDataset mask, IPixelIntegrationCache info) {
		long before = System.currentTimeMillis();
		List<Dataset> out = PixelIntegration.integrate(data,mask,info);
		long after = System.currentTimeMillis();
		System.out.println("pixel splitting (radial limited sector) in "+(after-before));
		
		if (out.size() != 2) {
			
			Assert.fail("Incorrect number of datasets returned");
		}
		double max = out.get(1).max().doubleValue();
		double min = out.get(1).min().doubleValue();
		double maxq = out.get(0).max().doubleValue();
		double minq = out.get(0).min().doubleValue();
		Assert.assertEquals(5488.909329548431, max,0.00001);
		Assert.assertEquals(3902.7255524746024, min,0.00001);
		Assert.assertEquals(-170, maxq,0.00001);
		Assert.assertEquals(-180, minq,0.00001);
		
		return after-before;
	}
	
	private double testMask(IDataset data, IDataset mask, IPixelIntegrationCache info) {
		long before = System.currentTimeMillis();
		List<Dataset> out = PixelIntegration.integrate(data,mask,info);
		long after = System.currentTimeMillis();
		System.out.println("Non pixel splitting (radial limited sector) in "+(after-before));
		
		if (out.size() != 2) {
			
			Assert.fail("Incorrect number of datasets returned");
		}
		double max = out.get(1).max().doubleValue();
		double min = out.get(1).min().doubleValue();
		double maxq = out.get(0).max().doubleValue();
		double minq = out.get(0).min().doubleValue();
		Assert.assertEquals(351256.4931002594, max,0.00001);
		Assert.assertEquals(0, min,0.00001);
		Assert.assertEquals(10.4025918608733616, maxq,0.00001);
		Assert.assertEquals(0.007293853017842496, minq,0.00001);
		
		return after-before;
	}
	
	private double testWholeImageAzimuthal1000Bins(IDataset data, IDataset mask, IPixelIntegrationCache info) {
		long before = System.currentTimeMillis();
		List<Dataset> out = PixelIntegration.integrate(data,mask,info);
		long after = System.currentTimeMillis();
		System.out.println("Non pixel splitting (1000 bins) in "+(after-before));
		
		if (out.size() != 2) {
			Assert.fail("Incorrect number of datasets returned");
		}
		
		if (out.get(0).getSize() != 1000) {
			Assert.fail("Incorrect number of points");
		}
		double max = out.get(1).max().doubleValue();
		double min = out.get(1).min().doubleValue();
		double maxq = out.get(0).max().doubleValue();
		double minq = out.get(0).min().doubleValue();
		Assert.assertEquals(255619.6554727802, max,0.00001);
		Assert.assertEquals(142.85434233832973, min,0.00001);
		Assert.assertEquals(10.400657851941666, maxq,0.00001);
		Assert.assertEquals(0.009227861949536547, minq,0.00001);
		
		return after-before;
	}
	
	
}

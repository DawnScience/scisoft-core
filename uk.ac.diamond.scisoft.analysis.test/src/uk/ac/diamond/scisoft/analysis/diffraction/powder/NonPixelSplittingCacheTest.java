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
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
import org.junit.Ignore;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.roi.XAxis;

public class NonPixelSplittingCacheTest extends AbstractPixelIntegrationTestBase {

	@Test
	public void testNonPixelSplittingAzimuthal() {
		
		IDataset data = getData();
		if (data == null) {
			Assert.fail("Could not load test data");
			return;
		}
		
		IDiffractionMetadata meta = getDiffractionMetadata();
//		NonPixelSplittingIntegration npsi = new NonPixelSplittingIntegration(meta, 1592);
		PixelIntegrationBean bean = new PixelIntegrationBean();
		bean.setTo1D(true);
		bean.setUsePixelSplitting(false);
		IPixelIntegrationCache info = new PixelIntegrationCache(meta, bean);
		
		//first pass
		double firstTime = testWholeImageAzimuthal(data,null,info);
		//second pass
		double secondTime = testWholeImageAzimuthal(data,null,info);
		
		
		bean.setAzimuthalRange(new double[]{-180,-170});
		info = new PixelIntegrationCache(meta, bean);
		//first pass
		firstTime =testSectionImageAzimuthal(data,null,info);
		//first pass
		secondTime =testSectionImageAzimuthal(data,null,info);
		
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
		info = new PixelIntegrationCache(meta, bean);
//		npsi.setMask(mask);
		
		firstTime =testMask(data,mask,info);
		secondTime =testMask(data,mask,info);
		//probably should take a similar time
		
	}
	
	@Test
	public void testNonPixelSplittingRadial() {
		
		IDataset data = getData();
		if (data == null) {
			Assert.fail("Could not load test data");
			return;
		}
		
		IDiffractionMetadata meta = getDiffractionMetadata();
		NonPixelSplittingIntegration npsi = new NonPixelSplittingIntegration(meta, 1592);
		PixelIntegrationBean bean = new PixelIntegrationBean();
		bean.setTo1D(true);
		bean.setUsePixelSplitting(false);
		bean.setAzimuthalIntegration(false);
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
		
//		
		bean.setAzimuthalRange(new double[]{-180,-170});
		info = new PixelIntegrationCache(meta, bean);
		//first pass
		firstTime =testSectorLimitedRadial(data,null,info);
		//second pass
		secondTime =testSectorLimitedRadial(data,null,info);
		
		//probably should take a similar time
	}
	
	@Test
	public void testNonPixelSplittingBinSetting() {
		IDataset data = getData();
		if (data == null) {
			Assert.fail("Could not load test data");
			return;
		}
		
		IDiffractionMetadata meta = getDiffractionMetadata();
		NonPixelSplittingIntegration npsi = new NonPixelSplittingIntegration(meta);
		PixelIntegrationBean bean = new PixelIntegrationBean();
		bean.setTo1D(true);
		bean.setUsePixelSplitting(false);
		IPixelIntegrationCache info = new PixelIntegrationCache(meta, bean);
		
		testWholeImageAzimuthal(data,null,info);
		bean.setNumberOfBinsRadial(1000);
		info = new PixelIntegrationCache(meta, bean);
		testWholeImageAzimuthal1000Bins(data,null,info);
	}
	
	@Test
	public void testNonPixelSplittingAxis() {
		
		IDataset data = getData();
		if (data == null) {
			Assert.fail("Could not load test data");
			return;
		}
		
		IDiffractionMetadata meta = getDiffractionMetadata();
//		NonPixelSplittingIntegration npsi = new NonPixelSplittingIntegration(meta, 1592);
		PixelIntegrationBean bean = new PixelIntegrationBean();
		bean.setTo1D(true);
		bean.setUsePixelSplitting(false);
		
		//2theta
		bean.setxAxis(XAxis.ANGLE);
		IPixelIntegrationCache info = new PixelIntegrationCache(meta, bean);
		List<Dataset> out = PixelIntegration.integrate(data,null,info);
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
		Assert.assertEquals(300, out.get(0).getDouble(1591),0.0001);
		
	}
	
	@Test
	public void testNonPixelSplittingRange() {
		IDataset data = getData();
		if (data == null) {
			Assert.fail("Could not load test data");
			return;
		}
		
		IDiffractionMetadata meta = getDiffractionMetadata();
		NonPixelSplittingIntegration npsi = new NonPixelSplittingIntegration(meta);
		PixelIntegrationBean bean = new PixelIntegrationBean();
		bean.setTo1D(true);
		bean.setUsePixelSplitting(false);
		bean.setRadialRange(new double[]{1,5});
		bean.setNumberOfBinsRadial(801);
		IPixelIntegrationCache info = new PixelIntegrationCache(meta, bean);
		List<Dataset> out = PixelIntegration.integrate(data,null,info);
		
		bean = new PixelIntegrationBean();
		bean.setTo1D(true);
		bean.setUsePixelSplitting(false);
		bean.setRadialRange(new double[]{2,5});
		bean.setNumberOfBinsRadial(601);
		IPixelIntegrationCache info2 = new PixelIntegrationCache(meta, bean);
		List<Dataset> out2 = PixelIntegration.integrate(data,null,info2);
		
		IDataset second = out2.get(1).getSlice(new int[]{1}, new int[]{-1},null);
		IDataset first = out.get(1).getSlice(new int[]{201}, new int[]{-1},null);
		
		IDataset secondx = out2.get(0).getSlice(new int[]{1}, new int[]{-1},null);
		IDataset firstx = out.get(0).getSlice(new int[]{201}, new int[]{-1},null);
		
		double delta = Maths.abs(Maths.subtract(second, first)).max().doubleValue();
		double max = second.max().doubleValue();
		double val = delta/max;
		Assert.assertTrue(delta/max < 10E-4);

	}
	
	
	private double testWholeImageAzimuthal(IDataset data, IDataset mask, IPixelIntegrationCache info) {
		long before = System.currentTimeMillis();
		List<Dataset> out = PixelIntegration.integrate(data,mask,info);
		long after = System.currentTimeMillis();
		System.out.println("Non pixel splitting (full) in "+(after-before));
		
		if (out.size() != 2) {
			
			Assert.fail("Incorrect number of datasets returned");
		}
		double max = out.get(1).max().doubleValue();
		double min = out.get(1).min().doubleValue();
		double maxq = out.get(0).max().doubleValue();
		double minq = out.get(0).min().doubleValue();
		Assert.assertEquals(430662.6784073507, max,0.00001);
		Assert.assertEquals(132.55555555555554, min,0.00001);
		Assert.assertEquals(10.399522783579055, maxq,0.00001);
		Assert.assertEquals(0.004975613824126736, minq,0.00001);
		
		return after-before;
	}
	
	private double testSectionImageAzimuthal(IDataset data, IDataset mask, IPixelIntegrationCache info) {
		long before = System.currentTimeMillis();
		List<Dataset> out = PixelIntegration.integrate(data,mask,info);
		long after = System.currentTimeMillis();
		System.out.println("Non pixel splitting (section) in "+(after-before));
		
		if (out.size() != 2) {
			Assert.fail("Incorrect number of datasets returned");
		}
		double max = out.get(1).max().doubleValue();
		double min = out.get(1).min().doubleValue();
		double maxq = out.get(0).max().doubleValue();
		double minq = out.get(0).min().doubleValue();
		Assert.assertEquals(397629.0263157895, max,0.00001);
		Assert.assertEquals(0, min,0.00001);
		Assert.assertEquals(10.399522783579055, maxq,0.00001);
		Assert.assertEquals(0.004975613824126736, minq,0.00001);
		return after-before;
	}
	
	private double testSectionLimitedImageAzimuthal(IDataset data, IDataset mask, IPixelIntegrationCache info) {
		long before = System.currentTimeMillis();
		List<Dataset> out = PixelIntegration.integrate(data,mask,info);
		long after = System.currentTimeMillis();
		System.out.println("Non pixel splitting (section, limited q) in "+(after-before));
		
		if (out.size() != 2) {
			Assert.fail("Incorrect number of datasets returned");
		}
		double max = out.get(1).max().doubleValue();
		double min = out.get(1).min().doubleValue();
		double maxq = out.get(0).max().doubleValue();
		double minq = out.get(0).min().doubleValue();
		Assert.assertEquals(420544.142857142845, max,0.00001);
		Assert.assertEquals(824.7391304347826, min,0.00001);
		Assert.assertEquals(5.0, maxq,0.00001);
		Assert.assertEquals(1.0, minq,0.00001);
		return after-before;
	}
	
	private double testWholeImageRadial(IDataset data, IDataset mask, IPixelIntegrationCache info) {
		long before = System.currentTimeMillis();
		List<Dataset> out = PixelIntegration.integrate(data,mask,info);
		long after = System.currentTimeMillis();
		System.out.println("Non pixel splitting (radial full) in "+(after-before));
		
		if (out.size() != 2) {
			
			Assert.fail("Incorrect number of datasets returned");
		}
		double max = out.get(1).max().doubleValue();
		double min = out.get(1).min().doubleValue();
		double maxq = out.get(0).max().doubleValue();
		double minq = out.get(0).min().doubleValue();
		Assert.assertEquals(4205.173402868318, max,0.00001);
		Assert.assertEquals(1936.9358498349834, min,0.00001);
		Assert.assertEquals(179.84457062163236, maxq,0.00001);
		Assert.assertEquals(-179.88114786702272, minq,0.00001);
		
		return after-before;
	}
	
	private double testLimitedRadial(IDataset data, IDataset mask, IPixelIntegrationCache info) {
		long before = System.currentTimeMillis();
		List<Dataset> out = PixelIntegration.integrate(data,mask,info);
		long after = System.currentTimeMillis();
		System.out.println("Non pixel splitting (radial limited) in "+(after-before));
		
		if (out.size() != 2) {
			
			Assert.fail("Incorrect number of datasets returned");
		}
		double max = out.get(1).max().doubleValue();
		double min = out.get(1).min().doubleValue();
		double maxq = out.get(0).max().doubleValue();
		double minq = out.get(0).min().doubleValue();
		Assert.assertEquals(5661.3592592592595, max,0.00001);
		Assert.assertEquals(3696.1310259579727, min,0.00001);
		Assert.assertEquals(179.84457062163236, maxq,0.00001);
		Assert.assertEquals(-179.88114786702272, minq,0.00001);
		
		return after-before;
	}
	
	private double testSectorLimitedRadial(IDataset data, IDataset mask, IPixelIntegrationCache info) {
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
		Assert.assertEquals(37002.0, max,0.00001);
		Assert.assertEquals(0, min,0.00001);
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
		Assert.assertEquals(421477.4012345679, max,0.00001);
		Assert.assertEquals(0, min,0.00001);
		Assert.assertEquals(10.399522783579055, maxq,0.00001);
		Assert.assertEquals(0.004975613824126736, minq,0.00001);
		
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
		Assert.assertEquals(343570.1180154143, max,0.00001);
		Assert.assertEquals(146.45, min,0.00001);
		Assert.assertEquals(10.397588914338172, maxq,0.00001);
		Assert.assertEquals(0.006909483065011375, minq,0.00001);
		
		return after-before;
	}
	
}

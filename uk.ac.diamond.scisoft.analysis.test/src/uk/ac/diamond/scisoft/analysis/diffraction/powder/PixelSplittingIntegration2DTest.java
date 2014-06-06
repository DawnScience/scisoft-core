/*-
 * Copyright 2014 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.diamond.scisoft.analysis.diffraction.powder;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.io.IDiffractionMetadata;
import uk.ac.diamond.scisoft.analysis.roi.ROIProfile.XAxis;

public class PixelSplittingIntegration2DTest extends AbstractPixelIntegrationTestBase {

	@Test
	public void testPixelSplitting2DAzimuthal() {
		
		IDataset data = getData();
		if (data == null) {
			Assert.fail("Could not load test data");
			return;
		}
		
		IDiffractionMetadata meta = getDiffractionMetadata();
		PixelSplittingIntegration2D npsi = new PixelSplittingIntegration2D(meta,1592,1592);
		
		//first pass
		double firstTime = testWholeImage(data,npsi);
		//second pass
		double secondTime = testWholeImage(data,npsi);
		
		if (firstTime < secondTime) {
			Assert.fail("Whole image: second run should be faster due to caching, something odd is afoot");
		}
		
		npsi.setAzimuthalRange(new double[]{-180,-170});
		//first pass
		firstTime =testSectionImageAzimuthal(data,npsi);
		//first pass
		secondTime =testSectionImageAzimuthal(data,npsi);
		
		//similar time
		
		npsi.setRadialRange(new double[]{1,5});
		//first pass
		firstTime =testSectionLimitedImageAzimuthal(data,npsi);
		//second pass
		secondTime =testSectionLimitedImageAzimuthal(data,npsi);
		
		//probably should take a similar time
		//Test different size axes
		npsi = new PixelSplittingIntegration2D(meta,1592,360);
		//first pass
		firstTime =testDifferentSizeAxis(data,npsi);
		//first pass
		secondTime =testDifferentSizeAxis(data,npsi);
		
		if (firstTime < secondTime) {
			Assert.fail("Whole image: second run should be faster due to caching, something odd is afoot");
		}

	}
	
	@Test
	public void testPixelSplittingBinSetting() {
		IDataset data = getData();
		if (data == null) {
			Assert.fail("Could not load test data");
			return;
		}
		
		IDiffractionMetadata meta = getDiffractionMetadata();
		PixelSplittingIntegration2D npsi = new PixelSplittingIntegration2D(meta);
		
		testWholeImage(data,npsi);
		npsi.setNumberOfBins(1000);
		npsi.setNumberOfAzimuthalBins(500);
		testWholeImage1000Bins(data,npsi);
	}
	
	@Test
	public void testNonPixelSplittingAxis() {
		
		IDataset data = getData();
		if (data == null) {
			Assert.fail("Could not load test data");
			return;
		}
		
		IDiffractionMetadata meta = getDiffractionMetadata();
		PixelSplittingIntegration2D npsi = new PixelSplittingIntegration2D(meta, 1592,1592);
		//2theta
		npsi.setAxisType(XAxis.ANGLE);
		List<AbstractDataset> out = npsi.integrate(data);
		npsi.setRadialRange(new double[]{10,30});
		out = npsi.integrate(data);
		Assert.assertEquals(30, out.get(0).getDouble(1591),0.00001);
		npsi.setRadialRange(null);
		
		//q
		npsi.setAxisType(XAxis.Q);
		out = npsi.integrate(data);
		Assert.assertEquals(10.40087854, out.get(0).getDouble(1591),0.00001);
		npsi.setRadialRange(new double[]{1,5});
		out = npsi.integrate(data);
		Assert.assertEquals(5, out.get(0).getDouble(1591),0.00001);
		npsi.setRadialRange(null);
		
		//d-spacing
		npsi.setAxisType(XAxis.RESOLUTION);
		out = npsi.integrate(data);
		Assert.assertEquals(0.6040915776473221, out.get(0).getDouble(1591),0.00001);
		npsi.setRadialRange(new double[]{0.6,4});
		out = npsi.integrate(data);
		Assert.assertEquals(0.6, out.get(0).getDouble(1591),0.00001);
		npsi.setRadialRange(null);
		//pixel
		npsi.setAxisType(XAxis.PIXEL);
		out = npsi.integrate(data);
		Assert.assertEquals(1591.823246, out.get(0).getDouble(1591),0.00001);
		npsi.setRadialRange(new double[]{100,300});
		out = npsi.integrate(data);
		Assert.assertEquals(300, out.get(0).getDouble(1591),0.00001);
		
	}
	
	private double testWholeImage(IDataset data, AbstractPixelIntegration integrator) {
		long before = System.currentTimeMillis();
		List<AbstractDataset> out = integrator.integrate(data);
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
		
		Assert.assertEquals(662878.25, max,0.00001);
		Assert.assertEquals(-21545.316406, min,0.00001);
		Assert.assertEquals(10.4008785, maxq,0.00001);
		Assert.assertEquals(0.0072926517, minq,0.00001);
		Assert.assertEquals(179.8869364, maxa,0.00001);
		Assert.assertEquals(-179.88113943, mina,0.00001);
		
		return after-before;
	}
	
	private double testSectionImageAzimuthal(IDataset data, AbstractPixelIntegration integrator) {
		long before = System.currentTimeMillis();
		List<AbstractDataset> out = integrator.integrate(data);
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
		
		Assert.assertEquals(444023.90625, max,0.00001);
		Assert.assertEquals(-8611.455078125, min,0.00001);
		Assert.assertEquals(10.400878542457326, maxq,0.00001);
		Assert.assertEquals(0.007292651712160095, minq,0.00001);
		Assert.assertEquals(-170, maxa,0.00001);
		Assert.assertEquals(-180, mina,0.00001);
		
		return after-before;
	}
	
	private double testSectionLimitedImageAzimuthal(IDataset data, AbstractPixelIntegration integrator) {
		long before = System.currentTimeMillis();
		List<AbstractDataset> out = integrator.integrate(data);
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
		Assert.assertEquals(-406.0, min,0.00001);
		Assert.assertEquals(5, maxq,0.00001);
		Assert.assertEquals(1, minq,0.00001);
		Assert.assertEquals(-170, maxa,0.00001);
		Assert.assertEquals(-180, mina,0.00001);
		return after-before;
	}
	
	private double testDifferentSizeAxis(IDataset data, AbstractPixelIntegration integrator) {
		long before = System.currentTimeMillis();
		List<AbstractDataset> out = integrator.integrate(data);
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
		
		Assert.assertEquals(580632.375, max,0.00001);
		Assert.assertEquals(-5684.68701171875, min,0.00001);
		Assert.assertEquals(10.400878542457326, maxq,0.00001);
		Assert.assertEquals(0.007292651712160095, minq,0.00001);
		Assert.assertEquals(179.50000805147747, maxa,0.00001);
		Assert.assertEquals(-179.49421098773428, mina,0.00001);
		return after-before;
	}
	
	private double testWholeImage1000Bins(IDataset data, AbstractPixelIntegration integrator) {
		long before = System.currentTimeMillis();
		List<AbstractDataset> out = integrator.integrate(data);
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
		Assert.assertEquals(363108.90625, max,0.00001);
		Assert.assertEquals(-2469.310791015625, min,0.00001);
		Assert.assertEquals(10.398944852059048, maxq,0.00001);
		Assert.assertEquals(0.009226342110438265, minq,0.00001);
		
		return after-before;
	}
	
}

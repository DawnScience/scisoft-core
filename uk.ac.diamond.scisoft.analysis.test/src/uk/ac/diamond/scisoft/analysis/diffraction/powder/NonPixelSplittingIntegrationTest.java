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
import uk.ac.diamond.scisoft.analysis.dataset.BooleanDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.io.IDiffractionMetadata;
import uk.ac.diamond.scisoft.analysis.roi.ROIProfile.XAxis;

public class NonPixelSplittingIntegrationTest extends AbstractPixelIntegrationTest {
	
	@Test
	public void testNonPixelSplittingAzimuthal() {
		
		IDataset data = getData();
		if (data == null) {
			Assert.fail("Could not load test data");
			return;
		}
		
		IDiffractionMetadata meta = getDiffractionMetadata();
		NonPixelSplittingIntegration npsi = new NonPixelSplittingIntegration(meta, 1592);
		
		//first pass
		double firstTime = testWholeImageAzimuthal(data,npsi);
		//second pass
		double secondTime = testWholeImageAzimuthal(data,npsi);
		
		if (firstTime < secondTime) {
			Assert.fail("Whole image: second run should be faster due to caching, something odd is afoot");
		}
		
		npsi.setAzimuthalRange(new double[]{-180,-170});
		//first pass
		firstTime =testSectionImageAzimuthal(data,npsi);
		//first pass
		secondTime =testSectionImageAzimuthal(data,npsi);
		
		if (firstTime < secondTime) {
			Assert.fail("Sector: Second run should be faster due to caching, something odd is afoot");
		}
		
		npsi.setRadialRange(new double[]{1,5});
		//first pass
		firstTime =testSectionLimitedImageAzimuthal(data,npsi);
		//second pass
		secondTime =testSectionLimitedImageAzimuthal(data,npsi);
		
		//probably should take a similar time
		
		npsi.setRadialRange(null);
		npsi.setAzimuthalRange(null);
		AbstractDataset mask = getMask(data.getShape());
		npsi.setMask(mask);
		
		firstTime =testMask(data,npsi);
		secondTime =testMask(data,npsi);
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
		npsi.setAzimuthalIntegration(false);
		
		//first pass
		double firstTime = testWholeImageRadial(data,npsi);
		//second pass
		double secondTime = testWholeImageRadial(data,npsi);
		
		if (firstTime < secondTime) {
			Assert.fail("Whole image: second run should be faster due to caching, something odd is afoot");
		}
		
		npsi.setRadialRange(new double[]{1,5});
		//first pass
		firstTime =testLimitedRadial(data,npsi);
		//first pass
		secondTime =testLimitedRadial(data,npsi);
		
		if (firstTime < secondTime) {
			Assert.fail("Sector: Second run should be faster due to caching, something odd is afoot");
		}
		
		npsi.setAzimuthalRange(new double[]{-180,-170});
		//first pass
		firstTime =testSectorLimitedRadial(data,npsi);
		//second pass
		secondTime =testSectorLimitedRadial(data,npsi);
		
		//probably should take a similar time
	}
	
	@Test
	public void testNonPixelSplittingAxis() {
		
		IDataset data = getData();
		if (data == null) {
			Assert.fail("Could not load test data");
			return;
		}
		
		IDiffractionMetadata meta = getDiffractionMetadata();
		NonPixelSplittingIntegration npsi = new NonPixelSplittingIntegration(meta, 1592);
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
		Assert.assertEquals(10.39797863552991, out.get(0).getDouble(1591),0.00001);
		npsi.setRadialRange(new double[]{1,5});
		out = npsi.integrate(data);
		Assert.assertEquals(5, out.get(0).getDouble(1591),0.00001);
		npsi.setRadialRange(null);
		
		//d-spacing
		npsi.setAxisType(XAxis.RESOLUTION);
		out = npsi.integrate(data);
		Assert.assertEquals(0.604269880465991, out.get(0).getDouble(1591),0.00001);
		npsi.setRadialRange(new double[]{0.6,4});
		out = npsi.integrate(data);
		Assert.assertEquals(0.6, out.get(0).getDouble(1591),0.00001);
		npsi.setRadialRange(null);
		//pixel
		npsi.setAxisType(XAxis.PIXEL);
		out = npsi.integrate(data);
		Assert.assertEquals(1591.1188954992163, out.get(0).getDouble(1591),0.00001);
		npsi.setRadialRange(new double[]{100,300});
		out = npsi.integrate(data);
		Assert.assertEquals(300, out.get(0).getDouble(1591),0.00001);
		
	}
	
	private double testWholeImageAzimuthal(IDataset data, AbstractPixelIntegration integrator) {
		long before = System.currentTimeMillis();
		List<AbstractDataset> out = integrator.integrate(data);
		long after = System.currentTimeMillis();
		System.out.println("Non pixel splitting (full) in "+(after-before));
		
		if (out.size() != 2) {
			
			Assert.fail("Incorrect number of datasets returned");
		}
		double max = out.get(1).max().doubleValue();
		double min = out.get(1).min().doubleValue();
		double maxq = out.get(0).max().doubleValue();
		double minq = out.get(0).min().doubleValue();
		Assert.assertEquals(431741.6495398773, max,0.00001);
		Assert.assertEquals(132.55555555555554, min,0.00001);
		Assert.assertEquals(10.39797863552991, maxq,0.00001);
		Assert.assertEquals(0.004903786580700596, minq,0.00001);
		
		return after-before;
	}
	
	private double testSectionImageAzimuthal(IDataset data, AbstractPixelIntegration integrator) {
		long before = System.currentTimeMillis();
		List<AbstractDataset> out = integrator.integrate(data);
		long after = System.currentTimeMillis();
		System.out.println("Non pixel splitting (section) in "+(after-before));
		
		if (out.size() != 2) {
			Assert.fail("Incorrect number of datasets returned");
		}
		double max = out.get(1).max().doubleValue();
		double min = out.get(1).min().doubleValue();
		double maxq = out.get(0).max().doubleValue();
		double minq = out.get(0).min().doubleValue();
		Assert.assertEquals(402551.9459459459, max,0.00001);
		Assert.assertEquals(0, min,0.00001);
		Assert.assertEquals(10.39797863552991, maxq,0.00001);
		Assert.assertEquals(0.004903786580700596, minq,0.00001);
		return after-before;
	}
	
	private double testSectionLimitedImageAzimuthal(IDataset data, AbstractPixelIntegration integrator) {
		long before = System.currentTimeMillis();
		List<AbstractDataset> out = integrator.integrate(data);
		long after = System.currentTimeMillis();
		System.out.println("Non pixel splitting (section, limited q) in "+(after-before));
		
		if (out.size() != 2) {
			Assert.fail("Incorrect number of datasets returned");
		}
		double max = out.get(1).max().doubleValue();
		double min = out.get(1).min().doubleValue();
		double maxq = out.get(0).max().doubleValue();
		double minq = out.get(0).min().doubleValue();
		Assert.assertEquals(405072.0, max,0.00001);
		Assert.assertEquals(824.5, min,0.00001);
		Assert.assertEquals(5, maxq,0.00001);
		Assert.assertEquals(1, minq,0.00001);
		return after-before;
	}
	
	private double testWholeImageRadial(IDataset data, AbstractPixelIntegration integrator) {
		long before = System.currentTimeMillis();
		List<AbstractDataset> out = integrator.integrate(data);
		long after = System.currentTimeMillis();
		System.out.println("Non pixel splitting (radial full) in "+(after-before));
		
		if (out.size() != 2) {
			
			Assert.fail("Incorrect number of datasets returned");
		}
		double max = out.get(1).max().doubleValue();
		double min = out.get(1).min().doubleValue();
		double maxq = out.get(0).max().doubleValue();
		double minq = out.get(0).min().doubleValue();
		Assert.assertEquals(4299.590659340659, max,0.00001);
		Assert.assertEquals(1937.2238955823293, min,0.00001);
		Assert.assertEquals(179.84418431665182, maxq,0.00001);
		Assert.assertEquals(-179.88153396074563, minq,0.00001);
		
		return after-before;
	}
	
	private double testLimitedRadial(IDataset data, AbstractPixelIntegration integrator) {
		long before = System.currentTimeMillis();
		List<AbstractDataset> out = integrator.integrate(data);
		long after = System.currentTimeMillis();
		System.out.println("Non pixel splitting (radial limited) in "+(after-before));
		
		if (out.size() != 2) {
			
			Assert.fail("Incorrect number of datasets returned");
		}
		double max = out.get(1).max().doubleValue();
		double min = out.get(1).min().doubleValue();
		double maxq = out.get(0).max().doubleValue();
		double minq = out.get(0).min().doubleValue();
		Assert.assertEquals(5665.809641532756, max,0.00001);
		Assert.assertEquals(3693.9962962962964, min,0.00001);
		Assert.assertEquals(179.84418431665182, maxq,0.00001);
		Assert.assertEquals(-179.88153396074563, minq,0.00001);
		
		return after-before;
	}
	
	private double testSectorLimitedRadial(IDataset data, AbstractPixelIntegration integrator) {
		long before = System.currentTimeMillis();
		List<AbstractDataset> out = integrator.integrate(data);
		long after = System.currentTimeMillis();
		System.out.println("Non pixel splitting (radial limited sector) in "+(after-before));
		
		if (out.size() != 2) {
			
			Assert.fail("Incorrect number of datasets returned");
		}
		double max = out.get(1).max().doubleValue();
		double min = out.get(1).min().doubleValue();
		double maxq = out.get(0).max().doubleValue();
		double minq = out.get(0).min().doubleValue();
		Assert.assertEquals(38043.04761904762, max,0.00001);
		Assert.assertEquals(0, min,0.00001);
		Assert.assertEquals(-170, maxq,0.00001);
		Assert.assertEquals(-180, minq,0.00001);
		
		return after-before;
	}
	
	private double testMask(IDataset data, AbstractPixelIntegration integrator) {
		long before = System.currentTimeMillis();
		List<AbstractDataset> out = integrator.integrate(data);
		long after = System.currentTimeMillis();
		System.out.println("Non pixel splitting (radial limited sector) in "+(after-before));
		
		if (out.size() != 2) {
			
			Assert.fail("Incorrect number of datasets returned");
		}
		double max = out.get(1).max().doubleValue();
		double min = out.get(1).min().doubleValue();
		double maxq = out.get(0).max().doubleValue();
		double minq = out.get(0).min().doubleValue();
		Assert.assertEquals(441337.6015625, max,0.00001);
		Assert.assertEquals(367.64285714285717, min,0.00001);
		Assert.assertEquals(9.448855451971152, maxq,0.00001);
		Assert.assertEquals(1.5283458922106619, minq,0.00001);
		
		return after-before;
	}

}

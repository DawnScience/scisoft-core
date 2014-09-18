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

import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.metadata.IDiffractionMetadata;
import uk.ac.diamond.scisoft.analysis.roi.ROIProfile.XAxis;

public class NonPixelSplittingIntegrationTest extends AbstractPixelIntegrationTestBase {
	
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
		Dataset mask = getMask(data.getShape());
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
	public void testNonPixelSplittingBinSetting() {
		IDataset data = getData();
		if (data == null) {
			Assert.fail("Could not load test data");
			return;
		}
		
		IDiffractionMetadata meta = getDiffractionMetadata();
		NonPixelSplittingIntegration npsi = new NonPixelSplittingIntegration(meta);
		
		testWholeImageAzimuthal(data,npsi);
		npsi.setNumberOfBins(1000);
		testWholeImageAzimuthal1000Bins(data,npsi);
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
		List<Dataset> out = npsi.integrate(data);
		npsi.setRadialRange(new double[]{10,30});
		out = npsi.integrate(data);
		Assert.assertEquals(30, out.get(0).getDouble(1591),0.00001);
		npsi.setRadialRange(null);
		
		//q
		npsi.setAxisType(XAxis.Q);
		out = npsi.integrate(data);
		Assert.assertEquals(10.399522783579055, out.get(0).getDouble(1591),0.00001);
		npsi.setRadialRange(new double[]{1,5});
		out = npsi.integrate(data);
		Assert.assertEquals(5, out.get(0).getDouble(1591),0.00001);
		npsi.setRadialRange(null);
		
		//d-spacing
		npsi.setAxisType(XAxis.RESOLUTION);
		out = npsi.integrate(data);
		Assert.assertEquals(0.6041801569107378, out.get(0).getDouble(1591),0.00001);
		npsi.setRadialRange(new double[]{0.6,4});
		out = npsi.integrate(data);
		Assert.assertEquals(0.6, out.get(0).getDouble(1591),0.00001);
		npsi.setRadialRange(null);
		//pixel
		npsi.setAxisType(XAxis.PIXEL);
		out = npsi.integrate(data);
		Assert.assertEquals(1591.1174742301246, out.get(0).getDouble(1591),0.00001);
		npsi.setRadialRange(new double[]{100,300});
		out = npsi.integrate(data);
		Assert.assertEquals(300, out.get(0).getDouble(1591),0.00001);
		
	}
	
	private double testWholeImageAzimuthal(IDataset data, AbstractPixelIntegration integrator) {
		long before = System.currentTimeMillis();
		List<Dataset> out = integrator.integrate(data);
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
	
	private double testSectionImageAzimuthal(IDataset data, AbstractPixelIntegration integrator) {
		long before = System.currentTimeMillis();
		List<Dataset> out = integrator.integrate(data);
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
	
	private double testSectionLimitedImageAzimuthal(IDataset data, AbstractPixelIntegration integrator) {
		long before = System.currentTimeMillis();
		List<Dataset> out = integrator.integrate(data);
		long after = System.currentTimeMillis();
		System.out.println("Non pixel splitting (section, limited q) in "+(after-before));
		
		if (out.size() != 2) {
			Assert.fail("Incorrect number of datasets returned");
		}
		double max = out.get(1).max().doubleValue();
		double min = out.get(1).min().doubleValue();
		double maxq = out.get(0).max().doubleValue();
		double minq = out.get(0).min().doubleValue();
		Assert.assertEquals(420544.14285714284, max,0.00001);
		Assert.assertEquals(824.7391304347826, min,0.00001);
		Assert.assertEquals(5.0, maxq,0.00001);
		Assert.assertEquals(1.0, minq,0.00001);
		return after-before;
	}
	
	private double testWholeImageRadial(IDataset data, AbstractPixelIntegration integrator) {
		long before = System.currentTimeMillis();
		List<Dataset> out = integrator.integrate(data);
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
	
	private double testLimitedRadial(IDataset data, AbstractPixelIntegration integrator) {
		long before = System.currentTimeMillis();
		List<Dataset> out = integrator.integrate(data);
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
	
	private double testSectorLimitedRadial(IDataset data, AbstractPixelIntegration integrator) {
		long before = System.currentTimeMillis();
		List<Dataset> out = integrator.integrate(data);
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
	
	private double testMask(IDataset data, AbstractPixelIntegration integrator) {
		long before = System.currentTimeMillis();
		List<Dataset> out = integrator.integrate(data);
		long after = System.currentTimeMillis();
		System.out.println("Non pixel splitting (radial limited sector) in "+(after-before));
		
		if (out.size() != 2) {
			
			Assert.fail("Incorrect number of datasets returned");
		}
		double max = out.get(1).max().doubleValue();
		double min = out.get(1).min().doubleValue();
		double maxq = out.get(0).max().doubleValue();
		double minq = out.get(0).min().doubleValue();
		Assert.assertEquals(442581.4365079365, max,0.00001);
		Assert.assertEquals(367.64285714285717, min,0.00001);
		Assert.assertEquals(9.45008809491463, maxq,0.00001);
		Assert.assertEquals(1.5287093029405394, minq,0.00001);
		
		return after-before;
	}
	
	private double testWholeImageAzimuthal1000Bins(IDataset data, AbstractPixelIntegration integrator) {
		long before = System.currentTimeMillis();
		List<Dataset> out = integrator.integrate(data);
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

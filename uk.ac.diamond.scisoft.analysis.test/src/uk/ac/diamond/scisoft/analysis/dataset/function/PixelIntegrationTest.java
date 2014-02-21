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

package uk.ac.diamond.scisoft.analysis.dataset.function;

import java.util.List;

import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;

import junit.framework.Assert;

import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.BooleanDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IndexIterator;
import uk.ac.diamond.scisoft.analysis.diffraction.DetectorProperties;
import uk.ac.diamond.scisoft.analysis.diffraction.DiffractionCrystalEnvironment;
import uk.ac.diamond.scisoft.analysis.diffraction.QSpace;
import uk.ac.diamond.scisoft.analysis.io.DataHolder;
import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.roi.ROIProfile.XAxis;
import uk.ac.diamond.scisoft.analysis.roi.SectorROI;

/**
 * TODO At some point, increase the test data used to benchmark the algorithm.
 */
public class PixelIntegrationTest {

	//FIXME Should test against values from other popular data reduction programs
	final static String testFileFolder = "testfiles/gda/analysis/io/Fit2dLoaderTest/";
	
	@Test
	public void testNonPixelSplitting() {
		
		AbstractDataset data = getData();
		if (data == null) {
			Assert.fail("Could not load test data");
			return;
		}
		
		QSpace qSpace = getQSpace();

		NonPixelSplittingIntegration npsi = new NonPixelSplittingIntegration(qSpace, 1592);
		
		long before = System.currentTimeMillis();
		List<AbstractDataset> out = npsi.value(data);
		long after = System.currentTimeMillis();
		System.out.println("Non pixel splitting (basic test) in "+(after-before));
		
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
		//Second pass should be faster
		before = System.currentTimeMillis();
		out = npsi.value(data);
		after = System.currentTimeMillis();
		System.out.println("Non pixel splitting repeat (basic test) in "+(after-before));
		
		max = out.get(1).max().doubleValue();
		min = out.get(1).min().doubleValue();
		maxq = out.get(0).max().doubleValue();
		minq = out.get(0).min().doubleValue();
		
		Assert.assertEquals(431741.6495398773, max,0.00001);
		Assert.assertEquals(132.55555555555554, min,0.00001);
		Assert.assertEquals(10.39797863552991, maxq,0.00001);
		Assert.assertEquals(0.004903786580700596, minq,0.00001);
		
	}
	
	@Test
	public void testPixelSplitting() {
		
		AbstractDataset data = getData();
		if (data == null) {
			Assert.fail("Could not load test data");
			return;
		}
		
		QSpace qSpace = getQSpace();

		PixelSplittingIntegration npsi = new PixelSplittingIntegration(qSpace, 1592);
		
		long before = System.currentTimeMillis();
		List<AbstractDataset> out = npsi.value(data);
		long after = System.currentTimeMillis();
		System.out.println("Pixel splitting (basic test) in "+(after-before));
		
		if (out.size() != 2) {
			Assert.fail("Incorrect number of datasets returned");
		}
		
		double max = out.get(1).max().doubleValue();
		double min = out.get(1).min().doubleValue();
		double maxq = out.get(0).max().doubleValue();
		double minq = out.get(0).min().doubleValue();
		
		Assert.assertEquals(353589.4987476404, max,0.00001);
		Assert.assertEquals(136.5790195926834, min,0.00001);
		Assert.assertEquals(10.401047688249356, maxq,0.00001);
		Assert.assertEquals(0.007368865272782814, minq,0.00001);
		
		//Second pass should be faster
		before = System.currentTimeMillis();
		out = npsi.value(data);
		after = System.currentTimeMillis();
		System.out.println("Pixel splitting repeat (basic test) in "+(after-before));
		
		max = out.get(1).max().doubleValue();
		min = out.get(1).min().doubleValue();
		maxq = out.get(0).max().doubleValue();
		minq = out.get(0).min().doubleValue();
		
		Assert.assertEquals(353589.4987476404, max,0.00001);
		Assert.assertEquals(136.5790195926834, min,0.00001);
		Assert.assertEquals(10.401047688249356, maxq,0.00001);
		Assert.assertEquals(0.007368865272782814, minq,0.00001);
	}
	
	@Test
	public void testPixelSplitting2D() {
		
		AbstractDataset data = getData();
		if (data == null) {
			Assert.fail("Could not load test data");
			return;
		}
		
		QSpace qSpace = getQSpace();

		PixelSplittingIntegration2D npsi = new PixelSplittingIntegration2D(qSpace, 1592, 1592);
		//npsi.setAxisType(XAxis.ANGLE);
		long before = System.currentTimeMillis();
		List<AbstractDataset> out = npsi.value(data);
		long after = System.currentTimeMillis();
		System.out.println("2D Pixel splitting (basic test) in "+(after-before));
		
		if (out.size() != 3) {
			Assert.fail("Incorrect number of datasets returned");
		}
		
		double maxq = out.get(0).max().doubleValue();
		double minq = out.get(0).min().doubleValue();
		
		double maxchi = out.get(2).max().doubleValue();
		double minchi = out.get(2).min().doubleValue();
		
		double maxi = out.get(1).max().doubleValue();
		double mini = out.get(1).min().doubleValue();
		
		Assert.assertEquals(10.401047688249356, maxq,0.00001);
		Assert.assertEquals(0.007368865272782814, minq,0.00001);
		
		Assert.assertEquals(662877.7117513434, maxi,0.00001);
		//-24427.040167283067
		//Assert.assertEquals(-26518.610477737853, mini,0.00001);
		
		before = System.currentTimeMillis();
		out = npsi.value(data);
		after = System.currentTimeMillis();
		System.out.println("2D Pixel splitting (repeat test) in "+(after-before));
		
		if (out.size() != 3) {
			Assert.fail("Incorrect number of datasets returned");
		}
		
		maxq = out.get(0).max().doubleValue();
		minq = out.get(0).min().doubleValue();
		
		maxi = out.get(1).max().doubleValue();
		mini = out.get(1).min().doubleValue();
		
		Assert.assertEquals(10.401047688249356, maxq,0.00001);
		Assert.assertEquals(0.007368865272782814, minq,0.00001);
		
		Assert.assertEquals(662877.7117513434, maxi,0.00001);
		//Assert.assertEquals(-26518.610477737853, mini,0.00001);
		

	}
	
	
	@Test
	public void testNonPixelSplittingMask() {
		
		AbstractDataset data = getData();
		if (data == null) {
			Assert.fail("Could not load test data");
			return;
		}
		
		QSpace qSpace = getQSpace();
		BooleanDataset mask = new BooleanDataset(data.getShape());
		
		for (int i = 100; i < 1000; i++) 
			for (int j = 100; j < 1000; j++) 
			mask.set(true, new int[]{j,i});
		
		NonPixelSplittingIntegration npsi = new NonPixelSplittingIntegration(qSpace, 1592);
		npsi.setMask(mask);
		
		long before = System.currentTimeMillis();
		List<AbstractDataset> out = npsi.value(data);
		long after = System.currentTimeMillis();
		System.out.println("Non pixel splitting mask (basic test) in "+(after-before));
		
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
		//Second pass should be faster
		before = System.currentTimeMillis();
		out = npsi.value(data);
		after = System.currentTimeMillis();
		System.out.println("Non pixel splitting repeat mask (basic test) in "+(after-before));
		
		max = out.get(1).max().doubleValue();
		min = out.get(1).min().doubleValue();
		maxq = out.get(0).max().doubleValue();
		minq = out.get(0).min().doubleValue();
		
		Assert.assertEquals(441337.6015625, max,0.00001);
		Assert.assertEquals(367.64285714285717, min,0.00001);
		Assert.assertEquals(9.448855451971152, maxq,0.00001);
		Assert.assertEquals(1.5283458922106619, minq,0.00001);
		
	}
	
	@Test
	public void testPixelSplittingMask() {
		
		AbstractDataset data = getData();
		if (data == null) {
			Assert.fail("Could not load test data");
			return;
		}
		
		QSpace qSpace = getQSpace();
		BooleanDataset mask = new BooleanDataset(data.getShape());
		
		for (int i = 100; i < 1000; i++) 
			for (int j = 100; j < 1000; j++) 
			mask.set(true, new int[]{j,i});
		
		PixelSplittingIntegration npsi = new PixelSplittingIntegration(qSpace, 1592);
		npsi.setMask(mask);
		
		long before = System.currentTimeMillis();
		List<AbstractDataset> out = npsi.value(data);
		long after = System.currentTimeMillis();
		System.out.println("pixel splitting mask (basic test) in "+(after-before));
	
		if (out.size() != 2) {
			Assert.fail("Incorrect number of datasets returned");
		}
		
		double max = out.get(1).max().doubleValue();
		double min = out.get(1).min().doubleValue();
		double maxq = out.get(0).max().doubleValue();
		double minq = out.get(0).min().doubleValue();
		
		Assert.assertEquals(375037.91325155017, max,0.00001);
		Assert.assertEquals(379.2469979461525, min,0.00001);
		Assert.assertEquals(9.45220790215385, maxq,0.00001);
		Assert.assertEquals(1.5323281860305737, minq,0.00001);
		//Second pass should be faster
		before = System.currentTimeMillis();
		out = npsi.value(data);
		after = System.currentTimeMillis();
		System.out.println("pixel splitting repeat mask (basic test) in "+(after-before));
		
		max = out.get(1).max().doubleValue();
		min = out.get(1).min().doubleValue();
		maxq = out.get(0).max().doubleValue();
		minq = out.get(0).min().doubleValue();
		
		Assert.assertEquals(375037.91325155017, max,0.00001);
		Assert.assertEquals(379.2469979461525, min,0.00001);
		Assert.assertEquals(9.45220790215385, maxq,0.00001);
		Assert.assertEquals(1.5323281860305737, minq,0.00001);
		
	}
	
	@Test
	public void testNonPixelSplittingROI() {
		
		AbstractDataset data = getData();
		if (data == null) {
			Assert.fail("Could not load test data");
			return;
		}
		
		QSpace qSpace = getQSpace();

		NonPixelSplittingIntegration npsi = new NonPixelSplittingIntegration(qSpace, 1592);
		npsi.setROI(new SectorROI(1000, 1000, 0, 1000, 0, 1));
		
		long before = System.currentTimeMillis();
		List<AbstractDataset> out = npsi.value(data);
		long after = System.currentTimeMillis();
		System.out.println("Non pixel splitting roi (basic test) in "+(after-before));
		
		if (out.size() != 2) {
			Assert.fail("Incorrect number of datasets returned");
		}
		
		double max = out.get(1).max().doubleValue();
		double min = out.get(1).min().doubleValue();
		double maxq = out.get(0).max().doubleValue();
		double minq = out.get(0).min().doubleValue();
		
//		Assert.assertEquals(451736.7508896797, max,0.00001);
//		Assert.assertEquals(0.0, min,0.00001);
//		Assert.assertEquals(8.332296339597459, maxq,0.00001);
//		Assert.assertEquals(0.090283344944834, minq,0.00001);
		//Second pass should be faster
		before = System.currentTimeMillis();
		out = npsi.value(data);
		after = System.currentTimeMillis();
		System.out.println("Non pixel splitting roi repeat (basic test) in "+(after-before));
		
		max = out.get(1).max().doubleValue();
		min = out.get(1).min().doubleValue();
		maxq = out.get(0).max().doubleValue();
		minq = out.get(0).min().doubleValue();
		
//		Assert.assertEquals(451736.7508896797, max,0.00001);
//		Assert.assertEquals(0.0, min,0.00001);
//		Assert.assertEquals(8.332296339597459, maxq,0.00001);
//		Assert.assertEquals(0.090283344944834, minq,0.00001);
		
	}
	
	@Test
	public void testPixelSplittingROI() {
		
		AbstractDataset data = getData();
		if (data == null) {
			Assert.fail("Could not load test data");
			return;
		}
		
		QSpace qSpace = getQSpace();

		PixelSplittingIntegration npsi = new PixelSplittingIntegration(qSpace, 1592);
		npsi.setROI(new SectorROI(1000, 1000, 0, 1000, 0, 1));
		
		long before = System.currentTimeMillis();
		List<AbstractDataset> out = npsi.value(data);
		long after = System.currentTimeMillis();
		System.out.println("pixel splitting roi (basic test) in "+(after-before));
		
		if (out.size() != 2) {
			Assert.fail("Incorrect number of datasets returned");
		}
		
		double max = out.get(1).max().doubleValue();
		double min = out.get(1).min().doubleValue();
		double maxq = out.get(0).max().doubleValue();
		double minq = out.get(0).min().doubleValue();
		
//		Assert.assertEquals(451736.7508896797, max,0.00001);
//		Assert.assertEquals(0.0, min,0.00001);
//		Assert.assertEquals(8.332296339597459, maxq,0.00001);
//		Assert.assertEquals(0.090283344944834, minq,0.00001);
		//Second pass should be faster
		before = System.currentTimeMillis();
		out = npsi.value(data);
		after = System.currentTimeMillis();
		System.out.println("pixel splitting roi repeat (basic test) in "+(after-before));
		
		max = out.get(1).max().doubleValue();
		min = out.get(1).min().doubleValue();
		maxq = out.get(0).max().doubleValue();
		minq = out.get(0).min().doubleValue();
		
//		Assert.assertEquals(451736.7508896797, max,0.00001);
//		Assert.assertEquals(0.0, min,0.00001);
//		Assert.assertEquals(8.332296339597459, maxq,0.00001);
//		Assert.assertEquals(0.090283344944834, minq,0.00001);
		
	}
	
	
	private AbstractDataset getData() {
		final String path = testFileFolder+"/test1.f2d";
		AbstractDataset data = null;
		try {
			DataHolder dataHolder = LoaderFactory.getData(path, null);
			data = dataHolder.getDataset(0);
		} catch (Exception e) {
		}
 		
		return data;
	}
	
	private QSpace getQSpace() {
		Vector3d origin = new Vector3d(0, 0, 1);
		
		Matrix3d or = new Matrix3d(0.18890371330716021,
				0.9819916621345969, -0.0027861437324560243, -0.9818848715409118, 0.18892425862797949,
				0.014481834861492937, 0.014747411225481444, 0.0, 0.9998912510179028);
		
		Vector3d bv = new Vector3d(-149.3111967697318, 270.9243609214487, 368.7598186824519);
		
		DetectorProperties dp = new DetectorProperties(bv, origin, 2048, 2048, 0.2, 0.2, or);
		DiffractionCrystalEnvironment ce = new DiffractionCrystalEnvironment(0.42566937014852557);
		
		return new QSpace(dp, ce);
		

	}
	
}

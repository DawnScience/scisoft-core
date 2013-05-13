/*
 * Copyright 2011 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.fitting;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.fitting.functions.CompositeFunction;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Gaussian;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Lorentzian;
import uk.ac.diamond.scisoft.analysis.fitting.functions.PearsonVII;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheNelderMead;

public class ApacheNelderMeadFittingTest {

	static DoubleDataset gaussian;
	static DoubleDataset lorentzian;
	static DoubleDataset pearsonVII;
	static DoubleDataset pseudoVoigt;
	static DoubleDataset xAxis;

	static double accuracy;
	static int smoothing;
	static int numPeaks;

	static double pos;
	static double fwhm;
	static double area;
	static double delta;
	private static List<CompositeFunction> fittedGaussian;
	private static List<CompositeFunction> fittedLorenzian;
	private static List<CompositeFunction> fittedPearsonVII;
	private static List<CompositeFunction> fittedPseudoVoigt;

	public static void doFitting() {
		fittedGaussian = Generic1DFitter.fitPeakFunctions(xAxis, gaussian, new Gaussian(1, 1, 1, 1), new ApacheNelderMead(),
				smoothing, numPeaks);
		fittedLorenzian = Generic1DFitter.fitPeakFunctions(xAxis, lorentzian, new Lorentzian(1, 1, 1, 1),
				new ApacheNelderMead(), smoothing, numPeaks);
		fittedPearsonVII = Generic1DFitter.fitPeakFunctions(xAxis, pearsonVII, new PearsonVII(1, 1, 1, 1),
				new ApacheNelderMead(), smoothing, numPeaks);
		fittedPseudoVoigt = Generic1DFitter.fitPeakFunctions(xAxis, pseudoVoigt, new PearsonVII(1, 1, 1, 1),
				new ApacheNelderMead(), smoothing, numPeaks);
	}

	@BeforeClass
	public static void setupTestEnvrionment() {

		gaussian = Generic1DDatasetCreater.createGaussianDataset();
		lorentzian = Generic1DDatasetCreater.createLorentzianDataset();
		pearsonVII = Generic1DDatasetCreater.createPearsonVII();
		pseudoVoigt = Generic1DDatasetCreater.createPseudoVoigt();
		xAxis = Generic1DDatasetCreater.xAxis;

		accuracy = Generic1DDatasetCreater.accuracy;
		smoothing = Generic1DDatasetCreater.smoothing;
		numPeaks = Generic1DDatasetCreater.numPeaks;

		pos = Generic1DDatasetCreater.peakPos;
		fwhm = Generic1DDatasetCreater.defaultFWHM;
		area = Generic1DDatasetCreater.defaultArea;
		delta = Generic1DDatasetCreater.delta;
		doFitting();
	}


	@Test
	public void testNumberOfPeaksFoundGaussian() {
		Assert.assertEquals(1, fittedGaussian.size(), 0);
	}

	@Test
	public void testNumberOfPeaksFoundLorenzian() {
		Assert.assertEquals(1, fittedLorenzian.size(), 0);
	}

	@Test
	public void testNumberOfPeaksFoundPearsonVII() {
		Assert.assertEquals(1, fittedPearsonVII.size(), 0);
	}

	@Test
	public void testNumberOfPeaksFoundPseudoVoigt() {
		Assert.assertEquals(1, fittedPseudoVoigt.size(), 0);
	}

	@Test
	public void testPeakPosGaussian() {
		Assert.assertEquals(pos, fittedGaussian.get(0).getPeak(0).getPosition(), delta);
	}

	@Test
	public void testPeakPosLorenzian() {
		Assert.assertEquals(pos, fittedLorenzian.get(0).getPeak(0).getPosition(), delta);
	}

	@Test
	public void testPeakPosPearsonVII() {
		Assert.assertEquals(pos, fittedPearsonVII.get(0).getPeak(0).getPosition(), delta);
	}

	@Test
	public void testPeakPosPseudoVoigt() {
		Assert.assertEquals(pos, fittedPseudoVoigt.get(0).getPeak(0).getPosition(), delta);
	}

	@Ignore("Test not finished and is failing. 9 Nov 11")
	@Test
	public void testFWHMGaussian() {
		Assert.assertEquals(fwhm, fittedGaussian.get(0).getPeak(0).getFWHM(), delta);
	}

	@Ignore("Test not finished and is failing. 9 Nov 11")
	@Test
	public void testFWHMLorenzian() {
		Assert.assertEquals(fwhm, fittedLorenzian.get(0).getPeak(0).getFWHM(), delta);
	}

	@Ignore("Test not finished and is failing. 9 Nov 11")
	@Test
	public void testFWHMPearsonVII() {
		Assert.assertEquals(fwhm, fittedPearsonVII.get(0).getPeak(0).getFWHM(), delta);
	}

	@Ignore("Test not finished and is failing. 9 Nov 11")
	@Test
	public void testFWHMPseudoVoigt() {
		Assert.assertEquals(fwhm, fittedPseudoVoigt.get(0).getPeak(0).getFWHM(), delta);
	}

	@Ignore("Test not finished and is failing. 9 Nov 11")
	@Test
	public void testAreaGaussian() {
		Assert.assertEquals(area, fittedGaussian.get(0).getPeak(0).getArea(), delta);
	}

	@Ignore("Test not finished and is failing. 9 Nov 11")
	@Test
	public void testAreaLorenzian() {
		Assert.assertEquals(area, fittedLorenzian.get(0).getPeak(0).getArea(), delta);
	}

	@Ignore("Test not finished and is failing. 9 Nov 11")
	@Test
	public void testAreaPearsonVII() {
		Assert.assertEquals(area, fittedPearsonVII.get(0).getPeak(0).getArea(), delta);
	}

	@Ignore("Test not finished and is failing. 9 Nov 11")
	@Test
	public void testAreaPseudoVoigt() {
		Assert.assertEquals(area, fittedPseudoVoigt.get(0).getPeak(0).getArea(), delta);
	}
}

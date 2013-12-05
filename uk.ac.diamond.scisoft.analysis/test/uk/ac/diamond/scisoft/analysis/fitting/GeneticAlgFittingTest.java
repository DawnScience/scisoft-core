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
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.fitting.functions.CompositeFunction;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Gaussian;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Lorentzian;
import uk.ac.diamond.scisoft.analysis.fitting.functions.PearsonVII;
import uk.ac.diamond.scisoft.analysis.fitting.functions.PseudoVoigt;
import uk.ac.diamond.scisoft.analysis.optimize.GeneticAlg;

public class GeneticAlgFittingTest {

	static DoubleDataset gaussian;
	static DoubleDataset lorentzian;
	static DoubleDataset pearsonVII;
	static DoubleDataset pseudoVoigt;
	static DoubleDataset xAxis;

	static double accuracy;
	static int smoothing;
	static int numPeaks = -1;

	static double pos;
	static double fwhm;
	static double area;
	static double delta;
	private static List<CompositeFunction> fittedGaussian;
	private static List<CompositeFunction> fittedLorentzian;
	private static List<CompositeFunction> fittedPearsonVII;
	private static List<CompositeFunction> fittedPseudoVoigt;

	static final long seed = 12357L;

	public static void doFitting() {
		fittedGaussian = Generic1DFitter.fitPeakFunctions(xAxis, gaussian, Gaussian.class,
				new GeneticAlg(accuracy, seed), smoothing, numPeaks);
		fittedLorentzian = Generic1DFitter.fitPeakFunctions(xAxis, lorentzian, Lorentzian.class,
				new GeneticAlg(accuracy, seed), smoothing, numPeaks);
		fittedPearsonVII = Generic1DFitter.fitPeakFunctions(xAxis, pearsonVII, PearsonVII.class,
				new GeneticAlg(accuracy, seed), smoothing, numPeaks);
		fittedPseudoVoigt = Generic1DFitter.fitPeakFunctions(xAxis, pseudoVoigt, PseudoVoigt.class,
				new GeneticAlg(accuracy, seed), smoothing, numPeaks);
	}

	@BeforeClass
	public static void setupTestEnvironment() {

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
		Assert.assertEquals(1, fittedGaussian.size());
	}

	@Test
	public void testNumberOfPeaksFoundLorentzian() {
		Assert.assertEquals(1, fittedLorentzian.size());
	}

	@Test
	public void testNumberOfPeaksFoundPearsonVII() {
		Assert.assertEquals(1, fittedPearsonVII.size());
	}

	@Test
	public void testNumberOfPeaksFoundPseudoVoigt() {
		Assert.assertEquals(1, fittedPseudoVoigt.size());
	}

	@Test
	public void testPeakPosGaussian() {
		Assert.assertEquals(pos, fittedGaussian.get(0).getPeak(0).getPosition(), delta);
	}

	@Test
	public void testPeakPosLorentzian() {
		Assert.assertEquals(pos, fittedLorentzian.get(0).getPeak(0).getPosition(), delta);
	}

	@Test
	public void testPeakPosPearsonVII() {
		Assert.assertEquals(pos, fittedPearsonVII.get(0).getPeak(0).getPosition(), delta);
	}

	@Test
	public void testPeakPosPseudoVoigt() {
		Assert.assertEquals(pos, fittedPseudoVoigt.get(0).getPeak(0).getPosition(), delta);
	}

	@Test
	public void testFWHMGaussian() {
		Assert.assertEquals(fwhm, fittedGaussian.get(0).getPeak(0).getFWHM(), delta);
	}

	@Test
	public void testFWHMLorentzian() {
		Assert.assertEquals(fwhm, fittedLorentzian.get(0).getPeak(0).getFWHM(), 4*delta);
	}

	@Test
	public void testFWHMPearsonVII() {
		Assert.assertEquals(fwhm, fittedPearsonVII.get(0).getPeak(0).getFWHM(), delta);
	}

	@Test
	public void testFWHMPseudoVoigt() {
		Assert.assertEquals(fwhm, fittedPseudoVoigt.get(0).getPeak(0).getFWHM(), 5*delta);
	}

	@Test
	public void testAreaGaussian() {
		Assert.assertEquals(area, fittedGaussian.get(0).getPeak(0).getArea(), delta);
	}

	@Test
	public void testAreaLorentzian() {
		Assert.assertEquals(area, fittedLorentzian.get(0).getPeak(0).getArea(), 6*delta);
	}

	@Test
	public void testAreaPearsonVII() {
		Assert.assertEquals(area, fittedPearsonVII.get(0).getPeak(0).getArea(), 2*delta);
	}

	@Test
	public void testAreaPseudoVoigt() {
		Assert.assertEquals(area, fittedPseudoVoigt.get(0).getPeak(0).getArea(), 9*delta);
	}
}

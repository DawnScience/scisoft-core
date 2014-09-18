/*-
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

import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.fitting.functions.CompositeFunction;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Gaussian;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Lorentzian;
import uk.ac.diamond.scisoft.analysis.fitting.functions.PearsonVII;
import uk.ac.diamond.scisoft.analysis.fitting.functions.PseudoVoigt;
import uk.ac.diamond.scisoft.analysis.optimize.IOptimizer;

public abstract class AbstractFittingTestBase {

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
	static List<CompositeFunction> fittedGaussian;
	static List<CompositeFunction> fittedLorentzian;
	static List<CompositeFunction> fittedPearsonVII;
	static List<CompositeFunction> fittedPseudoVoigt;

	abstract public IOptimizer createOptimizer();

	@Before
	public void doFitting() {
		fittedGaussian = Generic1DFitter.fitPeakFunctions(xAxis, gaussian, Gaussian.class,
				createOptimizer(), smoothing, numPeaks);
		fittedLorentzian = Generic1DFitter.fitPeakFunctions(xAxis, lorentzian, Lorentzian.class,
				createOptimizer(), smoothing, numPeaks);
		fittedPearsonVII = Generic1DFitter.fitPeakFunctions(xAxis, pearsonVII, PearsonVII.class,
				createOptimizer(), smoothing, numPeaks);
		fittedPseudoVoigt = Generic1DFitter.fitPeakFunctions(xAxis, pseudoVoigt, PseudoVoigt.class,
				createOptimizer(), smoothing, numPeaks);
	}

	@BeforeClass
	public static void setupTestEnvironment() {

		gaussian = Generic1DDatasetCreator.createGaussianDataset();
		lorentzian = Generic1DDatasetCreator.createLorentzianDataset();
		pearsonVII = Generic1DDatasetCreator.createPearsonVII();
		pseudoVoigt = Generic1DDatasetCreator.createPseudoVoigt();
		xAxis = Generic1DDatasetCreator.xAxis;

		accuracy = Generic1DDatasetCreator.accuracy;
		smoothing = Generic1DDatasetCreator.smoothing;
		numPeaks = Generic1DDatasetCreator.numPeaks;

		pos = Generic1DDatasetCreator.peakPos;
		fwhm = Generic1DDatasetCreator.defaultFWHM;
		area = Generic1DDatasetCreator.defaultArea;
		delta = Generic1DDatasetCreator.delta;
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
		checkClose("Gaussian pos", pos, fittedGaussian.get(0).getPeak(0).getPosition(), delta);
	}

	@Test
	public void testPeakPosLorentzian() {
		checkClose("Lorentzian pos", pos, fittedLorentzian.get(0).getPeak(0).getPosition(), delta);
	}

	@Test
	public void testPeakPosPearsonVII() {
		checkClose("Pearson7 pos", pos, fittedPearsonVII.get(0).getPeak(0).getPosition(), delta);
	}

	@Test
	public void testPeakPosPseudoVoigt() {
		checkClose("PseudoVoigt pos", pos, fittedPseudoVoigt.get(0).getPeak(0).getPosition(), delta);
	}

	boolean verbose = true;

	public void checkClose(String msg, double expected, double calculated, double delta) {
		if (verbose)
			System.out.printf("%s: %.1f%%\n", msg, 100*(expected - calculated)/delta);

		Assert.assertEquals(expected, calculated, delta);
	}
}

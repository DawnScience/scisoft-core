/*-
 * Copyright © 2011 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.fitting;

import gda.analysis.functions.APeak;
import gda.analysis.functions.Gaussian;
import gda.analysis.functions.Lorentzian;
import gda.analysis.functions.PearsonVII;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheConjugateGradient;
@Ignore("Test not finished and is failing. 9 Nov 11")
public class ApacheConjugateGradientFittingTest {

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
	static double delta = 0.1;
	private static List<APeak> fittedGaussian;
	private static List<APeak> fittedLorenzian;
	private static List<APeak> fittedPearsonVII;
	private static List<APeak> fittedPseudoVoigt;

	public static void doFitting() {
		fittedGaussian = Generic1DFitter.fitPeaks(xAxis, gaussian, new Gaussian(1, 1, 1, 1), new ApacheConjugateGradient(),
				smoothing, numPeaks);
		fittedLorenzian = Generic1DFitter.fitPeaks(xAxis, lorentzian, new Lorentzian(1, 1, 1, 1), new ApacheConjugateGradient(), smoothing, numPeaks);
		fittedPearsonVII = Generic1DFitter.fitPeaks(xAxis, pearsonVII, new PearsonVII(1, 1, 1, 1), new ApacheConjugateGradient(), smoothing, numPeaks);
		fittedPseudoVoigt = Generic1DFitter.fitPeaks(xAxis, pseudoVoigt, new PearsonVII(1, 1, 1, 1), new ApacheConjugateGradient(), smoothing, numPeaks);
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
		Assert.assertEquals(pos, fittedGaussian.get(0).getPosition(), delta);
	}

	@Test
	public void testPeakPosLorenzian() {
		Assert.assertEquals(pos, fittedLorenzian.get(0).getPosition(), delta);
	}

	@Test
	public void testPeakPosPearsonVII() {
		Assert.assertEquals(pos, fittedPearsonVII.get(0).getPosition(), delta);
	}

	@Test
	public void testPeakPosPseudoVoigt() {
		Assert.assertEquals(pos, fittedPseudoVoigt.get(0).getPosition(), delta);
	}

	@Test
	public void testFWHMGaussian() {
		Assert.assertEquals(fwhm, fittedGaussian.get(0).getFWHM(), delta);
	}

	@Test
	public void testFWHMLorenzian() {
		Assert.assertEquals(fwhm, fittedLorenzian.get(0).getFWHM(), delta);
	}

	@Test
	public void testFWHMPearsonVII() {
		Assert.assertEquals(fwhm, fittedPearsonVII.get(0).getFWHM(), delta);
	}

	@Test
	public void testFWHMPseudoVoigt() {
		Assert.assertEquals(fwhm, fittedPseudoVoigt.get(0).getFWHM(), delta);
	}

	@Test
	public void testAreaGaussian() {
		Assert.assertEquals(area, fittedGaussian.get(0).getArea(), delta);
	}

	@Test
	public void testAreaLorenzian() {
		Assert.assertEquals(area, fittedLorenzian.get(0).getArea(), delta);
	}

	@Test
	public void testAreaPearsonVII() {
		Assert.assertEquals(area, fittedPearsonVII.get(0).getArea(), delta);
	}

	@Test
	public void testAreaPseudoVoigt() {
		Assert.assertEquals(area, fittedPseudoVoigt.get(0).getArea(), delta);
	}
}

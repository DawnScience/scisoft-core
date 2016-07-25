/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting;

import java.util.List;
import java.util.Map;

import org.eclipse.january.dataset.DoubleDataset;
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

	protected static final String PSEUDO_VOIGT = "PseudoVoigt";
	protected static final String PEARSON_VII = "PearsonVII";
	protected static final String LORENTZIAN = "Lorentzian";
	protected static final String GAUSSIAN = "Gaussian";
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

	static Map<String, int[]> deltaFactor;

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
	public void testGaussianNumberOfPeaksFound() {
		Assert.assertEquals(1, fittedGaussian.size());
	}

	@Test
	public void testGaussianPeakPos() {
		checkClose(GAUSSIAN + " pos", pos, fittedGaussian.get(0).getPeak(0).getPosition(), delta);
	}

	@Test
	public void testGaussianFWHM() {
		checkClose(GAUSSIAN + " fwhm", fwhm, fittedGaussian.get(0).getPeak(0).getFWHM(), deltaFactor.get(GAUSSIAN)[0]*delta);
	}

	@Test
	public void testGaussianArea() {
		checkClose(GAUSSIAN + " area", area, fittedGaussian.get(0).getPeak(0).getArea(), deltaFactor.get(GAUSSIAN)[1]*delta);
	}

	@Test
	public void testLorentzianNumberOfPeaksFound() {
		Assert.assertEquals(1, fittedLorentzian.size());
	}

	@Test
	public void testLorentzianPeakPos() {
		checkClose(LORENTZIAN + " pos", pos, fittedLorentzian.get(0).getPeak(0).getPosition(), delta);
	}

	@Test
	public void testLorentzianFWHM() {
		checkClose(LORENTZIAN + " fwhm", fwhm, fittedLorentzian.get(0).getPeak(0).getFWHM(), deltaFactor.get(LORENTZIAN)[0]*delta);
	}

	@Test
	public void testLorentzianArea() {
		checkClose(LORENTZIAN + " area", area, fittedLorentzian.get(0).getPeak(0).getArea(), deltaFactor.get(LORENTZIAN)[1]*delta);
	}

	@Test
	public void testPearsonVIINumberOfPeaksFound() {
		Assert.assertEquals(1, fittedPearsonVII.size());
	}

	@Test
	public void testPearsonVIIPeakPos() {
		checkClose(PEARSON_VII + " pos", pos, fittedPearsonVII.get(0).getPeak(0).getPosition(), delta);
	}

	@Test
	public void testPearsonVIIFWHM() {
		checkClose(PEARSON_VII + " fwhm", fwhm, fittedPearsonVII.get(0).getPeak(0).getFWHM(), deltaFactor.get(PEARSON_VII)[0]*delta);
	}

	@Test
	public void testPearsonVIIArea() {
		checkClose(PEARSON_VII + " area", area, fittedPearsonVII.get(0).getPeak(0).getArea(), deltaFactor.get(PEARSON_VII)[1]*delta);
	}

	@Test
	public void testPseudoVoigtNumberOfPeaksFound() {
		Assert.assertEquals(1, fittedPseudoVoigt.size());
	}

	@Test
	public void testPseudoVoigtPeakPos() {
		checkClose(PSEUDO_VOIGT + " pos", pos, fittedPseudoVoigt.get(0).getPeak(0).getPosition(), delta);
	}

	@Test
	public void testPseudoVoigtFWHM() {
		checkClose(PSEUDO_VOIGT + " fwhm", fwhm, fittedPseudoVoigt.get(0).getPeak(0).getFWHM(), deltaFactor.get(PSEUDO_VOIGT)[0]*delta);
	}

	@Test
	public void testPseudoVoigtArea() {
		checkClose(PSEUDO_VOIGT + " area", area, fittedPseudoVoigt.get(0).getPeak(0).getArea(), deltaFactor.get(PSEUDO_VOIGT)[1]*delta);
	}

	boolean verbose = true;

	public void checkClose(String msg, double expected, double calculated, double delta) {
		if (verbose)
			System.out.printf("%s: %.1f%%\n", msg, 100*(expected - calculated)/delta);

		Assert.assertEquals(expected, calculated, delta);
	}
}

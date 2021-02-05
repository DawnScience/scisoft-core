/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.fitting.functions.IPeak;
import org.eclipse.january.dataset.Dataset;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.fitting.functions.CompositeFunction;
import uk.ac.diamond.scisoft.analysis.fitting.functions.PeakType;
import uk.ac.diamond.scisoft.analysis.optimize.IOptimizer;

public abstract class AbstractFittingTestBase {

	static double accuracy;
	static double pos;
	static double fwhm;
	static double area;
	static double delta;
	static Map<PeakType, List<CompositeFunction>> fitted;

	static Map<PeakType, int[]> deltaFactor;


	abstract public IOptimizer createOptimizer();

	@BeforeClass
	public static void setupTestEnvironment() {
		fitted = new HashMap<>();
		accuracy = Generic1DDatasetCreator.accuracy;
		pos = Generic1DDatasetCreator.peakPos;
		fwhm = Generic1DDatasetCreator.defaultFWHM;
		area = Generic1DDatasetCreator.defaultArea;
		delta = Generic1DDatasetCreator.delta;
	}

	private IPeak getFittedPeak(PeakType type) {
		return get(type).get(0).getPeak(0);
	}

	private List<CompositeFunction> get(PeakType type) {
		if (!fitted.containsKey(type)) {
			Dataset function = Generic1DDatasetCreator.createDataset(type);
			fitted.put(type, Generic1DFitter.fitPeakFunctions(Generic1DDatasetCreator.xAxis, function, type.getPeakClass(),
					createOptimizer(), Generic1DDatasetCreator.smoothing, Generic1DDatasetCreator.numPeaks));
		}
		return fitted.get(type);
	}

	private void testSize(PeakType type) {
		assertEquals(1, get(type).size());
	}

	private void testPeakPos(PeakType type) {
		checkClose(type + " pos", pos, getFittedPeak(type).getPosition(), delta);
	}

	private void testFWHM(PeakType type) {
		checkClose(type + " fwhm", fwhm, getFittedPeak(type).getFWHM(), deltaFactor.get(type)[0]*delta);
	}

	private void testArea(PeakType type) {
		checkClose(type + " area", area, getFittedPeak(type).getArea(), deltaFactor.get(type)[1]*delta);
	}

	@Test
	public void testGaussianNumberOfPeaksFound() {
		testSize(PeakType.GAUSSIAN);
	}

	@Test
	public void testGaussianPeakPos() {
		testPeakPos(PeakType.GAUSSIAN);
	}

	@Test
	public void testGaussianFWHM() {
		testFWHM(PeakType.GAUSSIAN);
	}

	@Test
	public void testGaussianArea() {
		testArea(PeakType.GAUSSIAN);
	}

	@Test
	public void testLorentzianNumberOfPeaksFound() {
		testSize(PeakType.LORENTZIAN);
	}

	@Test
	public void testLorentzianPeakPos() {
		testPeakPos(PeakType.LORENTZIAN);
	}

	@Test
	public void testLorentzianFWHM() {
		testFWHM(PeakType.LORENTZIAN);
	}

	@Test
	public void testLorentzianArea() {
		testArea(PeakType.LORENTZIAN);
	}

	@Test
	public void testPearsonVIINumberOfPeaksFound() {
		testSize(PeakType.PEARSON_VII);
	}

	@Test
	public void testPearsonVIIPeakPos() {
		testPeakPos(PeakType.PEARSON_VII);
	}

	@Test
	public void testPearsonVIIFWHM() {
		testFWHM(PeakType.PEARSON_VII);
	}

	@Test
	public void testPearsonVIIArea() {
		testArea(PeakType.PEARSON_VII);
	}

	@Test
	public void testPseudoVoigtNumberOfPeaksFound() {
		testSize(PeakType.PSEUDO_VOIGT);
	}

	@Test
	public void testPseudoVoigtPeakPos() {
		testPeakPos(PeakType.PSEUDO_VOIGT);
	}

	@Test
	public void testPseudoVoigtFWHM() {
		testFWHM(PeakType.PSEUDO_VOIGT);
	}

	@Test
	public void testPseudoVoigtArea() {
		testArea(PeakType.PSEUDO_VOIGT);
	}

	@Test
	public void testVoigtNumberOfPeaksFound() {
		testSize(PeakType.VOIGT);
	}

	@Test
	public void testVoigtPeakPos() {
		testPeakPos(PeakType.VOIGT);
	}

	@Test
	public void testVoigtFWHM() {
		testFWHM(PeakType.VOIGT);
	}

	@Test
	public void testVoigtArea() {
		testArea(PeakType.VOIGT);
	}

	boolean verbose = true;

	public void checkClose(String msg, double expected, double calculated, double delta) {
		if (verbose)
			System.out.printf("%s: %.1f%%\n", msg, 100*(expected - calculated)/delta);

		assertEquals(expected, calculated, delta);
	}
}

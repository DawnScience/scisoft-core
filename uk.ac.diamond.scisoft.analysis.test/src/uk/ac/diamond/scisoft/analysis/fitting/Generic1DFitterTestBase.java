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
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
import org.eclipse.dawnsci.analysis.dataset.impl.Random;
import org.junit.Before;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.fitting.functions.APeak;
import uk.ac.diamond.scisoft.analysis.fitting.functions.CompositeFunction;
import uk.ac.diamond.scisoft.analysis.fitting.functions.FunctionFactory;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Gaussian;
import uk.ac.diamond.scisoft.analysis.fitting.functions.IPeak;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Lorentzian;
import uk.ac.diamond.scisoft.analysis.fitting.functions.PearsonVII;
import uk.ac.diamond.scisoft.analysis.fitting.functions.PseudoVoigt;
import uk.ac.diamond.scisoft.analysis.optimize.GeneticAlg;
import uk.ac.diamond.scisoft.analysis.optimize.IOptimizer;

public abstract class Generic1DFitterTestBase {
	static final int dataRange = 550;
	static int[] defaultPeakPos = new int[] { 100, 200, 300, 400, 500, 150, 250, 350, 450 };
	static final int defaultFWHM = 20;
	static final int defaultArea = 50;
	static final double delta = 2;
	static final double lambda = 0.1;
	static final boolean backgroundDominated = true;
	static final boolean autoStopping = true;
	static final double threshold = 0.10;
	static final int numPeaks = -1;
	static final int smoothing = 5;
	static final DoubleDataset xAxis = (DoubleDataset) DatasetFactory.createRange(0, dataRange, 1, Dataset.FLOAT64);

	static final long seed = 12357L;

	protected static String name;
	
	@Before
	public void setup() throws Exception {
		FunctionFactory.registerFunctions(Gaussian.class, 
				Lorentzian.class, PseudoVoigt.class, PearsonVII.class);
	}

	@Test
	public void testPearsonVIIFitting() {
		int i = defaultPeakPos.length;
		int[] peakPos = new int[i];
		for (int j = 0; j < i; j++) {
			peakPos[j] = defaultPeakPos[j];
		}
		DoubleDataset testingPeaks = generatePearsonVII(i);
		try {
			fittingTest(peakPos, testingPeaks, FunctionFactory.getClassForPeakFn("PearsonVII"));
		} catch (Exception e) {
			System.out.println(e);
			fail("The number of generated peaks did not match the number of peaks found using " + name);
		}
	}

	@Test
	public void testGaussianFitting() {
		int i = defaultPeakPos.length;
		int[] peakPos = new int[i];
		for (int j = 0; j < i; j++) {
			peakPos[j] = defaultPeakPos[j];
		}
		DoubleDataset testingPeaks = generateGaussianPeaks(i);
		try {
			fittingTest(peakPos, testingPeaks, FunctionFactory.getClassForPeakFn("Gaussian"));
		} catch (Exception e) {
			System.out.println(e);
			fail("The number of generated peaks did not match the number of peaks found using " + name);
		}
	}

	@Test
	public void testPseudoVoigt() {
		int i = defaultPeakPos.length;
		int[] peakPos = new int[i];
		for (int j = 0; j < i; j++) {
			peakPos[j] = defaultPeakPos[j];
		}
		DoubleDataset testingPeaks = generatePseudoVoigt(i);
		try {
			fittingTest(peakPos, testingPeaks, FunctionFactory.getClassForPeakFn("PseudoVoigt"));
		} catch (Exception e) {
			System.out.println(e);
			fail("The number of generated peaks did not match the number of peaks found using " + name);
		}
	}

	@Test
	public void testLorentzianFitting() {
		int i = defaultPeakPos.length;
		int[] peakPos = new int[i];
		for (int j = 0; j < i; j++) {
			peakPos[j] = defaultPeakPos[j];
		}
		DoubleDataset testingPeaks = generateLorentzianPeaks(i);
		try {
			fittingTest(peakPos, testingPeaks, FunctionFactory.getClassForPeakFn("Lorentzian"));
		} catch (Exception e) {
			System.out.println(e);
			fail("The number of generated peaks did not match the number of peaks found using " + name);
		}
	}

	private DoubleDataset generatePseudoVoigt(int numPeaks) {
		CompositeFunction function = new CompositeFunction();
		if (numPeaks > defaultPeakPos.length)
			numPeaks = defaultPeakPos.length;
		for (int i = 0; i < numPeaks; i++) {
			function.addFunction(new PseudoVoigt(defaultPeakPos[i] - 20, defaultPeakPos[i] + 20, defaultFWHM,
					defaultArea));
		}
		DoubleDataset data = function.calculateValues(xAxis);
		return (DoubleDataset) Maths.add(data, generateNoisePlusBackground());
	}

	private DoubleDataset generatePearsonVII(int numPeaks) {
		CompositeFunction function = new CompositeFunction();
		if (numPeaks > defaultPeakPos.length)
			numPeaks = defaultPeakPos.length;
		for (int i = 0; i < numPeaks; i++) {
			function.addFunction(new PearsonVII(defaultPeakPos[i] - 20, defaultPeakPos[i] + 20, defaultFWHM,
					defaultArea));
		}
		DoubleDataset data = function.calculateValues(xAxis);
		return (DoubleDataset) Maths.add(data, generateNoisePlusBackground());
	}

	private DoubleDataset generateLorentzianPeaks(int numPeaks) {
		CompositeFunction function = new CompositeFunction();
		if (numPeaks > defaultPeakPos.length)
			numPeaks = defaultPeakPos.length;
		for (int i = 0; i < numPeaks; i++) {
			function.addFunction(new Lorentzian(defaultPeakPos[i] - 20, defaultPeakPos[i] + 20, defaultArea,
					defaultFWHM/2));
		}
		DoubleDataset data = function.calculateValues(xAxis);
		return (DoubleDataset) Maths.add(data, generateNoisePlusBackground());
	}

	private DoubleDataset generateGaussianPeaks(int numPeaks) {
		CompositeFunction function = new CompositeFunction();
		if (numPeaks > defaultPeakPos.length)
			numPeaks = defaultPeakPos.length;
		for (int i = 0; i < numPeaks; i++) {
			function.addFunction(new Gaussian(defaultPeakPos[i] - 20, defaultPeakPos[i] + 20, defaultFWHM, defaultArea));
		}
		DoubleDataset data = function.calculateValues(xAxis);
		return (DoubleDataset) Maths.add(data, generateNoisePlusBackground());
	}

	private DoubleDataset generateNoisePlusBackground() {
		return generateBackground();
	}

	private DoubleDataset generateBackground() {
		CompositeFunction comp = new CompositeFunction();
		comp.addFunction(new Gaussian(-10, 10, dataRange / 4, dataRange / 2));
		return comp.calculateValues(DoubleDataset.createRange(dataRange));
	}

	@SuppressWarnings("unused")
	private DoubleDataset addNoiseToDataSet(DoubleDataset data) {
		Random.seed(123568);
		DoubleDataset noise = (DoubleDataset) Random.poisson(lambda, dataRange).cast(Dataset.FLOAT64);
		noise = (DoubleDataset) Maths.multiply(noise, 0.01);
		return (DoubleDataset) Maths.add(data, noise);
	}

	private void fittingTest(int[] peakPos, DoubleDataset data, Class<? extends IPeak> mypeakClass) {
		//TODO FIXME Once Generic1DFitter updated, remove this cast.
		@SuppressWarnings("unchecked")
		Class<? extends APeak> peakClass = (Class<? extends APeak>)mypeakClass;
		List<CompositeFunction> fittedPeakList = Generic1DFitter.fitPeakFunctions(xAxis, data, peakClass, new GeneticAlg(0.0001, seed),
				smoothing, numPeaks, threshold, autoStopping, backgroundDominated);

		double[] fittedPeakPos = new double[fittedPeakList.size()];
		int i = 0;
		for (CompositeFunction p : fittedPeakList) {
			fittedPeakPos[i++] = p.getPeak(0).getPosition();
		}
		Arrays.sort(fittedPeakPos);
		Arrays.sort(peakPos);

		assertEquals("The number of peaks found was not the same as generated", peakPos.length, fittedPeakPos.length);

		for (int k = 0; k < fittedPeakPos.length; k++) {
			assertEquals(peakPos[k], fittedPeakPos[k], delta);
		}
	}

	abstract public IOptimizer createOptimizer();
}

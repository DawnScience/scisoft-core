/*-
 * Copyright © 2009 Diamond Light Source Ltd.
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
import org.junit.Ignore;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import gda.analysis.functions.APeak;
import gda.analysis.functions.CompositeFunction;
import gda.analysis.functions.Gaussian;
import gda.analysis.functions.Lorentzian;
import gda.analysis.functions.PearsonVII;
import gda.analysis.functions.PseudoVoigt;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Maths;
import uk.ac.diamond.scisoft.analysis.optimize.GeneticAlg;
@Ignore("Test not finished and is failing. 9 Nov 11")
public class Generic1DFitterGeneticAlgTest {
	static final int dataRange = 550;
	static int[] defaultPeakPos = new int[] { 100, 200, 300, 400, 500, 150, 250, 350, 450 };
	static final int defaultFWHM = 20;
	static final int defaultArea = 50;
	static final double delta = 0.5;
	static final double lambda = 0.1;
	static final boolean backgroundDominated = true;
	static final boolean autoStopping = true;
	static final double threshold = 0.10;
	static final int numPeaks = -1;
	static final int smoothing = 5;
	static final DoubleDataset xAxis = (DoubleDataset) AbstractDataset.arange(0, dataRange, 1, AbstractDataset.FLOAT64);

	@Test
	public void testPearsonVIIFitting() {
		int i = defaultPeakPos.length;
		int[] peakPos = new int[i];
		for (int j = 0; j < i; j++) {
			peakPos[j] = defaultPeakPos[j];
		}
		DoubleDataset testingPeaks = generatePearsonVII(i);
		try {
			FittingTestGeneticAlg(peakPos, testingPeaks, new PearsonVII(10,10,10,10,10));
		} catch (Exception e) {
			System.out.println(e);
			fail("The number of generated peaks did not match the number of peaks found using Genetic Algs");
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
			FittingTestGeneticAlg(peakPos, testingPeaks, new Gaussian(10,10,10));
		} catch (Exception e) {
			System.out.println(e);
			fail("The number of generated peaks did not match the number of peaks found using Genetic Algs");
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
			FittingTestGeneticAlg(peakPos, testingPeaks, new PseudoVoigt(1, 1, 1, 1));
		} catch (Exception e) {
			System.out.println(e);
			fail("The number of generated peaks did not match the number of peaks found using Genetic Algs");
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
			FittingTestGeneticAlg(peakPos, testingPeaks, new Lorentzian(10));
		} catch (Exception e) {
			System.out.println(e);
			fail("The number of generated peaks did not match the number of peaks found using Genetic Algs");
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
		DoubleDataset data = function.makeDataset(xAxis);
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
		DoubleDataset data = function.makeDataset(xAxis);
		return (DoubleDataset) Maths.add(data, generateNoisePlusBackground());
	}

	private DoubleDataset generateLorentzianPeaks(int numPeaks) {
		CompositeFunction function = new CompositeFunction();
		if (numPeaks > defaultPeakPos.length)
			numPeaks = defaultPeakPos.length;
		for (int i = 0; i < numPeaks; i++) {
			function.addFunction(new Lorentzian(defaultPeakPos[i] - 20, defaultPeakPos[i] + 20, defaultFWHM,
					defaultArea));
		}
		DoubleDataset data = function.makeDataset(xAxis);
		return (DoubleDataset) Maths.add(data, generateNoisePlusBackground());
	}

	private DoubleDataset generateGaussianPeaks(int numPeaks) {
		CompositeFunction function = new CompositeFunction();
		if (numPeaks > defaultPeakPos.length)
			numPeaks = defaultPeakPos.length;
		for (int i = 0; i < numPeaks; i++) {
			function.addFunction(new Gaussian(defaultPeakPos[i] - 20, defaultPeakPos[i] + 20, defaultFWHM, defaultArea));
		}
		DoubleDataset data = function.makeDataset(xAxis);

		return (DoubleDataset) Maths.add(data, generateNoisePlusBackground());
	}

	private DoubleDataset generateNoisePlusBackground() {
		return generateBackground();
	}

	private DoubleDataset generateBackground() {
		CompositeFunction comp = new CompositeFunction();
		comp.addFunction(new Gaussian(-10, 10, dataRange / 4, dataRange / 2));
		return comp.makeDataset(DoubleDataset.arange(dataRange));
	}

	private void FittingTestGeneticAlg(int[] peakPos, DoubleDataset data, APeak peakFunction) {

		List<APeak> fittedPeakList = Generic1DFitter.fitPeaks(xAxis, data, peakFunction, new GeneticAlg(0.0001),
				smoothing, numPeaks, threshold, autoStopping, backgroundDominated);
		
		double[] fittedPeakPos = new double[fittedPeakList.size()];
		int i = 0;
		for (APeak p : fittedPeakList) {
			fittedPeakPos[i++] = p.getPosition();
		}
		Arrays.sort(fittedPeakPos);
		Arrays.sort(peakPos);

		assertEquals("The number of peaks found was not the same as generated", peakPos.length, fittedPeakPos.length);

		for (int k = 0; k < fittedPeakPos.length; k++) {
			assertEquals(peakPos[k], fittedPeakPos[k], delta);
		}
	}
}
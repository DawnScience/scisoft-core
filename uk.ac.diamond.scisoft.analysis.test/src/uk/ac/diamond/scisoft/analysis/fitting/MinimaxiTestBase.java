/*-
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
import org.eclipse.january.dataset.DoubleDataset;
import org.junit.runners.Parameterized.Parameters;

import uk.ac.diamond.scisoft.analysis.fitting.functions.AFunction;
import uk.ac.diamond.scisoft.analysis.fitting.functions.CoordinatesIterator;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer;
import uk.ac.diamond.scisoft.analysis.optimize.IOptimizer;

/**
 * Test minimizer and maximizer
 */
abstract class MinimaxiTestBase {
	@Parameters(name = "{index}: {0}")
	public static Collection<Object> data() {
		return Arrays.asList(ApacheOptimizer.Optimizer.SIMPLEX_MD,
				ApacheOptimizer.Optimizer.SIMPLEX_NM,
				ApacheOptimizer.Optimizer.POWELL,
				ApacheOptimizer.Optimizer.CMAES,
//				ApacheOptimizer.Optimizer.BOBYQA, // does not work!!!
				ApacheOptimizer.Optimizer.CONJUGATE_GRADIENT);
	}

	class Function extends AFunction {
		public Function() {
			super(1);
		}

		static final double gMinPos = -6.83085195573;
		static final double lMinPos = 6.15340115458;
		static final double lMaxPos = 1.42745080114;

		@Override
		protected void setNames() {
			setNames("function", "function with two minima at -6.830851956 (global), 6.153401155 and local maximum at 1.427450802", "x");
		}

		@Override
		public double val(double... values) {
			double x = getParameterValue(0);
			return x*(x-3)*(x-8)*(x+10);
		}

		@Override
		public void fillWithValues(DoubleDataset data, CoordinatesIterator it) {
			double x = getParameterValue(0);
			double v = x*(x-3)*(x-8)*(x+10); // x^4 - x^3 - 86*x^2 + 240*x
			it.reset();
			int i = 0;
			double[] buffer = data.getData();
			while (it.hasNext()) {
				buffer[i++] = v;
			}
		}
	}

	protected static final double DELTA = 1e-4;
	protected double delta = DELTA;
	protected IOptimizer optimizer;
	protected boolean globalMin = false;
	protected boolean omit = false;
	protected boolean doMinimize = true;

	abstract public void setup() throws Exception;

	public void internalTest() throws Exception {
		setup();
		Function quartic = new Function();
		IParameter p = quartic.getParameter(0);
		p.setLimits(-20, 20);

		p.setValue(-15);
		optimizer.optimize(true, quartic);
		assertEquals(Function.gMinPos, p.getValue(), delta);

		p.setValue(Function.lMaxPos);
		optimizer.optimize(true, quartic);
		if (!omit) {
			if (globalMin) { // global search (depends on seed(!))
				assertEquals(Function.gMinPos, p.getValue(), delta);
			} else {
				assertEquals(Function.lMinPos, p.getValue(), delta);
			}
		}

		p.setValue(15);
		optimizer.optimize(true, quartic);
		if (globalMin) { // global search (depends on seed(!))
			assertEquals(Function.gMinPos, p.getValue(), delta);
		} else {
			assertEquals(Function.lMinPos, p.getValue(), delta);
		}

		if (doMinimize ) {
			p.setLimits(Function.gMinPos, Function.lMinPos);

			p.setValue(-5);
			optimizer.optimize(false, quartic);
			if (!omit) {
				assertEquals(Function.lMaxPos, p.getValue(), delta);
			}

			p.setValue(5);
			optimizer.optimize(false, quartic);
			assertEquals(Function.lMaxPos, p.getValue(), delta);
		}
	}
}

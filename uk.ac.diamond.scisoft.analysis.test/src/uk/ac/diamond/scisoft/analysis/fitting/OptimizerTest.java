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

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IOperator;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.junit.Assert;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.fitting.functions.APeak;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Add;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Gaussian;
import uk.ac.diamond.scisoft.analysis.fitting.functions.IdentifiedPeak;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Offset;
import uk.ac.diamond.scisoft.analysis.optimize.AbstractOptimizer;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer.Optimizer;

public class OptimizerTest  {

	static final long SEED = 12357L;

	public AbstractOptimizer createOptimizer(Optimizer o) {
		ApacheOptimizer opt = new ApacheOptimizer(o);
		opt.seed = SEED;
		return opt;
	}

	@Test
	public void testOptimizer() {
		DoubleDataset gaussian = Generic1DDatasetCreator.createGaussianDataset();

		List<IdentifiedPeak> peaks = Generic1DFitter.parseDataDerivative(Generic1DDatasetCreator.xAxis, gaussian, Generic1DDatasetCreator.smoothing);

		IdentifiedPeak iniPeak = peaks.get(0);
		int[] start = { iniPeak .getIndexOfDatasetAtMinPos() };
		int[] stop = { iniPeak.getIndexOfDatasetAtMaxPos() + 1 };
		int[] step = { 1 };
		Dataset y = gaussian.getSlice(start, stop, step);
		Dataset x = Generic1DDatasetCreator.xAxis.getSlice(start, stop, step);
		double lowOffset = y.min().doubleValue();
		double highOffset = (Double) y.mean();
		Offset baseline = new Offset(lowOffset, highOffset);
		APeak localPeak = new Gaussian(iniPeak);
		IOperator comp = new Add();
		comp.addFunction(localPeak);
		comp.addFunction(baseline);

		AbstractOptimizer opt = createOptimizer(Optimizer.SIMPLEX_MD);
		try {
			opt.optimize(new IDataset[] {x}, y, comp);
		} catch (Exception e) {
			System.err.println("Problem: " + e);
		}

		double[] parameters = opt.getParameterValues();
		for (int ind = 0; ind < parameters.length; ind++) {
			double v = parameters[ind];
			double dv = v * 1e-5;
			double od = evalDiff(parameters, ind, v, dv, opt);
			double nd = 0;
			System.err.printf("Difference is %g for %g\n", od, dv);
			dv *= 0.25;

			for (int i = 0; i < 20; i++) {
				// System.err.println(Arrays.toString(parameters));
				nd = evalDiff(parameters, ind, v, dv, opt);
				System.err.printf("Difference is %g for %g\n", nd, dv);
				if (Math.abs(nd - od) < 1e-15*Math.max(1, Math.abs(od))) {
					break;
				}
				od = nd;
				dv *= 0.25;
			}

			parameters[ind] = v;
			double pd = opt.calculateResidualDerivative(opt.getParameters().get(ind), parameters);
			System.err.println(nd + " cf " + pd);
			Assert.assertEquals(nd,  pd, 5e-3*Math.abs(pd));
		}
	}

	private double evalDiff(double[] parameters, int ind, double v, double dv, AbstractOptimizer opt) {
		parameters[ind] = v + dv;
		opt.setParameterValues(parameters);
		double r = opt.calculateResidual();

		parameters[ind] = v - dv;
		opt.setParameterValues(parameters);
		r -= opt.calculateResidual();

		return (r * 0.5) / dv;
	}
}

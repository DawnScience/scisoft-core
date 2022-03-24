/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting;

import static org.junit.Assert.assertTrue;

import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.Maths;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.fitting.functions.AFunction;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Add;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Gaussian;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Offset;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Polynomial;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Quadratic;
import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;

public class FitterTest {
	@BeforeClass
	public static void setSeed() {
		Fitter.seed = 12475L;
	}

	@Test
	public void testGaussianFit() {
		double pos = 2.0;
		double fwhm = 3.0;
		double area = 4.0;
		Gaussian gauss = new Gaussian(pos,fwhm,area);
		DoubleDataset xAxis = DatasetFactory.createRange(DoubleDataset.class, -10.0,10.0,0.1);
		DoubleDataset ds = gauss.calculateValues(xAxis);
		
		AFunction result = Fitter.GaussianFit(ds, xAxis);
		
		Assert.assertEquals(pos, result.getParameterValue(0), 0.1);
		Assert.assertEquals(fwhm, result.getParameterValue(1), 0.1);
		Assert.assertEquals(area, result.getParameterValue(2), 0.1);
	}

	@Test
	public void testGaussianOffsetFit() {
		double pos = 14.5;
		double fwhm = 4.5;
		double area = 4.5;
		double coff = 2.5;
		Gaussian gauss = new Gaussian(pos,fwhm,area);
		gauss.getParameter(0).setLimits(10, 19);
		gauss.getParameter(1).setLimits(0, 9);
		gauss.getParameter(2).setLimits(0, 45);
		Offset off = new Offset(coff);
		off.getParameter(0).setLimits(0, 5);
		Add add = new Add();
		add.addFunction(gauss);
		add.addFunction(off);
		DoubleDataset xAxis = DatasetFactory.createRange(DoubleDataset.class, 10., 20., 1.);
		DoubleDataset data = DatasetFactory.createFromObject(DoubleDataset.class, new double[] {0, 1, 2, 3, 4, 5, 4, 3, 2, 1});
//		DoubleDataset ds = gauss.calculateValues(xAxis);

		try {
			Fitter.geneticFit(new Dataset[] {xAxis}, data, add);
		} catch (Exception e) {
		}

		Assert.assertEquals(15.0, gauss.getParameterValue(0), 0.1);
		Assert.assertEquals(5.2, gauss.getParameterValue(1), 0.1);
		Assert.assertEquals(25.8, gauss.getParameterValue(2), 0.1);
		Assert.assertEquals(0.0, off.getParameterValue(0), 0.1);
	}

	@Test
	public void testNDGaussianFit1D() {
		double pos = 2.0;
		double fwhm = 3.0;
		double area = 4.0;
		Gaussian gauss = new Gaussian(pos,fwhm,area);
		DoubleDataset xAxis = DatasetFactory.createRange(DoubleDataset.class, -10.0,10.0,0.1);
		DoubleDataset ds = gauss.calculateValues(xAxis);
		
		NDGaussianFitResult result = Fitter.NDGaussianSimpleFit(ds, xAxis);
		
		Assert.assertEquals(pos, result.getPos()[0], 0.1);
		Assert.assertEquals(fwhm, result.getFwhm()[0], 0.1);
		Assert.assertEquals(area, result.getArea()[0], 0.1);
	}
	
	@Test
	public void testNDGaussianFit2D() {
		double pos1 = 2.0;
		double fwhm1 = 3.0;
		double area1 = 4.0;
		double pos2 = 1.0;
		double fwhm2 = 1.5;
		double area2 = 2.0;
		double xAxisStep = 0.1;
		double yAxisStep = 0.2;
		
		Gaussian gauss1 = new Gaussian(pos1,fwhm1,area1);
		DoubleDataset xAxis = DatasetFactory.createRange(DoubleDataset.class, -10.0,10.0,xAxisStep);
		DoubleDataset ds1 = gauss1.calculateValues(xAxis);
		
		Gaussian gauss2 = new Gaussian(pos2,fwhm2,area2);
		DoubleDataset yAxis = DatasetFactory.createRange(DoubleDataset.class, -20.0,20.0,yAxisStep);
		DoubleDataset ds2 = gauss2.calculateValues(yAxis);
		
		DoubleDataset ds = DatasetFactory.zeros(xAxis.getShape()[0], xAxis.getShape()[0]);
		
		IndexIterator iter = ds.getIterator(true);
		int[] pos;
		while(iter.hasNext()) {
			pos = iter.getPos();
			ds.set((ds1.getDouble(pos[0])*ds2.getDouble(pos[1])), pos);
		}
		
		NDGaussianFitResult result = Fitter.NDGaussianSimpleFit(ds, xAxis,yAxis);
		
		double area = ((Number) ds.sum()).doubleValue();
		
		Assert.assertEquals(pos1, result.getPos()[0], 0.1);
		Assert.assertEquals(pos2, result.getPos()[1], 0.1);
		Assert.assertEquals(fwhm1, result.getFwhm()[0], 0.1);
		Assert.assertEquals(fwhm2, result.getFwhm()[1], 0.1);
		Assert.assertEquals(area*xAxisStep, result.getArea()[0], 0.1);
		Assert.assertEquals(area*yAxisStep, result.getArea()[1], 0.1);
	}

	@Test
	public void testLLSquareFit() {
		Dataset x = DatasetFactory.createFromObject(new double[] {102,134,156});
		Dataset y = DatasetFactory.createFromObject(new double[] {102.1,134.2,156.3});

		Quadratic q = new Quadratic(new double[] {0, 1, 0});
		try {
			Fitter.llsqFit(new Dataset[] {x}, y, q);

			DoubleDataset z = q.calculateValues(x);
			Assert.assertEquals(y.getDouble(0), z.getDouble(0), 0.1);
			Assert.assertEquals(y.getDouble(1), z.getDouble(1), 0.1);
			Assert.assertEquals(y.getDouble(2), z.getDouble(2), 0.2);
		} catch (Exception e) {
			Assert.fail("" + e);
		}
	}

	@Test
	public void testPolyFit() {
		Dataset x = DatasetFactory.createFromObject(new double[] {102,134,156});
		Dataset y = DatasetFactory.createFromObject(new double[] {102.1,134.2,156.3});

		try {
			Polynomial fit = Fitter.polyFit(new Dataset[] {x}, y, 1e-6, 2);

			DoubleDataset z = fit.calculateValues(x);

			Assert.assertEquals(y.getDouble(0), z.getDouble(0), 0.1);
			Assert.assertEquals(y.getDouble(1), z.getDouble(1), 0.1);
			Assert.assertEquals(y.getDouble(2), z.getDouble(2), 0.1);
		} catch (Exception e) {
			Assert.fail("");
		}
	}

	@Test
	public void testI16Data() throws Exception {
		IDataHolder dh = LoaderFactory.getData("testfiles/fit_data.dat");
		Dataset x = DatasetUtils.convertToDataset(dh.getDataset(0));
		Dataset y = DatasetUtils.convertToDataset(dh.getDataset(1));
		// assume Poisson noise so rmse = sqrt(y) and therefore w = 1/e^2 = 1/|y|
		Dataset w = Maths.reciprocal(Maths.abs(y));

		Add ff = new Add();
		ff.addFunction(new Gaussian());
		ff.addFunction(new Offset());

		double posn = 37.7;
		double width = 0.008;
		double area = 19500;
		double base = y.min().doubleValue();
		IParameter[] params = ff.getParameters();
		params[0].setValue(posn);
		params[0].setLimits(Math.max(x.min().doubleValue(), posn-2*width), Math.min(x.max().doubleValue(), posn+2*width));
		params[1].setValue(width);
		params[1].setLimits(0.2*width, 5*width);
		params[2].setValue(area);
		params[2].setLimits(0.2*area, 2*area);
		params[3].setValue(base);
		params[3].setLimits(0.2*base, 2*base);
		System.out.println(ff);
		double res = ff.residual(true, y, null, x);
		System.out.printf("Initial residual = %g\n", res);
		double resw = ff.residual(true, y, w, x);
		System.out.printf("Initial weighted residual = %g\n", resw);
		assertTrue(res >= resw); // as y >= 1

		Fitter.geneticFit(1e-6, new Dataset[] {x}, y, ff);
		res = ff.residual(true, y, null, x);
		double offset = ff.getParameterValue(3);
		System.out.println(res + " -> " + ff);

		ff.setParameterValues(posn, width, area, base);
		Fitter.geneticFit(1e-6, new Dataset[] {x}, y, w, ff);
		resw = ff.residual(true, y, w, x);
		System.out.println(resw + " -> " + ff);

		assertTrue(res >= resw); // as y >= 1
		// offset has increased as lower weight values on peak base allows
		// fit to cross more of base
		assertTrue(offset < ff.getParameterValue(3)); 
	}
}

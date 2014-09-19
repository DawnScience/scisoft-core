/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import java.io.Serializable;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
import org.eclipse.dawnsci.analysis.dataset.impl.Signal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class that wrappers the Fermi function from Fermi-Dirac distribution
 * y(x) = scale / (exp((x - mu)/kT) + 1) + C
 */
public class FermiGauss extends AFunction implements Serializable{
	private static final int NUMBER_OF_PARAMETERS = 6;

	private static final Logger LOGGER = LoggerFactory.getLogger(FermiGauss.class);
	static final double K2EV_CONVERSION_FACTOR = 8.6173324e-5; // Boltzmann constant in eV/K

	private static final String NAME = "Fermi * Gaussian";
	private static final String DESC = "y(x) = (scale / (exp((x - mu)/kT) + 1) + C) * exp(-((x)^2)/(2*sigma^2))";
	private static final String[] PARAM_NAMES = new String[]{"mu", "temperature", "BG_slope", "FE_step_height", "Constant", "FWHM"};
	private static final double[] PARAMS = new double[] { 0, 0, 0, 0, 0, 0 };
	
	private double mu, kT, scaleM, scaleC, offset, temperature, fwhm;

	public FermiGauss() {
		this(PARAMS);
	}

	public FermiGauss(double... params) {
		super(params);

		setNames();
	}

	public FermiGauss(IParameter... params) {
		super(params);

		setNames();
	}

	/**
	 * Construction which allows setting of all the bounds
	 * @param minMu
	 * @param maxMu
	 * @param minkT
	 * @param maxkT
	 * @param minScaleM
	 * @param maxScaleM
	 * @param minScaleC
	 * @param maxScaleC
	 * @param minC
	 * @param maxC
	 * @param minFWHM
	 * @param maxFWHM
	 */
	public FermiGauss(double minMu, double maxMu, double minkT, double maxkT,
					double minScaleM, double maxScaleM, double minScaleC, double maxScaleC,
					double minC, double maxC, double minFWHM, double maxFWHM) {

		super(NUMBER_OF_PARAMETERS);

		IParameter p;
		p = getParameter(0);
		p.setLimits(minMu, maxMu);
		p.setValue((minMu + maxMu) / 2.0);

		p = getParameter(1);
		p.setLimits(minkT, maxkT);
		p.setValue((minkT + maxkT) / 2.0);

		p = getParameter(2);
		p.setLimits(minScaleM, maxScaleM);
		p.setValue((minScaleM + maxScaleM) / 2.0);

		p = getParameter(3);
		p.setLimits(minScaleC, maxScaleC);
		p.setValue((minScaleC + maxScaleC) / 2.0);

		p = getParameter(4);
		p.setLimits(minC, maxC);
		p.setValue((minC + maxC) / 2.0);

		p = getParameter(5);
		p.setLimits(minFWHM, maxFWHM);
		p.setValue((minFWHM + maxFWHM) / 2.0);

		setNames();
	}

	private void setNames() {
		name = NAME;
		description = DESC;
		for (int i = 0; i < PARAM_NAMES.length; i++) {
			IParameter p = getParameter(i);
			p.setName(PARAM_NAMES[i]);
		}
	}

	private void calcCachedParameters() {
		mu = getParameterValue(0);
		temperature = getParameterValue(1);
		scaleM = getParameterValue(2);
		scaleC = getParameterValue(3);
		offset = getParameterValue(4);
		fwhm = getParameterValue(5);
		kT = k2eV(temperature);

		setDirty(false);
	}

	@Override
	public double val(double... values)  {
		if (isDirty()) {
			calcCachedParameters();
		}

		Dataset fermiDS = getFermiDS(new DoubleDataset(values, new int[] {values.length}));
		return fermiDS.getDouble(0);
	}

	@Override
	public void fillWithValues(DoubleDataset data, CoordinatesIterator it) {
		if (isDirty()) {
			calcCachedParameters();
		}

		IDataset xAxis = it.getValues()[0];

		Dataset fermiDS = getFermiDS(xAxis);

		if (fwhm == 0.0) {
			data.setSlice(fermiDS);
			return;
		}

		Gaussian gauss = new Gaussian((double)xAxis.mean(), fwhm, 1.0);
		DoubleDataset gaussDS = gauss.calculateValues(xAxis);
		gaussDS.idivide(gaussDS.sum());
		int length = fermiDS.getShapeRef()[0];
		DoubleDataset s1 = DoubleDataset.ones(length*2-1);
		s1.setSlice(fermiDS.getDouble(0), new int[] {0}, new int[] {length/2}, new int[] {1});
		s1.setSlice(fermiDS, new int[] {length/2}, new int[] {length*3/2}, new int[] {1});
		s1.setSlice(fermiDS.getDouble(length-1), new int[] {length*3/2}, new int[] {length*2-1}, new int[] {1});

		data.setSlice(Signal.convolveForOverlap(s1, gaussDS, null));
	}

	public Dataset getFermiDS(IDataset xAxis) {
		if (isDirty()) {
			calcCachedParameters();
		}

		Fermi fermi = new Fermi(mu, kT, 1.0, 0.0);
		StraightLine sl = new StraightLine(new double[] {scaleM, scaleC});
		Dataset fermiDS = fermi.calculateValues(xAxis);
		DoubleDataset slDS = sl.calculateValues(Maths.subtract(xAxis,mu));
		fermiDS.imultiply(slDS);
		fermiDS.iadd(offset);
		return fermiDS;
	}

	/**
	 * Method to approximate a Gaussian FWHM from an apparent temperature,
	 * @param realTemperaure the real temperature the sample is at
	 * @return the width of the Fermi edge which needs to be considered for fitting
	 */
	public double approximateFWHM(double realTemperaure) {
		IParameter temp = getParameter(1);
		IParameter width = getParameter(5);
		if (temp.getValue() < realTemperaure) {
			LOGGER.warn("Fitted temperature was below the real temperature");
			temp.setValue(realTemperaure);
			width.setValue(0.0);
			return k2eV(realTemperaure)*8;
		}
		
		double fitEnergy = k2eV(temp.getValue());
		double realEnergy = k2eV(realTemperaure);
		double fwhm = fitEnergy*fitEnergy - realEnergy*realEnergy;
		fwhm = Math.sqrt(fwhm);
		if (temp.isFixed()) {
			temp.setFixed(false);
			temp.setValue(realTemperaure);
			temp.setFixed(true);
		} else {
			temp.setValue(realTemperaure);
		}
		
		if (width.isFixed()) {
			width.setFixed(false);
			width.setValue(fwhm);
			width.setFixed(true);
		} else {
			width.setValue(fwhm);
		}

		return fitEnergy*6;
	}

	private double k2eV(double temperature) {
		return temperature*K2EV_CONVERSION_FACTOR;
	}	
}

/*-
 * Copyright (c) 2012-2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import java.io.Serializable;

import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
import org.eclipse.dawnsci.analysis.dataset.impl.Signal;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for a convolution of the Fermi function and a Gaussian
 */
public class FermiGauss extends AFunction implements Serializable {
	private static final int NUMBER_OF_PARAMETERS = 6;

	private static final Logger LOGGER = LoggerFactory.getLogger(FermiGauss.class);
	static final double K2EV_CONVERSION_FACTOR = 8.6173324e-5; // Boltzmann constant in eV/K

	private static final String NAME = "Fermi-Gaussian";
	private static final String DESC = "A Fermi function that is also multiplied by a slope then convolved with a Gaussian."
			+ "\n    y(x) = Convolve((DOSheight * (x - mu) + DOSheight) * Fermi(x; mu, kT, 1, 0) + BKheight, Gaussian(x; 0, fwhm, 1)]";
	private static final String[] PARAM_NAMES = new String[]{"mu", "T", "DOSslope", "DOSheight", "BKheight", "fwhm"};
	private static final double[] PARAMS = new double[] { 0, 0, 0, 0, 0, 0 };
	
	private transient double mu, kT, dosSlope, dosheight, offset, temperature, fwhm;

	public FermiGauss() {
		this(PARAMS);
	}

	public FermiGauss(double... params) {
		super(params);
	}

	public FermiGauss(IParameter... params) {
		super(params);
	}

	/**
	 * Construction which allows setting of all the bounds
	 * @param minMu
	 * @param maxMu
	 * @param minkT
	 * @param maxkT
	 * @param minDOSslope
	 * @param maxDOSslope
	 * @param minDOSheight
	 * @param maxDOSheight
	 * @param minBKheight
	 * @param maxBKheight
	 * @param minFWHM
	 * @param maxFWHM
	 */
	public FermiGauss(double minMu, double maxMu, double minkT, double maxkT,
					double minDOSslope, double maxDOSslope, double minDOSheight, double maxDOSheight,
					double minBKheight, double maxBKheight, double minFWHM, double maxFWHM) {

		super(NUMBER_OF_PARAMETERS);

		IParameter p;
		p = getParameter(0);
		p.setLimits(minMu, maxMu);
		p.setValue((minMu + maxMu) / 2.0);

		p = getParameter(1);
		p.setLimits(minkT, maxkT);
		p.setValue((minkT + maxkT) / 2.0);

		p = getParameter(2);
		p.setLimits(minDOSslope, maxDOSslope);
		p.setValue((minDOSslope + maxDOSslope) / 2.0);

		p = getParameter(3);
		p.setLimits(minDOSheight, maxDOSheight);
		p.setValue((minDOSheight + maxDOSheight) / 2.0);

		p = getParameter(4);
		p.setLimits(minBKheight, maxBKheight);
		p.setValue((minBKheight + maxBKheight) / 2.0);

		p = getParameter(5);
		p.setLimits(minFWHM, maxFWHM);
		p.setValue((minFWHM + maxFWHM) / 2.0);
	}

	@Override
	protected void setNames() {
		setNames(NAME, DESC, PARAM_NAMES);
	}

	private void calcCachedParameters() {
		mu = getParameterValue(0);
		temperature = getParameterValue(1);
		dosSlope = getParameterValue(2);
		dosheight = getParameterValue(3);
		offset = getParameterValue(4);
		fwhm = getParameterValue(5);
		kT = temp2eV(temperature);

		setDirty(false);
	}

	private final static int HALF_LENGTH = 4;
	private final static double FACTOR = 1./4096;

	@Override
	public double val(double... values)  {
		if (isDirty()) {
			calcCachedParameters();
		}

		if (values.length != 1) {
			throw new IllegalArgumentException("A single value is expected and required");
		}

		double magnitude = Math.abs(values[0]);
		if (magnitude == 0) {
			magnitude = fwhm == 0 ? 1 : fwhm;
		}
		magnitude *= FACTOR;
		DoubleDataset coords = DatasetFactory.createRange(DoubleDataset.class, -HALF_LENGTH, HALF_LENGTH, 1);
		coords.imultiply(magnitude);
		coords.iadd(values[0]);

		Dataset fermiDS = calculateValues(coords);
		return fermiDS.getDouble(HALF_LENGTH);
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
		DoubleDataset s1 = DatasetFactory.ones(DoubleDataset.class, length*2-1);
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
		StraightLine sl = new StraightLine(new double[] {dosSlope, dosheight});
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
			return temp2eV(realTemperaure)*8;
		}
		
		double fitEnergy = temp2eV(temp.getValue());
		double realEnergy = temp2eV(realTemperaure);
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

	private double temp2eV(double temperature) {
		return temperature*K2EV_CONVERSION_FACTOR;
	}
}

/*-
 * Copyright (c) 2021 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
import org.eclipse.january.dataset.DoubleDataset;

/**
 * Class that wrappers the Planck black body radiation function
 */
public class Planck extends AFunction {

	private static final long serialVersionUID = -7945827933586334039L;
	private static final String NAME = "Planck";
	private static final String DESC = "The Planck grey body radiation function (spectral exitance) from a non-ideal surface"
			+ "\n    M_e(λ) = (emissivity / λ^5) (2 pi h c^2 / (exp(h c / k λ temperature) - 1) )"
			+ "\n    dependent axis is spectral exitance in wavelength, independent is wavelength in m"
			+ "\n    The resulting temperature in Kelvin, assumes grey body approximation"
			+ "\n    see equation 2 in https://doi.org/10.1080/08957950412331331718";
	private static final String[] PARAM_NAMES = new String[]{"emissivity", "temperature"};
    public static final double c = 299792458.; // speed of light; m / s
    public static final double h = 6.62607015e-34; // Planck constant; m**2 kg / s
    public static final double kB = 1.380649e-23; // Boltzmann constant; m**2 kg s**-2 K**-1
    private static final double twoPiHCSquared = 2 * Math.PI * h * c * c;
    private static final double HCOverkB = h * c / kB;
	/**
	 * Basic constructor, not advisable to use
	 */
	public Planck() {
		super(2);
	}

	public Planck(IParameter... params) {
		super(params);
	}

	/**
	 * Constructor that allows for the positioning of all the parameter bounds
	 *
	 * @param minEmissivity
	 *            minimum boundary for the E parameter
	 * @param maxEmissivity
	 *            maximum boundary for the E parameter
	 * @param minTemperature
	 *            minimum boundary for the T parameter
	 * @param maxTemperature
	 *            maximum boundary for the T parameter
	 */
	public Planck(double minEmissivity, double maxEmissivity, double minTemperature, double maxTemperature) {
		super(2);

		IParameter p;
		p = getParameter(0);
		p.setLowerLimit(minEmissivity);
		p.setUpperLimit(maxEmissivity);
		p.setValue((minEmissivity + maxEmissivity) / 2.0);

		p = getParameter(1);
		p.setLowerLimit(minTemperature);
		p.setUpperLimit(maxTemperature);
		p.setValue((minTemperature + maxTemperature) / 2.0);

	}

	/**
	 * A very simple constructor which just specifies the values, not the bounds
	 * @param params
	 */
	public Planck(double[] params) {
		super(params);
	}

	@Override
	public int getNoOfParameters() {
		return PARAM_NAMES.length;
	}

	@Override
	protected void setNames() {
		setNames(NAME, DESC, PARAM_NAMES);
	}

	private transient double emiss, temperature, scaledEmissivity, hcoverkbT;
	private void calcCachedParameters() {
		emiss = getParameterValue(0);
		temperature = getParameterValue(1);
		scaledEmissivity = twoPiHCSquared * emiss;
		hcoverkbT = HCOverkB / temperature;

		setDirty(false);
	}

	@Override
	public double val(double... values) {
		if (isDirty())
			calcCachedParameters();

		double lambda = values[0];
		double lambdaSq = lambda * lambda;
		double denominator = lambdaSq * lambdaSq * lambda * Math.expm1(hcoverkbT / lambda);
		return scaledEmissivity / denominator;
	}

	@Override
	public void fillWithValues(DoubleDataset data, CoordinatesIterator it) {
		if (isDirty())
			calcCachedParameters();

		it.reset();
		double[] coords = it.getCoordinates();
		int i = 0;
		double[] buffer = data.getData();
		while (it.hasNext()) {
			double lambda = coords[0];
			double lambdaSq = lambda * lambda;
			double denominator = lambdaSq * lambdaSq * lambda * Math.expm1(hcoverkbT / lambda);
			buffer[i++] = scaledEmissivity / denominator;
		}
	}
}

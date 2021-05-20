/*-
 * Copyright (c) 2015 Diamond Light Source Ltd.
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
 * Class to provide a Fano resonance profile
 */
public class Fano extends AFunction {
	private static final long serialVersionUID = -8140827833009417130L;

	private static final String NAME = "Fano";
	private static final String DESC = "A Fano resonance profile."
			+ "\n    y(x) = sigma [ q w / 2 + (x-r) ]^2 / [ w^2/4 + (x-r)^2 ]"
			+ "\nwhere r is resonant energy, the phase of the scattering amplitude"
			+ "\nchanges by pi within 2*w of the resonant energy,"
			+ "\nsigma is the resonant cross-section,"
			+ "\nand q is the Fano parameter that measure the ratio of resonant scattering"
			+ "\nto direct (background) scattering.";
	private static final String[] PARAM_NAMES = new String[] {"r", "w", "sigma", "q"};
	private static final double[] PARAMS = new double[] {0, 1, 2, 1};

	public Fano() {
		this(PARAMS);
	}

	/**
	 * Constructor which takes the four parameters required, which are
	 * 
	 * <pre>
	 *     r - resonant energy
	 *     w - line width
	 *     sigma - cross-section
	 *     q - Fano parameter
	 * </pre>
	 * 
	 * @param params
	 */
	public Fano(double... params) {
		super(PARAMS.length);
		if (params.length != PARAMS.length) {
			throw new IllegalArgumentException("A Fano profile requires 4 parameters, and it has been given " + params.length);
		}

		setParameterValues(params);
	}

	public Fano(IParameter... params) {
		super(PARAMS.length);
		if (params.length != PARAMS.length) {
			throw new IllegalArgumentException("A Fano profile requires 4 parameters, and it has been given " + params.length);
		}

		setParameters(params);
	}

	@Override
	public int getNoOfParameters() {
		return PARAMS.length;
	}

	@Override
	protected void setNames() {
		setNames(NAME, DESC, PARAM_NAMES);
	}

	private transient double r, d, sigma, q;

	protected void calcCachedParameters() {
		r = getParameterValue(0);
		d = getParameterValue(1) / 2.;
		sigma = getParameterValue(2);
		q = getParameterValue(3);

		setDirty(false);
	}

	@Override
	public double val(double... values) {
		if (isDirty())
			calcCachedParameters();

		double arg = (values[0] - r) / d; 
		double num  = q + arg;
		return sigma * num * num /(1 + arg * arg);
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
			double arg = (coords[0] - r) / d; 

			double num  = q + arg;
			buffer[i++] = sigma * num * num /(1 + arg * arg);
		}
	}
}

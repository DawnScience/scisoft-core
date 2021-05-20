/*-
 * Copyright (c) 2018 Diamond Light Source Ltd.
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
 * A differentiated Gaussian function.
 * <p>
 * <b>Note</b> this is not the derivative of {@link Gaussian} as the second and third
 * parameters have different definitions
 */
public class DiffGaussian extends AFunction {
	private static final long serialVersionUID = -2611860773626103521L;

	private static final String NAME = "DiffGaussian";
	private static final String DESC = "Derivative of Gaussian wrt to x"
			+ "\n    g(x) = a exp( -b * (x-posn)^2 )"
			+ "\nso  y(x) = -2*a*b*(x-pos)*exp(-b*(x-pos)^2)";
	private static final String[] PARAM_NAMES = new String[] {"pos", "a", "b"};

	public DiffGaussian() {
		super(PARAM_NAMES.length);
		getParameter(1).setLowerLimit(0);
		getParameter(2).setLowerLimit(0);
	}

	@Override
	public int getNoOfParameters() {
		return PARAM_NAMES.length;
	}

	@Override
	protected void setNames() {
		setNames(NAME, DESC, PARAM_NAMES);
	}

	private double pos;
	private double a;
	private double nb;
	private double f;
	public void calcCachedParameters() {
		pos = getParameterValue(0);
		a = getParameterValue(1);
		nb = - getParameterValue(2);
		f = 2 * a * nb;
		// g'(x) = f*(x-pos)*exp(nb*(x-pos)^2)
	}

	@Override
	public double val(double... values) {
		if (isDirty()) {
			calcCachedParameters();
		}

		double dx = values[0] - pos;
		return f * dx * Math.exp(nb * dx * dx);
	}

	@Override
	public void fillWithValues(DoubleDataset data, CoordinatesIterator it) {
		if (isDirty()) {
			calcCachedParameters();
		}

		it.reset();
		double[] coords = it.getCoordinates();
		int i = 0;
		double[] buffer = data.getData();
		while (it.hasNext()) {
			double dx = coords[0] - pos;

			buffer[i++] = f * dx * Math.exp(nb * dx * dx);
		}
	}

	@Override
	public double partialDeriv(IParameter parameter, double... position) {
		if (isDirty()) {
			calcCachedParameters();
		}

		if (isDuplicated(parameter)) {
			return super.partialDeriv(parameter, position);
		}

		int i = indexOfParameter(parameter);

		// g'(x) = -2*a*b*(x-pos)*exp(-b*(x-pos)^2)
		// d g' / d pos = 2*a*b*[1 - b*(x-pos)^2]*exp(-b*(x-pos)^2)
		// d g' / d a = -2*b*(x-pos)*exp(-b*(x-pos)^2)
		// d g' / d b = -2*a*(x-pos)*[1 - b*(x-pos)^2]*exp(-b*(x-pos)^2)
		final double dx = position[0] - pos;
		final double arg = nb * dx * dx;

		switch (i) {
		case 0:
			return -f * (1 - arg) * Math.exp(arg);
		case 1:
			return 2* nb * dx * Math.exp(arg);
		case 2:
			return -2 * a * dx * (1 - arg) * Math.exp(arg);
		default:
			return 0;
		}
	}

	@Override
	public void fillWithPartialDerivativeValues(IParameter parameter, DoubleDataset data, CoordinatesIterator it) {
		if (isDirty()) {
			calcCachedParameters();
		}

		if (isDuplicated(parameter)) {
			super.fillWithPartialDerivativeValues(parameter, data, it);
			return;
		}

		int j = indexOfParameter(parameter);

		it.reset();
		double[] coords = it.getCoordinates();
		int i = 0;
		double[] buffer = data.getData();
		switch (j) {
		case 0:
			while (it.hasNext()) {
				final double dx = coords[0] - pos;
				final double arg = nb * dx * dx;
				buffer[i++] = -f * (1 - arg) * Math.exp(arg);
			}
			break;
		case 1:
			while (it.hasNext()) {
				final double dx = coords[0] - pos;
				buffer[i++] = 2* nb * dx * Math.exp(nb * dx * dx);
			}
			break;
		case 2:
			while (it.hasNext()) {
				final double dx = coords[0] - pos;
				final double arg = nb * dx * dx;
				buffer[i++] = -2 * a * dx * (1 - arg) * Math.exp(arg);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * @return corresponding Gaussian
	 */
	public Gaussian getGaussian() {
		double b = getParameterValue(2);
		return new Gaussian(getParameterValue(0), 2*Math.sqrt(Math.log(2)/b), getParameterValue(1)*Math.sqrt(Math.PI/b));
	}
}

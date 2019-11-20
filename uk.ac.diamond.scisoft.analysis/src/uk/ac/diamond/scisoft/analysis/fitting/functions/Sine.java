/*-
 * Copyright 2019 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import org.eclipse.january.dataset.DoubleDataset;

public class Sine extends AFunction {
	private static final String FUNCTION_NAME = "Sine";
	private static final String DESC = "Sine function: amplitude * sin(angular_frequency * x + phase) + DC_offset";
	private static final String[] PARAM_NAMES = { "amplitude", "angular_frequency", "phase", "DC_offset" };
	private static final double[] DEFAULT_PARAMS = new double[] { 1.0, 1.0, 0.0, 0.0 };

	// Cached copies of most recent parameter values
	private transient double amplitude;
	private transient double angularFrequency;
	private transient double phase;
	private transient double offset;

	public Sine() {
		super(DEFAULT_PARAMS);
	}

	public Sine(double[] params) {
		super(params);
		if (params.length != 4) {
			throw new IllegalArgumentException("Constructor requires 4 parameters");
		}
	}

	@Override
	public double val(double... values) {
		if (isDirty()) {
			cacheParams();
		}
		return calculateResult(values[0]);
	}

	@Override
	protected void setNames() {
		setNames(FUNCTION_NAME, DESC, PARAM_NAMES);
	}

	@Override
	public void fillWithValues(DoubleDataset data, CoordinatesIterator it) {
		if (isDirty()) {
			cacheParams();
		}
		it.reset();
		final double[] coords = it.getCoordinates();
		final double[] buffer = data.getData();
		int i = 0;
		while (it.hasNext()) {
			buffer[i++] = calculateResult(coords[0]);
		}
	}

	/**
	 * Calculate the result value based on the cached parameters
	 * <p>
	 * NB. You must ensure that the parameters are up to date before calling this function: call {@link #cacheParams()}
	 * if necessary.
	 *
	 * @param inputVal
	 *            The "x" value
	 * @return result of the sine formula calculation
	 */
	private double calculateResult(double inputVal) {
		return amplitude * Math.sin(angularFrequency * inputVal + phase) + offset;
	}

	private void cacheParams() {
		amplitude = getParameterValue(0);
		angularFrequency = getParameterValue(1);
		phase = getParameterValue(2);
		offset = getParameterValue(3);

		setDirty(false);
	}
}

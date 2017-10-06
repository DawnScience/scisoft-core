/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import org.apache.commons.math3.distribution.PoissonDistribution;
import org.eclipse.january.dataset.DoubleDataset;

/**
 * Class which expands on the AFunction class to give the properties of a Poisson distribution.
 */
public class Poisson extends AFunction {
	private static final String NAME = "Poisson";
	private static final String DESC = "A Poisson distribution."
			+ "\n    y(x) = A exp(-posn) posn^x / x!"
			+ "\nwhere area = A.";
	
	private static final double[] PARAMS = new double[] { 1, 1 };
	private static final String[] PARAM_NAMES = new String[] { "posn", "area" };

	private transient PoissonDistribution poisson = null; 
	private transient double fr;

	/**
	 * Create a Poisson distribution function
	 */
	public Poisson() {
		super(PARAMS);
		getParameter(0).setLowerLimit(0);
	}

	@Override
	public double val(double... values) {
		if (isDirty()) {
			calcCachedParameters();
		}

		return fr * poisson.probability((int) Math.floor(values[0]));
	}

	private void calcCachedParameters() {
		poisson = new PoissonDistribution(getParameterValue(0));
		fr = getParameterValue(1);
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
			buffer[i++] = fr * poisson.probability((int) Math.floor(coords[0]));
		}
	}

	@Override
	protected void setNames() {
		setNames(NAME, DESC, PARAM_NAMES);
	}
}

/*-
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.Maths;


/**
 * Class that wrappers the function y(x) = ax^2 + bx + c
 */
public class Quadratic extends AFunction {
	private static final String NAME = "Quadratic";
	private static final String DESC = "A quadratic function or parabola."
			+ "\n    y(x) = a*x^2 + b*x + c";
	private static final String[] PARAM_NAMES = new String[]{"a", "b", "c"};

	/**
	 * Basic constructor, not advisable to use
	 */
	public Quadratic() {
		super(3);
	}

	public Quadratic(IParameter... params) {
		super(params);
	}

	/**
	 * Constructor that allows for the positioning of all the parameter bounds
	 * 
	 * @param minA
	 *            minimum boundary for the A parameter
	 * @param maxA
	 *            maximum boundary for the A parameter
	 * @param minB
	 *            minimum boundary for the B parameter
	 * @param maxB
	 *            maximum boundary for the B parameter
	 * @param minC
	 *            minimum boundary for the C parameter
	 * @param maxC
	 *            maximum boundary for the C parameter
	 */
	public Quadratic(double minA, double maxA, double minB, double maxB, double minC, double maxC) {
		super(3);

		IParameter p;
		p = getParameter(0);
		p.setLowerLimit(minA);
		p.setUpperLimit(maxA);
		p.setValue((minA + maxA) / 2.0);

		p = getParameter(1);
		p.setLowerLimit(minB);
		p.setUpperLimit(maxB);
		p.setValue((minB + maxB) / 2.0);

		p = getParameter(2);
		p.setLowerLimit(minC);
		p.setUpperLimit(maxC);
		p.setValue((minC + maxC) / 2.0);
	}
	
	/**
	 * A very simple constructor which just specifies the values, not the bounds
	 * @param params
	 */
	public Quadratic(double[] params) {
		super(params);
	}

	@Override
	protected void setNames() {
		setNames(NAME, DESC, PARAM_NAMES);
	}

	private transient double a, b, c;
	private void calcCachedParameters() {
		a = getParameterValue(0);
		b = getParameterValue(1);
		c = getParameterValue(2);

		setDirty(false);
	}

	@Override
	public double val(double... values) {
		if (isDirty())
			calcCachedParameters();

		double position = values[0];
		return a * position * position + b * position + c;
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
			double p = coords[0];
			buffer[i++] = a * p * p + b * p + c;
		}
	}

	@Override
	public double partialDeriv(IParameter parameter, double... position) {
		if (isDuplicated(parameter))
			return super.partialDeriv(parameter, position);

		int i = indexOfParameter(parameter);
		final double pos = position[0];
		switch (i) {
		case 0:
			return pos * pos;
		case 1:
			return pos;
		case 2:
			return 1.0;
		default:
			return 0;
		}
	}

	@Override
	public void fillWithPartialDerivativeValues(IParameter parameter, DoubleDataset data, CoordinatesIterator it) {
		int i = indexOfParameter(parameter);

		Dataset pos = DatasetUtils.convertToDataset(it.getValues()[0]);
		switch (i) {
		case 0:
			Maths.square(pos, data);
			break;
		case 1:
			data.setSlice(pos);
			break;
		case 2:
			data.fill(1);
			break;
		default:
			break;
		}
	}

}

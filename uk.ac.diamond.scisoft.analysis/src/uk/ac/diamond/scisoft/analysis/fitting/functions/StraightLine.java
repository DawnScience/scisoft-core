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
import org.eclipse.january.dataset.DoubleDataset;


/**
 * This class implements the function y(x) = m*x + c
 */
public class StraightLine extends AFunction {
	private static final long serialVersionUID = -5292924983249690869L;

	private static final String NAME = "Linear";
	private static final String DESC = "A linear function or straight line."
			+ "\n    y(x) = m*x + c";
	private static final String[] PARAM_NAMES = new String[]{"m", "c"};

	/**
	 * Basic constructor, not advisable to use.
	 */
	public StraightLine() {
		super(PARAM_NAMES.length);
	}

	public StraightLine(double[] params) {
		super(params);
	}

	public StraightLine(IParameter... params) {
		super(params);
	}

	/**
	 * Constructor allowing for careful construction of bounds
	 * 
	 * @param minM
	 *            Minimum value allowed for m
	 * @param maxM
	 *            Maximum value allowed for m
	 * @param minC
	 *            Minimum value allowed for c
	 * @param maxC
	 *            Maximum value allowed for c
	 */
	public StraightLine(double minM, double maxM, double minC, double maxC) {
		super(PARAM_NAMES.length);

		IParameter p;
		p = getParameter(0);
		p.setLowerLimit(minM);
		p.setUpperLimit(maxM);
		p.setValue((minM + maxM) / 2.0);

		p = getParameter(1);
		p.setLowerLimit(minC);
		p.setUpperLimit(maxC);
		p.setValue((minC + maxC) / 2.0);
	}

	@Override
	public int getNoOfParameters() {
		return PARAM_NAMES.length;
	}

	@Override
	protected void setNames() {
		setNames(NAME, DESC, PARAM_NAMES);
	}

	private transient double a, b;
	private void calcCachedParameters() {
		a = getParameterValue(0);
		b = getParameterValue(1);

		setDirty(false);
	}

	@Override
	public double val(double... values) {
		if (isDirty())
			calcCachedParameters();

		double position = values[0];
		return a * position + b;
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
			buffer[i++] = a * coords[0] + b;
		}
	}

	@Override
	public double partialDeriv(IParameter parameter, double... position) {
		if (isDuplicated(parameter))
			return super.partialDeriv(parameter, position);

		int i = indexOfParameter(parameter);
		switch (i) {
		case 0:
			return position[0];
		case 1:
			return 1.0;
		default:
			return 0;
		}
	}

	@Override
	public void fillWithPartialDerivativeValues(IParameter parameter, DoubleDataset data, CoordinatesIterator it) {
		int i = indexOfParameter(parameter);
		switch (i) {
		case 0:
			data.setSlice(it.getValues()[0]);
			break;
		case 1:
			data.fill(1);
			break;
		default:
			break;
		}
	}
}

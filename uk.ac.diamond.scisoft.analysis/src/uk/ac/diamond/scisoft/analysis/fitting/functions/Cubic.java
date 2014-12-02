/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;


/**
 * Class that wrappers the equation <br>
 * y(x) = ax^3 + bx^2 + cx + d
 */
public class Cubic extends AFunction {
	private static final String NAME = "Cubic";
	private static final String DESC = "y(x) = ax^3 + bx^2 + cx + d";
	private static final String[] PARAM_NAMES = new String[]{"A", "B", "C", "D"};

	/**
	 * Basic constructor, not advisable to use
	 */
	public Cubic() {
		super(4);

		setNames();
	}

	public Cubic(double[] params) {
		super(params);

		setNames();
	}

	public Cubic(IParameter... params) {
		super(params);

		setNames();
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
	 * @param minD
	 *            minimum boundary for the D parameter
	 * @param maxD
	 *            maximum boundary for the D parameter
	 */
	public Cubic(double minA, double maxA, double minB, double maxB, double minC, double maxC, double minD, double maxD) {
		super(4);

		getParameter(0).setLimits(minA,maxA);
		getParameter(0).setValue((minA + maxA) / 2.0);

		getParameter(1).setLimits(minB,maxB);
		getParameter(1).setValue((minB + maxB) / 2.0);

		getParameter(2).setLimits(minC,maxC);
		getParameter(2).setValue((minC + maxC) / 2.0);

		getParameter(3).setLimits(minD,maxD);
		getParameter(3).setValue((minD + maxD) / 2.0);

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

	double a, b, c, d;
	private void calcCachedParameters() {
		a = getParameterValue(0);
		b = getParameterValue(1);
		c = getParameterValue(2);
		d = getParameterValue(3);

		setDirty(false);
	}

	@Override
	public double val(double... values) {
		if (isDirty())
			calcCachedParameters();

		double position = values[0];
		return a * position * position * position + b * position * position + c * position + d;
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
			buffer[i++] = a * p * p * p + b * p * p + c * p + d;
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
			return pos * pos * pos;
		case 1:
			return pos * pos;
		case 2:
			return pos;
		case 3:
			return 1.0;
		default:
			return 0;
		}
	}

	@Override
	public void fillWithPartialDerivativeValues(IParameter parameter, DoubleDataset data, CoordinatesIterator it) {
		Dataset pos = DatasetUtils.convertToDataset(it.getValues()[0]);

		int i = indexOfParameter(parameter);
		switch (i) {
		case 0:
			Maths.power(pos, 3, data);
			break;
		case 1:
			Maths.square(pos, data);
			break;
		case 2:
			data.setSlice(pos);
			break;
		case 3:
			data.fill(1);
			break;
		default:
			break;
		}
	}

	public double getA() {
		return a;
	}

	public void setA(double a) {
		this.a = a;
	}

	public double getB() {
		return b;
	}

	public void setB(double b) {
		this.b = b;
	}

	public double getC() {
		return c;
	}

	public void setC(double c) {
		this.c = c;
	}

	public double getD() {
		return d;
	}

	public void setD(double d) {
		this.d = d;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(a);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(b);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(c);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(d);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cubic other = (Cubic) obj;
		if (Double.doubleToLongBits(a) != Double.doubleToLongBits(other.a))
			return false;
		if (Double.doubleToLongBits(b) != Double.doubleToLongBits(other.b))
			return false;
		if (Double.doubleToLongBits(c) != Double.doubleToLongBits(other.c))
			return false;
		if (Double.doubleToLongBits(d) != Double.doubleToLongBits(other.d))
			return false;
		return true;
	}
}

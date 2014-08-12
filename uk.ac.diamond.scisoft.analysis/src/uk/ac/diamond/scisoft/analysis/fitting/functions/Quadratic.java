/*-
 * Copyright 2011 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Maths;


/**
 * Class that wrappers the function y(x) = ax^2 + bx + c
 */
public class Quadratic extends AFunction {
	private static final String NAME = "Quadratic";
	private static final String DESC = "y(x) = ax^2 + bx + c";
	private static final String[] PARAM_NAMES = new String[]{"A", "B", "C"};

	/**
	 * Basic constructor, not advisable to use
	 */
	public Quadratic() {
		super(3);

		setNames();
	}

	public Quadratic(IParameter... params) {
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

		setNames();
	}
	
	/**
	 * A very simple constructor which just specifies the values, not the bounds
	 * @param Params
	 */
	public Quadratic(double[] Params) {
		super(Params);

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

	double a, b, c;
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

		AbstractDataset pos = DatasetUtils.convertToAbstractDataset(it.getValues()[0]);
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

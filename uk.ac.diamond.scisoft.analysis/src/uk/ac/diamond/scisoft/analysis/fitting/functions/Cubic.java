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
		AbstractDataset pos = DatasetUtils.convertToAbstractDataset(it.getValues()[0]);

		int i = indexOfParameter(parameter);
		switch (i) {
		case 0:
			data.fill(Maths.power(pos, 3));
			break;
		case 1:
			data.fill(Maths.square(pos));
			break;
		case 2:
			data.fill(pos);
			break;
		case 3:
			data.fill(1);
			break;
		default:
			break;
		}
	}
}

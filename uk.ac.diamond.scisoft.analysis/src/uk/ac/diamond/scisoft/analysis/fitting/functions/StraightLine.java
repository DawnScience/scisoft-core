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

import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;


/**
 * This class implements the function y(x) = m*x + c
 */
public class StraightLine extends AFunction {
	private static final String NAME = "Linear";
	private static final String DESC = "y(x) = m*x + c";
	private static final String[] PARAMETER_NAMES = new String[]{"M", "Constant"};

	/**
	 * Basic constructor, not advisable to use.
	 */
	public StraightLine() {
		super(2);

		setNames();
	}

	public StraightLine(double[] params) {
		super(params);

		setNames();
	}

	public StraightLine(IParameter... params) {
		super(params);

		setNames();
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
		super(2);

		IParameter p;
		p = getParameter(0);
		p.setLowerLimit(minM);
		p.setUpperLimit(maxM);
		p.setValue((minM + maxM) / 2.0);

		p = getParameter(1);
		p.setLowerLimit(minC);
		p.setUpperLimit(maxC);
		p.setValue((minC + maxC) / 2.0);

		setNames();
	}

	private void setNames() {
		name = NAME;
		description = DESC;
		for (int i = 0; i < PARAMETER_NAMES.length; i++) {
			IParameter p = getParameter(i);
			p.setName(PARAMETER_NAMES[i]);
		}
	}

	double a, b;
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

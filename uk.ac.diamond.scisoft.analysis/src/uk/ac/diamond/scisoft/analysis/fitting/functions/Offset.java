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
 * This class basically wraps the function y(x) = c
 */
public class Offset extends AFunction {
	private static String cname = "Offset";

	/**
	 * Constructor which simply creates the right number of parameters, but probably isn't that much good
	 */
	public Offset() {
		super(1);
		name = cname;
	}
	
	/**
	 * This constructor should always be kept just in case, very useful for automated systems
	 * @param params
	 */
	public Offset(double[] params) {
		super(params);
		name = cname;
	}

	public Offset(IParameter... params) {
		super(params);
		name = cname;
	}

	/**
	 * Constructor which allows the creator to specify the bounds of the parameters
	 * 
	 * @param minOffset
	 *            Minimum value the offset can take
	 * @param maxOffset
	 *            Maximum value the offset can take
	 */
	public Offset(double minOffset, double maxOffset) {
		super(1);

		IParameter p = parameters[0];
		p.setValue((minOffset + maxOffset) / 2.0);
		p.setLowerLimit(minOffset);
		p.setUpperLimit(maxOffset);

		name = cname;
	}

	@Override
	public double val(double... values) {
		return parameters[0].getValue();
	}

	@Override
	public void fillWithValues(DoubleDataset data, CoordinatesIterator it) {
		data.fill(parameters[0].getValue());
	}

	@Override
	public double partialDeriv(IParameter parameter, double... position) {
		if (indexOfParameter(parameter) < 0)
			return 0;
		return 1;
	}

	@Override
	public void fillWithPartialDerivativeValues(IParameter parameter, DoubleDataset data, CoordinatesIterator it) {
		data.fill(1);
	}
}

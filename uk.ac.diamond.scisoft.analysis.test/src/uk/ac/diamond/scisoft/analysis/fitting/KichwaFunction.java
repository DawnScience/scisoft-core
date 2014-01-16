/*-
 * Copyright 2014 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.fitting;

import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.fitting.functions.AFunction;
import uk.ac.diamond.scisoft.analysis.fitting.functions.CoordinatesIterator;
import uk.ac.diamond.scisoft.analysis.fitting.functions.IFunction;
import uk.ac.diamond.scisoft.analysis.fitting.functions.IParameter;

public class KichwaFunction extends AFunction implements IFunction {
	private static final double[] params = new double[]{};

	/**
	 * Zero-argument constructor required for extension-point instantiation
	 */
	public KichwaFunction() {
		super(params);
	}
	
	public KichwaFunction(int numberOfParameters) {
		super(numberOfParameters);
	}

	public KichwaFunction(double... params) {
		super(params);
	}

	public KichwaFunction(IParameter... params) {
		super(params);
	}

	@Override
	public double val(double... values) {
		return 0;
	}
	
	@Override
	public String getName() {
		return "Kichwa Test Function";
	}

	@Override
	public void fillWithValues(DoubleDataset data, CoordinatesIterator it) {
	}

}

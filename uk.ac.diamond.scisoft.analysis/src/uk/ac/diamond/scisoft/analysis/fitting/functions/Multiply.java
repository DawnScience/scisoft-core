/*-
 * Copyright 2013 Diamond Light Source Ltd.
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

/**
 * Multiply several functions
 */
public class Multiply extends ANaryOperator implements IOperator {
	private static final String NAME = "Multiply";
	private static final String DESC = "Multiply several functions together";

	public Multiply() {
		super();
		name = NAME;
		description = DESC;
	}

	@Override
	public double val(double... values) {
		double y = 1;
		for (int i = 0, imax = getNoOfFunctions(); i < imax; i++) {
			IFunction f = getFunction(i);
			if (f != null) {
				y *= f.val(values);
			}
		}

		return y;
	}

	@Override
	public void fillWithValues(DoubleDataset data, CoordinatesIterator it) {
		data.fill(1);
		DoubleDataset temp = new DoubleDataset(it.getShape());
		for (int i = 0, imax = getNoOfFunctions(); i < imax; i++) {
			IFunction f = getFunction(i);
			if (f == null)
				continue;

			if (f instanceof AFunction) {
				((AFunction) f).fillWithValues(temp, it);
				data.imultiply(temp);
			} else {
				data.imultiply(DatasetUtils.convertToAbstractDataset(f.calculateValues(it.getValues())));
			}
		}
	}

	@Override
	public double partialDeriv(int index, double... values) throws IndexOutOfBoundsException {
		IParameter p = getParameter(index);
		double d = 0;
		double m = 1;

		for (int i = 0, imax = getNoOfFunctions(); i < imax; i++) {
			IFunction f = getFunction(i);
			double r = f.partialDeriv(p, values);
			double t = f.val(values);
			m *= t;
			if (r != 0) {
				d += r / t;
			}
		}

		return d * m;
	}

	@Override
	public void fillWithPartialDerivativeValues(IParameter param, DoubleDataset data, CoordinatesIterator it) {
		data.fill(1); // holds total product
		DoubleDataset val = new DoubleDataset(it.getShape());
		DoubleDataset dif = new DoubleDataset(it.getShape());
		DoubleDataset sum = new DoubleDataset(it.getShape());
		for (int i = 0, imax = getNoOfFunctions(); i < imax; i++) {
			IFunction f = getFunction(i);
			boolean hasParam = indexOfParameter(f, param) >= 0;

			if (f instanceof AFunction) {
				((AFunction) f).fillWithValues(val, it);
				data.imultiply(val);

				if (hasParam) {
					((AFunction) f).fillWithPartialDerivativeValues(param, dif, it);
					dif.idivide(val);
					sum.iadd(dif);
				}
			} else {
				AbstractDataset v = DatasetUtils.convertToAbstractDataset(f.calculateValues(it.getValues()));
				data.imultiply(v);

				if (hasParam) {
					AbstractDataset d = DatasetUtils.convertToAbstractDataset(f.calculatePartialDerivativeValues(param, it.getValues()));
	
					d.idivide(v);
					sum.iadd(d);
				}
			}
		}

		data.imultiply(sum);
	}
}

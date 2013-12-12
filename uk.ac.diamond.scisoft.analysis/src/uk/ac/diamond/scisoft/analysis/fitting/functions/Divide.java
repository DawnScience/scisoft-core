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
 * Divide two functions
 */
public class Divide extends ABinaryOperator implements IOperator {
	private static final String NAME = "Divide";
	private static final String DESC = "Divide one function by another";

	public Divide() {
		super();
		name = NAME;
		description = DESC;
	}

	@Override
	public double val(double... values) {
		
		double y = fa == null ? 0 : fa.val(values);
		y /= fb == null ? 1 : fb.val(values);

		return y;
	}


	@Override
	public void fillWithValues(DoubleDataset data, CoordinatesIterator it) {
		if (fa != null) {
			if (fa instanceof AFunction) {
				((AFunction) fa).fillWithValues(data, it);
				it.reset();
			} else {
				data.fill(DatasetUtils.convertToAbstractDataset(fa.calculateValues(it.getValues())));
			}
		}

		if (fb != null) {
			if (fb instanceof AFunction) {
				DoubleDataset temp = new DoubleDataset(it.getShape());
				((AFunction) fb).fillWithValues(temp, it);
				it.reset();
				data.idivide(temp);
			} else {
				data.idivide(DatasetUtils.convertToAbstractDataset(fb.calculateValues(it.getValues())));
			}
		}
	}

	@Override
	public double partialDeriv(int index, double... values) throws IndexOutOfBoundsException {
		IParameter p = getParameter(index);
		double d = 0;

		if (fa == null) {
			if (fb == null) {
				d = Double.NaN;
			}
		} else {
			double da = fa.partialDeriv(p, values);
			if (fb == null) {
				d = da / 0;
			} else {
				double b = fb.val(values);
				d =  da * b;
				d -= fa.val(values) * fb.partialDeriv(p, values);
				d /= b*b;
			}
		}
		return d;
	}

	@Override
	public void fillWithPartialDerivativeValues(IParameter param, DoubleDataset data, CoordinatesIterator it) {
		if (fa == null) {
			if (fb == null) {
				data.fill(Double.NaN);
			}
		} else {
			DoubleDataset value = new DoubleDataset(it.getShape());
			if (fa instanceof AFunction) {
				((AFunction) fa).fillWithValues(value, it);
				it.reset();
				if (((AFunction) fa).indexOfParameter(param) >= 0) {
					((AFunction) fa).fillWithPartialDerivativeValues(param, data, it);
					it.reset();
				}
			} else {
				value.iadd(DatasetUtils.convertToAbstractDataset(fa.calculateValues(it.getValues())));
				if (indexOfParameter(fa, param) >= 0) { 
					data.iadd(DatasetUtils.convertToAbstractDataset(fa.calculatePartialDerivativeValues(param, it.getValues())));
				}
			}

			if (fb == null) {
				data.idivide(0);
			} else {
				AbstractDataset b = DatasetUtils.convertToAbstractDataset(fb.calculateValues(it.getValues()));
				data.idivide(b);

				if (indexOfParameter(fb, param) >= 0) { 
					value.imultiply(DatasetUtils.convertToAbstractDataset(fb.calculatePartialDerivativeValues(param, it.getValues())));
					b.imultiply(b);
					value.idivide(b);
					data.isubtract(value);
				}
			}
		}
	}
}

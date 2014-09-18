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

import org.eclipse.dawnsci.analysis.api.fitting.functions.IOperator;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;

/**
 * Divide two functions (missing functions are treated as unity)
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
		double y = fa == null ? 1 : fa.val(values);
		if (fb != null) {
			y /= fb.val(values);
		}

		return y;
	}

	@Override
	public void fillWithValues(DoubleDataset data, CoordinatesIterator it) {
		if (fa != null) {
			if (fa instanceof AFunction) {
				((AFunction) fa).fillWithValues(data, it);
			} else {
				data.setSlice(DatasetUtils.convertToDataset(fa.calculateValues(it.getValues())));
			}
		} else {
			data.fill(1);
		}

		if (fb != null) {
			if (fb instanceof AFunction) {
				DoubleDataset temp = new DoubleDataset(it.getShape());
				((AFunction) fb).fillWithValues(temp, it);
				data.idivide(temp);
			} else {
				data.idivide(DatasetUtils.convertToDataset(fb.calculateValues(it.getValues())));
			}
		}
	}

	@Override
	public double partialDeriv(int index, double... values) throws IndexOutOfBoundsException {
		IParameter p = getParameter(index);
		double a;
		double d;

		if (fa != null) {
			a = fa.val(values);
			d = fa.partialDeriv(p, values);
		} else {
			a = 1;
			d = 0;
		}

		if (fb != null) {
			double b = fb.val(values);
			double db = fb.partialDeriv(p, values);
			d = (d * b - a * db) / (b * b);
		}
		return d;
	}

	@Override
	public void fillWithPartialDerivativeValues(IParameter param, DoubleDataset data, CoordinatesIterator it) {
		if (fa == null) {
			data.fill(0);
		} else {
			if (fa instanceof AFunction) {
				if (((AFunction) fa).indexOfParameter(param) >= 0) {
					((AFunction) fa).fillWithPartialDerivativeValues(param, data, it);
				}
			} else {
				if (indexOfParameter(fa, param) >= 0) { 
					data.setSlice(DatasetUtils.convertToDataset(fa.calculatePartialDerivativeValues(param, it.getValues())));
				}
			}
		}

		if (fb != null) {
			Dataset b = DatasetUtils.convertToDataset(fb.calculateValues(it.getValues()));
			data.imultiply(b);
			b.imultiply(b);
			Dataset a;
			if (fa instanceof AFunction) {
				a = new DoubleDataset(it.getShape());
				((AFunction) fa).fillWithValues((DoubleDataset) a, it);
			} else {
				a = DatasetUtils.convertToDataset(fa.calculateValues(it.getValues()));
			}

			if (indexOfParameter(fb, param) >= 0) { 
				a.imultiply(DatasetUtils.convertToDataset(fb.calculatePartialDerivativeValues(param, it.getValues())));
				data.isubtract(a);
			}
			data.idivide(b);
		}
	}
}

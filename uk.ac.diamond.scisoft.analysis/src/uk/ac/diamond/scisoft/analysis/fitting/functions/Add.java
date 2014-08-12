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

import java.io.Serializable;

import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;

/**
 * Add several functions (missing functions are treated as zero)
 */
public class Add extends ANaryOperator implements IOperator, Serializable {
	private static final String NAME = "Add";
	private static final String DESC = "Add several functions together";

	public Add() {
		super();
		name = NAME;
		description = DESC;
	}

	@Override
	public double val(double... values) {
		double y = 0;
		for (int i = 0, imax = getNoOfFunctions(); i < imax; i++) {
			IFunction f = getFunction(i);
			if (f != null) {
				y += f.val(values);
			}
		}

		return y;
	}

	@Override
	public void fillWithValues(DoubleDataset data, CoordinatesIterator it) {
		int imax = getNoOfFunctions();
		if (imax < 1) {
			data.fill(0);
			return;
		}

		IFunction f = getFunction(0);
		if (f != null) {
			if (f instanceof AFunction) {
				((AFunction) f).fillWithValues(data, it);
			} else {
				data.setSlice(DatasetUtils.convertToAbstractDataset(f.calculateValues(it.getValues())));
			}
		}

		if (imax == 1)
			return;

		DoubleDataset temp = new DoubleDataset(it.getShape());
		for (int i = 1; i < imax; i++) {
			f = getFunction(i);
			if (f == null)
				continue;

			if (f instanceof AFunction) {
				((AFunction) f).fillWithValues(temp, it);
				data.iadd(temp);
			} else {
				data.iadd(DatasetUtils.convertToAbstractDataset(f.calculateValues(it.getValues())));
			}
		}
	}

	@Override
	public double partialDeriv(IParameter param, double... values) {
		double d = 0;

		for (int i = 0, imax = getNoOfFunctions(); i < imax; i++) {
			IFunction f = getFunction(i);
			if (f != null)
				d += f.partialDeriv(param, values);
		}

		return d;
	}

	@Override
	public void fillWithPartialDerivativeValues(IParameter param, DoubleDataset data, CoordinatesIterator it) {
		int imax = getNoOfFunctions();
		if (imax < 1) {
			data.fill(0);
			return;
		}
		IFunction f = getFunction(0);
		if (f != null && indexOfParameter(f, param) >= 0) {
			if (f instanceof AFunction) {
				((AFunction) f).fillWithPartialDerivativeValues(param, data, it);
			} else {
				data.setSlice(DatasetUtils.convertToAbstractDataset(f.calculatePartialDerivativeValues(param, it.getValues())));
			}
		} else {
			data.fill(0);
		}

		if (imax == 1)
			return;

		DoubleDataset temp = new DoubleDataset(it.getShape());
		for (int i = 1; i < imax; i++) {
			f = getFunction(i);
			if (f == null || indexOfParameter(f, param) < 0)
				continue;

			if (f instanceof AFunction) {
				((AFunction) f).fillWithPartialDerivativeValues(param, temp, it);
				data.iadd(temp);
			} else {
				data.iadd(DatasetUtils.convertToAbstractDataset(f.calculatePartialDerivativeValues(param, it.getValues())));
			}
		}
	}
}

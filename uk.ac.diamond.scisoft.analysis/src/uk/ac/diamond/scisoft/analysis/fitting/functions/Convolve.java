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

import uk.ac.diamond.scisoft.analysis.dataset.CompoundDoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Signal;
import uk.ac.diamond.scisoft.analysis.dataset.Slice;

/**
 * Convolve two functions where the second function is the kernel
 */
public class Convolve extends ABinaryOperator implements IOperator {
	private static final String NAME = "Convolve";
	private static final String DESC = "Convolve one function with a kernel";

	public Convolve() {
		super();
		name = NAME;
		description = DESC;
	}

	@Override
	public double val(double... values) {
		DoubleDataset v = calculateValues(new CompoundDoubleDataset(values.length, values, 1));
		return v.getAbs(0);
	}

	@Override
	public void fillWithValues(DoubleDataset data, CoordinatesIterator it) {
		if (fa == null || fb == null)
			return;

		IDataset[] values = it.getValues();
		DoubleDataset da = (DoubleDataset) DatasetUtils.cast(fa.calculateValues(values), Dataset.FLOAT64);
		Dataset db = DatasetUtils.cast(fb.calculateValues(values), Dataset.FLOAT64);
		data.setSlice(calcConvolution(da, db));
	}

	private static Dataset calcConvolution(DoubleDataset signal, Dataset kernel) {
		int l = signal.getSize();
		DoubleDataset padded = new DoubleDataset(2*l - 1);
		int hl = l/2;
		padded.setSlice(signal.getAbs(0), new Slice(0, hl));
		padded.setSlice(signal, new Slice(hl, hl + l));
		padded.setSlice(signal.getAbs(l - 1), new Slice(hl + l, null));

		kernel.idivide(kernel.sum());
		
		return Signal.convolveForOverlap(padded, kernel, null);
	}

	@Override
	public void fillWithPartialDerivativeValues(IParameter parameter, DoubleDataset data, CoordinatesIterator it) {
		if (fa == null || fb == null)
			return;

		final IDataset[] values = it.getValues();
		DoubleDataset da = (DoubleDataset) DatasetUtils.cast(fa.calculatePartialDerivativeValues(parameter, values), Dataset.FLOAT64);
		Dataset db = DatasetUtils.cast(fb.calculateValues(values), Dataset.FLOAT64);

		data.setSlice(calcConvolution(da, db));

		da = (DoubleDataset) DatasetUtils.cast(fa.calculateValues(values), Dataset.FLOAT64);
		db = DatasetUtils.cast(fb.calculatePartialDerivativeValues(parameter, values), Dataset.FLOAT64);
		data.iadd(calcConvolution(da, db));
	}
}

/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.Slice;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IOperator;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
import org.eclipse.dawnsci.analysis.dataset.impl.CompoundDoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Signal;

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

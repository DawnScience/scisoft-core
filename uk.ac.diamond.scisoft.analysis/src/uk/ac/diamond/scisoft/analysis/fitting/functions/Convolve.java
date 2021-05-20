/*-
 * Copyright (c) 2013 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import org.eclipse.dawnsci.analysis.api.fitting.functions.IOperator;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
import org.eclipse.dawnsci.analysis.dataset.impl.Signal;
import org.eclipse.january.dataset.CompoundDoubleDataset;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Slice;

/**
 * Convolve two functions where the second function is the kernel
 */
public class Convolve extends ABinaryOperator implements IOperator {
	private static final long serialVersionUID = 4129261574134837515L;

	private static final String NAME = "Convolve";
	private static final String DESC = "Convolve one function with a kernel";

	public Convolve() {
		super();
		name = NAME;
		description = DESC;
	}

	@Override
	protected void setNames() {
		setNames(NAME, DESC);
	}

	@Override
	public double val(double... values) {
		DoubleDataset v = calculateValues(DatasetFactory.createFromObject(values.length, CompoundDoubleDataset.class, values, 1));
		return v.getAbs(0);
	}

	@Override
	public void fillWithValues(DoubleDataset data, CoordinatesIterator it) {
		if (fa == null || fb == null)
			return;

		IDataset[] values = it.getValues();
		DoubleDataset da = DatasetUtils.cast(DoubleDataset.class, fa.calculateValues(values));
		Dataset db = DatasetUtils.cast(DoubleDataset.class, fb.calculateValues(values));
		data.setSlice(calcConvolution(da, db));
	}

	private static Dataset calcConvolution(DoubleDataset signal, Dataset kernel) {
		int l = signal.getSize();
		DoubleDataset padded = DatasetFactory.zeros(DoubleDataset.class, 2*l - 1);
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
		DoubleDataset da = DatasetUtils.cast(DoubleDataset.class, fa.calculatePartialDerivativeValues(parameter, values));
		Dataset db = DatasetUtils.cast(DoubleDataset.class, fb.calculateValues(values));

		data.setSlice(calcConvolution(da, db));

		da = DatasetUtils.cast(DoubleDataset.class, fa.calculateValues(values));
		db = DatasetUtils.cast(DoubleDataset.class, fb.calculatePartialDerivativeValues(parameter, values));
		data.iadd(calcConvolution(da, db));
	}
}

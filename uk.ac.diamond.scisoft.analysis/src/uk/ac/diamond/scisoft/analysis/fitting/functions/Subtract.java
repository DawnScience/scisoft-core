/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import org.eclipse.dawnsci.analysis.api.fitting.functions.IOperator;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;

/**
 * Subtract two functions (missing functions are treated as zero)
 */
public class Subtract extends ABinaryOperator implements IOperator {
	private static final String NAME = "Subtract";
	private static final String DESC = "Subtract one function from another";

	public Subtract() {
		super();
		name = NAME;
		description = DESC;
	}


	@Override
	public double val(double... values) {
		double y = fa == null ? 0 : fa.val(values);
		if (fb != null) {
			y -= fb.val(values);
		}

		return y;
	}

	@Override
	public void fillWithValues(DoubleDataset data, CoordinatesIterator it) {
		if (fa != null) {
			if (fa instanceof AFunction) {
				((AFunction) fa).fillWithValues(data, it);
			} else {
				data.setSlice(fa.calculateValues(it.getValues()));
			}
		} else {
			data.fill(0);
		}

		if (fb != null) {
			if (fb instanceof AFunction) {
				DoubleDataset temp = new DoubleDataset(it.getShape());
				((AFunction) fb).fillWithValues(temp, it);
				data.isubtract(temp);
			} else {
				data.isubtract(fb.calculateValues(it.getValues()));
			}
		}
	}

	@Override
	public double partialDeriv(IParameter param, double... values) {
		double d = fa == null ? 0 : fa.partialDeriv(param, values);

		if (fb != null) {
			d -= fb.partialDeriv(param, values);
		}

		return d;
	}

	@Override
	public void fillWithPartialDerivativeValues(IParameter param, DoubleDataset data, CoordinatesIterator it) {
		if (fa != null && indexOfParameter(fa, param) >= 0) {
			if (fa instanceof AFunction) {
				((AFunction) fa).fillWithPartialDerivativeValues(param, data, it);
			} else {
				data.setSlice(fa.calculatePartialDerivativeValues(param, it.getValues()));
			}
		} else {
			data.fill(0);
		}

		if (fb != null && indexOfParameter(fb, param) >= 0) {
			if (fb instanceof AFunction) {
				DoubleDataset temp = new DoubleDataset(it.getShape());
				((AFunction) fb).fillWithPartialDerivativeValues(param, temp, it);
				data.isubtract(temp);
			} else {
				data.isubtract(fb.calculatePartialDerivativeValues(param, it.getValues()));
			}
		}
	}
}

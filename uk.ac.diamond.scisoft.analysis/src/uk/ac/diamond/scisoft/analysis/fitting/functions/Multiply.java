/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import org.eclipse.dawnsci.analysis.api.fitting.functions.IFunction;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IOperator;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;

/**
 * Multiply several functions (missing functions are treated as unity)
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
		int imax = getNoOfFunctions();
		if (imax < 1) {
			data.fill(1);
			return;
		}

		IFunction f = getFunction(0);
		if (f != null) {
			if (f instanceof AFunction) {
				((AFunction) f).fillWithValues(data, it);
			} else {
				data.setSlice(DatasetUtils.convertToDataset(f.calculateValues(it.getValues())));
			}
		} else {
			data.fill(1);
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
				data.imultiply(temp);
			} else {
				data.imultiply(DatasetUtils.convertToDataset(f.calculateValues(it.getValues())));
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
			if (f == null)
				continue;

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
		int imax = getNoOfFunctions();
		if (imax < 1) {
			data.fill(0);
			return;
		}

		IFunction f = getFunction(0);
		boolean hasParam = indexOfParameter(f, param) >= 0;
		if (f != null && hasParam) {
			if (f instanceof AFunction) {
				((AFunction) f).fillWithPartialDerivativeValues(param, data, it);
			} else {
				data.setSlice(DatasetUtils.convertToDataset(f.calculatePartialDerivativeValues(param, it.getValues())));
			}
		} else {
			data.fill(0);
		}
		if (imax == 1)
			return;

		DoubleDataset val = new DoubleDataset(it.getShape());
		DoubleDataset sum = new DoubleDataset(it.getShape());
		DoubleDataset dif;
		if (f != null) {
			dif = new DoubleDataset(data); // copy derivatives and now store values
			if (f instanceof AFunction) {
				((AFunction) f).fillWithValues(data, it);
			} else {
				data.setSlice(DatasetUtils.convertToDataset(f.calculateValues(it.getValues())));
			}
			if (hasParam) {
				dif.idivide(data);
				sum.iadd(dif);
			}
		} else {
			dif = new DoubleDataset(it.getShape());
			data.fill(1);
		}
		for (int i = 1; i < imax; i++) {
			f = getFunction(i);
			if (f == null)
				continue;

			hasParam = indexOfParameter(f, param) >= 0;
			if (f instanceof AFunction) {
				((AFunction) f).fillWithValues(val, it);
				data.imultiply(val);

				if (hasParam) {
					((AFunction) f).fillWithPartialDerivativeValues(param, dif, it);
					dif.idivide(val);
					sum.iadd(dif);
				}
			} else {
				Dataset v = DatasetUtils.convertToDataset(f.calculateValues(it.getValues()));
				data.imultiply(v);

				if (hasParam) {
					Dataset d = DatasetUtils.convertToDataset(f.calculatePartialDerivativeValues(param, it.getValues()));
	
					d.idivide(v);
					sum.iadd(d);
				}
			}
		}

		data.imultiply(sum);
	}
}

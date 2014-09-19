/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.fitting.functions.IFunction;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IOperator;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;

/**
 * An abstract operator
 */
abstract public class AOperator extends AFunction implements IOperator {
	protected List<IParameter> params; // unique parameters

	public AOperator() {
		super(0);
		params = new ArrayList<>();
	}

	@Override
	public void updateParameters() {
		params.clear();
		for (int i = 0, imax = getNoOfFunctions(); i < imax; i++) {
			IFunction f = getFunction(i);
			if (f == null)
				continue;
			for (int j = 0, jmax = f.getNoOfParameters(); j < jmax; j++) {
				IParameter p = f.getParameter(j);
				boolean add = true;
				for (IParameter param : params) {
					if (p == param) {
						add = false;
						break;
					}
				}
				if (add) {
					params.add(p);
				}
			}
		}
		setDirty(true);

		if (parent != null) {
			parent.updateParameters();
		}
	}

	@Override
	protected int indexOfParameter(IParameter p) {
		for (int i = 0, imax = params.size(); i < imax; i++) {
			if (p == params.get(i))
				return i;
		}
		return -1;
	}

	@Override
	public int getNoOfParameters() {
		return params.size();
	}

	@Override
	public IParameter getParameter(int index) {
		return params.get(index);
	}

	@Override
	public void setParameter(int index, IParameter parameter) {
		IParameter op = params.get(index);
		for (int i = 0, imax = getNoOfFunctions(); i < imax; i++) {
			IFunction f = getFunction(i);
			int j = f instanceof AFunction ? ((AFunction) f).indexOfParameter(op) : indexOfParameter(f, op);
			if (j >= 0)
				f.setParameter(j, parameter);
		}
		params.set(index, parameter);
		setDirty(true);
	}

	@Override
	public IParameter[] getParameters() {
		int n = params.size();
		IParameter[] nParameters = new IParameter[n];
		for (int i = 0; i < n; i++) {
			nParameters[i] = params.get(i);
		}
		return nParameters;
	}

	@Override
	public double getParameterValue(int index) {
		return params.get(index).getValue();
	}

	@Override
	public void setParameterValues(double... parameters) {
		int n = Math.min(parameters.length, params.size());
		for (int i = 0; i < n; i++) {
			params.get(i).setValue(parameters[i]);
		}
		setDirty(true);
	}

	@Override
	protected boolean isDuplicated(IParameter param) {
		return false;
	}

	@Override
	public void setDirty(boolean isDirty) {
		super.setDirty(isDirty);
		int nf = getNoOfFunctions();
		for (int i = 0; i < nf; i++) {
			IFunction f = getFunction(i);
			if (f != null)
				f.setDirty(isDirty);
		}
	}

	protected final String OPERATOR_NO_FUNCTIONS = "Operator has no functions"; 

	@Override
	public String toString() {
		StringBuffer out = new StringBuffer();
		int nf = getNoOfFunctions();
		for (int i = 0; i < nf; i++) {
			IFunction f = getFunction(i);
			if (f != null) {
				if (nf > 1)
					out.append(String.format("Function %d - \n", i));
				out.append(f.toString());
				out.append('\n');
			}
		}

		return out.length() == 0 ? OPERATOR_NO_FUNCTIONS : out.substring(0, out.length() - 1);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (dirty ? 1231 : 1237);
		result = prime * result + (name == null ? 0 : name.hashCode());
		result = prime * result + params.hashCode();
		for (IFunction f : getFunctions()) {
			if (f != null) {
				result = prime * result + f.hashCode();
			} else {
				result = prime * result;
			}
		}
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		AOperator other = (AOperator) obj;
		if (dirty != other.dirty)
			return false;

		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;

		if (!params.equals(other.params))
			return false;

		int nf = getNoOfFunctions();
		if (nf != other.getNoOfFunctions())
			return false;

		for (int i = 0; i < nf; i++) {
			IFunction fa = getFunction(i);
			IFunction fo = other.getFunction(i);
			if (fa == null) {
				if (fo != null)
					return false;
			} else if (!fa.equals(fo)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isValid() {
		if (!super.isValid())
			return false;
		for (IFunction function : getFunctions()) {
			if (function == null)
				return false;
			if (!function.isValid())
				return false;
		}
		return true;
	}

	@Override
	public AOperator copy() throws Exception {
		AOperator copy = (AOperator) super.copy();
		for (IFunction f : getFunctions()) {
			if (f == null) {
				copy.addFunction(null);
			} else {
				copy.addFunction(f.copy());
			}
		}
		return copy;
	}

}

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

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract operator
 */
abstract public class AOperator extends AFunction implements IOperator {
	protected List<IParameter> params;

	public AOperator() {
		super(0);
		params = new ArrayList<>();
	}

	protected void updateParameters() {
		params.clear();
		for (int i = 0, imax = getNoOfFunctions(); i < imax; i++) {
			IFunction f = getFunction(i);
			for (int j = 0, jmax = f.getNoOfParameters(); j < jmax; j++) {
				IParameter p = f.getParameter(j);

				if (!params.contains(p)) {
					params.add(p);
				}
			}
		}
		setDirty(true);
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
	public String getParameterName(int index) {
		return params.get(index).getName();
	}

	@Override
	public double getParameterValue(int index) {
		return params.get(index).getValue();
	}

	@Override
	public double[] getParameterValues() {
		double[] values = new double[params.size()];
		for (int i = 0; i < values.length; i++) {
			values[i] = params.get(i).getValue();
		}
		return values;
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
	public void setDirty(boolean isDirty) {
		super.setDirty(isDirty);
		int nf = getNoOfFunctions();
		for (int i = 0; i < nf; i++) {
			IFunction f = getFunction(i);
			if (f != null)
				f.setDirty(isDirty);
		}
	}

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

		return out.substring(0, out.length() - 1);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (dirty ? 1231 : 1237);
		result = prime * result + (name == null ? 0 : name.hashCode());
		result = prime * result + params.hashCode();
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
		return true;
	}
}

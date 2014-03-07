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
 * A n-ary operator that uses n > 0 functions
 */
abstract public class ANaryOperator extends AOperator implements IOperator {
	protected List<IFunction> functions;

	public ANaryOperator() {
		super();
		functions = new ArrayList<IFunction>();
	}

	@Override
	public boolean isExtendible() {
		return true;
	}

	@Override
	public int getRequiredFunctions() {
		return -1;
	}

	@Override
	public void addFunction(IFunction function) {
		functions.add(function);
		function.setParentOperator(this);
		updateParameters();
	}

	@Override
	public void setFunction(int index, IFunction function) {
		functions.set(index, function);
		function.setParentOperator(this);
		updateParameters();
	}

	@Override
	public int getNoOfFunctions() {
		return functions.size();
	}

	@Override
	public IFunction getFunction(int index) {
		return functions.get(index);
	}

	@Override
	public IFunction[] getFunctions() {
		return functions.toArray(new IFunction[functions.size()]);
	}

	@Override
	public void removeFunction(int index) {
		functions.remove(index).setParentOperator(null);
		updateParameters();
	}
}

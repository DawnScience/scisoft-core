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

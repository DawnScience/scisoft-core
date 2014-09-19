/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.rpc.flattening.helpers;

import uk.ac.diamond.scisoft.analysis.rpc.flattening.IFlattener;
import uk.ac.diamond.scisoft.analysis.rpc.flattening.IFlattens;
import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;

public class SelfFlattensHelper implements IFlattener<Object> {

	static final String NULL_TYPE = "__null__";

	public SelfFlattensHelper() {
	}

	@Override
	public Object flatten(Object obj, IRootFlattener rootFlattener) {
		IFlattens selfFlattener = (IFlattens) obj;
		return selfFlattener.flatten(rootFlattener);
	}

	@Override
	public boolean canFlatten(Object obj) {
		return obj instanceof IFlattens;
	}

	@Override
	public Object unflatten(Object obj, IRootFlattener rootFlattener) {
		throw new AssertionError();
	}

	@Override
	public boolean canUnFlatten(Object obj) {
		return false;
	}
}

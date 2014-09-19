/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.rpc.flattening.helpers;

import java.util.List;

import uk.ac.diamond.scisoft.analysis.rpc.flattening.IFlattener;
import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;

public class ListHelper implements IFlattener<List<Object>> {

	public ListHelper() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object flatten(Object obj, IRootFlattener rootFlattener) {
		List<Object> l = (List<Object>) obj;
		return rootFlattener.flatten(l.toArray());
	}

	@Override
	public List<Object> unflatten(Object obj, IRootFlattener rootFlattener) {
		throw new AssertionError();
	}

	@Override
	public boolean canFlatten(Object obj) {
		return obj instanceof List;
	}

	@Override
	public boolean canUnFlatten(Object obj) {
		return false;
	}
}

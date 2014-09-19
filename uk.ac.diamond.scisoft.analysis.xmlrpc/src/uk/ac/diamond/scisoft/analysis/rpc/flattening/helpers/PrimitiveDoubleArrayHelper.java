/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.rpc.flattening.helpers;

import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;

public class PrimitiveDoubleArrayHelper extends PrimitiveArrayHelper {

	@Override
	public Object unflatten(Object obj, IRootFlattener rootFlattener) {
		Object[] array = (Object[]) obj;
		double[] doubleArray = new double[array.length];
		for (int i = 0; i < doubleArray.length; i++) {
			doubleArray[i] = (Double) array[i];
		}
		return doubleArray;
	}

	@Override
	public boolean canUnFlatten(Object obj) {
		if (!(obj instanceof Object[])) {
			return false;
		}
		Object[] array = (Object[]) obj;
		for (int i = 0; i < array.length; i++) {
			if (!(array[i] instanceof Double)) {
				return false;
			}
		}
		return array.length > 0;

	}

}

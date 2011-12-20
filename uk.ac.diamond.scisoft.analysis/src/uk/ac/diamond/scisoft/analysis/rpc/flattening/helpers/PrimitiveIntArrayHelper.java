/*
 * Copyright © 2011 Diamond Light Source Ltd.
 * Contact :  ScientificSoftware@diamond.ac.uk
 * 
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 * 
 * This software is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this software. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.rpc.flattening.helpers;

import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;

public class PrimitiveIntArrayHelper extends PrimitiveArrayHelper {

	@Override
	public Object unflatten(Object obj, IRootFlattener rootFlattener) {
		Object[] array = (Object[]) obj;
		int[] intArray = new int[array.length];
		for (int i = 0; i < intArray.length; i++) {
			intArray[i] = (Integer) array[i];
		}
		return intArray;
	}

	@Override
	public boolean canUnFlatten(Object obj) {
		if (!(obj instanceof Object[])) {
			return false;
		}
		Object[] array = (Object[]) obj;
		for (int i = 0; i < array.length; i++) {
			if (!(array[i] instanceof Integer)) {
				return false;
			}
		}
		return array.length > 0;

	}

}

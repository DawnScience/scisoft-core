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

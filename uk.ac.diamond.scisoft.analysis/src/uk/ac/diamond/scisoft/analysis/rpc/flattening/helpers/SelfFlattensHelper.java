/*-
 * Copyright © 2011 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
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
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
import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;

public class PassThroughFlatteningHelper implements IFlattener<Object> {

	@Override
	public Object flatten(Object obj, IRootFlattener rootFlattener) {
		return obj;
	}

	@Override
	public Object unflatten(Object obj, IRootFlattener rootFlattener) {
		return obj;
	}

	@Override
	public boolean canFlatten(Object obj) {
		return obj instanceof Integer || obj instanceof Boolean || obj instanceof String || obj instanceof Double
				|| obj instanceof byte[];
	}

	@Override
	public boolean canUnFlatten(Object obj) {
		return canFlatten(obj);
	}
}
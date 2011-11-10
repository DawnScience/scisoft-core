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

import java.util.Map;

import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;

public class ExceptionHelper extends MapFlatteningHelper<Exception> {

	public ExceptionHelper() {
		super(Exception.class);
	}

	@Override
	public Exception unflatten(Map<?, ?> thisMap, IRootFlattener rootFlattener) {
		return new Exception((String) thisMap.get(CONTENT));
	}

	@Override
	public Object flatten(Object obj, IRootFlattener rootFlattener) {
		Exception thisException = (Exception) obj;
		Map<String, Object> outMap = createMap(getTypeCanonicalName());
		outMap.put(CONTENT, thisException.getLocalizedMessage());
		return outMap;
	}

}
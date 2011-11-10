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

package uk.ac.diamond.scisoft.analysis.rpc.internal;

import uk.ac.diamond.scisoft.analysis.rpc.IAnalysisRpcHandler;

/**
 * This interface is responsible for the actual flattening and unflattening on the server side.
 */
public interface AnalysisRpcServerHandler {

	/**
	 * Direct a call to the handler registered for the given destination passing it args after unflattening them.
	 * 
	 * @param destination
	 *            name of the handler to call
	 * @param args
	 *            arguments to pass to the handler
	 * @return flattened return value from registered handler
	 * @see IAnalysisRpcHandler
	 */
	public Object handler(String destination, Object[] args);

}

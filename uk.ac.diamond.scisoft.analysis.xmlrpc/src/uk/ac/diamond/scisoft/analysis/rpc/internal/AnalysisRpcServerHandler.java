/*
 * Copyright 2011 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

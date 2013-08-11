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

import uk.ac.diamond.scisoft.analysis.rpc.AnalysisRpcException;
import uk.ac.diamond.scisoft.analysis.rpc.AnalysisRpcServer;
import uk.ac.diamond.scisoft.analysis.rpc.IAnalysisRpcHandler;
import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;

/**
 * @see AnalysisRpcServerHandler
 */
public class AnalysisRpcServerHandlerImpl implements AnalysisRpcServerHandler {
	private final AnalysisRpcServer analysisRPC;

	public AnalysisRpcServerHandlerImpl(AnalysisRpcServer analysisRPC) {
		this.analysisRPC = analysisRPC;
	}

	@Override
	public Object handler(String destination, Object[] args) {
		IRootFlattener flattener = analysisRPC.getFlattener();
		try {
			IAnalysisRpcHandler handler = analysisRPC.getDestination(destination);
			if (handler == null) {
				throw new AnalysisRpcException("No handler registered for " + destination);
			}
			Object[] unflattened = new Object[args.length];
			for (int i = 0; i < args.length; i++) {
				unflattened[i] = flattener.unflatten(args[i]);
			}
			Object ret = handler.run(unflattened);
			Object flatret = flattener.flatten(ret);
			return flatret;
		} catch (Exception e) {
			return flattener.flatten(e);
		}
	}

	@Override
	public Object is_alive() {
		// this method does nothing. Simply successfully running as an RPC call
		// determines if alive
		return true;
	}
}

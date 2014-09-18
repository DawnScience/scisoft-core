/*
 * Copyright 2012 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis;

import org.eclipse.dawnsci.analysis.api.AbstractClientProvider;

import uk.ac.diamond.scisoft.analysis.rpc.AnalysisRpcClient;
import uk.ac.diamond.scisoft.analysis.rpc.AnalysisRpcException;

/**
 * A simple interface to Java RMI so that objects can be exported using the defaults encoded in this class.
 */
public class AnalysisRpcClientProvider extends AbstractClientProvider {
	private static AnalysisRpcClientProvider instance = new AnalysisRpcClientProvider();
	private AnalysisRpcClient analysisRpcClient;

	/**
	 * Get Instance of provider
	 * 
	 * @return instance
	 */
	public static AnalysisRpcClientProvider getInstance() {
		return instance;
	}

	private AnalysisRpcClientProvider() {
	}

	/**
	 * Make a call to an RPC Service registered with addHandler.
	 * 
	 * @param serviceName
	 *            name of the service to call
	 * @param args
	 *            arguments to the target method
	 * @return what the delegated method call returned
	 * @throws AnalysisRpcException
	 */
	public Object request(String serviceName, Object... args) throws AnalysisRpcException {
		if (analysisRpcClient == null || analysisRpcClient.getPort() != getPort()) {
			analysisRpcClient = new AnalysisRpcClient(getPort());
		}
		return analysisRpcClient.request(serviceName, args);
	}

	@Override
	protected String getEnvName() {
		return "SCISOFT_RPC_PORT";
	}

}

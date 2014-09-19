/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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

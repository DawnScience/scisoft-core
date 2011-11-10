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

package uk.ac.diamond.scisoft.analysis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.rpc.AnalysisRpcClient;
import uk.ac.diamond.scisoft.analysis.rpc.AnalysisRpcException;
import uk.ac.diamond.scisoft.analysis.rpc.AnalysisRpcServer;
import uk.ac.diamond.scisoft.analysis.rpc.IAnalysisRpcHandler;

/**
 * A simple interface to Analysis RPC so that classes can be exported using the defaults encoded in this class.
 */
public class AnalysisRpcServerProvider {
	private static final Logger logger = LoggerFactory.getLogger(AnalysisRpcServerProvider.class);
	public final static int DEFAULT_RPCPORT = 8610;

	private static AnalysisRpcServerProvider instance = new AnalysisRpcServerProvider();
	private int port = DEFAULT_RPCPORT;
	private AnalysisRpcServer server = null;
	private AnalysisRpcClient analysisRpcClient;

	/**
	 * Get Instance of provider
	 * 
	 * @return instance
	 */
	public static AnalysisRpcServerProvider getInstance() {
		return instance;
	}

	private AnalysisRpcServerProvider() {
	}

	/**
	 * Export the handler under the named service.
	 * <p>
	 * This is the method you call on the "server" to make a handler available.
	 * 
	 * @param serviceName
	 *            name of the service
	 * @param handler
	 *            handler to export
	 */
	public synchronized void addHandler(String serviceName, IAnalysisRpcHandler handler) {
		if (server == null) {
			server = new AnalysisRpcServer(port);
			try {
				server.start();
				logger.info("Starting Analysis RPC Server on port " + port);
			} catch (AnalysisRpcException e) {
				logger.error("Failed to start AnalysisRpcServer", e);
			}
		}
		if (handler == null) {
			logger.info("Removing " + serviceName);
			server.removeHandler(serviceName);
		} else {
			logger.info("Adding " + serviceName);
			server.addHandler(serviceName, handler);
		}
	}

	/**
	 * TESTING ONLY: Retrieve existing handler for the given name
	 * 
	 * @param serviceName
	 * @return existing handler
	 */
	public synchronized IAnalysisRpcHandler getHandler(String serviceName) {
		if (server == null)
			return null;
		return server.getHandler(serviceName);
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
		if (analysisRpcClient == null || analysisRpcClient.getPort() != port) {
			analysisRpcClient = new AnalysisRpcClient(port);
		}
		return analysisRpcClient.request(serviceName, args);
	}

	/**
	 * Provide a port number to use. Allows overriding the default, particularly useful if multiple instances of SDA are
	 * run on the same machine.
	 * 
	 * @param analysisRpcPort
	 *            new port number, or 0 to use default port
	 * @throws IllegalStateException
	 *             if the class has already been initialised
	 * @throws IllegalArgumentException
	 *             if the port number is < 0
	 */
	public void setPort(int analysisRpcPort) throws IllegalStateException, IllegalArgumentException {
		if (analysisRpcPort < 0)
			throw new IllegalArgumentException("Port number must be >= 0");
		if (server != null)
			throw new IllegalStateException("Analysis Rpc Server Provider has already used the existing port, "
					+ "setPort must be called before any handlers are added or requests are made");

		if (analysisRpcPort == 0) {
			port = DEFAULT_RPCPORT;
		} else {
			port = analysisRpcPort;
		}
	}

	/**
	 * Return Port number in use
	 * 
	 * @return port number
	 */
	public int getPort() {
		return port;
	}

}

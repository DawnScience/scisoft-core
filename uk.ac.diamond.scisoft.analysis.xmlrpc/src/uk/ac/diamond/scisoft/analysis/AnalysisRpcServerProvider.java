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

package uk.ac.diamond.scisoft.analysis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.rpc.AnalysisRpcException;
import uk.ac.diamond.scisoft.analysis.rpc.AnalysisRpcServer;
import uk.ac.diamond.scisoft.analysis.rpc.IAnalysisRpcHandler;

/**
 * A simple interface to Analysis RPC so that classes can be exported using the defaults encoded in this class.
 */
public class AnalysisRpcServerProvider extends ServerProvider {
	private static final Logger logger = LoggerFactory.getLogger(AnalysisRpcServerProvider.class);
	
	private static AnalysisRpcServerProvider instance = new AnalysisRpcServerProvider();
	private int port = 0;
	private AnalysisRpcServer server = null;

	public boolean isServerRunning(){
		return server != null;
	}
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
	 * @throws AnalysisRpcException 
	 */
	public synchronized void addHandler(String serviceName, IAnalysisRpcHandler handler) throws AnalysisRpcException {
		if (Boolean.getBoolean("uk.ac.diamond.scisoft.analysis.analysisrpcserverprovider.disable")) {
			throw new AnalysisRpcException("Analysis RPC Server disabled with property uk.ac.diamond.scisoft.analysis.analysisrpcserverprovider.disable");
		}
			
		startServer();
		if (handler == null) {
			logger.info("Removing " + serviceName);
			server.removeHandler(serviceName);
		} else {
			logger.info("Adding " + serviceName);
			server.addHandler(serviceName, handler);
		}
	}

	/**
	 * Public for TESTING ONLY: Start the server
	 * 
	 * Forces the server to start even if no handlers have yet been added
	 * 
	 * @throws AnalysisRpcException
	 */
	public synchronized void startServer() throws AnalysisRpcException {
		if (server == null) {
			server = new AnalysisRpcServer(port);
			try {
				server.start();
				port = server.getPort();
				logger.info("Starting Analysis RPC Server on port " + port);
				firePortListeners(port, true);
			} catch (AnalysisRpcException e) {
				logger.error("Failed to start AnalysisRpcServer", e);
				throw new AnalysisRpcException(e);
			}
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
	 * Provide a port number to use. Allows overriding the default, particularly useful if multiple instances of SDA are
	 * run on the same machine.
	 * 
	 * @param analysisRpcPort
	 *            new port number, or 0 to auto select a free port
	 * @throws IllegalStateException
	 *             if the class has already been initialised
	 * @throws IllegalArgumentException
	 *             if the port number is < 0
	 */
	public void setPort(int analysisRpcPort) throws IllegalStateException, IllegalArgumentException {
		if (analysisRpcPort < 0)
			throw new IllegalArgumentException("Port number must be >= 0");
		if (server != null ){
			if( analysisRpcPort != 0 && port != analysisRpcPort){
				throw new IllegalStateException("Analysis Rpc Server Provider is already running using a different port, "
						+ "setPort must be called before any handlers are added or requests are made");
			}
			//do nothing A value of 0 means use any port.
		} else {
			port = analysisRpcPort;
		}
	}

	/**
	 * Return Port number in use.
	 * 
	 * The port number will not be valid before handlers are added. 
	 * 
	 * @return port number
	 */
	public int getPort() {
		return port;
	}

}

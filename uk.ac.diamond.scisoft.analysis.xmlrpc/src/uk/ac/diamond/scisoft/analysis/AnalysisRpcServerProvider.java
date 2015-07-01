/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis;

import org.eclipse.dawnsci.analysis.api.ServerProvider;
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
		if (Boolean.getBoolean("uk.ac.diamond.scisoft.analysis.analysisrpcserverprovider.disable")) {
			throw new IllegalStateException("Analysis RPC Server disabled with property uk.ac.diamond.scisoft.analysis.analysisrpcserverprovider.disable");
		}

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

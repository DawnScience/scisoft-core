/*-
 * Copyright (c) 2012-2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.rpc;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;
import org.eclipse.dawnsci.analysis.api.rpc.AnalysisRpcException;
import org.eclipse.dawnsci.analysis.api.rpc.IAnalysisRpcHandler;
import org.eclipse.dawnsci.analysis.api.rpc.IAnalysisRpcServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.AnalysisRpcServerProvider;
import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;
import uk.ac.diamond.scisoft.analysis.rpc.internal.AnalysisRpcServerHandler;
import uk.ac.diamond.scisoft.analysis.rpc.internal.AnalysisRpcServerHandlerImpl;
import uk.ac.diamond.scisoft.analysis.rpc.internal.AnalysisRpcWebServer;
import uk.ac.diamond.scisoft.analysis.rpc.internal.HandlerRequestProcessorFactoryFactory;

/**
 * Server class for AnalysisRpc.
 * <p>
 * Within SDA, consider registering new handlers with {@link AnalysisRpcServerProvider} rather than creating a new
 * server on an additional port
 * 
 * @see AnalysisRpcBasicTest See the Ananlysis Rpc Basic Test for an example of use
 */
public class AnalysisRpcServer implements IAnalysisRpcServer {
	private static final Logger logger = LoggerFactory.getLogger(AnalysisRpcServer.class);

	private XmlRpcServer xmlRpcServer;
	private WebServer webServer;

	private Map<String, IAnalysisRpcHandler> handlers = Collections.synchronizedMap(new HashMap<String, IAnalysisRpcHandler>());
	private IRootFlattener flattener = FlatteningService.getFlattener();

	/**
	 * Create a new AnalysisRpc server that listens on the given port
	 * 
	 * @param port
	 *            to listen on, or 0 to allocate port automatically
	 */
	public AnalysisRpcServer(int port) {
		try {
			webServer = new AnalysisRpcWebServer(port, InetAddress.getByAddress(new byte[] { 127, 0, 0, 1 }));
		} catch (UnknownHostException e1) {
			logger.error("Failed to get InetAddress of localhost", e1);
		}

		xmlRpcServer = webServer.getXmlRpcServer();

		PropertyHandlerMapping phm = new PropertyHandlerMapping();
		AnalysisRpcServerHandler handler = new AnalysisRpcServerHandlerImpl(this);
		phm.setRequestProcessorFactoryFactory(new HandlerRequestProcessorFactoryFactory(handler));

		try {
			phm.addHandler("Analysis", AnalysisRpcServerHandler.class);
		} catch (XmlRpcException e) {
			logger.error("Failed to addHandler for 'Analysis' to XML-RPC Server", e);
		}
		xmlRpcServer.setHandlerMapping(phm);

		XmlRpcServerConfigImpl serverConfig = (XmlRpcServerConfigImpl) xmlRpcServer.getConfig();
		serverConfig.setEnabledForExtensions(false);
		serverConfig.setContentLengthOptional(false);
	}

	@Override
	public void addHandler(String name, IAnalysisRpcHandler handler) {
		handlers.put(name, handler);
	}

	@Override
	public void removeHandler(String serviceName) {
		handlers.remove(serviceName);
	}

	@Override
	public IAnalysisRpcHandler getHandler(String serviceName) {
		return handlers.get(serviceName);
	}

	@Override
	public void start() throws AnalysisRpcException {
		try {
			webServer.start();
		} catch (IOException e) {
			logger.error("Failed to start AnalysisRPC underlying webServer", e);
			throw new AnalysisRpcException(e);
		}
	}

	@Override
	public void shutdown() {
		webServer.shutdown();
	}

	@Override
	public IAnalysisRpcHandler getDestination(String destination) {
		return handlers.get(destination);
	}

	public IRootFlattener getFlattener() {
		return flattener;
	}

	@Override
	public int getPort() {
		return webServer.getPort();
	}

}

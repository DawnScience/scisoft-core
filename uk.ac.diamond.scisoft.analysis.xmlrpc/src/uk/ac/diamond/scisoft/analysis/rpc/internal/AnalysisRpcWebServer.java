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

import java.net.InetAddress;

import org.apache.xmlrpc.server.XmlRpcStreamServer;
import org.apache.xmlrpc.webserver.WebServer;

/**
 * Custom {@link WebServer} that has our own type factory.
 * 
 * @see AnalysisRpcDoubleParser
 * @see AnalysisRpcServerHandlerImpl
 */
public class AnalysisRpcWebServer extends WebServer {

	// This constructor is currently unused, but included for completeness to 
	// provide same construction options as WebServer
	public AnalysisRpcWebServer(int pPort) {
		super(pPort);
	}

	public AnalysisRpcWebServer(int pPort, InetAddress pAddr) {
		super(pPort, pAddr);
	}

	@Override
	protected XmlRpcStreamServer newXmlRpcStreamServer() {
		XmlRpcStreamServer server = super.newXmlRpcStreamServer();
		server.setTypeFactory(new AnalysisRpcTypeFactoryImpl(server));
		return server;
	}

}

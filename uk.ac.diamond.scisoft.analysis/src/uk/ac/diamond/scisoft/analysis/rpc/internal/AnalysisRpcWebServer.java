/*
 * Copyright © 2011 Diamond Light Source Ltd.
 * Contact :  ScientificSoftware@diamond.ac.uk
 * 
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 * 
 * This software is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this software. If not, see <http://www.gnu.org/licenses/>.
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

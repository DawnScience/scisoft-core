/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.rpc.flattening;

import java.io.IOException;
import java.net.URL;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class FlatteningViaXmlRpcTest extends ExplicitFlatteningTestAbstract {
	static WebServer webServer;
	private static XmlRpcClient client;
	
	@BeforeClass
	public static void start() throws IOException, XmlRpcException {
		webServer = new WebServer(8614);
		XmlRpcServer xmlRpcServer = webServer.getXmlRpcServer();

		PropertyHandlerMapping phm = new PropertyHandlerMapping();

		phm.addHandler("Loopback", LoopbackClass.class);
		xmlRpcServer.setHandlerMapping(phm);

		XmlRpcServerConfigImpl serverConfig = (XmlRpcServerConfigImpl) xmlRpcServer.getConfig();
		serverConfig.setEnabledForExtensions(false);
		serverConfig.setContentLengthOptional(false);

		webServer.start();
		
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
	    config.setServerURL(new URL("http://127.0.0.1:8614/xmlrpc"));
	    client = new XmlRpcClient();
	    client.setConfig(config);
	}
	
	@AfterClass
	public static void stop() {
		if (webServer != null)
			webServer.shutdown();
		webServer = null;
		client = null;
	}
	
	public static class LoopbackClass {
		public Object loopback(Object[] obj) {
			return obj[0];
		}
	}

	@Override
	protected Object doAdditionalWorkOnFlattendForm(Object flat) {
		try {
			return client.execute("Loopback.loopback", new Object[] {new Object[]{flat}});
		} catch (XmlRpcException e) {
			throw new RuntimeException(e);
		}
	}

}

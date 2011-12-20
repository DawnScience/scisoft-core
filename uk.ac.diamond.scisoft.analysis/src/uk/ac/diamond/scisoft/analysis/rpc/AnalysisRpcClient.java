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

package uk.ac.diamond.scisoft.analysis.rpc;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;
import uk.ac.diamond.scisoft.analysis.rpc.internal.AnalysisRpcTypeFactoryImpl;

/**
 * Client class for AnalysisRpc.
 * <p>
 * Generally it is expected that a provider of a service will write a wrapper class that delegates to
 * {@link AnalysisRpcClient} and provides a "nice" interface which is strongly typed.
 * <p>
 * 
 * @see AnalysisRpcBasicTest See the Ananlysis Rpc Basic Test for an example of use
 */
public class AnalysisRpcClient {
	private static final Logger logger = LoggerFactory.getLogger(AnalysisRpcClient.class);

	private XmlRpcClient client;
	private IRootFlattener flattener = FlatteningService.getFlattener();

	private final int port;

	/**
	 * Create a new AnalysisRpc client that connects to a server on the given port
	 * 
	 * @param port
	 *            to connect to
	 */
	public AnalysisRpcClient(int port) {
		this.port = port;
		try {
			XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
			config.setServerURL(new URL("http://127.0.0.1:" + port + "/RPC2"));
			client = new XmlRpcClient();
			client.setConfig(config);
			client.setTypeFactory(new AnalysisRpcTypeFactoryImpl(client));
		} catch (MalformedURLException e) {
			// This is a programming error
			logger.error("Failed to create AnalysisRPCClient due to MalformedURLException", e);
		}
	}

	/**
	 * Issue a RPC call by calling request. The call is sent to the server on the registered port to the handler
	 * registered with the name passed to destination.
	 * <p>
	 * All arguments passed the server are "flattened" for transport, and automatically unflattened by the server before
	 * delivery to the destination handler. The return value is similarly flattened and unflattened.
	 * <p>
	 * If the delegated to method throws an exception, it is re-thrown here wrapped as an {@link AnalysisRpcException}.
	 * If the delegated to method returns an exception, it will be thrown rather than returned.
	 * <p>
	 * 
	 * @param destination
	 *            target handler in server
	 * @param args
	 *            arguments in the server
	 * @return value that the delegated to method returns
	 * @throws AnalysisRpcException under a few conditions, always as a wrapper around the original cause. The original causes can be one of:
	 * <ul>
	 * <li> {@link UnsupportedOperationException} if the arguments or return type failed to be flattened or unflattened.</li>
	 * <li> {@link XmlRpcException} if the underlying transport had a failure and XML-RPC was used as the transport </li>
	 * <li> Other type of {@link Exception} if the remote end threw an Exception </li>
	 * </ul>
	 */
	public Object request(String destination, Object[] args) throws AnalysisRpcException {
		try {
			Object[] flatargs = (Object[]) flattener.flatten(args);
			Object flatret = client.execute("Analysis.handler", new Object[] { destination, flatargs });
			Object unflatret = flattener.unflatten(flatret);
			if (unflatret instanceof Exception) {
				throw new AnalysisRpcException((Exception) unflatret);
			}
			return unflatret;
		} catch (XmlRpcException e) {
			throw new AnalysisRpcException(e);
		} catch (UnsupportedOperationException e) {
			throw new AnalysisRpcException(e);
		}
	}

	/**
	 * Return port number in use
	 * @return port
	 */
	public int getPort() {
		return port;
	}
}

/*-
 * Copyright (c) 2012-2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.rpc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.eclipse.dawnsci.analysis.api.rpc.AnalysisRpcException;
import org.eclipse.dawnsci.analysis.api.rpc.IAnalysisRpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;
import uk.ac.diamond.scisoft.analysis.rpc.internal.AnalysisRpcTypeFactoryImpl;

/**
 * Client class for AnalysisRpc.
 * <p>
 * Generally it is expected that a provider of a service will write a wrapper
 * class that delegates to {@link AnalysisRpcClient} and provides a "nice"
 * interface which is strongly typed.
 * <p>
 * 
 * @see AnalysisRpcBasicTest See the Ananlysis Rpc Basic Test for an example of
 *      use
 */
public class AnalysisRpcClient implements IAnalysisRpcClient {
	private static final Logger logger = LoggerFactory
			.getLogger(AnalysisRpcClient.class);

	private XmlRpcClient client;
	private IRootFlattener flattener = FlatteningService.getFlattener();

	private final int port;

	/**
	 * Create a new AnalysisRpc client that connects to a server on the given
	 * port
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
			logger.error(
					"Failed to create AnalysisRPCClient due to MalformedURLException",
					e);
		}
	}

	private Object request_common(String destination, Object[] args,
			boolean debug, boolean suspend) throws AnalysisRpcException {
		try {
			if (args == null) {
				// No arguments, convert null to empty array
				args = new Object[0];
			}
			Object[] flatargs = (Object[]) flattener.flatten(args);
			final Object flatret;
			if (debug) {
				flatret = client.execute("Analysis.handler_debug",
						new Object[] { destination, flatargs, suspend });
			} else {
				flatret = client.execute("Analysis.handler", new Object[] {
						destination, flatargs });
			}
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

	@Override
	public Object request(String destination, Object[] args)
			throws AnalysisRpcException {
		return request_common(destination, args, false, false);
	}

	@Override
	public Object request_debug(String destination, Object[] args,
			boolean suspend) throws AnalysisRpcException {
		return request_common(destination, args, true, suspend);
	}

	@Override
	public boolean isAlive() {
		try {
			client.execute("Analysis.is_alive", new Object[0]);
			return true;
		} catch (XmlRpcException e) {
			return false;
		}
	}

	@Override
	public void setPyDevSetTraceParams(Map<String, Object> options)
			throws AnalysisRpcException {
		try {
			client.execute("Analysis.set_pydev_settrace_params",
					new Object[] { options });
		} catch (XmlRpcException e) {
			throw new AnalysisRpcException(
					"Failed to set_pydev_settrace_params", e);
		}
	}

	@Override
	public void setPyDevSetTracePort(int port) throws AnalysisRpcException {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("port", port);
		setPyDevSetTraceParams(options);
	}

	@Override
	public void waitUntilAlive() throws AnalysisRpcException {
		String timeoutSystemProp = System.getProperty(
				"uk.ac.diamond.scisoft.analysis.xmlrpc.client.timeout", "15000");
		final long ms = Integer.parseInt(timeoutSystemProp);
		waitUntilAlive(ms);
	}

	@Override
	public void waitUntilAlive(long milliseconds) throws AnalysisRpcException {
		long stop = System.currentTimeMillis() + milliseconds;
		while (System.currentTimeMillis() < stop) {
			if (isAlive())
				return;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}

		throw new AnalysisRpcException(
				"Timeout waiting for other end to be alive");
	}

	@Override
	public int getPort() {
		return port;
	}

	@Override
	public Object newProxyInstance(ClassLoader loader, Class<?>[] interfaces,
			boolean debug) {
		return Proxy.newProxyInstance(loader, interfaces,
				new ClientProxy(debug));
	}

	@Override
	public Object newProxyInstance(Class<?>[] interfaces, boolean debug) {
		return newProxyInstance(interfaces[0].getClassLoader(), interfaces,
				debug);
	}

	@Override
	public <T> T newProxyInstance(Class<T> single_interface, boolean debug) {
		@SuppressWarnings("unchecked")
		T result = (T) newProxyInstance(new Class<?>[] { single_interface },
				debug);
		return result;
	}

	@Override
	public <T> T newProxyInstance(Class<T> single_interface) {
		@SuppressWarnings("unchecked")
		T result = (T) newProxyInstance(new Class<?>[] { single_interface },
				false);
		return result;
	}

	private class ClientProxy implements InvocationHandler {

		private boolean debug;

		public ClientProxy(boolean debug) {
			this.debug = debug;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			if (!hasThrowsAnalysisRpcException(method.getExceptionTypes())) {
				throw new RuntimeException(
						"Invoked methods on AnalysisRpcClient must be declared to throw AnalysisRpcException");
			}

			final String methodName = method.getName();
			if (debug) {
				return request_debug(methodName, args, false);
			} else {
				return request(methodName, args);
			}
		}

		private boolean hasThrowsAnalysisRpcException(Class<?>[] exceptionTypes) {
			for (Class<?> exceptionType : exceptionTypes) {
				if (exceptionType.isAssignableFrom(AnalysisRpcException.class)) {
					return true;
				}
			}
			return false;
		}

	}
}

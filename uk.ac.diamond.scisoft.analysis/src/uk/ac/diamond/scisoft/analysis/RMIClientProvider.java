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

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple interface to Java RMI so that objects can be exported using the defaults encoded in this class.
 */
public class RMIClientProvider {
	private static final Logger logger = LoggerFactory.getLogger(RMIClientProvider.class);

	private static RMIClientProvider instance = new RMIClientProvider();
	private int port = 0;

	/**
	 * Get Instance of provider
	 * 
	 * @return instance
	 */
	public static RMIClientProvider getInstance() {
		return instance;
	}

	private RMIClientProvider() {
	}

	/**
	 * Return a proxy to the object on the server.
	 * <p>
	 * This is the method you call from the "client" code (e.g. the code run from Jython) to access the server.
	 * 
	 * @param host
	 *            host name to connect to, or <code>null</code> for localhost
	 * @param serviceName
	 *            name of a registered service
	 * @param port
	 *            port on server to get registry from, or 0 for automatic port (using SCISOFT_RMI_PORT env variable)
	 * @return remote proxy remote object
	 * @throws RemoteException
	 * @throws NotBoundException
	 * @throws AccessException
	 */
	public Remote lookup(String host, String serviceName, int port) throws RemoteException, NotBoundException,
			AccessException {
		if (port == 0)
			port = getPort();
		Registry registry = LocateRegistry.getRegistry(host, port);
		return registry.lookup(serviceName);
	}

	/**
	 * Return a proxy to the object on the server.
	 * <p>
	 * This is the method you call from the "client" code (e.g. the code run from Jython) to access the server.
	 * 
	 * @param host
	 *            host name to connect to, or <code>null</code> for localhost
	 * @param serviceName
	 *            name of a registered service
	 * @return remote proxy remote object
	 * @throws RemoteException
	 * @throws NotBoundException
	 * @throws AccessException
	 */
	public Remote lookup(String host, String serviceName) throws RemoteException, NotBoundException, AccessException {
		return lookup(host, serviceName, 0);
	}

	/**
	 * Provide a port number to use. Allows overriding the default, particularly useful if multiple instances of SDA are
	 * controlled from the same Jython/Java JVM.
	 * 
	 * @param rmiPortNumber
	 *            new port number, or 0 to use default port resolution mechanism
	 * @throws IllegalArgumentException
	 *             if the port number is < 0
	 */
	public void setPort(int rmiPortNumber) throws IllegalStateException, IllegalArgumentException {
		if (rmiPortNumber < 0)
			throw new IllegalArgumentException("Port number must be >= 0");

		port = rmiPortNumber;
	}

	/**
	 * Return Port number in use
	 * 
	 * @return port number
	 * @throws RemoteException
	 *             if the remote port cannot be determined
	 */
	public int getPort() throws RemoteException {
		if (port == 0) {
			String rmiPortString = System.getenv("SCISOFT_RMI_PORT");
			if (rmiPortString != null) {
				try {
					port = Integer.parseInt(rmiPortString);
				} catch (NumberFormatException e) {
					port = 0;
				}
			}
			// It isn't going to work with a port of 0, so throw the error now
			if (port == 0) {
				String msg = "Failed to determine suitable port from SCISOFT_RMI_PORT, value was '"
						+ rmiPortString + "'";
				logger.error(msg);
				throw new RemoteException(msg);
			}
		}

		return port;
	}

}

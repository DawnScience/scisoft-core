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

/**
 * A simple interface to Java RMI so that objects can be exported using the defaults encoded in this class.
 */
public class RMIClientProvider extends AbstractClientProvider {
	private static RMIClientProvider instance = new RMIClientProvider();

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

	@Override
	protected String getEnvName() {
		return "SCISOFT_RMI_PORT";
	}


}

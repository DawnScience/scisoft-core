/*-
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

public abstract class AbstractClientProvider {
	private static final Logger logger = LoggerFactory.getLogger(AbstractClientProvider.class);

	private int port = 0;

	protected AbstractClientProvider() {
	}

	/**
	 * Provide a port number to use. Allows overriding the default, particularly useful if multiple instances of SDA are
	 * controlled from the same Jython/Java JVM.
	 * 
	 * @param portNumber
	 *            new port number, or 0 to use default port resolution mechanism
	 * @throws IllegalArgumentException
	 *             if the port number is < 0
	 */
	public void setPort(int portNumber) throws IllegalArgumentException {
		if (portNumber < 0)
			throw new IllegalArgumentException("Port number must be >= 0");

		port = portNumber;
	}

	/**
	 * Return Port number in use
	 * 
	 * @return port number
	 * @throws IllegalStateException
	 *             if the remote port cannot be determined
	 */
	public int getPort() throws IllegalStateException {
		if (port == 0) {
			String portString = System.getenv(getEnvName());
			if (portString != null) {
				try {
					port = Integer.parseInt(portString);
				} catch (NumberFormatException e) {
					port = 0;
				}
			}
			// It isn't going to work with a port of 0, so throw the error now
			if (port == 0) {
				String msg = "Failed to determine suitable port from " + getEnvName() + ", value was '" + portString
						+ "'";
				logger.error(msg);
				throw new IllegalStateException(msg);
			}
		}

		return port;
	}

	/**
	 * Return name of environment variable to initialise port from.
	 * 
	 * @return env variable name
	 */
	protected abstract String getEnvName();
}

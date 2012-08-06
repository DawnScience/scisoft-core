/*-
 * Copyright 2012 Diamond Light Source Ltd.
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

import java.util.EventObject;

public class RMIServerPortEvent extends EventObject {

	private int port;
	private boolean volatilePort;

	public RMIServerPortEvent(Object arg0, int port, boolean volatilePort) {
		super(arg0);
		this.port = port;
		this.volatilePort = volatilePort;
	}

	public boolean isVolatilePort() {
		return volatilePort;
	}

	public void setVolatilePort(boolean volatilePort) {
		this.volatilePort = volatilePort;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}

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

import java.util.Collection;
import java.util.HashSet;

public class ServerProvider {


	
	private Collection<ServerPortListener> portListeners;
	
	public void addPortListener(ServerPortListener l) {
		if (portListeners==null) portListeners = new HashSet<ServerPortListener>();
		portListeners.add(l);
	}
	public void removePortListener(ServerPortListener l) {
		if (portListeners==null) return;
		portListeners.remove(l);
	}
	protected void firePortListeners(int port, boolean volatilePort) {
		if (portListeners==null) return;
		final ServerPortEvent evt = new ServerPortEvent(this, port, volatilePort);
		for (ServerPortListener l : portListeners) {
			l.portAssigned(evt);
		}
	}

}

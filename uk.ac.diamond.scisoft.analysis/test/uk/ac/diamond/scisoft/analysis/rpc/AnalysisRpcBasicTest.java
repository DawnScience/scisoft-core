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

import org.junit.Assert;
import org.junit.Test;

/**
 * This test is intended to show the basic operation of the RPC Client and Server
 */
public class AnalysisRpcBasicTest {

	private static final int PORT = 8613;
	private static final String CAT_TWO_STRINGS = "cat";
	
	/**
	 * This is the class that will handle the RPC method call
	 */
	private final class CatTwoStringsHandler implements IAnalysisRpcHandler {
		@Override
		public Object run(Object[] unflattened) {
			return (String) unflattened[0] + (String) unflattened[1];
		}
	}

	@Test
	public void testBasicOperation() throws AnalysisRpcException {
		// Create a server
		AnalysisRpcServer analysisRpcServer = new AnalysisRpcServer(PORT);
		try {
			// Register a handler with it, with the given name
			analysisRpcServer.addHandler(CAT_TWO_STRINGS, new CatTwoStringsHandler());
			// Start the server
			analysisRpcServer.start();

			// Create a new client to connect to the server (note that the ports match)
			AnalysisRpcClient analysisRpcClient = new AnalysisRpcClient(PORT);

			// Set up arguments to pass
			Object[] args = new Object[] { "Hello, ", "World!" };
			// make the remote call
			String result = (String) analysisRpcClient.request(CAT_TWO_STRINGS, args);

			// Check our results
			Assert.assertEquals("Hello, World!", result);
		} finally {
			// Shutdown the server (in a finally block just in case the test fails)
			analysisRpcServer.shutdown();
		}
	}
}

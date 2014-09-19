/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.rpc;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is basically the same test as {@link AnalysisRpcBasicTest}, but with dynamically allocated port to listen on.
 * This is achieved by creating a server listening to port 0 and then calling {@link AnalysisRpcServer#getPort()} to
 * find out what port was selected.
 */
public class AnalysisRpcDefaultPortTest {

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
		AnalysisRpcServer analysisRpcServer = new AnalysisRpcServer(0);
		try {
			// Register a handler with it, with the given name
			analysisRpcServer.addHandler(CAT_TWO_STRINGS, new CatTwoStringsHandler());
			// Start the server
			analysisRpcServer.start();

			// Create a new client to connect to the server (note that the ports match)
			AnalysisRpcClient analysisRpcClient = new AnalysisRpcClient(analysisRpcServer.getPort());

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

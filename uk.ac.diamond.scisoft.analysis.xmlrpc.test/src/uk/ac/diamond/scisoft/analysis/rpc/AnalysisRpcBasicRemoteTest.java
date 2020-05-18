/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.rpc;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.Maths;
import org.junit.Assert;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.PythonHelper;
import uk.ac.diamond.scisoft.analysis.PythonHelper.PythonRunInfo;

/**
 * This test is intended to show the basic operation of the RPC Client and Server
 */
public class AnalysisRpcBasicRemoteTest {

	private static final int PORT = 8751;
	private static final String COS = "cos";

	@Test
	public void testBasicRemoteOperation() throws Exception {
		// Start the Python server
		PythonRunInfo server = PythonHelper
				.runPythonFileBackground("src/uk/ac/diamond/scisoft/analysis/rpc/rpcremote.py");

		try {
			Thread.sleep(3000); // add delay to ensure client is receiving

			// Create a new client to connect to the server (note that the ports match)
			AnalysisRpcClient analysisRpcClient = new AnalysisRpcClient(PORT);

			Thread.sleep(3000); // add delay to ensure client is receiving

			// Set up arguments to pass
			Dataset cosInput = DatasetFactory.createRange(IntegerDataset.class, 100);
			// make the remote call
			Dataset cosOutput = (Dataset) analysisRpcClient.request(COS, new Object[] {cosInput});


			double[] cosOutputRaw = (double[])cosOutput.getBuffer();
			double[] cosOutputExpectedRaw = (double[])Maths.cos(cosInput).getBuffer();
			Assert.assertArrayEquals(cosOutputExpectedRaw, cosOutputRaw, 0.000001);
		} finally {
			if (server != null) {
				server.terminate();
				server.getStdout(true);
			}
		}
	}
}

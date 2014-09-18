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

package uk.ac.diamond.scisoft.analysis.rpc;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
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
			// Create a new client to connect to the server (note that the ports match)
			AnalysisRpcClient analysisRpcClient = new AnalysisRpcClient(PORT);

			Thread.sleep(500); // add delay to ensure client is receiving

			// Set up arguments to pass
			Dataset cosInput = DatasetFactory.createRange(100, Dataset.INT32);
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

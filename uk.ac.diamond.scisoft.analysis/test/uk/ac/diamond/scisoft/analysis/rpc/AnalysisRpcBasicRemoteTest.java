/*-
 * Copyright © 2011 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.rpc;

import org.junit.Assert;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.PythonHelper;
import uk.ac.diamond.scisoft.analysis.PythonHelper.PythonRunInfo;
import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Maths;

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
				.runPythonFileBackground("test/uk/ac/diamond/scisoft/analysis/rpc/rpcremote.py");

		try {
			// Create a new client to connect to the server (note that the ports match)
			AnalysisRpcClient analysisRpcClient = new AnalysisRpcClient(PORT);

			// Set up arguments to pass
			AbstractDataset cosInput = AbstractDataset.arange(100, AbstractDataset.INT32);
			// make the remote call
			AbstractDataset cosOutput = (AbstractDataset) analysisRpcClient.request(COS, new Object[] {cosInput});


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

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

public class AnalysisRpcAdvancedTest {
	// Increment port on each use to avoid a Windows issue of a significant
	// slowdown between starting and then restarting a server
	static int PORT = 8615;

	@Test
	public void testMultipleHandlers() throws AnalysisRpcException {
		AnalysisRpcServer analysisRpcServer = null;
		try {
			analysisRpcServer = new AnalysisRpcServer(++PORT);
			analysisRpcServer.start();
			analysisRpcServer.addHandler("cat", new IAnalysisRpcHandler() {

				@Override
				public Object run(Object[] unflattened) {
					return (String) unflattened[0] + (String) unflattened[1];
				}
			});
			analysisRpcServer.addHandler("len", new IAnalysisRpcHandler() {

				@Override
				public Object run(Object[] unflattened) {
					return ((String) unflattened[0] + (String) unflattened[1]).length();
				}
			});

			AnalysisRpcClient analysisRpcClient = new AnalysisRpcClient(PORT);
			String catResult = (String) analysisRpcClient.request("cat", new Object[] { "Hello, ", "World!" });
			Assert.assertEquals("Hello, World!", catResult);
			int lenResult = (Integer) analysisRpcClient.request("len", new Object[] { "Hello, ", "World!" });
			Assert.assertEquals("Hello, World!".length(), lenResult);
		} finally {
			if (analysisRpcServer != null)
				analysisRpcServer.shutdown();
		}
	}

	@Test
	public void testExceptionOnHandlerFlattening() throws AnalysisRpcException {
		AnalysisRpcServer analysisRpcServer = null;
		try {
			analysisRpcServer = new AnalysisRpcServer(++PORT);
			analysisRpcServer.start();
			analysisRpcServer.addHandler("flaterror", new IAnalysisRpcHandler() {

				@Override
				public Object run(Object[] unflattened) {
					// return unflattanble type
					return new Object();
				}
			});

			AnalysisRpcClient analysisRpcClient = new AnalysisRpcClient(PORT);
			// force a flattening exception on the call side
			try {
				analysisRpcClient.request("flaterror", new Object[] { "Hello" });
				Assert.fail("No exception raised");
			} catch (AnalysisRpcException e) {
				Assert.assertFalse(e.getCause() instanceof UnsupportedOperationException);
				Assert.assertEquals(Exception.class, e.getCause().getClass());
			}
		} finally {
			if (analysisRpcServer != null)
				analysisRpcServer.shutdown();
		}
	}

	// As all comms are to the localhost, we want to make sure we time out pretty quickly
	@Test(expected = AnalysisRpcException.class, timeout = 2000)
	public void testConnectionTimesOutQuicklyEnough() throws AnalysisRpcException {
		AnalysisRpcClient analysisRpcClient = new AnalysisRpcClient(++PORT);
		analysisRpcClient.request("doesnotexist", new Object[] { "Hello" });
	}
}

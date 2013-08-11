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
					return ((String) unflattened[0] + (String) unflattened[1])
							.length();
				}
			});

			AnalysisRpcClient analysisRpcClient = new AnalysisRpcClient(PORT);
			String catResult = (String) analysisRpcClient.request("cat",
					new Object[] { "Hello, ", "World!" });
			Assert.assertEquals("Hello, World!", catResult);
			int lenResult = (Integer) analysisRpcClient.request("len",
					new Object[] { "Hello, ", "World!" });
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
			analysisRpcServer.addHandler("flaterror",
					new IAnalysisRpcHandler() {

						@Override
						public Object run(Object[] unflattened) {
							// return unflattanble type
							return new Object();
						}
					});

			AnalysisRpcClient analysisRpcClient = new AnalysisRpcClient(PORT);
			// force a flattening exception on the call side
			try {
				analysisRpcClient
						.request("flaterror", new Object[] { "Hello" });
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

	// As all comms are to the localhost, we want to make sure we time out
	// pretty quickly
	@Test(expected = AnalysisRpcException.class, timeout = 2000)
	public void testConnectionTimesOutQuicklyEnough()
			throws AnalysisRpcException {
		AnalysisRpcClient analysisRpcClient = new AnalysisRpcClient(++PORT);
		analysisRpcClient.request("doesnotexist", new Object[] { "Hello" });
	}

	@Test
	public void testIsAlive() throws AnalysisRpcException {
		AnalysisRpcClient analysisRpcClient = new AnalysisRpcClient(++PORT);
		Assert.assertFalse(analysisRpcClient.isAlive());
		AnalysisRpcServer analysisRpcServer = new AnalysisRpcServer(PORT);
		try {
			analysisRpcServer.start();
			Assert.assertTrue(analysisRpcClient.isAlive());
		} finally {
			analysisRpcServer.shutdown();
		}
	}

	@Test
	public void waitIsAlive() throws AnalysisRpcException {
		AnalysisRpcClient analysisRpcClient = new AnalysisRpcClient(++PORT);
		Assert.assertFalse(analysisRpcClient.isAlive());
		try {
			analysisRpcClient.waitUntilAlive(1000);
			Assert.fail();
		} catch (AnalysisRpcException e) {
			// test passes, waitUntilAlive has raised exception saying not ready
			// yet
		}
		AnalysisRpcServer analysisRpcServer = new AnalysisRpcServer(PORT);
		try {
			analysisRpcServer.start();
			analysisRpcClient.waitUntilAlive(1000);
			Assert.assertTrue(analysisRpcClient.isAlive());
		} finally {
			analysisRpcServer.shutdown();
		}
	}

}

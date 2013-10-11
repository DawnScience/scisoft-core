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
				Assert.assertTrue(e.getCause() instanceof AnalysisRpcRemoteException);
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

	interface TestProxy_Interface {
		public String cat(String a1, String a2) throws AnalysisRpcException;
	}

	@Test
	public void testProxy() throws AnalysisRpcException {
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

			AnalysisRpcClient analysisRpcClient = new AnalysisRpcClient(PORT);
			TestProxy_Interface catObject = analysisRpcClient
					.newProxyInstance(TestProxy_Interface.class);
			String catResult = catObject.cat("Hello, ", "World!");
			Assert.assertEquals("Hello, World!", catResult);
		} finally {
			if (analysisRpcServer != null)
				analysisRpcServer.shutdown();
		}
	}

	interface TestBadProxy_Interface {
		// Missing throws AnalysisRpcException
		public String cat(String a1, String a2);
	}

	/**
	 * This test makes sure that methods on the interface throws
	 * AnalysisRpcException. However, it may be desirable to change this
	 * restriction to a new RuntimeException that does not have this
	 * requirement. The advantage would be that any interface could be
	 * implemented in Python (across the server divide) but that would add an
	 * implication to the user of the interface that the call does not have to
	 * worry about such issues.
	 */
	@Test
	public void testBadProxy() {
		// don't need a real server because this test makes sure the bad proxy
		// fails
		AnalysisRpcClient analysisRpcClient = new AnalysisRpcClient(++PORT);
		TestBadProxy_Interface catObject = analysisRpcClient
				.newProxyInstance(TestBadProxy_Interface.class);
		try {
			catObject.cat("Hello, ", "World!");
			Assert.fail("Exception not thrown as expected");
		} catch (RuntimeException e) {
		}

		try {
			catObject.equals(null);
			Assert.fail("Exception not thrown as expected");
		} catch (RuntimeException e) {
		}

	}

}

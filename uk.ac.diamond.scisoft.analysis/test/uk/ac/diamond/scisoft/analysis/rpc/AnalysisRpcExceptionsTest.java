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


import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class AnalysisRpcExceptionsTest {

	private static final int PORT = 8614;
	
	private static final String CAT_TWO_STRINGS = "cat";
	private static AnalysisRpcServer analysisRpcServer;
	private static AnalysisRpcClient analysisRpcClient;



	@BeforeClass
	public static void setupBeforeClass() throws AnalysisRpcException {
		analysisRpcServer = new AnalysisRpcServer(PORT);
		analysisRpcServer.start();
		analysisRpcServer.addHandler(CAT_TWO_STRINGS, new IAnalysisRpcHandler() {
			
			@Override
			public Object run(Object[] unflattened) {
				return (String)unflattened[0] + (String)unflattened[1];
			}
		});
	
		analysisRpcClient = new AnalysisRpcClient(PORT);		
	}
	
	@AfterClass
	public static void tearDownAfterClass() {
		analysisRpcServer.shutdown();
	}

	
	
	@Test
	public void testBasicOperation() throws AnalysisRpcException {
		String result = (String)analysisRpcClient.request(CAT_TWO_STRINGS, new Object[] {"Hello, ", "World!"});
		Assert.assertEquals("Hello, World!", result);
	}
	
	@Test
	public void testHandlerExceptionOperation() {
		// force an ArrayIndexOutOfBoundsException in the handler and make sure it is raised rather than returned
		// due to flattening, it will be raised as an Exception wrapped in an AnalysisRpcException
		try {
			analysisRpcClient.request(CAT_TWO_STRINGS, new Object[] {"Hello, "});
			Assert.fail("No exception raised");
		} catch (AnalysisRpcException e) {			
			Assert.assertFalse(e.getCause() instanceof ArrayIndexOutOfBoundsException);
			Assert.assertEquals(Exception.class, e.getCause().getClass());
		}
	}
	
	@Test
	public void testInternalExceptionOperation() {
		// force a flattening exception on the call side
		try {
			analysisRpcClient.request(CAT_TWO_STRINGS, new Object[] {new Object()});
			Assert.fail("No exception raised");
		} catch (AnalysisRpcException e) {			
			Assert.assertEquals(UnsupportedOperationException.class, e.getCause().getClass());
		}
	}
	
	@Test
	public void testNoMatchingHandlerException() {
		// force a not found on the call side error
		try {
			analysisRpcClient.request(CAT_TWO_STRINGS + " invalid", new Object[] {"Hello, ", "World!"});
			Assert.fail("No exception raised");
		} catch (AnalysisRpcException e) {			
			Assert.assertFalse(e.getCause() instanceof AnalysisRpcException);
			Assert.assertEquals(Exception.class, e.getCause().getClass());
			Assert.assertEquals("No handler registered for " + CAT_TWO_STRINGS + " invalid", e.getCause().getMessage());
		}
	}
	
}

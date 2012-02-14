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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.AnalysisRpcServerProvider;

public class AnalysisRpcDisableTest {

	private static final String DISABLE_PROP = "uk.ac.diamond.scisoft.analysis.analysisrpcserverprovider.disable";
	private String original_disable;

	@Before
	public void setUp() {
		original_disable = System.getProperty(DISABLE_PROP);
	}
	
	@After
	public void tearDown() {
		if (original_disable == null) {
			System.clearProperty(DISABLE_PROP);
		} else {
			System.setProperty(DISABLE_PROP, original_disable);
		}
	}
	
	private IAnalysisRpcHandler makeHandler() {
		return new IAnalysisRpcHandler() {
			
			@Override
			public Object run(Object[] args) throws AnalysisRpcException {
				Assert.fail("Unreachable");
				return null;
			}
		};
	}

	@Test(expected=AnalysisRpcException.class)
	public void testDisableRPCTrue() throws AnalysisRpcException {
		System.setProperty(DISABLE_PROP, "True");
		AnalysisRpcServerProvider.getInstance().addHandler("should_not_register", makeHandler());
	}

	@Test
	public void testDisableRPCFalse() throws AnalysisRpcException {
		System.setProperty(DISABLE_PROP, "False");
		AnalysisRpcServerProvider.getInstance().addHandler("should_register", makeHandler());
		AnalysisRpcServerProvider.getInstance().addHandler("should_register", null);
	}

	@Test
	public void testDisableRPCClear() throws AnalysisRpcException {
		System.clearProperty(DISABLE_PROP);
		AnalysisRpcServerProvider.getInstance().addHandler("should_register", makeHandler());
		AnalysisRpcServerProvider.getInstance().addHandler("should_register", null);
	}

}

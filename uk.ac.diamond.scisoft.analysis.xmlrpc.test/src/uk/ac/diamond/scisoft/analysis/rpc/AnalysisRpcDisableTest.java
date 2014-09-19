/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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

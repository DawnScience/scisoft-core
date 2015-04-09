/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.rmi;


import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import org.eclipse.dawnsci.analysis.api.RMIServerProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RMIDisableTest {

	private static final String DISABLE_PROP = "uk.ac.diamond.scisoft.analysis.rmiserverprovider.disable";
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
	
	
	@Test(expected=RemoteException.class)
	public void testDisableRMITrue() throws AlreadyBoundException, IOException {
		System.setProperty(DISABLE_PROP, "true");
		RMIServerProvider.getInstance().exportAndRegisterObject("should_not_register", new Remote() {
		});
	}

}

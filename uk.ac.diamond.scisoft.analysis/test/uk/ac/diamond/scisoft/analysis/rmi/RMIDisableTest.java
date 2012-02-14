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

package uk.ac.diamond.scisoft.analysis.rmi;


import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.RMIServerProvider;

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
		System.setProperty(DISABLE_PROP, "True");
		RMIServerProvider.getInstance().exportAndRegisterObject("should_not_register", new Remote() {
		});
	}

}

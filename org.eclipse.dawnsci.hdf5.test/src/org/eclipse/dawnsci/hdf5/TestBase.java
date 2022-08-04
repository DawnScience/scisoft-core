/*-
 * Copyright 2020 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.dawnsci.hdf5;

import org.eclipse.dawnsci.nexus.test.utilities.TestUtils;
import org.junit.BeforeClass;

public class TestBase {
	@BeforeClass
	public static void requireScratchDirectory() throws Exception {
		TestUtils.makeScratchDirectory("test-scratch");
	}
}

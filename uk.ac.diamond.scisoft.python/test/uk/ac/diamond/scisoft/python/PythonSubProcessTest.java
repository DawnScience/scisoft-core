/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.python;

import static org.junit.Assert.*;

import org.junit.Test;

public class PythonSubProcessTest {
	@Test
	public void testSubProcessComms() {
		PythonSubProcess p = new PythonSubProcess();

		assertArrayEquals(new String[] {"Hello World!\n", null}, p.communicate("print \"Hello World!\"\n"));
		assertArrayEquals(new String[] {"Hello World2!\n", null}, p.communicate("print \"Hello World2!\"\n"));

		assertArrayEquals(new String[] {"0\n1\n2\n3\n", null}, p.communicate("for i in range(4): print i\n"));
	}
}

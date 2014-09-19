/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import static org.junit.Assert.fail;

import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.junit.Test;

import uk.ac.diamond.CBFlib.CBFlib;

/**
 *
 */
public class CBFErrorTest {
	static {
		CBFlib.loadLibrary();
	}

	/**
	 * tests a single error
	 */
	@Test
	public void testCBFError() {
		try {
			CBFError.errorChecker(128);
		} catch (ScanFileHolderException e) {
			System.out.print("(expected error message:) " + e);
			return;
		}
		fail("on error 128");
	}

	/**
	 * Tests double error [1024 + 2048]
	 */
	@Test
	public void testDoubleCBFError() {
		try {
			CBFError.errorChecker(3072);
		} catch (ScanFileHolderException e) {
			System.out.print("(expected error message:) " + e);
			return;
		}
		fail("on error 3072");
	}

	/**
	 * Test incorrect error. Argument was plucked out of thin air.
	 */
	@Test
	public void testIncorrectCBFError() {
		try {
			CBFError.errorChecker(42);
		} catch (ScanFileHolderException e) {
			System.out.print("(expected error message:) " + e);
			return;
		}
		fail("on error 42");
	}
}

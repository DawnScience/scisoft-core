/*
 * Copyright © 2011 Diamond Light Source Ltd.
 * Contact :  ScientificSoftware@diamond.ac.uk
 * 
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 * 
 * This software is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this software. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.io;

import static org.junit.Assert.fail;
import gda.analysis.io.ScanFileHolderException;

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

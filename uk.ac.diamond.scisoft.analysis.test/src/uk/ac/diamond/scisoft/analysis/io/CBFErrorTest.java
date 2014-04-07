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

package uk.ac.diamond.scisoft.analysis.io;

import static org.junit.Assert.fail;

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

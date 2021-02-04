/*-
 * Copyright 2021 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.test.operations.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;

public class ProcessingUtilsTest {

	@Test
	public void testScanNumbers() {
		assertEquals(Integer.valueOf(1234), ProcessingUtils.getScanNumber("i1234.nxs"));
		assertEquals(Integer.valueOf(1234), ProcessingUtils.getScanNumber("i01234.nxs"));

		assertEquals("i-foo1234-bar.nxs", ProcessingUtils.getNextScanString("i1234.nxs", 0, "%s-foo%s-bar%s"));
		assertEquals("i-foo01234-bar.nxs", ProcessingUtils.getNextScanString("i01234.nxs", 0, "%s-foo%s-bar%s"));

		assertEquals("i-foo100-bar.nxs", ProcessingUtils.getNextScanString("i99.nxs", 1, "%s-foo%s-bar%s"));
		assertEquals("i-foo100-bar.nxs", ProcessingUtils.getNextScanString("i099.nxs", 1, "%s-foo%s-bar%s"));

		assertEquals("i-foo099-bar.nxs", ProcessingUtils.getNextScanString("i100.nxs", -1, "%s-foo%s-bar%s"));
	}
}

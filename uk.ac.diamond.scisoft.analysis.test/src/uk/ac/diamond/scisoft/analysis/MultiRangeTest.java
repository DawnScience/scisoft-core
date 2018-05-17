/*-
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.regex.Matcher;

import org.junit.Test;

public class MultiRangeTest {

	@Test
	public void testSplit() {
		System.out.println(Arrays.toString("".split(",")));
		System.out.println(Arrays.toString(",".split(",")));
		System.out.println(Arrays.toString("2".split(",")));
		System.out.println(Arrays.toString("2,".split(",")));
		System.out.println(Arrays.toString(",2".split(",")));
		System.out.println(Arrays.toString("1,2".split(",")));
	}

	@Test
	public void testRegExp() {
		printMatch("");
		printMatch("-");
		printMatch("hello");
		printMatch("01");
		printMatch("1");
		printMatch("-1");
		printMatch("1-");
		printMatch("1-3");
		printMatch("0-1");
		printMatch("1--3");
		printMatch("-3--1");
		printMatch("003--1");
		printMatch("0--1");
	}

	private void printMatch(String input) {
		Matcher m = MultiRange.SUBRANGE_PATTERN.matcher(input);
		if (!m.matches()) {
			System.out.println("No match for \"" + input + "\"");
			return;
		}

		int g = m.groupCount();
		System.out.println("\"" + input + "\" matches " + g + " groups");
		for (int i = 0; i < g; i++) {
			System.out.println("\t" + i + ": " + m.group(i + 1));
		}
	}

	@Test
	public void testRanges() {
		MultiRange mr;

		mr = MultiRange.createMultiRange(""); // everything
		assertFalse(mr.contains(4, -1));
		assertTrue(mr.contains(4, 0));
		assertTrue(mr.contains(4, 3));
		assertFalse(mr.contains(4, 4));

		mr = MultiRange.createMultiRange("1"); // second only
		assertFalse(mr.contains(4, -1));
		assertFalse(mr.contains(4, 0));
		assertTrue(mr.contains(4, 1));
		assertFalse(mr.contains(4, 3));
		assertFalse(mr.contains(4, 4));

		mr = MultiRange.createMultiRange("-1"); // last only
		assertFalse(mr.contains(4, -1));
		assertFalse(mr.contains(4, 0));
		assertTrue(mr.contains(4, 3));
		assertFalse(mr.contains(4, 4));

		mr = MultiRange.createMultiRange("-3"); // third last only
		assertFalse(mr.contains(4, -1));
		assertFalse(mr.contains(4, 0));
		assertTrue(mr.contains(4, 1));
		assertFalse(mr.contains(4, 3));
		assertFalse(mr.contains(4, 4));

		mr = MultiRange.createMultiRange("1-"); // 1 onwards
		assertFalse(mr.contains(4, -1));
		assertFalse(mr.contains(4, 0));
		assertTrue(mr.contains(4, 3));
		assertFalse(mr.contains(4, 4));

		mr = MultiRange.createMultiRange("0--2"); // up to second to last
		assertFalse(mr.contains(4, -1));
		assertTrue(mr.contains(4, 0));
		assertTrue(mr.contains(4, 2));
		assertFalse(mr.contains(4, 3));
		assertFalse(mr.contains(4, 4));

		assertEquals(mr, MultiRange.createMultiRange("0 - -2"));
		assertNotEquals(mr, MultiRange.createMultiRange("0-4"));

		mr = MultiRange.createMultiRange("0-3,-2");
		assertFalse(mr.contains(8, -1));
		assertTrue(mr.contains(8, 0));
		assertTrue(mr.contains(8, 3));
		assertFalse(mr.contains(8, 5));
		assertTrue(mr.contains(8, 6));
		assertFalse(mr.contains(8, 7));
		assertEquals(mr, MultiRange.createMultiRange("0- 3, -2"));

		mr = MultiRange.createMultiRange("3-5");
		assertFalse(mr.contains(3, -1));
		assertFalse(mr.contains(3, 0));
		assertFalse(mr.contains(3, 2));
		assertFalse(mr.contains(3, 3));
		assertFalse(mr.contains(3, 4));
		assertFalse(mr.contains(4, -1));
		assertFalse(mr.contains(4, 0));
		assertTrue(mr.contains(4, 3));
		assertFalse(mr.contains(7, -1));
		assertFalse(mr.contains(7, 0));
		assertTrue(mr.contains(7, 3));
		assertTrue(mr.contains(7, 5));
		assertFalse(mr.contains(7, 6));
	}

	@Test
	public void testNegatedRanges() {
		MultiRange mr;

		mr = MultiRange.createMultiRange("!-1"); // all bar last
		assertFalse(mr.contains(4, -1));
		assertTrue(mr.contains(4, 0));
		assertFalse(mr.contains(4, 3));
		assertFalse(mr.contains(4, 4));

		mr = MultiRange.createMultiRange("!1"); // all bar second
		assertFalse(mr.contains(4, -1));
		assertTrue(mr.contains(4, 0));
		assertFalse(mr.contains(4, 1));
		assertTrue(mr.contains(4, 3));
		assertFalse(mr.contains(4, 4));

		mr = MultiRange.createMultiRange("!-3"); // all bar third last only
		assertFalse(mr.contains(4, -1));
		assertTrue(mr.contains(4, 0));
		assertFalse(mr.contains(4, 1));
		assertTrue(mr.contains(4, 3));
		assertFalse(mr.contains(4, 4));

		mr = MultiRange.createMultiRange("!1-"); // first only
		assertFalse(mr.contains(4, -1));
		assertTrue(mr.contains(4, 0));
		assertFalse(mr.contains(4, 3));
		assertFalse(mr.contains(4, 4));

		mr = MultiRange.createMultiRange("!0--2"); // last only
		assertFalse(mr.contains(4, -1));
		assertFalse(mr.contains(4, 0));
		assertFalse(mr.contains(4, 2));
		assertTrue(mr.contains(4, 3));
		assertFalse(mr.contains(4, 4));

		assertEquals(mr, MultiRange.createMultiRange("!0 - -2"));
		assertNotEquals(mr, MultiRange.createMultiRange("!0-4"));

		mr = MultiRange.createMultiRange("0-3,!-2");
		assertFalse(mr.contains(8, -1));
		assertTrue(mr.contains(8, 0));
		assertTrue(mr.contains(8, 3));
		assertTrue(mr.contains(8, 5));
		assertFalse(mr.contains(8, 6));
		assertTrue(mr.contains(8, 7));
		assertEquals(mr, MultiRange.createMultiRange("0- 3, !-2"));

		mr = MultiRange.createMultiRange("!3-5"); // not 3,4,5
		assertFalse(mr.contains(3, -1));
		assertTrue(mr.contains(3, 0));
		assertTrue(mr.contains(3, 2));
		assertFalse(mr.contains(3, 3));
		assertFalse(mr.contains(3, 4));
		assertFalse(mr.contains(4, -1));
		assertTrue(mr.contains(4, 0));
		assertFalse(mr.contains(4, 3));
		assertFalse(mr.contains(7, -1));
		assertTrue(mr.contains(7, 0));
		assertFalse(mr.contains(7, 3));
		assertFalse(mr.contains(7, 5));
		assertTrue(mr.contains(7, 6));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testOverlap() {
		MultiRange.createMultiRange("0-3,3-5");
	}
}

/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;

public class VersionSortTest {

	@Test
	public void testVersionCompare1() {
		List<String> stringsExpected = IntStream.range(0, 20).mapToObj(i -> String.format("Column %d", i)).collect(Collectors.toList());
		List<String> stringsActual = new ArrayList<>(stringsExpected);
		Collections.shuffle(stringsActual);
		stringsActual.sort(VersionSort::versionCompare);
		Assert.assertEquals(stringsExpected, stringsActual);
	}

	@Test
	public void testVersionCompare2() {
		List<String> stringsExpected = Arrays.asList(new String[]{"000", "00", "0", "01", "010", "09", "1", "9", "10"});
		List<String> stringsActual = new ArrayList<>(stringsExpected);
		Collections.shuffle(stringsActual);
		stringsActual.sort(VersionSort::versionCompare);
		Assert.assertEquals(stringsExpected, stringsActual);
	}
}

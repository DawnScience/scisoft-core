/*-
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.utils;

import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.junit.Assert;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.utils.I21ScanUtils.I21ScanCollection;

public class I21ScanUtilsTest {
	final static String TestFileFolder = "testfiles/gda/analysis/io/i21/";

	@Test
	public void testI21 () throws ScanFileHolderException {
		I21ScanCollection ssc = checkFile(true, TestFileFolder + "i21-13.nxs");
		I21ScanCollection esc = checkFile(false, TestFileFolder + "i21-14.nxs");

		Assert.assertEquals(3, ssc.getID(false, 0));
		Assert.assertEquals(4, ssc.getID(true, 0));

		Assert.assertArrayEquals(ssc.getIDs(false), esc.getIDs(false));
		Assert.assertArrayEquals(ssc.getIDs(true), esc.getIDs(true));

	}

	private I21ScanCollection checkFile(boolean isSample, String file) throws ScanFileHolderException {
		I21ScanCollection sc = I21ScanUtils.getI21ScanCollection(file);
		Assert.assertTrue(sc.isSample() == isSample);
		Assert.assertTrue(sc.isElasticAfter());
		Assert.assertEquals(1, sc.getRank());
		Assert.assertEquals(13, sc.getSize());
		Assert.assertArrayEquals(new int[] {13}, sc.getShape());
		Assert.assertArrayEquals(new String[] {"qval"}, sc.getScanNames());

		return sc;
	}
}

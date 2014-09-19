/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.io.File;

import junit.framework.Assert;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.junit.Test;

public class NexusLoaderThreadTest extends LoaderThreadTestBase {

	private static String filename = "testfiles/gda/analysis/io/NexusLoaderTest/FeKedge_1_15.nxs";

	@Override
	@Test
	public void testInTestThread() throws Exception {
		super.testInTestThread();
	}

	@Test
	public void testWithThreads() throws Exception {
		super.testWithNThreads(2);
	}

	@Override
	@Test
	public void testWithTenThreads() throws Exception {
		super.testWithTenThreads();
	}

	@Override
	public void doTestOfDataSet(int threadIndex) throws Exception {

		Assert.assertTrue(new File(filename).canRead());

		final IDataHolder dh = LoaderFactory.getData(filename, null);
		Assert.assertTrue(dh.getLazyDataset("/entry1/counterTimer01/lnI0It").getSize() == 489);
	}
}

/*-
 * Copyright © 2010 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.io.File;

import junit.framework.Assert;

import org.junit.Test;

public class NexusLoaderThreadTest extends LoaderThreadTestBase {
	
	private static String filename = "testfiles/gda/analysis/io/NexusLoaderTest/FeKedge_1_15.nxs";

	@Override
	@Test
	public void testInTestThread() throws Exception{
		super.testInTestThread();
	}
	
	@Override
	@Test
	public void testWithTenThreads() throws Exception{
		super.testWithTenThreads();
	}

	@Override
	public void doTestOfDataSet(int threadIndex) throws Exception {
		
		Assert.assertTrue(new File(filename).canRead());
        
        final DataHolder dh = LoaderFactory.getData(filename, null);
        Assert.assertTrue(dh.getDataset("counterTimer01.lnI0It").getSize()==489);
	}
}

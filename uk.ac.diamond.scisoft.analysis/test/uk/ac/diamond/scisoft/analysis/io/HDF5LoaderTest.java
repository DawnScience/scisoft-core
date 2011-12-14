/*-
 * Copyright © 2011 Diamond Light Source Ltd.
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gda.analysis.io.ScanFileHolderException;
import gda.util.TestUtils;

import java.util.List;

import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.ILazyDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Slice;
import uk.ac.diamond.scisoft.analysis.hdf5.HDF5Dataset;
import uk.ac.diamond.scisoft.analysis.hdf5.HDF5File;
import uk.ac.diamond.scisoft.analysis.hdf5.HDF5Group;
import uk.ac.diamond.scisoft.analysis.hdf5.HDF5Node;
import uk.ac.diamond.scisoft.analysis.hdf5.HDF5NodeLink;

public class HDF5LoaderTest {
	final static String TestFileFolder = "testfiles/gda/analysis/io/NexusLoaderTest/";

	private void checkDataset(String name, IDataset data, int[] expectedShape) {
		int[] shape = data.getShape();
		assertEquals("Rank of " + name, expectedShape.length, shape.length);
		for (int i = 0; i < expectedShape.length; i++)
			assertEquals("Dim of " + name, expectedShape[i], shape[i]);
	}

	@Test
	public void testLoadingTest() throws ScanFileHolderException {
		String n = TestFileFolder + "testlinks.nxs";
		HDF5Loader l = new HDF5Loader(n);

		HDF5File tree = l.loadTree(null);
		System.out.println(tree.getNodeLink());

		List<ILazyDataset> list;
		IDataset dataset;
		String name;

		// original
		name = "original";
		list = tree.getGroup().getDatasets("d1");
		assertEquals("Number of " + name, 1, list.size());
		dataset = list.get(0).getSlice();
		checkDataset(name, dataset, new int[] {25, 3});
		assertEquals("Value in " + name, 1, dataset.getInt(0,1));
		assertEquals("Value in " + name, 5, dataset.getInt(1,2));
		assertEquals("Value in " + name, 37, dataset.getInt(12,1));

		// hard link
		name = "hard link";
		list = tree.getGroup().getDatasets("d_hl");
		assertEquals("Number of " + name, 1, list.size());
		dataset = list.get(0).getSlice();
		checkDataset(name, dataset, new int[] {25, 3});
		assertEquals("Value in " + name, 1, dataset.getInt(0,1));
		assertEquals("Value in " + name, 5, dataset.getInt(1,2));
		assertEquals("Value in " + name, 37, dataset.getInt(12,1));

		// soft link
		name = "soft link";
		list = tree.getGroup().getDatasets("d_sl");
		assertEquals("Number of " + name, 1, list.size());
		dataset = list.get(0).getSlice();
		checkDataset(name, dataset, new int[] {25, 3});
		assertEquals("Value in " + name, 1, dataset.getInt(0,1));
		assertEquals("Value in " + name, 5, dataset.getInt(1,2));
		assertEquals("Value in " + name, 37, dataset.getInt(12,1));

		// external link
		name = "external link";
		list = tree.getGroup().getDatasets("d_el");
		assertEquals("Number of " + name, 1, list.size());
		dataset = list.get(0).getSlice();
		checkDataset(name, dataset, new int[] {2, 5});
		assertEquals("Value of " + name, 1., dataset.getDouble(0,1), 1e-8);
		assertEquals("Value of " + name, 9., dataset.getDouble(1,4), 1e-8);

		// NAPI mount
		name = "NAPI";
		list = tree.getGroup().getDatasets("extdst");
		assertEquals("Number of " + name, 1, list.size());
		dataset = list.get(0).getSlice();
		checkDataset(name, dataset, new int[] {2, 5});
		assertEquals("Value of " + name, 1., dataset.getDouble(0,1), 1e-8);
		assertEquals("Value of " + name, 9., dataset.getDouble(1,4), 1e-8);

		System.out.println(tree.findNodeLink("/entry1/to/this/level"));


	}

	@Test
	public void testLoading() throws ScanFileHolderException {
		String n = TestFileFolder + "FeKedge_1_15.nxs";
		HDF5Loader l = new HDF5Loader(n);

		HDF5File tree = l.loadTree(null);
		System.out.println(tree.getNodeLink());

		HDF5NodeLink nl;

		nl = tree.findNodeLink("/");
		assertTrue("Not a group", nl.isDestinationAGroup());
		assertTrue("Wrong name", nl.getName().equals("/"));

		nl = tree.findNodeLink("/entry1");
		assertTrue("Not a group", nl.isDestinationAGroup());
		assertTrue("Wrong name", nl.getName().equals("entry1"));

		nl = tree.findNodeLink("/entry1/FFI0");
		assertTrue("Not a group", nl.isDestinationAGroup());
		assertTrue("Wrong name", nl.getName().equals("FFI0"));

		nl = tree.findNodeLink("/entry1/FFI0/Energy");
		assertTrue("Not a group", nl.isDestinationADataset());
		assertTrue("Wrong name", nl.getName().equals("Energy"));

		nl = tree.getGroup().getNodeLink("entry1");
		System.out.println(nl);

		nl = ((HDF5Group) nl.getDestination()).getNodeLink("FFI0");
		System.out.println(nl);

		String name = "Energy";
		nl = ((HDF5Group) nl.getDestination()).getNodeLink(name);
		System.out.println(nl);

		List<ILazyDataset> list = tree.getGroup().getDatasets(name);
		assertEquals("Number of " + name, 1, list.size());
		IDataset dataset = list.get(0).getSlice();
		checkDataset(name, dataset, new int[] {489});
		assertEquals("Value of " + name, 6922, dataset.getDouble(2), 6922*1e-8);
		assertEquals("Value of " + name, 7944.5, dataset.getDouble(479), 7944.5*1e-8);
		nl = tree.findNodeLink("/entry1/user01/username");
		System.out.println(nl);
		HDF5Node nd = nl.getDestination();
		assertTrue("Dataset node", nd instanceof HDF5Dataset);
		HDF5Dataset dn = (HDF5Dataset) nd;
		assertTrue("String dataset", dn.isString());
		AbstractDataset a = (AbstractDataset) dn.getDataset();
		assertEquals("Username", "rjw82", a.getString(0));
	}

	@Test
	public void testLoadingMetadata() throws Exception {
		String n = TestFileFolder + "FeKedge_1_15.nxs";
		HDF5Loader l = new HDF5Loader(n);

		IMetaData md = l.loadFile().getMetadata();

		System.out.println(md.getMetaNames());
		assertTrue("Wrong version", md.getMetaValue("/@NeXus_version").equals("4.2.0"));

		assertTrue("Wrong axis value", md.getMetaValue("/entry1/FFI0/Energy@axis").equals("1"));

		assertTrue("Wrong name", md.getMetaValue("/entry1/instrument/source/name").equals("DLS"));
		assertTrue("Wrong voltage", md.getMetaValue("/entry1/instrument/source/voltage").equals("-1000.0000"));

	}

	@Test
	public void testLoadingChunked() throws ScanFileHolderException {
		final String n = TestUtils.getGDALargeTestFilesLocation()+"/NexusUITest/sino.h5";

		HDF5Loader l = new HDF5Loader(n);

		HDF5File tree = l.loadTree(null);
		HDF5NodeLink nl;
		nl = tree.findNodeLink("/RawDCT/data");
		HDF5Node nd = nl.getDestination();
		assertTrue("Dataset node", nd instanceof HDF5Dataset);
		HDF5Dataset dn = (HDF5Dataset) nd;

		AbstractDataset ad;
		double x;

		// slice with chunks
		ad = (AbstractDataset) dn.getDataset().getSlice(new Slice(1), new Slice(1), null);
		checkDataset("data", ad, new int[] {1, 1, 1481});
		x = ((Number) ad.sum()).doubleValue();
		System.err.println(x);
		assertEquals("Value of sum", 164.12514, x, x*1e-5);

		ad = (AbstractDataset) dn.getDataset().getSlice(new Slice(1), new Slice(null, null, 3), null);
		checkDataset("data", ad, new int[] {1, 75, 1481});
		x = ((Number) ad.sum()).doubleValue();
		System.err.println(x);
		assertEquals("Value of sum", 40271.562, x, x*1e-5);

		ad = (AbstractDataset) dn.getDataset().getSlice(new Slice(1), new Slice(null, null, 3), new Slice(2));
		checkDataset("data", ad, new int[] {1, 75, 2});
		x = ((Number) ad.sum()).doubleValue();
		System.err.println(x);
		assertEquals("Value of sum", 3.7149904, x, x*1e-5);

		ad = (AbstractDataset) dn.getDataset().getSlice(new Slice(null, null, 2), new Slice(1), null);
		checkDataset("data", ad, new int[] {31, 1, 1481});
		x = ((Number) ad.sum()).doubleValue();
		System.err.println(x);
		assertEquals("Value of sum", 10522.864, x, x*1e-5);

		ad = (AbstractDataset) dn.getDataset().getSlice(new Slice(null, null, 2), new Slice(null, null, 3), null);
		checkDataset("data", ad, new int[] {31, 75, 1481});
		x = ((Number) ad.sum()).doubleValue();
		System.err.println(x);
		assertEquals("Value of sum", 1640010.1, x, x*1e-3);

		ad = (AbstractDataset) dn.getDataset().getSlice(new Slice(null, null, 2), new Slice(null, null, 3), new Slice(2));
		checkDataset("data", ad, new int[] {31, 75, 2});
		x = ((Number) ad.sum()).doubleValue();
		System.err.println(x);
		assertEquals("Value of sum", 137.25012, x, x*1e-5);

		// slice across chunks
		ad = (AbstractDataset) dn.getDataset().getSlice(new Slice(null, null, 2), new Slice(null, null, 3), new Slice(1,2));
		checkDataset("data", ad, new int[] {31, 75, 1});
		x = ((Number) ad.sum()).doubleValue();
		System.err.println(x);
		assertEquals("Value of sum", 64.191261, x, x*1e-5);

	}

	@Test
	public void testCanonicalization() {
		String[] before = { "/asd/sdf/dfg/../ds/../../gfd", "/asd/asd/../as", "/asd/as/.././bad", "/asd/..", "/abal/." };
		String[] after = { "/asd/gfd", "/asd/as", "/asd/bad", "/", "/abal" };

		for (int i = 0; i < before.length; i++)
			assertEquals("Path", after[i], HDF5File.canonicalizePath(before[i]));
	}
	
	@Test
	public void testScanFileHolderLoading() throws ScanFileHolderException {
		String n = TestFileFolder + "FeKedge_1_15.nxs";
		HDF5Loader l = new HDF5Loader(n);
		DataHolder dh = l.loadFile();
		assertEquals("File does not have the correct number of datasets", 51, dh.getNames().length);
		if(dh.contains("/entry1/xspress2system/data")) {
			ILazyDataset data = dh.getLazyDataset("/entry1/xspress2system/data");
			assertEquals("Dataset is not the right shape", 3, data.getShape().length);
			assertEquals("Dataset dimention 0 is not of the correct shape", 489, data.getShape()[0]);
			assertEquals("Dataset dimention 1 is not of the correct shape", 1, data.getShape()[1]);
			assertEquals("Dataset dimention 2 is not of the correct shape", 64, data.getShape()[2]);
		}
	}
}

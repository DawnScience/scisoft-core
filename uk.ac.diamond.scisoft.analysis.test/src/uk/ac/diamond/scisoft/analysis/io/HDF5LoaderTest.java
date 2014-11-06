/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ncsa.hdf.object.FileFormat;
import ncsa.hdf.object.h5.H5File;

import org.apache.commons.math3.complex.Complex;
import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.dataset.Slice;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.metadata.IMetadata;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.analysis.api.tree.NodeLink;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.dataset.impl.ComplexDoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.StringDataset;
import org.eclipse.dawnsci.analysis.tree.impl.TreeImpl;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.TestUtils;

public class HDF5LoaderTest {
	final static String TestFileFolder = "testfiles/gda/analysis/io/NexusLoaderTest/";

	private void checkDataset(String name, IDataset data, int[] expectedShape) {
		int[] shape = data.getShape();
		assertEquals("Rank of " + name, expectedShape.length, shape.length);
		for (int i = 0; i < expectedShape.length; i++)
			assertEquals("Dim of " + name, expectedShape[i], shape[i]);
	}

	@Test
	public void testLoadingSpeed() {
		testLoadingSpeed(false);
		testLoadingSpeed(true);
	}

	private void testLoadingSpeed(boolean async) {
		List<Long> ourTimes = new ArrayList<Long>();
		List<Long> theirTimes = new ArrayList<Long>();
		String name = TestFileFolder + "manygroups.h5";
//		String name = "/dls/sci-scratch/ExampleData/NeXus/XPDSi7x7_2010-07-08_23-00-50.nxs";

		for (int i = 0; i < 3; i++) {
			long start;

			start = -System.currentTimeMillis();
			try {
				HDF5Loader l = new HDF5Loader(name);
				l.setAsyncLoad(async);
				l.loadTree(null);
			} catch (ScanFileHolderException e) {
			}
			start += System.currentTimeMillis();
			ourTimes.add(start);

			start = -System.currentTimeMillis();
			try {
				new H5File(name, FileFormat.READ).open();
			} catch (Exception e) {
			}
			start += System.currentTimeMillis();
			theirTimes.add(start);
		}

		Collections.sort(ourTimes);
		Collections.sort(theirTimes);
		System.out.printf("Load took %d ms cf %d ms\n", ourTimes.get(0), theirTimes.get(0));
	}

	@Test
	public void testLoadingStrings() throws ScanFileHolderException {
		String n = TestFileFolder + "strings1d.h5";
		HDF5Loader l = new HDF5Loader(n);

		@SuppressWarnings("unused")
		Tree tree = l.loadTree(null);

		n = TestFileFolder + "strings2d.h5";
		l = new HDF5Loader(n);

		tree = l.loadTree(null);
	}

	@Test
	public void testLoadingTest() throws ScanFileHolderException {
		testLoadingTest(false);
		testLoadingTest(true);
	}

	private void testLoadingTest(boolean async) throws ScanFileHolderException {
		String n = TestFileFolder + "testlinks.nxs";
		HDF5Loader l = new HDF5Loader(n);
		l.setAsyncLoad(async);

		Tree tree = l.loadTree(null);
		System.out.println(tree.getNodeLink());

		List<ILazyDataset> list;
		IDataset dataset;
		String name;

		// original
		name = "original";
		list = tree.getGroupNode().getDatasets("d1");
		assertEquals("Number of " + name, 1, list.size());
		dataset = list.get(0).getSlice();
		checkDataset(name, dataset, new int[] { 25, 3 });
		assertEquals("Value in " + name, 1, dataset.getInt(0, 1));
		assertEquals("Value in " + name, 5, dataset.getInt(1, 2));
		assertEquals("Value in " + name, 37, dataset.getInt(12, 1));

		// hard link
		name = "hard link";
		list = tree.getGroupNode().getDatasets("d_hl");
		assertEquals("Number of " + name, 1, list.size());
		dataset = list.get(0).getSlice();
		checkDataset(name, dataset, new int[] { 25, 3 });
		assertEquals("Value in " + name, 1, dataset.getInt(0, 1));
		assertEquals("Value in " + name, 5, dataset.getInt(1, 2));
		assertEquals("Value in " + name, 37, dataset.getInt(12, 1));

		// soft link
		name = "soft link";
		list = tree.getGroupNode().getDatasets("d_sl");
		assertEquals("Number of " + name, 1, list.size());
		dataset = list.get(0).getSlice();
		checkDataset(name, dataset, new int[] { 25, 3 });
		assertEquals("Value in " + name, 1, dataset.getInt(0, 1));
		assertEquals("Value in " + name, 5, dataset.getInt(1, 2));
		assertEquals("Value in " + name, 37, dataset.getInt(12, 1));

		// external link
		name = "external link";
		list = tree.getGroupNode().getDatasets("d_el");
		assertEquals("Number of " + name, 1, list.size());
		dataset = list.get(0).getSlice();
		checkDataset(name, dataset, new int[] { 2, 5 });
		assertEquals("Value of " + name, 1., dataset.getDouble(0, 1), 1e-8);
		assertEquals("Value of " + name, 9., dataset.getDouble(1, 4), 1e-8);

		// NAPI mount
		name = "NAPI";
		list = tree.getGroupNode().getDatasets("extdst");
		assertEquals("Number of " + name, 1, list.size());
		dataset = list.get(0).getSlice();
		checkDataset(name, dataset, new int[] { 2, 5 });
		assertEquals("Value of " + name, 1., dataset.getDouble(0, 1), 1e-8);
		assertEquals("Value of " + name, 9., dataset.getDouble(1, 4), 1e-8);

		System.out.println(tree.findNodeLink("/entry1/to/this/level"));
	}

	@Test
	public void testLoadingNames() throws ScanFileHolderException {
		testLoadingNames(false);
		testLoadingNames(true);
	}
	

	private void testLoadingNames(boolean async) throws ScanFileHolderException {
		final String n = TestUtils.getGDALargeTestFilesLocation() + "/327.nxs";
		HDF5Loader l = new HDF5Loader(n);
		l.setAsyncLoad(async);

		Tree tree = l.loadTree(null);
		System.out.println(tree.getNodeLink());
		for (NodeLink nl : tree.getGroupNode())
			System.out.println(nl);

		GroupNode g = tree.getGroupNode().getGroupNode("entry1");
		assertEquals("Group is wrongly named" , "/entry1/EDXD_Element_00", g.getNodeLink("EDXD_Element_00").getFullName());
		g = g.getGroupNode("EDXD_Element_00");
		assertEquals("Attribute is wrongly named" , "/entry1/EDXD_Element_00/a@axis", g.getDataNode("a").getAttribute("axis").getFullName());
	}

	@Test
	public void testLoading() throws ScanFileHolderException {
		String n = TestFileFolder + "FeKedge_1_15.nxs";
		HDF5Loader l = new HDF5Loader(n);

		Tree tree = l.loadTree(null);
		System.out.println(tree.getNodeLink());

		NodeLink nl;

		nl = tree.findNodeLink("/");
		assertTrue("Not a group", nl.isDestinationGroup());
		assertTrue("Wrong name", nl.getName().equals("/"));

		nl = tree.findNodeLink("/entry1");
		assertTrue("Not a group", nl.isDestinationGroup());
		assertTrue("Wrong name", nl.getName().equals("entry1"));

		nl = tree.findNodeLink("/entry1/FFI0");
		assertTrue("Not a group", nl.isDestinationGroup());
		assertTrue("Wrong name", nl.getName().equals("FFI0"));

		nl = tree.findNodeLink("/entry1/FFI0/Energy");
		assertTrue("Not a group", nl.isDestinationData());
		assertTrue("Wrong name", nl.getName().equals("Energy"));

		nl = tree.getGroupNode().getNodeLink("entry1");
		System.out.println(nl);

		nl = ((GroupNode) nl.getDestination()).getNodeLink("FFI0");
		System.out.println(nl);

		String name = "Energy";
		nl = ((GroupNode) nl.getDestination()).getNodeLink(name);
		System.out.println(nl);

		List<ILazyDataset> list = tree.getGroupNode().getDatasets(name);
		assertEquals("Number of " + name, 1, list.size());
		IDataset dataset = list.get(0).getSlice();
		checkDataset(name, dataset, new int[] { 489 });
		assertEquals("Value of " + name, 6922, dataset.getDouble(2), 6922 * 1e-8);
		assertEquals("Value of " + name, 7944.5, dataset.getDouble(479), 7944.5 * 1e-8);
		nl = tree.findNodeLink("/entry1/user01/username");
		System.out.println(nl);
		Node nd = nl.getDestination();
		assertTrue("Dataset node", nd instanceof DataNode);
		DataNode dn = (DataNode) nd;
		assertTrue("String dataset", dn.isString());
		Dataset a = (Dataset) dn.getDataset();
		assertEquals("Username", "rjw82", a.getString(0));
	}

	@Test
	public void testLoadingMetadata() throws Exception {
		String n = TestFileFolder + "FeKedge_1_15.nxs";
		HDF5Loader l = new HDF5Loader(n);

		IMetadata md = l.loadFile().getMetadata();

		System.out.println(md.getMetaNames());
		assertTrue("Wrong version", md.getMetaValue("/@NeXus_version").equals("4.2.0"));

		assertTrue("Wrong axis value", md.getMetaValue("/entry1/FFI0/Energy@axis").equals("1"));

		assertTrue("Wrong name", md.getMetaValue("/entry1/instrument/source/name").equals("DLS"));
		assertTrue("Wrong voltage", md.getMetaValue("/entry1/instrument/source/voltage").equals("-1000.0000"));

	}

	@Test
	public void testLoadingChunked() throws ScanFileHolderException {

		final String n = TestUtils.getGDALargeTestFilesLocation() + "/NexusUITest/sino.h5";
		long timeAtStartms = System.currentTimeMillis();

		HDF5Loader l = new HDF5Loader(n);

		Tree tree = l.loadTree(null);
		NodeLink nl;
		nl = tree.findNodeLink("/RawDCT/data");
		Node nd = nl.getDestination();
		assertTrue("Dataset node", nd instanceof DataNode);
		DataNode dn = (DataNode) nd;

		Dataset ad;
		double x;

		// slice with chunks
		ad = (Dataset) dn.getDataset().getSlice(new Slice(1), new Slice(1), null);
		checkDataset("data", ad, new int[] { 1, 1, 1481 });
		x = ((Number) ad.sum()).doubleValue();
		System.err.println(x);
		assertEquals("Value of sum", 164.12514, x, x * 1e-5);

		ad = (Dataset) dn.getDataset().getSlice(new Slice(1), new Slice(null, null, 3), null);
		checkDataset("data", ad, new int[] { 1, 75, 1481 });
		x = ((Number) ad.sum()).doubleValue();
		System.err.println(x);
		assertEquals("Value of sum", 40271.562, x, x * 1e-5);

		ad = (Dataset) dn.getDataset().getSlice(new Slice(1), new Slice(null, null, 3), new Slice(2));
		checkDataset("data", ad, new int[] { 1, 75, 2 });
		x = ((Number) ad.sum()).doubleValue();
		System.err.println(x);
		assertEquals("Value of sum", 3.7149904, x, x * 1e-5);

		ad = (Dataset) dn.getDataset().getSlice(new Slice(null, null, 2), new Slice(1), null);
		checkDataset("data", ad, new int[] { 31, 1, 1481 });
		x = ((Number) ad.sum()).doubleValue();
		System.err.println(x);
		assertEquals("Value of sum", 10522.864, x, x * 1e-5);

		ad = (Dataset) dn.getDataset().getSlice(new Slice(null, null, 2), new Slice(null, null, 3), null);
		checkDataset("data", ad, new int[] { 31, 75, 1481 });
		x = ((Number) ad.sum()).doubleValue();
		System.err.println(x);
		assertEquals("Value of sum", 1640010.1, x, x * 1e-3);

		ad = (Dataset) dn.getDataset().getSlice(new Slice(null, null, 2), new Slice(null, null, 3), new Slice(2));
		checkDataset("data", ad, new int[] { 31, 75, 2 });
		x = ((Number) ad.sum()).doubleValue();
		System.err.println(x);
		assertEquals("Value of sum", 137.25012, x, x * 1e-5);

		// slice across chunks
		ad = (Dataset) dn.getDataset().getSlice(new Slice(null, null, 2), new Slice(null, null, 3), new Slice(1, 2));
		checkDataset("data", ad, new int[] { 31, 75, 1 });
		x = ((Number) ad.sum()).doubleValue();
		System.err.println(x);
		assertEquals("Value of sum", 64.191261, x, x * 1e-5);
		long timeTaken = System.currentTimeMillis() - timeAtStartms;
		System.out.printf("Time taken = %d ms\n", timeTaken);
		assertTrue(timeTaken < 10000);
	}

	@Test
	public void testLoadingChunkedSpeed() throws Exception {
		final String n = TestUtils.getGDALargeTestFilesLocation() + "/NexusUITest/3dDataChunked.nxs";
		long timeAtStartms = System.currentTimeMillis();

		HDF5Loader.loadData(n, "entry/instrument/detector/data", new int[] { 0, 0, 0 }, new int[] { 1, 1795, 2069 },
				new int[] { 1, 1, 1 }, -1, false);
		long timeTaken = System.currentTimeMillis() - timeAtStartms;
		System.out.printf("Time taken = %d ms\n", timeTaken);
		assertTrue(timeTaken < 10000);
	}
	
	
	@Test
	public void testCanonicalization() {
		String[] before = { "/asd/sdf/dfg/../ds/../../gfd", "/asd/asd/../as", "/asd/as/.././bad", "/asd/..", "/abal/." };
		String[] after = { "/asd/gfd", "/asd/as", "/asd/bad", "/", "/abal" };

		for (int i = 0; i < before.length; i++)
			assertEquals("Path", after[i], TreeImpl.canonicalizePath(before[i]));
	}

	@Test
	public void testScanFileHolderLoading() throws ScanFileHolderException {
		String n = TestFileFolder + "FeKedge_1_15.nxs";
		HDF5Loader l = new HDF5Loader(n);
		DataHolder dh = l.loadFile();
		assertEquals("File does not have the correct number of datasets", 51, dh.getNames().length);
		assertTrue(dh.contains("/entry1/xspress2system/data"));
		ILazyDataset data = dh.getLazyDataset("/entry1/xspress2system/data");
		int[] shape = data.getShape();
		assertEquals("Dataset is not the right shape", 3, shape.length);
		assertEquals("Dataset dimension 0 is not of the correct shape", 489, shape[0]);
		assertEquals("Dataset dimension 1 is not of the correct shape", 1, shape[1]);
		assertEquals("Dataset dimension 2 is not of the correct shape", 64, shape[2]);
	}

	@Test
	public void testLoadingCompoundDatatype() throws ScanFileHolderException {
		String n = TestFileFolder + "h5py_complex.h5";
		HDF5Loader l = new HDF5Loader(n);
		DataHolder dh = l.loadFile();
		assertEquals("File does not have the correct number of datasets", 1, dh.getNames().length);
		assertTrue(dh.contains("/complex_example"));
		ILazyDataset data = dh.getLazyDataset("/complex_example");
		int[] shape = data.getShape();
		assertEquals("Dataset is not the right shape", 2, shape.length);
		assertEquals("Dataset dimension 0 is not of the correct shape", 50, shape[0]);
		assertEquals("Dataset dimension 1 is not of the correct shape", 50, shape[1]);
		IDataset s = data.getSlice(null, new int[] {1, 1}, null);
		assertTrue(s instanceof ComplexDoubleDataset);
		ComplexDoubleDataset cs = (ComplexDoubleDataset) s;
		Complex cx = cs.getComplexAbs(0);
		assertEquals(2.18634188175992, cx.getReal(), 1e-15);
		assertEquals(-0.01617389291212438, cx.getImaginary(), 1e-15);

		s = data.getSlice(new int[] {1, 3}, new int[] {10, 10}, new int[] {2, 3});
		assertTrue(s instanceof ComplexDoubleDataset);
		cs = (ComplexDoubleDataset) s;
		assertEquals("Dataset dimension 0 is not of the correct shape", 5, cs.getShapeRef()[0]);
		assertEquals("Dataset dimension 1 is not of the correct shape", 3, cs.getShapeRef()[1]);
		cx = cs.getComplex(0, 0);
		assertEquals(0.22884877031898887, cx.getReal(), 1e-15);
		assertEquals(0.19673784135439948, cx.getImaginary(), 1e-15);
		cx = cs.getComplex(4, 2);
		assertEquals(0.6922704317579508, cx.getReal(), 1e-15);
		assertEquals(-1.8087566023531674, cx.getImaginary(), 1e-15);
	}

	@Test
	public void testLoadingDatasets() throws ScanFileHolderException {
		String n = TestFileFolder + "FeKedge_1_15.nxs";
		HDF5Loader l = new HDF5Loader(n);
		List<ILazyDataset> ds = l.findDatasets(new String[] {"scan_command", "title"}, 1, null);
		assertEquals("File does not have the correct number of datasets", 1, ds.size());
		ILazyDataset d = ds.get(0);
		assertTrue(d instanceof StringDataset);
	}
	
}

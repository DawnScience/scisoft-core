/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import static org.eclipse.january.asserts.TestUtils.assertDatasetEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.vecmath.AxisAngle4d;
import javax.vecmath.Matrix3d;
import javax.vecmath.Matrix4d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;

import org.apache.commons.math3.complex.Complex;
import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.tree.Attribute;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.analysis.api.tree.NodeLink;
import org.eclipse.dawnsci.analysis.api.tree.SymbolicNode;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.api.tree.TreeUtils;
import org.eclipse.dawnsci.hdf5.HDF5Utils;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.ComplexDoubleDataset;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.dataset.StringDataset;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.IMetadata;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.IOTestUtils;
import uk.ac.diamond.scisoft.analysis.diffraction.MatrixUtils;

public class HDF5LoaderTest {
	final static String TestFileFolder = "testfiles/gda/analysis/io/NexusLoaderTest/";
	static String LargeTestFilesFolder;

	@BeforeClass
	static public void setUpClass() {
		LargeTestFilesFolder = IOTestUtils.getGDALargeTestFilesLocation();
	}

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
		}

		Collections.sort(ourTimes);
		System.out.printf("Load took %d ms\n", ourTimes.get(0));
	}

	@Test
	public void testLoadingStrings() throws ScanFileHolderException, InterruptedException {
		String n = TestFileFolder + "strings1d.h5";
		HDF5Loader l = new HDF5Loader(n);

		@SuppressWarnings("unused")
		Tree tree = l.loadTree(null);

		n = TestFileFolder + "strings2d.h5";
		l = new HDF5Loader(n);

		tree = l.loadTree(null);
		Thread.sleep(6000);
	}

	@Test
	public void testLoadingTest() throws Exception {
		testLoadingTest(false);
		testLoadingTest(true);
	}

	private void testLoadingTest(boolean async) throws Exception {
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

		GroupNode group = (GroupNode) tree.findNodeLink("/entry1/to/here").getDestination();
		NodeLink link;
		link = group.getNodeLink("source_g");
		assertTrue(link.isDestinationSymbolic());
		assertFalse(((SymbolicNode) link.getDestination()).isData());
		assertTrue(((SymbolicNode) link.getDestination()).getNode() instanceof GroupNode);
		link = group.getNodeLink("source_d");
		assertTrue(link.isDestinationSymbolic());
		assertTrue(((SymbolicNode) link.getDestination()).isData());
		assertTrue(((SymbolicNode) link.getDestination()).getNode() instanceof DataNode);
		link = group.getNodeLink("source_d2");
		assertTrue(link.isDestinationSymbolic());
		assertTrue(((SymbolicNode) link.getDestination()).isData());
		assertTrue(((SymbolicNode) link.getDestination()).getNode() instanceof DataNode);
		link = group.getNodeLink("missing_g");
		assertTrue(link.isDestinationSymbolic());
		assertTrue(((SymbolicNode) link.getDestination()).isData()); // cannot tell with h5py...
		assertEquals(null, ((SymbolicNode) link.getDestination()).getNode());
		assertEquals("../nonlevel", ((SymbolicNode) link.getDestination()).getPath());
		link = group.getNodeLink("missing_d");
		assertTrue(link.isDestinationSymbolic());
		assertTrue(((SymbolicNode) link.getDestination()).isData());
		assertEquals(null, ((SymbolicNode) link.getDestination()).getNode());
		assertEquals("../nonlevel/d", ((SymbolicNode) link.getDestination()).getPath());
		
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

		// attributes
		name = "a1";
		group = (GroupNode) tree.findNodeLink("/entry1/to/this/level").getDestination();
		try {
			link = group.findNodeLink(Node.ATTRIBUTE + name);
			fail("IAE expected");
		} catch (IllegalArgumentException e) {
			// do nothing
		}

		group = (GroupNode) tree.findNodeLink("/entry1/to/this").getDestination();
		link = group.findNodeLink("level" + Node.ATTRIBUTE + name);
		checkLink(link, "level", name, "hello");

		link = tree.findNodeLink("/entry1/to/this/level" + Node.ATTRIBUTE + name);
		checkLink(link, "level", name, "hello");

		group = (GroupNode) tree.findNodeLink("/entry1/to").getDestination();
		link = group.findNodeLink("this/level" + Node.ATTRIBUTE + name);
		checkLink(link, "level", name, "hello");

		group = (GroupNode) tree.findNodeLink("/entry1/to").getDestination();
		link = group.findNodeLink("this/level1" + Node.ATTRIBUTE + name);
		assertNull("Group found", link);

		name = "b1";
		link = tree.findNodeLink("/entry1/to/this/level" + Node.ATTRIBUTE + name);
		assertNull("Group's attribute found", link);

		link = tree.findNodeLink("this/level" + Node.ATTRIBUTE + name);
		assertNull("Group's attribute found", link);

		link = tree.findNodeLink(Tree.ROOT + Node.ATTRIBUTE + name);
		assertNull("Root group's attribute found", link);

		name = "NeXus_version";
		link = tree.findNodeLink(Tree.ROOT + Node.ATTRIBUTE + name);
		checkLink(link, Tree.ROOT, name, "4.3.0");

		System.out.println(tree.findNodeLink("/entry1/to/this/level"));
	}

	private void checkLink(NodeLink link, String lName, String aName, String value) {
		IDataset dataset;
		assertNotNull("Group's attribute not found", link);
		assertTrue("Group not found", link.isDestinationGroup());
		assertEquals("Group not found", lName, link.getName());
		GroupNode g = (GroupNode) link.getDestination();
		Attribute a = g.getAttribute(aName);
		assertNotNull("Attribute not found " + aName, a);
		dataset = a.getValue();
		assertEquals(value, dataset.getString());
	}

	@Test
	public void testLoadingNames() throws ScanFileHolderException {
		testLoadingNames(false);
		testLoadingNames(true);
	}
	

	private void testLoadingNames(boolean async) throws ScanFileHolderException {
		final String n = LargeTestFilesFolder + "327.nxs";
		HDF5Loader l = new HDF5Loader(n);
		l.setAsyncLoad(async);

		Tree tree = l.loadTree(null);
		System.out.println(tree.getNodeLink());
		for (NodeLink nl : tree.getGroupNode())
			System.out.println(nl);

		GroupNode g = tree.getGroupNode().getGroupNode("entry1");
		assertEquals("Group is wrongly named" , "/entry1/EDXD_Element_00/", TreeUtils.getPath(tree, g.getNodeLink("EDXD_Element_00").getDestination()));
		g = g.getGroupNode("EDXD_Element_00");
		assertEquals("Attribute is wrongly named" , "axis", g.getDataNode("a").getAttribute("axis").getName());
	}

	@Test
	public void testLoading() throws Exception {
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
		Dataset a = DatasetUtils.sliceAndConvertLazyDataset(dn.getDataset());
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
	public void testLoadingChunked() throws Exception {

		final String n = LargeTestFilesFolder + "NexusUITest/sino.h5";
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
		ad = DatasetUtils.convertToDataset(dn.getDataset().getSlice(new Slice(1), new Slice(1), null));
		checkDataset("data", ad, new int[] { 1, 1, 1481 });
		x = ((Number) ad.sum()).doubleValue();
		System.err.println(x);
		assertEquals("Value of sum", 164.12514, x, x * 1e-5);

		ad = DatasetUtils.convertToDataset(dn.getDataset().getSlice(new Slice(1), new Slice(null, null, 3), null));
		checkDataset("data", ad, new int[] { 1, 75, 1481 });
		x = ((Number) ad.sum()).doubleValue();
		System.err.println(x);
		assertEquals("Value of sum", 40271.562, x, x * 1e-5);

		ad = DatasetUtils.convertToDataset(dn.getDataset().getSlice(new Slice(1), new Slice(null, null, 3), new Slice(2)));
		checkDataset("data", ad, new int[] { 1, 75, 2 });
		x = ((Number) ad.sum()).doubleValue();
		System.err.println(x);
		assertEquals("Value of sum", 3.7149904, x, x * 1e-5);

		ad = DatasetUtils.convertToDataset(dn.getDataset().getSlice(new Slice(null, null, 2), new Slice(1), null));
		checkDataset("data", ad, new int[] { 31, 1, 1481 });
		x = ((Number) ad.sum()).doubleValue();
		System.err.println(x);
		assertEquals("Value of sum", 10522.864, x, x * 1e-5);

		ad = DatasetUtils.convertToDataset(dn.getDataset().getSlice(new Slice(null, null, 2), new Slice(null, null, 3), null));
		checkDataset("data", ad, new int[] { 31, 75, 1481 });
		x = ((Number) ad.sum()).doubleValue();
		System.err.println(x);
		assertEquals("Value of sum", 1640010.1, x, x * 1e-3);

		ad = DatasetUtils.convertToDataset(dn.getDataset().getSlice(new Slice(null, null, 2), new Slice(null, null, 3), new Slice(2)));
		checkDataset("data", ad, new int[] { 31, 75, 2 });
		x = ((Number) ad.sum()).doubleValue();
		System.err.println(x);
		assertEquals("Value of sum", 137.25012, x, x * 1e-5);

		// slice across chunks
		ad = DatasetUtils.convertToDataset(dn.getDataset().getSlice(new Slice(null, null, 2), new Slice(null, null, 3), new Slice(1, 2)));
		checkDataset("data", ad, new int[] { 31, 75, 1 });
		x = ((Number) ad.sum()).doubleValue();
		System.err.println(x);
		assertEquals("Value of sum", 64.191261, x, x * 1e-5);
		long timeTaken = System.currentTimeMillis() - timeAtStartms;
		System.out.printf("Time taken = %d ms\n", timeTaken);
		assertTrue("Time taken " + timeTaken + " < 12000", timeTaken < 12000);
	}

	@Test
	public void testLoadingChunkedSpeed() throws Exception {
		final String n = LargeTestFilesFolder + "NexusUITest/3dDataChunked.nxs";
		long timeAtStartms = System.currentTimeMillis();

		HDF5Utils.loadDataset(n, "entry/instrument/detector/data", new int[] { 0, 0, 0 }, new int[] { 1, 1795, 2069 },
				new int[] { 1, 1, 1 }, 1, null, false);
		long timeTaken = System.currentTimeMillis() - timeAtStartms;
		System.out.printf("Time taken = %d ms\n", timeTaken);
		assertTrue("Time taken " + timeTaken + " < 12000", timeTaken < 12000);
	}

	@Test
	public void testCanonicalization() {
		String[] before = { "./foo", "/asd/sdf/dfg/../ds/../../gfd", "/asd/asd/../as", "/asd/as/.././bad", "/asd/..", "/abal/.", "",
				"../blah", "/asd/sdf/././../ds", "/./sdf/././../ds"};
		String[] after = { "./foo", "/asd/gfd", "/asd/as", "/asd/bad", "/", "/abal", "", "../blah", "/asd/ds", "/ds" };

		for (int i = 0; i < before.length; i++) {
			assertEquals("Path", after[i], TreeUtils.canonicalizePath(before[i]));
		}
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
	public void testLoadingCompoundDatatype() throws Exception {
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

	@Test
	public void testLoadingNexusMetadata() throws ScanFileHolderException {
		String n = TestFileFolder + "../NexusDiffractionTest/results_i22-102527_Pilatus2M_280313_112434.nxs";
		NexusHDF5Loader l = new NexusHDF5Loader();
		l.setFile(n);
		DataHolder dh = l.loadFile();
		ILazyDataset d = dh.getLazyDataset("/entry1/Pilatus2M_processing/SectorIntegration/data");
		assertTrue(d.getErrors() == null);
		AxesMetadata amd = d.getFirstMetadata(AxesMetadata.class);
		assertTrue(amd != null);
		assertNull(amd.getAxis(0));
		assertNull(amd.getAxis(1));
		ILazyDataset[] qs = amd.getAxis(2);
		assertEquals(1, qs.length);
		assertEquals("q", qs[0].getName());
	}

	@Test
	public void testLoadingNexusDetector() throws ScanFileHolderException, NexusException {
		String n = TestFileFolder + "../NexusDiffractionTest/336502.nxs";
		NexusHDF5Loader l = new NexusHDF5Loader();
		l.setFile(n);
		DataHolder dh = l.loadFile();
		Tree t = dh.getTree();
		DetectorProperties dp = NexusTreeUtils.parseDetector("/entry/instrument/detector", t, 0)[0];

		ILazyDataset ld = dh.getLazyDataset(0);
		assertArrayEquals(new int[] {10, 195, 487}, ld.getShape());

		System.err.println(dp);

		AxisAngle4d ad = new AxisAngle4d(-1, 0, 0, Math.toRadians(9 + 36.53819));

		Matrix4d mo = new Matrix4d();
		mo.setIdentity();
		mo.setColumn(3, 571+210, 200, 0, 1);
		Matrix4d m = new Matrix4d();
		m.set(ad);
		m.mul(mo);

		Vector3d fast = new Vector3d(0, -Math.sqrt(0.5), Math.sqrt(0.5));
		Vector3d slow = new Vector3d(1, 0, 0);
		m.transform(fast);
		m.transform(slow);

		Vector4d o4 = new Vector4d();
		o4.setW(1);
		m.transform(o4);
		Vector3d origin = new Vector3d(o4.x, o4.y, o4.z);
		Matrix3d ori = MatrixUtils.computeFSOrientation(fast, slow);
		ori.transpose();
		DetectorProperties edp = new DetectorProperties(origin, 195, 487, 0.172, 0.172, ori);
		Vector3d bv = new Vector3d(origin);
		bv.normalize();

		assertEquals(edp.getPx(), dp.getPx());
		assertEquals(edp.getPy(), dp.getPy());
		assertEquals(edp.getStartX(), dp.getStartX());
		assertEquals(edp.getStartY(), dp.getStartY());
		assertTrue(MatrixUtils.isClose(edp.getBeamVector(), dp.getBeamVector(), 1e-8, 1e-8));
		assertTrue(MatrixUtils.isClose(edp.getHPxSize(), dp.getHPxSize(), 1e-8, 1e-8));
		assertTrue(MatrixUtils.isClose(edp.getVPxSize(), dp.getVPxSize(), 1e-8, 1e-8));
		assertTrue(MatrixUtils.isClose(edp.getOrigin(), dp.getOrigin(), 1e-8, 1e-8));
		assertTrue(MatrixUtils.isClose(edp.getOrientation(), dp.getOrientation(), 1e-8, 1e-8));
	}

	@Test
	public void testLoadingPercival() throws Exception {
		String n = TestFileFolder + "KnifeQuadBPos1_2_21.h5";
		HDF5Loader l = new HDF5Loader(n);
		DataHolder dh = l.loadFile();
		System.err.println(Arrays.toString(dh.getNames()));
		IDataset ds = dh.getLazyDataset("/KnifeQuadBPos1/10/Sample").getSlice();
		assertEquals(Byte.class, ds.getElementClass());
		assertArrayEquals(new int[] {160, 210}, ds.getShape());
		assertArrayEquals(new byte[] {87, 11, 1}, (byte[]) ds.getObject(0, 0));
	}

	@Test
	public void testLoadingMissingLink() throws ScanFileHolderException {
		String n = TestFileFolder + "missinglink.h5";
		HDF5Loader l = new HDF5Loader(n);
		DataHolder dh = l.loadFile();
		System.err.println(Arrays.toString(dh.getNames()));
		GroupNode g = l.tFile.getGroupNode();
		assertEquals(1, g.getNumberOfNodelinks());
	}

	@Test
	public void testLoadingRGBByte() throws Exception {
		String n = TestFileFolder + "rgb.nxs";
		HDF5Loader l = new HDF5Loader(n);
		DataHolder dh = l.loadFile();
		System.err.println(Arrays.toString(dh.getNames()));
		IDataset ds = dh.getLazyDataset("/entry/data/data").getSlice();
		assertEquals(Byte.class, ds.getElementClass());
		assertArrayEquals(new int[] {3, 3, 3}, ds.getShape());
		assertEquals((byte) 250, ds.getObject(0, 0, 0));
		assertEquals((byte) 125, ds.getObject(1, 1, 1));
	}

	@Test
	public void testMultifileLinks() throws ScanFileHolderException, DatasetException {
		HDF5Loader l = new HDF5Loader(TestFileFolder + "bottom.h5");
		DataHolder dh = l.loadFile();
		assertTrue(dh.getTree().getGroupNode().containsSymbolicNode("loop_2step"));
		Dataset b0 = DatasetUtils.sliceAndConvertLazyDataset(dh.getLazyDataset("/b0"));
		assertArrayEquals(new int[] {256}, b0.getShape());
		assertEquals(0, b0.getInt(0));
		assertEquals(255, b0.getInt(255));
		Dataset b2 = DatasetUtils.sliceAndConvertLazyDataset(dh.getLazyDataset("/b2"));
		assertArrayEquals(new int[] {256}, b2.getShape());
		assertEquals(2, b2.getInt(0));
		assertEquals(257, b2.getInt(255));

		l = new HDF5Loader(TestFileFolder + "middle.h5");
		dh = l.loadFile();
		assertTrue(dh.getTree().getGroupNode().containsSymbolicNode("loop"));
		assertTrue(dh.getTree().getGroupNode().containsSymbolicNode("loop_2step"));
		Dataset m1 = DatasetUtils.sliceAndConvertLazyDataset(dh.getLazyDataset("/m1"));
		assertArrayEquals(new int[] {256}, m1.getShape());
		assertEquals(5, m1.getInt(0));
		assertEquals(260, m1.getInt(255));
		Dataset m3 = DatasetUtils.sliceAndConvertLazyDataset(dh.getLazyDataset("/m3"));
		assertArrayEquals(new int[] {256}, m3.getShape());
		assertEquals(7, m3.getInt(0));
		assertEquals(262, m3.getInt(255));

		Dataset bl0 = DatasetUtils.sliceAndConvertLazyDataset(dh.getLazyDataset("/bl0"));
		assertDatasetEquals(b0, bl0);
		Dataset bl2 = DatasetUtils.sliceAndConvertLazyDataset(dh.getLazyDataset("/bl2"));
		assertDatasetEquals(b2, bl2);

		l = new HDF5Loader(TestFileFolder + "top.h5");
		dh = l.loadFile();
		assertTrue(dh.getTree().getGroupNode().containsSymbolicNode("loop"));
		assertTrue(dh.getTree().getGroupNode().containsSymbolicNode("loop_2step"));
		Dataset t1 = DatasetUtils.sliceAndConvertLazyDataset(dh.getLazyDataset("/t1"));
		assertArrayEquals(new int[] {256}, t1.getShape());
		assertEquals(9, t1.getInt(0));
		assertEquals(264, t1.getInt(255));
		Dataset t3 = DatasetUtils.sliceAndConvertLazyDataset(dh.getLazyDataset("/t3"));
		assertArrayEquals(new int[] {256}, t3.getShape());
		assertEquals(11, t3.getInt(0));
		assertEquals(266, t3.getInt(255));

		Dataset bll0 = DatasetUtils.sliceAndConvertLazyDataset(dh.getLazyDataset("/bll0"));
		assertDatasetEquals(b0, bll0);
		Dataset bll2 = DatasetUtils.sliceAndConvertLazyDataset(dh.getLazyDataset("/bll2"));
		assertDatasetEquals(b2, bll2);
		bl0 = DatasetUtils.sliceAndConvertLazyDataset(dh.getLazyDataset("/bl0"));
		assertDatasetEquals(b0, bl0);
		bl2 = DatasetUtils.sliceAndConvertLazyDataset(dh.getLazyDataset("/bl2"));
		assertDatasetEquals(b2, bl2);

		Dataset ml1 = DatasetUtils.sliceAndConvertLazyDataset(dh.getLazyDataset("/ml1"));
		assertDatasetEquals(m1, ml1);
		Dataset ml3 = DatasetUtils.sliceAndConvertLazyDataset(dh.getLazyDataset("/ml3"));
		assertDatasetEquals(m3, ml3);
	}
}

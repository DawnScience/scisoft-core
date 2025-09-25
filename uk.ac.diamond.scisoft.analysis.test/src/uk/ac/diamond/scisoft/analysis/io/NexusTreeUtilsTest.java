/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.dawnsci.analysis.api.diffraction.MatrixUtils;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.analysis.api.tree.NodeLink;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.tree.TreeFactory;
import org.eclipse.dawnsci.nexus.NexusConstants;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.ShapeUtils;
import org.eclipse.january.dataset.ShortDataset;
import org.eclipse.january.metadata.AxesMetadata;
import org.junit.Assert;
import org.junit.Test;

import si.uom.NonSI;
import si.uom.SI;
import tec.units.indriya.unit.MetricPrefix;

public class NexusTreeUtilsTest {

	private static void addNXclass(Node node, String nxClass) {
		node.addAttribute(TreeFactory.createAttribute(NexusConstants.NXCLASS, nxClass));
	}

	class AxisDataset {
		Dataset axis;
		int dim;
		int[] indices;
		public AxisDataset(int dim, Dataset axis, int... indices) {
			this.axis = axis;
			this.dim = dim;
			this.indices = indices;
		}
	}

	@Test
	public void testParseNXdataA() {
		int[] shape = new int[] {100};
		Dataset signal = DatasetFactory.ones(ShortDataset.class, shape);
		signal.setName("data");

		List<AxisDataset> axes = new ArrayList<>();
		Dataset axis;

		axis = DatasetFactory.createRange(ShortDataset.class, shape[0]);
		axis.setName("x");
		axes.add(new AxisDataset(0, axis, 0));

		GroupNode group = createNXdata(signal, axes);
		checkMetadata(group, signal, false);
	}

	@Test
	public void testParseNXdataB() {
		int[] shape = new int[] {100, 20};
		Dataset signal = DatasetFactory.ones(ShortDataset.class, shape);
		signal.setName("data");

		List<AxisDataset> axes = new ArrayList<>();
		Dataset axis;

		axis = DatasetFactory.createRange(ShortDataset.class, shape[0]);
		axis.setName("time");
		axes.add(new AxisDataset(0, axis, 0));

		axis = DatasetFactory.createRange(ShortDataset.class, shape[1]);
		axis.setName("pressure");
		axes.add(new AxisDataset(1, axis, 1));

		axis = DatasetFactory.createRange(ShortDataset.class, shape[1]);
		axis.setName("temperature");
		axes.add(new AxisDataset(-1, axis, 1));

		GroupNode group = createNXdata(signal, axes);
		checkMetadata(group, signal, false);
	}

	@Test
	public void testParseNXdataC() {
		int[] shape = new int[] {1000, 10000};
		Dataset signal = DatasetFactory.ones(ShortDataset.class, shape);
		signal.setName("det");

		List<AxisDataset> axes = new ArrayList<>();
		Dataset axis;

		axis = DatasetFactory.createRange(ShortDataset.class, shape[0]);
		axis.setName("pressure");
		axes.add(new AxisDataset(0, axis, 0));

		axis = DatasetFactory.createRange(ShortDataset.class, shape[1]);
		axis.setName("tof");
		axes.add(new AxisDataset(1, axis, 1));

		GroupNode group = createNXdata(signal, axes);
		checkMetadata(group, signal, false);
	}

	@Test
	public void testParseNXdataD() {
		int[] shape = new int[] {100, 5, 40};
		Dataset signal = DatasetFactory.ones(ShortDataset.class, shape);
		signal.setName("det");

		List<AxisDataset> axes = new ArrayList<>();
		Dataset axis;

		axis = DatasetFactory.createRange(ShortDataset.class, shape[0]*shape[1]).reshape(shape[0], shape[1]);
		axis.setName("x");
		axes.add(new AxisDataset(0, axis, 0, 1));
		axis = DatasetFactory.createRange(ShortDataset.class, shape[0]*shape[1]).reshape(shape[0], shape[1]);
		axis.setName("y");
		axes.add(new AxisDataset(1, axis, 0, 1));
		axis = DatasetFactory.createRange(ShortDataset.class, shape[2]);
		axis.setName("tof");
		axes.add(new AxisDataset(2, axis, 2));

		GroupNode group = createNXdata(signal, axes);
		checkMetadata(group, signal, false);
	}

	@Test
	public void testParseNXdataE() {
		int[] shape = new int[] {50, 5, 1024};
		Dataset signal = DatasetFactory.ones(ShortDataset.class, shape);
		signal.setName("det1");

		List<AxisDataset> axes = new ArrayList<>();
		Dataset axis;

		axis = DatasetFactory.createRange(ShortDataset.class, shape[0]);
		axis.setName("polar_angle_set");
		axes.add(new AxisDataset(0, axis, 0));

		axis = DatasetFactory.createRange(ShortDataset.class, shape[1]);
		axis.setName("frame_number");
		axes.add(new AxisDataset(1, axis, 1));

		axis = DatasetFactory.createRange(ShortDataset.class, shape[0]*shape[1]).reshape(shape[0], shape[1]);
		axis.setName("polar_angle_rbv");
		axes.add(new AxisDataset(-1, axis, 0, 1));

		axis = DatasetFactory.createRange(ShortDataset.class, shape[0]*shape[1]).reshape(shape[1], shape[0]);
		axis.setName("time");
		axes.add(new AxisDataset(-1, axis, 1, 0));

		GroupNode group = createNXdata(signal, axes);
		checkMetadata(group, signal, false);
	}

	private static GroupNode createNXdata(Dataset signal, List<AxisDataset> axes) {
		long count = 0;
		GroupNode group = TreeFactory.createGroupNode(count++);
		addNXclass(group, NexusConstants.DATA);

		DataNode dNode;
		dNode = TreeFactory.createDataNode(count++);
		dNode.setDataset(signal);
		group.addDataNode(signal.getName(), dNode);
		group.addAttribute(TreeFactory.createAttribute(NexusConstants.DATA_SIGNAL, signal.getName()));

		String[] axisArray = new String[signal.getRank()];
		Arrays.fill(axisArray, NexusConstants.DATA_AXESEMPTY);
		for (AxisDataset axis : axes) {
			dNode = TreeFactory.createDataNode(count++);
			dNode.setDataset(axis.axis);
			String n = axis.axis.getName();
			group.addDataNode(n, dNode);
	
			if (axis.dim >= 0 && NexusConstants.DATA_AXESEMPTY.equals(axisArray[axis.dim])) {
				axisArray[axis.dim] = n;
			}
			group.addAttribute(TreeFactory.createAttribute(n + NexusConstants.DATA_INDICES_SUFFIX, axis.indices));
		}

		group.addAttribute(TreeFactory.createAttribute(NexusConstants.DATA_AXES, axisArray));
		return group;
	}

	private static GroupNode createOldNXdata(Dataset signal, List<AxisDataset> axes) {
		long count = 0;
		GroupNode group = TreeFactory.createGroupNode(count++);
		addNXclass(group, NexusConstants.DATA);

		DataNode dNode;
		dNode = TreeFactory.createDataNode(count++);
		dNode.setDataset(signal);
		dNode.addAttribute(TreeFactory.createAttribute(NexusConstants.DATA_SIGNAL, 1));
		group.addDataNode(signal.getName(), dNode);

		for (AxisDataset axis : axes) {
			dNode = TreeFactory.createDataNode(count++);
			dNode.setDataset(axis.axis);
			String n = axis.axis.getName();
			group.addDataNode(n, dNode);
		}

		return group;
	}

	private static void checkMetadata(GroupNode group, Dataset signal, boolean noNulls) {
		NodeLink nl = TreeFactory.createNodeLink("", null, group);
		NexusTreeUtils.augmentNodeLink("/whatever/file.h5", "my/data/", nl, false);

		ILazyDataset s = group.getDataNode(signal.getName()).getDataset();
		AxesMetadata amd = s.getFirstMetadata(AxesMetadata.class);

		assertNotNull(amd);
		int[] shape = s.getShape();
		int rank = shape.length;
		boolean anyNulls = false;
		for (int i = 0; i < rank; i++) {
			ILazyDataset[] ds = amd.getAxis(i);
			if (ds == null) {
				anyNulls = true;
				System.err.printf("Axis datasets missing for dimension %d / %d%n", i, rank);
			} else {
				for (ILazyDataset l : ds) {
					assertTrue(String.format("%s: not compatible with %s", l.toString(), Arrays.toString(shape)), ShapeUtils.areShapesBroadcastCompatible(l.getShape(), shape));
				}
			}
		}
		if (noNulls) {
			assertFalse("Some axes were missing", anyNulls);
		}
	}

	@Test
	public void test2014Axes() throws Exception {
		NexusHDF5Loader loader = new NexusHDF5Loader();
		loader.setFile("testfiles/gda/analysis/io/NexusLoaderTest/nxdata.nxs");
		Tree t = loader.loadTree();

		GroupNode g = t.getGroupNode().getGroupNode("entry1").getGroupNode("complex");
		ILazyDataset d = g.getDataNode("detector").getDataset();
		Assert.assertArrayEquals(new int[] {5,  6, 10, 20}, d.getShape());
		AxesMetadata amd = d.getMetadata(AxesMetadata.class).get(0);
		ILazyDataset[] a;

		a = amd.getAxis(0);
		Assert.assertEquals(3, a.length);
		Assert.assertEquals("x", a[0].getName());
		Assert.assertArrayEquals(new int[] {5, 1, 1, 1}, a[0].getShape());
		Assert.assertEquals("y", a[1].getName());
		Assert.assertArrayEquals(new int[] {5, 6, 1, 1}, a[1].getShape());
		Assert.assertEquals("temp", a[2].getName());
		Assert.assertArrayEquals(new int[] {5, 6, 1, 1}, a[2].getShape());

		a = amd.getAxis(1);
		Assert.assertEquals(2, a.length);
		Assert.assertEquals("y", a[0].getName());
		Assert.assertArrayEquals(new int[] {5, 6, 1, 1}, a[0].getShape());
		Assert.assertEquals("temp", a[1].getName());
		Assert.assertArrayEquals(new int[] {5, 6, 1, 1}, a[1].getShape());
	}

	private static final double TOL = 1e-14;

	@Test
	public void testNXDetectorCreationAndParsing() throws NexusException {
		DetectorProperties det = DetectorProperties.getDefaultDetectorProperties(new int[] {200,100});
		det.setHPxSize(det.getHPxSize() * 2);
		det.setOrientationEulerZYZ(Math.toRadians(10), Math.toRadians(20), Math.toRadians(25));

		System.out.println("Fast pixel direction = " + det.getPixelRow());
		System.out.println("Slow pixel direction = " + det.getPixelColumn());
		GroupNode nxd = NexusTreeUtils.createNXDetector(det);
		GroupNode instr = TreeFactory.createGroupNode(0);
		instr.addGroupNode("detector", nxd);

		Tree tree = TreeFactory.createTree(0, null);
		tree.setGroupNode(instr);
		DetectorProperties dp = NexusTreeUtils.parseDetector("/detector", tree, 0)[0];
		MatrixUtils.isClose(det.getBeamVector(), dp.getBeamVector(), TOL, TOL);
		MatrixUtils.isClose(det.getOrigin(), dp.getOrigin(), TOL, TOL);
		MatrixUtils.isClose(det.getOrientation(), dp.getOrientation(), TOL, TOL);
		Assert.assertEquals(det.getPx(), dp.getPx());
		Assert.assertEquals(det.getPy(), dp.getPy());
		Assert.assertEquals(det.getHPxSize(), dp.getHPxSize(), TOL);
		Assert.assertEquals(det.getVPxSize(), dp.getVPxSize(), TOL);
	}

	@Test
	public void testUnits() {
		Assert.assertEquals(NonSI.ANGSTROM, NexusTreeUtils.parseUnit("Angstrom"));
		Assert.assertEquals(NonSI.ANGSTROM, NexusTreeUtils.parseUnit("angstrom"));
		Assert.assertEquals(NonSI.ELECTRON_VOLT, NexusTreeUtils.parseUnit("eV"));
		Assert.assertEquals(MetricPrefix.KILO(NonSI.ELECTRON_VOLT), NexusTreeUtils.parseUnit("keV"));
		Assert.assertEquals(NonSI.DEGREE_ANGLE, NexusTreeUtils.parseUnit("deg"));
		Assert.assertEquals(NonSI.DEGREE_ANGLE, NexusTreeUtils.parseUnit("degree"));
		Assert.assertEquals(NonSI.ANGSTROM.inverse(), NexusTreeUtils.parseUnit("angstrom^-1"));
		Assert.assertEquals(NonSI.ANGSTROM.inverse(), NexusTreeUtils.parseUnit("/angstrom"));
		Assert.assertEquals(SI.RADIAN, NexusTreeUtils.parseUnit("rad"));
	}

	@Test
	public void testFindFirstNode() {
		GroupNode g = TreeFactory.createGroupNode(0);
		GroupNode a;
		a = TreeFactory.createGroupNode(0);
		addNXclass(a, NexusConstants.NOTE);
		g.addGroupNode("note", a);
		a = TreeFactory.createGroupNode(0);
		addNXclass(a, NexusConstants.MONITOR);
		g.addGroupNode("monitor", a);

		NodeLink b = NexusTreeUtils.findFirstNode(g, NexusConstants.BEAM);
		assertNull(b);
		b = NexusTreeUtils.findFirstNode(g, NexusConstants.NOTE);
		assertNotNull(b);

		b = NexusTreeUtils.findFirstNode(g, "moon", NexusConstants.MONITOR);
		assertNull(b);

		Node n = null;
		try {
			n = NexusTreeUtils.requireNode(g, NexusConstants.BEAM);
			fail("Should not have found anything");
		} catch (NexusException e) {
			// do nothing
		}

		try {
			n = NexusTreeUtils.requireNode(g, NexusConstants.NOTE);
		} catch (NexusException e) {
			fail("Should have found something");
		}

		try {
			n = NexusTreeUtils.requireNode(g, "moon", NexusConstants.MONITOR);
			fail("Should not have found anything");
		} catch (NexusException e) {
			// do nothing
		}

		System.out.println(n);
	}

	@Test
	public void testCroppingAxes() {
		int[] shape = new int[] {8, 16};
		Dataset signal = DatasetFactory.ones(ShortDataset.class, shape);
		signal.setName("det1");

		List<AxisDataset> axes = new ArrayList<>();
		Dataset axis;

		axis = DatasetFactory.createRange(ShortDataset.class, 32);
		axis.setName("dummy");
		axes.add(new AxisDataset(0, axis, 0));


		axis = DatasetFactory.createRange(ShortDataset.class, 24);
		axis.setName("second");
		axes.add(new AxisDataset(1, axis, 1));

		GroupNode group = createNXdata(signal, axes);
		checkMetadata(group, signal, true);
		for (DataNode d: group.getDataNodes()) {
			System.err.println(d.getDataset());
		}
		System.err.println(group.getDataNode("det1").getDataset().getFirstMetadata(AxesMetadata.class));

		group = createOldNXdata(signal, axes);
		checkMetadata(group, signal, true);
		for (DataNode d: group.getDataNodes()) {
			System.err.println(d.getDataset());
		}
		System.err.println(group.getDataNode("det1").getDataset().getFirstMetadata(AxesMetadata.class));
	}

	@Test
	public void testCroppingData() {
		int[] shape = new int[] {102, 1, 100};
		Dataset signal = DatasetFactory.ones(ShortDataset.class, shape);
		signal.setName("image");

		List<AxisDataset> axes = new ArrayList<>();
		Dataset axis;

		axis = DatasetFactory.createRange(ShortDataset.class, 101);
		axis.setName("dummy");
		axes.add(new AxisDataset(0, axis, 0));

		axis = DatasetFactory.createRange(ShortDataset.class, 1);
		axis.setName("angles");
		axes.add(new AxisDataset(1, axis, 1));

		axis = DatasetFactory.createRange(ShortDataset.class, 100);
		axis.setName("binding_energy");
		axes.add(new AxisDataset(2, axis, 2));

		axis = DatasetFactory.ones(ShortDataset.class, 102, 1);
		axis.setName("excitation_energy");
		axes.add(new AxisDataset(-1, axis, 0, 1));

		axis = DatasetFactory.ones(ShortDataset.class, 101, 100);
		axis.setName("spectrum");
		axes.add(new AxisDataset(-1, axis, 0, 2));

		axis = DatasetFactory.ones(ShortDataset.class, 101, 1);
		axis.setName("total_intensity");
		axes.add(new AxisDataset(-1, axis, 0, 1));

		GroupNode group = createNXdata(signal, axes);
		checkMetadata(group, signal, true);
		for (DataNode d: group.getDataNodes()) {
			System.err.println(d.getDataset());
		}
		System.err.println(group.getDataNode("image").getDataset().getFirstMetadata(AxesMetadata.class));

		group = createOldNXdata(signal, axes);
		checkMetadata(group, signal, true);
		for (DataNode d: group.getDataNodes()) {
			System.err.println(d.getDataset());
		}
		System.err.println(group.getDataNode("image").getDataset().getFirstMetadata(AxesMetadata.class));
	}
}

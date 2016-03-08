/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.dawnsci.analysis.api.metadata.AxesMetadata;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.dataset.impl.AbstractDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.tree.TreeFactory;
import org.junit.Assert;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.diffraction.MatrixUtils;

public class NexusTreeUtilsTest {

	public static void addNXclass(Node node, String nxClass) {
		node.addAttribute(TreeFactory.createAttribute(NexusTreeUtils.NX_CLASS, nxClass));
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
		Dataset signal = DatasetFactory.ones(shape, Dataset.INT16);
		signal.setName("data");

		List<AxisDataset> axes = new ArrayList<>();
		Dataset axis;

		axis = DatasetFactory.createRange(shape[0], Dataset.INT16);
		axis.setName("x");
		axes.add(new AxisDataset(0, axis, 0));

		GroupNode group = createNXdata(signal, axes);
		checkMetadata(group, signal);
	}

	@Test
	public void testParseNXdataB() {
		int[] shape = new int[] {100, 20};
		Dataset signal = DatasetFactory.ones(shape, Dataset.INT16);
		signal.setName("data");

		List<AxisDataset> axes = new ArrayList<>();
		Dataset axis;

		axis = DatasetFactory.createRange(shape[0], Dataset.INT16);
		axis.setName("time");
		axes.add(new AxisDataset(0, axis, 0));

		axis = DatasetFactory.createRange(shape[1], Dataset.INT16);
		axis.setName("pressure");
		axes.add(new AxisDataset(1, axis, 1));

		axis = DatasetFactory.createRange(shape[1], Dataset.INT16);
		axis.setName("temperature");
		axes.add(new AxisDataset(-1, axis, 1));

		GroupNode group = createNXdata(signal, axes);
		checkMetadata(group, signal);
	}

	@Test
	public void testParseNXdataC() {
		int[] shape = new int[] {1000, 10000};
		Dataset signal = DatasetFactory.ones(shape, Dataset.INT16);
		signal.setName("det");

		List<AxisDataset> axes = new ArrayList<>();
		Dataset axis;

		axis = DatasetFactory.createRange(shape[0], Dataset.INT16);
		axis.setName("pressure");
		axes.add(new AxisDataset(0, axis, 0));

		axis = DatasetFactory.createRange(shape[1], Dataset.INT16);
		axis.setName("tof");
		axes.add(new AxisDataset(1, axis, 1));

		GroupNode group = createNXdata(signal, axes);
		checkMetadata(group, signal);
	}

	@Test
	public void testParseNXdataD() {
		int[] shape = new int[] {100, 5, 40};
		Dataset signal = DatasetFactory.ones(shape, Dataset.INT16);
		signal.setName("det");

		List<AxisDataset> axes = new ArrayList<>();
		Dataset axis;

		axis = DatasetFactory.createRange(shape[0]*shape[1], Dataset.INT16).reshape(shape[0], shape[1]);
		axis.setName("x");
		axes.add(new AxisDataset(0, axis, 0, 1));
		axis = DatasetFactory.createRange(shape[0]*shape[1], Dataset.INT16).reshape(shape[0], shape[1]);
		axis.setName("y");
		axes.add(new AxisDataset(1, axis, 0, 1));
		axis = DatasetFactory.createRange(shape[2], Dataset.INT16);
		axis.setName("tof");
		axes.add(new AxisDataset(2, axis, 2));

		GroupNode group = createNXdata(signal, axes);
		checkMetadata(group, signal);
	}

	@Test
	public void testParseNXdataE() {
		int[] shape = new int[] {50, 5, 1024};
		Dataset signal = DatasetFactory.ones(shape, Dataset.INT16);
		signal.setName("det1");

		List<AxisDataset> axes = new ArrayList<>();
		Dataset axis;

		axis = DatasetFactory.createRange(shape[0], Dataset.INT16);
		axis.setName("polar_angle_demand");
		axes.add(new AxisDataset(0, axis, 0));

		axis = DatasetFactory.createRange(shape[1], Dataset.INT16);
		axis.setName("frame_number");
		axes.add(new AxisDataset(1, axis, 1));

		axis = DatasetFactory.createRange(shape[0]*shape[1], Dataset.INT16).reshape(shape[0], shape[1]);
		axis.setName("polar_angle_rbv");
		axes.add(new AxisDataset(-1, axis, 0, 1));

		axis = DatasetFactory.createRange(shape[0]*shape[1], Dataset.INT16).reshape(shape[0], shape[1]);
		axis.setName("time");
		axes.add(new AxisDataset(-1, axis, 0, 1));

		GroupNode group = createNXdata(signal, axes);
		checkMetadata(group, signal);
	}

	private static GroupNode createNXdata(Dataset signal, List<AxisDataset> axes) {
		long count = 0;
		GroupNode group = TreeFactory.createGroupNode(count++);
		addNXclass(group, NexusTreeUtils.NX_DATA);

		DataNode dNode;
		dNode = TreeFactory.createDataNode(count++);
		dNode.setDataset(signal);
		group.addDataNode(signal.getName(), dNode);
		group.addAttribute(TreeFactory.createAttribute(NexusTreeUtils.NX_SIGNAL, signal.getName()));

		String[] axisArray = new String[signal.getRank()];
		Arrays.fill(axisArray, NexusTreeUtils.NX_AXES_EMPTY);
		for (AxisDataset axis : axes) {
			dNode = TreeFactory.createDataNode(count++);
			dNode.setDataset(axis.axis);
			String n = axis.axis.getName();
			group.addDataNode(n, dNode);
	
			if (axis.dim >= 0) {
				axisArray[axis.dim] = n;
			}
			group.addAttribute(TreeFactory.createAttribute(n + NexusTreeUtils.NX_INDICES_SUFFIX, axis.indices));
		}

		group.addAttribute(TreeFactory.createAttribute(NexusTreeUtils.NX_AXES, axisArray));
		return group;
	}

	private static void checkMetadata(GroupNode group, Dataset signal) {
		Assert.assertTrue(NexusTreeUtils.parseNXdataAndAugment(group));

		List<AxesMetadata> amds;
		try {
			amds = signal.getMetadata(AxesMetadata.class);
			Assert.assertTrue(amds != null);
			AxesMetadata amd = amds.get(0);
			int[] shape = signal.getShapeRef();
			int rank = shape.length;
			for (int i = 0; i < rank; i++) {
				ILazyDataset[] ds = amd.getAxis(i);
				for (ILazyDataset l : ds) {
					Assert.assertTrue(AbstractDataset.areShapesBroadcastCompatible(l.getShape(), shape));
				}
			}
		} catch (Exception e) {
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
	public void testNXDetectorCreationAndParsing() {
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
}

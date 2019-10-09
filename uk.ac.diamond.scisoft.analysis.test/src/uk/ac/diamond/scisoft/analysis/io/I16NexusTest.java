/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;

import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.dawnsci.analysis.api.diffraction.DiffractionCrystalEnvironment;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.hdf5.HDF5FileFactory;
import org.eclipse.dawnsci.hdf5.HDF5Utils;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.PositionIterator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.IOTestUtils;
import uk.ac.diamond.scisoft.analysis.crystallography.MillerSpace;
import uk.ac.diamond.scisoft.analysis.crystallography.ReciprocalCell;
import uk.ac.diamond.scisoft.analysis.crystallography.UnitCell;
import uk.ac.diamond.scisoft.analysis.diffraction.DiffractionSample;
import uk.ac.diamond.scisoft.analysis.diffraction.MatrixUtils;
import uk.ac.diamond.scisoft.analysis.diffraction.MillerSpaceMapper;
import uk.ac.diamond.scisoft.analysis.diffraction.QSpace;
import uk.ac.diamond.scisoft.analysis.diffraction.MillerSpaceMapper.MillerSpaceMapperBean;

public class I16NexusTest {
	static String testFileFolder = "testfiles/gda/analysis/io/";

	@BeforeClass
	static public void setUpClass() {
		testFileFolder = IOTestUtils.getGDALargeTestFilesLocation();
		testFileFolder = testFileFolder.concat("DiffractionMapping/i16/");
	}

	@Test
	public void testLoadingI16Nexus() throws ScanFileHolderException {
		String n = testFileFolder +  "538029.nxs";
		NexusHDF5Loader l = new NexusHDF5Loader();
		l.setFile(n);
		DataHolder dh = l.loadFile();
		Tree t = dh.getTree();
		DetectorProperties dp = NexusTreeUtils.parseDetector("/entry1/instrument/pil100k", t, 0)[0];
		System.out.println(dp);
	
		DiffractionSample sample = NexusTreeUtils.parseSample("/entry1/sample", t, true, 0);
		System.out.println(sample);
	}

	@Test
	public void testMSMWritesNexusFile() throws ScanFileHolderException, IOException {
		File if1 = File.createTempFile("src", ".h5");
		File if2 = File.createTempFile("src", ".h5");
		File dst = File.createTempFile("writeMSM_hdf5", ".h5");
		String if1Path = if1.getAbsolutePath();
		String if2Path = if2.getAbsolutePath();
		String dstPath = dst.getAbsolutePath();
		int[] shape0 = new int[] { 3, 2 };
		HDF5Utils.createDataset(if1Path, "/group0", "data0", shape0, shape0, shape0, Dataset.INT8, new short[] { 130 },
				true);
		HDF5Utils.createDataset(if2Path, "/group0", "data0", shape0, shape0, shape0, Dataset.INT8, new short[] { 100 },
				true);
		Dataset a = DatasetFactory.createFromObject("NXentry");
		a.setName("NX_class");
		HDF5Utils.writeAttributes(if1Path, "/group", true, a);
		HDF5Utils.writeAttributes(if2Path, "/group", true, a);
		HDF5FileFactory.releaseFile(if1Path, true);
		HDF5FileFactory.releaseFile(if2Path, true);

		MillerSpaceMapperBean bean = new MillerSpaceMapperBean();
		String[] inputs = { if1Path, if2Path };
		String output = "output";
		String splitterName = "someName";
		double splitterParameter = 1;
		double scaleFactor = 2;
		boolean reduceToNonZero = true;
		double[] millerStep = { 3, 4 };
		double[] qStep = { 5, 6 };

		MillerSpaceMapper.setBeanWithAutoBox(bean, inputs, output, splitterName, splitterParameter, scaleFactor,
				reduceToNonZero, millerStep, qStep);
		Dataset v = DatasetFactory.zeros(4);
		String volPath = "path/to/vol/data";
		Dataset axes = DatasetFactory.createRange(5);
		v.setName("volume");
		axes.setName("axes");
		MillerSpaceMapper.saveVolume(dstPath, bean, "/group0/data0", volPath, v, axes);
		Assert.assertTrue(HDF5Utils.hasDataset(dstPath, "/processed/process/date"));
		Assert.assertTrue(HDF5Utils.hasDataset(dstPath, "/processed/process/parameters"));
		Assert.assertTrue(HDF5Utils.hasDataset(dstPath, "/processed/process/program"));
	}

	@Test
	public void testMSMProcessing() throws ScanFileHolderException, IOException, NexusException {
		File dst = File.createTempFile("MSMProcessing_hdf5", ".h5");
		String dstPath = dst.getAbsolutePath();
		String n = testFileFolder + "588193.nxs";
		String[] inputPaths = { n, n };
		MillerSpaceMapper.processVolumeWithAutoBox(inputPaths, dstPath, "inverse", 0.5, 2., true, 0.005);

		Dataset[] a = HDF5Utils.readAttributes(dstPath, "/");
		Dataset[] b = HDF5Utils.readAttributes(dstPath, "/processed");
		Dataset[] c = HDF5Utils.readAttributes(dstPath, "/processed/process/reciprocal_space");
		Dataset[] d = HDF5Utils.readAttributes(dstPath, "entry0");
		Dataset[] e = HDF5Utils.readAttributes(dstPath, "entry1");

		Assert.assertTrue(HDF5Utils.hasDataset(dstPath, "/processed/process/date"));
		Assert.assertTrue(HDF5Utils.hasDataset(dstPath, "/processed/process/parameters"));
		Assert.assertTrue(HDF5Utils.hasDataset(dstPath, "/processed/process/program"));

		boolean found = false;
		for (Dataset dataset : a) {
			if (dataset.getName().equals("default")) {
				Assert.assertEquals("/processed", dataset.getString());
				found = true;
				break;
			}
		}
		Assert.assertTrue(found);

		found = false;
		for (Dataset dataset : b) {
			if (dataset.getName().equals("default")) {
				Assert.assertEquals("/processed/process/reciprocal_space", dataset.getString());
				found = true;
				break;
			}
		}
		Assert.assertTrue(found);

		Assert.assertEquals("NXdata", c[0].getString());
		Assert.assertEquals("NXentry", d[0].getString());
		Assert.assertEquals("NXentry", e[0].getString());
	}

	@Test
	public void testLoadingI16NexusDirectBeam() throws Exception {
		String n = testFileFolder +  "535432.nxs";
		NexusHDF5Loader l = new NexusHDF5Loader();
		l.setFile(n);
		DataHolder dh = l.loadFile();
		Tree t = dh.getTree();
		int[] dshape = NexusTreeUtils.parseDetectorScanShape("/entry1/instrument/pil100k", t);

		System.err.println(Arrays.toString(dshape));

		DataNode node = (DataNode) t.findNodeLink("/entry1/pil100k/data").getDestination();
		ILazyDataset images = node.getDataset();
		
		PositionIterator diter = new PositionIterator(dshape);
		int[] dpos = diter.getPos();

		int rank = images.getRank();
		Assert.assertEquals(dshape.length, rank - 2);
		int[] axes = new int[rank - 2];
		for (int i = 0; i < axes.length; i++) {
			axes[i] = rank - 2 + i;
		}
		int[] stop = images.getShape();
		PositionIterator iter = new PositionIterator(stop, axes);
		int[] pos = iter.getPos();

		int width = stop[rank -1];
		while (iter.hasNext() && diter.hasNext()) {
			DetectorProperties dp = NexusTreeUtils.parseDetector("/entry1/instrument/pil100k", t, dpos)[0];
//			System.out.println(dp);
			for (int i = 0; i < axes.length; i++) {
				stop[i] = pos[i] + 1;
			}

			Dataset image = DatasetUtils.convertToDataset(images.getSlice(pos, stop, null));
			double[] bc = dp.getBeamCentreCoords();
			int index = image.argMax();
			System.err.println(image.max() + " @ [" + (index % width) + "," + (index / width) + "]" );
			System.err.println(Arrays.toString(dpos) + " cf " + Arrays.toString(bc));
			Assert.assertEquals(index % width, bc[0], 1);
			Assert.assertEquals(index / width, bc[1], 1);
		}
	}

	@Test
	public void testCalc() {
		Vector3d fast = new Vector3d(new double[] {0.716073184,-0.013563705,-0.697893417});
		Vector3d slow = new Vector3d(new double[] {0.009045056,0.999907550,-0.010152737});
		Vector3d orig = new Vector3d(new double[] {50.137036087,-19.798290314,522.266327889});

		// 0, 0 => 271, 94
		double delta = -0.5 - 9.2;
		double gamma = -0.5;
		double pixel = 0.172;

		Matrix3d rotn = new Matrix3d();
		rotn.mul(MatrixUtils.createRotationMatrix(new Vector3d(1, 0, 0), gamma), MatrixUtils.createRotationMatrix(new Vector3d(0, 1, 0), delta));

		System.out.println("BC: " + Arrays.toString(calculateBeamCentre(fast, slow, orig, pixel)));

		rotn.transform(fast);
		rotn.transform(slow);
		rotn.transform(orig);
		System.err.println(rotn);
		System.err.println("Fast = " + fast + ";\nslow = " + slow + ";\norig = " + orig);
		System.err.println("BC: " + Arrays.toString(calculateBeamCentre(fast, slow, orig, pixel)));
	}

	private static double[] calculateBeamCentre(Vector3d fast, Vector3d slow, Vector3d orig, double pixel) {
		Vector3d norm = new Vector3d();
		norm.cross(fast, slow);
		double d = norm.dot(orig);
		double t = d/norm.z;
		Vector3d p = new Vector3d(orig);
		p.z -= t;
		p.scale(-1/pixel);
		return new double[] {fast.dot(p), slow.dot(p)};

	}

	@Test
	public void testLoadingI16Nexus222() throws Exception {
		String n = testFileFolder +  "535434.nxs";
		NexusHDF5Loader l = new NexusHDF5Loader();
		l.setFile(n);
		DataHolder dh = l.loadFile();
		Tree t = dh.getTree();
		int[] dshape = NexusTreeUtils.parseDetectorScanShape("/entry1/instrument/pil100k", t);
		System.err.println(Arrays.toString(dshape));
		dshape = NexusTreeUtils.parseSampleScanShape("/entry1/sample", t, dshape);
		System.err.println(Arrays.toString(dshape));

		DataNode node = (DataNode) t.findNodeLink("/entry1/pil100k/data").getDestination();
		ILazyDataset images = node.getDataset();

		PositionIterator diter = new PositionIterator(dshape);
		int[] dpos = diter.getPos();

		int rank = images.getRank();
		Assert.assertEquals(dshape.length, rank - 2);
		int[] axes = new int[rank - 2];
		for (int i = 0; i < axes.length; i++) {
			axes[i] = rank - 2 + i;
		}
		int[] stop = images.getShape();
		PositionIterator iter = new PositionIterator(stop, axes);
		int[] pos = iter.getPos();

		int width = stop[rank -1];
		while (iter.hasNext() && diter.hasNext()) {
			pos = new int[] {59, 0, 0};
			dpos = new int[] {59};
			DetectorProperties dp = NexusTreeUtils.parseDetector("/entry1/instrument/pil100k", t, dpos)[0];
//			System.out.println(dp);
			for (int i = 0; i < axes.length; i++) {
				stop[i] = pos[i] + 1;
			}
			DiffractionSample sample = NexusTreeUtils.parseSample("/entry1/sample", t, true, dpos);
			DiffractionCrystalEnvironment env = sample.getDiffractionCrystalEnvironment();
			UnitCell ucell = sample.getUnitCell();
			QSpace qspace = new QSpace(dp, env);
			MillerSpace mspace = new MillerSpace(ucell, env.getOrientation());
			Vector3d vt = new Vector3d(1, 1, 1);
			Matrix3d u = env.getOrientation();
			u.transform(vt);
			System.err.println(vt);
			vt = new Vector3d(1, 1, 1);
			u = new Matrix3d(u);
			u.invert();
			u.transform(vt);
			System.err.println(vt);
			Vector3d q = mspace.q(2, 2, 2);
			System.err.println("Q(222) = " + q);

			
			int[] p = qspace.pixelPosition(q);
			System.err.println("[2,2,2] is at " + Arrays.toString(p));
			Dataset image = DatasetUtils.convertToDataset(images.getSlice(pos, stop, null));
			int index = image.argMax();
			int[] max = new int[] {index % width, index / width};
			System.err.println("Max = " + image.max() + " @ [" + max[0] + "," + max[1] + "]" );
			Vector3d v = dp.pixelPosition(max[0], max[1]);
			System.err.println("Position of max " + v);

			Vector3d qm = qspace.qFromPixelPosition(max[0], max[1]);
			System.err.println("Q of max " + q);
			double[] h = mspace.h(qm, null);
//			Assert.assertArrayEquals(new double[]{2, 2, 2}, h, 0.01);
			System.err.println("hm = " + Arrays.toString(h));
//			System.err.println(Arrays.toString(dpos) + " cf " + Arrays.toString(bc));

			break;
		}
	}

	@Ignore
	@Test
	public void testLoadingI16Nexus220() throws Exception {
		String n = testFileFolder +  "535436.nxs";
		NexusHDF5Loader l = new NexusHDF5Loader();
		l.setFile(n);
		DataHolder dh = l.loadFile();
		Tree t = dh.getTree();
		int[] dshape = NexusTreeUtils.parseDetectorScanShape("/entry1/instrument/pil100k", t);
		System.err.println(Arrays.toString(dshape));
		dshape = NexusTreeUtils.parseSampleScanShape("/entry1/sample", t, dshape);
		System.err.println(Arrays.toString(dshape));

		DataNode node = (DataNode) t.findNodeLink("/entry1/pil100k/data").getDestination();
		ILazyDataset images = node.getDataset();

		PositionIterator diter = new PositionIterator(dshape);
		int[] dpos = diter.getPos();

		int rank = images.getRank();
		Assert.assertEquals(dshape.length, rank - 2);
		int[] axes = new int[rank - 2];
		for (int i = 0; i < axes.length; i++) {
			axes[i] = rank - 2 + i;
		}
		int[] stop = images.getShape();
		PositionIterator iter = new PositionIterator(stop, axes);
		int[] pos = iter.getPos();

		int width = stop[rank -1];
		while (iter.hasNext() && diter.hasNext()) {
			pos = new int[] {64, 0, 0};
			dpos = new int[] {64};
			DetectorProperties dp = NexusTreeUtils.parseDetector("/entry1/instrument/pil100k", t, dpos)[0];
//			System.out.println(dp);
			for (int i = 0; i < axes.length; i++) {
				stop[i] = pos[i] + 1;
			}
			DiffractionSample sample = NexusTreeUtils.parseSample("/entry1/sample", t, true, dpos);
			DiffractionCrystalEnvironment env = sample.getDiffractionCrystalEnvironment();
			UnitCell ucell = sample.getUnitCell();
			QSpace qspace = new QSpace(dp, env);
			MillerSpace mspace = new MillerSpace(ucell, env.getOrientation());
			Vector3d q = mspace.q(2, 2, 0);
			int[] p = qspace.pixelPosition(q);
			System.err.println("[2,2,0] is at " + Arrays.toString(p));
			Dataset image = DatasetUtils.convertToDataset(images.getSlice(pos, stop, null));
			int index = image.argMax();
			int[] max = new int[] {index % width, index / width};
			System.err.println("Max = " + image.max() + " @ [" + max[0] + "," + max[1] + "]" );
			Vector3d v = dp.pixelPosition(max[0], max[1]);
			System.err.println("Position of max " + v);
			
			Vector3d qm = qspace.qFromPixelPosition(max[0], max[1]);
			System.err.println("Q of max " + q);
			double[] h = mspace.h(qm, null);
//			Assert.assertArrayEquals(new double[]{2, 2, 0}, h, 0.01);
			System.err.println("hm = " + Arrays.toString(h));
			break;
		}
	}

	@Test
	public void testCalcOM() {
		double a = 5.658;
		double l = 1.5498026;
		UnitCell uc = new UnitCell(a);
		ReciprocalCell rc = new ReciprocalCell(uc);
		Matrix3d b = rc.orthogonalization();

		Matrix3d ub = new Matrix3d(new double[] {0.0702410889637, -0.142725586545, 0.0770255888463,
				0.12596710768, -0.00486097276024, -0.123878988118,
				0.102155871953, 0.104130316718, 0.0997917829103});

		Matrix3d DLS = new Matrix3d(new double[] {0.13853823, -0.21091404, 0.96763755,
				-0.85203924, -0.52341831, 0.00789938,
				0.50481312, -0.82555953, -0.25222049});

		DLS.setIdentity();
//		ub = new Matrix3d(new double[] {0.46402787, 0.46402787, 0.75455701,
//				-0.70710678, 0.70710678, 0.,
//				-0.53355238, -0.53355238, 0.6562345});
//		ub.mul(b);

		System.err.println("Scale: " + ub.getScale() + " cf " + b.getScale());

		Matrix3d cbf = new Matrix3d(); // correction XXX to move from You frame to CBF  
		cbf.setM00(1);
		cbf.setM12(-1);
		cbf.setM21(1);

		Matrix3d ib = new Matrix3d();
		ib.invert(b);
		Matrix3d u = new Matrix3d();
		u.mul(ub, ib);


		System.err.println("U:\n" + u);
		System.err.println("Scale: " + u.getScale());

		//		eta -> 59 = 28.331372 : value = 5447 @ 242,112
		Matrix3d rotn = MatrixUtils.createI16KappaRotation(24.143176495338366, -132.41296642237344, 83.88336577643585, -6.014900000017587E-6);
		System.err.println("R:\n" + rotn);

		// 111
		Vector3d v, vt = new Vector3d();
		v = new Vector3d(1,1,1);
		ub.transform(v);
		cbf.transform(v);
		v.normalize();
		rotn.transform(v, vt);
		System.err.println("(111): " + v + " -> " + vt);


		rotn = MatrixUtils.createI16KappaRotation(147.17097791082858, -75.03840726472006, 49.074684325441964, -1.4088300000048013E-5);
		System.err.println("\nR:\n" + rotn);

		v.set(2,0,2);
		ub.transform(v);
		cbf.transform(v);
		v.normalize();
		rotn.transform(v, vt);
		System.err.println("(202): " + v + " -> " + vt);

		v.set(2,2,0);
		ub.transform(v);
		cbf.transform(v);
		v.normalize();
		rotn.transform(v, vt);
		System.err.println("(220): " + v + " -> " + vt);

		v = new Vector3d(0,2,2);
		ub.transform(v);
		cbf.transform(v);
		v.normalize();
		rotn.transform(v, vt);
		System.err.println("(022): " + v + " -> " + vt);

		Vector3d q = new Vector3d(2, 2, 2);
		System.err.println("\nh: " + q);
		q.scale(2*Math.PI);
		ub.transform(q);
		cbf.transform(q); // XXX correction
		rotn = MatrixUtils.createI16KappaRotation(24.143176495338366, -132.41296642237344, 83.88336577643585, -6.014900000017587E-6);
//		rotn.invert();

		System.err.println("q: " + q);
		rotn.transform(q);
		System.err.println("q: " + q);
		Vector3d k = new Vector3d(0, 0, 2*Math.PI/l);
		System.err.println("ki: " + k);
		k.add(q);
		System.err.println("kf: " + k);
		k.normalize();
		System.err.println("  :" + k);

		// [59] => 242, 112
		double delta = 56.64474488574088 - 9.2;
		double gamma = 0;
//		double pixel = 0.172;
		rotn.mul(MatrixUtils.createRotationMatrix(new Vector3d(1, 0, 0), gamma), MatrixUtils.createRotationMatrix(new Vector3d(0, 1, 0), delta));

//		System.out.println("BC: " + Arrays.toString(calculateBeamCentre(fast, slow, orig, pixel)));
	}

	@Test
	public void testMapI16Nexus222() throws ScanFileHolderException {
		String n = testFileFolder + "588193.nxs";
		MillerSpaceMapper.processVolumeWithAutoBox(n, "test-scratch/588193-medium.h5", "inverse", 0.5, 2., true, 0.005);
	}

	@Test
	public void testListI16Nexus() throws ScanFileHolderException {
		String n = testFileFolder + "588193.nxs";
		MillerSpaceMapper.processList(new String[] {n}, "test-scratch/588193-list.h5", 2.);
	}

	@Test
	public void testI16NexusCorners() throws ScanFileHolderException {
		String n = testFileFolder + "588193.nxs";
		MillerSpaceMapper.printCorners(n, true);
	}
}

/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction;


import java.io.File;
import java.util.Arrays;

import javax.vecmath.Vector3d;

import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.dawnsci.analysis.api.diffraction.DiffractionCrystalEnvironment;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
import org.eclipse.dawnsci.analysis.dataset.impl.PositionIterator;
import org.eclipse.dawnsci.hdf5.HDF5Utils;

import uk.ac.diamond.scisoft.analysis.crystallography.MillerSpace;
import uk.ac.diamond.scisoft.analysis.crystallography.UnitCell;
import uk.ac.diamond.scisoft.analysis.io.NexusHDF5Loader;
import uk.ac.diamond.scisoft.analysis.io.NexusTreeUtils;

/**
 * Map datasets in a Nexus file from image coordinates to Miller space
 */
public class MillerSpaceMapper {
	public static Dataset mapToMillerSpace(String filePath, int[] mShape, double[] mStart, double[] mStop, double mDelta) throws ScanFileHolderException {
		double hdel; // spacing between voxels in Miller space
		Dataset newmap;

		hdel = mDelta;
		newmap = DatasetFactory.zeros(mShape, Dataset.FLOAT64);
		double[] hmin = mStart;
		double[] hmax = mStop;

		NexusHDF5Loader l = new NexusHDF5Loader();
		l.setFile(filePath);
		Tree tree = l.loadFile().getTree();
		int[] dshape = NexusTreeUtils.parseDetectorScanShape("/entry1/instrument/pil100k", tree);
		System.err.println(Arrays.toString(dshape));
		dshape = NexusTreeUtils.parseSampleScanShape("/entry1/sample", tree, dshape);
		System.err.println(Arrays.toString(dshape));

		DataNode node = (DataNode) tree.findNodeLink("/entry1/pil100k/data").getDestination();
		ILazyDataset images = node.getDataset();

		PositionIterator diter = new PositionIterator(dshape);
		int[] dpos = diter.getPos();

		int rank = images.getRank();
		if (rank < 2) {
			throw new ScanFileHolderException("Image data must be at least 2D");
		}
		if (dshape.length != rank - 2) {
			throw new ScanFileHolderException("Scan shape must be 2 dimensions less than image data");
		}

		int[] axes = new int[rank - 2];
		for (int i = 0; i < axes.length; i++) {
			axes[i] = rank - 2 + i;
		}
		int[] stop = images.getShape();
		PositionIterator iter = new PositionIterator(stop, axes);
		int[] pos = iter.getPos();

		int[] hpos = new int[3];
		Vector3d q = new Vector3d();
		Vector3d h = new Vector3d(2, 2, 2);
		Vector3d p = new Vector3d(); // position of pixel
		Vector3d t = new Vector3d(); // temporary
		Vector3d dh = new Vector3d();
		while (iter.hasNext() && diter.hasNext()) {
//			pos = new int[] {59, 0, 0};
//			dpos = new int[] {59};
			DetectorProperties dp = NexusTreeUtils.parseDetector("/entry1/instrument/pil100k", tree, dpos)[0];
//			System.out.println(dp);
			for (int i = 0; i < axes.length; i++) {
				stop[i] = pos[i] + 1;
			}
			DiffractionSample sample = NexusTreeUtils.parseSample("/entry1/sample", tree, dpos);
			DiffractionCrystalEnvironment env = sample.getDiffractionCrystalEnvironment();
			UnitCell ucell = sample.getUnitCell();
			QSpace qspace = new QSpace(dp, env);
			MillerSpace mspace = new MillerSpace(ucell, env.getOrientation());
//			mspace.q(h, q);
//			int[] pix = qspace.pixelPosition(q);
//			System.err.println("[2,2,2] is at " + Arrays.toString(pix));
			//			Vector3d q = qspace.qFromPixelPosition(x, y);
//			double[] h = mspace.h(q, null);
			Dataset image = DatasetUtils.convertToDataset(images.getSlice(pos, stop, null));
			int[] s = Arrays.copyOfRange(image.getShapeRef(), axes.length, rank);
			image.setShape(s);
//			double[] bc = dp.getBeamCentreCoords();
//			
//			System.err.println(image.max() + " @ [" + (image.argMax() % width) + "," + (image.argMax() / width) + "]" );
//			System.err.println(Arrays.toString(dpos) + " cf " + Arrays.toString(bc));

			// how does voxel size map to pixel size?
			// h = -hmax, -hmax+hdel, ..., hmax-hdel, hmax
			// algorithm:
			// iterate over image pixels
			// map pixel coords to Miller space
			// find voxel coords and store
			// map back from Miller space to projected image coords
			// put interpolated pixel value in voxel
			// 
			double value;
			for (int y = 0; y < s[0]; y++) {
				for (int x = 0; x < s[1]; x++) {
					qspace.qFromPixelPosition(x, y, q);
					mspace.h(q, null, h);
					if (!hToVoxel(hdel, hmin, hmax, h, hpos))
						continue;

					mspace.q(h, q);
					qspace.pixelPosition(q, p, t);
					value = Maths.interpolate(image, t.y, t.x);

//					addValue(newmap, hpos, value);
					// Steve Collin's algorithm implemented as first attempt
					// Assumes a pixel maps to a curvilinear patch that is
					// not bigger than a voxel
					hFromVoxel(hdel, hmin, dh, hpos);
					dh.sub(h, dh);
					spreadValue(hdel, mShape, newmap, dh, hpos, value);
				}
			}
		}
		return newmap;
	}

	/**
	 * Map from h to volume coords
	 * @param hdel
	 * @param hmin
	 * @param hmax
	 * @param h
	 * @param pos
	 * @return true if within bounds
	 */
	private static boolean hToVoxel(double hdel, double[] hmin, double[] hmax, final Vector3d h, int[] pos) {
		if (h.x < hmin[0] || h.x > hmax[0] || h.y < hmin[1] || h.y > hmax[1] || 
				h.z < hmin[2] || h.z > hmax[2])
			return false;
		pos[0] = (int) Math.floor((h.x - hmin[1])/hdel);
		pos[1] = (int) Math.floor((h.y - hmin[1])/hdel);
		pos[2] = (int) Math.floor((h.z - hmin[2])/hdel);
		return true;
	}

//	private static void addValue(Dataset newmap, final int[] pos, final double value) {
//		newmap.set(newmap.getDouble(pos) + value, pos);
//	}

	/**
	 * Map back from volume to h
	 * @param hdel
	 * @param hmin
	 * @param h
	 * @param pos
	 */
	private static void hFromVoxel(double hdel, double[] hmin, final Vector3d h, final int[] pos) {
		h.x = pos[0]*hdel + hmin[0];
		h.y = pos[1]*hdel + hmin[1];
		h.z = pos[2]*hdel + hmin[2];
	}

	/**
	 * Spread the value over nearest voxels on positive octant
	 * 
	 * The value is shared with weighting of inverse distance to centre of voxels
	 * @param hdel
	 * @param mshape 
	 * @param newmap 
	 * @param dh
	 * @param pos
	 * @param value
	 */
	private static void spreadValue(double hdel, int[] mshape, Dataset newmap, final Vector3d dh, final int[] pos, final double value) {
		int[] lpos = pos.clone();

		final double[] weights = new double[8];
		double old, f, tx, ty, tz;

		final double sx = dh.x*dh.x;
		final double sy = dh.y*dh.y;
		final double sz = dh.z*dh.z;

		tx = hdel - dh.x;
		tx *= tx;
		ty = hdel - dh.y;
		ty *= ty;
		tz = hdel - dh.z;
		tz *= tz;

		if (lpos[0] == mshape[0]) { // corner, face and edge cases
			if (lpos[1] == mshape[1]) {
				if (lpos[2] == mshape[2]) {
					old = newmap.getDouble(lpos);
					newmap.set(old + value, lpos);
				} else {
					weights[0] = 1./Math.sqrt(sz);
					weights[1] = 1./Math.sqrt(tz);

					f = 1./(weights[0] + weights[1]);

					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[0]*value, lpos);

					lpos[2]++;
					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[1]*value, lpos);					
				}
			} else {
				if (lpos[2] == mshape[2]) {
					weights[0] = 1./Math.sqrt(sy);
					weights[1] = 1./Math.sqrt(ty);

					f = 1./(weights[0] + weights[1]);

					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[0]*value, lpos);

					lpos[1]++;
					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[1]*value, lpos);
				} else {
					weights[0] = 1./Math.sqrt(sy + sz);
					weights[1] = 1./Math.sqrt(ty + sz);
					weights[2] = 1./Math.sqrt(sy + tz);
					weights[3] = 1./Math.sqrt(ty + tz);

					f = 1./(weights[0] + weights[1] + weights[2] + weights[3]);

					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[0]*value, lpos);

					lpos[1]++;
					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[1]*value, lpos);
					lpos[1]--;

					lpos[2]++;
					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[2]*value, lpos);

					lpos[1]++;
					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[3]*value, lpos);
				}				
			}
		} else {
			if (lpos[1] == mshape[1]) {
				if (lpos[2] == mshape[2]) {
					weights[0] = 1./Math.sqrt(sx);
					weights[1] = 1./Math.sqrt(tx);

					f = 1./(weights[0] + weights[1]);

					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[0]*value, lpos);

					lpos[0]++;
					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[1]*value, lpos);
				} else {
					weights[0] = 1./Math.sqrt(sx + sz);
					weights[1] = 1./Math.sqrt(tx + sz);
					weights[2] = 1./Math.sqrt(sx + tz);
					weights[3] = 1./Math.sqrt(tx + tz);

					f = 1./(weights[0] + weights[1] + weights[2] + weights[3]);

					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[0]*value, lpos);

					lpos[0]++;
					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[1]*value, lpos);
					lpos[0]--;

					lpos[2]++;
					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[2]*value, lpos);

					lpos[0]++;
					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[3]*value, lpos);
					lpos[0]--;
				}
			} else {
				if (lpos[2] == mshape[2]) {
					weights[0] = 1./Math.sqrt(sx + sy);
					weights[1] = 1./Math.sqrt(tx + sy);
					weights[2] = 1./Math.sqrt(sx + ty);
					weights[3] = 1./Math.sqrt(tx + ty);

					f = 1./(weights[0] + weights[1] + weights[2] + weights[3]);

					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[0]*value, lpos);

					lpos[0]++;
					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[1]*value, lpos);
					lpos[0]--;

					lpos[1]++;
					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[2]*value, lpos);

					lpos[0]++;
					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[3]*value, lpos);
				} else {
					weights[0] = 1./Math.sqrt(sx + sy + sz);
					weights[1] = 1./Math.sqrt(tx + sy + sz);
					weights[2] = 1./Math.sqrt(sx + ty + sz);
					weights[3] = 1./Math.sqrt(tx + ty + sz);

					weights[4] = 1./Math.sqrt(sx + sy + tz);
					weights[5] = 1./Math.sqrt(tx + sy + tz);
					weights[6] = 1./Math.sqrt(sx + ty + tz);
					weights[7] = 1./Math.sqrt(tx + ty + tz);

					f = 1./(weights[0] + weights[1] + weights[2] + weights[3] + weights[4] + weights[5] + weights[6] + weights[7]);

					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[0]*value, lpos);

					lpos[0]++;
					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[1]*value, lpos);
					lpos[0]--;

					lpos[1]++;
					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[2]*value, lpos);

					lpos[0]++;
					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[3]*value, lpos);
					lpos[0]--;
					lpos[1]--;

					lpos[2]++;
					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[4]*value, lpos);

					lpos[0]++;
					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[5]*value, lpos);
					lpos[0]--;

					lpos[1]++;
					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[6]*value, lpos);

					lpos[0]++;
					old = newmap.getDouble(lpos);
					newmap.set(old + f*weights[7]*value, lpos);
				}
			}
		}
	}

	/**
	 * 
	 * @param input
	 * @param output
	 * @param mShape
	 * @param mStart
	 * @param mDelta
	 * @throws ScanFileHolderException
	 */
	public static void processVolume(String input, String output, int[] mShape, double[] mStart, double mDelta) throws ScanFileHolderException {
		double[] mStop = new double[3];
		Dataset[] a = new Dataset[3];
		for (int i = 0; i < a.length; i++) {
			double mbeg = mStart[i];
			double mend = mbeg + mShape[i]*mDelta;
			mStop[i] = mend;
			a[i] = DatasetFactory.createRange(mbeg, mend, mDelta, Dataset.FLOAT64);
		}
		Dataset v = mapToMillerSpace(input, mShape, mStart, mStop, mDelta);

		saveVolume(output, v, a);
	}

	/**
	 * @param file
	 * @param v
	 * @param axes
	 * @throws ScanFileHolderException
	 */
	public static void saveVolume(String file, Dataset v, Dataset... axes) throws ScanFileHolderException {
		File f = new File(file);
		if (f.exists()) {
			f.delete();
		}
		v.setName("volume");
		HDF5Utils.writeDataset(file, "/entry1/data", v);
		for (int i = 0; i < axes.length; i++) {
			Dataset x = axes[i];
			x.setName("axis" + i);
			HDF5Utils.writeDataset(file, "/entry1/data", x);
		}
	}
}

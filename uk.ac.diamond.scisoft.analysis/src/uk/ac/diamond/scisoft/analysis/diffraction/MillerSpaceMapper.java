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
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
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
	private String detectorPath;
	private String dataPath;
	private String samplePath;
	private double hDelta;// spacing between voxels in Miller space
	private double[] hMin; // minimum
	private double[] hMax; // maximum
	private int[] hShape;

	/**
	 * 
	 * @param detectorPath
	 * @param detectorDataName
	 * @param samplePath
	 */
	public MillerSpaceMapper(String detectorPath, String detectorDataName, String samplePath) {
		this.detectorPath = detectorPath;
		this.dataPath = detectorPath + Node.SEPARATOR + detectorDataName;
		this.samplePath = samplePath;
	}

	/**
	 * Set Miller space volume parameters
	 * @param mShape shape of volume in Miller space
	 * @param mStart starting coordinates of volume
	 * @param mStop end coordinates
	 * @param mDelta length of voxel side
	 */
	public void setMillerSpaceVolume(int[] mShape, double[] mStart, double[] mStop, double mDelta) {
		hShape = mShape;
		hDelta = mDelta;
		hMin = mStart;
		hMax = mStop;
	}

	/**
	 * Map images from given Nexus file to a volume in Miller (aka HKL) space
	 * @param filePath path to Nexus file
	 * @return dataset
	 * @throws ScanFileHolderException
	 */
	public Dataset mapToMillerSpace(String filePath) throws ScanFileHolderException {
		NexusHDF5Loader l = new NexusHDF5Loader();
		l.setFile(filePath);
		Tree tree = l.loadFile().getTree();
		int[] dshape = NexusTreeUtils.parseDetectorScanShape(detectorPath, tree);
		System.err.println(Arrays.toString(dshape));
		dshape = NexusTreeUtils.parseSampleScanShape(samplePath, tree, dshape);
		System.err.println(Arrays.toString(dshape));

		DataNode node = (DataNode) tree.findNodeLink(dataPath).getDestination();
		ILazyDataset images = node.getDataset();

		PositionIterator diter = new PositionIterator(dshape);
		int[] dpos = diter.getPos();

		int rank = images.getRank();
		int srank = rank - 2;
		if (srank < 0) {
			throw new ScanFileHolderException("Image data must be at least 2D");
		}
		if (dshape.length != srank) {
			throw new ScanFileHolderException("Scan shape must be 2 dimensions less than image data");
		}

		int[] axes = new int[2];
		for (int i = 0; i < axes.length; i++) {
			axes[i] = srank + i;
		}
		int[] stop = images.getShape();
		PositionIterator iter = new PositionIterator(stop, axes);
		int[] pos = iter.getPos();

		Dataset map = DatasetFactory.zeros(hShape, Dataset.FLOAT64);

		while (iter.hasNext() && diter.hasNext()) {
			DetectorProperties dp = NexusTreeUtils.parseDetector(detectorPath, tree, dpos)[0];
			for (int i = 0; i < srank; i++) {
				stop[i] = pos[i] + 1;
			}
			DiffractionSample sample = NexusTreeUtils.parseSample(samplePath, tree, dpos);
			DiffractionCrystalEnvironment env = sample.getDiffractionCrystalEnvironment();
			UnitCell ucell = sample.getUnitCell();
			QSpace qspace = new QSpace(dp, env);
			MillerSpace mspace = new MillerSpace(ucell, env.getOrientation());
			Dataset image = DatasetUtils.convertToDataset(images.getSlice(pos, stop, null));
			int[] s = Arrays.copyOfRange(image.getShapeRef(), srank, rank);
			image.setShape(s);
			if (image.max().doubleValue() <= 0) {
				System.err.println("Skipping image at " + Arrays.toString(pos));
				continue;
			}
//			int index = image.argMax();
//			int[] max = new int[] {index % s[1], index / s[1]};
//			qspace.qFromPixelPosition(max[0], max[1], q);
//			mspace.h(q, null, h);
//			hToVoxel(hdel, hmin, hmax, h, hpos);
//			System.err.println("Max = " + image.max() + " @ [" + max[0] + "," + max[1] + "] for " + Arrays.toString(pos) + "; h = " + h + " => " + Arrays.toString(hpos));

			mapImage(s, qspace, mspace, image, map);
		}
		return map;
	}

	private void mapImage(int[] s, QSpace qspace, MillerSpace mspace, Dataset image, Dataset map) {
		// how does voxel size map to pixel size?
		// h = -hmax, -hmax+hdel, ..., hmax-hdel, hmax
		// algorithm:
		// iterate over image pixels
		// map pixel coords to Miller space
		// find voxel coords and store
		// map back from Miller space to projected image coords
		// put interpolated pixel value in voxel
		// 
//		long vs = 0;
		int[] hpos = new int[3];
		Vector3d q = new Vector3d();
		Vector3d h = new Vector3d();
		Vector3d p = new Vector3d(); // position of pixel
		Vector3d t = new Vector3d(); // temporary
		Vector3d dh = new Vector3d();
		double value;
		for (int y = 0; y < s[0]; y++) {
			for (int x = 0; x < s[1]; x++) {
				qspace.qFromPixelPosition(x, y, q);
				mspace.h(q, null, h);
				if (!hToVoxel(h, hpos))
					continue;

				value = image.getDouble(y, x);
				if (value > 0) {
//					vs++;
					voxelToH(hpos, dh);
//					System.err.println("Adding " + value + " @" + Arrays.toString(hpos) + " or " + dh + " from " + x + ", " + y);
					addValue(map, hpos, value);
				}
//				mspace.q(h, q);
//				qspace.pixelPosition(q, p, t);
//				value = Maths.interpolate(image, t.y, t.x);
				// Steve Collin's algorithm implemented as first attempt
				// Assumes a pixel maps to a curvilinear patch that is
				// not bigger than a voxel
//				voxelToH(hpos, dh);
//				dh.sub(h, dh);
//				spreadValue(map, dh, hpos, value);
			}
		}
//		System.err.println("Values added: " + vs);

	}

	/**
	 * Map from h to volume coords
	 * @param h Miller indices
	 * @param pos volume coords
	 * @return true if within bounds
	 */
	private boolean hToVoxel(final Vector3d h, int[] pos) {
		if (h.x < hMin[0] || h.x > hMax[0] || h.y < hMin[1] || h.y > hMax[1] || 
				h.z < hMin[2] || h.z > hMax[2])
			return false;
		pos[0] = (int) Math.floor((h.x - hMin[0])/hDelta);
		pos[1] = (int) Math.floor((h.y - hMin[1])/hDelta);
		pos[2] = (int) Math.floor((h.z - hMin[2])/hDelta);
		return true;
	}

	/**
	 * Map back from volume coords to h
	 * @param pos volume coords
	 * @param h Miller indices
	 */
	private void voxelToH(final int[] pos, final Vector3d h) {
		h.x = pos[0]*hDelta + hMin[0];
		h.y = pos[1]*hDelta + hMin[1];
		h.z = pos[2]*hDelta + hMin[2];
	}

	/**
	 * Add value to map
	 * @param map
	 * @param pos
	 * @param value
	 */
	private static void addValue(Dataset map, final int[] pos, final double value) {
		map.set(map.getDouble(pos) + value, pos);
	}

	/**
	 * Spread the value over nearest voxels on positive octant
	 * 
	 * The value is shared with weighting of inverse distance to centre of voxels
	 * @param map 
	 * @param dh discretized Miller indices
	 * @param pos
	 * @param value
	 */
	private void spreadValue(Dataset map, final Vector3d dh, final int[] pos, final double value) {
		int[] lpos = pos.clone();

		final double[] weights = new double[8];
		double old, f, tx, ty, tz;

		final double sx = dh.x*dh.x;
		final double sy = dh.y*dh.y;
		final double sz = dh.z*dh.z;

		tx = hDelta - dh.x;
		tx *= tx;
		ty = hDelta - dh.y;
		ty *= ty;
		tz = hDelta - dh.z;
		tz *= tz;

		if (lpos[0] == hShape[0]) { // corner, face and edge cases
			if (lpos[1] == hShape[1]) {
				if (lpos[2] == hShape[2]) {
					old = map.getDouble(lpos);
					map.set(old + value, lpos);
				} else {
					weights[0] = 1./Math.sqrt(sz);
					weights[1] = 1./Math.sqrt(tz);

					f = 1./(weights[0] + weights[1]);

					old = map.getDouble(lpos);
					map.set(old + f*weights[0]*value, lpos);

					lpos[2]++;
					old = map.getDouble(lpos);
					map.set(old + f*weights[1]*value, lpos);					
				}
			} else {
				if (lpos[2] == hShape[2]) {
					weights[0] = 1./Math.sqrt(sy);
					weights[1] = 1./Math.sqrt(ty);

					f = 1./(weights[0] + weights[1]);

					old = map.getDouble(lpos);
					map.set(old + f*weights[0]*value, lpos);

					lpos[1]++;
					old = map.getDouble(lpos);
					map.set(old + f*weights[1]*value, lpos);
				} else {
					weights[0] = 1./Math.sqrt(sy + sz);
					weights[1] = 1./Math.sqrt(ty + sz);
					weights[2] = 1./Math.sqrt(sy + tz);
					weights[3] = 1./Math.sqrt(ty + tz);

					f = 1./(weights[0] + weights[1] + weights[2] + weights[3]);

					old = map.getDouble(lpos);
					map.set(old + f*weights[0]*value, lpos);

					lpos[1]++;
					old = map.getDouble(lpos);
					map.set(old + f*weights[1]*value, lpos);
					lpos[1]--;

					lpos[2]++;
					old = map.getDouble(lpos);
					map.set(old + f*weights[2]*value, lpos);

					lpos[1]++;
					old = map.getDouble(lpos);
					map.set(old + f*weights[3]*value, lpos);
				}				
			}
		} else {
			if (lpos[1] == hShape[1]) {
				if (lpos[2] == hShape[2]) {
					weights[0] = 1./Math.sqrt(sx);
					weights[1] = 1./Math.sqrt(tx);

					f = 1./(weights[0] + weights[1]);

					old = map.getDouble(lpos);
					map.set(old + f*weights[0]*value, lpos);

					lpos[0]++;
					old = map.getDouble(lpos);
					map.set(old + f*weights[1]*value, lpos);
				} else {
					weights[0] = 1./Math.sqrt(sx + sz);
					weights[1] = 1./Math.sqrt(tx + sz);
					weights[2] = 1./Math.sqrt(sx + tz);
					weights[3] = 1./Math.sqrt(tx + tz);

					f = 1./(weights[0] + weights[1] + weights[2] + weights[3]);

					old = map.getDouble(lpos);
					map.set(old + f*weights[0]*value, lpos);

					lpos[0]++;
					old = map.getDouble(lpos);
					map.set(old + f*weights[1]*value, lpos);
					lpos[0]--;

					lpos[2]++;
					old = map.getDouble(lpos);
					map.set(old + f*weights[2]*value, lpos);

					lpos[0]++;
					old = map.getDouble(lpos);
					map.set(old + f*weights[3]*value, lpos);
					lpos[0]--;
				}
			} else {
				if (lpos[2] == hShape[2]) {
					weights[0] = 1./Math.sqrt(sx + sy);
					weights[1] = 1./Math.sqrt(tx + sy);
					weights[2] = 1./Math.sqrt(sx + ty);
					weights[3] = 1./Math.sqrt(tx + ty);

					f = 1./(weights[0] + weights[1] + weights[2] + weights[3]);

					old = map.getDouble(lpos);
					map.set(old + f*weights[0]*value, lpos);

					lpos[0]++;
					old = map.getDouble(lpos);
					map.set(old + f*weights[1]*value, lpos);
					lpos[0]--;

					lpos[1]++;
					old = map.getDouble(lpos);
					map.set(old + f*weights[2]*value, lpos);

					lpos[0]++;
					old = map.getDouble(lpos);
					map.set(old + f*weights[3]*value, lpos);
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

					old = map.getDouble(lpos);
					map.set(old + f*weights[0]*value, lpos);

					lpos[0]++;
					old = map.getDouble(lpos);
					map.set(old + f*weights[1]*value, lpos);
					lpos[0]--;

					lpos[1]++;
					old = map.getDouble(lpos);
					map.set(old + f*weights[2]*value, lpos);

					lpos[0]++;
					old = map.getDouble(lpos);
					map.set(old + f*weights[3]*value, lpos);
					lpos[0]--;
					lpos[1]--;

					lpos[2]++;
					old = map.getDouble(lpos);
					map.set(old + f*weights[4]*value, lpos);

					lpos[0]++;
					old = map.getDouble(lpos);
					map.set(old + f*weights[5]*value, lpos);
					lpos[0]--;

					lpos[1]++;
					old = map.getDouble(lpos);
					map.set(old + f*weights[6]*value, lpos);

					lpos[0]++;
					old = map.getDouble(lpos);
					map.set(old + f*weights[7]*value, lpos);
				}
			}
		}
	}

	/**
	 * Map images from given Nexus file to a volume in Miller (aka HKL) space and save to a HDF5 file
	 * @param input path to Nexus file
	 * @param output path for saving HDF5 file
	 * @param mShape shape of volume in Miller space
	 * @param mStart starting coordinates of volume
	 * @param mDelta length of voxel side
	 * @throws ScanFileHolderException
	 */
	public void mapToVolumeFile(String input, String output, int[] mShape, double[] mStart, double mDelta) throws ScanFileHolderException {
		double[] mStop = new double[3];
		Dataset[] a = new Dataset[3];
		for (int i = 0; i < a.length; i++) {
			double mbeg = mStart[i];
			double mend = mbeg + mShape[i] * mDelta;
			mStop[i] = mend;
			a[i] = DatasetUtils.linSpace(mbeg, mend - mDelta, mShape[i], Dataset.FLOAT64);
		}
		setMillerSpaceVolume(mShape, mStart, mStop, mDelta);
		Dataset v = mapToMillerSpace(input);

		saveVolume(output, v, a);
	}

	/**
	 * Save volume and its axes to a HDF5 file
	 * @param file path for saving HDF5 file
	 * @param v volume dataset
	 * @param axes axes datasets
	 * @throws ScanFileHolderException
	 */
	public static void saveVolume(String file, Dataset v, Dataset... axes) throws ScanFileHolderException {
		File f = new File(file);
		if (f.exists()) {
			f.delete();
		}
		v.setName("volume");
		HDF5Utils.writeDataset(file, "/entry1/data", v);
		String[] axisName = new String[] {"h", "k", "l"};
		for (int i = 0; i < axes.length; i++) {
			Dataset x = axes[i];
			x.setName(axisName[i] + "-axis");
			HDF5Utils.writeDataset(file, "/entry1/data", x);
		}
	}

	private static final MillerSpaceMapper I16Mapper = new MillerSpaceMapper("/entry1/instrument/pil100k", "image_data", "/entry1/sample");

	/**
	 * Process Nexus file for I16
	 * @param input Nexus file
	 * @param output name of HDF5 file to be created
	 * @param mShape shape of output volume
	 * @param mStart start coordinates in Miller space
	 * @param mDelta side of voxels in Miller space
	 * @throws ScanFileHolderException
	 */
	public static void processVolume(String input, String output, int[] mShape, double[] mStart, double mDelta) throws ScanFileHolderException {
		I16Mapper.mapToVolumeFile(input, output, mShape, mStart, mDelta);
	}
}

/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.IntegerDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
import org.eclipse.dawnsci.analysis.dataset.impl.PositionIterator;
import org.eclipse.dawnsci.hdf5.HDF5FileFactory;
import org.eclipse.dawnsci.hdf5.HDF5Utils;

import uk.ac.diamond.scisoft.analysis.crystallography.MillerSpace;
import uk.ac.diamond.scisoft.analysis.crystallography.UnitCell;
import uk.ac.diamond.scisoft.analysis.dataset.function.BicubicInterpolator;
import uk.ac.diamond.scisoft.analysis.io.NexusHDF5Loader;
import uk.ac.diamond.scisoft.analysis.io.NexusTreeUtils;

/**
 * Map datasets in a Nexus file from image coordinates to Miller space
 */
public class MillerSpaceMapper {
	private String detectorPath;
	private String dataPath;
	private String samplePath;
	private double[] hDel; // sides of voxels in Miller space
	private double[] hMin; // minimum
	private double[] hMax; // maximum
	private int[] hShape;
	private boolean findImageBB; // find bounding box for image
	private boolean reduceToNonZeroBB; // reduce data non-zero only
	private int[] min;
	private int[] max;
	private double scale; // image upsampling factor
	int border = 0; // extra voxels surrounding volume

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
	 * Set Miller space bounding box parameters
	 * @param mShape shape of volume in Miller space
	 * @param mStart starting coordinates of volume
	 * @param mStop end coordinates
	 * @param mDelta lengths of voxel sides
	 */
	public void setMillerSpaceBoundingBox(int[] mShape, double[] mStart, double[] mStop, double[] mDelta) {
		hShape = mShape;
		hMin = mStart;
		hMax = mStop;
		hDel = mDelta;
	}

	/**
	 * Set to reduce output to sub-volume with non-zero data
	 * @param reduceToNonZero
	 */
	public void setReduceToNonZeroData(boolean reduceToNonZero) {
		reduceToNonZeroBB = reduceToNonZero;
	}

	/**
	 * Set scale for upsampling images
	 * @param scale
	 */
	public void setUpsamplingScale(double scale) {
		this.scale = scale;
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

		if (findImageBB) {
			Arrays.fill(hMin, Double.POSITIVE_INFINITY);
			Arrays.fill(hMax, Double.NEGATIVE_INFINITY);
	
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
	
				calcMillerVolume(hMin, hMax, qspace, mspace);
			}
			for (int i = 0; i < 3; i++) {
				hMin[i] = hDel[i] * (Math.floor(hMin[i] / hDel[i]) - border);
				hMax[i] = hDel[i] * (Math.ceil(hMax[i] / hDel[i]) + border);
				hShape[i] = (int) (Math.floor((hMax[i] - hMin[i] + hDel[i]) / hDel[i]));
			}
		}

		DoubleDataset map = (DoubleDataset) DatasetFactory.zeros(hShape, Dataset.FLOAT64);
		IntegerDataset tally = (IntegerDataset) DatasetFactory.zeros(hShape, Dataset.INT32);
		iter.reset();
		diter.reset();
		if (reduceToNonZeroBB) {
			min = map.getShape();
			max = new int[3];
			Arrays.fill(max, -1);
		}
		int[] ishape = Arrays.copyOfRange(images.getShape(), srank, rank);
		BicubicInterpolator upSampler = null;
		if (scale != 1) {
			for (int i = 0; i < ishape.length; i++) {
				ishape[i] *= scale;
			}
			upSampler = new BicubicInterpolator(ishape);
		}
		while (iter.hasNext() && diter.hasNext()) {
			DetectorProperties dp = NexusTreeUtils.parseDetector(detectorPath, tree, dpos)[0];
			dp.setHPxSize(dp.getHPxSize()/scale);
			dp.setVPxSize(dp.getVPxSize()/scale);
			if (upSampler != null) {
				dp.setPx(ishape[0]);
				dp.setPy(ishape[1]);
			}
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
			if (upSampler != null) {
				image = upSampler.value(image).get(0);
				s = ishape;
			}
			mapImage(s, qspace, mspace, image, map, tally);
		}
		Maths.dividez(map, tally, map); // normalize by tally

		if (findImageBB) {
			System.err.println("Extent of Miller space was found to be " + Arrays.toString(hMin) + " to " + Arrays.toString(hMax));
			System.err.println("with shape = " + Arrays.toString(hShape));
		}
		if (reduceToNonZeroBB) {
			System.err.println("Reduced to non-zero bounding box: " + Arrays.toString(min) + " to " + Arrays.toString(max));
			for (int i = 0; i < 3; i++) {
				hMin[i] += min[i]*hDel[i];
				max[i]++;
				hShape[i] = max[i] - min[i];
			}
			System.err.println("so now start = " + Arrays.toString(hMin) + " for shape = " + Arrays.toString(hShape));
			map = (DoubleDataset) map.getSlice(min, max, null);
		}
		return map;
	}

	private void calcMillerVolume(double[] mBeg, double[] mEnd, QSpace qspace, MillerSpace mspace) {
		Vector3d q = new Vector3d();
		Vector3d m = new Vector3d();
		DetectorProperties dp = qspace.getDetectorProperties();
		int x = dp.getPx();
		int y = dp.getPy();

		qspace.qFromPixelPosition(0, 0, q);
		mspace.h(q, null, m);
		minMax(mBeg, mEnd, m);

		qspace.qFromPixelPosition(x/2, 0, q);
		mspace.h(q, null, m);
		minMax(mBeg, mEnd, m);

		qspace.qFromPixelPosition(x, 0, q);
		mspace.h(q, null, m);
		minMax(mBeg, mEnd, m);

		qspace.qFromPixelPosition(0, y/2, q);
		mspace.h(q, null, m);
		minMax(mBeg, mEnd, m);

		qspace.qFromPixelPosition(x/2, y/2, q);
		mspace.h(q, null, m);
		minMax(mBeg, mEnd, m);

		qspace.qFromPixelPosition(x, y/2, q);
		mspace.h(q, null, m);
		minMax(mBeg, mEnd, m);

		qspace.qFromPixelPosition(0, y, q);
		mspace.h(q, null, m);
		minMax(mBeg, mEnd, m);

		qspace.qFromPixelPosition(x/2, y, q);
		mspace.h(q, null, m);
		minMax(mBeg, mEnd, m);

		qspace.qFromPixelPosition(x, y, q);
		mspace.h(q, null, m);
		minMax(mBeg, mEnd, m);
	}

	private static void minMax(double[] min, double[] max, Vector3d v) {
		min[0] = Math.min(min[0], v.x);
		max[0] = Math.max(max[0], v.x);
		min[1] = Math.min(min[1], v.y);
		max[1] = Math.max(max[1], v.y);
		min[2] = Math.min(min[2], v.z);
		max[2] = Math.max(max[2], v.z);
	}

	private void mapImage(int[] s, QSpace qspace, MillerSpace mspace, Dataset image, DoubleDataset map, IntegerDataset tally) {
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
		double fkmod = 1e-6*qspace.getInitialWavevector().length();
//		double threshold = Stats.quantile(image, 1. - 10./image.getSize());
//		System.err.println("Threshold is " + threshold + " cf max = " + image.max());
//		Dataset mask = Comparisons.lessThan(image, threshold);
//		image.setByBoolean(0, mask);
//		image = Image.gaussianBlurFilter(image, 30);
		for (int y = 0; y < s[0]; y++) {
			for (int x = 0; x < s[1]; x++) {
				qspace.qFromPixelPosition(x, y, q);
//				qspace.pixelPosition(q, t);
//				if (Math.abs(x - t.x) > 1 || Math.abs(y - t.y) > 1) {
//					System.err.println("Q out");
//				}
				mspace.h(q, null, h);
//				mspace.q(h, t);
//				if (Math.abs(q.x - t.x) > fkmod || Math.abs(q.y - t.y) > fkmod || Math.abs(q.z - t.z) > fkmod) {
//					System.err.println("H out");
//				}
				if (!hToVoxel(h, hpos))
					continue;

				value = image.getDouble(y, x);
				if (value > 0) {
//					vs++;
//					voxelToH(hpos, dh);
//					System.err.println("Adding " + value + " @" + Arrays.toString(hpos) + " or " + dh + " from " + x + ", " + y);
					if (reduceToNonZeroBB) {
						minMax(min, max, hpos);
					}
					addValue(map, hpos, value, tally);
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

	private static void minMax(int[] min, int[] max, int[] p) {
		min[0] = Math.min(min[0], p[0]);
		max[0] = Math.max(max[0], p[0]);
		min[1] = Math.min(min[1], p[1]);
		max[1] = Math.max(max[1], p[1]);
		min[2] = Math.min(min[2], p[2]);
		max[2] = Math.max(max[2], p[2]);
	}

	/**
	 * Map from h to volume coords
	 * @param h Miller indices
	 * @param pos volume coords
	 * @return true if within bounds
	 */
	private boolean hToVoxel(final Vector3d h, int[] pos) {
		if (!findImageBB) {
			if (h.x < hMin[0] || h.x >= hMax[0] || h.y < hMin[1] || h.y >= hMax[1] || 
					h.z < hMin[2] || h.z >= hMax[2]) {
				return false;
			}
		}

		pos[0] = (int) Math.floor((h.x - hMin[0])/hDel[0]);
		pos[1] = (int) Math.floor((h.y - hMin[1])/hDel[1]);
		pos[2] = (int) Math.floor((h.z - hMin[2])/hDel[2]);

		return true;
	}

	/**
	 * Map back from volume coords to h
	 * @param pos volume coords
	 * @param h Miller indices
	 */
	private void voxelToH(final int[] pos, final Vector3d h) {
		h.x = pos[0]*hDel[0] + hMin[0];
		h.y = pos[1]*hDel[1] + hMin[1];
		h.z = pos[2]*hDel[2] + hMin[2];
	}

	/**
	 * Add value to map
	 * @param map
	 * @param pos
	 * @param value
	 */
	private static void addValue(DoubleDataset map, final int[] pos, final double value, IntegerDataset tally) {
		int index = map.get1DIndex(pos);
		map.setAbs(index, map.getAbs(index) + value);
		index = tally.get1DIndex(pos);
		tally.setAbs(index, tally.getAbs(index) + 1);
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

		tx = hDel[0] - dh.x;
		tx *= tx;
		ty = hDel[1] - dh.y;
		ty *= ty;
		tz = hDel[2] - dh.z;
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
	 * @param scale scale for upsampling images
	 * @param reduceToNonZero if true, reduce output to sub-volume with non-zero data
	 * @param mShape shape of volume in Miller space (can be null to be autoset)
	 * @param mStart starting coordinates of volume (can be null to be autoset)
	 * @param mDelta lengths of voxel sides
	 * @throws ScanFileHolderException
	 */
	public void mapToVolumeFile(String input, String output, double scale, boolean reduceToNonZero, int[] mShape, double[] mStart, double[] mDelta) throws ScanFileHolderException {
		Dataset[] a = new Dataset[3];
		double[] mStop = new double[3];
		if (mDelta == null || mDelta.length == 0) {
			throw new IllegalArgumentException("At least one length must be specified");
		} else if (mDelta.length == 1) {
			double d = mDelta[0];
			mDelta = new double[] {d, d, d};
		} else if (mDelta.length == 2) {
			double d = mDelta[1];
			mDelta = new double[] {mDelta[0], d, d};
		}
		if (mShape == null || mStart == null) {
			findImageBB = true;
			mShape = new int[3];
			mStart = new double[3];
		} else {
			for (int i = 0; i < a.length; i++) {
				double mbeg = mStart[i];
				double mend = mbeg + mShape[i] * mDelta[i];
				mStop[i] = mend;
				a[i] = DatasetUtils.linSpace(mbeg, mend - mDelta[i], mShape[i], Dataset.FLOAT64);
				System.err.println("Axis " + i + ": " + mbeg + " -> " + a[i].getDouble(mShape[i] - 1) +  "; " + mend);
			}
		}

		setReduceToNonZeroData(reduceToNonZero);
		setUpsamplingScale(scale);
		setMillerSpaceBoundingBox(mShape, mStart, mStop, mDelta);
		Dataset v = mapToMillerSpace(input);
		if (findImageBB) {
			for (int i = 0; i < a.length; i++) {
				double mbeg = mStart[i];
				double mend = mbeg + mShape[i] * mDelta[i];
				a[i] = DatasetUtils.linSpace(mbeg, mend - mDelta[i], mShape[i], Dataset.FLOAT64);
				System.err.println("Axis " + i + ": " + mbeg + " -> " + a[i].getDouble(mShape[i] - 1) +  "; " + mend);
			}
		}
		saveVolume(output, v, a);
	}

	private static final String[] AXIS_NAME = new String[] {"h", "k", "l"};

	/**
	 * Save volume and its axes to a HDF5 file
	 * @param file path for saving HDF5 file
	 * @param v volume dataset
	 * @param axes axes datasets
	 * @throws ScanFileHolderException
	 */
	public static void saveVolume(String file, Dataset v, Dataset... axes) throws ScanFileHolderException {
		HDF5FileFactory.deleteFile(file);

		v.setName("volume");
		HDF5Utils.writeDataset(file, "/entry1/data", v);
		for (int i = 0; i < axes.length; i++) {
			Dataset x = axes[i];
			x.setName(AXIS_NAME[i] + "-axis");
			HDF5Utils.writeDataset(file, "/entry1/data", x);
		}

		List<Dataset> attrs = new ArrayList<>();
		Dataset a;

		a = DatasetFactory.createFromObject("NXdata");
		a.setName("NX_class");
		attrs.add(a);

		a = DatasetFactory.createFromObject("volume");
		a.setName("signal");
		attrs.add(a);

		a = DatasetFactory.createFromObject(new String[] {"h-axis", "k-axis", "l-axis"});
		a.setName("axes");
		attrs.add(a);

		a = DatasetFactory.createFromObject(0);
		a.setName("h-axis_indices");
		attrs.add(a);

		a = DatasetFactory.createFromObject(1);
		a.setName("k-axis_indices");
		attrs.add(a);

		a = DatasetFactory.createFromObject(2);
		a.setName("l-axis_indices");
		attrs.add(a);

		HDF5Utils.writeAttributes(file, "/entry1/data", attrs.toArray(new Dataset[attrs.size()]));
	}

	private static final MillerSpaceMapper I16Mapper = new MillerSpaceMapper("/entry1/instrument/pil100k", "image_data", "/entry1/sample");

	/**
	 * Process Nexus file for I16
	 * @param input Nexus file
	 * @param output name of HDF5 file to be created
	 * @param mShape shape of output volume
	 * @param mStart start coordinates in Miller space
	 * @param mDelta sides of voxels in Miller space
	 * @throws ScanFileHolderException
	 */
	public static void processVolume(String input, String output, int[] mShape, double[] mStart, double... mDelta) throws ScanFileHolderException {
		processVolume(input, output, 1, mShape, mStart, mDelta);
	}

	/**
	 * Process Nexus file for I16
	 * @param input Nexus file
	 * @param output name of HDF5 file to be created
	 * @param scale upsampling factor
	 * @param mShape shape of output volume
	 * @param mStart start coordinates in Miller space
	 * @param mDelta sides of voxels in Miller space
	 * @throws ScanFileHolderException
	 */
	public static void processVolume(String input, String output, double scale, int[] mShape, double[] mStart, double... mDelta) throws ScanFileHolderException {
		I16Mapper.mapToVolumeFile(input, output, scale, false, mShape, mStart, mDelta);
	}

	/**
	 * Process Nexus file for I16 with automatic bounding box setting
	 * @param input Nexus file
	 * @param output name of HDF5 file to be created
	 * @param mDelta sides of voxels in Miller space
	 * @param reduceToNonZero if true, reduce output to sub-volume with non-zero data
	 * @throws ScanFileHolderException
	 */
	public static void processVolumeWithAutoBox(String input, String output, boolean reduceToNonZero, double... mDelta) throws ScanFileHolderException {
		processVolumeWithAutoBox(input, output, 1, reduceToNonZero, mDelta);
	}

	/**
	 * Process Nexus file for I16 with automatic bounding box setting
	 * @param input Nexus file
	 * @param output name of HDF5 file to be created
	 * @param scale upsampling factor
	 * @param mDelta sides of voxels in Miller space
	 * @param reduceToNonZero if true, reduce output to sub-volume with non-zero data
	 * @throws ScanFileHolderException
	 */
	public static void processVolumeWithAutoBox(String input, String output, double scale, boolean reduceToNonZero, double... mDelta) throws ScanFileHolderException {
		I16Mapper.mapToVolumeFile(input, output, scale, reduceToNonZero, null, null, mDelta);
	}
}

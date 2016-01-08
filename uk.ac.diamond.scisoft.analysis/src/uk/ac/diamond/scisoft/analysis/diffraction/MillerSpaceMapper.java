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
import org.eclipse.dawnsci.analysis.api.dataset.SliceND;
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
import org.eclipse.dawnsci.analysis.dataset.impl.LazyWriteableDataset;
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
 * Intensity value splitter that splits an image pixel value and adds the pieces to close-by voxels
 */
interface PixelSplitter {
	/**
	 * Spread a pixel intensity value over voxels near position
	 * @param volume dataset that holds the voxel values
	 * @param weight dataset that holds the relative contributions from each pixel
	 * @param vsize voxel size in reciprocal space
	 * @param dh offset in reciprocal space from voxel corner
	 * @param pos position in volume
	 * @param value pixel intensity to split
	 */
	public void splitValue(DoubleDataset volume, DoubleDataset weight, final double[] vsize, final Vector3d dh, final int[] pos, final double value);
}

/**
 * Map datasets in a Nexus file from image coordinates to Miller space
 */
public class MillerSpaceMapper {
	private String detectorPath;
	private String dataPath;
	private String samplePath;
	private String attenuatorPath;
	private double[] hDel; // sides of voxels in Miller space
	private double[] hMin; // minimum
	private double[] hMax; // maximum
	private int[] hShape;
	private boolean findImageBB; // find bounding box for image
	private boolean reduceToNonZeroBB; // reduce data non-zero only
	private int[] min;
	private int[] max;
	private double scale; // image upsampling factor

	private PixelSplitter splitter;

	/**
	 * This does not split pixel but places its value in the nearest voxel
	 */
	static class NonSplitter implements PixelSplitter {
		@Override
		public void splitValue(DoubleDataset volume, DoubleDataset weight, final double[] vsize, Vector3d dh, int[] pos, double value) {
			addToDataset(volume, pos, value);
			addToDataset(weight, pos, 1);
		}
	}

	/**
	 * Split pixel over eight voxels with weight determined by 1/distance
	 */
	static class InverseSplitter implements PixelSplitter {
		/**
		 * Weight function of distance squared
		 * @param squaredDistance 
		 * @return 1/distance
		 */
		protected double calcWeight(double squaredDistance) {
			return squaredDistance == 0 ? Double.POSITIVE_INFINITY : 1. / Math.sqrt(squaredDistance);
		}

		double[] weights = new double[8];
		double factor;

		/**
		 * Calculate weights
		 * @param vd size of voxel
		 * @param dx displacement in x from first voxel
		 * @param dy displacement in y from first voxel
		 * @param dz displacement in z from first voxel
		 */
		void calcWeights(double[] vd, double dx, double dy, double dz) {
			final double dxs = dx * dx;
			final double dys = dy * dy;
			final double dzs = dz * dz;
			final double cx = vd[0] - dx;
			final double cy = vd[1] - dy;
			final double cz = vd[2] - dz;
			final double cxs = cx * cx;
			final double cys = cy * cy;
			final double czs = cz * cz;

			weights[0] = calcWeight(dxs + dys + dzs);
			weights[1] = calcWeight(cxs + dys + dzs);
			weights[2] = calcWeight(dxs + cys + dzs);
			weights[3] = calcWeight(cxs + cys + dzs);
			weights[4] = calcWeight(dxs + dys + czs);
			weights[5] = calcWeight(cxs + dys + czs);
			weights[6] = calcWeight(dxs + cys + czs);
			weights[7] = calcWeight(cxs + cys + czs);

			double tw = weights[1] + weights[2] + weights[3] + weights[4] + weights[5] + weights[6] + weights[7];
			if (Double.isInfinite(weights[0])) {
				weights[0] = 1e3 * tw; // make voxel an arbitrary factor larger 
			}
			factor = 1./(weights[0] + tw);
		}

		@Override
		public void splitValue(DoubleDataset volume, DoubleDataset weight, final double[] vsize, Vector3d dh, int[] pos, double value) {
			calcWeights(vsize, dh.x, dh.y, dh.z);
			int[] vShape = volume.getShapeRef();

			double w;
			int[] lpos = pos.clone();

			w = factor * weights[0];
			addToDataset(volume, lpos, w * value);
			addToDataset(weight, lpos, w);

			lpos[0]++;
			if (lpos[0] >= 0 && lpos[0] < vShape[0]) {
				w = factor * weights[1];
				if (w > 0) {
					addToDataset(volume, lpos, w * value);
					addToDataset(weight, lpos, w);
				}
			}
			lpos[0]--;

			lpos[1]++;
			if (lpos[1] >= 0 && lpos[1] < vShape[1]) {
				w = factor * weights[2];
				if (w > 0) {
					addToDataset(volume, lpos, w * value);
					addToDataset(weight, lpos, w);
				}

				lpos[0]++;
				if (lpos[0] >= 0 && lpos[0] < vShape[0]) {
					w = factor * weights[3];
					if (w > 0) {
						addToDataset(volume, lpos, w * value);
						addToDataset(weight, lpos, w);
					}
				}
				lpos[0]--;
			}
			lpos[1]--;

			lpos[2]++;
			if (lpos[2] >= 0 && lpos[2] < vShape[2]) {
				w = factor * weights[4];
				if (w > 0) {
					addToDataset(volume, lpos, w * value);
					addToDataset(weight, lpos, w);
				}

				lpos[0]++;
				if (lpos[0] >= 0 && lpos[0] < vShape[0]) {
					w = factor * weights[5];
					if (w > 0) {
						addToDataset(volume, lpos, w * value);
						addToDataset(weight, lpos, w);
					}
				}
				lpos[0]--;

				lpos[1]++;
				if (lpos[1] >= 0 && lpos[1] < vShape[1]) {
					w = factor * weights[6];
					if (w > 0) {
						addToDataset(volume, lpos, w * value);
						addToDataset(weight, lpos, w);
					}

					lpos[0]++;
					if (lpos[0] >= 0 && lpos[0] < vShape[0]) {
						w = factor * weights[7];
						if (w > 0) {
							addToDataset(volume, lpos, w * value);
							addToDataset(weight, lpos, w);
						}
					}
				}
			}
		}
	}

	/**
	 * Split pixel over eight voxels with weight determined by exp(-log(2)*(distance/hwhm)^2)
	 */
	static class GaussianSplitter extends InverseSplitter {
		private double f;

		public GaussianSplitter(double hwhm) {
			f = Math.log(2) / (hwhm * hwhm);
		}

		@Override
		protected double calcWeight(double ds) {
			return Math.exp(-ds*f);
		}
	}

	/**
	 * Split pixel over eight voxels with weight determined by exp(-log(2)*(distance/hm))
	 */
	static class ExponentialSplitter extends InverseSplitter {
		private double f;

		public ExponentialSplitter(double hm) {
			f = Math.log(2) / hm;
		}

		@Override
		protected double calcWeight(double ds) {
			return Math.exp(-Math.sqrt(ds)*f);
		}
	}

	/**
	 * 
	 * @param entryPath
	 * @param instrument name of instrument in entry
	 * @param attenuator name of attenuator in instrument
	 * @param detector name of detector in instrument 
	 * @param data name of data in detector
	 * @param sample name of sample in entry
	 */
	public MillerSpaceMapper(String entryPath, String instrument, String attenuator, String detector, String data, String sample) {
		String instrumentPath = entryPath + Node.SEPARATOR + instrument + Node.SEPARATOR;
		detectorPath = instrumentPath + detector;
		attenuatorPath = instrumentPath + attenuator;
		dataPath = detectorPath + Node.SEPARATOR + data;
		samplePath = entryPath + Node.SEPARATOR + sample;
		this.splitter = new NonSplitter();
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
	 * Set pixel value splitter
	 * @param splitter
	 */
	public void setSplitter(PixelSplitter splitter) {
		this.splitter = splitter;
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
		PositionIterator[] iters = getPositionIterators(tree);

		if (findImageBB) {
			Arrays.fill(hMin, Double.POSITIVE_INFINITY);
			Arrays.fill(hMax, Double.NEGATIVE_INFINITY);
			findBoundingBox(tree, iters);
			System.err.println("Extent of Miller space was found to be " + Arrays.toString(hMin) + " to " + Arrays.toString(hMax));
			System.err.println("with shape = " + Arrays.toString(hShape));
		}

		if (reduceToNonZeroBB) {
			min = hShape.clone();
			max = new int[3];
			Arrays.fill(max, -1);
		}

		DoubleDataset map = (DoubleDataset) DatasetFactory.zeros(hShape, Dataset.FLOAT64);
		DoubleDataset weight = (DoubleDataset) DatasetFactory.zeros(hShape, Dataset.FLOAT64);

		mapToMillerSpace(tree, iters, map, weight);

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

	private void mapToMillerSpace(Tree tree, PositionIterator[] iters, DoubleDataset map, DoubleDataset weight) throws ScanFileHolderException {
		int[] dshape = iters[0].getShape();

		Dataset trans = NexusTreeUtils.parseAttenuator(attenuatorPath, tree);
		if (trans != null && trans.getSize() != 1) {
			int[] tshape = trans.getShapeRef();
			if (!Arrays.equals(tshape, dshape)) {
				throw new ScanFileHolderException("Attenuator transmission shape does not match detector or sample scan shape");
			}
		}

		DataNode node = (DataNode) tree.findNodeLink(dataPath).getDestination();
		ILazyDataset images = node.getDataset();
		int rank = images.getRank();
		int[] ishape = Arrays.copyOfRange(images.getShape(), rank - 2, rank);

		BicubicInterpolator upSampler = null;
		if (scale != 1) {
			for (int i = 0; i < ishape.length; i++) {
				ishape[i] *= scale;
			}
			upSampler = new BicubicInterpolator(ishape);
		}

		mapImages(tree, trans, images, iters, map, weight, ishape, upSampler);
		Maths.dividez(map, weight, map); // normalize by tally
	}

	private void findBoundingBox(Tree tree, PositionIterator[] iters) {
		PositionIterator diter = iters[0];
		PositionIterator iter = iters[1];
		int[] dpos = diter.getPos();
		int[] pos = iter.getPos();
		int[] stop = iter.getStop().clone();
		int srank = pos.length - 2;

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
			hMin[i] = hDel[i] * Math.floor(hMin[i] / hDel[i]);
			hMax[i] = hDel[i] * (Math.ceil(hMax[i] / hDel[i]) + 1);
			hShape[i] = (int) (Math.floor((hMax[i] - hMin[i] + hDel[i]) / hDel[i]));
		}
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

	private void mapImages(Tree tree, Dataset trans, ILazyDataset images, PositionIterator[] iters,
			DoubleDataset map, DoubleDataset weight, int[] ishape, BicubicInterpolator upSampler) {
		PositionIterator diter = iters[0];
		PositionIterator iter = iters[1];
		iter.reset();
		diter.reset();
		int[] dpos = diter.getPos();
		int[] pos = iter.getPos();
		int[] stop = iter.getStop().clone();
		int rank  = pos.length;
		int srank = rank - 2;

		while (iter.hasNext() && diter.hasNext()) {
			DetectorProperties dp = NexusTreeUtils.parseDetector(detectorPath, tree, dpos)[0];
			dp.setHPxSize(dp.getHPxSize() / scale);
			dp.setVPxSize(dp.getVPxSize() / scale);
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
			if (trans != null) {
				if (trans.getSize() == 1) {
					image.idivide(trans.getElementDoubleAbs(0));
				} else {
					image.idivide(trans.getDouble(dpos));
				}
			}
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
			mapImage(s, qspace, mspace, image, map, weight);
		}
	}

	private static void minMax(double[] min, double[] max, Vector3d v) {
		min[0] = Math.min(min[0], v.x);
		max[0] = Math.max(max[0], v.x);
		min[1] = Math.min(min[1], v.y);
		max[1] = Math.max(max[1], v.y);
		min[2] = Math.min(min[2], v.z);
		max[2] = Math.max(max[2], v.z);
	}

	private void mapImage(int[] s, QSpace qspace, MillerSpace mspace, Dataset image, DoubleDataset map, DoubleDataset weight) {
		int[] hpos = new int[3]; // voxel position
		Vector3d q = new Vector3d();
		Vector3d h = new Vector3d();
		Vector3d dh = new Vector3d(); // delta in h

		double value;
//		double fkmod = 1e-6*qspace.getInitialWavevector().length();

		for (int y = 0; y < s[0]; y++) {
			for (int x = 0; x < s[1]; x++) {
				qspace.qFromPixelPosition(x + 0.5, y + 0.5, q);

				mspace.h(q, null, h);

				if (!hToVoxel(h, dh, hpos))
					continue;

				value = image.getDouble(y, x);
				if (value > 0) {
					if (reduceToNonZeroBB) {
						minMax(min, max, hpos);
					}
					splitter.splitValue(map, weight, hDel, dh, hpos, value);
				}
			}
		}
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
	 * @param deltaH delta in Miller indices
	 * @param pos volume coords
	 * @return true if within bounds
	 */
	private boolean hToVoxel(final Vector3d h, final Vector3d deltaH, int[] pos) {
		if (!findImageBB) {
			if (h.x < hMin[0] || h.x >= hMax[0] || h.y < hMin[1] || h.y >= hMax[1] || 
					h.z < hMin[2] || h.z >= hMax[2]) {
				return false;
			}
		}

		int p;
		double dh, hd;

		dh = h.x - hMin[0];
		hd = hDel[0];
		p = (int) Math.floor(dh / hd);
		deltaH.x = dh - p * hd;
		pos[0] = p;

		dh = h.y - hMin[1];
		hd = hDel[1];
		p = (int) Math.floor(dh / hd);
		deltaH.y = dh - p * hd;
		pos[1] = p;

		dh = h.z - hMin[2];
		hd = hDel[2];
		p = (int) Math.floor(dh / hd);
		deltaH.z = dh - p * hd;
		pos[2] = p;

		return true;
	}

	private static void addToDataset(DoubleDataset d, final int[] pos, double v) {
		final int index = d.get1DIndex(pos);
		d.setAbs(index, d.getAbs(index) + v);
	}

	/**
	 * Map images from given Nexus files to a volume in Miller (aka HKL) space and save to a HDF5 file
	 * @param inputs paths to Nexus files
	 * @param output path for saving HDF5 file
	 * @param scale scale for upsampling images
	 * @param reduceToNonZero if true, reduce output to sub-volume with non-zero data
	 * @param mShape shape of volume in Miller space (can be null to be autoset)
	 * @param mStart starting coordinates of volume (can be null to be autoset)
	 * @param mDelta lengths of voxel sides
	 * @throws ScanFileHolderException
	 */
	public void mapToVolumeFile(String[] inputs, String output, double scale, boolean reduceToNonZero, int[] mShape, double[] mStart, double[] mDelta) throws ScanFileHolderException {
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
			createAxes(a, mShape, mStart, mStop, mDelta);
		}

		setReduceToNonZeroData(reduceToNonZero);
		setUpsamplingScale(scale);
		setMillerSpaceBoundingBox(mShape, mStart, mStop, mDelta);

		int n = inputs.length;
		Tree[] trees = new Tree[n];
		PositionIterator[][] allIters = new PositionIterator[n][];
		for (int i = 0; i < n; i++) {
			NexusHDF5Loader l = new NexusHDF5Loader();
			l.setFile(inputs[i]);
			trees[i] = l.loadFile().getTree();
			allIters[i] = getPositionIterators(trees[i]);
		}

		if (findImageBB) {
			Arrays.fill(hMin, Double.POSITIVE_INFINITY);
			Arrays.fill(hMax, Double.NEGATIVE_INFINITY);

			for (int i = 0; i < n; i++) {
				findBoundingBox(trees[i], allIters[i]);
			}
			System.err.println("Extent of Miller space was found to be " + Arrays.toString(hMin) + " to " + Arrays.toString(hMax));
			System.err.println("with shape = " + Arrays.toString(hShape));
		}

		if (reduceToNonZeroBB) {
			min = hShape.clone();
			max = new int[3];
			Arrays.fill(max, -1);
		}

		try {
			DoubleDataset map = (DoubleDataset) DatasetFactory.zeros(hShape, Dataset.FLOAT64);
			DoubleDataset weight = (DoubleDataset) DatasetFactory.zeros(hShape, Dataset.FLOAT64);

			for (int i = 0; i < n; i++) {
				Tree tree = trees[i];
				mapToMillerSpace(tree, allIters[i], map, weight);
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

			if (findImageBB) {
				createAxes(a, hShape, hMin, null, hDel);
			}
			saveVolume(output, map, a);
		} catch (OutOfMemoryError e) {
			System.err.println("There is not enough memory to do this all at once!");
			System.err.println("Now attempting to segment volume");
			if (findImageBB) {
				createAxes(a, hShape, hMin, null, hDel);
			}

			// unset these as code does not or should not handle them
			findImageBB = false;
			reduceToNonZeroBB = false;

			int parts = 1;
			DoubleDataset map = null;
			DoubleDataset weight = null;
			// find biggest size that fits
			int[] tShape = hShape.clone();
			while (true) {
				parts++;
				tShape[0] = (hShape[0] + parts - 1)/parts;
				if (tShape[0] == 1) { // maybe use other dimensions too(!)
					break;
				}
				try {
					map = (DoubleDataset) DatasetFactory.zeros(tShape, Dataset.FLOAT64);
					weight = (DoubleDataset) DatasetFactory.zeros(tShape, Dataset.FLOAT64);
					break;
				} catch (OutOfMemoryError ex) {
					map = null;
				}
			}

			if (map == null || weight == null) {
				System.err.println("Cannot segment volume fine enough to fit in memory!");
				throw e;
			}
			System.out.println("Mapping in " + parts + " parts");
			HDF5FileFactory.deleteFile(output);

			int[] cShape = hShape.clone();
			cShape[0] = 1;
			LazyWriteableDataset lazy = HDF5Utils.createLazyDataset(output, "/entry1/data", "volume", hShape, null, cShape, Dataset.FLOAT64, null, false);
			mapAndSaveInParts(trees, allIters, lazy, parts, map, weight);

			saveAxesAndAttributes(output, a);
		}
	}

	private static void createAxes(Dataset[] a, int[] mShape, double[] mStart, double[] mStop, double[] mDelta) {
		for (int i = 0; i < a.length; i++) {
			double mbeg = mStart[i];
			double mend = mbeg + mShape[i] * mDelta[i];
			if (mStop != null) {
				mStop[i] = mend;
			}
			a[i] = DatasetUtils.linSpace(mbeg, mend - mDelta[i], mShape[i], Dataset.FLOAT64);
			System.out.print("Axis " + i + ": " + mbeg);
			if (mShape[i] > 1) {
				System.out.print(" -> " + a[i].getDouble(mShape[i] - 1));
			}
			System.out.println("; " + mend);
		}
	}

	private PositionIterator[] getPositionIterators(Tree tree) throws ScanFileHolderException {
		int[] dshape = NexusTreeUtils.parseDetectorScanShape(detectorPath, tree);
		System.err.println(Arrays.toString(dshape));
		dshape = NexusTreeUtils.parseSampleScanShape(samplePath, tree, dshape);
		System.err.println(Arrays.toString(dshape));

		DataNode node = (DataNode) tree.findNodeLink(dataPath).getDestination();
		ILazyDataset images = node.getDataset();

		PositionIterator diter = new PositionIterator(dshape);

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
		PositionIterator iter = new PositionIterator(images.getShape(), axes);

		return new PositionIterator[] {diter, iter};
	}

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
		saveAxesAndAttributes(file, axes);
	}

	private static final String[] AXIS_NAME = new String[] {"h", "k", "l"};

	private static void saveAxesAndAttributes(String file, Dataset... axes) throws ScanFileHolderException {
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

	/**
	 * Map images from given Nexus file to a volume in Miller (aka HKL) space
	 * @param trees 
	 * @param allIters 
	 * @param parts 
	 * @param map 
	 * @param weight 
	 * @throws ScanFileHolderException
	 */
	private void mapAndSaveInParts(Tree[] trees, PositionIterator[][] allIters, LazyWriteableDataset output, int parts, DoubleDataset map, DoubleDataset weight) throws ScanFileHolderException {
		int n = trees.length;

		SliceND slice = new SliceND(hShape, null, map.getShapeRef(), null);
		int ml = map.getShapeRef()[0]; // length of first dimension
		int[] hstart = slice.getStart();
		int[] hstop = slice.getStop();
		double oMin = hMin[0];
		double oMax = hMax[0];

		Tree tree;
		PositionIterator[] iters;
		for (int p = 0; p < (parts-1); p++) {
			// shift min
			hMin[0] = oMin + hstart[0] * hDel[0];
			hMax[0] = hMin[0] + ml * hDel[0];

			for (int t = 0; t < n; t++) {
				tree = trees[t];
				iters = allIters[t];
				DataNode node = (DataNode) tree.findNodeLink(dataPath).getDestination();
				ILazyDataset images = node.getDataset();

				Dataset trans = NexusTreeUtils.parseAttenuator(attenuatorPath, tree);

				int rank = iters[1].getPos().length;
				int[] ishape = Arrays.copyOfRange(images.getShape(), rank - 2, rank);
				BicubicInterpolator upSampler = null;
				if (scale != 1) {
					for (int i = 0; i < ishape.length; i++) {
						ishape[i] *= scale;
					}
					upSampler = new BicubicInterpolator(ishape);
				}

				mapImages(tree, trans, images, iters, map, weight, ishape, upSampler);
			}
			Maths.dividez(map, weight, map); // normalize by tally

			try {
				output.setSlice(map, slice);
			} catch (Exception e) {
				System.err.println("Could not saving part of volume");
				throw new ScanFileHolderException("Could not saving part of volume", e);
			}
			map.fill(0);
			weight.fill(0);
			hstart[0] = hstop[0];
			hstop[0] = hstart[0] + ml;
		}

		// last part
		hMin[0] = oMin + hstart[0] * hDel[0];
		hMax[0] = oMax;
		int hl = hShape[0];
		boolean overflow = hstop[0] > hl;
		if (overflow) {
			hstop[0] = hl;
		}

		for (int t = 0; t < n; t++) {
			tree = trees[t];
			iters = allIters[t];
			DataNode node = (DataNode) tree.findNodeLink(dataPath).getDestination();
			ILazyDataset images = node.getDataset();

			Dataset trans = NexusTreeUtils.parseAttenuator(attenuatorPath, tree);

			int rank = iters[1].getPos().length;
			int[] ishape = Arrays.copyOfRange(images.getShape(), rank - 2, rank);
			BicubicInterpolator upSampler = null;
			if (scale != 1) {
				for (int i = 0; i < ishape.length; i++) {
					ishape[i] *= scale;
				}
				upSampler = new BicubicInterpolator(ishape);
			}

			mapImages(tree, trans, images, iters, map, weight, ishape, upSampler);
		}
		Maths.dividez(map, weight, map); // normalize by tally

		DoubleDataset tmap;
		if (overflow) {
			int[] tstop = map.getShape();
			tstop[0] = hstop[0] - hstart[0];
			tmap = (DoubleDataset) map.getSliceView(null, tstop, null);
			slice.getShape()[0] = tstop[0];
		} else {
			tmap = map;
		}
		try {
			output.setSlice(tmap, slice);
		} catch (Exception e) {
			System.err.println("Could not saving last part of volume");
			throw new ScanFileHolderException("Could not saving last part of volume", e);
		}
	}

	private static final MillerSpaceMapper I16Mapper = new MillerSpaceMapper("/entry1", "instrument", "attenuator", "pil100k", "image_data", "sample");

	/**
	 * Process Nexus file for I16
	 * @param input Nexus file
	 * @param output name of HDF5 file to be created
	 * @param splitter name of pixel splitting algorithm. Can be "gaussian", "inverse", or null, "", or "nearest" for the default.
	 * @param p splitter parameter
	 * @param scale upsampling factor
	 * @param mShape shape of output volume
	 * @param mStart start coordinates in Miller space
	 * @param mDelta sides of voxels in Miller space
	 * @throws ScanFileHolderException
	 */
	public static void processVolume(String input, String output, String splitter, double p, double scale, int[] mShape, double[] mStart, double... mDelta) throws ScanFileHolderException {
		setI16Splitter(splitter, p);
		I16Mapper.mapToVolumeFile(new String[] {input}, output, scale, false, mShape, mStart, mDelta);
	}

	/**
	 * Process Nexus file for I16 with automatic bounding box setting
	 * @param input Nexus file
	 * @param output name of HDF5 file to be created
	 * @param splitter name of pixel splitting algorithm. Can be "gaussian", "inverse", or null, "", or "nearest" for the default.
	 * @param p splitter parameter
	 * @param scale upsampling factor
	 * @param mDelta sides of voxels in Miller space
	 * @param reduceToNonZero if true, reduce output to sub-volume with non-zero data
	 * @throws ScanFileHolderException
	 */
	public static void processVolumeWithAutoBox(String input, String output, String splitter, double p, double scale, boolean reduceToNonZero, double... mDelta) throws ScanFileHolderException {
		setI16Splitter(splitter, p);
		I16Mapper.mapToVolumeFile(new String[] {input}, output, scale, reduceToNonZero, null, null, mDelta);
	}

	/**
	 * Process Nexus files for I16 with automatic bounding box setting
	 * @param inputs Nexus files
	 * @param output name of HDF5 file to be created
	 * @param splitter name of pixel splitting algorithm. Can be "gaussian", "inverse", or null, "", or "nearest" for the default.
	 * @param p splitter parameter
	 * @param scale upsampling factor
	 * @param mDelta sides of voxels in Miller space
	 * @param reduceToNonZero if true, reduce output to sub-volume with non-zero data
	 * @throws ScanFileHolderException
	 */
	public static void processVolumeWithAutoBox(String[] inputs, String output, String splitter, double p, double scale, boolean reduceToNonZero, double... mDelta) throws ScanFileHolderException {
		setI16Splitter(splitter, p);
		I16Mapper.mapToVolumeFile(inputs, output, scale, reduceToNonZero, null, null, mDelta);
	}

	static void setI16Splitter(String splitter, double p) {
		if (splitter == null || splitter.isEmpty()) {
			return;
		}
		if (splitter.equals("gaussian")) {
			I16Mapper.setSplitter(new GaussianSplitter(p));
		} else if (splitter.equals("negexp")) {
			I16Mapper.setSplitter(new ExponentialSplitter(p));
		} else if (splitter.equals("inverse")) {
			I16Mapper.setSplitter(new InverseSplitter());
		} else if (!splitter.equals("nearest")) {
			throw new IllegalArgumentException("Splitter is not known");
		}
	}
}

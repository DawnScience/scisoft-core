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

import javax.measure.unit.SI;
import javax.vecmath.Vector3d;

import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyWriteableDataset;
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
	private String timePath; // path to exposure dataset
	private String dataPath;
	private String samplePath;
	private String attenuatorPath;
	private MillerSpaceMapperBean bean;

	private double[] qDel; // sides of voxels in q space
	private double[] qMin; // minimum
	private double[] qMax; // maximum
	private int[] qShape;

	private double[] hDel; // sides of voxels in Miller space
	private double[] hMin; // minimum
	private double[] hMax; // maximum
	private int[] hShape;

	private boolean findImageBB; // find bounding box for image
	private boolean reduceToNonZeroBB; // reduce data non-zero only
	private int[] sMin; // shape min and max 
	private int[] sMax;
	private double scale; // image upsampling factor

	private PixelSplitter splitter;

	private double[] vDel;
	private double[] vMin;
	private double[] vMax;

	private boolean hasDeleted;
	private boolean listMillerEntries;

	private static final String VOLUME_NAME = "volume";
	private static final String MILLER_VOLUME_DATA_PATH = "/entry1/reciprocal_space";
	private static final String[] MILLER_VOLUME_AXES = new String[] {"h-axis", "k-axis", "l-axis"};
	private static final String Q_VOLUME_DATA_PATH = "/entry1/q_space";
	private static final String[] Q_VOLUME_AXES = new String[] {"x-axis", "y-axis", "z-axis"};

	private static final String INDICES_NAME = "hkli_list";
	private static final String MILLER_INDICES_DATA_PATH = "/entry1/indices";

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
	 * @param bean
	 */
	public MillerSpaceMapper(MillerSpaceMapperBean bean) {
		this.bean = bean.clone();
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
	 * Set q space bounding box parameters
	 * @param qShape shape of volume in Miller space
	 * @param qStart starting coordinates of volume
	 * @param qStop end coordinates
	 * @param qDelta lengths of voxel sides
	 */
	public void setQSpaceBoundingBox(int[] qShape, double[] qStart, double[] qStop, double[] qDelta) {
		this.qShape = qShape;
		qMin = qStart;
		qMax = qStop;
		qDel = qDelta;
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

	private int[] copyParameters(boolean mapQ) {
		if (mapQ) {
			vDel = qDel;
			vMin = qMin;
			vMax = qMax;
			return qShape;
		}
		vDel = hDel;
		vMin = hMin;
		vMax = hMax;
		return hShape;
	}

	/**
	 * Map images from given Nexus file to a volume in Miller (aka HKL) space
	 * @param filePath path to Nexus file
	 * @return dataset
	 * @throws ScanFileHolderException
	 */
	public Dataset mapToMillerSpace(String filePath) throws ScanFileHolderException {
		if (hDel == null) {
			throw new IllegalStateException("Miller space parameters have not been defined");
		}
		return mapToASpace(false, filePath);
	}

	/**
	 * Map images from given Nexus file to a volume in q space
	 * @param filePath path to Nexus file
	 * @return dataset
	 * @throws ScanFileHolderException
	 */
	public Dataset mapToQSpace(String filePath) throws ScanFileHolderException {
		if (qDel == null) {
			throw new IllegalStateException("q space parameters have not been defined");
		}
		return mapToASpace(true, filePath);
	}

	/**
	 * Map images from given Nexus file to a volume in q space
	 * @param mapQ
	 * @param filePath path to Nexus file
	 * @return dataset
	 * @throws ScanFileHolderException
	 */
	private Dataset mapToASpace(boolean mapQ, String filePath) throws ScanFileHolderException {
		int[] vShape = copyParameters(mapQ);
		NexusHDF5Loader l = new NexusHDF5Loader();
		l.setFile(filePath);
		Tree tree = l.loadFile().getTree();
		PositionIterator[] iters = getPositionIterators(tree);
	
		if (findImageBB) {
			Arrays.fill(vMin, Double.POSITIVE_INFINITY);
			Arrays.fill(vMax, Double.NEGATIVE_INFINITY);

			findBoundingBoxes(tree, iters);

			System.err.println("Extent of the space was found to be " + Arrays.toString(vMin) + " to " + Arrays.toString(vMax));
			System.err.println("with shape = " + Arrays.toString(vShape));
		}

		if (reduceToNonZeroBB) {
			sMin = vShape.clone();
			sMax = new int[3];
			Arrays.fill(sMax, -1);
		}

		DoubleDataset map = (DoubleDataset) DatasetFactory.zeros(vShape, Dataset.FLOAT64);
		DoubleDataset weight = (DoubleDataset) DatasetFactory.zeros(vShape, Dataset.FLOAT64);

		mapToASpace(mapQ, tree, iters, map, weight);

		Maths.dividez(map, weight, map); // normalize by tally
	
		if (reduceToNonZeroBB) {
			System.err.println("Reduced to non-zero bounding box: " + Arrays.toString(sMin) + " to " + Arrays.toString(sMax));
			for (int i = 0; i < 3; i++) {
				vMin[i] += sMin[i]*vDel[i];
				sMax[i]++;
				vShape[i] = sMax[i] - sMin[i];
			}
			System.err.println("so now start = " + Arrays.toString(qMin) + " for shape = " + Arrays.toString(vShape));
			map = (DoubleDataset) map.getSlice(sMin, sMax, null);
		}
		return map;
	}

	private void mapToASpace(boolean mapQ, Tree tree, PositionIterator[] iters, DoubleDataset map, DoubleDataset weight) throws ScanFileHolderException {
		int[] dshape = iters[0].getShape();
	
		Dataset trans = NexusTreeUtils.parseAttenuator(attenuatorPath, tree);
		if (trans != null && trans.getSize() != 1) {
			int[] tshape = trans.getShapeRef();
			if (!Arrays.equals(tshape, dshape)) {
				throw new ScanFileHolderException("Attenuator transmission shape does not match detector or sample scan shape");
			}
		}
	
		// factor in count time too
		Dataset time = NexusTreeUtils.getDataset(timePath, tree);
		if (time != null) {
			if (time.getSize() != 1) {
				int[] tshape = time.getShapeRef();
				if (!Arrays.equals(tshape, dshape)) {
					throw new ScanFileHolderException(
							"Exposure time shape does not match detector or sample scan shape");
				}
			}
			trans = trans == null ? time : Maths.multiply(trans, time);
		}
	
		DataNode node = (DataNode) tree.findNodeLink(dataPath).getDestination();
		ILazyDataset images = node.getDataset();
		if (images == null) {
			throw new ScanFileHolderException("Image data is empty");
		}
		int rank = images.getRank();
		int[] ishape = Arrays.copyOfRange(images.getShape(), rank - 2, rank);
	
		BicubicInterpolator upSampler = null;
		if (scale != 1) {
			for (int i = 0; i < ishape.length; i++) {
				ishape[i] *= scale;
			}
			upSampler = new BicubicInterpolator(ishape);
		}

		mapImages(mapQ, tree, trans, images, iters, map, weight, ishape, upSampler);
	}

	private void listToASpace(Tree tree, PositionIterator[] iters, ILazyWriteableDataset lazy) throws ScanFileHolderException {
		int[] dshape = iters[0].getShape();
		Dataset trans = NexusTreeUtils.parseAttenuator(attenuatorPath, tree);
		if (trans != null && trans.getSize() != 1) {
			int[] tshape = trans.getShapeRef();
			if (!Arrays.equals(tshape, dshape)) {
				throw new ScanFileHolderException("Attenuator transmission shape does not match detector or sample scan shape");
			}
		}
	
		// factor in count time too
		Dataset time = NexusTreeUtils.getDataset(timePath, tree);
		if (time != null) {
			if (time.getSize() != 1) {
				int[] tshape = time.getShapeRef();
				if (!Arrays.equals(tshape, dshape)) {
					throw new ScanFileHolderException(
							"Exposure time shape does not match detector or sample scan shape");
				}
			}
			trans = trans == null ? time : Maths.multiply(trans, time);
		}
	
		DataNode node = (DataNode) tree.findNodeLink(dataPath).getDestination();
		ILazyDataset images = node.getDataset();
		if (images == null) {
			throw new ScanFileHolderException("Image data is empty");
		}
		int rank = images.getRank();
		int[] ishape = Arrays.copyOfRange(images.getShape(), rank - 2, rank);

		BicubicInterpolator upSampler = null;
		if (scale != 1) {
			for (int i = 0; i < ishape.length; i++) {
				ishape[i] *= scale;
			}
			upSampler = new BicubicInterpolator(ishape);
		}

		doImages(tree, trans, images, iters, lazy, ishape, upSampler);
	}

	private void findBoundingBoxes(Tree tree, PositionIterator[] iters) {
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
			QSpace qspace = new QSpace(dp, env);
			if (qDel != null) {
				calcVolume(qMin, qMax, qspace, null);
			}

			if (hDel != null) {
				MillerSpace mspace = new MillerSpace(sample.getUnitCell(), env.getOrientation());
				calcVolume(hMin, hMax, qspace, mspace);
			}
		}

		if (qDel != null) {
			for (int i = 0; i < 3; i++) {
				qMin[i] = qDel[i] * Math.floor(qMin[i] / qDel[i]);
				qMax[i] = qDel[i] * (Math.ceil(qMax[i] / qDel[i]) + 1);
				qShape[i] = (int) (Math.floor((qMax[i] - qMin[i] + qDel[i]) / qDel[i]));
			}
		}

		if (hDel != null) {
			for (int i = 0; i < 3; i++) {
				hMin[i] = hDel[i] * Math.floor(hMin[i] / hDel[i]);
				hMax[i] = hDel[i] * (Math.ceil(hMax[i] / hDel[i]) + 1);
				hShape[i] = (int) (Math.floor((hMax[i] - hMin[i] + hDel[i]) / hDel[i]));
			}
		}
	}

	private void calcVolume(double[] vBeg, double[] vEnd, QSpace qspace, MillerSpace mspace) {
		Vector3d q = new Vector3d();
		Vector3d m = new Vector3d();
		DetectorProperties dp = qspace.getDetectorProperties();
		int x = dp.getPx();
		int y = dp.getPy();

		if (mspace == null) {
			qspace.qFromPixelPosition(0, 0, q);
			minMax(vBeg, vEnd, q);
		
			qspace.qFromPixelPosition(x/2, 0, q);
			minMax(vBeg, vEnd, q);
		
			qspace.qFromPixelPosition(x, 0, q);
			minMax(vBeg, vEnd, q);
		
			qspace.qFromPixelPosition(0, y/2, q);
			minMax(vBeg, vEnd, q);
		
			qspace.qFromPixelPosition(x/2, y/2, q);
			minMax(vBeg, vEnd, q);
		
			qspace.qFromPixelPosition(x, y/2, q);
			minMax(vBeg, vEnd, q);
		
			qspace.qFromPixelPosition(0, y, q);
			minMax(vBeg, vEnd, q);
		
			qspace.qFromPixelPosition(x/2, y, q);
			minMax(vBeg, vEnd, q);
		
			qspace.qFromPixelPosition(x, y, q);
			minMax(vBeg, vEnd, q);
		} else {
			qspace.qFromPixelPosition(0, 0, q);
			mspace.h(q, null, m);
			minMax(vBeg, vEnd, m);

			qspace.qFromPixelPosition(x / 2, 0, q);
			mspace.h(q, null, m);
			minMax(vBeg, vEnd, m);

			qspace.qFromPixelPosition(x, 0, q);
			mspace.h(q, null, m);
			minMax(vBeg, vEnd, m);

			qspace.qFromPixelPosition(0, y / 2, q);
			mspace.h(q, null, m);
			minMax(vBeg, vEnd, m);

			qspace.qFromPixelPosition(x / 2, y / 2, q);
			mspace.h(q, null, m);
			minMax(vBeg, vEnd, m);

			qspace.qFromPixelPosition(x, y / 2, q);
			mspace.h(q, null, m);
			minMax(vBeg, vEnd, m);

			qspace.qFromPixelPosition(0, y, q);
			mspace.h(q, null, m);
			minMax(vBeg, vEnd, m);

			qspace.qFromPixelPosition(x / 2, y, q);
			mspace.h(q, null, m);
			minMax(vBeg, vEnd, m);

			qspace.qFromPixelPosition(x, y, q);
			mspace.h(q, null, m);
			minMax(vBeg, vEnd, m);
		}
	}

	private void mapImages(boolean mapQ, Tree tree, Dataset trans, ILazyDataset images, PositionIterator[] iters,
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
		MillerSpace mspace = null;

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
			QSpace qspace = new QSpace(dp, env);
			if (!mapQ) {
				mspace = new MillerSpace(sample.getUnitCell(), env.getOrientation());
			}
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
		int[] pos = new int[3]; // voxel position
		Vector3d q = new Vector3d();
		double value;

		if (mspace == null) {
			Vector3d dq = new Vector3d(); // delta in q

			for (int y = 0; y < s[0]; y++) {
				for (int x = 0; x < s[1]; x++) {
					value = image.getDouble(y, x);
					if (value > 0) {
						qspace.qFromPixelPosition(x + 0.5, y + 0.5, q);
	
						if (convertToVoxel(q, dq, pos)) {
							value /= qspace.calculateSolidAngle(x, y);
							if (reduceToNonZeroBB) {
								minMax(sMin, sMax, pos);
							}
							splitter.splitValue(map, weight, vDel, dq, pos, value);
						}
					}
				}
			}
		} else {
			Vector3d h = new Vector3d();
			Vector3d dh = new Vector3d(); // delta in h

			for (int y = 0; y < s[0]; y++) {
				for (int x = 0; x < s[1]; x++) {
					value = image.getDouble(y, x);
					if (value > 0) {
						qspace.qFromPixelPosition(x + 0.5, y + 0.5, q);
	
						mspace.h(q, null, h);
						if (convertToVoxel(h, dh, pos)) {
							value /= qspace.calculateSolidAngle(x, y);
							if (reduceToNonZeroBB) {
								minMax(sMin, sMax, pos);
							}
							splitter.splitValue(map, weight, vDel, dh, pos, value);
						}
					}
				}
			}
		}
	}

	private static void minMax(final int[] min, final int[] max, final int[] p) {
		min[0] = Math.min(min[0], p[0]);
		max[0] = Math.max(max[0], p[0]);
		min[1] = Math.min(min[1], p[1]);
		max[1] = Math.max(max[1], p[1]);
		min[2] = Math.min(min[2], p[2]);
		max[2] = Math.max(max[2], p[2]);
	}

	/**
	 * Map from v to volume coords
	 * @param v vector
	 * @param deltaV delta in discrete space
	 * @param pos volume coords
	 * @return true if within bounds
	 */
	private boolean convertToVoxel(final Vector3d v, final Vector3d deltaV, final int[] pos) {
		if (!findImageBB) {
			if (v.x < vMin[0] || v.x >= vMax[0] || v.y < vMin[1] || v.y >= vMax[1] || 
					v.z < vMin[2] || v.z >= vMax[2]) {
				return false;
			}
		}

		int p;
		double dv, vd;

		dv = v.x - vMin[0];
		vd = vDel[0];
		p = (int) Math.floor(dv / vd);
		deltaV.x = dv - p * vd;
		pos[0] = p;

		dv = v.y - vMin[1];
		vd = vDel[1];
		p = (int) Math.floor(dv / vd);
		deltaV.y = dv - p * vd;
		pos[1] = p;

		dv = v.z - vMin[2];
		vd = vDel[2];
		p = (int) Math.floor(dv / vd);
		deltaV.z = dv - p * vd;
		pos[2] = p;

		return true;
	}

	private static void addToDataset(DoubleDataset d, final int[] pos, double v) {
		final int index = d.get1DIndex(pos);
		d.setAbs(index, d.getAbs(index) + v);
	}

	private void doImages(Tree tree, Dataset trans, ILazyDataset images, PositionIterator[] iters,
			ILazyWriteableDataset lazy, int[] ishape, BicubicInterpolator upSampler) throws ScanFileHolderException {
		PositionIterator diter = iters[0];
		PositionIterator iter = iters[1];
		iter.reset();
		diter.reset();
		int[] dpos = diter.getPos();
		int[] pos = iter.getPos();
		int[] stop = iter.getStop().clone();
		int rank  = pos.length;
		int srank = rank - 2;
		MillerSpace mspace = null;
		DoubleDataset list = null;

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
			QSpace qspace = new QSpace(dp, env);
			mspace = new MillerSpace(sample.getUnitCell(), env.getOrientation());
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

			// estimate size of flattened list from 
			if (list == null || list.getSize() != 4*image.getSize()) {
				list = (DoubleDataset) DatasetFactory.zeros(new int[] {image.getSize(), 4}, Dataset.FLOAT64);
			}
			int length = doImage(s, qspace, mspace, image, list);
			appendDataset(lazy, list, length);
		}
	}

	private void appendDataset(ILazyWriteableDataset lazy, Dataset data, int length) throws ScanFileHolderException {
		Dataset sdata = data.getSliceView(null, new int[] {length, 4}, null);
		int[] shape = lazy.getShape();
		int[] start = shape.clone();
		int[] stop = shape.clone();
		start[1] = 0;
		stop[0] = shape[0] + length;
		try {
			lazy.setSlice(null, sdata, start, stop, null);
		} catch (Exception e) {
			throw new ScanFileHolderException("Could not write list", e);
		}
	}

	private int doImage(int[] s, QSpace qspace, MillerSpace mspace, Dataset image, DoubleDataset list) {
		Vector3d q = new Vector3d();
		double value;
		Vector3d h = new Vector3d();

		int index = 0;
		for (int y = 0; y < s[0]; y++) {
			for (int x = 0; x < s[1]; x++) {
				value = image.getDouble(y, x);
				if (value > 0) {
					value /= qspace.calculateSolidAngle(x, y);
					qspace.qFromPixelPosition(x + 0.5, y + 0.5, q);
					mspace.h(q, null, h);
					list.setAbs(index++, h.x);
					list.setAbs(index++, h.y);
					list.setAbs(index++, h.z);
					list.setAbs(index++, value);
				}
			}
		}
		return index / 4;
	}

	private Dataset[][] processBean() {
		String instrumentPath = bean.getEntryPath() + Node.SEPARATOR + bean.getInstrumentName() + Node.SEPARATOR;
		detectorPath = instrumentPath + bean.getDetectorName();
		timePath = detectorPath + Node.SEPARATOR + "count_time";
		attenuatorPath = instrumentPath + bean.getAttenuatorName();
		dataPath = detectorPath + Node.SEPARATOR + bean.getDataName();
		samplePath = bean.getEntryPath() + Node.SEPARATOR + bean.getSampleName();
		this.splitter = createSplitter(bean.getSplitterName(), bean.getSplitterParameter());
		listMillerEntries = bean.isListMillerEntries();

		Dataset[][] a = new Dataset[2][];
		double[] qDelta = bean.getQStep();
		a[0] = new Dataset[3];
		if (qDelta != null && qDelta.length > 0) {
			double[] qStop = new double[3];
			if (qDelta.length == 1) {
				double d = qDelta[0];
				qDelta = new double[] {d, d, d};
			} else if (qDelta.length == 2) {
				double d = qDelta[1];
				qDelta = new double[] {qDelta[0], d, d};
			}
			int[] qShape = bean.getQShape();
			double[] qStart = bean.getQStart();
			if (qShape == null || qStart == null) {
				findImageBB = true;
				qShape = new int[3];
				qStart = new double[3];
			} else {
				createQSpaceAxes(a[0], qShape, qStart, qStop, qDelta);
			}
			setQSpaceBoundingBox(qShape, qStart, qStop, qDelta);
		}

		a[1] = new Dataset[3];
		double[] mDelta = bean.getMillerStep();
		if (mDelta != null && mDelta.length > 0) {
			double[] mStop = new double[3];
			if (mDelta.length == 1) {
				double d = mDelta[0];
				mDelta = new double[] { d, d, d };
			} else if (mDelta.length == 2) {
				double d = mDelta[1];
				mDelta = new double[] { mDelta[0], d, d };
			}
			int[] mShape = bean.getMillerShape();
			double[] mStart = bean.getMillerStart();
			if (mShape == null || mStart == null) {
				findImageBB = true;
				mShape = new int[3];
				mStart = new double[3];
			} else {
				createMillerSpaceAxes(a[1], mShape, mStart, mStop, mDelta);
			}
			setMillerSpaceBoundingBox(mShape, mStart, mStop, mDelta);
		}

		setReduceToNonZeroData(bean.isReduceToNonZero());
		setUpsamplingScale(bean.getScaleFactor());

		// TODO compensate for count_time and other optional stuff (ring current in NXinstrument / NXsource)
		// mask images
		return a;
	}

	/**
	 * Map images from given Nexus files to a volume in Miller (aka HKL) space and save to a HDF5 file
	 * @throws ScanFileHolderException
	 */
	public void mapToVolumeFile() throws ScanFileHolderException {
		hasDeleted = false; // reset state

		Dataset[][] a = processBean();
		if (qDel == null && hDel == null && !listMillerEntries) {
			throw new IllegalStateException("Both q space and Miller space parameters have not been defined");
		}
		String[] inputs = bean.getInputs();
		String output = bean.getOutput();

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
			if (qDel != null) {
				Arrays.fill(qMin, Double.POSITIVE_INFINITY);
				Arrays.fill(qMax, Double.NEGATIVE_INFINITY);
			}
			if (hDel != null) {
				Arrays.fill(hMin, Double.POSITIVE_INFINITY);
				Arrays.fill(hMax, Double.NEGATIVE_INFINITY);
			}

			for (int i = 0; i < n; i++) {
				findBoundingBoxes(trees[i], allIters[i]);
			}

			if (qDel != null) {
				System.err.println("Extent of q space was found to be " + Arrays.toString(qMin) + " to " + Arrays.toString(qMax));
				System.err.println("with shape = " + Arrays.toString(qShape));
			}
			if (hDel != null) {
				System.err.println("Extent of Miller space was found to be " + Arrays.toString(hMin) + " to " + Arrays.toString(hMax));
				System.err.println("with shape = " + Arrays.toString(hShape));
			}
		}

		if (qDel != null) {
			processTrees(true, trees, allIters, output, a[0]);
		}

		if (hDel != null) {
			processTrees(false, trees, allIters, output, a[1]);
		}

		if (listMillerEntries) {
			processTreesForList(trees, allIters, output);
		}
	}

	private void processTrees(boolean mapQ, Tree[] trees, PositionIterator[][] allIters, String output, Dataset[] a) throws ScanFileHolderException {
		int[] vShape = copyParameters(mapQ);

		if (reduceToNonZeroBB) {
			sMin = vShape.clone();
			sMax = new int[3];
			Arrays.fill(sMax, -1);
		}
		String volPath = mapQ ? Q_VOLUME_DATA_PATH : MILLER_VOLUME_DATA_PATH;

		try {
			DoubleDataset map = (DoubleDataset) DatasetFactory.zeros(vShape, Dataset.FLOAT64);
			DoubleDataset weight = (DoubleDataset) DatasetFactory.zeros(vShape, Dataset.FLOAT64);

			for (int i = 0; i < trees.length; i++) {
				Tree tree = trees[i];
				mapToASpace(mapQ, tree, allIters[i], map, weight);
			}
			Maths.dividez(map, weight, map); // normalize by tally

			if (reduceToNonZeroBB) {
				System.err.println("Reduced to non-zero bounding box: " + Arrays.toString(sMin) + " to " + Arrays.toString(sMax));
				for (int i = 0; i < 3; i++) {
					vMin[i] += vMin[i]*vDel[i];
					sMax[i]++;
					vShape[i] = sMax[i] - sMin[i];
				}
				System.err.println("so now start = " + Arrays.toString(vMin) + " for shape = " + Arrays.toString(vShape));
				map = (DoubleDataset) map.getSlice(sMin, sMax, null);
			}

			if (findImageBB) {
				if (mapQ) {
					createQSpaceAxes(a, vShape, vMin, null, vDel);
				} else {
					createMillerSpaceAxes(a, vShape, vMin, null, vDel);
				}
			}
			if (!hasDeleted) {
				HDF5FileFactory.deleteFile(output);
				hasDeleted = true;
			}
			saveVolume(output, volPath, map, a);
		} catch (IllegalArgumentException | OutOfMemoryError e) {
			System.err.println("There is not enough memory to do this all at once!");
			System.err.println("Now attempting to segment volume");
			if (findImageBB) {
				createMillerSpaceAxes(a, vShape, vMin, null, vDel);
			}

			// unset these as code does not or should not handle them
			findImageBB = false;
			reduceToNonZeroBB = false;

			int parts = 1;
			DoubleDataset map = null;
			DoubleDataset weight = null;
			// find biggest size that fits
			int[] tShape = vShape.clone();
			while (true) {
				parts++;
				tShape[0] = (vShape[0] + parts - 1)/parts;
				if (tShape[0] == 1) { // maybe use other dimensions too(!)
					break;
				}
				try {
					map = (DoubleDataset) DatasetFactory.zeros(tShape, Dataset.FLOAT64);
					weight = (DoubleDataset) DatasetFactory.zeros(tShape, Dataset.FLOAT64);
					break;
				} catch (IllegalArgumentException | OutOfMemoryError ex) {
					map = null;
				}
			}

			if (map == null || weight == null) {
				System.err.println("Cannot segment volume fine enough to fit in memory!");
				throw e;
			}
			System.out.println("Mapping in " + parts + " parts");
			if (!hasDeleted) {
				HDF5FileFactory.deleteFile(output);
				hasDeleted = true;
			}

			int[] cShape = vShape.clone();
			cShape[0] = 1;
			LazyWriteableDataset lazy = HDF5Utils.createLazyDataset(output, volPath, VOLUME_NAME, vShape, null, cShape, Dataset.FLOAT64, null, false);
			mapAndSaveInParts(mapQ, trees, allIters, lazy, parts, map, weight);

			saveAxesAndAttributes(output, volPath, a);
		}
	}

	private static void createQSpaceAxes(Dataset[] a, int[] mShape, double[] mStart, double[] mStop, double[] mDelta) {
		createAxes(Q_VOLUME_AXES, a, mShape, mStart, mStop, mDelta);
	}

	private static void createMillerSpaceAxes(Dataset[] a, int[] mShape, double[] mStart, double[] mStop, double[] mDelta) {
		createAxes(MILLER_VOLUME_AXES, a, mShape, mStart, mStop, mDelta);
	}

	private static void createAxes(String[] names, Dataset[] a, int[] mShape, double[] mStart, double[] mStop, double[] mDelta) {
		for (int i = 0; i < names.length; i++) {
			double mbeg = mStart[i];
			double mend = mbeg + mShape[i] * mDelta[i];
			if (mStop != null) {
				mStop[i] = mend;
			}
			a[i] = DatasetFactory.createLinearSpace(mbeg, mend - mDelta[i], mShape[i], Dataset.FLOAT64);
			a[i].setName(names[i]);
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
		if (images == null) {
			throw new ScanFileHolderException("Image data is empty");
		}

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
	 * @param volPath path to NXdata
	 * @param v volume dataset
	 * @param axes axes datasets
	 * @throws ScanFileHolderException
	 */
	private static void saveVolume(String file, String volPath, Dataset v, Dataset... axes) throws ScanFileHolderException {
		v.setName(VOLUME_NAME);
		HDF5Utils.writeDataset(file, volPath, v);
		saveAxesAndAttributes(file, volPath, axes);
	}

	private static void saveAxesAndAttributes(String file, String volPath, Dataset... axes) throws ScanFileHolderException {
		String[] axisNames = new String[axes.length];
		for (int i = 0; i < axes.length; i++) {
			Dataset x = axes[i];
			axisNames[i] = x.getName();
			HDF5Utils.writeDataset(file, volPath, x);
		}

		List<Dataset> attrs = new ArrayList<>();
		Dataset a;

		a = DatasetFactory.createFromObject("NXdata");
		a.setName("NX_class");
		attrs.add(a);

		a = DatasetFactory.createFromObject(VOLUME_NAME);
		a.setName("signal");
		attrs.add(a);

		a = DatasetFactory.createFromObject(axisNames);
		a.setName("axes");
		attrs.add(a);

		for (int i = 0; i < axisNames.length; i++) {
			a = DatasetFactory.createFromObject(i);
			a.setName(axisNames[i] + "_indices");
			attrs.add(a);
		}

		HDF5Utils.writeAttributes(file, volPath, attrs.toArray(new Dataset[attrs.size()]));
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
	private void mapAndSaveInParts(boolean mapQ, Tree[] trees, PositionIterator[][] allIters, LazyWriteableDataset output, int parts, DoubleDataset map, DoubleDataset weight) throws ScanFileHolderException {
		int n = trees.length;

		SliceND slice = new SliceND(hShape, null, map.getShapeRef(), null);
		int ml = map.getShapeRef()[0]; // length of first dimension
		int[] vstart = slice.getStart();
		int[] vstop = slice.getStop();
		double oMin = vMin[0];
		double oMax = vMax[0];

		Tree tree;
		PositionIterator[] iters;
		for (int p = 0; p < (parts-1); p++) {
			// shift min
			vMin[0] = oMin + vstart[0] * vDel[0];
			vMax[0] = vMin[0] + ml * vDel[0];

			for (int t = 0; t < n; t++) {
				tree = trees[t];
				iters = allIters[t];
				DataNode node = (DataNode) tree.findNodeLink(dataPath).getDestination();
				ILazyDataset images = node.getDataset();
				if (images == null) {
					throw new ScanFileHolderException("Image data is empty");
				}

				Dataset trans = NexusTreeUtils.parseAttenuator(attenuatorPath, tree);
				// factor in count time too
				Dataset time = NexusTreeUtils.getDataset(timePath, tree, SI.SECOND);
				if (time != null) {
					if (time.getSize() != 1) {
						int[] dshape = iters[0].getShape();
						int[] tshape = time.getShapeRef();
						if (!Arrays.equals(tshape, dshape)) {
							throw new ScanFileHolderException(
									"Exposure time shape does not match detector or sample scan shape");
						}
					}
					trans = trans == null ? time : Maths.multiply(trans, time);
				}

				int rank = iters[1].getPos().length;
				int[] ishape = Arrays.copyOfRange(images.getShape(), rank - 2, rank);
				BicubicInterpolator upSampler = null;
				if (scale != 1) {
					for (int i = 0; i < ishape.length; i++) {
						ishape[i] *= scale;
					}
					upSampler = new BicubicInterpolator(ishape);
				}

				mapImages(mapQ, tree, trans, images, iters, map, weight, ishape, upSampler);
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
			vstart[0] = vstop[0];
			vstop[0] = vstart[0] + ml;
		}

		// last part
		vMin[0] = oMin + vstart[0] * vDel[0];
		vMax[0] = oMax;
		int vl = hShape[0];
		boolean overflow = vstop[0] > vl;
		if (overflow) {
			vstop[0] = vl;
		}

		for (int t = 0; t < n; t++) {
			tree = trees[t];
			iters = allIters[t];
			DataNode node = (DataNode) tree.findNodeLink(dataPath).getDestination();
			ILazyDataset images = node.getDataset();

			Dataset trans = NexusTreeUtils.parseAttenuator(attenuatorPath, tree);
			// factor in count time too
			Dataset time = NexusTreeUtils.getDataset(timePath, tree);
			if (time != null) {
				if (time.getSize() != 1) {
					int[] dshape = iters[0].getShape();
					int[] tshape = time.getShapeRef();
					if (!Arrays.equals(tshape, dshape)) {
						throw new ScanFileHolderException(
								"Exposure time shape does not match detector or sample scan shape");
					}
				}
				trans = trans == null ? time : Maths.multiply(trans, time);
			}

			int rank = iters[1].getPos().length;
			int[] ishape = Arrays.copyOfRange(images.getShape(), rank - 2, rank);
			BicubicInterpolator upSampler = null;
			if (scale != 1) {
				for (int i = 0; i < ishape.length; i++) {
					ishape[i] *= scale;
				}
				upSampler = new BicubicInterpolator(ishape);
			}

			mapImages(mapQ, tree, trans, images, iters, map, weight, ishape, upSampler);
		}
		Maths.dividez(map, weight, map); // normalize by tally

		DoubleDataset tmap;
		if (overflow) {
			int[] tstop = map.getShape();
			tstop[0] = vstop[0] - vstart[0];
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

	private void processTreesForList(Tree[] trees, PositionIterator[][] allIters, String output) throws ScanFileHolderException {
		if (!hasDeleted) {
			HDF5FileFactory.deleteFile(output);
			hasDeleted = true;
		}

		// Each pixel => HKL (3 doubles) plus corrected intensity (1 double)
		LazyWriteableDataset lazy = HDF5Utils.createLazyDataset(output, MILLER_INDICES_DATA_PATH, INDICES_NAME, new int[] {0,4}, new int[] {-1,4}, new int[] {1024, 4}, Dataset.FLOAT64, null, false);
		for (int i = 0; i < trees.length; i++) {
			Tree tree = trees[i];
			listToASpace(tree, allIters[i], lazy);
		}
	}

	/**
	 * Process Nexus file for I16
	 * @param input Nexus file
	 * @param output name of HDF5 file to be created
	 * @param splitter name of pixel splitting algorithm. Can be "gaussian", "inverse", or null, "", or "nearest" for the default.
	 * @param p splitter parameter
	 * @param scale upsampling factor
	 * @param mShape shape of Miller space volume
	 * @param mStart start coordinates in Miller space
	 * @param mDelta sides of voxels in Miller space
	 * @throws ScanFileHolderException
	 */
	public static void processVolume(String input, String output, String splitter, double p, double scale, int[] mShape, double[] mStart, double... mDelta) throws ScanFileHolderException {
		setBean(I16MapperBean, input, output, splitter, p, scale, mShape, mStart, mDelta, null, null, null);
		MillerSpaceMapper mapper = new MillerSpaceMapper(I16MapperBean);
		mapper.mapToVolumeFile();
	}

	/**
	 * Process Nexus file for I16
	 * @param input Nexus file
	 * @param output name of HDF5 file to be created
	 * @param splitter name of pixel splitting algorithm. Can be "gaussian", "inverse", or null, "", or "nearest" for the default.
	 * @param p splitter parameter
	 * @param scale upsampling factor
	 * @param mShape shape of Miller space volume
	 * @param mStart start coordinates in Miller space
	 * @param mDelta sides of voxels in Miller space
	 * @param qShape shape of q space volume
	 * @param qStart start coordinates in q space
	 * @param qDelta sides of voxels in q space
	 * @throws ScanFileHolderException
	 */
	public static void processBothVolumes(String input, String output, String splitter, double p, double scale, int[] mShape, double[] mStart, double[] mDelta, int[] qShape, double[] qStart, double[] qDelta) throws ScanFileHolderException {
		setBean(I16MapperBean, input, output, splitter, p, scale, mShape, mStart, mDelta, qShape, qStart, qDelta);
		MillerSpaceMapper mapper = new MillerSpaceMapper(I16MapperBean);
		mapper.mapToVolumeFile();
	}

	/**
	 * Process Nexus file for I16 with automatic bounding box setting
	 * @param input Nexus file
	 * @param output name of HDF5 file to be created
	 * @param splitter name of pixel splitting algorithm. Can be "gaussian", "inverse", or null, "", or "nearest" for the default.
	 * @param p splitter parameter
	 * @param scale upsampling factor
	 * @param reduceToNonZero if true, reduce output to sub-volume with non-zero data
	 * @param mDelta sides of voxels in Miller space
	 * @throws ScanFileHolderException
	 */
	public static void processVolumeWithAutoBox(String input, String output, String splitter, double p, double scale, boolean reduceToNonZero, double... mDelta) throws ScanFileHolderException {
		processVolumeWithAutoBox(new String[] {input}, output, splitter, p, scale, reduceToNonZero, mDelta);
	}

	/**
	 * Process Nexus files for I16 with automatic bounding box setting
	 * @param inputs Nexus files
	 * @param output name of HDF5 file to be created
	 * @param splitter name of pixel splitting algorithm. Can be "gaussian", "inverse", or null, "", or "nearest" for the default.
	 * @param p splitter parameter
	 * @param scale upsampling factor
	 * @param reduceToNonZero if true, reduce output to sub-volume with non-zero data
	 * @param mDelta sides of voxels in Miller space
	 * @throws ScanFileHolderException
	 */
	public static void processVolumeWithAutoBox(String[] inputs, String output, String splitter, double p, double scale, boolean reduceToNonZero, double... mDelta) throws ScanFileHolderException {
		setBeanWithAutoBox(I16MapperBean, inputs, output, splitter, p, scale, reduceToNonZero, mDelta, null);
		MillerSpaceMapper mapper = new MillerSpaceMapper(I16MapperBean);
		mapper.mapToVolumeFile();
	}

	/**
	 * Process Nexus files for I16 with automatic bounding box setting
	 * @param inputs Nexus files
	 * @param output name of HDF5 file to be created
	 * @param splitter name of pixel splitting algorithm. Can be "gaussian", "inverse", or null, "", or "nearest" for the default.
	 * @param p splitter parameter
	 * @param scale upsampling factor
	 * @param reduceToNonZero if true, reduce output to sub-volume with non-zero data
	 * @param qDelta sides of voxels in q space
	 * @throws ScanFileHolderException
	 */
	public static void processQVolumeWithAutoBox(String[] inputs, String output, String splitter, double p, double scale, boolean reduceToNonZero, double... qDelta) throws ScanFileHolderException {
		setBeanWithAutoBox(I16MapperBean, inputs, output, splitter, p, scale, reduceToNonZero, null, qDelta);
		MillerSpaceMapper mapper = new MillerSpaceMapper(I16MapperBean);
		mapper.mapToVolumeFile();
	}

	/**
	 * Process Nexus files for I16 with automatic bounding box setting
	 * @param inputs Nexus files
	 * @param output name of HDF5 file to be created
	 * @param splitter name of pixel splitting algorithm. Can be "gaussian", "inverse", or null, "", or "nearest" for the default.
	 * @param p splitter parameter
	 * @param scale upsampling factor
	 * @param reduceToNonZero if true, reduce output to sub-volume with non-zero data
	 * @param mDelta sides of voxels in Miller space
	 * @param qDelta sides of voxels in q space
	 * @throws ScanFileHolderException
	 */
	public static void processBothVolumesWithAutoBox(String[] inputs, String output, String splitter, double p, double scale, boolean reduceToNonZero, double[] mDelta, double[] qDelta) throws ScanFileHolderException {
		setBeanWithAutoBox(I16MapperBean, inputs, output, splitter, p, scale, reduceToNonZero, mDelta, qDelta);
		MillerSpaceMapper mapper = new MillerSpaceMapper(I16MapperBean);
		mapper.mapToVolumeFile();
	}

	/**
	 * Process Nexus files for I16 to list of hkl and i
	 * @param inputs Nexus files
	 * @param output name of HDF5 file to be created
	 * @param scale upsampling factor
	 * @throws ScanFileHolderException
	 */
	public static void processList(String[] inputs, String output, double scale) throws ScanFileHolderException {
		setBeanWithList(I16MapperBean, inputs, output, scale);
		MillerSpaceMapper mapper = new MillerSpaceMapper(I16MapperBean);
		mapper.mapToVolumeFile();
	}

	static PixelSplitter createSplitter(String splitter, double p) {
		if (splitter == null || splitter.isEmpty() || splitter.equals("nearest")) {
			return new NonSplitter();
		} else if (splitter.equals("gaussian")) {
			return new GaussianSplitter(p);
		} else if (splitter.equals("negexp")) {
			return new ExponentialSplitter(p);
		} else if (splitter.equals("inverse")) {
			return new InverseSplitter();
		} 

		throw new IllegalArgumentException("Splitter is not known");
	}

	private static final MillerSpaceMapperBean I16MapperBean;
	static {
		I16MapperBean = createI16MapperBean();
	}

	/**
	 * Create a bean with Nexus configuration that is specific to I16
	 * @return bean
	 */
	public static MillerSpaceMapperBean createI16MapperBean() {
		MillerSpaceMapperBean bean = new MillerSpaceMapperBean();
		bean.setEntryPath("/entry1");
		bean.setInstrumentName("instrument");
		bean.setAttenuatorName("attenuator");
		bean.setDetectorName("pil100k");
		bean.setDataName("image_data");
		bean.setSampleName("sample");
		return bean;
	}

	/**
	 * Process Nexus files for I16 with automatic bounding box setting
	 * @param inputs Nexus files
	 * @param output name of HDF5 file to be created
	 * @param scale upsampling factor
	 */
	public static void setBeanWithList(MillerSpaceMapperBean bean, String[] inputs, String output, double scale) {
		bean.setInputs(inputs);
		bean.setOutput(output);
		bean.setSplitterName(null);
		bean.setSplitterParameter(0);
		bean.setScaleFactor(scale);
		bean.setReduceToNonZero(false);
		bean.setMillerShape(null);
		bean.setMillerStart(null);
		bean.setMillerStep(null);
		bean.setQShape(null);
		bean.setQStart(null);
		bean.setQStep(null);
		bean.setListMillerEntries(true);
	}

	/**
	 * Process Nexus files for I16 with automatic bounding box setting
	 * @param inputs Nexus files
	 * @param output name of HDF5 file to be created
	 * @param splitter name of pixel splitting algorithm. Can be "gaussian", "inverse", or null, "", or "nearest" for the default.
	 * @param p splitter parameter
	 * @param scale upsampling factor
	 * @param reduceToNonZero if true, reduce output to sub-volume with non-zero data
	 * @param mDelta sides of voxels in Miller space
	 * @param qDelta sides of voxels in q space
	 */
	public static void setBeanWithAutoBox(MillerSpaceMapperBean bean, String[] inputs, String output, String splitter, double p, double scale, boolean reduceToNonZero, double[] mDelta, double[] qDelta) {
		bean.setInputs(inputs);
		bean.setOutput(output);
		bean.setSplitterName(splitter);
		bean.setSplitterParameter(p);
		bean.setScaleFactor(scale);
		bean.setReduceToNonZero(reduceToNonZero);
		bean.setMillerShape(null);
		bean.setMillerStart(null);
		bean.setMillerStep(mDelta);
		bean.setQShape(null);
		bean.setQStart(null);
		bean.setQStep(qDelta);
		bean.setListMillerEntries(true);
	}

	/**
	 * Process Nexus file for I16
	 * @param input Nexus file
	 * @param output name of HDF5 file to be created
	 * @param splitter name of pixel splitting algorithm. Can be "gaussian", "inverse", or null, "", or "nearest" for the default.
	 * @param p splitter parameter
	 * @param scale upsampling factor
	 * @param mShape shape of Miller space volume
	 * @param mStart start coordinates in Miller space
	 * @param mDelta sides of voxels in Miller space
	 * @param qShape shape of q space volume
	 * @param qStart start coordinates in q space
	 * @param qDelta sides of voxels in q space
	 */
	public static void setBean(MillerSpaceMapperBean bean, String input, String output, String splitter, double p, double scale, int[] mShape, double[] mStart, double[] mDelta, int[] qShape, double[] qStart, double[] qDelta) {
		bean.setInputs(input);
		bean.setOutput(output);
		bean.setSplitterName(splitter);
		bean.setSplitterParameter(p);
		bean.setScaleFactor(scale);
		bean.setReduceToNonZero(false);
		bean.setMillerShape(mShape);
		bean.setMillerStart(mStart);
		bean.setMillerStep(mDelta);
		bean.setQShape(qShape);
		bean.setQStart(qStart);
		bean.setQStep(qDelta);
		bean.setListMillerEntries(false);
	}

	/**
	 * This represents all of the input parameters and options for the mapper
	 */
	public static class MillerSpaceMapperBean implements Cloneable {
		private String[] inputs;
		private String output;
		private String splitterName;
		private double splitterParameter;
		private double scaleFactor;

		private int[] millerShape;
		private double[] millerStart;
		private double[] millerStep;

		private boolean reduceToNonZero;

		private String entryPath;
		private String instrumentName;
		private String attenuatorName;
		private String detectorName;
		private String dataName;
		private String sampleName;
		private String[] otherPaths;

		private boolean listMillerEntries;

		private int[] qShape;
		private double[] qStart;
		private double[] qStep;

		public MillerSpaceMapperBean() {
		}

		/**
		 * @return inputs Nexus files
		 */
		public String[] getInputs() {
			return inputs;
		}

		/**
		 * @param inputs Nexus files
		 */
		public void setInputs(String... inputs) {
			this.inputs = inputs;
		}

		/**
		 * @return output name of HDF5 file to be created
		 */
		public String getOutput() {
			return output;
		}

		/**
		 * @param output name of HDF5 file to be created
		 */
		public void setOutput(String output) {
			this.output = output;
		}

		/**
		 * @return name of pixel splitting algorithm
		 */
		public String getSplitterName() {
			return splitterName;
		}

		/**
		 * @param splitterName name of pixel splitting algorithm. Can be "gaussian", "inverse", or null, "", or "nearest" for the default.
		 */
		public void setSplitterName(String splitterName) {
			this.splitterName = splitterName;
		}

		/**
		 * @return value of pixel splitting parameter
		 */
		public double getSplitterParameter() {
			return splitterParameter;
		}

		/**
		 * @param splitterParameter splitter parameter (usually a scale length)
		 */
		public void setSplitterParameter(double splitterParameter) {
			this.splitterParameter = splitterParameter;
		}

		/**
		 * @return scale upsampling factor
		 */
		public double getScaleFactor() {
			return scaleFactor;
		}

		/**
		 * @param scaleFactor upsampling factor
		 */
		public void setScaleFactor(double scaleFactor) {
			this.scaleFactor = scaleFactor;
		}

		/**
		 * @return shape of volume in Miller space (can be null to be autoset)
		 */
		public int[] getMillerShape() {
			return millerShape;
		}

		/**
		 * @param millerShape shape of volume in Miller space (can be null to be autoset)
		 */
		public void setMillerShape(int[] millerShape) {
			this.millerShape = millerShape;
		}

		/**
		 * @return starting coordinates of volume (can be null to be autoset)
		 */
		public double[] getMillerStart() {
			return millerStart;
		}

		/**
		 * @param millerStart starting coordinates of volume (can be null to be autoset)
		 */
		public void setMillerStart(double[] millerStart) {
			this.millerStart = millerStart;
		}

		/**
		 * @return sides of voxels in Miller space
		 */
		public double[] getMillerStep() {
			return millerStep;
		}

		/**
		 * @param millerStep sides of voxels in Miller space
		 */
		public void setMillerStep(double... millerStep) {
			this.millerStep = millerStep;
		}

		/**
		 * @return true if mapper attempts to reduce output to sub-volume with non-zero data
		 */
		public boolean isReduceToNonZero() {
			return reduceToNonZero;
		}

		/**
		 * @param reduceToNonZero if true, attempts to reduce output to sub-volume with non-zero data
		 */
		public void setReduceToNonZero(boolean reduceToNonZero) {
			this.reduceToNonZero = reduceToNonZero;
		}

		public String getEntryPath() {
			return entryPath;
		}

		/**
		 * @param entryPath
		 */
		public void setEntryPath(String entryPath) {
			this.entryPath = entryPath;
		}

		public String getInstrumentName() {
			return instrumentName;
		}

		/**
		 * @param instrumentName name of instrument in entry
		 */
		public void setInstrumentName(String instrumentName) {
			this.instrumentName = instrumentName;
		}

		public String getAttenuatorName() {
			return attenuatorName;
		}

		/**
		 * @param attenuatorName name of attenuator in instrument
		 */
		public void setAttenuatorName(String attenuatorName) {
			this.attenuatorName = attenuatorName;
		}

		public String getDetectorName() {
			return detectorName;
		}

		/**
		 * @param detectorName name of detector in instrument 
		 */
		public void setDetectorName(String detectorName) {
			this.detectorName = detectorName;
		}

		public String getDataName() {
			return dataName;
		}

		/**
		 * @param dataName name of data in detector
		 */
		public void setDataName(String dataName) {
			this.dataName = dataName;
		}

		public String getSampleName() {
			return sampleName;
		}

		/**
		 * @param sampleName name of sample in entry
		 */
		public void setSampleName(String sampleName) {
			this.sampleName = sampleName;
		}

		public String[] getOtherPaths() {
			return otherPaths;
		}

		/**
		 * @param otherPaths
		 */
		public void setOtherPaths(String... otherPaths) {
			this.otherPaths = otherPaths;
		}

		public boolean isListMillerEntries() {
			return listMillerEntries;
		}

		/**
		 * @param listMillerEntries if true, output list of hkls and corrected pixel intensities
		 */
		public void setListMillerEntries(boolean listMillerEntries) {
			this.listMillerEntries = listMillerEntries;
		}

		public int[] getQShape() {
			return qShape;
		}

		/**
		 * @param qShape shape of q space volume (can be null)
		 */
		public void setQShape(int[] qShape) {
			this.qShape = qShape;
		}

		public double[] getQStart() {
			return qStart;
		}

		/**
		 * @param qStart starting coordinates of volume of q space (can be null)
		 */
		public void setQStart(double[] qStart) {
			this.qStart = qStart;
		}

		public double[] getQStep() {
			return qStep;
		}

		/**
		 * @param qStep sides of voxels in q space (can be null)
		 */
		public void setQStep(double... qStep) {
			this.qStep = qStep;
		}

		@Override
		protected MillerSpaceMapperBean clone() {
			MillerSpaceMapperBean copy = null;
			try {
				copy = (MillerSpaceMapperBean) super.clone();
				copy.inputs = Arrays.copyOf(inputs, inputs.length);
				copy.otherPaths = otherPaths == null ? null : Arrays.copyOf(otherPaths, otherPaths.length);
				copy.millerShape = millerShape == null ? null : Arrays.copyOf(millerShape, millerShape.length);
				copy.millerStart = millerStart == null ? null : Arrays.copyOf(millerStart, millerStart.length);
				copy.millerStep = millerStep == null ? null : Arrays.copyOf(millerStep, millerStep.length);
				copy.qShape = qShape == null ? null : Arrays.copyOf(qShape, qShape.length);
				copy.qStart = qStart == null ? null : Arrays.copyOf(qStart, qStart.length);
				copy.qStep = qStep == null ? null : Arrays.copyOf(qStep, qStep.length);
			} catch (CloneNotSupportedException e) {
			}
			return copy;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((attenuatorName == null) ? 0 : attenuatorName.hashCode());
			result = prime * result + ((dataName == null) ? 0 : dataName.hashCode());
			result = prime * result + ((detectorName == null) ? 0 : detectorName.hashCode());
			result = prime * result + ((entryPath == null) ? 0 : entryPath.hashCode());
			result = prime * result + Arrays.hashCode(inputs);
			result = prime * result + ((instrumentName == null) ? 0 : instrumentName.hashCode());
			result = prime * result + (listMillerEntries ? 1231 : 1237);
			result = prime * result + Arrays.hashCode(millerShape);
			result = prime * result + Arrays.hashCode(millerStart);
			result = prime * result + Arrays.hashCode(millerStep);
			result = prime * result + Arrays.hashCode(otherPaths);
			result = prime * result + ((output == null) ? 0 : output.hashCode());
			result = prime * result + Arrays.hashCode(qShape);
			result = prime * result + Arrays.hashCode(qStart);
			result = prime * result + Arrays.hashCode(qStep);
			result = prime * result + (reduceToNonZero ? 1231 : 1237);
			result = prime * result + ((sampleName == null) ? 0 : sampleName.hashCode());
			long temp;
			temp = Double.doubleToLongBits(scaleFactor);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			result = prime * result + ((splitterName == null) ? 0 : splitterName.hashCode());
			temp = Double.doubleToLongBits(splitterParameter);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof MillerSpaceMapperBean)) {
				return false;
			}
			MillerSpaceMapperBean other = (MillerSpaceMapperBean) obj;
			if (attenuatorName == null) {
				if (other.attenuatorName != null) {
					return false;
				}
			} else if (!attenuatorName.equals(other.attenuatorName)) {
				return false;
			}
			if (dataName == null) {
				if (other.dataName != null) {
					return false;
				}
			} else if (!dataName.equals(other.dataName)) {
				return false;
			}
			if (detectorName == null) {
				if (other.detectorName != null) {
					return false;
				}
			} else if (!detectorName.equals(other.detectorName)) {
				return false;
			}
			if (entryPath == null) {
				if (other.entryPath != null) {
					return false;
				}
			} else if (!entryPath.equals(other.entryPath)) {
				return false;
			}
			if (!Arrays.equals(inputs, other.inputs)) {
				return false;
			}
			if (instrumentName == null) {
				if (other.instrumentName != null) {
					return false;
				}
			} else if (!instrumentName.equals(other.instrumentName)) {
				return false;
			}
			if (listMillerEntries != other.listMillerEntries) {
				return false;
			}
			if (!Arrays.equals(millerShape, other.millerShape)) {
				return false;
			}
			if (!Arrays.equals(millerStart, other.millerStart)) {
				return false;
			}
			if (!Arrays.equals(millerStep, other.millerStep)) {
				return false;
			}
			if (!Arrays.equals(otherPaths, other.otherPaths)) {
				return false;
			}
			if (output == null) {
				if (other.output != null) {
					return false;
				}
			} else if (!output.equals(other.output)) {
				return false;
			}
			if (!Arrays.equals(qShape, other.qShape)) {
				return false;
			}
			if (!Arrays.equals(qStart, other.qStart)) {
				return false;
			}
			if (!Arrays.equals(qStep, other.qStep)) {
				return false;
			}
			if (reduceToNonZero != other.reduceToNonZero) {
				return false;
			}
			if (sampleName == null) {
				if (other.sampleName != null) {
					return false;
				}
			} else if (!sampleName.equals(other.sampleName)) {
				return false;
			}
			if (Double.doubleToLongBits(scaleFactor) != Double.doubleToLongBits(other.scaleFactor)) {
				return false;
			}
			if (splitterName == null) {
				if (other.splitterName != null) {
					return false;
				}
			} else if (!splitterName.equals(other.splitterName)) {
				return false;
			}
			if (Double.doubleToLongBits(splitterParameter) != Double.doubleToLongBits(other.splitterParameter)) {
				return false;
			}
			return true;
		}
	}
}

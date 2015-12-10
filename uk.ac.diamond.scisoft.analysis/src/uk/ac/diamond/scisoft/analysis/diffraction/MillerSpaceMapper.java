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
			return 1./Math.sqrt(squaredDistance);
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
			final double cx = vd[0] - dx;
			final double cy = vd[1] - dy;
			final double cz = vd[2] - dz;
			final double cxs = cx * cx;
			final double cys = cy * cy;
			final double czs = cz * cz;
			final double dxs = dx * dx;
			final double dys = dy * dy;
			final double dzs = dz * dz;
			weights[0] = calcWeight(dxs + dys + dzs);
			weights[1] = calcWeight(cxs + dys + dzs);
			weights[2] = calcWeight(dxs + cys + dzs);
			weights[3] = calcWeight(cxs + cys + dzs);
			weights[4] = calcWeight(dxs + dys + czs);
			weights[5] = calcWeight(cxs + dys + czs);
			weights[6] = calcWeight(dxs + cys + czs);
			weights[7] = calcWeight(cxs + cys + czs);
	
			factor = 1./(weights[0] + weights[1] + weights[2] + weights[3] + weights[4] + weights[5] + weights[6] + weights[7]);
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
			if (lpos[0] >= 0 || lpos[0] < vShape[0]) {
				w = factor * weights[1];
				addToDataset(volume, lpos, w * value);
				addToDataset(weight, lpos, w);
			}
			lpos[0]--;
	
			lpos[1]++;
			if (lpos[1] >= 0 || lpos[1] < vShape[1]) {
				w = factor * weights[2];
				addToDataset(volume, lpos, w * value);
				addToDataset(weight, lpos, w);
	
				lpos[0]++;
				if (lpos[0] >= 0 || lpos[0] < vShape[0]) {
					w = factor * weights[3];
					addToDataset(volume, lpos, w * value);
					addToDataset(weight, lpos, w);
				}
				lpos[0]--;
			}
			lpos[1]--;
	
			lpos[2]++;
			if (lpos[2] >= 0 || lpos[2] < vShape[2]) {
				w = factor * weights[4];
				addToDataset(volume, lpos, w * value);
				addToDataset(weight, lpos, w);
	
				lpos[0]++;
				if (lpos[0] >= 0 || lpos[0] < vShape[0]) {
					w = factor * weights[5];
					addToDataset(volume, lpos, w * value);
					addToDataset(weight, lpos, w);
				}
				lpos[0]--;
	
				lpos[1]++;
				if (lpos[1] >= 0 || lpos[1] < vShape[1]) {
					w = factor * weights[6];
					addToDataset(volume, lpos, w * value);
					addToDataset(weight, lpos, w);
	
					lpos[0]++;
					if (lpos[0] >= 0 || lpos[0] < vShape[0]) {
						w = factor * weights[7];
						addToDataset(volume, lpos, w * value);
						addToDataset(weight, lpos, w);
					}
				}
			}
		}
	}

	/**
	 * Split pixel over eight voxels with weight determined by exp(-distance^2)
	 */
	static class GaussianSplitter extends InverseSplitter {
		@Override
		protected double calcWeight(double ds) {
			return Math.exp(-ds);
		}
	}

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
				hMin[i] = hDel[i] * Math.floor(hMin[i] / hDel[i]);
				hMax[i] = hDel[i] * (Math.ceil(hMax[i] / hDel[i]) + 1);
				hShape[i] = (int) (Math.floor((hMax[i] - hMin[i] + hDel[i]) / hDel[i]));
			}
		}

		if (reduceToNonZeroBB) {
			min = hShape.clone();
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

		// TODO maybe automatically down delta when out-of-memory
		DoubleDataset map = (DoubleDataset) DatasetFactory.zeros(hShape, Dataset.FLOAT64);
		DoubleDataset tally = (DoubleDataset) DatasetFactory.zeros(hShape, Dataset.FLOAT64);
		mapImages(tree, images, diter, dpos, rank, srank, stop, iter, pos, map, tally, ishape, upSampler);

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

	private void mapImages(Tree tree, ILazyDataset images, PositionIterator diter, int[] dpos, int rank, int srank,
			int[] stop, PositionIterator iter, int[] pos, DoubleDataset map, DoubleDataset tally, int[] ishape,
			BicubicInterpolator upSampler) {
		iter.reset();
		diter.reset();
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

	private void mapImage(int[] s, QSpace qspace, MillerSpace mspace, Dataset image, DoubleDataset map, DoubleDataset tally) {
		int[] hpos = new int[3]; // voxel position
		Vector3d q = new Vector3d();
		Vector3d h = new Vector3d();
//		Vector3d p = new Vector3d(); // position of pixel
//		Vector3d t = new Vector3d(); // temporary
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
					splitter.splitValue(map, tally, hDel, dh, hpos, value);
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
				double mbeg = hMin[i];
				double mend = mbeg + hShape[i] * hDel[i];
				a[i] = DatasetUtils.linSpace(mbeg, mend - hDel[i], hShape[i], Dataset.FLOAT64);
				System.err.println("Axis " + i + ": " + mbeg + " -> " + a[i].getDouble(hShape[i] - 1) +  "; " + mend);
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
	 * @param splitter name of pixel splitting algorithm. Can be "gaussian", "inverse", or null, "", or "nearest" for the default.
	 * @param scale upsampling factor
	 * @param mShape shape of output volume
	 * @param mStart start coordinates in Miller space
	 * @param mDelta sides of voxels in Miller space
	 * @throws ScanFileHolderException
	 */
	public static void processVolume(String input, String output, String splitter, double scale, int[] mShape, double[] mStart, double... mDelta) throws ScanFileHolderException {
		setI16Splitter(splitter);
		I16Mapper.mapToVolumeFile(input, output, scale, false, mShape, mStart, mDelta);
	}

	/**
	 * Process Nexus file for I16 with automatic bounding box setting
	 * @param input Nexus file
	 * @param output name of HDF5 file to be created
	 * @param splitter name of pixel splitting algorithm. Can be "gaussian", "inverse", or null, "", or "nearest" for the default.
	 * @param scale upsampling factor
	 * @param mDelta sides of voxels in Miller space
	 * @param reduceToNonZero if true, reduce output to sub-volume with non-zero data
	 * @throws ScanFileHolderException
	 */
	public static void processVolumeWithAutoBox(String input, String output, String splitter, double scale, boolean reduceToNonZero, double... mDelta) throws ScanFileHolderException {
		setI16Splitter(splitter);
		I16Mapper.mapToVolumeFile(input, output, scale, reduceToNonZero, null, null, mDelta);
	}

	static void setI16Splitter(String splitter) {
		if (splitter == null || splitter.isEmpty()) {
			return;
		}
		if (splitter.equals("gaussian")) {
			I16Mapper.setSplitter(new GaussianSplitter());
		} else if (splitter.equals("inverse")) {
			I16Mapper.setSplitter(new InverseSplitter());
		} else if (!splitter.equals("nearest")) {
			throw new IllegalArgumentException("Splitter is not known");
		}
	}
}

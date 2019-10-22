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
import java.io.IOException;
import java.nio.file.Files;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;

import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.dawnsci.analysis.api.diffraction.DiffractionCrystalEnvironment;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.analysis.api.tree.NodeLink;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.api.tree.TreeFile;
import org.eclipse.dawnsci.analysis.api.tree.TreeUtils;
import org.eclipse.dawnsci.analysis.tree.TreeFactory;
import org.eclipse.dawnsci.hdf5.HDF5FileFactory;
import org.eclipse.dawnsci.hdf5.HDF5Utils;
import org.eclipse.dawnsci.nexus.NexusConstants;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.ILazyWriteableDataset;
import org.eclipse.january.dataset.LazyWriteableDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.PositionIterator;
import org.eclipse.january.dataset.ShapeUtils;
import org.eclipse.january.dataset.SliceND;
import org.eclipse.january.dataset.StringDataset;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tec.units.indriya.unit.Units;
import uk.ac.diamond.scisoft.analysis.crystallography.MillerSpace;
import uk.ac.diamond.scisoft.analysis.crystallography.VersionUtils;
import uk.ac.diamond.scisoft.analysis.dataset.function.BicubicInterpolator;
import uk.ac.diamond.scisoft.analysis.io.ImageStackLoader;
import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
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
	private int[] sMin = new int[3]; // volume shape min and max 
	private int[] sMax = new int[3];
	private double scale; // image upsampling factor

	private PixelSplitter splitter;

	private double[] vDel;
	private double[] vMin;
	private double[] vMax;

	private boolean hasDeleted;
	private boolean listMillerEntries;
	private String entryPath;
	private Dataset imageWeight;
	private int begX;
	private int endX;
	private int begY;
	private int endY;

	private static final String VOLUME_NAME = "volume";
	private static final String[] MILLER_VOLUME_AXES = new String[] { "h-axis", "k-axis", "l-axis" };
	private static final String[] Q_VOLUME_AXES = new String[] { "x-axis", "y-axis", "z-axis" };

	private static final String ENTRY = "processed";
	private static final String PROCESS = "process";
	private static final String PROCESSED = Tree.ROOT + ENTRY;
	private static final String PROCESSPATH = PROCESSED + Node.SEPARATOR + "process";

	private static final String INDICES_NAME = "hkli_list";

	private static final String VERSION = "1.0";

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

	private Tree getTreeFromNexusFile(String file) {
		try {
			return LoaderFactory.getData(NexusHDF5Loader.class, file, true, null).getTree();
		} catch (Exception e) {
			throw new IllegalArgumentException("No tree loaded from file: " + file, e);
		}
	}

	private static void initializeVolumeBoundingBox(int[] vShape, int[] min, int[] max) {
		System.arraycopy(vShape, 0, min, 0, vShape.length);
		Arrays.fill(max, -1);
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
		Tree tree = getTreeFromNexusFile(filePath);
		PositionIterator[] iters = getPositionIterators(tree);
	
		if (findImageBB) {
			Arrays.fill(vMin, Double.POSITIVE_INFINITY);
			Arrays.fill(vMax, Double.NEGATIVE_INFINITY);

			findBoundingBoxes(tree, iters);
			roundLimitsAndFindShapes();
			System.err.println("Extent of the space was found to be " + Arrays.toString(vMin) + " to " + Arrays.toString(vMax));
			System.err.println("with shape = " + Arrays.toString(vShape));
		}

		if (reduceToNonZeroBB) {
			initializeVolumeBoundingBox(vShape, sMin, sMax);
		}

		DoubleDataset map = DatasetFactory.zeros(vShape);
		DoubleDataset weight = DatasetFactory.zeros(vShape);

		createImageWeight(tree, iters[0]);

		try {
			mapToASpace(mapQ, tree, iters, map, weight);
		} catch (ScanFileHolderException sfhe) {
			throw sfhe;
		} catch (DatasetException e) {
			throw new ScanFileHolderException("Could not get data from lazy dataset", e);
		}

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

	private void mapToASpace(boolean mapQ, Tree tree, PositionIterator[] iters, DoubleDataset map, DoubleDataset weight) throws ScanFileHolderException, DatasetException {
		int[] dshape = iters[0].getShape();

		if (tree instanceof TreeFile) {
			System.out.println("Mapping " + ((TreeFile) tree).getFilename() + " ...");
		}
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

	private void listToASpace(Tree tree, PositionIterator[] iters, ILazyWriteableDataset lazy) throws ScanFileHolderException, DatasetException {
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

	private boolean isOLDI16GDA(Tree t) {
		NodeLink nl = t.findNodeLink(entryPath);
		GroupNode g = (GroupNode) nl.getDestination();
		DataNode d = g.getDataNode("program_name");
		if (d != null) {
			String program = NexusTreeUtils.parseStringArray(d, 1)[0];
			if (program != null && program.startsWith("GDA")) {
				try {
					g = (GroupNode) NexusTreeUtils.requireNode(g, NexusConstants.USER);
					String user = NexusTreeUtils.parseStringArray(g.getDataNode("username"), 1)[0];
					if (user != null && user.equals("i16user")) {
						// program name given as "GDA 9.14.0", need version 9.15+
						String version = program.substring(program.indexOf(" ")).trim();
						return VersionUtils.isOldVersion("9.14.0", version);
					}
				} catch (NexusException e) {
					// do nothing
				}
			}
		}
		return false;
	}

	/**
	 * Find bounding boxes in reciprocal space and q-space
	 * @param tree
	 * @param iters
	 */
	private void findBoundingBoxes(Tree tree, PositionIterator[] iters) {
		PositionIterator diter = iters[0];
		PositionIterator iter = iters[1];
		int[] dpos = diter.getPos();

		boolean isOldGDA = isOLDI16GDA(tree);
		while (iter.hasNext() && diter.hasNext()) {
			DetectorProperties dp = NexusTreeUtils.parseDetector(detectorPath, tree, dpos)[0];
			DiffractionSample sample = NexusTreeUtils.parseSample(samplePath, tree, isOldGDA, dpos);
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
	}

	private void roundLimitsAndFindShapes() {
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
		int halfX = (begX + endX)/2;
		int halfY = (begY + endY)/2;

		if (mspace == null) {
			qspace.qFromPixelPosition(begX, begY, q);
			minMax(vBeg, vEnd, q);
		
			qspace.qFromPixelPosition(halfX, begY, q);
			minMax(vBeg, vEnd, q);
		
			qspace.qFromPixelPosition(endX, begY, q);
			minMax(vBeg, vEnd, q);
		
			qspace.qFromPixelPosition(begX, halfY, q);
			minMax(vBeg, vEnd, q);
		
			qspace.qFromPixelPosition(halfX, halfY, q);
			minMax(vBeg, vEnd, q);
		
			qspace.qFromPixelPosition(endX, halfY, q);
			minMax(vBeg, vEnd, q);
		
			qspace.qFromPixelPosition(begX, endY, q);
			minMax(vBeg, vEnd, q);
		
			qspace.qFromPixelPosition(halfX, endY, q);
			minMax(vBeg, vEnd, q);
		
			qspace.qFromPixelPosition(endX, endY, q);
			minMax(vBeg, vEnd, q);
		} else {
			qspace.qFromPixelPosition(begX, begY, q);
			mspace.h(q, null, m);
			minMax(vBeg, vEnd, m);

			qspace.qFromPixelPosition(halfX, begY, q);
			mspace.h(q, null, m);
			minMax(vBeg, vEnd, m);

			qspace.qFromPixelPosition(endX, begY, q);
			mspace.h(q, null, m);
			minMax(vBeg, vEnd, m);

			qspace.qFromPixelPosition(begX, halfY, q);
			mspace.h(q, null, m);
			minMax(vBeg, vEnd, m);

			qspace.qFromPixelPosition(halfX, halfY, q);
			mspace.h(q, null, m);
			minMax(vBeg, vEnd, m);

			qspace.qFromPixelPosition(endX, halfY, q);
			mspace.h(q, null, m);
			minMax(vBeg, vEnd, m);

			qspace.qFromPixelPosition(begX, endY, q);
			mspace.h(q, null, m);
			minMax(vBeg, vEnd, m);

			qspace.qFromPixelPosition(halfX, endY, q);
			mspace.h(q, null, m);
			minMax(vBeg, vEnd, m);

			qspace.qFromPixelPosition(endX, endY, q);
			mspace.h(q, null, m);
			minMax(vBeg, vEnd, m);
		}
	}

	/**
	 * Print where corners of all images are in Miller (aka HKL) space
	 * @param endsOnly if true, only print from first and last images 
	 * @throws ScanFileHolderException
	 */
	public void printMillerSpaceCorners(final boolean endsOnly) throws ScanFileHolderException {
		processBean(bean.getInputs()[0]);

		Tree tree = getTreeFromNexusFile(bean.getInputs()[0]);
		PositionIterator[] iters = getPositionIterators(tree);
	
		PositionIterator diter = iters[0];
		PositionIterator iter = iters[1];
		int[] dpos = diter.getPos();

		int end = ShapeUtils.calcSize(diter.getShape()) - 1;
		int n = 0;
		boolean isOldGDA = isOLDI16GDA(tree);
		while (iter.hasNext() && diter.hasNext()) {
			if (!endsOnly || n == 0 || n == end) {
				System.out.println("Image " + n);
				DetectorProperties dp = NexusTreeUtils.parseDetector(detectorPath, tree, dpos)[0];
				if (n == 0) {
					initializeImageLimits(dp);
				}
				DiffractionSample sample = NexusTreeUtils.parseSample(samplePath, tree, isOldGDA, dpos);
				DiffractionCrystalEnvironment env = sample.getDiffractionCrystalEnvironment();
				QSpace qspace = new QSpace(dp, env);
				MillerSpace mspace = new MillerSpace(sample.getUnitCell(), env.getOrientation());
				printCorners(qspace, mspace);
			}
			n++;
		}
	}

	private void printCorners(QSpace qspace, MillerSpace mspace) {
		printCorner(qspace, mspace, begX, begY);
		printCorner(qspace, mspace, endX, begY);
		printCorner(qspace, mspace, begX, endY);
		printCorner(qspace, mspace, endX, endY);
	}

	private static void printCorner(QSpace qspace, MillerSpace mspace, int x, int y) {
		Vector3d q = new Vector3d();
		Vector3d m = new Vector3d();

		qspace.qFromPixelPosition(x, y, q);
		mspace.h(q, null, m);
		System.out.printf("%d,%d: %s\n", x, y, m.toString());
	}

	private void mapImages(boolean mapQ, Tree tree, Dataset trans, ILazyDataset images, PositionIterator[] iters,
			DoubleDataset map, DoubleDataset weight, int[] ishape, BicubicInterpolator upSampler) throws DatasetException {
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

		int[] region = new int[] {(int) (begX*scale), (int) (endX*scale), (int) (begY*scale), (int) (endY*scale)};
		Dataset iWeight = imageWeight;
		if (iWeight != null && upSampler != null) {
			iWeight = upSampler.value(iWeight).get(0);
			if (!Arrays.equals(iWeight.getShapeRef(), ishape)) {
				String msg = String.format("Image weight shape %s does not match image shape %s", Arrays.toString(iWeight.getShapeRef()), Arrays.toString(ishape));
				throw new IllegalArgumentException(msg);
			}
		}
		boolean isOldGDA = isOLDI16GDA(tree);
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
			DiffractionSample sample = NexusTreeUtils.parseSample(samplePath, tree, isOldGDA, dpos);
			DiffractionCrystalEnvironment env = sample.getDiffractionCrystalEnvironment();
			QSpace qspace = new QSpace(dp, env);
			if (!mapQ) {
				mspace = new MillerSpace(sample.getUnitCell(), env.getOrientation());
			}
			Dataset image = DatasetUtils.convertToDataset(images.getSlice(pos, stop, null));
			if (trans != null) {
				double t = trans.getSize() == 1 ? trans.getElementDoubleAbs(0) : trans.getDouble(dpos); 
				if (image.hasFloatingPointElements()) {
					image.idivide(t);
				} else {
					image = Maths.divide(image, t);
				}
			}
			int[] s = Arrays.copyOfRange(image.getShapeRef(), srank, rank);
			image.setShape(s);
			if (image.max().doubleValue() <= 0) {
				String n = tree instanceof TreeFile ? " in " + ((TreeFile) tree).getFilename() : "";
				System.err.println("Skipping image at " + Arrays.toString(pos) + n);
				continue;
			}
			if (upSampler != null) {
				image = upSampler.value(image).get(0);
			}
			mapImage(region, qspace, mspace, image, iWeight, map, weight);
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

	private void mapImage(final int[] region, final QSpace qspace, final MillerSpace mspace, final Dataset image, final Dataset iWeight, final DoubleDataset map, final DoubleDataset weight) {
		final int[] pos = new int[3]; // voxel position
		final Matrix3d mTransform = mspace == null ? null : mspace.getMillerTransform();
		final Vector3d v = new Vector3d();
		final Vector3d dv = new Vector3d(); // delta in h

		for (int y = region[2]; y < region[3]; y++) {
			for (int x = region[0]; x < region[1]; x++) {
				double value = image.getDouble(y, x);
				if (iWeight != null) {
					value *= iWeight.getDouble(y, x);
				}
				if (value > 0) {
					qspace.qFromPixelPosition(x + 0.5, y + 0.5, v);

					if (mTransform != null) {
						mTransform.transform(v);
					}

					if (convertToVoxel(v, dv, pos)) {
						value /= qspace.calculateSolidAngle(x, y);
						if (reduceToNonZeroBB) {
							minMax(sMin, sMax, pos);
						}
						splitter.splitValue(map, weight, vDel, dv, pos, value);
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
			ILazyWriteableDataset lazy, int[] ishape, BicubicInterpolator upSampler) throws DatasetException, ScanFileHolderException {
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

		boolean isOldGDA = isOLDI16GDA(tree);
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
			DiffractionSample sample = NexusTreeUtils.parseSample(samplePath, tree, isOldGDA, dpos);
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
				list = DatasetFactory.zeros(image.getSize(), 4);
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
		} catch (DatasetException e) {
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

	private Dataset[][] processBean(String file) throws ScanFileHolderException {
		Tree tree = getTreeFromNexusFile(file);
		NodeLink link;

		entryPath = bean.getEntryPath();
		if (entryPath == null || entryPath.isEmpty()) {
			link = NexusTreeUtils.findFirstNode(tree.getGroupNode(), NexusConstants.ENTRY);
			System.out.println(NexusConstants.ENTRY + " found: " + link);
			entryPath = TreeUtils.getPath(tree, link.getDestination());
		} else {
			link = tree.findNodeLink(entryPath);
		}
		if (link == null || !link.isDestinationGroup()) {
			throw new ScanFileHolderException("Could not find entry");
		}
		GroupNode entry = (GroupNode) link.getDestination();

		String instrumentName = bean.getInstrumentName();
		if (instrumentName == null || instrumentName.isEmpty()) {
			link = NexusTreeUtils.findFirstNode(entry, NexusConstants.INSTRUMENT);
			System.out.println(NexusConstants.INSTRUMENT + " found: " + link);
		} else {
			link = entry.getNodeLink(instrumentName);
		}
		if (link == null || !link.isDestinationGroup()) {
			throw new ScanFileHolderException("Could not find instrument");
		}
		GroupNode instrument = (GroupNode) link.getDestination();

		String detectorName = bean.getDetectorName();
		if (detectorName == null || detectorName.isEmpty()) {
			link = NexusTreeUtils.findFirstNode(instrument, NexusConstants.DETECTOR);
			System.out.println(NexusConstants.DETECTOR + " found: " + link);
		} else {
			link = instrument.getNodeLink(detectorName);
		}
		if (link == null || !link.isDestinationGroup()) {
			throw new ScanFileHolderException("Could not find detector");
		}
		GroupNode detector = (GroupNode) link.getDestination();
		detectorPath = TreeUtils.getPath(tree, detector);

		timePath = detectorPath + "count_time";

		String attenuatorName = bean.getAttenuatorName();
		if (attenuatorName == null || attenuatorName.isEmpty()) {
			link = NexusTreeUtils.findFirstNode(instrument, NexusConstants.ATTENUATOR);
			System.out.println(NexusConstants.ATTENUATOR + " found: " + link);
		} else {
			link = instrument.getNodeLink(attenuatorName);
		}
		if (link == null || !link.isDestinationGroup()) {
			throw new ScanFileHolderException("Could not find attenuator");
		}
		attenuatorPath = TreeUtils.getPath(tree, link.getDestination());

		String dataName = bean.getDataName();
		if (dataName == null || dataName.isEmpty()) {
			link = NexusTreeUtils.findFirstSignalDataNode(detector);
			if (link == null) {
				link = detector.getNodeLink(NexusConstants.DATA_DATA);
			}
			System.out.println("Data found: " + link);
		} else {
			link = detector.getNodeLink(dataName);
		}

		if (link == null) {
			System.err.println("Missing image data in " + detectorPath + " - synthesizing it");
			link = synthesizeMissingImageDataForI16(file, entry, detector);
		}

		if (link == null || !link.isDestinationData()) {
			throw new ScanFileHolderException("Could not find image data");
		}
		dataPath = TreeUtils.getPath(tree, link.getDestination());

		String sampleName = bean.getSampleName();
		if (sampleName == null || sampleName.isEmpty()) {
			link = NexusTreeUtils.findFirstNode(entry, NexusConstants.SAMPLE);
			System.out.println(NexusConstants.SAMPLE + " found: " + link);
		} else {
			link = entry.getNodeLink(sampleName);
		}
		if (link == null || !link.isDestinationGroup()) {
			throw new ScanFileHolderException("Could not find sample");
		}
		samplePath = TreeUtils.getPath(tree, link.getDestination());

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

	private static final String I16_IMAGE_DATA = "image_data";
	private NodeLink synthesizeMissingImageDataForI16(String file, GroupNode entry, GroupNode detector) throws ScanFileHolderException {
		DataNode dn = entry.getDataNode("scan_dimensions");
		if (dn == null) {
			throw new ScanFileHolderException("I16 workaround: missing scan_dimensions in NXentry group");
		}

		// find directory in parent 
		File f = new File(file);
		File pf = f.getParentFile();
		String sn = f.getName().split("\\.")[0]; // expected file name to be scan_number.*
		File[] dirs = pf.listFiles(nf -> nf.isDirectory() && nf.getName().startsWith(sn));
		if (dirs.length == 0) {
			throw new ScanFileHolderException(String.format("I16 workaround: expecting directory starting with %s to exist", sn));
		}
		if (dirs.length > 1) {
			System.err.printf("Warning: just using first of candidate directories: %s\n", Arrays.toString(dirs));
		}

		List<String> names;
		try {
			names = Files.list(dirs[0].toPath())
					.filter(p -> p.toFile().isFile())
					.sorted()
					.map(p -> p.toString())
					.collect(Collectors.toList());
		} catch (IOException e1) {
			throw new ScanFileHolderException("I16 workaround: collating image file names", e1);
		}

		StringDataset pd = DatasetFactory.createFromObject(StringDataset.class, names, NexusTreeUtils.parseIntArray(dn));

		DataNode node = TreeFactory.createDataNode(-1);
		node.addAttribute(TreeFactory.createAttribute(NexusConstants.DATA_SIGNAL, 1));
		ImageStackLoader loader;
		try {
			loader = new ImageStackLoader(pd, pf.getAbsolutePath());
		} catch (Exception e) {
			throw new ScanFileHolderException("I16 workaround: cannot create image stack loader", e);
		}
		loader.setMaxShape(dn.getMaxShape());
		loader.squeeze();
		node.setMaxShape(loader.getMaxShape());
		node.setChunkShape(loader.getChunkShape());
		node.setDataset(loader.createLazyDataset(I16_IMAGE_DATA));
		detector.addDataNode(I16_IMAGE_DATA, node);
		return detector.getNodeLink(I16_IMAGE_DATA);
	}

	/**
	 * Map images from given Nexus files to a volume in Miller (aka HKL) space and save to a HDF5 file
	 * @throws ScanFileHolderException
	 */
	public void mapToVolumeFile() throws ScanFileHolderException {
		hasDeleted = false; // reset state

		Dataset[][] a = processBean(bean.getInputs()[0]);
		if (qDel == null && hDel == null && !listMillerEntries) {
			throw new IllegalStateException("Both q space and Miller space parameters have not been defined");
		}
		String[] inputs = bean.getInputs();

		int n = inputs.length;
		Tree[] trees = new Tree[n];
		PositionIterator[][] allIters = new PositionIterator[n][];
		for (int i = 0; i < n; i++) {
			trees[i] = getTreeFromNexusFile(inputs[i]);
			allIters[i] = getPositionIterators(trees[i]);
		}

		createImageWeight(trees[0], allIters[0][0]);

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
			roundLimitsAndFindShapes();

			if (qDel != null) {
				System.err.println("Extent of q space was found to be " + Arrays.toString(qMin) + " to " + Arrays.toString(qMax));
				System.err.println("with shape = " + Arrays.toString(qShape));
			}
			if (hDel != null) {
				System.err.println("Extent of Miller space was found to be " + Arrays.toString(hMin) + " to " + Arrays.toString(hMax));
				System.err.println("with shape = " + Arrays.toString(hShape));
			}
		}

		try {
			if (qDel != null) {
				processTrees(true, trees, allIters, a[0]);
			}

			if (hDel != null) {
				processTrees(false, trees, allIters, a[1]);
			}

			if (listMillerEntries) {
				processTreesForList(trees, allIters);
			}
		} catch (ScanFileHolderException sfhe) {
			throw sfhe;
		} catch (DatasetException e) {
			throw new ScanFileHolderException("Could not get data from lazy dataset", e);
		}
	}

	private void createImageWeight(Tree tree, PositionIterator dIter) {
		String wFile = bean.getWeightFilePath();
		if (wFile != null) {
			try {
				imageWeight = DatasetUtils.convertToDataset(LoaderFactory.getData(wFile).getDataset(0));
			} catch (Exception e1) {
				System.err.println("Could not load image weight dataset from " + wFile);
			} 
		}

		int[] dpos = dIter.getPos();
		DetectorProperties dp = NexusTreeUtils.parseDetector(detectorPath, tree, dpos)[0];
		initializeImageLimits(dp);
	}

	private void initializeImageLimits(DetectorProperties dp) {
		begX = 0;
		endX = dp.getPx();
		begY = 0;
		endY = dp.getPy();

		int[] region = bean.getRegion();
		if (region != null) {
			begX = Math.max(region[0], begX);
			endX = Math.min(region[1], endX);
			if (begX >= endX) { // no width
				return;
			}
			begY = Math.max(region[2], begY);
			endY = Math.min(region[3], endY);
			if (begY >= endY) { // no height
				return;
			}
		}
	}

	private void processTrees(boolean mapQ, Tree[] trees, PositionIterator[][] allIters, Dataset[] a) throws ScanFileHolderException, DatasetException {
		int[] vShape = copyParameters(mapQ);
		String output = bean.getOutput();

		if (reduceToNonZeroBB) {
			initializeVolumeBoundingBox(vShape, sMin, sMax);
		}

		String volName = mapQ ? "q_space" : "reciprocal_space";
		String volPath = PROCESSPATH + Node.SEPARATOR + volName;

		try {
			DoubleDataset map = DatasetFactory.zeros(vShape);
			DoubleDataset weight = DatasetFactory.zeros(vShape);

			for (int i = 0; i < trees.length; i++) {
				Tree tree = trees[i];
				mapToASpace(mapQ, tree, allIters[i], map, weight);
			}
			Maths.dividez(map, weight, map); // normalize by tally

			if (reduceToNonZeroBB) {
				System.err.println("Reduced to non-zero bounding box: " + Arrays.toString(sMin) + " to " + Arrays.toString(sMax));
				for (int i = 0; i < 3; i++) {
					vMin[i] += sMin[i]*vDel[i];
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

			createAndWriteAttribute(output, PROCESSED, NexusConstants.NXCLASS, NexusConstants.ENTRY);
			createAndWriteAttribute(output, PROCESSPATH, NexusConstants.NXCLASS, NexusConstants.PROCESS);

			saveVolume(output, bean, entryPath, volPath, map, a);
			writeDefaultAttributes(output, volName);
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
					map = DatasetFactory.zeros(tShape);
					weight = DatasetFactory.zeros(tShape);
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
			LazyWriteableDataset lazy = HDF5Utils.createLazyDataset(output, volPath, VOLUME_NAME, vShape, null, cShape, DoubleDataset.class, null, false);
			mapAndSaveInParts(mapQ, trees, allIters, lazy, parts, map, weight);

			saveAxesAndAttributes(output, volPath, a);
		}
	}

	private static void writeDefaultAttributes(String file, String volName) throws ScanFileHolderException {
		Dataset x = DatasetFactory.createFromObject(ENTRY);
		x.setName(NexusConstants.DEFAULT);
		HDF5Utils.writeAttributes(file, Tree.ROOT, false, x);

		Dataset y = DatasetFactory.createFromObject(PROCESS + Node.SEPARATOR + volName);
		y.setName(NexusConstants.DEFAULT);
		HDF5Utils.writeAttributes(file, PROCESSED, false, y);
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
			a[i] = DatasetFactory.createLinearSpace(DoubleDataset.class, mbeg, mend - mDelta[i], mShape[i]);
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
		if (dshape == null) {
			throw new IllegalArgumentException("Could not parse detector scan shape from tree");
		}
//		System.err.println(Arrays.toString(dshape));

		dshape = NexusTreeUtils.parseSampleScanShape(samplePath, tree, dshape);
		if (dshape == null) {
			throw new IllegalArgumentException("Could not parse sample scan shape from tree");
		}
//		System.err.println(Arrays.toString(dshape));

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
	 * @param bean
	 * @param entryPath path to NXentry
	 * @param volPath name for NXdata
	 * @param v volume Dataset
	 * @param axes axes Datasets
	 * @throws ScanFileHolderException
	 */
	public static void saveVolume(String file, MillerSpaceMapperBean bean, String entryPath, String volPath,
			Dataset v, Dataset... axes) throws ScanFileHolderException {

		v.setName(VOLUME_NAME);
		HDF5Utils.writeDataset(file, volPath, v);
		saveAxesAndAttributes(file, volPath, axes);

		String className = MillerSpaceMapper.class.getSimpleName();
		String program = "DAWN." + className;
		String timeStamp = Instant.now().toString();

		try {
			ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writeValueAsString(bean);

			writeData(file, PROCESSPATH, jsonString, "parameters");
			writeData(file, PROCESSPATH, program, "program");
			writeData(file, PROCESSPATH, timeStamp, "date");
			writeData(file, PROCESSPATH, VERSION, "version");

			String[] inputs = bean.inputs;
			for (int i = 0; i < inputs.length; i++) {
				String destination = String.format("/entry%d", i);
				HDF5Utils.createExternalLink(file, destination, inputs[i], entryPath);
			}

		} catch (JsonProcessingException e) {
			throw new ScanFileHolderException("Could not process JSON file", e);
		}
	}

	/**
	 * Create dataset and write as attribute
	 * 
	 * @param file
	 * @param path
	 * @param attributeName
	 * @param attribute
	 * @throws ScanFileHolderException
	 */
	public static void createAndWriteAttribute(String file, String path, String attributeName, String attribute)
			throws ScanFileHolderException {
		Dataset x = DatasetFactory.createFromObject(attribute);
		x.setName(attributeName);
		HDF5Utils.writeAttributes(file, path, true, x);
	}

	/**
	 * Write data string to file as dataset
	 * 
	 * @param file
	 * @param parentPath
	 * @param dataString
	 * @param dataName
	 * @throws ScanFileHolderException
	 */
	public static void writeData(String file, String parentPath, String dataString, String dataName)
			throws ScanFileHolderException {
		Dataset dataset = DatasetFactory.createFromObject(dataString);
		dataset.setName(dataName);
		HDF5Utils.writeDataset(file, parentPath, dataset);
	}

	public static void saveAxesAndAttributes(String file, String volPath, Dataset... axes)
			throws ScanFileHolderException {
		String[] axisNames = new String[axes.length];
		for (int i = 0; i < axes.length; i++) {
			Dataset x = axes[i];
			axisNames[i] = x.getName();
			HDF5Utils.writeDataset(file, volPath, x);
		}

		List<Dataset> attrs = new ArrayList<>();
		Dataset a;

		a = DatasetFactory.createFromObject(NexusConstants.DATA);
		a.setName(NexusConstants.NXCLASS);
		attrs.add(a);

		a = DatasetFactory.createFromObject(VOLUME_NAME);
		a.setName(NexusConstants.DATA_SIGNAL);
		attrs.add(a);

		a = DatasetFactory.createFromObject(axisNames);
		a.setName(NexusConstants.DATA_AXES);
		attrs.add(a);

		for (int i = 0; i < axisNames.length; i++) {
			a = DatasetFactory.createFromObject(i);
			a.setName(axisNames[i] + NexusConstants.DATA_INDICES_SUFFIX);
			attrs.add(a);
		}

		HDF5Utils.writeAttributes(file, volPath, true, attrs.toArray(new Dataset[attrs.size()]));
	}

	/**
	 * Map images from given Nexus file to a volume in Miller (aka HKL) space
	 * @param trees 
	 * @param allIters 
	 * @param parts 
	 * @param map 
	 * @param weight 
	 * @throws ScanFileHolderException 
	 * @throws DatasetException 
	 */
	private void mapAndSaveInParts(boolean mapQ, Tree[] trees, PositionIterator[][] allIters, LazyWriteableDataset output, int parts, DoubleDataset map, DoubleDataset weight) throws ScanFileHolderException, DatasetException {
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
				Dataset time = NexusTreeUtils.getDataset(timePath, tree, Units.SECOND);
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
			} catch (DatasetException e) {
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

	private void processTreesForList(Tree[] trees, PositionIterator[][] allIters)
			throws ScanFileHolderException, DatasetException {
		String output = bean.getOutput();
		if (!hasDeleted) {
			HDF5FileFactory.deleteFile(output);
			hasDeleted = true;
		}

		// Each pixel => HKL (3 doubles) plus corrected intensity (1 double)
		String millerIndicesPath = PROCESSPATH + Node.SEPARATOR + "indices";
		LazyWriteableDataset lazy = HDF5Utils.createLazyDataset(output, millerIndicesPath, INDICES_NAME, new int[] {0,4}, new int[] {-1,4}, new int[] {1024, 4}, DoubleDataset.class, null, false);
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
		processVolume(new String[] {input}, output, splitter, p, scale, mShape, mStart, mDelta);
	}

	/**
	 * Process Nexus files for I16
	 * @param inputs Nexus files
	 * @param output name of HDF5 file to be created
	 * @param splitter name of pixel splitting algorithm. Can be "gaussian", "inverse", or null, "", or "nearest" for the default.
	 * @param p splitter parameter
	 * @param scale upsampling factor
	 * @param mShape shape of Miller space volume
	 * @param mStart start coordinates in Miller space
	 * @param mDelta sides of voxels in Miller space
	 * @throws ScanFileHolderException
	 */
	public static void processVolume(String[] inputs, String output, String splitter, double p, double scale, int[] mShape, double[] mStart, double... mDelta) throws ScanFileHolderException {
		setBean(I16MapperBean, inputs, output, splitter, p, scale, mShape, mStart, mDelta, null, null, null);
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
		processBothVolumes(new String[] {input}, output, splitter, p, scale, mShape, mStart, mDelta, qShape, qStart, qDelta);
	}

	/**
	 * Process Nexus file for I16
	 * @param inputs Nexus file
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
	public static void processBothVolumes(String[] inputs, String output, String splitter, double p, double scale, int[] mShape, double[] mStart, double[] mDelta, int[] qShape, double[] qStart, double[] qDelta) throws ScanFileHolderException {
		setBean(I16MapperBean, inputs, output, splitter, p, scale, mShape, mStart, mDelta, qShape, qStart, qDelta);
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
	public static void processBothVolumesWithAutoBox(String[] inputs, String output, String splitter, double p, double scale, boolean reduceToNonZero, double[] mDelta, double... qDelta) throws ScanFileHolderException {
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

	/**
	 * Print where corners of all images are in Miller (aka HKL) space
	 * @param input Nexus file
	 * @param endsOnly if true, only print from first and last images 
	 * @throws ScanFileHolderException
	 */
	public static void printCorners(String input, boolean endsOnly) throws ScanFileHolderException {
		I16MapperBean.setInputs(input);
		MillerSpaceMapper mapper = new MillerSpaceMapper(I16MapperBean);
		mapper.printMillerSpaceCorners(endsOnly);
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
//		bean.setEntryPath("/entry1");
//		bean.setInstrumentName("instrument");
//		bean.setAttenuatorName("attenuator");
//		bean.setDetectorName("pil100k");
//		bean.setDataName("image_data");
//		bean.setSampleName("sample");
		return bean;
	}

	/**
	 * Set up bean to process Nexus file with automatic bounding box setting
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
	 * Set up bean to process Nexus file with automatic bounding box setting
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
		bean.setListMillerEntries(false);
	}

	/**
	 * Set up bean to process Nexus files
	 * @param inputs Nexus files
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
	public static void setBean(MillerSpaceMapperBean bean, String[] inputs, String output, String splitter, double p, double scale, int[] mShape, double[] mStart, double[] mDelta, int[] qShape, double[] qStart, double[] qDelta) {
		bean.setInputs(inputs);
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

		private int[] region; // masking ROI where only point within its bounds contribute to volume
		private String weightFilePath; // file path to image weight 

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
			if (inputs == null || inputs.length == 0) {
				throw new IllegalArgumentException("One or more file names must be supplied");
			}
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
		 * @param entryPath (can be null to imply 1st NXentry)
		 */
		public void setEntryPath(String entryPath) {
			this.entryPath = entryPath;
		}

		public String getInstrumentName() {
			return instrumentName;
		}

		/**
		 * @param instrumentName name of instrument in entry (can be null to imply 1st NXinstrument)
		 */
		public void setInstrumentName(String instrumentName) {
			this.instrumentName = instrumentName;
		}

		public String getAttenuatorName() {
			return attenuatorName;
		}

		/**
		 * @param attenuatorName name of attenuator in instrument (can be null to imply 1st NXattenuator)
		 */
		public void setAttenuatorName(String attenuatorName) {
			this.attenuatorName = attenuatorName;
		}

		public String getDetectorName() {
			return detectorName;
		}

		/**
		 * @param detectorName name of detector in instrument (can be null to imply 1st NXdetector)
		 */
		public void setDetectorName(String detectorName) {
			this.detectorName = detectorName;
		}

		public String getDataName() {
			return dataName;
		}

		/**
		 * @param dataName name of data in detector (can be null to imply 1st dataset with signal attribute)
		 */
		public void setDataName(String dataName) {
			this.dataName = dataName;
		}

		public String getSampleName() {
			return sampleName;
		}

		/**
		 * @param sampleName name of sample in entry (can be null to imply 1st NXsample)
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

		/**
		 * Set rectangular region that defines the area with its bounds that contribute to volume
		 * @param sx start x
		 * @param ex end x (exclusive)
		 * @param sy start y
		 * @param ey end y (exclusive)
		 */
		public void setRegion(int sx, int ex, int sy, int ey) {
			this.region = new int[] {sx, ex, sy, ey};
		}

		public int[] getRegion() {
			return region;
		}

		/**
		 * Set the file path to a weight dataset that will be applied to each pixel in image
		 * @param weight file path
		 */
		public void setWeightFilePath(String weight) {
			this.weightFilePath = weight;
		}

		public String getWeightFilePath() {
			return weightFilePath;
		}

		@Override
		protected MillerSpaceMapperBean clone() {
			MillerSpaceMapperBean copy = null;
			try {
				copy = (MillerSpaceMapperBean) super.clone();
				copy.inputs = Arrays.copyOf(inputs, inputs.length);
				copy.otherPaths = otherPaths == null ? null : Arrays.copyOf(otherPaths, otherPaths.length);
				copy.millerShape = millerShape == null ? null : millerShape.clone();
				copy.millerStart = millerStart == null ? null : millerStart.clone();
				copy.millerStep = millerStep == null ? null : millerStep.clone();
				copy.qShape = qShape == null ? null : qShape.clone();
				copy.qStart = qStart == null ? null : qStart.clone();
				copy.qStep = qStep == null ? null : qStep.clone();
				copy.region = region == null ? null : region.clone();
				copy.weightFilePath = weightFilePath;
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
			result = prime * result + ((region == null) ? 0 : Arrays.hashCode(region));
			result = prime * result + ((weightFilePath == null) ? 0 : weightFilePath.hashCode());
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
			if (!Arrays.equals(region, other.region)) {
				return false;
			}
			if (!Objects.equals(weightFilePath, other.weightFilePath)) {
				return false;
			}
			return true;
		}
	}
}

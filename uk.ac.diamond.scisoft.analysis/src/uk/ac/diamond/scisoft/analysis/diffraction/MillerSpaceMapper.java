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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Consumer;
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
import org.eclipse.january.dataset.BooleanDataset;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.ILazyWriteableDataset;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.LazyWriteableDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.PositionIterator;
import org.eclipse.january.dataset.ShapeUtils;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.dataset.SliceND;
import org.eclipse.january.dataset.StringDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tec.units.indriya.unit.Units;
import uk.ac.diamond.scisoft.analysis.crystallography.MillerSpace;
import uk.ac.diamond.scisoft.analysis.crystallography.VersionUtils;
import uk.ac.diamond.scisoft.analysis.dataset.function.BicubicInterpolator;
import uk.ac.diamond.scisoft.analysis.fitting.functions.CoordinatesIterator;
import uk.ac.diamond.scisoft.analysis.io.ImageStackLoader;
import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.io.NexusHDF5Loader;
import uk.ac.diamond.scisoft.analysis.io.NexusTreeUtils;

/**
 * Map datasets in a Nexus file from image coordinates to Miller space
 */
public class MillerSpaceMapper {
	private static final Logger logger = LoggerFactory.getLogger(MillerSpaceMapper.class);

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
	private int begX;
	private int endX;
	private int begY;
	private int endY;
	private ForkJoinPool pool;
	private Dataset mask; // non-zero values indicate that where pixels are ignored
	private double lower = 0; // lower threshold, pixel values less than or equal to it are ignored
	private double upper = Double.POSITIVE_INFINITY; // upper threshold, pixel values greater than or equal to it are ignored

	private static final String VOLUME_NAME = "volume";
	private static final String WEIGHT_NAME = "weight";
	private static final String[] MILLER_VOLUME_AXES = new String[] { "h-axis", "k-axis", "l-axis" };
	private static final String[] Q_VOLUME_AXES = new String[] { "x-axis", "y-axis", "z-axis" };

	private static final String ENTRY = "processed";
	private static final String PROCESSED = Tree.ROOT + ENTRY;
	private static final String PROCESSPATH = PROCESSED + Node.SEPARATOR + "process";

	private static final String INDICES_NAME = "hkli_list";

	private static final String VERSION = "1.0";

	private static final int CORES;
	private static int cores;

	static {
		// Xeon E5-1630v3 has 4 cores/8 HTs
		CORES = Runtime.getRuntime().availableProcessors();
		cores = CORES;
		logger.debug("This computer has {} processors", cores);
	}

	/**
	 * Set number of cores to use
	 * @param numberOfCores must be greater than zero, otherwise it is set to the number found by the JVM
	 */
	public static void setCores(int numberOfCores) {
		if (numberOfCores <= 0) {
			logger.warn("Warning number of cores must be > 0; setting to that found by JVM");
			numberOfCores = CORES;
		}
		cores = numberOfCores;
		logger.info("Cores set to {}", cores);
	}

	/**
	 * @param bean
	 */
	public MillerSpaceMapper(MillerSpaceMapperBean bean) {
		this.bean = bean.clone();
		pool = new ForkJoinPool(cores);
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
			logger.warn("Extent of the space was found to be {} to {}", Arrays.toString(vMin), Arrays.toString(vMax));
			logger.warn("with shape = {}", Arrays.toString(vShape));
		}

		if (reduceToNonZeroBB) {
			initializeVolumeBoundingBox(vShape, sMin, sMax);
		}

		DoubleDataset map = DatasetFactory.zeros(vShape);
		DoubleDataset weight = DatasetFactory.zeros(vShape);

		createPixelMask(tree, iters[0]);

		try {
			mapToASpace(mapQ, tree, iters, map, weight);
		} catch (ScanFileHolderException sfhe) {
			throw sfhe;
		} catch (DatasetException e) {
			throw new ScanFileHolderException("Could not get data from lazy dataset", e);
		}

		Maths.dividez(map, weight, map); // normalize by tally
	
		if (reduceToNonZeroBB) {
			logger.warn("Reduced to non-zero bounding box: {} to {}", Arrays.toString(sMin), Arrays.toString(sMax));
			for (int i = 0; i < 3; i++) {
				vMin[i] += sMin[i]*vDel[i];
				sMax[i]++;
				vShape[i] = sMax[i] - sMin[i];
			}
			logger.warn("so now start = {} for shape = {}", Arrays.toString(vMin), Arrays.toString(vShape));
			map = (DoubleDataset) map.getSliceView(sMin, sMax, null);
		}
		return map;
	}

	private int mapToASpace(boolean mapQ, Tree tree, PositionIterator[] iters, DoubleDataset map, DoubleDataset weight) throws ScanFileHolderException, DatasetException {
		int[] dshape = iters[0].getShape();

		if (tree instanceof TreeFile) {
			logger.debug("Mapping {} ...", ((TreeFile) tree).getFilename());
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
		int[] ishape = new int[] {Math.max(0, endY - begY), Math.max(0, endX - begX), };
	
		if (scale != 1) {
			for (int i = 0; i < ishape.length; i++) {
				ishape[i] *= scale;
			}
		}

		return mapImages(mapQ, tree, trans, images, iters, map, weight, ishape);
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
						return VersionUtils.isOldVersion("9.15.0", version);
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
		processBean(bean.getInputs()[0], true);

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
				logger.debug("Image {}", n);
				DetectorProperties dp = NexusTreeUtils.parseDetector(detectorPath, tree, dpos)[0];
				if (n == 0) {
					initializeImageLimits(dp);
				}
				DiffractionSample sample = NexusTreeUtils.parseSample(samplePath, tree, isOldGDA, dpos);
				DiffractionCrystalEnvironment env = sample.getDiffractionCrystalEnvironment();
				QSpace qspace = new QSpace(dp, env);
				MillerSpace mspace = new MillerSpace(sample.getUnitCell(), env.getOrientation());
				printCorners(qspace, mspace);

				if (hDel != null) {
					calcVolume(hMin, hMax, qspace, mspace);
				}
			}
			n++;
		}

		if (hDel != null) {
			roundLimitsAndFindShapes();
			logger.warn("Extent of the space was found to be {} to {}", Arrays.toString(hMin), Arrays.toString(hMax));
			logger.warn("with shape = {}", Arrays.toString(hShape));
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
		logger.debug("{},{}: {}", x, y, m.toString());
	}

	/**
	 * Calculate coordinates of given pixel addresses
	 * @param mapQ
	 * @throws ScanFileHolderException
	 */
	public void calculateCoordinates(boolean mapQ) throws ScanFileHolderException {
		IntegerDataset pIdx = DatasetFactory.createFromObject(IntegerDataset.class, bean.getPixelIndexes());
		if (pIdx == null) {
			throw new IllegalArgumentException("No pixel indexes defined");
		}

		String[] inputs = bean.getInputs();
		if (inputs.length != 1) {
			throw new IllegalArgumentException("Only one input file allowed");
		}
		processBean(inputs[0], false);


		Tree tree = getTreeFromNexusFile(inputs[0]);
		PositionIterator diter = getPositionIterators(tree)[0];
		final int[] dpos = diter.getPos();
		int nf = ShapeUtils.calcSize(diter.getShape());

		int np = pIdx.getShapeRef()[0]; // Nx2 or Nx3
		int ni = pIdx.getShapeRef()[1];
		Vector3d q = new Vector3d();
		Vector3d m = new Vector3d();
		DoubleDataset coords = null;
		IntegerDataset pInput = null;

		boolean isOldGDA = isOLDI16GDA(tree);

		if (ni == 2) {
			logger.debug("Using first frame of {}", nf);

			coords = DatasetFactory.zeros(np, 3);
			pInput = DatasetFactory.zeros(IntegerDataset.class, np, 3);
			if (diter.hasNext()) {
				DetectorProperties dp = NexusTreeUtils.parseDetector(detectorPath, tree, dpos)[0];
				DiffractionSample sample = NexusTreeUtils.parseSample(samplePath, tree, isOldGDA, dpos);
				DiffractionCrystalEnvironment env = sample.getDiffractionCrystalEnvironment();
				QSpace qspace = new QSpace(dp, env);
				if (mapQ) {
					for (int i = 0; i < np; i++) {
						int r = pIdx.get(i, 0);
						int c = pIdx.get(i, 1);
						pInput.set(r, i, 1);
						pInput.set(c, i, 2);
						qspace.qFromPixelPosition(c, r, q);
						coords.set(q.getX(), i, 0);
						coords.set(q.getY(), i, 1);
						coords.set(q.getZ(), i, 2);
					}
				} else {
					MillerSpace mspace = new MillerSpace(sample.getUnitCell(), env.getOrientation());
	
					for (int i = 0; i < np; i++) {
						int r = pIdx.get(i, 0);
						int c = pIdx.get(i, 1);
						pInput.set(r, i, 1);
						pInput.set(c, i, 2);
						qspace.qFromPixelPosition(c, r, q);
						mspace.h(q, null, m);
						coords.set(m.getX(), i, 0);
						coords.set(m.getY(), i, 1);
						coords.set(m.getZ(), i, 2);
					}
				}
			}
		} else if (ni == 3) { // TODO multi-dimensional scans
			IntegerDataset allFrames = null;
			IndexIterator aiter = null;
			IntegerDataset singleFrames = null;
			IndexIterator siter = null;
			int nt = 0;
			if (np == 1) {
				IntegerDataset frames = DatasetFactory.zeros(IntegerDataset.class, 1);
				if (pIdx.get(0, 0) < 0) {
					allFrames = frames;
					aiter = allFrames.getIterator();
					nt += nf;
				} else {
					singleFrames = frames;
					siter = singleFrames.getIterator();
					nt++;
				}
			} else {
				Dataset pFrame = pIdx.getSlice((Slice) null, new Slice(1)).squeeze();
				IntegerDataset order = DatasetUtils.indexSort(pFrame, 0);
				Dataset sorted = pFrame.getByIndexes(order);
				int z = DatasetUtils.findIndexGreaterThanOrEqualTo(sorted, 0);
				if (z > 0) {
					allFrames = (IntegerDataset) order.getSliceView(new Slice(z));
					aiter = allFrames.getIterator();
					nt += nf*z;
				}
				if (z < order.getSize()) {
					singleFrames = (IntegerDataset) order.getSliceView(new Slice(z, null));
					siter = singleFrames.getIterator();
					nt += singleFrames.getSize();
				}
			}

			coords = DatasetFactory.zeros(nt, 3);
			pInput = DatasetFactory.zeros(IntegerDataset.class, nt, 3);
			int k = 0;
			int f = 0;
			boolean keepStill = false;
			while (diter.hasNext()) {
				DetectorProperties dp = NexusTreeUtils.parseDetector(detectorPath, tree, dpos)[0];
				DiffractionSample sample = NexusTreeUtils.parseSample(samplePath, tree, isOldGDA, dpos);
				DiffractionCrystalEnvironment env = sample.getDiffractionCrystalEnvironment();
				QSpace qspace = new QSpace(dp, env);
				MillerSpace mspace = mapQ ? null : new MillerSpace(sample.getUnitCell(), env.getOrientation());

				if (aiter != null) {
					while (aiter.hasNext()) {
						int i = allFrames.getAbs(aiter.index);

						int r = pIdx.get(i, 1);
						int c = pIdx.get(i, 2);
						pInput.set(f, k, 0);
						pInput.set(r, k, 1);
						pInput.set(c, k, 2);
						qspace.qFromPixelPosition(c, r, q);
						if (mapQ) {
							coords.set(q.getX(), k, 0);
							coords.set(q.getY(), k, 1);
							coords.set(q.getZ(), k, 2);
						} else {
							mspace.h(q, null, m);
							coords.set(m.getX(), k, 0);
							coords.set(m.getY(), k, 1);
							coords.set(m.getZ(), k, 2);
						}
						k++;
					}
					aiter.reset();
				}
				if (siter != null) {
					while (keepStill || siter.hasNext()) {
						int i = singleFrames.getAbs(siter.index);
						int cf = pIdx.get(i, 0);
						keepStill = cf != f; 
						if (keepStill) { // don't iterate next time
							break;
						}
						int r = pIdx.get(i, 1);
						int c = pIdx.get(i, 2);
						pInput.set(f, k, 0);
						pInput.set(r, k, 1);
						pInput.set(c, k, 2);
						qspace.qFromPixelPosition(c, r, q);
						if (mapQ) {
							coords.set(q.getX(), k, 0);
							coords.set(q.getY(), k, 1);
							coords.set(q.getZ(), k, 2);
						} else {
							mspace.h(q, null, m);
							coords.set(m.getX(), k, 0);
							coords.set(m.getY(), k, 1);
							coords.set(m.getZ(), k, 2);
						}
						k++;
					}
				}
				f++;
			}
		}

		saveCoordinates(mapQ, pInput, coords);
	}

	private void saveCoordinates(boolean mapQ, Dataset indexes, Dataset coords) throws ScanFileHolderException {
		String output = bean.getOutput();
		String coordsName = mapQ ? "q_space" : "reciprocal_space";
		String coordsPath = PROCESSED + Node.SEPARATOR + coordsName;

		indexes.setName("pixel_indexes");
		coords.setName("coordinates");

		HDF5FileFactory.deleteFile(output);
		writeNXclass(output, PROCESSED, NexusConstants.ENTRY);
		writeNXclass(output, PROCESSPATH, NexusConstants.PROCESS);
		writeDefaultAttributes(output, coordsName);
		HDF5Utils.writeDataset(output, coordsPath, indexes);
		HDF5Utils.writeDataset(output, coordsPath, coords);
		saveAxesAndAttributes(output, coordsPath, coords.getName());
		writeProcessingParameters(output, bean, coordsPath);
		linkOriginalData(output, bean.getInputs(), entryPath);
	}

	/**
	 * Block upscale an image that does not interpolate values
	 * @param m
	 * @param mshape
	 * @return upscaled image
	 */
	private Dataset upscaleMask(Dataset m, int[] mshape) {
		if (scale == 1) {
			return m;
		}
		IntegerDataset im = DatasetFactory.zeros(IntegerDataset.class, mshape);
		for (int i = 0; i < mshape[0]; i++) {
			int k = (int) Math.floor(i / scale);
			for (int j = 0; j < mshape[1]; j++) {
				int l = (int) Math.floor(j / scale);
				im.setItem(m.getInt(k, l), i, j);
			}
		}
		return im;
	}

	private int mapImages(boolean mapQ, Tree tree, Dataset trans, ILazyDataset images, PositionIterator[] iters,
			DoubleDataset map, DoubleDataset weight, int[] ishape) throws DatasetException {
		PositionIterator diter = iters[0]; // scan iterator
		PositionIterator iter = iters[1]; // data iterator (omits image axes)
		iter.reset();
		diter.reset();
		int[] dpos = diter.getPos();
		int[] pos = iter.getPos();
		int[] start = pos.clone();
		int[] stop = iter.getStop().clone();
		int rank  = pos.length;
		int srank = rank - 2;
		MillerSpace mspace = null;

		Dataset iMask = mask;
		if (iMask != null && scale != 1) {
			iMask = upscaleMask(iMask.getSliceView(new Slice(begY, endY), new Slice(begX, endX)), ishape);
//			iMask = upSampler.value(iMask).get(0);
//			if (!Arrays.equals(iMask.getShapeRef(), ishape)) {
//				String msg = String.format("Image weight shape %s does not match image shape %s", Arrays.toString(iMask.getShapeRef()), Arrays.toString(ishape));
//				throw new IllegalArgumentException(msg);
//			}
		}
		boolean isOldGDA = isOLDI16GDA(tree);
		int ni = 0;

		start[srank] = begY;
		start[srank + 1] = begX;
		stop[srank] = endY;
		stop[srank + 1] = endX;
		while (iter.hasNext() && diter.hasNext()) {
			logger.info("Mapping image at {} in {}", Arrays.toString(dpos), Arrays.toString(diter.getShape()));
			DetectorProperties dp = NexusTreeUtils.parseDetector(detectorPath, tree, dpos)[0];
			if (scale == 1) {
				dp.setStartX(dp.getStartX() - begX);
				dp.setStartY(dp.getStartY() - begY);
			} else {
				dp.setStartX(dp.getStartX() - (int) Math.floor(begX*scale));
				dp.setStartY(dp.getStartY() - (int) Math.floor(begY*scale));
				dp.setHPxSize(dp.getHPxSize() / scale);
				dp.setVPxSize(dp.getVPxSize() / scale);
				dp.setPx(ishape[1]);
				dp.setPy(ishape[0]);
			}
			for (int i = 0; i < srank; i++) {
				start[i] = pos[i];
				stop[i] = pos[i] + 1;
			}
			DiffractionSample sample = NexusTreeUtils.parseSample(samplePath, tree, isOldGDA, dpos);
			DiffractionCrystalEnvironment env = sample.getDiffractionCrystalEnvironment();
			QSpace qspace = new QSpace(dp, env);
			if (!mapQ) {
				mspace = new MillerSpace(sample.getUnitCell(), env.getOrientation());
			}
			logger.info("Slicing image start:{}, stop:{}", Arrays.toString(start), Arrays.toString(stop));
			Dataset image = DatasetUtils.convertToDataset(images.getSlice(start, stop, null));
			
			double tFactor = 1;
			if (trans != null) {
				tFactor = trans.getSize() == 1 ? trans.getElementDoubleAbs(0) : trans.getDouble(dpos); 
				if (tFactor == 0) {
					logger.warn("Exposure time or transmission was zero; no correction applied");
					tFactor = 1;
				}
			}
			int[] s = Arrays.copyOfRange(image.getShapeRef(), srank, rank);
			image.setShape(s);
			if (image.max().doubleValue() <= 0) {
				String n = tree instanceof TreeFile ? " in " + ((TreeFile) tree).getFilename() : "";
				logger.warn("Skipping image at {} {}", Arrays.toString(pos), n);
				continue;
			}
			mapImageMultiThreaded(tFactor, qspace, mspace, iMask, image, ishape, map, weight);

			ni++;
		}

		return ni;
	}

	private static void minMax(double[] min, double[] max, Vector3d v) {
		min[0] = Math.min(min[0], v.x);
		max[0] = Math.max(max[0], v.x);
		min[1] = Math.min(min[1], v.y);
		max[1] = Math.max(max[1], v.y);
		min[2] = Math.min(min[2], v.z);
		max[2] = Math.max(max[2], v.z);
	}

	private void mapImage(final double tFactor, final BicubicInterpolator upSampler, PixelSplitter splitter, int[] sMinLocal, int[] sMaxLocal, final int[] region, final QSpace qspace, Matrix3d mTransform, final Dataset mask, final Dataset image, final DoubleDataset map, final DoubleDataset weight) {
		logger.debug("Map image region: {}", Arrays.toString(region));
		final int yStart = region[2];
		final int yStop  = region[3];
		int iStart = scale == 1 ? yStart : (int) Math.round(yStart/scale);
		int iStop  = scale == 1 ? yStart : (int) Math.round(yStop/scale);
		int yOff = 0;
		if (scale != 1) {
			// interpolator at boundaries gives different values
			int[] iShape = image.getShapeRef();
			upSampler.setInputShape(iStop - iStart, iShape[1]);
			if (iStart > 1) {
				iStart = iStart - 1;
				yOff = (int) scale;
			}
			iStop = Math.min(iStop + 4, iShape[0]);
		}
		Dataset iView = image.getSliceView(new Slice(iStart, iStop));
		Dataset mView = mask == null ? null : mask.getSliceView(new Slice(yStart, yStop));
		final int ySize = yStop - yStart;

		final int[] pos = new int[3]; // voxel position
		final Vector3d v = new Vector3d();
		final Vector3d dv = new Vector3d(); // delta in h

		final int xStart = region[0];
		final int xStop  = region[1];
		for (int y = 0; y < ySize; y++) {
			int py = y + yStart; // on detector (rather than sliced dataset)
			for (int x = xStart; x < xStop; x++) {
				if (mView != null) {
					if (mView.getBoolean(y, x)) {
						continue;
					}
				}

				double value = upSampler == null ? iView.getDouble(y, x) : upSampler.interpolate(y + yOff, x, iView);
				if (!Double.isFinite(value) || value <= lower || value >= upper) {
					continue;
				}

				if (value > 0) {
					qspace.qFromPixelPosition(x + 0.5, py + 0.5, v);

					if (mTransform != null) {
						mTransform.transform(v);
					}

					if (convertToVoxel(v, dv, pos)) {
						value *= tFactor/qspace.calculateSolidAngle(x, py);
						if (reduceToNonZeroBB) {
							minMax(sMinLocal, sMaxLocal, pos);
						}
						splitter.splitValue(map, weight, vDel, dv, pos, value);
					}
				}
			}
		}
	}

	private static final int MIN_CHUNK = 128;

	private boolean chunkSizeTooSmall(final int chunk, final int rows, final QSpace qspace,
			final Matrix3d mTransform) {

		if (chunk < MIN_CHUNK) {
			logger.debug("Chunk smaller than min: {} < {}", chunk, MIN_CHUNK);
			return true;
		}

		final Vector3d v = new Vector3d();
		Vector3d ov = null;
		double xCoord = 0.5;
		double[] voxelSides = mTransform == null ? qDel : hDel;
		for (int y = 0; y < rows; y += chunk) {
			qspace.qFromPixelPosition(xCoord, y + 0.5, v);
			if (mTransform != null) {
				mTransform.transform(v);
			}

			if (ov == null) {
				ov = new Vector3d(v);
			} else { // check if pixels from adjoining bands map to same voxel
				if (Math.abs(v.x - ov.x) < voxelSides[0] && Math.abs(v.y - ov.y) < voxelSides[1]
						&& Math.abs(v.z - ov.z) < voxelSides[2]) {
					logger.debug("Chunk is small enough for voxel contention");
					return true;
				}
				ov.set(v);
			}
		}

		return false;
	}

	private void mapImageMultiThreaded(final double tFactor, final QSpace qspace, final MillerSpace mspace,
			final Dataset iMask, final Dataset image, final int[] ishape, final DoubleDataset map, final DoubleDataset weight) {
		final Matrix3d mTransform = mspace == null ? null : mspace.getMillerTransform();
		int size = pool.getParallelism();
		if (size == 1) {
			BicubicInterpolator upSampler = scale == 1 ? null : new BicubicInterpolator(ishape);
			int[] regionSlice = new int[] { 0, ishape[1], 0, ishape[0] };
			mapImage(tFactor, upSampler, splitter, sMin, sMax, regionSlice, qspace, mTransform, iMask, image, map, weight);
			return;
		}
		final int rows = ishape[0];
		double regionSize = rows;

		int chunk = (int) Math.ceil(regionSize / size);
		while (size > 1 && chunkSizeTooSmall(chunk, rows, qspace, mTransform)) {
			chunk = (int) Math.ceil(regionSize / --size);
			logger.warn("Chunk too small so decrementing parallelism to {}", size);
		}
		logger.info("Splitting images to bands of {} rows across {} threads", chunk, size);

		final List<JobConfig> jobs = new ArrayList<>(size);
		int[] vshape = copyParameters(mspace == null);
		chunk = (int) (Math.ceil(chunk/scale)*scale); // round up to be divisible by scale
		for (int i = 0; i < size; i++) {
			int s = i * chunk;
			int e = Math.min(s + chunk, rows);
			logger.info("Chunk {}: {} -> {}", i, s, e);
			BicubicInterpolator upSampler = scale == 1 ? null : new BicubicInterpolator(new int[] {e - s, ishape[1]});
			jobs.add(new JobConfig(upSampler, splitter.clone(), s, e, vshape));
		}

		Consumer<JobConfig> subTask = job -> {
			int[] sMinLocal = job.getSMinLocal();
			int[] sMaxLocal = job.getSMaxLocal();
			int[] regionSlice = new int[] { 0, ishape[1], job.getStart(), job.getEnd() };

			mapImage(tFactor, job.getUpScaler(), job.getSplitter(), sMinLocal, sMaxLocal, regionSlice, qspace, mTransform, iMask, image, map, weight);
		};

		try {
			pool.submit(() -> jobs.parallelStream().forEach(subTask)).get();
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException("Multithreaded jobs did not finish successfully", e);
		}

		if (reduceToNonZeroBB) {
			for (JobConfig j : jobs) {
				minMax(sMin, sMax, j.getSMinLocal());
				minMax(sMin, sMax, j.getSMaxLocal());
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
				logger.warn("Skipping image at {}", Arrays.toString(pos));
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

	/**
	 * @param file
	 * @param complete if true, get axes and set fields completely
	 * @return q space and miller space axes
	 * @throws ScanFileHolderException
	 */
	private Dataset[][] processBean(String file, boolean complete) throws ScanFileHolderException {
		Tree tree = getTreeFromNexusFile(file);
		NodeLink link;

		entryPath = bean.getEntryPath();
		if (entryPath == null || entryPath.isEmpty()) {
			link = NexusTreeUtils.findFirstNode(tree.getGroupNode(), NexusConstants.ENTRY);
			logger.trace("{} found: {}", NexusConstants.ENTRY, link);
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
			logger.trace("{} found: {}", NexusConstants.INSTRUMENT, link);
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
			logger.trace("{} found: {}", NexusConstants.DETECTOR, link);
		} else {
			link = instrument.getNodeLink(detectorName);
		}
		if (link == null || !link.isDestinationGroup()) {
			throw new ScanFileHolderException("Could not find detector");
		}
		GroupNode detector = (GroupNode) link.getDestination();
		detectorPath = TreeUtils.getPath(tree, detector);

		String sampleName = bean.getSampleName();
		if (sampleName == null || sampleName.isEmpty()) {
			link = NexusTreeUtils.findFirstNode(entry, NexusConstants.SAMPLE);
			logger.trace("{} found: {}", NexusConstants.SAMPLE, link);
		} else {
			link = entry.getNodeLink(sampleName);
		}
		if (link == null || !link.isDestinationGroup()) {
			throw new ScanFileHolderException("Could not find sample");
		}
		samplePath = TreeUtils.getPath(tree, link.getDestination());

		String dataName = bean.getDataName();
		if (dataName == null || dataName.isEmpty()) {
			link = detector.getNodeLink(NexusConstants.DATA_DATA);
			if (link == null) {
				link = NexusTreeUtils.findFirstSignalDataNode(detector);
			}
			logger.trace("{} found: {}", NexusConstants.DATA_DATA, link);
		} else {
			link = detector.getNodeLink(dataName);
		}

		if (link == null) {
			logger.warn("Missing image data in {} - synthesizing it", detectorPath);
			link = synthesizeMissingImageDataForI16(file, entry, detector);
		}

		if (link == null || !link.isDestinationData()) {
			throw new ScanFileHolderException("Could not find image data");
		}
		dataPath = TreeUtils.getPath(tree, link.getDestination());

		if (!complete) {
			return null;
		}

		timePath = detectorPath + "count_time";

		String attenuatorName = bean.getAttenuatorName();
		if (attenuatorName == null || attenuatorName.isEmpty()) {
			link = NexusTreeUtils.findFirstNode(instrument, NexusConstants.ATTENUATOR);
			logger.trace("{} found: {}", NexusConstants.ATTENUATOR, link);
		} else {
			link = instrument.getNodeLink(attenuatorName);
		}
		if (link == null || !link.isDestinationGroup()) {
			attenuatorPath = null;
		} else {
			attenuatorPath = TreeUtils.getPath(tree, link.getDestination());
		}
		if (attenuatorPath == null) {
			logger.warn("Could not find attenuator");
		}

		this.splitter = PixelSplitter.createSplitter(bean.getSplitterName(), bean.getSplitterParameter());
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
			logger.warn("Warning: just using first of candidate directories: {}", Arrays.toString(dirs));
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
	 * @return Datasets
	 * @throws ScanFileHolderException
	 */
	public Dataset[][] mapToVolumeFile() throws ScanFileHolderException {
		return mapToVolumeFile(false);
	}

	/**
	 * Map images from given Nexus files to a volume in Miller (aka HKL) space and save to a HDF5 file
	 * 
	 * @param isErrorTest
	 * @return Datasets
	 * @throws ScanFileHolderException
	 */
	Dataset[][] mapToVolumeFile(boolean isErrorTest) throws ScanFileHolderException {
		hasDeleted = false; // reset state
		Dataset[] datasetA = null;
		Dataset[] datasetB = null;

		String[] inputs = bean.getInputs();
		Dataset[][] a = processBean(inputs[0], true);
		if (qDel == null && hDel == null && !listMillerEntries) {
			throw new IllegalStateException("Both q space and Miller space parameters have not been defined");
		}

		int n = inputs.length;
		Tree[] trees = new Tree[n];
		PositionIterator[][] allIters = new PositionIterator[n][];
		for (int i = 0; i < n; i++) {
			trees[i] = getTreeFromNexusFile(inputs[i]);
			allIters[i] = getPositionIterators(trees[i]);
		}

		createPixelMask(trees[0], allIters[0][0]);

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
				logger.warn("Extent of q space was found to be {} to {}", Arrays.toString(qMin), Arrays.toString(qMax));
				logger.warn("with shape = {}", Arrays.toString(qShape));
			}
			if (hDel != null) {
				logger.warn("Extent of Miller space was found to be {} to {}", Arrays.toString(hMin), Arrays.toString(hMax));
				logger.warn("with shape = {}", Arrays.toString(hShape));
			}
		}

		try {
			if (qDel != null) {
				datasetA = processTrees(true, trees, allIters, a[0], isErrorTest);
			}

			if (hDel != null) {
				datasetB = processTrees(false, trees, allIters, a[1], isErrorTest);
			}

			if (listMillerEntries) {
				processTreesForList(trees, allIters);
			}
		} catch (ScanFileHolderException sfhe) {
			throw sfhe;
		} catch (DatasetException e) {
			throw new ScanFileHolderException("Could not get data from lazy dataset", e);
		}

		return new Dataset[][] {datasetA, datasetB};
	}

	private void createPixelMask(Tree tree, PositionIterator dIter) {
		int[] dpos = new int[dIter.getPos().length]; // use detector from first scan position
		DetectorProperties dp = NexusTreeUtils.parseDetector(detectorPath, tree, dpos)[0];
		mask = dp.getMask();
		lower = dp.getLowerThreshold();
		upper = dp.getUpperThreshold();

		String mFile = bean.getMaskFilePath();
		if (mFile != null) {
			Dataset tMask = null;
			try {
				tMask = DatasetUtils.convertToDataset(LoaderFactory.getData(mFile).getDataset(0));
			} catch (Exception e) {
				logger.warn("Could not load mask dataset from {}", mFile);
			}

			if (tMask != null) {
				int[] shape = new int[] {dp.getPy(), dp.getPx()};
				if (tMask.getShapeRef()[1] == 2) { // treat as list of positions in mask to set true
					Dataset posn = DatasetUtils.createCompoundDatasetFromLastAxis(tMask, true);
					CoordinatesIterator it = CoordinatesIterator.createIterator(posn, true);
					int[] pos = it.getPosition();
					mask = DatasetFactory.zeros(BooleanDataset.class, shape);
					try {
						while (it.hasNext()) {
							mask.set(true, pos);
						}
					} catch (IllegalArgumentException e) {
						logger.warn("Mask coordinates outside image bounds");
					}
				} else if (Arrays.equals(shape, tMask.getShapeRef())){
					mask = tMask;
				} else {
					logger.warn("Loaded mask shape does not match image shape");
				}
			}
		}
		initializeImageLimits(dp);
	}

	private void initializeImageLimits(DetectorProperties dp) {
		begX = dp.getStartX();
		endX = begX + dp.getPx();
		begY = dp.getStartY();
		endY = begY + dp.getPy();

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

	private Dataset[] processTrees(boolean mapQ, Tree[] trees, PositionIterator[][] allIters, Dataset[] a,
			boolean isErrorTest) throws ScanFileHolderException, DatasetException {
		long start = System.currentTimeMillis();
		int[] vShape = copyParameters(mapQ);
		String output = bean.getOutput();

		if (reduceToNonZeroBB) {
			initializeVolumeBoundingBox(vShape, sMin, sMax);
		}

		String volName = mapQ ? "q_space" : "reciprocal_space";
		String volPath = PROCESSED + Node.SEPARATOR + volName;

		try {
			DoubleDataset map = DatasetFactory.zeros(vShape);
			DoubleDataset weight = DatasetFactory.zeros(vShape);

			int nt = 0;
			for (int i = 0; i < trees.length; i++) {
				logger.info("Processing file {}/{}: {}", i+1, trees.length, bean.getInputs()[i]);
				Tree tree = trees[i];
				int ni = mapToASpace(mapQ, tree, allIters[i], map, weight);
				nt += ni;
				logger.info("    processed {} images", ni);
			}
			Maths.dividez(map, weight, map); // normalize by tally

			if (reduceToNonZeroBB) {
				logger.warn("Reduced to non-zero bounding box: {} to {}", Arrays.toString(sMin), Arrays.toString(sMax));
				for (int i = 0; i < 3; i++) {
					vMin[i] += sMin[i]*vDel[i];
					sMax[i]++;
					vShape[i] = sMax[i] - sMin[i];
				}
				logger.warn("so now start = {} for shape = {}", Arrays.toString(vMin), Arrays.toString(vShape));
				map = (DoubleDataset) map.getSliceView(sMin, sMax, null);
				weight = (DoubleDataset) weight.getSliceView(sMin, sMax, null);
			}

			if (findImageBB) {
				if (mapQ) {
					createQSpaceAxes(a, vShape, vMin, null, vDel);
				} else {
					createMillerSpaceAxes(a, vShape, vMin, null, vDel);
				}
			}
			long process = System.currentTimeMillis() - start;
			logger.info("For {} threads, processing took {}ms ({}ms/frame)", pool.getParallelism(), process, process/nt);
			start = System.currentTimeMillis();

			if (isErrorTest) {
				throw new OutOfMemoryError();
			}

			boolean isFileNew = !hasDeleted;
			if (isFileNew) {
				HDF5FileFactory.deleteFile(output);
				hasDeleted = true;
			}

			if (isFileNew) {
				writeNXclass(output, PROCESSED, NexusConstants.ENTRY);
				writeNXclass(output, PROCESSPATH, NexusConstants.PROCESS);
				writeDefaultAttributes(output, volName);
			}

			saveVolume(output, bean, entryPath, volPath, map, weight, a);
			logger.info("Saving took {}ms", System.currentTimeMillis() - start);
		} catch (IllegalArgumentException | OutOfMemoryError e) {
			logger.warn("There is not enough memory to do this all at once!");
			logger.warn("Now attempting to segment volume");
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
				logger.error("Cannot segment volume fine enough to fit in memory!", e);
				throw e;
			}
			logger.info("Mapping in {} parts", parts);
			boolean isFileNew = !hasDeleted;
			if (isFileNew) {
				HDF5FileFactory.deleteFile(output);
				hasDeleted = true;
			}

			int[] cShape = new int[] { Math.min(vShape[0], 64), Math.min(vShape[1], 64), Math.min(vShape[2], 64) };

			LazyWriteableDataset lazyVolume = HDF5Utils.createLazyDataset(output, volPath, VOLUME_NAME, vShape, null,
					cShape, DoubleDataset.class, null, false);
			LazyWriteableDataset lazyWeight = HDF5Utils.createLazyDataset(output, volPath, WEIGHT_NAME, vShape, null,
					cShape, DoubleDataset.class, null, false);

			if (isFileNew) {
				writeNXclass(output, PROCESSED, NexusConstants.ENTRY);
				writeNXclass(output, PROCESSPATH, NexusConstants.PROCESS);
				writeDefaultAttributes(output, volName);
			}

			mapAndSaveInParts(mapQ, trees, allIters, lazyVolume, lazyWeight, parts, map, weight);

			saveAxesAndAttributes(output, volPath, VOLUME_NAME, a);
			writeProcessingParameters(output, bean, entryPath);
			linkOriginalData(output, bean.getInputs(), entryPath);
		}
		return a;
	}

	private static void writeDefaultAttributes(String file, String volName) throws ScanFileHolderException {
		createAndWriteAttribute(file, Tree.ROOT, NexusConstants.DEFAULT, ENTRY);
		createAndWriteAttribute(file, PROCESSED, NexusConstants.DEFAULT, volName);
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
			if (mShape[i] == 1) {
				logger.trace("Axis {}: {}; {}", i, mbeg, mend);
			} else {
				logger.trace("Axis {}: {} -> {}; {}", i, mbeg, a[i].getDouble(mShape[i] - 1), mend);
			}
		}
	}

	private PositionIterator[] getPositionIterators(Tree tree) throws ScanFileHolderException {
		int[] dshape = NexusTreeUtils.parseDetectorScanShape(detectorPath, tree);
		if (dshape == null) {
			throw new IllegalArgumentException("Could not parse detector scan shape from tree");
		}
//		logger.trace(Arrays.toString(dshape));

		dshape = NexusTreeUtils.parseSampleScanShape(samplePath, tree, dshape);
		if (dshape == null) {
			throw new IllegalArgumentException("Could not parse sample scan shape from tree");
		}
//		logger.trace(Arrays.toString(dshape));

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

		return new PositionIterator[] {diter, iter}; // scan iterator, image data iterator (ignores frame dimensions)
	}

	/**
	 * Save volume, weight and its axes to a HDF5 file
	 * @param file path for saving HDF5 file
	 * @param bean
	 * @param entryPath path to NXentry
	 * @param volPath name for NXdata
	 * @param v volume Dataset
	 * @param w weight Dataset
	 * @param axes axes Datasets
	 * @throws ScanFileHolderException
	 */
	public static void saveVolume(String file, MillerSpaceMapperBean bean, String entryPath, String volPath, Dataset v,
			Dataset w, Dataset... axes) throws ScanFileHolderException {

		v.setName(VOLUME_NAME);
		w.setName(WEIGHT_NAME);
		HDF5Utils.writeDataset(file, volPath, v);
		HDF5Utils.writeDataset(file, volPath, w);
		saveAxesAndAttributes(file, volPath, VOLUME_NAME, axes);
		writeProcessingParameters(file, bean, entryPath);
		linkOriginalData(file, bean.getInputs(), entryPath);
	}

	static void writeProcessingParameters(String file, MillerSpaceMapperBean bean, String entryPath)
			throws ScanFileHolderException {
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
		} catch (JsonProcessingException e) {
			throw new ScanFileHolderException("Could not process JSON file", e);
		}
	}

	static void linkOriginalData(String file, String[] inputs, String entryPath)
			throws ScanFileHolderException {
		Path fileParent = Paths.get(file).toAbsolutePath().getParent();
		for (int i = 0; i < inputs.length; i++) {
			String destination = String.format("/entry%d", i);
			try {
				String relativePath = fileParent.relativize(Paths.get(inputs[i]).toAbsolutePath()).toString();
				HDF5Utils.createExternalLink(file, destination, relativePath, entryPath);
			} catch (IllegalArgumentException e) {
				logger.warn("Could not create relative path between: {} and {}", file, inputs[i], e);
				HDF5Utils.createExternalLink(file, destination, inputs[i], entryPath);
			}
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
	private static void createAndWriteAttribute(String file, String path, String attributeName, String attribute)
			throws ScanFileHolderException {
		Dataset x = DatasetFactory.createFromObject(attribute);
		x.setName(attributeName);
		HDF5Utils.writeAttributes(file, path, true, x);
	}

	/**
	 * Write an NXclass attribute
	 * 
	 * @param file
	 * @param path
	 * @param nxClass
	 * @throws ScanFileHolderException
	 */
	private static void writeNXclass(String file, String path, String nxClass) throws ScanFileHolderException {
		createAndWriteAttribute(file, path, NexusConstants.NXCLASS, nxClass);
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

	public static void saveAxesAndAttributes(String file, String dataPath, String dataName, Dataset... axes)
			throws ScanFileHolderException {
		String[] axisNames = new String[axes.length];
		for (int i = 0; i < axes.length; i++) {
			Dataset x = axes[i];
			axisNames[i] = x.getName();
			HDF5Utils.writeDataset(file, dataPath, x);
		}

		List<Dataset> attrs = new ArrayList<>();
		Dataset a;

		a = DatasetFactory.createFromObject(NexusConstants.DATA);
		a.setName(NexusConstants.NXCLASS);
		attrs.add(a);

		a = DatasetFactory.createFromObject(dataName);
		a.setName(NexusConstants.DATA_SIGNAL);
		attrs.add(a);

		if (axisNames.length > 0) {
			a = DatasetFactory.createFromObject(axisNames);
			a.setName(NexusConstants.DATA_AXES);
			attrs.add(a);
	
			for (int i = 0; i < axisNames.length; i++) {
				a = DatasetFactory.createFromObject(i);
				a.setName(axisNames[i] + NexusConstants.DATA_INDICES_SUFFIX);
				attrs.add(a);
			}
		}

		HDF5Utils.writeAttributes(file, dataPath, true, attrs.toArray(new Dataset[attrs.size()]));
	}

	/**
	 * Map images from given Nexus file to a volume in Miller (aka HKL) space
	 * 
	 * @param mapQ
	 * @param trees
	 * @param allIters
	 * @param volumeOutput
	 * @param weightOutput
	 * @param parts
	 * @param map
	 * @param weight
	 * @throws ScanFileHolderException
	 * @throws DatasetException
	 */
	private void mapAndSaveInParts(boolean mapQ, Tree[] trees, PositionIterator[][] allIters,
			LazyWriteableDataset volumeOutput, LazyWriteableDataset weightOutput, int parts, DoubleDataset map,
			DoubleDataset weight) throws ScanFileHolderException, DatasetException {
		int n = trees.length;

		SliceND slice = new SliceND(hShape, null, map.getShapeRef(), null);
		int ml = map.getShapeRef()[0]; // length of first dimension
		int[] vstart = slice.getStart();
		int[] vstop = slice.getStop();
		double oMin = vMin[0];
		double oMax = vMax[0];

		long process = 0;
		long save = 0;
		long start = -1;
		long now = -1;
		Tree tree;
		PositionIterator[] iters;
		int nt = 0;
		start = System.currentTimeMillis();
		for (int p = 0; p < (parts-1); p++) {
			logger.info("Mapping in part: {}/{}", p+1, parts);
			// shift min
			vMin[0] = oMin + vstart[0] * vDel[0];
			vMax[0] = vMin[0] + ml * vDel[0];

			for (int t = 0; t < n; t++) {
				logger.info("    Processing file {}/{}: {}", t + 1, n, bean.getInputs()[t]);
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

				int[] ishape = new int[] {Math.max(0, endY - begY), Math.max(0, endX - begX), };
				if (scale != 1) {
					for (int i = 0; i < ishape.length; i++) {
						ishape[i] *= scale;
					}
				}

				int ni = mapImages(mapQ, tree, trans, images, iters, map, weight, ishape);
				nt += ni;
				logger.info("        processed {} images", ni);
			}
			Maths.dividez(map, weight, map); // normalize by tally
			now = System.currentTimeMillis();
			process += now - start;

			start = now;
			try {
				volumeOutput.setSlice(map, slice);
				weightOutput.setSlice(weight, slice);
			} catch (DatasetException e) {
				logger.error("Could not save part of volume", e);
				throw new ScanFileHolderException("Could not save part of volume", e);
			}
			now = System.currentTimeMillis();
			save += now - start;

			start = now;
			map.fill(0);
			weight.fill(0);
			vstart[0] = vstop[0];
			vstop[0] = vstart[0] + ml;
		}

		// last part
		logger.info("Mapping in last part: {}", parts);
		vMin[0] = oMin + vstart[0] * vDel[0];
		vMax[0] = oMax;
		int vl = hShape[0];
		boolean overflow = vstop[0] > vl;
		if (overflow) {
			vstop[0] = vl;
		}

		for (int t = 0; t < n; t++) {
			logger.info("    Processing file {}/{}: {}", t + 1, n, bean.getInputs()[t]);
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
			if (scale != 1) {
				for (int i = 0; i < ishape.length; i++) {
					ishape[i] *= scale;
				}
			}

			int ni = mapImages(mapQ, tree, trans, images, iters, map, weight, ishape);
			nt += ni;
			logger.info("        processed {} images", ni);
		}
		Maths.dividez(map, weight, map); // normalize by tally
		now = System.currentTimeMillis();
		process += now - start;

		start = now;
		DoubleDataset tmap;
		DoubleDataset tweight;
		if (overflow) {
			int[] tstop = map.getShape();
			tstop[0] = vstop[0] - vstart[0];
			tmap = (DoubleDataset) map.getSliceView(null, tstop, null);
			tweight = (DoubleDataset) weight.getSliceView(null, tstop, null);
			slice.getShape()[0] = tstop[0];
		} else {
			tmap = map;
			tweight = weight;
		}
		try {
			volumeOutput.setSlice(tmap, slice);
			weightOutput.setSlice(tweight, slice);
		} catch (Exception e) {
			logger.error("Could not save last part of volume", e);
			throw new ScanFileHolderException("Could not save last part of volume", e);
		}
		save += System.currentTimeMillis() - start;

		logger.info("For {} threads, processing took {}ms ({}ms/frame)", pool.getParallelism(), process, process/nt);
		logger.info("Saving took {}ms", save);
	}

	private static final String INDICES = "indices";
	private static final String millerIndicesPath = PROCESSED + Node.SEPARATOR + INDICES;

	private void processTreesForList(Tree[] trees, PositionIterator[][] allIters)
			throws ScanFileHolderException, DatasetException {
		String output = bean.getOutput();
		boolean isFileNew = !hasDeleted;
		if (isFileNew) {
			HDF5FileFactory.deleteFile(output);
			hasDeleted = true;
		}

		// Each pixel => HKL (3 doubles) plus corrected intensity (1 double)
		LazyWriteableDataset lazy = HDF5Utils.createLazyDataset(output, millerIndicesPath, INDICES_NAME, new int[] {0,4}, new int[] {-1,4}, new int[] {1024, 4}, DoubleDataset.class, null, false);

		if (isFileNew) {
			writeNXclass(output, PROCESSED, NexusConstants.ENTRY);
			writeNXclass(output, PROCESSPATH, NexusConstants.PROCESS);
			writeNXclass(output, millerIndicesPath, NexusConstants.DATA);
			writeDefaultAttributes(output, INDICES);
		}

		for (int i = 0; i < trees.length; i++) {
			Tree tree = trees[i];
			listToASpace(tree, allIters[i], lazy);
		}
		writeProcessingParameters(output, bean, entryPath);
		linkOriginalData(output, bean.getInputs(), entryPath);
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
		MillerSpaceMapperBean iBean = MillerSpaceMapperBean.createBean(inputs, output, splitter, p, scale, mShape, mStart, mDelta, null, null, null);
		MillerSpaceMapper mapper = new MillerSpaceMapper(iBean);
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
		MillerSpaceMapperBean iBean = MillerSpaceMapperBean.createBean(inputs, output, splitter, p, scale, mShape, mStart, mDelta, qShape, qStart, qDelta);
		MillerSpaceMapper mapper = new MillerSpaceMapper(iBean);
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
		processVolumeWithAutoBox(inputs, output, splitter, p, scale, reduceToNonZero, false, mDelta);
	}
	
	/**
	 * Process Nexus files for I16 with automatic bounding box setting
	 * @param inputs Nexus files
	 * @param output name of HDF5 file to be created
	 * @param splitter name of pixel splitting algorithm. Can be "gaussian", "inverse", or null, "", or "nearest" for the default.
	 * @param p splitter parameter
	 * @param scale upsampling factor
	 * @param reduceToNonZero if true, reduce output to sub-volume with non-zero data
	 * @param isErrorTest if true, will throw outOfMemoryError to test mapAndSaveInParts
	 * @param mDelta sides of voxels in Miller space
	 * @throws ScanFileHolderException
	 */
	private static void processVolumeWithAutoBox(String[] inputs, String output, String splitter, double p, double scale, boolean reduceToNonZero, boolean isErrorTest, double... mDelta) throws ScanFileHolderException {
		MillerSpaceMapperBean iBean = MillerSpaceMapperBean.createBeanWithAutoBox(inputs, output, splitter, p, scale, reduceToNonZero, mDelta, null);
		MillerSpaceMapper mapper = new MillerSpaceMapper(iBean);
		mapper.mapToVolumeFile(isErrorTest);
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
		MillerSpaceMapperBean iBean = MillerSpaceMapperBean.createBeanWithAutoBox(inputs, output, splitter, p, scale, reduceToNonZero, null, qDelta);
		MillerSpaceMapper mapper = new MillerSpaceMapper(iBean);
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
		MillerSpaceMapperBean iBean = MillerSpaceMapperBean.createBeanWithAutoBox(inputs, output, splitter, p, scale, reduceToNonZero, mDelta, qDelta);
		MillerSpaceMapper mapper = new MillerSpaceMapper(iBean);
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
		MillerSpaceMapperBean iBean = MillerSpaceMapperBean.createBeanWithList(inputs, output, scale);
		MillerSpaceMapper mapper = new MillerSpaceMapper(iBean);
		mapper.mapToVolumeFile();
	}

	/**
	 * Print where corners of all images are in Miller (aka HKL) space
	 * @param input Nexus file
	 * @param endsOnly if true, only print from first and last images 
	 * @throws ScanFileHolderException
	 */
	public static void printCorners(String input, boolean endsOnly) throws ScanFileHolderException {
		MillerSpaceMapperBean iBean = new MillerSpaceMapperBean();
		iBean.setInputs(input);
		MillerSpaceMapper mapper = new MillerSpaceMapper(iBean);
		mapper.printMillerSpaceCorners(endsOnly);
	}

	class JobConfig {
		private BicubicInterpolator upScaler;
		private PixelSplitter pSplitter;
		private int start;
		private int end;
		private Dataset data;
		private int[] sMinLocal;
		private int[] sMaxLocal;

		public JobConfig(BicubicInterpolator upScaler, PixelSplitter splitter, int start, int end, int[] vshape) {
			this.upScaler = upScaler;
			this.pSplitter = splitter;
			this.start = start;
			this.end = end;
			this.sMinLocal = new int[vshape.length];
			this.sMaxLocal = new int[vshape.length];
			initializeVolumeBoundingBox(vshape, sMinLocal, sMaxLocal);
		}

		public BicubicInterpolator getUpScaler() {
			return upScaler;
		}

		public PixelSplitter getSplitter() {
			return pSplitter;
		}

		public Dataset getData() {
			return data;
		}

		public int getStart() {
			return start;
		}

		public int getEnd() {
			return end;
		}

		public int[] getSMinLocal() {
			return sMinLocal;
		}

		public int[] getSMaxLocal() {
			return sMaxLocal;
		}
	}
}

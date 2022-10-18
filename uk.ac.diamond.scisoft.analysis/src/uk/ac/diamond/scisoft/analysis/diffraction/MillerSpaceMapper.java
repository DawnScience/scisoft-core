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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;

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

import uk.ac.diamond.daq.util.logging.deprecation.DeprecationLogger;
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
	private static final DeprecationLogger logger = DeprecationLogger.getLogger(MillerSpaceMapper.class);

	private String detectorPath;
	private String timePath; // path to exposure dataset
	private String dataPath;
	private String samplePath;
	private String attenuatorPath;
	private String monitorPath;
	private MillerSpaceMapperBean bean;

	private int[] vShape;

	private boolean findResultBB; // find bounding box for result dataset
	private boolean reduceToNonZeroBB; // reduce data non-zero only
	private int[] sMin; // output dataset shape min and max
	private int[] sMax;
	private double scale; // image upsampling factor

	private PixelSplitter splitter;
	private ImagePixelMapping pixelMapping;

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
	private ExecutorService pool;
	private int poolSize;
	private Dataset mask; // non-zero values indicate that where pixels are ignored
	private double lower = 0; // lower threshold, pixel values less than or equal to it are ignored
	private double upper = Double.POSITIVE_INFINITY; // upper threshold, pixel values greater than or equal to it are ignored

	private boolean isQSpace;
	private long loadTimeTotal;

	private Slice imagesSlice;

	private boolean warnExposureZero;
	private boolean correctPolarization;
	private boolean isI16;

	private static String getOutputName(int rank) {
		switch (rank) {
		case 1:
			return "line";
		case 2:
			return "area";
		case 3:
			return "volume";
		default:
			return "data";
		}
	}

	private static final String WEIGHT_NAME = "weight";
	private static String getSpaceName(boolean isQ) {
		return isQ ? "q_space" : "reciprocal_space";
	}

	private static final String APP_DEF_MX = "NXmx";
	private static final String ENTRY = "processed";
	private static final String PROCESSED = Tree.ROOT + ENTRY;
	private static final String PROCESSPATH = TreeUtils.join(PROCESSED, "process");

	private static final String INDICES_NAME = "hkli_list";

	private static final String VERSION = "1.6";

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
		poolSize = cores;
		pool = Executors.newFixedThreadPool(poolSize);
	}

	/**
	 * Set volume bounding box parameters
	 * @param mShape shape of output
	 * @param mStart starting coordinates of volume
	 * @param mStop end coordinates
	 * @param mDelta lengths of voxel sides
	 */
	public void setBoundingBox(int[] mShape, double[] mStart, double[] mStop, double[] mDelta) {
		vShape = mShape;
		vMin = mStart;
		vMax = mStop;
		vDel = mDelta;
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
		if (vDel == null) {
			throw new IllegalStateException("Miller space parameters have not been defined");
		}
		return mapToAVolume(false, filePath);
	}

	/**
	 * Map images from given Nexus file to a volume in q space
	 * @param filePath path to Nexus file
	 * @return dataset
	 * @throws ScanFileHolderException
	 */
	public Dataset mapToQSpace(String filePath) throws ScanFileHolderException {
		if (vDel == null) {
			throw new IllegalStateException("q space parameters have not been defined");
		}
		return mapToAVolume(true, filePath);
	}

	private Tree getTreeFromNexusFile(String file) {
		try {
			return LoaderFactory.getData(NexusHDF5Loader.class, file, true, null).getTree();
		} catch (Exception e) {
			throw new IllegalArgumentException("No tree loaded from file: " + file, e);
		}
	}

	private static void initializeOutputBoundingBox(int[] outShape, int[] min, int[] max) {
		System.arraycopy(outShape, 0, min, 0, outShape.length);
		Arrays.fill(max, -1);
	}

	/**
	 * Map images from given Nexus file to a volume
	 * @param mapQ
	 * @param filePath path to Nexus file
	 * @return dataset
	 * @throws ScanFileHolderException
	 */
	private Dataset mapToAVolume(boolean mapQ, String filePath) throws ScanFileHolderException {
		Tree tree = getTreeFromNexusFile(filePath);
		PositionIterator[] iters = getPositionIterators(tree);
	
		if (findResultBB) {
			Arrays.fill(vMin, Double.POSITIVE_INFINITY);
			Arrays.fill(vMax, Double.NEGATIVE_INFINITY);

			findBoundingBoxes(tree, iters);
			roundLimitsAndFindShapes();
			logger.warn("Extent of the space was found to be {} to {}", Arrays.toString(vMin), Arrays.toString(vMax));
			logger.warn("with shape = {}", Arrays.toString(vShape));
		}

		if (reduceToNonZeroBB) {
			initializeOutputBoundingBox(vShape, sMin, sMax);
		}

		DoubleDataset map = DatasetFactory.zeros(vShape);
		DoubleDataset weight = DatasetFactory.zeros(vShape);
		splitter.setDatasets(map, weight);

		createPixelMask(tree, iters[0]);
		try {
			mapToOutput(tree, iters);
		} catch (ScanFileHolderException sfhe) {
			throw sfhe;
		} catch (DatasetException e) {
			throw new ScanFileHolderException("Could not get data from lazy dataset", e);
		}

		Maths.dividez(map, weight, map); // normalize by tally
	
		if (reduceToNonZeroBB) {
			logger.warn("Reduced to non-zero bounding box: {} to {}", Arrays.toString(sMin), Arrays.toString(sMax));
			for (int i = 0; i < vShape.length; i++) {
				vMin[i] += sMin[i]*vDel[i];
				sMax[i]++;
				vShape[i] = sMax[i] - sMin[i];
			}
			logger.warn("so now start = {} for shape = {}", Arrays.toString(vMin), Arrays.toString(vShape));
			map = (DoubleDataset) map.getSliceView(sMin, sMax, null);
		}
		return map;
	}

	private int mapToOutput(Tree tree, PositionIterator[] iters) throws ScanFileHolderException, DatasetException {
		warnExposureZero = true;
		int[] dshape = iters[0].getShape();

		if (tree instanceof TreeFile) {
			logger.debug("Mapping {} ...", ((TreeFile) tree).getFilename());
		}
		Dataset trans = getTransmission(tree, dshape);
	
		DataNode node = (DataNode) tree.findNodeLink(dataPath).getDestination();
		ILazyDataset images = node.getDataset();
		if (images == null) {
			throw new ScanFileHolderException("Image data is empty");
		}
		int[] ishape = new int[] {Math.max(0, endY - begY), Math.max(0, endX - begX), };
	
		Dataset iMask = mask;
		if (scale != 1) {
			for (int i = 0; i < ishape.length; i++) {
				ishape[i] *= scale;
			}
			if (iMask != null) {
				iMask = upscaleMask(iMask.getSliceView(new Slice(begY, endY), new Slice(begX, endX)), ishape);
//				iMask = upSampler.value(iMask).get(0);
//				if (!Arrays.equals(iMask.getShapeRef(), ishape)) {
//					String msg = String.format("Image weight shape %s does not match image shape %s", Arrays.toString(iMask.getShapeRef()), Arrays.toString(ishape));
//					throw new IllegalArgumentException(msg);
//				}
			}
		}

		return bean.getOutputMode().getRank() > 2 ? mapImagesByStrips(tree, trans, iMask, images, iters, ishape)
				: mapImagesIndividually(tree, trans, iMask, images, iters, ishape);
	}

	private Dataset getTransmission(Tree tree, int[] dshape) throws ScanFileHolderException {
		Dataset trans = NexusTreeUtils.parseAttenuator(attenuatorPath, tree);
		if (trans != null && trans.getSize() != 1) {
			int[] tshape = trans.getShapeRef();
			if (!Arrays.equals(tshape, dshape)) {
				throw new ScanFileHolderException(String.format("Attenuator transmission shape %s does not match detector or sample scan shape %s",
					Arrays.toString(tshape), Arrays.toString(dshape)));
			}
		}
	
		// factor in count time too
		Dataset time = NexusTreeUtils.getDataset(timePath, tree);
		if (time != null) {
			if (time.getSize() != 1) {
				int[] tshape = time.getShapeRef();
				if (!Arrays.equals(tshape, dshape)) {
					throw new ScanFileHolderException(String.format("Exposure time shape %s does not match detector or sample scan shape %s",
						Arrays.toString(tshape), Arrays.toString(dshape)));
				}
			}
			trans = trans == null ? time : Maths.multiply(trans, time);
		}

		// factor in any monitor too
		if (monitorPath != null) {
			NodeLink monitorLink = TreeUtils.findNodeLink(tree, monitorPath, true);
			if (monitorLink != null) {
				GroupNode monitorNode = (GroupNode) monitorLink.getDestination();
				String monitorName = monitorLink.getName();
				String[] names;
				if (NexusTreeUtils.isNXClass(monitorNode, NexusConstants.POSITIONER)) {
					names = new String[] {"value", monitorName}; // use monitor name as fallback is a DLS special case
				} else { // works for NXmonitor, NXdetector and NXdata
					names = new String[] {"data"};
				}
				Dataset monitor = NexusTreeUtils.getDataset(monitorName, monitorNode, null, names);

				trans = trans == null ? monitor : Maths.multiply(trans, monitor);
			}
		}

		return trans;
	}

	private void mapToAList(Tree tree, PositionIterator[] iters, ILazyWriteableDataset lazy) throws ScanFileHolderException, DatasetException {
		int[] dshape = iters[0].getShape();
		Dataset trans = getTransmission(tree, dshape);
	
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
			upSampler = new BicubicInterpolator(true, ishape);
		}

		mapImagesToList(tree, trans, images, iters, lazy, ishape, upSampler);
	}

	private boolean isOLDI16GDA(Tree t) {
		if (!isI16) {
			return false;
		}

		NodeLink nl = t.findNodeLink(entryPath);
		GroupNode g = (GroupNode) nl.getDestination();
		DataNode d = g.getDataNode("program_name");
		if (d != null) {
			try {
				String program = NexusTreeUtils.getFirstString(d);
				if (program != null && program.startsWith("GDA")) {
					g = (GroupNode) NexusTreeUtils.requireNode(g, NexusConstants.USER);
					d = g.getDataNode("username"); // check old non-NeXus field
					if (d == null) {
						d = g.getDataNode("name");
						if (d == null) {
							return false;
						}
					}
					String user = NexusTreeUtils.getFirstString(d);
					if (user != null && user.equals("i16user")) {
						// program name given as "GDA 9.14.0", need version 9.15+
						String version = program.substring(program.indexOf(" ")).trim();
						return VersionUtils.isOldVersion("9.15.0", version);
					}
				}
			} catch (NexusException e) {
				// do nothing
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
			DiffractionSample sample = NexusTreeUtils.parseSample(samplePath, tree, isOldGDA, isI16, dpos);
			DiffractionCrystalEnvironment env = sample.getDiffractionCrystalEnvironment();
			QSpace qspace = new QSpace(dp, env);
			MillerSpace mspace = new MillerSpace(sample.getUnitCell(), env.getOrientation());
			pixelMapping.setSpaces(qspace, mspace);
			calcOutputBounds(4, vMin, vMax, qspace, mspace);
		}
	}

	private static final int PAD = 1 + 1;  // add one as we split across pixels
	private void roundLimitsAndFindShapes() {
		final int pad = PAD;
		for (int i = 0; i < vMin.length; i++) {
			double d = vDel[i];
			int l = (int) Math.floor(vMin[i] / d);
			vMin[i] = d * l;
			int u = (int) Math.ceil(vMax[i] / d);
			vMax[i] = d * u;
			if (i < vShape.length) {
				vShape[i] = u - l + pad;
			}
		}
	}

	private void calcOutputBounds(int n, double[] vBeg, double[] vEnd, QSpace qspace, MillerSpace mspace) {
		Vector3d q = new Vector3d();

		int dX = (endX - begX) / n;
		int dY = (endY - begY) / n;

		for (int x = begX; x < endX; x += dX) {
			for (int y = begX; y < endX; y += dY) {
				pixelMapping.map(x, y, q);
				minMax(vBeg, vEnd, q);
			}

			pixelMapping.map(x, endY, q);
			minMax(vBeg, vEnd, q);
		}

		for (int y = begX; y < endX; y += dY) {
			pixelMapping.map(endX, y, q);
			minMax(vBeg, vEnd, q);
		}

		pixelMapping.map(endX, endY, q);
		minMax(vBeg, vEnd, q);
	}

	/**
	 * Print where corners of all images are in Miller (aka HKL) space
	 * @param endsOnly if true, only print from first and last images 
	 * @throws ScanFileHolderException
	 */
	public void printMillerSpaceCorners(final boolean endsOnly) throws ScanFileHolderException {
		initializeFromBean(bean.getInputs()[0], true);

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
				DiffractionSample sample = NexusTreeUtils.parseSample(samplePath, tree, isOldGDA, isI16, dpos);
				DiffractionCrystalEnvironment env = sample.getDiffractionCrystalEnvironment();
				QSpace qspace = new QSpace(dp, env);
				MillerSpace mspace = new MillerSpace(sample.getUnitCell(), env.getOrientation());
				pixelMapping.setSpaces(qspace, mspace);
				printCorners(qspace, mspace);

				if (vDel != null) {
					calcOutputBounds(2, vMin, vMax, qspace, mspace);
				}
			}
			n++;
		}

		if (vDel != null) {
			roundLimitsAndFindShapes();
			logger.warn("Extent of the space was found to be {} to {}", Arrays.toString(vMin), Arrays.toString(vMax));
			logger.warn("with shape = {}", Arrays.toString(vShape));
		}
	}

	private void printCorners(QSpace qspace, MillerSpace mspace) {
		printCorner(pixelMapping, begX, begY);
		printCorner(pixelMapping, endX, begY);
		printCorner(pixelMapping, begX, endY);
		printCorner(pixelMapping, endX, endY);
	}

	private static void printCorner(ImagePixelMapping mapping, int x, int y) {
		Vector3d m = new Vector3d();
		mapping.map(x, y, m);
		logger.debug("{},{}: {}", x, y, m.toString());
	}

	/**
	 * Calculate coordinates of given pixel addresses
	 * @throws ScanFileHolderException
	 */
	public void calculateCoordinates() throws ScanFileHolderException {
		IntegerDataset pIdx = DatasetFactory.createFromObject(IntegerDataset.class, bean.getPixelIndexes());
		if (pIdx == null) {
			throw new IllegalArgumentException("No pixel indexes defined");
		}

		String[] inputs = bean.getInputs();
		if (inputs.length != 1) {
			throw new IllegalArgumentException("Only one input file allowed");
		}
		initializeFromBean(inputs[0], false);
		pixelMapping = ImagePixelMapping.createPixelMapping(bean.getOutputMode());

		Tree tree = getTreeFromNexusFile(inputs[0]);
		PositionIterator diter = getPositionIterators(tree)[0];
		final int[] dpos = diter.getPos();
		int nf = ShapeUtils.calcSize(diter.getShape());

		int np = pIdx.getShapeRef()[0]; // Nx2 or Nx3
		int ni = pIdx.getShapeRef()[1];
		Vector3d q = new Vector3d();
		DoubleDataset coords = null;
		IntegerDataset pInput = null;

		boolean isOldGDA = isOLDI16GDA(tree);

		if (ni == 2) {
			logger.debug("Using first frame of {}", nf);

			coords = DatasetFactory.zeros(np, 3);
			pInput = DatasetFactory.zeros(IntegerDataset.class, np, 3);
			if (diter.hasNext()) {
				DetectorProperties dp = NexusTreeUtils.parseDetector(detectorPath, tree, dpos)[0];
				DiffractionSample sample = NexusTreeUtils.parseSample(samplePath, tree, isOldGDA, isI16, dpos);
				DiffractionCrystalEnvironment env = sample.getDiffractionCrystalEnvironment();
				QSpace qspace = new QSpace(dp, env);
				MillerSpace mspace = new MillerSpace(sample.getUnitCell(), env.getOrientation());
				pixelMapping.setSpaces(qspace, mspace);

				for (int i = 0; i < np; i++) {
					int r = pIdx.get(i, 0);
					int c = pIdx.get(i, 1);
					pInput.set(r, i, 1);
					pInput.set(c, i, 2);
					pixelMapping.map(c, r, q);
					coords.set(q.getX(), i, 0);
					coords.set(q.getY(), i, 1);
					coords.set(q.getZ(), i, 2);
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
				DiffractionSample sample = NexusTreeUtils.parseSample(samplePath, tree, isOldGDA, false, dpos);
				DiffractionCrystalEnvironment env = sample.getDiffractionCrystalEnvironment();
				QSpace qspace = new QSpace(dp, env);
				MillerSpace mspace = new MillerSpace(sample.getUnitCell(), env.getOrientation());
				pixelMapping.setSpaces(qspace, mspace);

				if (aiter != null) {
					while (aiter.hasNext()) {
						int i = allFrames.getAbs(aiter.index);

						int r = pIdx.get(i, 1);
						int c = pIdx.get(i, 2);
						pInput.set(f, k, 0);
						pInput.set(r, k, 1);
						pInput.set(c, k, 2);
						pixelMapping.map(c, r, q);
						coords.set(q.getX(), k, 0);
						coords.set(q.getY(), k, 1);
						coords.set(q.getZ(), k, 2);
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
						pixelMapping.map(c, r, q);
						coords.set(q.getX(), k, 0);
						coords.set(q.getY(), k, 1);
						coords.set(q.getZ(), k, 2);
						k++;
					}
				}
				f++;
			}
		}

		saveCoordinates(pInput, coords);
	}

	private void saveCoordinates(Dataset indexes, Dataset coords) throws ScanFileHolderException {
		String output = bean.getOutput();
		String spaceName = getSpaceName(bean.getOutputMode().isQ());
		String coordsPath = TreeUtils.join(PROCESSED, spaceName);

		indexes.setName("pixel_indexes");
		coords.setName("coordinates");

		HDF5FileFactory.deleteFile(output);
		writeNXclass(output, PROCESSED, NexusConstants.ENTRY);
		writeNXclass(output, PROCESSPATH, NexusConstants.PROCESS);
		writeDefaultAttributes(output, spaceName);
		HDF5Utils.writeDataset(output, coordsPath, indexes);
		HDF5Utils.writeDataset(output, coordsPath, coords);
		saveAxesAndAttributes(output, coordsPath, coords.getName(), null, null);
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

	private int getImageNumbers(int[] scanShape) {
		int end = ShapeUtils.calcSize(scanShape);
		imagesSlice.setLength(end);
		int images = imagesSlice.getNumSteps();
		logger.info("Selecting {} images using slice: {} with end {}", images, imagesSlice, end);
		return images;
	}

	private int mapImagesByStrips(Tree tree, Dataset trans, Dataset iMask, ILazyDataset images, PositionIterator[] iters,
			int[] ishape) throws DatasetException {
		warnExposureZero = true;
		PositionIterator diter = iters[0]; // scan iterator
		PositionIterator iter = iters[1]; // data iterator (omits image axes)
		iter.reset();
		diter.reset();
		int ne = getImageNumbers(diter.getShape());

		int[] dpos = diter.getPos();
		int[] pos = iter.getPos();
		int[] start = pos.clone();
		int[] stop = iter.getStop().clone();
		int rank  = pos.length;
		int srank = rank - 2;

		boolean isOldGDA = isOLDI16GDA(tree);
		int ni = 0;

		start[srank] = begY;
		start[srank + 1] = begX;
		stop[srank] = endY;
		stop[srank + 1] = endX;

		final String inFile = tree instanceof TreeFile ? " in " + ((TreeFile) tree).getFilename() : "";
		int miss = imagesSlice.getStart() + 1;
		Dataset image = getNextImage(miss, images, iter, start, stop);
		while (image != null && ni < ne) {
			while (diter.hasNext() && --miss > 0) {
			}
			if (miss > 0) {
				break;
			}

			int[] s = Arrays.copyOfRange(image.getShapeRef(), srank, rank);
			image.setShape(s);
			miss = imagesSlice.getStep();
			if (image.max().doubleValue() <= 0) {
				logger.info("Skipping image at {}{}", Arrays.toString(dpos), inFile);
				image = getNextImage(miss, images, iter, start, stop);
			} else {
				logger.info("Mapping image at {}/{}{}", Arrays.toString(dpos), Arrays.toString(diter.getShape()), inFile);
				initializeImagePixelMapping(pixelMapping, tree, ishape, dpos, isOldGDA);
	
				double tFactor = getTransmissionCorrection(trans, dpos);
				image = mapImageByStripsMultiThreaded(ni < ne - 1, images, iter, start, stop, tFactor, iMask, image, ishape);
			}

			ni++;
		}

		return ni;
	}

	/**
	 * @param trans
	 * @param dpos
	 * @return correction factor for adjust for exposure time and/or transmission
	 */
	private double getTransmissionCorrection(Dataset trans, int[] dpos) {
		double tFactor = 1;
		if (trans != null) {
			tFactor = trans.getSize() == 1 ? trans.getDouble() : trans.getDouble(dpos);
			if (tFactor == 0) {
				tFactor = 1;
				if (warnExposureZero) {
					warnExposureZero = false;
					logger.warn("Exposure time or transmission was zero; no correction applied");
				}
			} else {
				tFactor = 1. / tFactor;
			}
		}
		return tFactor;
	}

	private void initializeImagePixelMapping(ImagePixelMapping pMapping, Tree tree, int[] ishape, int[] dpos, boolean isOldGDA) {
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
		DiffractionSample sample = NexusTreeUtils.parseSample(samplePath, tree, isOldGDA, isI16, dpos);
		DiffractionCrystalEnvironment env = sample.getDiffractionCrystalEnvironment();
		QSpace qspace = new QSpace(dp, env);
		MillerSpace mspace = new MillerSpace(sample.getUnitCell(), env.getOrientation());
		pMapping.setSpaces(qspace, mspace);
	}

	private Dataset getNextImage(int miss, ILazyDataset images, PositionIterator iter, int[] start, int[] stop) throws DatasetException {
		while (iter.hasNext() && --miss > 0) {
		}
		if (miss > 0) {
			return null;
		}

		long begin = System.currentTimeMillis();
		int[] pos = iter.getPos();
		for (int i = 0; i < (pos.length - 2); i++) {
			start[i] = pos[i];
			stop[i] = pos[i] + 1;
		}
		try {
			logger.info("Slicing image start:{}, stop:{}", Arrays.toString(start), Arrays.toString(stop));
			return DatasetUtils.convertToDataset(images.getSlice(start, stop, null));
		} catch (DatasetException e) {
			logger.error("Could not get data from lazy dataset", e);
			throw e;
		} finally {
			loadTimeTotal += System.currentTimeMillis() - begin;
		}
	}

	private void mapImageToOutput(int w, final double tFactor, final BicubicInterpolator upSampler, final ImagePixelMapping mapping, PixelSplitter splitter, int[] sMinLocal, int[] sMaxLocal, final int[] region, final Dataset mask, final Dataset image) {
		logger.trace("Map image region: {}", Arrays.toString(region));
		final int yStart = region[2];
		final int yStop  = region[3];
		int iStart = scale == 1 ? yStart : (int) Math.round(yStart/scale);
		int iStop  = scale == 1 ? yStop  : (int) Math.round(yStop/scale);
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

		final int[] pos = new int[vShape.length]; // output position
		final Vector3d v = new Vector3d();
		final Vector3d dv = new Vector3d(); // delta in h

		final int xStart = region[0];
		final int xStop  = region[1];
		final DetectorProperties detector = mapping.getDetectorProperties();
		final QSpace qSpace = mapping.getQSpace();
		final Vector4d stokes = qSpace.getStokesVector();
		final double polnQ = stokes.getY()/stokes.getX(); // normalized difference of linear H/V polarization
		final double polnU = stokes.getZ()/stokes.getX(); // normalized difference of linear +/-45 degrees polarization
		final Vector3d beam = detector.getBeamVector();
		final Vector3d reference = qSpace.getReferenceNormal();

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
					mapping.map(x + 0.5, py + 0.5, v);

					if (convertToOutputCoords(v, dv, pos)) {
						double correction = detector.calculateSolidAngle(x, py);
						if (correctPolarization) {
							correction *= calculatePolarizationFactor(polnQ, polnU, beam, reference, mapping.getPositionVector(), mapping.getCosineScatteringAngle());
						}
						value *= tFactor / correction;

						if (reduceToNonZeroBB) {
							minMax(splitter.doesSpread(), sMinLocal, sMaxLocal, pos);
						}

						splitter.splitValue(dv, pos, value);
					}
				}
			}
		}
	}

	/**
	 * Polarization factor is the dependency on input polarization and pixel position
	 * <p>
	 * Formula can be derived from azimuthal rotation from reference plane to scattering plane,
	 * followed by finding the component of in-scattering plane field normal to the scattering
	 * direction.
	 * <p>
	 * In terms of Stokes parameters,
	 * <pre>
	 * I_f = [ (mu^2 + 1) I_i + (mu^2 - 1) (cos(2 phi) Q_i + sin(2 phi) U_i)] / 2
	 * </pre>
	 * where mu = cos(theta) and theta is the scattering angle, phi is the azimuthal angle.
	 * See, e.g., Kahn et al, J. Appl. Cryst. 15, pp330-7 (1982).
	 * <p>
	 * @param q Q/I
	 * @param u U/I
	 * @param reference normal to reference plane
	 * @param posn pixel position
	 * @param cosTheta cosine of scattering angle
	 * @return factor
	 */
	public static final double calculatePolarizationFactor(final double q, double u, final Vector3d beam, final Vector3d reference, final Vector3d posn, double cosTheta) {
		Vector3d proj = new Vector3d();
		proj.scaleAdd(-beam.dot(posn), beam, posn); // projection of position on plane perpendicular to beam

		double muSq = cosTheta * cosTheta;
		double l = proj.lengthSquared();
		if (l == 0) { // azimuth = 0
			return 0.5 * ((muSq + 1) + (muSq - 1)*q);
		}
		double comp = reference.dot(proj); // normal component of position is sine of azimuth * length
		double sinPhiSq = comp * comp / l;
		double cosTwoPhi = 1 - 2 * sinPhiSq;
		double sinTwoPhi = Math.signum(comp) * Math.sqrt(1 - cosTwoPhi * cosTwoPhi);
		return 0.5 * ((muSq + 1) + (muSq - 1)*(cosTwoPhi * q + sinTwoPhi * u));
	}

	private static final int MIN_CHUNK = 128;

	private boolean chunkSizeTooSmall(final int chunk, final int rows) {

		if (chunk < MIN_CHUNK) {
			logger.debug("Chunk smaller than min: {} < {}", chunk, MIN_CHUNK);
			return true;
		}

		final Vector3d v = new Vector3d();
		Vector3d ov = null;
		double xCoord = 0.5;
		final double[] voxelSides = vDel;
		final double safetyFactor = 1./4;
		for (int y = 0; y < rows; y += chunk) {
			pixelMapping.map(xCoord, y + 0.5, v);

			if (ov == null) {
				ov = new Vector3d(v);
			} else { // check if pixels from adjoining bands map to same voxel
				if (Math.abs(v.x - ov.x) * safetyFactor < voxelSides[0]
						&& Math.abs(v.y - ov.y) * safetyFactor < voxelSides[1]
						&& (voxelSides.length < 3 || Math.abs(v.z - ov.z) * safetyFactor < voxelSides[2])) {
					v.sub(ov);
					logger.debug("Chunk is small enough for voxel contention: difference {} < {} (with safety factor {})", v, voxelSides, safetyFactor);
					return true;
				}
				ov.set(v);
			}
		}

		return false;
	}

	private Dataset mapImageByStripsMultiThreaded(boolean loadNext, ILazyDataset images, PositionIterator iter, int[] start, int[] stop, final double tFactor,
			final Dataset iMask, final Dataset image, final int[] ishape) throws DatasetException {
		int size = poolSize - 1; // reserve for loading next image
		final int[] pos = iter.getPos();
		if (size == 0) {
			BicubicInterpolator upSampler = scale == 1 ? null : new BicubicInterpolator(true, ishape);
			int[] regionSlice = new int[] { 0, ishape[1], 0, ishape[0] };
			try {
				mapImageToOutput(0, tFactor, upSampler, pixelMapping, splitter, sMin, sMax, regionSlice, iMask, image);
				logger.debug("Finished mapping at {}", Arrays.toString(pos));
			} catch (Exception e) {
				logger.error("Failed mapping at {}", Arrays.toString(pos), e);
			}

			return loadNext ? getNextImage(imagesSlice.getStep(), images, iter, start, stop) : null;
		}

		final int rows = ishape[0];
		double regionSize = rows;

		int chunk = (int) Math.ceil(regionSize / size);
		while (size > 1 && chunkSizeTooSmall(chunk, rows)) {
			chunk = (int) Math.ceil(regionSize / --size);
			logger.info("Chunk too small so decrementing parallelism to {}", size);
		}
		logger.info("Splitting images to bands of {} rows across {} threads", chunk, size);

		final List<JobConfig> jobs = new ArrayList<>(size);
		chunk = (int) (Math.ceil(chunk/scale)*scale); // round up to be divisible by scale
		for (int i = 0; i < size; i++) {
			int s = i * chunk;
			int e = Math.min(s + chunk, rows);
			logger.info("Chunk {}: {} -> {}", i, s, e);
			BicubicInterpolator upSampler = scale == 1 ? null : new BicubicInterpolator(true, e - s, ishape[1]);
			jobs.add(new JobConfig(i, upSampler, pixelMapping.clone(), splitter.clone(), s, e, vShape));
		}

		JobConfig loadJob = new JobConfig(-1, null, null, null, 0, 0, null); // load next image
		jobs.add(loadJob); // load next image

		Consumer<JobConfig> subTask = job -> {
			if (job.getSplitter() == null) {
				if (loadNext) {
					try {
						job.setData(getNextImage(imagesSlice.getStep(), images, iter, start, stop));
					} catch (DatasetException e) {
						job.setException(e);
					}
				}
				return;
			}
			int[] sMinLocal = job.getMinLocal();
			int[] sMaxLocal = job.getMaxLocal();
			int[] regionSlice = new int[] { 0, ishape[1], job.getStart(), job.getEnd() };

			try {
				mapImageToOutput(job.getNo(), tFactor, job.getUpScaler(), job.getMapping(), job.getSplitter(), sMinLocal, sMaxLocal, regionSlice, iMask, image);
				logger.debug("Job {}: finished mapping at {}", job.getNo(), Arrays.toString(pos));
			} catch (Exception e) {
				logger.error("Job {}: failed mapping at {}", job.getNo(), Arrays.toString(pos), e);
			}
		};

		try {
			pool.submit(() -> jobs.parallelStream().forEach(subTask)).get();
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException("Multithreaded jobs did not finish successfully", e);
		}

		if (reduceToNonZeroBB) {
			for (JobConfig j : jobs) {
				if (j.getMinLocal() != null) {
					minMax(sMin, sMax, j.getMinLocal(), j.getMaxLocal());
				}
			}
		}

		DatasetException e = loadJob.getException();
		if (e != null) {
			throw e;
		}
		return loadJob.getData();
	}

	private static void minMax(double[] min, double[] max, Vector3d v) {
		min[0] = Math.min(min[0], v.x);
		max[0] = Math.max(max[0], v.x);
		min[1] = Math.min(min[1], v.y);
		max[1] = Math.max(max[1], v.y);
		min[2] = Math.min(min[2], v.z);
		max[2] = Math.max(max[2], v.z);
	}

	private static void minMax(boolean spreads, final int[] min, final int[] max, final int[] p) {
		int t = p[0];
		min[0] = Math.min(min[0], t);
		if (spreads) {
			t++;
		}
		max[0] = Math.max(max[0], t);

		if (p.length > 1) {
			t = p[1];
			min[1] = Math.min(min[1], t);
			if (spreads) {
				t++;
			}
			max[1] = Math.max(max[1], t);

			if (p.length > 2) {
				t = p[2];
				min[2] = Math.min(min[2], t);
				if (spreads) {
					t++;
				}
				max[2] = Math.max(max[2], t);
			}
		}
	}

	private static void minMax(final int[] min, final int[] max, final int[] lmin, final int[] lmax) {
		min[0] = Math.min(min[0], lmin[0]);
		max[0] = Math.max(max[0], lmax[0]);
		if (min.length > 1) {
			min[1] = Math.min(min[1], lmin[1]);
			max[1] = Math.max(max[1], lmax[1]);
			if (min.length > 2) {
				min[2] = Math.min(min[2], lmin[2]);
				max[2] = Math.max(max[2], lmax[2]);
			}
		}
	}

	/**
	 * Map from v to output position
	 * @param v vector
	 * @param deltaV delta in discrete space
	 * @param pos output coords
	 * @return true if within bounds
	 */
	private boolean convertToOutputCoords(final Vector3d v, final Vector3d deltaV, final int[] pos) {
		if (!findResultBB) {
			if (v.x < vMin[0] || v.x >= vMax[0] || v.y < vMin[1] || v.y >= vMax[1] ||
					v.z < vMin[2] || v.z >= vMax[2]) {
				return false; 
			}
		}

		double p;
		double dv, f;

		dv = v.x - vMin[0];
		if (dv < 0) {
			return false;
		}
		f = dv / vDel[0];
		p = Math.floor(f);
		deltaV.x = f - p;
		pos[0] = (int) p;

		dv = v.y - vMin[1];
		if (dv < 0) {
			return false;
		}
		f = dv / vDel[1];
		p = Math.floor(f);
		deltaV.y = f - p;
		if (pos.length > 1) {
			pos[1] = (int) p;
		}

		dv = v.z - vMin[2];
		if (dv < 0) {
			return false;
		}
		f = dv / vDel[2];
		p = Math.floor(f);
		deltaV.z = f - p;
		if (pos.length > 2) {
			pos[2] = (int) p;
		}

		return true;
	}

	/*
	 * Differs from mapImagesByStrips by loading in main thread that
	 * adds jobs (for each image) to a queue for rest of thread pool
	 */
	private int mapImagesIndividually(Tree tree, Dataset trans, Dataset iMask, ILazyDataset images, PositionIterator[] iters,
			int[] ishape) throws DatasetException {
		int size = Math.max(1, poolSize - 1); // reserve for loading next image


		// create queue and workers
		BlockingQueue<ImageJobConfig> queue = new LinkedBlockingQueue<>();
		List<QueueWorker> workers = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			PixelSplitter wSplitter;
			if (i == 0) {
				wSplitter = splitter;
			} else {
				wSplitter = splitter.clone();
				wSplitter.setDatasets(DatasetFactory.zeros(vShape), DatasetFactory.zeros(vShape));
			}
			BicubicInterpolator upSampler = scale == 1 ? null : new BicubicInterpolator(true, ishape);
			QueueWorker w = new QueueWorker(i, queue, upSampler, pixelMapping.clone(), wSplitter, iMask, trans, tree, ishape, vShape);
			workers.add(w);
			pool.execute(w);
		}

		PositionIterator diter = iters[0]; // scan iterator
		PositionIterator iter = iters[1]; // data iterator (omits image axes)
		iter.reset();
		diter.reset();
		final int[] dpos = diter.getPos();
		final int[] dShape = diter.getShape();
		final int[] pos = iter.getPos();
		final int[] start = pos.clone();
		final int[] stop = iter.getStop().clone();
		int rank  = pos.length;
		int srank = rank - 2;
		int ne = getImageNumbers(dShape);

//		boolean isOldGDA = isOLDI16GDA(tree);
		int ni = 0;

		start[srank] = begY;
		start[srank + 1] = begX;
		stop[srank] = endY;
		stop[srank + 1] = endX;

		final String inFile = tree instanceof TreeFile ? " in " + ((TreeFile) tree).getFilename() : "";
		int limit = 2 * size;
		int miss = imagesSlice.getStart() + 1;
		while (ni < ne) {
			Dataset image = getNextImage(miss, images, iter, start, stop);
			while (diter.hasNext() && --miss >0) {
			}
			if (miss > 0) {
				break;
			}

			int[] s = Arrays.copyOfRange(image.getShapeRef(), srank, rank);
			image.setShape(s);
			miss = imagesSlice.getStep();
			if (image.max().doubleValue() <= 0) {
				logger.info("Skipping image at {} {}", Arrays.toString(pos), inFile);
			} else {
				ImageJobConfig j = new ImageJobConfig(image, dpos, dShape);
				queue.add(j);
			}

			double wait = 0;
			while (queue.size() >= limit) {
				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
				}
				wait += 0.25;
			}
			if (wait > 0) {
				logger.debug("Paused image loading for {} second until queue size < {}", wait, limit);
			}
			ni++;
		}

		ImageJobConfig lastJob = new ImageJobConfig(); // empty config to stop workers
		for (int i = 0; i < size; i++) {
			queue.add(lastJob);
		}
		limit = 2 * queue.size();
		while (queue.size() > 0 && limit > 0) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			limit--;
		}
		if (queue.size() > 0) {
			logger.warn("Queue has {} items left", queue.size());
		}

		// aggregate results from workers
		DoubleDataset[] results = null;
		for (QueueWorker w : workers) {
			if (w.isRunning()) {
				int i = w.interrupt();
				logger.error("Interrupted worker {} that has processed {} images", w.getNo(), i);
			}
			DoubleDataset[] r = w.getDatasets();
			if (results == null) {
				results = r;
			} else {
				results[0].iadd(r[0]);
				results[1].iadd(r[1]);
			}
			if (reduceToNonZeroBB) {
				if (w.getMinLocal() != null) {
					minMax(sMin, sMax, w.getMinLocal(), w.getMaxLocal());
				}
			}
		}

		return ni;
	}

	private void mapImagesToList(Tree tree, Dataset trans, ILazyDataset images, PositionIterator[] iters,
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
			DiffractionSample sample = NexusTreeUtils.parseSample(samplePath, tree, isOldGDA, isI16, dpos);
			DiffractionCrystalEnvironment env = sample.getDiffractionCrystalEnvironment();
			QSpace qspace = new QSpace(dp, env);
			mspace = new MillerSpace(sample.getUnitCell(), env.getOrientation());
			pixelMapping.setSpaces(qspace, mspace);

			Dataset image = DatasetUtils.convertToDataset(images.getSlice(pos, stop, null));
			double tFactor = getTransmissionCorrection(trans, dpos);
			if (tFactor != 1) {
				image.imultiply(tFactor);
			}

			int[] s = Arrays.copyOfRange(image.getShapeRef(), srank, rank);
			image.setShape(s);
			if (image.max().doubleValue() <= 0) {
				logger.info("Skipping image at {}", Arrays.toString(pos));
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
			int length = mapImageToList(s, qspace, image, list);
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

	private int mapImageToList(int[] s, QSpace qspace, Dataset image, DoubleDataset list) {
		double value;
		Vector3d h = new Vector3d();

		int index = 0;
		for (int y = 0; y < s[0]; y++) {
			for (int x = 0; x < s[1]; x++) {
				value = image.getDouble(y, x);
				if (value > 0) {
					value /= qspace.calculateSolidAngle(x, y);
					pixelMapping.map(x + 0.5, y + 0.5, h);
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
	 * Initialize from bean and NeXus file
	 * @param file NeXus file
	 * @param complete if true, get axes and set fields completely
	 * @return axes to volume
	 * @throws ScanFileHolderException
	 */
	private Dataset[] initializeFromBean(String file, boolean complete) throws ScanFileHolderException {
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

		String definition = NexusTreeUtils.getFirstString(entry.getAttribute("definition"));
		if (definition == null || !definition.equals(APP_DEF_MX)) {
			GroupNode subentry = NexusTreeUtils.findFirstSubEntry(entry, APP_DEF_MX);
			if (subentry != null) {
				logger.trace("{} found for {}: {}", NexusConstants.SUBENTRY, APP_DEF_MX, subentry);
				entry = subentry;
			} else if (definition != null) {
				logger.warn("Application definition is {} but require {} and no correct subentries too ...", definition, APP_DEF_MX);
			}
		}

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

		DataNode nameNode = instrument.getDataNode("name");
		String beamline = nameNode == null ? null : NexusTreeUtils.getStringArray(nameNode, 1)[0].toLowerCase();
		isI16 = "i16".equals(beamline);
		if (isI16) {
			logger.info("{} is a file from I16", file);
		}

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

		isQSpace = bean.getOutputMode().isQ();

		imagesSlice = bean.getImages() == null ? new Slice(): Slice.convertFromString(bean.getImages())[0];
		if (imagesSlice.getStart() == null) {
			imagesSlice.setStart(0);
		}

		correctPolarization = bean.isCorrectPolarization();

		if (!complete) {
			return null;
		}

		timePath = detectorPath + "count_time";

		String attenuatorName = bean.getAttenuatorName();
		if (attenuatorName == null || attenuatorName.isEmpty()) {
			link = NexusTreeUtils.findFirstNode(instrument, NexusConstants.ATTENUATOR);
			if (link == null) {
				logger.trace("{} not found", NexusConstants.ATTENUATOR);
			} else {
				logger.trace("{} found: {}", NexusConstants.ATTENUATOR, link);
			}
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

		String monitorName = bean.getMonitorName();
		if (monitorName == null || monitorName.isEmpty()) {
			link = NexusTreeUtils.findFirstNode(instrument, NexusConstants.MONITOR);
			if (link == null) {
				logger.trace("{} not found", NexusConstants.MONITOR);
			} else {
				logger.trace("{} found: {}", NexusConstants.MONITOR, link);
			}
		} else {
			link = instrument.getNodeLink(monitorName);
		}
		if (link == null || !link.isDestinationGroup()) {
			monitorPath = null;
		} else {
			monitorPath = TreeUtils.getPath(tree, link.getDestination());
		}
		if (monitorPath == null) {
			logger.warn("Could not find monitor");
		}

		int outputRank = bean.getOutputMode().getRank();
		this.splitter = PixelSplitter.createSplitter(outputRank, bean.getSplitterName(), bean.getSplitterParameter());
		listMillerEntries = bean.isListMillerEntries();
		pixelMapping = ImagePixelMapping.createPixelMapping(bean.getOutputMode());

		double[] delta = bean.getStep();
		if (delta != null) {
			if (delta.length == 0) {
				throw new IllegalArgumentException("MSM bean must have step array defined (with one or more items)");
			}
			if (delta.length == 1) {
				double d = delta[0];
				vDel = new double[] {d, d, d};
			} else if (delta.length == 2) {
				double d = delta[1];
				vDel = outputRank > 2 ? new double[] {delta[0], d, d} : new double[] {delta[0], d, Math.min(delta[0], d)};
			} else {
				vDel = delta;
			}
		}

		sMin = new int[outputRank];
		sMax = new int[outputRank];

		vShape = new int[outputRank];
		int[] bShape = bean.getShape();
		if (bShape == null) {
			findResultBB = true;
		} else if (bShape.length < outputRank) {
			throw new IllegalArgumentException("MSM bean has shape of rank less than required for output mode");
		} else {
			System.arraycopy(bShape, 0, vShape, 0, outputRank);
		}

		double[] bStart = bean.getStart();
		if (bStart == null) {
			findResultBB = true;
			vMin = new double[3];
		} else if (bStart.length < 3) {
			double m = Double.POSITIVE_INFINITY;
			vMin = new double[3];
			int i = 0;
			for (; i < bStart.length; i++) {
				double v = bStart[i];
				if (v < m) {
					m = v;
				}
				vMin[i] = v;
			}
			for (; i < 3; i++) {
				vMin[i] = m;
			}
		} else {
			vMin = bStart;
		}
		vMax = new double[3];

		Dataset[] axes = new Dataset[outputRank];
		if (!findResultBB && vDel != null) {
			createOutputAxes(pixelMapping.getAxesName(), axes, vShape, vMin, vMax, vDel);
		}

		setReduceToNonZeroData(bean.isReduceToNonZero());
		setUpsamplingScale(bean.getScaleFactor());

		return axes;
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

		StringDataset pd = DatasetFactory.createFromObject(StringDataset.class, names, NexusTreeUtils.getIntArray(dn));

		DataNode node = TreeFactory.createDataNode(-1);
		node.addAttribute(TreeFactory.createAttribute(NexusConstants.DATA_SIGNAL, 1));
		ImageStackLoader loader;
		try {
			loader = new ImageStackLoader(pd, pf.getAbsolutePath());
		} catch (Exception e) {
			throw new ScanFileHolderException("I16 workaround: cannot create image stack loader", e);
		}
		loader.setMaxShape(dn.getMaxShape());
		node.setMaxShape(loader.getMaxShape());
		node.setChunkShape(loader.getChunkShape());
		node.setDataset(loader.createLazyDataset(I16_IMAGE_DATA));
		detector.addDataNode(I16_IMAGE_DATA, node);
		return detector.getNodeLink(I16_IMAGE_DATA);
	}

	/**
	 * Map images from given Nexus files to an output dataset and save to a HDF5 file
	 * @return axis datasets
	 * @throws ScanFileHolderException
	 */
	public Dataset[] mapToOutputFile() throws ScanFileHolderException {
		return mapToOutputFile(false);
	}

	/**
	 * Map images from given Nexus files to a volume and save to a HDF5 file
	 * @return axis datasets
	 * @throws ScanFileHolderException
	 * @deprecated Use {{@link #mapToOutputFile()} instead
	 */
	@Deprecated(since="Dawn 2.25")
	public Dataset[] mapToVolumeFile() throws ScanFileHolderException {
		logger.deprecatedMethod("mapToVolumeFile()", null, "mapToOutputFile()");
		return mapToOutputFile();
	}

	/**
	 * Map images from given Nexus files to a volume and save to a HDF5 file
	 * 
	 * @param isErrorTest
	 * @return axis datasets
	 * @throws ScanFileHolderException
	 */
	Dataset[] mapToOutputFile(boolean isErrorTest) throws ScanFileHolderException {
		hasDeleted = false; // reset state
		Dataset[] datasetA = null;

		String[] inputs = bean.getInputs();
		Dataset[] axes = initializeFromBean(inputs[0], true);
		if (vDel == null && !listMillerEntries) {
			throw new IllegalStateException("Volume parameters have not been defined");
		}

		int n = inputs.length;
		Tree[] trees = new Tree[n];
		PositionIterator[][] allIters = new PositionIterator[n][];
		for (int i = 0; i < n; i++) {
			trees[i] = getTreeFromNexusFile(inputs[i]);
			allIters[i] = getPositionIterators(trees[i]);
		}

		createPixelMask(trees[0], allIters[0][0]);

		if (findResultBB && vDel != null) {
			Arrays.fill(vMin, Double.POSITIVE_INFINITY);
			Arrays.fill(vMax, Double.NEGATIVE_INFINITY);

			for (int i = 0; i < n; i++) {
				findBoundingBoxes(trees[i], allIters[i]);
			}
			roundLimitsAndFindShapes();

			logger.warn("Extent of volume was found to be {} to {}", Arrays.toString(vMin), Arrays.toString(vMax));
			logger.warn("with shape = {}", Arrays.toString(vShape));
		}

		try {
			if (vDel != null) {
				datasetA = processTreesToOutput(trees, allIters, axes, isErrorTest);
			}

			if (listMillerEntries) {
				processTreesToList(trees, allIters);
			}
		} catch (ScanFileHolderException sfhe) {
			throw sfhe;
		} catch (DatasetException e) {
			throw new ScanFileHolderException("Could not get data from lazy dataset", e);
		}

		return datasetA;
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

	private Dataset[] processTreesToOutput(Tree[] trees, PositionIterator[][] allIters, Dataset[] axes,
			boolean isErrorTest) throws ScanFileHolderException, DatasetException {
		long start = System.currentTimeMillis();
		String output = bean.getOutput();

		if (reduceToNonZeroBB) {
			initializeOutputBoundingBox(vShape, sMin, sMax);
		}

		String spaceName = getSpaceName(isQSpace);
		String outputName = getOutputName(vShape.length);
		String outputPath = TreeUtils.join(PROCESSED, spaceName);

		loadTimeTotal = 0;
		try {
			if (isErrorTest) {
				throw new OutOfMemoryError();
			}

			DoubleDataset map = DatasetFactory.zeros(vShape);
			DoubleDataset weight = DatasetFactory.zeros(vShape);
			splitter.setDatasets(map, weight);

			int nt = mapAllTrees(trees, allIters);
			Maths.dividez(map, weight, map); // normalize by tally

			if (reduceToNonZeroBB) {
				logger.warn("Reduced to non-zero bounding box: {} to {}", Arrays.toString(sMin), Arrays.toString(sMax));
				for (int i = 0; i < vShape.length; i++) {
					vMin[i] += sMin[i]*vDel[i];
					sMax[i]++;
					vShape[i] = sMax[i] - sMin[i];
				}
				logger.warn("so now start = {} for shape = {}", Arrays.toString(vMin), Arrays.toString(vShape));
				map = (DoubleDataset) map.getSliceView(sMin, sMax, null);
				weight = (DoubleDataset) weight.getSliceView(sMin, sMax, null);
			}

			if (findResultBB) {
				createOutputAxes(pixelMapping.getAxesName(), axes, vShape, vMin, null, vDel);
			}
			long process = System.currentTimeMillis() - start;
			logger.info("For {} threads, processing took {}s ({}ms/frame)", poolSize, process/1000, process/nt);
			logger.info("               loading {} frames took {}s ({}ms/frame)", nt, loadTimeTotal/1000, loadTimeTotal/nt);
			start = System.currentTimeMillis();

			boolean isFileNew = !hasDeleted;
			if (isFileNew) {
				HDF5FileFactory.deleteFile(output);
				hasDeleted = true;
			}

			if (isFileNew) {
				writeNXclass(output, PROCESSED, NexusConstants.ENTRY);
				writeNXclass(output, PROCESSPATH, NexusConstants.PROCESS);
				writeDefaultAttributes(output, spaceName);
			}

			saveOutput(output, bean, entryPath, outputPath, map, weight, pixelMapping.getAxesUnits(), axes);
			logger.info("Saving took {}ms", System.currentTimeMillis() - start);
		} catch (IllegalArgumentException | OutOfMemoryError e) {
			logger.warn("There is not enough memory (for output shape = {}) to do this all at once!", Arrays.toString(vShape));
			logger.warn("Now attempting to segment volume");
			if (findResultBB) {
				createOutputAxes(pixelMapping.getAxesName(), axes, vShape, vMin, null, vDel);
			}

			// unset these as code does not or should not handle them
			findResultBB = false;
			reduceToNonZeroBB = false;

			int parts = 1;
			DoubleDataset map = null;
			DoubleDataset weight = null;
			// find biggest size that fits
			int[] tShape = vShape.clone();
			while (true) {
				parts++;
				tShape[0] = (vShape[0] + parts - 1)/parts + 1; // add one for overlapping split contributions
				if (tShape[0] == 2) { // maybe use other dimensions too(!)
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
			splitter.setDatasets(map, weight);

			logger.info("Mapping in {} parts, each of shape {}", parts, Arrays.toString(tShape));
			boolean isFileNew = !hasDeleted;
			if (isFileNew) {
				HDF5FileFactory.deleteFile(output);
				hasDeleted = true;
			}

			int[] cShape = vShape.length == 2 ? vShape.clone() :
				new int[] { Math.min(vShape[0], 64), vShape[1], vShape[2] };

			LazyWriteableDataset lazyVolume = HDF5Utils.createLazyDataset(output, outputPath, outputName, vShape, null,
					cShape, DoubleDataset.class, null, false);
			LazyWriteableDataset lazyWeight = HDF5Utils.createLazyDataset(output, outputPath, WEIGHT_NAME, vShape, null,
					cShape, DoubleDataset.class, null, false);

			if (isFileNew) {
				writeNXclass(output, PROCESSED, NexusConstants.ENTRY);
				writeNXclass(output, PROCESSPATH, NexusConstants.PROCESS);
				writeDefaultAttributes(output, spaceName);
			}

			mapToVolumeAndSaveInParts(trees, allIters, lazyVolume, lazyWeight, parts);

			saveAxesAndAttributes(output, outputPath, outputName, new String[] {WEIGHT_NAME}, pixelMapping.getAxesUnits(), axes);
			writeProcessingParameters(output, bean, entryPath);
			linkOriginalData(output, bean.getInputs(), entryPath);
		}
		return axes;
	}

	private static void writeDefaultAttributes(String file, String outputName) throws ScanFileHolderException {
		createAndWriteAttribute(file, Tree.ROOT, NexusConstants.DEFAULT, ENTRY);
		createAndWriteAttribute(file, PROCESSED, NexusConstants.DEFAULT, outputName);
	}

	private static void createOutputAxes(String[] names, Dataset[] axes, int[] mShape, double[] mStart, double[] mStop, double[] mDelta) {
		for (int i = 0; i < mShape.length; i++) {
			int l = mShape[i];
			double mbeg = mStart[i];
			double mend = mbeg + l * mDelta[i];
			if (mStop != null) {
				mStop[i] = mend;
			}
			DoubleDataset a = DatasetFactory.createRange(l);
			axes[i] = a.imultiply(mDelta[i]).iadd(mbeg);
			a.setName(names[i]);
			if (l == 1) {
				logger.trace("Axis {}: {}; {}", i, mbeg, mend);
			} else {
				logger.trace("Axis {}: {} -> {}; {}", i, mbeg, axes[i].getDouble(l - 1), mend);
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

		int rank = images.getRank();
		int srank = rank - 2;
		if (srank < 0) {
			throw new ScanFileHolderException("Image data must be at least 2D");
		}
		if (dshape.length != srank) {
			int[] nshape = ShapeUtils.squeezeShape(dshape, true);
			if (nshape.length != srank) {
				logger.warn("Scan shape must be 2 dimensions less than image data");
			}
		}

		PositionIterator diter = new PositionIterator(dshape);
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
	 * @param v volume dataset
	 * @param w weight dataset
	 * @param axes axes Datasets
	 * @throws ScanFileHolderException
	 * @deprecated Use {{@link #saveOutput(String, MillerSpaceMapperBean, String, String, Dataset, Dataset, Dataset...)} instead
	 */
	@Deprecated(since="Dawn 2.25")
	public static void saveVolume(String file, MillerSpaceMapperBean bean, String entryPath, String volPath, Dataset v,
			Dataset w, Dataset... axes) throws ScanFileHolderException {
		logger.deprecatedMethod("saveVolume(String, MillerSpaceMapperBean, String, String, Dataset, Dataset, Dataset...)", null, "saveOutput(String, MillerSpaceMapperBean, String, String, Dataset, Dataset, String[], Dataset...)");
		saveOutput(file, bean, entryPath, volPath, v, w, null, axes);
	}

	/**
	 * Save output, weight and its axes to a HDF5 file
	 * @param file path for saving HDF5 file
	 * @param bean
	 * @param entryPath path to NXentry
	 * @param oPath name for NXdata
	 * @param o output dataset
	 * @param w weight dataset
	 * @param axesUnits axes units
	 * @param axes axes datasets
	 * @throws ScanFileHolderException
	 */
	public static void saveOutput(String file, MillerSpaceMapperBean bean, String entryPath, String oPath, Dataset o,
			Dataset w, String[] axesUnits, Dataset... axes) throws ScanFileHolderException {

		String oName = getOutputName(o.getRank());
		o.setName(oName);
		w.setName(WEIGHT_NAME);
		HDF5Utils.writeDataset(file, oPath, o);
		HDF5Utils.writeDataset(file, oPath, w);
		saveAxesAndAttributes(file, oPath, oName, new String[] {WEIGHT_NAME}, axesUnits, axes);
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

	public static void saveAxesAndAttributes(String file, String dataPath, String dataName, String[] auxSignals, String[] axesUnits, Dataset... axes)
			throws ScanFileHolderException {
		String[] axisNames = new String[axes.length];
		for (int i = 0; i < axes.length; i++) {
			Dataset x = axes[i];
			String xName = x.getName();
			axisNames[i] = xName;
			HDF5Utils.writeDataset(file, dataPath, x);
			if (axesUnits != null && i < axesUnits.length) {
				String units = axesUnits[i];
				if (units != null) {
					Dataset u = DatasetFactory.createFromObject(units);
					u.setName(NexusConstants.UNITS);
					HDF5Utils.writeAttributes(file, dataPath + Node.SEPARATOR + xName, false, u);
				}
			}
		}

		List<Dataset> attrs = new ArrayList<>();
		Dataset a;

		a = DatasetFactory.createFromObject(NexusConstants.DATA);
		a.setName(NexusConstants.NXCLASS);
		attrs.add(a);

		a = DatasetFactory.createFromObject(dataName);
		a.setName(NexusConstants.DATA_SIGNAL);
		attrs.add(a);

		if (auxSignals != null && auxSignals.length > 0) {
			a = DatasetFactory.createFromObject(auxSignals);
			a.setName(NexusConstants.DATA_AUX_SIGNALS);
			attrs.add(a);
		}

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
	 * @param trees
	 * @param allIters
	 * @param volumeOutput
	 * @param weightOutput
	 * @param parts
	 * @throws ScanFileHolderException
	 * @throws DatasetException
	 */
	private void mapToVolumeAndSaveInParts(Tree[] trees, PositionIterator[][] allIters,
			LazyWriteableDataset volumeOutput, LazyWriteableDataset weightOutput, int parts) throws ScanFileHolderException, DatasetException {
		int[] uShape = splitter.getShape();
		DoubleDataset[] datasets = splitter.getDatasets();
		DoubleDataset map = datasets[0];
		DoubleDataset weight = datasets[1];

		int l = uShape[0] - 1; // length of first dimension
		int[] oShape = uShape.clone();
		oShape[0] -= 1; // trim overlapping volume
		SliceND oSlice = new SliceND(vShape, null, oShape, null);
		int[] oStart = oSlice.getStart();
		int[] oStop = oSlice.getStop();
		double oMin = vMin[0];

		long process = 0;
		long save = 0;
		long start = -1;
		long now = -1;
		int nt = 0;

		SliceND begSlice = new SliceND(uShape, new Slice(1));
		SliceND midSlice = new SliceND(uShape, new Slice(0, -1));
		SliceND endSlice = new SliceND(uShape, new Slice(-1, null));
		start = System.currentTimeMillis();
		loadTimeTotal = 0;
		for (int p = 0; p < (parts-1); p++) {
			logger.info("Mapping in part: {}/{}", p+1, parts);
			// shift min/max
			vMin[0] = oMin + oStart[0] * vDel[0];
			vMax[0] = oMin + (oStart[0] + l) * vDel[0];

			logger.trace("First dimension: {} -> {}", vMin[0], vMax[0]);
			nt += mapAllTrees(trees, allIters);
			Dataset endMap = map.getSlice(endSlice);
			Dataset endWeight = weight.getSlice(endSlice);
			Maths.dividez(map, weight, map); // normalize by tally
			now = System.currentTimeMillis();
			process += now - start;

			start = now;
			try {
				logger.debug("Saving to {}", oSlice);
				volumeOutput.setSlice(map.getSliceView(midSlice), oSlice);
				weightOutput.setSlice(weight.getSliceView(midSlice), oSlice);
			} catch (DatasetException e) {
				logger.error("Could not save part of volume", e);
				throw new ScanFileHolderException("Could not save part of volume", e);
			}
			now = System.currentTimeMillis();
			save += now - start;

			start = now;
			map.fill(0);
			map.setSlice(endMap, begSlice);
			weight.fill(0);
			weight.setSlice(endWeight, begSlice);
			oStart[0] = oStop[0];
			oStop[0] = oStart[0] + l;
		}

		// last part
		logger.info("Mapping in last part: {}", parts);
		vMin[0] = oMin + oStart[0] * vDel[0];
		vMax[0] = oMin + (oStart[0] + l) * vDel[0];
		logger.trace("First dimension: {} -> {}", vMin[0], vMax[0]);

		nt += mapAllTrees(trees, allIters);
		Maths.dividez(map, weight, map); // normalize by tally
		now = System.currentTimeMillis();
		process += now - start;

		start = now;
		int vl = vShape[0];
		if (oStop[0] > vl) { // clip extra bit (when splitting to odd number of parts)
			oStop[0] = vl;
			oSlice.setSlice(0, oStart[0], oStop[0], 1);
			midSlice.setSlice(0, 0, oStop[0] - oStart[0], 1);
		}

		try {
			logger.debug("Saving to {}", oSlice);
			volumeOutput.setSlice(map.getSliceView(midSlice), oSlice);
			weightOutput.setSlice(weight.getSliceView(midSlice), oSlice);
		} catch (Exception e) {
			logger.error("Could not save last part of volume", e);
			throw new ScanFileHolderException("Could not save last part of volume", e);
		}
		save += System.currentTimeMillis() - start;

		logger.info("For {} threads, processing took {}s ({}ms/frame)", poolSize, process/1000, process/nt);
		logger.info("                loading {} frames took {}s ({}ms/frame)", nt, loadTimeTotal/1000, loadTimeTotal/nt);
		logger.info("Saving took {}ms", save);
	}

	private int mapAllTrees(Tree[] trees, PositionIterator[][] allIters) throws ScanFileHolderException, DatasetException {
		int nt = 0;

		int n = trees.length;
		for (int t = 0; t < n; t++) {
			logger.info("    Processing file {}/{}: {}", t + 1, n, bean.getInputs()[t]);
			int ni = mapToOutput(trees[t], allIters[t]);
			nt += ni;
			logger.info("        processed {} images", ni);
		}
		return nt;
	}

	private static final String INDICES = "indices";
	private static final String millerIndicesPath = TreeUtils.join(PROCESSED, INDICES);

	private void processTreesToList(Tree[] trees, PositionIterator[][] allIters)
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
			mapToAList(tree, allIters[i], lazy);
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
		MillerSpaceMapperBean iBean = MillerSpaceMapperBean.createBean(inputs, output, splitter, p, scale, mShape, mStart, mDelta, false);
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
		MillerSpaceMapperBean iBean = MillerSpaceMapperBean.createBeanWithAutoBox(inputs, output, splitter, p, scale, reduceToNonZero, false, mDelta);
		MillerSpaceMapper mapper = new MillerSpaceMapper(iBean);
		mapper.mapToOutputFile(isErrorTest);
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
		MillerSpaceMapperBean iBean = MillerSpaceMapperBean.createBeanWithAutoBox(inputs, output, splitter, p, scale, reduceToNonZero, true, qDelta);
		MillerSpaceMapper mapper = new MillerSpaceMapper(iBean);
		mapper.mapToOutputFile();
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
		mapper.mapToOutputFile();
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
		private int jNo;
		private BicubicInterpolator upScaler;
		private ImagePixelMapping mapping;
		private PixelSplitter pSplitter;
		private int start;
		private int end;
		private Dataset data;
		private int[] minLocal;
		private int[] maxLocal;
		private DatasetException exception;

		public JobConfig(int jNo, BicubicInterpolator upScaler, ImagePixelMapping mapping, PixelSplitter splitter, int start, int end, int[] vshape) {
			this.jNo = jNo;
			this.upScaler = upScaler;
			this.mapping = mapping;
			this.pSplitter = splitter;
			this.start = start;
			this.end = end;
			if (vshape != null) {
				this.minLocal = new int[vshape.length];
				this.maxLocal = new int[vshape.length];
				initializeOutputBoundingBox(vshape, minLocal, maxLocal);
			}
		}

		public int getNo() {
			return jNo;
		}

		public void setException(DatasetException e) {
			exception = e;
		}

		public DatasetException getException() {
			return exception;
		}

		public BicubicInterpolator getUpScaler() {
			return upScaler;
		}

		public ImagePixelMapping getMapping() {
			return mapping;
		}

		public PixelSplitter getSplitter() {
			return pSplitter;
		}

		public void setData(Dataset data) {
			this.data = data;
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

		public int[] getMinLocal() {
			return minLocal;
		}

		public int[] getMaxLocal() {
			return maxLocal;
		}
	}

	class ImageJobConfig {
		private Dataset image;
		private int[] pos;
		private int[] shape;

		public ImageJobConfig(Dataset image, int[] pos, int[] shape) {
			this.image = image;
			this.pos = pos.clone();
			this.shape = shape;
		}

		public ImageJobConfig() {
		}

		public int[] getPos() {
			return pos;
		}

		public int[] getShape() {
			return shape;
		}

		public Dataset getImage() {
			return image;
		}
	}

	private class QueueWorker implements Runnable {
		final private int wNo;
		final private BlockingQueue<ImageJobConfig> queue;
		final private BicubicInterpolator upSampler;
		final private ImagePixelMapping mapping;
		final private PixelSplitter splitter;
		final private int[] minLocal;
		final private int[] maxLocal;
		final private int[] iShape;
		final private int[] region;
		final private Dataset mask;
		final private Dataset trans;
		final private Tree tree;

		boolean running = true;
		private Thread thread;
		private int ni = 0;

		public QueueWorker(int wNo, BlockingQueue<ImageJobConfig> queue, BicubicInterpolator upSampler, ImagePixelMapping mapping, PixelSplitter splitter,
				Dataset mask, Dataset trans, Tree tree, int[] iShape, int[] oShape) {
			this.wNo = wNo;
			this.queue = queue;
			this.upSampler = upSampler;
			this.mapping = mapping;
			this.splitter = splitter;
			this.iShape = iShape;
			this.mask = mask;
			this.trans = trans;
			this.tree = tree;
			this.region = new int[] { 0, iShape[1], 0, iShape[0] };

			this.minLocal = new int[oShape.length];
			this.maxLocal = new int[oShape.length];
			initializeOutputBoundingBox(oShape, minLocal, maxLocal);
		}

		/**
		 * @return number of completed images
		 */
		public int interrupt() {
			thread.interrupt();
			return ni;
		}

		@Override
		public void run() {
			thread = Thread.currentThread();

			ImageJobConfig j;
			while (running) {
				try {
					j = queue.take();
				} catch (InterruptedException e) {
					running = false;
					break;
				}

				int[] pos = j.getPos();
				if (pos == null) {
					running = false;
					break;
				}
				logger.info("Worker {}: initializing mapping at {}/{}", wNo, Arrays.toString(pos), Arrays.toString(j.getShape()));
				initializeImagePixelMapping(mapping, tree, iShape, pos, false);

				double tFactor = getTransmissionCorrection(trans, pos);
				try {
					mapImageToOutput(wNo, tFactor, upSampler, mapping, splitter, minLocal, maxLocal, region, mask, j.getImage());
					logger.debug("Worker {}: finished mapping at {}", wNo, Arrays.toString(pos));
				} catch (Exception e) {
					logger.error("Worker {}: failed mapping at {}", wNo, Arrays.toString(pos), e);
				}
				ni++;
			}
		}

		public DoubleDataset[] getDatasets() {
			return splitter.getDatasets();
		}

		public int[] getMinLocal() {
			return minLocal;
		}

		public int[] getMaxLocal() {
			return maxLocal;
		}

		public boolean isRunning() {
			return running;
		}

		public int getNo() {
			return wNo;
		}
	}
}

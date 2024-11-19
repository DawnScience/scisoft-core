/*-
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.rixs;

import java.io.File;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.eclipse.dawnsci.analysis.api.persistence.IPersistenceService;
import org.eclipse.dawnsci.analysis.api.persistence.IPersistentNodeFactory;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationDataForDisplay;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationLog;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.IOperationModel;
import org.eclipse.dawnsci.analysis.api.roi.IRectangularROI;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Node;
import org.eclipse.dawnsci.analysis.api.tree.NodeLink;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.api.tree.TreeUtils;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceInformation;
import org.eclipse.dawnsci.nexus.NexusConstants;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Comparisons;
import org.eclipse.january.dataset.Comparisons.Monotonicity;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.LongDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.Operations;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.dataset.UnaryOperation;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.UnitMetadata;

import si.uom.NonSI;
import uk.ac.diamond.osgi.services.ServiceProvider;
import uk.ac.diamond.scisoft.analysis.MultiRange;
import uk.ac.diamond.scisoft.analysis.dataset.function.Histogram;
import uk.ac.diamond.scisoft.analysis.dataset.function.RegisterNoisyData1D;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Quadratic;
import uk.ac.diamond.scisoft.analysis.fitting.functions.StraightLine;
import uk.ac.diamond.scisoft.analysis.image.ImageUtils;
import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.io.NexusTreeUtils;
import uk.ac.diamond.scisoft.analysis.processing.operations.MetadataUtils;
import uk.ac.diamond.scisoft.analysis.processing.operations.backgroundsubtraction.SubtractFittedBackgroundOperation;
import uk.ac.diamond.scisoft.analysis.processing.operations.rixs.RixsBaseModel.ENERGY_DIRECTION;
import uk.ac.diamond.scisoft.analysis.processing.operations.rixs.RixsImageReductionBaseModel.CORRELATE_ORDER;
import uk.ac.diamond.scisoft.analysis.processing.operations.rixs.RixsImageReductionBaseModel.CORRELATE_PHOTON;
import uk.ac.diamond.scisoft.analysis.processing.operations.rixs.RixsImageReductionBaseModel.ENERGY_OFFSET;
import uk.ac.diamond.scisoft.analysis.processing.operations.rixs.RixsImageReductionBaseModel.XIP_OPTION;
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;

public abstract class RixsImageReductionBase<T extends RixsImageReductionBaseModel> extends RixsBaseOperation<T> {

	private double[][] energyFitCoefficients = new double[2][];
	private Dataset totalSum = null; // dataset of all event sums (so far)
	private List<Dataset> allSums = new ArrayList<>(); // list of dataset of event sums in each image
	private List<Dataset> allOffsets = new ArrayList<>(); // list of dataset of event base offsets in each image
	private List<Dataset> allPositions = new ArrayList<>(); // list of dataset of event coords in each image
	private List<Dataset> allRawPositions = new ArrayList<>(); // list of dataset of raw event coords in each image
	@SuppressWarnings("unchecked")
	private List<Dataset>[] allSpectra = new List[] {new ArrayList<>(), new ArrayList<>()};
	private List<Dataset> allHistos = new ArrayList<>();

	protected String currentDataFile = null;
	private MultiRange selection;
	private String centroidFilePath;
	Map<Double, Dataset> xLookup, yLookup;
	Dataset xBase;
	Dataset yBase;
	Dataset normValues = null;

	/**
	 * Auxiliary subentry. This must match the name field defined in the plugin extension
	 */
	public static final String PROCESS_NAME = "RIXS image reduction";

	private static final String ENERGY_LOSS = "Energy loss";
	private static final String SPECTRUM_PREFIX = "spectrum_";
	private static final String SPECTRA_PREFIX = "spectra_";

	@Override
	public String getId() {
		return getClass().getName();
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}

	@Override
	void updateFromModel(boolean throwEx, String name) {
		try {
			selection = MultiRange.createMultiRange(model.getFrameSelection());
		} catch (IllegalArgumentException e) {
			if (throwEx) {
				throw new OperationException(this, "Frame selection invalid", e);
			}
		}

		Arrays.fill(energyFitCoefficients, null);
		energyFitCoefficients[0] = model.getEnergyDispersion();
		isIVE = false; // default to coefficients for fit of energy versus intercept
		if (energyFitCoefficients[0] == null) {
			String file = model.getCalibrationFile();
			if (file == null && throwEx) {
				throw new OperationException(this, "Energy dispersion calibration must be defined");
			}
			// energy dispersion in terms of pixel/eV
			if (file != null) {
				DoubleDataset tmp = parseForCalibration(log, file).cast(DoubleDataset.class);
				UnitMetadata um = tmp.getFirstMetadata(UnitMetadata.class);
				isIVE = !(um != null && um.getUnit().equals(NonSI.ELECTRON_VOLT)); // could also check process for intercept_energy_fit_ cf energy_intercept_fit_

				tmp.squeeze();
				int rmax = tmp.getShapeRef()[0];
				for (int r = 0; r < rmax; r++) {
					DoubleDataset res = tmp.getSlice(new Slice(r, r +1)).cast(DoubleDataset.class);
					energyFitCoefficients[r] = res.getData(); 
				}
			}
		}
		if (energyFitCoefficients[1] == null) { // assume first entry is fine
			energyFitCoefficients[1] = energyFitCoefficients[0];
		}

		String lookup = model.getCentroidLookupFile();
		if (lookup == null) {
			centroidFilePath = null;
			xLookup = null;
			yLookup = null;
		} else if (!lookup.equals(centroidFilePath)) {
			try {
				initializeLookupTables(lookup, model.getEnergyDirection() == ENERGY_DIRECTION.FAST);
			} catch (Exception e) {
				if (throwEx) {
					throw new OperationException(this, "Could not load centroid correction tables", e);
				}
			}
		}

		updateROICount();
	}

	private void initializeLookupTables(String lookupFile, boolean xAsFirstDimension) throws Exception {
		CSVParser parser = CSVParser.parse(new File(lookupFile), StandardCharsets.US_ASCII, CSVFormat.DEFAULT);
		boolean isX = false;
		boolean isY = false;
		Map<Double, Dataset> lXLookup = new HashMap<>();
		Map<Double, Dataset> lYLookup = new HashMap<>();
		int xLength = 0;
		int yLength = 0;
		for (CSVRecord r : parser) {
			int n = r.size();
			if (n == 1) {
				String dirn = r.get(0);
				if (dirn.equalsIgnoreCase("x")) {
					isX = true;
					isY = false;
				} else if (dirn.equalsIgnoreCase("y")) {
					isX = false;
					isY = true;
				}
			} else if (n > 0 && (isX || isY)) {
					double e = Double.parseDouble(r.get(0));
					double[] values = new double[n - 1];
					int j = 0;
					for (int i = 1; i < n; i++) {
						values[j++] = Double.parseDouble(r.get(i)) + 0.5; // shift back half-pixel as used for histogram
					}
					Dataset d = DatasetFactory.createFromObject(values);
					if (isX) {
						xLength = d.getSize();
						lXLookup.put(e, d);
					} else {
						yLength = d.getSize();
						lYLookup.put(e, d);
					}
				
			}
		}

		if (xAsFirstDimension) {
			xLookup = lXLookup;
			yLookup = lYLookup;
			xBase = DatasetFactory.createLinearSpace(DoubleDataset.class, 0, 1, xLength);
			yBase = DatasetFactory.createLinearSpace(DoubleDataset.class, 0, 1, yLength);
		} else {
			xLookup = lYLookup;
			yLookup = lXLookup;
			xBase = DatasetFactory.createLinearSpace(DoubleDataset.class, 0, 1, yLength);
			yBase = DatasetFactory.createLinearSpace(DoubleDataset.class, 0, 1, xLength);
		}
		centroidFilePath = lookupFile;
	}

	@Override
	protected void resetProcess(IDataset original, int total) {
		totalSum = null;
		resetList(allOffsets, total);
		resetList(allPositions, total);
		resetList(allRawPositions, total);
		resetList(allSums, total);
		resetList(allHistos, total);
		for (int i = 0; i < 2; i++) {
			resetList(allSpectra[i], total);
		}
		currentDataFile = null;

		normValues = null;
		String normPath = model.getNormalizationPath();
		if (normPath != null && !normPath.isEmpty()) {
			SliceFromSeriesMetadata smd = original.getFirstMetadata(SliceFromSeriesMetadata.class);
			initializeNormDataset(smd.getFilePath(), normPath, smd.getSliceInfo());
		}
	}

	// TODO also need to read from OperationMetadata for previous SubtractFittedBackgroundModel
	// and its regions
	protected void initializeROIsFromFile(String file) {
		if (file == null) {
			return;
		}
		try {
			Tree tree = LoaderFactory.getData(file).getTree();
			IPersistenceService service = ServiceProvider.getService(IPersistenceService.class);
			IPersistentNodeFactory pf = service.getPersistentNodeFactory();
			for (IOperation<?,?> o : pf.readOperationsFromTree(tree)) {
				IOperationModel m = o.getModel();
				if (m instanceof RixsBaseModel rbm) {
					model.internalSetRoiA(rbm.getRoiA());
					model.internalSetRoiB(rbm.getRoiB());
					break;
				}
			}
		} catch (Exception e) {
			String msg = String.format("Cannot load file '%s' with ROIs", file);
			throw new OperationException(this, msg, e);
		}
	}

//	@Override
//	protected void parseNXrixs(GroupNode detector, double pEnergy) {
//		// TODO read energy_dispersion
//		super.parseNXrixs(detector, pEnergy);
//		if (Double.isNaN(energyDispersion)) {
//			// energy dispersion in terms of eV/pixel
//			energyDispersion = NexusTreeUtils.parseDoubleArray(detector.getDataNode("energy_dispersion"))[0];
//		}
//	}
//

	private final static String PROCESS_RESULT = "result";

	private static Dataset parseForCalibration(OperationLog log, String elasticLineFile) {
		try {
			Tree t = LoaderFactory.getData(elasticLineFile).getTree();

			GroupNode root = t.getGroupNode();
			GroupNode entry = NexusTreeUtils.findFirstEntryWithProcess(root);

			GroupNode rg = entry.getGroupNode(PROCESS_RESULT);
			if (rg == null) {
				throw new NexusException("File does not contain a result group");
			}

			Dataset data = NexusTreeUtils.getDataset(PROCESS_RESULT, rg, null, "data");
			if (data == null) {
				throw new NexusException("File does not contain a result dataset");
			}

			// GroupNode pg = ProcessingUtils.checkForProcess(this, entry, ElasticLineReduction.PROCESS_NAME);
			// DataNode pnd = pg.getDataNode("data");
			// String model = NexusTreeUtils.getFirstString(pnd);
			return data; // model.contains("fitQuadratic") ? data : Maths.reciprocal(data); // Old file has dispersion
		} catch (Exception e) {
			log.appendFailure("Could not parse Nexus file %s:%s", elasticLineFile, e);
		}

		return DatasetFactory.zeros(2, 1).fill(-1);
	}

	@Override
	IDataset processImageRegion(int sn, IDataset original, int rn, Dataset in) {
		Dataset[] result = makeSpectrum(rn, in);
		Dataset spectrum = result[1];
		spectrum.setName(SPECTRA_PREFIX + rn);

		spectrum.clearMetadata(AxesMetadata.class);
		Dataset e = createEnergyScale(isIVE, DatasetFactory.createRange(result[1].getSize()), xrayEnergy,
				getZeroEnergyOffset(rn), result[0].getDouble(), energyFitCoefficients[rn]);

		MetadataUtils.setAxes(this, spectrum, e);
		auxData.add(spectrum.getView(true));
		allSpectra[rn].set(sn, spectrum.getView(true));
		return spectrum;
	}

	@Override
	boolean skipFrame(int size, int frame) {
		return !selection.contains(size, frame);
	}

	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		Dataset original = DatasetUtils.convertToDataset(input);
		SliceFromSeriesMetadata ssm = getSliceSeriesMetadata(input);
		SliceInformation si = ssm.getSliceInfo();

		OperationData od = super.process(original, monitor);
		List<Dataset> events = null;
		Dataset eSum = null;
		if (od != null) { // find photon events in entire image
			Dataset o = original;
			if (o.min().doubleValue() < 0) {
				log.appendFailure("Clipping original image as it has negative values");
				o = Maths.clip(o, 0, Double.POSITIVE_INFINITY);
			}
			events = ImageUtils.findWindowedPeaks(o, model.getWindow(), countsPerPhoton * model.getLowThreshold(), countsPerPhoton * model.getHighThreshold());
			eSum = events.get(0);
		} else {
			log.appendFailure("Skipping frame %d", si.getSliceNumber());
		}

		int sn = si.getSliceNumber();
		if (eSum == null || eSum.getSize() == 0) {
			log.appendFailure("No events found");
			allSums.set(sn, null);
			allOffsets.set(sn, null);
			allRawPositions.set(sn, null);
			allPositions.set(sn, null);
		} else {
			// accumulate event sums and photons
			if (totalSum == null) {
				totalSum = eSum;
			} else {
				totalSum = DatasetUtils.concatenate(new IDataset[] {totalSum, eSum}, 0);
			}
			log.appendSuccess("Found %d photon events, current total = %s", eSum.getSize(), totalSum.getSize());
			allSums.set(sn, eSum);
			allOffsets.set(sn, events.get(4));
			Dataset posn = events.get(1);
			if (xLookup != null) {
				allRawPositions.set(sn, posn);
				posn = correctCentroid(posn);
			}
			allPositions.set(sn, posn);
			if (model.isSaveAllPositions()) {
				Dataset values = events.get(3);
				List<Dataset> hs = SubtractFittedBackgroundOperation.createHistogram(values, true, 1);
				Dataset h = hs.get(0);
				Dataset x = hs.get(1).getSliceView(new Slice(-1));
				System.out.printf("Image %d: found pixel values in [%d, %d]\n", sn, x.getInt(), x.getInt(-1));
				MetadataUtils.setAxes(this, h, x);
				allHistos.set(sn, h);
			}

		}

		IntegerDataset bins = null;
		Dataset a = null;
		Dataset h = null;
		if (totalSum != null) {
			double min = totalSum.min(true).doubleValue();
			double max = Math.max(min + 1, totalSum.max(true).doubleValue()); // ensure that we get at least two bins
			bins = DatasetFactory.createRange(IntegerDataset.class, min, max + 1, 1);
			bins.setName("Event sum");
			Histogram histo = new Histogram(bins);
			h = histo.value(totalSum).get(0);
			h.setName("Number of events");
	
			a = Maths.log10(h);
			a.setName("Log10 of " + h.getName());
			MetadataUtils.setAxes(this, a, bins);
			displayData.add(a);
		}

		log.appendSuccess("At frame %d/%d", si.getSliceNumber(), si.getTotalSlices());
		summaryData.clear(); // do not save anything yet

		if (si.isLastSlice()) {
			addSummaryData();
			if (a != null) {
				summaryData.add(a);
			}

			processAccumulatedDataOnLastSlice(ssm.getFilePath(), ssm.getDatasetName(), original.getShapeRef(), si.getTotalSlices(), bins, h);
		}

		OperationDataForDisplay odd;
		if (od instanceof OperationDataForDisplay tod) {
			odd = tod;
		} else {
			odd = new OperationDataForDisplay(od);
		}
		odd.setShowSeparately(true);
		odd.setLog(log);
		odd.setDisplayData(displayData.toArray(new IDataset[displayData.size()]));
		odd.setAuxData(auxData.toArray(new Serializable[auxData.size()]));
		odd.setSummaryData(summaryData.toArray(new Serializable[summaryData.size()]));
		return odd;
	}

	private Dataset correctCentroid(Dataset posn) {
		Dataset correction = getCorrection(xLookup);
		Dataset oldX = posn.getSlice((Slice) null, new Slice(1)).squeeze();
		Dataset newX = correctCoord(xBase, correction, oldX);

		correction = getCorrection(yLookup);
		Dataset oldY = posn.getSlice((Slice) null, new Slice(1,2)).squeeze();
		Dataset newY = correctCoord(yBase, correction, oldY);

		newX.setShape(-1,1);
		newY.setShape(-1,1);
		return DatasetUtils.concatenate(new Dataset[] {newX, newY}, 1);
	}

	private Dataset correctCoord(Dataset base, Dataset correction, Dataset coords) {
		if (correction == null) {
			return coords;
		}
		coords.isubtract(0.5); // same half-pixel shift as used in position histogram
		IntegerDataset iCoords = DatasetUtils.copy(IntegerDataset.class, coords);
		coords.isubtract(iCoords);
		Dataset nCoords = Maths.interpolate(base, correction, coords, null, null);
		return nCoords.iadd(iCoords);
	}

	private Dataset getCorrection(Map<Double, Dataset> lookup) {
		double delta = Double.POSITIVE_INFINITY;
		Dataset found = null;
		for (Entry<Double, Dataset> e : lookup.entrySet()) {
			double d = Math.abs(e.getKey() - xrayEnergy);
			if (d < delta) {
				delta = d;
				found = e.getValue();
			}
		}
		return found;
	}

	private static final String XCAM_XIP_FILENAME = "xcam-xpi%d-%d.hdf"; // eg. xcam-xip2-143781.hdf
	private static final String XCAM_XIP_DATA = "/entry/data/data"; // or "/entry/instrument/detector/data"

//	private static final String XCAM_XIP_PROCESSED_DATA = "/entry_1/processed%d/centroids/data";
	private static final String XCAM_XIP_PROCESSED_DATA = "xip%d";

	protected List<Dataset> readXIPEventsFromExternalFile(String path, int i) {
		int l = path.lastIndexOf(File.separator) + 1;
		String name = l == 0 ? path : path.substring(l);

		Integer scan = ProcessingUtils.getScanNumber(name);
		if (scan == null) {
			throw new OperationException(this, "Current file path does not end with scan number (before file extension)");
		}
		String xipPath = path.substring(0, l).concat(String.format(XCAM_XIP_FILENAME, i, scan));

		if (!new File(xipPath).exists()) {
			return parseXIPEvents(this, log, xipPath, XCAM_XIP_DATA);
		}
		return Collections.emptyList();
	}

	protected List<Dataset> readXIPEventsFromCurrentFile(String path, String dataPath, int i) {
		int l = dataPath.lastIndexOf(Node.SEPARATOR) + 1; // expected to be in same group
		if (l > 0) {
			dataPath = dataPath.substring(0, l);
		}
		return parseXIPEvents(this, log, path, dataPath + String.format(XCAM_XIP_PROCESSED_DATA, i));
	}

	/**
	 * Parse data from XIP Area Detector plugin provided by XCAM
	 * @param log
	 * @param filePath
	 * @param dataPath
	 * @return sum, centroid xy, eta-corrected xy, eta & iso-linear corrected y
	 */
	static List<Dataset> parseXIPEvents(IOperation<?, ?> op, OperationLog log, String filePath, String dataPath) {
		try {
			Tree t = ProcessingUtils.getTree(op, filePath);

			Node n = TreeUtils.getNode(t, dataPath);
			if (n == null) {
				log.appendFailure("Could not parse Nexus file %s to find %s", filePath, dataPath);
			} else if (n instanceof DataNode d) {
				// xip is [F,N,7]
				// 0,1: x,y centroids w/o eta correction
				// 2,3: x,y centroids w/ eta correction
				// 4  : y w/ eta and iso-linear corrections
				// 5  : 3x3 sum
				// 6  : algorithm mode (0 = 3x3; 1 = 2x2; 2 = both)
				Dataset xip = DatasetUtils.sliceAndConvertLazyDataset(d.getDataset());

				// list of their sum, centroids (as coordinates in N x rank dataset), centre fraction, all pixel values,
				// and position of clashes (where other pixels in the window are equal to the maximum)
				List<Dataset> results = new ArrayList<>();
				int r = xip.getRank();
				Slice[] slice = new Slice[r];
				r--;
				slice[r] =  new Slice(5, 6);
				results.add(xip.getSliceView(slice).squeezeEnds());
				slice[r] =  new Slice(2);
				results.add(xip.getSliceView(slice).squeezeEnds());
				slice[r] =  new Slice(2, 4);
				results.add(xip.getSliceView(slice).squeezeEnds());
				slice[r] =  new Slice(4, 5);
				results.add(xip.getSliceView(slice).squeezeEnds());
				return results;
			} else {
				log.appendFailure("Could not parse Nexus file %s to find %s", filePath, dataPath);
			}
		} catch (Exception e) {
			log.appendFailure("Could not parse Nexus file %s:%s", filePath, e);
		}

		return Collections.emptyList();
	}

	protected void processAccumulatedDataOnLastSlice(String filePath, String dataPath, int[] shape, int smax, IntegerDataset bins, Dataset h) {
		int bin = 0;
		int bmax = 0;
		int single = 0;
		int multiple = 0;
		XIP_OPTION xipOpt = model.getXIPOption();
		int nr = roiMax;

		if (xipOpt != XIP_OPTION.DONT_USE) {
			nr += 2 * roiMax; // for raw and eta-corrected centroids
		}

		int[][][] allSingle = new int[nr][smax][]; // [number of regions, number of slices, number of bins]
		int[][][] allMultiple = new int[nr][smax][];

		if (bins != null) {
			// After last image, calculate splitting levels
			Dataset x = bins.getSlice(new Slice(-1));
			Dataset dh = Maths.derivative(x, h, 3);
			MetadataUtils.setAxes(this, dh, bins);
			displayData.set(0, dh);
			List<Double> z = DatasetUtils.crossings(x, dh, 0);
			log.append("Histogram derivative zero-crossings = %s", z.subList(0, Math.max(5, z.size())));
			single = (int) Math.floor(z.get(0));
			multiple = single + countsPerPhoton; // (int) Math.floor(z.get(1)); // TODO
			log.appendSuccess("Setting limits for single photon as [%d, %d)", single, multiple);
			summaryData.add(ProcessingUtils.createNamedDataset(single, "single_photon_minimum"));
			summaryData.add(ProcessingUtils.createNamedDataset(multiple, "multiple_photon_minimum"));

			bin = model.getBins();
			bmax = bin * shape[model.getEnergyIndex()];

			// per image, separate by sum
			List<Double> cX = new ArrayList<>();
			List<Double> cY = new ArrayList<>();
			List<Double> rX = new ArrayList<>();
			List<Double> rY = new ArrayList<>();
			List<Integer> cV = new ArrayList<>();
			boolean save = true;
			boolean all = model.isSaveAllPositions();
			for (int r = 0; r < roiMax; r++) {
				cX.clear();
				cY.clear();
				cV.clear();
				rX.clear();
				rY.clear();
				int j = 0;
				int imax = allSums.size();
				int[] events = new int[imax];
				for (int i = 0; i < imax; i++) {
					Dataset sums = allSums.get(i);
					if (sums == null) {
						continue;
					}
					Dataset posn = allPositions.get(i);
					Dataset rawPosn = xLookup == null ? null : allRawPositions.get(i);

					int[] hSingle = allSingle[r][j] = new int[bmax];
					int[] hMultiple = allMultiple[r][j] = new int[bmax];
					StraightLine line = getStraightLine(r);
					IRectangularROI roi = getROI(r);
					int hits = shiftAndBinPhotonEvents(false, 0, 0, single, multiple, bin, bmax, save, cX, cY, cV, line, roi, sums,
							posn, hSingle, hMultiple, rX, rY, rawPosn);

					log.append("Binned %d events in region %d from %d events in frame %d", hits, r, sums.getSize(), i);
					events[i] = hits;

					if (save && !all) { // then only save photon data from first frame and first region
						save = false;
					}
					j++;
				}
				int side = cX.size();
				if (side > 0) {
					Dataset t;
					t = DatasetFactory.createFromList(cY);
					MetadataUtils.setAxes(this, t, ProcessingUtils.createNamedDataset(DatasetFactory.createFromList(cX), "x"));
					t.setName("photon_positions_" + r);
					summaryData.add(t);
					t = DatasetFactory.createFromList(cV);
					t.setName("photon_values_" + r);
					summaryData.add(t);
					if (xLookup != null) {
						t = DatasetFactory.createFromList(rY);
						MetadataUtils.setAxes(this, t, ProcessingUtils.createNamedDataset(DatasetFactory.createFromList(rX), "x"));
						t.setName("photon_raw_positions_" + r);
						summaryData.add(t);
					}
					t = DatasetFactory.createFromObject(events);
					t.setName("photon_counts_" + r);
					summaryData.add(t);
				}

				if (j < smax) { // truncate for omitted frames
					allSingle[r] = Arrays.copyOf(allSingle[r], j);
					allMultiple[r] = Arrays.copyOf(allMultiple[r], j);
				}
			}
		}

		if (xipOpt != XIP_OPTION.DONT_USE) {
			boolean[][] regionInCCDs = findRegionsInCCDs(shape); // [2, number of regions] if true then ROI is in given CCD

			@SuppressWarnings("unchecked")
			List<Double>[] cXs = new List[2 * roiMax];
			@SuppressWarnings("unchecked")
			List<Double>[] cYs = new List[2 * roiMax];
			@SuppressWarnings("unchecked")
			List<Integer>[] cVs = new List[2 * roiMax];
			for (int r = 0; r < 2*roiMax; r++) {
				cXs[r] = new ArrayList<>();
				cYs[r] = new ArrayList<>();
				cVs[r] = new ArrayList<>();
			}

			boolean save = true;
			boolean all = model.isSaveAllPositions();
			double offsetX = shape[1]/2; // correct for XIP coords being relative to CCD
			for (int s = 0; s < 2; s++) {
				int nx = s + 1;
				List<Dataset> xip = xipOpt == XIP_OPTION.USE_EXTERNAL ? readXIPEventsFromExternalFile(filePath, nx) : readXIPEventsFromCurrentFile(filePath, dataPath, nx);
				if (xip.isEmpty()) {
					continue;
				}

				Dataset sums = xip.get(0);
				for (int r = 0; r < roiMax; r++) {
					if (!regionInCCDs[s][r]) {
						continue;
					}

					StraightLine line = getStraightLine(r);
					IRectangularROI roi = getROI(r);

					for (int q = 1; q < 3; q++) { // process both centroid and eta-corrected centroid
						Dataset posn = xip.get(q);
						int j = 2*r + q - 1;
						List<Double> cX = cXs[j];
						List<Double> cY = cYs[j];
						List<Integer> cV = cVs[j];
	
						int[] hSingle = allSingle[j + roiMax][0];
						if (hSingle == null) {
							allSingle[j + roiMax][0] = hSingle = new int[bmax];
						}

						int hits;
						if (all || !save) {
							hits = shiftAndBinPhotonEvents(true, s*offsetX, 0, 1, Integer.MAX_VALUE, bin, bmax, save,
									cX, cY, cV, line, roi, sums, posn, hSingle, null, null, null, null);
						} else { // split first frame
							Slice slice = new Slice(1);
							hits = shiftAndBinPhotonEvents(true, s*offsetX, 0, 1, Integer.MAX_VALUE, bin, bmax, true,
									cX, cY, cV, line, roi, sums.getSliceView(slice), posn.getSliceView(slice), hSingle, null, null, null, null);
							slice = new Slice(1, null, null);
							hits += shiftAndBinPhotonEvents(true, s*offsetX, 0, 1, Integer.MAX_VALUE, bin, bmax, false,
									cX, cY, cV, line, roi, sums.getSliceView(slice), posn.getSliceView(slice), hSingle, null, null, null, null);
						}
						log.append("Binned %d events in region %d from XIP %d", hits, r, s);
					}
					if (save && !all) { // then only save photon data from first frame and first region
						save = false;
					}
				}
			}
			for (int n = 0; n < 2*roiMax; n++) {
				List<Double> cX = cXs[n];
				List<Double> cY = cYs[n];
				List<Integer> cV = cVs[n];
				int side = cX.size();
				if (side == 0) {
					continue;
				}
				Dataset t;
				t = DatasetFactory.createFromList(cY);
				MetadataUtils.setAxes(this, t, ProcessingUtils.createNamedDataset(DatasetFactory.createFromList(cX), "x"));
				int r = n / 2;
				if (n % 2 == 0) {
					log.append("Saving %d non-zero events from XIP for region %d", side, r);
					t.setName("xip_photon_positions_" + r);
				} else {
					log.append("Saving %d non-zero eta-corrected events from XIP for region %d", side, r);
					t.setName("xip_eta_photon_positions_" + r);
				}
				summaryData.add(t);
				t = DatasetFactory.createFromList(cV);
				t.setName("xip_photon_values_" + r);
				summaryData.add(t);
			}

		}

		for (int r = 0; r < roiMax; r++) {
			// total and correlated spectra
			Dataset sp = null;
			Dataset[] sArray = toArray(allSpectra[r]);
			sp = accumulate(sArray);
			if (sp == null) {
				continue;
			}
			sp.setName("total_spectrum_" + r);
			summaryData.add(sp);

			int ind = model.getCorrelateOrder() == CORRELATE_ORDER.FIRST ? 0 : sArray.length - 1;
			AxesMetadata am = sArray[ind].getFirstMetadata(AxesMetadata.class);
			Dataset ax = null;
			try {
				if (am != null) {
					ax = DatasetUtils.sliceAndConvertLazyDataset(am.getAxis(0)[0]);
				}
			} catch (Exception e) {
			}

			RegisterNoisyData1D reg = createCorrelateShifter();
			double[] eRange = model.getEnergyRange();
			if (eRange != null && ax != null) {
				Arrays.sort(eRange);
				Monotonicity m = Comparisons.findMonotonicity(ax);
				RectangularROI rect = null;
				if (m == Monotonicity.NONDECREASING || m == Monotonicity.STRICTLY_INCREASING) {
					int beg = DatasetUtils.findIndexGreaterThanOrEqualTo(ax, eRange[0]);
					int end = DatasetUtils.findIndexGreaterThan(ax, eRange[1]);
					rect = new RectangularROI(beg, 0, end - beg, 0, 0);
				} else if (m == Monotonicity.STRICTLY_DECREASING || m == Monotonicity.NONINCREASING) {
					int beg = DatasetUtils.findIndexLessThanOrEqualTo(ax, eRange[1]);
					int end = DatasetUtils.findIndexLessThan(ax, eRange[0]);
					rect = new RectangularROI(beg, 0, end - beg, 0, 0);
				}

				reg.setRectangle(rect);
			}

			List<Double> shift = new ArrayList<>();
			Dataset[] cSpecs = correlateSpectra("", r, reg, shift, ax, sArray);
			if (normValues != null) {
				Dataset usedNormValues = normValues.getByBoolean(getUsedFrames());
				summaryData.add(ProcessingUtils.createNamedDataset(usedNormValues, "normalization"));
				Dataset nSpectra = Maths.divide(cSpecs[0], usedNormValues.reshape(-1, 1));
				nSpectra.setName("normalized_correlated_spectra_" + r);
				MetadataUtils.setAxes(this, nSpectra, null, ax);
				summaryData.add(nSpectra);
				Dataset nSpectrum = Maths.divide(cSpecs[1], usedNormValues.sum());
				nSpectrum.setName("normalized_correlated_spectrum_" + r);
				MetadataUtils.setAxes(this, nSpectrum, ax);
				summaryData.add(nSpectrum);
			}

			if (bins == null) {
				continue; // no photon events!!!
			}

			double el0 = getZeroEnergyOffset(r); // elastic line intercept
			final Dataset energies = createEnergyScale(bmax, bin, xrayEnergy, el0, energyFitCoefficients[r]);

			Dataset t = DatasetFactory.createFromObject(allSingle[r]);
			t.setName("single_photon_spectra_" + r);
			MetadataUtils.setAxes(this, t, null, energies);
			Dataset sSpectra = t;
			summaryData.add(t);

			Dataset nf = t.sum(1); // to work out per-image as single fraction of total events
			nf.setName("single_photon_count_" + r);
			Dataset sEvents = nf;
			summaryData.add(nf);

			t = DatasetFactory.createFromObject(allMultiple[r]);
			t.setName("multiple_photon_spectra_" + r);
			MetadataUtils.setAxes(this, t, null, energies);
			Dataset mSpectra = t;
			summaryData.add(t);

			t = t.sum(1);
			t.setName("multiple_photon_count_" + r);
			Dataset mEvents = t;
			summaryData.add(t);

			t = Maths.add(t, nf);
			nf = Maths.divide(nf.cast(DoubleDataset.class), t);
			nf.setName("single_events_fraction_" + r);
			summaryData.add(nf);

			double ts = ((Number) sEvents.sum()).doubleValue();
			double tm = ((Number) mEvents.sum()).doubleValue();
			double tt = ts + tm;
			double sf = tt == 0 ? 0 : ts/tt;
			log.appendSuccess("Events: single/total = %g/%g = %g", ts, tt, sf);
			summaryData.add(ProcessingUtils.createNamedDataset(sf, "single_events_total_fraction_" + r));

			sp = sSpectra.sum(0);
			sp.setName("single_photon_spectrum_" + r);
			MetadataUtils.setAxes(this, sp, energies);
			summaryData.add(sp);

			sp = mSpectra.sum(0);
			sp.setName("multiple_photon_spectrum_" + r);
			MetadataUtils.setAxes(this, sp, energies);
			summaryData.add(sp);

			if (model.getCorrelateOption() == CORRELATE_PHOTON.USE_INTENSITY_SHIFTS) {
				for (int i = 0, imax = allSingle[r].length; i < imax; i++) { // image to image shifts
					double offset = shift.get(i);
					if (Double.isNaN(offset)) {
						continue;
					}
					int[] hSingle = new int[bmax];
					int[] hMultiple = new int[bmax];
					allSingle[r][i] = hSingle; // need to repopulate as previous arrays are referenced in datasets
					allMultiple[r][i] = hMultiple;
					StraightLine line = getStraightLine(r);
					IRectangularROI roi = getROI(r);
					Dataset sums = allSums.get(i);
					if (sums != null) {
						Dataset posn = allPositions.get(i);
						shiftAndBinPhotonEvents(false, 0, offset, single, multiple, bin, bmax, false, null, null, null, line, roi, sums,
								posn, hSingle, hMultiple, null, null, null);
					}
				}

				summarizePhotonSpectra("single_photon_", allSingle, r, energies);
				summarizePhotonSpectra("multiple_photon_", allMultiple, r, energies);
			} else {
				correlateSpectra("single_photon_", r, reg, shift, energies, sSpectra);
				correlateSpectra("multiple_photon_", r, reg, shift, energies, mSpectra);
			}
		}

		for (int r = 0; r < roiMax; r++) { // add XIP to summary data
			double el0 = getZeroEnergyOffset(r); // elastic line intercept
			final Dataset energies = createEnergyScale(bmax, bin, xrayEnergy, el0, energyFitCoefficients[r]);

			int xr = roiMax + 2 * r;
			int[] spectrum = allSingle[xr][0];
			if (spectrum != null) {
				Dataset sp = DatasetFactory.createFromObject(spectrum);
				if (sp.max().intValue() > 0) {
					sp.setName("xip_photon_spectrum_" + r);
					MetadataUtils.setAxes(this, sp, energies);
					summaryData.add(sp);
				}
			}
			spectrum = allSingle[xr + 1][0];
			if (spectrum != null) {
				Dataset sp = DatasetFactory.createFromObject(spectrum);
				if (sp.max().intValue() > 0) {
					sp.setName("xip_eta_photon_spectrum_" + r);
					MetadataUtils.setAxes(this, sp, energies);
					summaryData.add(sp);
				}
			}
		}

		if (model.isSaveAllPositions()) { // add total histogram to summary data
			int min = Integer.MAX_VALUE;
			int max = -1;
			for (Dataset hi : allHistos) {
				if (hi != null) {
					Dataset x = MetadataUtils.getAxes(hi)[0];
					min = Math.min(min, x.getInt());
					max = Math.max(max, x.getInt(-1));
				}
			}

			IntegerDataset intensity = DatasetFactory.createRange(IntegerDataset.class, min, max + 1, 1);
			intensity.setName("intensity");
			LongDataset totalHistogram = DatasetFactory.zeros(LongDataset.class, intensity.getSize());
			totalHistogram.setName("event_histogram");
			for (Dataset hi : allHistos) {
				if (hi != null) {
					Dataset x = MetadataUtils.getAxes(hi)[0];
					int beg = x.getInt();
					int end = x.getInt(-1) + 1;
					totalHistogram.getSliceView(new Slice(beg - min, end - min)).iadd(hi);
				}
			}
			MetadataUtils.setAxes(totalHistogram, intensity);
			summaryData.add(totalHistogram);
		}
	}

	private boolean[][] findRegionsInCCDs(int[] shape) {
		boolean[][] regionInCCDs = new boolean[2][roiMax];
		RectangularROI[] ccds = {new RectangularROI(shape[1]/2, shape[0], 0), // left half
				new RectangularROI(shape[1]/2, 0, shape[1]/2, shape[0], 0)}; // right half

		for (int r = 0; r < roiMax; r++) {
			IRectangularROI roi = getROI(r);
			double[] start = roi.getPointRef();
			double[] end = roi.getEndPoint();
			for (int s = 0; s < 2; s++) {
				RectangularROI ccd = ccds[s];
				boolean[] regionInCCD = regionInCCDs[s];
				if (ccd.containsPoint(start) || ccd.containsPoint(end)) {
					regionInCCD[r] = true;
					continue;
				}
				double t = end[0];
				end[0] = start[0];
				if (ccd.containsPoint(end)) {
					regionInCCD[r] = true;
					end[0] = t;
					continue;
				}
				end[0] = t;
				t = end[1];
				end[1] = start[1];
				if (ccd.containsPoint(end)) {
					regionInCCD[r] = true;
					end[1] = t;
					continue;
				}
				end[1] = t;
			}
		}
		return regionInCCDs;
	}

	private void initializeNormDataset(String filePath, String dataPath, SliceInformation si) {
		try {
			Tree t = ProcessingUtils.getTree(this, filePath);
			NodeLink l = t.findNodeLink(dataPath);

			if (l == null) {
				NodeLink n = NexusTreeUtils.findFirstNode(t.getGroupNode(), NexusConstants.ENTRY);
				int i = dataPath.indexOf(Node.SEPARATOR, 1);
				if (n != null && i > 1) {
					String path = Tree.ROOT + n.getName() + dataPath.substring(i);
					l = t.findNodeLink(path);
					if (l != null) {
						model.internalSetNormalizationPath(path);
						log.append("Using normalization dataset at %s instead of %s from file %s", path, dataPath, filePath);
					}
				}
			}

			if (l == null) {
				log.appendFailure("No normalization dataset at %s from file %s", dataPath, filePath);
			} else if (l.isDestinationData()) {
				Dataset norm = DatasetUtils.sliceAndConvertLazyDataset(((DataNode) l.getDestination()).getDataset());
				if (norm == null) {
					throw new OperationException(this, "Could not read dataset");
				}

				if (norm.max().doubleValue() < 0) {
					log.appendFailure("Warning: normalization dataset at %s has all values < 0 so negating values", dataPath);
					norm.imultiply(-1);
				} else if (norm.min().doubleValue() < 0) {
					throw new OperationException(this, "Normalization dataset has some values < 0");
				}
				normValues = subsampleData(norm, si);
			}
		} catch (Exception e) {
			log.appendFailure("Could not set normalization dataset %s from file %s: %s", dataPath, filePath, e);
		}
	}

	/**
	 * Subsample data in matching dimensions according to slice information
	 * @param data
	 * @param si
	 * @return sliced data
	 */
	private Dataset subsampleData(Dataset data, SliceInformation si) {
		int r = data.getRank();
		int[] oShape = si.getOriginalShape();
		int[] nShape = data.getShapeRef();
		int[] dDims = si.getDataDimensions();
		Slice[] sampling = si.getSubSampling();
		Slice[] slicing = new Slice[r];
		int j = 0;
		for (int i = 0; i < r && j < r; i++) {
			final int f = i;
			if (!Arrays.stream(dDims).anyMatch(d -> d == f)) {
				Slice s = sampling[f];
				if (nShape[j] == oShape[f]) {
					slicing[j++] = s;
				}
			}
		}
		return data.getSlice(slicing);
	}

	// make summary data for spectra and sum up for spectrum
	private void summarizePhotonSpectra(String prefix, int[][][] allPhotonCounts, int r, Dataset energies) {
		prefix = "correlated_" + prefix;
		Dataset t = DatasetFactory.createFromObject(allPhotonCounts[r]);
		t.setName(prefix + SPECTRA_PREFIX + r);
		MetadataUtils.setAxes(this, t, null, energies);
		summaryData.add(t);

		t = t.sum(0);
		t.setName(prefix + SPECTRUM_PREFIX + r);
		MetadataUtils.setAxes(this, t, energies);
		summaryData.add(t);
	}

	// TODO batch up spectra (i.e. combine several frames together for better SNR) [depends on exposure time and signal strength?]
	// correlate spectra and makes summary data for them and sum up for spectrum
	private void correlateSpectra(String prefix, int r, RegisterNoisyData1D reg, List<Double> shift, Dataset energies, Dataset spectra) {
		Dataset[] sArray = new Dataset[spectra.getShapeRef()[0]];
		for (int i = 0; i < sArray.length; i++) {
			sArray[i] = spectra.getSliceView(new Slice(i, i+1)).squeeze();
		}

		correlateSpectra(prefix, r, reg, shift, energies, sArray);
	}

	private Dataset[] correlateSpectra(String prefix, int r, RegisterNoisyData1D reg, List<Double> shift, Dataset energies, Dataset[] sArray) {

		List<Dataset> results;
		try {
			results = reg.value(sArray);
		} catch (Exception e) {
			throw new OperationException(this, "Could not correlate spectra", e);
		}

		Dataset[] msArray = new Dataset[sArray.length];
		for (int i = 0; i < msArray.length; i++) {
			msArray[i] = results.get(2*i + 1);
		}

		Dataset sp = stack(msArray);
		prefix = "correlated_" + prefix;
		sp.setName(prefix + SPECTRA_PREFIX + r);
		MetadataUtils.setAxes(this, sp, null, energies);
		summaryData.add(sp);

		Dataset spSum = Maths.clip(sp, 0, Double.POSITIVE_INFINITY).sum(0);
		spSum.setName(prefix + SPECTRUM_PREFIX + r);
		MetadataUtils.setAxes(this, spSum, energies);
		summaryData.add(spSum);

		shift.clear();
		for (int i = 0; i < sArray.length; i++) {
			Dataset d = results.get(2*i);
			shift.add(d == null ? Double.NaN : d.getDouble());
		}
		summaryData.add(ProcessingUtils.createNamedDataset((Serializable) shift, prefix + "shift_" + r));

		return new Dataset[] {sp, spSum};
	}

	// bins photons according to their locations
	private static int shiftAndBinPhotonEvents(boolean flip, double offsetX, double offsetY, int single, int multiple, int bin, int bmax, boolean save,
			List<Double> cX, List<Double> cY, List<Integer> cV, StraightLine line, IRectangularROI roi, Dataset sums, Dataset posn, int[] hSingle, int[] hMultiple, List<Double> rX, List<Double> rY, Dataset rawPosn) {
		final double slope = -line.getParameterValue(STRAIGHT_LINE_M);
		final int ix = flip ? 0 : 1;
		final int iy = 1 - ix;
		final IndexIterator it = sums.getIterator(true);
		final int[] sp = it.getPos();
		int l = sp.length;
		final int[] pp = new int[l + 1];

		int np = 0;
		while (it.hasNext()) {
			int ps = sums.getInt(sp);
			if (ps < single) {
				continue;
			}

			System.arraycopy(sp, 0, pp, 0, l);
			pp[l] = ix;
			double px = posn.getDouble(pp) + offsetX;
			pp[l] = iy;
			double py = posn.getDouble(pp) + offsetY;

			if (roi != null && !roi.containsPoint(px, py)) {
				continue; // separate by regions
			}

			if (save) {
				cX.add(px);
				cY.add(py);
				cV.add(ps);

				if (rawPosn != null) {
					rY.add(rawPosn.getDouble(pp) + offsetY);
					pp[l] = ix;
					rX.add(rawPosn.getDouble(pp) + offsetX);
				}
			}

			// correct for tilt
			py += slope*px;
			int ip = (int) Math.floor(bin * py); // discretize position
			if (ip >= 0 && ip < bmax) {
				np++;
				if (ps < multiple) {
					hSingle[ip]++;
				} else {
					hMultiple[ip]++;
				}
			}
		}

		return np;
	}

	private static Dataset[] toArray(List<Dataset> d) {
		int imax = d.size();
		int minSize = Integer.MAX_VALUE;
		for (Dataset di : d) {
			if (di != null) {
				int size = di.getSize();
				if (size < minSize) {
					minSize = size;
				}
			}
		}
		Dataset[] a = new Dataset[imax];
		int j = 0;
		for (Dataset di : d) {
			if (di != null) {
				di = di.getView(true).squeeze();
				if (di.getSize() > minSize) {
					di = di.getSliceView(new Slice(minSize));
				}
				a[j++] = di;
			}
		}
		if (j < imax) {
			a = Arrays.copyOf(a, j);
		}
		return a;
	}

	private static Dataset accumulate(Dataset... d) {
		Dataset sp = null;
		AxesMetadata am = null;
		for (Dataset s : d) {
			if (s == null) {
				continue;
			}
			if (am == null) {
				am = s.getFirstMetadata(AxesMetadata.class);
			}
			s = Maths.clip(s, 0, Double.POSITIVE_INFINITY);
			if (sp == null) {
				sp = s;
			} else {
				sp.iadd(s);
			}
		}
		if (sp == null) {
			return null;
		}
		sp.setMetadata(am);
		return sp;
	}

	protected static Dataset stack(Dataset... d) {
		List<Dataset> nd = new ArrayList<>();
		for (Dataset s : d) {
			if (s != null) {
				int[] oldShape = s.getShapeRef();
				if (oldShape.length == 0 || oldShape[0] != 1) { 
					int[] newShape = new int[s.getRank() + 1];
					System.arraycopy(oldShape, 0, newShape, 1, s.getRank());
					newShape[0] = 1;
					nd.add(s.reshape(newShape));
				} else {
					nd.add(s);
				}
			}
		}

		return DatasetUtils.concatenate(nd.toArray(new Dataset[nd.size()]), 0);
	}

	private RegisterNoisyData1D createCorrelateShifter() {
		RegisterNoisyData1D reg = new RegisterNoisyData1D();
		reg.setFilter(DatasetFactory.ones(5).imultiply(1./5));
		reg.setPeakCentroidThresholdFraction(0.85);
		reg.setFitAll(model.getCorrelateOption() == CORRELATE_PHOTON.ALL_PAIRS);
		reg.setUseFirstAsAnchor(model.getCorrelateOrder() == CORRELATE_ORDER.FIRST);
		return reg;
	}

	/**
	 * @param rn
	 * @param in
	 * @return elastic line position and spectrum datasets
	 */
	private Dataset[] makeSpectrum(int rn, Dataset in) {
		StraightLine line = getStraightLine(rn);

		Double slope = model.getSlopeOverride();
		if (slope == null) {
			slope = line.getParameterValue(STRAIGHT_LINE_M);
		}

		Dataset[] results = makeSpectrum(in, new double[] {offset[0], offset[1]}, slope, model.isClipSpectra(), model.isNormalizeByRows());

		if (model.getEnergyOffsetOption() == ENERGY_OFFSET.TURNING_POINT) {
			results[0].isubtract(findTurningPoint(false, results[1]));
		}
		return results;
	}

	/**
	 * Make spectrum from image by summing along line<p>
	 * The image comprises rows of spectrum that are offset by a line of given slope and intercept
	 * @param in image orientated so that each row represents an individual spectrum
	 * @param rOffset ROI offsets x, y
	 * @param slope line slope
	 * @param intercept line intercept
	 * @param clip if true, clip columns where rows contribute from outside image
	 * @param average if true, average rather than just summing to calculate spectrum
	 * @return start position (in pixels) and spectrum datasets
	 */
	public static Dataset[] makeSpectrum(Dataset in, double[] rOffset, double slope, boolean clip, boolean average) {
		Dataset start = DatasetFactory.createFromObject(-rOffset[0] * slope + rOffset[1]);
	
		Dataset spectrum = makeSpectrum(in, slope, clip, average);
		AxesMetadata am = spectrum.getFirstMetadata(AxesMetadata.class);
		if (am != null) {
			try { // adjust for shift by clipping
				Dataset x = DatasetUtils.sliceAndConvertLazyDataset(am.getAxes()[0]);
				start.iadd(-x.getDouble());
			} catch (DatasetException e1) {
				// do nothing
			}
		}

		return new Dataset[] {start, spectrum};
	}

	private boolean isIVE = true; // if true, fit was intercept versus energy otherwise it was energy versus intercept

	private static class InvertQuadraticOperation implements UnaryOperation {
		private final double fadb;
		private final double bd2a;
		private final double c;
		private double base = 0;
		private double u;

		public InvertQuadraticOperation(double[] coeffs) {
			double a = coeffs[0];
			double b = coeffs[1];
			c = coeffs[2];
			fadb = 4 * a /(b * b);
			u = Math.signum(b);
			bd2a = u * b / (2 * a);
		}

		public void setBase(double base) {
			this.base = base;
		}

		@Override
		public String toString(String a) {
			return "Invert quadratic";
		}

		@Override
		public long longOperate(long a) {
			return (long) doubleOperate(a);
		}

		@Override
		public double doubleOperate(double i) {
			double e = (Math.sqrt(1 + fadb * (i - c)) - u) * bd2a;
			return e - base; // relative to base
		}

		@Override
		public void complexOperate(double[] out, double ra, double ia) {
			// do nothing
		}

		@Override
		public boolean booleanOperate(long a) {
			return doubleOperate(a) != 0;
		}
	}

	/**
	 * Make energy scale
	 * @param result
	 * @param cEnergy current energy (only used when coefficient length is 3)
	 * @param elasticIntercept
	 * @param coeffs for Energy vs Intercept fit (single value taken as linear energy resolution)
	 * @return energy scale
	 */
	public static Dataset makeEnergyScale(Dataset[] result, double cEnergy, double elasticIntercept, double... coeffs) {
		return createEnergyScale(false, DatasetFactory.createRange(result[1].getSize()), cEnergy, elasticIntercept, result[0].getDouble(), coeffs);
	}

	private static Dataset createEnergyScale(boolean isIVE, Dataset i, double cEnergy, double elasticIntercept, double regionStart,
			double[] coeffs) {
		Dataset e = null;
		if (coeffs.length == 3) {
			InvertQuadraticOperation invQuad = new InvertQuadraticOperation(coeffs);
			Quadratic quad = new Quadratic(coeffs);
			if (isIVE) { // intercept fitted against energy
				double expectedIntercept = quad.val(cEnergy);
				if (Math.abs(cEnergy) - invQuad.doubleOperate(expectedIntercept) > 1e-3) {
					System.err.println("Check invert: " + cEnergy + " cf " + cEnergy + ": " + Arrays.toString(coeffs));
				}
				double delta = expectedIntercept - elasticIntercept; // shift in elastic line from energy calibration
				i.iadd(regionStart + delta); // align indexes to calibration frame
				invQuad.setBase(cEnergy);
				e = Operations.operate(invQuad, i, null);
			} else {
				double expectedIntercept = invQuad.doubleOperate(cEnergy);
				double delta = expectedIntercept - elasticIntercept; // shift in elastic line from energy calibration
				i.iadd(regionStart + delta); // align indexes to calibration frame

				e = quad.calculateValues(i).isubtract(cEnergy); // relative to elastic
			}
		} else {
			i.iadd(regionStart - elasticIntercept);
			e = Maths.multiply(i, isIVE ? 1. / coeffs[0] : coeffs[0]);
		}
		e.imultiply(-1);
		e.setName(ENERGY_LOSS);
		return e;
	}

	private Dataset createEnergyScale(int length, int oversampling, double cEnergy, double elasticIntercept, double[] coeffs) {
		Dataset i = DatasetFactory.createRange(length);
		if (oversampling != 1) {
			i.idivide(oversampling);
		}
		return createEnergyScale(isIVE, i, cEnergy, elasticIntercept, 0, coeffs);
	}

	private static int findTurningPoint(boolean fromFirst, Dataset y) {
		int n = y.getSize();
		Dataset diff = Maths.derivative(DatasetFactory.createRange(n), y, 3);
		List<Double> cs = DatasetUtils.crossings(diff, 0);
		if (cs.isEmpty()) {
			return 0;
		}
		return (int) (fromFirst ? Math.floor(cs.get(0)) : Math.ceil(cs.get(cs.size() - 1)));
	}

	/**
	 * @param r
	 * @return offset for zero energy loss along energy axis
	 */
	private double getZeroEnergyOffset(int r) {
		double offset = Double.NaN;
		if (model.getEnergyOffsetOption() == ENERGY_OFFSET.MANUAL_OVERRIDE) {
			offset = r == 0 ? model.getEnergyOffsetA() : model.getEnergyOffsetB();
		}
		if (!Double.isFinite(offset)) {
			offset = getStraightLine(r).getParameterValue(STRAIGHT_LINE_C); // elastic line intercept
		}
		return offset;
	}
}

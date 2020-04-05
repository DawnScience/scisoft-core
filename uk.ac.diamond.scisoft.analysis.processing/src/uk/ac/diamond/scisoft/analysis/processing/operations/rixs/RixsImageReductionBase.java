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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.metadata.AxesMetadata;

import uk.ac.diamond.scisoft.analysis.MultiRange;
import uk.ac.diamond.scisoft.analysis.dataset.function.Histogram;
import uk.ac.diamond.scisoft.analysis.dataset.function.RegisterNoisyData1D;
import uk.ac.diamond.scisoft.analysis.fitting.functions.StraightLine;
import uk.ac.diamond.scisoft.analysis.image.ImageUtils;
import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.io.NexusTreeUtils;
import uk.ac.diamond.scisoft.analysis.processing.LocalServiceManager;
import uk.ac.diamond.scisoft.analysis.processing.operations.MetadataUtils;
import uk.ac.diamond.scisoft.analysis.processing.operations.rixs.RixsImageReductionBaseModel.CORRELATE_ORDER;
import uk.ac.diamond.scisoft.analysis.processing.operations.rixs.RixsImageReductionBaseModel.CORRELATE_PHOTON;
import uk.ac.diamond.scisoft.analysis.processing.operations.rixs.RixsImageReductionBaseModel.ENERGY_OFFSET;
import uk.ac.diamond.scisoft.analysis.processing.operations.rixs.RixsImageReductionBaseModel.XIP_OPTION;
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;

abstract public class RixsImageReductionBase<T extends RixsImageReductionBaseModel> extends RixsBaseOperation<T> {

	private double[] energyDispersion = new double[2];
	private Dataset totalSum = null; // dataset of all event sums (so far)
	private List<Dataset> allSums = new ArrayList<>(); // list of dataset of event sums in each image
	private List<Dataset> allPositions = new ArrayList<>(); // list of dataset of event coords in each image
	@SuppressWarnings("unchecked")
	private List<Dataset>[] allSpectra = new List[] {new ArrayList<>(), new ArrayList<>()};

	protected String currentDataFile = null;
	private MultiRange selection;

	/**
	 * Auxiliary subentry. This must match the name field defined in the plugin extension
	 */
	public static final String PROCESS_NAME = "RIXS image reduction";

	private static final String ENERGY_LOSS = "Energy loss";

	// string that contains a set of digits before a period and another substring 
	protected static final Pattern NUMBERED_FILE_REGEX = Pattern.compile(".*?([0-9]+)\\.\\w+");

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

		Arrays.fill(energyDispersion, Double.NaN);
		energyDispersion[0] = model.getEnergyDispersion();
		if (Double.isNaN(energyDispersion[0])) {
			String file = model.getCalibrationFile();
			if (file == null && throwEx) {
				throw new OperationException(this, "Energy dispersion calibration must be defined");
			}
			// energy dispersion in terms of eV/pixel
			if (file != null) {
				double[] tmp = parseForCalibration(file);
				System.arraycopy(tmp, 0, energyDispersion, 0, Math.min(2, tmp.length));
			}
		}
		if (energyDispersion[1] == 0 || Double.isNaN(energyDispersion[1])) { // assume first entry is fine
			energyDispersion[1] = energyDispersion[0];
		}

		updateROICount();
	}

	@Override
	protected void resetProcess(IDataset original, int total) {
		totalSum = null;
		resetList(allPositions, total);
		resetList(allSums, total);
		for (int i = 0; i < 2; i++) {
			resetList(allSpectra[i], total);
		}
		currentDataFile = null;
	}

	protected void initializeROIsFromFile(String file) {
		if (file == null) {
			return;
		}
		try {
			Tree tree = LoaderFactory.getData(file).getTree();
			IPersistenceService service = LocalServiceManager.getPersistenceService();
			IPersistentNodeFactory pf = service.getPersistentNodeFactory();
			for (IOperation<?,?> o : pf.readOperationsFromTree(tree)) {
				IOperationModel m = o.getModel();
				if (m instanceof RixsBaseModel) {
					RixsBaseModel rbm = (RixsBaseModel) m;
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

	private double[] parseForCalibration(String elasticLineFile) {
		try {
			Tree t = LoaderFactory.getData(elasticLineFile).getTree();

			GroupNode root = t.getGroupNode();
			GroupNode entry = NexusTreeUtils.findFirstEntryWithProcess(root);

			ProcessingUtils.checkForProcess(this, entry, ElasticLineReduction.PROCESS_NAME);

			GroupNode rg = entry.getGroupNode("result");
			if (rg == null) {
				throw new NexusException("File does not contain a result group");
			}

			DataNode d = rg.getDataNode("data");
			if (d == null) {
				throw new NexusException("File does not contain a result dataset");
			}

			return NexusTreeUtils.parseDoubleArray(d);
		} catch (Exception e) {
			log.appendFailure("Could not parse Nexus file %s:%s", elasticLineFile, e);
		}

		return new double[] {-1, -1};
	}

	@Override
	IDataset processImageRegion(int sn, IDataset original, int rn, Dataset in) {
		Dataset[] result = makeSpectrum(rn, in, model.getSlopeOverride(), model.isClipSpectra());
		Dataset spectrum = result[1];
		spectrum.setName("spectrum_" + rn);

		spectrum.clearMetadata(AxesMetadata.class);
		// work out energy scale (needs calibration)
		Dataset e = makeEnergyScale(result, offset[1], energyDispersion[rn]);
		MetadataUtils.setAxes(spectrum, e);
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
		if (od != null) {
			// find photon events in entire image
			Maths.clip(original, original, 0, Double.POSITIVE_INFINITY);
			events = ImageUtils.findWindowedPeaks(original, model.getWindow(), countsPerPhoton * model.getLowThreshold(), countsPerPhoton * model.getHighThreshold());
			eSum = events.get(0);
		} else {
			log.appendFailure("Skipping frame %d", si.getSliceNumber());
		}

		int sn = si.getSliceNumber();
		if (eSum == null || eSum.getSize() == 0) {
			log.appendFailure("No events found");
			// need to pad spectra
			for (int r = 0; r < roiMax; r++) {
				allSpectra[r].set(sn, null);
			}
			allSums.set(sn, null);
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
			allPositions.set(sn, events.get(1));
		}

		IntegerDataset bins = null;
		Dataset a = null;
		Dataset h = null;
		if (totalSum != null) {
			double max = totalSum.max(true).doubleValue();
			bins = DatasetFactory.createRange(IntegerDataset.class, totalSum.min(true).doubleValue(), max+1, 1);
			bins.setName("Event sum");
			Histogram histo = new Histogram(bins);
			h = histo.value(totalSum).get(0);
			h.setName("Number of events");
	
			a = Maths.log10(h);
			a.setName("Log10 of " + h.getName());
			MetadataUtils.setAxes(a, bins);
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
		if (od instanceof OperationDataForDisplay) {
			odd = (OperationDataForDisplay) od;
		} else {
			odd = new OperationDataForDisplay(od == null ? null : od.getData());
		}
		odd.setShowSeparately(true);
		odd.setLog(log);
		odd.setDisplayData(displayData.toArray(new IDataset[displayData.size()]));
		odd.setAuxData(auxData.toArray(new Serializable[auxData.size()]));
		odd.setSummaryData(summaryData.toArray(new Serializable[summaryData.size()]));
		return odd;
	}

	private static final String XCAM_XIP_FILENAME = "xcam-xpi%d-%d.hdf"; // eg. xcam-xip2-143781.hdf
	private static final String XCAM_XIP_DATA = "/entry/data/data"; // or "/entry/instrument/detector/data"

//	private static final String XCAM_XIP_PROCESSED_DATA = "/entry_1/processed%d/centroids/data";
	private static final String XCAM_XIP_PROCESSED_DATA = "xip%d";

	protected List<Dataset> readXIPEventsFromExternalFile(String path, int i) {
		int l = path.lastIndexOf(File.separator) + 1;
		String name = l == 0 ? path : path.substring(l);

		Matcher m = NUMBERED_FILE_REGEX.matcher(name);
		if (!m.matches()) {
			throw new OperationException(this, "Current file path does not end with scan number (before file extension)");
		}
		String digits = m.group(1);
		int scan = Integer.parseInt(digits);
		String xipPath = path.substring(0, l).concat(String.format(XCAM_XIP_FILENAME, i, scan));

		if (!new File(xipPath).exists()) {
			return parseXIPEvents(log, xipPath, XCAM_XIP_DATA);
		}
		return null;
	}

	protected List<Dataset> readXIPEventsFromCurrentFile(String path, String dataPath, int i) {
		int l = dataPath.lastIndexOf(Node.SEPARATOR) + 1; // expected to be in same group
		if (l > 0) {
			dataPath = dataPath.substring(0, l);
		}
		return parseXIPEvents(log, path, dataPath + String.format(XCAM_XIP_PROCESSED_DATA, i));
	}

	/**
	 * Parse data from XIP Area Detector plugin provided by XCAM
	 * @param log
	 * @param filePath
	 * @param dataPath
	 * @return sum, centroid xy, eta-corrected xy, eta & iso-linear corrected y
	 */
	static List<Dataset> parseXIPEvents(OperationLog log, String filePath, String dataPath) {
		try {
			Tree t = LocalServiceManager.getLoaderService().getData(filePath, null).getTree();

			Node n = TreeUtils.getNode(t, dataPath);
			if (n == null) {
				log.appendFailure("Could not parse Nexus file %s to find %s", filePath, dataPath);
			} else if (n instanceof DataNode) {
				DataNode d = (DataNode) n;
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

		return null;
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
			MetadataUtils.setAxes(dh, bins);
			displayData.set(0, dh);
			List<Double> z = DatasetUtils.crossings(x, dh, 0);
			log.append("Histogram derivative zero-crossings = %s", z);
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
			List<Integer> cV = new ArrayList<>();
			boolean save = true;
			boolean all = model.isSaveAllPositions();
			for (int r = 0; r < roiMax; r++) {
				cX.clear();
				cY.clear();
				cV.clear();
				int j = 0;
				for (int i = 0, imax = allSums.size(); i < imax; i++) {
					Dataset sums = allSums.get(i);
					if (sums == null) {
						continue;
					}
					Dataset posn = allPositions.get(i);

					int[] hSingle = allSingle[r][j] = new int[bmax];
					int[] hMultiple = allMultiple[r][j] = new int[bmax];
					StraightLine line = getStraightLine(r);
					IRectangularROI roi = getROI(r);
					int hits = shiftAndBinPhotonEvents(false, 0, 0, single, multiple, bin, bmax, save, cX, cY, cV, line, roi, sums,
							posn, hSingle, hMultiple);

					log.append("Binned %d events in region %d from %d events in frame %d", hits, r, sums.getSize(), i);

					if (save && !all) { // then only save photon data from first frame and first region
						save = false;
					}
					j++;
				}
				int side = cX.size();
				if (side > 0) {
					Dataset t;
					t = DatasetFactory.createFromList(cY);
					MetadataUtils.setAxes(t, ProcessingUtils.createNamedDataset(DatasetFactory.createFromList(cX), "x"));
					t.setName("photon_positions_" + r);
					summaryData.add(t);
					t = DatasetFactory.createFromList(cV);
					t.setName("photon_values_" + r);
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
				if (xip == null) {
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
									cX, cY, cV, line, roi, sums, posn, hSingle, null);
						} else { // split first frame
							Slice slice = new Slice(1);
							hits = shiftAndBinPhotonEvents(true, s*offsetX, 0, 1, Integer.MAX_VALUE, bin, bmax, true,
									cX, cY, cV, line, roi, sums.getSliceView(slice), posn.getSliceView(slice), hSingle, null);
							slice = new Slice(1, null, null);
							hits += shiftAndBinPhotonEvents(true, s*offsetX, 0, 1, Integer.MAX_VALUE, bin, bmax, false,
									cX, cY, cV, line, roi, sums.getSliceView(slice), posn.getSliceView(slice), hSingle, null);
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
				MetadataUtils.setAxes(t, ProcessingUtils.createNamedDataset(DatasetFactory.createFromList(cX), "x"));
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

			Dataset ax = null;
			try {
				ax = DatasetUtils.sliceAndConvertLazyDataset(sp.getFirstMetadata(AxesMetadata.class).getAxis(0)[0]);
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
			Dataset cSpectrum = correlateSpectra("", r, reg, shift, ax, sArray);
			String normPath = model.getNormalizationPath();
			if (normPath != null && !normPath.isEmpty()) {
				Dataset nSpectrum = normalizeSpectrum(filePath, normPath, cSpectrum);
				if (nSpectrum != null) {
					nSpectrum.setName("normalized_correlated_spectrum_" + r);
					MetadataUtils.setAxes(nSpectrum, ax);
					summaryData.add(nSpectrum);
				}
			}

			if (bins == null) {
				continue; // no photon events!!!
			}

			double el0 = getZeroEnergyOffset(r); // elastic line intercept
			final Dataset energies = DatasetFactory.createRange(bmax);
			energies.iadd(-bin*el0); // adjust zero
			energies.imultiply(-energyDispersion[r]/bin);
			energies.setName(ENERGY_LOSS);

			Dataset t = DatasetFactory.createFromObject(allSingle[r]);
			t.setName("single_photon_spectra_" + r);
			MetadataUtils.setAxes(t, null, energies);
			Dataset sSpectra = t;
			summaryData.add(t);

			Dataset nf = t.sum(1); // to work out per-image as single fraction of total events
			nf.setName("single_photon_count_" + r);
			Dataset sEvents = nf;
			summaryData.add(nf);

			t = DatasetFactory.createFromObject(allMultiple[r]);
			t.setName("multiple_photon_spectra_" + r);
			MetadataUtils.setAxes(t, null, energies);
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

			double ts = (Double) ((Number) sEvents.sum()).doubleValue();
			double tm = (Double) ((Number) mEvents.sum()).doubleValue();
			double tt = ts + tm;
			double sf = tt == 0 ? 0 : ts/tt;
			log.appendSuccess("Events: single/total = %g/%g = %g", ts, tt, sf);
			summaryData.add(ProcessingUtils.createNamedDataset(sf, "single_events_total_fraction_" + r));

			sp = sSpectra.sum(0);
			sp.setName("single_photon_spectrum_" + r);
			MetadataUtils.setAxes(sp, energies);
			summaryData.add(sp);

			sp = mSpectra.sum(0);
			sp.setName("multiple_photon_spectrum_" + r);
			MetadataUtils.setAxes(sp, energies);
			summaryData.add(sp);

			if (model.getCorrelateOption() == CORRELATE_PHOTON.USE_INTENSITY_SHIFTS) {
				for (int i = 0, imax = allSingle[r].length; i < imax; i++) { // image to image shifts
					double offset = shift.get(i);
					int[] hSingle = new int[bmax];
					int[] hMultiple = new int[bmax];
					allSingle[r][i] = hSingle; // need to repopulate as previous arrays are referenced in datasets
					allMultiple[r][i] = hMultiple;
					StraightLine line = getStraightLine(r);
					IRectangularROI roi = getROI(r);
					Dataset sums = allSums.get(i);
					if (sums == null) {
						continue;
					}
					Dataset posn = allPositions.get(i);
					shiftAndBinPhotonEvents(false, 0, offset, single, multiple, bin, bmax, false, null, null, null, line, roi, sums,
							posn, hSingle, hMultiple);
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
			final Dataset energies = DatasetFactory.createRange(bmax);
			energies.iadd(-bin*el0); // adjust zero
			energies.imultiply(-energyDispersion[r]/bin);
			energies.setName(ENERGY_LOSS);

			int xr = roiMax + 2 * r;
			int[] spectrum = allSingle[xr][0];
			if (spectrum != null) {
				Dataset sp = DatasetFactory.createFromObject(spectrum);
				if (sp.max().intValue() > 0) {
					sp.setName("xip_photon_spectrum_" + r);
					MetadataUtils.setAxes(sp, energies);
					summaryData.add(sp);
				}
			}
			spectrum = allSingle[xr + 1][0];
			if (spectrum != null) {
				Dataset sp = DatasetFactory.createFromObject(spectrum);
				if (sp.max().intValue() > 0) {
					sp.setName("xip_eta_photon_spectrum_" + r);
					MetadataUtils.setAxes(sp, energies);
					summaryData.add(sp);
				}
			}
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
				if (ccd.containsPoint(start)) {
					regionInCCD[r] = true;
					continue;
				} else if (ccd.containsPoint(end)) {
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

	protected Dataset normalizeSpectrum(String filePath, String dataPath, Dataset spectrum) {
		try {
			Tree t = LocalServiceManager.getLoaderService().getData(filePath, null).getTree();
			NodeLink l = t.findNodeLink(dataPath);
			if (l == null) {
				log.append("No normalization dataset at %s from file %s", dataPath, filePath);
			} else if (l.isDestinationData()) {
				Dataset n = DatasetUtils.sliceAndConvertLazyDataset(((DataNode) l.getDestination()).getDataset());
				if (n == null) {
					throw new OperationException(this, "Could not read normalization dataset at " + dataPath + " in " + filePath);
				}
				return Maths.divide(spectrum, n.getByBoolean(getUsedFrames()).sum(true));
			}
		} catch (Exception e) {
			log.appendFailure("Could not read normalization dataset %s from file %s: %s", dataPath, filePath, e);
		}

		return null;
	}

	// make summary data for spectra and sum up for spectrum
	private void summarizePhotonSpectra(String prefix, int[][][] allPhotonCounts, int r, Dataset energies) {
		prefix = "correlated_" + prefix;
		Dataset t = DatasetFactory.createFromObject(allPhotonCounts[r]);
		t.setName(prefix + "spectra_" + r);
		MetadataUtils.setAxes(t, null, energies);
		summaryData.add(t);

		t = t.sum(0);
		t.setName(prefix + "spectrum_" + r);
		MetadataUtils.setAxes(t, energies);
		summaryData.add(t);
	}

	// correlate spectra and makes summary data for them and sum up for spectrum
	private void correlateSpectra(String prefix, int r, RegisterNoisyData1D reg, List<Double> shift, Dataset energies, Dataset spectra) {
		Dataset[] sArray = new Dataset[spectra.getShapeRef()[0]];
		for (int i = 0; i < sArray.length; i++) {
			sArray[i] = spectra.getSliceView(new Slice(i, i+1)).squeeze();
		}

		correlateSpectra(prefix, r, reg, shift, energies, sArray);
	}

	private Dataset correlateSpectra(String prefix, int r, RegisterNoisyData1D reg, List<Double> shift, Dataset energies, Dataset[] sArray) {

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
		sp.setName(prefix + "spectra_" + r);
		MetadataUtils.setAxes(sp, null, energies);
		summaryData.add(sp);

		sp = sp.sum(0);
		sp.setName(prefix + "spectrum_" + r);
		MetadataUtils.setAxes(sp, energies);
		summaryData.add(sp);

		shift.clear();
		for (int i = 0; i < sArray.length; i++) {
			shift.add(results.get(2*i).getDouble());
		}
		summaryData.add(ProcessingUtils.createNamedDataset((Serializable) shift, prefix + "shift_" + r));

		return sp;
	}

	// bins photons according to their locations
	private static int shiftAndBinPhotonEvents(boolean flip, double offsetX, double offsetY, int single, int multiple, int bin, int bmax, boolean save,
			List<Double> cX, List<Double> cY, List<Integer> cV, StraightLine line, IRectangularROI roi, Dataset sums, Dataset posn, int[] hSingle, int[] hMultiple) {
		final double slope = -line.getParameterValue(STRAIGHT_LINE_M);
		final int ix = flip ? 0 : 1;
		final int iy = 1 - ix;
		final IndexIterator it = sums.getIterator(true);
		final int[] sp = it.getPos();
		int l = sp.length;
		final int[] pp = new int[l + 1];

		int np = 0;
		while (it.hasNext()) {
			System.arraycopy(sp, 0, pp, 0, l);
			pp[l] = ix;
			double px = posn.getDouble(pp) + offsetX;
			pp[l] = iy;
			double py = posn.getDouble(pp) + offsetY;

			if (roi != null && !roi.containsPoint(px, py)) {
				continue; // separate by regions
			}

			int ps = sums.getInt(sp);

			// add coords
			if (save && ps > 0) {
				cX.add(px);
				cY.add(py);
				cV.add(ps);
			}

			// correct for tilt
			py += slope*px;
			if (ps >= single) {
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
		}
		return np;
	}

	private static Dataset[] toArray(List<Dataset> d) {
		int imax = d.size();
		int minSize = Integer.MAX_VALUE;
		for (Dataset s : d) {
			if (s != null) {
				int size = s.getSize();
				if (size < minSize) {
					minSize = size;
				}
			}
		}
		Dataset[] a = new Dataset[imax];
		int j = 0;
		for (int i = 0; i < imax; i++) {
			Dataset di = d.get(i);
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
		for (Dataset s : d) {
			if (s == null) {
				continue;
			}
			if (sp == null) {
				sp = s.clone();
			} else {
				sp.iadd(s);
			}
		}
		return sp;
	}

	protected static Dataset stack(Dataset... d) {
		List<Dataset> nd = new ArrayList<>();
		for (Dataset s : d) {
			if (s != null) {
				nd.add(s.reshape(1, s.getSize()));
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
	 * @param slope override value
	 * @param clip if true, clip columns where rows contribute from outside image 
	 * @return elastic line position and spectrum datasets
	 */
	private Dataset[] makeSpectrum(int rn, Dataset in, Double slope, boolean clip) {
		StraightLine line = getStraightLine(rn);

		if (slope == null) {
			slope = line.getParameterValue(STRAIGHT_LINE_M);
		}
		double intercept = model.getEnergyOffsetOption() != ENERGY_OFFSET.MANUAL_OVERRIDE
				? line.getParameterValue(STRAIGHT_LINE_C)
				: rn == 0 ? model.getEnergyOffsetA() : model.getEnergyOffsetB();

		Dataset[] results = makeSpectrum(in, offset[0], slope, intercept, clip);

		if (model.getEnergyOffsetOption() == ENERGY_OFFSET.TURNING_POINT) {
			results[0].isubtract(findTurningPoint(false, results[1]));
		}
		return results;
	}

	/**
	 * Make spectrum from image by summing along line<p>
	 * The image comprises rows of spectrum that are offset by a line of given slope and intercept
	 * @param in image orientated so that each row represents an individual spectrum
	 * @param rOffset ROI offset
	 * @param slope line slope
	 * @param intercept line intercept
	 * @param clip if true, clip columns where rows contribute from outside image 
	 * @return elastic line position and spectrum datasets
	 */
	public static Dataset[] makeSpectrum(Dataset in, int rOffset, double slope, double intercept, boolean clip) {
		int rows = in.getShapeRef()[0];
		Dataset elastic = DatasetFactory.createRange(rows);
		if (rOffset != 0) {
			elastic.iadd(rOffset);
		}
		if (slope != 1) {
			elastic.imultiply(slope);
		}
		if (intercept != 0) {
			elastic.iadd(intercept);
		}
	
		Dataset spectrum = makeSpectrum(in, slope, clip);
		AxesMetadata am = spectrum.getFirstMetadata(AxesMetadata.class);
		if (am != null) {
			try { // adjust for shift by clipping
				Dataset x = DatasetUtils.sliceAndConvertLazyDataset(am.getAxes()[0]);
				elastic.iadd(-x.getDouble());
			} catch (DatasetException e1) {
				// do nothing
			}
		}

		return new Dataset[] {elastic, spectrum};
	}

	/**
	 * Make energy scale
	 * @param result
	 * @param offset
	 * @param dispersion
	 * @return energy scale
	 */
	public static Dataset makeEnergyScale(Dataset[] result, int offset, double dispersion) {
		Dataset e = DatasetFactory.createRange(result[1].getSize());
		e.iadd(offset - result[0].getDouble()); // TODO discretize???
		e.imultiply(-dispersion);
		e.setName(ENERGY_LOSS);
		return e;
	}

	private static int findTurningPoint(boolean fromFirst, Dataset y) {
		int n = y.getSize();
		Dataset diff = Maths.derivative(DatasetFactory.createRange(n), y, 3);
		List<Double> cs = DatasetUtils.crossings(diff, 0);
		if (cs.size() == 0) {
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

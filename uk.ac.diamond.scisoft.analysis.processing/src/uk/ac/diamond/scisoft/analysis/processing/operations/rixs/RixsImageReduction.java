/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.rixs;

import java.io.File;
import java.io.FilenameFilter;
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
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.IOperationModel;
import org.eclipse.dawnsci.analysis.api.roi.IRectangularROI;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.NodeLink;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.dataset.impl.function.DatasetToDatasetFunction;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceInformation;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.metadata.AxesMetadata;

import uk.ac.diamond.scisoft.analysis.MultiRange;
import uk.ac.diamond.scisoft.analysis.dataset.function.Histogram;
import uk.ac.diamond.scisoft.analysis.dataset.function.RegisterData1D;
import uk.ac.diamond.scisoft.analysis.dataset.function.RegisterNoisyData1D;
import uk.ac.diamond.scisoft.analysis.fitting.functions.StraightLine;
import uk.ac.diamond.scisoft.analysis.image.ImageUtils;
import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.io.NexusTreeUtils;
import uk.ac.diamond.scisoft.analysis.processing.LocalServiceManager;
import uk.ac.diamond.scisoft.analysis.processing.operations.MetadataUtils;
import uk.ac.diamond.scisoft.analysis.processing.operations.rixs.RixsImageReductionModel.ENERGY_OFFSET;
import uk.ac.diamond.scisoft.analysis.processing.operations.rixs.RixsImageReductionModel.CORRELATE_ORDER;
import uk.ac.diamond.scisoft.analysis.processing.operations.rixs.RixsImageReductionModel.CORRELATE_PHOTON;
import uk.ac.diamond.scisoft.analysis.processing.operations.rixs.RixsImageReductionModel.FIT_FILE_OPTION;
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;

public class RixsImageReduction extends RixsBaseOperation<RixsImageReductionModel> {

	private double[] energyDispersion = new double[2];
	private Dataset totalSum = null; // dataset of all event sums (so far)
	private List<Dataset> allSums = new ArrayList<>(); // list of dataset of event sums in each image
	private List<Dataset> allPositions = new ArrayList<>(); // list of dataset of event coords in each image
	private List<Dataset>[] allSpectra = new List[] {new ArrayList<>(), new ArrayList<>()};

	private String currentDataFile = null;
	private MultiRange selection;

	/**
	 * Auxiliary subentry. This must match the name field defined in the plugin extension
	 */
	public static final String PROCESS_NAME = "RIXS image reduction";

	private static final String ENERGY_LOSS = "Energy loss";

	@Override
	public String getId() {
		return getClass().getName();
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}

	@Override
	public String getFilenameSuffix() {
		return "rixs_spectra";
	}

	@Override
	void updateFromModel(boolean throwEx) {
		try {
			selection = MultiRange.createMultiRange(model.getFrameSelection());
		} catch (IllegalArgumentException e) {
			if (throwEx) {
				throw new OperationException(this, "Frame selection invalid", e);
			}
		}

		Arrays.fill(energyDispersion, Double.NaN);
		energyDispersion[0] = model.getEnergyDispersion();
		String file = model.getCalibrationFile();
		if (Double.isNaN(energyDispersion[0])) {
			if (file == null) {
				file = model.getFitFile();
				if (file == null && throwEx) {
					throw new OperationException(this, "Either energy dispersion calibration or elastic fit file must be defined");
				}
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

		// done regardless of fit file option but gets overwrite when not manual override
		file = model.getFitFile();
		if (file == null) {
			file = model.getCalibrationFile();
		}
		initializeFitLine(file);

		if (model.isRegionsFromFile()) {
			file = model.getFitFile();
			if (file == null) {
				file = model.getCalibrationFile();
				if (file != null) { // only in case of calibration only
					initializeROIsFromFile(file);
				}
			}
		}

		updateROICount();
	}

	@Override
	protected void resetProcess(IDataset original) {
		totalSum = null;
		allSums.clear();
		allPositions.clear();
		allSpectra[0].clear();
		allSpectra[1].clear();
		currentDataFile = null;
	}

	private static final String PROCESSING = "processing";

	@Override
	void initializeProcess(IDataset original) {
		log.append("RIXS Image Reduction");
		log.append("====================");

		SliceFromSeriesMetadata smd = original.getFirstMetadata(SliceFromSeriesMetadata.class);
		if (smd.getSliceInfo().isFirstSlice()) {
			String filePath = smd.getSourceInfo().getFilePath();
			if (model.getFitFileOption() != FIT_FILE_OPTION.MANUAL_OVERRIDE  && !filePath.equals(currentDataFile)) {
				currentDataFile = filePath;

				File file = new File(filePath);
				File currentDir = file.getParentFile();
				String currentName = file.getName();
				Pattern REGEX = Pattern.compile(".*?([0-9]+)\\.nxs");

				Matcher m = REGEX.matcher(currentName);
				if (!m.matches()) {
					throw new OperationException(this, "Current file path does not end with scan number");
				}
				int scan = Integer.parseInt(m.group(1));
				if (model.getFitFileOption() == FIT_FILE_OPTION.NEXT_SCAN) {
					scan++;
				} else if (model.getFitFileOption() == FIT_FILE_OPTION.PREVIOUS_SCAN) {
					scan--;
				}
				log.append("Looking for processed elastic line fit of scan %d", scan);

				String prefix = currentName.substring(0, m.start(1)) + scan + "_processed_" + ElasticLineReduction.SUFFIX;
				FilenameFilter filter = new FilenameFilter() {
					
					@Override
					public boolean accept(File dir, String name) {
						return name.startsWith(prefix);
					}
				};

				String fitFile = null;
				if (model.getCalibrationFile() != null) { // try in calibration file's directory
					File calibDir = new File(model.getCalibrationFile()).getParentFile();
					fitFile = findLatestFitFile(filter, calibDir, prefix);
				}

				if (fitFile == null) {
					fitFile = findLatestFitFile(filter, currentDir, prefix);
					if (fitFile == null) {
						fitFile = findLatestFitFile(filter, new File(currentDir, PROCESSING), prefix);
					}
					if (fitFile == null) {
						throw new OperationException(this, "Could not find fit file in data, calibration or processing directories");
					}
				}

				log.append("Using fit file: %s", fitFile);
				initializeFitLine(fitFile);
				if (model.isRegionsFromFile()) {
					initializeROIsFromFile(fitFile);
					updateROICount();
				}
			}
		}
	}

	// find any processed fit file with scan number
	private String findLatestFitFile(FilenameFilter filter, File cwd, final String prefix) {
		File[] files = cwd.listFiles(filter);
		if (files == null) {
			return null;
		}
		long latest = 0;
		File last = null;
		for (File f : files) {
			if (last == null) {
				last = f;
				latest = f.lastModified();
			} else if (latest < f.lastModified()) {
				last = f;
				latest = f.lastModified();
			}
		}
		return last == null ? null : last.toString();
	}

	private void initializeFitLine(String elasticLineFile) {
		if (elasticLineFile == null) {
			lines[0] = new StraightLine();
			lines[1] = new StraightLine();
			return;
		}

		try {
			Tree tree = LoaderFactory.getData(elasticLineFile).getTree();
			GroupNode root = tree.getGroupNode();
			GroupNode entry = (GroupNode) NexusTreeUtils.findFirstNode(root, "entry", "NXentry").getDestination();

			GroupNode pg = ProcessingUtils.checkForProcess(this, entry, ElasticLineReduction.PROCESS_NAME);

			// find /entry/auxiliary/*-RIXS elastic line reduction/line?_[cm]
			GroupNode g = (GroupNode) entry.getGroupNode("auxiliary");
			for (NodeLink n : g) {
				if (n.getName().endsWith(ElasticLineReduction.PROCESS_NAME) && n.isDestinationGroup()) {
					GroupNode fg = (GroupNode) n.getDestination();
					int r = Math.min(MAX_ROIS, fg.getNumberOfGroupNodes() / 3); // three datasets per line
					double[] p = new double[2];
					for (int i = 0; i < r; i++) {
						String l;
						l = String.format(ElasticLineReduction.LINE_GRADIENT_FORMAT, i);
						p[0] = NexusTreeUtils.parseDoubleArray(fg.getGroupNode(l).getDataNode("data"))[0];
						l = String.format(ElasticLineReduction.LINE_INTERCEPT_FORMAT, i);
						p[1] = NexusTreeUtils.parseDoubleArray(fg.getGroupNode(l).getDataNode("data"))[0];
						lines[i] = new StraightLine(p);
					}
					if (r == 1 && Double.isNaN(lines[0].getParameterValue(0))) {
						throw new OperationException(this, "Loaded elastic line fit is invalid");
					}
					if (r > 1 && Double.isNaN(lines[1].getParameterValue(0))) {
						lines[1].setParameterValues(lines[0].getParameterValues());
					}
					break;
				}
			}

			// TODO may be set slope limits from process in process group by reading model
		} catch (Exception e) {
			throw new OperationException(this, "Cannot load file with elastic line fit", e);
		}
	}

	private void initializeROIsFromFile(String file) {
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
			throw new OperationException(this, "Cannot load file with ROIs", e);
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
			// entry1:NXentry
			//     before_scan:NXcollection
			//         andorPreampGain:NXcollection/andorPreampGain [1, 2, 4]
			//         pgmEnergy:NXcollection/ [energy in eV, always single value, even for an energy scan]

			GroupNode entry = (GroupNode) NexusTreeUtils.findFirstNode(root, "entry", "NXentry").getDestination();

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
			log.append("Could not parse Nexus file %s:%s", elasticLineFile, e);
		}

		return new double[] {-1, -1};
	}


	@Override
	IDataset processImageRegion(int r, IDataset original, Dataset in) {
		Dataset[] result = makeSpectrum(r, in, model.getSlopeOverride(), model.isClipSpectra());
		Dataset spectrum = result[1];
		spectrum.setName("spectrum_" + r);

		// work out energy scale (needs calibration)
		Dataset e = DatasetFactory.createRange(spectrum.getSize());
		e.iadd(offset[1]-result[0].getDouble()); // TODO discretize???
		e.imultiply(-energyDispersion[r]);
		e.setName(ENERGY_LOSS);
		MetadataUtils.setAxes(spectrum, e);
		auxData.add(spectrum);
		allSpectra[r].add(spectrum);
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
		int smax = si.getTotalSlices();

		OperationData od = super.process(original, monitor);
		List<Dataset> events = null;
		Dataset eSum = null;
		if (od != null) {
			// find photon events in entire image
			events = ImageUtils.findWindowedPeaks(original, model.getWindow(), countsPerPhoton * model.getLowThreshold(), countsPerPhoton * model.getHighThreshold());
			eSum = events.get(0);
		} else {
			log.append("Skipping frame %d", si.getSliceNumber());
		}

		IntegerDataset bins = null;
		Dataset a = null;
		Dataset h = null;
		if (eSum == null || eSum.getSize() == 0) {
			log.append("No events found");
			// need to pad spectra
			for (int r = 0; r < roiMax; r++) {
				allSpectra[r].add(null);
			}
			allSums.add(null);
			allPositions.add(null);
		} else {
			// accumulate event sums and photons
			if (totalSum == null) {
				totalSum = eSum;
			} else {
				totalSum = DatasetUtils.concatenate(new IDataset[] {totalSum, eSum}, 0);
			}
			log.append("Found %d photon events, current total = %s", eSum.getSize(), totalSum.getSize());
			allSums.add(eSum);
			allPositions.add(events.get(1));
	
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

		log.append("At frame %d/%d", si.getSliceNumber(), smax);
		System.err.printf("At frame %d/%d\n", si.getSliceNumber(), smax);
		summaryData.clear(); // do not save anything yet

		if (si.getSliceNumber() == smax - 1) {
			addSummaryData();
			int[][][] allSingle = null;
			int[][][] allMultiple = null;
			int bin = 0;
			int bmax = 0;
			int single = 0;
			int multiple = 0;

			if (bins != null) {
				summaryData.add(a);

				// After last image, calculate splitting levels
				Dataset x = bins.getSlice(new Slice(-1));
				Dataset dh = Maths.derivative(x, h, 3);
				MetadataUtils.setAxes(dh, bins);
				displayData.set(0, dh);
				List<Double> z = DatasetUtils.crossings(x, dh, 0);
				log.append("Histogram derivative zero-crossings = %s", z);
				single = (int) Math.floor(z.get(0));
				multiple = single + countsPerPhoton; // (int) Math.floor(z.get(1)); // TODO
				log.append("Setting limits for single photon as [%d, %d)", single, multiple);
				summaryData.add(ProcessingUtils.createNamedDataset(single, "single_photon_minimum"));
				summaryData.add(ProcessingUtils.createNamedDataset(multiple, "multiple_photon_minimum"));

				bin = model.getBins();
				bmax = bin * original.getShapeRef()[model.getEnergyIndex()];

				// per image, separate by sum
				allSingle = new int[roiMax][smax][];
				allMultiple = new int[roiMax][smax][];
				List<Double> cX = new ArrayList<>();
				List<Double> cY = new ArrayList<>();
				boolean first = true;
				int j = 0;
				for (int i = 0; i < smax; i++) {
					for (int r = 0; r < roiMax; r++) {
						Dataset sums = allSums.get(i);
						if (sums == null) {
							continue;
						}
						cX.clear();
						cY.clear();

						int[] hSingle = new int[bmax];
						int[] hMultiple = new int[bmax];
						allSingle[r][j] = hSingle;
						allMultiple[r][j++] = hMultiple;
						Dataset posn = allPositions.get(i);
						StraightLine line = getStraightLine(r);
						IRectangularROI roi = getROI(r);
						shiftAndBinPhotonEvents(0, single, multiple, bin, bmax, cX, cY, i, line, roi, sums, posn,
								hSingle, hMultiple);

						// add coords from first (non-omitted) frame
						if (first) {
							int side = cX.size();
							if (side > 0) {
								first = false;
								Dataset t;
								t = DatasetFactory.createFromList(cY);
								MetadataUtils.setAxes(t, ProcessingUtils.createNamedDataset(DatasetFactory.createFromList(cX), "x"));
								// for DExplore's 2d scatter point
//									t = DatasetFactory.zeros(side, side);
//									ProcessingUtils.addAxes(t, ProcessingUtils.createNamedDataset(DatasetFactory.createFromList(cX), "x"),
//											ProcessingUtils.createNamedDataset(DatasetFactory.createFromList(cY), "y"));
								t.setName("photon_positions_" + r);
								summaryData.add(t);
							}
						}
					}
				}
				if (j < smax) { // truncate for omitted frames
					for (int r = 0; r < roiMax; r++) {
						allSingle[r] = Arrays.copyOf(allSingle[r], j);
						allMultiple[r] = Arrays.copyOf(allMultiple[r], j);
					}
				}
			}

			for (int r = 0; r < roiMax; r++) {
				// total and correlated spectra
				Dataset sp = null;
				IDataset[] sArray = toArray(allSpectra[r]);
				sp = accumulate(sArray);
				if (sp == null) {
					continue;
				}
				sp.setName("total_spectrum_" + r);
				summaryData.add(sp);

				Dataset ax = null;
				try {
					ax = DatasetUtils.sliceAndConvertLazyDataset(sp.getFirstMetadata(AxesMetadata.class).getAxis(0)[0]);
				} catch (DatasetException e) {
				}

				DatasetToDatasetFunction reg = getCorrelateShifter(true);
				List<? extends IDataset> results = reg.value(sArray);
				for (int i = 0; i < sArray.length; i++) {
					sArray[i] = results.get(2*i + 1);
				}
				sp = accumulate(sArray);
				sp.setName("correlated_spectrum_" + r);
				MetadataUtils.setAxes(sp, ax);
				summaryData.add(sp);

				List<Double> shift = new ArrayList<>();
				for (int i = 0; i < sArray.length; i++) {
					shift.add(DatasetUtils.convertToDataset(results.get(2*i)).getDouble());
				}
				summaryData.add(ProcessingUtils.createNamedDataset((Serializable) shift, "correlated_spectrum_shift_" + r));

				if (bins == null) {
					continue; // no photon events!!!
				}

				double el0 = getZeroEnergyOffset(r); // elastic line intercept
				Dataset energies = DatasetFactory.createRange(bmax);
				energies.iadd(-bin*el0); // adjust zero TODO sign wrong??
				energies.imultiply(-energyDispersion[r]/bin);
				energies.setName(ENERGY_LOSS);
				Dataset t = DatasetFactory.createFromObject(allSingle[r]);
				t.setName("single_photon_spectrum_" + r);
				MetadataUtils.setAxes(t, null, energies);
				Dataset sSpectra = t;
				summaryData.add(t);

				Dataset nf = t.sum(1); // to work out per-image as single fraction of total events
				nf.setName("single_photon_count_" + r);
				Dataset sEvents = nf;
				summaryData.add(nf);

				t = DatasetFactory.createFromObject(allMultiple[r]);
				t.setName("multiple_photon_spectrum_" + r);
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
				log.append("Events: single/total = %g/%g = %g ", ts, tm, ts/(ts + tm));
				summaryData.add(ProcessingUtils.createNamedDataset(ts/(ts + tm), "total_single_events_fraction_" + r));

				sp = sSpectra.sum(0);
				sp.setName("total_single_photon_spectrum_" + r);
				MetadataUtils.setAxes(sp, energies);
				summaryData.add(sp);

				sp = mSpectra.sum(0);
				sp.setName("total_multiple_photon_spectrum_" + r);
				MetadataUtils.setAxes(sp, energies);
				summaryData.add(sp);

				if (model.getCorrelateOption() == CORRELATE_PHOTON.USE_INTENSITY_SHIFTS) {
					for (int i = 0; i < sArray.length; i++) { // image to image shifts
						double offset = DatasetUtils.convertToDataset(results.get(2*i)).getDouble();
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
						shiftAndBinPhotonEvents(offset, single, multiple, bin, bmax, null, null, i, line, roi, sums, posn,
								hSingle, hMultiple);
					}

					t = DatasetFactory.createFromObject(allSingle[r]).sum(0);
					t.setName("correlated_single_photon_spectrum_" + r);
					MetadataUtils.setAxes(t, energies);
					summaryData.add(t);

					t = DatasetFactory.createFromObject(allMultiple[r]).sum(0);
					t.setName("correlated_multiple_photon_spectrum_" + r);
					MetadataUtils.setAxes(t, energies);
					summaryData.add(t);
				} else {
					reg = getCorrelateShifter(true);
					sArray = new IDataset[sSpectra.getShapeRef()[0]];
					for (int i = 0; i < sArray.length; i++) {
						sArray[i] = sSpectra.getSliceView(new Slice(i, i+1)).squeeze();
					}
					results = reg.value(sArray);
					for (int i = 0; i < sArray.length; i++) {
						sArray[i] = results.get(2*i + 1);
					}
					sp = accumulate(sArray);
					sp.setName("correlated_single_photon_spectrum_" + r);
					MetadataUtils.setAxes(sp, energies);
					summaryData.add(sp);
					shift.clear();
					for (int i = 0; i < sArray.length; i++) {
						shift.add(DatasetUtils.convertToDataset(results.get(2*i)).getDouble());
					}
					summaryData.add(ProcessingUtils.createNamedDataset((Serializable) shift, "correlated_single_photon_spectrum_shift_" + r));

					sArray = new IDataset[mSpectra.getShapeRef()[0]];
					for (int i = 0; i < sArray.length; i++) {
						sArray[i] = mSpectra.getSliceView(new Slice(i, i+1)).squeeze();
					}
					results = reg.value(sArray);
					for (int i = 0; i < sArray.length; i++) {
						sArray[i] = results.get(2*i + 1);
					}
					sp = accumulate(sArray);
					sp.setName("correlated_multiple_photon_spectrum_" + r);
					MetadataUtils.setAxes(sp, energies);
					summaryData.add(sp);
					shift.clear();
					for (int i = 0; i < sArray.length; i++) {
						shift.add(DatasetUtils.convertToDataset(results.get(2*i)).getDouble());
					}
					summaryData.add(ProcessingUtils.createNamedDataset((Serializable) shift, "correlated_multiple_photon_spectrum_shift_" + r));
				}
			}
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

	// bins photons according to their locations
	private void shiftAndBinPhotonEvents(double offset, int single, int multiple, int bin, int bmax, List<Double> cX,
			List<Double> cY, int i, StraightLine line, IRectangularROI roi, Dataset sums, Dataset posn, int[] hSingle, int[] hMultiple) {
		double slope = -line.getParameterValue(0);
		for (int j = 0, jmax = sums.getSize(); j < jmax; j++) {
			double px = posn.getDouble(j, 1);
			double py = posn.getDouble(j, 0) + offset;

			if (roi != null && !roi.containsPoint(px, py)) {
				continue; // separate by regions
			}

			// add coords
			if (i == 0 && cX != null && cY != null) {
				cX.add(px);
				cY.add(py);
			}

			// correct for tilt
			py += slope*px;

			double ps = sums.getDouble(j);
			if (ps >= single) {
				int ip = (int) Math.floor(bin * py); // discretize position
				if (ip >= 0 && ip < bmax) {
					if (ps < multiple) {
						hSingle[ip]++;
					} else {
						hMultiple[ip]++;
					}
				}
			}
		}
	}

	private static IDataset[] toArray(List<Dataset> d) {
		int imax = d.size();
		IDataset[] a = new IDataset[imax];
		int j = 0;
		for (int i = 0; i < imax; i++) {
			Dataset di = d.get(i);
			if (di != null) {
				a[j++] = di.getView(true).squeeze();
			}
		}
		if (j < imax) {
			a = Arrays.copyOf(a, j);
		}
		return a;
	}

	private Dataset accumulate(IDataset... d) {
		Dataset sp = null;
		for (IDataset s : d) {
			if (s == null) {
				continue;
			}
			if (sp == null) {
				sp = DatasetUtils.convertToDataset(s).clone();
			} else {
				sp.iadd(s);
			}
		}
		return sp;
	}

	private DatasetToDatasetFunction getCorrelateShifter(boolean noisy) {
		boolean all = model.getCorrelateOption() == CORRELATE_PHOTON.ALL_PAIRS;
		boolean first = model.getCorrelateOrder() == CORRELATE_ORDER.FIRST;
		if (noisy) {
			RegisterNoisyData1D reg = new RegisterNoisyData1D();
			reg.setFilter(DatasetFactory.ones(5).imultiply(1./5));
			reg.setPeakCentroidThresholdFraction(0.85);
			reg.setFitAll(all);
			reg.setUseFirstAsAnchor(first);
			return reg;
		}

		RegisterData1D reg = new RegisterData1D();
		reg.setFilter(DatasetFactory.ones(5).imultiply(1./5));
		reg.setWindowFunction(0.25);
		reg.setUseFirstAsAnchor(first);
		return reg;
	}

	/**
	 * @param r
	 * @param in
	 * @param slope override value
	 * @param clip if true, clip columns where rows contribute from outside image 
	 * @return elastic line position and spectrum datasets
	 */
	public Dataset[] makeSpectrum(int r, Dataset in, double slope, boolean clip) {
		// shift and accumulate spectra
		int rows = in.getShapeRef()[0];
		Dataset y = DatasetFactory.createRange(rows);
		y.iadd(offset[0]);
		StraightLine line = getStraightLine(r);
		Dataset elastic;
		if (slope == 0) {
			slope = line.getParameterValue(0);
			elastic = line.calculateValues(y); // absolute position of elastic line to use a zero point
		} else {
			elastic = DatasetFactory.createRange(rows);
			elastic.imultiply(slope);
			elastic.iadd(line.getParameterValue(1));
		}
		if (model.getEnergyOffsetOption() == ENERGY_OFFSET.MANUAL_OVERRIDE) {
			double offset = r == 0 ? model.getEnergyOffsetA() : model.getEnergyOffsetB();
			if (Double.isFinite(offset)) {
				elastic.iadd(offset - line.getParameterValue(1));
			}
		}
	
		Dataset spectrum = makeSpectrum(in, slope, clip);
		if (Double.isFinite(slope)) {
			if (clip && slope < 0) { // adjust for shift by clipping
				elastic.iadd(spectrum.getSize() - in.getShapeRef()[1]);
			}
		}
	
		if (model.getEnergyOffsetOption() == ENERGY_OFFSET.TURNING_POINT) {
			int offset = findTurningPoint(false, spectrum);
			elastic.isubtract(offset);
		}
		return new Dataset[] {elastic, spectrum};
	}

	private int findTurningPoint(boolean fromFirst, Dataset y) {
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
			offset = getStraightLine(r).getParameterValue(1); // elastic line intercept
		}
		return offset;
	}
}

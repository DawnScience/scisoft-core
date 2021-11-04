/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.rixs;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.diffraction.DiffractionCrystalEnvironment;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationDataForDisplay;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationLog;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.roi.IRectangularROI;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.NodeLink;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.dataset.impl.Signal;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.roi.ROISliceUtils;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceInformation;
import org.eclipse.dawnsci.nexus.NexusConstants;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.BooleanDataset;
import org.eclipse.january.dataset.Comparisons;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.dataset.SliceND;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;

import uk.ac.diamond.scisoft.analysis.fitting.functions.StraightLine;
import uk.ac.diamond.scisoft.analysis.io.NexusTreeUtils;
import uk.ac.diamond.scisoft.analysis.processing.LocalServiceManager;
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.KnownDetector;
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;

/**
 * Find and fit the RIXS elastic line image to a straight line so other image
 * <p>
 * Returns line fit parameters and also peak FWHM as auxiliary data
 */
public abstract class RixsBaseOperation<T extends RixsBaseModel>  extends AbstractOperation<T, OperationData> implements PropertyChangeListener {

	protected int[] offset = new int[2]; // ROI origin
	protected List<IDataset> displayData = new ArrayList<>();
	protected List<IDataset> auxData = new ArrayList<>();
	protected List<IDataset> summaryData = new ArrayList<>();

	protected OperationLog log = new OperationLog();
	protected int countsPerPhoton;
	public static final int MAX_ROIS = 2;
	protected StraightLine[] lines = new StraightLine[MAX_ROIS];
	protected boolean useBothROIs;
	protected int roiMax;
	private Dataset currentCountTime;
	private double countTime = 0;
	private double drainCurrent;
	private BooleanDataset usedFrames = null;
	private double detectorAngle = Double.NaN;
	private KnownDetector detector;
	private IRectangularROI[] rois = new IRectangularROI[2];
	protected double xrayEnergy;

	@Override
	public void setModel(T model) {
		super.setModel(model);
		model.addPropertyChangeListener(this);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		updateFromModel(false, evt == null ? null : evt.getPropertyName());
	}

	/**
	 * Update internal state from model
	 * <p>Done when model gets changed by user through GUI or on first slice in process
	 * @param throwEx
	 * @param name name of property changed
	 */
	abstract void updateFromModel(boolean throwEx, String name);

	/**
	 * Updates useBothROIs and roiMax so override for more
	 */
	protected void updateROICount() {
		useBothROIs = model.getRoiA() != null && model.getRoiB() != null;
		roiMax = useBothROIs ? 2 : 1;
	}

	/**
	 * Reset list and fill with nulls
	 * @param list
	 * @param n number of nulls
	 */
	protected static void resetList(List<?> list, int n) {
		list.clear();
		for (int i = 0; i < n; i++) {
			list.add(null);
		}
	}

	@Override
	public String getId() {
		return getClass().getName();
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	protected BooleanDataset getUsedFrames() {
		return usedFrames;
	}

	abstract boolean skipFrame(int size, int frame);

	private static final int MARGIN = 10; // margin by which to reduce regions

	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		displayData.clear();
		auxData.clear();

		SliceFromSeriesMetadata smd = input.getFirstMetadata(SliceFromSeriesMetadata.class);
		SliceInformation si = smd.getSliceInfo();
		if (si.isFirstSlice()) {
			detector = KnownDetector.getDetector(smd.getFilePath(), smd.getDatasetName(), input);

			summaryData.clear();
			log.clear();
			if (usedFrames == null || usedFrames.getSize() != si.getTotalSlices()) {
				usedFrames = DatasetFactory.zeros(BooleanDataset.class, si.getTotalSlices());
			}
			countTime = 0;
			currentCountTime = null;
			resetProcess(input, si.getTotalSlices());
			updateFromModel(true, null);
			parseNexusFile(smd.getFilePath());
			double cropY = -1; // negative for no crop

			if (model.isCropROI() && KnownDetector.getDetector(smd.getFilePath(), smd.getDatasetName(), input) == KnownDetector.ANDOR) {
				cropY = calculateCropYForAndor(detectorAngle);
			}

			for (int r = 0; r < roiMax; r++) {
				rois[r] = null;
				IRectangularROI roi = getROI(r);
				boolean configure = roi == null;
				if (configure) {
					roi = KnownDetector.getDefaultROI(detector, input.getShape(), roiMax, r, MARGIN);
				}
				if (cropY > 0) {
					roi = cropROIForAndor(roi, cropY);
				}
				rois[r] = roi;
				if (configure || cropY > 0) {
					addConfiguredField(r == 0 ? "roiA" : "roiB", roi);
				}
			}
		}

		if (currentCountTime != null) {
			countTime += ((Number) currentCountTime.getSlice(si.getInputSliceWithoutDataDimensions()).sum(true)).doubleValue();
		}

		int s = si.getSliceNumber();
		boolean skip = skipFrame(si.getTotalSlices(), s);
		usedFrames.setAbs(si.getSliceNumber(), !skip);
		if (skip) {
			return null;
		}

		initializeProcess(input);

		IDataset result = input;
		for (int r = 0; r < roiMax; r++) {
			result = processRegion(s, input, rois[r], r);
		}

		OperationDataForDisplay od = new OperationDataForDisplay();
		od.setShowSeparately(true);
		od.setLog(log);
		od.setData(result);
		if (displayData.size() > 0) {
			od.setDisplayData(displayData.toArray(new IDataset[displayData.size()]));
		}
		od.setAuxData(auxData.toArray(new Serializable[auxData.size()]));
		if (si.isFirstSlice()) {
			setConfiguredFields(od);
		}
		return od;
	}

	private double calculateCropYForAndor(double detectorAngle) {
		if (Double.isFinite(detectorAngle)) {
			// formula to fit (12., 1200.), (20., 1600.), (30., 1800.)
			double cy = 2048. - 132.3922 / Math.tan(Math.toRadians(detectorAngle - 3.1425));

			log.append("Setting crop y for Andor to %g", cy);
			return cy;
		}
		return -1;
	}

	/**
	 * Crop ROI for Andor if necessary
	 * @param r
	 * @return cropped ROI or original
	 */
	private IRectangularROI cropROIForAndor(IRectangularROI r, double cropY) {
		double ey = r.getValue(1) + r.getLength(1);

		if (cropY < ey) {
			RectangularROI roi = new RectangularROI(r);
			roi.setLengths(roi.getLength(0), Math.floor(roi.getLength(1) + cropY - ey));
			return roi;
		}

		return r;
	}

	private IDataset processRegion(int s, IDataset input, IRectangularROI roi, int r) {
		Dataset in = preprocessImage(input, roi);
		if (in.getSize() == 0) {
			return null;
		}
		if (model.isUseCutoff()) {
			int cutoff = (int) Math.floor(countsPerPhoton * model.getCutoff());
			BooleanDataset hots = Comparisons.greaterThan(in, cutoff);
			log.appendSuccess("Number of cut-off pixels = %d", hots.sum());
			int size = model.getCutoffSize();
			if (size > 1) {
				double aboveZero = 1./1024;
				Comparisons.greaterThan(Signal.convolveToSameShape(hots, DatasetFactory.ones(DoubleDataset.class, size, size), null), aboveZero, hots);
			}
			in.setByBoolean(0, hots);
		}

		log.appendSuccess("Processing region %d", r);
		return processImageRegion(s, input, r, in);
	}

	protected IRectangularROI getROI(int r) {
		IRectangularROI roi = rois[r];
		if (roi == null) {
			if (useBothROIs) {
				roi = r == 0 ? model.getRoiA() : model.getRoiB();
			} else {
				roi = model.getRoiA();
				if (roi == null) {
					roi = model.getRoiB();
				}
			}
		}

		return roi;
	}

	/**
	 * Override to reset state of processing object. This is called for the first slice of data only.
	 * @param original
	 * @param total total number of slices
	 */
	abstract void resetProcess(IDataset original, int total);

	/**
	 * Initialize log and fields as necessary
	 * @param original
	 */
	abstract void initializeProcess(IDataset original);

	/**
	 * Process a region in image
	 * @param sn slice number
	 * @param original image
	 * @param rn region number
	 * @param in sub-image within region
	 * @return
	 */
	abstract IDataset processImageRegion(int sn, IDataset original, int rn, Dataset in);

	protected static final int STRAIGHT_LINE_M = 0;
	protected static final int STRAIGHT_LINE_C = 1;
	protected StraightLine getStraightLine(int r) {
		return lines[r >= lines.length ? 0 : r]; // in case number of ROIs have increased
	}

	protected static Dataset makeSpectrum(Dataset in, double slope, boolean clip, boolean average) {
		Dataset spectrum;
		if (Double.isFinite(slope)) {
			spectrum = sumImageAlongSlope(in, slope, clip);
			if (average) {
				spectrum.idivide(in.getShapeRef()[0]);
			}
		} else {
			int rows = in.getShapeRef()[0];
			spectrum = DatasetFactory.zeros(rows).fill(Double.NaN);
		}
		return spectrum;
	}

	/**
	 * Sum image row-by-row offset by slope
	 * @param image
	 * @param slope
	 * @param clip if true, clip columns where rows contribute from outside image 
	 * @return summed image (when clipped, an axis is added that gives the clipped indices)
	 */
	public static Dataset sumImageAlongSlope(Dataset image, double slope, boolean clip) {
		int[] shape = image.getShapeRef();
		int rows = shape[0];
		int cols = shape[1];
		Dataset result = DatasetFactory.zeros(cols);
		SliceND slice = new SliceND(shape);
		Dataset c = DatasetFactory.createRange(cols);
		Dataset nc = c.clone();
		for (int i = 0; i < rows; i++) {
			slice.setSlice(0, i, i+1, 1);
			Dataset row = image.getSliceView(slice).squeeze();
			result.iadd(Maths.interpolate(nc, row, c, 0, 0));
			nc.isubtract(slope);
		}
		if (clip) {
			int b = slope > 0 ? 0 : (int) (Math.ceil(-slope*rows) + 1);
			int e = cols - (slope > 0 ? (int) Math.ceil(slope*rows) : 0);
			Slice s = new Slice(b, e);
			result = result.getSliceView(s);
			AxesMetadata am;
			try {
				am = MetadataFactory.createMetadata(AxesMetadata.class, 1);
				am.addAxis(0, c.getSliceView(s));
				result.addMetadata(am);
			} catch (MetadataException e1) {
				// do nothing
			}
			System.err.println("Clipping sum to " + b + ":" + e + "; size = " + result.getSize() + ", for slope = " + slope);
		}
		return result;
	}

	private static final String DETECTOR_LOCAL_NAME = "local_name";
	private static final String DETECTOR_NAME_ANDOR = "andor";
	private static final String DETECTOR_NAME_XCAM = "xcam";

	/**
	 * Parse NeXus file to set various fields
	 * @param filePath
	 */
	protected void parseNexusFile(String filePath) {
		try {
			Tree t = LocalServiceManager.getLoaderService().getData(filePath, null).getTree();

			if (t == null) {
				log.appendFailure("Could not load tree from file %s", filePath);
				countsPerPhoton = model.getCountsPerPhoton();
				return;
			}

			GroupNode root = t.getGroupNode();
			// entry1:NXentry
			//     before_scan:NXcollection
			//         andorPreampGain:NXcollection/andorPreampGain [1, 2, 4]
			//         pgmEnergy:NXcollection/ [energy in eV, always single value, even for an energy scan]

			GroupNode entry = (GroupNode) NexusTreeUtils.requireNode(root, NexusConstants.ENTRY);
			GroupNode instrument = (GroupNode) NexusTreeUtils.requireNode(entry, NexusConstants.INSTRUMENT);

			// find first area detector
			List<NodeLink> detectors = NexusTreeUtils.findNodes(instrument, NexusConstants.DETECTOR);
			GroupNode detector = null;
			for (NodeLink l : detectors) {
				if (l.isDestinationGroup()) {
					GroupNode g = (GroupNode) l.getDestination();
					if (g.containsDataNode(NexusConstants.DATA_DATA)) {
						if (g.getDataNode(NexusConstants.DATA_DATA).getRank() > 1) {
							detector = g;
							break;
						}
					}
				}
			}
			if (detector == null) {
				String msg = "Could not find NXdetector with image data";
				log.append(msg);
				throw new NexusException(msg);
			}

			currentCountTime = DatasetUtils.sliceAndConvertLazyDataset(detector.getDataNode("count_time").getDataset());

			GroupNode mdg = entry.getGroupNode("before_scan");
			if (mdg != null) {
				parseOldDLSNeXus(filePath, detector, mdg);
			} else if (entry.containsGroupNode("diamond_scan")) {
				parseNewDLSNexus(filePath, instrument, detector);
			} else {
				throw new NexusException("File does not contain a before_scan or diamond_scan collection");
			}

		} catch (Exception e) {
			log.appendFailure("Could not parse Nexus file %s: %s", filePath, e);
		}

		log.append("Counts per single photon event = %d", countsPerPhoton);
	}


	private double getDouble(GroupNode g, String name) throws NexusException {
		try {
			return NexusTreeUtils.getFirstDouble(g.getDataNode(name));
		} catch (NexusException e) {
			log.appendFailure("Could not read value from %s", name, e);
			throw e;
		}
	}

	private String getString(GroupNode g, String name) throws NexusException {
		try {
			return NexusTreeUtils.getFirstString(g.getDataNode(name));
		} catch (NexusException e) {
			log.appendFailure("Could not read value from %s", name, e);
			throw e;
		}
	}

	private void parseOldDLSNeXus(String filePath, GroupNode detector, GroupNode mdg) throws NexusException {
		countsPerPhoton = -1;
		xrayEnergy = -1;
		try {
			xrayEnergy = getEnergy(mdg);
			countsPerPhoton = calculateCountsPerPhoton(mdg);
		} catch (Exception e) {
			if (detector.containsDataNode(DETECTOR_LOCAL_NAME)) {
				try {
					String[] names = NexusTreeUtils.getStringArray(detector.getDataNode(DETECTOR_LOCAL_NAME), 1);
					if (names[0].toLowerCase().contains(DETECTOR_NAME_XCAM)) {
						double energy = getEnergy(mdg);
						countsPerPhoton = (int) (800 * (energy / 933.)); // rough estimate from XCAM commissioning
					} else {
						log.appendFailure("Unknown detector in Nexus file %s: %s = %s", filePath, DETECTOR_LOCAL_NAME, names[0]);
					}
				} catch (Exception e1) {
					log.appendFailure("Could not read %s in NXdetector from Nexus file %s: %s", DETECTOR_LOCAL_NAME, filePath, e1);
				}
			} else {
				log.appendFailure("Could not calculate counts per photon from Nexus file %s: %s", filePath, e);
			}
			if (countsPerPhoton < 0) {
				countsPerPhoton = model.getCountsPerPhoton();
			}
		}

		drainCurrent = parseBeforeScanItem(mdg, "draincurrent");
		detectorAngle = parseBeforeScanItem(mdg, "specgamma");

		// TODO ring current, other things
	}

	/**
	 * Add standard items to summary data for monitoring and normalizing
	 */
	protected void addSummaryData() {
		summaryData.add(ProcessingUtils.createNamedDataset(drainCurrent, "drain_current"));
		summaryData.add(ProcessingUtils.createNamedDataset(countsPerPhoton, "counts_per_photon"));
		summaryData.add(ProcessingUtils.createNamedDataset(countTime, "total_count_time"));
	}

	private static final double PAIR_PRODUCTION_ENERGY = 3.67; // energy required to generate an electron-hole pair in silicon

	// XCAM operating temperature at -110C = 160K (16umx16um)
	// RIXSCam2 2 detectors, 3264x1608 (2 CCDs, left and right dark reference bands per CCD, 16 pixel columns)
	// Andor operating temperature at -60C = 210K
	// MN Mazziotta
	// Electron–hole pair creation energy and Fano factor temperature dependence in silicon
	// Nuclear Instruments and Methods in Physics Research Section A: Accelerators, Spectrometers, Detectors and Associated Equipment
	// Volume 584, Issues 2–3, 11 January 2008, Pages 436-439
	// photons of 500eV @160K, 3.694
	// photons of 500eV @210K, 3.680
	// values from ANDOR iKon-L CCD system performance booklet (20160824)
	// Head DO936N-00Z-#BN-9UY, serial no CCD-19600
	// CCD from E2V, CCD42-40, 2048x2048 (13.5umx13.5um), serial no 15242-01-06
	private static final double[] ANDOR_AD_RATE = new double[] {0.05, 1, 3}; // rate in MHz
	private static final String[] ANDOR_AD_RATE_ENUM = {"0.05 MHz", "1.00 MHz", "3.00 MHz"};
	private static final int[] ANDOR_PREAMP_GAIN = new int[] {1, 2, 4};
	private static final String[] ANDOR_PREAMP_GAIN_ENUM = {"1.00", "2.00", "4.00"};
	private static final double[][] ANDOR_SENSITIVITY = new double[][] {{3.5, 1.9, 1.0}, {3.4, 1.8, 1.0}, {3.1, 1.8, 1.0}}; // in electrons per AD count

	private int calculateCountsPerPhoton(GroupNode mdg) throws NexusException {
		try {
			int gain = (int) parseBeforeScanItem(mdg, "andorPreampGain");
			double speed = parseBeforeScanItem(mdg, "andorADCSpeed");

			return (int) Math.floor(xrayEnergy / andorSensitivity(gain, speed));
		} catch (Exception e) {
			throw new NexusException("Not an Andor detector:", e);
		}
	}

	/**
	 * @param mdg
	 * @return photon energy in eV
	 * @throws NexusException
	 */
	private double getEnergy(GroupNode mdg) throws NexusException {
		return parseBeforeScanItem(mdg, "pgmEnergy");
	}

	/**
	 * @param gain
	 * @param speed
	 * @return photon energy sensitivity (eV/AD count)
	 * @throws NexusException
	 */
	private double andorSensitivity(int gain, double speed) throws NexusException {
		int gi = Arrays.binarySearch(ANDOR_PREAMP_GAIN, gain);
		if (gi < 0) {
			throw new OperationException(this, "Gain value not allowed: " + gain);
		}
		int si = Arrays.binarySearch(ANDOR_AD_RATE, speed);
		if (si < 0) {
			throw new OperationException(this, "ADC speed value not allowed: " + speed);
		}

		return ANDOR_SENSITIVITY[si][gi] * PAIR_PRODUCTION_ENERGY;
	}

	/**
	 * @param gain enumeration string
	 * @param speed enumeration string
	 * @return photon energy sensitivity (eV/AD count)
	 * @throws NexusException
	 */
	private double andorSensitivity(String gain, String speed) throws NexusException {
		int gi = Arrays.binarySearch(ANDOR_PREAMP_GAIN_ENUM, gain);
		if (gi < 0) {
			throw new OperationException(this, "Gain value not allowed: " + gain);
		}
		int si = Arrays.binarySearch(ANDOR_AD_RATE_ENUM, speed);
		if (si < 0) {
			throw new OperationException(this, "ADC speed value not allowed: " + speed);
		}

		return ANDOR_SENSITIVITY[si][gi] * PAIR_PRODUCTION_ENERGY;
	}

	private double parseBeforeScanItem(GroupNode bsg, String name) throws NexusException {
		GroupNode ig = bsg.getGroupNode(name);
		if (ig == null) {
			throw new NexusException("Before_scan collection does not contain " + name);
		}
		DataNode id = ig.getDataNode(name);
		if (id == null) {
			throw new NexusException("Before_scan collection has group but does not contain data node " + name);
		}
		return NexusTreeUtils.getFirstDouble(id);
	}

	private void parseNewDLSNexus(String filePath, GroupNode instrument, GroupNode detector) {
		xrayEnergy = -1;
		countsPerPhoton = -1;
		try {
			GroupNode mono = NexusTreeUtils.getDefaultNXobject(instrument, null, NexusConstants.MONOCHROMATOR);
			xrayEnergy = getDouble(mono, "energy");
		} catch (Exception e) {
			log.appendFailure("Could not read X-ray energy from NXmonochromator from Nexus file %s: %s", filePath, e);
		}

		try {
			String dName = NexusTreeUtils.getFirstString(detector.getDataNode(DETECTOR_LOCAL_NAME));
			GroupNode settings = detector.getGroupNode(dName + "_settings");
			dName = dName.toLowerCase();
			if (dName.contains(DETECTOR_NAME_ANDOR)) {
				String gainEnum = getString(settings, "preamp_gain");
				String speedEnum = getString(settings, "adc_speed");
				countsPerPhoton = (int) Math.floor(xrayEnergy / andorSensitivity(gainEnum, speedEnum));
			} else if (dName.contains(DETECTOR_NAME_XCAM)) {
				countsPerPhoton = (int) (800 * (xrayEnergy / 933.)); // rough estimate from XCAM commissioning
			} else {
				log.appendFailure("Unknown detector in Nexus file %s: %s = %s", filePath, DETECTOR_LOCAL_NAME, dName);
			}
		} catch (Exception e) {
			log.appendFailure("Could not calculate counts per photon from Nexus file %s: %s", filePath, e);
			if (countsPerPhoton < 0) {
				countsPerPhoton = model.getCountsPerPhoton();
			}
		}

		try {
			GroupNode manipulator = instrument.getGroupNode("manipulator");
			drainCurrent = getDouble(manipulator, "draincurrent");
			GroupNode spectrometer = instrument.getGroupNode("spectrometer");
			detectorAngle = getDouble(spectrometer, "specgamma");
		} catch (Exception e) {
			log.appendFailure("Could not read drain current or spectrometer gamma values from Nexus file %s: %s", filePath, e);
		}
		// TODO ring current, other things
	}

	// TODO work-in-progress on structure of NeXus RIXS application definition
	protected void parseDesiredNexusFile(String filePath) {
		try {
			Tree t = LocalServiceManager.getLoaderService().getData(filePath, null).getTree();

			GroupNode entry = (GroupNode) NexusTreeUtils.requireNode(t.getGroupNode(), NexusConstants.ENTRY);

			// TODO find photon energy, (decide where it should be recorded)
			GroupNode sample = (GroupNode) NexusTreeUtils.requireNode(entry, NexusConstants.SAMPLE);
			GroupNode beam = (GroupNode) NexusTreeUtils.requireNode(sample, NexusConstants.BEAM);
			DiffractionCrystalEnvironment dce = new DiffractionCrystalEnvironment();
			NexusTreeUtils.parseBeam(beam, dce);

			GroupNode instrument = (GroupNode) NexusTreeUtils.requireNode(entry, NexusConstants.INSTRUMENT);
			GroupNode detector = (GroupNode) NexusTreeUtils.requireNode(instrument, NexusConstants.DETECTOR);
			parseDesiredNXrixs(detector, 1e3 * dce.getEnergy());
		} catch (Exception e) {
			log.appendFailure("Could not parse Nexus file %s:%s", filePath, e);
			countsPerPhoton = model.getCountsPerPhoton();
		}

		log.append("Counts per single photon event = %d", countsPerPhoton);
	}

	/**
	 * Parse detector and set fields
	 * @param detector
	 * @param pEnergy photon energy in eV
	 * @throws NexusException 
	 */
	protected void parseDesiredNXrixs(GroupNode detector, double pEnergy) throws NexusException {
		String ed = detector.getDataNode("energy_direction").getString();
		model.setEnergyDirection(ed.toLowerCase().equals("slow") ? RixsBaseModel.ENERGY_DIRECTION.SLOW : RixsBaseModel.ENERGY_DIRECTION.FAST);

		// energy required for each photoelectron [eV / e^-]
		double pe = NexusTreeUtils.getFirstDouble(detector.getDataNode("photoelectrons_energy"));
		// digitization of photoelectron to AD units [ADu / e^-]
		double ds = NexusTreeUtils.getFirstDouble(detector.getDataNode("detector_sensitivity"));

		countsPerPhoton = (int) Math.floor(pEnergy * ds / pe);
	}

	/**
	 * Slice and transpose image as necessary
	 * @param image
	 * @return image
	 */
	private Dataset preprocessImage(IDataset image, IRectangularROI r) {
		Dataset in;
		int axis = model.getEnergyIndex();
		if (axis == 0) { // ensure we work row-wise
			in = DatasetUtils.transpose(image);
		} else {
			in = DatasetUtils.convertToDataset(image);
		}

		offset[0] = 0;
		offset[1] = 0;
		if (r == null) {
			return in;
		}

		if (in == image) {
			in = in.getView(false);
		}
		in.clearMetadata(null);
		Slice[] s = getSlice(in.getShapeRef(), axis, r);
		log.append("Slicing image %s from %s", Arrays.toString(s), Arrays.toString(in.getShapeRef()));
		return s[0] == null && s[1] == null ? in : in.getSliceView(s);
	}

	private Slice[] getSlice(int[] shape, int axis, IRectangularROI r) {
		double[] p = r.getPointRef();
		Slice s0 = ROISliceUtils.createSlice(p[axis], r.getLength(axis), shape[axis]);
		axis = 1 - axis;
		Slice s1 = ROISliceUtils.createSlice(p[axis], r.getLength(axis), shape[axis]);

		offset[0] = s0 == null ? 0 : s0.getStart();
		offset[1] = s1 == null ? 0 : s1.getStart();
		return new Slice[] {s0, s1};
	}

//	// FIXME this is wasteful as these axis datasets are replicated for each
//	// aux data
//	// TODO fix NexusFileExecutionVisitor to automatically link any axis datasets
//	// from the SSFMD#getParent()'s axes metadata
//	private Dataset addPositionAxis(Dataset y) {
//		try {
//			AxesMetadata am;
//			am = MetadataFactory.createMetadata(AxesMetadata.class, 1);
//			am.addAxis(0, position);
//			y.addMetadata(am);
//		} catch (Exception e) {
//		}
//
//		return y;
//	}
//

}

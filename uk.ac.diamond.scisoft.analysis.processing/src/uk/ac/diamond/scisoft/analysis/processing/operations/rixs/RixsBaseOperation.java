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
import org.eclipse.dawnsci.analysis.api.fitting.functions.IFunction;
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
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.january.IMonitor;
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

import uk.ac.diamond.scisoft.analysis.fitting.functions.StraightLine;
import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.io.NexusTreeUtils;
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;

/**
 * Find and fit the RIXS elastic line image to a straight line so other image
 * <p>
 * Returns line fit parameters and also peak FWHM as auxiliary data
 */
public abstract class RixsBaseOperation<T extends RixsBaseModel>  extends AbstractOperation<T, OperationData> implements PropertyChangeListener {

	protected int[] offset = new int[2];
	protected List<IDataset> displayData = new ArrayList<>();
	protected List<IDataset> auxData = new ArrayList<>();
	protected List<IDataset> summaryData = new ArrayList<>();
	protected OperationLog log = new OperationLog();
	protected int countsPerPhoton;
	public static final int MAX_ROIS = 2;
	protected StraightLine[] lines = new StraightLine[MAX_ROIS];
	protected boolean useBothROIs;
	protected int roiMax;

	@Override
	public void setModel(T model) {
		super.setModel(model);
		model.addPropertyChangeListener(this);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		updateFromModel();
	}

	/**
	 * Updates useBothROIs and roiMax so override for more
	 */
	void updateFromModel() {
		useBothROIs = model.getRoiA() != null && model.getRoiB() != null;
		roiMax = useBothROIs ? 2 : 1;
	};

	@Override
	public String getId() {
		return getClass().getName();
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		displayData.clear();
		auxData.clear();
		log.clear();

		SliceFromSeriesMetadata smd = input.getFirstMetadata(SliceFromSeriesMetadata.class);
		if (smd.getSliceInfo().getSliceNumber() == 1) {
			resetProcess(input);
			updateFromModel();
			parseNexusFile(smd.getFilePath());
		}
		initializeProcess(input);

		IRectangularROI roi;
		IDataset result = input;
		for (int r = 0; r < roiMax; r++) {
			roi = getROI(r);
			Dataset in = preprocessImage(input, roi);
			int cutoff = (int) Math.floor(countsPerPhoton * model.getCutoff());
			if (cutoff > 0) {
				BooleanDataset hots = Comparisons.greaterThan(in, cutoff);
				log.append("Number of cut-off pixels = %d", hots.sum());
				in.setByBoolean(0, hots);
			}

			result = processImageRegion(r, input, in);
		}

		OperationDataForDisplay od = new OperationDataForDisplay();
		od.setShowSeparately(true);
		od.setLog(log);
		od.setData(result);
		if (displayData.size() > 0) {
			od.setDisplayData(displayData.toArray(new IDataset[displayData.size()]));
		}
		od.setAuxData(auxData.toArray(new Serializable[auxData.size()]));
		return od;
	}

	protected IRectangularROI getROI(int r) {
		IRectangularROI roi;
		if (useBothROIs) {
			roi = r == 0 ? model.getRoiA() : model.getRoiB();
		} else {
			roi = model.getRoiA();
			if (roi == null) {
				roi = model.getRoiB();
			}
		}
		return roi;
	}


	/**
	 * Override to reset state of processing object. This is called for the first slice of data only.
	 * @param original
	 */
	abstract void resetProcess(IDataset original);

	/**
	 * Initialize log and fields as necessary
	 * @param original
	 */
	abstract void initializeProcess(IDataset original);

	/**
	 * Process a region in image
	 * @param r region number
	 * @param original image
	 * @param in sub-image within region
	 * @return
	 */
	abstract IDataset processImageRegion(int r, IDataset original, Dataset in);

	protected StraightLine getStraightLine(int r) {
		return lines[r >= lines.length ? 0 : r]; // in case number of ROIs have increased
	}

	/**
	 * @param r
	 * @param in
	 * @return elastic line position and spectrum datasets
	 */
	public Dataset[] makeSpectrum(int r, Dataset in, double maxSlope) {
		// shift and accumulate spectra
		int[] shape = in.getShapeRef();
		int rows = shape[0];
		int cols = shape[1]; // energy
		Dataset y = DatasetFactory.createRange(rows);
		Dataset c = DatasetFactory.createRange(cols);
		y.iadd(offset[0]);
		StraightLine line = getStraightLine(r);

		DoubleDataset elastic = line.calculateValues(y); // absolute position of elastic line to use a zero point
		double elastic0 = elastic.getDouble();

		DoubleDataset spectrum = DatasetFactory.zeros(cols);
		SliceND slice = new SliceND(shape); 
		for (int i = 0; i < rows; i++) {
			slice.setSlice(0, i, i+1, 1);
			spectrum.iadd(Maths.interpolate(Maths.add(c, elastic.getDouble(i) - elastic0), in.getSliceView(slice).squeeze(), c, 0, 0));
		}

		return new Dataset[] {elastic, spectrum};
	}

	/**
	 * Parse NeXus file to set various fields
	 * @param filePath
	 */
	protected void parseNexusFile(String filePath) {
		try {
			Tree t = LoaderFactory.getData(filePath).getTree();

			GroupNode root = t.getGroupNode();
			// entry1:NXentry
			//     before_scan:NXcollection
			//         andorPreampGain:NXcollection/andorPreampGain [1, 2, 4]
			//         pgmEnergy:NXcollection/ [energy in eV, always single value, even for an energy scan]

			GroupNode entry = (GroupNode) NexusTreeUtils.findFirstNode(root, "NXentry").getDestination();

			GroupNode mdg = entry.getGroupNode("before_scan");
			if (mdg == null) {
				throw new NexusException("File does not contain a before_scan collection");
			}

			int gain = (int) parseBeforeScanItem(mdg, "andorPreampGain");
			double speed = parseBeforeScanItem(mdg, "andorADCSpeed");

			
			double energy = parseBeforeScanItem(mdg, "pgmEnergy");
			countsPerPhoton = calculateCountsPerPhoton(gain, speed, energy);
		} catch (Exception e) {
			log.append("Could not parse Nexus file %s:%s", filePath, e);
			countsPerPhoton = 74;
		}

		log.append("Counts per single photon event = %d", countsPerPhoton);
	}

	private static final double PAIR_PRODUCTION_ENERGY = 3.67; // energy required to generate an electron-hole pair

	// values from ANDOR iKon-L CCD system performance booklet (20160824)
	// Head DO936N-00Z-#BN-9UY, serial no CCD-19600
	// CCD from E2V, CCD42-40, 2048x2048 (13.5umx13.5um), serial no 15242-01-06
	private static final double[] ANDOR_AD_RATE = new double[] {0.05, 1, 3}; // rate in MHz
	private static final int[] ANDOR_PREAMP_GAIN = new int[] {1, 2, 4};
	private static final double[][] ANDOR_SENSITIVITY = new double[][] {{3.5, 1.9, 1.0}, {3.4, 1.8, 1.0}, {3.1, 1.8, 1.0}}; // in electrons per AD count

	private int calculateCountsPerPhoton(int gain, double speed, double energy) {
		int gi = Arrays.binarySearch(ANDOR_PREAMP_GAIN, gain);
		if (gi < 0) {
			throw new OperationException(this, "Gain value not allowed");
		}
		int si = Arrays.binarySearch(ANDOR_AD_RATE, speed);
		if (si < 0) {
			throw new OperationException(this, "Gain value not allowed");
		}

		return (int) Math.floor(energy / (ANDOR_SENSITIVITY[si][gi] * PAIR_PRODUCTION_ENERGY));
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
		return NexusTreeUtils.parseDoubleArray(id)[0];
	}

	// TODO work-in-progress on structure of NeXus RIXS application definition
	/**
	 * Parse detector and set fields
	 * @param detector
	 * @param pEnergy photon energy in eV
	 */
	protected void parseNXrixs(GroupNode detector, double pEnergy) {
		String ed = detector.getDataNode("energy_direction").getString();
		model.setEnergyDirection(ed.toLowerCase().equals("slow") ? RixsBaseModel.ENERGY_DIRECTION.SLOW : RixsBaseModel.ENERGY_DIRECTION.FAST);

		// energy required for each photoelectron [e^- / eV]
		double pe = NexusTreeUtils.parseDoubleArray(detector.getDataNode("photoelectrons_energy"))[0];
		// digitization of photoelectron to AD units [ADu / e^-]
		double ds = NexusTreeUtils.parseDoubleArray(detector.getDataNode("detector_sensitivity"))[0];

		countsPerPhoton = (int) Math.floor(pEnergy / (pe * ds));
	}

	protected void parseDesiredNexusFile(String filePath) {
		try {
			Tree t = LoaderFactory.getData(filePath).getTree();

			GroupNode root = t.getGroupNode();
			// TODO find photon energy, (decide where it should be recorded)
			NodeLink beam = NexusTreeUtils.findFirstNode(root, NexusTreeUtils.NX_BEAM);
			DiffractionCrystalEnvironment dce = new DiffractionCrystalEnvironment();
			NexusTreeUtils.parseBeam(beam, dce);

			NodeLink detector = NexusTreeUtils.findFirstNode(root, NexusTreeUtils.NX_DETECTOR);
			parseNXrixs((GroupNode) detector.getDestination(), 1e3 * dce.getEnergy());
		} catch (Exception e) {
			log.append("Could not parse Nexus file %s:%s", filePath, e);
			countsPerPhoton = 74;
		}

		log.append("Counts per single photon event = %d", countsPerPhoton);
	}

	protected void parseDesiredNXrixs(GroupNode detector, double pEnergy) {
		String ed = detector.getDataNode("energy_direction").getString();
		model.setEnergyDirection(ed.toLowerCase().equals("slow") ? RixsBaseModel.ENERGY_DIRECTION.SLOW : RixsBaseModel.ENERGY_DIRECTION.FAST);

		// energy required for each photoelectron [e^- / eV]
		double pe = NexusTreeUtils.parseDoubleArray(detector.getDataNode("photoelectrons_energy"))[0];
		// digitization of photoelectron to AD units [ADu / e^-]
		double ds = NexusTreeUtils.parseDoubleArray(detector.getDataNode("detector_sensitivity"))[0];

		countsPerPhoton = (int) Math.floor(pEnergy / (pe * ds));
	}

	private final Slice createSlice(double start, double length, int max) {
		int lo = Math.max(0, (int) Math.floor(start));
		int hi = Math.min(max, (int) Math.ceil(start + length));
		return lo <= 0 && hi >= max ? null : new Slice(lo, hi);
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

		Slice[] s = getSlice(in.getShapeRef(), axis, r);
		return s[0] == null && s[1] == null ? in : in.getSliceView(s);
	}

	protected Slice[] getSlice(int[] shape, int axis, IRectangularROI r) {
		Slice s0 = createSlice(r.getPointX(), r.getLength(0), shape[axis]);
		Slice s1 = createSlice(r.getPointY(), r.getLength(1), shape[1 - axis]);

		offset[0] = s0 == null ? 0 : s0.getStart();
		offset[1] = s1 == null ? 0 : s1.getStart();
		return new Slice[] {s0, s1};
	}

	protected void generateFitForDisplay(IFunction f, Dataset x, Dataset d, String name, boolean transpose) {
		IDataset fit = f.calculateValues(x);
		fit.setName(name);
		ProcessingUtils.setAxes(fit, x);
		ProcessingUtils.setAxes(d, x);
		displayData.add(d);
		displayData.add(fit);
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

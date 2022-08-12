/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.backgroundsubtraction;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.fitting.functions.IFunction;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationDataForDisplay;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationLog;
import org.eclipse.dawnsci.analysis.api.roi.IRectangularROI;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.dataset.impl.Signal;
import org.eclipse.dawnsci.analysis.dataset.roi.ROISliceUtils;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceInformation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SourceInformation;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Comparisons;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.LazyMaths;
import org.eclipse.january.dataset.LinearAlgebra;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.Operations;
import org.eclipse.january.dataset.ShapeUtils;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.dataset.SliceND;
import org.eclipse.january.dataset.Stats;
import org.eclipse.january.dataset.UnaryOperation;

import uk.ac.diamond.scisoft.analysis.dataset.function.Histogram;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Gaussian;
import uk.ac.diamond.scisoft.analysis.fitting.functions.StraightLine;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer.Optimizer;
import uk.ac.diamond.scisoft.analysis.processing.metadata.FitMetadataImpl;
import uk.ac.diamond.scisoft.analysis.processing.operations.MetadataUtils;
import uk.ac.diamond.scisoft.analysis.processing.operations.backgroundsubtraction.SubtractFittedBackgroundModel.BackgroundPixelPDF;
import uk.ac.diamond.scisoft.analysis.processing.operations.backgroundsubtraction.SubtractFittedBackgroundModel.RegionCount;
import uk.ac.diamond.scisoft.analysis.processing.operations.rixs.ElasticLineReduction;
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.KnownDetector;
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;

/**
 * Subtract a background from the input dataset. The background is determined by fitting to a function
 */
public class SubtractFittedBackgroundOperation extends AbstractImageSubtractionOperation<SubtractFittedBackgroundModel> implements PropertyChangeListener {

	private IFunction pdf;
	private double residual;
	private Dataset x;
	private Dataset h;
	private Dataset fit;
	private double threshold;
	private OperationLog log = new OperationLog();
	private String darkImageFile = null;
	private Dataset[] smoothedDarkData;
	private Dataset[] darkData;
	private SliceND fitSlice;
	private boolean useBothROIs;
	private int roiMax;
	private IRectangularROI[] rois = new IRectangularROI[2];

	protected List<IDataset> displayData = new ArrayList<>();
	protected List<IDataset> auxData = new ArrayList<>();
	private double darkImageCountTime;

	/**
	 * Auxiliary collection. This must match the name field defined in the plugin extension
	 */
	public static final String PROCESS_NAME = "Image background subtraction - Fitted to a PDF";

	/**
	 * Prefix for edge position in auxiliary data
	 */
	public static final String EDGE_POSITION_PREFIX = "edge_position_";

	/**
	 * New field externally linked to detector data in other file
	 */
	private static final String DARK_IMAGE = "dark_image";

	@Override
	public String getFilenameSuffix() {
		return "subtract_fitted_bg";
	}

	@Override
	public String getId() {
		return getClass().getName();
	}

	@Override
	public void setModel(SubtractFittedBackgroundModel model) {
		super.setModel(model);
		model.addPropertyChangeListener(this);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String name = evt.getPropertyName();
		switch (name) {
		case SubtractFittedBackgroundModel.GAUSSIAN_PROPERTY:
			smoothedDarkData = null;
			break;
		case SubtractFittedBackgroundModel.REMOVE_OUTLIER_PROPERTY:
			darkData = null;
			smoothedDarkData = null;
			break;
		case SubtractFittedBackgroundModel.MODE_2D_PROPERTY:
			darkData = null;
			smoothedDarkData = null;
			break;
		case SubtractFittedBackgroundModel.DARK_FILE_PROPERTY:
			darkData = null;
			smoothedDarkData = null;
			break;
		case SubtractFittedBackgroundModel.REGION_PROPERTY:
			darkData = null;
			smoothedDarkData = null;
		case SubtractFittedBackgroundModel.FIT_REGION_PROPERTY:
			fitSlice = null;
			clippedBackground = null;
			break;
		default:
			break;
		}

		if (name.startsWith(SubtractFittedBackgroundModel.REGION_PROPERTY) || name.equals(SubtractFittedBackgroundModel.FIT_REGION_PROPERTY)) {
			updateROICount();
			for (int i  = 0; i < roiMax; i++) {
				IRectangularROI r = getROI(i);
				if (r != null && r.getAngle() != 0) {
					log.appendFailure("Rectangle %d must have angle set to 0", i, r);
				}
				r = getFitROI(i);
				if (r != null && r.getAngle() != 0) {
					log.appendFailure("Region %d must have angle set to 0", i, r);
				}
			}
		}
	}

	public static List<Dataset> createHistogram(Dataset in, boolean positive, int delta) {
		if (delta < 1) {
			throw new IllegalArgumentException("delta must be greater than 0");
		}
		double min = Math.floor(positive ? 1 : in.min(true).doubleValue());
		if (Double.isInfinite(min)) {
			return null; // none are above zero
		}
		double max = Math.ceil(in.max(true).doubleValue());
		int nbin = (int) Math.ceil(max - min);
		if (nbin == 0) {
			return null;
		}

		Dataset bins;
		if (in.hasFloatingPointElements() || nbin >= SubtractFittedBackgroundModel.HISTOGRAM_MAX_BINS) {
			bins = DatasetFactory.createLinearSpace(DoubleDataset.class, min, max, Math.min(nbin + 1, SubtractFittedBackgroundModel.HISTOGRAM_MAX_BINS));
		} else {
			int imin = (int) min;
			int imax = (int) max;
			if (delta > 1) { // align bins with zero
				imin = ((imin-delta+1)/delta) * delta;
				imax = ((imax+delta-1)/delta) * delta;
			}
			imax = Math.max(imin + 1, imax); // numbers of bin >= 2
			bins = DatasetFactory.createRange(IntegerDataset.class, imin, imax+1, delta);
		}

		Histogram histo = new Histogram(bins);
		return histo.value(in);
	}

	private double fitImageHistogram(Dataset in, boolean positive) {
		List<Dataset> hs = createHistogram(in, positive, 1);
		if (hs == null) {
			return Double.NaN;
		}
		h = hs.get(0);
		h.setName("Histogram counts");
		x = hs.get(1).getSliceView(new Slice(-1));
		x.setName("Intensity values");
		pdf = prepareBackgroundPDF(x, h, model.getBackgroundPDF());

		// extract indexes
		IParameter p = pdf.getParameter(0);
		int b = (int) p.getLowerLimit();
		int e = (int) p.getUpperLimit();
		int c = (int) p.getValue();
		p.setLimits(x.getDouble(c - 1) - 0.5, x.getDouble(c + 1)); // set narrow range for fitting pdf position
		double cx = x.getDouble(c);
		p.setValue(cx);
		SliceND slice = new SliceND(h.getShapeRef(), new Slice(b, e + 1, 1));

		residual = fitFunction("Exception in PDF fit", pdf, x.getSliceView(slice), h.getSliceView(slice));
		log.appendSuccess("\nFitted PDF in %s: residual = %g\n%s", slice, residual, pdf);

		fit = DatasetUtils.convertToDataset(pdf.calculateValues(x));
		fit.setName("Background fit");

		return x.getDouble((int) p.getValue());
	}

	protected double calculateThreshold(Dataset in, boolean positive) throws OperationException {
		double cx = fitImageHistogram(in, positive);
		if (Double.isNaN(cx)) {
			return Double.NaN;
		}

		// find where given ratio occurs and is past peak position
		List<Double> cs = DatasetUtils.crossings(x, Maths.dividez(h, fit), model.getRatio());
		double thr = Double.NaN;

		for (Double d : cs) {
			if (d > cx) {
				thr = d;
				break;
			}
		}

		if (Double.isNaN(thr)) {
			throw new OperationException(this, "Failed to find a threshold");
		}
		return thr;
	}

	/**
	 * Updates useBothROIs and roiMax so override for more
	 */
	protected void updateROICount() {
		useBothROIs = model.getRegionCount() == RegionCount.Two;
		roiMax = useBothROIs ? 2 : 1;
		rois[0] = null;
		rois[1] = null;
	}

	protected IRectangularROI getROI(int r) {
		IRectangularROI roi = rois[r];
		if (roi == null) {
			if (useBothROIs) {
				roi = r == 0 ? model.getRoiA() : model.getRoiB();
			} else {
				roi = model.getRoiA();
			}
		}
		return roi;
	}

	protected RectangularROI getFitROI(int r) {
		IRectangularROI roi;
		if (useBothROIs) {
			if (r == 0) {
				roi = model.getFitRegionA();
			} else {
				roi = model.getFitRegionB();
				if (roi == null) {
					roi = model.getFitRegionA();
				}
			}
		} else {
			roi = model.getFitRegionA();
		}
		if (roi != null) {
			return roi instanceof RectangularROI ? (RectangularROI) roi : new RectangularROI(roi);
		}
		return null;
	}

	private RectangularROI getIntersection(IRectangularROI a, IRectangularROI b) {
		if (a.getAngle() != 0 || b.getAngle() != 0) { // XXX perhaps validate at model level
			return null;
		}
		double[] sa = a.getPointRef();
		double[] ea = a.getEndPoint();
		double[] sb = b.getPointRef();
		double[] eb = b.getEndPoint();
		double[] sc = new double[2];
		double[] ec = new double[2];

		if (!calcOverlap(0, sa, ea, sb, eb, sc, ec)) {
			return null;
		}

		if (!calcOverlap(1, sa, ea, sb, eb, sc, ec)) {
			return null;
		}

		return new RectangularROI(sc, ec);
	}

	private boolean calcOverlap(int i, double[] sa, double[] ea, double[] sb, double[] eb, double[] sc, double[] ec) {
		double s0 = sa[i];
		double s1 = sb[i];
		double e0 = ea[i];
		double e1 = eb[i];
		if (s0 < s1) {
			if (e0 < s1) {
				return false;
			}
			sc[i] = s1;
		} else {
			if (s0 > e1) {
				return false;
			}
			sc[i] = s0;
		}
		ec[i] = Math.min(e0, e1);
		return true;
	}

	@Override
	protected Dataset getImage(IDataset input) throws OperationException {
		// this calculates a background to subtract from input
		Dataset in = DatasetUtils.convertToDataset(input);

		if (smoothedDarkData != null) {
			threshold = 0;
			h = null;
			// fit dark to current with offset
			Dataset subtractImage = null;

			Double darkOffset = model.getDarkOffset();
			for (int r = 0; r < roiMax; r++) {
				IRectangularROI roi = getROI(r);
				if (roi == null) {
					roi = KnownDetector.getDefaultROI(detector, in.getShapeRef(), roiMax, r, MARGIN);
					addToConfigured(r, roi);
					rois[r] = roi;
				}

				SliceND s = createSliceND(in.getShapeRef(), roi);
				Dataset crop = in.getSliceView(s);

				/* TODO
				 * Need to set rest of image to input so net image is zero there
				 * or propagate regions to elastic line or RIXS image reduction
				 * (can check in operation metadata)
				 */
				double[] scaleOffset;
				if (model.isMode2D()) {
					if (notValid(darkOffset)) {
						scaleOffset = findDarkDataScaleAndOffset(crop, smoothedDarkData[r], r, roi);
					} else {
						scaleOffset = new double[] {model.getDarkScaling(), darkOffset};
					}
					auxData.add(ProcessingUtils.createNamedDataset(scaleOffset[1], "dark_offset_" + r));
					auxData.add(ProcessingUtils.createNamedDataset(scaleOffset[0], "dark_scale_" + r));

					Dataset subtractCrop = scaleOffset[0] == 1 ? Maths.add(smoothedDarkData[r], scaleOffset[1]) : Maths.multiply(smoothedDarkData[r], scaleOffset[0]).iadd(scaleOffset[1]);
					if (subtractImage == null) {
						subtractImage = in.clone();
					}
					subtractImage.setSlice(subtractCrop, s);

					Dataset darkFit = subtractCrop.mean(1, true);
					auxData.add(ProcessingUtils.createNamedDataset(darkFit, "profile_fit_dark_" + r));
					Dataset profile = crop.mean(1, true);
					cleanUpProfile(profile);
					profile.setName("profile_" + r);
					displayData.add(profile);
					auxData.add(profile);
					Dataset diff = Maths.subtract(profile, darkFit);
					auxData.add(ProcessingUtils.createNamedDataset(diff, "profile_diff_" + r));
					displayData.add(darkFit);
				} else {
					Dataset profile = crop.mean(1, true);
					cleanUpProfile(profile);
					profile.setName("profile");
					auxData.add(profile);

					if (notValid(darkOffset)) {
						scaleOffset = findDarkDataScaleAndOffset(profile.reshape(-1), smoothedDarkData[r].reshape(-1), r, roi);
					} else {
						scaleOffset = new double[] {model.getDarkScaling(), darkOffset};
					}
					auxData.add(ProcessingUtils.createNamedDataset(scaleOffset[1], "dark_offset"));
					auxData.add(ProcessingUtils.createNamedDataset(scaleOffset[0], "dark_scale"));

					Dataset darkFit = scaleOffset[0] == 1 ? Maths.add(smoothedDarkData[r], scaleOffset[1]) : Maths.multiply(smoothedDarkData[r], scaleOffset[0]).iadd(scaleOffset[1]);
					auxData.add(ProcessingUtils.createNamedDataset(darkFit, "profile_fit_dark"));
					Dataset diff = Maths.subtract(profile, darkFit);
					auxData.add(ProcessingUtils.createNamedDataset(diff, "profile_diff"));
					Dataset subtractCrop = darkFit.reshape(darkFit.getSize(), 1);

					if (subtractImage == null) {
						subtractImage = in.clone();
					}
					subtractImage.setSlice(subtractCrop, s);

					displayData.add(darkData[r]);
					if (smoothedDarkData != darkData) {
						displayData.add(smoothedDarkData[r]);
					}
					displayData.add(darkFit);
					displayData.add(profile);
				}
			}

			return subtractImage;
		}

		double thr = calculateThreshold(in, smoothedDarkData == null ? model.isPositiveOnly() : false);
		threshold = thr;
		if (Double.isNaN(thr)) {
			double min = in.min(true).doubleValue();
			log.appendFailure("No range in data. All finite values are %g", min);
			return DatasetFactory.createFromObject(min);
		}

		if (!in.hasFloatingPointElements()) {
			int ithr = (int) Math.floor(thr);
			log.appendSuccess("Threshold = %d", ithr);
			return DatasetUtils.select(Comparisons.lessThan(in, ithr), in, ithr);
		}

		log.appendSuccess("Threshold = %g", threshold);
		return DatasetUtils.select(Comparisons.lessThan(in, thr), in, thr);
	}

	public static final Slice createSlice(double start, double length, int max) {
		int lo = Math.max(0, (int) Math.floor(start));
		int hi = Math.min(max, (int) Math.ceil(start + length));
		return lo <= 0 && hi >= max ? null : new Slice(lo, hi);
	}

	public static SliceND createSliceND(int[] shape, IRectangularROI roi) {
		SliceND s = null;
		if (roi == null) {
			s = new SliceND(shape);
		} else {
			s = ROISliceUtils.createSliceND(roi, shape);
		}
		return s;
	}

	private void cleanUpProfile(Dataset profile) {
		if (profile.getSize() == 0) {
			return;
		}
		if (profile.getDouble() == 0) {
			profile.set(0.5*(profile.getDouble(1) + profile.getDouble(2)), 0); // ensure 1st entry is non-zero
		}
	}

	private static final int CLIP_END = -50; // clip end off
	private Dataset clippedBackground = null;
	private KnownDetector detector;
	private static final String CLIPPED_BACKGROUND = "bg_dark_smoothed";

	private static final int MARGIN = 10; // margin by which to reduce regions

	private double[] findDarkDataScaleAndOffset(Dataset in, Dataset smooth, int r, IRectangularROI roi) {
		in = in.getView(false);
		in.clearMetadata(null);

		/* TODO
		 * if two regions, does not single fit region intersect both
		 * checks: intersections
		 */
		RectangularROI froi = getFitROI(r);
		if (froi != null) {
			froi = getIntersection(roi, froi);
			if (froi == null) {
				log.appendFailure("Failed to find intersect between match region and rectangle {}", r);
			} else {
				froi.setPoint(froi.getValue(0) - roi.getValue(0), froi.getValue(1) - roi.getValue(1)); // set relative to cropped input
				log.appendFailure("Intersect between match region and rectangle {} is {}", r, froi);
			}
		}

		boolean findDrop = froi == null && !notValid(model.getGaussianSmoothingLength());

		if (findDrop) { // find drop zone to shadow region on the right by looking for trough in derivative
			if (fitSlice == null) { // TODO need fitSlice for 2nd fit ROI...
				Dataset y;
				if (smooth.getRank() == 2) {
					y = smooth.mean(1, true);
					y.squeezeEnds();
					cleanUpProfile(y);
				} else {
					y = smooth;
				}

				Dataset d = Maths.derivative(DatasetFactory.createRange(y.getSize()), y, 1);
				int m = d.argMin(true);
				log.append("Found edge drop centre at %d", m);
				auxData.add(ProcessingUtils.createNamedDataset(m, EDGE_POSITION_PREFIX + r));

				// work out full width at half-minimum to use for slicing fit region
				double dmin = d.getDouble(m);
				List<Double> z = DatasetUtils.crossings(d, dmin/2);
				int pmax = z.size();
				int b = 0;
				int e = 0;
				if (pmax == 0) {
					log.appendFailure("No crossing of derivative of dark image profile found: no edge or shadow?");
					return new double[] {1, 0};
				} else {
					int p = 0;
					while (z.get(p) < m && ++p < pmax) { // find 1st crossing left of trough
					}
					double w = 1;
					if (p == 0) { // only crossings to right of trough
						w = 2*(z.get(p) - m);
					} else if (p < pmax) {
						w = z.get(p) - z.get(p - 1);
					} else { // only crossings to left of trough
						w = 2*(m - z.get(p - 1));
					}
					log.append("Full width at half minimum is %g", w);
					// make slice across drop zone so many widths
					w = Math.ceil(model.getDarkFWHMScaling() * w);
					b = Math.max(m - (int) w, 0);
					e = Math.min(m + (int) w, y.getSize() + CLIP_END);
				}

				int[] shape = in.getShapeRef();
				froi = new RectangularROI(shape.length < 2 ? roi.getLength(0) : shape[1], e - b, 0);
				froi.setValue(1, b);

				fitSlice = createSliceND(shape, froi);

				log.append("Using clipped region in %s to fit background", fitSlice);
				clippedBackground = smooth.getSliceView(fitSlice);
				clippedBackground.setName(CLIPPED_BACKGROUND);

				if (clippedBackground.getSize() == 0) { // no trough
					log.appendFailure("No drop zone to shadow region found");
					fitSlice = null;
					return new double[] {1, 0};
				}

				addConfiguredField(r == 0 ? "fitRegionA" : "fitRegionB", froi);
			} else if (clippedBackground == null) {
				clippedBackground = smooth.getSliceView(fitSlice);
				clippedBackground.setName(CLIPPED_BACKGROUND);
			}
		} else {
			if (fitSlice == null) {
				int[] shape = in.getShapeRef();
				if (froi == null) {
					fitSlice = new SliceND(shape, new Slice(0, CLIP_END));
				} else {
					if (shape.length == 1) {
						Slice s0 = ROISliceUtils.createSlice(froi.getValue(1), froi.getLength(1), shape[0]); // set start to zero as input has been cropped
						fitSlice = new SliceND(shape, s0);
					} else {
						fitSlice = createSliceND(shape, froi);
					}
				}
			}
			if (clippedBackground == null) {
				clippedBackground = smooth.getSliceView(fitSlice);
				clippedBackground.setName(CLIPPED_BACKGROUND);
			}
		}

		in = in.getSlice(fitSlice);
		if (in.getRank() == 2) { // remove pixels cosmic ray events before fit
			removeBlips2D(in);
		}

		double offset = ((Number) in.mean()).doubleValue() - ((Number) clippedBackground.mean()).doubleValue();
		IFunction f = new StraightLine();
		IParameter scale = f.getParameter(0);
		scale.setValue(1);
		scale.setLimits(0.5, 2.0); // limits to prevent flipping
		f.setParameterValues(1, offset);
		if (in.getRank() == 1) { // don't save 2D images as it duplicates input
			auxData.add(ProcessingUtils.createNamedDataset(in, "bg_input_cleaned"));
		}
		double residuals = fitFunction("Exception in dark data fit", f, clippedBackground, in);
		residuals /= in.getSize();
		auxData.add(ProcessingUtils.createNamedDataset(residuals, "dark_fit_mse_" + r));

		System.err.println("Fit: " + f + " cf " + offset);
		log.append("Dark image fit: %s", f);
		return f.getParameterValues();
	}

	class GaussianOperation implements UnaryOperation {

		private double length;

		public GaussianOperation(double length) {
			this.length = length;
		}

		@Override
		public boolean booleanOperate(long a) {
			return false;
		}

		@Override
		public long longOperate(long a) {
			return (long) doubleOperate(a);
		}

		@Override
		public double doubleOperate(double a) {
			double x = a/length;
			return Math.exp(-x*x);
		}

		@Override
		public void complexOperate(double[] out, double ra, double ia) {
		}

		@Override
		public String toString(String a) {
			return "Gaussian";
		}
	}

	private Dataset createFilter(double l) {
		int n = Math.max((int) Math.ceil(3*l), 2);
		int m = 2*n + 1;
		Dataset x = DatasetFactory.createRange(m);
		x.isubtract(n);
		return Operations.operate(new GaussianOperation(l), x, null);
	}

	private Dataset smoothFilter1D(Dataset darkProfile, Dataset g, int r) {
		int n = g.getSize() / 2;
		g.imultiply(1./((Number) g.sum(true)).doubleValue());
		Dataset smoothed = Signal.convolveToSameShape(darkProfile.cast(DoubleDataset.class), g, null);
		smoothed.setSlice(smoothed.getDouble(n), new Slice(n));
		smoothed.setSlice(smoothed.getDouble(-n), new Slice(-n-1, null));
		smoothed.setName("dark_smoothed_profile_" + r);
		return smoothed;
	}

	private Dataset smoothFilter2D(Dataset darkImage, Dataset g, int r) {
		int n = g.getSize() / 2;
		g = LinearAlgebra.outerProduct(g, g);
		g.imultiply(1./((Number) g.sum(true)).doubleValue());
		Dataset smoothed = Signal.convolveToSameShape(darkImage.cast(DoubleDataset.class), g, null);
		smoothed.setSlice(smoothed.getSliceView(new Slice(n, n+1)), new Slice(n));
		smoothed.setSlice(smoothed.getSliceView(new Slice(-n, -n+1)), new Slice(-n+1, null));
		smoothed.setSlice(smoothed.getSliceView(null, new Slice(n, n+1)), null, new Slice(n));
		smoothed.setSlice(smoothed.getSliceView(null, new Slice(-n, -n+1)), null, new Slice(-n+1, null));
		smoothed.setName("dark_smoothed_image_" + r);
		return smoothed;
	}

	private void createDarkData(GroupNode nxDetector, String name) throws OperationException {
		String file = null;
		ILazyDataset dark = null;
		if (nxDetector.containsDataNode(DARK_IMAGE)) {
			dark = nxDetector.getDataNode(DARK_IMAGE).getDataset();
			file = ProcessingUtils.getOriginatingFile(dark);
			if (file != null) {
				log.append("Found %s which links to %s", DARK_IMAGE, file);
				addConfiguredField("darkImageFile", file);
			}
		}
		if (file == null) {
			file = model.getDarkImageFile();
		}

		if (file == null) {
			smoothedDarkData = null;
			darkImageFile = null;
			return;
		}
		if (file.equals(darkImageFile) && smoothedDarkData != null) {
			return;
		}

		if (!file.equals(darkImageFile) || darkData == null) {
			darkImageFile = file;
			if (dark == null) {
				try {
					dark = ProcessingUtils.getLazyDataset(this, file, name);
				} catch (Exception e) {
					throw new OperationException(this, "Could not load dark image file", e);
				}
			}
			GroupNode diDetector = ProcessingUtils.getNXdetector(this, darkImageFile);
			darkImageCountTime = getCountTime(diDetector);

			dark = dark.getSliceView().squeezeEnds();
			Dataset d;
			if (dark.getRank() == 2) {
				try {
					d = DatasetUtils.sliceAndConvertLazyDataset(dark);
				} catch (DatasetException e) {
					throw new OperationException(this, "Could not read in dark image", e);
				}
			} else if (dark.getRank() > 2) {
				try {
					d = LazyMaths.mean(dark, -2, -1);
					int[] extra = ShapeUtils.getRemainingAxes(dark.getRank(), -2, -1);
					log.append("Taken average over %s-axes of dark image data", Arrays.toString(extra));
				} catch (DatasetException e) {
					throw new OperationException(this, "Could not take average of dark image", e);
				}
			} else {
				throw new OperationException(this, "Dark image should be at least 2D");
			}

			if (detector != null && !Arrays.equals(detector.getShape(), d.getShapeRef())) {
				throw new OperationException(this, "Dark image shape does not match input image shape");
			}

			// split into cropped regions
			darkData = new Dataset[roiMax];
			for (int r = 0; r < roiMax; r++) {
				IRectangularROI roi = getROI(r);
				if (roi == null) {
					roi = KnownDetector.getDefaultROI(detector, d.getShapeRef(), roiMax, r, MARGIN);
					addToConfigured(r, roi);
					rois[r] = roi;
				}
				SliceND s = createSliceND(d.getShapeRef(), roi);
				Dataset crop = d.getSliceView(s);

				if (model.isMode2D()) {
					darkData[r] = crop;
					if (model.isRemoveOutliers()) { // remove pixels cosmic ray events
						removeBlips2D(crop);
					}
				} else {
					// average all so that only profile along row is left
					if (model.isRemoveOutliers()) {
						darkData[r] = createProfileWithoutOutliers(crop);
					} else {
						darkData[r] = crop.mean(1, true);
					}
					if (crop.getDouble() == 0) {
						crop.set(0.5*(crop.getDouble(1) + crop.getDouble(2)), 0); // ensure 1st entry is non-zero
					}
					crop.setName("dark_profile_" + r);
			
					removeBlips1D(crop);
				}
			}
		}

		Double smoothingLength = model.getGaussianSmoothingLength();
		if (notValid(smoothingLength)) {
			smoothedDarkData = darkData;
		} else {
			smoothedDarkData = new Dataset[roiMax];
			for (int r = 0; r < roiMax; r++) {
				Dataset g = createFilter(Math.abs(smoothingLength));
				if (model.isMode2D()) {
					smoothedDarkData[r] = smoothFilter2D(darkData[r], g, r);
				} else {
					smoothedDarkData[r] = smoothFilter1D(darkData[r], g, r);
				}
			}
		}
		clippedBackground = null;
		fitSlice = null;
	}

	private void addToConfigured(int r, IRectangularROI roi) {
		addConfiguredField(r == 0 ? "roiA" : "roiB", roi);
	}

	private final static boolean notValid(Double v) {
		return v == null || !Double.isFinite(v);
	}

	private void removeBlips2D(Dataset data) {
		fitImageHistogram(data, true);

		IndexIterator it = data.getIterator(true);
		double thr = pdf.getParameterValue(0) + 2 * pdf.getParameterValue(1);
		log.append("Blip removal using threshold = %g", thr);
		int blips = 0;
		while (it.hasNext()) {
			int i = it.index;
			if (data.getElementDoubleAbs(i) >= thr) {
				blips++;
				while (it.hasNext() && data.getElementDoubleAbs(it.index) >= thr) {
				}
				int imax = it.index;
				double d = 0.5*(data.getElementDoubleAbs(Math.max(0, i - 1)) + data.getElementDoubleAbs(imax));
				while (i < imax) {
					data.setObjectAbs(i++, d);
				}
			}
		}
		log.append("Blips removed: %d", blips);
	}

	private void removeBlips1D(Dataset data) {
		// does so by checking for differences that greater than FWHM (assumes symmetric central distribution)
		// in zeroth dimension
		Dataset diffs = Maths.difference(data, 1, 0);

		// get FWHM
		IFunction dpdf = fitDiffHistogram(diffs, false);
		double f = dpdf.getParameterValue(1);
		System.err.println("Differences has FWHM of " + f);
		IndexIterator it = diffs.getIterator();
		it.hasNext();
		double yp = diffs.getElementDoubleAbs(it.index);
		int i = 2;
		int blips = 0;
		while (it.hasNext()) {
			double yc = diffs.getElementDoubleAbs(it.index);
			if (Math.abs(yp) > f && Math.abs(yc) > f && Math.signum(yp) != Math.signum(yc)) {
				blips++;
				double py = data.getElementDoubleAbs(i-1);
				double ny = data.getElementDoubleAbs(i+1);
				data.setObjectAbs(i, 0.5*(py + ny));
				yc = 0.5*(ny - py);
			}
			i++;
			yp = yc;
		}
		log.append("Blips removed: %d", blips);
	}

	private static final double Q_UPPER = 0.99;
	private static final double Q_FACTOR = 20;
	private Dataset createProfileWithoutOutliers(Dataset d) {
		// row-wise reject upper outliers using threshold
		// calculated from the 99% percentile and scaling its distance from the median 
		// by a large factor
		int[] shape = d.getShapeRef();
		SliceND s = new SliceND(shape);
		int imax = shape[0];
		DoubleDataset p = DatasetFactory.zeros(imax);
		for (int i = 0; i < imax; i++) {
			s.setSlice(0, i, i+1, 1);
			Dataset r = d.getSliceView(s);
			double q = Stats.quantile(r, Q_UPPER);
			double c = (Double) Stats.median(r);
			q -= c;
			double u = Math.ceil((Double) c + Q_FACTOR * q);
			double max = r.max(true).doubleValue();
			double v = 0;
			if (u >= max) {
				v = ((Number) r.mean(true)).doubleValue();
			} else {
//				System.err.printf("Upper threshold of row %d is %d (cf %d) [%g, %g]\n", i, u, max, c, q);
				IndexIterator it = r.getIterator();
				int j = 0;
				while (it.hasNext()) {
					double x = r.getElementDoubleAbs(it.index);
					if (x < u) {
						j++;
						v += x;
					}
				}
				if (j > 0) { // new mean
					v /= j;
				}
			}
			p.setAbs(i, v);
		}
		return p;
	}

	private IFunction fitDiffHistogram(Dataset d, boolean plot) {
		List<Dataset> hs = createHistogram(d, false, 200);
		if (hs == null) {
			return null;
		}
		Dataset hd = hs.get(0);
		hd.setName("diff histo");
		Dataset dx = hs.get(1).getSliceView(new Slice(-1));
		IFunction dpdf = prepareBackgroundPDF(dx, hd, model.getBackgroundPDF());
		IParameter p = dpdf.getParameter(0);
		int b = (int) p.getLowerLimit();
		int e = (int) p.getUpperLimit();
		int c = (int) p.getValue();
		p.setLimits(dx.getDouble(Math.max(0, c - 5)) - 0.5, dx.getDouble(Math.min(dx.getSize()-1, c + 5))); // set narrow range for fitting pdf position
		p.setValue(dx.getDouble(c)); 
		SliceND slice = new SliceND(hd.getShapeRef(), new Slice(b, e + 1, 1));
		System.err.println("Initial histo peak: " + dpdf);

		double res = fitFunction("Exception in bg histogram fit", dpdf, dx.getSliceView(slice), hd.getSliceView(slice));
		log.appendSuccess("\nFitted PDF in %s: residual = %g\n%s", slice, res, dpdf);
		System.err.println("Fitted histo peak: " + dpdf + " in " + slice);
		if (plot) {
			IDataset t = dpdf.calculateValues(dx);
			MetadataUtils.setAxes(this, t, dx);
			displayData.add(t);
		}
		return dpdf;
	}


	protected double fitFunction(String excMessage, IFunction f, Dataset x, Dataset v) {
		return ElasticLineReduction.fitFunction(this, new ApacheOptimizer(Optimizer.LEVENBERG_MARQUARDT), excMessage, log, f, x, v, null);
	}

	@Override
	public OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		auxData.clear();
		displayData.clear();
		log.append("Subtract Fitted Background");
		log.append("==========================");

		SliceFromSeriesMetadata ssm = getSliceSeriesMetadata(input);
		SliceInformation si = ssm.getSliceInfo();
		if (si.isFirstSlice()) {
			log.clear();
			updateROICount();
			SourceInformation src = ssm.getSourceInfo();

			String name = src.getDatasetName();
			detector = KnownDetector.getDetector(src.getFilePath(), name, input);
			GroupNode nxDetector = ProcessingUtils.getNXdetector(this, src.getFilePath());

			createDarkData(nxDetector, name);

			if (smoothedDarkData != null) {
				String filePath = src.getFilePath();
				double countTime = getCountTime(nxDetector);
				if (countTime != darkImageCountTime) {
					throw new OperationException(this, "Count time in " + filePath + ": " + countTime + " != " + darkImageCountTime + " for dark image");
				}
			}
		}

		OperationData op = super.process(input, monitor);

		// add fit parameters metadata to dataset to pass down chain
		OperationDataForDisplay od = new OperationDataForDisplay(op.getData());
		od.setShowSeparately(true);
		od.setLog(log);
		od.setDisplayData(displayData.toArray(new IDataset[displayData.size()]));

		if (h != null) {
			if (model.getDarkImageFile() == null) {
				setDisplayData(od, x, h, fit);
			}

			// switch to use display data and record fit parameters as [1]-shape datasets
			// need two plots...
			auxData.add(ProcessingUtils.createNamedDataset(threshold, "threshold"));
			auxData.add(ProcessingUtils.createNamedDataset(pdf.getParameterValue(0), "pdf_posn"));
			auxData.add(ProcessingUtils.createNamedDataset(pdf.getParameterValue(1), "pdf_fwhm"));
			auxData.add(ProcessingUtils.createNamedDataset(pdf.getParameterValue(2), "pdf_area"));
			auxData.add(ProcessingUtils.createNamedDataset(residual, "pdf_residual"));
			MetadataUtils.setAxes(this, h, x);
			auxData.add(ProcessingUtils.createNamedDataset(h, "histogram"));
			MetadataUtils.setAxes(this, fit, x);
			auxData.add(ProcessingUtils.createNamedDataset(fit, "histogram_fit"));

			od.getData().addMetadata(new FitMetadataImpl(SubtractFittedBackgroundOperation.class).setFitFunction(pdf));
			// TODO add edge positions too?
		}

		if (si.isFirstSlice()) {
			setConfiguredFields(od);
		}

		od.setAuxData(auxData.toArray(new Serializable[auxData.size()]));

		if (si.isLastSlice()) {
			if (smoothedDarkData != null && smoothedDarkData != darkData) {
				if (model.isMode2D()) {
					od.setSummaryData(smoothedDarkData, clippedBackground);
				} else {
					od.setSummaryData(darkData, smoothedDarkData, clippedBackground);
				}
			}
		}

		return od;
	}

	// make display datasets here to be recorded in file
	private void setDisplayData(OperationDataForDisplay od, Dataset x, Dataset h, Dataset fit) {
		Dataset a = Maths.log10(h);
		a.setName("Log10 of " + h.getName());
		Dataset b = Maths.log10(fit);
		b.setName("Log10 of " + fit.getName());
		// clamp values to above zero for nicer display 
		b.setByBoolean(0, Comparisons.lessThan(b, 0));
		MetadataUtils.setAxes(this, a, x);
		MetadataUtils.setAxes(this, b, x);
		od.setDisplayData(a, b);
	}

	private IFunction prepareBackgroundPDF(Dataset x, Dataset h, BackgroundPixelPDF pdfType) {
		IFunction pdf = null;
		switch (pdfType) {
		default:
		case Gaussian:
			log.append("Using Gaussian PDF");
			pdf = new Gaussian();
		}

		initializeFunctionParameters(x, h, pdf);
		log.append("Initial PDF:\n%s", pdf);
		return pdf;
	}

	private static void initializeFunctionParameters(Dataset x, Dataset h, IFunction pdf) {
		IParameter p = pdf.getParameter(0);
		double xb = x.getDouble(0);
		double dx = x.getDouble(1) - xb;
		int pMax = h.maxPos(true)[0]; // position of maximum

		int pBeg = pMax - 1; // only fit from just before peak as background peak can be unresolved
		int pEnd = pMax;
		double hm = h.max(true).doubleValue();
		double hr = 1e-3 * hm; // set right threshold to determine fit interval
		for (int max = h.getSize() - 1; pEnd < max && h.getDouble(++pEnd) > hr;);
		p.setValue(pMax); // set these to indexes to allow slicing
		p.setLimits(pBeg, pEnd);

		p = pdf.getParameter(1);
		// estimate FWHM from crossings at HM and finding the crossing that is less than max x
		double xr = findFWHMPostMax(x, h, 0);
		p.setLimits(dx, 2*xr);
		p.setValue(xr);

		p = pdf.getParameter(2);
		// estimate area
		double t = dx * ((Number) h.sum(true)).doubleValue();
		p.setValue(t);
		p.setLimits(dx * hm, 2*xr * hm);
	}

	/**
	 * Find FWHM from values of y after maximum
	 * @param x
	 * @param y
	 * @return FWHM or NaN if not found
	 */
	public static double findFWHMPostMax(Dataset x, Dataset y, double base) {
		double ym = y.max(true).doubleValue();
		int pos = y.maxPos(true)[0];
		SliceND slice = new SliceND(x.getShapeRef(), new Slice(pos, null));
		List<Double> cs = DatasetUtils.crossings(x.getSliceView(slice), y.getSliceView(slice), (ym + base)*0.5);
		return cs.size() > 0 ? (cs.get(0) - x.getDouble(pos)) * 2 : Double.NaN;
	}

	protected Dataset getDetectorField(GroupNode nxDetector, String field) {
		try {
			return DatasetUtils.sliceAndConvertLazyDataset(nxDetector.getDataNode(field).getDataset());
		} catch (DatasetException e) {
			throw new OperationException(this, "Could not read value for " + field, e);
		}
	}

	private double getCountTime(GroupNode nxDetector) {
		return getDetectorField(nxDetector, "count_time").getDouble();
	}
}

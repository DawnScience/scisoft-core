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
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.eclipse.dawnsci.analysis.dataset.impl.Signal;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceInformation;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.BroadcastSelfIterator;
import org.eclipse.january.dataset.BroadcastSingleIterator;
import org.eclipse.january.dataset.Comparisons;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.LinearAlgebra;
import org.eclipse.january.dataset.LongDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.Operations;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.dataset.SliceND;
import org.eclipse.january.dataset.Stats;
import org.eclipse.january.dataset.UnaryOperation;

import uk.ac.diamond.scisoft.analysis.dataset.function.Histogram;
import uk.ac.diamond.scisoft.analysis.fitting.functions.AFunction;
import uk.ac.diamond.scisoft.analysis.fitting.functions.CoordinatesIterator;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Gaussian;
import uk.ac.diamond.scisoft.analysis.io.NexusTreeUtils;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer.Optimizer;
import uk.ac.diamond.scisoft.analysis.processing.LocalServiceManager;
import uk.ac.diamond.scisoft.analysis.processing.metadata.FitMetadataImpl;
import uk.ac.diamond.scisoft.analysis.processing.operations.MetadataUtils;
import uk.ac.diamond.scisoft.analysis.processing.operations.backgroundsubtraction.SubtractFittedBackgroundModel.BackgroundPixelPDF;
import uk.ac.diamond.scisoft.analysis.processing.operations.rixs.ElasticLineReduction;
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
	private Dataset smoothedDarkData;
	private Dataset darkData;

	protected List<IDataset> displayData = new ArrayList<>();
	protected List<IDataset> auxData = new ArrayList<>();
	private double darkImageCountTime;

	/**
	 * Auxiliary subentry. This must match the name field defined in the plugin extension
	 */
	public static final String PROCESS_NAME = "Image background subtraction - Fitted to a PDF";

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
		default:
			break;
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

		return cx;
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

	@Override
	protected Dataset getImage(IDataset input) throws OperationException {
		// this calculates a background to subtract from input
		Dataset in = DatasetUtils.convertToDataset(input);
//		IRectangularROI r = model.getRoi(); // cannot do this as superclass expects result to have input's shape 
//		Slice[] s = null;
//		if (r != null) {
//			s = getSlice(in.getShapeRef(), r);
//			if (s[0] != null || s[1] != null) {
//				in = in.getSliceView(s);
//			}
//		}

		if (smoothedDarkData != null) {
			h = null;
			// fit dark to current with offset
			Dataset subtractImage = null;

			if (model.isMode2D()) {
				double offset = findDarkDataOffset(in, smoothedDarkData);
				auxData.add(ProcessingUtils.createNamedDataset(offset, "dark_offset"));
				subtractImage = Maths.add(smoothedDarkData, offset);

				Dataset darkFit = subtractImage.sum(1, true).getSliceView(new Slice(1, -1));
				auxData.add(ProcessingUtils.createNamedDataset(darkFit, "profile_fit_dark"));
				Dataset profile = in.sum(1, true).getSliceView(new Slice(1, -1));;
				cleanUpProfile(profile);
				profile.setName("profile");
				displayData.add(profile);

				Dataset diff = Maths.subtract(profile, darkFit);
				auxData.add(ProcessingUtils.createNamedDataset(diff, "profile_diff"));
				displayData.add(darkFit);
			} else {
				Dataset profile = in.hasFloatingPointElements() ? in.sum(1, true) :
					in.cast(LongDataset.class).sum(1, true); // avoid integer overflows
				cleanUpProfile(profile);
				profile.setName("profile");
				auxData.add(profile);

				double offset = findDarkDataOffset(profile.reshape(-1), smoothedDarkData.reshape(-1));
				auxData.add(ProcessingUtils.createNamedDataset(offset, "dark_offset"));
				Dataset darkFit = Maths.add(smoothedDarkData, offset);

				auxData.add(ProcessingUtils.createNamedDataset(darkFit, "profile_fit_dark"));
				Dataset diff = Maths.subtract(profile, darkFit);
				auxData.add(ProcessingUtils.createNamedDataset(diff, "profile_diff"));
				subtractImage = Maths.multiply(darkFit.reshape(darkFit.getSize(), 1), 1./in.getShapeRef()[1]);

				displayData.add(darkData);
				displayData.add(smoothedDarkData);
				displayData.add(darkFit);
				displayData.add(profile);
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

	private void cleanUpProfile(Dataset profile) {
		if (profile.getDouble() == 0) {
			profile.set(0.5*(profile.getDouble(1) + profile.getDouble(2)), 0); // ensure 1st entry is non-zero
		}
	}

	private static final int CLIP_END = -10; // clip end off

	private double findDarkDataOffset(Dataset in, Dataset smooth) {
		// find extent of shadow region on right by looking for trough
		Dataset y;
		if (smooth.getRank() == 2) {
			y = smooth.hasFloatingPointElements() ? smooth.sum(1, true) :
				smooth.cast(LongDataset.class).sum(1, true); // avoid integer overflows
			y.squeezeEnds();
			cleanUpProfile(y);
		} else {
			y = smooth;
		}

		Dataset d = Maths.derivative(DatasetFactory.createRange(y.getSize()), y, 1);
		int r = d.argMin(true);
		double dmin = d.getDouble(r);
		List<Double> z = DatasetUtils.crossings(d, dmin/2);
		int b = r + (int) Math.ceil(2.5 * (z.get(1) - z.get(0)));
		System.err.println("Crossings: " + z + " give start of " + b);

		Slice s = new Slice(b, CLIP_END);
		smooth = smooth.getSliceView(s);
		in = in.getSliceView(s);

		Offset so = new Offset(smooth);
		so.setParameterValues(0);
		double res = fitFunction("Exception in dark data fit", so, DatasetFactory.zeros(in.getShapeRef()), in);
		System.err.printf("Offset results: res=%g\n%s\n", res, so);
		return so.getParameterValue(0);
	}

	class Offset extends AFunction {
		private static final long serialVersionUID = -5259488500375549641L;
		private Dataset in;

		public Offset(Dataset in) {
			super(1);
			this.in = in;
		}

		@Override
		public double val(double... values) {
			return 0;
		}

		@Override
		protected void setNames() {
		}

		@Override
		public void fillWithValues(DoubleDataset data, CoordinatesIterator it) {
			Maths.add(in, getParameterValue(0), data);
		}

		@Override
		public void fillWithPartialDerivativeValues(IParameter parameter, DoubleDataset data, CoordinatesIterator it) {
			data.fill(1);
		}
	}

	class ScaleAndOffset extends AFunction {
		private static final long serialVersionUID = -5789700891337976334L;
		private Dataset in;
		private BroadcastSelfIterator bit;
		private int[] shape;

		public ScaleAndOffset(Dataset in) {
			super(2);
			this.in = in;
		}

		@Override
		public double val(double... values) {
			return 0;
		}

		@Override
		protected void setNames() {
		}

		@Override
		public void fillWithValues(DoubleDataset data, CoordinatesIterator it) {
			double a = getParameterValue(0);
			double b = getParameterValue(1);

			int[] dshape = data.getShapeRef();
			if (bit == null || !Arrays.equals(shape, dshape)) {
				shape = dshape;
				bit = BroadcastSingleIterator.createIterator(data, in);
			} else {
				bit.reset();
			}
			bit.setOutputDouble(true);
			while (bit.hasNext()) {
				data.setAbs(bit.aIndex, a * bit.bDouble + b);
			}
		}
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

	private Dataset createFilter() {
		double l = model.getGaussianSmoothingLength();
		int n = (int) Math.ceil(3*l);
		int m = 2*n + 1;
		Dataset x = DatasetFactory.createRange(m);
		x.isubtract(n);
		return Operations.operate(new GaussianOperation(l), x, null);
	}

	private Dataset smoothFilter1D(Dataset darkProfile) {
		Dataset g = createFilter();
		int n = g.getSize() / 2;
		g.imultiply(1./((Number) g.sum(true)).doubleValue());
		Dataset smoothed = Signal.convolveToSameShape(darkProfile.cast(DoubleDataset.class), g, null);
		smoothed.setSlice(smoothed.getDouble(n), new Slice(n));
		smoothed.setSlice(smoothed.getDouble(-n), new Slice(-n-1, null));
		return smoothed;
	}

	private Dataset smoothFilter2D(Dataset darkImage) {
		Dataset g = createFilter();
		int n = g.getSize() / 2;
		g = LinearAlgebra.outerProduct(g, g);
		g.imultiply(1./((Number) g.sum(true)).doubleValue());
		Dataset smoothed = Signal.convolveToSameShape(darkImage.cast(DoubleDataset.class), g, null);
		smoothed.setSlice(smoothed.getSliceView(new Slice(n, n+1)), new Slice(n));
		smoothed.setSlice(smoothed.getSliceView(new Slice(-n, -n+1)), new Slice(-n+1, null));
		smoothed.setSlice(smoothed.getSliceView(null, new Slice(n, n+1)), null, new Slice(n));
		smoothed.setSlice(smoothed.getSliceView(null, new Slice(-n, -n+1)), null, new Slice(-n+1, null));
		return smoothed;
	}

	private void createDarkData(SliceFromSeriesMetadata ssm) {
		String file = model.getDarkImageFile();
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

			String data = ssm.getDatasetName();
	
			ILazyDataset dark;
			try {
				dark = LocalServiceManager.getLoaderService().getData(file, null).getLazyDataset(data);
			} catch (Exception e) {
				throw new OperationException(this, "Could not load dark image file", e);
			}

			darkImageCountTime = getCountTime(file).getDouble();

			Dataset d;
			try {
				d = DatasetUtils.sliceAndConvertLazyDataset(dark);
			} catch (DatasetException e) {
				throw new OperationException(this, "Could not read in dark image", e);
			}

			d.squeezeEnds();
			if (d.getRank() != 2) { // TODO implement averaging
				throw new OperationException(this, "Can only handle single frame in dark image");
			}

			if (model.isMode2D()) {
				darkData = d;
				removeBlips2D();
			} else {
				// sum all so that only profile along row is left
				if (model.isRemoveOutliers()) {
					darkData = createProfileWithoutOutliers(d);
				} else {
					darkData = d.sum(1, true).cast(LongDataset.class);
				}
				if (darkData.getDouble() == 0) {
					darkData.set(0.5*(darkData.getDouble(1) + darkData.getDouble(2)), 0); // ensure 1st entry is non-zero
				}
				darkData.imultiply(1./dark.getShape()[0]); // assume 3D
				darkData.setName("dark_profile");
		
				removeBlips1D();
			}
		}

		if (model.isMode2D()) {
			smoothedDarkData = smoothFilter2D(darkData);
			smoothedDarkData.setName("dark_smoothed_image");
		} else {
			smoothedDarkData = smoothFilter1D(darkData);
			smoothedDarkData.setName("dark_smoothed_profile");
		}
	}

	private void removeBlips2D() {
		fitImageHistogram(darkData, true);

		IndexIterator it = darkData.getIterator(true);
		double thr = pdf.getParameterValue(0) + 2 * pdf.getParameterValue(1);
		log.append("Blip removal using threshold = %g", thr);
		int blips = 0;
		while (it.hasNext()) {
			int i = it.index;
			if (darkData.getElementDoubleAbs(i) >= thr) {
				blips++;
				while (it.hasNext() && darkData.getElementDoubleAbs(it.index) >= thr) {
				}
				int imax = it.index;
				double d = 0.5*(darkData.getElementDoubleAbs(i - 1) + darkData.getElementDoubleAbs(imax));
				while (i < imax) {
					darkData.setObjectAbs(i++, d);
				}
			}
		}
		log.append("Blips removed: %d", blips);
	}

	private void removeBlips1D() {
		// does so by checking for differences that greater than FWHM (assumes symmetric central distribution)
		// in zeroth dimension
		Dataset diffs = Maths.difference(darkData, 1, 0);

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
				double py = darkData.getElementDoubleAbs(i-1);
				double ny = darkData.getElementDoubleAbs(i+1);
				darkData.setObjectAbs(i, 0.5*(py + ny));
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
		int jmax = shape[1];
		LongDataset p = DatasetFactory.zeros(LongDataset.class, imax);
		for (int i = 0; i < imax; i++) {
			s.setSlice(0, i, i+1, 1);
			Dataset r = d.getSliceView(s);
			double q = (Double) Stats.quantile(r, Q_UPPER);
			double c = (Double) Stats.median(r);
			q -= c;
			int u = (int) Math.ceil((Double) c + Q_FACTOR * q);
			int max = r.max(true).intValue();
			long v = 0;
			if (u >= max) {
				v = ((Number) r.sum(true)).longValue();
			} else {
//				System.err.printf("Upper threshold of row %d is %d (cf %d) [%g, %g]\n", i, u, max, c, q);
				IndexIterator it = r.getIterator();
				int j = 0;
				while (it.hasNext()) {
					long x = r.getElementLongAbs(it.index);
					if (x < u) {
						j++;
						v += x;
					}
				}
				if (j > 0 && j < jmax) { // scale up by omitted fraction
					v = (long) (v * ((double) jmax / j));
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
			MetadataUtils.setAxes(t, dx);
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
			createDarkData(ssm);

			if (smoothedDarkData != null) {
				String filePath = ssm.getFilePath();
				double countTime = getCountTime(filePath).getDouble();
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
			setDisplayData(od, x, h, fit);

			// switch to use display data and record fit parameters as [1]-shape datasets
			// need two plots...
			auxData.add(ProcessingUtils.createNamedDataset(threshold, "threshold"));
			auxData.add(ProcessingUtils.createNamedDataset(pdf.getParameterValue(0), "pdf_posn"));
			auxData.add(ProcessingUtils.createNamedDataset(pdf.getParameterValue(1), "pdf_fwhm"));
			auxData.add(ProcessingUtils.createNamedDataset(pdf.getParameterValue(2), "pdf_area"));
			auxData.add(ProcessingUtils.createNamedDataset(residual, "pdf_residual"));
			MetadataUtils.setAxes(h, x);
			auxData.add(ProcessingUtils.createNamedDataset(h, "histogram"));
			MetadataUtils.setAxes(fit, x);
			auxData.add(ProcessingUtils.createNamedDataset(fit, "histogram_fit"));

			od.getData().addMetadata(new FitMetadataImpl(SubtractFittedBackgroundOperation.class).setFitFunction(pdf));
		}
		od.setAuxData(auxData.toArray(new Serializable[auxData.size()]));

		if (si.isLastSlice() && smoothedDarkData != null) {
			if (model.isMode2D()) {
				od.setSummaryData(smoothedDarkData);
			} else {
				od.setSummaryData(darkData, smoothedDarkData);
			}
		}

		return od;
	}

	// make display datasets here to be recorded in file
	public static void setDisplayData(OperationDataForDisplay od, Dataset x, Dataset h, Dataset fit) {
		Dataset a = Maths.log10(h);
		a.setName("Log10 of " + h.getName());
		Dataset b = Maths.log10(fit);
		b.setName("Log10 of " + fit.getName());
		// clamp values to above zero for nicer display 
		b.setByBoolean(0, Comparisons.lessThan(b, 0));
		MetadataUtils.setAxes(a, x);
		MetadataUtils.setAxes(b, x);
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

	/**
	 * Parse NeXus file to set various fields
	 * @param llog 
	 * @param filePath
	 */
	protected Dataset getCountTime(String filePath) {
		try {
			Tree t = LocalServiceManager.getLoaderService().getData(filePath, null).getTree();

			GroupNode root = t.getGroupNode();
			// entry1:NXentry
			//     before_scan:NXcollection
			//         andorPreampGain:NXcollection/andorPreampGain [1, 2, 4]
			//         pgmEnergy:NXcollection/ [energy in eV, always single value, even for an energy scan]

			GroupNode entry = (GroupNode) NexusTreeUtils.requireNode(root, "NXentry");
			GroupNode instrument = (GroupNode) NexusTreeUtils.requireNode(entry, "NXinstrument");
			GroupNode detector = (GroupNode) NexusTreeUtils.requireNode(instrument, "NXdetector");
			return DatasetUtils.sliceAndConvertLazyDataset(detector.getDataNode("count_time").getDataset());
		} catch (Exception e) {
			throw new OperationException(this, "Could not parse Nexus file " + filePath + " for count time", e);
		}
	}

}

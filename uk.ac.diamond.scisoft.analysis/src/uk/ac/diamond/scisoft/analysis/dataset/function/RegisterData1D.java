/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.dataset.function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.roi.IRectangularROI;
import org.eclipse.dawnsci.analysis.dataset.impl.FFT;
import org.eclipse.dawnsci.analysis.dataset.impl.Signal;
import org.eclipse.dawnsci.analysis.dataset.impl.function.DatasetToDatasetFunction;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.dataset.SliceND;

import uk.ac.diamond.scisoft.analysis.fitting.Fitter;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Gaussian;

/**
 * Register 1D data using a phase correlation method that has sub-pixel accuracy
 * <p>
 * This fails for noisy, relatively featureless data
 */
public class RegisterData1D implements DatasetToDatasetFunction {

	private IRectangularROI roi = null;
	private double tukeyWidth = 0.0;
	private int[] shape;
	private int[] pShape; // padded shape
	private Dataset window; // window function
	private boolean dirty = true;
	private SliceND slice = null;
	private Dataset tFilter = null;
	private Dataset filter;

	private IDataset anchor;
	private Dataset cfAnchor; // conjugate transform of windowed anchor
	private Dataset fAnchor; // transform of windowed anchor

	public RegisterData1D() {
	}

	/**
	 * Set reference data that will act as an anchor
	 * @param reference
	 */
	public void setReference(IDataset reference) {
		if (reference.getRank() != 1) {
			throw new IllegalArgumentException("Reference dataset must be 1D");
		}

		anchor = reference;
		shape = anchor.getShape();
		dirty = true;
	}

	private int[] padShape(int[] shape) {
		int[] s = shape.clone();
//		for (int i = 0; i < s.length; i++) {
//			s[i] += shape[i] - 1; // pad 
//		}

		return s;
	}

	private void update(IDataset... datasets) {
		if (anchor == null) {
			if (datasets == null || datasets.length == 0) {
				throw new IllegalArgumentException("No reference defined and no data given");
			}
			setReference(datasets[0]);
		}
		int[] wShape;
		if (roi == null) {
			slice = new SliceND(shape);
			pShape = padShape(shape);
			wShape = shape;
		} else {
			double[] beg = roi.getPoint();
			double[] end = roi.getEndPoint();
			// use row-major ordering
			slice = new SliceND(shape, new Slice((int) Math.floor(beg[0]), (int) Math.ceil(end[0])));
			wShape = slice.getShape();
			pShape = padShape(wShape);
		}

		window = tukeyWidth > 0 ? Signal.tukeyWindow(wShape[0], tukeyWidth) : null;

		if (filter != null) {
			Dataset nfilter = DatasetFactory.zeros(pShape);
			nfilter.setSlice(filter, null, filter.getShapeRef(), null);
			tFilter = FFT.fftn(nfilter, pShape, null);
		}
		fAnchor = pTF(anchor);
		cfAnchor = Maths.conjugate(fAnchor);
		dirty = false;
	}

	/**
	 * Set filter to use for convolving data
	 * @param filter
	 */
	public void setFilter(IDataset filter) {
		this.filter = DatasetUtils.convertToDataset(filter);
		dirty = true;
	}

	/**
	 * Preprocess, transform and filter
	 * @param data
	 * @return result
	 */
	public Dataset pTF(IDataset data) {
		// TODO use gradient images (dx, dy) as complex pair
		DoubleDataset preprocessed = DatasetUtils.cast(DoubleDataset.class, data.getSlice(slice));
		preprocessed.isubtract(preprocessed.mean());
		if (window != null) preprocessed.imultiply(window);
		Dataset transform = FFT.fftn(preprocessed, pShape, null);
		if (tFilter  != null) {
			transform.imultiply(tFilter);
		}
		return transform;
	}

	/**
	 * Set width for window function. Default is 0
	 * @param width
	 */
	public void setWindowFunction(double width) {
		tukeyWidth = width;
		dirty = true;
	}

	/**
	 * 
	 * @param rectangle (can be null)
	 */
	public void setRectangle(IRectangularROI rectangle) {
		roi = rectangle;
		dirty = true;
	}

	/**
	 * @param datasets array of datasets
	 * @return pairs of datasets of shift and shifted data
	 */
	@Override
	public List<Dataset> value(IDataset... datasets) {
		if (dirty) {
			update(datasets);
		}

		List<Dataset> result = new ArrayList<Dataset>();
		double shift;
		for (IDataset d : datasets) {
			if (!Arrays.equals(d.getShape(), shape)) {
				throw new IllegalArgumentException("Shape of dataset must match reference image");
			}

			Dataset pCorrelation = phaseCorrelate(d);
//			shift = calcForooshShift(pCorrelation);
//			System.err.println("Foroosh : " + shift);
//			shift = findCentroid(pCorrelation, Math.min(pCorrelation.getShapeRef()[0], 7));
//			System.err.println("Centroid: " + shift);
			shift = fitGaussians(pCorrelation, Math.min(pCorrelation.getShapeRef()[0], 11));
			System.err.println("Fit     : " + shift);
			result.add(DatasetFactory.createFromObject(shift));
			Dataset shiftedData = shiftData(DatasetUtils.convertToDataset(d) , shift);
			result.add(shiftedData);
		}

		return result;
	}

	/**
	 * @param im
	 * @return return central region of phase correlation
	 */
	public Dataset phaseCorrelate(IDataset im) {
		if (dirty) {
			update();
		}

		Dataset fImage = pTF(im);

		// phase correlate
		Dataset spectrum = Maths.phaseAsComplexNumber(Maths.dividez(fImage, fAnchor), true); // more stable???

		Dataset pc = FFT.ifftn(spectrum, pShape, null).getRealView();

		return FFT.fftshift(pc, null);
	}

	/**
	 * @param im
	 * @return return central region of phase correlation
	 */
	public Dataset phaseCorrelate2(IDataset im) {
		if (dirty) {
			update();
		}

		Dataset fImage = pTF(im);

		// phase correlate
		Dataset spectrum = Maths.phaseAsComplexNumber(fImage.imultiply(cfAnchor), true);

		Dataset pc = FFT.ifftn(spectrum, pShape, null).getRealView();

		return FFT.fftshift(pc, null);
	}

	/**
	 * @param im
	 * @return return central region of cross-correlation
	 */
	public Dataset crossCorrelate(IDataset im) {
		if (dirty) {
			update();
		}

		Dataset fImage = pTF(im);

		Dataset spectrum = fImage.imultiply(cfAnchor);

		Dataset cc = FFT.ifftn(spectrum, pShape, null).getRealView();

		return FFT.fftshift(cc, null);
	}

	/**
	 * @param im
	 * @param factor
	 * @return return central region of cross-correlation
	 */
	public Dataset crossCorrelate(IDataset im, int factor) {
		if (dirty) {
			update();
		}

		Dataset fImage = pTF(im);
		Dataset spectrum = fImage.imultiply(cfAnchor);
		int[] nshape;
		if (factor > 1) {
			nshape = pShape.clone();
			for (int i = 0; i < nshape.length; i++) {
				nshape[i] *= factor;
			}
			spectrum = FFT.zeroPad(spectrum, nshape, true);
		} else {
			nshape = pShape;
		}

		Dataset cc = FFT.ifftn(spectrum, nshape, null).getRealView();

		return FFT.fftshift(cc, null);
	}

	// Foroosh et al, "Extension of Phase Correlation to Subpixel Registration",
	// IEEE Trans. Image Processing, v11n3, 188-200 (2002)
	protected double calcForooshShift(Dataset pc) {
		int maxpos = pc.maxPos()[0]; // peak pos
		System.out.println("Max: " + maxpos);
		double c0 = pc.getDouble(maxpos);
		double shift = 0;
		int l = pc.getShapeRef()[0];
		maxpos++;
		if (maxpos < l) {
			final double c1 = pc.getDouble(maxpos);
			shift = c1/(c1-c0);
			if (Math.abs(shift) > 1) {
				shift = c1/(c1+c0);
			}
			shift = maxpos - 1 + shift - l/2;
		}

		return shift;
	}

	protected double fitGaussians(Dataset pc, int side) {
		int maxpos = pc.maxPos()[0]; // peak pos
		int hs = (side+1)/2;
		int beg = maxpos - hs + 1;
		double shift = 0;
		
		Gaussian gaussian = new Gaussian();

		Dataset data = pc.getSliceView(new int[] {beg}, new int[] {beg + side}, null).squeeze();
		shift = fitGaussian(gaussian, data) + beg - (pc.getShapeRef()[0])/2;

		return shift;
	}

	private double fitGaussian(Gaussian gaussian, Dataset data) {
		int length = data.getSize();
		gaussian.setParameterValues(length/2, 2., ((Number) data.sum()).doubleValue());
		Dataset axis = DatasetFactory.createRange(DoubleDataset.class, data.getSize());
		try {
			Fitter.ApacheConjugateGradientFit(new Dataset[] {axis}, data, gaussian);
			return gaussian.getPosition();
		} catch (Exception e) {
		}
		return Double.NaN;
	}

	protected double findCentroid(Dataset pc, int side) {
		int maxpos = pc.maxPos()[0]; // peak pos
		int hs = (side+1)/2;
		int beg = maxpos - hs + 1;
		
		IndexIterator it = pc.getSliceIterator(new int[] {beg}, new int[] {beg + side}, null);
		int[] pos = it.getPos();
		double sum = 0, sum0 = 0;
		while (it.hasNext()) {
			double v = pc.getElementDoubleAbs(it.index);
			sum += v;
			sum0 += v*pos[0];
		}

		return sum0/sum - (pc.getShapeRef()[0])/2;
	}

	/**
	 * Shift 1D using linear interpolation
	 * @param im
	 * @param shift
	 * @return shifted data
	 */
	public static Dataset shiftData(Dataset im, double shift) {
		if (im.getRank() != 1) {
			throw new IllegalArgumentException("Dataset must be 1d");
		}
		Dataset newImage = DatasetFactory.zeros(im, DoubleDataset.class);
		int[] shape = im.getShapeRef();

		double cx0;
		for (int x0 = 0; x0 < shape[0]; x0++) {
			cx0 = x0 + shift;
			newImage.set(Maths.interpolate(im, cx0), x0);
		}

		return newImage;
	}
}

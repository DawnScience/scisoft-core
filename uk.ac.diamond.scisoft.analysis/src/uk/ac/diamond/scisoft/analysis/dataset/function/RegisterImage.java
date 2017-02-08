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
import org.eclipse.january.dataset.LinearAlgebra;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.dataset.SliceND;

import uk.ac.diamond.scisoft.analysis.fitting.Fitter;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Gaussian;

/**
 * Register images using a phase correlation method that has sub-pixel accuracy
 */
public class RegisterImage implements DatasetToDatasetFunction {

	private IRectangularROI roi = null;
	private double tukeyWidth = 0.25;
	private IDataset anchor;
	private int[] shape;
	private int[] pShape; // padded shape
	private Dataset window; // window function
	private Dataset fAnchor; // conjugate of windowed anchor
	private SliceND slice = null;
	private boolean dirty = true;

	public RegisterImage() {
	}

	/**
	 * Set reference image that will act as an anchor
	 * @param reference
	 */
	public void setReference(IDataset reference) {
		if (reference.getRank() != 2) {
			throw new IllegalArgumentException("Reference dataset must be 2D");
		}

		anchor = reference;
		shape = anchor.getShape();
		dirty  = true;
	}

	private int[] padShape(int[] shape) {
		int[] s = shape.clone();
//		for (int i = 0; i < s.length; i++) {
//			s[i] += shape[i] - 1; // pad 
//		}

		return s;
	}

	private void update() {
		int[] wShape;
		if (roi == null) {
			slice = new SliceND(shape);
			pShape = padShape(shape);
			wShape = shape;
		} else {
			double[] beg = roi.getPoint();
			double[] end = roi.getEndPoint();
			// use row-major ordering
			slice = new SliceND(shape, new Slice((int) Math.floor(beg[1]), (int) Math.ceil(end[1])),
					new Slice((int) Math.floor(beg[0]), (int) Math.ceil(end[0])));
			wShape = slice.getShape();
			pShape = padShape(wShape);
		}

		window = LinearAlgebra.outerProduct(Signal.tukeyWindow(wShape[0], tukeyWidth), Signal.tukeyWindow(wShape[1], tukeyWidth));

		fAnchor = Maths.conjugate(FFT.fftn(preprocess(anchor), pShape, null));
		dirty  = false;
	}

	private Dataset preprocess(IDataset image) {
		// TODO use gradient images (dx, dy) as complex pair
		return DatasetUtils.cast(DoubleDataset.class, image.getSlice(slice)).isubtract(image.mean()).imultiply(window);
	}

	public void setWindowFunction(double width) {
		tukeyWidth = width;
		dirty  = true;
	}

	/**
	 * 
	 * @param rectangle (can be null)
	 */
	public void setRectangle(IRectangularROI rectangle) {
		roi = rectangle;
		dirty  = true;
	}

	/**
	 * @param datasets array of datasets
	 * @return pairs of datasets of shift and shifted images
	 */
	@Override
	public List<Dataset> value(IDataset... datasets) {
		if (dirty) {
			update();
		}

		List<Dataset> result = new ArrayList<Dataset>();
		double[] shifts;
		for (IDataset d : datasets) {
			if (!Arrays.equals(d.getShape(), shape)) {
				throw new IllegalArgumentException("Shape of dataset must match reference image");
			}

			Dataset pCorrelation = phaseCorrelate(d);
//			shifts = calcForooshShift(pCorrelation);
//			System.err.println("Foroosh : " + Arrays.toString(shifts));
//			shifts = findCentroid(pCorrelation, Math.min(pCorrelation.getShapeRef()[0], 7));
//			System.err.println("Centroid: " + Arrays.toString(shifts));
			shifts = fitGaussians(pCorrelation, Math.min(pCorrelation.getShapeRef()[0], 11));
			System.err.println("Fit     : " + Arrays.toString(shifts));
			result.add(DatasetFactory.createFromObject(shifts));
			Dataset shiftedImage = shiftImage(DatasetUtils.convertToDataset(d) , shifts);
			result.add(shiftedImage);
		}

		return result;
	}

	/**
	 * @param im
	 * @return return central region of phase correlation
	 */
	protected Dataset phaseCorrelate(IDataset im) {
		if (dirty) {
			update();
		}

		Dataset fImage = FFT.fftn(preprocess(im), pShape, null);

		// phase correlate
		Dataset spectrum = Maths.phaseAsComplexNumber(fImage.imultiply(fAnchor), true);
		// spectrum = Maths.phaseAsComplexNumber(Maths.dividez(fAnchor, fImage), true); // more stable???

		Dataset pc = FFT.ifftn(spectrum, pShape, null).getRealView();

		return FFT.fftshift(pc, null);
	}

	protected double[] calcForooshShift(Dataset pc) {
		int[] maxpos = pc.maxPos(); // peak pos
//		System.out.println("Max: " + Arrays.toString(maxpos));
		double c0 = pc.getDouble(maxpos);
		double[] shifts = new double[2];
		for (int i = 0; i < 2; i++) {
			int l = pc.getShapeRef()[i];
			maxpos[i]++;
			if (maxpos[i] < l) {
				final double c1 = pc.getDouble(maxpos);
				double shift = c1/(c1-c0);
				if (Math.abs(shift) > 1) {
					shift = c1/(c1+c0);
				}
				shifts[i] = maxpos[i] - 1 + shift - l/2;
			}
			maxpos[i]--;
		}

		return shifts;
	}

	protected double[] fitGaussians(Dataset pc, int side) {
		int[] maxpos = pc.maxPos(); // peak pos
		int hs = (side+1)/2;
		int[] beg = new int[] {maxpos[0] - hs + 1, maxpos[1] - hs + 1};
		double[] shifts = new double[2];
		
		Gaussian gaussian = new Gaussian();

		Dataset data = pc.getSliceView(new int[] {beg[0], maxpos[1]}, new int[] {beg[0] + side, maxpos[1]+1}, null).squeeze();
		shifts[0] = fitGaussian(gaussian, data) + beg[0] - (pc.getShapeRef()[0])/2;

		data = pc.getSliceView(new int[] {maxpos[0], beg[1]}, new int[] {maxpos[0]+1, beg[1] + side}, null).squeeze();
		shifts[1] = fitGaussian(gaussian, data) + beg[1] - (pc.getShapeRef()[1])/2;

		return shifts;
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

	protected double[] findCentroid(Dataset pc, int side) {
		int[] maxpos = pc.maxPos(); // peak pos
		int hs = (side+1)/2;
		int[] beg = new int[] {maxpos[0] - hs + 1, maxpos[1] - hs + 1};
		
		IndexIterator it = pc.getSliceIterator(new int[] {beg[0], beg[1]}, new int[] {beg[0] + side, beg[1] + side}, null);
		int[] pos = it.getPos();
		double sum = 0, sum0 = 0, sum1 = 0;
		while (it.hasNext()) {
			double v = pc.getElementDoubleAbs(it.index);
			sum += v;
			sum0 += v*pos[0];
			sum1 += v*pos[1];
		}

		return new double[] {sum0/sum - (pc.getShapeRef()[0])/2, sum1/sum - (pc.getShapeRef()[1])/2};
	}

	private Dataset shiftImage(Dataset im, double[] shifts) {
		Dataset newImage = DatasetFactory.zeros(im);

		double cx0, cx1;
		for (int x0 = 0; x0 < shape[0]; x0++) {
			cx0 = x0 - shifts[0];
			for (int x1 = 0; x1 < shape[1]; x1++) {
				cx1 = x1 - shifts[1];
				newImage.set(Maths.interpolate(im, cx0, cx1), x0, x1);
			}
		}

		return newImage;
	}
}
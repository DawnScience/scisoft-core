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
import org.eclipse.dawnsci.analysis.dataset.DatasetCache;
import org.eclipse.dawnsci.analysis.dataset.impl.FFT;
import org.eclipse.dawnsci.analysis.dataset.impl.Signal;
import org.eclipse.dawnsci.analysis.dataset.impl.function.DatasetToDatasetFunction;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.LinearAlgebra;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.dataset.SliceND;

import uk.ac.diamond.scisoft.analysis.image.ImageUtils;

/**
 * Register images using a cross correlation method that has sub-pixel accuracy
 * <p>
 * This is suitable for noisy images and based on MotionCorr's whole frame registration algorithm
 * as detailed in XM Li, P Mooney, S Zheng, C Booth, MB Braunfeld, S Gubbens, DA Agard and YF Cheng,
 * "Electron counting and beam-induced motion correction enable near atomic resolution single particle
 * cryoEM", Nature Methods 10(6), 584-90 (2013); doi:10.1038/nmeth.2472
 */
public class RegisterNoisyImage implements DatasetToDatasetFunction {

	private IRectangularROI roi = null;
	private double tukeyWidth = 0.25;
	private int[] shape;
	private int[] pShape; // padded shape
	private Dataset window; // window function
	private SliceND slice = null;
	private boolean dirty = true;
	private Dataset tFilter = null;
	private Dataset filter;
	private DatasetCache filtered;
	private IMonitor monitor = null;
	private boolean shiftImage = true;

	public RegisterNoisyImage() {
		filtered = new DatasetCache(new DatasetToDatasetFunction() {
			@Override
			public List<? extends IDataset> value(IDataset... datasets) {
				List<Dataset>list = new ArrayList<>();
				for (IDataset d : datasets) {
					list.add(pTF(d));
				}
				return list;
			}
		});
	}

	/**
	 * @param monitor The monitor to set.
	 */
	public void setMonitor(IMonitor monitor) {
		this.monitor = monitor;
	}

	private int[] padShape(int[] shape) {
		int[] s = shape.clone();
//		for (int i = 0; i < s.length; i++) {
//			s[i] += shape[i] - 1; // pad 
//		}

		return s;
	}

	public void setShiftImage(boolean shift) {
		this.shiftImage  = shift;
	}

	public void update(int[] shape) {
		this.shape = shape;
		update();
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

		if (filter != null) {
			Dataset nfilter = DatasetFactory.zeros(pShape);
			nfilter.setSlice(filter, null, filter.getShapeRef(), null);
			tFilter = FFT.fftn(nfilter, pShape, null);
		}
		dirty = false;
	}

	/**
	 * Set filter to use for convolving images
	 * @param filter
	 */
	public void setFilter(IDataset filter) {
		this.filter = DatasetUtils.convertToDataset(filter);
		dirty = true;
	}

	/**
	 * Preprocess, transform and filter
	 * @param image
	 * @return result
	 */
	public Dataset pTF(IDataset image) {
		// TODO use gradient images (dx, dy) as complex pair
		DoubleDataset preprocessed = DatasetUtils.cast(DoubleDataset.class, image.getSlice(slice));
		preprocessed.isubtract(preprocessed.mean()).imultiply(window);
		Dataset transform = FFT.fftn(preprocessed, pShape, null);
		if (tFilter  != null) {
			transform.imultiply(tFilter);
		}
		return transform;
	}

	public Dataset getPTF(IDataset image) {
		return filtered.get(image);
	}

	/**
	 * Set width for window function
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

	private static final double R_LIMIT = 1;

	/**
	 * @param datasets array of datasets
	 * @return pairs of datasets of shift and shifted images
	 */
	@Override
	public List<Dataset> value(IDataset... datasets) {
		if (monitor != null) {
			monitor.subTask("Registering images");
		}

		if (dirty) {
			if (datasets == null || datasets.length == 0) {
				throw new IllegalArgumentException("Need at least one image");
			}
			IDataset image = datasets[0];
			if (image.getRank() != 2) {
				throw new IllegalArgumentException("Dataset must be 2D");
			}

			shape = image.getShape();
			update();
		}

		List<Dataset> result = new ArrayList<Dataset>();
		int n = datasets.length;
		/*
		 * Work out shifts for each of n(n-1)/2 pairs
		 * 
		 * r_i = displacement between I_i and I_(i+1)
		 * b_(ij) = computed distance between I_i and I_j (where i < j)
		 * 
		 * then
		 *    b_(01) = r_0
		 *    b_(02) = r_0 + r_1
		 *       ...
		 *    b_(0(N-1)) = r_0 + r_1 + r_(N-2)
		 *    b_(12) = r_1
		 *       ...
		 *       ...
		 *       ...
		 *    b_((N-2)(N-1)) = r_(N-2)
		 * or
		 *    b_(ij) = A_((ij)k) r_k, k = [0, N-2]
		 *  which can be solved as a linear least squares problem:
		 *        r = (A^T A)^-1 A^T b
		 */
		int m = n*(n-1); // total number of knowns

		// create all values for A and b as list of rows
		double[] shift;
		List<double[]> list = new ArrayList<>();
		int rlen = 2*n - 1;
		for (int i = 0; i < n - 1; i++) {
			double[] rowk0 = new double[rlen];
			double[] rowk1 = new double[rlen];
			rowk0[2*i] = 1;
			rowk1[2*i + 1] = 1;
			Dataset cf = Maths.conjugate(filtered.get(datasets[i]));
			shift = ccFindShift(cf, datasets[i+1]);
			if (monitor != null) {
				if (monitor.isCancelled()) {
					return null;
				}
				monitor.worked(1);
			}
			rowk0[rlen - 1] = shift[0];
			rowk1[rlen - 1] = shift[1];
			list.add(rowk0);
			list.add(rowk1);

			for (int j = i + 2; j < n; j++) {
				rowk0 = new double[rlen];
				rowk1 = new double[rlen];
				for (int l = i; l < j; l++) {
					rowk0[2*l] = 1;
					rowk1[2*l + 1] = 1;
				}

				shift = ccFindShift(cf, datasets[j]);
				if (monitor != null) {
					if (monitor.isCancelled()) {
						return null;
					}
					monitor.worked(1);
				}
				rowk0[rlen - 1] = shift[0];
				rowk1[rlen - 1] = shift[1];
				list.add(rowk0);
				list.add(rowk1);
			}
		}

		assert list.size() == m;
		boolean[] use = new boolean[m];
		Arrays.fill(use, true);

		DoubleDataset[] fit = null;
		int used = 0;
		do {
			// need to check residual and prune outliers
			// loop if any false until too many rows are dropped
			// (used > 2*(n - 1) for least square)
			fit = fitLeastSquares(list, use);
			DoubleDataset residuals = fit[1];
			int rows = residuals.getSize();

			used = 0;
			for (int i = 0, k = 0; i < m; i += 2) {
				if (use[i]) {
					if (residuals.getDouble(k) > R_LIMIT || residuals.getDouble(k + 1) > R_LIMIT) {
						use[i] = false;
						use[i + 1] = false;
					} else {
						used += 2;
					}
					k += 2;
				}
			}
			if (used == rows) { // no change
				break;
			}
			if (monitor != null) {
				if (monitor.isCancelled()) {
					return null;
				}
				monitor.worked(1);
			}
//			System.err.println("Useable " + Arrays.toString(use));
		} while (used >= rlen - 1);

		if (used < rlen - 1) { // dropped too many rows
			//return null;	Gives no user feedback (other than NPE) to what went wrong - replaced with below for now
			throw new NullPointerException("Images are too far out of sync to be aligned!"); // AlignProgressJob should be refactored to make use of Status
		}

		@SuppressWarnings("null")
		DoubleDataset vecr = fit[0];
		shift = new double[2];
		result.add(DatasetFactory.createFromObject(shift.clone()));
		Dataset shiftedImage = shiftImage ? DatasetUtils.convertToDataset(datasets[0]).clone() : null;
		result.add(shiftedImage);
		for (int i = 1; i < n; i++) {
			int x = 2*i - 2;
			shift[0] += vecr.get(x);
			shift[1] += vecr.get(x+1);
//			System.err.println("Cumulative shifts for " + i + ": " + Arrays.toString(shift));
			result.add(DatasetFactory.createFromObject(shift.clone()));
			shiftedImage = shiftImage ? ImageUtils.shiftImage(DatasetUtils.convertToDataset(datasets[i]), shift) : null;
			result.add(shiftedImage);
			if (shiftImage && monitor != null) {
				if (monitor.isCancelled()) {
					return null;
				}
				monitor.worked(1);
			}
		}
		return result;
	}

	private DoubleDataset[] fitLeastSquares(List<double[]> rows, boolean[] use) {
		int used = 0;
		for (boolean u : use) {
			if (u) {
				used++;
			}
		}

		int end = rows.get(0).length - 1;

		DoubleDataset vecb = DatasetFactory.zeros(DoubleDataset.class, used);
		DoubleDataset matA = DatasetFactory.zeros(DoubleDataset.class, used, end);
		int k = 0;
		for (int i = 0; i < use.length; i++) {
			if (use[i]) {
				double[] row = rows.get(i);
				for (int j = 0; j < end; j++) {
					matA.set(row[j], k, j);
				}
				vecb.set(row[end], k);
				k++;
			}
		}

//		System.err.println(matA.toString(true));
		Dataset matAT = matA.getTransposedView();
		Dataset matInv = LinearAlgebra.calcPseudoInverse(LinearAlgebra.dotProduct(matAT, matA));
		DoubleDataset vecr = DatasetUtils.cast(DoubleDataset.class, LinearAlgebra.dotProduct(matInv, LinearAlgebra.dotProduct(matAT, vecb)));
//		System.err.println("Shifts : " + vecr.toString(true));

		DoubleDataset residuals = DatasetUtils.cast(DoubleDataset.class, Maths.abs(Maths.subtract(LinearAlgebra.dotProduct(matA, vecr), vecb)));
//		System.err.println("Residuals are " + residuals.toString(true));
		return new DoubleDataset[] {vecr, residuals};
	}

	private static int peakCrop = 16; // length of side of cropped area around peak
	private static double superSampleFactor = 32; // supersampling factor

	public double[] ccFindShift(IDataset fim, IDataset imb) {
		Dataset cc = crossCorrelate(fim, imb);
		int[] maxpos = cc.maxPos(); // peak pos
//		System.out.println("Max: " + Arrays.toString(maxpos));

		int hs = (peakCrop+1)/2;
		int[] beg = new int[] {maxpos[0] - hs + 1, maxpos[1] - hs + 1};
		Dataset peak = cc.getSliceView(beg, new int[] {beg[0] + peakCrop, beg[1] + peakCrop}, null);
		Dataset superPeak = superSample(peak, (int) superSampleFactor);
		
		maxpos = superPeak.maxPos(); // new peak pos
		return new double[] {maxpos[0]/superSampleFactor + beg[0] - (cc.getShapeRef()[0] + 1)/2, maxpos[1]/superSampleFactor + beg[1] - (cc.getShapeRef()[1] + 1)/2};
	}

	private Dataset superSample(Dataset image, int factor) {
		int[] nShape = image.getShape();
		for (int i = 0; i < nShape.length; i++) {
			nShape[i] *= factor;
		}
		return FFT.ifftn(FFT.zeroPad(FFT.fftn(image, null, null), nShape, true), null, null).getRealView();
	}

	/**
	 * @param fim
	 * @param imb
	 * @return return central region of cross-correlation
	 */
	public Dataset crossCorrelate(IDataset fim, IDataset imb) {
		if (dirty) {
			update();
		}

		Dataset spectrum = Maths.multiply(fim, filtered.get(imb));

		Dataset cc = FFT.ifftn(spectrum, pShape, null).getRealView();

		return FFT.shift(cc, true);
	}
}

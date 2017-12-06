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

/**
 * Register 1D data using a cross correlation method that has sub-pixel accuracy
 * <p>
 * This is suitable for noisy 1D data
 */
public class RegisterNoisyData1D implements DatasetToDatasetFunction {

	private IRectangularROI roi = null;
	private double tukeyWidth = 0.0;
	private int[] shape;
	private int[] pShape; // padded shape
	private Dataset window = null; // window function
	private SliceND slice = null;
	private boolean dirty = true;
	private Dataset tFilter = null;
	private Dataset filter;
	private DatasetCache filtered;
	private IMonitor monitor = null;
	private boolean shiftData = true;
	private boolean fitAll = true;

	private double peakThresholdFraction = 0.90; // fraction of peak height to use as threshold
	private boolean useFirst = true;

	/**
	 * Set function to fit all data at once
	 * @param fitAll
	 */
	public void setFitAll(boolean fitAll) {
		this.fitAll = fitAll;
	}

	/**
	 * Set which given dataset to use as anchor
	 * @param useFirst if true, use the first dataset otherwise use the last dataset
	 */
	public void setUseFirstAsAnchor(boolean useFirst) {
		this.useFirst = useFirst;
		dirty = true;
	}

	public RegisterNoisyData1D() {
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

		return s;
	}

	public void setShiftData(boolean shift) {
		this.shiftData  = shift;
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

	public Dataset getPTF(IDataset data) {
		return filtered.get(data);
	}

	/**
	 * Set width for window function. Default is 0
	 * @param width for Tukey window
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
	 * @return pairs of datasets of shift and shifted data
	 */
	@Override
	public List<Dataset> value(IDataset... datasets) {
		if (monitor != null) {
			monitor.subTask("Registering images");
		}

		if (datasets == null || datasets.length == 0) {
			throw new IllegalArgumentException("Need at least one image");
		}

		int n = datasets.length;
		if (!useFirst) { // reverse array
			datasets = datasets.clone();
			for (int i = 0, j = n - 1; i < j; i++, j--) {
				IDataset t = datasets[j];
				datasets[j] = datasets[i];
				datasets[i] = t;
			}
		}

		if (dirty) {
			IDataset image = datasets[0];
			if (image.getRank() != 1) {
				throw new IllegalArgumentException("Dataset must be 1D");
			}

			shape = image.getShape();
			update();
		}

		List<Dataset> result = new ArrayList<Dataset>();

		if (fitAll) {
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
			int m = n*(n-1)/2; // total number of knowns
			double shift;
	
			// create all values for A and b as list of rows
			List<double[]> list = new ArrayList<>();
			int rlen = n;
			for (int i = 0; i < n - 1; i++) {
				double[] rowk0 = new double[rlen];
				rowk0[i] = 1;
				Dataset cf = Maths.conjugate(filtered.get(datasets[i]));
				shift = ccFindShift(cf, datasets[i+1]);
				if (monitor != null) {
					if(monitor.isCancelled()) {
						return result;
					}
					monitor.worked(1);
				}
				rowk0[rlen - 1] = shift;
				list.add(rowk0);
	
				for (int j = i + 2; j < n; j++) {
					rowk0 = new double[rlen];
					for (int l = i; l < j; l++) {
						rowk0[l] = 1;
					}
	
					shift = ccFindShift(cf, datasets[j]);
					if (monitor != null) {
						if(monitor.isCancelled()) {
							return result;
						}
						monitor.worked(1);
					}
					rowk0[rlen - 1] = shift;
					list.add(rowk0);
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
				// (used > (n - 1) for least square)
				fit = fitLeastSquares(list, use);
				DoubleDataset residuals = fit[1];
				int rows = residuals.getSize();
	
				used = 0;
				for (int i = 0, k = 0; i < m; i++) {
					if (use[i]) {
						if (residuals.getDouble(k) > R_LIMIT) {
							use[i] = false;
						} else {
							used++;
						}
						k++;
					}
				}
				if (used == rows) { // no change
					break;
				}
				if (monitor != null) {
					if(monitor.isCancelled()) {
						return result;
					}
					monitor.worked(1);
				}
	//			System.err.println("Useable " + Arrays.toString(use));
			} while (used >= rlen - 1);
	
			if (used < rlen - 1) { // dropped too many rows
				return null;
			}
	
			@SuppressWarnings("null")
			DoubleDataset vecr = fit[0];
			shift = 0;
			result.add(DatasetFactory.createFromObject(shift));
			Dataset shiftedImage = shiftData ? DatasetUtils.convertToDataset(datasets[0]).clone() : null;
			result.add(shiftedImage);
			for (int i = 1; i < n; i++) {
				int x = i - 1;
				shift += vecr.get(x);
	//			System.err.println("Cumulative shifts for " + i + ": " + Arrays.toString(shift));
				result.add(DatasetFactory.createFromObject(shift));
				shiftedImage = shiftData ? RegisterData1D.shiftData(DatasetUtils.convertToDataset(datasets[i]), shift) : null;
				result.add(shiftedImage);
				if (shiftData && monitor != null) {
					if(monitor.isCancelled()) {
						return result;
					}
					monitor.worked(1);
				}
			}
		} else {
			double shift = 0;
			result.add(DatasetFactory.createFromObject(shift));
			Dataset shiftedImage = shiftData ? DatasetUtils.convertToDataset(datasets[0]).clone() : null;
			result.add(shiftedImage);
			Dataset cf = Maths.conjugate(filtered.get(datasets[0]));
			for (int i = 1; i < n; i++) {
				shift = ccFindShift(cf, datasets[i]);
				result.add(DatasetFactory.createFromObject(shift));
				shiftedImage = shiftData ? RegisterData1D.shiftData(DatasetUtils.convertToDataset(datasets[i]), shift) : null;
				result.add(shiftedImage);
				if (monitor != null) {
					if(monitor.isCancelled()) {
						return result;
					}
					monitor.worked(1);
				}
			}
		}

		if (!useFirst) { // block reverse order of list
			List<Dataset> reversed = new ArrayList<>();
			for (int i = 2*(n - 1); i >= 0; i -= 2) {
				reversed.add(result.get(i));
				reversed.add(result.get(i+1));
			}

			return reversed;
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

	/**
	 * Set threshold for determination of the centroid of cross-correlation peak
	 * @param threshold as a fraction of peak maximum (default is 0.90)
	 */
	public void setPeakCentroidThresholdFraction(double threshold) {
		peakThresholdFraction = threshold;
	}

	/**
	 * Find shift in cross-correlation peak
	 * @param fim
	 * @param imb
	 * @return shift
	 */
	public double ccFindShift(IDataset fim, IDataset imb) {
		Dataset cc = crossCorrelate(fim, imb);
		int[] maxpos = cc.maxPos(); // peak pos
		// crop to threshold intercept with at least one side of peak and find centroid
		double threshold = cc.max().doubleValue() * peakThresholdFraction;
		int left = maxpos[0];
		int right = left;
		double sum = cc.max().doubleValue(), sum0 = sum*left;
		double vl, vr;
		boolean bl = true, br = true;
		do {
			if (bl) {
				vl = cc.getDouble(--left);
				bl = vl > threshold;
				sum += vl;
				sum0 += vl*left;
			}
			if (br) {
				vr = cc.getDouble(++right);
				br = vr > threshold;
				sum += vr;
				sum0 += vr*right;
			}
		} while (bl || br);
//		System.out.println("Width used to find centroid = " + (right - left));

		int hs = (cc.getShapeRef()[0] + 1)/2;
		return sum0/sum - hs;
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

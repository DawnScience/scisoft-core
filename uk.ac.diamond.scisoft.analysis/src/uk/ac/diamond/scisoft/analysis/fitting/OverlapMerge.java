/*-
 * Copyright 2025 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting;

import java.util.Arrays;

import org.eclipse.dawnsci.analysis.api.fitting.functions.IFunction;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
import org.eclipse.dawnsci.analysis.api.metadata.FitMetadata;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.dataset.metadata.FitMetadataImpl;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.Comparisons;
import org.eclipse.january.dataset.Comparisons.Monotonicity;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.Slice;

import uk.ac.diamond.scisoft.analysis.fitting.functions.StraightLine;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer.Optimizer;
import uk.ac.diamond.scisoft.analysis.utils.ErrorPropagationUtils;

/**
 * Class to merge overlapping datasets
 * <p>
 * Resample at smallest intervals
 * <pre>
 *      lower           upper
 *        v               v
 * A |  |  |  |  |  |  |  |
 * B     |   |   |   |   |   |   |
 * O       |  |  |  |  |  |
 * </pre>
 * 
 */
public class OverlapMerge {
	private boolean isOverlapping;
	private Dataset aX; // this contains beginning of overlap (and may contain zone before overlap)
	private Dataset bX; // this contains end of overlap (and may contain zone after overlap)
	private boolean isSwapped; // true if given inputs were swapped
	private int aStart; // exclusive index of start of overlap in dataset a
	private int aStop = -1; // exclusive index of stop of overlap in dataset a
	private int bStart; // exclusive index of stop of overlap in dataset b
	private int bStop = -1; // exclusive index of stop of overlap in dataset b
	private boolean useASampling;
	private boolean isAReversed;
	private boolean isBReversed;
	private Dataset[] scaleOffset;

	private static final Slice REVERSE_SLICE = new Slice(null, null, -1);
	private static final Dataset HALF = DatasetFactory.createFromObject(0.5);

	/**
	 * 
	 * @param aX
	 * @param bX
	 */
	public OverlapMerge(Dataset aX, Dataset bX) {
		boolean[] result = doesOverlap(aX, bX);
		isOverlapping = result[0];
		isAReversed = result[1];
		isBReversed = result[2];

		if (isAReversed) { // flip to be increasing
			aX = aX.getSliceView(REVERSE_SLICE);
		}

		if (isBReversed) {
			bX = bX.getSliceView(REVERSE_SLICE);
		}

		double minA = aX.min(true).doubleValue();
		double minB = bX.min(true).doubleValue();
		isSwapped = minA > minB;
		if (isSwapped) {
			this.aX = bX;
			this.bX = aX;
			isAReversed = result[2];
			isBReversed = result[1];
		} else {
			this.aX = aX;
			this.bX = bX;
		}
		aStop = this.aX.getSize();
		double stepA = Maths.difference(this.aX, 0, 0).min(true).doubleValue();
		double stepB = Maths.difference(this.bX, 0, 0).min(true).doubleValue();
		useASampling = stepA < stepB;
		if (isOverlapping) {
			setStart(this.bX.min(true).doubleValue());
			setStop(this.aX.max(true).doubleValue());
		}
	}

	/**
	 * @return true if X axes overlap
	 */
	public boolean isOverlapping() {
		return isOverlapping;
	}

	private static boolean isDecreasing(Monotonicity m) {
		return m == Monotonicity.NONINCREASING || m == Monotonicity.STRICTLY_DECREASING;
	}

	/**
	 * Determine whether given datasets overlap
	 * @param a
	 * @param b
	 * @return [true if dataset values overlap, true if a decreases, true if b decreases]
	 */
	private static boolean[] doesOverlap(Dataset a, Dataset b) {
		Monotonicity monoA = Comparisons.findMonotonicity(a);
		if (monoA == Monotonicity.NOT_ORDERED) {
			throw new IllegalArgumentException("Dataset a is not monotonic: " + a.getName());
		}
		Monotonicity monoB = Comparisons.findMonotonicity(b);
		if (monoB == Monotonicity.NOT_ORDERED) {
			throw new IllegalArgumentException("Dataset b is not monotonic " + b.getName());
		}

		boolean[] result = {false, isDecreasing(monoA), isDecreasing(monoB)};
		double maxA = a.max(true).doubleValue();
		double minB = b.min(true).doubleValue();
		if (maxA <= minB) {
			return result;
		}

		double minA = a.min(true).doubleValue();
		double maxB = b.max(true).doubleValue();
		result[0] = maxB > minA;
		return result;
	}

	static int[] findOverlap(Dataset a, Dataset b) {
		boolean[] test = doesOverlap(a, b);
		if (!test[0]) {
			return null;
		}

		if (test[1]) {
			a = a.getSliceView(REVERSE_SLICE);
		}

		if (test[2]) {
			b = b.getSliceView(REVERSE_SLICE);
		}
		double minA = a.min(true).doubleValue();
		double minB = b.min(true).doubleValue();
		double minO = Math.max(minA, minB);
		double maxA = a.max(true).doubleValue();
		double maxB = b.max(true).doubleValue();
		double maxO = Math.min(maxA, maxB);

		return new int[] {
			DatasetUtils.findIndexGreaterThanOrEqualTo(a, minO),
			DatasetUtils.findIndexGreaterThanOrEqualTo(b, minO),
			DatasetUtils.findIndexGreaterThanOrEqualTo(a, maxO),
			DatasetUtils.findIndexGreaterThanOrEqualTo(b, maxO)
		};
	}

	/**
	 * Get overlap start and end from given datasets
	 * @return [beg, end]
	 */
	public double[] getOverlap() {
		double[] ends = new double[2];
		double minB = bX.min(true).doubleValue();
		setStart(minB);
		double maxA = aX.max(true).doubleValue();
		double maxB = bX.max(true).doubleValue();
		boolean isBWithinA = maxB < maxA;
		setStop(isBWithinA ? maxB : maxA);
		ends[0] = aX.getDouble(Math.max(aStart - 1, 0));
		
		if (isBWithinA) { // when B ends in A, find index in A of x > maxB
			ends[1] = aX.getDouble(aStop);
		} else {
			ends[1] = bX.getDouble(bStop);
		}

		return ends;
	}

	public boolean setStart(double start) {
		int iStart = aX.getNDPosition(DatasetUtils.findIndexGreaterThan(aX, start))[0];
		boolean update = iStart != aStart;
		aStart = iStart;
		iStart = bX.getNDPosition(DatasetUtils.findIndexGreaterThanOrEqualTo(bX, start))[0];
		update |= iStart != bStart;
		bStart = iStart;
		return update;
	}

	public boolean setStop(double stop) {
		int iStop = aX.getNDPosition(DatasetUtils.findIndexGreaterThanOrEqualTo(aX, stop))[0];
		boolean update = iStop != aStop;
		aStop = iStop;
		iStop = bX.getNDPosition(DatasetUtils.findIndexGreaterThanOrEqualTo(bX, stop))[0];
		update |= iStop != bStop;
		bStop = iStop;
		return update;
	}

	/**
	 * Interpolate at new x points given x,y data
	 * @param x
	 * @param y
	 * @param newX points at which to sample given data
	 * @return linearly interpolated values (plus errors)
	 */
	static Dataset interpolate(Dataset x, Dataset y, Dataset newX) {
		Dataset newY = Maths.interpolate(x, y, newX, null, null);
		if (y.hasErrors()) {
			Dataset newYErrors = interpolateErrors(x, y.getErrors(), newX);
			newY.setErrors(newYErrors);
		}
		return newY;
	}

	private static Dataset interpolateErrors(Dataset x, Dataset yErrors, Dataset newX) {
		// assume x is monotonically increasing
		DoubleDataset r = DatasetFactory.zeros(newX, DoubleDataset.class);

		IndexIterator it = newX.getIterator();
		int[] strides = x.getStrides();
		DoubleDataset dx;
		if (strides != null && strides[0] != 1) {
			dx = DatasetUtils.copy(DoubleDataset.class, x);
		} else {
			dx = DatasetUtils.cast(DoubleDataset.class, x);
		}
		double[] xa = dx.getData();
		final int s = xa.length - 1;
		int k = -1;
		int start = 0;
		while (it.hasNext()) {
			k++;
			double v = newX.getElementDoubleAbs(it.index);
			int i = Arrays.binarySearch(xa, start, s, v);
			if (i < 0) {
				i = -i - 1;
				if (i == 0) {
					final double xa0 = xa[0];
					final double d1 = xa0 - xa[1];
					double t = d1 - v + xa0;
					if (t >= 0) {
						continue; // sets to zero
					}
					t /= d1;
					r.setAbs(k, t * yErrors.getDouble(0));
				} else if (i == s) {
					final double xas = xa[s];
					final double d1 = xas - xa[s - 1];
					double t = d1 - v + xas;
					if (t <= 0) {
						continue; // sets to zero
					}
					t /= d1;
					r.setAbs(k, t * yErrors.getDouble(s));
				} else {
					final double xai = xa[i];
					double t = (xai - v) / (xai - xa[i - 1]);
					double yi = (1 - t) * yErrors.getDouble(i);
					double yj = t * yErrors.getDouble(i - 1);
					r.setAbs(k, Math.hypot(yi, yj));
				}
			} else {
				r.setAbs(k, yErrors.getDouble(i));
			}
		}
		return r;
	}

	/**
	 * 
	 * @param aY
	 * @param bY
	 * @param start
	 * @param stop
	 * @return
	 */
	private Dataset[] sampleOverlap(Dataset aY, Dataset bY) {
		if (useASampling) {
			Slice overlapSlice = new Slice(aStart, aStop);
			Dataset overlapX = aX.getSliceView(overlapSlice);
			return new Dataset[] {overlapX, aY.getSliceView(overlapSlice), interpolate(bX, bY, overlapX)};
		}
		Slice overlapSlice = new Slice(bStart, bStop);
		Dataset overlapX = bX.getSliceView(overlapSlice);
		return new Dataset[] {overlapX, interpolate(aX, aY, overlapX), bY.getSliceView(overlapSlice)};
	}

	public enum NormOption {
		NEITHER, // just take the mean
		FIRST,  // normalize to A 
		SECOND; // normalize to B
	}

	/**
	 * @return scale and offset used to normalize other curve
	 */
	public Dataset[] getScaleOffset() {
		return scaleOffset;
	}


	private Dataset scaleAndOffset(Dataset in) {
		return ErrorPropagationUtils.addWithUncertainty(ErrorPropagationUtils.multiplyWithUncertainty(in, scaleOffset[0]), scaleOffset[1]);
	}

	/**
	 * Merge y data that corresponds to given x data
	 * @param sA
	 * @param sB
	 * @param option
	 * @return merged data [x, y]
	 * @throws DatasetException 
	 */
	public Dataset[] mergeOverlap(Dataset aY, Dataset bY, NormOption option) throws DatasetException {
		if (isSwapped) {
			Dataset tY = bY;
			bY = aY;
			aY = tY;
		}
		if (isAReversed) {
			aY = aY.getSliceView(new Slice(null, null, -1));
		}
		if (isBReversed) {
			bY = bY.getSliceView(new Slice(null, null, -1));
		}

		Dataset[] xyy = sampleOverlap(aY, bY);
		Dataset overlapX = xyy[0];
		Dataset overlapY = mergeOverlapPiece(xyy[1], xyy[2], option);

		Slice aSlice = new Slice(0, aStart);
		Slice bSlice = new Slice(bStop, null);
		bSlice.setLength(bX.getSize());

		Dataset allX = DatasetUtils.concatenate(new IDataset[] {aX.getSliceView(aSlice),
				overlapX, bX.getSliceView(bSlice)}, 0);
		allX.setName(option != NormOption.SECOND ? aX.getName() : bX.getName());

		Dataset nAY = aY.getSliceView(aSlice);
		Dataset nBY = bY.getSliceView(bSlice);
		if (option == NormOption.FIRST) {
			nBY = scaleAndOffset(nBY);
		}
		if (option == NormOption.SECOND) {
			nAY = scaleAndOffset(nAY);
		}

		Dataset allY = concatenate(nAY, overlapY, nBY);
		allY.setName(option != NormOption.SECOND ? aY.getName() : bY.getName());
		allY.addMetadata(overlapY.getFirstMetadata(FitMetadata.class));

		return new Dataset[] {allX, allY};
	}

	private Dataset concatenate(Dataset... in) {
		Dataset all = DatasetUtils.concatenate(in, 0);
		Dataset[] errs = new Dataset[in.length];
		boolean anyErrs = false;
		for (int i = 0; i < in.length; i++) {
			Dataset e = in[i].getErrors();
			errs[i] = e;
			anyErrs |= e != null;
		}
		if (anyErrs) {
			all.setErrors(DatasetUtils.concatenate(errs, 0));
		}
		return all;
	}

	/**
	 * Merge Y data that's been sampled over same X
	 * @param sA
	 * @param sB
	 * @param option
	 * @return 
	 * @throws DatasetException
	 */
	private Dataset mergeOverlapPiece(Dataset sA, Dataset sB, NormOption option) throws DatasetException {
		if (option == NormOption.NEITHER) {
			return ErrorPropagationUtils.multiplyWithUncertainty(ErrorPropagationUtils.addWithUncertainty(sA, sB), HALF);
		}

		Dataset inX;
		Dataset inY;
		if (option == NormOption.FIRST) {
			inX = sA;
			inY = sB;
		} else {
			inX = sB;
			inY = sA;
		}

		IFunction f = new StraightLine();
		IParameter scale = f.getParameter(0);
		scale.setValue(1);
		scale.setLimits(0.3, 3.0); // limits to prevent flipping
		IParameter offset = f.getParameter(1);
		offset.setLimits(-0., aStart);
		f.setParameterValues(1, 0);
		double residuals = fitFunction("Exception in fit", f, inY, inX);
		System.out.println("Residual from scale-offset fit: " + residuals);
		System.out.println("  : " + f);

		double[] fitValues = f.getParameterValues();
		scaleOffset = new Dataset[] {DatasetFactory.createFromObject(fitValues[0]), DatasetFactory.createFromObject(fitValues[1])};
		Dataset fitY = scaleAndOffset(inY);
		DoubleDataset meanY = ErrorPropagationUtils.multiplyWithUncertainty(ErrorPropagationUtils.addWithUncertainty(fitY, inX), HALF);

		FitMetadataImpl fmd = new FitMetadataImpl((Class<? extends IOperation<?,?>>) null);
		fmd.setFitFunction(f);
		meanY.addMetadata(fmd);
		return meanY;
	}

	private static double fitFunction(String excMessage, IFunction f, Dataset x, Dataset v) throws DatasetException {
		double residual = Double.POSITIVE_INFINITY;
		double[] errors = null;

		ApacheOptimizer opt = new ApacheOptimizer(Optimizer.LEVENBERG_MARQUARDT);
		opt.setWeight(null);
		try {
			opt.optimize(new Dataset[] {x}, v, f);
			residual = opt.calculateResidual();
			errors = opt.guessParametersErrors();
		} catch (Exception fittingError) {
			throw new DatasetException(excMessage, fittingError);
		}

		if (opt.hasErrors() && errors == null) {
			throw new DatasetException(excMessage);
		}
		return residual;
	}
}

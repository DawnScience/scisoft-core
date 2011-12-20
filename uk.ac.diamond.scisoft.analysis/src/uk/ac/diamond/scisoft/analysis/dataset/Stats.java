/*
 * Copyright © 2011 Diamond Light Source Ltd.
 * Contact :  ScientificSoftware@diamond.ac.uk
 * 
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 * 
 * This software is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this software. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.dataset;

import org.apache.commons.math.complex.Complex;
import org.apache.commons.math.stat.descriptive.moment.FourthMoment;
import org.apache.commons.math.stat.descriptive.moment.Kurtosis;
import org.apache.commons.math.stat.descriptive.moment.Skewness;


/**
 * Statistics class
 */
public class Stats {
	// calculates statistics and returns sorted dataset (0th element if compound)
	private static AbstractDataset calcQuartileStats(final AbstractDataset a) {
		AbstractDataset s = null;
		final int is = a.getElementsPerItem();

		if (is == 1) {
			s = DatasetUtils.sort(a, null);
		
			a.setStoredValue("median", new Double(pQuantile(s, 0.5)));
			a.setStoredValue("quartile1", new Double(pQuantile(s, 0.25)));
			a.setStoredValue("quartile3", new Double(pQuantile(s, 0.75)));
		} else {
			AbstractDataset w = AbstractDataset.zeros(a.shape, a.getDtype());
			a.setStoredValue("median", new double[is]);
			a.setStoredValue("quartile1", new double[is]);
			a.setStoredValue("quartile3", new double[is]);
			for (int j = 0; j < is; j++) {
				((AbstractCompoundDataset) a).copyElements(w, j);
				w.sort(null);

				double[] store;
				store = (double[]) a.getStoredValue("median");
				store[j] = pQuantile(w, 0.5);
				store = (double[]) a.getStoredValue("quartile1");
				store[j] = pQuantile(w, 0.25);
				store = (double[]) a.getStoredValue("quartile3");
				store[j] = pQuantile(w, 0.75);
				if (j == 0)
					s = w.clone();
			}
		}
		return s;
	}

	static private Object getQStatistics(final AbstractDataset a, String stat) {
		Object m = a.getStoredValue(stat);
		if (m == null) {
			calcQuartileStats(a);
			m = a.getStoredValue(stat);
		}
		return m;
	}

	static private AbstractDataset getQStatistics(final AbstractDataset a, int axis, String stat) {
		axis = a.checkAxis(axis);
		Object obj = a.getStoredValue(stat);
		final int is = a.getElementsPerItem();

		if (obj == null) {
			if (is == 1) {
				AbstractDataset s = DatasetUtils.sort(a, axis);

				a.setStoredValue("median-" + axis, pQuantile(s, axis, 0.5));
				a.setStoredValue("quartile1-" + axis, pQuantile(s, axis, 0.25));
				a.setStoredValue("quartile3-" + axis, pQuantile(s, axis, 0.75));
			} else {
				AbstractDataset w = AbstractDataset.zeros(a.shape, a.getDtype());
				for (int j = 0; j < is; j++) {
					((AbstractCompoundDataset) a).copyElements(w, j);
					w.sort(axis);

					AbstractDataset c;
					CompoundDoubleDataset s;

					c = pQuantile(w, axis, 0.5);
					if (j == 0) {
						s = (CompoundDoubleDataset) AbstractDataset.zeros(is, c.shape, c.getDtype());
						a.setStoredValue("median-" + axis, c);
						s = (CompoundDoubleDataset) AbstractDataset.zeros(is, c.shape, c.getDtype());
						a.setStoredValue("quartile1-" + axis, c);
						s = (CompoundDoubleDataset) AbstractDataset.zeros(is, c.shape, c.getDtype());
						a.setStoredValue("quartile3-" + axis, c);
					}
					s = (CompoundDoubleDataset) a.getStoredValue("median-" + axis);
					s.setElements(c, j);

					c = pQuantile(w, axis, 0.25);
					s = (CompoundDoubleDataset) a.getStoredValue("quartile1-" + axis);
					s.setElements(c, j);

					c = pQuantile(w, axis, 0.75);
					s = (CompoundDoubleDataset) a.getStoredValue("quartile3-" + axis);
					s.setElements(c, j);
				}
			}
			obj = a.getStoredValue(stat);
		}

		return (AbstractDataset) obj;
	}

	// process a sorted dataset
	private static double pQuantile(final AbstractDataset s, final double q) {
		double f = (s.size - 1) * q; // fraction of sample number
		int qpt = (int) Math.floor(f); // quantile point
		f -= qpt;

		double quantile = s.getElementDoubleAbs(qpt);
		if (f > 0) {
			quantile = (1-f)*quantile + f*s.getElementDoubleAbs(qpt+1);
		}
		return quantile;
	}

	// process a sorted dataset and returns a double or compound double dataset
	private static AbstractDataset pQuantile(final AbstractDataset s, final int axis, final double q) {
		final int rank = s.getRank();
		final int is = s.getElementsPerItem();

		int[] oshape = s.getShape();

		double f = (oshape[axis] - 1) * q; // fraction of sample number
		int qpt = (int) Math.floor(f); // quantile point
		f -= qpt;

		oshape[axis] = 1;
		int[] qshape = AbstractDataset.squeezeShape(oshape, false);
		AbstractDataset qds = AbstractDataset.zeros(is, qshape, AbstractDataset.FLOAT64);

		IndexIterator qiter = qds.getIterator(true);
		int[] qpos = qiter.getPos();
		int[] spos = oshape;

		while (qiter.hasNext()) {
			int i = 0;
			for (; i < axis; i++) {
				spos[i] = qpos[i];
			}
			spos[i++] = qpt;
			for (; i < rank; i++) {
				spos[i] = qpos[i-1];
			}

			Object obj = s.getObject(spos);
			qds.set(obj, qpos);
		}

		if (f > 0) {
			qiter = qds.getIterator(true);
			qpos = qiter.getPos();
			qpt++;
			AbstractDataset rds = AbstractDataset.zeros(is, qshape, AbstractDataset.FLOAT64);
			
			while (qiter.hasNext()) {
				int i = 0;
				for (; i < axis; i++) {
					spos[i] = qpos[i];
				}
				spos[i++] = qpt;
				for (; i < rank; i++) {
					spos[i] = qpos[i-1];
				}

				Object obj = s.getObject(spos);
				rds.set(obj, qpos);
			}
			rds.imultiply(f);
			qds.imultiply(1-f);
			qds.iadd(rds);
		}

		return qds;
	}

	/**
	 * Calculate quantile of dataset which is defined as the inverse of the cumulative distribution function (CDF)
	 * @param a
	 * @param q
	 * @return point at which CDF has value q
	 */
	public static double quantile(AbstractDataset a, double q) {
		if (q < 0 || q > 1) {
			throw new IllegalArgumentException("Quantile requested is outside [0,1]");
		}
		final AbstractDataset s = calcQuartileStats(a);
		return pQuantile(s, q);
	}

	/**
	 * Calculate quantiles of dataset which is defined as the inverse of the cumulative distribution function (CDF)
	 * @param a
	 * @param values
	 * @return points at which CDF has given values
	 */
	public static double[] quantile(AbstractDataset a, double... values) {
		final double[] points  = new double[values.length];
		for (int i = 0; i < points.length; i++) {
			final double q = values[i];
			if (q < 0 || q > 1) {
				throw new IllegalArgumentException("Quantile requested is outside [0,1]");
			}
			final AbstractDataset s = calcQuartileStats(a);
			points[i] = pQuantile(s, q);
		}
		return points;
	}

	/**
	 * @param a dataset
	 * @param axis
	 * @return median
	 */
	public static AbstractDataset median(final AbstractDataset a, int axis) {
		return getQStatistics(a, axis, "median-" + axis);
	}

	/**
	 * @param a dataset
	 * @return median
	 */
	public static Object median(final AbstractDataset a) {
		return getQStatistics(a, "median");
	}

	/**
	 * Interquartile range: Q3 - Q1
	 * @param a
	 * @return range
	 */
	public static Object iqr(final AbstractDataset a) {
		final int is = a.getElementsPerItem();
		if (is == 1) {
			double q3 = ((Double) getQStatistics(a, "quartile3"));
			return new Double(q3 - ((Double) a.getStoredValue("quartile1")).doubleValue());
		}

		double[] q1 = (double[]) getQStatistics(a, "quartile1");
		double[] q3 = (double[]) getQStatistics(a, "quartile3");
		for (int j = 0; j < is; j++) {
			q3[j] -= q1[j];
		}
		return q3;
	}

	/**
	 * Interquartile range: Q3 - Q1
	 * @param a
	 * @param axis
	 * @return range
	 */
	public static AbstractDataset iqr(final AbstractDataset a, int axis) {
		AbstractDataset q3 = getQStatistics(a, axis, "quartile3-" + axis);

		return Maths.subtract(q3, a.getStoredValue("quartile1-" + axis));
	}

	static private Object getFourthMoment(final AbstractDataset a) {
		Object obj = a.getStoredValue("moment4");
		if (obj == null) {
			final int is = a.getElementsPerItem();
			final IndexIterator iter = a.getIterator();

			if (is == 1) {
				FourthMoment moment = new FourthMoment();

				while (iter.hasNext()) {
					moment.increment(a.getElementDoubleAbs(iter.index));
				}
				a.setStoredValue("moment4", moment);
			} else {
				FourthMoment[] moments = new FourthMoment[is];

				for (int j = 0; j < is; j++) {
					moments[j] = new FourthMoment();
				}
				while (iter.hasNext()) {
					for (int j = 0; j < is; j++)
						moments[j].increment(a.getElementDoubleAbs(iter.index + j));
				}
				a.setStoredValue("moment4", moments);
			}
			obj = a.getStoredValue("moment4");
		}

		return obj;
	}

	/**
	 * @param a dataset
	 * @return skewness
	 */
	public static Object skewness(final AbstractDataset a) {
		Object m = getFourthMoment(a);
		if (m instanceof FourthMoment) {
			return new Double((new Skewness((FourthMoment) m)).getResult());
		}

		FourthMoment[] mos = (FourthMoment[]) m;
		final int is = mos.length;
		double[] skews = new double[is];
		for (int j = 0; j < is; j++) {
			skews[j] = (new Skewness(mos[j])).getResult();
		}
		return skews;
	}

	/**
	 * @param a dataset
	 * @return kurtosis
	 */
	public static Object kurtosis(final AbstractDataset a) {
		Object m = getFourthMoment(a);
		if (m instanceof FourthMoment) {
			return new Double((new Kurtosis((FourthMoment) m)).getResult());
		}

		FourthMoment[] mos = (FourthMoment[]) m;
		final int is = mos.length;
		double[] kurts = new double[is];
		for (int j = 0; j < is; j++) {
			kurts[j] = (new Kurtosis(mos[j])).getResult();
		}
		return kurts;
	}

	static private void calculateHigherMoments(final AbstractDataset a, int axis) {
		int rank = a.getRank();

		int[] oshape = a.getShape();
		int alen = oshape[axis];
		oshape[axis] = 1;

		int[] nshape = AbstractDataset.squeezeShape(oshape, false);
		DoubleDataset sk = new DoubleDataset(nshape);
		DoubleDataset ku = new DoubleDataset(nshape);

		IndexIterator qiter = sk.getIterator(true);
		int[] qpos = qiter.getPos();
		int[] spos = oshape;

		while (qiter.hasNext()) {
			int i = 0;
			for (; i < axis; i++) {
				spos[i] = qpos[i];
			}
			spos[i++] = 0;
			for (; i < rank; i++) {
				spos[i] = qpos[i-1];
			}

			FourthMoment moment = new FourthMoment();
			for (int j = 0; j < alen; j++) {
				spos[axis] = j;
				final double val = a.getDouble(spos);
				if (Double.isInfinite(val) || Double.isNaN(val))
					continue;

				moment.increment(val);
			}
			sk.set((new Skewness(moment)).getResult(), spos);
			ku.set((new Kurtosis(moment)).getResult(), spos);
		}
		a.setStoredValue("skewness-"+axis, sk);
		a.setStoredValue("kurtosis-"+axis, ku);
	}

	static private DoubleDataset getHigherStatistic(final AbstractDataset a, int axis, String stat) {
		axis = a.checkAxis(axis);

		DoubleDataset obj = (DoubleDataset) a.getStoredValue(stat);
		if (obj == null) {
			calculateHigherMoments(a, axis);
			obj = (DoubleDataset) a.getStoredValue(stat);
		}

		return obj;
	}

	/**
	 * @param a dataset
	 * @param axis
	 * @return skewness
	 */
	public static AbstractDataset skewness(final AbstractDataset a, int axis) {
		return getHigherStatistic(a, axis, "skewness-" + axis);
	}

	/**
	 * @param a dataset
	 * @param axis
	 * @return kurtosis
	 */
	public static AbstractDataset kurtosis(final AbstractDataset a, int axis) {
		return getHigherStatistic(a, axis, "kurtosis-" + axis);
	}

	/**
	 * @param a
	 * @return product of all items in dataset
	 */
	public static Object product(final AbstractDataset a) {
		int dtype = a.getDtype();

		if (a.isComplex()) {
			IndexIterator it = a.getIterator();
			double rv = 1, iv = 0;

			while (it.hasNext()) {
				final double r1 = a.getElementDoubleAbs(it.index);
				final double i1 = a.getElementDoubleAbs(it.index + 1);
				final double tv = r1*rv - i1*iv;
				iv = r1*iv + i1*rv;
				rv = tv;
			}

			return new Complex(rv, iv);
		}

		IndexIterator it = a.getIterator();
		final int is;
		final long[] lresults;
		final double[] dresults;

		switch (dtype) {
		case AbstractDataset.BOOL:
		case AbstractDataset.INT8: case AbstractDataset.INT16:
		case AbstractDataset.INT32: case AbstractDataset.INT64:
			long lresult = 1;
			while (it.hasNext()) {
				lresult *= a.getElementLongAbs(it.index);
			}
			return new Long(lresult);
		case AbstractDataset.ARRAYINT8: case AbstractDataset.ARRAYINT16:
		case AbstractDataset.ARRAYINT32: case AbstractDataset.ARRAYINT64:
			lresult = 1;
			is = a.getElementsPerItem();
			lresults = new long[is];
			for (int j = 0; j < is; j++) {
				lresults[j] = 1;
			}
			while (it.hasNext()) {
				for (int j = 0; j < is; j++)
					lresults[j] *= a.getElementLongAbs(it.index+j);
			}
			return lresults;
		case AbstractDataset.FLOAT32: case AbstractDataset.FLOAT64:
			double dresult = 1.;
			while (it.hasNext()) {
				dresult *= a.getElementDoubleAbs(it.index);
			}
			return new Double(dresult);
		case AbstractDataset.ARRAYFLOAT32:
		case AbstractDataset.ARRAYFLOAT64:
			is = a.getElementsPerItem();
			dresults = new double[is];
			for (int j = 0; j < is; j++) {
				dresults[j] = 1.;
			}
			while (it.hasNext()) {
				for (int j = 0; j < is; j++)
					dresults[j] *= a.getElementDoubleAbs(it.index+j);
			}
			return dresults;
		}

		return null;
	}

	/**
	 * @param a
	 * @param axis
	 * @return product of items along axis in dataset
	 */
	public static AbstractDataset product(final AbstractDataset a, int axis) {
		axis = a.checkAxis(axis);
		final int dtype = a.getDtype();
		final int rank = a.getRank();
		final int[] oshape = a.getShape();
		final int is = a.getElementsPerItem();
		final int alen = oshape[axis];
		oshape[axis] = 1;

		int[] nshape = AbstractDataset.squeezeShape(oshape, false);

		AbstractDataset result = AbstractDataset.zeros(is, nshape, dtype);

		IndexIterator qiter = result.getIterator(true);
		int[] qpos = qiter.getPos();
		int[] spos = oshape;

		while (qiter.hasNext()) {
			int i = 0;
			for (; i < axis; i++) {
				spos[i] = qpos[i];
			}
			spos[i++] = 0;
			for (; i < rank; i++) {
				spos[i] = qpos[i-1];
			}

			if (a.isComplex()) {
				double rv = 1, iv = 0;
				switch (dtype) {
				case AbstractDataset.COMPLEX64:
					ComplexFloatDataset af = (ComplexFloatDataset) a;
					for (int j = 0; j < alen; j++) {
						spos[axis] = j;
						final float r1 = af.getReal(spos);
						final float i1 = af.getImag(spos);
						final double tv = r1*rv - i1*iv;
						iv = r1*iv + i1*rv;
						rv = tv;
					}
					break;
				case AbstractDataset.COMPLEX128:
					ComplexDoubleDataset ad = (ComplexDoubleDataset) a;
					for (int j = 0; j < alen; j++) {
						spos[axis] = j;
						final double r1 = ad.getReal(spos);
						final double i1 = ad.getImag(spos);
						final double tv = r1*rv - i1*iv;
						iv = r1*iv + i1*rv;
						rv = tv;
					}
					break;
				}

				result.set(new Complex(rv, iv), qpos);
			} else {
				final long[] lresults;
				final double[] dresults;

				switch (dtype) {
				case AbstractDataset.BOOL:
				case AbstractDataset.INT8: case AbstractDataset.INT16:
				case AbstractDataset.INT32: case AbstractDataset.INT64:
					long lresult = 1;
					for (int j = 0; j < alen; j++) {
						spos[axis] = j;
						lresult *= a.getInt(spos);
					}
					result.set(lresult, qpos);
					break;
				case AbstractDataset.ARRAYINT8:
					lresults = new long[is];
					for (int k = 0; k < is; k++) {
						lresults[k] = 1;
					}
					for (int j = 0; j < alen; j++) {
						spos[axis] = j;
						final byte[] va = (byte[]) a.getObject(spos);
						for (int k = 0; k < is; k++) {
							lresults[k] *= va[k];
						}
					}
					result.set(lresults, qpos);
					break;
				case AbstractDataset.ARRAYINT16:
					lresults = new long[is];
					for (int k = 0; k < is; k++) {
						lresults[k] = 1;
					}
					for (int j = 0; j < alen; j++) {
						spos[axis] = j;
						final short[] va = (short[]) a.getObject(spos);
						for (int k = 0; k < is; k++) {
							lresults[k] *= va[k];
						}
					}
					result.set(lresults, qpos);
					break;
				case AbstractDataset.ARRAYINT32:
					lresults = new long[is];
					for (int k = 0; k < is; k++) {
						lresults[k] = 1;
					}
					for (int j = 0; j < alen; j++) {
						spos[axis] = j;
						final int[] va = (int[]) a.getObject(spos);
						for (int k = 0; k < is; k++) {
							lresults[k] *= va[k];
						}
					}
					result.set(lresults, qpos);
					break;
				case AbstractDataset.ARRAYINT64:
					lresults = new long[is];
					for (int k = 0; k < is; k++) {
						lresults[k] = 1;
					}
					for (int j = 0; j < alen; j++) {
						spos[axis] = j;
						final long[] va = (long[]) a.getObject(spos);
						for (int k = 0; k < is; k++) {
							lresults[k] *= va[k];
						}
					}
					result.set(lresults, qpos);
					break;
				case AbstractDataset.FLOAT32: case AbstractDataset.FLOAT64:
					double dresult = 1.;
					for (int j = 0; j < alen; j++) {
						spos[axis] = j;
						dresult *= a.getDouble(spos);
					}
					result.set(dresult, qpos);
					break;
				case AbstractDataset.ARRAYFLOAT32:
					dresults = new double[is];
					for (int k = 0; k < is; k++) {
						dresults[k] = 1.;
					}
					for (int j = 0; j < alen; j++) {
						spos[axis] = j;
						final float[] va = (float[]) a.getObject(spos);
						for (int k = 0; k < is; k++) {
							dresults[k] *= va[k];
						}
					}
					result.set(dresults, qpos);
					break;
				case AbstractDataset.ARRAYFLOAT64:
					dresults = new double[is];
					for (int k = 0; k < is; k++) {
						dresults[k] = 1.;
					}
					for (int j = 0; j < alen; j++) {
						spos[axis] = j;
						final double[] va = (double[]) a.getObject(spos);
						for (int k = 0; k < is; k++) {
							dresults[k] *= va[k];
						}
					}
					result.set(dresults, qpos);
					break;
				}
			}
		}

		return result;
	}

	/**
	 * @param a
	 * @return cumulative product of items in flattened dataset
	 */
	public static AbstractDataset cumulativeProduct(final AbstractDataset a) {
		return cumulativeProduct(a.flatten(), 0);
	}

	/**
	 * @param a
	 * @param axis
	 * @return cumulative product of items along axis in dataset
	 */
	public static AbstractDataset cumulativeProduct(final AbstractDataset a, int axis) {
		axis = a.checkAxis(axis);
		int dtype = a.getDtype();
		int[] oshape = a.getShape();
		int alen = oshape[axis];
		oshape[axis] = 1;

		AbstractDataset result = AbstractDataset.zeros(a);
		PositionIterator pi = result.getPositionIterator(axis);

		int[] pos = pi.getPos();

		while (pi.hasNext()) {

			if (a.isComplex()) {
				double rv = 1, iv = 0;
				switch (dtype) {
				case AbstractDataset.COMPLEX64:
					ComplexFloatDataset af = (ComplexFloatDataset) a;
					for (int j = 0; j < alen; j++) {
						pos[axis] = j;
						final float r1 = af.getReal(pos);
						final float i1 = af.getImag(pos);
						final double tv = r1*rv - i1*iv;
						iv = r1*iv + i1*rv;
						rv = tv;
						af.set((float) rv, (float) iv, pos);
					}
					break;
				case AbstractDataset.COMPLEX128:
					ComplexDoubleDataset ad = (ComplexDoubleDataset) a;
					for (int j = 0; j < alen; j++) {
						pos[axis] = j;
						final double r1 = ad.getReal(pos);
						final double i1 = ad.getImag(pos);
						final double tv = r1*rv - i1*iv;
						iv = r1*iv + i1*rv;
						rv = tv;
						ad.set(rv, iv, pos);
					}
					break;
				}

				result.set(new Complex(rv, iv), pos);
			} else {
				final int is;
				final long[] lresults;
				final double[] dresults;

				switch (dtype) {
				case AbstractDataset.BOOL:
				case AbstractDataset.INT8: case AbstractDataset.INT16:
				case AbstractDataset.INT32: case AbstractDataset.INT64:
					long lresult = 1;
					for (int j = 0; j < alen; j++) {
						pos[axis] = j;
						lresult *= a.getInt(pos);
						result.set(lresult, pos);
					}
					break;
				case AbstractDataset.ARRAYINT8:
					is = a.getElementsPerItem();
					lresults = new long[is];
					for (int k = 0; k < is; k++) {
						lresults[k] = 1;
					}
					for (int j = 0; j < alen; j++) {
						pos[axis] = j;
						final byte[] va = (byte[]) a.getObject(pos);
						for (int k = 0; k < is; k++) {
							lresults[k] *= va[k];
						}
						result.set(lresults, pos);
					}
					break;
				case AbstractDataset.ARRAYINT16:
					is = a.getElementsPerItem();
					lresults = new long[is];
					for (int k = 0; k < is; k++) {
						lresults[k] = 1;
					}
					for (int j = 0; j < alen; j++) {
						pos[axis] = j;
						final short[] va = (short[]) a.getObject(pos);
						for (int k = 0; k < is; k++) {
							lresults[k] *= va[k];
						}
						result.set(lresults, pos);
					}
					break;
				case AbstractDataset.ARRAYINT32:
					is = a.getElementsPerItem();
					lresults = new long[is];
					for (int k = 0; k < is; k++) {
						lresults[k] = 1;
					}
					for (int j = 0; j < alen; j++) {
						pos[axis] = j;
						final int[] va = (int[]) a.getObject(pos);
						for (int k = 0; k < is; k++) {
							lresults[k] *= va[k];
						}
						result.set(lresults, pos);
					}
					break;
				case AbstractDataset.ARRAYINT64:
					is = a.getElementsPerItem();
					lresults = new long[is];
					for (int k = 0; k < is; k++) {
						lresults[k] = 1;
					}
					for (int j = 0; j < alen; j++) {
						pos[axis] = j;
						final long[] va = (long[]) a.getObject(pos);
						for (int k = 0; k < is; k++) {
							lresults[k] *= va[k];
						}
						result.set(lresults, pos);
					}
					break;
				case AbstractDataset.FLOAT32: case AbstractDataset.FLOAT64:
					double dresult = 1.;
					for (int j = 0; j < alen; j++) {
						pos[axis] = j;
						dresult *= a.getDouble(pos);
						result.set(dresult, pos);
					}
					break;
				case AbstractDataset.ARRAYFLOAT32:
					is = a.getElementsPerItem();
					dresults = new double[is];
					for (int k = 0; k < is; k++) {
						dresults[k] = 1.;
					}
					for (int j = 0; j < alen; j++) {
						pos[axis] = j;
						final float[] va = (float[]) a.getObject(pos);
						for (int k = 0; k < is; k++) {
							dresults[k] *= va[k];
						}
						result.set(dresults, pos);
					}
					break;
				case AbstractDataset.ARRAYFLOAT64:
					is = a.getElementsPerItem();
					dresults = new double[is];
					for (int k = 0; k < is; k++) {
						dresults[k] = 1.;
					}
					for (int j = 0; j < alen; j++) {
						pos[axis] = j;
						final double[] va = (double[]) a.getObject(pos);
						for (int k = 0; k < is; k++) {
							dresults[k] *= va[k];
						}
						result.set(dresults, pos);
					}
					break;
				}
			}
		}

		return result;
	}

	/**
	 * @param a
	 * @return cumulative sum of items in flattened dataset
	 */
	public static AbstractDataset cumulativeSum(final AbstractDataset a) {
		return cumulativeSum(a.flatten(), 0);
	}

	/**
	 * @param a
	 * @param axis
	 * @return cumulative sum of items along axis in dataset
	 */
	public static AbstractDataset cumulativeSum(final AbstractDataset a, int axis) {
		axis = a.checkAxis(axis);
		int dtype = a.getDtype();
		int[] oshape = a.getShape();
		int alen = oshape[axis];
		oshape[axis] = 1;

		AbstractDataset result = AbstractDataset.zeros(a);
		PositionIterator pi = result.getPositionIterator(axis);

		int[] pos = pi.getPos();

		while (pi.hasNext()) {

			if (a.isComplex()) {
				double rv = 0, iv = 0;
				switch (dtype) {
				case AbstractDataset.COMPLEX64:
					ComplexFloatDataset af = (ComplexFloatDataset) a;
					for (int j = 0; j < alen; j++) {
						pos[axis] = j;
						rv += af.getReal(pos);
						iv += af.getImag(pos);
						af.set((float) rv, (float) iv, pos);
					}
					break;
				case AbstractDataset.COMPLEX128:
					ComplexDoubleDataset ad = (ComplexDoubleDataset) a;
					for (int j = 0; j < alen; j++) {
						pos[axis] = j;
						rv += ad.getReal(pos);
						iv += ad.getImag(pos);
						ad.set(rv, iv, pos);
					}
					break;
				}

				result.set(new Complex(rv, iv), pos);
			} else {
				final int is;
				final long[] lresults;
				final double[] dresults;

				switch (dtype) {
				case AbstractDataset.BOOL:
				case AbstractDataset.INT8: case AbstractDataset.INT16:
				case AbstractDataset.INT32: case AbstractDataset.INT64:
					long lresult = 0;
					for (int j = 0; j < alen; j++) {
						pos[axis] = j;
						lresult += a.getInt(pos);
						result.set(lresult, pos);
					}
					break;
				case AbstractDataset.ARRAYINT8:
					is = a.getElementsPerItem();
					lresults = new long[is];
					for (int j = 0; j < alen; j++) {
						pos[axis] = j;
						final byte[] va = (byte[]) a.getObject(pos);
						for (int k = 0; k < is; k++) {
							lresults[k] += va[k];
						}
						result.set(lresults, pos);
					}
					break;
				case AbstractDataset.ARRAYINT16:
					is = a.getElementsPerItem();
					lresults = new long[is];
					for (int j = 0; j < alen; j++) {
						pos[axis] = j;
						final short[] va = (short[]) a.getObject(pos);
						for (int k = 0; k < is; k++) {
							lresults[k] += va[k];
						}
						result.set(lresults, pos);
					}
					break;
				case AbstractDataset.ARRAYINT32:
					is = a.getElementsPerItem();
					lresults = new long[is];
					for (int j = 0; j < alen; j++) {
						pos[axis] = j;
						final int[] va = (int[]) a.getObject(pos);
						for (int k = 0; k < is; k++) {
							lresults[k] += va[k];
						}
						result.set(lresults, pos);
					}
					break;
				case AbstractDataset.ARRAYINT64:
					is = a.getElementsPerItem();
					lresults = new long[is];
					for (int j = 0; j < alen; j++) {
						pos[axis] = j;
						final long[] va = (long[]) a.getObject(pos);
						for (int k = 0; k < is; k++) {
							lresults[k] += va[k];
						}
						result.set(lresults, pos);
					}
					break;
				case AbstractDataset.FLOAT32: case AbstractDataset.FLOAT64:
					double dresult = 0.;
					for (int j = 0; j < alen; j++) {
						pos[axis] = j;
						dresult += a.getDouble(pos);
						result.set(dresult, pos);
					}
					break;
				case AbstractDataset.ARRAYFLOAT32:
					is = a.getElementsPerItem();
					dresults = new double[is];
					for (int j = 0; j < alen; j++) {
						pos[axis] = j;
						final float[] va = (float[]) a.getObject(pos);
						for (int k = 0; k < is; k++) {
							dresults[k] += va[k];
						}
						result.set(dresults, pos);
					}
					break;
				case AbstractDataset.ARRAYFLOAT64:
					is = a.getElementsPerItem();
					dresults = new double[is];
					for (int j = 0; j < alen; j++) {
						pos[axis] = j;
						final double[] va = (double[]) a.getObject(pos);
						for (int k = 0; k < is; k++) {
							dresults[k] += va[k];
						}
						result.set(dresults, pos);
					}
					break;
				}
			}
		}

		return result;
	}

	/**
	 * @param a
	 * @return average deviation value of all items the dataset
	 */
	public static Object averageDeviation(final AbstractDataset a) {
		final IndexIterator it = a.getIterator();
		final int is = a.getElementsPerItem();

		if (is == 1) {
			double mean = (Double) a.mean();
			double sum = 0.0;

			while (it.hasNext()) {
				sum += Math.abs(a.getElementDoubleAbs(it.index) - mean);
			}

			return sum / a.getSize();
		}

		double[] means = (double[]) a.mean();
		double[] sums = new double[is];

		while (it.hasNext()) {
			for (int j = 0; j < is; j++)
				sums[j] += Math.abs(a.getElementDoubleAbs(it.index + j) - means[j]);
		}

		double n = a.getSize();
		for (int j = 0; j < is; j++)
			sums[j] /= n;

		return sums;
	}

	/**
	 * The residual is the sum of squared differences
	 * @param a
	 * @param b
	 * @return residual value
	 */
	public static double residual(final AbstractDataset a, final AbstractDataset b) {
		return a.residual(b);
	}

}

/*
 * Copyright 2011 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.diamond.scisoft.analysis.dataset;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.math.complex.Complex;
import org.apache.commons.math.stat.descriptive.SummaryStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Generic container class for data that is compound in nature
 * 
 * Each subclass has an array of compound types, items of this array are composed of primitive types
 * 
 * Data items can be Complex, Vector, etc
 * 
 */
public abstract class AbstractCompoundDataset extends AbstractDataset {
	/**
	 * Setup the logging facilities
	 */
	transient private static final Logger abstractCompoundLogger = LoggerFactory.getLogger(AbstractCompoundDataset.class);

	protected int isize; // number of elements per item

	@Override
	public int getElementsPerItem() {
		return isize;
	}

	@Override
	public IndexIterator getIterator(final boolean withPosition) {
		if (shape.length <= 1 || dataShape == null) {
			return (withPosition) ? getSliceIterator(null, null, null) :
			//new ContiguousIteratorWithPosition(shape, size) :
				new ContiguousIterator(size, isize);
		}
		return new DiscontiguousIterator(shape, dataShape, dataSize, isize);
//		return getSliceIterator(null, null, null); // alternative way (probably a little slower)
	}

	/**
	 * Get an iterator that picks out the chosen element from all items
	 * @param element
	 * @return an iterator
	 */
	public IndexIterator getIterator(int element) {
		if (element < 0)
			element += isize;
		if (element < 0 || element > isize) {
			abstractCompoundLogger.error("Invalid choice of element: {}/{}", element, isize);
			throw new IllegalArgumentException("Invalid choice of element: " + element + "/" + isize);
		}
		final IndexIterator it = (shape.length <= 1 || dataShape == null) ? new ContiguousIterator(size, isize) :
			new DiscontiguousIterator(shape, dataShape, dataSize, isize);

		it.index += element;
		return it;
	}

	@Override
	public IndexIterator getSliceIterator(final int[] start, final int[] stop, final int[] step) {
		int rank = shape.length;

		int[] lstart, lstop, lstep;

		if (step == null) {
			lstep = new int[rank];
			for (int i = 0; i < rank; i++) {
				lstep[i] = 1;
			}
		} else {
			lstep = step;
		}

		if (start == null) {
			lstart = new int[rank];
		} else {
			lstart = start;
		}

		if (stop == null) {
			lstop = new int[rank];
		} else {
			lstop = stop;
		}

		int[] newShape;
		if (rank > 1 || (rank > 0 && shape[0] > 0)) {
			newShape = checkSlice(start, stop, lstart, lstop, lstep);
		} else {
			newShape = new int[rank];
		}

		if (rank <= 1 || dataShape == null)
			return new SliceIterator(shape, size, lstart, lstep, newShape, isize);

		return new SliceIterator(dataShape, dataSize, lstart, lstep, newShape, isize);
	}

	/**
	 * Constructor required for serialisation.
	 */
	public AbstractCompoundDataset() {
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		AbstractCompoundDataset other = (AbstractCompoundDataset) obj;
		return isize == other.isize;
	}

	@Override
	public int hashCode() {
		return getHash();
	}

	protected static double[] toDoubleArray(final Object b, final int itemSize) {
		double[] result = null;

		// ensure array is of given length
		if (b instanceof double[]) {
			result = (double[]) b;
			if (result.length < itemSize) {
				result = new double[itemSize];
				int ilen = result.length;
				for (int i = 0; i < ilen; i++)
					result[i] = ((double[]) b)[i];
			}
		} else if (b instanceof List<?>) {
			result = new double[itemSize];
			List<?> jl = (List<?>) b;
			int ilen = jl.size();
			if (ilen > 0 && !(jl.get(0) instanceof Number)) {
				abstractCompoundLogger.error("Given array was not of a numerical primitive type");
				throw new IllegalArgumentException("Given array was not of a numerical primitive type");
			}
			ilen = Math.min(itemSize, ilen);
			for (int i = 0; i < ilen; i++) {
				result[i] = toReal(jl.get(i));
			}
		} else if (b.getClass().isArray()) {
			result = new double[itemSize];
			int ilen = Array.getLength(b);
			if (ilen > 0 && !(Array.get(b, 0) instanceof Number)) {
				abstractCompoundLogger.error("Given array was not of a numerical primitive type");
				throw new IllegalArgumentException("Given array was not of a numerical primitive type");
			}
			ilen = Math.min(itemSize, ilen);
			for (int i = 0; i < ilen; i++)
				result[i] = ((Number) Array.get(b, i)).doubleValue();
		} else if (b instanceof Complex) {
			if (itemSize > 2) {
				abstractCompoundLogger.error("Complex number will not fit in compound dataset");
				throw new IllegalArgumentException("Complex number will not fit in compound dataset");
			}
			switch (itemSize) {
			default:
			case 0:
				break;
			case 1:
				result = new double[] {((Complex) b).getReal()};
				break;
			case 2:
				result = new double[] {((Complex) b).getReal(), ((Complex) b).getImaginary()};
				break;
			}
		} else if (b instanceof Number) {
			result = new double[itemSize];
			double val = ((Number) b).doubleValue();
			for (int i = 0; i < itemSize; i++)
				result[i] = val;
		}

		return result;
	}

	protected static float[] toFloatArray(final Object b, final int itemSize) {
		float[] result = null;

		if (b instanceof float[]) {
			result = (float[]) b;
			if (result.length < itemSize) {
				result = new float[itemSize];
				int ilen = result.length;
				for (int i = 0; i < ilen; i++)
					result[i] = ((float[]) b)[i];
			}
		} else if (b instanceof List<?>) {
			result = new float[itemSize];
			List<?> jl = (List<?>) b;
			int ilen = jl.size();
			if (ilen > 0 && !(jl.get(0) instanceof Number)) {
				abstractCompoundLogger.error("Given array was not of a numerical primitive type");
				throw new IllegalArgumentException("Given array was not of a numerical primitive type");
			}
			ilen = Math.min(itemSize, ilen);
			for (int i = 0; i < ilen; i++) {
				result[i] = (float) toReal(jl.get(i));
			}
		} else if (b.getClass().isArray()) {
			result = new float[itemSize];
			int ilen = Array.getLength(b);
			if (ilen > 0 && !(Array.get(b, 0) instanceof Number)) {
				abstractCompoundLogger.error("Given array was not of a numerical primitive type");
				throw new IllegalArgumentException("Given array was not of a numerical primitive type");
			}
			ilen = Math.min(itemSize, ilen);
			for (int i = 0; i < ilen; i++)
				result[i] = ((Number) Array.get(b, i)).floatValue();
		} else if (b instanceof Number) {
			result = new float[itemSize];
			float val = ((Number) b).floatValue();
			for (int i = 0; i < itemSize; i++)
				result[i] = val;
		}

		return result;
	}

	protected static long[] toLongArray(final Object b, final int itemSize) {
		long[] result = null;

		if (b instanceof long[]) {
			result = (long[]) b;
			if (result.length < itemSize) {
				result = new long[itemSize];
				int ilen = result.length;
				for (int i = 0; i < ilen; i++)
					result[i] = ((long[]) b)[i];
			}
		} else if (b instanceof List<?>) {
			result = new long[itemSize];
			List<?> jl = (List<?>) b;
			int ilen = jl.size();
			if (ilen > 0 && !(jl.get(0) instanceof Number)) {
				abstractCompoundLogger.error("Given array was not of a numerical primitive type");
				throw new IllegalArgumentException("Given array was not of a numerical primitive type");
			}
			ilen = Math.min(itemSize, ilen);
			for (int i = 0; i < ilen; i++) {
				result[i] = toLong(jl.get(i));
			}
		} else if (b.getClass().isArray()) {
			result = new long[itemSize];
			int ilen = Array.getLength(b);
			if (ilen > 0 && !(Array.get(b, 0) instanceof Number)) {
				abstractCompoundLogger.error("Given array was not of a numerical primitive type");
				throw new IllegalArgumentException("Given array was not of a numerical primitive type");
			}
			ilen = Math.min(itemSize, ilen);
			for (int i = 0; i < ilen; i++)
				result[i] = ((Number) Array.get(b, i)).longValue();
		} else if (b instanceof Number) {
			result = new long[itemSize];
			long val = ((Number) b).longValue();
			for (int i = 0; i < itemSize; i++)
				result[i] = val;
		}

		return result;
	}

	protected static int[] toIntegerArray(final Object b, final int itemSize) {
		int[] result = null;

		if (b instanceof int[]) {
			result = (int[]) b;
			if (result.length < itemSize) {
				result = new int[itemSize];
				int ilen = result.length;
				for (int i = 0; i < ilen; i++)
					result[i] = ((int[]) b)[i];
			}
		} else if (b instanceof List<?>) {
			result = new int[itemSize];
			List<?> jl = (List<?>) b;
			int ilen = jl.size();
			if (ilen > 0 && !(jl.get(0) instanceof Number)) {
				abstractCompoundLogger.error("Given array was not of a numerical primitive type");
				throw new IllegalArgumentException("Given array was not of a numerical primitive type");
			}
			ilen = Math.min(itemSize, ilen);
			for (int i = 0; i < ilen; i++) {
				result[i] = (int) toLong(jl.get(i));
			}
		} else if (b.getClass().isArray()) {
			result = new int[itemSize];
			int ilen = Array.getLength(b);
			if (ilen > 0 && !(Array.get(b, 0) instanceof Number)) {
				abstractCompoundLogger.error("Given array was not of a numerical primitive type");
				throw new IllegalArgumentException("Given array was not of a numerical primitive type");
			}
			ilen = Math.min(itemSize, ilen);
			for (int i = 0; i < ilen; i++)
				result[i] = (int) ((Number) Array.get(b, i)).longValue();
		} else if (b instanceof Number) {
			result = new int[itemSize];
			int val = ((Number) b).intValue();
			for (int i = 0; i < itemSize; i++)
				result[i] = val;
		}

		return result;
	}

	protected static short[] toShortArray(final Object b, final int itemSize) {
		short[] result = null;

		if (b instanceof short[]) {
			result = (short[]) b;
			if (result.length < itemSize) {
				result = new short[itemSize];
				int ilen = result.length;
				for (int i = 0; i < ilen; i++)
					result[i] = ((short[]) b)[i];
			}
		} else if (b instanceof List<?>) {
			result = new short[itemSize];
			List<?> jl = (List<?>) b;
			int ilen = jl.size();
			if (ilen > 0 && !(jl.get(0) instanceof Number)) {
				abstractCompoundLogger.error("Given array was not of a numerical primitive type");
				throw new IllegalArgumentException("Given array was not of a numerical primitive type");
			}
			ilen = Math.min(itemSize, ilen);
			for (int i = 0; i < ilen; i++) {
				result[i] = (short) toLong(jl.get(i));
			}
		} else if (b.getClass().isArray()) {
			result = new short[itemSize];
			int ilen = Array.getLength(b);
			if (ilen > 0 && !(Array.get(b, 0) instanceof Number)) {
				abstractCompoundLogger.error("Given array was not of a numerical primitive type");
				throw new IllegalArgumentException("Given array was not of a numerical primitive type");
			}
			ilen = Math.min(itemSize, ilen);
			for (int i = 0; i < ilen; i++)
				result[i] = (short) ((Number) Array.get(b, i)).longValue();
		} else if (b instanceof Number) {
			result = new short[itemSize];
			short val = ((Number) b).shortValue();
			for (int i = 0; i < itemSize; i++)
				result[i] = val;
		}

		return result;
	}

	protected static byte[] toByteArray(final Object b, final int itemSize) {
		byte[] result = null;

		if (b instanceof byte[]) {
			result = (byte[]) b;
			if (result.length < itemSize) {
				result = new byte[itemSize];
				int ilen = result.length;
				for (int i = 0; i < ilen; i++)
					result[i] = ((byte[]) b)[i];
			}
		} else if (b instanceof List<?>) {
			result = new byte[itemSize];
			List<?> jl = (List<?>) b;
			int ilen = jl.size();
			if (ilen > 0 && !(jl.get(0) instanceof Number)) {
				abstractCompoundLogger.error("Given array was not of a numerical primitive type");
				throw new IllegalArgumentException("Given array was not of a numerical primitive type");
			}
			ilen = Math.min(itemSize, ilen);
			for (int i = 0; i < ilen; i++) {
				result[i] = (byte) toLong(jl.get(i));
			}
		} else if (b.getClass().isArray()) {
			result = new byte[itemSize];
			int ilen = Array.getLength(b);
			if (ilen > 0 && !(Array.get(b, 0) instanceof Number)) {
				abstractCompoundLogger.error("Given array was not of a numerical primitive type");
				throw new IllegalArgumentException("Given array was not of a numerical primitive type");
			}
			ilen = Math.min(itemSize, ilen);
			for (int i = 0; i < ilen; i++)
				result[i] = (byte) ((Number) Array.get(b, i)).longValue();
		} else if (b instanceof Number) {
			result = new byte[itemSize];
			final byte val = ((Number) b).byteValue();
			for (int i = 0; i < itemSize; i++)
				result[i] = val;
		}

		return result;
	}

	abstract protected double getFirstValue(final int...pos);

	@Override
	public boolean getBoolean(final int... pos) {
		return getFirstValue(pos) != 0;
	}

	@Override
	public byte getByte(final int... pos) {
		return (byte) getFirstValue(pos);
	}

	@Override
	public short getShort(final int... pos) {
		return (short) getFirstValue(pos);
	}

	@Override
	public int getInt(final int... pos) {
		return (int) getFirstValue(pos);
	}

	@Override
	public long getLong(final int... pos) {
		return (long) getFirstValue(pos);
	}

	@Override
	public float getFloat(final int... pos) {
		return (float) getFirstValue(pos);
	}

	@Override
	public double getDouble(final int... pos) {
		return getFirstValue(pos);
	}

	/**
	 * Get an item as a double array
	 * @param darray double array must be allocated and have sufficient length
	 * @param pos
	 */
	abstract public void getDoubleArray(final double[] darray, final int... pos);

	/**
	 * Get chosen elements from each item as a dataset
	 * @param element
	 * @return dataset of chosen elements
	 */
	abstract public AbstractDataset getElements(int element);

	/**
	 * Set values of chosen elements from each item according to source dataset
	 * @param source
	 * @param element
	 */
	abstract public void setElements(AbstractDataset source, int element);

	/**
	 * Copy chosen elements from each item to another dataset
	 * @param destination
	 * @param element
	 */
	abstract public void copyElements(AbstractDataset destination, int element);

	/**
	 * Gets a view of compound dataset as a non-compound dataset
	 * @return non-compound dataset
	 */
	public AbstractDataset asNonCompoundDataset() {
		return DatasetUtils.createDatasetFromCompoundDataset(this, true);
	}

	/**
	 * Calculate minimum and maximum for a dataset
	 */
	protected void calculateHash() {
		IndexIterator iter = getIterator();
		double hash = 0;
		while (iter.hasNext()) {
			for (int j = 0; j < isize; j++) {
				final double val = getElementDoubleAbs(iter.index + j);
				if (Double.isInfinite(val) || Double.isNaN(val)) {
					hash = (hash * 19) % Integer.MAX_VALUE;
				} else {
					hash = (hash * 19 + val) % Integer.MAX_VALUE;
				}
			}
		}

		int ihash = ((int) hash) * 19 + getDtype() * 17 + getElementsPerItem();
		setStoredValue(STORE_SHAPELESS_HASH, ihash);
	}

	private int getHash() {
		Object value = getStoredValue(STORE_HASH);
		if (value == null) {
			value = getStoredValue(STORE_SHAPELESS_HASH);
			if (value == null) {
				calculateHash();
				value = getStoredValue(STORE_SHAPELESS_HASH);
			}

			int ihash = (Integer) value;
			int rank = shape.length;
			for (int i = 0; i < rank; i++) {
				ihash = ihash * 17 + shape[i];
			}
			storedValues.put(STORE_HASH, ihash);
			return ihash;
		}

		return (Integer) value;
	}
	@Override
	protected void calculateSummaryStats(boolean ignoreNaNs, String name) {
		IndexIterator iter = getIterator();
		SummaryStatistics[] stats = new SummaryStatistics[isize];
		for (int i = 0; i < isize; i++)
			stats[i] = new SummaryStatistics();

		double[] vals = new double[isize];
		while (iter.hasNext()) {
			boolean okay = true;
			for (int i = 0; i < isize; i++) {
				final double val = getElementDoubleAbs(iter.index + i);
				if (Double.isNaN(val) && ignoreNaNs) {
					okay = false;
					break;
				}
				vals[i] = val;
			}
			if (!okay)
				continue;
			for (int i = 0; i < isize; i++)
				stats[i].addValue(vals[i]);
		}

		// now all the calculations are done, add the values into store
		storedValues = new HashMap<String, Object>();
		for (int i = 0; i < isize; i++)
			storedValues.put(name+i, stats[i]);
	}

	@Override
	protected void calculateSummaryStats(final boolean ignoreNaNs, final int axis) {
		int rank = getRank();

		int[] oshape = getShape();
		int alen = oshape[axis];
		oshape[axis] = 1;

		int[] nshape = AbstractDataset.squeezeShape(oshape, false);

		CompoundDoubleDataset sum = new CompoundDoubleDataset(isize, nshape);
		CompoundDoubleDataset mean = new CompoundDoubleDataset(isize, nshape);
		CompoundDoubleDataset var = new CompoundDoubleDataset(isize, nshape);

		IndexIterator qiter = sum.getIterator(true);
		int[] qpos = qiter.getPos();
		int[] spos = oshape;
		double[] darray = new double[isize];

		while (qiter.hasNext()) {
			int i = 0;
			for (; i < axis; i++) {
				spos[i] = qpos[i];
			}
			spos[i++] = 0;
			for (; i < rank; i++) {
				spos[i] = qpos[i-1];
			}

			final SummaryStatistics[] stats = new SummaryStatistics[isize];
			for (int k = 0; k < isize; k++) {
				stats[k] = new SummaryStatistics();
			}
			for (int j = 0; j < alen; j++) {
				spos[axis] = j;
				getDoubleArray(darray, spos);
				boolean skip = false;
				for (int k = 0; k < isize; k++) {
					if (Double.isNaN(darray[k]) && ignoreNaNs)
						skip = true;
					
				}
				if (!skip)
					for (int k = 0; k < isize; k++) {
						stats[k].addValue(darray[k]);
					}
			}

			for (int k = 0; k < isize; k++) {
				darray[k] = stats[k].getSum();
			}
			sum.set(darray, spos);
			for (int k = 0; k < isize; k++) {
				darray[k] = stats[k].getSum();
			}
			mean.set(darray, spos);
			for (int k = 0; k < isize; k++) {
				darray[k] = stats[k].getSum();
			}
			var.set(darray, spos);
		}
		storedValues.put(storeName(ignoreNaNs, "sum-"+axis), sum);
		storedValues.put(storeName(ignoreNaNs, "mean-"+axis), mean);
		storedValues.put(storeName(ignoreNaNs, "var-"+axis), var);
	}

	@Override
	public Number max() {
		abstractCompoundLogger.error("Cannot compare compound numbers");
		throw new UnsupportedOperationException("Cannot compare compound numbers");
	}

	@Override
	public Number min() {
		abstractCompoundLogger.error("Cannot compare compound numbers");
		throw new UnsupportedOperationException("Cannot compare compound numbers");
	}

	@Override
	public int[] maxPos(boolean ignoreNaNs) {
		abstractCompoundLogger.error("Cannot compare compound numbers");
		throw new UnsupportedOperationException("Cannot compare compound numbers");
	}

	@Override
	public int[] minPos(boolean ignoreNaNs) {
		abstractCompoundLogger.error("Cannot compare compound numbers");
		throw new UnsupportedOperationException("Cannot compare compound numbers");
	}

	protected final static String STORE_STATS_ITEM_NAME = STORE_STATS + "=";

	/**
	 * Calculate maximum values of elements over all items in dataset
	 * @return double array of element-wise maxima
	 */
	public double[] maxItem() {
		final String n = storeName(false, STORE_STATS_ITEM_NAME);
		if (storedValues == null) {
			calculateSummaryStats(false, n);
		}

		double[] results = new double[isize];
		for (int i = 0; i < isize; i++) {
			results[i] = ((SummaryStatistics) storedValues.get(n + i)).getMax();
		}
		return results;
	}

	/**
	 * Calculate minimum values of elements over all items in dataset
	 * @return double array of element-wise minima
	 */
	public double[] minItem() {
		final String n = storeName(false, STORE_STATS_ITEM_NAME);
		if (storedValues == null) {
			calculateSummaryStats(false, n);
		}

		double[] results = new double[isize];
		for (int i = 0; i < isize; i++) {
			results[i] = ((SummaryStatistics) storedValues.get(n + i)).getMin();
		}
		return results;
	}

	@Override
	public Object sum() {
		final String n = storeName(false, STORE_STATS_ITEM_NAME);
		if (storedValues == null) {
			calculateSummaryStats(false, n);
		}

		double[] results = new double[isize];
		for (int i = 0; i < isize; i++) {
			results[i] = ((SummaryStatistics) storedValues.get(n + i)).getSum();
		}
		return results;
	}

	private static Object fromDoublesToBiggestPrimitives(double[] x, int dtype) {
		switch (dtype) {
		case BOOL:
		case INT8:
		case INT16:
		case INT32:
			int[] i32 = new int[x.length];
			for (int i = 0; i < x.length; i++)
				i32[i] = (int) (long) x[i];
			return i32;
		case INT64:
			long[] i64 = new long[x.length];
			for (int i = 0; i < x.length; i++)
				i64[i] = (long) x[i];
			return i64;
		case FLOAT32:
			float[] f32 = new float[x.length];
			for (int i = 0; i < x.length; i++)
				f32[i] = (float) x[i];
			return f32;
		case FLOAT64:
			return x;
		}
		return null;
	}

	/**
	 * @param dtype
	 * @return sum over all items in dataset as array of primitives or a complex number
	 */
	@Override
	public Object typedSum(int dtype) {
		return fromDoublesToBiggestPrimitives((double[]) sum(), dtype);
	}

	@Override
	public Object mean() {
		final String n = storeName(false, STORE_STATS_ITEM_NAME);
		if (storedValues == null) {
			calculateSummaryStats(false, n);
		}

		double[] results = new double[isize];
		for (int i = 0; i < isize; i++) {
			results[i] = ((SummaryStatistics) storedValues.get(n + i)).getMean();
		}
		return results;
	}

	@Override
	public Number variance() {
		final String n = storeName(false, STORE_STATS_ITEM_NAME);
		if (storedValues == null) {
			calculateSummaryStats(false, n);
		}

		double result = 0;
		for (int i = 0; i < isize; i++)
			result += ((SummaryStatistics) storedValues.get(n + i)).getVariance();
		return result;
	}

	@Override
	public Number rootMeanSquare() {
		final String n = storeName(false, STORE_STATS_ITEM_NAME);
		if (storedValues == null) {
			calculateSummaryStats(false, n);
		}

		double result = 0;
		for (int i = 0; i < isize; i++) {
			final SummaryStatistics stats = (SummaryStatistics) storedValues.get(n + i);
			final double mean = stats.getMean();
			result += stats.getVariance() + mean*mean;
		}
		return Math.sqrt(result);
	}

	@Override
	public void setError(Serializable error) {		
		double[] errors = toDoubleArray(error, isize);

		if (errors == null) {
			super.setError(error);
		} else {
			for (int i = 0; i < isize; i++) {
				double x = errors[i];
				errors[i] = x*x;
			}
			
			errorData = errors;
		}
	}

	@Override
	public AbstractCompoundDataset getError() {
		if (errorData == null) {
			return null;
		}

		double[] e = toDoubleArray(errorData, isize);
		if (e != null) {
			if (e == errorData) {
				e = e.clone();
			}
			CompoundDoubleDataset errors = new CompoundDoubleDataset(isize, shape);
			for (int i = 0; i < isize; i++) {
				e[i] = Math.sqrt(e[i]);
			}
			errors.fill(e);
			return errors;
		}

		if (errorData instanceof CompoundDoubleDataset) {
			return (CompoundDoubleDataset) Maths.sqrt((AbstractDataset) errorData);
		}

		CompoundDoubleDataset errors = new CompoundDoubleDataset(isize, shape);
		AbstractDataset err = (AbstractDataset) errorData;
		IndexIterator it = err.getIterator();
		int i = 0;
		e = new double[isize];
		while (it.hasNext()) {
			Arrays.fill(e, Math.sqrt(err.getElementDoubleAbs(it.index)));
			errors.setAbs(i, e);
			i += isize;
		}
		return errors;
	}

	/**
	 * Gets the total error value for a single point in the dataset
	 * @param pos of the point to be referenced 
	 * @return the value of the error at this point
	 */
	@Override
	public double getError(int... pos) {
		double[] es = getSquaredErrorArray(pos);
		if (es == null)
			return 0;

		// assume elements are independent
		double e = 0;
		for (int i = 0; i < isize; i++) {
			e += es[i];
		}

		return Math.sqrt(e);
	}

	/**
	 * Gets the error values for a single point in the dataset
	 * @param pos of the point to be referenced 
	 * @return the values of the error at this point
	 */
	public double[] getErrorArray(int... pos) {
		double[] e = getSquaredErrorArray(pos);
		if (e != null) {
			for (int i = 0; i < isize; i++) {
				e[i] = Math.sqrt(e[i]);
			}
		}
		return e;
	}

	private double[] getSquaredErrorArray(int... pos) {
		if (errorData == null) {
			return null;
		}

		double[] es = toDoubleArray(errorData, isize);
		if (es == null) {
			if (errorData instanceof CompoundDoubleDataset) {
				es = ((CompoundDoubleDataset) errorData).getDoubleArray(pos);
			} else {
				es = new double[isize];
				Arrays.fill(es, ((DoubleDataset) errorData).get(pos));
			}
		}
		if (es == errorData) {
			es = es.clone();
		}
		return es;
	}
}


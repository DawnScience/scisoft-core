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

import java.lang.reflect.Array;
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
		Object value = getStoredValue("hash");
		if (value == null) {
			calculateHash();
			value = getStoredValue("hash");
		}

		return (Integer) value;
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

		int ihash = ((int) hash)*19 + getDtype()*17 + getElementsPerItem();
		int rank = shape.length;
		for (int i = 0; i < rank; i++) {
			ihash = ihash*17 + shape[i];
		}
		setStoredValue("hash", ihash);
	}

	@Override
	protected void calculateSummaryStats() {
		IndexIterator iter = getIterator();
		SummaryStatistics[] stats = new SummaryStatistics[isize];
		for (int i = 0; i < isize; i++)
			stats[i] = new SummaryStatistics();

		double[] vals = new double[isize];
		while (iter.hasNext()) {
			boolean okay = true;
			for (int i = 0; i < isize; i++) {
				final double val = getElementDoubleAbs(iter.index + i);
				if (Double.isInfinite(val) || Double.isNaN(val)) {
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
			storedValues.put("stats-"+i, stats[i]);
	}

	@Override
	protected void calculateSummaryStats(final int axis) {
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
					if (Double.isInfinite(darray[k]) || Double.isNaN(darray[k]))
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
		storedValues.put("sum-"+axis, sum);
		storedValues.put("mean-"+axis, mean);
		storedValues.put("var-"+axis, var);
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
	public int[] maxPos() {
		abstractCompoundLogger.error("Cannot compare compound numbers");
		throw new UnsupportedOperationException("Cannot compare compound numbers");
	}

	@Override
	public int[] minPos() {
		abstractCompoundLogger.error("Cannot compare compound numbers");
		throw new UnsupportedOperationException("Cannot compare compound numbers");
	}

	/**
	 * Calculate maximum values of elements over all items in dataset
	 * @return double array of element-wise maxima
	 */
	public double[] maxItem() {
		if (storedValues == null) {
			calculateSummaryStats();
		}

		double[] results = new double[isize];
		for (int i = 0; i < isize; i++) {
			results[i] = ((SummaryStatistics) storedValues.get("stats-"+i)).getMax();
		}
		return results;
	}

	/**
	 * Calculate minimum values of elements over all items in dataset
	 * @return double array of element-wise minima
	 */
	public double[] minItem() {
		if (storedValues == null) {
			calculateSummaryStats();
		}

		double[] results = new double[isize];
		for (int i = 0; i < isize; i++) {
			results[i] = ((SummaryStatistics) storedValues.get("stats-"+i)).getMin();
		}
		return results;
	}

	@Override
	public Object sum() {
		if (storedValues == null) {
			calculateSummaryStats();
		}

		double[] results = new double[isize];
		for (int i = 0; i < isize; i++) {
			results[i] = ((SummaryStatistics) storedValues.get("stats-"+i)).getSum();
		}
		return results;
	}

	@Override
	public Object mean() {
		if (storedValues == null) {
			calculateSummaryStats();
		}

		double[] results = new double[isize];
		for (int i = 0; i < isize; i++) {
			results[i] = ((SummaryStatistics) storedValues.get("stats-"+i)).getMean();
		}
		return results;
	}

	@Override
	public Number variance() {
		if (storedValues == null) {
			calculateSummaryStats();
		}

		double result = 0;
		for (int i = 0; i < isize; i++)
			result += ((SummaryStatistics) storedValues.get("stats-"+i)).getVariance();
		return result;
	}

	@Override
	public Number rootMeanSquare() {
		if (storedValues == null) {
			calculateSummaryStats();
		}

		double result = 0;
		for (int i = 0; i < isize; i++) {
			final SummaryStatistics stats = (SummaryStatistics) storedValues.get("stats-"+i);
			final double mean = stats.getMean();
			result += stats.getVariance() + mean*mean;
		}
		return Math.sqrt(result);
	}
	
	
	
	protected Object errorArray = null;
	protected AbstractCompoundDataset errorCompoundData = null;
	
	public void setErrorArray(Object errorArray) {		
		
		double[] errorSquared = new double[isize];
		
		for (int i = 0; i < isize; i++) {
			errorSquared[i] = Math.pow(AbstractCompoundDataset.toDoubleArray(errorArray, isize)[i],2);
		}
		
		this.errorArray = errorSquared;
		errorData = null;
		errorValue = null;
		errorCompoundData = null;
	}
	
	public void setErrorCompoundData(AbstractCompoundDataset errorCompoundData) {
		if(!this.isCompatibleWith(errorCompoundData)){
			throw new IllegalArgumentException("Error Array Compound dataset is incompartable with this compound dataset");
		}
		
		AbstractDataset[] abstractDatasetArray = new AbstractDataset[isize];
		for (int i = 0; i < isize; i++) {
			abstractDatasetArray[i] = Maths.square(errorCompoundData.getElements(i));
		}		
		
		this.errorCompoundData = new CompoundDoubleDataset(abstractDatasetArray);
		errorArray = null;
		errorData = null;
		errorValue = null;
	}
	
	/**
	 * Gets the error array from the dataset, or creates an error array if all 
	 * values are the same
	 * @return the AbstractDataset which contains the error information
	 */
	@Override
	public AbstractCompoundDataset getError() {
		
		if (errorData != null) {
			AbstractDataset[] abstractDatasetArray = new AbstractDataset[isize];
			for (int i = 0; i < isize; i++) {
				abstractDatasetArray[i] = Maths.sqrt(errorData);
			}
			
			return new CompoundDoubleDataset(abstractDatasetArray);
		}
		
		if(errorArray != null) {
			AbstractDataset[] abstractDatasetArray = new AbstractDataset[isize];
			for (int i = 0; i < isize; i++) {
				DoubleDataset dds = new DoubleDataset(shape);
				dds.fill(AbstractCompoundDataset.toDoubleArray(errorArray, isize)[i]);
				abstractDatasetArray[i] = Maths.sqrt(dds);
			}
			
			return new CompoundDoubleDataset(abstractDatasetArray);
		}
		
		if(errorValue != null) {
			AbstractDataset[] abstractDatasetArray = new AbstractDataset[isize];
			for (int i = 0; i < isize; i++) {
				DoubleDataset dds = new DoubleDataset(shape);
				dds.fill(errorValue.doubleValue());
				abstractDatasetArray[i] = Maths.sqrt(dds);
			}
			
			return new CompoundDoubleDataset(abstractDatasetArray);
		}
	
		AbstractDataset[] abstractDatasetArray = new AbstractDataset[isize];
		for (int i = 0; i < isize; i++) {
			abstractDatasetArray[i] = Maths.sqrt(errorCompoundData.getElements(i));
		}
		
		return new CompoundDoubleDataset(abstractDatasetArray);
		
	}
	
	
	/**
	 * Gets the error value for a single point in the dataset
	 * @param pos of the point to be referenced 
	 * @return the value of the error at this point as a float
	 */
	public double[] getErrorArray(int... pos) {
		
		if (errorCompoundData != null) {
			double[] doubleArray = new double[isize];
			for (int i = 0; i < isize; i++) {
				doubleArray[i] = Math.sqrt(errorCompoundData.getElements(i).getDouble(pos));
			}
			
			return doubleArray;
		}
		
		if (errorData != null) {
			double[] doubleArray = new double[isize];
			for (int i = 0; i < isize; i++) {
				doubleArray[i] = Math.sqrt(errorData.getDouble(pos));
			}
			
			return doubleArray;
		}
		
		if (errorValue != null) {
			double[] doubleArray = new double[isize];
			for (int i = 0; i < isize; i++) {
				doubleArray[i] = Math.sqrt(errorValue.doubleValue());
			}
			
			return doubleArray;
		}
		
		double[] doubleArray = new double[isize];
		for (int i = 0; i < isize; i++) {
			doubleArray[i] = Math.sqrt(AbstractCompoundDataset.toDoubleArray(errorArray, isize)[i]);
		}
			
		return doubleArray;

	}
	

}


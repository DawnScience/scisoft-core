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

// This is generated from DoubleDataset.java by fromdouble.py

package uk.ac.diamond.scisoft.analysis.dataset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.math.complex.Complex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Extend dataset for long values // PRIM_TYPE
 */
public class LongDataset extends AbstractDataset {
	// pin UID to base class
	private static final long serialVersionUID = AbstractDataset.serialVersionUID;

	/**
	 * Setup the logging facilities
	 */
	transient private static final Logger logger = LoggerFactory.getLogger(LongDataset.class);

	protected long[] data; // subclass alias // PRIM_TYPE

	@Override
	protected void setData() {
		data = (long[]) odata; // PRIM_TYPE
	}

	protected static long[] createArray(final int size) { // PRIM_TYPE
		long[] array = null; // PRIM_TYPE

		try {
			array = new long[size]; // PRIM_TYPE
		} catch (OutOfMemoryError e) {
			logger.error("The size of the dataset ({}) that is being created is too large "
					+ "and there is not enough memory to hold it.", size);
			throw new OutOfMemoryError("The dimensions given are too large, and there is "
					+ "not enough memory available in the Java Virtual Machine");
		}
		return array;
	}

	@Override
	public int getDtype() {
		return INT64; // DATA_TYPE
	}

	public LongDataset() {
	}

	/**
	 * Create a zero-filled dataset of given shape
	 * @param shape
	 */
	public LongDataset(final int... shape) {
		if (shape.length == 1) {
			size = shape[0];
			if (size < 0) {
				throw new IllegalArgumentException("Negative component in shape is not allowed");
			}
		} else {
			size = calcSize(shape);
		}
		this.shape = shape.clone();

		odata = data = createArray(size);
	}

	/**
	 * Create a dataset using given data
	 * @param data
	 * @param shape
	 *            (can be null to create 1D dataset)
	 */
	public LongDataset(final long[] data, int... shape) { // PRIM_TYPE
		if (shape == null || shape.length == 0) {
			shape = new int[] { data.length };
		}
		size = calcSize(shape);
		if (size != data.length) {
			throw new IllegalArgumentException(String.format("Shape %s is not compatible with size of data array, %d",
					Arrays.toString(shape), data.length));
		}
		this.shape = shape.clone();

		odata = this.data = data;
	}

	/**
	 * Copy a dataset
	 * @param dataset
	 */
	public LongDataset(final LongDataset dataset) {
		this(dataset, false);
	}

	/**
	 * Copy a dataset or just wrap in a new reference (for Jython sub-classing)
	 * @param dataset
	 * @param wrap
	 */
	public LongDataset(final LongDataset dataset, final boolean wrap) {
		if (wrap) {
			copyToView(dataset, this, false, false);
			data = dataset.data;
			return;
		}

		copyToView(dataset, this, true, true);

		odata = data = dataset.data.clone();
	}

	/**
	 * Cast a dataset to this class type
	 * @param dataset
	 */
	public LongDataset(final AbstractDataset dataset) {
		copyToView(dataset, this, true, false);

		odata = data = createArray(size);

		IndexIterator iter = dataset.getIterator();
		for (int i = 0; iter.hasNext(); i++) {
			data[i] = dataset.getElementLongAbs(iter.index); // GET_ELEMENT_WITH_CAST
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}

		if (getRank() == 0) // already true for zero-rank dataset
			return true;

		LongDataset other = (LongDataset) obj;
		IndexIterator it = getIterator();
		while (it.hasNext()) {
			if (data[it.index] != other.data[it.index]) // OBJECT_UNEQUAL
				return false;
		}
		return true;
	}

	/**
	 * Create a dataset from an object which could be a PySequence, a Java array (of arrays...) or Number. Ragged
	 * sequences or arrays are padded with zeros.
	 *
	 * @param obj
	 * @return dataset with contents given by input
	 */
	public static LongDataset createFromObject(final Object obj) {
		LongDataset result = new LongDataset();

		result.shape = getShapeFromObject(obj);
		result.size = calcSize(result.shape);

		result.odata = result.data = createArray(result.size);

		int[] pos = new int[result.shape.length];
		result.fillData(obj, 0, pos);
		return result;
	}
	// BOOLEAN_OMIT
	/**
	 *
	 * @param stop
	 * @return a new 1D dataset, filled with values determined by parameters
	 */
	public static LongDataset arange(final double stop) {
		return arange(0, stop, 1);
	}
	// BOOLEAN_OMIT
	/**
	 *
	 * @param start
	 * @param stop
	 * @param step
	 * @return a new 1D dataset, filled with values determined by parameters
	 */
	public static LongDataset arange(final double start, final double stop, final double step) {
		int size = calcSteps(start, stop, step);
		LongDataset result = new LongDataset(size);
		for (int i = 0; i < size; i++) {
			result.data[i] = (long) (start + i * step); // PRIM_TYPE // ADD_CAST
		}
		return result;
	}

	/**
	 * @param shape
	 * @return a dataset filled with ones
	 */
	public static LongDataset ones(final int... shape) {
		return new LongDataset(shape).fill(1);
	}

	/**
	 * @param obj
	 * @return dataset filled with given object
	 */
	@Override
	public LongDataset fill(final Object obj) {
		long dv = toLong(obj); // PRIM_TYPE // FROM_OBJECT

		IndexIterator iter = getIterator();
		while (iter.hasNext()) {
			data[iter.index] = dv;
		}

		return this;
	}

	/**
	 * This is a typed version of {@link #getBuffer()}
	 * @return data buffer as linear array
	 */
	public long[] getData() { // PRIM_TYPE
		return data;
	}

	@Override
	public LongDataset getView() {
		LongDataset view = new LongDataset();
		copyToView(this, view, true, true);
		view.data = data;
		return view;
	}

	/**
	 * Get a value from an absolute index of the internal array. This is an internal method with no checks so can be
	 * dangerous. Use with care or ideally with an iterator.
	 *
	 * @param index
	 *            absolute index
	 * @return value
	 */
	public long getAbs(final int index) { // PRIM_TYPE
		return data[index];
	}

	@Override
	public boolean getElementBooleanAbs(final int index) {
		return data[index] != 0; // BOOLEAN_FALSE
	}

	@Override
	public double getElementDoubleAbs(final int index) {
		return data[index]; // BOOLEAN_ZERO
	}

	@Override
	public long getElementLongAbs(final int index) {
		return data[index]; // BOOLEAN_ZERO // OMIT_CAST_INT
	}

	@Override
	public Object getObjectAbs(final int index) {
		return data[index];
	}

	@Override
	public String getStringAbs(final int index) {
		return String.format("%d", data[index]); // FORMAT_STRING
	}

	/**
	 * Set a value at absolute index in the internal array. This is an internal method with no checks so can be
	 * dangerous. Use with care or ideally with an iterator.
	 *
	 * @param index
	 *            absolute index
	 * @param val
	 *            new value
	 */
	public void setAbs(final int index, final long val) { // PRIM_TYPE
		data[index] = val;
		setDirty();
	}

	@Override
	protected void setItemDirect(final int dindex, final int sindex, final Object src) {
		long[] dsrc = (long[]) src; // PRIM_TYPE
		data[dindex] = dsrc[sindex];
	}

	@Override
	public void setObjectAbs(final int index, final Object obj) {
		if (index < 0 || index > data.length) {
			throw new IndexOutOfBoundsException("Index given is outside dataset");
		}

		setAbs(index, toLong(obj)); // FROM_OBJECT
	}

	/**
	 * @param pos
	 * @return item in given position
	 */
	public long get(final int... pos) { // PRIM_TYPE
		return data[get1DIndex(pos)];
	}

	@Override
	public Object getObject(final int... pos) {
		return Long.valueOf(get(pos)); // CLASS_TYPE
	}

	@Override
	public double getDouble(final int... pos) {
		return get(pos); // BOOLEAN_ZERO // OMIT_SAME_CAST // ADD_CAST
	}

	@Override
	public float getFloat(final int... pos) {
		return get(pos); // BOOLEAN_ZERO // OMIT_REAL_CAST
	}

	@Override
	public long getLong(final int... pos) {
		return get(pos); // BOOLEAN_ZERO // OMIT_UPCAST
	}

	@Override
	public int getInt(final int... pos) {
		return (int) get(pos); // BOOLEAN_ZERO // OMIT_UPCAST
	}

	@Override
	public short getShort(final int... pos) {
		return (short) get(pos); // BOOLEAN_ZERO // OMIT_UPCAST
	}

	@Override
	public byte getByte(final int... pos) {
		return (byte) get(pos); // BOOLEAN_ZERO // OMIT_UPCAST
	}

	@Override
	public boolean getBoolean(final int... pos) {
		return get(pos) != 0; // BOOLEAN_FALSE
	}

	@Override
	public String getString(final int... pos) {
		return getStringAbs(get1DIndex(pos));
	}

	/**
	 * Sets the value at a particular point to the passed value. Note, this will automatically expand the dataset if the
	 * given position is outside its bounds and make it discontiguous.
	 *
	 * @param value
	 * @param pos
	 */
	public void setItem(final long value, final int... pos) { // PRIM_TYPE
		if (!isPositionInShape(pos)) {
			throw new ArrayIndexOutOfBoundsException("Index out of bounds");
		}
		setAbs(get1DIndex(pos), value);
	}

	@Override
	public void set(final Object obj, int... pos) {
		if (pos == null || pos.length == 0) {
			pos = new int[shape.length];
		}

		setItem(toLong(obj), pos); // FROM_OBJECT
	}


	@Override
	public void resize(int... newShape) {
		final IndexIterator iter = getIterator();
		final int nsize = calcSize(newShape);
		final long[] ndata = createArray(nsize); // PRIM_TYPE
		for (int i = 0; iter.hasNext() && i < nsize; i++) {
			ndata[i] = data[iter.index];
		}

		odata = data = ndata;
		size = nsize;
		shape = newShape;
	}

	@Override
	public LongDataset getSlice(final SliceIterator siter) {
		LongDataset result = new LongDataset(siter.getSliceShape());
		long[] rdata = result.data; // PRIM_TYPE

		for (int i = 0; siter.hasNext(); i++)
			rdata[i] = data[siter.index];

		result.setName(name + ".slice");
		return result;
	}

	@Override
	public void fillDataset(AbstractDataset result, IndexIterator iter) {
		IndexIterator riter = result.getIterator();

		long[] rdata = ((LongDataset) result).data; // PRIM_TYPE

		while (riter.hasNext() && iter.hasNext())
			rdata[riter.index] = data[iter.index];
	}

	@Override
	public LongDataset setByBoolean(final Object obj, BooleanDataset selection) {
		if (obj instanceof AbstractDataset) {
			final AbstractDataset ds = (AbstractDataset) obj;
			final int length = ((Number) selection.sum()).intValue();
			if (length != ds.getSize()) {
				throw new IllegalArgumentException(
						"Number of true items in selection does not match number of items in dataset");
			}

			final IndexIterator oiter = ds.getIterator();
			final BooleanIterator biter = getBooleanIterator(selection);

			while (biter.hasNext() && oiter.hasNext()) {
				data[biter.index] = ds.getElementLongAbs(oiter.index); // GET_ELEMENT_WITH_CAST
			}
		} else {
			final long dv = toLong(obj); // PRIM_TYPE // FROM_OBJECT
			final BooleanIterator biter = getBooleanIterator(selection);

			while (biter.hasNext()) {
				data[biter.index] = dv;
			}
		}
		return this;
	}

	@Override
	public LongDataset setByIndex(final Object obj, IntegerDataset index) {
		if (obj instanceof AbstractDataset) {
			final AbstractDataset ds = (AbstractDataset) obj;
			if (index.getSize() != ds.getSize()) {
				throw new IllegalArgumentException(
						"Number of true items in index dataset does not match number of items in dataset");
			}

			final IndexIterator oiter = ds.getIterator();
			final IntegerIterator iter = new IntegerIterator(index, size);

			while (iter.hasNext() && oiter.hasNext()) {
				data[iter.index] = ds.getElementLongAbs(oiter.index); // GET_ELEMENT_WITH_CAST
			}
		} else {
			final long dv = toLong(obj); // PRIM_TYPE // FROM_OBJECT
			IntegerIterator iter = new IntegerIterator(index, size);

			while (iter.hasNext()) {
				data[iter.index] = dv;
			}
		}
		return this;
	}

	@Override
	public LongDataset setSlice(final Object obj, final SliceIterator siter) {

		if (obj instanceof IDataset) {
			final IDataset ds = (IDataset) obj;
			final int[] oshape = ds.getShape();

			if (!areShapesCompatible(siter.getSliceShape(), oshape)) {
				throw new IllegalArgumentException(String.format(
						"Input dataset is not compatible with slice: %s cf %s", Arrays.toString(oshape),
						Arrays.toString(siter.getSliceShape())));
			}

			if (ds instanceof AbstractDataset) {
				final AbstractDataset ads = (AbstractDataset) ds;
				final IndexIterator oiter = ads.getIterator();

				while (siter.hasNext() && oiter.hasNext())
					data[siter.index] = ads.getElementLongAbs(oiter.index); // GET_ELEMENT_WITH_CAST
			} else {
				final IndexIterator oiter = new PositionIterator(oshape);
				final int[] pos = oiter.getPos();

				while (siter.hasNext() && oiter.hasNext())
					data[siter.index] = ds.getLong(pos); // PRIM_TYPE
			}
		} else {
			try {
				long v = toLong(obj); // PRIM_TYPE // FROM_OBJECT

				while (siter.hasNext())
					data[siter.index] = v;
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("Object for setting slice is not a dataset or number");
			}
		}
		setDirty();
		return this;
	}

	@Override
	public void copyItemsFromAxes(final int[] pos, final boolean[] axes, final AbstractDataset dest) {
		long[] ddata = (long[]) dest.odata; // PRIM_TYPE

		SliceIterator siter = getSliceIteratorFromAxes(pos, axes);
		int[] sshape = squeezeShape(siter.getSliceShape(), false);

		IndexIterator diter = dest.getSliceIterator(null, sshape, null);

		if (ddata.length < calcSize(sshape)) {
			throw new IllegalArgumentException("destination array is not large enough");
		}

		while (siter.hasNext() && diter.hasNext())
			ddata[diter.index] = data[siter.index];
	}

	@Override
	public void setItemsOnAxes(final int[] pos, final boolean[] axes, final Object src) {
		long[] sdata = (long[]) src; // PRIM_TYPE

		SliceIterator siter = getSliceIteratorFromAxes(pos, axes);

		if (sdata.length < calcSize(siter.getSliceShape())) {
			throw new IllegalArgumentException("destination array is not large enough");
		}

		for (int i = 0; siter.hasNext(); i++) {
			data[siter.index] = sdata[i];
		}
		setDirty();
	}

	private List<Integer> findPositions(final long value) { // PRIM_TYPE
		IndexIterator iter = getIterator();
		List<Integer> posns = new ArrayList<Integer>();

		{
			while (iter.hasNext()) {
				if (data[iter.index] == value) {
					posns.add(iter.index);
				}
			}
		}
		return posns;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public int[] maxPos(boolean ignoreInvalids) {
		if (storedValues == null) {
			if (ignoreInvalids)
				calculateMaxMin(true, true);
			else
				calculateMaxMin(false, false);
		}
		String n = ignoreInvalids ? storeName(true, true, STORE_MAX_POS) : storeName(false, false, STORE_MAX_POS);
		Object o = storedValues.get(n);

		List<Integer> max = null;
		if (o == null) {
			// TODO this test is necessary because Jython thinks max(boolean) is max(int)!
			if (ignoreInvalids)
				max = findPositions(max(ignoreInvalids).longValue()); // PRIM_TYPE
			else
				max = findPositions(max().longValue()); // PRIM_TYPE
			// max = findPositions(max().intValue() != 0); // BOOLEAN_USE
			// max = findPositions(null); // OBJECT_USE
			storedValues.put(n, max);
		} else if (o instanceof List<?>) {
			max = (ArrayList<Integer>) o;
		} else {
			throw new InternalError("Inconsistent internal state of stored values for statistics calculation");
		}

		return getNDPosition(max.get(0)); // first maximum
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public int[] minPos(boolean ignoreInvalids) {
		if (storedValues == null) {
			if (ignoreInvalids)
				calculateMaxMin(true, true);
			else
				calculateMaxMin(false, false);
		}
		String n = ignoreInvalids ? storeName(true, true, STORE_MAX_POS) : storeName(false, false, STORE_MAX_POS);
		Object o = storedValues.get(n);
		List<Integer> min = null;
		if (o == null) {
			if (ignoreInvalids)
				min = findPositions(min(ignoreInvalids).longValue()); // PRIM_TYPE
			else
				min = findPositions(min().longValue()); // PRIM_TYPE
			// min = findPositions(min().intValue() != 0); // BOOLEAN_USE
			// min = findPositions(null); // OBJECT_USE
			storedValues.put(n, min);
		} else if (o instanceof ArrayList<?>) {
			min = (ArrayList<Integer>) o;
		} else {
			throw new InternalError("Inconsistent internal state of stored values for statistics calculation");
		}

		return getNDPosition(min.get(0)); // first minimum
	}

	@Override
	public boolean containsNans() {
		return false;
	}

	@Override
	public boolean containsInfs() {
		return false;
	}

	@Override
	public boolean containsInvalidNumbers() {
		return false;
	}

	@Override
	public LongDataset iadd(final Object b) {
		if (b instanceof AbstractDataset) {
			AbstractDataset bds = (AbstractDataset) b;
			checkCompatibility(bds);

			IndexIterator it1 = getIterator();
			IndexIterator it2 = bds.getIterator();

			while (it1.hasNext() && it2.hasNext()) {
				data[it1.index] += bds.getElementLongAbs(it2.index); // GET_ELEMENT
			}
		} else {
			final double v = toReal(b);
			IndexIterator it1 = getIterator();

			while (it1.hasNext()) {
				data[it1.index] += v;
			}
		}
		setDirty();
		return this;
	}

	@Override
	public LongDataset isubtract(final Object b) {
		if (b instanceof AbstractDataset) {
			AbstractDataset bds = (AbstractDataset) b;
			checkCompatibility(bds);

			IndexIterator it1 = getIterator();
			IndexIterator it2 = bds.getIterator();

			while (it1.hasNext() && it2.hasNext()) {
				data[it1.index] -= bds.getElementLongAbs(it2.index); // GET_ELEMENT
			}
		} else {
			final double v = toReal(b);
			IndexIterator it1 = getIterator();

			while (it1.hasNext()) {
				data[it1.index] -= v;
			}
		}
		setDirty();
		return this;
	}

	@Override
	public LongDataset imultiply(final Object b) {
		if (b instanceof AbstractDataset) {
			AbstractDataset bds = (AbstractDataset) b;
			checkCompatibility(bds);

			IndexIterator it1 = getIterator();
			IndexIterator it2 = bds.getIterator();

			while (it1.hasNext() && it2.hasNext()) {
				data[it1.index] *= bds.getElementLongAbs(it2.index); // GET_ELEMENT
			}
		} else {
			final double v = toReal(b);
			IndexIterator it1 = getIterator();

			while (it1.hasNext()) {
				data[it1.index] *= v;
			}
		}
		setDirty();
		return this;
	}

	@Override
	public LongDataset idivide(final Object b) {
		if (b instanceof AbstractDataset) {
			AbstractDataset bds = (AbstractDataset) b;
			checkCompatibility(bds);

			IndexIterator it1 = getIterator();
			IndexIterator it2 = bds.getIterator();

			while (it1.hasNext() && it2.hasNext()) {
				try {
					data[it1.index] /= bds.getElementLongAbs(it2.index); // GET_ELEMENT // INT_EXCEPTION
				} catch (ArithmeticException e) {
					data[it1.index] = 0;
				}
			}
		} else {
			final double v = toReal(b);
			if (v == 0) { // INT_ZEROTEST
				fill(0); // INT_ZEROTEST
			} else { // INT_ZEROTEST
			IndexIterator it1 = getIterator();

			while (it1.hasNext()) {
				data[it1.index] /= v;
			}
			} // INT_ZEROTEST
		}
		setDirty();
		return this;
	}

	@Override
	public LongDataset ifloor() {
		return this;
	}

	@Override
	public LongDataset iremainder(final Object b) {
		if (b instanceof AbstractDataset) {
			AbstractDataset bds = (AbstractDataset) b;
			checkCompatibility(bds);
			// BOOLEAN_OMIT
			IndexIterator it1 = getIterator();
			IndexIterator it2 = bds.getIterator();
			// BOOLEAN_OMIT
			while (it1.hasNext() && it2.hasNext()) {
				try {
					data[it1.index] %= bds.getElementLongAbs(it2.index); // GET_ELEMENT // INT_EXCEPTION
				} catch (ArithmeticException e) {
					data[it1.index] = 0;
				}
			}
		} else {
			final double v = toReal(b);
			if (v == 0) { // INT_ZEROTEST
				fill(0); // INT_ZEROTEST
			} else { // INT_ZEROTEST
			IndexIterator it1 = getIterator();
			// BOOLEAN_OMIT
			while (it1.hasNext()) {
				data[it1.index] %= v;
			}
			} // INT_ZEROTEST
		}
		setDirty();
		return this;
	}

	@Override
	public LongDataset ipower(final Object b) {
		if (b instanceof AbstractDataset) {
			AbstractDataset bds = (AbstractDataset) b;
			checkCompatibility(bds);
			// BOOLEAN_OMIT
			IndexIterator it1 = getIterator();
			IndexIterator it2 = bds.getIterator();
			// BOOLEAN_OMIT
			while (it1.hasNext() && it2.hasNext()) {
				final double v = Math.pow(data[it1.index], bds.getElementDoubleAbs(it2.index));
				if (Double.isInfinite(v) || Double.isNaN(v)) { // INT_ZEROTEST
					data[it1.index] = 0; // INT_ZEROTEST
				} else { // INT_ZEROTEST
				data[it1.index] = (long) v; // PRIM_TYPE_LONG // ADD_CAST
				} // INT_ZEROTEST
			}
		} else {
			double vr = toReal(b);
			double vi = toImag(b);
			IndexIterator it1 = getIterator();
			// BOOLEAN_OMIT
			if (vi == 0.) {
				while (it1.hasNext()) {
					final double v = Math.pow(data[it1.index], vr);
					if (Double.isInfinite(v) || Double.isNaN(v)) { // INT_ZEROTEST
						data[it1.index] = 0; // INT_ZEROTEST
					} else { // INT_ZEROTEST
					data[it1.index] = (long) v; // PRIM_TYPE_LONG // ADD_CAST
					} // INT_ZEROTEST
				}
			} else {
				Complex zv = new Complex(vr, vi);
				while (it1.hasNext()) {
					Complex zd = new Complex(data[it1.index], 0.);
					final double v = zd.pow(zv).getReal();
					if (Double.isInfinite(v) || Double.isNaN(v)) { // INT_ZEROTEST
						data[it1.index] = 0; // INT_ZEROTEST
					} else { // INT_ZEROTEST
					data[it1.index] = (long) v; // PRIM_TYPE_LONG // ADD_CAST
					} // INT_ZEROTEST
				}
			}
		}
		setDirty();
		return this;
	}

	@Override
	public double residual(final Object b, boolean ignoreNaNs) {
		double sum = 0;
		if (b instanceof AbstractDataset) {
			AbstractDataset bds = (AbstractDataset) b;
			checkCompatibility(bds);

			IndexIterator it1 = getIterator();
			IndexIterator it2 = bds.getIterator();

			double comp = 0;
			{
				while (it1.hasNext() && it2.hasNext()) {
					final double diff = data[it1.index] - bds.getElementDoubleAbs(it2.index);
					final double err = diff * diff - comp;
					final double temp = sum + err;
					comp = (temp - sum) - err;
					sum = temp;
				}
			}
		} else {
			final double v = toReal(b);
			IndexIterator it1 = getIterator();

			double comp = 0;
			while (it1.hasNext()) {
				final double diff = data[it1.index] - v;
				final double err = diff * diff - comp;
				final double temp = sum + err;
				comp = (temp - sum) - err;
				sum = temp;
			}
		}
		return sum;
	}
}

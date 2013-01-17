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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Extend dataset for String values // PRIM_TYPE
 */
public class StringDatasetBase extends AbstractDataset {
	// pin UID to base class
	private static final long serialVersionUID = AbstractDataset.serialVersionUID;

	/**
	 * Setup the logging facilities
	 */
	transient private static final Logger logger = LoggerFactory.getLogger(StringDatasetBase.class);

	protected String[] data; // subclass alias // PRIM_TYPE

	@Override
	protected void setData() {
		data = (String[]) odata; // PRIM_TYPE
	}

	private static String[] createArray(final int size) { // PRIM_TYPE
		String[] array = null; // PRIM_TYPE

		try {
			array = new String[size]; // PRIM_TYPE
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
		return STRING; // DATA_TYPE
	}

	public StringDatasetBase() {
	}

	/**
	 * Create a zero-filled dataset of given shape
	 * @param shape
	 */
	public StringDatasetBase(final int... shape) {
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
	public StringDatasetBase(final String[] data, int... shape) { // PRIM_TYPE
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
	public StringDatasetBase(final StringDatasetBase dataset) {
		this(dataset, false);
	}

	/**
	 * Copy a dataset or just wrap in a new reference (for Jython sub-classing)
	 * @param dataset
	 * @param wrap
	 */
	public StringDatasetBase(final StringDatasetBase dataset, final boolean wrap) {
		if (wrap) {
			copyToView(dataset, this, false, false);
			data = dataset.data;
			return;
		}

		copyToView(dataset, this, true, true);

		String[] gdata = dataset.data; // PRIM_TYPE

		if (dataset.isContiguous()) {
			odata = data = gdata.clone();
		} else {
			odata = data = createArray(size);

			IndexIterator iter = dataset.getIterator();
			for (int i = 0; iter.hasNext(); i++) {
				data[i] = gdata[iter.index];
			}
		}

		// now also populate the errors
		errorValue = dataset.errorValue;
		errorData = dataset.errorData;
	}

	/**
	 * Cast a dataset to this class type
	 * @param dataset
	 */
	public StringDatasetBase(final AbstractDataset dataset) {
		copyToView(dataset, this, true, false);

		odata = data = createArray(size);

		IndexIterator iter = dataset.getIterator();
		for (int i = 0; iter.hasNext(); i++) {
			data[i] = dataset.getStringAbs(iter.index); // GET_ELEMENT_WITH_CAST
		}

		// now also populate the errors
		errorValue = dataset.errorValue;
		errorData = dataset.errorData;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}

		if (getRank() == 0) // already true for scalar dataset
			return true;

		StringDatasetBase other = (StringDatasetBase) obj;
		IndexIterator it = getIterator();
		while (it.hasNext()) {
			if (!data[it.index].equals(other.data[it.index])) // OBJECT_UNEQUAL
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
	public static StringDatasetBase createFromObject(final Object obj) {
		StringDatasetBase result = new StringDatasetBase();

		result.shape = getShapeFromObject(obj);
		result.size = calcSize(result.shape);

		result.odata = result.data = createArray(result.size);

		int[] pos = new int[result.shape.length];
		result.fillData(obj, 0, pos);
		return result;
	}

	/**
	 * @param shape
	 * @return a dataset filled with ones
	 */
	public static StringDatasetBase ones(final int... shape) {
		return new StringDatasetBase(shape).fill(1);
	}

	/**
	 * @param obj
	 * @return dataset filled with given object
	 */
	@Override
	public StringDatasetBase fill(final Object obj) {
		String dv = obj.toString(); // PRIM_TYPE // FROM_OBJECT

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
	public String[] getData() { // PRIM_TYPE
		return data;
	}

	@Override
	public StringDatasetBase getView() {
		StringDatasetBase view = new StringDatasetBase();
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
	public String getAbs(final int index) { // PRIM_TYPE
		return data[index];
	}

	@Override
	public boolean getElementBooleanAbs(final int index) {
		return false;
	}

	@Override
	public double getElementDoubleAbs(final int index) {
		return 0;
	}

	@Override
	public long getElementLongAbs(final int index) {
		return 0;
	}

	@Override
	public Object getObjectAbs(final int index) {
		return data[index];
	}

	@Override
	public String getStringAbs(final int index) {
		return  data[index]; // FORMAT_STRING
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
	public void setAbs(final int index, final String val) { // PRIM_TYPE
		data[index] = val;
		setDirty();
	}

	@Override
	protected void setItemDirect(final int dindex, final int sindex, final Object src) {
		String[] dsrc = (String[]) src; // PRIM_TYPE
		data[dindex] = dsrc[sindex];
	}

	@Override
	public void setObjectAbs(final int index, final Object obj) {
		if (index < 0 || index > data.length) {
			throw new IndexOutOfBoundsException("Index given is outside dataset");
		}

		setAbs(index, obj.toString()); // FROM_OBJECT
	}

	/**
	 * @param pos
	 * @return item in given position
	 */
	public String get(final int... pos) { // PRIM_TYPE
		return data[get1DIndex(pos)];
	}

	@Override
	public Object getObject(final int... pos) {
		return get(pos); // CLASS_TYPE
	}

	@Override
	public double getDouble(final int... pos) {
		return 0;
	}

	@Override
	public float getFloat(final int... pos) {
		return 0;
	}

	@Override
	public long getLong(final int... pos) {
		return 0;
	}

	@Override
	public int getInt(final int... pos) {
		return 0;
	}

	@Override
	public short getShort(final int... pos) {
		return 0;
	}

	@Override
	public byte getByte(final int... pos) {
		return 0;
	}

	@Override
	public boolean getBoolean(final int... pos) {
		return false;
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
	public void setItem(final String value, final int... pos) { // PRIM_TYPE
		try {
			if (!isPositionInShape(pos)) {
				int[] nshape = shape.clone();

				for (int i = 0; i < pos.length; i++)
					if (pos[i] >= nshape[i])
						nshape[i] = pos[i] + 1;

				allocateArray(nshape);
			}
			setAbs(get1DIndex(pos), value);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(String.format(
					"Dimensionalities of requested position, %d, and dataset, %d, are incompatible", pos.length,
					shape.length));
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("Index out of bounds - need to make dataset extendible");
		}
	}

	@Override
	public void set(final Object obj, int... pos) {
		if (pos == null || pos.length == 0) {
			pos = new int[shape.length];
		}

		setItem(obj.toString(), pos); // FROM_OBJECT
	}

	private void allocateArray(final int... nshape) {
		if (data == null) {
			throw new IllegalStateException("Data buffer in dataset is null");
		}

		if (dataShape != null) {
			// see if reserved space is sufficient
			if (isShapeInDataShape(nshape)) {
				shape = nshape;
				size = calcSize(shape);
				if (Arrays.equals(shape, dataShape)) {
					dataShape = null; // no reserved space
				}
				return;
			}
		}

		final IndexIterator iter = getIterator();

		// not enough room so need to expand the allocated memory
		if (dataShape == null)
			dataShape = shape.clone();
		expandDataShape(nshape);
		dataSize = calcSize(dataShape);

		final String[] ndata = createArray(dataSize); // PRIM_TYPE
		final int[] oshape = shape;

		// now this object has the new dimensions so specify them correctly
		shape = nshape;
		size = calcSize(nshape);

		// make sure that all the data is set to NaN, minimum value or false

		// now copy the data back to the correct positions
		final IndexIterator niter = getSliceIterator(null, oshape, null);

		while (niter.hasNext() && iter.hasNext())
			ndata[niter.index] = data[iter.index];

		odata = data = ndata;

		// if fully expanded then reset the reserved space dimensions
		if (dataSize == size) {
			dataShape = null;
		}
	}

	@Override
	public void resize(int... newShape) {
		final IndexIterator iter = getIterator();
		final int nsize = calcSize(newShape);
		final String[] ndata = createArray(nsize); // PRIM_TYPE
		for (int i = 0; iter.hasNext() && i < nsize; i++) {
			ndata[i] = data[iter.index];
		}

		odata = data = ndata;
		size = nsize;
		shape = newShape;
		dataShape = null;
		dataSize = size;
	}

	@Override
	public StringDatasetBase getSlice(final SliceIterator siter) {
		StringDatasetBase result = new StringDatasetBase(siter.getSliceShape());
		String[] rdata = result.data; // PRIM_TYPE

		for (int i = 0; siter.hasNext(); i++)
			rdata[i] = data[siter.index];

		result.setName(name + ".slice");
		return result;
	}

	@Override
	public void fillDataset(AbstractDataset result, IndexIterator iter) {
		IndexIterator riter = result.getIterator();

		String[] rdata = ((StringDatasetBase) result).data; // PRIM_TYPE

		while (riter.hasNext() && iter.hasNext())
			rdata[riter.index] = data[iter.index];
	}

	@Override
	public StringDatasetBase setByBoolean(final Object obj, BooleanDataset selection) {
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
				data[biter.index] = ds.getStringAbs(oiter.index); // GET_ELEMENT_WITH_CAST
			}
		} else {
			final String dv = obj.toString(); // PRIM_TYPE // FROM_OBJECT
			final BooleanIterator biter = getBooleanIterator(selection);

			while (biter.hasNext()) {
				data[biter.index] = dv;
			}
		}
		return this;
	}

	@Override
	public StringDatasetBase setByIndex(final Object obj, IntegerDataset index) {
		if (obj instanceof AbstractDataset) {
			final AbstractDataset ds = (AbstractDataset) obj;
			if (index.getSize() != ds.getSize()) {
				throw new IllegalArgumentException(
						"Number of true items in index dataset does not match number of items in dataset");
			}

			final IndexIterator oiter = ds.getIterator();
			final IntegerIterator iter = new IntegerIterator(index, size);

			while (iter.hasNext() && oiter.hasNext()) {
				data[iter.index] = ds.getStringAbs(oiter.index); // GET_ELEMENT_WITH_CAST
			}
		} else {
			final String dv = obj.toString(); // PRIM_TYPE // FROM_OBJECT
			IntegerIterator iter = new IntegerIterator(index, size);

			while (iter.hasNext()) {
				data[iter.index] = dv;
			}
		}
		return this;
	}

	@Override
	public StringDatasetBase setSlice(final Object obj, final SliceIterator siter) {

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
					data[siter.index] = ads.getStringAbs(oiter.index); // GET_ELEMENT_WITH_CAST
			} else {
				final IndexIterator oiter = new PositionIterator(oshape);
				final int[] pos = oiter.getPos();

				while (siter.hasNext() && oiter.hasNext())
					data[siter.index] = ds.getString(pos); // PRIM_TYPE
			}
		} else {
			try {
				String v = obj.toString(); // PRIM_TYPE // FROM_OBJECT

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
		String[] ddata = (String[]) dest.odata; // PRIM_TYPE

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
		String[] sdata = (String[]) src; // PRIM_TYPE

		SliceIterator siter = getSliceIteratorFromAxes(pos, axes);

		if (sdata.length < calcSize(siter.getSliceShape())) {
			throw new IllegalArgumentException("destination array is not large enough");
		}

		for (int i = 0; siter.hasNext(); i++) {
			data[siter.index] = sdata[i];
		}
		setDirty();
	}

	private List<Integer> findPositions(final String value) { // PRIM_TYPE
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
	public int[] maxPos(boolean ignoreNaNs) {
		if (storedValues == null) {
			calculateMaxMin(ignoreNaNs);
		}
		String n = storeName(ignoreNaNs, STORE_MAX_POS);
		Object o = storedValues.get(n);

		List<Integer> max = null;
		if (o == null) {
			// TODO this test is necessary because Jython thinks max(boolean) is max(int)!
			// max = findPositions(max().intValue() != 0); // BOOLEAN_USE
			max = findPositions(null); // OBJECT_USE
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
	public int[] minPos(boolean ignoreNaNs) {
		if (storedValues == null) {
			calculateMaxMin(ignoreNaNs);
		}
		String n = storeName(ignoreNaNs, STORE_MIN_POS);
		Object o = storedValues.get(n);
		List<Integer> min = null;
		if (o == null) {
			// min = findPositions(min().intValue() != 0); // BOOLEAN_USE
			min = findPositions(null); // OBJECT_USE
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
	public StringDatasetBase iadd(final Object b) {
		if (b instanceof AbstractDataset) {
			AbstractDataset bds = (AbstractDataset) b;
			checkCompatibility(bds);

			IndexIterator it1 = getIterator();
			IndexIterator it2 = bds.getIterator();

			while (it1.hasNext() && it2.hasNext()) {
			}
		} else {
			IndexIterator it1 = getIterator();

			while (it1.hasNext()) {
			}
		}
		setDirty();
		return this;
	}

	@Override
	public StringDatasetBase isubtract(final Object b) {
		if (b instanceof AbstractDataset) {
			AbstractDataset bds = (AbstractDataset) b;
			checkCompatibility(bds);

			IndexIterator it1 = getIterator();
			IndexIterator it2 = bds.getIterator();

			while (it1.hasNext() && it2.hasNext()) {
			}
		} else {
			IndexIterator it1 = getIterator();

			while (it1.hasNext()) {
			}
		}
		setDirty();
		return this;
	}

	@Override
	public StringDatasetBase imultiply(final Object b) {
		if (b instanceof AbstractDataset) {
			AbstractDataset bds = (AbstractDataset) b;
			checkCompatibility(bds);

			IndexIterator it1 = getIterator();
			IndexIterator it2 = bds.getIterator();

			while (it1.hasNext() && it2.hasNext()) {
			}
		} else {
			IndexIterator it1 = getIterator();

			while (it1.hasNext()) {
			}
		}
		setDirty();
		return this;
	}

	@Override
	public StringDatasetBase idivide(final Object b) {
		if (b instanceof AbstractDataset) {
			AbstractDataset bds = (AbstractDataset) b;
			checkCompatibility(bds);

			IndexIterator it1 = getIterator();
			IndexIterator it2 = bds.getIterator();

			while (it1.hasNext() && it2.hasNext()) {
			}
		} else {
			// if (v == 0) { // INT_ZEROTEST
			// 	fill(0); // INT_ZEROTEST
			// } else { // INT_ZEROTEST
			IndexIterator it1 = getIterator();

			while (it1.hasNext()) {
			}
			// } // INT_ZEROTEST
		}
		setDirty();
		return this;
	}

	@Override
	public StringDatasetBase ifloor() {
		return this;
	}

	@Override
	public StringDatasetBase iremainder(final Object b) {
			// if (v == 0) { // INT_ZEROTEST
			// 	fill(0); // INT_ZEROTEST
			// } else { // INT_ZEROTEST
			// } // INT_ZEROTEST
		return this;
	}

	@Override
	public StringDatasetBase ipower(final Object b) {
				// if (Double.isInfinite(v) || Double.isNaN(v)) { // INT_ZEROTEST
				// 	data[it1.index] = 0; // INT_ZEROTEST
				// } else { // INT_ZEROTEST
				// } // INT_ZEROTEST
					// if (Double.isInfinite(v) || Double.isNaN(v)) { // INT_ZEROTEST
					// 	data[it1.index] = 0; // INT_ZEROTEST
					// } else { // INT_ZEROTEST
					// } // INT_ZEROTEST
					// if (Double.isInfinite(v) || Double.isNaN(v)) { // INT_ZEROTEST
					// 	data[it1.index] = 0; // INT_ZEROTEST
					// } else { // INT_ZEROTEST
					// } // INT_ZEROTEST
		return this;
	}

	@Override
	public double residual(final Object b, boolean ignoreNaNs) {
		double sum = 0;



		return sum;
	}
}

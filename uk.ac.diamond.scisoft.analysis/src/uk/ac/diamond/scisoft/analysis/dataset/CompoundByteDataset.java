/*-
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

// This is generated from CompoundDoubleDataset.java by fromcpddouble.py

package uk.ac.diamond.scisoft.analysis.dataset;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class CompoundByteDataset extends AbstractCompoundDataset {
	// pin UID to base class
	private static final long serialVersionUID = AbstractDataset.serialVersionUID;

	/**
	 * Setup the logging facilities
	 */
	transient private static final Logger compoundLogger = LoggerFactory.getLogger(CompoundByteDataset.class);

	protected byte[] data; // subclass alias // PRIM_TYPE

	@Override
	protected void setData() {
		data = (byte[]) odata; // PRIM_TYPE
	}

	protected byte[] createArray(final int size) { // PRIM_TYPE
		byte[] array = null; // PRIM_TYPE

		try {
			array = new byte[isize * size]; // PRIM_TYPE
		} catch (OutOfMemoryError e) {
			compoundLogger.error("The size of the dataset ({}) that is being created is too large "
					+ "and there is not enough memory to hold it.", size);
			throw new OutOfMemoryError("The dimensions given are too large, and there is "
					+ "not enough memory available in the Java Virtual Machine");
		}
		return array;
	}

	@Override
	public int getDtype() {
		return ARRAYINT8; // DATA_TYPE
	}

	public CompoundByteDataset() {
	}

	public CompoundByteDataset(final int itemSize) {
		isize = itemSize;
	}

	/**
	 * Create a zero-filled dataset of given item size and shape
	 * @param itemSize
	 * @param shape
	 */
	public CompoundByteDataset(final int itemSize, final int[] shape) {
		isize = itemSize;
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
	 * Copy a dataset
	 * @param dataset
	 */
	public CompoundByteDataset(final CompoundByteDataset dataset) {
		isize = dataset.isize;

		copyToView(dataset, this, true, true);
		if (dataset.stride == null || dataset.size == dataset.base.size) {
			odata = data = dataset.data.clone();
		} else {
			IndexIterator iter = dataset.getIterator();
			for (int j = 0; iter.hasNext();) {
				for (int i = 0; i < isize; i++) {
					data[j++] = dataset.data[iter.index + i];
				}
			}
		}
	}

	/**
	 * Create a dataset using given dataset
	 * @param dataset
	 */
	public CompoundByteDataset(final AbstractCompoundDataset dataset) {
		copyToView(dataset, this, true, false);
		isize = dataset.isize;

		odata = data = createArray(size);

		IndexIterator iter = dataset.getIterator();
		for (int j = 0; iter.hasNext();) {
			for (int i = 0; i < isize; i++) {
				data[j++] = (byte) dataset.getElementLongAbs(iter.index + i); // GET_ELEMENT_WITH_CAST
			}
		}
	}

	/**
	 * Create a dataset using given data (elements are grouped together)
	 * @param itemSize
	 * @param data
	 * @param shape
	 *            (can be null to create 1D dataset)
	 */
	public CompoundByteDataset(final int itemSize, final byte[] data, int... shape) { // PRIM_TYPE
		isize = itemSize;
		if (shape == null || shape.length == 0) {
			shape = new int[] { data.length / isize };
		}
		size = calcSize(shape);
		if (size * isize != data.length) {
			throw new IllegalArgumentException(String.format("Shape %s is not compatible with size of data array, %d",
					Arrays.toString(shape), data.length / isize));
		}
		this.shape = shape.clone();

		odata = this.data = data;
	}

	/**
	 * Create a dataset using given datasets
	 * @param datasets
	 */
	public CompoundByteDataset(final ADataset... datasets) {
		if (datasets.length < 1) {
			throw new IllegalArgumentException("Array of datasets must have length greater than zero");
		}

		for (int i = 1; i < datasets.length; i++)
			datasets[0].checkCompatibility(datasets[i]);

		isize = datasets.length;
		size = calcSize(datasets[0].getShapeRef());
		shape = datasets[0].getShape();

		odata = data = createArray(size);

		IndexIterator[] iters = new IndexIterator[isize];
		for (int i = 0; i < datasets.length; i++)
			iters[i] = datasets[i].getIterator();

		for (int j = 0; iters[0].hasNext();) {
			data[j++] = (byte) datasets[0].getElementLongAbs(iters[0].index); // GET_ELEMENT_WITH_CAST
			for (int i = 1; i < datasets.length; i++) {
				iters[i].hasNext();
				data[j++] = (byte) datasets[i].getElementLongAbs(iters[i].index); // GET_ELEMENT_WITH_CAST
			}
		}
	}

	/**
	 * Cast a dataset to this compound type. If repeat is set, the first element of each item in the given dataset is
	 * repeated across all elements of an item. Otherwise, each item comprises a truncated or zero-padded copy of
	 * elements from the given dataset.
	 * @param itemSize
	 * @param repeat
	 *            repeat first element
	 * @param dataset
	 */
	public CompoundByteDataset(final int itemSize, final boolean repeat, final ADataset dataset) {
		isize = itemSize;
		size = dataset.getSize();
		shape = dataset.getShape();
		name = new String(dataset.getName());

		odata = data = createArray(size);
		final int os = dataset.getElementsPerItem();

		IndexIterator iter = dataset.getIterator();
		if (repeat) {
			int i = 0;
			while (iter.hasNext()) {
				final byte v = (byte) dataset.getElementLongAbs(iter.index); // PRIM_TYPE // GET_ELEMENT_WITH_CAST
				for (int k = 0; k < isize; k++)
					data[i++] = v;
			}
		} else {
			final int kmax = Math.min(isize, os);
			int i = 0;
			while (iter.hasNext()) {
				for (int k = 0; k < kmax; k++)
					data[i + k] = (byte) dataset.getElementLongAbs(iter.index + k); // GET_ELEMENT_WITH_CAST
				i += isize;
			}
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}

		if (getRank() == 0) // already true for zero-rank dataset
			return true;

		CompoundByteDataset other = (CompoundByteDataset) obj;
		IndexIterator iter = getIterator();
		IndexIterator oiter = other.getIterator();
		while (iter.hasNext() && oiter.hasNext()) {
			for (int j = 0; j < isize; j++) {
				if (data[iter.index+j] != other.data[oiter.index+j])
					return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	/**
	 * Create a dataset from an object which could be a PySequence, a Java array (of arrays...) or Number. Ragged
	 * sequences or arrays are padded with zeros.
	 *
	 * @param obj
	 * @return dataset with contents given by input
	 */
	public static CompoundByteDataset createFromObject(final Object obj) {
		ByteDataset result = ByteDataset.createFromObject(obj); // CLASS_TYPE
		return (CompoundByteDataset) DatasetUtils.createCompoundDatasetFromLastAxis(result, true);
	}

	/**
	 * @param stop
	 * @return a new 1D dataset, filled with values determined by parameters
	 */
	public static CompoundByteDataset arange(final int itemSize, final double stop) {
		return arange(itemSize, 0., stop, 1.);
	}

	/**
	 * @param start
	 * @param stop
	 * @param step
	 * @return a new 1D dataset, filled with values determined by parameters
	 */
	public static CompoundByteDataset arange(final int itemSize, final double start, final double stop,
			final double step) {
		int size = calcSteps(start, stop, step);
		CompoundByteDataset result = new CompoundByteDataset(itemSize, new int[] { size });
		for (int i = 0; i < size; i++) {
			result.data[i * result.isize] = (byte) (start + i * step); // PRIM_TYPE // ADD_CAST
		}
		return result;
	}

	/**
	 * @param shape
	 * @return a dataset filled with ones
	 */
	public static CompoundByteDataset ones(final int itemSize, final int... shape) {
		return new CompoundByteDataset(itemSize, shape).fill(1);
	}

	/**
	 * @param obj
	 * @return dataset filled with given object
	 */
	@Override
	public CompoundByteDataset fill(final Object obj) {
		IndexIterator iter = getIterator();
		byte[] vr = toByteArray(obj, isize); // PRIM_TYPE // CLASS_TYPE

		while (iter.hasNext()) {
			for (int i = 0; i < isize; i++)
				data[iter.index + i] = vr[i]; // PRIM_TYPE
		}

		return this;
	}

	/**
	 * This is a typed version of {@link #getBuffer()}
	 * @return data buffer as linear array
	 */
	public byte[] getData() { // PRIM_TYPE
		return data;
	}

	@Override
	protected int getBufferLength() {
		if (data == null)
			return 0;
		return data.length;
	}

	@Override
	public CompoundByteDataset getView() {
		CompoundByteDataset view = new CompoundByteDataset(isize);
		copyToView(this, view, true, true);
		view.data = data;
		return view;
	}

	/**
	 * Get values at absolute index in the internal array. This is an internal method with no checks so can be
	 * dangerous. Use with care or ideally with an iterator.
	 *
	 * @param index
	 *            absolute index
	 * @return values
	 */
	public byte[] getAbs(final int index) { // PRIM_TYPE
		byte[] result = new byte[isize]; // PRIM_TYPE
		for (int i = 0; i < isize; i++)
			result[i] = data[index + i];
		return result;
	}

	/**
	 * Get values at absolute index in the internal array. This is an internal method with no checks so can be
	 * dangerous. Use with care or ideally with an iterator.
	 *
	 * @param index
	 *            absolute index
	 * @param values
	 */
	public void getAbs(final int index, final byte[] values) { // PRIM_TYPE
		for (int i = 0; i < isize; i++)
			values[i] = data[index + i];
	}

	@Override
	public boolean getElementBooleanAbs(final int index) {
		for (int i = 0; i < isize; i++) {
			if (data[index + i] == 0) {
				return false;
			}
		}
		return true;
	}

	@Override
	public double getElementDoubleAbs(final int index) {
		return data[index];
	}

	@Override
	public long getElementLongAbs(final int index) {
		return data[index]; // OMIT_CAST_INT
	}

	@Override
	protected void setItemDirect(final int dindex, final int sindex, final Object src) {
		byte[] dsrc = (byte[]) src; // PRIM_TYPE
		for (int i = 0; i < isize; i++)
			data[dindex + i] = dsrc[sindex + i];
	}

	/**
	 * Set values at absolute index in the internal array. This is an internal method with no checks so can be
	 * dangerous. Use with care or ideally with an iterator.
	 *
	 * @param index
	 *            absolute index
	 * @param val
	 *            new values
	 */
	public void setAbs(final int index, final byte[] val) { // PRIM_TYPE
		for (int i = 0; i < isize; i++)
			data[index + i] = val[i];
		setDirty();
	}

	/**
	 * Set element value at absolute index in the internal array. This is an internal method with no checks so can be
	 * dangerous. Use with care or ideally with an iterator.
	 *
	 * @param index
	 *            absolute index
	 * @param val
	 *            new value
	 */
	public void setAbs(final int index, final byte val) { // PRIM_TYPE
		data[index] = val;
		setDirty();
	}

	@Override
	public Object getObject(final int... pos) {
		return getByteArray(pos); // CLASS_TYPE
	}

	/**
	 * @param pos
	 * @return item in given position
	 */
	public byte[] getByteArray(final int... pos) { // CLASS_TYPE // PRIM_TYPE
		return (byte[]) getObjectAbs(get1DIndex(pos)); // PRIM_TYPE
	}

	@Override
	public void getDoubleArrayAbs(final int index, final double[] darray) {
		for (int i = 0; i < isize; i++)
			darray[i] = data[index + i];
	}

	@Override
	public String getString(final int... pos) {
		return getStringAbs(get1DIndex(pos));
	}

	@Override
	protected double getFirstValue(final int... pos) {
		return data[get1DIndex(pos)];
	}

	@Override
	public Object getObjectAbs(final int index) {
		byte[] result = new byte[isize]; // PRIM_TYPE
		for (int i = 0; i < isize; i++)
			result[i] = data[index + i];
		return result;
	}

	@Override
	public String getStringAbs(final int index) {
		StringBuilder s = new StringBuilder();
		s.append('(');
		s.append(String.format("%d", data[index])); // FORMAT_STRING
		for (int i = 1; i < isize; i++) {
			s.append(' ');
			s.append(String.format("%d", data[index + i])); // FORMAT_STRING
		}
		s.append(')');
		return s.toString();
	}

	@Override
	public void setObjectAbs(final int index, final Object obj) {
		byte[] oa = toByteArray(obj, isize); // PRIM_TYPE // CLASS_TYPE
		setAbs(index, oa);
	}

	@Override
	public void set(final Object obj, int... pos) {
		if (pos == null || pos.length == 0) {
			pos = new int[shape.length];
		}

		setItem(toByteArray(obj, isize), pos); // CLASS_TYPE
	}

	/**
	 * Set values at given position
	 *
	 * @param d
	 * @param pos
	 */
	public void setItem(final byte[] d, final int... pos) { // PRIM_TYPE
		if (!isPositionInShape(pos)) {
			throw new ArrayIndexOutOfBoundsException("Index out of bounds");
		}
		if (d.length > isize) {
			throw new IllegalArgumentException("Array is larger than number of elements in an item");
		}
		setAbs(get1DIndex(pos), d);
	}

	private void setDoubleArrayAbs(final int index, final double[] d) {
		for (int i = 0; i < isize; i++)
			data[index + i] = (byte) d[i]; // ADD_CAST
	}

	@Override
	public void resize(int... newShape) {
		IndexIterator iter = getIterator();
		int nsize = calcSize(newShape);
		byte[] ndata = createArray(nsize); // PRIM_TYPE

		int i = 0;
		while (iter.hasNext() && i < nsize) {
			for (int j = 0; j < isize; j++) {
				ndata[i++] = data[iter.index + j];
			}
		}

		odata = data = ndata;
		size = nsize;
		shape = newShape;
		stride = null;
		offset = 0;
		base = null;
	}

	@Override
	public ByteDataset real() { // CLASS_TYPE
		ByteDataset rdataset = new ByteDataset(shape); // CLASS_TYPE
		IndexIterator iter = getIterator();
		IndexIterator riter = rdataset.getIterator();

		byte[] rdata = rdataset.data; // PRIM_TYPE
		while (iter.hasNext() && riter.hasNext())
			rdata[riter.index] = data[iter.index];

		return rdataset;
	}

	@Override
	public CompoundByteDataset getSlice(final SliceIterator siter) {
		CompoundByteDataset result = new CompoundByteDataset(isize, siter.getShape());
		byte[] rdata = result.data; // PRIM_TYPE
		IndexIterator riter = result.getIterator();

		while (siter.hasNext() && riter.hasNext()) {
			for (int i = 0; i < isize; i++)
				rdata[riter.index + i] = data[siter.index + i];
		}

		result.setName(name + BLOCK_OPEN + createSliceString(siter.shape, siter.start, siter.stop, siter.step) + BLOCK_CLOSE);
		return result;
	}

	@Override
	public AbstractDataset getElements(int element) {
		final ByteDataset elements = new ByteDataset(shape); // CLASS_TYPE

		copyElements(elements, element);
		return elements;
	}

	@Override
	public void copyElements(ADataset destination, int element) {
		if (element < 0)
			element += isize;
		if (element < 0 || element > isize) {
			throw new IllegalArgumentException(String.format("Invalid choice of element: %d/%d", element, isize));
		}
		if (elementClass() != destination.elementClass()) {
			throw new IllegalArgumentException("Element class of destination does not match this dataset");
		}

		final IndexIterator it = getIterator(element);
		final byte[] elements = ((ByteDataset) destination).data; // CLASS_TYPE // PRIM_TYPE

		int n = 0;
		while (it.hasNext()) {
			elements[n] = data[it.index];
			n++;
		}
	}

	@Override
	public void setElements(ADataset source, int element) {
		if (element < 0)
			element += isize;
		if (element < 0 || element > isize) {
			throw new IllegalArgumentException(String.format("Invalid choice of element: %d/%d", element, isize));
		}
		if (elementClass() != source.elementClass()) {
			throw new IllegalArgumentException("Element class of destination does not match this dataset");
		}

		final IndexIterator it = getIterator(element);
		final byte[] elements = ((ByteDataset) source).data; // CLASS_TYPE // PRIM_TYPE

		int n = 0;
		while (it.hasNext()) {
			data[it.index] = elements[n];
			n++;
		}
	}

	@Override
	public void fillDataset(ADataset result, IndexIterator iter) {
		IndexIterator riter = result.getIterator();

		byte[] rdata = ((CompoundByteDataset) result).data; // PRIM_TYPE

		while (riter.hasNext() && iter.hasNext()) {
			for (int i = 0; i < isize; i++)
				rdata[riter.index + i] = data[iter.index + i];
		}
	}

	@Override
	public CompoundByteDataset setByBoolean(final Object o, ADataset selection) {
		if (o instanceof ADataset) {
			ADataset ds = (ADataset) o;
			final int length = ((Number) selection.sum()).intValue();
			if (length != ds.getSize()) {
				throw new IllegalArgumentException(
						"Number of true items in selection does not match number of items in dataset");
			}

			IndexIterator iter = ds.getIterator();
			BooleanIterator biter = getBooleanIterator(selection);

			if (ds instanceof AbstractCompoundDataset) {
				if (isize != ds.getElementsPerItem()) {
					throw new IllegalArgumentException("Input dataset is not compatible with slice");
				}

				while (biter.hasNext() && iter.hasNext()) {
					for (int i = 0; i < isize; i++)
						data[biter.index + i] = (byte) ds.getElementLongAbs(iter.index + i); // GET_ELEMENT_WITH_CAST
				}
			} else {
				while (biter.hasNext() && iter.hasNext()) {
					data[biter.index] = (byte) ds.getElementLongAbs(iter.index); // GET_ELEMENT_WITH_CAST
					for (int i = 1; i < isize; i++)
						data[biter.index + i] = 0;
				}
			}
		} else {
			try {
				final byte[] vr = toByteArray(o, isize); // PRIM_TYPE // CLASS_TYPE

				final BooleanIterator biter = getBooleanIterator(selection);

				while (biter.hasNext()) {
					for (int i = 0; i < isize; i++)
						data[biter.index + i] = vr[i];
				}
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("Object for setting is not a dataset or number");
			}
		}
		setDirty();
		return this;
	}

	@Override
	public CompoundByteDataset setByIndex(final Object o, ADataset index) {
		if (o instanceof ADataset) {
			ADataset ds = (ADataset) o;
			if (index.getSize() != ds.getSize()) {
				throw new IllegalArgumentException(
						"Number of items in selection does not match number of items in dataset");
			}

			IndexIterator oiter = ds.getIterator();
			final IntegerIterator iter = new IntegerIterator(index, size, isize);

			if (ds instanceof AbstractCompoundDataset) {
				if (isize != ds.getElementsPerItem()) {
					throw new IllegalArgumentException("Input dataset is not compatible with slice");
				}

				double[] temp = new double[isize];
				while (iter.hasNext() && oiter.hasNext()) {
					((AbstractCompoundDataset) ds).getDoubleArrayAbs(oiter.index, temp);
					setDoubleArrayAbs(iter.index, temp);
				}
				while (iter.hasNext() && oiter.hasNext()) {
					for (int i = 0; i < isize; i++)
						data[iter.index + i] = (byte) ds.getElementLongAbs(oiter.index + i); // GET_ELEMENT_WITH_CAST
				}
			} else {
				while (iter.hasNext() && oiter.hasNext()) {
					data[iter.index] = (byte) ds.getElementLongAbs(oiter.index); // GET_ELEMENT_WITH_CAST
					for (int i = 1; i < isize; i++)
						data[iter.index + i] = 0;
				}
			}
		} else {
			try {
				final byte[] vr = toByteArray(o, isize); // PRIM_TYPE // CLASS_TYPE

				final IntegerIterator iter = new IntegerIterator(index, size, isize);

				while (iter.hasNext()) {
					setAbs(iter.index, vr);
				}
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("Object for setting is not a dataset or number");
			}
		}
		setDirty();
		return this;
	}

	@Override
	public CompoundByteDataset setByIndexes(final Object o, final Object... index) {
		final IntegersIterator iter = new IntegersIterator(shape, index);
		final int[] pos = iter.getPos();

		if (o instanceof ADataset) {
			ADataset ds = (ADataset) o;
			if (calcSize(iter.getShape()) != ds.getSize()) {
				throw new IllegalArgumentException(
						"Number of items in selection does not match number of items in dataset");
			}

			IndexIterator oiter = ds.getIterator();

			if (ds instanceof AbstractCompoundDataset) {
				if (isize != ds.getElementsPerItem()) {
					throw new IllegalArgumentException("Input dataset is not compatible with slice");
				}

				double[] temp = new double[isize];
				while (iter.hasNext() && oiter.hasNext()) {
					((AbstractCompoundDataset) ds).getDoubleArray(temp, pos);
					setDoubleArrayAbs(get1DIndex(pos), temp);
				}
			} else {
				while (iter.hasNext() && oiter.hasNext()) {
					int n = get1DIndex(pos);
					data[n] = (byte) ds.getElementLongAbs(oiter.index); // GET_ELEMENT_WITH_CAST
					for (int i = 1; i < isize; i++)
						data[n + i] = 0;
				}
			}
		} else {
			try {
				final byte[] vr = toByteArray(o, isize); // PRIM_TYPE // CLASS_TYPE

				while (iter.hasNext()) {
					setAbs(get1DIndex(pos), vr);
				}
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("Object for setting is not a dataset or number");
			}
		}
		setDirty();
		return this;
	}

	@Override
	public CompoundByteDataset setSlice(final Object o, final IndexIterator siter) {
		if (o instanceof IDataset) {
			final IDataset ds = (IDataset) o;
			final int[] oshape = ds.getShape();

			if (!areShapesCompatible(siter.getShape(), oshape)) {
				throw new IllegalArgumentException(String.format(
						"Input dataset is not compatible with slice: %s cf %s", Arrays.toString(oshape),
						Arrays.toString(siter.getShape())));
			}

			if (ds instanceof ADataset) {
				final ADataset ads = (ADataset) ds;
				IndexIterator oiter = ads.getIterator();

				if (ds instanceof AbstractCompoundDataset) {
					if (isize != ads.getElementsPerItem()) {
						throw new IllegalArgumentException("Input dataset is not compatible with slice");
					}

					while (siter.hasNext() && oiter.hasNext()) {
						for (int i = 0; i < isize; i++)
							data[siter.index + i] = (byte) ads.getElementLongAbs(oiter.index + i); // GET_ELEMENT_WITH_CAST
					}
				} else {
					while (siter.hasNext() && oiter.hasNext()) {
						data[siter.index] = (byte) ads.getElementLongAbs(oiter.index); // GET_ELEMENT_WITH_CAST
						for (int i = 1; i < isize; i++)
							data[siter.index + i] = 0;
					}
				}
			} else {
				final IndexIterator oiter = new PositionIterator(oshape);
				final int[] pos = oiter.getPos();

				if (ds.getElementsPerItem() == 1) {
					while (siter.hasNext() && oiter.hasNext()) {
						data[siter.index] = ds.getByte(pos); // PRIM_TYPE
						for (int i = 1; i < isize; i++)
							data[siter.index + i] = 0;
					}
				} else {
					while (siter.hasNext() && oiter.hasNext()) {
						final byte[] val = toByteArray(ds.getObject(pos), isize); // PRIM_TYPE // CLASS_TYPE
						for (int i = 0; i < isize; i++)
							data[siter.index + i] = val[i];
					}
				}
			}
		} else {
			try {
				final byte[] vr = toByteArray(o, isize); // PRIM_TYPE // CLASS_TYPE

				while (siter.hasNext()) {
					for (int i = 0; i < isize; i++)
						data[siter.index + i] = vr[i];
				}
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("Object for setting slice is not a dataset or number");
			}
		}
		setDirty();
		return this;
	}

	@Override
	public void copyItemsFromAxes(final int[] pos, final boolean[] axes, final ADataset dest) {
		byte[] ddata = (byte[]) dest.getBuffer(); // PRIM_TYPE

		if (dest.getElementsPerItem() != isize) {
			throw new IllegalArgumentException(String.format(
					"Destination dataset is incompatible as it has %d elements per item not %d",
					dest.getElementsPerItem(), isize));
		}

		SliceIterator siter = getSliceIteratorFromAxes(pos, axes);
		int[] sshape = squeezeShape(siter.getShape(), false);

		IndexIterator diter = dest.getSliceIterator(null, sshape, null);

		if (ddata.length < calcSize(sshape)) {
			throw new IllegalArgumentException("destination array is not large enough");
		}

		while (siter.hasNext() && diter.hasNext()) {
			for (int i = 0; i < isize; i++)
				ddata[diter.index + i] = data[siter.index + i];
		}
	}

	@Override
	public void setItemsOnAxes(final int[] pos, final boolean[] axes, final Object src) {
		byte[] sdata = (byte[]) src; // PRIM_TYPE

		SliceIterator siter = getSliceIteratorFromAxes(pos, axes);

		if (sdata.length < calcSize(siter.getShape())) {
			throw new IllegalArgumentException("source array is not large enough");
		}

		for (int i = 0; siter.hasNext(); i++) {
			for (int j = 0; j < isize; j++)
				data[siter.index + j] = sdata[isize * i + j];
		}

		setDirty();
	}

	/**
	 * @return true if dataset contains any NaNs
	 */
	@Override
	public boolean containsNans() {
		return false;
	}

	/**
	 * @return true if dataset contains any Infs
	 */
	@Override
	public boolean containsInfs() {
		return false;
	}

	@Override
	public boolean containsInvalidNumbers() {
		return false;
	}

	@Override
	public CompoundByteDataset iadd(final Object b) {
		if (b instanceof ADataset) {
			final ADataset bds = (ADataset) b;
			checkCompatibility(bds);

			final IndexIterator it1 = getIterator();
			final IndexIterator it2 = bds.getIterator();
			final int bis = bds.getElementsPerItem();

			if (bis == 1) {
				while (it1.hasNext() && it2.hasNext()) {
					final byte db = (byte) bds.getElementLongAbs(it2.index); // PRIM_TYPE // GET_ELEMENT_WITH_CAST
					for (int i = 0; i < isize; i++)
						data[it1.index + i] += db;
				}
			} else if (bis == isize) {
				while (it1.hasNext() && it2.hasNext()) {
					for (int i = 0; i < isize; i++)
						data[it1.index + i] += bds.getElementLongAbs(it2.index + i); // GET_ELEMENT
				}
			} else {
				throw new IllegalArgumentException(
						"Argument does not have same number of elements per item or is not a non-compound dataset");
			}
		} else {
			final byte[] vr = toByteArray(b, isize); // PRIM_TYPE // CLASS_TYPE
			final IndexIterator it1 = getIterator();

			while (it1.hasNext()) {
				for (int i = 0; i < isize; i++)
					data[it1.index + i] += vr[i];
			}
		}
		setDirty();
		return this;
	}

	@Override
	public CompoundByteDataset isubtract(final Object b) {
		if (b instanceof ADataset) {
			final ADataset bds = (ADataset) b;
			checkCompatibility(bds);

			final IndexIterator it1 = getIterator();
			final IndexIterator it2 = bds.getIterator();
			final int bis = bds.getElementsPerItem();

			if (bis == 1) {
				while (it1.hasNext() && it2.hasNext()) {
					final byte db = (byte) bds.getElementLongAbs(it2.index); // PRIM_TYPE // GET_ELEMENT_WITH_CAST
					for (int i = 0; i < isize; i++)
						data[it1.index + i] -= db;
				}
			} else if (bis == isize) {
				while (it1.hasNext() && it2.hasNext()) {
					for (int i = 0; i < isize; i++)
						data[it1.index + i] -= bds.getElementLongAbs(it2.index + i); // GET_ELEMENT
				}
			} else {
				throw new IllegalArgumentException(
						"Argument does not have same number of elements per item or is not a non-compound dataset");
			}
		} else {
			final byte[] vr = toByteArray(b, isize); // PRIM_TYPE // CLASS_TYPE
			final IndexIterator it1 = getIterator();

			while (it1.hasNext()) {
				for (int i = 0; i < isize; i++)
					data[it1.index + i] -= vr[i];
			}
		}
		setDirty();
		return this;
	}

	@Override
	public CompoundByteDataset imultiply(final Object b) {
		if (b instanceof ADataset) {
			final ADataset bds = (ADataset) b;
			checkCompatibility(bds);

			final IndexIterator it1 = getIterator();
			final IndexIterator it2 = bds.getIterator();
			final int bis = bds.getElementsPerItem();

			if (bis == 1) {
				while (it1.hasNext() && it2.hasNext()) {
					final byte db = (byte) bds.getElementLongAbs(it2.index); // PRIM_TYPE // GET_ELEMENT_WITH_CAST
					for (int i = 0; i < isize; i++)
						data[it1.index + i] *= db;
				}
			} else if (bis == isize) {
				while (it1.hasNext() && it2.hasNext()) {
					for (int i = 0; i < isize; i++)
						data[it1.index + i] *= bds.getElementLongAbs(it2.index + i); // GET_ELEMENT
				}
			} else {
				throw new IllegalArgumentException(
						"Argument does not have same number of elements per item or is not a non-compound dataset");
			}
		} else {
			final byte[] vr = toByteArray(b, isize); // PRIM_TYPE // CLASS_TYPE
			final IndexIterator it1 = getIterator();

			while (it1.hasNext()) {
				for (int i = 0; i < isize; i++)
					data[it1.index + i] *= vr[i];
			}
		}
		setDirty();
		return this;
	}

	@Override
	public CompoundByteDataset idivide(final Object b) {
		if (b instanceof ADataset) {
			final ADataset bds = (ADataset) b;
			checkCompatibility(bds);

			final IndexIterator it1 = getIterator();
			final IndexIterator it2 = bds.getIterator();
			final int bis = bds.getElementsPerItem();

			if (bis == 1) {
				while (it1.hasNext() && it2.hasNext()) {
					final byte db = (byte) bds.getElementLongAbs(it2.index); // PRIM_TYPE // GET_ELEMENT_WITH_CAST
					if (db == 0) { // INT_ZEROTEST
					for (int i = 0; i < isize; i++) // INT_ZEROTEST
						data[it1.index + i] = 0; // INT_ZEROTEST
					} else { // INT_ZEROTEST
					for (int i = 0; i < isize; i++)
						data[it1.index + i] /= db;
					} // INT_ZEROTEST
				}
			} else if (bis == isize) {
				while (it1.hasNext() && it2.hasNext()) {
					for (int i = 0; i < isize; i++) {
						try {
							data[it1.index + i] /= bds.getElementLongAbs(it2.index + i); // GET_ELEMENT // INT_EXCEPTION
						} catch (ArithmeticException e) {
							data[it1.index + i] = 0;
						}
					}
				}
			} else {
				throw new IllegalArgumentException(
						"Argument does not have same number of elements per item or is not a non-compound dataset");
			}
		} else {
			final double[] vr = toDoubleArray(b, isize);
			final IndexIterator it1 = getIterator();

			while (it1.hasNext()) {
				for (int i = 0; i < isize; i++) {
					if (vr[i] == 0) { // INT_ZEROTEST
						data[it1.index + i] = 0; // INT_ZEROTEST
					} else { // INT_ZEROTEST
					data[it1.index + i] /= vr[i];
					} // INT_ZEROTEST
				}
			}
		}
		setDirty();
		return this;
	}

	@Override
	public CompoundByteDataset ifloor() {
		return this;
	}

	@Override
	public CompoundByteDataset iremainder(final Object b) {
		if (b instanceof ADataset) {
			final ADataset bds = (ADataset) b;
			checkCompatibility(bds);

			final IndexIterator it1 = getIterator();
			final IndexIterator it2 = bds.getIterator();
			final int bis = bds.getElementsPerItem();

			if (bis == 1) {
				while (it1.hasNext() && it2.hasNext()) {
					final byte db = (byte) bds.getElementLongAbs(it2.index); // PRIM_TYPE // GET_ELEMENT_WITH_CAST
					if (db == 0) { // INT_ZEROTEST
					for (int i = 0; i < isize; i++) // INT_ZEROTEST
						data[it1.index + i] = 0; // INT_ZEROTEST
					} else { // INT_ZEROTEST
					for (int i = 0; i < isize; i++)
						data[it1.index + i] %= db;
					} // INT_ZEROTEST
				}
			} else if (bis == isize) {
				while (it1.hasNext() && it2.hasNext()) {
					for (int i = 0; i < isize; i++) {
						try {
							data[it1.index + i] %= bds.getElementLongAbs(it2.index + i); // GET_ELEMENT // INT_EXCEPTION
						} catch (ArithmeticException e) {
							data[it1.index + i] = 0;
						}
					}
				}
			} else {
				throw new IllegalArgumentException(
						"Argument does not have same number of elements per item or is not a non-compound dataset");
			}
		} else {
			final double[] vr = toDoubleArray(b, isize);
			final IndexIterator it1 = getIterator();

			while (it1.hasNext()) {
				for (int i = 0; i < isize; i++) {
					if (vr[i] == 0) { // INT_ZEROTEST
						data[it1.index + i] = 0; // INT_ZEROTEST
					} else { // INT_ZEROTEST
					data[it1.index + i] %= vr[i];
					} // INT_ZEROTEST
				}
			}
		}
		setDirty();
		return this;
	}

	@Override
	public CompoundByteDataset ipower(final Object b) {
		if (b instanceof ADataset) {
			final ADataset bds = (ADataset) b;
			checkCompatibility(bds);

			final IndexIterator it1 = getIterator();
			final IndexIterator it2 = bds.getIterator();
			final int bis = bds.getElementsPerItem();

			if (bis == 1) {
				while (it1.hasNext() && it2.hasNext()) {
					final byte db = (byte) bds.getElementLongAbs(it2.index); // PRIM_TYPE // GET_ELEMENT_WITH_CAST
					for (int i = 0; i < isize; i++) {
						final double v = Math.pow(data[it1.index + i], db);
						if (Double.isInfinite(v) || Double.isNaN(v)) { // INT_ZEROTEST
							data[it1.index + i] = 0; // INT_ZEROTEST // CLASS_TYPE
						} else { // INT_ZEROTEST
						data[it1.index + i] = (byte) (long) v; // PRIM_TYPE_LONG // ADD_CAST
						} // INT_ZEROTEST
					}
				}
			} else if (bis == isize) {
				while (it1.hasNext() && it2.hasNext()) {
					for (int i = 0; i < isize; i++) {
						final double v = Math.pow(data[it1.index + i], bds.getElementDoubleAbs(it2.index + i));
						if (Double.isInfinite(v) || Double.isNaN(v)) { // INT_ZEROTEST
							data[it1.index + i] = 0; // INT_ZEROTEST // CLASS_TYPE
						} else { // INT_ZEROTEST
						data[it1.index + i] = (byte) (long) v; // PRIM_TYPE_LONG // ADD_CAST
						} // INT_ZEROTEST
					}
				}
			} else {
				throw new IllegalArgumentException(
						"Argument does not have same number of elements per item or is not a non-compound dataset");
			}
		} else {
			final byte[] vr = toByteArray(b, isize); // PRIM_TYPE // CLASS_TYPE
			final IndexIterator it1 = getIterator();

			while (it1.hasNext()) {
				for (int i = 0; i < isize; i++) {
					final double v = Math.pow(data[it1.index + i], vr[i]);
					if (Double.isInfinite(v) || Double.isNaN(v)) { // INT_ZEROTEST
						data[it1.index + i] = 0; // INT_ZEROTEST // CLASS_TYPE
					} else { // INT_ZEROTEST
					data[it1.index + i] = (byte) (long) v; // PRIM_TYPE_LONG // ADD_CAST
					} // INT_ZEROTEST
				}
			}
		}
		setDirty();
		return this;
	}

	@Override
	public double residual(final Object b, final ADataset w, boolean ignoreNaNs) {
		double sum = 0;
		if (b instanceof ADataset) {
			final ADataset bds = (ADataset) b;
			checkCompatibility(bds);

			final IndexIterator it1 = getIterator();
			final IndexIterator it2 = bds.getIterator();
			final int bis = bds.getElementsPerItem();

			if (bis == 1) {
				double comp = 0;
				if (w == null) {
					while (it1.hasNext() && it2.hasNext()) {
						final double db = bds.getElementDoubleAbs(it2.index);
						if (ignoreNaNs) {
							if (Double.isNaN(db))
								continue;
							boolean skip = false;
							for (int i = 0; i < isize; i++) {
								if (Double.isNaN(data[it1.index + i])) {
									skip = true;
									break;
								}
							}
							if (skip) {
								continue;
							}
						}
						for (int i = 0; i < isize; i++) {
							final double diff = data[it1.index + i] - db;
							final double err = diff * diff - comp;
							final double temp = sum + err;
							comp = (temp - sum) - err;
							sum = temp;
						}
					}
				} else {
					final IndexIterator it3 = w.getIterator();
					while (it1.hasNext() && it2.hasNext() && it3.hasNext()) {
						final double db = bds.getElementDoubleAbs(it2.index);
						final double dw = w.getElementDoubleAbs(it3.index);
						if (ignoreNaNs) {
							if (Double.isNaN(db))
								continue;
							boolean skip = false;
							for (int i = 0; i < isize; i++) {
								if (Double.isNaN(data[it1.index + i])) {
									skip = true;
									break;
								}
							}
							if (skip) {
								continue;
							}
						}
						for (int i = 0; i < isize; i++) {
							final double diff = (data[it1.index + i] - db);
							final double err = diff * diff * dw - comp;
							final double temp = sum + err;
							comp = (temp - sum) - err;
							sum = temp;
						}
					}
				}
			} else if (bis == isize) {
				double comp = 0;
				if (w == null) {
					while (it1.hasNext() && it2.hasNext()) {
						if (ignoreNaNs) {
							boolean skip = false;
							for (int i = 0; i < isize; i++) {
								if (Double.isNaN(data[it1.index + i])
										|| Double.isNaN(bds.getElementDoubleAbs(it2.index + i))) {
									skip = true;
									break;
								}
							}
							if (skip) {
								continue;
							}
						}
						for (int i = 0; i < isize; i++) {
							final double diff = data[it1.index + i] - bds.getElementDoubleAbs(it2.index + i);
							final double err = diff * diff - comp;
							final double temp = sum + err;
							comp = (temp - sum) - err;
							sum = temp;
						}
					}
				} else {
					final IndexIterator it3 = w.getIterator();
					while (it1.hasNext() && it2.hasNext() && it3.hasNext()) {
						if (ignoreNaNs) {
							boolean skip = false;
							for (int i = 0; i < isize; i++) {
								if (Double.isNaN(data[it1.index + i])
										|| Double.isNaN(bds.getElementDoubleAbs(it2.index + i))) {
									skip = true;
									break;
								}
							}
							if (skip) {
								continue;
							}
						}
						final double dw = w.getElementDoubleAbs(it3.index);
						for (int i = 0; i < isize; i++) {
							final double diff = (data[it1.index + i] - bds.getElementDoubleAbs(it2.index + i));
							final double err = diff * diff * dw - comp;
							final double temp = sum + err;
							comp = (temp - sum) - err;
							sum = temp;
						}
					}
				}
			} else {
				throw new IllegalArgumentException(
						"Argument does not have same number of elements per item or is not a non-compound dataset");
			}
		} else {
			final double[] vr = toDoubleArray(b, isize);
			final IndexIterator it1 = getIterator();

			if (ignoreNaNs) {
				boolean skip = false;
				for (int i = 0; i < isize; i++) {
					if (Double.isNaN(vr[i])) {
						skip = true;
						break;
					}
				}
				if (skip) {
					return sum;
				}
			}

			double comp = 0;
			if (w == null) {
				while (it1.hasNext()) {
					if (ignoreNaNs) {
						boolean skip = false;
						for (int i = 0; i < isize; i++) {
							if (Double.isNaN(data[it1.index + i])) {
								skip = true;
								break;
							}
						}
						if (skip) {
							continue;
						}
					}
					for (int i = 0; i < isize; i++) {
						final double diff = data[it1.index + i] - vr[i];
						final double err = diff * diff - comp;
						final double temp = sum + err;
						comp = (temp - sum) - err;
						sum = temp;
					}
				}
			} else {
				final IndexIterator it3 = w.getIterator();
				while (it1.hasNext() && it3.hasNext()) {
					if (ignoreNaNs) {
						boolean skip = false;
						for (int i = 0; i < isize; i++) {
							if (Double.isNaN(data[it1.index + i])) {
								skip = true;
								break;
							}
						}
						if (skip) {
							continue;
						}
					}
					final double dw = w.getElementDoubleAbs(it3.index);
					for (int i = 0; i < isize; i++) {
						final double diff = data[it1.index + i] - vr[i];
						final double err = diff * diff * dw - comp;
						final double temp = sum + err;
						comp = (temp - sum) - err;
						sum = temp;
					}
				}
			}
		}
		return sum;
	}
}

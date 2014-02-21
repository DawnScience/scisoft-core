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

package uk.ac.diamond.scisoft.analysis.dataset;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Extend boolean base dataset for boolean values
 */
public class BooleanDataset extends BooleanDatasetBase {
	// pin UID to base class
	private static final long serialVersionUID = AbstractDataset.serialVersionUID;

	/**
	 * Setup the logging facilities
	 */
	transient private static final Logger logger = LoggerFactory.getLogger(BooleanDataset.class);

	public BooleanDataset() {
		super();
	}

	/**
	 * Create a false-filled dataset of given shape
	 * @param shape
	 */
	public BooleanDataset(final int... shape) {
		super(shape);
	}

	/**
	 * Create a dataset using given data
	 * @param data
	 * @param shape (can be null to create 1D dataset)
	 */
	public BooleanDataset(final boolean[] data, int... shape) {
		super(data, shape);
	}

	/**
	 * Copy a dataset
	 * @param dataset
	 */
	public BooleanDataset(final BooleanDataset dataset) {
		super(dataset);
	}

	/**
	 * Cast a dataset to this class type
	 * @param dataset
	 */
	public BooleanDataset(final AbstractDataset dataset) {
		super(dataset);
	}

	@Override
	public BooleanDataset getView() {
		BooleanDataset view = new BooleanDataset();
		copyToView(this, view, true, true);
		view.data = data;
		return view;
	}

	/**
	 * Create a dataset from an object which could be a PySequence, a Java array (of arrays...)
	 * or Number. Ragged sequences or arrays are padded with zeros.
	 * 
	 * @param obj
	 * @return dataset with contents given by input
	 */
	public static BooleanDataset createFromObject(final Object obj) {
		BooleanDatasetBase result = BooleanDatasetBase.createFromObject(obj);
		return new BooleanDataset(result.data, result.shape);
	}

	/**
	 * @param shape
	 * @return a dataset filled with trues
	 */
	public static BooleanDataset ones(final int... shape) {
		BooleanDatasetBase result = BooleanDatasetBase.ones(shape);
		return new BooleanDataset(result.data, result.shape);
	}

	@Override
	public boolean getElementBooleanAbs(final int index) {
		return data[index];
	}

	@Override
	public double getElementDoubleAbs(final int index) {
		return data[index] ? 1 : 0;
	}

	@Override
	public long getElementLongAbs(final int index) {
		return data[index] ? 1 : 0;
	}

	@Override
	public double getDouble(final int i) {
		return getInt(i);
	}

	@Override
	public double getDouble(final int i, final int j) {
		return getInt(i, j);
	}

	@Override
	public double getDouble(final int... pos) {
		return getInt(pos);
	}

	@Override
	public float getFloat(final int i) {
		return getInt(i);
	}

	@Override
	public float getFloat(final int i, final int j) {
		return getInt(i, j);
	}

	@Override
	public float getFloat(final int... pos) {
		return getInt(pos);
	}

	@Override
	public long getLong(final int i) {
		return getInt(i);
	}

	@Override
	public long getLong(final int i, final int j) {
		return getInt(i, j);
	}

	@Override
	public long getLong(final int... pos) {
		return getInt(pos);
	}

	@Override
	public int getInt(final int i) {
		return get(i) ? 1 : 0;
	}

	@Override
	public int getInt(final int i, final int j) {
		return get(i, j) ? 1 : 0;
	}

	@Override
	public int getInt(final int... pos) {
		return get(pos) ? 1 : 0;
	}

	@Override
	public short getShort(final int i) {
		return (short) getInt(i);
	}

	@Override
	public short getShort(final int i, final int j) {
		return (short) getInt(i, j);
	}

	@Override
	public short getShort(final int... pos) {
		return (short) getInt(pos);
	}

	@Override
	public byte getByte(final int i) {
		return (byte) getInt(i);
	}

	@Override
	public byte getByte(final int i, final int j) {
		return (byte) getInt(i, j);
	}

	@Override
	public byte getByte(final int... pos) {
		return (byte) getInt(pos);
	}

	@Override
	public boolean getBoolean(final int i) {
		return get(i);
	}

	@Override
	public boolean getBoolean(final int i, final int j) {
		return get(i, j);
	}

	@Override
	public boolean getBoolean(final int... pos) {
		return get(pos);
	}

	@Override
	public BooleanDataset getSlice(final int[] start, final int[] stop, final int[] step) {
		BooleanDatasetBase result = (BooleanDatasetBase) super.getSlice(start, stop, step);

		return new BooleanDataset(result.data, result.shape);
	}

	/**
	 * OR
	 */
	@Override
	public BooleanDataset iadd(final Object b) {
		if (b instanceof ADataset) {
			ADataset bds = (ADataset) b;
			checkCompatibility(bds);

			IndexIterator it1 = getIterator();
			IndexIterator it2 = bds.getIterator();

			while (it1.hasNext() && it2.hasNext()) {
				data[it1.index] |= bds.getElementBooleanAbs(it2.index);
			}
		} else {
			boolean v = toBoolean(b);
			IndexIterator it1 = getIterator();

			while (it1.hasNext()) {
				data[it1.index] |= v;
			}
		}
		setDirty();
		return this;
	}

	/**
	 * XOR
	 */
	@Override
	public BooleanDataset isubtract(final Object b) {
		if (b instanceof ADataset) {
			ADataset bds = (ADataset) b;
			checkCompatibility(bds);

			IndexIterator it1 = getIterator();
			IndexIterator it2 = bds.getIterator();

			while (it1.hasNext() && it2.hasNext()) {
				data[it1.index] ^= bds.getElementBooleanAbs(it2.index);
			}
		} else {
			boolean v = toBoolean(b);
			IndexIterator it1 = getIterator();

			if (!(b instanceof Number)) {
				logger.error("Argument is of unsupported class");
				throw new IllegalArgumentException("Argument is of unsupported class");
			}

			while (it1.hasNext()) {
				data[it1.index] ^= v;
			}
		}
		setDirty();
		return this;
	}

	/**
	 * AND
	 */
	@Override
	public BooleanDataset imultiply(final Object b) {
		if (b instanceof ADataset) {
			ADataset bds = (ADataset) b;
			checkCompatibility(bds);

			IndexIterator it1 = getIterator();
			IndexIterator it2 = bds.getIterator();

			while (it1.hasNext() && it2.hasNext()) {
				data[it1.index] &= bds.getElementBooleanAbs(it2.index);
			}
		} else {
			boolean v = toBoolean(b);
			IndexIterator it1 = getIterator();

			if (!(b instanceof Number)) {
				logger.error("Argument is of unsupported class");
				throw new IllegalArgumentException("Argument is of unsupported class");
			}

			while (it1.hasNext()) {
				data[it1.index] &= v;
			}
		}
		setDirty();
		return this;
	}

	@Override
	public BooleanDataset idivide(final Object b) {
		if (b instanceof ADataset) {
			ADataset bds = (ADataset) b;
			checkCompatibility(bds);

			IndexIterator it1 = getIterator();
			IndexIterator it2 = bds.getIterator();

			while (it1.hasNext() && it2.hasNext()) {
				data[it1.index] &= bds.getElementBooleanAbs(it2.index);
			}
		} else {
			boolean v = toBoolean(b);
			IndexIterator it1 = getIterator();

			if (!(b instanceof Number)) {
				logger.error("Argument is of unsupported class");
				throw new IllegalArgumentException("Argument is of unsupported class");
			}

			while (it1.hasNext()) {
				data[it1.index] &= v;
			}
		}
		setDirty();
		return this;
	}

	@Override
	public BooleanDataset iremainder(final Object b) {
		logger.error("Unsupported method for class");
		throw new UnsupportedOperationException("Unsupported method for class");
	}

	@Override
	public BooleanDataset ipower(final Object b) {
		logger.error("Unsupported method for class");
		throw new UnsupportedOperationException("Unsupported method for class");
	}

	@Override
	public double residual(final Object b) {
		double sum = 0;
		if (b instanceof ADataset) {
			ADataset bds = (ADataset) b;
			checkCompatibility(bds);

			IndexIterator it1 = getIterator();
			IndexIterator it2 = bds.getIterator();

			while (it1.hasNext() && it2.hasNext()) {
				if (data[it1.index] ^ bds.getElementBooleanAbs(it2.index))
					sum++;
			}
		} else {
			boolean v = toBoolean(b);
			IndexIterator it1 = getIterator();

			while (it1.hasNext()) {
				if (data[it1.index] ^ v)
					sum++;
			}
		}
		return sum;
	}
}

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

// This is generated from ComplexDoubleDataset.java by fromcpxdouble.py

package uk.ac.diamond.scisoft.analysis.dataset;


import java.util.Arrays;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;


/**
 * Extend compound dataset to hold complex float values // PRIM_TYPE
 */
public class ComplexFloatDataset extends CompoundFloatDataset { // CLASS_TYPE
	// pin UID to base class
	private static final long serialVersionUID = AbstractDataset.serialVersionUID;

	private static final int ISIZE = 2; // number of elements per item

	@Override
	public int getDtype() {
		return Dataset.COMPLEX64; // DATA_TYPE
	}

	public ComplexFloatDataset() {
		super(ISIZE);
	}

	/**
	 * Create a zero-filled dataset of given shape
	 * @param shape
	 */
	public ComplexFloatDataset(final int... shape) {
		super(ISIZE, shape);
	}

	/**
	 * Create a dataset using given data (real and imaginary parts are grouped in pairs)
	 * @param data
	 * @param shape (can be null to create 1D dataset)
	 */
	public ComplexFloatDataset(final float[] data, final int... shape) { // PRIM_TYPE
		super(ISIZE, data, shape);
	}

	/**
	 * Copy a dataset
	 * @param dataset
	 */
	public ComplexFloatDataset(final ComplexFloatDataset dataset) {
		super(dataset);
	}

	/**
	 * Create a dataset using given data (real and imaginary parts are given separately)
	 * @param realData
	 * @param imagData
	 * @param shape (can be null to create 1D dataset)
	 */
	public ComplexFloatDataset(final float[] realData, final float[] imagData, int... shape) { // PRIM_TYPE
		int dsize = realData.length > imagData.length ? imagData.length : realData.length;
		if (shape == null || shape.length == 0) {
			shape = new int[] {dsize};
		}
		isize = ISIZE;
		size = calcSize(shape);
		if (size*isize != dsize) {
			throw new IllegalArgumentException(String.format("Shape %s is not compatible with size of data array, %d",
					Arrays.toString(shape), dsize));
		}
		this.shape = shape.clone();

		odata = data = createArray(size);

		for (int i = 0, n = 0; i < size; i++) {
			data[n++] = realData[i];
			data[n++] = imagData[i];
		}
	}

	/**
	 * Create a dataset using given data (real and imaginary parts are given separately)
	 * @param real
	 * @param imag
	 */
	public ComplexFloatDataset(final Dataset real, final Dataset imag) {
		super(ISIZE, real.getShapeRef());
		real.checkCompatibility(imag);

		IndexIterator riter = real.getIterator();
		IndexIterator iiter = imag.getIterator();

		for (int i = 0; riter.hasNext() && iiter.hasNext();) {
			data[i++] = (float) real.getElementDoubleAbs(riter.index); // ADD_CAST
			data[i++] = (float) imag.getElementDoubleAbs(iiter.index); // ADD_CAST
		}
	}

	/**
	 * Cast a dataset to this complex type
	 * @param dataset
	 */
	public ComplexFloatDataset(final Dataset dataset) {
		super(ISIZE, dataset.getShapeRef());
		copyToView(dataset, this, true, false);
		offset = 0;
		stride = null;
		base = null;

		IndexIterator iter = dataset.getIterator();
		int disize = dataset.getElementsPerItem();
		if (disize == 1) {
			for (int i = 0; iter.hasNext(); i += isize) {
				data[i] = (float) dataset.getElementDoubleAbs(iter.index); // ADD_CAST
			}
		} else {
			for (int i = 0; iter.hasNext(); i += isize) {
				data[i] = (float) dataset.getElementDoubleAbs(iter.index); // ADD_CAST
				data[i+1] = (float) dataset.getElementDoubleAbs(iter.index+1); // ADD_CAST
			}
		}
	}

	@Override
	public ComplexFloatDataset clone() {
		return new ComplexFloatDataset(this);
	}

	/**
	 * Create a dataset from an object which could be a Java list, array (of arrays...)
	 * or Number. Ragged sequences or arrays are padded with zeros.
	 *
	 * @param obj
	 * @return dataset with contents given by input
	 */
	public static ComplexFloatDataset createFromObject(final Object obj) {
		ComplexFloatDataset result = new ComplexFloatDataset();

		result.shape = getShapeFromObject(obj);
		result.size = calcSize(result.shape);

		result.odata = result.data = result.createArray(result.size);

		int[] pos = new int[result.shape.length];
		result.fillData(obj, 0, pos);
		return result;
	}

	/**
	 * @param stop
	 * @return a new 1D dataset, filled with values determined by parameters
	 * @deprecated Use {@link #createRange(double)}
	 */
	@Deprecated
	public static ComplexFloatDataset arange(final double stop) {
		return createRange(0, stop, 1);
	}

	/**
	 * @param start
	 * @param stop
	 * @param step
	 * @return a new 1D dataset, filled with values determined by parameters
	 * @deprecated Use {@link #createRange(double, double, double)}
	 */
	@Deprecated
	public static ComplexFloatDataset arange(final double start, final double stop, final double step) {
		return createRange(start, stop, step);
	}

	/**
	 * @param stop
	 * @return a new 1D dataset, filled with values determined by parameters
	 */
	public static ComplexFloatDataset createRange(final double stop) {
		return createRange(0, stop, 1);
	}

	/**
	 * @param start
	 * @param stop
	 * @param step
	 * @return a new 1D dataset, filled with values determined by parameters
	 */
	public static ComplexFloatDataset createRange(final double start, final double stop, final double step) {
		int size = calcSteps(start, stop, step);
		ComplexFloatDataset result = new ComplexFloatDataset(size);
		for (int i = 0; i < size; i ++) {
			result.data[i*ISIZE] = (float) (start + i*step); // ADD_CAST
		}
		return result;
	}

	/**
	 * @param shape
	 * @return a dataset filled with ones
	 */
	public static ComplexFloatDataset ones(final int... shape) {
		return new ComplexFloatDataset(shape).fill(1);
	}

	/**
	 * @param obj
	 * @return dataset filled with given object
	 */
	@Override
	public ComplexFloatDataset fill(final Object obj) {
		if (obj instanceof IDataset) {
			IDataset ds = (IDataset) obj;
			if (!isCompatibleWith(ds)) {
				logger.error("Tried to fill with dataset of incompatible shape");
				throw new IllegalArgumentException("Tried to fill with dataset of incompatible shape");
			}
			if (ds instanceof Dataset) {
				Dataset ads = (Dataset) ds;
				IndexIterator itd = ads.getIterator();
				IndexIterator iter = getIterator();
				while (iter.hasNext() && itd.hasNext()) {
					Object o = ads.getObjectAbs(itd.index);
					float vr = (float) toReal(o); // PRIM_TYPE // ADD_CAST
					float vi = (float) toImag(o); // PRIM_TYPE // ADD_CAST
					data[iter.index] = vr;
					data[iter.index+1] = vi;
				}
			} else {
				IndexIterator itd = new PositionIterator(ds.getShape());
				int[] pos = itd.getPos();
				IndexIterator iter = getIterator();
				while (iter.hasNext() && itd.hasNext()) {
					Object o = ds.getObject(pos);
					float vr = (float) toReal(o); // PRIM_TYPE // ADD_CAST
					float vi = (float) toImag(o); // PRIM_TYPE // ADD_CAST
					data[iter.index] = vr;
					data[iter.index+1] = vi;
				}
			}

			setDirty();
			return this;
		}

		IndexIterator iter = getIterator();
		float vr = (float) toReal(obj); // PRIM_TYPE // ADD_CAST
		float vi = (float) toImag(obj); // PRIM_TYPE // ADD_CAST

		while (iter.hasNext()) {
			data[iter.index] = vr;
			data[iter.index+1] = vi;
		}

		setDirty();
		return this;
	}

	@Override
	public ComplexFloatDataset getView() {
		ComplexFloatDataset view = new ComplexFloatDataset();
		copyToView(this, view, true, true);
		view.data = data;
		return view;
	}

	/**
	 * Get complex value at absolute index in the internal array.
	 *
	 * This is an internal method with no checks so can be dangerous. Use with care or ideally with an iterator.
	 *
	 * @param index absolute index
	 * @return value
	 */
	public Complex getComplexAbs(final int index) {
		return new Complex(data[index], data[index+1]);
	}

	@Override
	public Object getObjectAbs(final int index) {
		return new Complex(data[index], data[index+1]);
	}

	@Override
	public String getStringAbs(final int index) {
		float di = data[index+1]; // PRIM_TYPE
		return di >= 0 ? String.format("%.8g + %.8gj", data[index], di) :  // FORMAT_STRING
			String.format("%.8g - %.8gj", data[index], -di);  // FORMAT_STRING
	}

	/**
	 * Set values at absolute index in the internal array.
	 *
	 * This is an internal method with no checks so can be dangerous. Use with care or ideally with an iterator.
	 * @param index absolute index
	 * @param val new values
	 */
	public void setAbs(final int index, final Complex val) {
		setAbs(index, (float) val.getReal(), (float) val.getImaginary()); // PRIM_TYPE
	}

	@Override
	public void setObjectAbs(final int index, final Object obj) {
		setAbs(index, (float) toReal(obj), (float) toImag(obj)); // PRIM_TYPE
	}

	/**
	 * Set item at index to complex value given by real and imaginary parts
	 * @param index absolute index
	 * @param real
	 * @param imag
	 */
	public void setAbs(final int index, final float real, final float imag) { // PRIM_TYPE
		data[index] = real;
		data[index+1] = imag;
		setDirty();
	}

	/**
	 * @param i
	 * @return item in given position
	 */
	public Complex get(final int i) {
		int n = get1DIndex(i);
		Complex z = new Complex(data[n], data[n+1]);
		return z;
	}

	/**
	 * @param i
	 * @param j
	 * @return item in given position
	 */
	public Complex get(final int i, final int j) {
		int n = get1DIndex(i, j);
		Complex z = new Complex(data[n], data[n+1]);
		return z;
	}

	/**
	 * @param pos
	 * @return item in given position
	 */
	public Complex get(final int... pos) {
		int n = get1DIndex(pos);
		Complex z = new Complex(data[n], data[n+1]);
		return z;
	}

	@Override
	public Object getObject(final int i) {
		return getComplex(i);
	}

	@Override
	public Object getObject(final int i, final int j) {
		return getComplex(i, j);
	}

	@Override
	public Object getObject(final int... pos) {
		return getComplex(pos);
	}

	/**
	 * @param i
	 * @return item in given position
	 */
	public float getReal(final int i) { // PRIM_TYPE
		return (float) getFirstValue(i); // PRIM_TYPE
	}

	/**
	 * @param i
	 * @param j
	 * @return item in given position
	 */
	public float getReal(final int i, final int j) { // PRIM_TYPE
		return (float) getFirstValue(i, j); // PRIM_TYPE
	}

	/**
	 * @param pos
	 * @return item in given position
	 */
	public float getReal(final int... pos) { // PRIM_TYPE
		return (float) getFirstValue(pos); // PRIM_TYPE
	}

	/**
	 * @param i
	 * @return item in given position
	 */
	public float getImag(final int i) { // PRIM_TYPE
		return data[get1DIndex(i) + 1];
	}

	/**
	 * @param i
	 * @param j
	 * @return item in given position
	 */
	public float getImag(final int i, final int j) { // PRIM_TYPE
		return data[get1DIndex(i, j) + 1];
	}

	/**
	 * @param pos
	 * @return item in given position
	 */
	public float getImag(final int... pos) { // PRIM_TYPE
		return data[get1DIndex(pos) + 1];
	}

	/**
	 * @param i
	 * @return item in given position
	 */
	public Complex getComplex(final int i) {
		return get(i);
	}

	/**
	 * @param i
	 * @param j
	 * @return item in given position
	 */
	public Complex getComplex(final int i, final int j) {
		return get(i, j);
	}

	/**
	 * @param pos
	 * @return item in given position
	 */
	public Complex getComplex(final int... pos) {
		return get(pos);
	}

	@Override
	public void set(final Object obj, final int i) {
		setItem(new float[] {(float) toReal(obj), (float) toImag(obj)}, i); // PRIM_TYPE
	}

	@Override
	public void set(final Object obj, final int i, final int j) {
		setItem(new float[] {(float) toReal(obj), (float) toImag(obj)}, i, j); // PRIM_TYPE
	}

	@Override
	public void set(final Object obj, int... pos) {
		if (pos == null || (pos.length == 0 && shape.length > 0)) {
			pos = new int[shape.length];
		}

		setItem(new float[] {(float) toReal(obj), (float) toImag(obj)}, pos); // PRIM_TYPE
	}

	/**
	 * Set real and imaginary values at given position
	 * @param dr
	 * @param di
	 * @param i
	 */
	public void set(final float dr, final float di, final int i) { // PRIM_TYPE
		setItem(new float[] {dr, di}, i); // PRIM_TYPE
	}

	/**
	 * Set real and imaginary values at given position
	 * @param dr
	 * @param di
	 * @param i
	 * @param j
	 */
	public void set(final float dr, final float di, final int i, final int j) { // PRIM_TYPE
		setItem(new float[] {dr, di}, i, j); // PRIM_TYPE
	}

	/**
	 * Set real and imaginary values at given position
	 * @param dr
	 * @param di
	 * @param pos
	 */
	public void set(final float dr, final float di, final int... pos) { // PRIM_TYPE
		setItem(new float[] {dr, di}, pos); // PRIM_TYPE
	}

	public FloatDataset imag() { // CLASS_TYPE
		FloatDataset rdataset = new FloatDataset(shape); // CLASS_TYPE
		IndexIterator iter = getIterator();
		IndexIterator riter = rdataset.getIterator();

		float[] rdata = rdataset.data; // PRIM_TYPE
		while (iter.hasNext() && riter.hasNext())
			rdata[riter.index] = data[iter.index + 1];

		return rdataset;
	}

	@Override
	public Number max(boolean... switches) {
		throw new UnsupportedOperationException("Cannot compare complex numbers");
	}

	@Override
	public Number min(boolean... switches) {
		throw new UnsupportedOperationException("Cannot compare complex numbers");
	}

	@Override
	public Object sum() {
		final String n = storeName(false, STORE_STATS_ITEM_NAME);
		if (storedValues == null) {
			calculateSummaryStats(false, false, n);
		}

		final SummaryStatistics rstats = (SummaryStatistics) storedValues.get(n + "0");
		final SummaryStatistics istats = (SummaryStatistics) storedValues.get(n + "1");
		return new Complex(rstats.getSum(), istats.getSum());
	}

	@Override
	public Object mean(boolean... switches) {
		final String n = storeName(false, STORE_STATS_ITEM_NAME);
		if (storedValues == null) {
			calculateSummaryStats(false, false, n);
		}

		final SummaryStatistics rstats = (SummaryStatistics) storedValues.get(n + "0");
		final SummaryStatistics istats = (SummaryStatistics) storedValues.get(n + "1");
		return new Complex(rstats.getMean(), istats.getMean());
	}

	@Override
	public int[] maxPos(boolean ignoreNaNs) {
		throw new UnsupportedOperationException("Cannot compare complex numbers");
	}

	@Override
	public int[] minPos(boolean ignoreNaNs) {
		throw new UnsupportedOperationException("Cannot compare complex numbers");
	}

	@Override
	public ComplexFloatDataset getSlice(final SliceIterator siter) {
		ComplexFloatDataset result = new ComplexFloatDataset(siter.getShape());
		float[] rdata = result.data; // PRIM_TYPE
		IndexIterator riter = result.getIterator();

		while (siter.hasNext() && riter.hasNext()) {
			rdata[riter.index] = data[siter.index];
			rdata[riter.index+1] = data[siter.index+1];
		}

		result.setName(name + BLOCK_OPEN + createSliceString(siter.shape, siter.start, siter.stop, siter.step) + BLOCK_CLOSE);
		return result;
	}

	@Override
	public ComplexFloatDataset setSlice(final Object o, final IndexIterator siter) {
		if (o instanceof ComplexFloatDataset) {
			ComplexFloatDataset zds = (ComplexFloatDataset) o;

			if (!areShapesCompatible(siter.getShape(), zds.shape)) {
				throw new IllegalArgumentException(String.format(
						"Input dataset is not compatible with slice: %s cf %s", Arrays.toString(zds.shape),
						Arrays.toString(siter.getShape())));
			}

			IndexIterator oiter = zds.getIterator();
			float[] odata = zds.data;

			while (siter.hasNext() && oiter.hasNext()) {
				data[siter.index] = odata[oiter.index];
				data[siter.index+1] = odata[oiter.index+1];
			}
		} else if (o instanceof ComplexDoubleDataset) { // IGNORE_CLASS
			ComplexDoubleDataset zds = (ComplexDoubleDataset) o; // IGNORE_CLASS

			if (!areShapesCompatible(siter.getShape(), zds.shape)) {
				throw new IllegalArgumentException(String.format(
						"Input dataset is not compatible with slice: %s cf %s", Arrays.toString(zds.shape),
						Arrays.toString(siter.getShape())));
			}

			IndexIterator oiter = zds.getIterator();
			double[] odata = zds.data;

			while (siter.hasNext() && oiter.hasNext()) {
				data[siter.index] = (float) odata[oiter.index]; // PRIM_TYPE // ADD_CAST
				data[siter.index+1] = (float) odata[oiter.index+1]; // PRIM_TYPE // ADD_CAST
			}
		} else if (o instanceof IDataset) {
			super.setSlice(o, siter);
		} else {
			try {
				float vr = (float) toReal(o); // PRIM_TYPE // ADD_CAST
				float vi = (float) toImag(o); // PRIM_TYPE // ADD_CAST

				while (siter.hasNext()) {
					data[siter.index] = vr;
					data[siter.index+1] = vi;
				}
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("Object for setting slice is not a dataset or number");
			}
		}
		setDirty();
		return this;
	}

	@Override
	public ComplexFloatDataset iadd(final Object b) {
		if (b instanceof Dataset) {
			Dataset bds = (Dataset) b;
			checkCompatibility(bds);

			IndexIterator it1 = getIterator();
			IndexIterator it2 = bds.getIterator();

			switch (bds.getDtype()) {
			case Dataset.COMPLEX64: case Dataset.COMPLEX128:
				while (it1.hasNext() && it2.hasNext()) {
					data[it1.index]   += bds.getElementDoubleAbs(it2.index);
					data[it1.index+1] += bds.getElementDoubleAbs(it2.index+1);
				}
				break;
			default:
				while (it1.hasNext() && it2.hasNext()) {
					data[it1.index] += bds.getElementDoubleAbs(it2.index);
				}
				break;
			}
		} else {
			float vr = (float) toReal(b); // PRIM_TYPE // ADD_CAST
			float vi = (float) toImag(b); // PRIM_TYPE // ADD_CAST
			IndexIterator it1 = getIterator();

			if (vi == 0) {
				while (it1.hasNext()) {
					data[it1.index] += vr;
				}
			} else {
				while (it1.hasNext()) {
					data[it1.index]   += vr;
					data[it1.index+1] += vi;
				}
			}
		}
		setDirty();
		return this;
	}

	@Override
	public ComplexFloatDataset isubtract(final Object b) {
		if (b instanceof Dataset) {
			Dataset bds = (Dataset) b;
			checkCompatibility(bds);

			IndexIterator it1 = getIterator();
			IndexIterator it2 = bds.getIterator();

			switch (bds.getDtype()) {
			case Dataset.COMPLEX64: case Dataset.COMPLEX128:
				while (it1.hasNext() && it2.hasNext()) {
					data[it1.index]   -= bds.getElementDoubleAbs(it2.index);
					data[it1.index+1] -= bds.getElementDoubleAbs(it2.index+1);
				}
				break;
			default:
				while (it1.hasNext() && it2.hasNext()) {
					data[it1.index] -= bds.getElementDoubleAbs(it2.index);
				}
				break;
			}
		} else {
			float vr = (float) toReal(b); // PRIM_TYPE // ADD_CAST
			float vi = (float) toImag(b); // PRIM_TYPE // ADD_CAST
			IndexIterator it1 = getIterator();

			if (vi == 0) {
				while (it1.hasNext()) {
					data[it1.index] -= vr;
				}
			} else {
				while (it1.hasNext()) {
					data[it1.index]   -= vr;
					data[it1.index+1] -= vi;
				}
			}
		}
		setDirty();
		return this;
	}

	@Override
	public ComplexFloatDataset imultiply(final Object b) {
		if (b instanceof Dataset) {
			Dataset bds = (Dataset) b;
			checkCompatibility(bds);

			IndexIterator it1 = getIterator();
			IndexIterator it2 = bds.getIterator();

			switch (bds.getDtype()) {
			case Dataset.COMPLEX64: case Dataset.COMPLEX128:
				while (it1.hasNext() && it2.hasNext()) {
					double r1 = data[it1.index];
					double r2 = bds.getElementDoubleAbs(it2.index);
					double i1 = data[it1.index+1];
					double i2 = bds.getElementDoubleAbs(it2.index + 1);
					data[it1.index]   = (float) (r1*r2 - i1*i2); // ADD_CAST
					data[it1.index+1] = (float) (r1*i2 + i1*r2); // ADD_CAST
				}
				break;
			default:
				while (it1.hasNext() && it2.hasNext()) {
					float r2 = (float) bds.getElementDoubleAbs(it2.index); // PRIM_TYPE // ADD_CAST
					data[it1.index]   *= r2;
					data[it1.index+1] *= r2;
				}
				break;
			}
		} else {
			float vr = (float) toReal(b); // PRIM_TYPE // ADD_CAST
			float vi = (float) toImag(b); // PRIM_TYPE // ADD_CAST
			IndexIterator it1 = getIterator();

			if (vi == 0) {
				while (it1.hasNext()) {
					data[it1.index]   *= vr;
					data[it1.index+1] *= vr;
				}
			} else {
				while (it1.hasNext()) {
					float r1 = data[it1.index]; // PRIM_TYPE
					float i1 = data[it1.index+1]; // PRIM_TYPE
					data[it1.index]   = r1*vr - i1*vi;
					data[it1.index+1] = r1*vi + i1*vr;
				}
			}
		}
		setDirty();
		return this;
	}

	@Override
	public ComplexFloatDataset idivide(final Object b) {
		if (b instanceof Dataset) {
			Dataset bds = (Dataset) b;
			checkCompatibility(bds);

			IndexIterator it1 = getIterator();
			IndexIterator it2 = bds.getIterator();

			switch (bds.getDtype()) {
			case Dataset.COMPLEX64: case Dataset.COMPLEX128:
				while (it1.hasNext() && it2.hasNext()) {
					double r1 = data[it1.index];
					double r2 = bds.getElementDoubleAbs(it2.index);
					double i1 = data[it1.index+1];
					double i2 = bds.getElementDoubleAbs(it2.index + 1);
					if (Math.abs(r2) < Math.abs(i2)) {
						double q = r2/i2;
						double den = r2*q + i2;
						data[it1.index]   = (float) ((r1*q + i1) / den); // ADD_CAST
						data[it1.index+1] = (float) ((i1*q - r1) / den); // ADD_CAST
					} else {
						double q = i2/r2;
						double den = i2*q + r2;
						if (den == 0) {
							data[it1.index]   = Float.NaN; // CLASS_TYPE
							data[it1.index+1] = Float.NaN; // CLASS_TYPE
						} else {
							data[it1.index]   = (float) ((i1 * q + r1) / den); // ADD_CAST
							data[it1.index+1] = (float) ((i1 - r1 * q) / den); // ADD_CAST
						}
					}
				}
				break;
			default:
				while (it1.hasNext() && it2.hasNext()) {
					float r2 = (float) bds.getElementDoubleAbs(it2.index); // PRIM_TYPE // ADD_CAST
					if (r2 == 0) {
						data[it1.index]   = Float.NaN; // CLASS_TYPE
						data[it1.index+1] = Float.NaN; // CLASS_TYPE
					} else {
						data[it1.index]   /= r2;
						data[it1.index+1] /= r2;
					}
				}
				break;
			}
		} else {
			float vr = (float) toReal(b); // PRIM_TYPE // ADD_CAST
			float vi = (float) toImag(b); // PRIM_TYPE // ADD_CAST
			IndexIterator it1 = getIterator();

			if (vi == 0) {
				if (vr == 0) {
					while (it1.hasNext()) {
						data[it1.index]   = Float.NaN; // CLASS_TYPE
						data[it1.index+1] = Float.NaN; // CLASS_TYPE
					}
				} else {
					while (it1.hasNext()) {
						data[it1.index]   /= vr;
						data[it1.index+1] /= vr;
					}
				}
			} else {
				if (Math.abs(vr) < Math.abs(vi)) {
					double q = vr/vi;
					double den = vr*q + vi;
					while (it1.hasNext()) {
						double r1 = data[it1.index];
						double i1 = data[it1.index+1];
						data[it1.index]   = (float) ((r1*q + i1) / den); // ADD_CAST
						data[it1.index+1] = (float) ((i1*q - r1) / den); // ADD_CAST
					}
				} else {
					double q = vi/vr;
					double den = vi*q + vr;
					while (it1.hasNext()) {
						double r1 = data[it1.index];
						double i1 = data[it1.index+1];
						data[it1.index]   = (float) ((i1*q + r1) / den); // ADD_CAST
						data[it1.index+1] = (float) ((i1 - r1*q) / den); // ADD_CAST
					}
				}
			}
		}
		setDirty();
		return this;
	}

	@Override
	public ComplexFloatDataset iremainder(final Object b) {
		throw new UnsupportedOperationException("Unsupported method for class");
	}

	@Override
	public ComplexFloatDataset ipower(final Object b) {
		if (b instanceof Dataset) {
			Dataset bds = (Dataset) b;
			checkCompatibility(bds);

			IndexIterator it1 = getIterator();
			IndexIterator it2 = bds.getIterator();
			switch (bds.getDtype()) {
			case Dataset.COMPLEX64: case Dataset.COMPLEX128:
				while (it1.hasNext() && it2.hasNext()) {
					final Complex zv = new Complex(bds.getElementDoubleAbs(it2.index),
							bds.getElementDoubleAbs(it2.index+1));
					final Complex zd = new Complex(data[it1.index], data[it1.index+1]).pow(zv);
					data[it1.index]   = (float) zd.getReal(); // ADD_CAST
					data[it1.index+1] = (float) zd.getImaginary(); // ADD_CAST
				}
				break;
			default:
				while (it1.hasNext() && it2.hasNext()) {
					final Complex zv = new Complex(bds.getElementDoubleAbs(it2.index), 0);
					final Complex zd = new Complex(data[it1.index], data[it1.index+1]).pow(zv);
					data[it1.index]   = (float) zd.getReal(); // ADD_CAST
					data[it1.index+1] = (float) zd.getImaginary(); // ADD_CAST
				}
				break;
			}
		} else {
			final Complex zv = new Complex(toReal(b), toImag(b));
			IndexIterator it1 = getIterator();

			while (it1.hasNext()) {
				Complex zd = new Complex(data[it1.index], data[it1.index+1]).pow(zv);
				data[it1.index]   = (float) zd.getReal(); // ADD_CAST
				data[it1.index+1] = (float) zd.getImaginary(); // ADD_CAST
			}
		}
		setDirty();
		return this;
	}

	@Override
	public double residual(final Object b, Dataset w, boolean ignoreNaNs) {
		double sum = 0;
		if (b instanceof Dataset) {
			Dataset bds = (Dataset) b;
			checkCompatibility(bds);

			final IndexIterator it1 = getIterator();
			final IndexIterator it2 = bds.getIterator();
			double comp = 0;

			switch (bds.getDtype()) {
			case Dataset.COMPLEX64: case Dataset.COMPLEX128:
				if (ignoreNaNs) {
					if (w == null) {
						while (it1.hasNext() && it2.hasNext()) {
							double diffr, diffi, err, temp;
							diffr = data[it1.index] - bds.getElementDoubleAbs(it2.index);
							diffi = data[it1.index + 1] - bds.getElementDoubleAbs(it2.index + 1);
							if (Double.isNaN(diffr) || Double.isNaN(diffi))
								continue;

							err = diffr*diffr - comp;
							temp = sum + err;
							comp = (temp - sum) - err;
							sum = temp;

							err = diffi*diffi - comp;
							temp = sum + err;
							comp = (temp - sum) - err;
							sum = temp;
						}
					} else {
						final IndexIterator it3 = w.getIterator();
						while (it1.hasNext() && it2.hasNext() && it3.hasNext()) {
							double dw = w.getElementDoubleAbs(it3.index);
							double diffr, diffi, err, temp;
							diffr = data[it1.index] - bds.getElementDoubleAbs(it2.index);
							diffi = data[it1.index + 1] - bds.getElementDoubleAbs(it2.index + 1);
							if (Double.isNaN(diffr) || Double.isNaN(diffi))
								continue;

							err = diffr*diffr*dw - comp;
							temp = sum + err;
							comp = (temp - sum) - err;
							sum = temp;

							err = diffi*diffi*dw - comp;
							temp = sum + err;
							comp = (temp - sum) - err;
							sum = temp;
						}
					}
				} else {
					if (w == null) {
						while (it1.hasNext() && it2.hasNext()) {
							double diff, err, temp;
							diff = data[it1.index] - bds.getElementDoubleAbs(it2.index);
							err = diff*diff - comp;
							temp = sum + err;
							comp = (temp - sum) - err;
							sum = temp;

							diff = data[it1.index + 1] - bds.getElementDoubleAbs(it2.index + 1);
							err = diff*diff - comp;
							temp = sum + err;
							comp = (temp - sum) - err;
							sum = temp;
						}
					} else {
						final IndexIterator it3 = w.getIterator();
						while (it1.hasNext() && it2.hasNext() && it3.hasNext()) {
							double dw = w.getElementDoubleAbs(it3.index);
							double diff, err, temp;
							diff = data[it1.index] - bds.getElementDoubleAbs(it2.index);
							err = diff*diff*dw - comp;
							temp = sum + err;
							comp = (temp - sum) - err;
							sum = temp;

							diff = data[it1.index + 1] - bds.getElementDoubleAbs(it2.index + 1);
							err = diff*diff*dw - comp;
							temp = sum + err;
							comp = (temp - sum) - err;
							sum = temp;
						}
					}
				}
				break;
			default:
				if (w == null) {
					while (it1.hasNext() && it2.hasNext()) {
						final double diff = data[it1.index] - bds.getElementDoubleAbs(it2.index);
						if (ignoreNaNs && Double.isInfinite(diff))
							continue;
						final double err  = diff*diff - comp;
						final double temp = sum + err;
						comp = (temp - sum) - err;
						sum = temp;
					}
				} else {
					final IndexIterator it3 = w.getIterator();
					while (it1.hasNext() && it2.hasNext() && it3.hasNext()) {
						double dw = w.getElementDoubleAbs(it3.index);
						final double diff = data[it1.index] - bds.getElementDoubleAbs(it2.index);
						if (ignoreNaNs && Double.isInfinite(diff))
							continue;
						final double err  = diff*diff*dw - comp;
						final double temp = sum + err;
						comp = (temp - sum) - err;
						sum = temp;
					}
				}
				break;
			}
		} else {
			final Complex zv = new Complex(toReal(b), toImag(b));
			if (ignoreNaNs) {
				if (zv.isNaN())
					return sum;
			}
			IndexIterator it1 = getIterator();

			double comp = 0;
			if (w == null) {
				if (ignoreNaNs) {
					while (it1.hasNext()) {
						double diffr, diffi, err, temp;
						diffr = data[it1.index] - zv.getReal();
						diffi = data[it1.index + 1] - zv.getImaginary();
						if (Double.isNaN(diffr) || Double.isNaN(diffi))
							continue;

						err = diffr*diffr - comp;
						temp = sum + err;
						comp = (temp - sum) - err;
						sum = temp;

						err = diffi*diffi - comp;
						temp = sum + err;
						comp = (temp - sum) - err;
						sum = temp;
					}
				} else {
					while (it1.hasNext()) {
						double diff, err, temp;
						diff = data[it1.index] - zv.getReal();
						err = diff*diff - comp;
						temp = sum + err;
						comp = (temp - sum) - err;
						sum = temp;

						diff = data[it1.index + 1] - zv.getImaginary();
						err = diff*diff - comp;
						temp = sum + err;
						comp = (temp - sum) - err;
						sum = temp;
					}
				}
			} else {
				final IndexIterator it3 = w.getIterator();
				if (ignoreNaNs) {
					while (it1.hasNext() && it3.hasNext()) {
						double dw = w.getElementDoubleAbs(it3.index);
						double diffr, diffi, err, temp;
						diffr = data[it1.index] - zv.getReal();
						diffi = data[it1.index + 1] - zv.getImaginary();
						if (Double.isNaN(diffr) || Double.isNaN(diffi))
							continue;

						err = diffr*diffr*dw - comp;
						temp = sum + err;
						comp = (temp - sum) - err;
						sum = temp;

						err = diffi*diffi*dw - comp;
						temp = sum + err;
						comp = (temp - sum) - err;
						sum = temp;
					}
				} else {
					while (it1.hasNext() && it3.hasNext()) {
						double dw = w.getElementDoubleAbs(it3.index);
						double diff, err, temp;
						diff = data[it1.index] - zv.getReal();
						err = diff*diff*dw - comp;
						temp = sum + err;
						comp = (temp - sum) - err;
						sum = temp;

						diff = data[it1.index + 1] - zv.getImaginary();
						err = diff*diff*dw - comp;
						temp = sum + err;
						comp = (temp - sum) - err;
						sum = temp;
					}
				}
			}
		}
		return sum;
	}
}

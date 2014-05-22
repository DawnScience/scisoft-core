/*-
 * Copyright 2014 Diamond Light Source Ltd.
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

import java.util.List;

import org.apache.commons.math3.complex.Complex;

public class DatasetFactory {

	/**
	 * Create dataset with items ranging from 0 to given stop in steps of 1
	 * @param stop
	 * @param dtype
	 * @return a new dataset of given shape and type, filled with values determined by parameters
	 */
	public static Dataset createRange(final double stop, final int dtype) {
		return createRange(0, stop, 1, dtype);
	}

	/**
	 * Create dataset with items ranging from given start to given stop in given steps
	 * @param start
	 * @param stop
	 * @param step
	 * @param dtype
	 * @return a new 1D dataset of given type, filled with values determined by parameters
	 */
	public static Dataset createRange(final double start, final double stop, final double step, final int dtype) {
		if ((step > 0) != (start <= stop)) {
			return null;
		}

		switch (dtype) {
		case Dataset.BOOL:
			break;
		case Dataset.INT8:
			return ByteDataset.arange(start, stop, step);
		case Dataset.INT16:
			return ShortDataset.arange(start, stop, step);
		case Dataset.INT32:
			return IntegerDataset.arange(start, stop, step);
		case Dataset.INT64:
			return LongDataset.arange(start, stop, step);
		case Dataset.FLOAT32:
			return FloatDataset.arange(start, stop, step);
		case Dataset.FLOAT64:
			return DoubleDataset.arange(start, stop, step);
		case Dataset.COMPLEX64:
			return ComplexFloatDataset.arange(start, stop, step);
		case Dataset.COMPLEX128:
			return ComplexDoubleDataset.arange(start, stop, step);
		}
		throw new IllegalArgumentException("dtype not known");
	}

	/**
	 * Create a dataset from object (automatically detect dataset type)
	 * 
	 * @param obj
	 *            can be Java list, array or Number
	 * @return dataset
	 */
	public static Dataset createFromObject(final Object obj) {
		if (obj instanceof AbstractDataset)
			return (AbstractDataset) obj;
		if (obj instanceof ILazyDataset)
			return DatasetUtils.convertToAbstractDataset((ILazyDataset) obj);

		final int dtype = AbstractDataset.getDTypeFromObject(obj);
		return createFromObject(obj, dtype);
	}

	/**
	 * Create a dataset from object (automatically detect dataset type)
	 * 
	 * @param obj
	 *            can be a Java list, array or Number
	 * @param isUnsigned
	 *            if true, interpret integer values as unsigned by increasing element bit width
	 * @return dataset
	 */
	public static Dataset createFromObject(final Object obj, boolean isUnsigned) {
		Dataset a = createFromObject(obj);
		if (isUnsigned) {
			switch (a.getDtype()) {
			case Dataset.INT32:
				a = new LongDataset(a);
				DatasetUtils.unwrapUnsigned(a, 32);
				break;
			case Dataset.INT16:
				a = new IntegerDataset(a);
				DatasetUtils.unwrapUnsigned(a, 16);
				break;
			case Dataset.INT8:
				a = new ShortDataset(a);
				DatasetUtils.unwrapUnsigned(a, 8);
				break;
			case Dataset.ARRAYINT32:
				a = new CompoundLongDataset(a);
				DatasetUtils.unwrapUnsigned(a, 32);
				break;
			case Dataset.ARRAYINT16:
				a = new CompoundIntegerDataset(a);
				DatasetUtils.unwrapUnsigned(a, 16);
				break;
			case Dataset.ARRAYINT8:
				a = new CompoundShortDataset(a);
				DatasetUtils.unwrapUnsigned(a, 8);
				break;
			}

		}
		return a;
	}

	/**
	 * Create a dataset from object
	 * 
	 * @param obj
	 *            can be a Java list, array or Number
	 * @param dtype
	 * @return dataset
	 */
	public static Dataset createFromObject(final Object obj, final int dtype) {
		if (obj instanceof AbstractDataset)
			return DatasetUtils.cast((AbstractDataset) obj, dtype);

		if (obj instanceof ILazyDataset)
			return DatasetUtils.cast(DatasetUtils.convertToAbstractDataset((ILazyDataset) obj), dtype);

		switch (dtype) {
		case Dataset.BOOL:
			return BooleanDataset.createFromObject(obj);
		case Dataset.INT8:
			return ByteDataset.createFromObject(obj);
		case Dataset.INT16:
			return ShortDataset.createFromObject(obj);
		case Dataset.INT32:
			return IntegerDataset.createFromObject(obj);
		case Dataset.INT64:
			return LongDataset.createFromObject(obj);
		case Dataset.ARRAYINT8:
			return CompoundByteDataset.createFromObject(obj);
		case Dataset.ARRAYINT16:
			return CompoundShortDataset.createFromObject(obj);
		case Dataset.ARRAYINT32:
			return CompoundIntegerDataset.createFromObject(obj);
		case Dataset.ARRAYINT64:
			return CompoundLongDataset.createFromObject(obj);
		case Dataset.FLOAT32:
			return FloatDataset.createFromObject(obj);
		case Dataset.FLOAT64:
			return DoubleDataset.createFromObject(obj);
		case Dataset.ARRAYFLOAT32:
			return CompoundFloatDataset.createFromObject(obj);
		case Dataset.ARRAYFLOAT64:
			return CompoundDoubleDataset.createFromObject(obj);
		case Dataset.COMPLEX64:
			return ComplexFloatDataset.createFromObject(obj);
		case Dataset.COMPLEX128:
			return ComplexDoubleDataset.createFromObject(obj);
		case Dataset.STRING:
			return StringDataset.createFromObject(obj);
		case Dataset.OBJECT:
			return ObjectDataset.createFromObject(obj);
		default:
			return null;
		}
	}

	/**
	 * Create dataset of appropriate type from list
	 * 
	 * @param objectList
	 * @return dataset filled with values from list
	 */
	public static Dataset createFromList(List<?> objectList) {
		if (objectList == null || objectList.size() == 0) {
			throw new IllegalArgumentException("No list or zero-length list given");
		}
		Object obj = objectList.get(0);
		if (obj instanceof Number || obj instanceof Complex) {
			int dtype = AbstractDataset.getDTypeFromClass(obj.getClass());
			int len = objectList.size();
			Dataset result = zeros(new int[] { len }, dtype);

			int i = 0;
			for (Object object : objectList) {
				result.setObjectAbs(i++, object);
			}
			return result;
		}
		throw new IllegalArgumentException("Class of list element not supported");
	}

	/**
	 * @param shape
	 * @param dtype
	 * @return a new dataset of given shape and type, filled with zeros
	 */
	public static Dataset zeros(final int[] shape, final int dtype) {
		switch (dtype) {
		case Dataset.BOOL:
			return new BooleanDataset(shape);
		case Dataset.INT8:
		case Dataset.ARRAYINT8:
			return new ByteDataset(shape);
		case Dataset.INT16:
		case Dataset.ARRAYINT16:
			return new ShortDataset(shape);
		case Dataset.RGB:
			return new RGBDataset(shape);
		case Dataset.INT32:
		case Dataset.ARRAYINT32:
			return new IntegerDataset(shape);
		case Dataset.INT64:
		case Dataset.ARRAYINT64:
			return new LongDataset(shape);
		case Dataset.FLOAT32:
		case Dataset.ARRAYFLOAT32:
			return new FloatDataset(shape);
		case Dataset.FLOAT64:
		case Dataset.ARRAYFLOAT64:
			return new DoubleDataset(shape);
		case Dataset.COMPLEX64:
			return new ComplexFloatDataset(shape);
		case Dataset.COMPLEX128:
			return new ComplexDoubleDataset(shape);
		case Dataset.STRING:
			return new StringDataset(shape);
		case Dataset.OBJECT:
			return new ObjectDataset(shape);
		}
		throw new IllegalArgumentException("dtype not known or unsupported");
	}

	/**
	 * @param itemSize
	 *            if equal to 1, then non-compound dataset is returned
	 * @param shape
	 * @param dtype
	 * @return a new dataset of given item size, shape and type, filled with zeros
	 */
	public static Dataset zeros(final int itemSize, final int[] shape, final int dtype) {
		if (itemSize == 1) {
			return zeros(shape, dtype);
		}
		switch (dtype) {
		case Dataset.INT8:
		case Dataset.ARRAYINT8:
			return new CompoundByteDataset(itemSize, shape);
		case Dataset.INT16:
		case Dataset.ARRAYINT16:
			return new CompoundShortDataset(itemSize, shape);
		case Dataset.RGB:
			if (itemSize != 3) {
				throw new IllegalArgumentException("Number of elements not compatible with RGB type");
			}
			return new RGBDataset(shape);
		case Dataset.INT32:
		case Dataset.ARRAYINT32:
			return new CompoundIntegerDataset(itemSize, shape);
		case Dataset.INT64:
		case Dataset.ARRAYINT64:
			return new CompoundLongDataset(itemSize, shape);
		case Dataset.FLOAT32:
		case Dataset.ARRAYFLOAT32:
			return new CompoundFloatDataset(itemSize, shape);
		case Dataset.FLOAT64:
		case Dataset.ARRAYFLOAT64:
			return new CompoundDoubleDataset(itemSize, shape);
		case Dataset.COMPLEX64:
			if (itemSize != 2) {
				throw new IllegalArgumentException("Number of elements not compatible with complex type");
			}
			return new ComplexFloatDataset(shape);
		case Dataset.COMPLEX128:
			if (itemSize != 2) {
				throw new IllegalArgumentException("Number of elements not compatible with complex type");
			}
			return new ComplexDoubleDataset(shape);
		}
		throw new IllegalArgumentException("dtype not a known compound type");
	}

	/**
	 * @param dataset
	 * @return a new dataset of same shape and type as input dataset, filled with zeros
	 */
	public static Dataset zeros(final Dataset dataset) {
		return zeros(dataset, dataset.getDtype());
	}

	/**
	 * Create a new dataset of same shape as input dataset, filled with zeros. If dtype is not
	 * explicitly compound then an elemental dataset is created 
	 * @param dataset
	 * @param dtype
	 * @return a new dataset
	 */
	public static Dataset zeros(final Dataset dataset, final int dtype) {
		final int[] shape = dataset.getShapeRef();
		final int isize = AbstractDataset.isDTypeElemental(dtype) ? 1 :dataset.getElementsPerItem();

		return zeros(isize, shape, dtype);
	}

	/**
	 * @param dataset
	 * @return a new dataset of same shape and type as input dataset, filled with ones
	 */
	public static Dataset ones(final Dataset dataset) {
		return ones(dataset, dataset.getDtype());
	}

	/**
	 * Create a new dataset of same shape as input dataset, filled with ones. If dtype is not
	 * explicitly compound then an elemental dataset is created
	 * @param dataset
	 * @param dtype
	 * @return a new dataset
	 */
	public static Dataset ones(final Dataset dataset, final int dtype) {
		final int[] shape = dataset.getShapeRef();
		final int isize = AbstractDataset.isDTypeElemental(dtype) ? 1 :dataset.getElementsPerItem();

		return ones(isize, shape, dtype);
	}

	/**
	 * @param shape
	 * @param dtype
	 * @return a new dataset of given shape and type, filled with ones
	 */
	public static Dataset ones(final int[] shape, final int dtype) {
		switch (dtype) {
		case Dataset.BOOL:
			return BooleanDataset.ones(shape);
		case Dataset.INT8:
			return ByteDataset.ones(shape);
		case Dataset.INT16:
			return ShortDataset.ones(shape);
		case Dataset.RGB:
			return new RGBDataset(shape).fill(1);
		case Dataset.INT32:
			return IntegerDataset.ones(shape);
		case Dataset.INT64:
			return LongDataset.ones(shape);
		case Dataset.FLOAT32:
			return FloatDataset.ones(shape);
		case Dataset.FLOAT64:
			return DoubleDataset.ones(shape);
		case Dataset.COMPLEX64:
			return ComplexFloatDataset.ones(shape);
		case Dataset.COMPLEX128:
			return ComplexDoubleDataset.ones(shape);
		}
		throw new IllegalArgumentException("dtype not known");
	}

	/**
	 * @param itemSize
	 *            if equal to 1, then non-compound dataset is returned
	 * @param shape
	 * @param dtype
	 * @return a new dataset of given item size, shape and type, filled with ones
	 */
	public static Dataset ones(final int itemSize, final int[] shape, final int dtype) {
		if (itemSize == 1) {
			return ones(shape, dtype);
		}
		switch (dtype) {
		case Dataset.INT8:
		case Dataset.ARRAYINT8:
			return CompoundByteDataset.ones(itemSize, shape);
		case Dataset.INT16:
		case Dataset.ARRAYINT16:
			return CompoundShortDataset.ones(itemSize, shape);
		case Dataset.RGB:
			if (itemSize != 3) {
				throw new IllegalArgumentException("Number of elements not compatible with RGB type");
			}
			return new RGBDataset(shape).fill(1);
		case Dataset.INT32:
		case Dataset.ARRAYINT32:
			return CompoundIntegerDataset.ones(itemSize, shape);
		case Dataset.INT64:
		case Dataset.ARRAYINT64:
			return CompoundLongDataset.ones(itemSize, shape);
		case Dataset.FLOAT32:
		case Dataset.ARRAYFLOAT32:
			return CompoundFloatDataset.ones(itemSize, shape);
		case Dataset.FLOAT64:
		case Dataset.ARRAYFLOAT64:
			return CompoundDoubleDataset.ones(itemSize, shape);
		case Dataset.COMPLEX64:
			if (itemSize != 2) {
				throw new IllegalArgumentException("Number of elements not compatible with complex type");
			}
			return ComplexFloatDataset.ones(shape);
		case Dataset.COMPLEX128:
			if (itemSize != 2) {
				throw new IllegalArgumentException("Number of elements not compatible with complex type");
			}
			return ComplexDoubleDataset.ones(shape);
		}
		throw new IllegalArgumentException("dtype not a known compound type");
	}
}

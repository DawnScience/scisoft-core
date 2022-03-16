/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.python;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.math3.complex.Complex;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.BroadcastUtils;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.InterfaceUtils;
import org.eclipse.january.dataset.LongDataset;
import org.eclipse.january.dataset.ObjectDataset;
import org.eclipse.january.dataset.ShapeUtils;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.dataset.SliceND;
import org.python.core.Py;
import org.python.core.PyArray;
import org.python.core.PyComplex;
import org.python.core.PyEllipsis;
import org.python.core.PyException;
import org.python.core.PyInteger;
import org.python.core.PyNone;
import org.python.core.PyObject;
import org.python.core.PySequence;
import org.python.core.PySequenceList;
import org.python.core.PySlice;
import org.python.core.PyString;

/**
 * Class of utilities for interfacing with Jython
 */
public class PythonUtils {

	/**
	 * Convert tuples/lists of tuples/lists to Java lists of lists. Also convert complex numbers to Apache Commons
	 * version and Jython strings to strings
	 * 
	 * @param obj
	 * @return converted object
	 */
	public static Object convertToJava(Object obj) {
		if (obj == null || obj instanceof PyNone)
			return null;

		if (obj instanceof PySequenceList) {
			obj = ((PySequenceList) obj).toArray();
		}

		if (obj instanceof PyArray) {
			obj = ((PyArray) obj).getArray();
		}

		if (obj instanceof List<?>) {
			@SuppressWarnings("unchecked")
			List<Object> jl = (List<Object>) obj;
			int l = jl.size();
			for (int i = 0; i < l; i++) {
				Object lo = jl.get(i);
				if (lo instanceof PyObject) {
					jl.set(i, convertToJava(lo));
				}
			}
			return obj;
		}

		if (obj.getClass().isArray()) {
			int l = Array.getLength(obj);
			for (int i = 0; i < l; i++) {
				Object lo = Array.get(obj, i);
				if (lo instanceof PyObject) {
					Array.set(obj, i, convertToJava(lo));
				}
			}
			return obj;
		}

		// NB PyLong gets converted to BigInteger automatically so pass it through
		if (obj instanceof BigInteger || !(obj instanceof PyObject)) {
			return obj;
		}

		if (obj instanceof PyComplex) {
			PyComplex z = (PyComplex) obj;
			return new Complex(z.real, z.imag);
		} else if (obj instanceof PyString) {
			return obj.toString();
		}

		return ((PyObject) obj).__tojava__(Object.class);
	}

	static class SliceData {
		SliceND slice; // slices
		/**
		 * Required or output shape
		 */
		int[] shape;
	}

	/**
	 * Convert an array of python slice objects to a slice array
	 * 
	 * @param indexes
	 * @param shape
	 * @return slice array
	 */
	private static SliceData convertPySlicesToSlice(final PyObject indexes, final int[] shape) {
		PyObject indices[] = indexes instanceof PySequenceList ? ((PySequenceList) indexes).getArray() : new PyObject[] { indexes };

		int orank = shape.length;

		int na = 0; // count new axes
		int nc = 0; // count collapsed dimensions
		int ns = 0; // count slices or ellipses
		boolean hasEllipsis = false;
		for (int j = 0; j < indices.length; j++) {
			PyObject index = indices[j];
			if (index instanceof PyNone) {
				na++;
			} else if (index instanceof PyInteger) {
				nc++;
			} else if (index instanceof PySlice) {
				ns++;
			} else if (index instanceof PyEllipsis) {
				if (hasEllipsis) {
					throw new IllegalArgumentException("Only one ellipsis is allowed");
				}
				hasEllipsis = true;
				ns++;
			} else {
				throw new IllegalArgumentException("Unsupported object in index: " + index);
			}
		}

		int spare = orank - nc - ns; // number of spare dimensions
		SliceND slice = new SliceND(shape);

		hasEllipsis = false;
		boolean[] sdim = new boolean[orank]; // flag which dimensions are sliced
		int[] axes = new int[na]; // new axes
		int i = 0;
		int a = 0; // new axes
		int c = 0; // collapsed dimensions
		for (int j = 0; i < orank && j < indices.length; j++) {
			PyObject index = indices[j];
			if (index instanceof PyEllipsis) {
				sdim[i++] = true;
				if (!hasEllipsis) { // pad out with full slices on first ellipsis
					hasEllipsis = true;
					for (int k = 0; k < spare; k++) {
						sdim[i++] = true;
					}
				}
			} else if (index instanceof PyInteger) {
				int n = ((PyInteger) index).getValue();
				if (n < -shape[i] || n >= shape[i]) {
					throw new PyException(Py.IndexError);
				}
				if (n < 0) {
					n += shape[i];
				}
				sdim[i] = false; // nb specifying indexes whilst using slices will reduce rank
				slice.setSlice(i++, n, n + 1, 1);
				c++;
			} else if (index instanceof PySlice) {
				PySlice pyslice = (PySlice) index;
				sdim[i] = true;
				Slice nslice = convertToSlice(pyslice);
				slice.setSlice(i++, nslice.getStart(), nslice.getStop(), nslice.getStep());
			} else if (index instanceof PyNone) { // newaxis
				axes[a++] = (hasEllipsis ? j + spare : j) - c;
			} else {
				throw new IllegalArgumentException("Unexpected item in indexing");
			}
		}

		assert nc == c;
		while (i < orank) {
			sdim[i++] = true;
		}
		while (a < na) {
			axes[a] = i - c + a;
			a++;
		}

		int[] sShape = slice.getShape();
		int[] newShape = new int[orank - nc];
		i = 0;
		for (int j = 0; i < orank; i++) {
			if (sdim[i]) {
				newShape[j++] = sShape[i];
			}
		}

		if (na > 0) {
			int[] oldShape = newShape;
			newShape = new int[newShape.length + na];
			i = 0;
			for (int k = 0, j = 0; i < newShape.length; i++) {
				if (k < na && i == axes[k]) {
					k++;
					newShape[i] = 1;
				} else {
					newShape[i] = oldShape[j++];
				}
			}
		}

		SliceData sd = new SliceData();
		sd.slice = slice;
		sd.shape = newShape;
		return sd;
	}

	/**
	 * @param pyslice
	 * @return slice
	 */
	public static Slice convertToSlice(PySlice pyslice) {
		return new Slice(pyslice.start instanceof PyNone ? null : ((PyInteger) pyslice.start).getValue(),
					pyslice.stop instanceof PyNone ? null : ((PyInteger) pyslice.stop).getValue(),
					pyslice.step instanceof PyNone ? null : ((PyInteger) pyslice.step).getValue());
	}

	/**
	 * @param indexes
	 * @param shape
	 * @return N-D slice
	 */
	public static SliceND convertToSliceND(PyObject indexes, int[] shape) {
		return convertPySlicesToSlice(indexes, shape).slice;
	}

	/**
	 * Jython method to get slice within a dataset
	 * 
	 * @param a
	 *            dataset
	 * @param indexes
	 *            can be a mixed array of integers or slices
	 * @return dataset of specified sub-dataset
	 * @throws DatasetException
	 */
	public static IDataset getSlice(final ILazyDataset a, final PyObject indexes) throws DatasetException {
		int[] shape = a.getShape();

		SliceData slice = convertPySlicesToSlice(indexes, shape);
		IDataset dataSlice;
		if (a instanceof IDataset) {
			dataSlice = ((IDataset) a).getSliceView(slice.slice);
		} else {
			dataSlice = a.getSlice(slice.slice);
		}
		dataSlice.setShape(slice.shape);

		return dataSlice;
	}

	/**
	 * Jython method to set slice within a dataset
	 * 
	 * @param a
	 *            dataset
	 * @param object
	 *            can an item or a dataset
	 * @param indexes
	 *            can be a mixed array of integers or slices
	 */
	public static void setSlice(Dataset a, Object object, final PyObject indexes) {
		if (a.isComplex() || a.getElementsPerItem() == 1) {
			if (object instanceof PySequence) {
				object = DatasetFactory.createFromObject(a.getClass(), object);
			}
		}

		SliceData slice = convertPySlicesToSlice(indexes, a.getShapeRef());
		if (object instanceof IDataset) {
			IDataset d = (IDataset) object;

			int[] iShape = d instanceof Dataset ? ((Dataset) d).getShapeRef() : d.getShape();
			int[] sShape = slice.slice.getShape();
			if (!Arrays.equals(iShape, slice.shape)) { // check input shape matches required one
				try {
					if (iShape.length > slice.shape.length) {
						BroadcastUtils.broadcastShapesToMax(iShape, slice.shape);
						iShape = slice.shape;
					} else {
						iShape = BroadcastUtils.broadcastShapesToMax(slice.shape, iShape).get(0);
					}
				} catch (IllegalArgumentException e) {
					throw new IllegalArgumentException("Input dataset shape must match slice shape");
				}
			} else if (!Arrays.equals(iShape, sShape)) { // check input shape matches slice shape
				if (iShape.length > sShape.length) {
					BroadcastUtils.broadcastShapesToMax(iShape, sShape);
				}
				iShape = sShape;
			}

			d = d.getSliceView();
			try {
				d.setShape(iShape);
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("Input dataset could not be set to slice shape");
			}
			object = d;
		}
		a.setSlice(object, slice.slice);
	}

	/**
	 * Create a dataset from object (as workaround for Jython's funky dispatcher calling wrong method)
	 * @param clazz
	 * @param obj
	 *            can be a Java list, array or Number
	 * @return dataset
	 * @throws IllegalArgumentException if dataset interface is not known
	 */
	public static Dataset createFromObject(Class<? extends Dataset> clazz, final Object obj) {
		return DatasetFactory.createFromObject(clazz, obj, null);
	}

	/**
	 * Get dataset type from an object. The following are supported: Java Number objects, Apache common math Complex
	 * objects, Java arrays and lists
	 *
	 * @param obj
	 * @return dataset type
	 */
	public static Class<? extends Dataset> getInterfaceFromObject(Object obj) {
		if (obj == null) {
			return ObjectDataset.class;
		}

		Class<? extends Dataset> clazz = null;

		if (obj instanceof List<?>) {
			List<?> jl = (List<?>) obj;
			int l = jl.size();
			for (int i = 0; i < l; i++) {
				clazz = InterfaceUtils.getBestInterface(clazz, getInterfaceFromObject(jl.get(i)));
			}
		} else if (obj.getClass().isArray()) {
			Class<?> ca = obj.getClass().getComponentType();
			if (InterfaceUtils.isElementSupported(ca)) {
				return getInterfaceFromClass(ca);
			}
			int l = Array.getLength(obj);
			for (int i = 0; i < l; i++) {
				Object lo = Array.get(obj, i);
				clazz = InterfaceUtils.getBestInterface(clazz, getInterfaceFromObject(lo));
			}
		} else if (obj instanceof Dataset) {
			return ((Dataset) obj).getClass();
		} else if (obj instanceof ILazyDataset) {
			clazz = getInterfaceFromClass(((ILazyDataset) obj).getElementClass(), ((ILazyDataset) obj).getElementsPerItem());
		} else {
			clazz = getInterfaceFromClass(obj.getClass());
		}
		return clazz;
	}

	/**
	 * Get dataset type from an element class
	 *
	 * @param cls element class
	 * @return dataset type
	 */
	private static Class<? extends Dataset> getInterfaceFromClass(Class<? extends Object> cls, int isize) {
		if (BigInteger.class.equals(cls)) {
			return LongDataset.class;
		}
		return InterfaceUtils.getInterfaceFromClass(isize, cls);
	}

	/**
	 * Get dataset type from an element class
	 *
	 * @param cls element class
	 * @return dataset type
	 */
	private static Class<? extends Dataset> getInterfaceFromClass(Class<? extends Object> cls) {
		if (BigInteger.class.equals(cls)) {
			return LongDataset.class;
		}
		return InterfaceUtils.getInterfaceFromClass(1, cls);
	}

	/**
	 * Concatenate the set of datasets along given axis
	 * @param as datasets
	 * @param axis to concatenate along
	 * @return concatenated dataset
	 */
	public static Dataset concatenate(final IDataset[] as, int axis) {
		return DatasetUtils.concatenate(convertToFirst(as), axis);
	}

	private static final Dataset[] convertToFirst(final IDataset[] as) {
		if (as == null) {
			return null;
		}

		int n = as.length;
		Class<? extends Dataset> ac = InterfaceUtils.getInterface(as[0]);
		for (int i = 1; i < n; i++) {
			ac = InterfaceUtils.getBestInterface(ac, InterfaceUtils.getInterface(as[i]));
		}

		// TODO remove this shape checking once January can concatenate zero-sized datasets
		List<Dataset> ds = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			Dataset ai = DatasetUtils.cast(ac, as[i]);
			if (!ShapeUtils.isZeroSize(ai.getShapeRef())) {
				ds.add(ai);
			}
		}

		return ds.toArray(new Dataset[ds.size()]);
	}
}

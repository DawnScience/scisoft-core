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
import java.util.List;

import org.apache.commons.math3.complex.Complex;
import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.dataset.Slice;
import org.eclipse.dawnsci.analysis.api.dataset.SliceND;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
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

		if (obj instanceof BigInteger || !(obj instanceof PyObject))
			return obj;

		if (obj instanceof PyComplex) {
			PyComplex z = (PyComplex) obj;
			return new Complex(z.real, z.imag);
		} else if (obj instanceof PyString) {
			return obj.toString();
		}

		return ((PyObject) obj).__tojava__(Object.class);
	}

	static class SliceData {
		SliceND slice;  // slices
		boolean[] sdim; // flag which dimensions are sliced
		int[] axes;  // new axes
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
		SliceData slice = new SliceData();

		int na = 0; // count new axes
		int nc = 0; // count collapsed dimensions
		int ns = 0; // count slices or ellipses
		for (int j = 0; j < indices.length; j++) {
			PyObject index = indices[j];
			if (index instanceof PyNone)
				na++;
			else if (index instanceof PyInteger)
				nc++;
			else if (index instanceof PySlice)
				ns++;
			else if (index instanceof PyEllipsis)
				ns++;
		}

		int spare = orank - nc - ns; // number of spare dimensions
		slice.slice = new SliceND(shape);
		slice.axes = new int[na];
		slice.sdim = new boolean[orank]; // flag which dimensions are sliced

		boolean hasEllipse = false;
		int i = 0;
		int a = 0; // new axes
		int c = 0; // collapsed dimensions
		for (int j = 0; i < orank && j < indices.length; j++) {
			PyObject index = indices[j];
			if (index instanceof PyEllipsis) {
				slice.sdim[i++] = true;
				if (!hasEllipse) { // pad out with full slices on first ellipse
					hasEllipse = true;
					for (int k = 0; k < spare; k++) {
						slice.sdim[i++] = true;
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
				slice.sdim[i] = false; // nb specifying indexes whilst using slices will reduce rank
				slice.slice.setSlice(i++, n, n + 1, 1);
				c++;
			} else if (index instanceof PySlice) {
				PySlice pyslice = (PySlice) index;
				slice.sdim[i] = true;
				slice.slice.setSlice(i++, pyslice.start instanceof PyNone ? null : ((PyInteger) pyslice.start).getValue(),
						pyslice.stop instanceof PyNone ? null : ((PyInteger) pyslice.stop).getValue(),
						pyslice.step instanceof PyNone ? 1 : ((PyInteger) pyslice.step).getValue());
			} else if (index instanceof PyNone) { // newaxis
				slice.axes[a++] = (hasEllipse ? j + spare : j) - c;
			} else {
				throw new IllegalArgumentException("Unexpected item in indexing");
			}
		}

		assert nc == c;
		while (i < orank) {
			slice.sdim[i++] = true;
		}
		while (a < na) {
			slice.axes[a] = i - c + a;
			a++;
		}

		return slice;
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
	 */
	public static IDataset getSlice(final ILazyDataset a, final PyObject indexes) {
		int[] shape = a.getShape();
		int orank = shape.length;

		SliceData slice = convertPySlicesToSlice(indexes, shape);
		IDataset dataSlice;
		if (a instanceof IDataset) {
			dataSlice = DatasetUtils.convertToDataset((IDataset) a).getSliceView(slice.slice);
		} else {
			dataSlice = a.getSlice(slice.slice);
		}

		// removed dimensions that were not sliced (i.e. that were indexed with an integer)
		int rank = 0;
		for (int i = 0; i < orank; i++) {
			if (slice.sdim[i])
				rank++;
		}

		if (rank < orank) {
			int[] oldShape = dataSlice.getShape();
			int[] newShape = new int[rank];
			int j = 0;
			for (int i = 0; i < orank; i++) {
				if (slice.sdim[i]) {
					newShape[j++] = oldShape[i];
				}
			}
			dataSlice.setShape(newShape);
		}

		int n = slice.axes.length;
		if (n > 0) {
			int[] oldShape = dataSlice.getShape();
			int[] newShape = new int[rank + n];
			int j = 0;
			int k = 0;
			for (int i = 0; i < newShape.length; i++) {
				if (k < n && i == slice.axes[k]) {
					k++;
					newShape[i] = 1;
				} else {
					newShape[i] = oldShape[j++];
				}
			}
			dataSlice.setShape(newShape);
		}
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
				object = DatasetFactory.createFromObject(object, a.getDtype());
			}
		}

		int[] shape = a.getShape();
		SliceData slice = convertPySlicesToSlice(indexes, shape);
		a.setSlice(object, slice.slice);
	}
}

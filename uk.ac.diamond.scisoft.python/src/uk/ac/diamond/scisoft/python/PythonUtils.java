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

package uk.ac.diamond.scisoft.python;

import java.lang.reflect.Array;
import java.util.List;

import org.apache.commons.math.complex.Complex;
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
import org.python.core.PyTuple;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.ILazyDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Slice;

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

		if (!(obj instanceof PyObject))
			return obj;

		if (obj instanceof PyComplex) {
			PyComplex z = (PyComplex) obj;
			return new Complex(z.real, z.imag);
		} else if (obj instanceof PyString) {
			return obj.toString();
		}

		return ((PyObject) obj).__tojava__(Object.class);
	}

	/**
	 * Convert an array of python slice objects to a slice array
	 * 
	 * @param indexes
	 * @param shape
	 * @param isDimSliced
	 * @return slice array
	 */
	private static Slice[] convertPySlicesToSlice(final PyObject indexes, final int[] shape, final boolean[] isDimSliced) {
		PyObject indices[] = (indexes instanceof PyTuple) ? ((PyTuple) indexes).getArray() : new PyObject[] { indexes };

		int slen;
		int orank = shape.length;

		// first check the dimensionality
		if (indices.length > orank) {
			slen = orank;
		} else {
			slen = indices.length;
		}

		boolean hasEllipse = false;
		int i = 0;

		Slice[] slice = new Slice[orank];

		for (int j = 0; j < slen; j++) {
			PyObject index = indices[j];
			if (index instanceof PyEllipsis) {
				isDimSliced[i] = true;
				slice[i++] = null;
				if (!hasEllipse) { // pad out with full slices on first ellipse
					hasEllipse = true;
					for (int k = slen; k < orank; k++) {
						isDimSliced[i] = true;
						slice[i++] = null;
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
				isDimSliced[i] = false; // nb specifying indexes whilst using slices will reduce rank
				slice[i++] = new Slice(n, n + 1);
			} else if (index instanceof PySlice) {
				PySlice pyslice = (PySlice) index;
				isDimSliced[i] = true;
				slice[i++] = new Slice(pyslice.start instanceof PyNone ? null : ((PyInteger) pyslice.start).getValue(),
						pyslice.stop instanceof PyNone ? null : ((PyInteger) pyslice.stop).getValue(),
						pyslice.step instanceof PyNone ? null : ((PyInteger) pyslice.step).getValue());
			} else {
				throw new IllegalArgumentException("Unexpected item in indexing");
			}
		}

		while (i < orank) {
			isDimSliced[i++] = true;
		}

		return slice;
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
		boolean[] sdim = new boolean[orank]; // flag which dimensions are sliced

		IDataset dataSlice = a.getSlice(convertPySlicesToSlice(indexes, shape, sdim));

		// removed dimensions that were not sliced (i.e. that were indexed with an integer)
		int rank = 0;
		for (int i = 0; i < orank; i++) {
			if (sdim[i])
				rank++;
		}

		if (rank < orank) {
			int[] oldShape = dataSlice.getShape();
			int[] newShape = new int[rank];
			int j = 0;
			for (int i = 0; i < orank; i++) {
				if (sdim[i]) {
					newShape[j++] = oldShape[i];
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
	public static void setSlice(AbstractDataset a, Object object, final PyObject indexes) {
		if (a.isComplex() || a.getElementsPerItem() == 1) {
			if (object instanceof PySequence) {
				object = AbstractDataset.array(object, a.getDtype());
			}
		}

		int[] shape = a.getShape();
		boolean[] sdim = new boolean[shape.length];
		a.setSlice(object, convertPySlicesToSlice(indexes, shape, sdim));
	}
}

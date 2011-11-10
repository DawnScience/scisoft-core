/*-
 * Copyright © 2011 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.dataset;

import org.python.core.Py;
import org.python.core.PyEllipsis;
import org.python.core.PyException;
import org.python.core.PyInteger;
import org.python.core.PyNone;
import org.python.core.PyObject;
import org.python.core.PySlice;
import org.python.core.PyTuple;

/**
 * Class to represent a slice through a single dimension of a multi-dimensional dataset. A slice
 * comprises a starting position, a stopping position (not included) and a stepping size.
 */
public class Slice {

	private Integer start;
	private Integer stop;
	private int step;

	private int length; // max length of dimension

	public Slice() {
		this(null);
	}

	/**
	 * Default to starting at 0 with steps of 1
	 * @param stop if null, then default to whatever shape is when converted
	 */
	public Slice(final Integer stop) {
		this(null, stop);
	}

	/**
	 * Default to steps of 1
	 * @param start if null, then default to 0
	 * @param stop if null, then default to whatever shape is when converted
	 */
	public Slice(final Integer start, final Integer stop) {
		this(start, stop, 1);
	}

	/**
	 * 
	 * @param start if null, then default to bound
	 * @param stop if null, then default to bound
	 * @param step if null, then default to 1
	 */
	public Slice(final Integer start, final Integer stop, final Integer step) {
		this.start = start;
		this.stop = stop;
		this.step = step == null ? 1 : step;
		length = -1;
	}

	@Override
	public Slice clone() {
		Slice c = new Slice(start, stop, step);
		c.length = length;
		return c;
	}

	/**
	 * Set maximum value of slice
	 * @param length
	 * @return this slice
	 */
	public Slice setLength(int length) {
		if (stop != null && step > 0 && length < stop) {
			throw new IllegalArgumentException("Length must be greater than or equal to stop");
		}
		if (start != null && step < 0 && length < start) {
			throw new IllegalArgumentException("Length must be greater than or equal to start");
		}
		this.length = length;
		return this;
	}

	/**
	 * @return true if slice represents complete dimension
	 */
	public boolean isSliceComplete() {
		if (start == null && stop == null && (step == 1 || step == -1))
			return true;
		if (length > 0) {
			return getNumSteps() == length;
		}

		return true;
	}

	/**
	 * @return maximum value of slice
	 */
	public int getLength() {
		return length;
	}

	/**
	 * @return starting position of slice
	 */
	public Integer getStart() {
		return start;
	}

	/**
	 * @return stopping position of slice
	 */
	public Integer getStop() {
		return stop;
	}

	/**
	 * @return step size of slice
	 */
	public int getStep() {
		return step;
	}

	/**
	 * Set starting position of slice
	 * @param start (can be null)
	 */
	public void setStart(Integer start) {
		if (start != null && length > 0) {
			if (step > 0) {
				int end = stop == null ? length : stop;
				if (start >= end) {
					throw new IllegalArgumentException("Non-null start must be less than end");
				}
			} else {
				int end = stop == null ? -1 : stop;
				if (start < end) {
					throw new IllegalArgumentException("Non-null start must be greater than end for negative step");
				}
			}
		}
		this.start = start;
	}

	/**
	 * Set stopping position of slice
	 * @param stop (can be null)
	 */
	public void setStop(Integer stop) {
		if (stop != null && length > 0) {
			if (step > 0) {
				int beg = start == null ? 0 : start;
				if (stop < beg) {
					throw new IllegalArgumentException("Non-null stop must be greater than or equal to beginning");
				}
			} else {
				int beg = start == null ? length - 1 : start;
				if (stop >= beg) {
					throw new IllegalArgumentException("Non-null stop must be less than beginning for negative step");
				}
			}
			if (stop > length)
				stop = length;
		}
		this.stop = stop;
	}

	/**
	 * Set start and end from implied number of steps. I.e. shift start to position given by
	 * parameter whilst keeping size of slice fixed
	 * @param beg
	 * @return true if end reached
	 */
	public boolean setPosition(int beg) {
		boolean end = false;
		int len = getNumSteps();
		int max = getNumSteps(beg, length);
		if (len > max) {
			len = max;
			end = true;
		}
		start = beg;
		stop = start + (len-1) * step + 1;
		return end;
	}

	/**
	 * Set step size of slice
	 * @param step
	 */
	public void setStep(int step) {
		if (step == 0) {
			throw new IllegalArgumentException("Step must not be zero");
		}
		this.step = step;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		if (start != null) {
			if (start != 0)
				s.append(start);

			if (stop != null && start == (stop - 1)) {
				if (s.length() == 0)
					s.append(start);
				return s.toString();
			}
		} else {
			if (stop != null && stop == 1)
				return "0";
		}
		s.append(':');
		if (stop != null) {
			if (length < 0 || length != stop) {
				s.append(stop);
			}
		}

		if (step != 1) {
			s.append(':');
			s.append(step);
		}
		return s.toString();
	}

	/**
	 * @return number of steps in slice
	 */
	public int getNumSteps() {
		if (length < 0) {
			if (stop == null)
				throw new IllegalStateException("Slice is underspecified - stop is null and length is negative");
			int beg = start == null ? (step > 0 ? 0: stop-1) : start;
			if (step > 0 && stop <= beg)
				return 0;
			if (step < 0 && stop > beg)
				return 0;
			return getNumSteps(beg, stop);
		}
		int beg = start == null ? (step > 0 ? 0: length-1) : start;
		int end = stop == null ? (step > 0 ? length : -1) : stop;
		return getNumSteps(beg, end);
	}

	/**
	 * @param beg
	 * @param end (exclusive)
	 * @return number of steps between limits
	 */
	public int getNumSteps(int beg, int end) {
		int del = step > 0 ? 1 : -1;
		return (end - beg - del) / step + 1;
	}

	/**
	 * @return last value in slice (< stop if step > 0, > stop if step < 0)
	 */
	public int getEnd() {
		int n = getNumSteps() - 1;
		if (n < 0)
			throw new IllegalStateException("End is not defined");
		if (length < 0) {
			if (start == null) {
				return step*n;
			}
			return start + step*n;
		}
		int beg = start == null ? (step > 0 ? 0: length-1) : start;
		return beg + step*n;
	}

	/**
	 * Populate given start, stop, step arrays from given slice array 
	 * @param shape
	 * @param start
	 * @param stop
	 * @param step
	 * @param slice
	 */
	public static void convertFromSlice(final Slice[] slice, final int[] shape, final int[] start, final int[] stop, final int[] step) {
		final int rank = shape.length;
		final int length = slice == null ? 0 : slice.length;
	
		int i = 0;
		for (; i < length; i++) {
			@SuppressWarnings("null")
			Slice s = slice[i];
			if (s == null) {
				start[i] = 0;
				stop[i] = shape[i];
				step[i] = 1;
				continue;
			}
			int n;
			if (s.start == null) {
				start[i] = s.step > 0 ? 0 : shape[i] - 1;
			} else {
				n = s.start;
				if (n < 0)
					n += shape[i];
				if (n < 0 || n >= shape[i]) {
					throw new IllegalArgumentException(String.format("Start is out of bounds: %d is not in [%d,%d)",
							n, s.start, shape[i]));
				}
				start[i] = n;
			}

			if (s.stop == null) {
				stop[i] = s.step > 0 ? shape[i] : -1;
			} else {
				n = s.stop;
				if (n < 0)
					n += shape[i];
				if (n < 0 || n > shape[i]) {
					throw new IllegalArgumentException(String.format("Stop is out of bounds: %d is not in [%d,%d)",
							n, s.stop, shape[i]));
				}
				stop[i] = n;
			}

			n = s.step;
			if (n == 0) {
				throw new IllegalArgumentException("Step cannot be zero");
			}
			if (n > 0) {
				if (start[i] > stop[i])
					throw new IllegalArgumentException("Start must be less than stop for positive steps");
			} else {
				if (start[i] < stop[i])
					throw new IllegalArgumentException("Start must be greater than stop for negative steps");				
			}
			step[i] = n;
		}
		for (; i < rank; i++) {
			start[i] = 0;
			stop[i] = shape[i];
			step[i] = 1;
		}
	}

	/**
	 * Convert an array of python slice objects to a slice array
	 * @param indexes
	 * @param shape
	 * @param isDimSliced
	 * @return slice array
	 */
	public static Slice[] convertPySlicesToSlice(final PyObject indexes, final int[] shape, final boolean[] isDimSliced) {
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
				slice[i++] = new Slice(n, n+1);
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
	 * Convert from a set of integer arrays to a slice array
	 * @param start
	 * @param stop
	 * @param step
	 * @return a slice array
	 */
	public static Slice[] convertToSlice(final int[] start, final int[] stop, final int[] step) {
		int orank = start.length;

		if (stop.length != orank || step.length != orank) {
			throw new IllegalArgumentException("All arrays must be same length");
		}

		Slice[] slice = new Slice[orank];

		for (int j = 0; j < orank; j++) {
			slice[j] = new Slice(start[j], stop[j], step[j]);
		}

		return slice;
	}

	/**
	 * @param slices
	 * @return string specifying slices
	 */
	public static String createString(Slice... slices) {
		StringBuilder t = new StringBuilder();
		for (Slice s : slices) {
			t.append(s != null ? s.toString() : ':');
			t.append(',');
		}
		t.deleteCharAt(t.length()-1);
		return t.toString();
	}
}

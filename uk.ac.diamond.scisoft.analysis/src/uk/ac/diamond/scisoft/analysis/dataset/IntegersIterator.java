/*-
 * Copyright 2013 Diamond Light Source Ltd.
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class to run over an array of integer datasets and return its items
 */
public class IntegersIterator extends IndexIterator {
	final private int[] ishape; // shape of input
	final private int irank; // rank of input shape
	final private int[] oshape; // shape of output
	final private int orank; // rank of output shape
	private int offset; // offset of index subspace in new position 
	private int srank; // rank of subspace

	final private IndexIterator it;

	/**
	 * position in input shape
	 */
	final private int[] ipos;
	/**
	 * position in output shape
	 */
	final private int[] opos;
	final private List<Object> indexes;

	/**
	 * Constructor for an iterator over the items of an array of objects
	 * @param shape of entire data array
	 * @param index an array of integer dataset, boolean dataset, slices or null entries (same as full slices)
	 */
	@SuppressWarnings("null")
	public IntegersIterator(final int[] shape, final Object... index) {
		ishape = shape.clone();
		irank = shape.length;
		if (irank < index.length) {
			throw new IllegalArgumentException("Number of index datasets is greater than rank of dataset");
		}
		indexes = new ArrayList<Object>();
		for (Object i : index) {
			if (i instanceof BooleanDataset) { // turn boolean datasets into integer ones
				for (IntegerDataset id : Comparisons.nonZero((AbstractDataset) i)) {
					indexes.add(id);
				}
			} else if (i == null || i instanceof Slice || i instanceof IntegerDataset) {
				indexes.add(i);
			} else {
				throw new IllegalArgumentException("Unsupported object for indexing");
			}
		}
		if (indexes.size() < irank) { // pad out index list
			for (int i = indexes.size(); i < irank; i++)
				indexes.add(null);
		}

		int ilength = -1;
		int[] cshape = null;
		int first = -1; // index of first null or slice after non-null index
		boolean intact = true;
		srank = 0;
		for (int i = 0; i < irank; i++) { // see if shapes are consistent and subspace is intact
			Object obj = indexes.get(i);
			if (obj instanceof IntegerDataset) {
				IntegerDataset ind = (IntegerDataset) obj;
				if (first > 0) {
					intact = false;
				}

				int l = ind.size;
				if (ilength < 0) {
					ilength = l;
				} else if (l != ilength) {
					throw new IllegalArgumentException("Index datasets do not have same size");
				}
				if (cshape == null) {
					cshape = ind.shape;
					srank = cshape.length;
					offset = i;
				} else if (!Arrays.equals(ind.shape, cshape)) {
					throw new IllegalArgumentException("Index datasets do not have same shape");
				}
			} else {
				if (cshape != null) {
					if (first < 0)
						first = i;
				}
			}
		}

		List<Integer> oShape = new ArrayList<Integer>(irank);

		if (intact) { // get new output shape list
			boolean used = false;
			for (int i = 0; i < irank; i++) {
				Object obj = indexes.get(i);
				if (obj instanceof IntegerDataset) {
					IntegerDataset ind = (IntegerDataset) obj;
					if (!used) {
						used = true;
						for (int j : ind.shape) {
							oShape.add(j);
						}
					}
				} else if (obj instanceof Slice) {
					Slice s = (Slice) obj;
					int l = ishape[i];
					s.setLength(l);
					oShape.add(s.getNumSteps());
				} else {
					oShape.add(ishape[i]);
				}
			}
		} else {
			assert cshape != null;
			for (int j : cshape) {
				oShape.add(j);
			}
			for (int i = 0; i < irank; i++) {
				Object obj = indexes.get(i);
				if (obj == null) {
					oShape.add(ishape[i]);
				} else if (obj instanceof Slice) {
					Slice s = (Slice) obj;
					int l = ishape[i];
					s.setLength(l);
					oShape.add(s.getNumSteps());
				}
			}
		}
		orank = oShape.size();
		oshape = new int[orank];
		for (int i = 0; i < orank; i++) {
			oshape[i] = oShape.get(i);
		}

		for (int i = 0; i < irank; i++) { // check input indexes for out of bounds
			Object obj = indexes.get(i);
			if (obj instanceof IntegerDataset) {
				IntegerDataset ind = (IntegerDataset) obj;
				if (ind.min().intValue() < 0 || ind.max().intValue() >= shape[i]) {
					throw new IllegalArgumentException("A value in index datasets is outside permitted range");
				}
			}
		}

		ipos = new int[irank];
		it = new PositionIterator(oshape);
		opos = it.getPos();
	}

	/**
	 * @return shape of output
	 */
	public int[] getShape() {
		return oshape;
	}

	@Override
	public boolean hasNext() {
		if (it.hasNext()) {
			int i = 0;
			for (; i < offset; i++) {
				Object obj = indexes.get(i);
				if (obj == null) {
					ipos[i] = opos[i];
				} else if (obj instanceof Slice) {
					Slice s = (Slice) obj;
					ipos[i] = s.getPosition(opos[i]); // overwrite position
				} else {
					throw new IllegalStateException("Bad state: index dataset before offset");
				}
			}
			int[] spos = srank > 0 ? Arrays.copyOfRange(opos, i, i+srank) : opos;
			for (int j = 0; j < irank; j++) {
				Object obj = indexes.get(j);
				if (obj instanceof IntegerDataset) {
					IntegerDataset ind = (IntegerDataset) obj;
					ipos[i++] = ind.get(spos);
				}
			}
			if (srank > 0) {
				int o = orank - irank;
				for (; i < irank; i++) {
					Object obj = indexes.get(i);
					if (obj == null) {
						ipos[i] = opos[i+o];
					} else if (obj instanceof Slice) {
						Slice s = (Slice) obj;
						ipos[i] = s.getPosition(opos[i+o]); // overwrite position
					} else {
						throw new IllegalStateException("Bad state: index dataset after subspace");
					}
				}
			}
			System.err.println(Arrays.toString(opos) + ", " + Arrays.toString(spos) + ", " + Arrays.toString(ipos));
			return true;
		}
		return false;
	}

	@Override
	public int[] getPos() {
		return ipos;
	}

	@Override
	public void reset() {
		it.reset();
		Arrays.fill(ipos, 0);
		index = 0;
	}
}

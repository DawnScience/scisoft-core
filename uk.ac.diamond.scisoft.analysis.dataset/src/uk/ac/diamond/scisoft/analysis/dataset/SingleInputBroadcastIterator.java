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

import java.util.Arrays;
import java.util.List;

/**
 * Class to run over a single dataset with NumPy broadcasting to promote shapes
 * which have lower rank and outputs to a second dataset
 */
public class SingleInputBroadcastIterator extends IndexIterator {
	private int[] maxShape;
	private int[] aShape;
	private final Dataset aDataset;
	private final Dataset oDataset;
	private int[] aStride;
	private int[] oStride;

	final private int endrank;

	/**
	 * position in dataset
	 */
	private final int[] pos;
	private final int[] aDelta;
	private final int[] oDelta; // this being non-null means output is different from inputs
	private final int aStep, oStep;
	private int aMax;
	private int aStart, oStart;
	private final boolean outputA;

	/**
	 * Index in array
	 */
	public int aIndex, oIndex;

	/**
	 * Current value in array
	 */
	public double aDouble;

	/**
	 * Current value in array
	 */
	public long aLong;

	private boolean asDouble = true;

	/**
	 * @param a
	 * @param o (can be null for new dataset, or a)
	 */
	public SingleInputBroadcastIterator(Dataset a, Dataset o) {
		this(a, o, false);
	}

	/**
	 * @param a
	 * @param o (can be null for new dataset, or a)
	 * @param createIfNull (by default, can create float or complex datasets)
	 */
	public SingleInputBroadcastIterator(Dataset a, Dataset o, boolean createIfNull) {
		this(a, o, createIfNull, false, true);
	}

	/**
	 * @param a
	 * @param o (can be null for new dataset, or a)
	 * @param createIfNull
	 * @param allowInteger if true, can create integer datasets
	 * @param allowComplex if true, can create complex datasets
	 */
	public SingleInputBroadcastIterator(Dataset a, Dataset o, boolean createIfNull, boolean allowInteger, boolean allowComplex) {
		List<int[]> fullShapes = BroadcastIterator.setupShapes(a.getShapeRef(), o == null ? null : o.getShapeRef());

		checkItemSize(a, o);

		maxShape = fullShapes.remove(0);

		oStride = null;
		if (o != null && !Arrays.equals(maxShape, o.getShapeRef())) {
			throw new IllegalArgumentException("Output does not match broadcasted shape");
		}
		aShape = fullShapes.remove(0);

		int rank = maxShape.length;
		endrank = rank - 1;

		aDataset = a.reshape(aShape);
		aStride = AbstractDataset.createBroadcastStrides(aDataset, maxShape);
		outputA = o == a;
		if (outputA) {
			oStride = aStride;
			oDelta = null;
			oStep = 0;
			oDataset = aDataset;
		} else if (o != null) {
			oStride = AbstractDataset.createBroadcastStrides(o, maxShape);
			oDelta = new int[rank];
			oStep = o.getElementsPerItem();
			oDataset = o;
		} else if (createIfNull) {
			int is = aDataset.getElementsPerItem();
			int dt = aDataset.getDtype();
			if (aDataset.isComplex() && !allowComplex) {
				is = 1;
				dt = AbstractDataset.getBestFloatDType(dt);
			} else if (!aDataset.hasFloatingPointElements() && !allowInteger) {
				dt = AbstractDataset.getBestFloatDType(dt);
			}
			oDataset = DatasetFactory.zeros(is, maxShape, dt);
			oStride = AbstractDataset.createBroadcastStrides(oDataset, maxShape);
			oDelta = new int[rank];
			oStep = oDataset.getElementsPerItem();
		} else {
			oDelta = null;
			oStep = 0;
			oDataset = o;
		}

		pos = new int[rank];
		aDelta = new int[rank];
		aStep = aDataset.getElementsPerItem();
		for (int j = endrank; j >= 0; j--) {
			aDelta[j] = aStride[j] * aShape[j];
			if (oDelta != null) {
				oDelta[j] = oStride[j] * maxShape[j];
			}
		}
		if (endrank < 0) {
			aMax = aStep;
		} else {
			aMax = Integer.MIN_VALUE; // use max delta
			for (int j = endrank; j >= 0; j--) {
				if (aDelta[j] > aMax) {
					aMax = aDelta[j];
				}
			}
		}
		aStart = aDataset.getOffset();
		aMax += aStart;
		oStart = oDelta == null ? 0 : oDataset.getOffset();
		reset();
	}

	/**
	 * Set to output doubles
	 * @param asDouble
	 */
	public void setDoubleOutput(boolean asDouble) {
		this.asDouble = asDouble;
	}

	private static void checkItemSize(Dataset a, Dataset o) {
		final int isa = a.getElementsPerItem();
		if (o != null) {
			final int iso = o.getElementsPerItem();
			if (isa != 1 && iso != isa) {
				throw new IllegalArgumentException("Can not output to dataset whose number of elements per item mismatch inputs'");
			}
		}
	}

	@Override
	public int[] getShape() {
		return maxShape;
	}

	@Override
	public boolean hasNext() {
		int j = endrank;
		int oldA = aIndex;
		for (; j >= 0; j--) {
			pos[j]++;
			aIndex += aStride[j];
			if (oDelta != null)
				oIndex += oStride[j];
			if (pos[j] >= maxShape[j]) {
				pos[j] = 0;
				aIndex -= aDelta[j]; // reset these dimensions
				if (oDelta != null)
					oIndex -= oDelta[j];
			} else {
				break;
			}
		}
		if (j == -1) {
			if (endrank >= 0) {
				aIndex = aMax;
				return false;
			}
			aIndex += aStep;
			if (oDelta != null)
				oIndex += oStep;
		}
		if (outputA) {
			oIndex = aIndex;
		}

		if (oldA != aIndex) {
			if (asDouble) {
				aDouble = aDataset.getElementDoubleAbs(aIndex);
			} else {
				aLong = aDataset.getElementLongAbs(aIndex);
			}
		}

		return aIndex != aMax;
	}

	/**
	 * @return output dataset (can be null)
	 */
	public Dataset getOutput() {
		return oDataset;
	}

	@Override
	public int[] getPos() {
		return pos;
	}

	@Override
	public void reset() {
		for (int i = 0; i <= endrank; i++)
			pos[i] = 0;

		if (endrank >= 0) {
			pos[endrank] = -1;
			aIndex = aStart - aStride[endrank];
			oIndex = oStart - (oStride == null ? 0 : oStride[endrank]);
		} else {
			aIndex = -aStep;
			oIndex = -oStep;
		}

		// for zero-ranked datasets
		if (aIndex == 0) {
			aDouble = aDataset.getElementDoubleAbs(aIndex);
			if (aMax == aIndex)
				aMax++;
		}
	}
}

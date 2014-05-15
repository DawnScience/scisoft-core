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

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Mathematics class for lazy datasets
 */
public final class LazyMaths {
	/**
	 * Setup the logging facilities
	 */
	protected static final Logger logger = LoggerFactory.getLogger(LazyMaths.class);

	/**
	 * @param data
	 * @param axis (can be negative)
	 * @return sum along axis in lazy dataset
	 */
	public static AbstractDataset sum(final ILazyDataset data, int axis) {
		int rank = data.getRank();
		if (axis < 0)
			axis += rank;
		if (axis < 0 || axis >= rank) {
			logger.error("Axis argument is outside allowed range");
			throw new IllegalArgumentException("Axis argument is outside allowed range");
		}

		final int[] shape = data.getShape();

		final int[] nshape = shape.clone();
		nshape[axis] = 1;

		final AbstractDataset sum = AbstractDataset.zeros(nshape, Dataset.FLOAT64);
		final int length = shape[axis];

		final int[] start = new int[shape.length];
		final int[] stop = shape.clone();
		final int[] step = new int[shape.length];
		Arrays.fill(step, 1);

		for (int i = 0; i < length; i++) {
			start[axis] = i;
			stop[axis] = i + 1;
			sum.iadd(data.getSlice(start, stop, step));
		}

		sum.setShape(AbstractDataset.squeezeShape(shape, axis));
		return sum;
	}

	/**
	 * @param data
	 * @param axis (can be negative)
	 * @return product along axis in lazy dataset
	 */
	public static AbstractDataset product(final ILazyDataset data, int axis) {
		int rank = data.getRank();
		if (axis < 0)
			axis += rank;
		if (axis < 0 || axis >= rank) {
			logger.error("Axis argument is outside allowed range");
			throw new IllegalArgumentException("Axis argument is outside allowed range");
		}

		final int[] shape = data.getShape();

		final int[] nshape = shape.clone();
		nshape[axis] = 1;

		final AbstractDataset prod = AbstractDataset.ones(nshape, Dataset.FLOAT64);
		final int length = shape[axis];

		final int[] start = new int[shape.length];
		final int[] stop = shape.clone();
		final int[] step = new int[shape.length];
		Arrays.fill(step, 1);

		for (int i = 0; i < length; i++) {
			start[axis] = i;
			stop[axis] = i + 1;
			prod.imultiply(data.getSlice(start, stop, step));
		}

		prod.setShape(AbstractDataset.squeezeShape(shape, axis));
		return prod;
	}

}

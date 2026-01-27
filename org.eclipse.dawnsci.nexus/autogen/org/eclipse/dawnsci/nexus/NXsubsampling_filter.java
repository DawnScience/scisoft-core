/*-
 *******************************************************************************
 * Copyright (c) 2020 Diamond Light Source Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * This file was auto-generated from the NXDL XML definition.
 *******************************************************************************/

package org.eclipse.dawnsci.nexus;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Dataset;

/**
 * Base class of a filter to sample members in a set based on their indices.
 * The filter defines three parameters: The minimum, the increment, and the maximum
 * index of values to include of a sequence :math:`[i_0, i_0 + 1, i_0 + 2, \ldots, i_0 + \mathcal{N}] with i_0 \in \mathcal{Z}`
 * of indices. The increment controls which n-th index (value) to take.
 * Take as an example a dataset with 100 indices (aka entries). Assume that the indices start at zero,
 * i.e., index_offset is 0. Assume further that min, increment, max are set to 0, 1, and 99, respectively.
 * In this case the filter will yield all indices. Setting min, increment, max to 0, 2, and 99, respectively
 * will yield each second index value. Setting min, increment, max to 90, 3, and 99 respectively will yield
 * each third index value beginning from index values 90 up to 99.
 *
 */
public interface NXsubsampling_filter extends NXparameters {

	public static final String NX_MIN = "min";
	public static final String NX_INCREMENT = "increment";
	public static final String NX_MAX = "max";
	/**
	 * Minimum index.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getMin();

	/**
	 * Minimum index.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param minDataset the minDataset
	 */
	public DataNode setMin(IDataset minDataset);

	/**
	 * Minimum index.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getMinScalar();

	/**
	 * Minimum index.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param min the min
	 */
	public DataNode setMinScalar(Long minValue);

	/**
	 * Increment.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIncrement();

	/**
	 * Increment.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param incrementDataset the incrementDataset
	 */
	public DataNode setIncrement(IDataset incrementDataset);

	/**
	 * Increment.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getIncrementScalar();

	/**
	 * Increment.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param increment the increment
	 */
	public DataNode setIncrementScalar(Long incrementValue);

	/**
	 * Maximum index.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getMax();

	/**
	 * Maximum index.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param maxDataset the maxDataset
	 */
	public DataNode setMax(IDataset maxDataset);

	/**
	 * Maximum index.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getMaxScalar();

	/**
	 * Maximum index.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param max the max
	 */
	public DataNode setMaxScalar(Long maxValue);

}

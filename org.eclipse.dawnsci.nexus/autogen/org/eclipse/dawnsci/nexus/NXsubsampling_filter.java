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
 * Settings of a filter to sample entries based on their value.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul></ul></p>
 *
 */
public interface NXsubsampling_filter extends NXobject {

	public static final String NX_LINEAR_RANGE_MIN_INCR_MAX = "linear_range_min_incr_max";
	/**
	 * Triplet of the minimum, increment, and maximum value which will
	 * be included in the analysis. The increment controls which n-th entry to take.
	 * Take as an example a dataset with 100 entries (their indices start at zero)
	 * and the filter set to 0, 1, 99. This will process each entry.
	 * 0, 2, 99 will take each second entry. 90, 3, 99 will take only each third
	 * entry beginning from entry 90 up to 99.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getLinear_range_min_incr_max();

	/**
	 * Triplet of the minimum, increment, and maximum value which will
	 * be included in the analysis. The increment controls which n-th entry to take.
	 * Take as an example a dataset with 100 entries (their indices start at zero)
	 * and the filter set to 0, 1, 99. This will process each entry.
	 * 0, 2, 99 will take each second entry. 90, 3, 99 will take only each third
	 * entry beginning from entry 90 up to 99.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @param linear_range_min_incr_maxDataset the linear_range_min_incr_maxDataset
	 */
	public DataNode setLinear_range_min_incr_max(IDataset linear_range_min_incr_maxDataset);

	/**
	 * Triplet of the minimum, increment, and maximum value which will
	 * be included in the analysis. The increment controls which n-th entry to take.
	 * Take as an example a dataset with 100 entries (their indices start at zero)
	 * and the filter set to 0, 1, 99. This will process each entry.
	 * 0, 2, 99 will take each second entry. 90, 3, 99 will take only each third
	 * entry beginning from entry 90 up to 99.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getLinear_range_min_incr_maxScalar();

	/**
	 * Triplet of the minimum, increment, and maximum value which will
	 * be included in the analysis. The increment controls which n-th entry to take.
	 * Take as an example a dataset with 100 entries (their indices start at zero)
	 * and the filter set to 0, 1, 99. This will process each entry.
	 * 0, 2, 99 will take each second entry. 90, 3, 99 will take only each third
	 * entry beginning from entry 90 up to 99.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @param linear_range_min_incr_max the linear_range_min_incr_max
	 */
	public DataNode setLinear_range_min_incr_maxScalar(Long linear_range_min_incr_maxValue);

}

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

package org.eclipse.dawnsci.nexus.impl;

import java.util.Set;
import java.util.EnumSet;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Dataset;

import org.eclipse.dawnsci.nexus.*;

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

 */
public class NXsubsampling_filterImpl extends NXparametersImpl implements NXsubsampling_filter {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXsubsampling_filterImpl() {
		super();
	}

	public NXsubsampling_filterImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXsubsampling_filter.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_SUBSAMPLING_FILTER;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getMin() {
		return getDataset(NX_MIN);
	}

	@Override
	public Long getMinScalar() {
		return getLong(NX_MIN);
	}

	@Override
	public DataNode setMin(IDataset minDataset) {
		return setDataset(NX_MIN, minDataset);
	}

	@Override
	public DataNode setMinScalar(Long minValue) {
		return setField(NX_MIN, minValue);
	}

	@Override
	public Dataset getIncrement() {
		return getDataset(NX_INCREMENT);
	}

	@Override
	public Long getIncrementScalar() {
		return getLong(NX_INCREMENT);
	}

	@Override
	public DataNode setIncrement(IDataset incrementDataset) {
		return setDataset(NX_INCREMENT, incrementDataset);
	}

	@Override
	public DataNode setIncrementScalar(Long incrementValue) {
		return setField(NX_INCREMENT, incrementValue);
	}

	@Override
	public Dataset getMax() {
		return getDataset(NX_MAX);
	}

	@Override
	public Long getMaxScalar() {
		return getLong(NX_MAX);
	}

	@Override
	public DataNode setMax(IDataset maxDataset) {
		return setDataset(NX_MAX, maxDataset);
	}

	@Override
	public DataNode setMaxScalar(Long maxValue) {
		return setField(NX_MAX, maxValue);
	}

}

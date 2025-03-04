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
 * Settings of a filter to sample entries based on their value.

 */
public class NXsubsampling_filterImpl extends NXobjectImpl implements NXsubsampling_filter {

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
	public Dataset getLinear_range_min_incr_max() {
		return getDataset(NX_LINEAR_RANGE_MIN_INCR_MAX);
	}

	@Override
	public Long getLinear_range_min_incr_maxScalar() {
		return getLong(NX_LINEAR_RANGE_MIN_INCR_MAX);
	}

	@Override
	public DataNode setLinear_range_min_incr_max(IDataset linear_range_min_incr_maxDataset) {
		return setDataset(NX_LINEAR_RANGE_MIN_INCR_MAX, linear_range_min_incr_maxDataset);
	}

	@Override
	public DataNode setLinear_range_min_incr_maxScalar(Long linear_range_min_incr_maxValue) {
		return setField(NX_LINEAR_RANGE_MIN_INCR_MAX, linear_range_min_incr_maxValue);
	}

}

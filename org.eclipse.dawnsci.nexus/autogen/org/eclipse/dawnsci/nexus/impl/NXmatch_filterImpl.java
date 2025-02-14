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
 * Settings of a filter to select or remove entries based on their value.

 */
public class NXmatch_filterImpl extends NXobjectImpl implements NXmatch_filter {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXmatch_filterImpl() {
		super();
	}

	public NXmatch_filterImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXmatch_filter.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_MATCH_FILTER;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getMethod() {
		return getDataset(NX_METHOD);
	}

	@Override
	public String getMethodScalar() {
		return getString(NX_METHOD);
	}

	@Override
	public DataNode setMethod(IDataset methodDataset) {
		return setDataset(NX_METHOD, methodDataset);
	}

	@Override
	public DataNode setMethodScalar(String methodValue) {
		return setString(NX_METHOD, methodValue);
	}

	@Override
	public Dataset getMatch() {
		return getDataset(NX_MATCH);
	}

	@Override
	public Number getMatchScalar() {
		return getNumber(NX_MATCH);
	}

	@Override
	public DataNode setMatch(IDataset matchDataset) {
		return setDataset(NX_MATCH, matchDataset);
	}

	@Override
	public DataNode setMatchScalar(Number matchValue) {
		return setField(NX_MATCH, matchValue);
	}

}

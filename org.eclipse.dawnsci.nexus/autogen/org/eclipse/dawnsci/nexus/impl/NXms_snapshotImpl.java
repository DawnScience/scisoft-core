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
 * Base class for data on the state of the microstructure at a given
 * time/iteration.

 */
public class NXms_snapshotImpl extends NXobjectImpl implements NXms_snapshot {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXms_snapshotImpl() {
		super();
	}

	public NXms_snapshotImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXms_snapshot.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_MS_SNAPSHOT;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getComment() {
		return getDataset(NX_COMMENT);
	}

	@Override
	public String getCommentScalar() {
		return getString(NX_COMMENT);
	}

	@Override
	public DataNode setComment(IDataset commentDataset) {
		return setDataset(NX_COMMENT, commentDataset);
	}

	@Override
	public DataNode setCommentScalar(String commentValue) {
		return setString(NX_COMMENT, commentValue);
	}

	@Override
	public Dataset getTime() {
		return getDataset(NX_TIME);
	}

	@Override
	public Number getTimeScalar() {
		return getNumber(NX_TIME);
	}

	@Override
	public DataNode setTime(IDataset timeDataset) {
		return setDataset(NX_TIME, timeDataset);
	}

	@Override
	public DataNode setTimeScalar(Number timeValue) {
		return setField(NX_TIME, timeValue);
	}

	@Override
	public Dataset getIteration() {
		return getDataset(NX_ITERATION);
	}

	@Override
	public Long getIterationScalar() {
		return getLong(NX_ITERATION);
	}

	@Override
	public DataNode setIteration(IDataset iterationDataset) {
		return setDataset(NX_ITERATION, iterationDataset);
	}

	@Override
	public DataNode setIterationScalar(Long iterationValue) {
		return setField(NX_ITERATION, iterationValue);
	}

}

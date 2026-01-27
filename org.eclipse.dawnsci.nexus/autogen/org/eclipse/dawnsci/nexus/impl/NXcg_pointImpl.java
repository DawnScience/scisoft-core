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

import java.util.Date;
import java.util.Set;
import java.util.EnumSet;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Dataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * Computational geometry description of a set of points.
 * Points may have an associated time value. Users are advised though to store
 * time data of point sets rather as instances of time events, where for each
 * point in time there is an :ref:`NXcg_point` instance which specifies the
 * points' locations.
 * This is a frequent situation in experiments and computer simulations, where
 * positions of points are taken at the same point in time (real time or
 * simulated physical time). Thereby, the storage of redundant timestamp
 * information per point is considered as obsolete.

 */
public class NXcg_pointImpl extends NXcg_primitiveImpl implements NXcg_point {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXcg_pointImpl() {
		super();
	}

	public NXcg_pointImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcg_point.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CG_POINT;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getPosition() {
		return getDataset(NX_POSITION);
	}

	@Override
	public Number getPositionScalar() {
		return getNumber(NX_POSITION);
	}

	@Override
	public DataNode setPosition(IDataset positionDataset) {
		return setDataset(NX_POSITION, positionDataset);
	}

	@Override
	public DataNode setPositionScalar(Number positionValue) {
		return setField(NX_POSITION, positionValue);
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
	public Dataset getTimestamp() {
		return getDataset(NX_TIMESTAMP);
	}

	@Override
	public Date getTimestampScalar() {
		return getDate(NX_TIMESTAMP);
	}

	@Override
	public DataNode setTimestamp(IDataset timestampDataset) {
		return setDataset(NX_TIMESTAMP, timestampDataset);
	}

	@Override
	public DataNode setTimestampScalar(Date timestampValue) {
		return setDate(NX_TIMESTAMP, timestampValue);
	}

	@Override
	public Dataset getTime_offset() {
		return getDataset(NX_TIME_OFFSET);
	}

	@Override
	public Date getTime_offsetScalar() {
		return getDate(NX_TIME_OFFSET);
	}

	@Override
	public DataNode setTime_offset(IDataset time_offsetDataset) {
		return setDataset(NX_TIME_OFFSET, time_offsetDataset);
	}

	@Override
	public DataNode setTime_offsetScalar(Date time_offsetValue) {
		return setDate(NX_TIME_OFFSET, time_offsetValue);
	}

}

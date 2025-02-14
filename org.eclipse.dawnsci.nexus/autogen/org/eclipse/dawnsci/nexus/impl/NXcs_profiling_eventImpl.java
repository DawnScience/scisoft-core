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
 * Computer science description of a profiling event.

 */
public class NXcs_profiling_eventImpl extends NXobjectImpl implements NXcs_profiling_event {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXcs_profiling_eventImpl() {
		super();
	}

	public NXcs_profiling_eventImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcs_profiling_event.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CS_PROFILING_EVENT;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getStart_time() {
		return getDataset(NX_START_TIME);
	}

	@Override
	public Date getStart_timeScalar() {
		return getDate(NX_START_TIME);
	}

	@Override
	public DataNode setStart_time(IDataset start_timeDataset) {
		return setDataset(NX_START_TIME, start_timeDataset);
	}

	@Override
	public DataNode setStart_timeScalar(Date start_timeValue) {
		return setDate(NX_START_TIME, start_timeValue);
	}

	@Override
	public Dataset getEnd_time() {
		return getDataset(NX_END_TIME);
	}

	@Override
	public Date getEnd_timeScalar() {
		return getDate(NX_END_TIME);
	}

	@Override
	public DataNode setEnd_time(IDataset end_timeDataset) {
		return setDataset(NX_END_TIME, end_timeDataset);
	}

	@Override
	public DataNode setEnd_timeScalar(Date end_timeValue) {
		return setDate(NX_END_TIME, end_timeValue);
	}

	@Override
	public Dataset getDescription() {
		return getDataset(NX_DESCRIPTION);
	}

	@Override
	public String getDescriptionScalar() {
		return getString(NX_DESCRIPTION);
	}

	@Override
	public DataNode setDescription(IDataset descriptionDataset) {
		return setDataset(NX_DESCRIPTION, descriptionDataset);
	}

	@Override
	public DataNode setDescriptionScalar(String descriptionValue) {
		return setString(NX_DESCRIPTION, descriptionValue);
	}

	@Override
	public Dataset getElapsed_time() {
		return getDataset(NX_ELAPSED_TIME);
	}

	@Override
	public Number getElapsed_timeScalar() {
		return getNumber(NX_ELAPSED_TIME);
	}

	@Override
	public DataNode setElapsed_time(IDataset elapsed_timeDataset) {
		return setDataset(NX_ELAPSED_TIME, elapsed_timeDataset);
	}

	@Override
	public DataNode setElapsed_timeScalar(Number elapsed_timeValue) {
		return setField(NX_ELAPSED_TIME, elapsed_timeValue);
	}

	@Override
	public Dataset getNumber_of_processes() {
		return getDataset(NX_NUMBER_OF_PROCESSES);
	}

	@Override
	public Long getNumber_of_processesScalar() {
		return getLong(NX_NUMBER_OF_PROCESSES);
	}

	@Override
	public DataNode setNumber_of_processes(IDataset number_of_processesDataset) {
		return setDataset(NX_NUMBER_OF_PROCESSES, number_of_processesDataset);
	}

	@Override
	public DataNode setNumber_of_processesScalar(Long number_of_processesValue) {
		return setField(NX_NUMBER_OF_PROCESSES, number_of_processesValue);
	}

	@Override
	public Dataset getNumber_of_threads() {
		return getDataset(NX_NUMBER_OF_THREADS);
	}

	@Override
	public Long getNumber_of_threadsScalar() {
		return getLong(NX_NUMBER_OF_THREADS);
	}

	@Override
	public DataNode setNumber_of_threads(IDataset number_of_threadsDataset) {
		return setDataset(NX_NUMBER_OF_THREADS, number_of_threadsDataset);
	}

	@Override
	public DataNode setNumber_of_threadsScalar(Long number_of_threadsValue) {
		return setField(NX_NUMBER_OF_THREADS, number_of_threadsValue);
	}

	@Override
	public Dataset getNumber_of_gpus() {
		return getDataset(NX_NUMBER_OF_GPUS);
	}

	@Override
	public Long getNumber_of_gpusScalar() {
		return getLong(NX_NUMBER_OF_GPUS);
	}

	@Override
	public DataNode setNumber_of_gpus(IDataset number_of_gpusDataset) {
		return setDataset(NX_NUMBER_OF_GPUS, number_of_gpusDataset);
	}

	@Override
	public DataNode setNumber_of_gpusScalar(Long number_of_gpusValue) {
		return setField(NX_NUMBER_OF_GPUS, number_of_gpusValue);
	}

	@Override
	public Dataset getMax_virtual_memory_snapshot() {
		return getDataset(NX_MAX_VIRTUAL_MEMORY_SNAPSHOT);
	}

	@Override
	public Number getMax_virtual_memory_snapshotScalar() {
		return getNumber(NX_MAX_VIRTUAL_MEMORY_SNAPSHOT);
	}

	@Override
	public DataNode setMax_virtual_memory_snapshot(IDataset max_virtual_memory_snapshotDataset) {
		return setDataset(NX_MAX_VIRTUAL_MEMORY_SNAPSHOT, max_virtual_memory_snapshotDataset);
	}

	@Override
	public DataNode setMax_virtual_memory_snapshotScalar(Number max_virtual_memory_snapshotValue) {
		return setField(NX_MAX_VIRTUAL_MEMORY_SNAPSHOT, max_virtual_memory_snapshotValue);
	}

	@Override
	public Dataset getMax_resident_memory_snapshot() {
		return getDataset(NX_MAX_RESIDENT_MEMORY_SNAPSHOT);
	}

	@Override
	public Number getMax_resident_memory_snapshotScalar() {
		return getNumber(NX_MAX_RESIDENT_MEMORY_SNAPSHOT);
	}

	@Override
	public DataNode setMax_resident_memory_snapshot(IDataset max_resident_memory_snapshotDataset) {
		return setDataset(NX_MAX_RESIDENT_MEMORY_SNAPSHOT, max_resident_memory_snapshotDataset);
	}

	@Override
	public DataNode setMax_resident_memory_snapshotScalar(Number max_resident_memory_snapshotValue) {
		return setField(NX_MAX_RESIDENT_MEMORY_SNAPSHOT, max_resident_memory_snapshotValue);
	}

}

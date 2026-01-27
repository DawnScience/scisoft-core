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
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Dataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * Computer science description for performance and profiling data of an application.
 * Performance monitoring and benchmarking of software is a task where questions
 * can be asked at various levels of detail. In general, there are three main
 * contributions to performance:
 * * Hardware capabilities and configuration
 * * Software configuration and capabilities
 * * Dynamic effects of the system in operation and the system working together
 * with eventually multiple computers, especially when these have to exchange
 * information across a network and these are used usually by multiple users.
 * At the most basic level users may wish to document how long e.g. a data
 * analysis with a scientific software, i.e. an app took.
 * A frequent idea is here to answer practical questions like how critical is the
 * effect on the workflow of the scientists, i.e. is the analysis possible in
 * a few seconds or would it take days if I were to run this analysis on a
 * comparable machine?
 * For this more qualitative performance monitoring, mainly the order of
 * magnitude is relevant, as well as how this was achieved using parallelization
 * (i.e. reporting the number of CPU and GPU resources used, the number of
 * processes and threads configured, and providing basic details about the computer).
 * At more advanced levels benchmarks may go as deep as detailed temporal tracking
 * of individual processor instructions, their relation to other instructions, the
 * state of call stacks; in short eventually the entire app execution history
 * and hardware state history. Such analyses are mainly used for performance
 * optimization, i.e. by software and hardware developers as well as for
 * tracking bugs. Specialized software exists which documents such performance
 * data in specifically-formatted event log files or databases.
 * This base class cannot and should not replace these specific solutions for now.
 * Instead, the intention of the base class is to serve scientists at the
 * basic level to enable simple monitoring of performance data and log profiling
 * data of key algorithmic steps or parts of computational workflows, so that
 * these pieces of information can guide users which order of magnitude differences
 * should be expected or not.
 * Developers of application definitions should add additional fields and
 * references to e.g. more detailed performance data to which they wish to link
 * the metadata in this base class.

 */
public class NXcs_profilingImpl extends NXobjectImpl implements NXcs_profiling {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_CS_COMPUTER,
		NexusBaseClass.NX_CS_PROFILING_EVENT);

	public NXcs_profilingImpl() {
		super();
	}

	public NXcs_profilingImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcs_profiling.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CS_PROFILING;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getCurrent_working_directory() {
		return getDataset(NX_CURRENT_WORKING_DIRECTORY);
	}

	@Override
	public String getCurrent_working_directoryScalar() {
		return getString(NX_CURRENT_WORKING_DIRECTORY);
	}

	@Override
	public DataNode setCurrent_working_directory(IDataset current_working_directoryDataset) {
		return setDataset(NX_CURRENT_WORKING_DIRECTORY, current_working_directoryDataset);
	}

	@Override
	public DataNode setCurrent_working_directoryScalar(String current_working_directoryValue) {
		return setString(NX_CURRENT_WORKING_DIRECTORY, current_working_directoryValue);
	}

	@Override
	public Dataset getCommand_line_call() {
		return getDataset(NX_COMMAND_LINE_CALL);
	}

	@Override
	public String getCommand_line_callScalar() {
		return getString(NX_COMMAND_LINE_CALL);
	}

	@Override
	public DataNode setCommand_line_call(IDataset command_line_callDataset) {
		return setDataset(NX_COMMAND_LINE_CALL, command_line_callDataset);
	}

	@Override
	public DataNode setCommand_line_callScalar(String command_line_callValue) {
		return setString(NX_COMMAND_LINE_CALL, command_line_callValue);
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
	public Dataset getTotal_elapsed_time() {
		return getDataset(NX_TOTAL_ELAPSED_TIME);
	}

	@Override
	public Number getTotal_elapsed_timeScalar() {
		return getNumber(NX_TOTAL_ELAPSED_TIME);
	}

	@Override
	public DataNode setTotal_elapsed_time(IDataset total_elapsed_timeDataset) {
		return setDataset(NX_TOTAL_ELAPSED_TIME, total_elapsed_timeDataset);
	}

	@Override
	public DataNode setTotal_elapsed_timeScalar(Number total_elapsed_timeValue) {
		return setField(NX_TOTAL_ELAPSED_TIME, total_elapsed_timeValue);
	}

	@Override
	public Dataset getMax_processes() {
		return getDataset(NX_MAX_PROCESSES);
	}

	@Override
	public Long getMax_processesScalar() {
		return getLong(NX_MAX_PROCESSES);
	}

	@Override
	public DataNode setMax_processes(IDataset max_processesDataset) {
		return setDataset(NX_MAX_PROCESSES, max_processesDataset);
	}

	@Override
	public DataNode setMax_processesScalar(Long max_processesValue) {
		return setField(NX_MAX_PROCESSES, max_processesValue);
	}

	@Override
	public Dataset getMax_threads() {
		return getDataset(NX_MAX_THREADS);
	}

	@Override
	public Long getMax_threadsScalar() {
		return getLong(NX_MAX_THREADS);
	}

	@Override
	public DataNode setMax_threads(IDataset max_threadsDataset) {
		return setDataset(NX_MAX_THREADS, max_threadsDataset);
	}

	@Override
	public DataNode setMax_threadsScalar(Long max_threadsValue) {
		return setField(NX_MAX_THREADS, max_threadsValue);
	}

	@Override
	public Dataset getMax_gpus() {
		return getDataset(NX_MAX_GPUS);
	}

	@Override
	public Long getMax_gpusScalar() {
		return getLong(NX_MAX_GPUS);
	}

	@Override
	public DataNode setMax_gpus(IDataset max_gpusDataset) {
		return setDataset(NX_MAX_GPUS, max_gpusDataset);
	}

	@Override
	public DataNode setMax_gpusScalar(Long max_gpusValue) {
		return setField(NX_MAX_GPUS, max_gpusValue);
	}

	@Override
	public NXcs_computer getCs_computer() {
		// dataNodeName = NX_CS_COMPUTER
		return getChild("cs_computer", NXcs_computer.class);
	}

	@Override
	public void setCs_computer(NXcs_computer cs_computerGroup) {
		putChild("cs_computer", cs_computerGroup);
	}

	@Override
	public NXcs_computer getCs_computer(String name) {
		return getChild(name, NXcs_computer.class);
	}

	@Override
	public void setCs_computer(String name, NXcs_computer cs_computer) {
		putChild(name, cs_computer);
	}

	@Override
	public Map<String, NXcs_computer> getAllCs_computer() {
		return getChildren(NXcs_computer.class);
	}

	@Override
	public void setAllCs_computer(Map<String, NXcs_computer> cs_computer) {
		setChildren(cs_computer);
	}

	@Override
	public NXcs_profiling_event getEventid() {
		// dataNodeName = NX_EVENTID
		return getChild("eventid", NXcs_profiling_event.class);
	}

	@Override
	public void setEventid(NXcs_profiling_event eventidGroup) {
		putChild("eventid", eventidGroup);
	}

}

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

import java.util.Date;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Dataset;

/**
 * Computer science description of a profiling event.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>n_processes</b>
 * Number of processes.</li></ul></p>
 *
 */
public interface NXcs_profiling_event extends NXobject {

	public static final String NX_START_TIME = "start_time";
	public static final String NX_END_TIME = "end_time";
	public static final String NX_DESCRIPTION = "description";
	public static final String NX_ELAPSED_TIME = "elapsed_time";
	public static final String NX_MAX_PROCESSES = "max_processes";
	public static final String NX_MAX_THREADS = "max_threads";
	public static final String NX_MAX_GPUS = "max_gpus";
	public static final String NX_MAX_VIRTUAL_MEMORY_SNAPSHOT = "max_virtual_memory_snapshot";
	public static final String NX_MAX_RESIDENT_MEMORY_SNAPSHOT = "max_resident_memory_snapshot";
	/**
	 * ISO 8601 time code with local time zone offset to UTC information
	 * included when the event tracking started.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getStart_time();

	/**
	 * ISO 8601 time code with local time zone offset to UTC information
	 * included when the event tracking started.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @param start_timeDataset the start_timeDataset
	 */
	public DataNode setStart_time(IDataset start_timeDataset);

	/**
	 * ISO 8601 time code with local time zone offset to UTC information
	 * included when the event tracking started.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Date getStart_timeScalar();

	/**
	 * ISO 8601 time code with local time zone offset to UTC information
	 * included when the event tracking started.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @param start_time the start_time
	 */
	public DataNode setStart_timeScalar(Date start_timeValue);

	/**
	 * ISO 8601 time code with local time zone offset to UTC information
	 * included when the event tracking ended.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getEnd_time();

	/**
	 * ISO 8601 time code with local time zone offset to UTC information
	 * included when the event tracking ended.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @param end_timeDataset the end_timeDataset
	 */
	public DataNode setEnd_time(IDataset end_timeDataset);

	/**
	 * ISO 8601 time code with local time zone offset to UTC information
	 * included when the event tracking ended.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Date getEnd_timeScalar();

	/**
	 * ISO 8601 time code with local time zone offset to UTC information
	 * included when the event tracking ended.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @param end_time the end_time
	 */
	public DataNode setEnd_timeScalar(Date end_timeValue);

	/**
	 * Free-text description what was monitored/executed during the event.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDescription();

	/**
	 * Free-text description what was monitored/executed during the event.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param descriptionDataset the descriptionDataset
	 */
	public DataNode setDescription(IDataset descriptionDataset);

	/**
	 * Free-text description what was monitored/executed during the event.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getDescriptionScalar();

	/**
	 * Free-text description what was monitored/executed during the event.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param description the description
	 */
	public DataNode setDescriptionScalar(String descriptionValue);

	/**
	 * Wall-clock time how long the event took.
	 * This may be in principle end_time minus start_time; however usage of
	 * eventually more precise timers may warrant to use a finer temporal
	 * discretization, and thus demand for a more precise record of the
	 * wall-clock time.
	 * Elapsed time may contain time portions where resources were idling.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getElapsed_time();

	/**
	 * Wall-clock time how long the event took.
	 * This may be in principle end_time minus start_time; however usage of
	 * eventually more precise timers may warrant to use a finer temporal
	 * discretization, and thus demand for a more precise record of the
	 * wall-clock time.
	 * Elapsed time may contain time portions where resources were idling.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @param elapsed_timeDataset the elapsed_timeDataset
	 */
	public DataNode setElapsed_time(IDataset elapsed_timeDataset);

	/**
	 * Wall-clock time how long the event took.
	 * This may be in principle end_time minus start_time; however usage of
	 * eventually more precise timers may warrant to use a finer temporal
	 * discretization, and thus demand for a more precise record of the
	 * wall-clock time.
	 * Elapsed time may contain time portions where resources were idling.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getElapsed_timeScalar();

	/**
	 * Wall-clock time how long the event took.
	 * This may be in principle end_time minus start_time; however usage of
	 * eventually more precise timers may warrant to use a finer temporal
	 * discretization, and thus demand for a more precise record of the
	 * wall-clock time.
	 * Elapsed time may contain time portions where resources were idling.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @param elapsed_time the elapsed_time
	 */
	public DataNode setElapsed_timeScalar(Number elapsed_timeValue);

	/**
	 * The number of nominal processes that the app invoked during the execution of this event.
	 * The main idea behind this field e.g. for apps which use e.g. MPI
	 * (Message Passing Interface) parallelization is to communicate
	 * how many processes were used.
	 * For sequentially running apps number_of_processes and number_of_threads
	 * is one. If the app exclusively uses GPU parallelization, number_of_gpus
	 * can be larger than one. If no GPU is used, number_of_gpus is zero,
	 * even though the hardware may have GPUs installed.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getMax_processes();

	/**
	 * The number of nominal processes that the app invoked during the execution of this event.
	 * The main idea behind this field e.g. for apps which use e.g. MPI
	 * (Message Passing Interface) parallelization is to communicate
	 * how many processes were used.
	 * For sequentially running apps number_of_processes and number_of_threads
	 * is one. If the app exclusively uses GPU parallelization, number_of_gpus
	 * can be larger than one. If no GPU is used, number_of_gpus is zero,
	 * even though the hardware may have GPUs installed.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param max_processesDataset the max_processesDataset
	 */
	public DataNode setMax_processes(IDataset max_processesDataset);

	/**
	 * The number of nominal processes that the app invoked during the execution of this event.
	 * The main idea behind this field e.g. for apps which use e.g. MPI
	 * (Message Passing Interface) parallelization is to communicate
	 * how many processes were used.
	 * For sequentially running apps number_of_processes and number_of_threads
	 * is one. If the app exclusively uses GPU parallelization, number_of_gpus
	 * can be larger than one. If no GPU is used, number_of_gpus is zero,
	 * even though the hardware may have GPUs installed.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getMax_processesScalar();

	/**
	 * The number of nominal processes that the app invoked during the execution of this event.
	 * The main idea behind this field e.g. for apps which use e.g. MPI
	 * (Message Passing Interface) parallelization is to communicate
	 * how many processes were used.
	 * For sequentially running apps number_of_processes and number_of_threads
	 * is one. If the app exclusively uses GPU parallelization, number_of_gpus
	 * can be larger than one. If no GPU is used, number_of_gpus is zero,
	 * even though the hardware may have GPUs installed.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param max_processes the max_processes
	 */
	public DataNode setMax_processesScalar(Long max_processesValue);

	/**
	 * The number of nominal threads that the app invoked at during the execution of this event.
	 * Specifically here the maximum number of threads used for the
	 * high-level threading library used (e.g. OMP_NUM_THREADS), posix.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getMax_threads();

	/**
	 * The number of nominal threads that the app invoked at during the execution of this event.
	 * Specifically here the maximum number of threads used for the
	 * high-level threading library used (e.g. OMP_NUM_THREADS), posix.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param max_threadsDataset the max_threadsDataset
	 */
	public DataNode setMax_threads(IDataset max_threadsDataset);

	/**
	 * The number of nominal threads that the app invoked at during the execution of this event.
	 * Specifically here the maximum number of threads used for the
	 * high-level threading library used (e.g. OMP_NUM_THREADS), posix.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getMax_threadsScalar();

	/**
	 * The number of nominal threads that the app invoked at during the execution of this event.
	 * Specifically here the maximum number of threads used for the
	 * high-level threading library used (e.g. OMP_NUM_THREADS), posix.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param max_threads the max_threads
	 */
	public DataNode setMax_threadsScalar(Long max_threadsValue);

	/**
	 * The number of nominal GPUs that the app invoked during the execution of this
	 * event.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getMax_gpus();

	/**
	 * The number of nominal GPUs that the app invoked during the execution of this
	 * event.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param max_gpusDataset the max_gpusDataset
	 */
	public DataNode setMax_gpus(IDataset max_gpusDataset);

	/**
	 * The number of nominal GPUs that the app invoked during the execution of this
	 * event.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getMax_gpusScalar();

	/**
	 * The number of nominal GPUs that the app invoked during the execution of this
	 * event.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param max_gpus the max_gpus
	 */
	public DataNode setMax_gpusScalar(Long max_gpusValue);

	/**
	 * Maximum amount of virtual memory allocated per process during the event.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n_processes;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getMax_virtual_memory_snapshot();

	/**
	 * Maximum amount of virtual memory allocated per process during the event.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n_processes;
	 * </p>
	 *
	 * @param max_virtual_memory_snapshotDataset the max_virtual_memory_snapshotDataset
	 */
	public DataNode setMax_virtual_memory_snapshot(IDataset max_virtual_memory_snapshotDataset);

	/**
	 * Maximum amount of virtual memory allocated per process during the event.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n_processes;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getMax_virtual_memory_snapshotScalar();

	/**
	 * Maximum amount of virtual memory allocated per process during the event.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n_processes;
	 * </p>
	 *
	 * @param max_virtual_memory_snapshot the max_virtual_memory_snapshot
	 */
	public DataNode setMax_virtual_memory_snapshotScalar(Number max_virtual_memory_snapshotValue);

	/**
	 * Maximum amount of resident memory allocated per process during the event.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n_processes;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getMax_resident_memory_snapshot();

	/**
	 * Maximum amount of resident memory allocated per process during the event.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n_processes;
	 * </p>
	 *
	 * @param max_resident_memory_snapshotDataset the max_resident_memory_snapshotDataset
	 */
	public DataNode setMax_resident_memory_snapshot(IDataset max_resident_memory_snapshotDataset);

	/**
	 * Maximum amount of resident memory allocated per process during the event.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n_processes;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getMax_resident_memory_snapshotScalar();

	/**
	 * Maximum amount of resident memory allocated per process during the event.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n_processes;
	 * </p>
	 *
	 * @param max_resident_memory_snapshot the max_resident_memory_snapshot
	 */
	public DataNode setMax_resident_memory_snapshotScalar(Number max_resident_memory_snapshotValue);

}

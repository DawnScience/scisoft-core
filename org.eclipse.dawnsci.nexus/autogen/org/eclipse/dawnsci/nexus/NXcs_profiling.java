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
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;

/**
 * Computer science description for summary performance/profiling data of an application.
 * Performance monitoring and benchmarking of software is a task where questions
 * can be asked at various levels of detail. In general, there are three main
 * contributions to performance:
 * * Hardware capabilities and configuration
 * * Software configuration and capabilities
 * * Dynamic effects of the system in operation and the system working together
 * with eventually multiple computers, especially when these have to exchange
 * information across a network.
 * At the most basic level users may wish to document how long e.g. a data
 * analysis with a scientific software (app).
 * A frequent idea is here to judge how critical the effect is on the workflow
 * of the scientists, i.e. is the analysis possible in a few seconds or would it
 * take days if I were to run this analysis on a comparable machine. In this case,
 * mainly the order of magnitude is relevant, as well as how this can be achieved
 * with using parallelization (i.e. reporting the number of CPU and GPU resources
 * used, the number of processes and/or threads, and basic details about the
 * computing node/computer.
 * At more advanced levels benchmarks may go as deep as detailed temporal tracking
 * of individual processor instructions, their relation to other instructions, the
 * state of call stacks, in short eventually the entire app execution history
 * and hardware state history. Such analyses are mainly used for performance
 * optimization as well as for tracking bugs and other development purposes.
 * Specialized software exists which documents such performance data in
 * specifically-formatted event log files or databases.
 * This base class cannot and should not replace these specific solutions.
 * Instead, the intention of the base class is to serve scientists at the
 * basic level to enable simple monitoring of performance data and log profiling
 * data of key algorithmic steps or parts of computational workflows, so that
 * these pieces of information can guide users which order of magnitude differences
 * should be expected or not.
 * Developers of application definitions should add additional fields and
 * references to e.g. more detailed performance data to which they wish to link
 * the metadata in this base class.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul></ul></p>
 *
 */
public interface NXcs_profiling extends NXobject {

	public static final String NX_CURRENT_WORKING_DIRECTORY = "current_working_directory";
	public static final String NX_COMMAND_LINE_CALL = "command_line_call";
	public static final String NX_START_TIME = "start_time";
	public static final String NX_END_TIME = "end_time";
	public static final String NX_TOTAL_ELAPSED_TIME = "total_elapsed_time";
	public static final String NX_NUMBER_OF_PROCESSES = "number_of_processes";
	public static final String NX_NUMBER_OF_THREADS = "number_of_threads";
	public static final String NX_NUMBER_OF_GPUS = "number_of_gpus";
	/**
	 * Path to the directory from which the tool was called.
	 *
	 * @return  the value.
	 */
	public IDataset getCurrent_working_directory();

	/**
	 * Path to the directory from which the tool was called.
	 *
	 * @param current_working_directoryDataset the current_working_directoryDataset
	 */
	public DataNode setCurrent_working_directory(IDataset current_working_directoryDataset);

	/**
	 * Path to the directory from which the tool was called.
	 *
	 * @return  the value.
	 */
	public String getCurrent_working_directoryScalar();

	/**
	 * Path to the directory from which the tool was called.
	 *
	 * @param current_working_directory the current_working_directory
	 */
	public DataNode setCurrent_working_directoryScalar(String current_working_directoryValue);

	/**
	 * Command line call with arguments if applicable.
	 *
	 * @return  the value.
	 */
	public IDataset getCommand_line_call();

	/**
	 * Command line call with arguments if applicable.
	 *
	 * @param command_line_callDataset the command_line_callDataset
	 */
	public DataNode setCommand_line_call(IDataset command_line_callDataset);

	/**
	 * Command line call with arguments if applicable.
	 *
	 * @return  the value.
	 */
	public String getCommand_line_callScalar();

	/**
	 * Command line call with arguments if applicable.
	 *
	 * @param command_line_call the command_line_call
	 */
	public DataNode setCommand_line_callScalar(String command_line_callValue);

	/**
	 * ISO 8601 time code with local time zone offset to UTC information
	 * included when the app was started.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getStart_time();

	/**
	 * ISO 8601 time code with local time zone offset to UTC information
	 * included when the app was started.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @param start_timeDataset the start_timeDataset
	 */
	public DataNode setStart_time(IDataset start_timeDataset);

	/**
	 * ISO 8601 time code with local time zone offset to UTC information
	 * included when the app was started.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Date getStart_timeScalar();

	/**
	 * ISO 8601 time code with local time zone offset to UTC information
	 * included when the app was started.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @param start_time the start_time
	 */
	public DataNode setStart_timeScalar(Date start_timeValue);

	/**
	 * ISO 8601 time code with local time zone offset to UTC information
	 * included when the app terminated or crashed.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getEnd_time();

	/**
	 * ISO 8601 time code with local time zone offset to UTC information
	 * included when the app terminated or crashed.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @param end_timeDataset the end_timeDataset
	 */
	public DataNode setEnd_time(IDataset end_timeDataset);

	/**
	 * ISO 8601 time code with local time zone offset to UTC information
	 * included when the app terminated or crashed.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Date getEnd_timeScalar();

	/**
	 * ISO 8601 time code with local time zone offset to UTC information
	 * included when the app terminated or crashed.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @param end_time the end_time
	 */
	public DataNode setEnd_timeScalar(Date end_timeValue);

	/**
	 * Wall-clock time how long the app execution took. This may be in principle
	 * end_time minus start_time; however usage of eventually more precise timers
	 * may warrant to use a finer temporal discretization,
	 * and thus demand a more precise record of the wall-clock time.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getTotal_elapsed_time();

	/**
	 * Wall-clock time how long the app execution took. This may be in principle
	 * end_time minus start_time; however usage of eventually more precise timers
	 * may warrant to use a finer temporal discretization,
	 * and thus demand a more precise record of the wall-clock time.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @param total_elapsed_timeDataset the total_elapsed_timeDataset
	 */
	public DataNode setTotal_elapsed_time(IDataset total_elapsed_timeDataset);

	/**
	 * Wall-clock time how long the app execution took. This may be in principle
	 * end_time minus start_time; however usage of eventually more precise timers
	 * may warrant to use a finer temporal discretization,
	 * and thus demand a more precise record of the wall-clock time.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getTotal_elapsed_timeScalar();

	/**
	 * Wall-clock time how long the app execution took. This may be in principle
	 * end_time minus start_time; however usage of eventually more precise timers
	 * may warrant to use a finer temporal discretization,
	 * and thus demand a more precise record of the wall-clock time.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @param total_elapsed_time the total_elapsed_time
	 */
	public DataNode setTotal_elapsed_timeScalar(Number total_elapsed_timeValue);

	/**
	 * Qualifier which specifies with how many nominal processes the app was
	 * invoked. The main idea behind this field, for instance for app using a
	 * Message Passing Interface parallelization is to communicate how many
	 * processes were used.
	 * For sequentially running apps number_of_processes and number_of_threads
	 * is 1. If the app uses exclusively GPU parallelization number_of_gpus
	 * can be larger than 1. If no GPU is used number_of_gpus is 0 even though
	 * the hardware may have GPUs installed, thus indicating these were not
	 * used though.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getNumber_of_processes();

	/**
	 * Qualifier which specifies with how many nominal processes the app was
	 * invoked. The main idea behind this field, for instance for app using a
	 * Message Passing Interface parallelization is to communicate how many
	 * processes were used.
	 * For sequentially running apps number_of_processes and number_of_threads
	 * is 1. If the app uses exclusively GPU parallelization number_of_gpus
	 * can be larger than 1. If no GPU is used number_of_gpus is 0 even though
	 * the hardware may have GPUs installed, thus indicating these were not
	 * used though.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_processesDataset the number_of_processesDataset
	 */
	public DataNode setNumber_of_processes(IDataset number_of_processesDataset);

	/**
	 * Qualifier which specifies with how many nominal processes the app was
	 * invoked. The main idea behind this field, for instance for app using a
	 * Message Passing Interface parallelization is to communicate how many
	 * processes were used.
	 * For sequentially running apps number_of_processes and number_of_threads
	 * is 1. If the app uses exclusively GPU parallelization number_of_gpus
	 * can be larger than 1. If no GPU is used number_of_gpus is 0 even though
	 * the hardware may have GPUs installed, thus indicating these were not
	 * used though.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getNumber_of_processesScalar();

	/**
	 * Qualifier which specifies with how many nominal processes the app was
	 * invoked. The main idea behind this field, for instance for app using a
	 * Message Passing Interface parallelization is to communicate how many
	 * processes were used.
	 * For sequentially running apps number_of_processes and number_of_threads
	 * is 1. If the app uses exclusively GPU parallelization number_of_gpus
	 * can be larger than 1. If no GPU is used number_of_gpus is 0 even though
	 * the hardware may have GPUs installed, thus indicating these were not
	 * used though.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_processes the number_of_processes
	 */
	public DataNode setNumber_of_processesScalar(Long number_of_processesValue);

	/**
	 * Qualifier with how many nominal threads were accessible to the app at
	 * runtime. Specifically here the maximum number of threads used for the
	 * high-level threading library used (e.g. OMP_NUM_THREADS), posix.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getNumber_of_threads();

	/**
	 * Qualifier with how many nominal threads were accessible to the app at
	 * runtime. Specifically here the maximum number of threads used for the
	 * high-level threading library used (e.g. OMP_NUM_THREADS), posix.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_threadsDataset the number_of_threadsDataset
	 */
	public DataNode setNumber_of_threads(IDataset number_of_threadsDataset);

	/**
	 * Qualifier with how many nominal threads were accessible to the app at
	 * runtime. Specifically here the maximum number of threads used for the
	 * high-level threading library used (e.g. OMP_NUM_THREADS), posix.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getNumber_of_threadsScalar();

	/**
	 * Qualifier with how many nominal threads were accessible to the app at
	 * runtime. Specifically here the maximum number of threads used for the
	 * high-level threading library used (e.g. OMP_NUM_THREADS), posix.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_threads the number_of_threads
	 */
	public DataNode setNumber_of_threadsScalar(Long number_of_threadsValue);

	/**
	 * Qualifier with how many nominal GPUs the app was invoked at runtime.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getNumber_of_gpus();

	/**
	 * Qualifier with how many nominal GPUs the app was invoked at runtime.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_gpusDataset the number_of_gpusDataset
	 */
	public DataNode setNumber_of_gpus(IDataset number_of_gpusDataset);

	/**
	 * Qualifier with how many nominal GPUs the app was invoked at runtime.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getNumber_of_gpusScalar();

	/**
	 * Qualifier with how many nominal GPUs the app was invoked at runtime.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_gpus the number_of_gpus
	 */
	public DataNode setNumber_of_gpusScalar(Long number_of_gpusValue);

	/**
	 * A collection with one or more computing nodes each with own resources.
	 * This can be as simple as a laptop or the nodes of a cluster computer.
	 *
	 * @return  the value.
	 */
	public NXcs_computer getCs_computer();

	/**
	 * A collection with one or more computing nodes each with own resources.
	 * This can be as simple as a laptop or the nodes of a cluster computer.
	 *
	 * @param cs_computerGroup the cs_computerGroup
	 */
	public void setCs_computer(NXcs_computer cs_computerGroup);

	/**
	 * Get a NXcs_computer node by name:
	 * <ul>
	 * <li>
	 * A collection with one or more computing nodes each with own resources.
	 * This can be as simple as a laptop or the nodes of a cluster computer.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXcs_computer for that node.
	 */
	public NXcs_computer getCs_computer(String name);

	/**
	 * Set a NXcs_computer node by name:
	 * <ul>
	 * <li>
	 * A collection with one or more computing nodes each with own resources.
	 * This can be as simple as a laptop or the nodes of a cluster computer.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param cs_computer the value to set
	 */
	public void setCs_computer(String name, NXcs_computer cs_computer);

	/**
	 * Get all NXcs_computer nodes:
	 * <ul>
	 * <li>
	 * A collection with one or more computing nodes each with own resources.
	 * This can be as simple as a laptop or the nodes of a cluster computer.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXcs_computer for that node.
	 */
	public Map<String, NXcs_computer> getAllCs_computer();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * A collection with one or more computing nodes each with own resources.
	 * This can be as simple as a laptop or the nodes of a cluster computer.</li>
	 * </ul>
	 *
	 * @param cs_computer the child nodes to add
	 */

	public void setAllCs_computer(Map<String, NXcs_computer> cs_computer);


	/**
	 * A collection of individual profiling event data which detail e.g. how
	 * much time the app took for certain computational steps and/or how much
	 * memory was consumed during these operations.
	 *
	 * @return  the value.
	 */
	public NXcs_profiling_event getCs_profiling_event();

	/**
	 * A collection of individual profiling event data which detail e.g. how
	 * much time the app took for certain computational steps and/or how much
	 * memory was consumed during these operations.
	 *
	 * @param cs_profiling_eventGroup the cs_profiling_eventGroup
	 */
	public void setCs_profiling_event(NXcs_profiling_event cs_profiling_eventGroup);

	/**
	 * Get a NXcs_profiling_event node by name:
	 * <ul>
	 * <li>
	 * A collection of individual profiling event data which detail e.g. how
	 * much time the app took for certain computational steps and/or how much
	 * memory was consumed during these operations.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXcs_profiling_event for that node.
	 */
	public NXcs_profiling_event getCs_profiling_event(String name);

	/**
	 * Set a NXcs_profiling_event node by name:
	 * <ul>
	 * <li>
	 * A collection of individual profiling event data which detail e.g. how
	 * much time the app took for certain computational steps and/or how much
	 * memory was consumed during these operations.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param cs_profiling_event the value to set
	 */
	public void setCs_profiling_event(String name, NXcs_profiling_event cs_profiling_event);

	/**
	 * Get all NXcs_profiling_event nodes:
	 * <ul>
	 * <li>
	 * A collection of individual profiling event data which detail e.g. how
	 * much time the app took for certain computational steps and/or how much
	 * memory was consumed during these operations.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXcs_profiling_event for that node.
	 */
	public Map<String, NXcs_profiling_event> getAllCs_profiling_event();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * A collection of individual profiling event data which detail e.g. how
	 * much time the app took for certain computational steps and/or how much
	 * memory was consumed during these operations.</li>
	 * </ul>
	 *
	 * @param cs_profiling_event the child nodes to add
	 */

	public void setAllCs_profiling_event(Map<String, NXcs_profiling_event> cs_profiling_event);


}

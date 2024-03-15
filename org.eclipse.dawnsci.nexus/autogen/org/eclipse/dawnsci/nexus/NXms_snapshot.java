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

/**
 * Base class for data on the state of the microstructure at a given
 * time/iteration.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul></ul></p>
 *
 */
public interface NXms_snapshot extends NXobject {

	public static final String NX_COMMENT = "comment";
	public static final String NX_TIME = "time";
	public static final String NX_ITERATION = "iteration";
	/**
	 * Is this time for a measurement or a simulation.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>measurement</b> </li>
	 * <li><b>simulation</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getComment();

	/**
	 * Is this time for a measurement or a simulation.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>measurement</b> </li>
	 * <li><b>simulation</b> </li></ul></p>
	 * </p>
	 *
	 * @param commentDataset the commentDataset
	 */
	public DataNode setComment(IDataset commentDataset);

	/**
	 * Is this time for a measurement or a simulation.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>measurement</b> </li>
	 * <li><b>simulation</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getCommentScalar();

	/**
	 * Is this time for a measurement or a simulation.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>measurement</b> </li>
	 * <li><b>simulation</b> </li></ul></p>
	 * </p>
	 *
	 * @param comment the comment
	 */
	public DataNode setCommentScalar(String commentValue);

	/**
	 * Measured or simulated physical time stamp for this snapshot.
	 * Not to be confused with wall-clock timing or profiling data.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getTime();

	/**
	 * Measured or simulated physical time stamp for this snapshot.
	 * Not to be confused with wall-clock timing or profiling data.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @param timeDataset the timeDataset
	 */
	public DataNode setTime(IDataset timeDataset);

	/**
	 * Measured or simulated physical time stamp for this snapshot.
	 * Not to be confused with wall-clock timing or profiling data.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getTimeScalar();

	/**
	 * Measured or simulated physical time stamp for this snapshot.
	 * Not to be confused with wall-clock timing or profiling data.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @param time the time
	 */
	public DataNode setTimeScalar(Number timeValue);

	/**
	 * Iteration or increment counter.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getIteration();

	/**
	 * Iteration or increment counter.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param iterationDataset the iterationDataset
	 */
	public DataNode setIteration(IDataset iterationDataset);

	/**
	 * Iteration or increment counter.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getIterationScalar();

	/**
	 * Iteration or increment counter.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param iteration the iteration
	 */
	public DataNode setIterationScalar(Long iterationValue);

}

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
 * Computational geometry description of a set of points.
 * Points may have an associated time value. Users are advised though to store
 * time data of point sets rather as instances of time events, where for each
 * point in time there is an :ref:`NXcg_point` instance which specifies the
 * points' locations.
 * This is a frequent situation in experiments and computer simulations, where
 * positions of points are taken at the same point in time (real time or
 * simulated physical time). Thereby, the storage of redundant timestamp
 * information per point is considered as obsolete.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>d</b>
 * The dimensionality.</li>
 * <li><b>c</b>
 * The cardinality of the set, i.e. the number of points.</li></ul></p>
 *
 */
public interface NXcg_point extends NXcg_primitive {

	public static final String NX_POSITION = "position";
	public static final String NX_TIME = "time";
	public static final String NX_TIMESTAMP = "timestamp";
	public static final String NX_TIME_OFFSET = "time_offset";
	/**
	 * Coordinates of the points.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getPosition();

	/**
	 * Coordinates of the points.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @param positionDataset the positionDataset
	 */
	public DataNode setPosition(IDataset positionDataset);

	/**
	 * Coordinates of the points.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getPositionScalar();

	/**
	 * Coordinates of the points.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @param position the position
	 */
	public DataNode setPositionScalar(Number positionValue);

	/**
	 * (Elapsed) time for each point.
	 * If the field time is needed contextualize the time_offset relative to which
	 * time values are defined. Alternative store timestamp.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getTime();

	/**
	 * (Elapsed) time for each point.
	 * If the field time is needed contextualize the time_offset relative to which
	 * time values are defined. Alternative store timestamp.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param timeDataset the timeDataset
	 */
	public DataNode setTime(IDataset timeDataset);

	/**
	 * (Elapsed) time for each point.
	 * If the field time is needed contextualize the time_offset relative to which
	 * time values are defined. Alternative store timestamp.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getTimeScalar();

	/**
	 * (Elapsed) time for each point.
	 * If the field time is needed contextualize the time_offset relative to which
	 * time values are defined. Alternative store timestamp.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param time the time
	 */
	public DataNode setTimeScalar(Number timeValue);

	/**
	 * ISO8601 with local time zone offset for each point.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getTimestamp();

	/**
	 * ISO8601 with local time zone offset for each point.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param timestampDataset the timestampDataset
	 */
	public DataNode setTimestamp(IDataset timestampDataset);

	/**
	 * ISO8601 with local time zone offset for each point.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Date getTimestampScalar();

	/**
	 * ISO8601 with local time zone offset for each point.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param timestamp the timestamp
	 */
	public DataNode setTimestampScalar(Date timestampValue);

	/**
	 * ISO8601 with local time zone offset that serves as the reference
	 * for values in the field time.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getTime_offset();

	/**
	 * ISO8601 with local time zone offset that serves as the reference
	 * for values in the field time.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @param time_offsetDataset the time_offsetDataset
	 */
	public DataNode setTime_offset(IDataset time_offsetDataset);

	/**
	 * ISO8601 with local time zone offset that serves as the reference
	 * for values in the field time.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Date getTime_offsetScalar();

	/**
	 * ISO8601 with local time zone offset that serves as the reference
	 * for values in the field time.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @param time_offset the time_offset
	 */
	public DataNode setTime_offsetScalar(Date time_offsetValue);

}

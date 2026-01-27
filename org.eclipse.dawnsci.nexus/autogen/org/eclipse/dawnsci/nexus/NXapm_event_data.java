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
 * Base class to store state and (meta)data of events over the course of an atom probe experiment.
 * Having at least one instance for an instance of NXapm is recommended.
 * This base class applies the concept of the :ref:`NXem_event_data` base class to the specific needs
 * of atom probe research. Again static and dynamic quantities are split to avoid a duplication
 * of information. Specifically, the time interval considered is the entire time
 * starting at start_time until end_time during which we assume the pulser triggered pulses.
 * These pulses are identified via the pulse_id field. The point in time when each pulse was
 * fired can be recovered from analyzing start_time and delta_time.
 * Which temporal granularity is adequate depends on the situation and research question.
 * Using a model which enables a collection of events offers the most flexible way to cater for
 * both atom probe experiments or simulation. To monitor the course of an ion extraction experiment
 * (or simulation) it makes sense to track time explicitly via time stamps or implicitly
 * via e.g. a clock inside the instrument, such as the clock of the pulser and respective pulse_id.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>p</b>
 * Number of pulses collected in between start_time and end_time.</li></ul></p>
 *
 */
public interface NXapm_event_data extends NXobject {

	public static final String NX_START_TIME = "start_time";
	public static final String NX_END_TIME = "end_time";
	public static final String NX_DELTA_TIME = "delta_time";
	public static final String NX_PULSE_ID_OFFSET = "pulse_id_offset";
	public static final String NX_PULSE_ID = "pulse_id";
	/**
	 * ISO 8601 time code with local time zone offset to UTC information included
	 * when the snapshot time interval started.
	 * If users wish to specify an interval of time that the snapshot should represent
	 * during which the instrument was stable and configured using specific settings and
	 * calibrations, the start_time is the start, the left bound of the time interval, while
	 * the end_time specifies the end, the right bound of the time interval.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getStart_time();

	/**
	 * ISO 8601 time code with local time zone offset to UTC information included
	 * when the snapshot time interval started.
	 * If users wish to specify an interval of time that the snapshot should represent
	 * during which the instrument was stable and configured using specific settings and
	 * calibrations, the start_time is the start, the left bound of the time interval, while
	 * the end_time specifies the end, the right bound of the time interval.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @param start_timeDataset the start_timeDataset
	 */
	public DataNode setStart_time(IDataset start_timeDataset);

	/**
	 * ISO 8601 time code with local time zone offset to UTC information included
	 * when the snapshot time interval started.
	 * If users wish to specify an interval of time that the snapshot should represent
	 * during which the instrument was stable and configured using specific settings and
	 * calibrations, the start_time is the start, the left bound of the time interval, while
	 * the end_time specifies the end, the right bound of the time interval.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Date getStart_timeScalar();

	/**
	 * ISO 8601 time code with local time zone offset to UTC information included
	 * when the snapshot time interval started.
	 * If users wish to specify an interval of time that the snapshot should represent
	 * during which the instrument was stable and configured using specific settings and
	 * calibrations, the start_time is the start, the left bound of the time interval, while
	 * the end_time specifies the end, the right bound of the time interval.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @param start_time the start_time
	 */
	public DataNode setStart_timeScalar(Date start_timeValue);

	/**
	 * ISO 8601 time code with local time zone offset to UTC information included
	 * when the snapshot time interval ended.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getEnd_time();

	/**
	 * ISO 8601 time code with local time zone offset to UTC information included
	 * when the snapshot time interval ended.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @param end_timeDataset the end_timeDataset
	 */
	public DataNode setEnd_time(IDataset end_timeDataset);

	/**
	 * ISO 8601 time code with local time zone offset to UTC information included
	 * when the snapshot time interval ended.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Date getEnd_timeScalar();

	/**
	 * ISO 8601 time code with local time zone offset to UTC information included
	 * when the snapshot time interval ended.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @param end_time the end_time
	 */
	public DataNode setEnd_timeScalar(Date end_timeValue);

	/**
	 * Delta time array which resolves for each pulse_id the time difference
	 * between when that pulse was fired and start_time.
	 * In summary, using start_time, end_time, delta_time, pulse_id_offset,
	 * and pulse_id provides temporal context information when a pulse was
	 * fired relative to start_time and when it is relevant to translate this into
	 * coordinated world time UTC.
	 * Note that pulses in reality have a shape and thus additional documentation
	 * is required to assure that the entries in delta_time are always taken at
	 * at points in time that, relative to the triggering of the pulse, represent an
	 * as close as possible state of the pulse.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * <b>Dimensions:</b> 1: p;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDelta_time();

	/**
	 * Delta time array which resolves for each pulse_id the time difference
	 * between when that pulse was fired and start_time.
	 * In summary, using start_time, end_time, delta_time, pulse_id_offset,
	 * and pulse_id provides temporal context information when a pulse was
	 * fired relative to start_time and when it is relevant to translate this into
	 * coordinated world time UTC.
	 * Note that pulses in reality have a shape and thus additional documentation
	 * is required to assure that the entries in delta_time are always taken at
	 * at points in time that, relative to the triggering of the pulse, represent an
	 * as close as possible state of the pulse.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * <b>Dimensions:</b> 1: p;
	 * </p>
	 *
	 * @param delta_timeDataset the delta_timeDataset
	 */
	public DataNode setDelta_time(IDataset delta_timeDataset);

	/**
	 * Delta time array which resolves for each pulse_id the time difference
	 * between when that pulse was fired and start_time.
	 * In summary, using start_time, end_time, delta_time, pulse_id_offset,
	 * and pulse_id provides temporal context information when a pulse was
	 * fired relative to start_time and when it is relevant to translate this into
	 * coordinated world time UTC.
	 * Note that pulses in reality have a shape and thus additional documentation
	 * is required to assure that the entries in delta_time are always taken at
	 * at points in time that, relative to the triggering of the pulse, represent an
	 * as close as possible state of the pulse.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * <b>Dimensions:</b> 1: p;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getDelta_timeScalar();

	/**
	 * Delta time array which resolves for each pulse_id the time difference
	 * between when that pulse was fired and start_time.
	 * In summary, using start_time, end_time, delta_time, pulse_id_offset,
	 * and pulse_id provides temporal context information when a pulse was
	 * fired relative to start_time and when it is relevant to translate this into
	 * coordinated world time UTC.
	 * Note that pulses in reality have a shape and thus additional documentation
	 * is required to assure that the entries in delta_time are always taken at
	 * at points in time that, relative to the triggering of the pulse, represent an
	 * as close as possible state of the pulse.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * <b>Dimensions:</b> 1: p;
	 * </p>
	 *
	 * @param delta_time the delta_time
	 */
	public DataNode setDelta_timeScalar(Number delta_timeValue);

	/**
	 * Integer which defines the first pulse_id.
	 * Typically, this is either zero or one.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getPulse_id_offset();

	/**
	 * Integer which defines the first pulse_id.
	 * Typically, this is either zero or one.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param pulse_id_offsetDataset the pulse_id_offsetDataset
	 */
	public DataNode setPulse_id_offset(IDataset pulse_id_offsetDataset);

	/**
	 * Integer which defines the first pulse_id.
	 * Typically, this is either zero or one.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getPulse_id_offsetScalar();

	/**
	 * Integer which defines the first pulse_id.
	 * Typically, this is either zero or one.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param pulse_id_offset the pulse_id_offset
	 */
	public DataNode setPulse_id_offsetScalar(Long pulse_id_offsetValue);

	/**
	 * An integer to identify a specific pulse in a sequence.
	 * There are two possibilities to report pulse_id values:
	 * If pulse_id_offset is provided, the pulse_id values are defined
	 * by the sequence :math:`[pulse\_id\_offset, pulse\_id\_offset + p]`
	 * with :math:`p` the number of pulses collected in between
	 * start_time and end_time.
	 * Alternatively, pulse_id_offset is not provided but instead
	 * a sequence of :math:`p` values is defined.
	 * These integer values do not need to be sorted.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: p;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getPulse_id();

	/**
	 * An integer to identify a specific pulse in a sequence.
	 * There are two possibilities to report pulse_id values:
	 * If pulse_id_offset is provided, the pulse_id values are defined
	 * by the sequence :math:`[pulse\_id\_offset, pulse\_id\_offset + p]`
	 * with :math:`p` the number of pulses collected in between
	 * start_time and end_time.
	 * Alternatively, pulse_id_offset is not provided but instead
	 * a sequence of :math:`p` values is defined.
	 * These integer values do not need to be sorted.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: p;
	 * </p>
	 *
	 * @param pulse_idDataset the pulse_idDataset
	 */
	public DataNode setPulse_id(IDataset pulse_idDataset);

	/**
	 * An integer to identify a specific pulse in a sequence.
	 * There are two possibilities to report pulse_id values:
	 * If pulse_id_offset is provided, the pulse_id values are defined
	 * by the sequence :math:`[pulse\_id\_offset, pulse\_id\_offset + p]`
	 * with :math:`p` the number of pulses collected in between
	 * start_time and end_time.
	 * Alternatively, pulse_id_offset is not provided but instead
	 * a sequence of :math:`p` values is defined.
	 * These integer values do not need to be sorted.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: p;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getPulse_idScalar();

	/**
	 * An integer to identify a specific pulse in a sequence.
	 * There are two possibilities to report pulse_id values:
	 * If pulse_id_offset is provided, the pulse_id values are defined
	 * by the sequence :math:`[pulse\_id\_offset, pulse\_id\_offset + p]`
	 * with :math:`p` the number of pulses collected in between
	 * start_time and end_time.
	 * Alternatively, pulse_id_offset is not provided but instead
	 * a sequence of :math:`p` values is defined.
	 * These integer values do not need to be sorted.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: p;
	 * </p>
	 *
	 * @param pulse_id the pulse_id
	 */
	public DataNode setPulse_idScalar(Long pulse_idValue);

	/**
	 * Place to store dynamic metadata of the instrument to document as close as possible
	 * the state of the instrument during the event, i.e. in between start_time and end_time.
	 *
	 * @return  the value.
	 */
	public NXapm_instrument getInstrument();

	/**
	 * Place to store dynamic metadata of the instrument to document as close as possible
	 * the state of the instrument during the event, i.e. in between start_time and end_time.
	 *
	 * @param instrumentGroup the instrumentGroup
	 */
	public void setInstrument(NXapm_instrument instrumentGroup);

}

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

/**
 * NXevent_data is a special group for storing data from neutron
 * detectors in event mode. In this mode, the detector electronics
 * emits a stream of detectorID, timestamp pairs. With detectorID
 * describing the detector element in which the neutron was detected
 * and timestamp the timestamp at which the neutron event was
 * detected. In NeXus detectorID maps to event_id, event_time_offset
 * to the timestamp.
 * As this kind of data is common at pulsed neutron
 * sources, the timestamp is almost always relative to the start of a
 * neutron pulse. Thus the pulse timestamp is recorded too together
 * with an index in the event_id, event_time_offset pair at which data for
 * that pulse starts. At reactor source the same pulsed data effect
 * may be achieved through the use of choppers or in stroboscopic
 * measurement setups.
 * In order to make random access to timestamped data
 * faster there is an optional array pair of
 * cue_timestamp_zero and cue_index. The cue_timestamp_zero will
 * contain courser timestamps then in the time array, say
 * every five minutes. The cue_index will then contain the
 * index into the event_id,event_time_offset pair of arrays for that
 * courser cue_timestamp_zero.
 * 
 */
public interface NXevent_data extends NXobject {

	public static final String NX_EVENT_TIME_OFFSET = "event_time_offset";
	public static final String NX_EVENT_ID = "event_id";
	public static final String NX_EVENT_TIME_ZERO = "event_time_zero";
	public static final String NX_EVENT_TIME_ZERO_ATTRIBUTE_OFFSET = "offset";
	public static final String NX_EVENT_INDEX = "event_index";
	public static final String NX_PULSE_HEIGHT = "pulse_height";
	public static final String NX_CUE_TIMESTAMP_ZERO = "cue_timestamp_zero";
	public static final String NX_CUE_TIMESTAMP_ZERO_ATTRIBUTE_START = "start";
	public static final String NX_CUE_INDEX = "cue_index";
	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	/**
	 * A list of timestamps for each event as it comes in.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME_OF_FLIGHT
	 * <b>Dimensions:</b> 1: i;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getEvent_time_offset();
	
	/**
	 * A list of timestamps for each event as it comes in.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME_OF_FLIGHT
	 * <b>Dimensions:</b> 1: i;
	 * </p>
	 * 
	 * @param event_time_offsetDataset the event_time_offsetDataset
	 */
	public DataNode setEvent_time_offset(IDataset event_time_offsetDataset);

	/**
	 * A list of timestamps for each event as it comes in.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME_OF_FLIGHT
	 * <b>Dimensions:</b> 1: i;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Number getEvent_time_offsetScalar();

	/**
	 * A list of timestamps for each event as it comes in.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME_OF_FLIGHT
	 * <b>Dimensions:</b> 1: i;
	 * </p>
	 * 
	 * @param event_time_offset the event_time_offset
	 */
	public DataNode setEvent_time_offsetScalar(Number event_time_offsetValue);

	/**
	 * There will be extra information in the NXdetector to convert
	 * event_id to detector_number.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: i;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getEvent_id();
	
	/**
	 * There will be extra information in the NXdetector to convert
	 * event_id to detector_number.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: i;
	 * </p>
	 * 
	 * @param event_idDataset the event_idDataset
	 */
	public DataNode setEvent_id(IDataset event_idDataset);

	/**
	 * There will be extra information in the NXdetector to convert
	 * event_id to detector_number.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: i;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Long getEvent_idScalar();

	/**
	 * There will be extra information in the NXdetector to convert
	 * event_id to detector_number.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: i;
	 * </p>
	 * 
	 * @param event_id the event_id
	 */
	public DataNode setEvent_idScalar(Long event_idValue);

	/**
	 * The time that each pulse started with respect to the offset
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * <b>Dimensions:</b> 1: j;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getEvent_time_zero();
	
	/**
	 * The time that each pulse started with respect to the offset
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * <b>Dimensions:</b> 1: j;
	 * </p>
	 * 
	 * @param event_time_zeroDataset the event_time_zeroDataset
	 */
	public DataNode setEvent_time_zero(IDataset event_time_zeroDataset);

	/**
	 * The time that each pulse started with respect to the offset
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * <b>Dimensions:</b> 1: j;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Number getEvent_time_zeroScalar();

	/**
	 * The time that each pulse started with respect to the offset
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * <b>Dimensions:</b> 1: j;
	 * </p>
	 * 
	 * @param event_time_zero the event_time_zero
	 */
	public DataNode setEvent_time_zeroScalar(Number event_time_zeroValue);

	/**
	 * ISO8601
	 * 
	 * @return  the value.
	 */
	public Date getEvent_time_zeroAttributeOffset();
	
	/**
	 * ISO8601
	 * 
	 * @param offsetValue the offsetValue
	 */
	public void setEvent_time_zeroAttributeOffset(Date offsetValue);

	/**
	 * The index into the event_time_offset, event_id pair for
	 * the pulse occurring at the matching entry in event_time_zero.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: j;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getEvent_index();
	
	/**
	 * The index into the event_time_offset, event_id pair for
	 * the pulse occurring at the matching entry in event_time_zero.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: j;
	 * </p>
	 * 
	 * @param event_indexDataset the event_indexDataset
	 */
	public DataNode setEvent_index(IDataset event_indexDataset);

	/**
	 * The index into the event_time_offset, event_id pair for
	 * the pulse occurring at the matching entry in event_time_zero.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: j;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Long getEvent_indexScalar();

	/**
	 * The index into the event_time_offset, event_id pair for
	 * the pulse occurring at the matching entry in event_time_zero.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: j;
	 * </p>
	 * 
	 * @param event_index the event_index
	 */
	public DataNode setEvent_indexScalar(Long event_indexValue);

	/**
	 * If voltages from the ends of the detector are read out this
	 * is where they go. This list is for all events with information
	 * to attach to a particular pulse height. The information to
	 * attach to a particular pulse is located in events_per_pulse.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: i; 2: k;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getPulse_height();
	
	/**
	 * If voltages from the ends of the detector are read out this
	 * is where they go. This list is for all events with information
	 * to attach to a particular pulse height. The information to
	 * attach to a particular pulse is located in events_per_pulse.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: i; 2: k;
	 * </p>
	 * 
	 * @param pulse_heightDataset the pulse_heightDataset
	 */
	public DataNode setPulse_height(IDataset pulse_heightDataset);

	/**
	 * If voltages from the ends of the detector are read out this
	 * is where they go. This list is for all events with information
	 * to attach to a particular pulse height. The information to
	 * attach to a particular pulse is located in events_per_pulse.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: i; 2: k;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getPulse_heightScalar();

	/**
	 * If voltages from the ends of the detector are read out this
	 * is where they go. This list is for all events with information
	 * to attach to a particular pulse height. The information to
	 * attach to a particular pulse is located in events_per_pulse.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: i; 2: k;
	 * </p>
	 * 
	 * @param pulse_height the pulse_height
	 */
	public DataNode setPulse_heightScalar(Double pulse_heightValue);

	/**
	 * Timestamps matching the corresponding cue_index into the
	 * event_id, event_time_offset pair.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * <b>Units:</b> NX_TIME
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getCue_timestamp_zero();
	
	/**
	 * Timestamps matching the corresponding cue_index into the
	 * event_id, event_time_offset pair.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * <b>Units:</b> NX_TIME
	 * </p>
	 * 
	 * @param cue_timestamp_zeroDataset the cue_timestamp_zeroDataset
	 */
	public DataNode setCue_timestamp_zero(IDataset cue_timestamp_zeroDataset);

	/**
	 * Timestamps matching the corresponding cue_index into the
	 * event_id, event_time_offset pair.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * <b>Units:</b> NX_TIME
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Date getCue_timestamp_zeroScalar();

	/**
	 * Timestamps matching the corresponding cue_index into the
	 * event_id, event_time_offset pair.
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * <b>Units:</b> NX_TIME
	 * </p>
	 * 
	 * @param cue_timestamp_zero the cue_timestamp_zero
	 */
	public DataNode setCue_timestamp_zeroScalar(Date cue_timestamp_zeroValue);

	/**
	 * 
	 * @return  the value.
	 */
	public Date getCue_timestamp_zeroAttributeStart();
	
	/**
	 * 
	 * @param startValue the startValue
	 */
	public void setCue_timestamp_zeroAttributeStart(Date startValue);

	/**
	 * Index into the event_id, event_time_offset pair matching the corresponding
	 * cue_timestamp.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getCue_index();
	
	/**
	 * Index into the event_id, event_time_offset pair matching the corresponding
	 * cue_timestamp.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 * 
	 * @param cue_indexDataset the cue_indexDataset
	 */
	public DataNode setCue_index(IDataset cue_indexDataset);

	/**
	 * Index into the event_id, event_time_offset pair matching the corresponding
	 * cue_timestamp.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Long getCue_indexScalar();

	/**
	 * Index into the event_id, event_time_offset pair matching the corresponding
	 * cue_timestamp.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 * 
	 * @param cue_index the cue_index
	 */
	public DataNode setCue_indexScalar(Long cue_indexValue);

	/**
	 * .. index:: plotting
	 * Declares which child group contains a path leading
	 * to a :ref:`NXdata` group.
	 * It is recommended (as of NIAC2014) to use this attribute
	 * to help define the path to the default dataset to be plotted.
	 * See https://www.nexusformat.org/2014_How_to_find_default_data.html
	 * for a summary of the discussion.
	 * 
	 * @return  the value.
	 */
	public String getAttributeDefault();
	
	/**
	 * .. index:: plotting
	 * Declares which child group contains a path leading
	 * to a :ref:`NXdata` group.
	 * It is recommended (as of NIAC2014) to use this attribute
	 * to help define the path to the default dataset to be plotted.
	 * See https://www.nexusformat.org/2014_How_to_find_default_data.html
	 * for a summary of the discussion.
	 * 
	 * @param defaultValue the defaultValue
	 */
	public void setAttributeDefault(String defaultValue);

}

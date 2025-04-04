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
 * Information recorded as a function of time.
 * Description of information that is recorded against
 * time. There are two common use cases for this:
 * - When logging data such as temperature during a run
 * - When data is taken in streaming mode data acquisition,
 * i.e. just timestamp, value pairs are stored and
 * correlated later in data reduction with other data,
 * In both cases, NXlog contains
 * the logged or streamed values and the times at which they were measured as elapsed time since a starting
 * time recorded in ISO8601 format. The time units are
 * specified in the units attribute. An optional scaling attribute
 * can be used to accomodate non standard clocks.
 * This method of storing logged data helps to distinguish instances in which a variable contains signal or
 * axis coordinate values of plottable data, in which case it is stored
 * in an :ref:`NXdata` group, and instances in which it is logged during the
 * run, when it should be stored in an :ref:`NXlog` group.
 * In order to make random access to timestamped data faster there is an optional array pair of
 * ``cue_timestamp_zero`` and ``cue_index``. The ``cue_timestamp_zero`` will
 * contain coarser timestamps than in the time array, say
 * every five minutes. The ``cue_index`` will then contain the
 * index into the time,value pair of arrays for that
 * coarser ``cue_timestamp_zero``.
 *
 */
public interface NXlog extends NXobject {

	public static final String NX_TIME = "time";
	public static final String NX_TIME_ATTRIBUTE_START = "start";
	public static final String NX_TIME_ATTRIBUTE_SCALING_FACTOR = "scaling_factor";
	public static final String NX_VALUE = "value";
	public static final String NX_RAW_VALUE = "raw_value";
	public static final String NX_DESCRIPTION = "description";
	public static final String NX_AVERAGE_VALUE = "average_value";
	public static final String NX_AVERAGE_VALUE_ERROR = "average_value_error";
	public static final String NX_AVERAGE_VALUE_ERRORS = "average_value_errors";
	public static final String NX_MINIMUM_VALUE = "minimum_value";
	public static final String NX_MAXIMUM_VALUE = "maximum_value";
	public static final String NX_DURATION = "duration";
	public static final String NX_CUE_TIMESTAMP_ZERO = "cue_timestamp_zero";
	public static final String NX_CUE_TIMESTAMP_ZERO_ATTRIBUTE_START = "start";
	public static final String NX_CUE_TIMESTAMP_ZERO_ATTRIBUTE_SCALING_FACTOR = "scaling_factor";
	public static final String NX_CUE_INDEX = "cue_index";
	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	/**
	 * Time of logged entry. The times are relative to the "start" attribute
	 * and in the units specified in the "units"
	 * attribute. Please note that absolute
	 * timestamps under unix are relative to ``1970-01-01T00:00:00.0Z``.
	 * The scaling_factor, when present, has to be applied to the time values in order
	 * to arrive at the units specified in the units attribute. The scaling_factor allows
	 * for arbitrary time units such as ticks of some hardware clock.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getTime();

	/**
	 * Time of logged entry. The times are relative to the "start" attribute
	 * and in the units specified in the "units"
	 * attribute. Please note that absolute
	 * timestamps under unix are relative to ``1970-01-01T00:00:00.0Z``.
	 * The scaling_factor, when present, has to be applied to the time values in order
	 * to arrive at the units specified in the units attribute. The scaling_factor allows
	 * for arbitrary time units such as ticks of some hardware clock.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @param timeDataset the timeDataset
	 */
	public DataNode setTime(IDataset timeDataset);

	/**
	 * Time of logged entry. The times are relative to the "start" attribute
	 * and in the units specified in the "units"
	 * attribute. Please note that absolute
	 * timestamps under unix are relative to ``1970-01-01T00:00:00.0Z``.
	 * The scaling_factor, when present, has to be applied to the time values in order
	 * to arrive at the units specified in the units attribute. The scaling_factor allows
	 * for arbitrary time units such as ticks of some hardware clock.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getTimeScalar();

	/**
	 * Time of logged entry. The times are relative to the "start" attribute
	 * and in the units specified in the "units"
	 * attribute. Please note that absolute
	 * timestamps under unix are relative to ``1970-01-01T00:00:00.0Z``.
	 * The scaling_factor, when present, has to be applied to the time values in order
	 * to arrive at the units specified in the units attribute. The scaling_factor allows
	 * for arbitrary time units such as ticks of some hardware clock.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @param time the time
	 */
	public DataNode setTimeScalar(Number timeValue);

	/**
	 *
	 * @return  the value.
	 */
	public Date getTimeAttributeStart();

	/**
	 *
	 * @param startValue the startValue
	 */
	public void setTimeAttributeStart(Date startValue);

	/**
	 *
	 * @return  the value.
	 */
	public Number getTimeAttributeScaling_factor();

	/**
	 *
	 * @param scaling_factorValue the scaling_factorValue
	 */
	public void setTimeAttributeScaling_factor(Number scaling_factorValue);

	/**
	 * Array of logged value, such as temperature. If this is
	 * a single value the dimensionality is
	 * nEntries. However, NXlog can also be used to store
	 * multi dimensional time stamped data such as images. In
	 * this example the dimensionality of values would be value[nEntries,xdim,ydim].
	 * <p>
	 * <b>Units:</b> NX_ANY
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getValue();

	/**
	 * Array of logged value, such as temperature. If this is
	 * a single value the dimensionality is
	 * nEntries. However, NXlog can also be used to store
	 * multi dimensional time stamped data such as images. In
	 * this example the dimensionality of values would be value[nEntries,xdim,ydim].
	 * <p>
	 * <b>Units:</b> NX_ANY
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @param valueDataset the valueDataset
	 */
	public DataNode setValue(IDataset valueDataset);

	/**
	 * Array of logged value, such as temperature. If this is
	 * a single value the dimensionality is
	 * nEntries. However, NXlog can also be used to store
	 * multi dimensional time stamped data such as images. In
	 * this example the dimensionality of values would be value[nEntries,xdim,ydim].
	 * <p>
	 * <b>Units:</b> NX_ANY
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getValueScalar();

	/**
	 * Array of logged value, such as temperature. If this is
	 * a single value the dimensionality is
	 * nEntries. However, NXlog can also be used to store
	 * multi dimensional time stamped data such as images. In
	 * this example the dimensionality of values would be value[nEntries,xdim,ydim].
	 * <p>
	 * <b>Units:</b> NX_ANY
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @param value the value
	 */
	public DataNode setValueScalar(Number valueValue);

	/**
	 * Array of raw information, such as thermocouple voltage
	 * <p>
	 * <b>Units:</b> NX_ANY
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getRaw_value();

	/**
	 * Array of raw information, such as thermocouple voltage
	 * <p>
	 * <b>Units:</b> NX_ANY
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @param raw_valueDataset the raw_valueDataset
	 */
	public DataNode setRaw_value(IDataset raw_valueDataset);

	/**
	 * Array of raw information, such as thermocouple voltage
	 * <p>
	 * <b>Units:</b> NX_ANY
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getRaw_valueScalar();

	/**
	 * Array of raw information, such as thermocouple voltage
	 * <p>
	 * <b>Units:</b> NX_ANY
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @param raw_value the raw_value
	 */
	public DataNode setRaw_valueScalar(Number raw_valueValue);

	/**
	 * Description of logged value
	 *
	 * @return  the value.
	 */
	public Dataset getDescription();

	/**
	 * Description of logged value
	 *
	 * @param descriptionDataset the descriptionDataset
	 */
	public DataNode setDescription(IDataset descriptionDataset);

	/**
	 * Description of logged value
	 *
	 * @return  the value.
	 */
	public String getDescriptionScalar();

	/**
	 * Description of logged value
	 *
	 * @param description the description
	 */
	public DataNode setDescriptionScalar(String descriptionValue);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getAverage_value();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param average_valueDataset the average_valueDataset
	 */
	public DataNode setAverage_value(IDataset average_valueDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getAverage_valueScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param average_value the average_value
	 */
	public DataNode setAverage_valueScalar(Double average_valueValue);

	/**
	 * estimated uncertainty (often used: standard deviation) of average_value
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @deprecated see: https://github.com/nexusformat/definitions/issues/639
	 * @return  the value.
	 */
	@Deprecated
	public Dataset getAverage_value_error();

	/**
	 * estimated uncertainty (often used: standard deviation) of average_value
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @deprecated see: https://github.com/nexusformat/definitions/issues/639
	 * @param average_value_errorDataset the average_value_errorDataset
	 */
	@Deprecated
	public DataNode setAverage_value_error(IDataset average_value_errorDataset);

	/**
	 * estimated uncertainty (often used: standard deviation) of average_value
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @deprecated see: https://github.com/nexusformat/definitions/issues/639
	 * @return  the value.
	 */
	@Deprecated
	public Double getAverage_value_errorScalar();

	/**
	 * estimated uncertainty (often used: standard deviation) of average_value
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @deprecated see: https://github.com/nexusformat/definitions/issues/639
	 * @param average_value_error the average_value_error
	 */
	@Deprecated
	public DataNode setAverage_value_errorScalar(Double average_value_errorValue);

	/**
	 * estimated uncertainty (often used: standard deviation) of average_value
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getAverage_value_errors();

	/**
	 * estimated uncertainty (often used: standard deviation) of average_value
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param average_value_errorsDataset the average_value_errorsDataset
	 */
	public DataNode setAverage_value_errors(IDataset average_value_errorsDataset);

	/**
	 * estimated uncertainty (often used: standard deviation) of average_value
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getAverage_value_errorsScalar();

	/**
	 * estimated uncertainty (often used: standard deviation) of average_value
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param average_value_errors the average_value_errors
	 */
	public DataNode setAverage_value_errorsScalar(Double average_value_errorsValue);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getMinimum_value();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param minimum_valueDataset the minimum_valueDataset
	 */
	public DataNode setMinimum_value(IDataset minimum_valueDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getMinimum_valueScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param minimum_value the minimum_value
	 */
	public DataNode setMinimum_valueScalar(Double minimum_valueValue);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getMaximum_value();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param maximum_valueDataset the maximum_valueDataset
	 */
	public DataNode setMaximum_value(IDataset maximum_valueDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getMaximum_valueScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param maximum_value the maximum_value
	 */
	public DataNode setMaximum_valueScalar(Double maximum_valueValue);

	/**
	 * Total time log was taken
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDuration();

	/**
	 * Total time log was taken
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param durationDataset the durationDataset
	 */
	public DataNode setDuration(IDataset durationDataset);

	/**
	 * Total time log was taken
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getDurationScalar();

	/**
	 * Total time log was taken
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param duration the duration
	 */
	public DataNode setDurationScalar(Double durationValue);

	/**
	 * Timestamps matching the corresponding cue_index into the
	 * time, value pair.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getCue_timestamp_zero();

	/**
	 * Timestamps matching the corresponding cue_index into the
	 * time, value pair.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @param cue_timestamp_zeroDataset the cue_timestamp_zeroDataset
	 */
	public DataNode setCue_timestamp_zero(IDataset cue_timestamp_zeroDataset);

	/**
	 * Timestamps matching the corresponding cue_index into the
	 * time, value pair.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getCue_timestamp_zeroScalar();

	/**
	 * Timestamps matching the corresponding cue_index into the
	 * time, value pair.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @param cue_timestamp_zero the cue_timestamp_zero
	 */
	public DataNode setCue_timestamp_zeroScalar(Number cue_timestamp_zeroValue);

	/**
	 * If missing start is assumed to be the same as for "time".
	 *
	 * @return  the value.
	 */
	public Date getCue_timestamp_zeroAttributeStart();

	/**
	 * If missing start is assumed to be the same as for "time".
	 *
	 * @param startValue the startValue
	 */
	public void setCue_timestamp_zeroAttributeStart(Date startValue);

	/**
	 * If missing start is assumed to be the same as for "time".
	 *
	 * @return  the value.
	 */
	public Number getCue_timestamp_zeroAttributeScaling_factor();

	/**
	 * If missing start is assumed to be the same as for "time".
	 *
	 * @param scaling_factorValue the scaling_factorValue
	 */
	public void setCue_timestamp_zeroAttributeScaling_factor(Number scaling_factorValue);

	/**
	 * Index into the time, value pair matching the corresponding
	 * cue_timestamp_zero.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getCue_index();

	/**
	 * Index into the time, value pair matching the corresponding
	 * cue_timestamp_zero.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 *
	 * @param cue_indexDataset the cue_indexDataset
	 */
	public DataNode setCue_index(IDataset cue_indexDataset);

	/**
	 * Index into the time, value pair matching the corresponding
	 * cue_timestamp_zero.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getCue_indexScalar();

	/**
	 * Index into the time, value pair matching the corresponding
	 * cue_timestamp_zero.
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

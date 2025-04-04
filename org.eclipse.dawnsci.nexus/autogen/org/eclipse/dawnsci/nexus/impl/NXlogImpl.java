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

 */
public class NXlogImpl extends NXobjectImpl implements NXlog {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXlogImpl() {
		super();
	}

	public NXlogImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXlog.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_LOG;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
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
	public Date getTimeAttributeStart() {
		return getAttrDate(NX_TIME, NX_TIME_ATTRIBUTE_START);
	}

	@Override
	public void setTimeAttributeStart(Date startValue) {
		setAttribute(NX_TIME, NX_TIME_ATTRIBUTE_START, startValue);
	}

	@Override
	public Number getTimeAttributeScaling_factor() {
		return getAttrNumber(NX_TIME, NX_TIME_ATTRIBUTE_SCALING_FACTOR);
	}

	@Override
	public void setTimeAttributeScaling_factor(Number scaling_factorValue) {
		setAttribute(NX_TIME, NX_TIME_ATTRIBUTE_SCALING_FACTOR, scaling_factorValue);
	}

	@Override
	public Dataset getValue() {
		return getDataset(NX_VALUE);
	}

	@Override
	public Number getValueScalar() {
		return getNumber(NX_VALUE);
	}

	@Override
	public DataNode setValue(IDataset valueDataset) {
		return setDataset(NX_VALUE, valueDataset);
	}

	@Override
	public DataNode setValueScalar(Number valueValue) {
		return setField(NX_VALUE, valueValue);
	}

	@Override
	public Dataset getRaw_value() {
		return getDataset(NX_RAW_VALUE);
	}

	@Override
	public Number getRaw_valueScalar() {
		return getNumber(NX_RAW_VALUE);
	}

	@Override
	public DataNode setRaw_value(IDataset raw_valueDataset) {
		return setDataset(NX_RAW_VALUE, raw_valueDataset);
	}

	@Override
	public DataNode setRaw_valueScalar(Number raw_valueValue) {
		return setField(NX_RAW_VALUE, raw_valueValue);
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
	public Dataset getAverage_value() {
		return getDataset(NX_AVERAGE_VALUE);
	}

	@Override
	public Double getAverage_valueScalar() {
		return getDouble(NX_AVERAGE_VALUE);
	}

	@Override
	public DataNode setAverage_value(IDataset average_valueDataset) {
		return setDataset(NX_AVERAGE_VALUE, average_valueDataset);
	}

	@Override
	public DataNode setAverage_valueScalar(Double average_valueValue) {
		return setField(NX_AVERAGE_VALUE, average_valueValue);
	}

	@Override
	@Deprecated
	public Dataset getAverage_value_error() {
		return getDataset(NX_AVERAGE_VALUE_ERROR);
	}

	@Override
	@Deprecated
	public Double getAverage_value_errorScalar() {
		return getDouble(NX_AVERAGE_VALUE_ERROR);
	}

	@Override
	@Deprecated
	public DataNode setAverage_value_error(IDataset average_value_errorDataset) {
		return setDataset(NX_AVERAGE_VALUE_ERROR, average_value_errorDataset);
	}

	@Override
	@Deprecated
	public DataNode setAverage_value_errorScalar(Double average_value_errorValue) {
		return setField(NX_AVERAGE_VALUE_ERROR, average_value_errorValue);
	}

	@Override
	public Dataset getAverage_value_errors() {
		return getDataset(NX_AVERAGE_VALUE_ERRORS);
	}

	@Override
	public Double getAverage_value_errorsScalar() {
		return getDouble(NX_AVERAGE_VALUE_ERRORS);
	}

	@Override
	public DataNode setAverage_value_errors(IDataset average_value_errorsDataset) {
		return setDataset(NX_AVERAGE_VALUE_ERRORS, average_value_errorsDataset);
	}

	@Override
	public DataNode setAverage_value_errorsScalar(Double average_value_errorsValue) {
		return setField(NX_AVERAGE_VALUE_ERRORS, average_value_errorsValue);
	}

	@Override
	public Dataset getMinimum_value() {
		return getDataset(NX_MINIMUM_VALUE);
	}

	@Override
	public Double getMinimum_valueScalar() {
		return getDouble(NX_MINIMUM_VALUE);
	}

	@Override
	public DataNode setMinimum_value(IDataset minimum_valueDataset) {
		return setDataset(NX_MINIMUM_VALUE, minimum_valueDataset);
	}

	@Override
	public DataNode setMinimum_valueScalar(Double minimum_valueValue) {
		return setField(NX_MINIMUM_VALUE, minimum_valueValue);
	}

	@Override
	public Dataset getMaximum_value() {
		return getDataset(NX_MAXIMUM_VALUE);
	}

	@Override
	public Double getMaximum_valueScalar() {
		return getDouble(NX_MAXIMUM_VALUE);
	}

	@Override
	public DataNode setMaximum_value(IDataset maximum_valueDataset) {
		return setDataset(NX_MAXIMUM_VALUE, maximum_valueDataset);
	}

	@Override
	public DataNode setMaximum_valueScalar(Double maximum_valueValue) {
		return setField(NX_MAXIMUM_VALUE, maximum_valueValue);
	}

	@Override
	public Dataset getDuration() {
		return getDataset(NX_DURATION);
	}

	@Override
	public Double getDurationScalar() {
		return getDouble(NX_DURATION);
	}

	@Override
	public DataNode setDuration(IDataset durationDataset) {
		return setDataset(NX_DURATION, durationDataset);
	}

	@Override
	public DataNode setDurationScalar(Double durationValue) {
		return setField(NX_DURATION, durationValue);
	}

	@Override
	public Dataset getCue_timestamp_zero() {
		return getDataset(NX_CUE_TIMESTAMP_ZERO);
	}

	@Override
	public Number getCue_timestamp_zeroScalar() {
		return getNumber(NX_CUE_TIMESTAMP_ZERO);
	}

	@Override
	public DataNode setCue_timestamp_zero(IDataset cue_timestamp_zeroDataset) {
		return setDataset(NX_CUE_TIMESTAMP_ZERO, cue_timestamp_zeroDataset);
	}

	@Override
	public DataNode setCue_timestamp_zeroScalar(Number cue_timestamp_zeroValue) {
		return setField(NX_CUE_TIMESTAMP_ZERO, cue_timestamp_zeroValue);
	}

	@Override
	public Date getCue_timestamp_zeroAttributeStart() {
		return getAttrDate(NX_CUE_TIMESTAMP_ZERO, NX_CUE_TIMESTAMP_ZERO_ATTRIBUTE_START);
	}

	@Override
	public void setCue_timestamp_zeroAttributeStart(Date startValue) {
		setAttribute(NX_CUE_TIMESTAMP_ZERO, NX_CUE_TIMESTAMP_ZERO_ATTRIBUTE_START, startValue);
	}

	@Override
	public Number getCue_timestamp_zeroAttributeScaling_factor() {
		return getAttrNumber(NX_CUE_TIMESTAMP_ZERO, NX_CUE_TIMESTAMP_ZERO_ATTRIBUTE_SCALING_FACTOR);
	}

	@Override
	public void setCue_timestamp_zeroAttributeScaling_factor(Number scaling_factorValue) {
		setAttribute(NX_CUE_TIMESTAMP_ZERO, NX_CUE_TIMESTAMP_ZERO_ATTRIBUTE_SCALING_FACTOR, scaling_factorValue);
	}

	@Override
	public Dataset getCue_index() {
		return getDataset(NX_CUE_INDEX);
	}

	@Override
	public Long getCue_indexScalar() {
		return getLong(NX_CUE_INDEX);
	}

	@Override
	public DataNode setCue_index(IDataset cue_indexDataset) {
		return setDataset(NX_CUE_INDEX, cue_indexDataset);
	}

	@Override
	public DataNode setCue_indexScalar(Long cue_indexValue) {
		return setField(NX_CUE_INDEX, cue_indexValue);
	}

	@Override
	public String getAttributeDefault() {
		return getAttrString(null, NX_ATTRIBUTE_DEFAULT);
	}

	@Override
	public void setAttributeDefault(String defaultValue) {
		setAttribute(null, NX_ATTRIBUTE_DEFAULT, defaultValue);
	}

}

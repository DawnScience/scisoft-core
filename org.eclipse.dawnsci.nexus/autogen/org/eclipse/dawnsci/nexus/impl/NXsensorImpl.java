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

import java.util.Set;
import java.util.EnumSet;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * A sensor used to monitor an external condition
 * The condition itself is described in :ref:`NXenvironment`.
 * 
 */
public class NXsensorImpl extends NXobjectImpl implements NXsensor {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_GEOMETRY,
		NexusBaseClass.NX_LOG,
		NexusBaseClass.NX_LOG,
		NexusBaseClass.NX_LOG,
		NexusBaseClass.NX_ORIENTATION);

	public NXsensorImpl() {
		super();
	}

	public NXsensorImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXsensor.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_SENSOR;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}
	

	@Override
	public IDataset getModel() {
		return getDataset(NX_MODEL);
	}

	@Override
	public String getModelScalar() {
		return getString(NX_MODEL);
	}

	@Override
	public DataNode setModel(IDataset modelDataset) {
		return setDataset(NX_MODEL, modelDataset);
	}

	@Override
	public DataNode setModelScalar(String modelValue) {
		return setString(NX_MODEL, modelValue);
	}

	@Override
	public IDataset getName() {
		return getDataset(NX_NAME);
	}

	@Override
	public String getNameScalar() {
		return getString(NX_NAME);
	}

	@Override
	public DataNode setName(IDataset nameDataset) {
		return setDataset(NX_NAME, nameDataset);
	}

	@Override
	public DataNode setNameScalar(String nameValue) {
		return setString(NX_NAME, nameValue);
	}

	@Override
	public IDataset getShort_name() {
		return getDataset(NX_SHORT_NAME);
	}

	@Override
	public String getShort_nameScalar() {
		return getString(NX_SHORT_NAME);
	}

	@Override
	public DataNode setShort_name(IDataset short_nameDataset) {
		return setDataset(NX_SHORT_NAME, short_nameDataset);
	}

	@Override
	public DataNode setShort_nameScalar(String short_nameValue) {
		return setString(NX_SHORT_NAME, short_nameValue);
	}

	@Override
	public IDataset getAttached_to() {
		return getDataset(NX_ATTACHED_TO);
	}

	@Override
	public String getAttached_toScalar() {
		return getString(NX_ATTACHED_TO);
	}

	@Override
	public DataNode setAttached_to(IDataset attached_toDataset) {
		return setDataset(NX_ATTACHED_TO, attached_toDataset);
	}

	@Override
	public DataNode setAttached_toScalar(String attached_toValue) {
		return setString(NX_ATTACHED_TO, attached_toValue);
	}

	@Override
	public NXgeometry getGeometry() {
		// dataNodeName = NX_GEOMETRY
		return getChild("geometry", NXgeometry.class);
	}

	@Override
	public void setGeometry(NXgeometry geometryGroup) {
		putChild("geometry", geometryGroup);
	}

	@Override
	public IDataset getMeasurement() {
		return getDataset(NX_MEASUREMENT);
	}

	@Override
	public String getMeasurementScalar() {
		return getString(NX_MEASUREMENT);
	}

	@Override
	public DataNode setMeasurement(IDataset measurementDataset) {
		return setDataset(NX_MEASUREMENT, measurementDataset);
	}

	@Override
	public DataNode setMeasurementScalar(String measurementValue) {
		return setString(NX_MEASUREMENT, measurementValue);
	}

	@Override
	public IDataset getType() {
		return getDataset(NX_TYPE);
	}

	@Override
	public String getTypeScalar() {
		return getString(NX_TYPE);
	}

	@Override
	public DataNode setType(IDataset typeDataset) {
		return setDataset(NX_TYPE, typeDataset);
	}

	@Override
	public DataNode setTypeScalar(String typeValue) {
		return setString(NX_TYPE, typeValue);
	}

	@Override
	public IDataset getRun_control() {
		return getDataset(NX_RUN_CONTROL);
	}

	@Override
	public Boolean getRun_controlScalar() {
		return getBoolean(NX_RUN_CONTROL);
	}

	@Override
	public DataNode setRun_control(IDataset run_controlDataset) {
		return setDataset(NX_RUN_CONTROL, run_controlDataset);
	}

	@Override
	public DataNode setRun_controlScalar(Boolean run_controlValue) {
		return setField(NX_RUN_CONTROL, run_controlValue);
	}

	@Override
	public IDataset getHigh_trip_value() {
		return getDataset(NX_HIGH_TRIP_VALUE);
	}

	@Override
	public Double getHigh_trip_valueScalar() {
		return getDouble(NX_HIGH_TRIP_VALUE);
	}

	@Override
	public DataNode setHigh_trip_value(IDataset high_trip_valueDataset) {
		return setDataset(NX_HIGH_TRIP_VALUE, high_trip_valueDataset);
	}

	@Override
	public DataNode setHigh_trip_valueScalar(Double high_trip_valueValue) {
		return setField(NX_HIGH_TRIP_VALUE, high_trip_valueValue);
	}

	@Override
	public IDataset getLow_trip_value() {
		return getDataset(NX_LOW_TRIP_VALUE);
	}

	@Override
	public Double getLow_trip_valueScalar() {
		return getDouble(NX_LOW_TRIP_VALUE);
	}

	@Override
	public DataNode setLow_trip_value(IDataset low_trip_valueDataset) {
		return setDataset(NX_LOW_TRIP_VALUE, low_trip_valueDataset);
	}

	@Override
	public DataNode setLow_trip_valueScalar(Double low_trip_valueValue) {
		return setField(NX_LOW_TRIP_VALUE, low_trip_valueValue);
	}

	@Override
	public IDataset getValue() {
		return getDataset(NX_VALUE);
	}

	@Override
	public Double getValueScalar() {
		return getDouble(NX_VALUE);
	}

	@Override
	public DataNode setValue(IDataset valueDataset) {
		return setDataset(NX_VALUE, valueDataset);
	}

	@Override
	public DataNode setValueScalar(Double valueValue) {
		return setField(NX_VALUE, valueValue);
	}

	@Override
	public IDataset getValue_deriv1() {
		return getDataset(NX_VALUE_DERIV1);
	}

	@Override
	public Double getValue_deriv1Scalar() {
		return getDouble(NX_VALUE_DERIV1);
	}

	@Override
	public DataNode setValue_deriv1(IDataset value_deriv1Dataset) {
		return setDataset(NX_VALUE_DERIV1, value_deriv1Dataset);
	}

	@Override
	public DataNode setValue_deriv1Scalar(Double value_deriv1Value) {
		return setField(NX_VALUE_DERIV1, value_deriv1Value);
	}

	@Override
	public IDataset getValue_deriv2() {
		return getDataset(NX_VALUE_DERIV2);
	}

	@Override
	public Double getValue_deriv2Scalar() {
		return getDouble(NX_VALUE_DERIV2);
	}

	@Override
	public DataNode setValue_deriv2(IDataset value_deriv2Dataset) {
		return setDataset(NX_VALUE_DERIV2, value_deriv2Dataset);
	}

	@Override
	public DataNode setValue_deriv2Scalar(Double value_deriv2Value) {
		return setField(NX_VALUE_DERIV2, value_deriv2Value);
	}

	@Override
	public NXlog getValue_log() {
		// dataNodeName = NX_VALUE_LOG
		return getChild("value_log", NXlog.class);
	}

	@Override
	public void setValue_log(NXlog value_logGroup) {
		putChild("value_log", value_logGroup);
	}

	@Override
	public NXlog getValue_deriv1_log() {
		// dataNodeName = NX_VALUE_DERIV1_LOG
		return getChild("value_deriv1_log", NXlog.class);
	}

	@Override
	public void setValue_deriv1_log(NXlog value_deriv1_logGroup) {
		putChild("value_deriv1_log", value_deriv1_logGroup);
	}

	@Override
	public NXlog getValue_deriv2_log() {
		// dataNodeName = NX_VALUE_DERIV2_LOG
		return getChild("value_deriv2_log", NXlog.class);
	}

	@Override
	public void setValue_deriv2_log(NXlog value_deriv2_logGroup) {
		putChild("value_deriv2_log", value_deriv2_logGroup);
	}

	@Override
	public IDataset getExternal_field_brief() {
		return getDataset(NX_EXTERNAL_FIELD_BRIEF);
	}

	@Override
	public String getExternal_field_briefScalar() {
		return getString(NX_EXTERNAL_FIELD_BRIEF);
	}

	@Override
	public DataNode setExternal_field_brief(IDataset external_field_briefDataset) {
		return setDataset(NX_EXTERNAL_FIELD_BRIEF, external_field_briefDataset);
	}

	@Override
	public DataNode setExternal_field_briefScalar(String external_field_briefValue) {
		return setString(NX_EXTERNAL_FIELD_BRIEF, external_field_briefValue);
	}

	@Override
	public NXorientation getExternal_field_full() {
		// dataNodeName = NX_EXTERNAL_FIELD_FULL
		return getChild("external_field_full", NXorientation.class);
	}

	@Override
	public void setExternal_field_full(NXorientation external_field_fullGroup) {
		putChild("external_field_full", external_field_fullGroup);
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

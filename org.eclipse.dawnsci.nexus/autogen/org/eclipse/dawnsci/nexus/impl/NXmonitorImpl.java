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

import org.eclipse.dawnsci.nexus.*;

/**
 * A monitor of incident beam data.
 * It is similar to the :ref:`NXdata` groups containing
 * monitor data and its associated dimension scale, e.g. time_of_flight or
 * wavelength in pulsed neutron instruments. However, it may also include
 * integrals, or scalar monitor counts, which are often used in both in both
 * pulsed and steady-state instrumentation.
 * 
 */
public class NXmonitorImpl extends NXobjectImpl implements NXmonitor {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_LOG,
		NexusBaseClass.NX_GEOMETRY);

	public NXmonitorImpl() {
		super();
	}

	public NXmonitorImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXmonitor.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_MONITOR;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}
	

	@Override
	public IDataset getMode() {
		return getDataset(NX_MODE);
	}

	@Override
	public String getModeScalar() {
		return getString(NX_MODE);
	}

	@Override
	public DataNode setMode(IDataset modeDataset) {
		return setDataset(NX_MODE, modeDataset);
	}

	@Override
	public DataNode setModeScalar(String modeValue) {
		return setString(NX_MODE, modeValue);
	}

	@Override
	public IDataset getStart_time() {
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
	public IDataset getEnd_time() {
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
	public IDataset getPreset() {
		return getDataset(NX_PRESET);
	}

	@Override
	public Number getPresetScalar() {
		return getNumber(NX_PRESET);
	}

	@Override
	public DataNode setPreset(IDataset presetDataset) {
		return setDataset(NX_PRESET, presetDataset);
	}

	@Override
	public DataNode setPresetScalar(Number presetValue) {
		return setField(NX_PRESET, presetValue);
	}

	@Override
	public IDataset getDistance() {
		return getDataset(NX_DISTANCE);
	}

	@Override
	public Double getDistanceScalar() {
		return getDouble(NX_DISTANCE);
	}

	@Override
	public DataNode setDistance(IDataset distanceDataset) {
		return setDataset(NX_DISTANCE, distanceDataset);
	}

	@Override
	public DataNode setDistanceScalar(Double distanceValue) {
		return setField(NX_DISTANCE, distanceValue);
	}

	@Override
	public IDataset getRange() {
		return getDataset(NX_RANGE);
	}

	@Override
	public Double getRangeScalar() {
		return getDouble(NX_RANGE);
	}

	@Override
	public DataNode setRange(IDataset rangeDataset) {
		return setDataset(NX_RANGE, rangeDataset);
	}

	@Override
	public DataNode setRangeScalar(Double rangeValue) {
		return setField(NX_RANGE, rangeValue);
	}

	@Override
	public IDataset getNominal() {
		return getDataset(NX_NOMINAL);
	}

	@Override
	public Number getNominalScalar() {
		return getNumber(NX_NOMINAL);
	}

	@Override
	public DataNode setNominal(IDataset nominalDataset) {
		return setDataset(NX_NOMINAL, nominalDataset);
	}

	@Override
	public DataNode setNominalScalar(Number nominalValue) {
		return setField(NX_NOMINAL, nominalValue);
	}

	@Override
	public IDataset getIntegral() {
		return getDataset(NX_INTEGRAL);
	}

	@Override
	public Number getIntegralScalar() {
		return getNumber(NX_INTEGRAL);
	}

	@Override
	public DataNode setIntegral(IDataset integralDataset) {
		return setDataset(NX_INTEGRAL, integralDataset);
	}

	@Override
	public DataNode setIntegralScalar(Number integralValue) {
		return setField(NX_INTEGRAL, integralValue);
	}

	@Override
	public NXlog getIntegral_log() {
		// dataNodeName = NX_INTEGRAL_LOG
		return getChild("integral_log", NXlog.class);
	}

	@Override
	public void setIntegral_log(NXlog integral_logGroup) {
		putChild("integral_log", integral_logGroup);
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
	public IDataset getTime_of_flight() {
		return getDataset(NX_TIME_OF_FLIGHT);
	}

	@Override
	public Double getTime_of_flightScalar() {
		return getDouble(NX_TIME_OF_FLIGHT);
	}

	@Override
	public DataNode setTime_of_flight(IDataset time_of_flightDataset) {
		return setDataset(NX_TIME_OF_FLIGHT, time_of_flightDataset);
	}

	@Override
	public DataNode setTime_of_flightScalar(Double time_of_flightValue) {
		return setField(NX_TIME_OF_FLIGHT, time_of_flightValue);
	}

	@Override
	public IDataset getEfficiency() {
		return getDataset(NX_EFFICIENCY);
	}

	@Override
	public Number getEfficiencyScalar() {
		return getNumber(NX_EFFICIENCY);
	}

	@Override
	public DataNode setEfficiency(IDataset efficiencyDataset) {
		return setDataset(NX_EFFICIENCY, efficiencyDataset);
	}

	@Override
	public DataNode setEfficiencyScalar(Number efficiencyValue) {
		return setField(NX_EFFICIENCY, efficiencyValue);
	}

	@Override
	public IDataset getData() {
		return getDataset(NX_DATA);
	}

	@Override
	public Number getDataScalar() {
		return getNumber(NX_DATA);
	}

	@Override
	public DataNode setData(IDataset dataDataset) {
		return setDataset(NX_DATA, dataDataset);
	}

	@Override
	public DataNode setDataScalar(Number dataValue) {
		return setField(NX_DATA, dataValue);
	}

	@Override
	public IDataset getSampled_fraction() {
		return getDataset(NX_SAMPLED_FRACTION);
	}

	@Override
	public Double getSampled_fractionScalar() {
		return getDouble(NX_SAMPLED_FRACTION);
	}

	@Override
	public DataNode setSampled_fraction(IDataset sampled_fractionDataset) {
		return setDataset(NX_SAMPLED_FRACTION, sampled_fractionDataset);
	}

	@Override
	public DataNode setSampled_fractionScalar(Double sampled_fractionValue) {
		return setField(NX_SAMPLED_FRACTION, sampled_fractionValue);
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
	public NXgeometry getGeometry(String name) {
		return getChild(name, NXgeometry.class);
	}

	@Override
	public void setGeometry(String name, NXgeometry geometry) {
		putChild(name, geometry);
	}

	@Override
	public Map<String, NXgeometry> getAllGeometry() {
		return getChildren(NXgeometry.class);
	}
	
	@Override
	public void setAllGeometry(Map<String, NXgeometry> geometry) {
		setChildren(geometry);
	}

	@Override
	public IDataset getCount_time() {
		return getDataset(NX_COUNT_TIME);
	}

	@Override
	public Double getCount_timeScalar() {
		return getDouble(NX_COUNT_TIME);
	}

	@Override
	public DataNode setCount_time(IDataset count_timeDataset) {
		return setDataset(NX_COUNT_TIME, count_timeDataset);
	}

	@Override
	public DataNode setCount_timeScalar(Double count_timeValue) {
		return setField(NX_COUNT_TIME, count_timeValue);
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

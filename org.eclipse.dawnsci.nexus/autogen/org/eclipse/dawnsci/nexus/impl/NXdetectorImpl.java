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
 * A detector, detector bank, or multidetector.
 * 
 */
public class NXdetectorImpl extends NXobjectImpl implements NXdetector {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_GEOMETRY,
		NexusBaseClass.NX_DATA,
		NexusBaseClass.NX_NOTE,
		NexusBaseClass.NX_NOTE,
		NexusBaseClass.NX_COLLECTION,
		NexusBaseClass.NX_DETECTOR_MODULE);

	public NXdetectorImpl() {
		super();
	}

	public NXdetectorImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXdetector.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_DETECTOR;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
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
	@Deprecated
	public Long getTime_of_flightAttributeAxis() {
		return getAttrLong(NX_TIME_OF_FLIGHT, NX_TIME_OF_FLIGHT_ATTRIBUTE_AXIS);
	}

	@Override
	@Deprecated
	public void setTime_of_flightAttributeAxis(Long axisValue) {
		setAttribute(NX_TIME_OF_FLIGHT, NX_TIME_OF_FLIGHT_ATTRIBUTE_AXIS, axisValue);
	}

	@Override
	@Deprecated
	public Long getTime_of_flightAttributePrimary() {
		return getAttrLong(NX_TIME_OF_FLIGHT, NX_TIME_OF_FLIGHT_ATTRIBUTE_PRIMARY);
	}

	@Override
	@Deprecated
	public void setTime_of_flightAttributePrimary(Long primaryValue) {
		setAttribute(NX_TIME_OF_FLIGHT, NX_TIME_OF_FLIGHT_ATTRIBUTE_PRIMARY, primaryValue);
	}

	@Override
	public String getTime_of_flightAttributeLong_name() {
		return getAttrString(NX_TIME_OF_FLIGHT, NX_TIME_OF_FLIGHT_ATTRIBUTE_LONG_NAME);
	}

	@Override
	public void setTime_of_flightAttributeLong_name(String long_nameValue) {
		setAttribute(NX_TIME_OF_FLIGHT, NX_TIME_OF_FLIGHT_ATTRIBUTE_LONG_NAME, long_nameValue);
	}

	@Override
	public IDataset getRaw_time_of_flight() {
		return getDataset(NX_RAW_TIME_OF_FLIGHT);
	}

	@Override
	public Long getRaw_time_of_flightScalar() {
		return getLong(NX_RAW_TIME_OF_FLIGHT);
	}

	@Override
	public DataNode setRaw_time_of_flight(IDataset raw_time_of_flightDataset) {
		return setDataset(NX_RAW_TIME_OF_FLIGHT, raw_time_of_flightDataset);
	}

	@Override
	public DataNode setRaw_time_of_flightScalar(Long raw_time_of_flightValue) {
		return setField(NX_RAW_TIME_OF_FLIGHT, raw_time_of_flightValue);
	}

	@Override
	public Number getRaw_time_of_flightAttributeFrequency() {
		return getAttrNumber(NX_RAW_TIME_OF_FLIGHT, NX_RAW_TIME_OF_FLIGHT_ATTRIBUTE_FREQUENCY);
	}

	@Override
	public void setRaw_time_of_flightAttributeFrequency(Number frequencyValue) {
		setAttribute(NX_RAW_TIME_OF_FLIGHT, NX_RAW_TIME_OF_FLIGHT_ATTRIBUTE_FREQUENCY, frequencyValue);
	}

	@Override
	public IDataset getDetector_number() {
		return getDataset(NX_DETECTOR_NUMBER);
	}

	@Override
	public Long getDetector_numberScalar() {
		return getLong(NX_DETECTOR_NUMBER);
	}

	@Override
	public DataNode setDetector_number(IDataset detector_numberDataset) {
		return setDataset(NX_DETECTOR_NUMBER, detector_numberDataset);
	}

	@Override
	public DataNode setDetector_numberScalar(Long detector_numberValue) {
		return setField(NX_DETECTOR_NUMBER, detector_numberValue);
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
	public String getDataAttributeLong_name() {
		return getAttrString(NX_DATA, NX_DATA_ATTRIBUTE_LONG_NAME);
	}

	@Override
	public void setDataAttributeLong_name(String long_nameValue) {
		setAttribute(NX_DATA, NX_DATA_ATTRIBUTE_LONG_NAME, long_nameValue);
	}

	@Override
	public Long getDataAttributeCheck_sum() {
		return getAttrLong(NX_DATA, NX_DATA_ATTRIBUTE_CHECK_SUM);
	}

	@Override
	public void setDataAttributeCheck_sum(Long check_sumValue) {
		setAttribute(NX_DATA, NX_DATA_ATTRIBUTE_CHECK_SUM, check_sumValue);
	}

	@Override
	public IDataset getData_errors() {
		return getDataset(NX_DATA_ERRORS);
	}

	@Override
	public Number getData_errorsScalar() {
		return getNumber(NX_DATA_ERRORS);
	}

	@Override
	public DataNode setData_errors(IDataset data_errorsDataset) {
		return setDataset(NX_DATA_ERRORS, data_errorsDataset);
	}

	@Override
	public DataNode setData_errorsScalar(Number data_errorsValue) {
		return setField(NX_DATA_ERRORS, data_errorsValue);
	}

	@Override
	public IDataset getX_pixel_offset() {
		return getDataset(NX_X_PIXEL_OFFSET);
	}

	@Override
	public Double getX_pixel_offsetScalar() {
		return getDouble(NX_X_PIXEL_OFFSET);
	}

	@Override
	public DataNode setX_pixel_offset(IDataset x_pixel_offsetDataset) {
		return setDataset(NX_X_PIXEL_OFFSET, x_pixel_offsetDataset);
	}

	@Override
	public DataNode setX_pixel_offsetScalar(Double x_pixel_offsetValue) {
		return setField(NX_X_PIXEL_OFFSET, x_pixel_offsetValue);
	}

	@Override
	@Deprecated
	public Long getX_pixel_offsetAttributeAxis() {
		return getAttrLong(NX_X_PIXEL_OFFSET, NX_X_PIXEL_OFFSET_ATTRIBUTE_AXIS);
	}

	@Override
	@Deprecated
	public void setX_pixel_offsetAttributeAxis(Long axisValue) {
		setAttribute(NX_X_PIXEL_OFFSET, NX_X_PIXEL_OFFSET_ATTRIBUTE_AXIS, axisValue);
	}

	@Override
	@Deprecated
	public Long getX_pixel_offsetAttributePrimary() {
		return getAttrLong(NX_X_PIXEL_OFFSET, NX_X_PIXEL_OFFSET_ATTRIBUTE_PRIMARY);
	}

	@Override
	@Deprecated
	public void setX_pixel_offsetAttributePrimary(Long primaryValue) {
		setAttribute(NX_X_PIXEL_OFFSET, NX_X_PIXEL_OFFSET_ATTRIBUTE_PRIMARY, primaryValue);
	}

	@Override
	public String getX_pixel_offsetAttributeLong_name() {
		return getAttrString(NX_X_PIXEL_OFFSET, NX_X_PIXEL_OFFSET_ATTRIBUTE_LONG_NAME);
	}

	@Override
	public void setX_pixel_offsetAttributeLong_name(String long_nameValue) {
		setAttribute(NX_X_PIXEL_OFFSET, NX_X_PIXEL_OFFSET_ATTRIBUTE_LONG_NAME, long_nameValue);
	}

	@Override
	public IDataset getY_pixel_offset() {
		return getDataset(NX_Y_PIXEL_OFFSET);
	}

	@Override
	public Double getY_pixel_offsetScalar() {
		return getDouble(NX_Y_PIXEL_OFFSET);
	}

	@Override
	public DataNode setY_pixel_offset(IDataset y_pixel_offsetDataset) {
		return setDataset(NX_Y_PIXEL_OFFSET, y_pixel_offsetDataset);
	}

	@Override
	public DataNode setY_pixel_offsetScalar(Double y_pixel_offsetValue) {
		return setField(NX_Y_PIXEL_OFFSET, y_pixel_offsetValue);
	}

	@Override
	@Deprecated
	public Long getY_pixel_offsetAttributeAxis() {
		return getAttrLong(NX_Y_PIXEL_OFFSET, NX_Y_PIXEL_OFFSET_ATTRIBUTE_AXIS);
	}

	@Override
	@Deprecated
	public void setY_pixel_offsetAttributeAxis(Long axisValue) {
		setAttribute(NX_Y_PIXEL_OFFSET, NX_Y_PIXEL_OFFSET_ATTRIBUTE_AXIS, axisValue);
	}

	@Override
	@Deprecated
	public Long getY_pixel_offsetAttributePrimary() {
		return getAttrLong(NX_Y_PIXEL_OFFSET, NX_Y_PIXEL_OFFSET_ATTRIBUTE_PRIMARY);
	}

	@Override
	@Deprecated
	public void setY_pixel_offsetAttributePrimary(Long primaryValue) {
		setAttribute(NX_Y_PIXEL_OFFSET, NX_Y_PIXEL_OFFSET_ATTRIBUTE_PRIMARY, primaryValue);
	}

	@Override
	public String getY_pixel_offsetAttributeLong_name() {
		return getAttrString(NX_Y_PIXEL_OFFSET, NX_Y_PIXEL_OFFSET_ATTRIBUTE_LONG_NAME);
	}

	@Override
	public void setY_pixel_offsetAttributeLong_name(String long_nameValue) {
		setAttribute(NX_Y_PIXEL_OFFSET, NX_Y_PIXEL_OFFSET_ATTRIBUTE_LONG_NAME, long_nameValue);
	}

	@Override
	public IDataset getZ_pixel_offset() {
		return getDataset(NX_Z_PIXEL_OFFSET);
	}

	@Override
	public Double getZ_pixel_offsetScalar() {
		return getDouble(NX_Z_PIXEL_OFFSET);
	}

	@Override
	public DataNode setZ_pixel_offset(IDataset z_pixel_offsetDataset) {
		return setDataset(NX_Z_PIXEL_OFFSET, z_pixel_offsetDataset);
	}

	@Override
	public DataNode setZ_pixel_offsetScalar(Double z_pixel_offsetValue) {
		return setField(NX_Z_PIXEL_OFFSET, z_pixel_offsetValue);
	}

	@Override
	@Deprecated
	public Long getZ_pixel_offsetAttributeAxis() {
		return getAttrLong(NX_Z_PIXEL_OFFSET, NX_Z_PIXEL_OFFSET_ATTRIBUTE_AXIS);
	}

	@Override
	@Deprecated
	public void setZ_pixel_offsetAttributeAxis(Long axisValue) {
		setAttribute(NX_Z_PIXEL_OFFSET, NX_Z_PIXEL_OFFSET_ATTRIBUTE_AXIS, axisValue);
	}

	@Override
	@Deprecated
	public Long getZ_pixel_offsetAttributePrimary() {
		return getAttrLong(NX_Z_PIXEL_OFFSET, NX_Z_PIXEL_OFFSET_ATTRIBUTE_PRIMARY);
	}

	@Override
	@Deprecated
	public void setZ_pixel_offsetAttributePrimary(Long primaryValue) {
		setAttribute(NX_Z_PIXEL_OFFSET, NX_Z_PIXEL_OFFSET_ATTRIBUTE_PRIMARY, primaryValue);
	}

	@Override
	public String getZ_pixel_offsetAttributeLong_name() {
		return getAttrString(NX_Z_PIXEL_OFFSET, NX_Z_PIXEL_OFFSET_ATTRIBUTE_LONG_NAME);
	}

	@Override
	public void setZ_pixel_offsetAttributeLong_name(String long_nameValue) {
		setAttribute(NX_Z_PIXEL_OFFSET, NX_Z_PIXEL_OFFSET_ATTRIBUTE_LONG_NAME, long_nameValue);
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
	public IDataset getPolar_angle() {
		return getDataset(NX_POLAR_ANGLE);
	}

	@Override
	public Double getPolar_angleScalar() {
		return getDouble(NX_POLAR_ANGLE);
	}

	@Override
	public DataNode setPolar_angle(IDataset polar_angleDataset) {
		return setDataset(NX_POLAR_ANGLE, polar_angleDataset);
	}

	@Override
	public DataNode setPolar_angleScalar(Double polar_angleValue) {
		return setField(NX_POLAR_ANGLE, polar_angleValue);
	}

	@Override
	public IDataset getAzimuthal_angle() {
		return getDataset(NX_AZIMUTHAL_ANGLE);
	}

	@Override
	public Double getAzimuthal_angleScalar() {
		return getDouble(NX_AZIMUTHAL_ANGLE);
	}

	@Override
	public DataNode setAzimuthal_angle(IDataset azimuthal_angleDataset) {
		return setDataset(NX_AZIMUTHAL_ANGLE, azimuthal_angleDataset);
	}

	@Override
	public DataNode setAzimuthal_angleScalar(Double azimuthal_angleValue) {
		return setField(NX_AZIMUTHAL_ANGLE, azimuthal_angleValue);
	}

	@Override
	public IDataset getDescription() {
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
	public IDataset getSerial_number() {
		return getDataset(NX_SERIAL_NUMBER);
	}

	@Override
	public String getSerial_numberScalar() {
		return getString(NX_SERIAL_NUMBER);
	}

	@Override
	public DataNode setSerial_number(IDataset serial_numberDataset) {
		return setDataset(NX_SERIAL_NUMBER, serial_numberDataset);
	}

	@Override
	public DataNode setSerial_numberScalar(String serial_numberValue) {
		return setString(NX_SERIAL_NUMBER, serial_numberValue);
	}

	@Override
	public IDataset getLocal_name() {
		return getDataset(NX_LOCAL_NAME);
	}

	@Override
	public String getLocal_nameScalar() {
		return getString(NX_LOCAL_NAME);
	}

	@Override
	public DataNode setLocal_name(IDataset local_nameDataset) {
		return setDataset(NX_LOCAL_NAME, local_nameDataset);
	}

	@Override
	public DataNode setLocal_nameScalar(String local_nameValue) {
		return setString(NX_LOCAL_NAME, local_nameValue);
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
	public IDataset getSolid_angle() {
		return getDataset(NX_SOLID_ANGLE);
	}

	@Override
	public Double getSolid_angleScalar() {
		return getDouble(NX_SOLID_ANGLE);
	}

	@Override
	public DataNode setSolid_angle(IDataset solid_angleDataset) {
		return setDataset(NX_SOLID_ANGLE, solid_angleDataset);
	}

	@Override
	public DataNode setSolid_angleScalar(Double solid_angleValue) {
		return setField(NX_SOLID_ANGLE, solid_angleValue);
	}

	@Override
	public IDataset getX_pixel_size() {
		return getDataset(NX_X_PIXEL_SIZE);
	}

	@Override
	public Double getX_pixel_sizeScalar() {
		return getDouble(NX_X_PIXEL_SIZE);
	}

	@Override
	public DataNode setX_pixel_size(IDataset x_pixel_sizeDataset) {
		return setDataset(NX_X_PIXEL_SIZE, x_pixel_sizeDataset);
	}

	@Override
	public DataNode setX_pixel_sizeScalar(Double x_pixel_sizeValue) {
		return setField(NX_X_PIXEL_SIZE, x_pixel_sizeValue);
	}

	@Override
	public IDataset getY_pixel_size() {
		return getDataset(NX_Y_PIXEL_SIZE);
	}

	@Override
	public Double getY_pixel_sizeScalar() {
		return getDouble(NX_Y_PIXEL_SIZE);
	}

	@Override
	public DataNode setY_pixel_size(IDataset y_pixel_sizeDataset) {
		return setDataset(NX_Y_PIXEL_SIZE, y_pixel_sizeDataset);
	}

	@Override
	public DataNode setY_pixel_sizeScalar(Double y_pixel_sizeValue) {
		return setField(NX_Y_PIXEL_SIZE, y_pixel_sizeValue);
	}

	@Override
	public IDataset getDead_time() {
		return getDataset(NX_DEAD_TIME);
	}

	@Override
	public Double getDead_timeScalar() {
		return getDouble(NX_DEAD_TIME);
	}

	@Override
	public DataNode setDead_time(IDataset dead_timeDataset) {
		return setDataset(NX_DEAD_TIME, dead_timeDataset);
	}

	@Override
	public DataNode setDead_timeScalar(Double dead_timeValue) {
		return setField(NX_DEAD_TIME, dead_timeValue);
	}

	@Override
	public IDataset getGas_pressure() {
		return getDataset(NX_GAS_PRESSURE);
	}

	@Override
	public Double getGas_pressureScalar() {
		return getDouble(NX_GAS_PRESSURE);
	}

	@Override
	public DataNode setGas_pressure(IDataset gas_pressureDataset) {
		return setDataset(NX_GAS_PRESSURE, gas_pressureDataset);
	}

	@Override
	public DataNode setGas_pressureScalar(Double gas_pressureValue) {
		return setField(NX_GAS_PRESSURE, gas_pressureValue);
	}

	@Override
	public IDataset getDetection_gas_path() {
		return getDataset(NX_DETECTION_GAS_PATH);
	}

	@Override
	public Double getDetection_gas_pathScalar() {
		return getDouble(NX_DETECTION_GAS_PATH);
	}

	@Override
	public DataNode setDetection_gas_path(IDataset detection_gas_pathDataset) {
		return setDataset(NX_DETECTION_GAS_PATH, detection_gas_pathDataset);
	}

	@Override
	public DataNode setDetection_gas_pathScalar(Double detection_gas_pathValue) {
		return setField(NX_DETECTION_GAS_PATH, detection_gas_pathValue);
	}

	@Override
	public IDataset getCrate() {
		return getDataset(NX_CRATE);
	}

	@Override
	public Long getCrateScalar() {
		return getLong(NX_CRATE);
	}

	@Override
	public DataNode setCrate(IDataset crateDataset) {
		return setDataset(NX_CRATE, crateDataset);
	}

	@Override
	public DataNode setCrateScalar(Long crateValue) {
		return setField(NX_CRATE, crateValue);
	}

	@Override
	public String getCrateAttributeLocal_name() {
		return getAttrString(NX_CRATE, NX_CRATE_ATTRIBUTE_LOCAL_NAME);
	}

	@Override
	public void setCrateAttributeLocal_name(String local_nameValue) {
		setAttribute(NX_CRATE, NX_CRATE_ATTRIBUTE_LOCAL_NAME, local_nameValue);
	}

	@Override
	public IDataset getSlot() {
		return getDataset(NX_SLOT);
	}

	@Override
	public Long getSlotScalar() {
		return getLong(NX_SLOT);
	}

	@Override
	public DataNode setSlot(IDataset slotDataset) {
		return setDataset(NX_SLOT, slotDataset);
	}

	@Override
	public DataNode setSlotScalar(Long slotValue) {
		return setField(NX_SLOT, slotValue);
	}

	@Override
	public String getSlotAttributeLocal_name() {
		return getAttrString(NX_SLOT, NX_SLOT_ATTRIBUTE_LOCAL_NAME);
	}

	@Override
	public void setSlotAttributeLocal_name(String local_nameValue) {
		setAttribute(NX_SLOT, NX_SLOT_ATTRIBUTE_LOCAL_NAME, local_nameValue);
	}

	@Override
	public IDataset getInput() {
		return getDataset(NX_INPUT);
	}

	@Override
	public Long getInputScalar() {
		return getLong(NX_INPUT);
	}

	@Override
	public DataNode setInput(IDataset inputDataset) {
		return setDataset(NX_INPUT, inputDataset);
	}

	@Override
	public DataNode setInputScalar(Long inputValue) {
		return setField(NX_INPUT, inputValue);
	}

	@Override
	public String getInputAttributeLocal_name() {
		return getAttrString(NX_INPUT, NX_INPUT_ATTRIBUTE_LOCAL_NAME);
	}

	@Override
	public void setInputAttributeLocal_name(String local_nameValue) {
		setAttribute(NX_INPUT, NX_INPUT_ATTRIBUTE_LOCAL_NAME, local_nameValue);
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
	public NXdata getEfficiency() {
		// dataNodeName = NX_EFFICIENCY
		return getChild("efficiency", NXdata.class);
	}

	@Override
	public void setEfficiency(NXdata efficiencyGroup) {
		putChild("efficiency", efficiencyGroup);
	}

	@Override
	public IDataset getReal_time() {
		return getDataset(NX_REAL_TIME);
	}

	@Override
	public Number getReal_timeScalar() {
		return getNumber(NX_REAL_TIME);
	}

	@Override
	public DataNode setReal_time(IDataset real_timeDataset) {
		return setDataset(NX_REAL_TIME, real_timeDataset);
	}

	@Override
	public DataNode setReal_timeScalar(Number real_timeValue) {
		return setField(NX_REAL_TIME, real_timeValue);
	}

	@Override
	public IDataset getStart_time() {
		return getDataset(NX_START_TIME);
	}

	@Override
	public Double getStart_timeScalar() {
		return getDouble(NX_START_TIME);
	}

	@Override
	public DataNode setStart_time(IDataset start_timeDataset) {
		return setDataset(NX_START_TIME, start_timeDataset);
	}

	@Override
	public DataNode setStart_timeScalar(Double start_timeValue) {
		return setField(NX_START_TIME, start_timeValue);
	}

	@Override
	public Date getStart_timeAttributeStart() {
		return getAttrDate(NX_START_TIME, NX_START_TIME_ATTRIBUTE_START);
	}

	@Override
	public void setStart_timeAttributeStart(Date startValue) {
		setAttribute(NX_START_TIME, NX_START_TIME_ATTRIBUTE_START, startValue);
	}

	@Override
	public IDataset getStop_time() {
		return getDataset(NX_STOP_TIME);
	}

	@Override
	public Double getStop_timeScalar() {
		return getDouble(NX_STOP_TIME);
	}

	@Override
	public DataNode setStop_time(IDataset stop_timeDataset) {
		return setDataset(NX_STOP_TIME, stop_timeDataset);
	}

	@Override
	public DataNode setStop_timeScalar(Double stop_timeValue) {
		return setField(NX_STOP_TIME, stop_timeValue);
	}

	@Override
	public Date getStop_timeAttributeStart() {
		return getAttrDate(NX_STOP_TIME, NX_STOP_TIME_ATTRIBUTE_START);
	}

	@Override
	public void setStop_timeAttributeStart(Date startValue) {
		setAttribute(NX_STOP_TIME, NX_STOP_TIME_ATTRIBUTE_START, startValue);
	}

	@Override
	public IDataset getCalibration_date() {
		return getDataset(NX_CALIBRATION_DATE);
	}

	@Override
	public Date getCalibration_dateScalar() {
		return getDate(NX_CALIBRATION_DATE);
	}

	@Override
	public DataNode setCalibration_date(IDataset calibration_dateDataset) {
		return setDataset(NX_CALIBRATION_DATE, calibration_dateDataset);
	}

	@Override
	public DataNode setCalibration_dateScalar(Date calibration_dateValue) {
		return setDate(NX_CALIBRATION_DATE, calibration_dateValue);
	}

	@Override
	public NXnote getCalibration_method() {
		// dataNodeName = NX_CALIBRATION_METHOD
		return getChild("calibration_method", NXnote.class);
	}

	@Override
	public void setCalibration_method(NXnote calibration_methodGroup) {
		putChild("calibration_method", calibration_methodGroup);
	}

	@Override
	public IDataset getLayout() {
		return getDataset(NX_LAYOUT);
	}

	@Override
	public String getLayoutScalar() {
		return getString(NX_LAYOUT);
	}

	@Override
	public DataNode setLayout(IDataset layoutDataset) {
		return setDataset(NX_LAYOUT, layoutDataset);
	}

	@Override
	public DataNode setLayoutScalar(String layoutValue) {
		return setString(NX_LAYOUT, layoutValue);
	}

	@Override
	public IDataset getCount_time() {
		return getDataset(NX_COUNT_TIME);
	}

	@Override
	public Number getCount_timeScalar() {
		return getNumber(NX_COUNT_TIME);
	}

	@Override
	public DataNode setCount_time(IDataset count_timeDataset) {
		return setDataset(NX_COUNT_TIME, count_timeDataset);
	}

	@Override
	public DataNode setCount_timeScalar(Number count_timeValue) {
		return setField(NX_COUNT_TIME, count_timeValue);
	}

	@Override
	public NXnote getData_file() {
		// dataNodeName = NX_DATA_FILE
		return getChild("data_file", NXnote.class);
	}

	@Override
	public void setData_file(NXnote data_fileGroup) {
		putChild("data_file", data_fileGroup);
	}

	@Override
	public NXcollection getCollection() {
		// dataNodeName = NX_COLLECTION
		return getChild("collection", NXcollection.class);
	}

	@Override
	public void setCollection(NXcollection collectionGroup) {
		putChild("collection", collectionGroup);
	}

	@Override
	public NXcollection getCollection(String name) {
		return getChild(name, NXcollection.class);
	}

	@Override
	public void setCollection(String name, NXcollection collection) {
		putChild(name, collection);
	}

	@Override
	public Map<String, NXcollection> getAllCollection() {
		return getChildren(NXcollection.class);
	}
	
	@Override
	public void setAllCollection(Map<String, NXcollection> collection) {
		setChildren(collection);
	}

	@Override
	public IDataset getSequence_number() {
		return getDataset(NX_SEQUENCE_NUMBER);
	}

	@Override
	public Long getSequence_numberScalar() {
		return getLong(NX_SEQUENCE_NUMBER);
	}

	@Override
	public DataNode setSequence_number(IDataset sequence_numberDataset) {
		return setDataset(NX_SEQUENCE_NUMBER, sequence_numberDataset);
	}

	@Override
	public DataNode setSequence_numberScalar(Long sequence_numberValue) {
		return setField(NX_SEQUENCE_NUMBER, sequence_numberValue);
	}

	@Override
	public IDataset getBeam_center_x() {
		return getDataset(NX_BEAM_CENTER_X);
	}

	@Override
	public Double getBeam_center_xScalar() {
		return getDouble(NX_BEAM_CENTER_X);
	}

	@Override
	public DataNode setBeam_center_x(IDataset beam_center_xDataset) {
		return setDataset(NX_BEAM_CENTER_X, beam_center_xDataset);
	}

	@Override
	public DataNode setBeam_center_xScalar(Double beam_center_xValue) {
		return setField(NX_BEAM_CENTER_X, beam_center_xValue);
	}

	@Override
	public IDataset getBeam_center_y() {
		return getDataset(NX_BEAM_CENTER_Y);
	}

	@Override
	public Double getBeam_center_yScalar() {
		return getDouble(NX_BEAM_CENTER_Y);
	}

	@Override
	public DataNode setBeam_center_y(IDataset beam_center_yDataset) {
		return setDataset(NX_BEAM_CENTER_Y, beam_center_yDataset);
	}

	@Override
	public DataNode setBeam_center_yScalar(Double beam_center_yValue) {
		return setField(NX_BEAM_CENTER_Y, beam_center_yValue);
	}

	@Override
	public IDataset getFrame_start_number() {
		return getDataset(NX_FRAME_START_NUMBER);
	}

	@Override
	public Long getFrame_start_numberScalar() {
		return getLong(NX_FRAME_START_NUMBER);
	}

	@Override
	public DataNode setFrame_start_number(IDataset frame_start_numberDataset) {
		return setDataset(NX_FRAME_START_NUMBER, frame_start_numberDataset);
	}

	@Override
	public DataNode setFrame_start_numberScalar(Long frame_start_numberValue) {
		return setField(NX_FRAME_START_NUMBER, frame_start_numberValue);
	}

	@Override
	public IDataset getDiameter() {
		return getDataset(NX_DIAMETER);
	}

	@Override
	public Double getDiameterScalar() {
		return getDouble(NX_DIAMETER);
	}

	@Override
	public DataNode setDiameter(IDataset diameterDataset) {
		return setDataset(NX_DIAMETER, diameterDataset);
	}

	@Override
	public DataNode setDiameterScalar(Double diameterValue) {
		return setField(NX_DIAMETER, diameterValue);
	}

	@Override
	public IDataset getAcquisition_mode() {
		return getDataset(NX_ACQUISITION_MODE);
	}

	@Override
	public String getAcquisition_modeScalar() {
		return getString(NX_ACQUISITION_MODE);
	}

	@Override
	public DataNode setAcquisition_mode(IDataset acquisition_modeDataset) {
		return setDataset(NX_ACQUISITION_MODE, acquisition_modeDataset);
	}

	@Override
	public DataNode setAcquisition_modeScalar(String acquisition_modeValue) {
		return setString(NX_ACQUISITION_MODE, acquisition_modeValue);
	}

	@Override
	public IDataset getAngular_calibration_applied() {
		return getDataset(NX_ANGULAR_CALIBRATION_APPLIED);
	}

	@Override
	public Boolean getAngular_calibration_appliedScalar() {
		return getBoolean(NX_ANGULAR_CALIBRATION_APPLIED);
	}

	@Override
	public DataNode setAngular_calibration_applied(IDataset angular_calibration_appliedDataset) {
		return setDataset(NX_ANGULAR_CALIBRATION_APPLIED, angular_calibration_appliedDataset);
	}

	@Override
	public DataNode setAngular_calibration_appliedScalar(Boolean angular_calibration_appliedValue) {
		return setField(NX_ANGULAR_CALIBRATION_APPLIED, angular_calibration_appliedValue);
	}

	@Override
	public IDataset getAngular_calibration() {
		return getDataset(NX_ANGULAR_CALIBRATION);
	}

	@Override
	public Double getAngular_calibrationScalar() {
		return getDouble(NX_ANGULAR_CALIBRATION);
	}

	@Override
	public DataNode setAngular_calibration(IDataset angular_calibrationDataset) {
		return setDataset(NX_ANGULAR_CALIBRATION, angular_calibrationDataset);
	}

	@Override
	public DataNode setAngular_calibrationScalar(Double angular_calibrationValue) {
		return setField(NX_ANGULAR_CALIBRATION, angular_calibrationValue);
	}

	@Override
	public IDataset getFlatfield_applied() {
		return getDataset(NX_FLATFIELD_APPLIED);
	}

	@Override
	public Boolean getFlatfield_appliedScalar() {
		return getBoolean(NX_FLATFIELD_APPLIED);
	}

	@Override
	public DataNode setFlatfield_applied(IDataset flatfield_appliedDataset) {
		return setDataset(NX_FLATFIELD_APPLIED, flatfield_appliedDataset);
	}

	@Override
	public DataNode setFlatfield_appliedScalar(Boolean flatfield_appliedValue) {
		return setField(NX_FLATFIELD_APPLIED, flatfield_appliedValue);
	}

	@Override
	public IDataset getFlatfield() {
		return getDataset(NX_FLATFIELD);
	}

	@Override
	public Double getFlatfieldScalar() {
		return getDouble(NX_FLATFIELD);
	}

	@Override
	public DataNode setFlatfield(IDataset flatfieldDataset) {
		return setDataset(NX_FLATFIELD, flatfieldDataset);
	}

	@Override
	public DataNode setFlatfieldScalar(Double flatfieldValue) {
		return setField(NX_FLATFIELD, flatfieldValue);
	}

	@Override
	public IDataset getFlatfield_errors() {
		return getDataset(NX_FLATFIELD_ERRORS);
	}

	@Override
	public Double getFlatfield_errorsScalar() {
		return getDouble(NX_FLATFIELD_ERRORS);
	}

	@Override
	public DataNode setFlatfield_errors(IDataset flatfield_errorsDataset) {
		return setDataset(NX_FLATFIELD_ERRORS, flatfield_errorsDataset);
	}

	@Override
	public DataNode setFlatfield_errorsScalar(Double flatfield_errorsValue) {
		return setField(NX_FLATFIELD_ERRORS, flatfield_errorsValue);
	}

	@Override
	public IDataset getPixel_mask_applied() {
		return getDataset(NX_PIXEL_MASK_APPLIED);
	}

	@Override
	public Boolean getPixel_mask_appliedScalar() {
		return getBoolean(NX_PIXEL_MASK_APPLIED);
	}

	@Override
	public DataNode setPixel_mask_applied(IDataset pixel_mask_appliedDataset) {
		return setDataset(NX_PIXEL_MASK_APPLIED, pixel_mask_appliedDataset);
	}

	@Override
	public DataNode setPixel_mask_appliedScalar(Boolean pixel_mask_appliedValue) {
		return setField(NX_PIXEL_MASK_APPLIED, pixel_mask_appliedValue);
	}

	@Override
	public IDataset getPixel_mask() {
		return getDataset(NX_PIXEL_MASK);
	}

	@Override
	public Long getPixel_maskScalar() {
		return getLong(NX_PIXEL_MASK);
	}

	@Override
	public DataNode setPixel_mask(IDataset pixel_maskDataset) {
		return setDataset(NX_PIXEL_MASK, pixel_maskDataset);
	}

	@Override
	public DataNode setPixel_maskScalar(Long pixel_maskValue) {
		return setField(NX_PIXEL_MASK, pixel_maskValue);
	}

	@Override
	public IDataset getCountrate_correction_applied() {
		return getDataset(NX_COUNTRATE_CORRECTION_APPLIED);
	}

	@Override
	public Boolean getCountrate_correction_appliedScalar() {
		return getBoolean(NX_COUNTRATE_CORRECTION_APPLIED);
	}

	@Override
	public DataNode setCountrate_correction_applied(IDataset countrate_correction_appliedDataset) {
		return setDataset(NX_COUNTRATE_CORRECTION_APPLIED, countrate_correction_appliedDataset);
	}

	@Override
	public DataNode setCountrate_correction_appliedScalar(Boolean countrate_correction_appliedValue) {
		return setField(NX_COUNTRATE_CORRECTION_APPLIED, countrate_correction_appliedValue);
	}

	@Override
	public IDataset getBit_depth_readout() {
		return getDataset(NX_BIT_DEPTH_READOUT);
	}

	@Override
	public Long getBit_depth_readoutScalar() {
		return getLong(NX_BIT_DEPTH_READOUT);
	}

	@Override
	public DataNode setBit_depth_readout(IDataset bit_depth_readoutDataset) {
		return setDataset(NX_BIT_DEPTH_READOUT, bit_depth_readoutDataset);
	}

	@Override
	public DataNode setBit_depth_readoutScalar(Long bit_depth_readoutValue) {
		return setField(NX_BIT_DEPTH_READOUT, bit_depth_readoutValue);
	}

	@Override
	public IDataset getDetector_readout_time() {
		return getDataset(NX_DETECTOR_READOUT_TIME);
	}

	@Override
	public Double getDetector_readout_timeScalar() {
		return getDouble(NX_DETECTOR_READOUT_TIME);
	}

	@Override
	public DataNode setDetector_readout_time(IDataset detector_readout_timeDataset) {
		return setDataset(NX_DETECTOR_READOUT_TIME, detector_readout_timeDataset);
	}

	@Override
	public DataNode setDetector_readout_timeScalar(Double detector_readout_timeValue) {
		return setField(NX_DETECTOR_READOUT_TIME, detector_readout_timeValue);
	}

	@Override
	public IDataset getTrigger_delay_time() {
		return getDataset(NX_TRIGGER_DELAY_TIME);
	}

	@Override
	public Double getTrigger_delay_timeScalar() {
		return getDouble(NX_TRIGGER_DELAY_TIME);
	}

	@Override
	public DataNode setTrigger_delay_time(IDataset trigger_delay_timeDataset) {
		return setDataset(NX_TRIGGER_DELAY_TIME, trigger_delay_timeDataset);
	}

	@Override
	public DataNode setTrigger_delay_timeScalar(Double trigger_delay_timeValue) {
		return setField(NX_TRIGGER_DELAY_TIME, trigger_delay_timeValue);
	}

	@Override
	public IDataset getTrigger_delay_time_set() {
		return getDataset(NX_TRIGGER_DELAY_TIME_SET);
	}

	@Override
	public Double getTrigger_delay_time_setScalar() {
		return getDouble(NX_TRIGGER_DELAY_TIME_SET);
	}

	@Override
	public DataNode setTrigger_delay_time_set(IDataset trigger_delay_time_setDataset) {
		return setDataset(NX_TRIGGER_DELAY_TIME_SET, trigger_delay_time_setDataset);
	}

	@Override
	public DataNode setTrigger_delay_time_setScalar(Double trigger_delay_time_setValue) {
		return setField(NX_TRIGGER_DELAY_TIME_SET, trigger_delay_time_setValue);
	}

	@Override
	public IDataset getTrigger_internal_delay_time() {
		return getDataset(NX_TRIGGER_INTERNAL_DELAY_TIME);
	}

	@Override
	public Double getTrigger_internal_delay_timeScalar() {
		return getDouble(NX_TRIGGER_INTERNAL_DELAY_TIME);
	}

	@Override
	public DataNode setTrigger_internal_delay_time(IDataset trigger_internal_delay_timeDataset) {
		return setDataset(NX_TRIGGER_INTERNAL_DELAY_TIME, trigger_internal_delay_timeDataset);
	}

	@Override
	public DataNode setTrigger_internal_delay_timeScalar(Double trigger_internal_delay_timeValue) {
		return setField(NX_TRIGGER_INTERNAL_DELAY_TIME, trigger_internal_delay_timeValue);
	}

	@Override
	public IDataset getTrigger_dead_time() {
		return getDataset(NX_TRIGGER_DEAD_TIME);
	}

	@Override
	public Double getTrigger_dead_timeScalar() {
		return getDouble(NX_TRIGGER_DEAD_TIME);
	}

	@Override
	public DataNode setTrigger_dead_time(IDataset trigger_dead_timeDataset) {
		return setDataset(NX_TRIGGER_DEAD_TIME, trigger_dead_timeDataset);
	}

	@Override
	public DataNode setTrigger_dead_timeScalar(Double trigger_dead_timeValue) {
		return setField(NX_TRIGGER_DEAD_TIME, trigger_dead_timeValue);
	}

	@Override
	public IDataset getFrame_time() {
		return getDataset(NX_FRAME_TIME);
	}

	@Override
	public Double getFrame_timeScalar() {
		return getDouble(NX_FRAME_TIME);
	}

	@Override
	public DataNode setFrame_time(IDataset frame_timeDataset) {
		return setDataset(NX_FRAME_TIME, frame_timeDataset);
	}

	@Override
	public DataNode setFrame_timeScalar(Double frame_timeValue) {
		return setField(NX_FRAME_TIME, frame_timeValue);
	}

	@Override
	public IDataset getGain_setting() {
		return getDataset(NX_GAIN_SETTING);
	}

	@Override
	public String getGain_settingScalar() {
		return getString(NX_GAIN_SETTING);
	}

	@Override
	public DataNode setGain_setting(IDataset gain_settingDataset) {
		return setDataset(NX_GAIN_SETTING, gain_settingDataset);
	}

	@Override
	public DataNode setGain_settingScalar(String gain_settingValue) {
		return setString(NX_GAIN_SETTING, gain_settingValue);
	}

	@Override
	public IDataset getSaturation_value() {
		return getDataset(NX_SATURATION_VALUE);
	}

	@Override
	public Long getSaturation_valueScalar() {
		return getLong(NX_SATURATION_VALUE);
	}

	@Override
	public DataNode setSaturation_value(IDataset saturation_valueDataset) {
		return setDataset(NX_SATURATION_VALUE, saturation_valueDataset);
	}

	@Override
	public DataNode setSaturation_valueScalar(Long saturation_valueValue) {
		return setField(NX_SATURATION_VALUE, saturation_valueValue);
	}

	@Override
	public IDataset getUnderload_value() {
		return getDataset(NX_UNDERLOAD_VALUE);
	}

	@Override
	public Long getUnderload_valueScalar() {
		return getLong(NX_UNDERLOAD_VALUE);
	}

	@Override
	public DataNode setUnderload_value(IDataset underload_valueDataset) {
		return setDataset(NX_UNDERLOAD_VALUE, underload_valueDataset);
	}

	@Override
	public DataNode setUnderload_valueScalar(Long underload_valueValue) {
		return setField(NX_UNDERLOAD_VALUE, underload_valueValue);
	}

	@Override
	public IDataset getNumber_of_cycles() {
		return getDataset(NX_NUMBER_OF_CYCLES);
	}

	@Override
	public Long getNumber_of_cyclesScalar() {
		return getLong(NX_NUMBER_OF_CYCLES);
	}

	@Override
	public DataNode setNumber_of_cycles(IDataset number_of_cyclesDataset) {
		return setDataset(NX_NUMBER_OF_CYCLES, number_of_cyclesDataset);
	}

	@Override
	public DataNode setNumber_of_cyclesScalar(Long number_of_cyclesValue) {
		return setField(NX_NUMBER_OF_CYCLES, number_of_cyclesValue);
	}

	@Override
	public IDataset getSensor_material() {
		return getDataset(NX_SENSOR_MATERIAL);
	}

	@Override
	public String getSensor_materialScalar() {
		return getString(NX_SENSOR_MATERIAL);
	}

	@Override
	public DataNode setSensor_material(IDataset sensor_materialDataset) {
		return setDataset(NX_SENSOR_MATERIAL, sensor_materialDataset);
	}

	@Override
	public DataNode setSensor_materialScalar(String sensor_materialValue) {
		return setString(NX_SENSOR_MATERIAL, sensor_materialValue);
	}

	@Override
	public IDataset getSensor_thickness() {
		return getDataset(NX_SENSOR_THICKNESS);
	}

	@Override
	public Double getSensor_thicknessScalar() {
		return getDouble(NX_SENSOR_THICKNESS);
	}

	@Override
	public DataNode setSensor_thickness(IDataset sensor_thicknessDataset) {
		return setDataset(NX_SENSOR_THICKNESS, sensor_thicknessDataset);
	}

	@Override
	public DataNode setSensor_thicknessScalar(Double sensor_thicknessValue) {
		return setField(NX_SENSOR_THICKNESS, sensor_thicknessValue);
	}

	@Override
	public IDataset getThreshold_energy() {
		return getDataset(NX_THRESHOLD_ENERGY);
	}

	@Override
	public Double getThreshold_energyScalar() {
		return getDouble(NX_THRESHOLD_ENERGY);
	}

	@Override
	public DataNode setThreshold_energy(IDataset threshold_energyDataset) {
		return setDataset(NX_THRESHOLD_ENERGY, threshold_energyDataset);
	}

	@Override
	public DataNode setThreshold_energyScalar(Double threshold_energyValue) {
		return setField(NX_THRESHOLD_ENERGY, threshold_energyValue);
	}

	@Override
	public NXdetector_module getDetector_module() {
		// dataNodeName = NX_DETECTOR_MODULE
		return getChild("detector_module", NXdetector_module.class);
	}

	@Override
	public void setDetector_module(NXdetector_module detector_moduleGroup) {
		putChild("detector_module", detector_moduleGroup);
	}

	@Override
	public NXdetector_module getDetector_module(String name) {
		return getChild(name, NXdetector_module.class);
	}

	@Override
	public void setDetector_module(String name, NXdetector_module detector_module) {
		putChild(name, detector_module);
	}

	@Override
	public Map<String, NXdetector_module> getAllDetector_module() {
		return getChildren(NXdetector_module.class);
	}
	
	@Override
	public void setAllDetector_module(Map<String, NXdetector_module> detector_module) {
		setChildren(detector_module);
	}
	// Unprocessed choice:  pixel_shape
	// Unprocessed choice:  detector_shape

	@Override
	public String getAttributeDefault() {
		return getAttrString(null, NX_ATTRIBUTE_DEFAULT);
	}

	@Override
	public void setAttributeDefault(String defaultValue) {
		setAttribute(null, NX_ATTRIBUTE_DEFAULT, defaultValue);
	}

}

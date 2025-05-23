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

import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Dataset;

/**
 * A sensor used to monitor an external condition
 * The condition itself is described in :ref:`NXenvironment`.
 *
 */
public interface NXsensor extends NXobject {

	public static final String NX_MODEL = "model";
	public static final String NX_NAME = "name";
	public static final String NX_SHORT_NAME = "short_name";
	public static final String NX_ATTACHED_TO = "attached_to";
	public static final String NX_MEASUREMENT = "measurement";
	public static final String NX_TYPE = "type";
	public static final String NX_RUN_CONTROL = "run_control";
	public static final String NX_HIGH_TRIP_VALUE = "high_trip_value";
	public static final String NX_LOW_TRIP_VALUE = "low_trip_value";
	public static final String NX_VALUE = "value";
	public static final String NX_VALUE_DERIV1 = "value_deriv1";
	public static final String NX_VALUE_DERIV2 = "value_deriv2";
	public static final String NX_EXTERNAL_FIELD_BRIEF = "external_field_brief";
	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	public static final String NX_DEPENDS_ON = "depends_on";
	/**
	 * Sensor identification code/model number
	 *
	 * @return  the value.
	 */
	public Dataset getModel();

	/**
	 * Sensor identification code/model number
	 *
	 * @param modelDataset the modelDataset
	 */
	public DataNode setModel(IDataset modelDataset);

	/**
	 * Sensor identification code/model number
	 *
	 * @return  the value.
	 */
	public String getModelScalar();

	/**
	 * Sensor identification code/model number
	 *
	 * @param model the model
	 */
	public DataNode setModelScalar(String modelValue);

	/**
	 * Name for the sensor
	 *
	 * @return  the value.
	 */
	public Dataset getName();

	/**
	 * Name for the sensor
	 *
	 * @param nameDataset the nameDataset
	 */
	public DataNode setName(IDataset nameDataset);

	/**
	 * Name for the sensor
	 *
	 * @return  the value.
	 */
	public String getNameScalar();

	/**
	 * Name for the sensor
	 *
	 * @param name the name
	 */
	public DataNode setNameScalar(String nameValue);

	/**
	 * Short name of sensor used e.g. on monitor display program
	 *
	 * @return  the value.
	 */
	public Dataset getShort_name();

	/**
	 * Short name of sensor used e.g. on monitor display program
	 *
	 * @param short_nameDataset the short_nameDataset
	 */
	public DataNode setShort_name(IDataset short_nameDataset);

	/**
	 * Short name of sensor used e.g. on monitor display program
	 *
	 * @return  the value.
	 */
	public String getShort_nameScalar();

	/**
	 * Short name of sensor used e.g. on monitor display program
	 *
	 * @param short_name the short_name
	 */
	public DataNode setShort_nameScalar(String short_nameValue);

	/**
	 * where sensor is attached to ("sample" | "can")
	 *
	 * @return  the value.
	 */
	public Dataset getAttached_to();

	/**
	 * where sensor is attached to ("sample" | "can")
	 *
	 * @param attached_toDataset the attached_toDataset
	 */
	public DataNode setAttached_to(IDataset attached_toDataset);

	/**
	 * where sensor is attached to ("sample" | "can")
	 *
	 * @return  the value.
	 */
	public String getAttached_toScalar();

	/**
	 * where sensor is attached to ("sample" | "can")
	 *
	 * @param attached_to the attached_to
	 */
	public DataNode setAttached_toScalar(String attached_toValue);

	/**
	 * Defines the axes for logged vector quantities if they are not the global instrument axes.
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the beamstop and NXoff_geometry to describe its shape instead
	 * @return  the value.
	 */
	@Deprecated
	public NXgeometry getGeometry();

	/**
	 * Defines the axes for logged vector quantities if they are not the global instrument axes.
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the beamstop and NXoff_geometry to describe its shape instead
	 * @param geometryGroup the geometryGroup
	 */
	@Deprecated
	public void setGeometry(NXgeometry geometryGroup);

	/**
	 * name for measured signal
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>temperature</b> </li>
	 * <li><b>pH</b> </li>
	 * <li><b>magnetic_field</b> </li>
	 * <li><b>electric_field</b> </li>
	 * <li><b>conductivity</b> </li>
	 * <li><b>resistance</b> </li>
	 * <li><b>voltage</b> </li>
	 * <li><b>pressure</b> </li>
	 * <li><b>flow</b> </li>
	 * <li><b>stress</b> </li>
	 * <li><b>strain</b> </li>
	 * <li><b>shear</b> </li>
	 * <li><b>surface_pressure</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getMeasurement();

	/**
	 * name for measured signal
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>temperature</b> </li>
	 * <li><b>pH</b> </li>
	 * <li><b>magnetic_field</b> </li>
	 * <li><b>electric_field</b> </li>
	 * <li><b>conductivity</b> </li>
	 * <li><b>resistance</b> </li>
	 * <li><b>voltage</b> </li>
	 * <li><b>pressure</b> </li>
	 * <li><b>flow</b> </li>
	 * <li><b>stress</b> </li>
	 * <li><b>strain</b> </li>
	 * <li><b>shear</b> </li>
	 * <li><b>surface_pressure</b> </li></ul></p>
	 * </p>
	 *
	 * @param measurementDataset the measurementDataset
	 */
	public DataNode setMeasurement(IDataset measurementDataset);

	/**
	 * name for measured signal
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>temperature</b> </li>
	 * <li><b>pH</b> </li>
	 * <li><b>magnetic_field</b> </li>
	 * <li><b>electric_field</b> </li>
	 * <li><b>conductivity</b> </li>
	 * <li><b>resistance</b> </li>
	 * <li><b>voltage</b> </li>
	 * <li><b>pressure</b> </li>
	 * <li><b>flow</b> </li>
	 * <li><b>stress</b> </li>
	 * <li><b>strain</b> </li>
	 * <li><b>shear</b> </li>
	 * <li><b>surface_pressure</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getMeasurementScalar();

	/**
	 * name for measured signal
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>temperature</b> </li>
	 * <li><b>pH</b> </li>
	 * <li><b>magnetic_field</b> </li>
	 * <li><b>electric_field</b> </li>
	 * <li><b>conductivity</b> </li>
	 * <li><b>resistance</b> </li>
	 * <li><b>voltage</b> </li>
	 * <li><b>pressure</b> </li>
	 * <li><b>flow</b> </li>
	 * <li><b>stress</b> </li>
	 * <li><b>strain</b> </li>
	 * <li><b>shear</b> </li>
	 * <li><b>surface_pressure</b> </li></ul></p>
	 * </p>
	 *
	 * @param measurement the measurement
	 */
	public DataNode setMeasurementScalar(String measurementValue);

	/**
	 * The type of hardware used for the measurement.
	 * Examples (suggestions but not restrictions):
	 * :Temperature:
	 * J | K | T | E | R | S | Pt100 | Rh/Fe
	 * :pH:
	 * Hg/Hg2Cl2 | Ag/AgCl | ISFET
	 * :Ion selective electrode:
	 * specify species; e.g. Ca2+
	 * :Magnetic field:
	 * Hall
	 * :Surface pressure:
	 * wilhelmy plate
	 *
	 * @return  the value.
	 */
	public Dataset getType();

	/**
	 * The type of hardware used for the measurement.
	 * Examples (suggestions but not restrictions):
	 * :Temperature:
	 * J | K | T | E | R | S | Pt100 | Rh/Fe
	 * :pH:
	 * Hg/Hg2Cl2 | Ag/AgCl | ISFET
	 * :Ion selective electrode:
	 * specify species; e.g. Ca2+
	 * :Magnetic field:
	 * Hall
	 * :Surface pressure:
	 * wilhelmy plate
	 *
	 * @param typeDataset the typeDataset
	 */
	public DataNode setType(IDataset typeDataset);

	/**
	 * The type of hardware used for the measurement.
	 * Examples (suggestions but not restrictions):
	 * :Temperature:
	 * J | K | T | E | R | S | Pt100 | Rh/Fe
	 * :pH:
	 * Hg/Hg2Cl2 | Ag/AgCl | ISFET
	 * :Ion selective electrode:
	 * specify species; e.g. Ca2+
	 * :Magnetic field:
	 * Hall
	 * :Surface pressure:
	 * wilhelmy plate
	 *
	 * @return  the value.
	 */
	public String getTypeScalar();

	/**
	 * The type of hardware used for the measurement.
	 * Examples (suggestions but not restrictions):
	 * :Temperature:
	 * J | K | T | E | R | S | Pt100 | Rh/Fe
	 * :pH:
	 * Hg/Hg2Cl2 | Ag/AgCl | ISFET
	 * :Ion selective electrode:
	 * specify species; e.g. Ca2+
	 * :Magnetic field:
	 * Hall
	 * :Surface pressure:
	 * wilhelmy plate
	 *
	 * @param type the type
	 */
	public DataNode setTypeScalar(String typeValue);

	/**
	 * Is data collection controlled or synchronised to this quantity:
	 * 1=no, 0=to "value", 1=to "value_deriv1", etc.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getRun_control();

	/**
	 * Is data collection controlled or synchronised to this quantity:
	 * 1=no, 0=to "value", 1=to "value_deriv1", etc.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param run_controlDataset the run_controlDataset
	 */
	public DataNode setRun_control(IDataset run_controlDataset);

	/**
	 * Is data collection controlled or synchronised to this quantity:
	 * 1=no, 0=to "value", 1=to "value_deriv1", etc.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Boolean getRun_controlScalar();

	/**
	 * Is data collection controlled or synchronised to this quantity:
	 * 1=no, 0=to "value", 1=to "value_deriv1", etc.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param run_control the run_control
	 */
	public DataNode setRun_controlScalar(Boolean run_controlValue);

	/**
	 * Upper control bound of sensor reading if using run_control
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getHigh_trip_value();

	/**
	 * Upper control bound of sensor reading if using run_control
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param high_trip_valueDataset the high_trip_valueDataset
	 */
	public DataNode setHigh_trip_value(IDataset high_trip_valueDataset);

	/**
	 * Upper control bound of sensor reading if using run_control
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getHigh_trip_valueScalar();

	/**
	 * Upper control bound of sensor reading if using run_control
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param high_trip_value the high_trip_value
	 */
	public DataNode setHigh_trip_valueScalar(Double high_trip_valueValue);

	/**
	 * Lower control bound of sensor reading if using run_control
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getLow_trip_value();

	/**
	 * Lower control bound of sensor reading if using run_control
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param low_trip_valueDataset the low_trip_valueDataset
	 */
	public DataNode setLow_trip_value(IDataset low_trip_valueDataset);

	/**
	 * Lower control bound of sensor reading if using run_control
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getLow_trip_valueScalar();

	/**
	 * Lower control bound of sensor reading if using run_control
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param low_trip_value the low_trip_value
	 */
	public DataNode setLow_trip_valueScalar(Double low_trip_valueValue);

	/**
	 * nominal setpoint or average value
	 * - need [n] as may be a vector
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getValue();

	/**
	 * nominal setpoint or average value
	 * - need [n] as may be a vector
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 *
	 * @param valueDataset the valueDataset
	 */
	public DataNode setValue(IDataset valueDataset);

	/**
	 * nominal setpoint or average value
	 * - need [n] as may be a vector
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getValueScalar();

	/**
	 * nominal setpoint or average value
	 * - need [n] as may be a vector
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 *
	 * @param value the value
	 */
	public DataNode setValueScalar(Double valueValue);

	/**
	 * Nominal/average first derivative of value
	 * e.g. strain rate
	 * - same dimensions as "value" (may be a vector)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: ;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getValue_deriv1();

	/**
	 * Nominal/average first derivative of value
	 * e.g. strain rate
	 * - same dimensions as "value" (may be a vector)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: ;
	 * </p>
	 *
	 * @param value_deriv1Dataset the value_deriv1Dataset
	 */
	public DataNode setValue_deriv1(IDataset value_deriv1Dataset);

	/**
	 * Nominal/average first derivative of value
	 * e.g. strain rate
	 * - same dimensions as "value" (may be a vector)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: ;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getValue_deriv1Scalar();

	/**
	 * Nominal/average first derivative of value
	 * e.g. strain rate
	 * - same dimensions as "value" (may be a vector)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: ;
	 * </p>
	 *
	 * @param value_deriv1 the value_deriv1
	 */
	public DataNode setValue_deriv1Scalar(Double value_deriv1Value);

	/**
	 * Nominal/average second derivative of value
	 * - same dimensions as "value" (may be a vector)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: ;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getValue_deriv2();

	/**
	 * Nominal/average second derivative of value
	 * - same dimensions as "value" (may be a vector)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: ;
	 * </p>
	 *
	 * @param value_deriv2Dataset the value_deriv2Dataset
	 */
	public DataNode setValue_deriv2(IDataset value_deriv2Dataset);

	/**
	 * Nominal/average second derivative of value
	 * - same dimensions as "value" (may be a vector)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: ;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getValue_deriv2Scalar();

	/**
	 * Nominal/average second derivative of value
	 * - same dimensions as "value" (may be a vector)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: ;
	 * </p>
	 *
	 * @param value_deriv2 the value_deriv2
	 */
	public DataNode setValue_deriv2Scalar(Double value_deriv2Value);

	/**
	 * Time history of sensor readings
	 *
	 * @return  the value.
	 */
	public NXlog getValue_log();

	/**
	 * Time history of sensor readings
	 *
	 * @param value_logGroup the value_logGroup
	 */
	public void setValue_log(NXlog value_logGroup);

	/**
	 * Time history of first derivative of sensor readings
	 *
	 * @return  the value.
	 */
	public NXlog getValue_deriv1_log();

	/**
	 * Time history of first derivative of sensor readings
	 *
	 * @param value_deriv1_logGroup the value_deriv1_logGroup
	 */
	public void setValue_deriv1_log(NXlog value_deriv1_logGroup);

	/**
	 * Time history of second derivative of sensor readings
	 *
	 * @return  the value.
	 */
	public NXlog getValue_deriv2_log();

	/**
	 * Time history of second derivative of sensor readings
	 *
	 * @param value_deriv2_logGroup the value_deriv2_logGroup
	 */
	public void setValue_deriv2_log(NXlog value_deriv2_logGroup);

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>along beam</b> </li>
	 * <li><b>across beam</b> </li>
	 * <li><b>transverse</b> </li>
	 * <li><b>solenoidal</b> </li>
	 * <li><b>flow shear gradient</b> </li>
	 * <li><b>flow vorticity</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getExternal_field_brief();

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>along beam</b> </li>
	 * <li><b>across beam</b> </li>
	 * <li><b>transverse</b> </li>
	 * <li><b>solenoidal</b> </li>
	 * <li><b>flow shear gradient</b> </li>
	 * <li><b>flow vorticity</b> </li></ul></p>
	 * </p>
	 *
	 * @param external_field_briefDataset the external_field_briefDataset
	 */
	public DataNode setExternal_field_brief(IDataset external_field_briefDataset);

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>along beam</b> </li>
	 * <li><b>across beam</b> </li>
	 * <li><b>transverse</b> </li>
	 * <li><b>solenoidal</b> </li>
	 * <li><b>flow shear gradient</b> </li>
	 * <li><b>flow vorticity</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getExternal_field_briefScalar();

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>along beam</b> </li>
	 * <li><b>across beam</b> </li>
	 * <li><b>transverse</b> </li>
	 * <li><b>solenoidal</b> </li>
	 * <li><b>flow shear gradient</b> </li>
	 * <li><b>flow vorticity</b> </li></ul></p>
	 * </p>
	 *
	 * @param external_field_brief the external_field_brief
	 */
	public DataNode setExternal_field_briefScalar(String external_field_briefValue);

	/**
	 * For complex external fields not satisfied by External_field_brief
	 *
	 * @return  the value.
	 */
	public NXorientation getExternal_field_full();

	/**
	 * For complex external fields not satisfied by External_field_brief
	 *
	 * @param external_field_fullGroup the external_field_fullGroup
	 */
	public void setExternal_field_full(NXorientation external_field_fullGroup);

	/**
	 * This group describes the shape of the sensor when necessary.
	 *
	 * @return  the value.
	 */
	public NXoff_geometry getOff_geometry();

	/**
	 * This group describes the shape of the sensor when necessary.
	 *
	 * @param off_geometryGroup the off_geometryGroup
	 */
	public void setOff_geometry(NXoff_geometry off_geometryGroup);

	/**
	 * Get a NXoff_geometry node by name:
	 * <ul>
	 * <li>
	 * This group describes the shape of the sensor when necessary.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXoff_geometry for that node.
	 */
	public NXoff_geometry getOff_geometry(String name);

	/**
	 * Set a NXoff_geometry node by name:
	 * <ul>
	 * <li>
	 * This group describes the shape of the sensor when necessary.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param off_geometry the value to set
	 */
	public void setOff_geometry(String name, NXoff_geometry off_geometry);

	/**
	 * Get all NXoff_geometry nodes:
	 * <ul>
	 * <li>
	 * This group describes the shape of the sensor when necessary.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXoff_geometry for that node.
	 */
	public Map<String, NXoff_geometry> getAllOff_geometry();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * This group describes the shape of the sensor when necessary.</li>
	 * </ul>
	 *
	 * @param off_geometry the child nodes to add
	 */

	public void setAllOff_geometry(Map<String, NXoff_geometry> off_geometry);


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

	/**
	 * NeXus positions components by applying a set of translations and rotations
	 * to apply to the component starting from 0, 0, 0. The order of these operations
	 * is critical and forms what NeXus calls a dependency chain. The depends_on
	 * field defines the path to the top most operation of the dependency chain or the
	 * string "." if located in the origin. Usually these operations are stored in a
	 * NXtransformations group. But NeXus allows them to be stored anywhere.
	 * .. todo::
	 * Add a definition for the reference point of a sensor.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDepends_on();

	/**
	 * NeXus positions components by applying a set of translations and rotations
	 * to apply to the component starting from 0, 0, 0. The order of these operations
	 * is critical and forms what NeXus calls a dependency chain. The depends_on
	 * field defines the path to the top most operation of the dependency chain or the
	 * string "." if located in the origin. Usually these operations are stored in a
	 * NXtransformations group. But NeXus allows them to be stored anywhere.
	 * .. todo::
	 * Add a definition for the reference point of a sensor.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param depends_onDataset the depends_onDataset
	 */
	public DataNode setDepends_on(IDataset depends_onDataset);

	/**
	 * NeXus positions components by applying a set of translations and rotations
	 * to apply to the component starting from 0, 0, 0. The order of these operations
	 * is critical and forms what NeXus calls a dependency chain. The depends_on
	 * field defines the path to the top most operation of the dependency chain or the
	 * string "." if located in the origin. Usually these operations are stored in a
	 * NXtransformations group. But NeXus allows them to be stored anywhere.
	 * .. todo::
	 * Add a definition for the reference point of a sensor.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getDepends_onScalar();

	/**
	 * NeXus positions components by applying a set of translations and rotations
	 * to apply to the component starting from 0, 0, 0. The order of these operations
	 * is critical and forms what NeXus calls a dependency chain. The depends_on
	 * field defines the path to the top most operation of the dependency chain or the
	 * string "." if located in the origin. Usually these operations are stored in a
	 * NXtransformations group. But NeXus allows them to be stored anywhere.
	 * .. todo::
	 * Add a definition for the reference point of a sensor.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param depends_on the depends_on
	 */
	public DataNode setDepends_onScalar(String depends_onValue);

	/**
	 * This is the group recommended for holding the chain of translation
	 * and rotation operations necessary to position the component within
	 * the instrument. The dependency chain may however traverse similar groups in
	 * other component groups.
	 *
	 * @return  the value.
	 */
	public NXtransformations getTransformations();

	/**
	 * This is the group recommended for holding the chain of translation
	 * and rotation operations necessary to position the component within
	 * the instrument. The dependency chain may however traverse similar groups in
	 * other component groups.
	 *
	 * @param transformationsGroup the transformationsGroup
	 */
	public void setTransformations(NXtransformations transformationsGroup);

	/**
	 * Get a NXtransformations node by name:
	 * <ul>
	 * <li>
	 * This is the group recommended for holding the chain of translation
	 * and rotation operations necessary to position the component within
	 * the instrument. The dependency chain may however traverse similar groups in
	 * other component groups.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXtransformations for that node.
	 */
	public NXtransformations getTransformations(String name);

	/**
	 * Set a NXtransformations node by name:
	 * <ul>
	 * <li>
	 * This is the group recommended for holding the chain of translation
	 * and rotation operations necessary to position the component within
	 * the instrument. The dependency chain may however traverse similar groups in
	 * other component groups.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param transformations the value to set
	 */
	public void setTransformations(String name, NXtransformations transformations);

	/**
	 * Get all NXtransformations nodes:
	 * <ul>
	 * <li>
	 * This is the group recommended for holding the chain of translation
	 * and rotation operations necessary to position the component within
	 * the instrument. The dependency chain may however traverse similar groups in
	 * other component groups.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXtransformations for that node.
	 */
	public Map<String, NXtransformations> getAllTransformations();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * This is the group recommended for holding the chain of translation
	 * and rotation operations necessary to position the component within
	 * the instrument. The dependency chain may however traverse similar groups in
	 * other component groups.</li>
	 * </ul>
	 *
	 * @param transformations the child nodes to add
	 */

	public void setAllTransformations(Map<String, NXtransformations> transformations);


}

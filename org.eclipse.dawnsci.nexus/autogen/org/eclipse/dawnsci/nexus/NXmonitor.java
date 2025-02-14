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
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Dataset;

/**
 * A monitor of incident beam data.
 * It is similar to the :ref:`NXdata` groups containing
 * monitor data and its associated axis coordinates, e.g. time_of_flight or
 * wavelength in pulsed neutron instruments. However, it may also include
 * integrals, or scalar monitor counts, which are often used in both in both
 * pulsed and steady-state instrumentation.
 *
 */
public interface NXmonitor extends NXobject {

	public static final String NX_MODE = "mode";
	public static final String NX_START_TIME = "start_time";
	public static final String NX_END_TIME = "end_time";
	public static final String NX_PRESET = "preset";
	public static final String NX_DISTANCE = "distance";
	public static final String NX_RANGE = "range";
	public static final String NX_NOMINAL = "nominal";
	public static final String NX_INTEGRAL = "integral";
	public static final String NX_TYPE = "type";
	public static final String NX_TIME_OF_FLIGHT = "time_of_flight";
	public static final String NX_EFFICIENCY = "efficiency";
	public static final String NX_DATA = "data";
	public static final String NX_SAMPLED_FRACTION = "sampled_fraction";
	public static final String NX_COUNT_TIME = "count_time";
	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	public static final String NX_DEPENDS_ON = "depends_on";
	/**
	 * Count to a preset value based on either clock time (timer)
	 * or received monitor counts (monitor).
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>monitor</b> </li>
	 * <li><b>timer</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getMode();

	/**
	 * Count to a preset value based on either clock time (timer)
	 * or received monitor counts (monitor).
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>monitor</b> </li>
	 * <li><b>timer</b> </li></ul></p>
	 * </p>
	 *
	 * @param modeDataset the modeDataset
	 */
	public DataNode setMode(IDataset modeDataset);

	/**
	 * Count to a preset value based on either clock time (timer)
	 * or received monitor counts (monitor).
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>monitor</b> </li>
	 * <li><b>timer</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getModeScalar();

	/**
	 * Count to a preset value based on either clock time (timer)
	 * or received monitor counts (monitor).
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>monitor</b> </li>
	 * <li><b>timer</b> </li></ul></p>
	 * </p>
	 *
	 * @param mode the mode
	 */
	public DataNode setModeScalar(String modeValue);

	/**
	 * Starting time of measurement
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getStart_time();

	/**
	 * Starting time of measurement
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @param start_timeDataset the start_timeDataset
	 */
	public DataNode setStart_time(IDataset start_timeDataset);

	/**
	 * Starting time of measurement
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Date getStart_timeScalar();

	/**
	 * Starting time of measurement
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @param start_time the start_time
	 */
	public DataNode setStart_timeScalar(Date start_timeValue);

	/**
	 * Ending time of measurement
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getEnd_time();

	/**
	 * Ending time of measurement
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @param end_timeDataset the end_timeDataset
	 */
	public DataNode setEnd_time(IDataset end_timeDataset);

	/**
	 * Ending time of measurement
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Date getEnd_timeScalar();

	/**
	 * Ending time of measurement
	 * <p>
	 * <b>Type:</b> NX_DATE_TIME
	 * </p>
	 *
	 * @param end_time the end_time
	 */
	public DataNode setEnd_timeScalar(Date end_timeValue);

	/**
	 * preset value for time or monitor
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getPreset();

	/**
	 * preset value for time or monitor
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param presetDataset the presetDataset
	 */
	public DataNode setPreset(IDataset presetDataset);

	/**
	 * preset value for time or monitor
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getPresetScalar();

	/**
	 * preset value for time or monitor
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param preset the preset
	 */
	public DataNode setPresetScalar(Number presetValue);

	/**
	 * Distance of monitor from sample
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @deprecated Use transformations/distance instead
	 * @return  the value.
	 */
	@Deprecated
	public Dataset getDistance();

	/**
	 * Distance of monitor from sample
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @deprecated Use transformations/distance instead
	 * @param distanceDataset the distanceDataset
	 */
	@Deprecated
	public DataNode setDistance(IDataset distanceDataset);

	/**
	 * Distance of monitor from sample
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @deprecated Use transformations/distance instead
	 * @return  the value.
	 */
	@Deprecated
	public Double getDistanceScalar();

	/**
	 * Distance of monitor from sample
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @deprecated Use transformations/distance instead
	 * @param distance the distance
	 */
	@Deprecated
	public DataNode setDistanceScalar(Double distanceValue);

	/**
	 * Range (X-axis, Time-of-flight, etc.) over which the integral was calculated
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getRange();

	/**
	 * Range (X-axis, Time-of-flight, etc.) over which the integral was calculated
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 *
	 * @param rangeDataset the rangeDataset
	 */
	public DataNode setRange(IDataset rangeDataset);

	/**
	 * Range (X-axis, Time-of-flight, etc.) over which the integral was calculated
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getRangeScalar();

	/**
	 * Range (X-axis, Time-of-flight, etc.) over which the integral was calculated
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: 2;
	 * </p>
	 *
	 * @param range the range
	 */
	public DataNode setRangeScalar(Double rangeValue);

	/**
	 * Nominal reading to be used for normalisation purposes.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getNominal();

	/**
	 * Nominal reading to be used for normalisation purposes.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param nominalDataset the nominalDataset
	 */
	public DataNode setNominal(IDataset nominalDataset);

	/**
	 * Nominal reading to be used for normalisation purposes.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getNominalScalar();

	/**
	 * Nominal reading to be used for normalisation purposes.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param nominal the nominal
	 */
	public DataNode setNominalScalar(Number nominalValue);

	/**
	 * Total integral monitor counts
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIntegral();

	/**
	 * Total integral monitor counts
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param integralDataset the integralDataset
	 */
	public DataNode setIntegral(IDataset integralDataset);

	/**
	 * Total integral monitor counts
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getIntegralScalar();

	/**
	 * Total integral monitor counts
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param integral the integral
	 */
	public DataNode setIntegralScalar(Number integralValue);

	/**
	 * Time variation of monitor counts
	 *
	 * @return  the value.
	 */
	public NXlog getIntegral_log();

	/**
	 * Time variation of monitor counts
	 *
	 * @param integral_logGroup the integral_logGroup
	 */
	public void setIntegral_log(NXlog integral_logGroup);

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>Fission Chamber</b> </li>
	 * <li><b>Scintillator</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getType();

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>Fission Chamber</b> </li>
	 * <li><b>Scintillator</b> </li></ul></p>
	 * </p>
	 *
	 * @param typeDataset the typeDataset
	 */
	public DataNode setType(IDataset typeDataset);

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>Fission Chamber</b> </li>
	 * <li><b>Scintillator</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getTypeScalar();

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>Fission Chamber</b> </li>
	 * <li><b>Scintillator</b> </li></ul></p>
	 * </p>
	 *
	 * @param type the type
	 */
	public DataNode setTypeScalar(String typeValue);

	/**
	 * Time-of-flight
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME_OF_FLIGHT
	 * <b>Dimensions:</b> 1: ;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getTime_of_flight();

	/**
	 * Time-of-flight
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME_OF_FLIGHT
	 * <b>Dimensions:</b> 1: ;
	 * </p>
	 *
	 * @param time_of_flightDataset the time_of_flightDataset
	 */
	public DataNode setTime_of_flight(IDataset time_of_flightDataset);

	/**
	 * Time-of-flight
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME_OF_FLIGHT
	 * <b>Dimensions:</b> 1: ;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getTime_of_flightScalar();

	/**
	 * Time-of-flight
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME_OF_FLIGHT
	 * <b>Dimensions:</b> 1: ;
	 * </p>
	 *
	 * @param time_of_flight the time_of_flight
	 */
	public DataNode setTime_of_flightScalar(Double time_of_flightValue);

	/**
	 * Monitor efficiency
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: ;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getEfficiency();

	/**
	 * Monitor efficiency
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: ;
	 * </p>
	 *
	 * @param efficiencyDataset the efficiencyDataset
	 */
	public DataNode setEfficiency(IDataset efficiencyDataset);

	/**
	 * Monitor efficiency
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: ;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getEfficiencyScalar();

	/**
	 * Monitor efficiency
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: ;
	 * </p>
	 *
	 * @param efficiency the efficiency
	 */
	public DataNode setEfficiencyScalar(Number efficiencyValue);

	/**
	 * Monitor data
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getData();

	/**
	 * Monitor data
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b>
	 * </p>
	 *
	 * @param dataDataset the dataDataset
	 */
	public DataNode setData(IDataset dataDataset);

	/**
	 * Monitor data
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getDataScalar();

	/**
	 * Monitor data
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b>
	 * </p>
	 *
	 * @param data the data
	 */
	public DataNode setDataScalar(Number dataValue);

	/**
	 * Proportion of incident beam sampled by the monitor (0<x<1)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSampled_fraction();

	/**
	 * Proportion of incident beam sampled by the monitor (0<x<1)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * </p>
	 *
	 * @param sampled_fractionDataset the sampled_fractionDataset
	 */
	public DataNode setSampled_fraction(IDataset sampled_fractionDataset);

	/**
	 * Proportion of incident beam sampled by the monitor (0<x<1)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getSampled_fractionScalar();

	/**
	 * Proportion of incident beam sampled by the monitor (0<x<1)
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * </p>
	 *
	 * @param sampled_fraction the sampled_fraction
	 */
	public DataNode setSampled_fractionScalar(Double sampled_fractionValue);

	/**
	 * Geometry of the monitor
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the monitor and NXoff_geometry to describe its shape instead
	 * @return  the value.
	 */
	@Deprecated
	public NXgeometry getGeometry();

	/**
	 * Geometry of the monitor
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the monitor and NXoff_geometry to describe its shape instead
	 * @param geometryGroup the geometryGroup
	 */
	@Deprecated
	public void setGeometry(NXgeometry geometryGroup);

	/**
	 * Get a NXgeometry node by name:
	 * <ul>
	 * <li>
	 * Geometry of the monitor</li>
	 * </ul>
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the monitor and NXoff_geometry to describe its shape instead
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXgeometry for that node.
	 */
	@Deprecated
	public NXgeometry getGeometry(String name);

	/**
	 * Set a NXgeometry node by name:
	 * <ul>
	 * <li>
	 * Geometry of the monitor</li>
	 * </ul>
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the monitor and NXoff_geometry to describe its shape instead
	 * @param name the name of the node
	 * @param geometry the value to set
	 */
	@Deprecated
	public void setGeometry(String name, NXgeometry geometry);

	/**
	 * Get all NXgeometry nodes:
	 * <ul>
	 * <li>
	 * Geometry of the monitor</li>
	 * </ul>
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the monitor and NXoff_geometry to describe its shape instead
	 * @return  a map from node names to the NXgeometry for that node.
	 */
	@Deprecated
	public Map<String, NXgeometry> getAllGeometry();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Geometry of the monitor</li>
	 * </ul>
	 *
	 * @deprecated Use the field `depends_on` and :ref:`NXtransformations` to position the monitor and NXoff_geometry to describe its shape instead
	 * @param geometry the child nodes to add
	 */

	@Deprecated
	public void setAllGeometry(Map<String, NXgeometry> geometry);


	/**
	 * Elapsed actual counting time, can be an array of size ``np``
	 * when scanning. This is not the difference of the calendar time
	 * but the time the instrument was really counting, without
	 * pauses or times lost due beam unavailability
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getCount_time();

	/**
	 * Elapsed actual counting time, can be an array of size ``np``
	 * when scanning. This is not the difference of the calendar time
	 * but the time the instrument was really counting, without
	 * pauses or times lost due beam unavailability
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @param count_timeDataset the count_timeDataset
	 */
	public DataNode setCount_time(IDataset count_timeDataset);

	/**
	 * Elapsed actual counting time, can be an array of size ``np``
	 * when scanning. This is not the difference of the calendar time
	 * but the time the instrument was really counting, without
	 * pauses or times lost due beam unavailability
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getCount_timeScalar();

	/**
	 * Elapsed actual counting time, can be an array of size ``np``
	 * when scanning. This is not the difference of the calendar time
	 * but the time the instrument was really counting, without
	 * pauses or times lost due beam unavailability
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @param count_time the count_time
	 */
	public DataNode setCount_timeScalar(Double count_timeValue);

	/**
	 * This group describes the shape of the beam line component
	 *
	 * @return  the value.
	 */
	public NXoff_geometry getOff_geometry();

	/**
	 * This group describes the shape of the beam line component
	 *
	 * @param off_geometryGroup the off_geometryGroup
	 */
	public void setOff_geometry(NXoff_geometry off_geometryGroup);

	/**
	 * Get a NXoff_geometry node by name:
	 * <ul>
	 * <li>
	 * This group describes the shape of the beam line component</li>
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
	 * This group describes the shape of the beam line component</li>
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
	 * This group describes the shape of the beam line component</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXoff_geometry for that node.
	 */
	public Map<String, NXoff_geometry> getAllOff_geometry();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * This group describes the shape of the beam line component</li>
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
	 * The reference plane of the monitor contains the surface of the detector that faces the source
	 * and is the entry point of the beam. The reference point of the monitor in the x and y axis is
	 * its centre on this surface. The reference plane is orthogonal to the the z axis and the
	 * reference point on this z axis is where they intersect.
	 * .. image:: monitor/monitor.png
	 * :width: 40%
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
	 * The reference plane of the monitor contains the surface of the detector that faces the source
	 * and is the entry point of the beam. The reference point of the monitor in the x and y axis is
	 * its centre on this surface. The reference plane is orthogonal to the the z axis and the
	 * reference point on this z axis is where they intersect.
	 * .. image:: monitor/monitor.png
	 * :width: 40%
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
	 * The reference plane of the monitor contains the surface of the detector that faces the source
	 * and is the entry point of the beam. The reference point of the monitor in the x and y axis is
	 * its centre on this surface. The reference plane is orthogonal to the the z axis and the
	 * reference point on this z axis is where they intersect.
	 * .. image:: monitor/monitor.png
	 * :width: 40%
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
	 * The reference plane of the monitor contains the surface of the detector that faces the source
	 * and is the entry point of the beam. The reference point of the monitor in the x and y axis is
	 * its centre on this surface. The reference plane is orthogonal to the the z axis and the
	 * reference point on this z axis is where they intersect.
	 * .. image:: monitor/monitor.png
	 * :width: 40%
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

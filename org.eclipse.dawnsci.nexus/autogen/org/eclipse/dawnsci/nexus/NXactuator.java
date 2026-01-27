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
 * An actuator used to control an external condition.
 * The condition itself is described in :ref:`NXenvironment`.
 *
 */
public interface NXactuator extends NXcomponent {

	public static final String NX_SHORT_NAME = "short_name";
	public static final String NX_ACTUATION_TARGET = "actuation_target";
	public static final String NX_PHYSICAL_QUANTITY = "physical_quantity";
	public static final String NX_TYPE = "type";
	public static final String NX_OUTPUTVALUE = "outputvalue";
	/**
	 * Name of the actuator
	 *
	 * @return  the value.
	 */
	public Dataset getName();

	/**
	 * Name of the actuator
	 *
	 * @param nameDataset the nameDataset
	 */
	public DataNode setName(IDataset nameDataset);

	/**
	 * Name of the actuator
	 *
	 * @return  the value.
	 */
	public String getNameScalar();

	/**
	 * Name of the actuator
	 *
	 * @param name the name
	 */
	public DataNode setNameScalar(String nameValue);

	/**
	 * Short name of actuator used e.g. on monitor display program
	 *
	 * @return  the value.
	 */
	public Dataset getShort_name();

	/**
	 * Short name of actuator used e.g. on monitor display program
	 *
	 * @param short_nameDataset the short_nameDataset
	 */
	public DataNode setShort_name(IDataset short_nameDataset);

	/**
	 * Short name of actuator used e.g. on monitor display program
	 *
	 * @return  the value.
	 */
	public String getShort_nameScalar();

	/**
	 * Short name of actuator used e.g. on monitor display program
	 *
	 * @param short_name the short_name
	 */
	public DataNode setShort_nameScalar(String short_nameValue);

	/**
	 * The physical component on which this actuator acts.
	 * This should be a path in the NeXus tree structure.
	 * For example, this could be an instance of NXsample or a device on NXinstrument.
	 *
	 * @return  the value.
	 */
	public Dataset getActuation_target();

	/**
	 * The physical component on which this actuator acts.
	 * This should be a path in the NeXus tree structure.
	 * For example, this could be an instance of NXsample or a device on NXinstrument.
	 *
	 * @param actuation_targetDataset the actuation_targetDataset
	 */
	public DataNode setActuation_target(IDataset actuation_targetDataset);

	/**
	 * The physical component on which this actuator acts.
	 * This should be a path in the NeXus tree structure.
	 * For example, this could be an instance of NXsample or a device on NXinstrument.
	 *
	 * @return  the value.
	 */
	public String getActuation_targetScalar();

	/**
	 * The physical component on which this actuator acts.
	 * This should be a path in the NeXus tree structure.
	 * For example, this could be an instance of NXsample or a device on NXinstrument.
	 *
	 * @param actuation_target the actuation_target
	 */
	public DataNode setActuation_targetScalar(String actuation_targetValue);

	/**
	 * Name for the physical quantity effected by the actuation
	 * Examples:
	 * temperature | pH | magnetic_field | electric_field | current | conductivity | resistance | voltage |
	 * pressure | flow | stress | strain | shear | surface_pressure
	 *
	 * @return  the value.
	 */
	public Dataset getPhysical_quantity();

	/**
	 * Name for the physical quantity effected by the actuation
	 * Examples:
	 * temperature | pH | magnetic_field | electric_field | current | conductivity | resistance | voltage |
	 * pressure | flow | stress | strain | shear | surface_pressure
	 *
	 * @param physical_quantityDataset the physical_quantityDataset
	 */
	public DataNode setPhysical_quantity(IDataset physical_quantityDataset);

	/**
	 * Name for the physical quantity effected by the actuation
	 * Examples:
	 * temperature | pH | magnetic_field | electric_field | current | conductivity | resistance | voltage |
	 * pressure | flow | stress | strain | shear | surface_pressure
	 *
	 * @return  the value.
	 */
	public String getPhysical_quantityScalar();

	/**
	 * Name for the physical quantity effected by the actuation
	 * Examples:
	 * temperature | pH | magnetic_field | electric_field | current | conductivity | resistance | voltage |
	 * pressure | flow | stress | strain | shear | surface_pressure
	 *
	 * @param physical_quantity the physical_quantity
	 */
	public DataNode setPhysical_quantityScalar(String physical_quantityValue);

	/**
	 * The type of hardware used for the actuation.
	 * Examples (suggestions, but not restrictions):
	 * :Temperature: laser | gas lamp | filament | resistive
	 * :Pressure: anvil cell
	 * :Voltage: potentiostat
	 *
	 * @return  the value.
	 */
	public Dataset getType();

	/**
	 * The type of hardware used for the actuation.
	 * Examples (suggestions, but not restrictions):
	 * :Temperature: laser | gas lamp | filament | resistive
	 * :Pressure: anvil cell
	 * :Voltage: potentiostat
	 *
	 * @param typeDataset the typeDataset
	 */
	public DataNode setType(IDataset typeDataset);

	/**
	 * The type of hardware used for the actuation.
	 * Examples (suggestions, but not restrictions):
	 * :Temperature: laser | gas lamp | filament | resistive
	 * :Pressure: anvil cell
	 * :Voltage: potentiostat
	 *
	 * @return  the value.
	 */
	public String getTypeScalar();

	/**
	 * The type of hardware used for the actuation.
	 * Examples (suggestions, but not restrictions):
	 * :Temperature: laser | gas lamp | filament | resistive
	 * :Pressure: anvil cell
	 * :Voltage: potentiostat
	 *
	 * @param type the type
	 */
	public DataNode setTypeScalar(String typeValue);

	/**
	 * Any output that the actuator produces.
	 * For example, a heater can have the field output_power(NX_NUMBER).
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getOutputvalue();

	/**
	 * Any output that the actuator produces.
	 * For example, a heater can have the field output_power(NX_NUMBER).
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param outputvalueDataset the outputvalueDataset
	 */
	public DataNode setOutputvalue(IDataset outputvalueDataset);

	/**
	 * Any output that the actuator produces.
	 * For example, a heater can have the field output_power(NX_NUMBER).
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getOutputvalueScalar();

	/**
	 * Any output that the actuator produces.
	 * For example, a heater can have the field output_power(NX_NUMBER).
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param outputvalue the outputvalue
	 */
	public DataNode setOutputvalueScalar(Number outputvalueValue);

	/**
	 * If the actuator is PID-controlled, the settings of the PID controller can be
	 * stored here.
	 *
	 * @return  the value.
	 */
	public NXpid_controller getPid_controller();

	/**
	 * If the actuator is PID-controlled, the settings of the PID controller can be
	 * stored here.
	 *
	 * @param pid_controllerGroup the pid_controllerGroup
	 */
	public void setPid_controller(NXpid_controller pid_controllerGroup);

	/**
	 * Get a NXpid_controller node by name:
	 * <ul>
	 * <li>
	 * If the actuator is PID-controlled, the settings of the PID controller can be
	 * stored here.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXpid_controller for that node.
	 */
	public NXpid_controller getPid_controller(String name);

	/**
	 * Set a NXpid_controller node by name:
	 * <ul>
	 * <li>
	 * If the actuator is PID-controlled, the settings of the PID controller can be
	 * stored here.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param pid_controller the value to set
	 */
	public void setPid_controller(String name, NXpid_controller pid_controller);

	/**
	 * Get all NXpid_controller nodes:
	 * <ul>
	 * <li>
	 * If the actuator is PID-controlled, the settings of the PID controller can be
	 * stored here.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXpid_controller for that node.
	 */
	public Map<String, NXpid_controller> getAllPid_controller();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * If the actuator is PID-controlled, the settings of the PID controller can be
	 * stored here.</li>
	 * </ul>
	 *
	 * @param pid_controller the child nodes to add
	 */

	public void setAllPid_controller(Map<String, NXpid_controller> pid_controller);


}

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
 * Base class to describe the use of manipulators and sample stages.
 *
 */
public interface NXmanipulator extends NXcomponent {

	public static final String NX_TYPE = "type";
	/**
	 * Name of the manipulator.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getName();

	/**
	 * Name of the manipulator.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param nameDataset the nameDataset
	 */
	public DataNode setName(IDataset nameDataset);

	/**
	 * Name of the manipulator.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getNameScalar();

	/**
	 * Name of the manipulator.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param name the name
	 */
	public DataNode setNameScalar(String nameValue);

	/**
	 * A description of the manipulator.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDescription();

	/**
	 * A description of the manipulator.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param descriptionDataset the descriptionDataset
	 */
	public DataNode setDescription(IDataset descriptionDataset);

	/**
	 * A description of the manipulator.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getDescriptionScalar();

	/**
	 * A description of the manipulator.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param description the description
	 */
	public DataNode setDescriptionScalar(String descriptionValue);

	/**
	 * Type of manipulator, Hexapod, Rod, etc.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getType();

	/**
	 * Type of manipulator, Hexapod, Rod, etc.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param typeDataset the typeDataset
	 */
	public DataNode setType(IDataset typeDataset);

	/**
	 * Type of manipulator, Hexapod, Rod, etc.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getTypeScalar();

	/**
	 * Type of manipulator, Hexapod, Rod, etc.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param type the type
	 */
	public DataNode setTypeScalar(String typeValue);

	/**
	 * Cryostat for cooling the sample (and, potentially, the whole manipulator).
	 *
	 * @return  the value.
	 */
	public NXactuator getCryostat();

	/**
	 * Cryostat for cooling the sample (and, potentially, the whole manipulator).
	 *
	 * @param cryostatGroup the cryostatGroup
	 */
	public void setCryostat(NXactuator cryostatGroup);
	// Unprocessed group:

	/**
	 * Temperature sensor measuring the sample temperature.
	 *
	 * @return  the value.
	 */
	public NXsensor getTemperature_sensor();

	/**
	 * Temperature sensor measuring the sample temperature.
	 *
	 * @param temperature_sensorGroup the temperature_sensorGroup
	 */
	public void setTemperature_sensor(NXsensor temperature_sensorGroup);
	// Unprocessed group:value_log

	/**
	 * Device to heat the sample.
	 *
	 * @return  the value.
	 */
	public NXactuator getSample_heater();

	/**
	 * Device to heat the sample.
	 *
	 * @param sample_heaterGroup the sample_heaterGroup
	 */
	public void setSample_heater(NXactuator sample_heaterGroup);
	// Unprocessed group:output_heater_power_log
	// Unprocessed group:

	/**
	 * Ammeter measuring the drain current of the sample and sample holder.
	 *
	 * @return  the value.
	 */
	public NXsensor getDrain_current_ammeter();

	/**
	 * Ammeter measuring the drain current of the sample and sample holder.
	 *
	 * @param drain_current_ammeterGroup the drain_current_ammeterGroup
	 */
	public void setDrain_current_ammeter(NXsensor drain_current_ammeterGroup);
	// Unprocessed group:value_log

	/**
	 * Actuator applying a voltage between sample holder and sample.
	 *
	 * @return  the value.
	 */
	public NXactuator getSample_bias_potentiostat();

	/**
	 * Actuator applying a voltage between sample holder and sample.
	 *
	 * @param sample_bias_potentiostatGroup the sample_bias_potentiostatGroup
	 */
	public void setSample_bias_potentiostat(NXactuator sample_bias_potentiostatGroup);
	// Unprocessed group:

	/**
	 * Sensor measuring the voltage applied to sample and sample holder.
	 *
	 * @return  the value.
	 */
	public NXsensor getSample_bias_voltmeter();

	/**
	 * Sensor measuring the voltage applied to sample and sample holder.
	 *
	 * @param sample_bias_voltmeterGroup the sample_bias_voltmeterGroup
	 */
	public void setSample_bias_voltmeter(NXsensor sample_bias_voltmeterGroup);
	// Unprocessed group:value_log

	/**
	 * Any additional actuator on the manipulator used to control an external
	 * condition.
	 *
	 * @return  the value.
	 */
	public NXactuator getActuator();

	/**
	 * Any additional actuator on the manipulator used to control an external
	 * condition.
	 *
	 * @param actuatorGroup the actuatorGroup
	 */
	public void setActuator(NXactuator actuatorGroup);

	/**
	 * Get a NXactuator node by name:
	 * <ul>
	 * <li>
	 * Cryostat for cooling the sample (and, potentially, the whole manipulator).</li>
	 * <li>
	 * Device to heat the sample.</li>
	 * <li>
	 * Actuator applying a voltage between sample holder and sample.</li>
	 * <li>
	 * Any additional actuator on the manipulator used to control an external
	 * condition.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXactuator for that node.
	 */
	public NXactuator getActuator(String name);

	/**
	 * Set a NXactuator node by name:
	 * <ul>
	 * <li>
	 * Cryostat for cooling the sample (and, potentially, the whole manipulator).</li>
	 * <li>
	 * Device to heat the sample.</li>
	 * <li>
	 * Actuator applying a voltage between sample holder and sample.</li>
	 * <li>
	 * Any additional actuator on the manipulator used to control an external
	 * condition.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param actuator the value to set
	 */
	public void setActuator(String name, NXactuator actuator);

	/**
	 * Get all NXactuator nodes:
	 * <ul>
	 * <li>
	 * Cryostat for cooling the sample (and, potentially, the whole manipulator).</li>
	 * <li>
	 * Device to heat the sample.</li>
	 * <li>
	 * Actuator applying a voltage between sample holder and sample.</li>
	 * <li>
	 * Any additional actuator on the manipulator used to control an external
	 * condition.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXactuator for that node.
	 */
	public Map<String, NXactuator> getAllActuator();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Cryostat for cooling the sample (and, potentially, the whole manipulator).</li>
	 * <li>
	 * Device to heat the sample.</li>
	 * <li>
	 * Actuator applying a voltage between sample holder and sample.</li>
	 * <li>
	 * Any additional actuator on the manipulator used to control an external
	 * condition.</li>
	 * </ul>
	 *
	 * @param actuator the child nodes to add
	 */

	public void setAllActuator(Map<String, NXactuator> actuator);


	/**
	 * Any additional sensors on the manipulator used to monitor an external condition.
	 *
	 * @return  the value.
	 */
	public NXsensor getSensor();

	/**
	 * Any additional sensors on the manipulator used to monitor an external condition.
	 *
	 * @param sensorGroup the sensorGroup
	 */
	public void setSensor(NXsensor sensorGroup);

	/**
	 * Get a NXsensor node by name:
	 * <ul>
	 * <li>
	 * Temperature sensor measuring the sample temperature.</li>
	 * <li>
	 * Ammeter measuring the drain current of the sample and sample holder.</li>
	 * <li>
	 * Sensor measuring the voltage applied to sample and sample holder.</li>
	 * <li>
	 * Any additional sensors on the manipulator used to monitor an external condition.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXsensor for that node.
	 */
	public NXsensor getSensor(String name);

	/**
	 * Set a NXsensor node by name:
	 * <ul>
	 * <li>
	 * Temperature sensor measuring the sample temperature.</li>
	 * <li>
	 * Ammeter measuring the drain current of the sample and sample holder.</li>
	 * <li>
	 * Sensor measuring the voltage applied to sample and sample holder.</li>
	 * <li>
	 * Any additional sensors on the manipulator used to monitor an external condition.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param sensor the value to set
	 */
	public void setSensor(String name, NXsensor sensor);

	/**
	 * Get all NXsensor nodes:
	 * <ul>
	 * <li>
	 * Temperature sensor measuring the sample temperature.</li>
	 * <li>
	 * Ammeter measuring the drain current of the sample and sample holder.</li>
	 * <li>
	 * Sensor measuring the voltage applied to sample and sample holder.</li>
	 * <li>
	 * Any additional sensors on the manipulator used to monitor an external condition.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXsensor for that node.
	 */
	public Map<String, NXsensor> getAllSensor();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Temperature sensor measuring the sample temperature.</li>
	 * <li>
	 * Ammeter measuring the drain current of the sample and sample holder.</li>
	 * <li>
	 * Sensor measuring the voltage applied to sample and sample holder.</li>
	 * <li>
	 * Any additional sensors on the manipulator used to monitor an external condition.</li>
	 * </ul>
	 *
	 * @param sensor the child nodes to add
	 */

	public void setAllSensor(Map<String, NXsensor> sensor);


	/**
	 * Class to describe the motors that are used in the manipulator.
	 *
	 * @return  the value.
	 */
	public NXpositioner getPositioner();

	/**
	 * Class to describe the motors that are used in the manipulator.
	 *
	 * @param positionerGroup the positionerGroup
	 */
	public void setPositioner(NXpositioner positionerGroup);

	/**
	 * Get a NXpositioner node by name:
	 * <ul>
	 * <li>
	 * Class to describe the motors that are used in the manipulator.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXpositioner for that node.
	 */
	public NXpositioner getPositioner(String name);

	/**
	 * Set a NXpositioner node by name:
	 * <ul>
	 * <li>
	 * Class to describe the motors that are used in the manipulator.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param positioner the value to set
	 */
	public void setPositioner(String name, NXpositioner positioner);

	/**
	 * Get all NXpositioner nodes:
	 * <ul>
	 * <li>
	 * Class to describe the motors that are used in the manipulator.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXpositioner for that node.
	 */
	public Map<String, NXpositioner> getAllPositioner();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Class to describe the motors that are used in the manipulator.</li>
	 * </ul>
	 *
	 * @param positioner the child nodes to add
	 */

	public void setAllPositioner(Map<String, NXpositioner> positioner);


}

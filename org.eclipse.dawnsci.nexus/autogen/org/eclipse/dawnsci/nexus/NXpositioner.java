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

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Dataset;

/**
 * A generic positioner such as a motor or piezo-electric transducer.
 *
 */
public interface NXpositioner extends NXcomponent {

	public static final String NX_VALUE = "value";
	public static final String NX_RAW_VALUE = "raw_value";
	public static final String NX_TARGET_VALUE = "target_value";
	public static final String NX_TOLERANCE = "tolerance";
	public static final String NX_SOFT_LIMIT_MIN = "soft_limit_min";
	public static final String NX_SOFT_LIMIT_MAX = "soft_limit_max";
	public static final String NX_VELOCITY = "velocity";
	public static final String NX_ACCELERATION_TIME = "acceleration_time";
	public static final String NX_CONTROLLER_RECORD = "controller_record";
	/**
	 * symbolic or mnemonic name (one word)
	 *
	 * @return  the value.
	 */
	public Dataset getName();

	/**
	 * symbolic or mnemonic name (one word)
	 *
	 * @param nameDataset the nameDataset
	 */
	public DataNode setName(IDataset nameDataset);

	/**
	 * symbolic or mnemonic name (one word)
	 *
	 * @return  the value.
	 */
	public String getNameScalar();

	/**
	 * symbolic or mnemonic name (one word)
	 *
	 * @param name the name
	 */
	public DataNode setNameScalar(String nameValue);

	/**
	 * description of positioner
	 *
	 * @return  the value.
	 */
	public Dataset getDescription();

	/**
	 * description of positioner
	 *
	 * @param descriptionDataset the descriptionDataset
	 */
	public DataNode setDescription(IDataset descriptionDataset);

	/**
	 * description of positioner
	 *
	 * @return  the value.
	 */
	public String getDescriptionScalar();

	/**
	 * description of positioner
	 *
	 * @param description the description
	 */
	public DataNode setDescriptionScalar(String descriptionValue);

	/**
	 * best known value of positioner - need [n] as may be scanned
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getValue();

	/**
	 * best known value of positioner - need [n] as may be scanned
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 *
	 * @param valueDataset the valueDataset
	 */
	public DataNode setValue(IDataset valueDataset);

	/**
	 * best known value of positioner - need [n] as may be scanned
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getValueScalar();

	/**
	 * best known value of positioner - need [n] as may be scanned
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 *
	 * @param value the value
	 */
	public DataNode setValueScalar(Number valueValue);

	/**
	 * raw value of positioner - need [n] as may be scanned
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getRaw_value();

	/**
	 * raw value of positioner - need [n] as may be scanned
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 *
	 * @param raw_valueDataset the raw_valueDataset
	 */
	public DataNode setRaw_value(IDataset raw_valueDataset);

	/**
	 * raw value of positioner - need [n] as may be scanned
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getRaw_valueScalar();

	/**
	 * raw value of positioner - need [n] as may be scanned
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 *
	 * @param raw_value the raw_value
	 */
	public DataNode setRaw_valueScalar(Number raw_valueValue);

	/**
	 * targeted (commanded) value of positioner - need [n] as may be scanned
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getTarget_value();

	/**
	 * targeted (commanded) value of positioner - need [n] as may be scanned
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 *
	 * @param target_valueDataset the target_valueDataset
	 */
	public DataNode setTarget_value(IDataset target_valueDataset);

	/**
	 * targeted (commanded) value of positioner - need [n] as may be scanned
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getTarget_valueScalar();

	/**
	 * targeted (commanded) value of positioner - need [n] as may be scanned
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 *
	 * @param target_value the target_value
	 */
	public DataNode setTarget_valueScalar(Number target_valueValue);

	/**
	 * maximum allowable difference between target_value and value
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getTolerance();

	/**
	 * maximum allowable difference between target_value and value
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 *
	 * @param toleranceDataset the toleranceDataset
	 */
	public DataNode setTolerance(IDataset toleranceDataset);

	/**
	 * maximum allowable difference between target_value and value
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getToleranceScalar();

	/**
	 * maximum allowable difference between target_value and value
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 *
	 * @param tolerance the tolerance
	 */
	public DataNode setToleranceScalar(Number toleranceValue);

	/**
	 * minimum allowed limit to set value
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSoft_limit_min();

	/**
	 * minimum allowed limit to set value
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param soft_limit_minDataset the soft_limit_minDataset
	 */
	public DataNode setSoft_limit_min(IDataset soft_limit_minDataset);

	/**
	 * minimum allowed limit to set value
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getSoft_limit_minScalar();

	/**
	 * minimum allowed limit to set value
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param soft_limit_min the soft_limit_min
	 */
	public DataNode setSoft_limit_minScalar(Number soft_limit_minValue);

	/**
	 * maximum allowed limit to set value
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSoft_limit_max();

	/**
	 * maximum allowed limit to set value
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param soft_limit_maxDataset the soft_limit_maxDataset
	 */
	public DataNode setSoft_limit_max(IDataset soft_limit_maxDataset);

	/**
	 * maximum allowed limit to set value
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getSoft_limit_maxScalar();

	/**
	 * maximum allowed limit to set value
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param soft_limit_max the soft_limit_max
	 */
	public DataNode setSoft_limit_maxScalar(Number soft_limit_maxValue);

	/**
	 * velocity of the positioner (distance moved per unit time)
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getVelocity();

	/**
	 * velocity of the positioner (distance moved per unit time)
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param velocityDataset the velocityDataset
	 */
	public DataNode setVelocity(IDataset velocityDataset);

	/**
	 * velocity of the positioner (distance moved per unit time)
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getVelocityScalar();

	/**
	 * velocity of the positioner (distance moved per unit time)
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param velocity the velocity
	 */
	public DataNode setVelocityScalar(Number velocityValue);

	/**
	 * time to ramp the velocity up to full speed
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getAcceleration_time();

	/**
	 * time to ramp the velocity up to full speed
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param acceleration_timeDataset the acceleration_timeDataset
	 */
	public DataNode setAcceleration_time(IDataset acceleration_timeDataset);

	/**
	 * time to ramp the velocity up to full speed
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getAcceleration_timeScalar();

	/**
	 * time to ramp the velocity up to full speed
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param acceleration_time the acceleration_time
	 */
	public DataNode setAcceleration_timeScalar(Number acceleration_timeValue);

	/**
	 * Hardware device record, e.g. EPICS process variable, taco/tango ...
	 *
	 * @return  the value.
	 */
	public Dataset getController_record();

	/**
	 * Hardware device record, e.g. EPICS process variable, taco/tango ...
	 *
	 * @param controller_recordDataset the controller_recordDataset
	 */
	public DataNode setController_record(IDataset controller_recordDataset);

	/**
	 * Hardware device record, e.g. EPICS process variable, taco/tango ...
	 *
	 * @return  the value.
	 */
	public String getController_recordScalar();

	/**
	 * Hardware device record, e.g. EPICS process variable, taco/tango ...
	 *
	 * @param controller_record the controller_record
	 */
	public DataNode setController_recordScalar(String controller_recordValue);

	/**
	 * .. todo::
	 * Add a definition for the reference point of a positioner.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDepends_on();

	/**
	 * .. todo::
	 * Add a definition for the reference point of a positioner.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param depends_onDataset the depends_onDataset
	 */
	public DataNode setDepends_on(IDataset depends_onDataset);

	/**
	 * .. todo::
	 * Add a definition for the reference point of a positioner.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getDepends_onScalar();

	/**
	 * .. todo::
	 * Add a definition for the reference point of a positioner.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param depends_on the depends_on
	 */
	public DataNode setDepends_onScalar(String depends_onValue);

}

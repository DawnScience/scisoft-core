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

/**
 * definition for a electrostatic kicker.
 * 
 */
public interface NXelectrostatic_kicker extends NXobject {

	public static final String NX_DESCRIPTION = "description";
	public static final String NX_BEAMLINE_DISTANCE = "beamline_distance";
	public static final String NX_TIMING = "timing";
	public static final String NX_TIMING_ATTRIBUTE_DESCRIPTION = "description";
	public static final String NX_SET_CURRENT = "set_current";
	public static final String NX_SET_VOLTAGE = "set_voltage";
	/**
	 * extended description of the kicker.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getDescription();
	
	/**
	 * extended description of the kicker.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param descriptionDataset the descriptionDataset
	 */
	public DataNode setDescription(IDataset descriptionDataset);

	/**
	 * extended description of the kicker.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getDescriptionScalar();

	/**
	 * extended description of the kicker.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param description the description
	 */
	public DataNode setDescriptionScalar(String descriptionValue);

	/**
	 * define position of beamline element relative to production target
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getBeamline_distance();
	
	/**
	 * define position of beamline element relative to production target
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param beamline_distanceDataset the beamline_distanceDataset
	 */
	public DataNode setBeamline_distance(IDataset beamline_distanceDataset);

	/**
	 * define position of beamline element relative to production target
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getBeamline_distanceScalar();

	/**
	 * define position of beamline element relative to production target
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param beamline_distance the beamline_distance
	 */
	public DataNode setBeamline_distanceScalar(Double beamline_distanceValue);

	/**
	 * kicker timing as defined by ``description`` attribute
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getTiming();
	
	/**
	 * kicker timing as defined by ``description`` attribute
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 * 
	 * @param timingDataset the timingDataset
	 */
	public DataNode setTiming(IDataset timingDataset);

	/**
	 * kicker timing as defined by ``description`` attribute
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getTimingScalar();

	/**
	 * kicker timing as defined by ``description`` attribute
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * </p>
	 * 
	 * @param timing the timing
	 */
	public DataNode setTimingScalar(Double timingValue);

	/**
	 * 
	 * @return  the value.
	 */
	public String getTimingAttributeDescription();
	
	/**
	 * 
	 * @param descriptionValue the descriptionValue
	 */
	public void setTimingAttributeDescription(String descriptionValue);

	/**
	 * current set on supply.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getSet_current();
	
	/**
	 * current set on supply.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 * 
	 * @param set_currentDataset the set_currentDataset
	 */
	public DataNode setSet_current(IDataset set_currentDataset);

	/**
	 * current set on supply.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getSet_currentScalar();

	/**
	 * current set on supply.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 * 
	 * @param set_current the set_current
	 */
	public DataNode setSet_currentScalar(Double set_currentValue);

	/**
	 * current read from supply.
	 * 
	 * @return  the value.
	 */
	public NXlog getRead_current();
	
	/**
	 * current read from supply.
	 * 
	 * @param read_currentGroup the read_currentGroup
	 */
	public void setRead_current(NXlog read_currentGroup);

	/**
	 * volage set on supply.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getSet_voltage();
	
	/**
	 * volage set on supply.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * </p>
	 * 
	 * @param set_voltageDataset the set_voltageDataset
	 */
	public DataNode setSet_voltage(IDataset set_voltageDataset);

	/**
	 * volage set on supply.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getSet_voltageScalar();

	/**
	 * volage set on supply.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * </p>
	 * 
	 * @param set_voltage the set_voltage
	 */
	public DataNode setSet_voltageScalar(Double set_voltageValue);

	/**
	 * voltage read from supply.
	 * 
	 * @return  the value.
	 */
	public NXlog getRead_voltage();
	
	/**
	 * voltage read from supply.
	 * 
	 * @param read_voltageGroup the read_voltageGroup
	 */
	public void setRead_voltage(NXlog read_voltageGroup);

}

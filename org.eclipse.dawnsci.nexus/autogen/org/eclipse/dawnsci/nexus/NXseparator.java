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
 * definition for an electrostatic separator.
 * 
 */
public interface NXseparator extends NXobject {

	public static final String NX_DESCRIPTION = "description";
	public static final String NX_BEAMLINE_DISTANCE = "beamline_distance";
	public static final String NX_SET_BFIELD_CURRENT = "set_bfield_current";
	public static final String NX_SET_EFIELD_VOLTAGE = "set_efield_voltage";
	/**
	 * extended description of the separator.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getDescription();
	
	/**
	 * extended description of the separator.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param descriptionDataset the descriptionDataset
	 */
	public DataNode setDescription(IDataset descriptionDataset);

	/**
	 * extended description of the separator.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getDescriptionScalar();

	/**
	 * extended description of the separator.
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
	 * current set on magnet supply.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getSet_bfield_current();
	
	/**
	 * current set on magnet supply.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 * 
	 * @param set_bfield_currentDataset the set_bfield_currentDataset
	 */
	public DataNode setSet_bfield_current(IDataset set_bfield_currentDataset);

	/**
	 * current set on magnet supply.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getSet_bfield_currentScalar();

	/**
	 * current set on magnet supply.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 * 
	 * @param set_bfield_current the set_bfield_current
	 */
	public DataNode setSet_bfield_currentScalar(Double set_bfield_currentValue);

	/**
	 * current read from magnet supply.
	 * 
	 * @return  the value.
	 */
	public NXlog getRead_bfield_current();
	
	/**
	 * current read from magnet supply.
	 * 
	 * @param read_bfield_currentGroup the read_bfield_currentGroup
	 */
	public void setRead_bfield_current(NXlog read_bfield_currentGroup);

	/**
	 * voltage read from magnet supply.
	 * 
	 * @return  the value.
	 */
	public NXlog getRead_bfield_voltage();
	
	/**
	 * voltage read from magnet supply.
	 * 
	 * @param read_bfield_voltageGroup the read_bfield_voltageGroup
	 */
	public void setRead_bfield_voltage(NXlog read_bfield_voltageGroup);

	/**
	 * current set on HT supply.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getSet_efield_voltage();
	
	/**
	 * current set on HT supply.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * </p>
	 * 
	 * @param set_efield_voltageDataset the set_efield_voltageDataset
	 */
	public DataNode setSet_efield_voltage(IDataset set_efield_voltageDataset);

	/**
	 * current set on HT supply.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getSet_efield_voltageScalar();

	/**
	 * current set on HT supply.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * </p>
	 * 
	 * @param set_efield_voltage the set_efield_voltage
	 */
	public DataNode setSet_efield_voltageScalar(Double set_efield_voltageValue);

	/**
	 * current read from HT supply.
	 * 
	 * @return  the value.
	 */
	public NXlog getRead_efield_current();
	
	/**
	 * current read from HT supply.
	 * 
	 * @param read_efield_currentGroup the read_efield_currentGroup
	 */
	public void setRead_efield_current(NXlog read_efield_currentGroup);

	/**
	 * voltage read from HT supply.
	 * 
	 * @return  the value.
	 */
	public NXlog getRead_efield_voltage();
	
	/**
	 * voltage read from HT supply.
	 * 
	 * @param read_efield_voltageGroup the read_efield_voltageGroup
	 */
	public void setRead_efield_voltage(NXlog read_efield_voltageGroup);

}

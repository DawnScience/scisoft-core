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
 * Base class for a quadrupole magnet.
 *
 */
public interface NXquadrupole_magnet extends NXcomponent {

	public static final String NX_BEAMLINE_DISTANCE = "beamline_distance";
	public static final String NX_SET_CURRENT = "set_current";
	/**
	 * Extended description of the magnet.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDescription();

	/**
	 * Extended description of the magnet.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param descriptionDataset the descriptionDataset
	 */
	public DataNode setDescription(IDataset descriptionDataset);

	/**
	 * Extended description of the magnet.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getDescriptionScalar();

	/**
	 * Extended description of the magnet.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param description the description
	 */
	public DataNode setDescriptionScalar(String descriptionValue);

	/**
	 * Define position of beamline element relative to production target
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getBeamline_distance();

	/**
	 * Define position of beamline element relative to production target
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param beamline_distanceDataset the beamline_distanceDataset
	 */
	public DataNode setBeamline_distance(IDataset beamline_distanceDataset);

	/**
	 * Define position of beamline element relative to production target
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getBeamline_distanceScalar();

	/**
	 * Define position of beamline element relative to production target
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param beamline_distance the beamline_distance
	 */
	public DataNode setBeamline_distanceScalar(Double beamline_distanceValue);

	/**
	 * Current set on supply.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSet_current();

	/**
	 * Current set on supply.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 *
	 * @param set_currentDataset the set_currentDataset
	 */
	public DataNode setSet_current(IDataset set_currentDataset);

	/**
	 * Current set on supply.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getSet_currentScalar();

	/**
	 * Current set on supply.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 *
	 * @param set_current the set_current
	 */
	public DataNode setSet_currentScalar(Double set_currentValue);

	/**
	 * Current read from supply.
	 *
	 * @return  the value.
	 */
	public NXlog getRead_current();

	/**
	 * Current read from supply.
	 *
	 * @param read_currentGroup the read_currentGroup
	 */
	public void setRead_current(NXlog read_currentGroup);

	/**
	 * Voltage read from supply.
	 *
	 * @return  the value.
	 */
	public NXlog getRead_voltage();

	/**
	 * Voltage read from supply.
	 *
	 * @param read_voltageGroup the read_voltageGroup
	 */
	public void setRead_voltage(NXlog read_voltageGroup);

}

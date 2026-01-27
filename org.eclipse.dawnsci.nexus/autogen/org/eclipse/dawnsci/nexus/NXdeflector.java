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
 * Component of an electron analyzer that deflects the paths of electrons. This includes electrostatic and electromagnetic deflectors.
 *
 */
public interface NXdeflector extends NXcomponent {

	public static final String NX_TYPE = "type";
	public static final String NX_VOLTAGE = "voltage";
	public static final String NX_CURRENT = "current";
	public static final String NX_OFFSET_X = "offset_x";
	public static final String NX_OFFSET_Y = "offset_y";
	/**
	 * Qualitative type of deflector with respect to the number of pole pieces.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>dipole</b> </li>
	 * <li><b>quadrupole</b> </li>
	 * <li><b>hexapole</b> </li>
	 * <li><b>octupole</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getType();

	/**
	 * Qualitative type of deflector with respect to the number of pole pieces.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>dipole</b> </li>
	 * <li><b>quadrupole</b> </li>
	 * <li><b>hexapole</b> </li>
	 * <li><b>octupole</b> </li></ul></p>
	 * </p>
	 *
	 * @param typeDataset the typeDataset
	 */
	public DataNode setType(IDataset typeDataset);

	/**
	 * Qualitative type of deflector with respect to the number of pole pieces.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>dipole</b> </li>
	 * <li><b>quadrupole</b> </li>
	 * <li><b>hexapole</b> </li>
	 * <li><b>octupole</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getTypeScalar();

	/**
	 * Qualitative type of deflector with respect to the number of pole pieces.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>dipole</b> </li>
	 * <li><b>quadrupole</b> </li>
	 * <li><b>hexapole</b> </li>
	 * <li><b>octupole</b> </li></ul></p>
	 * </p>
	 *
	 * @param type the type
	 */
	public DataNode setTypeScalar(String typeValue);

	/**
	 * Colloquial or short name for the deflector. For manufacturer names and
	 * identifiers use ``NXfabrication`` and ``identifierNAME``.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getName();

	/**
	 * Colloquial or short name for the deflector. For manufacturer names and
	 * identifiers use ``NXfabrication`` and ``identifierNAME``.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param nameDataset the nameDataset
	 */
	public DataNode setName(IDataset nameDataset);

	/**
	 * Colloquial or short name for the deflector. For manufacturer names and
	 * identifiers use ``NXfabrication`` and ``identifierNAME``.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getNameScalar();

	/**
	 * Colloquial or short name for the deflector. For manufacturer names and
	 * identifiers use ``NXfabrication`` and ``identifierNAME``.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param name the name
	 */
	public DataNode setNameScalar(String nameValue);

	/**
	 * Excitation voltage of the deflector. For dipoles it is a single number.
	 * For higher order multipoles, it is an array.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_VOLTAGE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getVoltage();

	/**
	 * Excitation voltage of the deflector. For dipoles it is a single number.
	 * For higher order multipoles, it is an array.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_VOLTAGE
	 * </p>
	 *
	 * @param voltageDataset the voltageDataset
	 */
	public DataNode setVoltage(IDataset voltageDataset);

	/**
	 * Excitation voltage of the deflector. For dipoles it is a single number.
	 * For higher order multipoles, it is an array.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_VOLTAGE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getVoltageScalar();

	/**
	 * Excitation voltage of the deflector. For dipoles it is a single number.
	 * For higher order multipoles, it is an array.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_VOLTAGE
	 * </p>
	 *
	 * @param voltage the voltage
	 */
	public DataNode setVoltageScalar(Number voltageValue);

	/**
	 * Excitation current of the deflector. For dipoles it is a single number. For
	 * higher orders, it is an array.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getCurrent();

	/**
	 * Excitation current of the deflector. For dipoles it is a single number. For
	 * higher orders, it is an array.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 *
	 * @param currentDataset the currentDataset
	 */
	public DataNode setCurrent(IDataset currentDataset);

	/**
	 * Excitation current of the deflector. For dipoles it is a single number. For
	 * higher orders, it is an array.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getCurrentScalar();

	/**
	 * Excitation current of the deflector. For dipoles it is a single number. For
	 * higher orders, it is an array.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 *
	 * @param current the current
	 */
	public DataNode setCurrentScalar(Number currentValue);

	/**
	 * Spatial offset of the deflector in x direction (perpendicular to
	 * ```offset_y```).
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getOffset_x();

	/**
	 * Spatial offset of the deflector in x direction (perpendicular to
	 * ```offset_y```).
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param offset_xDataset the offset_xDataset
	 */
	public DataNode setOffset_x(IDataset offset_xDataset);

	/**
	 * Spatial offset of the deflector in x direction (perpendicular to
	 * ```offset_y```).
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getOffset_xScalar();

	/**
	 * Spatial offset of the deflector in x direction (perpendicular to
	 * ```offset_y```).
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param offset_x the offset_x
	 */
	public DataNode setOffset_xScalar(Number offset_xValue);

	/**
	 * Spatial offset of the deflector in y direction (perpendicular to
	 * ```offset_x```).
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getOffset_y();

	/**
	 * Spatial offset of the deflector in y direction (perpendicular to
	 * ```offset_x```).
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param offset_yDataset the offset_yDataset
	 */
	public DataNode setOffset_y(IDataset offset_yDataset);

	/**
	 * Spatial offset of the deflector in y direction (perpendicular to
	 * ```offset_x```).
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getOffset_yScalar();

	/**
	 * Spatial offset of the deflector in y direction (perpendicular to
	 * ```offset_x```).
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param offset_y the offset_y
	 */
	public DataNode setOffset_yScalar(Number offset_yValue);

}

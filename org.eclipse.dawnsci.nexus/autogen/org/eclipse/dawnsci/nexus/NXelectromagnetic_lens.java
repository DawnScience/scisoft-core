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
 * Base class for an electro-magnetic lens or a compound lens.
 * For :ref:`NXtransformations` the origin of the coordinate system is placed
 * in the center of the lens its pole piece, pinhole, or another point of reference.
 * The origin should be specified in the :ref:`NXtransformations`.
 * For details of electro-magnetic lenses in the literature see e.g.
 * * `L. Reimer: Scanning Electron Microscopy <https://doi.org/10.1007/978-3-540-38967-5>`_
 * * `P. Hawkes: Magnetic Electron Lenses <https://link.springer.com/book/10.1007/978-3-642-81516-4>`_
 * * `Y. Liao: Practical Electron Microscopy and Database <https://www.globalsino.com/EM/>`_
 *
 */
public interface NXelectromagnetic_lens extends NXcomponent {

	public static final String NX_POWER_SETTING = "power_setting";
	public static final String NX_MODE = "mode";
	public static final String NX_VOLTAGE = "voltage";
	public static final String NX_CURRENT = "current";
	public static final String NX_TYPE = "type";
	public static final String NX_NUMBER_OF_POLES = "number_of_poles";
	/**
	 * Name of the lens.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getName();

	/**
	 * Name of the lens.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param nameDataset the nameDataset
	 */
	public DataNode setName(IDataset nameDataset);

	/**
	 * Name of the lens.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getNameScalar();

	/**
	 * Name of the lens.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param name the name
	 */
	public DataNode setNameScalar(String nameValue);

	/**
	 * Ideally, use instances of ``identifierNAME`` to point to a resource
	 * that provides further details.
	 * If such a resource does not exist or should not be used, use this free text,
	 * although it is not recommended.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDescription();

	/**
	 * Ideally, use instances of ``identifierNAME`` to point to a resource
	 * that provides further details.
	 * If such a resource does not exist or should not be used, use this free text,
	 * although it is not recommended.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param descriptionDataset the descriptionDataset
	 */
	public DataNode setDescription(IDataset descriptionDataset);

	/**
	 * Ideally, use instances of ``identifierNAME`` to point to a resource
	 * that provides further details.
	 * If such a resource does not exist or should not be used, use this free text,
	 * although it is not recommended.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getDescriptionScalar();

	/**
	 * Ideally, use instances of ``identifierNAME`` to point to a resource
	 * that provides further details.
	 * If such a resource does not exist or should not be used, use this free text,
	 * although it is not recommended.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param description the description
	 */
	public DataNode setDescriptionScalar(String descriptionValue);

	/**
	 * Descriptor for the lens excitation when the exact technical details
	 * are unknown or not directly controllable as the control software of
	 * the microscope does not enable or was not configured to display these
	 * values for users.
	 * Although this value does not document the exact physical voltage or
	 * excitation, it can still give useful context to reproduce the lens
	 * setting, provided a properly working instrument and software sets the lens
	 * into a similar state to the technical level possible when no more
	 * information is available physically or accessible legally.
	 * <p>
	 * <b>Type:</b> NX_CHAR_OR_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getPower_setting();

	/**
	 * Descriptor for the lens excitation when the exact technical details
	 * are unknown or not directly controllable as the control software of
	 * the microscope does not enable or was not configured to display these
	 * values for users.
	 * Although this value does not document the exact physical voltage or
	 * excitation, it can still give useful context to reproduce the lens
	 * setting, provided a properly working instrument and software sets the lens
	 * into a similar state to the technical level possible when no more
	 * information is available physically or accessible legally.
	 * <p>
	 * <b>Type:</b> NX_CHAR_OR_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param power_settingDataset the power_settingDataset
	 */
	public DataNode setPower_setting(IDataset power_settingDataset);

	/**
	 * Descriptor for the lens excitation when the exact technical details
	 * are unknown or not directly controllable as the control software of
	 * the microscope does not enable or was not configured to display these
	 * values for users.
	 * Although this value does not document the exact physical voltage or
	 * excitation, it can still give useful context to reproduce the lens
	 * setting, provided a properly working instrument and software sets the lens
	 * into a similar state to the technical level possible when no more
	 * information is available physically or accessible legally.
	 * <p>
	 * <b>Type:</b> NX_CHAR_OR_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Object getPower_settingScalar();

	/**
	 * Descriptor for the lens excitation when the exact technical details
	 * are unknown or not directly controllable as the control software of
	 * the microscope does not enable or was not configured to display these
	 * values for users.
	 * Although this value does not document the exact physical voltage or
	 * excitation, it can still give useful context to reproduce the lens
	 * setting, provided a properly working instrument and software sets the lens
	 * into a similar state to the technical level possible when no more
	 * information is available physically or accessible legally.
	 * <p>
	 * <b>Type:</b> NX_CHAR_OR_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param power_setting the power_setting
	 */
	public DataNode setPower_settingScalar(Object power_settingValue);

	/**
	 * Descriptor for the operation mode of the lens when other details are not
	 * directly controllable as the control software of the microscope
	 * does not enable or is not configured to display these values.
	 * Like value, the mode can only be interpreted for a specific microscope
	 * but can still be useful to guide users as to how to repeat the measurement.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getMode();

	/**
	 * Descriptor for the operation mode of the lens when other details are not
	 * directly controllable as the control software of the microscope
	 * does not enable or is not configured to display these values.
	 * Like value, the mode can only be interpreted for a specific microscope
	 * but can still be useful to guide users as to how to repeat the measurement.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param modeDataset the modeDataset
	 */
	public DataNode setMode(IDataset modeDataset);

	/**
	 * Descriptor for the operation mode of the lens when other details are not
	 * directly controllable as the control software of the microscope
	 * does not enable or is not configured to display these values.
	 * Like value, the mode can only be interpreted for a specific microscope
	 * but can still be useful to guide users as to how to repeat the measurement.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getModeScalar();

	/**
	 * Descriptor for the operation mode of the lens when other details are not
	 * directly controllable as the control software of the microscope
	 * does not enable or is not configured to display these values.
	 * Like value, the mode can only be interpreted for a specific microscope
	 * but can still be useful to guide users as to how to repeat the measurement.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param mode the mode
	 */
	public DataNode setModeScalar(String modeValue);

	/**
	 * Excitation voltage of the lens.
	 * For dipoles it is a single number.
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
	 * Excitation voltage of the lens.
	 * For dipoles it is a single number.
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
	 * Excitation voltage of the lens.
	 * For dipoles it is a single number.
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
	 * Excitation voltage of the lens.
	 * For dipoles it is a single number.
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
	 * Excitation current of the lens.
	 * For dipoles it is a single number.
	 * For higher-order multipoles, it is an array.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getCurrent();

	/**
	 * Excitation current of the lens.
	 * For dipoles it is a single number.
	 * For higher-order multipoles, it is an array.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 *
	 * @param currentDataset the currentDataset
	 */
	public DataNode setCurrent(IDataset currentDataset);

	/**
	 * Excitation current of the lens.
	 * For dipoles it is a single number.
	 * For higher-order multipoles, it is an array.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getCurrentScalar();

	/**
	 * Excitation current of the lens.
	 * For dipoles it is a single number.
	 * For higher-order multipoles, it is an array.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 *
	 * @param current the current
	 */
	public DataNode setCurrentScalar(Number currentValue);

	/**
	 * Qualitative type of lens with respect to the number of pole pieces.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>single</b> </li>
	 * <li><b>double</b> </li>
	 * <li><b>quadrupole</b> </li>
	 * <li><b>hexapole</b> </li>
	 * <li><b>octupole</b> </li>
	 * <li><b>dodecapole</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getType();

	/**
	 * Qualitative type of lens with respect to the number of pole pieces.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>single</b> </li>
	 * <li><b>double</b> </li>
	 * <li><b>quadrupole</b> </li>
	 * <li><b>hexapole</b> </li>
	 * <li><b>octupole</b> </li>
	 * <li><b>dodecapole</b> </li></ul></p>
	 * </p>
	 *
	 * @param typeDataset the typeDataset
	 */
	public DataNode setType(IDataset typeDataset);

	/**
	 * Qualitative type of lens with respect to the number of pole pieces.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>single</b> </li>
	 * <li><b>double</b> </li>
	 * <li><b>quadrupole</b> </li>
	 * <li><b>hexapole</b> </li>
	 * <li><b>octupole</b> </li>
	 * <li><b>dodecapole</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getTypeScalar();

	/**
	 * Qualitative type of lens with respect to the number of pole pieces.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>single</b> </li>
	 * <li><b>double</b> </li>
	 * <li><b>quadrupole</b> </li>
	 * <li><b>hexapole</b> </li>
	 * <li><b>octupole</b> </li>
	 * <li><b>dodecapole</b> </li></ul></p>
	 * </p>
	 *
	 * @param type the type
	 */
	public DataNode setTypeScalar(String typeValue);

	/**
	 * Qualitative description of the lens based on the number of pole pieces.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getNumber_of_poles();

	/**
	 * Qualitative description of the lens based on the number of pole pieces.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_polesDataset the number_of_polesDataset
	 */
	public DataNode setNumber_of_poles(IDataset number_of_polesDataset);

	/**
	 * Qualitative description of the lens based on the number of pole pieces.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getNumber_of_polesScalar();

	/**
	 * Qualitative description of the lens based on the number of pole pieces.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_poles the number_of_poles
	 */
	public DataNode setNumber_of_polesScalar(Long number_of_polesValue);

}

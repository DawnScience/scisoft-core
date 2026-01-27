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
 * A subclass of NXdetector for detectors that detect electrons.
 *
 */
public interface NXelectron_detector extends NXdetector {

	public static final String NX_AMPLIFIER_TYPE = "amplifier_type";
	public static final String NX_DETECTOR_TYPE = "detector_type";
	public static final String NX_DETECTOR_VOLTAGE = "detector_voltage";
	public static final String NX_AMPLIFIER_VOLTAGE = "amplifier_voltage";
	public static final String NX_AMPLIFIER_BIAS = "amplifier_bias";
	/**
	 * Type of electron amplifier, MCP, channeltron, etc.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getAmplifier_type();

	/**
	 * Type of electron amplifier, MCP, channeltron, etc.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param amplifier_typeDataset the amplifier_typeDataset
	 */
	public DataNode setAmplifier_type(IDataset amplifier_typeDataset);

	/**
	 * Type of electron amplifier, MCP, channeltron, etc.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getAmplifier_typeScalar();

	/**
	 * Type of electron amplifier, MCP, channeltron, etc.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param amplifier_type the amplifier_type
	 */
	public DataNode setAmplifier_typeScalar(String amplifier_typeValue);

	/**
	 * Description of the electron detector type, DLD, Phosphor+CCD, CMOS.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDetector_type();

	/**
	 * Description of the electron detector type, DLD, Phosphor+CCD, CMOS.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param detector_typeDataset the detector_typeDataset
	 */
	public DataNode setDetector_type(IDataset detector_typeDataset);

	/**
	 * Description of the electron detector type, DLD, Phosphor+CCD, CMOS.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getDetector_typeScalar();

	/**
	 * Description of the electron detector type, DLD, Phosphor+CCD, CMOS.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param detector_type the detector_type
	 */
	public DataNode setDetector_typeScalar(String detector_typeValue);

	/**
	 * Voltage applied to the electron detector.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDetector_voltage();

	/**
	 * Voltage applied to the electron detector.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * </p>
	 *
	 * @param detector_voltageDataset the detector_voltageDataset
	 */
	public DataNode setDetector_voltage(IDataset detector_voltageDataset);

	/**
	 * Voltage applied to the electron detector.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getDetector_voltageScalar();

	/**
	 * Voltage applied to the electron detector.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * </p>
	 *
	 * @param detector_voltage the detector_voltage
	 */
	public DataNode setDetector_voltageScalar(Double detector_voltageValue);

	/**
	 * Voltage applied to the amplifier.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getAmplifier_voltage();

	/**
	 * Voltage applied to the amplifier.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * </p>
	 *
	 * @param amplifier_voltageDataset the amplifier_voltageDataset
	 */
	public DataNode setAmplifier_voltage(IDataset amplifier_voltageDataset);

	/**
	 * Voltage applied to the amplifier.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getAmplifier_voltageScalar();

	/**
	 * Voltage applied to the amplifier.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * </p>
	 *
	 * @param amplifier_voltage the amplifier_voltage
	 */
	public DataNode setAmplifier_voltageScalar(Double amplifier_voltageValue);

	/**
	 * The low voltage of the amplifier might not be the ground.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getAmplifier_bias();

	/**
	 * The low voltage of the amplifier might not be the ground.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * </p>
	 *
	 * @param amplifier_biasDataset the amplifier_biasDataset
	 */
	public DataNode setAmplifier_bias(IDataset amplifier_biasDataset);

	/**
	 * The low voltage of the amplifier might not be the ground.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getAmplifier_biasScalar();

	/**
	 * The low voltage of the amplifier might not be the ground.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * </p>
	 *
	 * @param amplifier_bias the amplifier_bias
	 */
	public DataNode setAmplifier_biasScalar(Double amplifier_biasValue);

}

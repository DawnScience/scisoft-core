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

/**
 * Metadata for laser-, voltage-, or combined pulsing triggering field evaporation.
 * <p><b>Symbols:</b> 
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>n_ions</b> 
 * Total number of ions collected.</li></ul></p>
 * 
 */
public interface NXpulser_apm extends NXobject {

	public static final String NX_PULSE_MODE = "pulse_mode";
	public static final String NX_PULSE_FREQUENCY = "pulse_frequency";
	public static final String NX_PULSE_FRACTION = "pulse_fraction";
	public static final String NX_PULSED_VOLTAGE = "pulsed_voltage";
	public static final String NX_STANDING_VOLTAGE = "standing_voltage";
	/**
	 * How is field evaporation physically triggered.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>laser</b> </li>
	 * <li><b>high_voltage</b> </li>
	 * <li><b>laser_and_high_voltage</b> </li></ul></p>
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getPulse_mode();
	
	/**
	 * How is field evaporation physically triggered.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>laser</b> </li>
	 * <li><b>high_voltage</b> </li>
	 * <li><b>laser_and_high_voltage</b> </li></ul></p>
	 * </p>
	 * 
	 * @param pulse_modeDataset the pulse_modeDataset
	 */
	public DataNode setPulse_mode(IDataset pulse_modeDataset);

	/**
	 * How is field evaporation physically triggered.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>laser</b> </li>
	 * <li><b>high_voltage</b> </li>
	 * <li><b>laser_and_high_voltage</b> </li></ul></p>
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getPulse_modeScalar();

	/**
	 * How is field evaporation physically triggered.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>laser</b> </li>
	 * <li><b>high_voltage</b> </li>
	 * <li><b>laser_and_high_voltage</b> </li></ul></p>
	 * </p>
	 * 
	 * @param pulse_mode the pulse_mode
	 */
	public DataNode setPulse_modeScalar(String pulse_modeValue);

	/**
	 * Frequency with which the high voltage or laser pulser fires.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_FREQUENCY
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getPulse_frequency();
	
	/**
	 * Frequency with which the high voltage or laser pulser fires.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_FREQUENCY
	 * </p>
	 * 
	 * @param pulse_frequencyDataset the pulse_frequencyDataset
	 */
	public DataNode setPulse_frequency(IDataset pulse_frequencyDataset);

	/**
	 * Frequency with which the high voltage or laser pulser fires.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_FREQUENCY
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Number getPulse_frequencyScalar();

	/**
	 * Frequency with which the high voltage or laser pulser fires.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_FREQUENCY
	 * </p>
	 * 
	 * @param pulse_frequency the pulse_frequency
	 */
	public DataNode setPulse_frequencyScalar(Number pulse_frequencyValue);

	/**
	 * Fraction of the pulse_voltage that is applied in addition
	 * to the standing_voltage at peak voltage of a pulse.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getPulse_fraction();
	
	/**
	 * Fraction of the pulse_voltage that is applied in addition
	 * to the standing_voltage at peak voltage of a pulse.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 * 
	 * @param pulse_fractionDataset the pulse_fractionDataset
	 */
	public DataNode setPulse_fraction(IDataset pulse_fractionDataset);

	/**
	 * Fraction of the pulse_voltage that is applied in addition
	 * to the standing_voltage at peak voltage of a pulse.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Number getPulse_fractionScalar();

	/**
	 * Fraction of the pulse_voltage that is applied in addition
	 * to the standing_voltage at peak voltage of a pulse.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 * 
	 * @param pulse_fraction the pulse_fraction
	 */
	public DataNode setPulse_fractionScalar(Number pulse_fractionValue);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * <b>Dimensions:</b> 1: n_ions;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getPulsed_voltage();
	
	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * <b>Dimensions:</b> 1: n_ions;
	 * </p>
	 * 
	 * @param pulsed_voltageDataset the pulsed_voltageDataset
	 */
	public DataNode setPulsed_voltage(IDataset pulsed_voltageDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * <b>Dimensions:</b> 1: n_ions;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getPulsed_voltageScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * <b>Dimensions:</b> 1: n_ions;
	 * </p>
	 * 
	 * @param pulsed_voltage the pulsed_voltage
	 */
	public DataNode setPulsed_voltageScalar(Double pulsed_voltageValue);

	/**
	 * Direct current voltage between the specimen and the
	 * (local electrode) in the case of local electrode atom
	 * probe (LEAP) instrument.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * <b>Dimensions:</b> 1: n_ions;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getStanding_voltage();
	
	/**
	 * Direct current voltage between the specimen and the
	 * (local electrode) in the case of local electrode atom
	 * probe (LEAP) instrument.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * <b>Dimensions:</b> 1: n_ions;
	 * </p>
	 * 
	 * @param standing_voltageDataset the standing_voltageDataset
	 */
	public DataNode setStanding_voltage(IDataset standing_voltageDataset);

	/**
	 * Direct current voltage between the specimen and the
	 * (local electrode) in the case of local electrode atom
	 * probe (LEAP) instrument.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * <b>Dimensions:</b> 1: n_ions;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getStanding_voltageScalar();

	/**
	 * Direct current voltage between the specimen and the
	 * (local electrode) in the case of local electrode atom
	 * probe (LEAP) instrument.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * <b>Dimensions:</b> 1: n_ions;
	 * </p>
	 * 
	 * @param standing_voltage the standing_voltage
	 */
	public DataNode setStanding_voltageScalar(Double standing_voltageValue);

	/**
	 * Atom probe microscopes use controlled laser, voltage,
	 * or a combination of pulsing strategies to trigger the
	 * excitation and eventual field evaporation/emission of
	 * an ion during an atom probe microscopy experiment.
	 * 
	 * @return  the value.
	 */
	public NXsource getLaser_gun();
	
	/**
	 * Atom probe microscopes use controlled laser, voltage,
	 * or a combination of pulsing strategies to trigger the
	 * excitation and eventual field evaporation/emission of
	 * an ion during an atom probe microscopy experiment.
	 * 
	 * @param laser_gunGroup the laser_gunGroup
	 */
	public void setLaser_gun(NXsource laser_gunGroup);
	// Unprocessed group: 
	// Unprocessed group: 

	/**
	 * Details about specific positions along the focused laser beam
	 * which illuminates the (atom probe) specimen.
	 * 
	 * @return  the value.
	 */
	public NXbeam getLaser_beam();
	
	/**
	 * Details about specific positions along the focused laser beam
	 * which illuminates the (atom probe) specimen.
	 * 
	 * @param laser_beamGroup the laser_beamGroup
	 */
	public void setLaser_beam(NXbeam laser_beamGroup);
	// Unprocessed group: pinhole_position
	// Unprocessed group: spot_position

}

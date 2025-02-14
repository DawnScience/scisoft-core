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
 * Metadata for laser- and/or voltage-pulsing in atom probe microscopy.
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
	public static final String NX_PULSE_NUMBER = "pulse_number";
	public static final String NX_STANDING_VOLTAGE = "standing_voltage";
	/**
	 *
	 * @return  the value.
	 */
	public NXfabrication getFabrication();

	/**
	 *
	 * @param fabricationGroup the fabricationGroup
	 */
	public void setFabrication(NXfabrication fabricationGroup);

	/**
	 * Get a NXfabrication node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXfabrication for that node.
	 */
	public NXfabrication getFabrication(String name);

	/**
	 * Set a NXfabrication node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param fabrication the value to set
	 */
	public void setFabrication(String name, NXfabrication fabrication);

	/**
	 * Get all NXfabrication nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXfabrication for that node.
	 */
	public Map<String, NXfabrication> getAllFabrication();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param fabrication the child nodes to add
	 */

	public void setAllFabrication(Map<String, NXfabrication> fabrication);


	/**
	 * How is field evaporation physically triggered.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>laser</b> </li>
	 * <li><b>voltage</b> </li>
	 * <li><b>laser_and_voltage</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getPulse_mode();

	/**
	 * How is field evaporation physically triggered.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>laser</b> </li>
	 * <li><b>voltage</b> </li>
	 * <li><b>laser_and_voltage</b> </li></ul></p>
	 * </p>
	 *
	 * @param pulse_modeDataset the pulse_modeDataset
	 */
	public DataNode setPulse_mode(IDataset pulse_modeDataset);

	/**
	 * How is field evaporation physically triggered.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>laser</b> </li>
	 * <li><b>voltage</b> </li>
	 * <li><b>laser_and_voltage</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getPulse_modeScalar();

	/**
	 * How is field evaporation physically triggered.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>laser</b> </li>
	 * <li><b>voltage</b> </li>
	 * <li><b>laser_and_voltage</b> </li></ul></p>
	 * </p>
	 *
	 * @param pulse_mode the pulse_mode
	 */
	public DataNode setPulse_modeScalar(String pulse_modeValue);

	/**
	 * Frequency with which the high voltage or laser pulser fires.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_FREQUENCY
	 * <b>Dimensions:</b> 1: n_ions;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getPulse_frequency();

	/**
	 * Frequency with which the high voltage or laser pulser fires.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_FREQUENCY
	 * <b>Dimensions:</b> 1: n_ions;
	 * </p>
	 *
	 * @param pulse_frequencyDataset the pulse_frequencyDataset
	 */
	public DataNode setPulse_frequency(IDataset pulse_frequencyDataset);

	/**
	 * Frequency with which the high voltage or laser pulser fires.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_FREQUENCY
	 * <b>Dimensions:</b> 1: n_ions;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getPulse_frequencyScalar();

	/**
	 * Frequency with which the high voltage or laser pulser fires.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_FREQUENCY
	 * <b>Dimensions:</b> 1: n_ions;
	 * </p>
	 *
	 * @param pulse_frequency the pulse_frequency
	 */
	public DataNode setPulse_frequencyScalar(Double pulse_frequencyValue);

	/**
	 * Fraction of the pulse_voltage that is applied in addition
	 * to the standing_voltage at peak voltage of a pulse.
	 * If a standing voltage is applied, this gives nominal pulse fraction
	 * (as a function of standing voltage). Otherwise this field should not be
	 * present.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: n_ions;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getPulse_fraction();

	/**
	 * Fraction of the pulse_voltage that is applied in addition
	 * to the standing_voltage at peak voltage of a pulse.
	 * If a standing voltage is applied, this gives nominal pulse fraction
	 * (as a function of standing voltage). Otherwise this field should not be
	 * present.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: n_ions;
	 * </p>
	 *
	 * @param pulse_fractionDataset the pulse_fractionDataset
	 */
	public DataNode setPulse_fraction(IDataset pulse_fractionDataset);

	/**
	 * Fraction of the pulse_voltage that is applied in addition
	 * to the standing_voltage at peak voltage of a pulse.
	 * If a standing voltage is applied, this gives nominal pulse fraction
	 * (as a function of standing voltage). Otherwise this field should not be
	 * present.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: n_ions;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getPulse_fractionScalar();

	/**
	 * Fraction of the pulse_voltage that is applied in addition
	 * to the standing_voltage at peak voltage of a pulse.
	 * If a standing voltage is applied, this gives nominal pulse fraction
	 * (as a function of standing voltage). Otherwise this field should not be
	 * present.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: n_ions;
	 * </p>
	 *
	 * @param pulse_fraction the pulse_fraction
	 */
	public DataNode setPulse_fractionScalar(Double pulse_fractionValue);

	/**
	 * In laser pulsing mode the values will be zero so the this field is recommended.
	 * However, for voltage pulsing mode it is highly recommended that users report the pulsed_voltage.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * <b>Dimensions:</b> 1: n_ions;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getPulsed_voltage();

	/**
	 * In laser pulsing mode the values will be zero so the this field is recommended.
	 * However, for voltage pulsing mode it is highly recommended that users report the pulsed_voltage.
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
	 * In laser pulsing mode the values will be zero so the this field is recommended.
	 * However, for voltage pulsing mode it is highly recommended that users report the pulsed_voltage.
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
	 * In laser pulsing mode the values will be zero so the this field is recommended.
	 * However, for voltage pulsing mode it is highly recommended that users report the pulsed_voltage.
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
	 * Absolute number of pulses starting from the beginning of the experiment.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_ions;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getPulse_number();

	/**
	 * Absolute number of pulses starting from the beginning of the experiment.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_ions;
	 * </p>
	 *
	 * @param pulse_numberDataset the pulse_numberDataset
	 */
	public DataNode setPulse_number(IDataset pulse_numberDataset);

	/**
	 * Absolute number of pulses starting from the beginning of the experiment.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_ions;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getPulse_numberScalar();

	/**
	 * Absolute number of pulses starting from the beginning of the experiment.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_ions;
	 * </p>
	 *
	 * @param pulse_number the pulse_number
	 */
	public DataNode setPulse_numberScalar(Long pulse_numberValue);

	/**
	 * Direct current voltage between the specimen and the (local electrode) in
	 * the case of local electrode atom probe (LEAP) instrument.
	 * The standing voltage applied to the sample, relative to system ground.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_VOLTAGE
	 * <b>Dimensions:</b> 1: n_ions;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getStanding_voltage();

	/**
	 * Direct current voltage between the specimen and the (local electrode) in
	 * the case of local electrode atom probe (LEAP) instrument.
	 * The standing voltage applied to the sample, relative to system ground.
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
	 * Direct current voltage between the specimen and the (local electrode) in
	 * the case of local electrode atom probe (LEAP) instrument.
	 * The standing voltage applied to the sample, relative to system ground.
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
	 * Direct current voltage between the specimen and the (local electrode) in
	 * the case of local electrode atom probe (LEAP) instrument.
	 * The standing voltage applied to the sample, relative to system ground.
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
	 * an ion during an experiment.
	 *
	 * @return  the value.
	 */
	public NXsource getSource();

	/**
	 * Atom probe microscopes use controlled laser, voltage,
	 * or a combination of pulsing strategies to trigger the
	 * excitation and eventual field evaporation/emission of
	 * an ion during an experiment.
	 *
	 * @param sourceGroup the sourceGroup
	 */
	public void setSource(NXsource sourceGroup);

	/**
	 * Get a NXsource node by name:
	 * <ul>
	 * <li>
	 * Atom probe microscopes use controlled laser, voltage,
	 * or a combination of pulsing strategies to trigger the
	 * excitation and eventual field evaporation/emission of
	 * an ion during an experiment.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXsource for that node.
	 */
	public NXsource getSource(String name);

	/**
	 * Set a NXsource node by name:
	 * <ul>
	 * <li>
	 * Atom probe microscopes use controlled laser, voltage,
	 * or a combination of pulsing strategies to trigger the
	 * excitation and eventual field evaporation/emission of
	 * an ion during an experiment.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param source the value to set
	 */
	public void setSource(String name, NXsource source);

	/**
	 * Get all NXsource nodes:
	 * <ul>
	 * <li>
	 * Atom probe microscopes use controlled laser, voltage,
	 * or a combination of pulsing strategies to trigger the
	 * excitation and eventual field evaporation/emission of
	 * an ion during an experiment.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXsource for that node.
	 */
	public Map<String, NXsource> getAllSource();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Atom probe microscopes use controlled laser, voltage,
	 * or a combination of pulsing strategies to trigger the
	 * excitation and eventual field evaporation/emission of
	 * an ion during an experiment.</li>
	 * </ul>
	 *
	 * @param source the child nodes to add
	 */

	public void setAllSource(Map<String, NXsource> source);

	// Unprocessed group:
	// Unprocessed group:
	// Unprocessed group:

}

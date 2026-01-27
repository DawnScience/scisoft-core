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
 * Base class for documenting circuit devices.
 * Electronic circuits are hardware components that connect several electronic components to achieve
 * specific functionality, e.g. amplifying a voltage or convert a voltage to binary numbers, etc.
 *
 */
public interface NXcircuit extends NXcomponent {

	public static final String NX_COMPONENTS = "components";
	public static final String NX_CONNECTIONS = "connections";
	public static final String NX_POWER_SOURCE = "power_source";
	public static final String NX_SIGNAL_TYPE = "signal_type";
	public static final String NX_OPERATING_FREQUENCY = "operating_frequency";
	public static final String NX_BANDWIDTH = "bandwidth";
	public static final String NX_INPUT_IMPEDANCE = "input_impedance";
	public static final String NX_OUTPUT_IMPEDANCE = "output_impedance";
	public static final String NX_GAIN = "gain";
	public static final String NX_NOISE_LEVEL = "noise_level";
	public static final String NX_TEMPERATURE_RANGE = "temperature_range";
	public static final String NX_OFFSET = "offset";
	public static final String NX_OUTPUT_CHANNELS = "output_channels";
	public static final String NX_OUTPUT_SIGNAL = "output_signal";
	public static final String NX_POWER_CONSUMPTION = "power_consumption";
	public static final String NX_STATUS_INDICATORS = "status_indicators";
	public static final String NX_PROTECTION_FEATURES = "protection_features";
	public static final String NX_ACQUISITION_TIME = "acquisition_time";
	public static final String NX_OUTPUT_SLEW_RATE = "output_slew_rate";
	/**
	 * Hardware where the circuit is implanted; includes information about the
	 * hardware manufacturers and type (e.g. part number)
	 * All the elements below may be single numbers of an array of values with length N_channel
	 * describing multiple input and output channels.
	 *
	 * @return  the value.
	 */
	public NXfabrication getHardware();

	/**
	 * Hardware where the circuit is implanted; includes information about the
	 * hardware manufacturers and type (e.g. part number)
	 * All the elements below may be single numbers of an array of values with length N_channel
	 * describing multiple input and output channels.
	 *
	 * @param hardwareGroup the hardwareGroup
	 */
	public void setHardware(NXfabrication hardwareGroup);

	/**
	 * List of components used in the circuit, e.g., resistors, capacitors, transistors or any
	 * other complex components.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getComponents();

	/**
	 * List of components used in the circuit, e.g., resistors, capacitors, transistors or any
	 * other complex components.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param componentsDataset the componentsDataset
	 */
	public DataNode setComponents(IDataset componentsDataset);

	/**
	 * List of components used in the circuit, e.g., resistors, capacitors, transistors or any
	 * other complex components.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getComponentsScalar();

	/**
	 * List of components used in the circuit, e.g., resistors, capacitors, transistors or any
	 * other complex components.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param components the components
	 */
	public DataNode setComponentsScalar(String componentsValue);

	/**
	 * Description of how components are interconnected, including connection points
	 * and wiring.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getConnections();

	/**
	 * Description of how components are interconnected, including connection points
	 * and wiring.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param connectionsDataset the connectionsDataset
	 */
	public DataNode setConnections(IDataset connectionsDataset);

	/**
	 * Description of how components are interconnected, including connection points
	 * and wiring.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getConnectionsScalar();

	/**
	 * Description of how components are interconnected, including connection points
	 * and wiring.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param connections the connections
	 */
	public DataNode setConnectionsScalar(String connectionsValue);

	/**
	 * Details of the power source for the circuit, including voltage and current
	 * ratings.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getPower_source();

	/**
	 * Details of the power source for the circuit, including voltage and current
	 * ratings.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param power_sourceDataset the power_sourceDataset
	 */
	public DataNode setPower_source(IDataset power_sourceDataset);

	/**
	 * Details of the power source for the circuit, including voltage and current
	 * ratings.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getPower_sourceScalar();

	/**
	 * Details of the power source for the circuit, including voltage and current
	 * ratings.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param power_source the power_source
	 */
	public DataNode setPower_sourceScalar(String power_sourceValue);

	/**
	 * Type of signal (input signal) the circuit is designed to handle, e.g., analog,
	 * digital, mixed-signal.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSignal_type();

	/**
	 * Type of signal (input signal) the circuit is designed to handle, e.g., analog,
	 * digital, mixed-signal.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param signal_typeDataset the signal_typeDataset
	 */
	public DataNode setSignal_type(IDataset signal_typeDataset);

	/**
	 * Type of signal (input signal) the circuit is designed to handle, e.g., analog,
	 * digital, mixed-signal.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getSignal_typeScalar();

	/**
	 * Type of signal (input signal) the circuit is designed to handle, e.g., analog,
	 * digital, mixed-signal.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param signal_type the signal_type
	 */
	public DataNode setSignal_typeScalar(String signal_typeValue);

	/**
	 * The operating frequency of the circuit, see also bandwidth, which is possibly
	 * but not necessarily centered around this frequency (e.g. running a 100 kHz bandwidth
	 * amplifier at low, audio frequencies 1 - 20,000 Hz).
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_FREQUENCY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getOperating_frequency();

	/**
	 * The operating frequency of the circuit, see also bandwidth, which is possibly
	 * but not necessarily centered around this frequency (e.g. running a 100 kHz bandwidth
	 * amplifier at low, audio frequencies 1 - 20,000 Hz).
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_FREQUENCY
	 * </p>
	 *
	 * @param operating_frequencyDataset the operating_frequencyDataset
	 */
	public DataNode setOperating_frequency(IDataset operating_frequencyDataset);

	/**
	 * The operating frequency of the circuit, see also bandwidth, which is possibly
	 * but not necessarily centered around this frequency (e.g. running a 100 kHz bandwidth
	 * amplifier at low, audio frequencies 1 - 20,000 Hz).
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_FREQUENCY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getOperating_frequencyScalar();

	/**
	 * The operating frequency of the circuit, see also bandwidth, which is possibly
	 * but not necessarily centered around this frequency (e.g. running a 100 kHz bandwidth
	 * amplifier at low, audio frequencies 1 - 20,000 Hz).
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_FREQUENCY
	 * </p>
	 *
	 * @param operating_frequency the operating_frequency
	 */
	public DataNode setOperating_frequencyScalar(Number operating_frequencyValue);

	/**
	 * The bandwidth of the frequency response of the circuit.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_FREQUENCY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getBandwidth();

	/**
	 * The bandwidth of the frequency response of the circuit.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_FREQUENCY
	 * </p>
	 *
	 * @param bandwidthDataset the bandwidthDataset
	 */
	public DataNode setBandwidth(IDataset bandwidthDataset);

	/**
	 * The bandwidth of the frequency response of the circuit.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_FREQUENCY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getBandwidthScalar();

	/**
	 * The bandwidth of the frequency response of the circuit.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_FREQUENCY
	 * </p>
	 *
	 * @param bandwidth the bandwidth
	 */
	public DataNode setBandwidthScalar(Number bandwidthValue);

	/**
	 * Input impedance of the circuit.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getInput_impedance();

	/**
	 * Input impedance of the circuit.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param input_impedanceDataset the input_impedanceDataset
	 */
	public DataNode setInput_impedance(IDataset input_impedanceDataset);

	/**
	 * Input impedance of the circuit.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getInput_impedanceScalar();

	/**
	 * Input impedance of the circuit.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param input_impedance the input_impedance
	 */
	public DataNode setInput_impedanceScalar(Number input_impedanceValue);

	/**
	 * Output impedance of the circuit.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getOutput_impedance();

	/**
	 * Output impedance of the circuit.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param output_impedanceDataset the output_impedanceDataset
	 */
	public DataNode setOutput_impedance(IDataset output_impedanceDataset);

	/**
	 * Output impedance of the circuit.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getOutput_impedanceScalar();

	/**
	 * Output impedance of the circuit.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param output_impedance the output_impedance
	 */
	public DataNode setOutput_impedanceScalar(Number output_impedanceValue);

	/**
	 * Gain of the circuit, if applicable, usually all instruments have a gain
	 * which might be important or not.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getGain();

	/**
	 * Gain of the circuit, if applicable, usually all instruments have a gain
	 * which might be important or not.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param gainDataset the gainDataset
	 */
	public DataNode setGain(IDataset gainDataset);

	/**
	 * Gain of the circuit, if applicable, usually all instruments have a gain
	 * which might be important or not.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getGainScalar();

	/**
	 * Gain of the circuit, if applicable, usually all instruments have a gain
	 * which might be important or not.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param gain the gain
	 */
	public DataNode setGainScalar(Number gainValue);

	/**
	 * Root-mean-square (RMS) noise level (in current or voltage)
	 * in the circuit in voltage or current.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getNoise_level();

	/**
	 * Root-mean-square (RMS) noise level (in current or voltage)
	 * in the circuit in voltage or current.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param noise_levelDataset the noise_levelDataset
	 */
	public DataNode setNoise_level(IDataset noise_levelDataset);

	/**
	 * Root-mean-square (RMS) noise level (in current or voltage)
	 * in the circuit in voltage or current.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getNoise_levelScalar();

	/**
	 * Root-mean-square (RMS) noise level (in current or voltage)
	 * in the circuit in voltage or current.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param noise_level the noise_level
	 */
	public DataNode setNoise_levelScalar(Number noise_levelValue);

	/**
	 * Operating temperature range of the circuit.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getTemperature_range();

	/**
	 * Operating temperature range of the circuit.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param temperature_rangeDataset the temperature_rangeDataset
	 */
	public DataNode setTemperature_range(IDataset temperature_rangeDataset);

	/**
	 * Operating temperature range of the circuit.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getTemperature_rangeScalar();

	/**
	 * Operating temperature range of the circuit.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param temperature_range the temperature_range
	 */
	public DataNode setTemperature_rangeScalar(Number temperature_rangeValue);

	/**
	 * Calibration data for the circuit.
	 *
	 * @return  the value.
	 */
	public NXcalibration getCalibration();

	/**
	 * Calibration data for the circuit.
	 *
	 * @param calibrationGroup the calibrationGroup
	 */
	public void setCalibration(NXcalibration calibrationGroup);

	/**
	 * Offset value for current or voltage.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getOffset();

	/**
	 * Offset value for current or voltage.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param offsetDataset the offsetDataset
	 */
	public DataNode setOffset(IDataset offsetDataset);

	/**
	 * Offset value for current or voltage.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getOffsetScalar();

	/**
	 * Offset value for current or voltage.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param offset the offset
	 */
	public DataNode setOffsetScalar(Number offsetValue);

	/**
	 * Number of output channels connected to this circuit. Most probably N_channel.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getOutput_channels();

	/**
	 * Number of output channels connected to this circuit. Most probably N_channel.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @param output_channelsDataset the output_channelsDataset
	 */
	public DataNode setOutput_channels(IDataset output_channelsDataset);

	/**
	 * Number of output channels connected to this circuit. Most probably N_channel.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getOutput_channelsScalar();

	/**
	 * Number of output channels connected to this circuit. Most probably N_channel.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @param output_channels the output_channels
	 */
	public DataNode setOutput_channelsScalar(Number output_channelsValue);

	/**
	 * Type of output signal, e.g., voltage, current, digital.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getOutput_signal();

	/**
	 * Type of output signal, e.g., voltage, current, digital.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param output_signalDataset the output_signalDataset
	 */
	public DataNode setOutput_signal(IDataset output_signalDataset);

	/**
	 * Type of output signal, e.g., voltage, current, digital.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getOutput_signalScalar();

	/**
	 * Type of output signal, e.g., voltage, current, digital.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param output_signal the output_signal
	 */
	public DataNode setOutput_signalScalar(Number output_signalValue);

	/**
	 * Power consumption of the circuit per unit time.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getPower_consumption();

	/**
	 * Power consumption of the circuit per unit time.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param power_consumptionDataset the power_consumptionDataset
	 */
	public DataNode setPower_consumption(IDataset power_consumptionDataset);

	/**
	 * Power consumption of the circuit per unit time.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getPower_consumptionScalar();

	/**
	 * Power consumption of the circuit per unit time.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param power_consumption the power_consumption
	 */
	public DataNode setPower_consumptionScalar(Number power_consumptionValue);

	/**
	 * Status indicators for the circuit, e.g., LEDs, display readouts.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getStatus_indicators();

	/**
	 * Status indicators for the circuit, e.g., LEDs, display readouts.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param status_indicatorsDataset the status_indicatorsDataset
	 */
	public DataNode setStatus_indicators(IDataset status_indicatorsDataset);

	/**
	 * Status indicators for the circuit, e.g., LEDs, display readouts.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getStatus_indicatorsScalar();

	/**
	 * Status indicators for the circuit, e.g., LEDs, display readouts.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param status_indicators the status_indicators
	 */
	public DataNode setStatus_indicatorsScalar(String status_indicatorsValue);

	/**
	 * Protection features built into the circuit, e.g., overvoltage protection,
	 * thermal shutdown.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getProtection_features();

	/**
	 * Protection features built into the circuit, e.g., overvoltage protection,
	 * thermal shutdown.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param protection_featuresDataset the protection_featuresDataset
	 */
	public DataNode setProtection_features(IDataset protection_featuresDataset);

	/**
	 * Protection features built into the circuit, e.g., overvoltage protection,
	 * thermal shutdown.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getProtection_featuresScalar();

	/**
	 * Protection features built into the circuit, e.g., overvoltage protection,
	 * thermal shutdown.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param protection_features the protection_features
	 */
	public DataNode setProtection_featuresScalar(String protection_featuresValue);

	/**
	 * Updated rate for several processes using the input signal, e.g., History Graph, the circuit
	 * uses for any such process.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getAcquisition_time();

	/**
	 * Updated rate for several processes using the input signal, e.g., History Graph, the circuit
	 * uses for any such process.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @param acquisition_timeDataset the acquisition_timeDataset
	 */
	public DataNode setAcquisition_time(IDataset acquisition_timeDataset);

	/**
	 * Updated rate for several processes using the input signal, e.g., History Graph, the circuit
	 * uses for any such process.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getAcquisition_timeScalar();

	/**
	 * Updated rate for several processes using the input signal, e.g., History Graph, the circuit
	 * uses for any such process.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_TIME
	 * </p>
	 *
	 * @param acquisition_time the acquisition_time
	 */
	public DataNode setAcquisition_timeScalar(Number acquisition_timeValue);

	/**
	 * The rate at which the signal changes when ramping from the starting
	 * value.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getOutput_slew_rate();

	/**
	 * The rate at which the signal changes when ramping from the starting
	 * value.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param output_slew_rateDataset the output_slew_rateDataset
	 */
	public DataNode setOutput_slew_rate(IDataset output_slew_rateDataset);

	/**
	 * The rate at which the signal changes when ramping from the starting
	 * value.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getOutput_slew_rateScalar();

	/**
	 * The rate at which the signal changes when ramping from the starting
	 * value.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param output_slew_rate the output_slew_rate
	 */
	public DataNode setOutput_slew_rateScalar(String output_slew_rateValue);

}

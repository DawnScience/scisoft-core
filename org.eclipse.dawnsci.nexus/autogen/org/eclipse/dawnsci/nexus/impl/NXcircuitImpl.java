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

package org.eclipse.dawnsci.nexus.impl;

import java.util.Set;
import java.util.EnumSet;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Dataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * Base class for documenting circuit devices.
 * Electronic circuits are hardware components that connect several electronic components to achieve
 * specific functionality, e.g. amplifying a voltage or convert a voltage to binary numbers, etc.

 */
public class NXcircuitImpl extends NXcomponentImpl implements NXcircuit {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_FABRICATION,
		NexusBaseClass.NX_CALIBRATION);

	public NXcircuitImpl() {
		super();
	}

	public NXcircuitImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcircuit.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CIRCUIT;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public NXfabrication getHardware() {
		// dataNodeName = NX_HARDWARE
		return getChild("hardware", NXfabrication.class);
	}

	@Override
	public void setHardware(NXfabrication hardwareGroup) {
		putChild("hardware", hardwareGroup);
	}

	@Override
	public Dataset getComponents() {
		return getDataset(NX_COMPONENTS);
	}

	@Override
	public String getComponentsScalar() {
		return getString(NX_COMPONENTS);
	}

	@Override
	public DataNode setComponents(IDataset componentsDataset) {
		return setDataset(NX_COMPONENTS, componentsDataset);
	}

	@Override
	public DataNode setComponentsScalar(String componentsValue) {
		return setString(NX_COMPONENTS, componentsValue);
	}

	@Override
	public Dataset getConnections() {
		return getDataset(NX_CONNECTIONS);
	}

	@Override
	public String getConnectionsScalar() {
		return getString(NX_CONNECTIONS);
	}

	@Override
	public DataNode setConnections(IDataset connectionsDataset) {
		return setDataset(NX_CONNECTIONS, connectionsDataset);
	}

	@Override
	public DataNode setConnectionsScalar(String connectionsValue) {
		return setString(NX_CONNECTIONS, connectionsValue);
	}

	@Override
	public Dataset getPower_source() {
		return getDataset(NX_POWER_SOURCE);
	}

	@Override
	public String getPower_sourceScalar() {
		return getString(NX_POWER_SOURCE);
	}

	@Override
	public DataNode setPower_source(IDataset power_sourceDataset) {
		return setDataset(NX_POWER_SOURCE, power_sourceDataset);
	}

	@Override
	public DataNode setPower_sourceScalar(String power_sourceValue) {
		return setString(NX_POWER_SOURCE, power_sourceValue);
	}

	@Override
	public Dataset getSignal_type() {
		return getDataset(NX_SIGNAL_TYPE);
	}

	@Override
	public String getSignal_typeScalar() {
		return getString(NX_SIGNAL_TYPE);
	}

	@Override
	public DataNode setSignal_type(IDataset signal_typeDataset) {
		return setDataset(NX_SIGNAL_TYPE, signal_typeDataset);
	}

	@Override
	public DataNode setSignal_typeScalar(String signal_typeValue) {
		return setString(NX_SIGNAL_TYPE, signal_typeValue);
	}

	@Override
	public Dataset getOperating_frequency() {
		return getDataset(NX_OPERATING_FREQUENCY);
	}

	@Override
	public Number getOperating_frequencyScalar() {
		return getNumber(NX_OPERATING_FREQUENCY);
	}

	@Override
	public DataNode setOperating_frequency(IDataset operating_frequencyDataset) {
		return setDataset(NX_OPERATING_FREQUENCY, operating_frequencyDataset);
	}

	@Override
	public DataNode setOperating_frequencyScalar(Number operating_frequencyValue) {
		return setField(NX_OPERATING_FREQUENCY, operating_frequencyValue);
	}

	@Override
	public Dataset getBandwidth() {
		return getDataset(NX_BANDWIDTH);
	}

	@Override
	public Number getBandwidthScalar() {
		return getNumber(NX_BANDWIDTH);
	}

	@Override
	public DataNode setBandwidth(IDataset bandwidthDataset) {
		return setDataset(NX_BANDWIDTH, bandwidthDataset);
	}

	@Override
	public DataNode setBandwidthScalar(Number bandwidthValue) {
		return setField(NX_BANDWIDTH, bandwidthValue);
	}

	@Override
	public Dataset getInput_impedance() {
		return getDataset(NX_INPUT_IMPEDANCE);
	}

	@Override
	public Number getInput_impedanceScalar() {
		return getNumber(NX_INPUT_IMPEDANCE);
	}

	@Override
	public DataNode setInput_impedance(IDataset input_impedanceDataset) {
		return setDataset(NX_INPUT_IMPEDANCE, input_impedanceDataset);
	}

	@Override
	public DataNode setInput_impedanceScalar(Number input_impedanceValue) {
		return setField(NX_INPUT_IMPEDANCE, input_impedanceValue);
	}

	@Override
	public Dataset getOutput_impedance() {
		return getDataset(NX_OUTPUT_IMPEDANCE);
	}

	@Override
	public Number getOutput_impedanceScalar() {
		return getNumber(NX_OUTPUT_IMPEDANCE);
	}

	@Override
	public DataNode setOutput_impedance(IDataset output_impedanceDataset) {
		return setDataset(NX_OUTPUT_IMPEDANCE, output_impedanceDataset);
	}

	@Override
	public DataNode setOutput_impedanceScalar(Number output_impedanceValue) {
		return setField(NX_OUTPUT_IMPEDANCE, output_impedanceValue);
	}

	@Override
	public Dataset getGain() {
		return getDataset(NX_GAIN);
	}

	@Override
	public Number getGainScalar() {
		return getNumber(NX_GAIN);
	}

	@Override
	public DataNode setGain(IDataset gainDataset) {
		return setDataset(NX_GAIN, gainDataset);
	}

	@Override
	public DataNode setGainScalar(Number gainValue) {
		return setField(NX_GAIN, gainValue);
	}

	@Override
	public Dataset getNoise_level() {
		return getDataset(NX_NOISE_LEVEL);
	}

	@Override
	public Number getNoise_levelScalar() {
		return getNumber(NX_NOISE_LEVEL);
	}

	@Override
	public DataNode setNoise_level(IDataset noise_levelDataset) {
		return setDataset(NX_NOISE_LEVEL, noise_levelDataset);
	}

	@Override
	public DataNode setNoise_levelScalar(Number noise_levelValue) {
		return setField(NX_NOISE_LEVEL, noise_levelValue);
	}

	@Override
	public Dataset getTemperature_range() {
		return getDataset(NX_TEMPERATURE_RANGE);
	}

	@Override
	public Number getTemperature_rangeScalar() {
		return getNumber(NX_TEMPERATURE_RANGE);
	}

	@Override
	public DataNode setTemperature_range(IDataset temperature_rangeDataset) {
		return setDataset(NX_TEMPERATURE_RANGE, temperature_rangeDataset);
	}

	@Override
	public DataNode setTemperature_rangeScalar(Number temperature_rangeValue) {
		return setField(NX_TEMPERATURE_RANGE, temperature_rangeValue);
	}

	@Override
	public NXcalibration getCalibration() {
		// dataNodeName = NX_CALIBRATION
		return getChild("calibration", NXcalibration.class);
	}

	@Override
	public void setCalibration(NXcalibration calibrationGroup) {
		putChild("calibration", calibrationGroup);
	}

	@Override
	public Dataset getOffset() {
		return getDataset(NX_OFFSET);
	}

	@Override
	public Number getOffsetScalar() {
		return getNumber(NX_OFFSET);
	}

	@Override
	public DataNode setOffset(IDataset offsetDataset) {
		return setDataset(NX_OFFSET, offsetDataset);
	}

	@Override
	public DataNode setOffsetScalar(Number offsetValue) {
		return setField(NX_OFFSET, offsetValue);
	}

	@Override
	public Dataset getOutput_channels() {
		return getDataset(NX_OUTPUT_CHANNELS);
	}

	@Override
	public Number getOutput_channelsScalar() {
		return getNumber(NX_OUTPUT_CHANNELS);
	}

	@Override
	public DataNode setOutput_channels(IDataset output_channelsDataset) {
		return setDataset(NX_OUTPUT_CHANNELS, output_channelsDataset);
	}

	@Override
	public DataNode setOutput_channelsScalar(Number output_channelsValue) {
		return setField(NX_OUTPUT_CHANNELS, output_channelsValue);
	}

	@Override
	public Dataset getOutput_signal() {
		return getDataset(NX_OUTPUT_SIGNAL);
	}

	@Override
	public Number getOutput_signalScalar() {
		return getNumber(NX_OUTPUT_SIGNAL);
	}

	@Override
	public DataNode setOutput_signal(IDataset output_signalDataset) {
		return setDataset(NX_OUTPUT_SIGNAL, output_signalDataset);
	}

	@Override
	public DataNode setOutput_signalScalar(Number output_signalValue) {
		return setField(NX_OUTPUT_SIGNAL, output_signalValue);
	}

	@Override
	public Dataset getPower_consumption() {
		return getDataset(NX_POWER_CONSUMPTION);
	}

	@Override
	public Number getPower_consumptionScalar() {
		return getNumber(NX_POWER_CONSUMPTION);
	}

	@Override
	public DataNode setPower_consumption(IDataset power_consumptionDataset) {
		return setDataset(NX_POWER_CONSUMPTION, power_consumptionDataset);
	}

	@Override
	public DataNode setPower_consumptionScalar(Number power_consumptionValue) {
		return setField(NX_POWER_CONSUMPTION, power_consumptionValue);
	}

	@Override
	public Dataset getStatus_indicators() {
		return getDataset(NX_STATUS_INDICATORS);
	}

	@Override
	public String getStatus_indicatorsScalar() {
		return getString(NX_STATUS_INDICATORS);
	}

	@Override
	public DataNode setStatus_indicators(IDataset status_indicatorsDataset) {
		return setDataset(NX_STATUS_INDICATORS, status_indicatorsDataset);
	}

	@Override
	public DataNode setStatus_indicatorsScalar(String status_indicatorsValue) {
		return setString(NX_STATUS_INDICATORS, status_indicatorsValue);
	}

	@Override
	public Dataset getProtection_features() {
		return getDataset(NX_PROTECTION_FEATURES);
	}

	@Override
	public String getProtection_featuresScalar() {
		return getString(NX_PROTECTION_FEATURES);
	}

	@Override
	public DataNode setProtection_features(IDataset protection_featuresDataset) {
		return setDataset(NX_PROTECTION_FEATURES, protection_featuresDataset);
	}

	@Override
	public DataNode setProtection_featuresScalar(String protection_featuresValue) {
		return setString(NX_PROTECTION_FEATURES, protection_featuresValue);
	}

	@Override
	public Dataset getAcquisition_time() {
		return getDataset(NX_ACQUISITION_TIME);
	}

	@Override
	public Number getAcquisition_timeScalar() {
		return getNumber(NX_ACQUISITION_TIME);
	}

	@Override
	public DataNode setAcquisition_time(IDataset acquisition_timeDataset) {
		return setDataset(NX_ACQUISITION_TIME, acquisition_timeDataset);
	}

	@Override
	public DataNode setAcquisition_timeScalar(Number acquisition_timeValue) {
		return setField(NX_ACQUISITION_TIME, acquisition_timeValue);
	}

	@Override
	public Dataset getOutput_slew_rate() {
		return getDataset(NX_OUTPUT_SLEW_RATE);
	}

	@Override
	public String getOutput_slew_rateScalar() {
		return getString(NX_OUTPUT_SLEW_RATE);
	}

	@Override
	public DataNode setOutput_slew_rate(IDataset output_slew_rateDataset) {
		return setDataset(NX_OUTPUT_SLEW_RATE, output_slew_rateDataset);
	}

	@Override
	public DataNode setOutput_slew_rateScalar(String output_slew_rateValue) {
		return setString(NX_OUTPUT_SLEW_RATE, output_slew_rateValue);
	}

}

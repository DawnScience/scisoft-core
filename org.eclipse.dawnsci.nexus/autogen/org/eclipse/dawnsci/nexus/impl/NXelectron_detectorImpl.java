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
 * A subclass of NXdetector for detectors that detect electrons.

 */
public class NXelectron_detectorImpl extends NXdetectorImpl implements NXelectron_detector {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXelectron_detectorImpl() {
		super();
	}

	public NXelectron_detectorImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXelectron_detector.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_ELECTRON_DETECTOR;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getAmplifier_type() {
		return getDataset(NX_AMPLIFIER_TYPE);
	}

	@Override
	public String getAmplifier_typeScalar() {
		return getString(NX_AMPLIFIER_TYPE);
	}

	@Override
	public DataNode setAmplifier_type(IDataset amplifier_typeDataset) {
		return setDataset(NX_AMPLIFIER_TYPE, amplifier_typeDataset);
	}

	@Override
	public DataNode setAmplifier_typeScalar(String amplifier_typeValue) {
		return setString(NX_AMPLIFIER_TYPE, amplifier_typeValue);
	}

	@Override
	public Dataset getDetector_type() {
		return getDataset(NX_DETECTOR_TYPE);
	}

	@Override
	public String getDetector_typeScalar() {
		return getString(NX_DETECTOR_TYPE);
	}

	@Override
	public DataNode setDetector_type(IDataset detector_typeDataset) {
		return setDataset(NX_DETECTOR_TYPE, detector_typeDataset);
	}

	@Override
	public DataNode setDetector_typeScalar(String detector_typeValue) {
		return setString(NX_DETECTOR_TYPE, detector_typeValue);
	}

	@Override
	public Dataset getDetector_voltage() {
		return getDataset(NX_DETECTOR_VOLTAGE);
	}

	@Override
	public Double getDetector_voltageScalar() {
		return getDouble(NX_DETECTOR_VOLTAGE);
	}

	@Override
	public DataNode setDetector_voltage(IDataset detector_voltageDataset) {
		return setDataset(NX_DETECTOR_VOLTAGE, detector_voltageDataset);
	}

	@Override
	public DataNode setDetector_voltageScalar(Double detector_voltageValue) {
		return setField(NX_DETECTOR_VOLTAGE, detector_voltageValue);
	}

	@Override
	public Dataset getAmplifier_voltage() {
		return getDataset(NX_AMPLIFIER_VOLTAGE);
	}

	@Override
	public Double getAmplifier_voltageScalar() {
		return getDouble(NX_AMPLIFIER_VOLTAGE);
	}

	@Override
	public DataNode setAmplifier_voltage(IDataset amplifier_voltageDataset) {
		return setDataset(NX_AMPLIFIER_VOLTAGE, amplifier_voltageDataset);
	}

	@Override
	public DataNode setAmplifier_voltageScalar(Double amplifier_voltageValue) {
		return setField(NX_AMPLIFIER_VOLTAGE, amplifier_voltageValue);
	}

	@Override
	public Dataset getAmplifier_bias() {
		return getDataset(NX_AMPLIFIER_BIAS);
	}

	@Override
	public Double getAmplifier_biasScalar() {
		return getDouble(NX_AMPLIFIER_BIAS);
	}

	@Override
	public DataNode setAmplifier_bias(IDataset amplifier_biasDataset) {
		return setDataset(NX_AMPLIFIER_BIAS, amplifier_biasDataset);
	}

	@Override
	public DataNode setAmplifier_biasScalar(Double amplifier_biasValue) {
		return setField(NX_AMPLIFIER_BIAS, amplifier_biasValue);
	}

}

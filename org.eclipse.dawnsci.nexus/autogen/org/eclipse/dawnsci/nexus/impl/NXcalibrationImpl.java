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

import org.eclipse.dawnsci.nexus.*;

/**
 * Subclass of NXprocess to describe post-processing calibrations.
 * 
 */
public class NXcalibrationImpl extends NXobjectImpl implements NXcalibration {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXcalibrationImpl() {
		super();
	}

	public NXcalibrationImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcalibration.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CALIBRATION;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}
	

	@Override
	public IDataset getLast_process() {
		return getDataset(NX_LAST_PROCESS);
	}

	@Override
	public String getLast_processScalar() {
		return getString(NX_LAST_PROCESS);
	}

	@Override
	public DataNode setLast_process(IDataset last_processDataset) {
		return setDataset(NX_LAST_PROCESS, last_processDataset);
	}

	@Override
	public DataNode setLast_processScalar(String last_processValue) {
		return setString(NX_LAST_PROCESS, last_processValue);
	}

	@Override
	public IDataset getApplied() {
		return getDataset(NX_APPLIED);
	}

	@Override
	public Boolean getAppliedScalar() {
		return getBoolean(NX_APPLIED);
	}

	@Override
	public DataNode setApplied(IDataset appliedDataset) {
		return setDataset(NX_APPLIED, appliedDataset);
	}

	@Override
	public DataNode setAppliedScalar(Boolean appliedValue) {
		return setField(NX_APPLIED, appliedValue);
	}

	@Override
	public IDataset getCoefficients() {
		return getDataset(NX_COEFFICIENTS);
	}

	@Override
	public Double getCoefficientsScalar() {
		return getDouble(NX_COEFFICIENTS);
	}

	@Override
	public DataNode setCoefficients(IDataset coefficientsDataset) {
		return setDataset(NX_COEFFICIENTS, coefficientsDataset);
	}

	@Override
	public DataNode setCoefficientsScalar(Double coefficientsValue) {
		return setField(NX_COEFFICIENTS, coefficientsValue);
	}

	@Override
	public IDataset getFit_function() {
		return getDataset(NX_FIT_FUNCTION);
	}

	@Override
	public String getFit_functionScalar() {
		return getString(NX_FIT_FUNCTION);
	}

	@Override
	public DataNode setFit_function(IDataset fit_functionDataset) {
		return setDataset(NX_FIT_FUNCTION, fit_functionDataset);
	}

	@Override
	public DataNode setFit_functionScalar(String fit_functionValue) {
		return setString(NX_FIT_FUNCTION, fit_functionValue);
	}

	@Override
	public IDataset getScaling() {
		return getDataset(NX_SCALING);
	}

	@Override
	public Double getScalingScalar() {
		return getDouble(NX_SCALING);
	}

	@Override
	public DataNode setScaling(IDataset scalingDataset) {
		return setDataset(NX_SCALING, scalingDataset);
	}

	@Override
	public DataNode setScalingScalar(Double scalingValue) {
		return setField(NX_SCALING, scalingValue);
	}

	@Override
	public IDataset getOffset() {
		return getDataset(NX_OFFSET);
	}

	@Override
	public Double getOffsetScalar() {
		return getDouble(NX_OFFSET);
	}

	@Override
	public DataNode setOffset(IDataset offsetDataset) {
		return setDataset(NX_OFFSET, offsetDataset);
	}

	@Override
	public DataNode setOffsetScalar(Double offsetValue) {
		return setField(NX_OFFSET, offsetValue);
	}

	@Override
	public IDataset getCalibrated_axis() {
		return getDataset(NX_CALIBRATED_AXIS);
	}

	@Override
	public Double getCalibrated_axisScalar() {
		return getDouble(NX_CALIBRATED_AXIS);
	}

	@Override
	public DataNode setCalibrated_axis(IDataset calibrated_axisDataset) {
		return setDataset(NX_CALIBRATED_AXIS, calibrated_axisDataset);
	}

	@Override
	public DataNode setCalibrated_axisScalar(Double calibrated_axisValue) {
		return setField(NX_CALIBRATED_AXIS, calibrated_axisValue);
	}

	@Override
	public IDataset getOriginal_axis() {
		return getDataset(NX_ORIGINAL_AXIS);
	}

	@Override
	public Double getOriginal_axisScalar() {
		return getDouble(NX_ORIGINAL_AXIS);
	}

	@Override
	public DataNode setOriginal_axis(IDataset original_axisDataset) {
		return setDataset(NX_ORIGINAL_AXIS, original_axisDataset);
	}

	@Override
	public DataNode setOriginal_axisScalar(Double original_axisValue) {
		return setField(NX_ORIGINAL_AXIS, original_axisValue);
	}

	@Override
	public IDataset getDescription() {
		return getDataset(NX_DESCRIPTION);
	}

	@Override
	public String getDescriptionScalar() {
		return getString(NX_DESCRIPTION);
	}

	@Override
	public DataNode setDescription(IDataset descriptionDataset) {
		return setDataset(NX_DESCRIPTION, descriptionDataset);
	}

	@Override
	public DataNode setDescriptionScalar(String descriptionValue) {
		return setString(NX_DESCRIPTION, descriptionValue);
	}

}

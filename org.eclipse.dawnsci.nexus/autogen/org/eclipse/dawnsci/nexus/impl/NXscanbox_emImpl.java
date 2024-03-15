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
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * Scan box and coils which deflect an electron beam in a controlled manner.
 * In electron microscopy, the scan box is instructed by the microscope
 * control software. This component directs the probe to controlled
 * locations according to a scan scheme and plan.

 */
public class NXscanbox_emImpl extends NXobjectImpl implements NXscanbox_em {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_FABRICATION);

	public NXscanbox_emImpl() {
		super();
	}

	public NXscanbox_emImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXscanbox_em.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_SCANBOX_EM;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public IDataset getCalibration_style() {
		return getDataset(NX_CALIBRATION_STYLE);
	}

	@Override
	public String getCalibration_styleScalar() {
		return getString(NX_CALIBRATION_STYLE);
	}

	@Override
	public DataNode setCalibration_style(IDataset calibration_styleDataset) {
		return setDataset(NX_CALIBRATION_STYLE, calibration_styleDataset);
	}

	@Override
	public DataNode setCalibration_styleScalar(String calibration_styleValue) {
		return setString(NX_CALIBRATION_STYLE, calibration_styleValue);
	}

	@Override
	public IDataset getCenter() {
		return getDataset(NX_CENTER);
	}

	@Override
	public Number getCenterScalar() {
		return getNumber(NX_CENTER);
	}

	@Override
	public DataNode setCenter(IDataset centerDataset) {
		return setDataset(NX_CENTER, centerDataset);
	}

	@Override
	public DataNode setCenterScalar(Number centerValue) {
		return setField(NX_CENTER, centerValue);
	}

	@Override
	public IDataset getFlyback_time() {
		return getDataset(NX_FLYBACK_TIME);
	}

	@Override
	public Double getFlyback_timeScalar() {
		return getDouble(NX_FLYBACK_TIME);
	}

	@Override
	public DataNode setFlyback_time(IDataset flyback_timeDataset) {
		return setDataset(NX_FLYBACK_TIME, flyback_timeDataset);
	}

	@Override
	public DataNode setFlyback_timeScalar(Double flyback_timeValue) {
		return setField(NX_FLYBACK_TIME, flyback_timeValue);
	}

	@Override
	public IDataset getLine_time() {
		return getDataset(NX_LINE_TIME);
	}

	@Override
	public Double getLine_timeScalar() {
		return getDouble(NX_LINE_TIME);
	}

	@Override
	public DataNode setLine_time(IDataset line_timeDataset) {
		return setDataset(NX_LINE_TIME, line_timeDataset);
	}

	@Override
	public DataNode setLine_timeScalar(Double line_timeValue) {
		return setField(NX_LINE_TIME, line_timeValue);
	}

	@Override
	public IDataset getPixel_time() {
		return getDataset(NX_PIXEL_TIME);
	}

	@Override
	public Double getPixel_timeScalar() {
		return getDouble(NX_PIXEL_TIME);
	}

	@Override
	public DataNode setPixel_time(IDataset pixel_timeDataset) {
		return setDataset(NX_PIXEL_TIME, pixel_timeDataset);
	}

	@Override
	public DataNode setPixel_timeScalar(Double pixel_timeValue) {
		return setField(NX_PIXEL_TIME, pixel_timeValue);
	}

	@Override
	public IDataset getRequested_pixel_time() {
		return getDataset(NX_REQUESTED_PIXEL_TIME);
	}

	@Override
	public Double getRequested_pixel_timeScalar() {
		return getDouble(NX_REQUESTED_PIXEL_TIME);
	}

	@Override
	public DataNode setRequested_pixel_time(IDataset requested_pixel_timeDataset) {
		return setDataset(NX_REQUESTED_PIXEL_TIME, requested_pixel_timeDataset);
	}

	@Override
	public DataNode setRequested_pixel_timeScalar(Double requested_pixel_timeValue) {
		return setField(NX_REQUESTED_PIXEL_TIME, requested_pixel_timeValue);
	}

	@Override
	public IDataset getRotation() {
		return getDataset(NX_ROTATION);
	}

	@Override
	public Double getRotationScalar() {
		return getDouble(NX_ROTATION);
	}

	@Override
	public DataNode setRotation(IDataset rotationDataset) {
		return setDataset(NX_ROTATION, rotationDataset);
	}

	@Override
	public DataNode setRotationScalar(Double rotationValue) {
		return setField(NX_ROTATION, rotationValue);
	}

	@Override
	public IDataset getAc_line_sync() {
		return getDataset(NX_AC_LINE_SYNC);
	}

	@Override
	public Boolean getAc_line_syncScalar() {
		return getBoolean(NX_AC_LINE_SYNC);
	}

	@Override
	public DataNode setAc_line_sync(IDataset ac_line_syncDataset) {
		return setDataset(NX_AC_LINE_SYNC, ac_line_syncDataset);
	}

	@Override
	public DataNode setAc_line_syncScalar(Boolean ac_line_syncValue) {
		return setField(NX_AC_LINE_SYNC, ac_line_syncValue);
	}

	@Override
	public NXfabrication getFabrication() {
		// dataNodeName = NX_FABRICATION
		return getChild("fabrication", NXfabrication.class);
	}

	@Override
	public void setFabrication(NXfabrication fabricationGroup) {
		putChild("fabrication", fabricationGroup);
	}

	@Override
	public NXfabrication getFabrication(String name) {
		return getChild(name, NXfabrication.class);
	}

	@Override
	public void setFabrication(String name, NXfabrication fabrication) {
		putChild(name, fabrication);
	}

	@Override
	public Map<String, NXfabrication> getAllFabrication() {
		return getChildren(NXfabrication.class);
	}

	@Override
	public void setAllFabrication(Map<String, NXfabrication> fabrication) {
		setChildren(fabrication);
	}

}

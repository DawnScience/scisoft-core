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
 * Container for reporting a set of secondary electron images.
 * Secondary electron images are one of the most important signal especially
 * for scanning electron microscopy in materials science and engineering, for
 * analyses of surface topography, getting an overview of the analysis region,
 * or fractography.
 * 
 */
public class NXimage_set_em_seImpl extends NXobjectImpl implements NXimage_set_em_se {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_DATA,
		NexusBaseClass.NX_OPTICAL_SYSTEM_EM,
		NexusBaseClass.NX_PROCESS,
		NexusBaseClass.NX_PROCESS,
		NexusBaseClass.NX_PROCESS);

	public NXimage_set_em_seImpl() {
		super();
	}

	public NXimage_set_em_seImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXimage_set_em_se.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_IMAGE_SET_EM_SE;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}
	

	@Override
	public NXdata getData() {
		// dataNodeName = NX_DATA
		return getChild("data", NXdata.class);
	}

	@Override
	public void setData(NXdata dataGroup) {
		putChild("data", dataGroup);
	}

	@Override
	public NXdata getData(String name) {
		return getChild(name, NXdata.class);
	}

	@Override
	public void setData(String name, NXdata data) {
		putChild(name, data);
	}

	@Override
	public Map<String, NXdata> getAllData() {
		return getChildren(NXdata.class);
	}
	
	@Override
	public void setAllData(Map<String, NXdata> data) {
		setChildren(data);
	}

	@Override
	public IDataset getRoi() {
		return getDataset(NX_ROI);
	}

	@Override
	public Number getRoiScalar() {
		return getNumber(NX_ROI);
	}

	@Override
	public DataNode setRoi(IDataset roiDataset) {
		return setDataset(NX_ROI, roiDataset);
	}

	@Override
	public DataNode setRoiScalar(Number roiValue) {
		return setField(NX_ROI, roiValue);
	}

	@Override
	public IDataset getDwell_time() {
		return getDataset(NX_DWELL_TIME);
	}

	@Override
	public Double getDwell_timeScalar() {
		return getDouble(NX_DWELL_TIME);
	}

	@Override
	public DataNode setDwell_time(IDataset dwell_timeDataset) {
		return setDataset(NX_DWELL_TIME, dwell_timeDataset);
	}

	@Override
	public DataNode setDwell_timeScalar(Double dwell_timeValue) {
		return setField(NX_DWELL_TIME, dwell_timeValue);
	}

	@Override
	public IDataset getNumber_of_frames_averaged() {
		return getDataset(NX_NUMBER_OF_FRAMES_AVERAGED);
	}

	@Override
	public Long getNumber_of_frames_averagedScalar() {
		return getLong(NX_NUMBER_OF_FRAMES_AVERAGED);
	}

	@Override
	public DataNode setNumber_of_frames_averaged(IDataset number_of_frames_averagedDataset) {
		return setDataset(NX_NUMBER_OF_FRAMES_AVERAGED, number_of_frames_averagedDataset);
	}

	@Override
	public DataNode setNumber_of_frames_averagedScalar(Long number_of_frames_averagedValue) {
		return setField(NX_NUMBER_OF_FRAMES_AVERAGED, number_of_frames_averagedValue);
	}

	@Override
	public IDataset getBias_voltage() {
		return getDataset(NX_BIAS_VOLTAGE);
	}

	@Override
	public Double getBias_voltageScalar() {
		return getDouble(NX_BIAS_VOLTAGE);
	}

	@Override
	public DataNode setBias_voltage(IDataset bias_voltageDataset) {
		return setDataset(NX_BIAS_VOLTAGE, bias_voltageDataset);
	}

	@Override
	public DataNode setBias_voltageScalar(Double bias_voltageValue) {
		return setField(NX_BIAS_VOLTAGE, bias_voltageValue);
	}

	@Override
	public NXoptical_system_em getOptical_system_em() {
		// dataNodeName = NX_OPTICAL_SYSTEM_EM
		return getChild("optical_system_em", NXoptical_system_em.class);
	}

	@Override
	public void setOptical_system_em(NXoptical_system_em optical_system_emGroup) {
		putChild("optical_system_em", optical_system_emGroup);
	}

	@Override
	public NXoptical_system_em getOptical_system_em(String name) {
		return getChild(name, NXoptical_system_em.class);
	}

	@Override
	public void setOptical_system_em(String name, NXoptical_system_em optical_system_em) {
		putChild(name, optical_system_em);
	}

	@Override
	public Map<String, NXoptical_system_em> getAllOptical_system_em() {
		return getChildren(NXoptical_system_em.class);
	}
	
	@Override
	public void setAllOptical_system_em(Map<String, NXoptical_system_em> optical_system_em) {
		setChildren(optical_system_em);
	}

	@Override
	public NXprocess getScan_rotation() {
		// dataNodeName = NX_SCAN_ROTATION
		return getChild("scan_rotation", NXprocess.class);
	}

	@Override
	public void setScan_rotation(NXprocess scan_rotationGroup) {
		putChild("scan_rotation", scan_rotationGroup);
	}

	@Override
	public NXprocess getTilt_correction() {
		// dataNodeName = NX_TILT_CORRECTION
		return getChild("tilt_correction", NXprocess.class);
	}

	@Override
	public void setTilt_correction(NXprocess tilt_correctionGroup) {
		putChild("tilt_correction", tilt_correctionGroup);
	}

	@Override
	public NXprocess getDynamic_focus() {
		// dataNodeName = NX_DYNAMIC_FOCUS
		return getChild("dynamic_focus", NXprocess.class);
	}

	@Override
	public void setDynamic_focus(NXprocess dynamic_focusGroup) {
		putChild("dynamic_focus", dynamic_focusGroup);
	}

}

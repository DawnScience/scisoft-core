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

import java.util.Date;
import java.util.Set;
import java.util.EnumSet;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * A device that reduces the intensity of a beam by attenuation.
 * If uncertain whether to use :ref:`NXfilter` (band-pass filter)
 * or :ref:`NXattenuator` (reduces beam intensity), then choose
 * :ref:`NXattenuator`.
 * 
 */
public class NXattenuatorImpl extends NXobjectImpl implements NXattenuator {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXattenuatorImpl() {
		super();
	}

	public NXattenuatorImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXattenuator.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_ATTENUATOR;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}
	

	@Override
	public IDataset getDistance() {
		return getDataset(NX_DISTANCE);
	}

	@Override
	public Double getDistanceScalar() {
		return getDouble(NX_DISTANCE);
	}

	@Override
	public DataNode setDistance(IDataset distanceDataset) {
		return setDataset(NX_DISTANCE, distanceDataset);
	}

	@Override
	public DataNode setDistanceScalar(Double distanceValue) {
		return setField(NX_DISTANCE, distanceValue);
	}

	@Override
	public IDataset getType() {
		return getDataset(NX_TYPE);
	}

	@Override
	public String getTypeScalar() {
		return getString(NX_TYPE);
	}

	@Override
	public DataNode setType(IDataset typeDataset) {
		return setDataset(NX_TYPE, typeDataset);
	}

	@Override
	public DataNode setTypeScalar(String typeValue) {
		return setString(NX_TYPE, typeValue);
	}

	@Override
	public IDataset getThickness() {
		return getDataset(NX_THICKNESS);
	}

	@Override
	public Double getThicknessScalar() {
		return getDouble(NX_THICKNESS);
	}

	@Override
	public DataNode setThickness(IDataset thicknessDataset) {
		return setDataset(NX_THICKNESS, thicknessDataset);
	}

	@Override
	public DataNode setThicknessScalar(Double thicknessValue) {
		return setField(NX_THICKNESS, thicknessValue);
	}

	@Override
	public IDataset getScattering_cross_section() {
		return getDataset(NX_SCATTERING_CROSS_SECTION);
	}

	@Override
	public Double getScattering_cross_sectionScalar() {
		return getDouble(NX_SCATTERING_CROSS_SECTION);
	}

	@Override
	public DataNode setScattering_cross_section(IDataset scattering_cross_sectionDataset) {
		return setDataset(NX_SCATTERING_CROSS_SECTION, scattering_cross_sectionDataset);
	}

	@Override
	public DataNode setScattering_cross_sectionScalar(Double scattering_cross_sectionValue) {
		return setField(NX_SCATTERING_CROSS_SECTION, scattering_cross_sectionValue);
	}

	@Override
	public IDataset getAbsorption_cross_section() {
		return getDataset(NX_ABSORPTION_CROSS_SECTION);
	}

	@Override
	public Double getAbsorption_cross_sectionScalar() {
		return getDouble(NX_ABSORPTION_CROSS_SECTION);
	}

	@Override
	public DataNode setAbsorption_cross_section(IDataset absorption_cross_sectionDataset) {
		return setDataset(NX_ABSORPTION_CROSS_SECTION, absorption_cross_sectionDataset);
	}

	@Override
	public DataNode setAbsorption_cross_sectionScalar(Double absorption_cross_sectionValue) {
		return setField(NX_ABSORPTION_CROSS_SECTION, absorption_cross_sectionValue);
	}

	@Override
	public IDataset getAttenuator_transmission() {
		return getDataset(NX_ATTENUATOR_TRANSMISSION);
	}

	@Override
	public Double getAttenuator_transmissionScalar() {
		return getDouble(NX_ATTENUATOR_TRANSMISSION);
	}

	@Override
	public DataNode setAttenuator_transmission(IDataset attenuator_transmissionDataset) {
		return setDataset(NX_ATTENUATOR_TRANSMISSION, attenuator_transmissionDataset);
	}

	@Override
	public DataNode setAttenuator_transmissionScalar(Double attenuator_transmissionValue) {
		return setField(NX_ATTENUATOR_TRANSMISSION, attenuator_transmissionValue);
	}

	@Override
	public IDataset getStatus() {
		return getDataset(NX_STATUS);
	}

	@Override
	public String getStatusScalar() {
		return getString(NX_STATUS);
	}

	@Override
	public DataNode setStatus(IDataset statusDataset) {
		return setDataset(NX_STATUS, statusDataset);
	}

	@Override
	public DataNode setStatusScalar(String statusValue) {
		return setString(NX_STATUS, statusValue);
	}

	@Override
	public Date getStatusAttributeTime() {
		return getAttrDate(NX_STATUS, NX_STATUS_ATTRIBUTE_TIME);
	}

	@Override
	public void setStatusAttributeTime(Date timeValue) {
		setAttribute(NX_STATUS, NX_STATUS_ATTRIBUTE_TIME, timeValue);
	}

	@Override
	public String getAttributeDefault() {
		return getAttrString(null, NX_ATTRIBUTE_DEFAULT);
	}

	@Override
	public void setAttributeDefault(String defaultValue) {
		setAttribute(null, NX_ATTRIBUTE_DEFAULT, defaultValue);
	}

}

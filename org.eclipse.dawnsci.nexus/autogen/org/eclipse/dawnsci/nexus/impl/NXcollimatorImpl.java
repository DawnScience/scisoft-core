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
 * A beamline collimator.
 * 
 */
public class NXcollimatorImpl extends NXobjectImpl implements NXcollimator {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_GEOMETRY,
		NexusBaseClass.NX_LOG);

	public NXcollimatorImpl() {
		super();
	}

	public NXcollimatorImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcollimator.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_COLLIMATOR;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}
	

	@Override
	public NXgeometry getGeometry() {
		// dataNodeName = NX_GEOMETRY
		return getChild("geometry", NXgeometry.class);
	}

	@Override
	public void setGeometry(NXgeometry geometryGroup) {
		putChild("geometry", geometryGroup);
	}

	@Override
	public NXgeometry getGeometry(String name) {
		return getChild(name, NXgeometry.class);
	}

	@Override
	public void setGeometry(String name, NXgeometry geometry) {
		putChild(name, geometry);
	}

	@Override
	public Map<String, NXgeometry> getAllGeometry() {
		return getChildren(NXgeometry.class);
	}
	
	@Override
	public void setAllGeometry(Map<String, NXgeometry> geometry) {
		setChildren(geometry);
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
	public IDataset getSoller_angle() {
		return getDataset(NX_SOLLER_ANGLE);
	}

	@Override
	public Double getSoller_angleScalar() {
		return getDouble(NX_SOLLER_ANGLE);
	}

	@Override
	public DataNode setSoller_angle(IDataset soller_angleDataset) {
		return setDataset(NX_SOLLER_ANGLE, soller_angleDataset);
	}

	@Override
	public DataNode setSoller_angleScalar(Double soller_angleValue) {
		return setField(NX_SOLLER_ANGLE, soller_angleValue);
	}

	@Override
	public IDataset getDivergence_x() {
		return getDataset(NX_DIVERGENCE_X);
	}

	@Override
	public Double getDivergence_xScalar() {
		return getDouble(NX_DIVERGENCE_X);
	}

	@Override
	public DataNode setDivergence_x(IDataset divergence_xDataset) {
		return setDataset(NX_DIVERGENCE_X, divergence_xDataset);
	}

	@Override
	public DataNode setDivergence_xScalar(Double divergence_xValue) {
		return setField(NX_DIVERGENCE_X, divergence_xValue);
	}

	@Override
	public IDataset getDivergence_y() {
		return getDataset(NX_DIVERGENCE_Y);
	}

	@Override
	public Double getDivergence_yScalar() {
		return getDouble(NX_DIVERGENCE_Y);
	}

	@Override
	public DataNode setDivergence_y(IDataset divergence_yDataset) {
		return setDataset(NX_DIVERGENCE_Y, divergence_yDataset);
	}

	@Override
	public DataNode setDivergence_yScalar(Double divergence_yValue) {
		return setField(NX_DIVERGENCE_Y, divergence_yValue);
	}

	@Override
	public IDataset getFrequency() {
		return getDataset(NX_FREQUENCY);
	}

	@Override
	public Double getFrequencyScalar() {
		return getDouble(NX_FREQUENCY);
	}

	@Override
	public DataNode setFrequency(IDataset frequencyDataset) {
		return setDataset(NX_FREQUENCY, frequencyDataset);
	}

	@Override
	public DataNode setFrequencyScalar(Double frequencyValue) {
		return setField(NX_FREQUENCY, frequencyValue);
	}

	@Override
	public NXlog getFrequency_log() {
		// dataNodeName = NX_FREQUENCY_LOG
		return getChild("frequency_log", NXlog.class);
	}

	@Override
	public void setFrequency_log(NXlog frequency_logGroup) {
		putChild("frequency_log", frequency_logGroup);
	}

	@Override
	public IDataset getBlade_thickness() {
		return getDataset(NX_BLADE_THICKNESS);
	}

	@Override
	public Double getBlade_thicknessScalar() {
		return getDouble(NX_BLADE_THICKNESS);
	}

	@Override
	public DataNode setBlade_thickness(IDataset blade_thicknessDataset) {
		return setDataset(NX_BLADE_THICKNESS, blade_thicknessDataset);
	}

	@Override
	public DataNode setBlade_thicknessScalar(Double blade_thicknessValue) {
		return setField(NX_BLADE_THICKNESS, blade_thicknessValue);
	}

	@Override
	public IDataset getBlade_spacing() {
		return getDataset(NX_BLADE_SPACING);
	}

	@Override
	public Double getBlade_spacingScalar() {
		return getDouble(NX_BLADE_SPACING);
	}

	@Override
	public DataNode setBlade_spacing(IDataset blade_spacingDataset) {
		return setDataset(NX_BLADE_SPACING, blade_spacingDataset);
	}

	@Override
	public DataNode setBlade_spacingScalar(Double blade_spacingValue) {
		return setField(NX_BLADE_SPACING, blade_spacingValue);
	}

	@Override
	public IDataset getAbsorbing_material() {
		return getDataset(NX_ABSORBING_MATERIAL);
	}

	@Override
	public String getAbsorbing_materialScalar() {
		return getString(NX_ABSORBING_MATERIAL);
	}

	@Override
	public DataNode setAbsorbing_material(IDataset absorbing_materialDataset) {
		return setDataset(NX_ABSORBING_MATERIAL, absorbing_materialDataset);
	}

	@Override
	public DataNode setAbsorbing_materialScalar(String absorbing_materialValue) {
		return setString(NX_ABSORBING_MATERIAL, absorbing_materialValue);
	}

	@Override
	public IDataset getTransmitting_material() {
		return getDataset(NX_TRANSMITTING_MATERIAL);
	}

	@Override
	public String getTransmitting_materialScalar() {
		return getString(NX_TRANSMITTING_MATERIAL);
	}

	@Override
	public DataNode setTransmitting_material(IDataset transmitting_materialDataset) {
		return setDataset(NX_TRANSMITTING_MATERIAL, transmitting_materialDataset);
	}

	@Override
	public DataNode setTransmitting_materialScalar(String transmitting_materialValue) {
		return setString(NX_TRANSMITTING_MATERIAL, transmitting_materialValue);
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

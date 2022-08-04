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
 * A neutron velocity selector
 * 
 */
public class NXvelocity_selectorImpl extends NXobjectImpl implements NXvelocity_selector {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_GEOMETRY);

	public NXvelocity_selectorImpl() {
		super();
	}

	public NXvelocity_selectorImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXvelocity_selector.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_VELOCITY_SELECTOR;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
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
	public IDataset getRotation_speed() {
		return getDataset(NX_ROTATION_SPEED);
	}

	@Override
	public Double getRotation_speedScalar() {
		return getDouble(NX_ROTATION_SPEED);
	}

	@Override
	public DataNode setRotation_speed(IDataset rotation_speedDataset) {
		return setDataset(NX_ROTATION_SPEED, rotation_speedDataset);
	}

	@Override
	public DataNode setRotation_speedScalar(Double rotation_speedValue) {
		return setField(NX_ROTATION_SPEED, rotation_speedValue);
	}

	@Override
	public IDataset getRadius() {
		return getDataset(NX_RADIUS);
	}

	@Override
	public Double getRadiusScalar() {
		return getDouble(NX_RADIUS);
	}

	@Override
	public DataNode setRadius(IDataset radiusDataset) {
		return setDataset(NX_RADIUS, radiusDataset);
	}

	@Override
	public DataNode setRadiusScalar(Double radiusValue) {
		return setField(NX_RADIUS, radiusValue);
	}

	@Override
	public IDataset getSpwidth() {
		return getDataset(NX_SPWIDTH);
	}

	@Override
	public Double getSpwidthScalar() {
		return getDouble(NX_SPWIDTH);
	}

	@Override
	public DataNode setSpwidth(IDataset spwidthDataset) {
		return setDataset(NX_SPWIDTH, spwidthDataset);
	}

	@Override
	public DataNode setSpwidthScalar(Double spwidthValue) {
		return setField(NX_SPWIDTH, spwidthValue);
	}

	@Override
	public IDataset getLength() {
		return getDataset(NX_LENGTH);
	}

	@Override
	public Double getLengthScalar() {
		return getDouble(NX_LENGTH);
	}

	@Override
	public DataNode setLength(IDataset lengthDataset) {
		return setDataset(NX_LENGTH, lengthDataset);
	}

	@Override
	public DataNode setLengthScalar(Double lengthValue) {
		return setField(NX_LENGTH, lengthValue);
	}

	@Override
	public IDataset getNum() {
		return getDataset(NX_NUM);
	}

	@Override
	public Long getNumScalar() {
		return getLong(NX_NUM);
	}

	@Override
	public DataNode setNum(IDataset numDataset) {
		return setDataset(NX_NUM, numDataset);
	}

	@Override
	public DataNode setNumScalar(Long numValue) {
		return setField(NX_NUM, numValue);
	}

	@Override
	public IDataset getTwist() {
		return getDataset(NX_TWIST);
	}

	@Override
	public Double getTwistScalar() {
		return getDouble(NX_TWIST);
	}

	@Override
	public DataNode setTwist(IDataset twistDataset) {
		return setDataset(NX_TWIST, twistDataset);
	}

	@Override
	public DataNode setTwistScalar(Double twistValue) {
		return setField(NX_TWIST, twistValue);
	}

	@Override
	public IDataset getTable() {
		return getDataset(NX_TABLE);
	}

	@Override
	public Double getTableScalar() {
		return getDouble(NX_TABLE);
	}

	@Override
	public DataNode setTable(IDataset tableDataset) {
		return setDataset(NX_TABLE, tableDataset);
	}

	@Override
	public DataNode setTableScalar(Double tableValue) {
		return setField(NX_TABLE, tableValue);
	}

	@Override
	public IDataset getHeight() {
		return getDataset(NX_HEIGHT);
	}

	@Override
	public Double getHeightScalar() {
		return getDouble(NX_HEIGHT);
	}

	@Override
	public DataNode setHeight(IDataset heightDataset) {
		return setDataset(NX_HEIGHT, heightDataset);
	}

	@Override
	public DataNode setHeightScalar(Double heightValue) {
		return setField(NX_HEIGHT, heightValue);
	}

	@Override
	public IDataset getWidth() {
		return getDataset(NX_WIDTH);
	}

	@Override
	public Double getWidthScalar() {
		return getDouble(NX_WIDTH);
	}

	@Override
	public DataNode setWidth(IDataset widthDataset) {
		return setDataset(NX_WIDTH, widthDataset);
	}

	@Override
	public DataNode setWidthScalar(Double widthValue) {
		return setField(NX_WIDTH, widthValue);
	}

	@Override
	public IDataset getWavelength() {
		return getDataset(NX_WAVELENGTH);
	}

	@Override
	public Double getWavelengthScalar() {
		return getDouble(NX_WAVELENGTH);
	}

	@Override
	public DataNode setWavelength(IDataset wavelengthDataset) {
		return setDataset(NX_WAVELENGTH, wavelengthDataset);
	}

	@Override
	public DataNode setWavelengthScalar(Double wavelengthValue) {
		return setField(NX_WAVELENGTH, wavelengthValue);
	}

	@Override
	public IDataset getWavelength_spread() {
		return getDataset(NX_WAVELENGTH_SPREAD);
	}

	@Override
	public Double getWavelength_spreadScalar() {
		return getDouble(NX_WAVELENGTH_SPREAD);
	}

	@Override
	public DataNode setWavelength_spread(IDataset wavelength_spreadDataset) {
		return setDataset(NX_WAVELENGTH_SPREAD, wavelength_spreadDataset);
	}

	@Override
	public DataNode setWavelength_spreadScalar(Double wavelength_spreadValue) {
		return setField(NX_WAVELENGTH_SPREAD, wavelength_spreadValue);
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
	public String getAttributeDefault() {
		return getAttrString(null, NX_ATTRIBUTE_DEFAULT);
	}

	@Override
	public void setAttributeDefault(String defaultValue) {
		setAttribute(null, NX_ATTRIBUTE_DEFAULT, defaultValue);
	}

}

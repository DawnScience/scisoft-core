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
 * A Fermi chopper, possibly with curved slits.
 * 
 */
public class NXfermi_chopperImpl extends NXobjectImpl implements NXfermi_chopper {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_GEOMETRY);

	public NXfermi_chopperImpl() {
		super();
	}

	public NXfermi_chopperImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXfermi_chopper.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_FERMI_CHOPPER;
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
	public IDataset getSlit() {
		return getDataset(NX_SLIT);
	}

	@Override
	public Double getSlitScalar() {
		return getDouble(NX_SLIT);
	}

	@Override
	public DataNode setSlit(IDataset slitDataset) {
		return setDataset(NX_SLIT, slitDataset);
	}

	@Override
	public DataNode setSlitScalar(Double slitValue) {
		return setField(NX_SLIT, slitValue);
	}

	@Override
	public IDataset getR_slit() {
		return getDataset(NX_R_SLIT);
	}

	@Override
	public Double getR_slitScalar() {
		return getDouble(NX_R_SLIT);
	}

	@Override
	public DataNode setR_slit(IDataset r_slitDataset) {
		return setDataset(NX_R_SLIT, r_slitDataset);
	}

	@Override
	public DataNode setR_slitScalar(Double r_slitValue) {
		return setField(NX_R_SLIT, r_slitValue);
	}

	@Override
	public IDataset getNumber() {
		return getDataset(NX_NUMBER);
	}

	@Override
	public Long getNumberScalar() {
		return getLong(NX_NUMBER);
	}

	@Override
	public DataNode setNumber(IDataset numberDataset) {
		return setDataset(NX_NUMBER, numberDataset);
	}

	@Override
	public DataNode setNumberScalar(Long numberValue) {
		return setField(NX_NUMBER, numberValue);
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
	public IDataset getEnergy() {
		return getDataset(NX_ENERGY);
	}

	@Override
	public Double getEnergyScalar() {
		return getDouble(NX_ENERGY);
	}

	@Override
	public DataNode setEnergy(IDataset energyDataset) {
		return setDataset(NX_ENERGY, energyDataset);
	}

	@Override
	public DataNode setEnergyScalar(Double energyValue) {
		return setField(NX_ENERGY, energyValue);
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

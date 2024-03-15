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
 * Subclass of NXelectronanalyser to describe the energy dispersion section of a
 * photoelectron analyser.

 */
public class NXenergydispersionImpl extends NXobjectImpl implements NXenergydispersion {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_APERTURE,
		NexusBaseClass.NX_DEFLECTOR,
		NexusBaseClass.NX_LENS_EM);

	public NXenergydispersionImpl() {
		super();
	}

	public NXenergydispersionImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXenergydispersion.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_ENERGYDISPERSION;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public IDataset getScheme() {
		return getDataset(NX_SCHEME);
	}

	@Override
	public String getSchemeScalar() {
		return getString(NX_SCHEME);
	}

	@Override
	public DataNode setScheme(IDataset schemeDataset) {
		return setDataset(NX_SCHEME, schemeDataset);
	}

	@Override
	public DataNode setSchemeScalar(String schemeValue) {
		return setString(NX_SCHEME, schemeValue);
	}

	@Override
	public IDataset getPass_energy() {
		return getDataset(NX_PASS_ENERGY);
	}

	@Override
	public Double getPass_energyScalar() {
		return getDouble(NX_PASS_ENERGY);
	}

	@Override
	public DataNode setPass_energy(IDataset pass_energyDataset) {
		return setDataset(NX_PASS_ENERGY, pass_energyDataset);
	}

	@Override
	public DataNode setPass_energyScalar(Double pass_energyValue) {
		return setField(NX_PASS_ENERGY, pass_energyValue);
	}

	@Override
	public IDataset getCenter_energy() {
		return getDataset(NX_CENTER_ENERGY);
	}

	@Override
	public Double getCenter_energyScalar() {
		return getDouble(NX_CENTER_ENERGY);
	}

	@Override
	public DataNode setCenter_energy(IDataset center_energyDataset) {
		return setDataset(NX_CENTER_ENERGY, center_energyDataset);
	}

	@Override
	public DataNode setCenter_energyScalar(Double center_energyValue) {
		return setField(NX_CENTER_ENERGY, center_energyValue);
	}

	@Override
	public IDataset getEnergy_interval() {
		return getDataset(NX_ENERGY_INTERVAL);
	}

	@Override
	public Double getEnergy_intervalScalar() {
		return getDouble(NX_ENERGY_INTERVAL);
	}

	@Override
	public DataNode setEnergy_interval(IDataset energy_intervalDataset) {
		return setDataset(NX_ENERGY_INTERVAL, energy_intervalDataset);
	}

	@Override
	public DataNode setEnergy_intervalScalar(Double energy_intervalValue) {
		return setField(NX_ENERGY_INTERVAL, energy_intervalValue);
	}

	@Override
	public NXaperture getAperture() {
		// dataNodeName = NX_APERTURE
		return getChild("aperture", NXaperture.class);
	}

	@Override
	public void setAperture(NXaperture apertureGroup) {
		putChild("aperture", apertureGroup);
	}

	@Override
	public NXaperture getAperture(String name) {
		return getChild(name, NXaperture.class);
	}

	@Override
	public void setAperture(String name, NXaperture aperture) {
		putChild(name, aperture);
	}

	@Override
	public Map<String, NXaperture> getAllAperture() {
		return getChildren(NXaperture.class);
	}

	@Override
	public void setAllAperture(Map<String, NXaperture> aperture) {
		setChildren(aperture);
	}

	@Override
	public IDataset getDiameter() {
		return getDataset(NX_DIAMETER);
	}

	@Override
	public Double getDiameterScalar() {
		return getDouble(NX_DIAMETER);
	}

	@Override
	public DataNode setDiameter(IDataset diameterDataset) {
		return setDataset(NX_DIAMETER, diameterDataset);
	}

	@Override
	public DataNode setDiameterScalar(Double diameterValue) {
		return setField(NX_DIAMETER, diameterValue);
	}

	@Override
	public IDataset getEnergy_scan_mode() {
		return getDataset(NX_ENERGY_SCAN_MODE);
	}

	@Override
	public String getEnergy_scan_modeScalar() {
		return getString(NX_ENERGY_SCAN_MODE);
	}

	@Override
	public DataNode setEnergy_scan_mode(IDataset energy_scan_modeDataset) {
		return setDataset(NX_ENERGY_SCAN_MODE, energy_scan_modeDataset);
	}

	@Override
	public DataNode setEnergy_scan_modeScalar(String energy_scan_modeValue) {
		return setString(NX_ENERGY_SCAN_MODE, energy_scan_modeValue);
	}

	@Override
	public IDataset getTof_distance() {
		return getDataset(NX_TOF_DISTANCE);
	}

	@Override
	public Double getTof_distanceScalar() {
		return getDouble(NX_TOF_DISTANCE);
	}

	@Override
	public DataNode setTof_distance(IDataset tof_distanceDataset) {
		return setDataset(NX_TOF_DISTANCE, tof_distanceDataset);
	}

	@Override
	public DataNode setTof_distanceScalar(Double tof_distanceValue) {
		return setField(NX_TOF_DISTANCE, tof_distanceValue);
	}

	@Override
	public NXdeflector getDeflector() {
		// dataNodeName = NX_DEFLECTOR
		return getChild("deflector", NXdeflector.class);
	}

	@Override
	public void setDeflector(NXdeflector deflectorGroup) {
		putChild("deflector", deflectorGroup);
	}

	@Override
	public NXdeflector getDeflector(String name) {
		return getChild(name, NXdeflector.class);
	}

	@Override
	public void setDeflector(String name, NXdeflector deflector) {
		putChild(name, deflector);
	}

	@Override
	public Map<String, NXdeflector> getAllDeflector() {
		return getChildren(NXdeflector.class);
	}

	@Override
	public void setAllDeflector(Map<String, NXdeflector> deflector) {
		setChildren(deflector);
	}

	@Override
	public NXlens_em getLens_em() {
		// dataNodeName = NX_LENS_EM
		return getChild("lens_em", NXlens_em.class);
	}

	@Override
	public void setLens_em(NXlens_em lens_emGroup) {
		putChild("lens_em", lens_emGroup);
	}

	@Override
	public NXlens_em getLens_em(String name) {
		return getChild(name, NXlens_em.class);
	}

	@Override
	public void setLens_em(String name, NXlens_em lens_em) {
		putChild(name, lens_em);
	}

	@Override
	public Map<String, NXlens_em> getAllLens_em() {
		return getChildren(NXlens_em.class);
	}

	@Override
	public void setAllLens_em(Map<String, NXlens_em> lens_em) {
		setChildren(lens_em);
	}

}

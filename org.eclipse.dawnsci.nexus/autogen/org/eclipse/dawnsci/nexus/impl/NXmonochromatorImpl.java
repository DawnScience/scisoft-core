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
import org.eclipse.january.dataset.Dataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * A wavelength defining device.
 * This is a base class for everything which
 * selects a wavelength or energy, be it a
 * monochromator crystal, a velocity selector,
 * an undulator or whatever.
 * The expected units are:
 * * wavelength: angstrom
 * * energy: eV

 */
public class NXmonochromatorImpl extends NXobjectImpl implements NXmonochromator {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_DATA,
		NexusBaseClass.NX_GEOMETRY,
		NexusBaseClass.NX_OFF_GEOMETRY,
		NexusBaseClass.NX_CRYSTAL,
		NexusBaseClass.NX_VELOCITY_SELECTOR,
		NexusBaseClass.NX_GRATING,
		NexusBaseClass.NX_TRANSFORMATIONS);

	public NXmonochromatorImpl() {
		super();
	}

	public NXmonochromatorImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXmonochromator.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_MONOCHROMATOR;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getWavelength() {
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
	@Deprecated
	public Dataset getWavelength_error() {
		return getDataset(NX_WAVELENGTH_ERROR);
	}

	@Override
	@Deprecated
	public Double getWavelength_errorScalar() {
		return getDouble(NX_WAVELENGTH_ERROR);
	}

	@Override
	@Deprecated
	public DataNode setWavelength_error(IDataset wavelength_errorDataset) {
		return setDataset(NX_WAVELENGTH_ERROR, wavelength_errorDataset);
	}

	@Override
	@Deprecated
	public DataNode setWavelength_errorScalar(Double wavelength_errorValue) {
		return setField(NX_WAVELENGTH_ERROR, wavelength_errorValue);
	}

	@Override
	public Dataset getWavelength_errors() {
		return getDataset(NX_WAVELENGTH_ERRORS);
	}

	@Override
	public Double getWavelength_errorsScalar() {
		return getDouble(NX_WAVELENGTH_ERRORS);
	}

	@Override
	public DataNode setWavelength_errors(IDataset wavelength_errorsDataset) {
		return setDataset(NX_WAVELENGTH_ERRORS, wavelength_errorsDataset);
	}

	@Override
	public DataNode setWavelength_errorsScalar(Double wavelength_errorsValue) {
		return setField(NX_WAVELENGTH_ERRORS, wavelength_errorsValue);
	}

	@Override
	public Dataset getEnergy() {
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
	@Deprecated
	public Dataset getEnergy_error() {
		return getDataset(NX_ENERGY_ERROR);
	}

	@Override
	@Deprecated
	public Double getEnergy_errorScalar() {
		return getDouble(NX_ENERGY_ERROR);
	}

	@Override
	@Deprecated
	public DataNode setEnergy_error(IDataset energy_errorDataset) {
		return setDataset(NX_ENERGY_ERROR, energy_errorDataset);
	}

	@Override
	@Deprecated
	public DataNode setEnergy_errorScalar(Double energy_errorValue) {
		return setField(NX_ENERGY_ERROR, energy_errorValue);
	}

	@Override
	public Dataset getEnergy_errors() {
		return getDataset(NX_ENERGY_ERRORS);
	}

	@Override
	public Double getEnergy_errorsScalar() {
		return getDouble(NX_ENERGY_ERRORS);
	}

	@Override
	public DataNode setEnergy_errors(IDataset energy_errorsDataset) {
		return setDataset(NX_ENERGY_ERRORS, energy_errorsDataset);
	}

	@Override
	public DataNode setEnergy_errorsScalar(Double energy_errorsValue) {
		return setField(NX_ENERGY_ERRORS, energy_errorsValue);
	}

	@Override
	public NXdata getDistribution() {
		// dataNodeName = NX_DISTRIBUTION
		return getChild("distribution", NXdata.class);
	}

	@Override
	public void setDistribution(NXdata distributionGroup) {
		putChild("distribution", distributionGroup);
	}

	@Override
	@Deprecated
	public NXgeometry getGeometry() {
		// dataNodeName = NX_GEOMETRY
		return getChild("geometry", NXgeometry.class);
	}

	@Override
	@Deprecated
	public void setGeometry(NXgeometry geometryGroup) {
		putChild("geometry", geometryGroup);
	}

	@Override
	public NXoff_geometry getOff_geometry() {
		// dataNodeName = NX_OFF_GEOMETRY
		return getChild("off_geometry", NXoff_geometry.class);
	}

	@Override
	public void setOff_geometry(NXoff_geometry off_geometryGroup) {
		putChild("off_geometry", off_geometryGroup);
	}

	@Override
	public NXoff_geometry getOff_geometry(String name) {
		return getChild(name, NXoff_geometry.class);
	}

	@Override
	public void setOff_geometry(String name, NXoff_geometry off_geometry) {
		putChild(name, off_geometry);
	}

	@Override
	public Map<String, NXoff_geometry> getAllOff_geometry() {
		return getChildren(NXoff_geometry.class);
	}

	@Override
	public void setAllOff_geometry(Map<String, NXoff_geometry> off_geometry) {
		setChildren(off_geometry);
	}

	@Override
	public NXcrystal getCrystal() {
		// dataNodeName = NX_CRYSTAL
		return getChild("crystal", NXcrystal.class);
	}

	@Override
	public void setCrystal(NXcrystal crystalGroup) {
		putChild("crystal", crystalGroup);
	}

	@Override
	public NXcrystal getCrystal(String name) {
		return getChild(name, NXcrystal.class);
	}

	@Override
	public void setCrystal(String name, NXcrystal crystal) {
		putChild(name, crystal);
	}

	@Override
	public Map<String, NXcrystal> getAllCrystal() {
		return getChildren(NXcrystal.class);
	}

	@Override
	public void setAllCrystal(Map<String, NXcrystal> crystal) {
		setChildren(crystal);
	}

	@Override
	public NXvelocity_selector getVelocity_selector() {
		// dataNodeName = NX_VELOCITY_SELECTOR
		return getChild("velocity_selector", NXvelocity_selector.class);
	}

	@Override
	public void setVelocity_selector(NXvelocity_selector velocity_selectorGroup) {
		putChild("velocity_selector", velocity_selectorGroup);
	}

	@Override
	public NXvelocity_selector getVelocity_selector(String name) {
		return getChild(name, NXvelocity_selector.class);
	}

	@Override
	public void setVelocity_selector(String name, NXvelocity_selector velocity_selector) {
		putChild(name, velocity_selector);
	}

	@Override
	public Map<String, NXvelocity_selector> getAllVelocity_selector() {
		return getChildren(NXvelocity_selector.class);
	}

	@Override
	public void setAllVelocity_selector(Map<String, NXvelocity_selector> velocity_selector) {
		setChildren(velocity_selector);
	}

	@Override
	public NXgrating getGrating() {
		// dataNodeName = NX_GRATING
		return getChild("grating", NXgrating.class);
	}

	@Override
	public void setGrating(NXgrating gratingGroup) {
		putChild("grating", gratingGroup);
	}

	@Override
	public NXgrating getGrating(String name) {
		return getChild(name, NXgrating.class);
	}

	@Override
	public void setGrating(String name, NXgrating grating) {
		putChild(name, grating);
	}

	@Override
	public Map<String, NXgrating> getAllGrating() {
		return getChildren(NXgrating.class);
	}

	@Override
	public void setAllGrating(Map<String, NXgrating> grating) {
		setChildren(grating);
	}

	@Override
	public String getAttributeDefault() {
		return getAttrString(null, NX_ATTRIBUTE_DEFAULT);
	}

	@Override
	public void setAttributeDefault(String defaultValue) {
		setAttribute(null, NX_ATTRIBUTE_DEFAULT, defaultValue);
	}

	@Override
	public Dataset getDepends_on() {
		return getDataset(NX_DEPENDS_ON);
	}

	@Override
	public String getDepends_onScalar() {
		return getString(NX_DEPENDS_ON);
	}

	@Override
	public DataNode setDepends_on(IDataset depends_onDataset) {
		return setDataset(NX_DEPENDS_ON, depends_onDataset);
	}

	@Override
	public DataNode setDepends_onScalar(String depends_onValue) {
		return setString(NX_DEPENDS_ON, depends_onValue);
	}

	@Override
	public NXtransformations getTransformations() {
		// dataNodeName = NX_TRANSFORMATIONS
		return getChild("transformations", NXtransformations.class);
	}

	@Override
	public void setTransformations(NXtransformations transformationsGroup) {
		putChild("transformations", transformationsGroup);
	}

	@Override
	public NXtransformations getTransformations(String name) {
		return getChild(name, NXtransformations.class);
	}

	@Override
	public void setTransformations(String name, NXtransformations transformations) {
		putChild(name, transformations);
	}

	@Override
	public Map<String, NXtransformations> getAllTransformations() {
		return getChildren(NXtransformations.class);
	}

	@Override
	public void setAllTransformations(Map<String, NXtransformations> transformations) {
		setChildren(transformations);
	}

}

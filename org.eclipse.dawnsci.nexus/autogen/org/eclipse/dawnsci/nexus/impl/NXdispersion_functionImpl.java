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
 * This describes a dispersion function for a material or layer

 */
public class NXdispersion_functionImpl extends NXobjectImpl implements NXdispersion_function {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_DISPERSION_SINGLE_PARAMETER,
		NexusBaseClass.NX_DISPERSION_REPEATED_PARAMETER);

	public NXdispersion_functionImpl() {
		super();
	}

	public NXdispersion_functionImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXdispersion_function.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_DISPERSION_FUNCTION;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public IDataset getModel_name() {
		return getDataset(NX_MODEL_NAME);
	}

	@Override
	public String getModel_nameScalar() {
		return getString(NX_MODEL_NAME);
	}

	@Override
	public DataNode setModel_name(IDataset model_nameDataset) {
		return setDataset(NX_MODEL_NAME, model_nameDataset);
	}

	@Override
	public DataNode setModel_nameScalar(String model_nameValue) {
		return setString(NX_MODEL_NAME, model_nameValue);
	}

	@Override
	public IDataset getFormula() {
		return getDataset(NX_FORMULA);
	}

	@Override
	public String getFormulaScalar() {
		return getString(NX_FORMULA);
	}

	@Override
	public DataNode setFormula(IDataset formulaDataset) {
		return setDataset(NX_FORMULA, formulaDataset);
	}

	@Override
	public DataNode setFormulaScalar(String formulaValue) {
		return setString(NX_FORMULA, formulaValue);
	}

	@Override
	public IDataset getConvention() {
		return getDataset(NX_CONVENTION);
	}

	@Override
	public String getConventionScalar() {
		return getString(NX_CONVENTION);
	}

	@Override
	public DataNode setConvention(IDataset conventionDataset) {
		return setDataset(NX_CONVENTION, conventionDataset);
	}

	@Override
	public DataNode setConventionScalar(String conventionValue) {
		return setString(NX_CONVENTION, conventionValue);
	}

	@Override
	public IDataset getEnergy_identifier() {
		return getDataset(NX_ENERGY_IDENTIFIER);
	}

	@Override
	public String getEnergy_identifierScalar() {
		return getString(NX_ENERGY_IDENTIFIER);
	}

	@Override
	public DataNode setEnergy_identifier(IDataset energy_identifierDataset) {
		return setDataset(NX_ENERGY_IDENTIFIER, energy_identifierDataset);
	}

	@Override
	public DataNode setEnergy_identifierScalar(String energy_identifierValue) {
		return setString(NX_ENERGY_IDENTIFIER, energy_identifierValue);
	}

	@Override
	public IDataset getEnergy_min() {
		return getDataset(NX_ENERGY_MIN);
	}

	@Override
	public Number getEnergy_minScalar() {
		return getNumber(NX_ENERGY_MIN);
	}

	@Override
	public DataNode setEnergy_min(IDataset energy_minDataset) {
		return setDataset(NX_ENERGY_MIN, energy_minDataset);
	}

	@Override
	public DataNode setEnergy_minScalar(Number energy_minValue) {
		return setField(NX_ENERGY_MIN, energy_minValue);
	}

	@Override
	public IDataset getEnergy_max() {
		return getDataset(NX_ENERGY_MAX);
	}

	@Override
	public Number getEnergy_maxScalar() {
		return getNumber(NX_ENERGY_MAX);
	}

	@Override
	public DataNode setEnergy_max(IDataset energy_maxDataset) {
		return setDataset(NX_ENERGY_MAX, energy_maxDataset);
	}

	@Override
	public DataNode setEnergy_maxScalar(Number energy_maxValue) {
		return setField(NX_ENERGY_MAX, energy_maxValue);
	}

	@Override
	public IDataset getEnergy_unit() {
		return getDataset(NX_ENERGY_UNIT);
	}

	@Override
	public Number getEnergy_unitScalar() {
		return getNumber(NX_ENERGY_UNIT);
	}

	@Override
	public DataNode setEnergy_unit(IDataset energy_unitDataset) {
		return setDataset(NX_ENERGY_UNIT, energy_unitDataset);
	}

	@Override
	public DataNode setEnergy_unitScalar(Number energy_unitValue) {
		return setField(NX_ENERGY_UNIT, energy_unitValue);
	}

	@Override
	public IDataset getWavelength_identifier() {
		return getDataset(NX_WAVELENGTH_IDENTIFIER);
	}

	@Override
	public String getWavelength_identifierScalar() {
		return getString(NX_WAVELENGTH_IDENTIFIER);
	}

	@Override
	public DataNode setWavelength_identifier(IDataset wavelength_identifierDataset) {
		return setDataset(NX_WAVELENGTH_IDENTIFIER, wavelength_identifierDataset);
	}

	@Override
	public DataNode setWavelength_identifierScalar(String wavelength_identifierValue) {
		return setString(NX_WAVELENGTH_IDENTIFIER, wavelength_identifierValue);
	}

	@Override
	public IDataset getWavelength_unit() {
		return getDataset(NX_WAVELENGTH_UNIT);
	}

	@Override
	public Number getWavelength_unitScalar() {
		return getNumber(NX_WAVELENGTH_UNIT);
	}

	@Override
	public DataNode setWavelength_unit(IDataset wavelength_unitDataset) {
		return setDataset(NX_WAVELENGTH_UNIT, wavelength_unitDataset);
	}

	@Override
	public DataNode setWavelength_unitScalar(Number wavelength_unitValue) {
		return setField(NX_WAVELENGTH_UNIT, wavelength_unitValue);
	}

	@Override
	public IDataset getWavelength_min() {
		return getDataset(NX_WAVELENGTH_MIN);
	}

	@Override
	public Number getWavelength_minScalar() {
		return getNumber(NX_WAVELENGTH_MIN);
	}

	@Override
	public DataNode setWavelength_min(IDataset wavelength_minDataset) {
		return setDataset(NX_WAVELENGTH_MIN, wavelength_minDataset);
	}

	@Override
	public DataNode setWavelength_minScalar(Number wavelength_minValue) {
		return setField(NX_WAVELENGTH_MIN, wavelength_minValue);
	}

	@Override
	public IDataset getWavelength_max() {
		return getDataset(NX_WAVELENGTH_MAX);
	}

	@Override
	public Number getWavelength_maxScalar() {
		return getNumber(NX_WAVELENGTH_MAX);
	}

	@Override
	public DataNode setWavelength_max(IDataset wavelength_maxDataset) {
		return setDataset(NX_WAVELENGTH_MAX, wavelength_maxDataset);
	}

	@Override
	public DataNode setWavelength_maxScalar(Number wavelength_maxValue) {
		return setField(NX_WAVELENGTH_MAX, wavelength_maxValue);
	}

	@Override
	public IDataset getRepresentation() {
		return getDataset(NX_REPRESENTATION);
	}

	@Override
	public String getRepresentationScalar() {
		return getString(NX_REPRESENTATION);
	}

	@Override
	public DataNode setRepresentation(IDataset representationDataset) {
		return setDataset(NX_REPRESENTATION, representationDataset);
	}

	@Override
	public DataNode setRepresentationScalar(String representationValue) {
		return setString(NX_REPRESENTATION, representationValue);
	}

	@Override
	public NXdispersion_single_parameter getDispersion_single_parameter() {
		// dataNodeName = NX_DISPERSION_SINGLE_PARAMETER
		return getChild("dispersion_single_parameter", NXdispersion_single_parameter.class);
	}

	@Override
	public void setDispersion_single_parameter(NXdispersion_single_parameter dispersion_single_parameterGroup) {
		putChild("dispersion_single_parameter", dispersion_single_parameterGroup);
	}

	@Override
	public NXdispersion_single_parameter getDispersion_single_parameter(String name) {
		return getChild(name, NXdispersion_single_parameter.class);
	}

	@Override
	public void setDispersion_single_parameter(String name, NXdispersion_single_parameter dispersion_single_parameter) {
		putChild(name, dispersion_single_parameter);
	}

	@Override
	public Map<String, NXdispersion_single_parameter> getAllDispersion_single_parameter() {
		return getChildren(NXdispersion_single_parameter.class);
	}

	@Override
	public void setAllDispersion_single_parameter(Map<String, NXdispersion_single_parameter> dispersion_single_parameter) {
		setChildren(dispersion_single_parameter);
	}

	@Override
	public NXdispersion_repeated_parameter getDispersion_repeated_parameter() {
		// dataNodeName = NX_DISPERSION_REPEATED_PARAMETER
		return getChild("dispersion_repeated_parameter", NXdispersion_repeated_parameter.class);
	}

	@Override
	public void setDispersion_repeated_parameter(NXdispersion_repeated_parameter dispersion_repeated_parameterGroup) {
		putChild("dispersion_repeated_parameter", dispersion_repeated_parameterGroup);
	}

	@Override
	public NXdispersion_repeated_parameter getDispersion_repeated_parameter(String name) {
		return getChild(name, NXdispersion_repeated_parameter.class);
	}

	@Override
	public void setDispersion_repeated_parameter(String name, NXdispersion_repeated_parameter dispersion_repeated_parameter) {
		putChild(name, dispersion_repeated_parameter);
	}

	@Override
	public Map<String, NXdispersion_repeated_parameter> getAllDispersion_repeated_parameter() {
		return getChildren(NXdispersion_repeated_parameter.class);
	}

	@Override
	public void setAllDispersion_repeated_parameter(Map<String, NXdispersion_repeated_parameter> dispersion_repeated_parameter) {
		setChildren(dispersion_repeated_parameter);
	}

}

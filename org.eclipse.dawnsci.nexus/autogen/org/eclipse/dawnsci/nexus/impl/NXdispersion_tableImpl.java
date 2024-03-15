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
 * A dispersion table denoting energy, dielectric function tabulated values.

 */
public class NXdispersion_tableImpl extends NXobjectImpl implements NXdispersion_table {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXdispersion_tableImpl() {
		super();
	}

	public NXdispersion_tableImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXdispersion_table.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_DISPERSION_TABLE;
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
	public IDataset getWavelength() {
		return getDataset(NX_WAVELENGTH);
	}

	@Override
	public Number getWavelengthScalar() {
		return getNumber(NX_WAVELENGTH);
	}

	@Override
	public DataNode setWavelength(IDataset wavelengthDataset) {
		return setDataset(NX_WAVELENGTH, wavelengthDataset);
	}

	@Override
	public DataNode setWavelengthScalar(Number wavelengthValue) {
		return setField(NX_WAVELENGTH, wavelengthValue);
	}

	@Override
	public IDataset getEnergy() {
		return getDataset(NX_ENERGY);
	}

	@Override
	public Number getEnergyScalar() {
		return getNumber(NX_ENERGY);
	}

	@Override
	public DataNode setEnergy(IDataset energyDataset) {
		return setDataset(NX_ENERGY, energyDataset);
	}

	@Override
	public DataNode setEnergyScalar(Number energyValue) {
		return setField(NX_ENERGY, energyValue);
	}

	@Override
	public IDataset getRefractive_index() {
		return getDataset(NX_REFRACTIVE_INDEX);
	}

	@Override
	public String getRefractive_indexScalar() {
		return getString(NX_REFRACTIVE_INDEX);
	}

	@Override
	public DataNode setRefractive_index(IDataset refractive_indexDataset) {
		return setDataset(NX_REFRACTIVE_INDEX, refractive_indexDataset);
	}

	@Override
	public DataNode setRefractive_indexScalar(String refractive_indexValue) {
		return setString(NX_REFRACTIVE_INDEX, refractive_indexValue);
	}

	@Override
	public IDataset getDielectric_function() {
		return getDataset(NX_DIELECTRIC_FUNCTION);
	}

	@Override
	public String getDielectric_functionScalar() {
		return getString(NX_DIELECTRIC_FUNCTION);
	}

	@Override
	public DataNode setDielectric_function(IDataset dielectric_functionDataset) {
		return setDataset(NX_DIELECTRIC_FUNCTION, dielectric_functionDataset);
	}

	@Override
	public DataNode setDielectric_functionScalar(String dielectric_functionValue) {
		return setString(NX_DIELECTRIC_FUNCTION, dielectric_functionValue);
	}

}

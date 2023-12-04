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
 * Set of atoms of a molecular ion or fragment in e.g. ToF mass spectrometry.
 * 
 */
public class NXionImpl extends NXobjectImpl implements NXion {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXionImpl() {
		super();
	}

	public NXionImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXion.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_ION;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}
	

	@Override
	public IDataset getIon_type() {
		return getDataset(NX_ION_TYPE);
	}

	@Override
	public Long getIon_typeScalar() {
		return getLong(NX_ION_TYPE);
	}

	@Override
	public DataNode setIon_type(IDataset ion_typeDataset) {
		return setDataset(NX_ION_TYPE, ion_typeDataset);
	}

	@Override
	public DataNode setIon_typeScalar(Long ion_typeValue) {
		return setField(NX_ION_TYPE, ion_typeValue);
	}

	@Override
	public IDataset getIsotope_vector() {
		return getDataset(NX_ISOTOPE_VECTOR);
	}

	@Override
	public Long getIsotope_vectorScalar() {
		return getLong(NX_ISOTOPE_VECTOR);
	}

	@Override
	public DataNode setIsotope_vector(IDataset isotope_vectorDataset) {
		return setDataset(NX_ISOTOPE_VECTOR, isotope_vectorDataset);
	}

	@Override
	public DataNode setIsotope_vectorScalar(Long isotope_vectorValue) {
		return setField(NX_ISOTOPE_VECTOR, isotope_vectorValue);
	}

	@Override
	public IDataset getCharge_state() {
		return getDataset(NX_CHARGE_STATE);
	}

	@Override
	public Long getCharge_stateScalar() {
		return getLong(NX_CHARGE_STATE);
	}

	@Override
	public DataNode setCharge_state(IDataset charge_stateDataset) {
		return setDataset(NX_CHARGE_STATE, charge_stateDataset);
	}

	@Override
	public DataNode setCharge_stateScalar(Long charge_stateValue) {
		return setField(NX_CHARGE_STATE, charge_stateValue);
	}

	@Override
	public IDataset getName() {
		return getDataset(NX_NAME);
	}

	@Override
	public String getNameScalar() {
		return getString(NX_NAME);
	}

	@Override
	public DataNode setName(IDataset nameDataset) {
		return setDataset(NX_NAME, nameDataset);
	}

	@Override
	public DataNode setNameScalar(String nameValue) {
		return setString(NX_NAME, nameValue);
	}

	@Override
	public IDataset getMass_to_charge_range() {
		return getDataset(NX_MASS_TO_CHARGE_RANGE);
	}

	@Override
	public Double getMass_to_charge_rangeScalar() {
		return getDouble(NX_MASS_TO_CHARGE_RANGE);
	}

	@Override
	public DataNode setMass_to_charge_range(IDataset mass_to_charge_rangeDataset) {
		return setDataset(NX_MASS_TO_CHARGE_RANGE, mass_to_charge_rangeDataset);
	}

	@Override
	public DataNode setMass_to_charge_rangeScalar(Double mass_to_charge_rangeValue) {
		return setField(NX_MASS_TO_CHARGE_RANGE, mass_to_charge_rangeValue);
	}

}

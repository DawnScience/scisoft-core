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
import org.eclipse.january.dataset.Dataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * Base class for documenting a set of atoms.
 * Atoms in the set may be bonded. The set may have
 * a net charge to represent an ion.
 * An ion can be a molecular ion.

 */
public class NXatomImpl extends NXobjectImpl implements NXatom {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXatomImpl() {
		super();
	}

	public NXatomImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXatom.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_ATOM;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getName() {
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
	public Dataset getId() {
		return getDataset(NX_ID);
	}

	@Override
	public Long getIdScalar() {
		return getLong(NX_ID);
	}

	@Override
	public DataNode setId(IDataset idDataset) {
		return setDataset(NX_ID, idDataset);
	}

	@Override
	public DataNode setIdScalar(Long idValue) {
		return setField(NX_ID, idValue);
	}

	@Override
	public Dataset getIdentifier_chemical() {
		return getDataset(NX_IDENTIFIER_CHEMICAL);
	}

	@Override
	public String getIdentifier_chemicalScalar() {
		return getString(NX_IDENTIFIER_CHEMICAL);
	}

	@Override
	public DataNode setIdentifier_chemical(IDataset identifier_chemicalDataset) {
		return setDataset(NX_IDENTIFIER_CHEMICAL, identifier_chemicalDataset);
	}

	@Override
	public DataNode setIdentifier_chemicalScalar(String identifier_chemicalValue) {
		return setString(NX_IDENTIFIER_CHEMICAL, identifier_chemicalValue);
	}

	@Override
	public Dataset getCharge() {
		return getDataset(NX_CHARGE);
	}

	@Override
	public Number getChargeScalar() {
		return getNumber(NX_CHARGE);
	}

	@Override
	public DataNode setCharge(IDataset chargeDataset) {
		return setDataset(NX_CHARGE, chargeDataset);
	}

	@Override
	public DataNode setChargeScalar(Number chargeValue) {
		return setField(NX_CHARGE, chargeValue);
	}

	@Override
	public Dataset getCharge_state() {
		return getDataset(NX_CHARGE_STATE);
	}

	@Override
	public Number getCharge_stateScalar() {
		return getNumber(NX_CHARGE_STATE);
	}

	@Override
	public DataNode setCharge_state(IDataset charge_stateDataset) {
		return setDataset(NX_CHARGE_STATE, charge_stateDataset);
	}

	@Override
	public DataNode setCharge_stateScalar(Number charge_stateValue) {
		return setField(NX_CHARGE_STATE, charge_stateValue);
	}

	@Override
	public Dataset getVolume() {
		return getDataset(NX_VOLUME);
	}

	@Override
	public Number getVolumeScalar() {
		return getNumber(NX_VOLUME);
	}

	@Override
	public DataNode setVolume(IDataset volumeDataset) {
		return setDataset(NX_VOLUME, volumeDataset);
	}

	@Override
	public DataNode setVolumeScalar(Number volumeValue) {
		return setField(NX_VOLUME, volumeValue);
	}

	@Override
	public Dataset getIndices() {
		return getDataset(NX_INDICES);
	}

	@Override
	public String getIndicesScalar() {
		return getString(NX_INDICES);
	}

	@Override
	public DataNode setIndices(IDataset indicesDataset) {
		return setDataset(NX_INDICES, indicesDataset);
	}

	@Override
	public DataNode setIndicesScalar(String indicesValue) {
		return setString(NX_INDICES, indicesValue);
	}

	@Override
	public Dataset getType() {
		return getDataset(NX_TYPE);
	}

	@Override
	public Long getTypeScalar() {
		return getLong(NX_TYPE);
	}

	@Override
	public DataNode setType(IDataset typeDataset) {
		return setDataset(NX_TYPE, typeDataset);
	}

	@Override
	public DataNode setTypeScalar(Long typeValue) {
		return setField(NX_TYPE, typeValue);
	}

	@Override
	public Dataset getPosition() {
		return getDataset(NX_POSITION);
	}

	@Override
	public Number getPositionScalar() {
		return getNumber(NX_POSITION);
	}

	@Override
	public DataNode setPosition(IDataset positionDataset) {
		return setDataset(NX_POSITION, positionDataset);
	}

	@Override
	public DataNode setPositionScalar(Number positionValue) {
		return setField(NX_POSITION, positionValue);
	}

	@Override
	public String getPositionAttributeDepends_on() {
		return getAttrString(NX_POSITION, NX_POSITION_ATTRIBUTE_DEPENDS_ON);
	}

	@Override
	public void setPositionAttributeDepends_on(String depends_onValue) {
		setAttribute(NX_POSITION, NX_POSITION_ATTRIBUTE_DEPENDS_ON, depends_onValue);
	}

	@Override
	public Dataset getOccupancy() {
		return getDataset(NX_OCCUPANCY);
	}

	@Override
	public Number getOccupancyScalar() {
		return getNumber(NX_OCCUPANCY);
	}

	@Override
	public DataNode setOccupancy(IDataset occupancyDataset) {
		return setDataset(NX_OCCUPANCY, occupancyDataset);
	}

	@Override
	public DataNode setOccupancyScalar(Number occupancyValue) {
		return setField(NX_OCCUPANCY, occupancyValue);
	}

	@Override
	public Dataset getNuclide_hash() {
		return getDataset(NX_NUCLIDE_HASH);
	}

	@Override
	public Long getNuclide_hashScalar() {
		return getLong(NX_NUCLIDE_HASH);
	}

	@Override
	public DataNode setNuclide_hash(IDataset nuclide_hashDataset) {
		return setDataset(NX_NUCLIDE_HASH, nuclide_hashDataset);
	}

	@Override
	public DataNode setNuclide_hashScalar(Long nuclide_hashValue) {
		return setField(NX_NUCLIDE_HASH, nuclide_hashValue);
	}

	@Override
	public Dataset getNuclide_list() {
		return getDataset(NX_NUCLIDE_LIST);
	}

	@Override
	public Long getNuclide_listScalar() {
		return getLong(NX_NUCLIDE_LIST);
	}

	@Override
	public DataNode setNuclide_list(IDataset nuclide_listDataset) {
		return setDataset(NX_NUCLIDE_LIST, nuclide_listDataset);
	}

	@Override
	public DataNode setNuclide_listScalar(Long nuclide_listValue) {
		return setField(NX_NUCLIDE_LIST, nuclide_listValue);
	}

	@Override
	public Dataset getMass_to_charge_range() {
		return getDataset(NX_MASS_TO_CHARGE_RANGE);
	}

	@Override
	public Number getMass_to_charge_rangeScalar() {
		return getNumber(NX_MASS_TO_CHARGE_RANGE);
	}

	@Override
	public DataNode setMass_to_charge_range(IDataset mass_to_charge_rangeDataset) {
		return setDataset(NX_MASS_TO_CHARGE_RANGE, mass_to_charge_rangeDataset);
	}

	@Override
	public DataNode setMass_to_charge_rangeScalar(Number mass_to_charge_rangeValue) {
		return setField(NX_MASS_TO_CHARGE_RANGE, mass_to_charge_rangeValue);
	}

}

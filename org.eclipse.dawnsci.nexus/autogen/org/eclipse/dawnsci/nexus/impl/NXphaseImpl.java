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
 * Base class to describe a (thermodynamic) phase as a component of a material.
 * Instances of phases can be crystalline.

 */
public class NXphaseImpl extends NXobjectImpl implements NXphase {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_UNIT_CELL,
		NexusBaseClass.NX_ATOM);

	public NXphaseImpl() {
		super();
	}

	public NXphaseImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXphase.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_PHASE;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getPhase_id() {
		return getDataset(NX_PHASE_ID);
	}

	@Override
	public Long getPhase_idScalar() {
		return getLong(NX_PHASE_ID);
	}

	@Override
	public DataNode setPhase_id(IDataset phase_idDataset) {
		return setDataset(NX_PHASE_ID, phase_idDataset);
	}

	@Override
	public DataNode setPhase_idScalar(Long phase_idValue) {
		return setField(NX_PHASE_ID, phase_idValue);
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
	public NXunit_cell getUnit_cell() {
		// dataNodeName = NX_UNIT_CELL
		return getChild("unit_cell", NXunit_cell.class);
	}

	@Override
	public void setUnit_cell(NXunit_cell unit_cellGroup) {
		putChild("unit_cell", unit_cellGroup);
	}

	@Override
	public NXunit_cell getUnit_cell(String name) {
		return getChild(name, NXunit_cell.class);
	}

	@Override
	public void setUnit_cell(String name, NXunit_cell unit_cell) {
		putChild(name, unit_cell);
	}

	@Override
	public Map<String, NXunit_cell> getAllUnit_cell() {
		return getChildren(NXunit_cell.class);
	}

	@Override
	public void setAllUnit_cell(Map<String, NXunit_cell> unit_cell) {
		setChildren(unit_cell);
	}

	@Override
	public NXatom getAtom() {
		// dataNodeName = NX_ATOM
		return getChild("atom", NXatom.class);
	}

	@Override
	public void setAtom(NXatom atomGroup) {
		putChild("atom", atomGroup);
	}

	@Override
	public NXatom getAtom(String name) {
		return getChild(name, NXatom.class);
	}

	@Override
	public void setAtom(String name, NXatom atom) {
		putChild(name, atom);
	}

	@Override
	public Map<String, NXatom> getAllAtom() {
		return getChildren(NXatom.class);
	}

	@Override
	public void setAllAtom(Map<String, NXatom> atom) {
		setChildren(atom);
	}

}

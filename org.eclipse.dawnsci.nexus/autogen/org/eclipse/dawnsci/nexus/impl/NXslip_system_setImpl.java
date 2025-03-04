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
 * Base class for describing a set of crystallographic slip systems.

 */
public class NXslip_system_setImpl extends NXobjectImpl implements NXslip_system_set {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXslip_system_setImpl() {
		super();
	}

	public NXslip_system_setImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXslip_system_set.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_SLIP_SYSTEM_SET;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getLattice_type() {
		return getDataset(NX_LATTICE_TYPE);
	}

	@Override
	public String getLattice_typeScalar() {
		return getString(NX_LATTICE_TYPE);
	}

	@Override
	public DataNode setLattice_type(IDataset lattice_typeDataset) {
		return setDataset(NX_LATTICE_TYPE, lattice_typeDataset);
	}

	@Override
	public DataNode setLattice_typeScalar(String lattice_typeValue) {
		return setString(NX_LATTICE_TYPE, lattice_typeValue);
	}

	@Override
	public Dataset getMiller_plane() {
		return getDataset(NX_MILLER_PLANE);
	}

	@Override
	public Number getMiller_planeScalar() {
		return getNumber(NX_MILLER_PLANE);
	}

	@Override
	public DataNode setMiller_plane(IDataset miller_planeDataset) {
		return setDataset(NX_MILLER_PLANE, miller_planeDataset);
	}

	@Override
	public DataNode setMiller_planeScalar(Number miller_planeValue) {
		return setField(NX_MILLER_PLANE, miller_planeValue);
	}

	@Override
	public Dataset getMiller_direction() {
		return getDataset(NX_MILLER_DIRECTION);
	}

	@Override
	public Number getMiller_directionScalar() {
		return getNumber(NX_MILLER_DIRECTION);
	}

	@Override
	public DataNode setMiller_direction(IDataset miller_directionDataset) {
		return setDataset(NX_MILLER_DIRECTION, miller_directionDataset);
	}

	@Override
	public DataNode setMiller_directionScalar(Number miller_directionValue) {
		return setField(NX_MILLER_DIRECTION, miller_directionValue);
	}

	@Override
	public Dataset getIs_specific() {
		return getDataset(NX_IS_SPECIFIC);
	}

	@Override
	public Boolean getIs_specificScalar() {
		return getBoolean(NX_IS_SPECIFIC);
	}

	@Override
	public DataNode setIs_specific(IDataset is_specificDataset) {
		return setDataset(NX_IS_SPECIFIC, is_specificDataset);
	}

	@Override
	public DataNode setIs_specificScalar(Boolean is_specificValue) {
		return setField(NX_IS_SPECIFIC, is_specificValue);
	}

}

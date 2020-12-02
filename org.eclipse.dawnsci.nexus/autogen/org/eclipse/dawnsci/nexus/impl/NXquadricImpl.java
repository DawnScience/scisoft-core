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
 * definition of a quadric surface.
 * 
 */
public class NXquadricImpl extends NXobjectImpl implements NXquadric {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXquadricImpl() {
		super();
	}

	public NXquadricImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXquadric.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_QUADRIC;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}
	

	@Override
	public IDataset getParameters() {
		return getDataset(NX_PARAMETERS);
	}

	@Override
	public Number getParametersScalar() {
		return getNumber(NX_PARAMETERS);
	}

	@Override
	public DataNode setParameters(IDataset parametersDataset) {
		return setDataset(NX_PARAMETERS, parametersDataset);
	}

	@Override
	public DataNode setParametersScalar(Number parametersValue) {
		return setField(NX_PARAMETERS, parametersValue);
	}

	@Override
	public IDataset getSurface_type() {
		return getDataset(NX_SURFACE_TYPE);
	}

	@Override
	public String getSurface_typeScalar() {
		return getString(NX_SURFACE_TYPE);
	}

	@Override
	public DataNode setSurface_type(IDataset surface_typeDataset) {
		return setDataset(NX_SURFACE_TYPE, surface_typeDataset);
	}

	@Override
	public DataNode setSurface_typeScalar(String surface_typeValue) {
		return setString(NX_SURFACE_TYPE, surface_typeValue);
	}

	@Override
	public IDataset getDepends_on() {
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

}

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
 * Device to reduce an atmosphere to a controlled remaining pressure level.
 * 
 */
public class NXpumpImpl extends NXobjectImpl implements NXpump {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXpumpImpl() {
		super();
	}

	public NXpumpImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXpump.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_PUMP;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}
	

	@Override
	public IDataset getDesign() {
		return getDataset(NX_DESIGN);
	}

	@Override
	public String getDesignScalar() {
		return getString(NX_DESIGN);
	}

	@Override
	public DataNode setDesign(IDataset designDataset) {
		return setDataset(NX_DESIGN, designDataset);
	}

	@Override
	public DataNode setDesignScalar(String designValue) {
		return setString(NX_DESIGN, designValue);
	}

}

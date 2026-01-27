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
 * Base class for reporting the description of the I/O of a computer.

 */
public class NXcs_storageImpl extends NXcomponentImpl implements NXcs_storage {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_CIRCUIT);

	public NXcs_storageImpl() {
		super();
	}

	public NXcs_storageImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcs_storage.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CS_STORAGE;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public NXcircuit getCircuit() {
		// dataNodeName = NX_CIRCUIT
		return getChild("circuit", NXcircuit.class);
	}

	@Override
	public void setCircuit(NXcircuit circuitGroup) {
		putChild("circuit", circuitGroup);
	}

	@Override
	public NXcircuit getCircuit(String name) {
		return getChild(name, NXcircuit.class);
	}

	@Override
	public void setCircuit(String name, NXcircuit circuit) {
		putChild(name, circuit);
	}

	@Override
	public Map<String, NXcircuit> getAllCircuit() {
		return getChildren(NXcircuit.class);
	}

	@Override
	public void setAllCircuit(Map<String, NXcircuit> circuit) {
		setChildren(circuit);
	}

}

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


import org.eclipse.dawnsci.nexus.*;

/**
 * Base class method-specific for Electron Energy Loss Spectroscopy (EELS).

 */
public class NXem_eelsImpl extends NXprocessImpl implements NXem_eels {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_PROCESS,
		NexusBaseClass.NX_PROCESS);

	public NXem_eelsImpl() {
		super();
	}

	public NXem_eelsImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXem_eels.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_EM_EELS;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public NXprocess getZlp_correction() {
		// dataNodeName = NX_ZLP_CORRECTION
		return getChild("zlp_correction", NXprocess.class);
	}

	@Override
	public void setZlp_correction(NXprocess zlp_correctionGroup) {
		putChild("zlp_correction", zlp_correctionGroup);
	}
	// Unprocessed group:

	@Override
	public NXprocess getIndexing() {
		// dataNodeName = NX_INDEXING
		return getChild("indexing", NXprocess.class);
	}

	@Override
	public void setIndexing(NXprocess indexingGroup) {
		putChild("indexing", indexingGroup);
	}
	// Unprocessed group:
	// Unprocessed group:
	// Unprocessed group:

}

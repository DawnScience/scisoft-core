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
 * Base class method-specific for energy-dispersive X-ray spectroscopy (EDS/EDXS).
 * `IUPAC instead of Siegbahn notation <https://doi.org/10.1002/xrs.1300200308>`_ should be used.
 * X-ray spectroscopy is a surface-sensitive technique. Therefore, three-dimensional elemental
 * characterization requires typically a sequence of characterization and preparation of the
 * surface to expose new surface layer that can be characterized in the next acquisition.
 * In effect, the resulting three-dimensional elemental information mappings are truly the
 * result of a correlation and post-processing of several measurements which is the field
 * of correlative tomographic usage of electron microscopy.

 */
public class NXem_edsImpl extends NXprocessImpl implements NXem_eds {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_PROCESS);

	public NXem_edsImpl() {
		super();
	}

	public NXem_edsImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXem_eds.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_EM_EDS;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


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
	// Unprocessed group: summary
	// Unprocessed group:
	// Unprocessed group: ELEMENT_SPECIFIC_MAP

}

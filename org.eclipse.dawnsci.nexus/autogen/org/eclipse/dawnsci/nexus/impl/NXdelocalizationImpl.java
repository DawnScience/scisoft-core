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
 * Base class of the configuration and results of a delocalization algorithm.
 * Delocalization is used to distribute point-like objects on a grid to obtain
 * e.g. smoother count, composition, or concentration values of scalar fields
 * and compute gradients of these fields.

 */
public class NXdelocalizationImpl extends NXobjectImpl implements NXdelocalization {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_CG_GRID,
		NexusBaseClass.NX_MATCH_FILTER);

	public NXdelocalizationImpl() {
		super();
	}

	public NXdelocalizationImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXdelocalization.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_DELOCALIZATION;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public NXcg_grid getGrid() {
		// dataNodeName = NX_GRID
		return getChild("grid", NXcg_grid.class);
	}

	@Override
	public void setGrid(NXcg_grid gridGroup) {
		putChild("grid", gridGroup);
	}

	@Override
	public NXmatch_filter getWeighting_model() {
		// dataNodeName = NX_WEIGHTING_MODEL
		return getChild("weighting_model", NXmatch_filter.class);
	}

	@Override
	public void setWeighting_model(NXmatch_filter weighting_modelGroup) {
		putChild("weighting_model", weighting_modelGroup);
	}

}

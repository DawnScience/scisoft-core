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
 * Base class to store the configuration when using the MTex/Matlab software.
 * MTex is a Matlab package for texture analysis used in the Materials and Earth Sciences.
 * See `R. Hielscher et al. <https://mtex-toolbox.github.io/publications>`_ and
 * the `MTex source code <https://github.com/mtex-toolbox>`_ for details.

 */
public class NXmicrostructure_mtex_configImpl extends NXparametersImpl implements NXmicrostructure_mtex_config {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_COLLECTION,
		NexusBaseClass.NX_COLLECTION,
		NexusBaseClass.NX_COLLECTION,
		NexusBaseClass.NX_COLLECTION,
		NexusBaseClass.NX_COLLECTION,
		NexusBaseClass.NX_COLLECTION);

	public NXmicrostructure_mtex_configImpl() {
		super();
	}

	public NXmicrostructure_mtex_configImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXmicrostructure_mtex_config.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_MICROSTRUCTURE_MTEX_CONFIG;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public NXcollection getConventions() {
		// dataNodeName = NX_CONVENTIONS
		return getChild("conventions", NXcollection.class);
	}

	@Override
	public void setConventions(NXcollection conventionsGroup) {
		putChild("conventions", conventionsGroup);
	}

	@Override
	public NXcollection getPlotting() {
		// dataNodeName = NX_PLOTTING
		return getChild("plotting", NXcollection.class);
	}

	@Override
	public void setPlotting(NXcollection plottingGroup) {
		putChild("plotting", plottingGroup);
	}

	@Override
	public NXcollection getMiscellaneous() {
		// dataNodeName = NX_MISCELLANEOUS
		return getChild("miscellaneous", NXcollection.class);
	}

	@Override
	public void setMiscellaneous(NXcollection miscellaneousGroup) {
		putChild("miscellaneous", miscellaneousGroup);
	}

	@Override
	public NXcollection getNumerics() {
		// dataNodeName = NX_NUMERICS
		return getChild("numerics", NXcollection.class);
	}

	@Override
	public void setNumerics(NXcollection numericsGroup) {
		putChild("numerics", numericsGroup);
	}

	@Override
	public NXcollection getSystem() {
		// dataNodeName = NX_SYSTEM
		return getChild("system", NXcollection.class);
	}

	@Override
	public void setSystem(NXcollection systemGroup) {
		putChild("system", systemGroup);
	}

	@Override
	public NXcollection getPath() {
		// dataNodeName = NX_PATH
		return getChild("path", NXcollection.class);
	}

	@Override
	public void setPath(NXcollection pathGroup) {
		putChild("path", pathGroup);
	}

}

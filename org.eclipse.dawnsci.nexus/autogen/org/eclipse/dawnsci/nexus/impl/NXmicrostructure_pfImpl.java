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
 * Base class to store a pole figure (PF) computation.
 * A pole figure is the X-ray diffraction intensity for specific integrated
 * peaks for a hemispherical illumination of a real or virtual specimen.

 */
public class NXmicrostructure_pfImpl extends NXprocessImpl implements NXmicrostructure_pf {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_PARAMETERS,
		NexusBaseClass.NX_DATA);

	public NXmicrostructure_pfImpl() {
		super();
	}

	public NXmicrostructure_pfImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXmicrostructure_pf.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_MICROSTRUCTURE_PF;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public NXparameters getConfiguration() {
		// dataNodeName = NX_CONFIGURATION
		return getChild("configuration", NXparameters.class);
	}

	@Override
	public void setConfiguration(NXparameters configurationGroup) {
		putChild("configuration", configurationGroup);
	}

	@Override
	public NXdata getPf() {
		// dataNodeName = NX_PF
		return getChild("pf", NXdata.class);
	}

	@Override
	public void setPf(NXdata pfGroup) {
		putChild("pf", pfGroup);
	}

}

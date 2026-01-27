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


import org.eclipse.dawnsci.nexus.*;

/**
 * Base class for documenting structuring features of a microstructure.
 * Instances of the class enable sub-grouping of microstructural features
 * as the abstract base class NXobject should not be used for this purpose.

 */
public class NXmicrostructure_featureImpl extends NXobjectImpl implements NXmicrostructure_feature {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_CHEMICAL_COMPOSITION);

	public NXmicrostructure_featureImpl() {
		super();
	}

	public NXmicrostructure_featureImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXmicrostructure_feature.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_MICROSTRUCTURE_FEATURE;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public NXchemical_composition getChemical_composition() {
		// dataNodeName = NX_CHEMICAL_COMPOSITION
		return getChild("chemical_composition", NXchemical_composition.class);
	}

	@Override
	public void setChemical_composition(NXchemical_composition chemical_compositionGroup) {
		putChild("chemical_composition", chemical_compositionGroup);
	}

}

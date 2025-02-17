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
 * An unvalidated set of terms, such as the description of a beam line.
 * Use :ref:`NXcollection` to gather together any set of terms.
 * The original suggestion is to use this as a container
 * class for the description of a beamline.
 * For NeXus validation, :ref:`NXcollection` will always generate
 * a warning since it is always an optional group.
 * Anything (groups, fields, or attributes) placed in
 * an :ref:`NXcollection` group will not be validated.

 */
public class NXcollectionImpl extends NXobjectImpl implements NXcollection {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXcollectionImpl() {
		super();
	}

	public NXcollectionImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcollection.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_COLLECTION;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


}

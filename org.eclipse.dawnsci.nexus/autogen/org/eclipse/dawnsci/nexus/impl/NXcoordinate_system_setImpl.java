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
 * Container to hold different coordinate systems conventions.
 * It is the purpose of this base class to define these conventions and
 * offer a place to store mappings between different coordinate systems
 * which are relevant for the interpretation of the data described
 * by the application definition and base class instances.
 * For each Cartesian coordinate system users should use a set of
 * NXtransformations:
 * * These should define the three base vectors.
 * * The location of the origin.
 * * The affine transformations which bring each axis of this coordinate system
 * into registration with the McStas coordinate system.
 * * Equally, affine transformations should be given for the inverse mapping.
 * As an example one may take an experiment or computer simulation where
 * there is a laboratory (lab) coordinate system, a sample/specimen coordinate
 * system, a crystal coordinate system, and additional coordinate systems,
 * which are eventually attached to components of the instrument.
 * If no additional transformation is specified in this group or if an
 * instance of an NXcoordinate_system_set is absent it should be assumed
 * the so-called McStas coordinate system is used.
 * Many application definitions in NeXus refer to this `McStas <https://mailman2.mcstas.org/pipermail/mcstas-users/2021q2/001431.html>`_ coordinate system.
 * This is a Cartesian coordinate system whose z axis points along the neutron
 * propagation axis. The systems y axis is vertical up, while the x axis points
 * left when looking along the z-axis. Thus, McStas is a right-handed coordinate system.
 * Within each NXtransformations a depends_on section is required. The depends_on
 * field specifies if the coordinate system is the root/reference
 * (which is indicated by writing "." in the depends_on section.)
 * 
 */
public class NXcoordinate_system_setImpl extends NXobjectImpl implements NXcoordinate_system_set {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_TRANSFORMATIONS);

	public NXcoordinate_system_setImpl() {
		super();
	}

	public NXcoordinate_system_setImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcoordinate_system_set.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_COORDINATE_SYSTEM_SET;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}
	

	@Override
	public NXtransformations getTransformations() {
		// dataNodeName = NX_TRANSFORMATIONS
		return getChild("transformations", NXtransformations.class);
	}

	@Override
	public void setTransformations(NXtransformations transformationsGroup) {
		putChild("transformations", transformationsGroup);
	}

	@Override
	public NXtransformations getTransformations(String name) {
		return getChild(name, NXtransformations.class);
	}

	@Override
	public void setTransformations(String name, NXtransformations transformations) {
		putChild(name, transformations);
	}

	@Override
	public Map<String, NXtransformations> getAllTransformations() {
		return getChildren(NXtransformations.class);
	}
	
	@Override
	public void setAllTransformations(Map<String, NXtransformations> transformations) {
		setChildren(transformations);
	}

}

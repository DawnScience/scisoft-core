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
 * Base class to hold geometric primitives.

 */
public class NXcg_roi_setImpl extends NXobjectImpl implements NXcg_roi_set {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_CG_SPHERE_SET,
		NexusBaseClass.NX_CG_ELLIPSOID_SET,
		NexusBaseClass.NX_CG_CYLINDER_SET,
		NexusBaseClass.NX_CG_POLYHEDRON_SET);

	public NXcg_roi_setImpl() {
		super();
	}

	public NXcg_roi_setImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcg_roi_set.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CG_ROI_SET;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public NXcg_sphere_set getCg_sphere_set() {
		// dataNodeName = NX_CG_SPHERE_SET
		return getChild("cg_sphere_set", NXcg_sphere_set.class);
	}

	@Override
	public void setCg_sphere_set(NXcg_sphere_set cg_sphere_setGroup) {
		putChild("cg_sphere_set", cg_sphere_setGroup);
	}

	@Override
	public NXcg_ellipsoid_set getCg_ellipsoid_set() {
		// dataNodeName = NX_CG_ELLIPSOID_SET
		return getChild("cg_ellipsoid_set", NXcg_ellipsoid_set.class);
	}

	@Override
	public void setCg_ellipsoid_set(NXcg_ellipsoid_set cg_ellipsoid_setGroup) {
		putChild("cg_ellipsoid_set", cg_ellipsoid_setGroup);
	}

	@Override
	public NXcg_cylinder_set getCg_cylinder_set() {
		// dataNodeName = NX_CG_CYLINDER_SET
		return getChild("cg_cylinder_set", NXcg_cylinder_set.class);
	}

	@Override
	public void setCg_cylinder_set(NXcg_cylinder_set cg_cylinder_setGroup) {
		putChild("cg_cylinder_set", cg_cylinder_setGroup);
	}

	@Override
	public NXcg_polyhedron_set getCg_polyhedron_set() {
		// dataNodeName = NX_CG_POLYHEDRON_SET
		return getChild("cg_polyhedron_set", NXcg_polyhedron_set.class);
	}

	@Override
	public void setCg_polyhedron_set(NXcg_polyhedron_set cg_polyhedron_setGroup) {
		putChild("cg_polyhedron_set", cg_polyhedron_setGroup);
	}

}

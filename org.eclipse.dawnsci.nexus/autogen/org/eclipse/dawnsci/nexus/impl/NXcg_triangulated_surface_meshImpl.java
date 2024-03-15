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
 * Computational geometry description of a mesh of triangles.
 * The mesh may be self-intersecting and have holes but the
 * triangles must not be degenerated.

 */
public class NXcg_triangulated_surface_meshImpl extends NXobjectImpl implements NXcg_triangulated_surface_mesh {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_CG_TRIANGLE_SET,
		NexusBaseClass.NX_CG_HALF_EDGE_DATA_STRUCTURE);

	public NXcg_triangulated_surface_meshImpl() {
		super();
	}

	public NXcg_triangulated_surface_meshImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcg_triangulated_surface_mesh.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CG_TRIANGULATED_SURFACE_MESH;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public NXcg_triangle_set getCg_triangle_set() {
		// dataNodeName = NX_CG_TRIANGLE_SET
		return getChild("cg_triangle_set", NXcg_triangle_set.class);
	}

	@Override
	public void setCg_triangle_set(NXcg_triangle_set cg_triangle_setGroup) {
		putChild("cg_triangle_set", cg_triangle_setGroup);
	}

	@Override
	public NXcg_triangle_set getCg_triangle_set(String name) {
		return getChild(name, NXcg_triangle_set.class);
	}

	@Override
	public void setCg_triangle_set(String name, NXcg_triangle_set cg_triangle_set) {
		putChild(name, cg_triangle_set);
	}

	@Override
	public Map<String, NXcg_triangle_set> getAllCg_triangle_set() {
		return getChildren(NXcg_triangle_set.class);
	}

	@Override
	public void setAllCg_triangle_set(Map<String, NXcg_triangle_set> cg_triangle_set) {
		setChildren(cg_triangle_set);
	}

	@Override
	public NXcg_half_edge_data_structure getCg_half_edge_data_structure() {
		// dataNodeName = NX_CG_HALF_EDGE_DATA_STRUCTURE
		return getChild("cg_half_edge_data_structure", NXcg_half_edge_data_structure.class);
	}

	@Override
	public void setCg_half_edge_data_structure(NXcg_half_edge_data_structure cg_half_edge_data_structureGroup) {
		putChild("cg_half_edge_data_structure", cg_half_edge_data_structureGroup);
	}

	@Override
	public NXcg_half_edge_data_structure getCg_half_edge_data_structure(String name) {
		return getChild(name, NXcg_half_edge_data_structure.class);
	}

	@Override
	public void setCg_half_edge_data_structure(String name, NXcg_half_edge_data_structure cg_half_edge_data_structure) {
		putChild(name, cg_half_edge_data_structure);
	}

	@Override
	public Map<String, NXcg_half_edge_data_structure> getAllCg_half_edge_data_structure() {
		return getChildren(NXcg_half_edge_data_structure.class);
	}

	@Override
	public void setAllCg_half_edge_data_structure(Map<String, NXcg_half_edge_data_structure> cg_half_edge_data_structure) {
		setChildren(cg_half_edge_data_structure);
	}

}

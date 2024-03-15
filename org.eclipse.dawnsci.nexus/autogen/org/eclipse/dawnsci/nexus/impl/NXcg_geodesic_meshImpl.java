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
 * Computational geometry description of a geodesic mesh.
 * People from geodesic/surveyors will likely have specific demands and
 * different views about what should be included in such a base class, given
 * that nested geodesic meshes are a key component of climate modelling tools.
 * For now we propose to use this base class as a container to organize metadata
 * and data related to geodesic meshes.
 * Specifically an instance of this base class should detail the rule set how
 * how the geodesic (surface) mesh was instantiated as there are many
 * possibilities. A geodesic surface mesh is in this sense a triangulated
 * surface mesh with metadata. For additional details as an introduction
 * into the topic see e.g.:
 * * `E. S. Popko and C. J. Kitrick <https://doi.org/10.1201/9781003134114>`_
 * Here, especially the section on subdivision schemes is relevant.

 */
public class NXcg_geodesic_meshImpl extends NXobjectImpl implements NXcg_geodesic_mesh {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_TRANSFORMATIONS,
		NexusBaseClass.NX_CG_TRIANGULATED_SURFACE_MESH);

	public NXcg_geodesic_meshImpl() {
		super();
	}

	public NXcg_geodesic_meshImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcg_geodesic_mesh.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CG_GEODESIC_MESH;
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

	@Override
	public NXcg_triangulated_surface_mesh getCg_triangulated_surface_mesh() {
		// dataNodeName = NX_CG_TRIANGULATED_SURFACE_MESH
		return getChild("cg_triangulated_surface_mesh", NXcg_triangulated_surface_mesh.class);
	}

	@Override
	public void setCg_triangulated_surface_mesh(NXcg_triangulated_surface_mesh cg_triangulated_surface_meshGroup) {
		putChild("cg_triangulated_surface_mesh", cg_triangulated_surface_meshGroup);
	}

	@Override
	public NXcg_triangulated_surface_mesh getCg_triangulated_surface_mesh(String name) {
		return getChild(name, NXcg_triangulated_surface_mesh.class);
	}

	@Override
	public void setCg_triangulated_surface_mesh(String name, NXcg_triangulated_surface_mesh cg_triangulated_surface_mesh) {
		putChild(name, cg_triangulated_surface_mesh);
	}

	@Override
	public Map<String, NXcg_triangulated_surface_mesh> getAllCg_triangulated_surface_mesh() {
		return getChildren(NXcg_triangulated_surface_mesh.class);
	}

	@Override
	public void setAllCg_triangulated_surface_mesh(Map<String, NXcg_triangulated_surface_mesh> cg_triangulated_surface_mesh) {
		setChildren(cg_triangulated_surface_mesh);
	}

}

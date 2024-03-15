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

package org.eclipse.dawnsci.nexus;

import java.util.Map;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;


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
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul></ul></p>
 *
 */
public interface NXcg_geodesic_mesh extends NXobject {

	/**
	 * Reference to or definition of a coordinate system with
	 * which the positions and directions are interpretable.
	 *
	 * @return  the value.
	 */
	public NXtransformations getTransformations();

	/**
	 * Reference to or definition of a coordinate system with
	 * which the positions and directions are interpretable.
	 *
	 * @param transformationsGroup the transformationsGroup
	 */
	public void setTransformations(NXtransformations transformationsGroup);

	/**
	 * Get a NXtransformations node by name:
	 * <ul>
	 * <li>
	 * Reference to or definition of a coordinate system with
	 * which the positions and directions are interpretable.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXtransformations for that node.
	 */
	public NXtransformations getTransformations(String name);

	/**
	 * Set a NXtransformations node by name:
	 * <ul>
	 * <li>
	 * Reference to or definition of a coordinate system with
	 * which the positions and directions are interpretable.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param transformations the value to set
	 */
	public void setTransformations(String name, NXtransformations transformations);

	/**
	 * Get all NXtransformations nodes:
	 * <ul>
	 * <li>
	 * Reference to or definition of a coordinate system with
	 * which the positions and directions are interpretable.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXtransformations for that node.
	 */
	public Map<String, NXtransformations> getAllTransformations();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Reference to or definition of a coordinate system with
	 * which the positions and directions are interpretable.</li>
	 * </ul>
	 *
	 * @param transformations the child nodes to add
	 */

	public void setAllTransformations(Map<String, NXtransformations> transformations);


	/**
	 *
	 * @return  the value.
	 */
	public NXcg_triangulated_surface_mesh getCg_triangulated_surface_mesh();

	/**
	 *
	 * @param cg_triangulated_surface_meshGroup the cg_triangulated_surface_meshGroup
	 */
	public void setCg_triangulated_surface_mesh(NXcg_triangulated_surface_mesh cg_triangulated_surface_meshGroup);

	/**
	 * Get a NXcg_triangulated_surface_mesh node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXcg_triangulated_surface_mesh for that node.
	 */
	public NXcg_triangulated_surface_mesh getCg_triangulated_surface_mesh(String name);

	/**
	 * Set a NXcg_triangulated_surface_mesh node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param cg_triangulated_surface_mesh the value to set
	 */
	public void setCg_triangulated_surface_mesh(String name, NXcg_triangulated_surface_mesh cg_triangulated_surface_mesh);

	/**
	 * Get all NXcg_triangulated_surface_mesh nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXcg_triangulated_surface_mesh for that node.
	 */
	public Map<String, NXcg_triangulated_surface_mesh> getAllCg_triangulated_surface_mesh();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param cg_triangulated_surface_mesh the child nodes to add
	 */

	public void setAllCg_triangulated_surface_mesh(Map<String, NXcg_triangulated_surface_mesh> cg_triangulated_surface_mesh);


}

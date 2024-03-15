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
 * Computational geometry description of a mesh of triangles.
 * The mesh may be self-intersecting and have holes but the
 * triangles must not be degenerated.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul></ul></p>
 *
 */
public interface NXcg_triangulated_surface_mesh extends NXobject {

	/**
	 *
	 * @return  the value.
	 */
	public NXcg_triangle_set getCg_triangle_set();

	/**
	 *
	 * @param cg_triangle_setGroup the cg_triangle_setGroup
	 */
	public void setCg_triangle_set(NXcg_triangle_set cg_triangle_setGroup);

	/**
	 * Get a NXcg_triangle_set node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXcg_triangle_set for that node.
	 */
	public NXcg_triangle_set getCg_triangle_set(String name);

	/**
	 * Set a NXcg_triangle_set node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param cg_triangle_set the value to set
	 */
	public void setCg_triangle_set(String name, NXcg_triangle_set cg_triangle_set);

	/**
	 * Get all NXcg_triangle_set nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXcg_triangle_set for that node.
	 */
	public Map<String, NXcg_triangle_set> getAllCg_triangle_set();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param cg_triangle_set the child nodes to add
	 */

	public void setAllCg_triangle_set(Map<String, NXcg_triangle_set> cg_triangle_set);


	/**
	 * A graph-based approach to describe the mesh when it is also desired
	 * to perform topological processing or analyses on the mesh.
	 *
	 * @return  the value.
	 */
	public NXcg_half_edge_data_structure getCg_half_edge_data_structure();

	/**
	 * A graph-based approach to describe the mesh when it is also desired
	 * to perform topological processing or analyses on the mesh.
	 *
	 * @param cg_half_edge_data_structureGroup the cg_half_edge_data_structureGroup
	 */
	public void setCg_half_edge_data_structure(NXcg_half_edge_data_structure cg_half_edge_data_structureGroup);

	/**
	 * Get a NXcg_half_edge_data_structure node by name:
	 * <ul>
	 * <li>
	 * A graph-based approach to describe the mesh when it is also desired
	 * to perform topological processing or analyses on the mesh.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXcg_half_edge_data_structure for that node.
	 */
	public NXcg_half_edge_data_structure getCg_half_edge_data_structure(String name);

	/**
	 * Set a NXcg_half_edge_data_structure node by name:
	 * <ul>
	 * <li>
	 * A graph-based approach to describe the mesh when it is also desired
	 * to perform topological processing or analyses on the mesh.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param cg_half_edge_data_structure the value to set
	 */
	public void setCg_half_edge_data_structure(String name, NXcg_half_edge_data_structure cg_half_edge_data_structure);

	/**
	 * Get all NXcg_half_edge_data_structure nodes:
	 * <ul>
	 * <li>
	 * A graph-based approach to describe the mesh when it is also desired
	 * to perform topological processing or analyses on the mesh.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXcg_half_edge_data_structure for that node.
	 */
	public Map<String, NXcg_half_edge_data_structure> getAllCg_half_edge_data_structure();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * A graph-based approach to describe the mesh when it is also desired
	 * to perform topological processing or analyses on the mesh.</li>
	 * </ul>
	 *
	 * @param cg_half_edge_data_structure the child nodes to add
	 */

	public void setAllCg_half_edge_data_structure(Map<String, NXcg_half_edge_data_structure> cg_half_edge_data_structure);


}

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
 * Base class for a region-of-interest (ROI) bound by geometric primitives.
 * So-called region-of-interest(s) (ROIs) are typically used to describe a
 * region in space (and time) where an observation is made or for which
 * a computer simulation is performed with given boundary conditions.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.
 * Use :ref:`NXcg_primitive` and :ref:`NXcoordinate_system` classes to
 * define explicitly the reference frame in which the primitives are
 * defined.<ul></ul></p>
 *
 */
public interface NXcg_roi extends NXobject {

	/**
	 *
	 * @return  the value.
	 */
	public NXcg_ellipsoid getCg_ellipsoid();

	/**
	 *
	 * @param cg_ellipsoidGroup the cg_ellipsoidGroup
	 */
	public void setCg_ellipsoid(NXcg_ellipsoid cg_ellipsoidGroup);

	/**
	 * Get a NXcg_ellipsoid node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXcg_ellipsoid for that node.
	 */
	public NXcg_ellipsoid getCg_ellipsoid(String name);

	/**
	 * Set a NXcg_ellipsoid node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param cg_ellipsoid the value to set
	 */
	public void setCg_ellipsoid(String name, NXcg_ellipsoid cg_ellipsoid);

	/**
	 * Get all NXcg_ellipsoid nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXcg_ellipsoid for that node.
	 */
	public Map<String, NXcg_ellipsoid> getAllCg_ellipsoid();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param cg_ellipsoid the child nodes to add
	 */

	public void setAllCg_ellipsoid(Map<String, NXcg_ellipsoid> cg_ellipsoid);


	/**
	 *
	 * @return  the value.
	 */
	public NXcg_cylinder getCg_cylinder();

	/**
	 *
	 * @param cg_cylinderGroup the cg_cylinderGroup
	 */
	public void setCg_cylinder(NXcg_cylinder cg_cylinderGroup);

	/**
	 * Get a NXcg_cylinder node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXcg_cylinder for that node.
	 */
	public NXcg_cylinder getCg_cylinder(String name);

	/**
	 * Set a NXcg_cylinder node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param cg_cylinder the value to set
	 */
	public void setCg_cylinder(String name, NXcg_cylinder cg_cylinder);

	/**
	 * Get all NXcg_cylinder nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXcg_cylinder for that node.
	 */
	public Map<String, NXcg_cylinder> getAllCg_cylinder();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param cg_cylinder the child nodes to add
	 */

	public void setAllCg_cylinder(Map<String, NXcg_cylinder> cg_cylinder);


	/**
	 *
	 * @return  the value.
	 */
	public NXcg_parallelogram getCg_parallelogram();

	/**
	 *
	 * @param cg_parallelogramGroup the cg_parallelogramGroup
	 */
	public void setCg_parallelogram(NXcg_parallelogram cg_parallelogramGroup);

	/**
	 * Get a NXcg_parallelogram node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXcg_parallelogram for that node.
	 */
	public NXcg_parallelogram getCg_parallelogram(String name);

	/**
	 * Set a NXcg_parallelogram node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param cg_parallelogram the value to set
	 */
	public void setCg_parallelogram(String name, NXcg_parallelogram cg_parallelogram);

	/**
	 * Get all NXcg_parallelogram nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXcg_parallelogram for that node.
	 */
	public Map<String, NXcg_parallelogram> getAllCg_parallelogram();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param cg_parallelogram the child nodes to add
	 */

	public void setAllCg_parallelogram(Map<String, NXcg_parallelogram> cg_parallelogram);


	/**
	 *
	 * @return  the value.
	 */
	public NXcg_hexahedron getCg_hexahedron();

	/**
	 *
	 * @param cg_hexahedronGroup the cg_hexahedronGroup
	 */
	public void setCg_hexahedron(NXcg_hexahedron cg_hexahedronGroup);

	/**
	 * Get a NXcg_hexahedron node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXcg_hexahedron for that node.
	 */
	public NXcg_hexahedron getCg_hexahedron(String name);

	/**
	 * Set a NXcg_hexahedron node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param cg_hexahedron the value to set
	 */
	public void setCg_hexahedron(String name, NXcg_hexahedron cg_hexahedron);

	/**
	 * Get all NXcg_hexahedron nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXcg_hexahedron for that node.
	 */
	public Map<String, NXcg_hexahedron> getAllCg_hexahedron();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param cg_hexahedron the child nodes to add
	 */

	public void setAllCg_hexahedron(Map<String, NXcg_hexahedron> cg_hexahedron);


	/**
	 *
	 * @return  the value.
	 */
	public NXcg_polyhedron getCg_polyhedron();

	/**
	 *
	 * @param cg_polyhedronGroup the cg_polyhedronGroup
	 */
	public void setCg_polyhedron(NXcg_polyhedron cg_polyhedronGroup);

	/**
	 * Get a NXcg_polyhedron node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXcg_polyhedron for that node.
	 */
	public NXcg_polyhedron getCg_polyhedron(String name);

	/**
	 * Set a NXcg_polyhedron node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param cg_polyhedron the value to set
	 */
	public void setCg_polyhedron(String name, NXcg_polyhedron cg_polyhedron);

	/**
	 * Get all NXcg_polyhedron nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXcg_polyhedron for that node.
	 */
	public Map<String, NXcg_polyhedron> getAllCg_polyhedron();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param cg_polyhedron the child nodes to add
	 */

	public void setAllCg_polyhedron(Map<String, NXcg_polyhedron> cg_polyhedron);


}

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

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Dataset;

/**
 * Base class for a spatial filter for objects within a region-of-interest (ROI).
 * Objects can be points, objects composed from other geometric primitives,
 * or objects.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>n_hexahedra</b>
 * Number of hexahedra.</li>
 * <li><b>n_cylinders</b>
 * Number of cylinders.</li>
 * <li><b>n_ellipsoids</b>
 * Number of ellipsoids.</li>
 * <li><b>n_polyhedra</b>
 * Number of polyhedra.</li></ul></p>
 *
 */
public interface NXspatial_filter extends NXparameters {

	public static final String NX_WINDOWING_METHOD = "windowing_method";
	/**
	 * Qualitative statement which describes the logical operations
	 * that define which objects will be included and which excluded:
	 * * entire_dataset, no filter is applied, all objects are included.
	 * * union_of_primitives, a filter with (possibly non-axis-aligned) geometric
	 * primitives. Objects in or on the surface of the primitives are included.
	 * All other objects are excluded.
	 * * bitmask, a boolean array whose bits encode with 1 which objects
	 * are included. Bits set to zero encode which objects are excluded.
	 * Users of python can use the bitfield operations of the numpy package to work with bitfields.
	 * Multiple instances of NXcg base classes are used to compose a union_of_primitives.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>entire_dataset</b> </li>
	 * <li><b>union_of_primitives</b> </li>
	 * <li><b>bitmask</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getWindowing_method();

	/**
	 * Qualitative statement which describes the logical operations
	 * that define which objects will be included and which excluded:
	 * * entire_dataset, no filter is applied, all objects are included.
	 * * union_of_primitives, a filter with (possibly non-axis-aligned) geometric
	 * primitives. Objects in or on the surface of the primitives are included.
	 * All other objects are excluded.
	 * * bitmask, a boolean array whose bits encode with 1 which objects
	 * are included. Bits set to zero encode which objects are excluded.
	 * Users of python can use the bitfield operations of the numpy package to work with bitfields.
	 * Multiple instances of NXcg base classes are used to compose a union_of_primitives.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>entire_dataset</b> </li>
	 * <li><b>union_of_primitives</b> </li>
	 * <li><b>bitmask</b> </li></ul></p>
	 * </p>
	 *
	 * @param windowing_methodDataset the windowing_methodDataset
	 */
	public DataNode setWindowing_method(IDataset windowing_methodDataset);

	/**
	 * Qualitative statement which describes the logical operations
	 * that define which objects will be included and which excluded:
	 * * entire_dataset, no filter is applied, all objects are included.
	 * * union_of_primitives, a filter with (possibly non-axis-aligned) geometric
	 * primitives. Objects in or on the surface of the primitives are included.
	 * All other objects are excluded.
	 * * bitmask, a boolean array whose bits encode with 1 which objects
	 * are included. Bits set to zero encode which objects are excluded.
	 * Users of python can use the bitfield operations of the numpy package to work with bitfields.
	 * Multiple instances of NXcg base classes are used to compose a union_of_primitives.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>entire_dataset</b> </li>
	 * <li><b>union_of_primitives</b> </li>
	 * <li><b>bitmask</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getWindowing_methodScalar();

	/**
	 * Qualitative statement which describes the logical operations
	 * that define which objects will be included and which excluded:
	 * * entire_dataset, no filter is applied, all objects are included.
	 * * union_of_primitives, a filter with (possibly non-axis-aligned) geometric
	 * primitives. Objects in or on the surface of the primitives are included.
	 * All other objects are excluded.
	 * * bitmask, a boolean array whose bits encode with 1 which objects
	 * are included. Bits set to zero encode which objects are excluded.
	 * Users of python can use the bitfield operations of the numpy package to work with bitfields.
	 * Multiple instances of NXcg base classes are used to compose a union_of_primitives.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>entire_dataset</b> </li>
	 * <li><b>union_of_primitives</b> </li>
	 * <li><b>bitmask</b> </li></ul></p>
	 * </p>
	 *
	 * @param windowing_method the windowing_method
	 */
	public DataNode setWindowing_methodScalar(String windowing_methodValue);

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


	/**
	 *
	 * @return  the value.
	 */
	public NXcs_filter_boolean_mask getCs_filter_boolean_mask();

	/**
	 *
	 * @param cs_filter_boolean_maskGroup the cs_filter_boolean_maskGroup
	 */
	public void setCs_filter_boolean_mask(NXcs_filter_boolean_mask cs_filter_boolean_maskGroup);

	/**
	 * Get a NXcs_filter_boolean_mask node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXcs_filter_boolean_mask for that node.
	 */
	public NXcs_filter_boolean_mask getCs_filter_boolean_mask(String name);

	/**
	 * Set a NXcs_filter_boolean_mask node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param cs_filter_boolean_mask the value to set
	 */
	public void setCs_filter_boolean_mask(String name, NXcs_filter_boolean_mask cs_filter_boolean_mask);

	/**
	 * Get all NXcs_filter_boolean_mask nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXcs_filter_boolean_mask for that node.
	 */
	public Map<String, NXcs_filter_boolean_mask> getAllCs_filter_boolean_mask();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param cs_filter_boolean_mask the child nodes to add
	 */

	public void setAllCs_filter_boolean_mask(Map<String, NXcs_filter_boolean_mask> cs_filter_boolean_mask);


}

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

/**
 * Spatial filter to filter entries within a region-of-interest based on their
 * position.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>n_ellipsoids</b>
 * Number of ellipsoids.</li>
 * <li><b>n_hexahedra</b>
 * Number of hexahedra.</li>
 * <li><b>n_cylinders</b>
 * Number of cylinders.</li></ul></p>
 *
 */
public interface NXspatial_filter extends NXobject {

	public static final String NX_WINDOWING_METHOD = "windowing_method";
	/**
	 * Qualitative statement which specifies which spatial filtering with respective
	 * geometric primitives or bitmask is used. These settings are possible:
	 * * entire_dataset, no filter is applied, the entire dataset is used.
	 * * union_of_primitives, a filter with (rotated) geometric primitives.
	 * All ions in or on the surface of the primitives are considered
	 * while all other ions are ignored.
	 * * bitmasked_points, a boolean array whose bits encode with 1
	 * which ions should be included. Those ions whose bit is set to 0
	 * will be excluded. Users of python can use the bitfield operations
	 * of the numpy package to define such bitfields.
	 * Conditions:
	 * In the case that windowing_method is entire_dataset all entries are processed.
	 * In the case that windowing_method is union_of_primitives,
	 * it is possible to specify none or all types of primitives
	 * (ellipsoids, cylinder, hexahedra). If no primitives are specified
	 * the filter falls back to entire_dataset.
	 * In the case that windowing_method is bitmask, the bitmask has to be defined;
	 * otherwise the filter falls back to entire dataset.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>entire_dataset</b> </li>
	 * <li><b>union_of_primitives</b> </li>
	 * <li><b>bitmask</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getWindowing_method();

	/**
	 * Qualitative statement which specifies which spatial filtering with respective
	 * geometric primitives or bitmask is used. These settings are possible:
	 * * entire_dataset, no filter is applied, the entire dataset is used.
	 * * union_of_primitives, a filter with (rotated) geometric primitives.
	 * All ions in or on the surface of the primitives are considered
	 * while all other ions are ignored.
	 * * bitmasked_points, a boolean array whose bits encode with 1
	 * which ions should be included. Those ions whose bit is set to 0
	 * will be excluded. Users of python can use the bitfield operations
	 * of the numpy package to define such bitfields.
	 * Conditions:
	 * In the case that windowing_method is entire_dataset all entries are processed.
	 * In the case that windowing_method is union_of_primitives,
	 * it is possible to specify none or all types of primitives
	 * (ellipsoids, cylinder, hexahedra). If no primitives are specified
	 * the filter falls back to entire_dataset.
	 * In the case that windowing_method is bitmask, the bitmask has to be defined;
	 * otherwise the filter falls back to entire dataset.
	 * <p>
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
	 * Qualitative statement which specifies which spatial filtering with respective
	 * geometric primitives or bitmask is used. These settings are possible:
	 * * entire_dataset, no filter is applied, the entire dataset is used.
	 * * union_of_primitives, a filter with (rotated) geometric primitives.
	 * All ions in or on the surface of the primitives are considered
	 * while all other ions are ignored.
	 * * bitmasked_points, a boolean array whose bits encode with 1
	 * which ions should be included. Those ions whose bit is set to 0
	 * will be excluded. Users of python can use the bitfield operations
	 * of the numpy package to define such bitfields.
	 * Conditions:
	 * In the case that windowing_method is entire_dataset all entries are processed.
	 * In the case that windowing_method is union_of_primitives,
	 * it is possible to specify none or all types of primitives
	 * (ellipsoids, cylinder, hexahedra). If no primitives are specified
	 * the filter falls back to entire_dataset.
	 * In the case that windowing_method is bitmask, the bitmask has to be defined;
	 * otherwise the filter falls back to entire dataset.
	 * <p>
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
	 * Qualitative statement which specifies which spatial filtering with respective
	 * geometric primitives or bitmask is used. These settings are possible:
	 * * entire_dataset, no filter is applied, the entire dataset is used.
	 * * union_of_primitives, a filter with (rotated) geometric primitives.
	 * All ions in or on the surface of the primitives are considered
	 * while all other ions are ignored.
	 * * bitmasked_points, a boolean array whose bits encode with 1
	 * which ions should be included. Those ions whose bit is set to 0
	 * will be excluded. Users of python can use the bitfield operations
	 * of the numpy package to define such bitfields.
	 * Conditions:
	 * In the case that windowing_method is entire_dataset all entries are processed.
	 * In the case that windowing_method is union_of_primitives,
	 * it is possible to specify none or all types of primitives
	 * (ellipsoids, cylinder, hexahedra). If no primitives are specified
	 * the filter falls back to entire_dataset.
	 * In the case that windowing_method is bitmask, the bitmask has to be defined;
	 * otherwise the filter falls back to entire dataset.
	 * <p>
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
	public NXcg_ellipsoid_set getCg_ellipsoid_set();

	/**
	 *
	 * @param cg_ellipsoid_setGroup the cg_ellipsoid_setGroup
	 */
	public void setCg_ellipsoid_set(NXcg_ellipsoid_set cg_ellipsoid_setGroup);

	/**
	 * Get a NXcg_ellipsoid_set node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXcg_ellipsoid_set for that node.
	 */
	public NXcg_ellipsoid_set getCg_ellipsoid_set(String name);

	/**
	 * Set a NXcg_ellipsoid_set node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param cg_ellipsoid_set the value to set
	 */
	public void setCg_ellipsoid_set(String name, NXcg_ellipsoid_set cg_ellipsoid_set);

	/**
	 * Get all NXcg_ellipsoid_set nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXcg_ellipsoid_set for that node.
	 */
	public Map<String, NXcg_ellipsoid_set> getAllCg_ellipsoid_set();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param cg_ellipsoid_set the child nodes to add
	 */

	public void setAllCg_ellipsoid_set(Map<String, NXcg_ellipsoid_set> cg_ellipsoid_set);


	/**
	 *
	 * @return  the value.
	 */
	public NXcg_cylinder_set getCg_cylinder_set();

	/**
	 *
	 * @param cg_cylinder_setGroup the cg_cylinder_setGroup
	 */
	public void setCg_cylinder_set(NXcg_cylinder_set cg_cylinder_setGroup);

	/**
	 * Get a NXcg_cylinder_set node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXcg_cylinder_set for that node.
	 */
	public NXcg_cylinder_set getCg_cylinder_set(String name);

	/**
	 * Set a NXcg_cylinder_set node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param cg_cylinder_set the value to set
	 */
	public void setCg_cylinder_set(String name, NXcg_cylinder_set cg_cylinder_set);

	/**
	 * Get all NXcg_cylinder_set nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXcg_cylinder_set for that node.
	 */
	public Map<String, NXcg_cylinder_set> getAllCg_cylinder_set();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param cg_cylinder_set the child nodes to add
	 */

	public void setAllCg_cylinder_set(Map<String, NXcg_cylinder_set> cg_cylinder_set);


	/**
	 *
	 * @return  the value.
	 */
	public NXcg_hexahedron_set getCg_hexahedron_set();

	/**
	 *
	 * @param cg_hexahedron_setGroup the cg_hexahedron_setGroup
	 */
	public void setCg_hexahedron_set(NXcg_hexahedron_set cg_hexahedron_setGroup);

	/**
	 * Get a NXcg_hexahedron_set node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXcg_hexahedron_set for that node.
	 */
	public NXcg_hexahedron_set getCg_hexahedron_set(String name);

	/**
	 * Set a NXcg_hexahedron_set node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param cg_hexahedron_set the value to set
	 */
	public void setCg_hexahedron_set(String name, NXcg_hexahedron_set cg_hexahedron_set);

	/**
	 * Get all NXcg_hexahedron_set nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXcg_hexahedron_set for that node.
	 */
	public Map<String, NXcg_hexahedron_set> getAllCg_hexahedron_set();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param cg_hexahedron_set the child nodes to add
	 */

	public void setAllCg_hexahedron_set(Map<String, NXcg_hexahedron_set> cg_hexahedron_set);


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

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

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;

/**
 * Base class for describing a set of crystallographic slip systems.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>n</b>
 * Number of slip systems.</li></ul></p>
 *
 */
public interface NXslip_system_set extends NXobject {

	public static final String NX_LATTICE_TYPE = "lattice_type";
	public static final String NX_MILLER_PLANE = "miller_plane";
	public static final String NX_MILLER_DIRECTION = "miller_direction";
	public static final String NX_IS_SPECIFIC = "is_specific";
	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>triclinic</b> </li>
	 * <li><b>monoclinic</b> </li>
	 * <li><b>orthorhombic</b> </li>
	 * <li><b>tetragonal</b> </li>
	 * <li><b>trigonal</b> </li>
	 * <li><b>hexagonal</b> </li>
	 * <li><b>cubic</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getLattice_type();

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>triclinic</b> </li>
	 * <li><b>monoclinic</b> </li>
	 * <li><b>orthorhombic</b> </li>
	 * <li><b>tetragonal</b> </li>
	 * <li><b>trigonal</b> </li>
	 * <li><b>hexagonal</b> </li>
	 * <li><b>cubic</b> </li></ul></p>
	 * </p>
	 *
	 * @param lattice_typeDataset the lattice_typeDataset
	 */
	public DataNode setLattice_type(IDataset lattice_typeDataset);

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>triclinic</b> </li>
	 * <li><b>monoclinic</b> </li>
	 * <li><b>orthorhombic</b> </li>
	 * <li><b>tetragonal</b> </li>
	 * <li><b>trigonal</b> </li>
	 * <li><b>hexagonal</b> </li>
	 * <li><b>cubic</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getLattice_typeScalar();

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>triclinic</b> </li>
	 * <li><b>monoclinic</b> </li>
	 * <li><b>orthorhombic</b> </li>
	 * <li><b>tetragonal</b> </li>
	 * <li><b>trigonal</b> </li>
	 * <li><b>hexagonal</b> </li>
	 * <li><b>cubic</b> </li></ul></p>
	 * </p>
	 *
	 * @param lattice_type the lattice_type
	 */
	public DataNode setLattice_typeScalar(String lattice_typeValue);

	/**
	 * Array of Miller indices which describe the crystallographic plane.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n; 2: i;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getMiller_plane();

	/**
	 * Array of Miller indices which describe the crystallographic plane.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n; 2: i;
	 * </p>
	 *
	 * @param miller_planeDataset the miller_planeDataset
	 */
	public DataNode setMiller_plane(IDataset miller_planeDataset);

	/**
	 * Array of Miller indices which describe the crystallographic plane.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n; 2: i;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getMiller_planeScalar();

	/**
	 * Array of Miller indices which describe the crystallographic plane.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n; 2: i;
	 * </p>
	 *
	 * @param miller_plane the miller_plane
	 */
	public DataNode setMiller_planeScalar(Number miller_planeValue);

	/**
	 * Array of Miller indices which describe the crystallographic direction.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n; 2: i;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getMiller_direction();

	/**
	 * Array of Miller indices which describe the crystallographic direction.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n; 2: i;
	 * </p>
	 *
	 * @param miller_directionDataset the miller_directionDataset
	 */
	public DataNode setMiller_direction(IDataset miller_directionDataset);

	/**
	 * Array of Miller indices which describe the crystallographic direction.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n; 2: i;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getMiller_directionScalar();

	/**
	 * Array of Miller indices which describe the crystallographic direction.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n; 2: i;
	 * </p>
	 *
	 * @param miller_direction the miller_direction
	 */
	public DataNode setMiller_directionScalar(Number miller_directionValue);

	/**
	 * For each slip system a marker whether the specified Miller indices
	 * refer to the specific slip system or the set of crystallographic equivalent
	 * slip systems of the respective family of slip systems.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getIs_specific();

	/**
	 * For each slip system a marker whether the specified Miller indices
	 * refer to the specific slip system or the set of crystallographic equivalent
	 * slip systems of the respective family of slip systems.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 *
	 * @param is_specificDataset the is_specificDataset
	 */
	public DataNode setIs_specific(IDataset is_specificDataset);

	/**
	 * For each slip system a marker whether the specified Miller indices
	 * refer to the specific slip system or the set of crystallographic equivalent
	 * slip systems of the respective family of slip systems.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Boolean getIs_specificScalar();

	/**
	 * For each slip system a marker whether the specified Miller indices
	 * refer to the specific slip system or the set of crystallographic equivalent
	 * slip systems of the respective family of slip systems.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 *
	 * @param is_specific the is_specific
	 */
	public DataNode setIs_specificScalar(Boolean is_specificValue);

}

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
import org.eclipse.january.dataset.Dataset;

/**
 * Base class for describing a set of crystallographic slip systems.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>n</b>
 * Number of slip systems.</li>
 * <li><b>m</b>
 * Number of indices used for reporting Miller (3) or Miller-Bravais indices (4).</li></ul></p>
 *
 */
public interface NXmicrostructure_slip_system extends NXobject {

	public static final String NX_LATTICE_TYPE = "lattice_type";
	public static final String NX_MILLER_PLANE = "miller_plane";
	public static final String NX_MILLER_DIRECTION = "miller_direction";
	public static final String NX_IS_SPECIFIC = "is_specific";
	/**
	 * Bravais lattice type
	 * <p>
	 * <b>Type:</b> NX_CHAR
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
	public Dataset getLattice_type();

	/**
	 * Bravais lattice type
	 * <p>
	 * <b>Type:</b> NX_CHAR
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
	 * Bravais lattice type
	 * <p>
	 * <b>Type:</b> NX_CHAR
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
	 * Bravais lattice type
	 * <p>
	 * <b>Type:</b> NX_CHAR
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
	 * Array of Miller indices which describe the crystallographic planes.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n; 2: i;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getMiller_plane();

	/**
	 * Array of Miller indices which describe the crystallographic planes.
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
	 * Array of Miller indices which describe the crystallographic planes.
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
	 * Array of Miller indices which describe the crystallographic planes.
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
	 * Array of Miller or Miller-Bravais indices that describe the crystallographic
	 * direction.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n; 2: m;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getMiller_direction();

	/**
	 * Array of Miller or Miller-Bravais indices that describe the crystallographic
	 * direction.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n; 2: m;
	 * </p>
	 *
	 * @param miller_directionDataset the miller_directionDataset
	 */
	public DataNode setMiller_direction(IDataset miller_directionDataset);

	/**
	 * Array of Miller or Miller-Bravais indices that describe the crystallographic
	 * direction.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n; 2: m;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getMiller_directionScalar();

	/**
	 * Array of Miller or Miller-Bravais indices that describe the crystallographic
	 * direction.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n; 2: m;
	 * </p>
	 *
	 * @param miller_direction the miller_direction
	 */
	public DataNode setMiller_directionScalar(Number miller_directionValue);

	/**
	 * For each slip system a marker whether the Miller indices refer to a specific slip system
	 * or to a set of equivalent crystallographic slip systems.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIs_specific();

	/**
	 * For each slip system a marker whether the Miller indices refer to a specific slip system
	 * or to a set of equivalent crystallographic slip systems.
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
	 * For each slip system a marker whether the Miller indices refer to a specific slip system
	 * or to a set of equivalent crystallographic slip systems.
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
	 * For each slip system a marker whether the Miller indices refer to a specific slip system
	 * or to a set of equivalent crystallographic slip systems.
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

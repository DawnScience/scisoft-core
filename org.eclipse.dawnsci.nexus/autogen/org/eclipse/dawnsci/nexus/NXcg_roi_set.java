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


/**
 * Base class to hold geometric primitives.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul></ul></p>
 *
 */
public interface NXcg_roi_set extends NXobject {

	/**
	 *
	 * @return  the value.
	 */
	public NXcg_sphere_set getCg_sphere_set();

	/**
	 *
	 * @param cg_sphere_setGroup the cg_sphere_setGroup
	 */
	public void setCg_sphere_set(NXcg_sphere_set cg_sphere_setGroup);

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
	 *
	 * @return  the value.
	 */
	public NXcg_polyhedron_set getCg_polyhedron_set();

	/**
	 *
	 * @param cg_polyhedron_setGroup the cg_polyhedron_setGroup
	 */
	public void setCg_polyhedron_set(NXcg_polyhedron_set cg_polyhedron_setGroup);

}

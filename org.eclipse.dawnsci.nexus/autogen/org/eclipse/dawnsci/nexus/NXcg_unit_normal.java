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
 * Computational geometry description of a set of (oriented) unit normal vectors.
 * Store normal vector information as properties of primitives.
 * Use only only as a child of an instance of :ref:`NXcg_primitive`
 * so that this instance acts as the parent to define a context.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>d</b>
 * The dimensionality, which has to be at least 2.</li>
 * <li><b>c</b>
 * The cardinality of the set, i.e. the number of unit normals.</li></ul></p>
 *
 */
public interface NXcg_unit_normal extends NXobject {

	public static final String NX_NORMALS = "normals";
	public static final String NX_ORIENTATION = "orientation";
	/**
	 * Direction of each normal - a unit normal.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getNormals();

	/**
	 * Direction of each normal - a unit normal.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @param normalsDataset the normalsDataset
	 */
	public DataNode setNormals(IDataset normalsDataset);

	/**
	 * Direction of each normal - a unit normal.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getNormalsScalar();

	/**
	 * Direction of each normal - a unit normal.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @param normals the normals
	 */
	public DataNode setNormalsScalar(Number normalsValue);

	/**
	 * An indicator which details the orientation of each normal vector
	 * in relation to its primitive, assuming the object is viewed
	 * from a position outside the object.
	 * * 0 - undefined
	 * * 1 - outer unit normal vector
	 * * 2 - inner unit normal vector
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getOrientation();

	/**
	 * An indicator which details the orientation of each normal vector
	 * in relation to its primitive, assuming the object is viewed
	 * from a position outside the object.
	 * * 0 - undefined
	 * * 1 - outer unit normal vector
	 * * 2 - inner unit normal vector
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param orientationDataset the orientationDataset
	 */
	public DataNode setOrientation(IDataset orientationDataset);

	/**
	 * An indicator which details the orientation of each normal vector
	 * in relation to its primitive, assuming the object is viewed
	 * from a position outside the object.
	 * * 0 - undefined
	 * * 1 - outer unit normal vector
	 * * 2 - inner unit normal vector
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getOrientationScalar();

	/**
	 * An indicator which details the orientation of each normal vector
	 * in relation to its primitive, assuming the object is viewed
	 * from a position outside the object.
	 * * 0 - undefined
	 * * 1 - outer unit normal vector
	 * * 2 - inner unit normal vector
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param orientation the orientation
	 */
	public DataNode setOrientationScalar(Long orientationValue);

}

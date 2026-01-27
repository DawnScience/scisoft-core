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
 * Computational geometry description of a set of triangles.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>d</b>
 * The dimensionality, which has to be at least 2.</li>
 * <li><b>c</b>
 * The cardinality of the set, i.e. the number of triangles.</li>
 * <li><b>n_unique</b>
 * The number of unique vertices supporting the triangles.</li></ul></p>
 *
 */
public interface NXcg_triangle extends NXcg_primitive {

	public static final String NX_NUMBER_OF_UNIQUE_VERTICES = "number_of_unique_vertices";
	public static final String NX_EDGE_LENGTH = "edge_length";
	public static final String NX_INTERIOR_ANGLE = "interior_angle";
	/**
	 * Number of unique vertices in the triangle set.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getNumber_of_unique_vertices();

	/**
	 * Number of unique vertices in the triangle set.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_unique_verticesDataset the number_of_unique_verticesDataset
	 */
	public DataNode setNumber_of_unique_vertices(IDataset number_of_unique_verticesDataset);

	/**
	 * Number of unique vertices in the triangle set.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getNumber_of_unique_verticesScalar();

	/**
	 * Number of unique vertices in the triangle set.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_unique_vertices the number_of_unique_vertices
	 */
	public DataNode setNumber_of_unique_verticesScalar(Long number_of_unique_verticesValue);

	/**
	 * Combined storage of all primitives of all triangles.
	 * This description resembles the typical representation of primitives
	 * in file formats such as OFF, PLY, VTK, or STL.
	 *
	 * @return  the value.
	 */
	public NXcg_face_list_data_structure getTriangles();

	/**
	 * Combined storage of all primitives of all triangles.
	 * This description resembles the typical representation of primitives
	 * in file formats such as OFF, PLY, VTK, or STL.
	 *
	 * @param trianglesGroup the trianglesGroup
	 */
	public void setTriangles(NXcg_face_list_data_structure trianglesGroup);

	/**
	 * Individual storage of each triangle.
	 * Users are advised that using such individual storage of primitives
	 * may be less storage efficient than creating a combined storage.
	 *
	 * @return  the value.
	 */
	public NXcg_face_list_data_structure getTriangleid();

	/**
	 * Individual storage of each triangle.
	 * Users are advised that using such individual storage of primitives
	 * may be less storage efficient than creating a combined storage.
	 *
	 * @param triangleidGroup the triangleidGroup
	 */
	public void setTriangleid(NXcg_face_list_data_structure triangleidGroup);

	/**
	 * Length of the edges of each triangle.
	 * For each triangle values are reported via traversing
	 * the vertices in the sequence as these are defined.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getEdge_length();

	/**
	 * Length of the edges of each triangle.
	 * For each triangle values are reported via traversing
	 * the vertices in the sequence as these are defined.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: 3;
	 * </p>
	 *
	 * @param edge_lengthDataset the edge_lengthDataset
	 */
	public DataNode setEdge_length(IDataset edge_lengthDataset);

	/**
	 * Length of the edges of each triangle.
	 * For each triangle values are reported via traversing
	 * the vertices in the sequence as these are defined.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getEdge_lengthScalar();

	/**
	 * Length of the edges of each triangle.
	 * For each triangle values are reported via traversing
	 * the vertices in the sequence as these are defined.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: 3;
	 * </p>
	 *
	 * @param edge_length the edge_length
	 */
	public DataNode setEdge_lengthScalar(Number edge_lengthValue);

	/**
	 * Interior angles of each triangle.
	 * For each triangle values are reported for the angle opposite
	 * to the respective edges in the sequence how vertices are defined.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: c; 2: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getInterior_angle();

	/**
	 * Interior angles of each triangle.
	 * For each triangle values are reported for the angle opposite
	 * to the respective edges in the sequence how vertices are defined.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: c; 2: 3;
	 * </p>
	 *
	 * @param interior_angleDataset the interior_angleDataset
	 */
	public DataNode setInterior_angle(IDataset interior_angleDataset);

	/**
	 * Interior angles of each triangle.
	 * For each triangle values are reported for the angle opposite
	 * to the respective edges in the sequence how vertices are defined.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: c; 2: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getInterior_angleScalar();

	/**
	 * Interior angles of each triangle.
	 * For each triangle values are reported for the angle opposite
	 * to the respective edges in the sequence how vertices are defined.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: c; 2: 3;
	 * </p>
	 *
	 * @param interior_angle the interior_angle
	 */
	public DataNode setInterior_angleScalar(Number interior_angleValue);

}

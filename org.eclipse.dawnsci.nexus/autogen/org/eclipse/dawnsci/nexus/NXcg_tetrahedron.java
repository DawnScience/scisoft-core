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
 * Computational geometry description of a set of tetrahedra.
 * Among hexahedral elements, tetrahedral elements are one of the most
 * frequently used geometric primitive for meshing and describing volumetric
 * objects in continuum-field simulations.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>c</b>
 * The cardinality of the set, i.e. the number of tetrahedra.</li></ul></p>
 *
 */
public interface NXcg_tetrahedron extends NXcg_primitive {

	public static final String NX_FACE_AREA = "face_area";
	public static final String NX_EDGE_LENGTH = "edge_length";
	/**
	 * Area of each of the four triangular faces of each tetrahedron.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c; 2: 4;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getFace_area();

	/**
	 * Area of each of the four triangular faces of each tetrahedron.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c; 2: 4;
	 * </p>
	 *
	 * @param face_areaDataset the face_areaDataset
	 */
	public DataNode setFace_area(IDataset face_areaDataset);

	/**
	 * Area of each of the four triangular faces of each tetrahedron.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c; 2: 4;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getFace_areaScalar();

	/**
	 * Area of each of the four triangular faces of each tetrahedron.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c; 2: 4;
	 * </p>
	 *
	 * @param face_area the face_area
	 */
	public DataNode setFace_areaScalar(Number face_areaValue);

	/**
	 * Length of each edge of each tetrahedron.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: 6;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getEdge_length();

	/**
	 * Length of each edge of each tetrahedron.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: 6;
	 * </p>
	 *
	 * @param edge_lengthDataset the edge_lengthDataset
	 */
	public DataNode setEdge_length(IDataset edge_lengthDataset);

	/**
	 * Length of each edge of each tetrahedron.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: 6;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getEdge_lengthScalar();

	/**
	 * Length of each edge of each tetrahedron.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: 6;
	 * </p>
	 *
	 * @param edge_length the edge_length
	 */
	public DataNode setEdge_lengthScalar(Number edge_lengthValue);

	/**
	 * Combined storage of all primitives of all tetrahedra.
	 *
	 * @return  the value.
	 */
	public NXcg_face_list_data_structure getTetrahedra();

	/**
	 * Combined storage of all primitives of all tetrahedra.
	 *
	 * @param tetrahedraGroup the tetrahedraGroup
	 */
	public void setTetrahedra(NXcg_face_list_data_structure tetrahedraGroup);

	/**
	 * Individual storage of each tetrahedron.
	 *
	 * @return  the value.
	 */
	public NXcg_face_list_data_structure getTetrahedronid();

	/**
	 * Individual storage of each tetrahedron.
	 *
	 * @param tetrahedronidGroup the tetrahedronidGroup
	 */
	public void setTetrahedronid(NXcg_face_list_data_structure tetrahedronidGroup);

	/**
	 * Individual storage of each tetrahedron as a graph.
	 *
	 * @return  the value.
	 */
	public NXcg_half_edge_data_structure getTetrahedron_half_edgeid();

	/**
	 * Individual storage of each tetrahedron as a graph.
	 *
	 * @param tetrahedron_half_edgeidGroup the tetrahedron_half_edgeidGroup
	 */
	public void setTetrahedron_half_edgeid(NXcg_half_edge_data_structure tetrahedron_half_edgeidGroup);

}

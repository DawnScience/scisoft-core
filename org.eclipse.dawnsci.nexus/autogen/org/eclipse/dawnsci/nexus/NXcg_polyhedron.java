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
 * Computational geometry description of a set of polyhedra in Euclidean space.
 * Polyhedra or so-called cells (especially in the convex of tessellations) are
 * constructed from polygon meshes. Polyhedra may make contact to allow a usage
 * of this base class for a description of tessellations.
 * For the description of more complicated manifolds and especially for polyhedra
 * with holes, users are advised to check if their particular needs are described
 * by creating customized instances of an :ref:`NXcg_polygon`.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>c</b>
 * The cardinality of the set, i.e. the number of polyhedra.</li>
 * <li><b>n_e_total</b>
 * The total number of edges for all polyhedra.</li>
 * <li><b>n_f_total</b>
 * The total number of faces for all polyhedra.</li></ul></p>
 *
 */
public interface NXcg_polyhedron extends NXcg_primitive {

	public static final String NX_NUMBER_OF_FACES = "number_of_faces";
	public static final String NX_FACE_AREA = "face_area";
	public static final String NX_NUMBER_OF_EDGES = "number_of_edges";
	public static final String NX_EDGE_LENGTH = "edge_length";
	/**
	 * The number of faces for each polyhedron. Faces of adjoining polyhedra
	 * are counted for each polyhedron.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getNumber_of_faces();

	/**
	 * The number of faces for each polyhedron. Faces of adjoining polyhedra
	 * are counted for each polyhedron.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param number_of_facesDataset the number_of_facesDataset
	 */
	public DataNode setNumber_of_faces(IDataset number_of_facesDataset);

	/**
	 * The number of faces for each polyhedron. Faces of adjoining polyhedra
	 * are counted for each polyhedron.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getNumber_of_facesScalar();

	/**
	 * The number of faces for each polyhedron. Faces of adjoining polyhedra
	 * are counted for each polyhedron.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param number_of_faces the number_of_faces
	 */
	public DataNode setNumber_of_facesScalar(Long number_of_facesValue);

	/**
	 * Area of each of faces.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: n_f_total;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getFace_area();

	/**
	 * Area of each of faces.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: n_f_total;
	 * </p>
	 *
	 * @param face_areaDataset the face_areaDataset
	 */
	public DataNode setFace_area(IDataset face_areaDataset);

	/**
	 * Area of each of faces.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: n_f_total;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getFace_areaScalar();

	/**
	 * Area of each of faces.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: n_f_total;
	 * </p>
	 *
	 * @param face_area the face_area
	 */
	public DataNode setFace_areaScalar(Number face_areaValue);

	/**
	 * The number of edges for each polyhedron. Edges of adjoining polyhedra
	 * are counted for each polyhedron.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getNumber_of_edges();

	/**
	 * The number of edges for each polyhedron. Edges of adjoining polyhedra
	 * are counted for each polyhedron.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * </p>
	 *
	 * @param number_of_edgesDataset the number_of_edgesDataset
	 */
	public DataNode setNumber_of_edges(IDataset number_of_edgesDataset);

	/**
	 * The number of edges for each polyhedron. Edges of adjoining polyhedra
	 * are counted for each polyhedron.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getNumber_of_edgesScalar();

	/**
	 * The number of edges for each polyhedron. Edges of adjoining polyhedra
	 * are counted for each polyhedron.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * </p>
	 *
	 * @param number_of_edges the number_of_edges
	 */
	public DataNode setNumber_of_edgesScalar(Long number_of_edgesValue);

	/**
	 * Length of each edge.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n_e_total;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getEdge_length();

	/**
	 * Length of each edge.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n_e_total;
	 * </p>
	 *
	 * @param edge_lengthDataset the edge_lengthDataset
	 */
	public DataNode setEdge_length(IDataset edge_lengthDataset);

	/**
	 * Length of each edge.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n_e_total;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getEdge_lengthScalar();

	/**
	 * Length of each edge.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n_e_total;
	 * </p>
	 *
	 * @param edge_length the edge_length
	 */
	public DataNode setEdge_lengthScalar(Number edge_lengthValue);

	/**
	 * Combined storage of all primitives of all polyhedra.
	 *
	 * @return  the value.
	 */
	public NXcg_face_list_data_structure getPolyhedra();

	/**
	 * Combined storage of all primitives of all polyhedra.
	 *
	 * @param polyhedraGroup the polyhedraGroup
	 */
	public void setPolyhedra(NXcg_face_list_data_structure polyhedraGroup);

	/**
	 * Individual storage of each polyhedron.
	 *
	 * @return  the value.
	 */
	public NXcg_face_list_data_structure getPolyhedronid();

	/**
	 * Individual storage of each polyhedron.
	 *
	 * @param polyhedronidGroup the polyhedronidGroup
	 */
	public void setPolyhedronid(NXcg_face_list_data_structure polyhedronidGroup);

	/**
	 * Individual storage of each polygon as a graph.
	 *
	 * @return  the value.
	 */
	public NXcg_half_edge_data_structure getPolyhedron_half_edgeid();

	/**
	 * Individual storage of each polygon as a graph.
	 *
	 * @param polyhedron_half_edgeidGroup the polyhedron_half_edgeidGroup
	 */
	public void setPolyhedron_half_edgeid(NXcg_half_edge_data_structure polyhedron_half_edgeidGroup);

}

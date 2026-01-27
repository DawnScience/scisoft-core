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
 * Computational geometry description of a half-edge data structure.
 * Such a data structure can be used to efficiently circulate around faces
 * and iterate over vertices of a planar graph. The data structure is also
 * known as a doubly connected edge list.
 * Indices can be used as identifier and thus names for individual instances.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>d</b>
 * The dimensionality, which has to be at least 2.</li>
 * <li><b>n_v</b>
 * The number of vertices.</li>
 * <li><b>n_f</b>
 * The number of faces.</li>
 * <li><b>n_he</b>
 * The number of half-edges.</li></ul></p>
 *
 */
public interface NXcg_half_edge_data_structure extends NXcg_primitive {

	public static final String NX_NUMBER_OF_VERTICES = "number_of_vertices";
	public static final String NX_NUMBER_OF_EDGES = "number_of_edges";
	public static final String NX_INDEX_OFFSET_VERTEX = "index_offset_vertex";
	public static final String NX_INDEX_OFFSET_EDGE = "index_offset_edge";
	public static final String NX_INDEX_OFFSET_FACE = "index_offset_face";
	public static final String NX_POSITION = "position";
	public static final String NX_VERTEX_INCIDENT_HALF_EDGE = "vertex_incident_half_edge";
	public static final String NX_FACE_HALF_EDGE = "face_half_edge";
	public static final String NX_HALF_EDGE_VERTEX_ORIGIN = "half_edge_vertex_origin";
	public static final String NX_HALF_EDGE_TWIN = "half_edge_twin";
	public static final String NX_HALF_EDGE_INCIDENT_FACE = "half_edge_incident_face";
	public static final String NX_HALF_EDGE_NEXT = "half_edge_next";
	public static final String NX_HALF_EDGE_PREV = "half_edge_prev";
	public static final String NX_WEINBERG_VECTOR = "weinberg_vector";
	/**
	 * Dimensionality of the primitives described.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDimensionality();

	/**
	 * Dimensionality of the primitives described.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param dimensionalityDataset the dimensionalityDataset
	 */
	public DataNode setDimensionality(IDataset dimensionalityDataset);

	/**
	 * Dimensionality of the primitives described.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getDimensionalityScalar();

	/**
	 * Dimensionality of the primitives described.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param dimensionality the dimensionality
	 */
	public DataNode setDimensionalityScalar(Long dimensionalityValue);

	/**
	 * Number of vertices for each face.
	 * Each entry represents the total number of vertices for that face,
	 * irrespectively whether vertices are shared among faces or not.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_f;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getNumber_of_vertices();

	/**
	 * Number of vertices for each face.
	 * Each entry represents the total number of vertices for that face,
	 * irrespectively whether vertices are shared among faces or not.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_f;
	 * </p>
	 *
	 * @param number_of_verticesDataset the number_of_verticesDataset
	 */
	public DataNode setNumber_of_vertices(IDataset number_of_verticesDataset);

	/**
	 * Number of vertices for each face.
	 * Each entry represents the total number of vertices for that face,
	 * irrespectively whether vertices are shared among faces or not.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_f;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getNumber_of_verticesScalar();

	/**
	 * Number of vertices for each face.
	 * Each entry represents the total number of vertices for that face,
	 * irrespectively whether vertices are shared among faces or not.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_f;
	 * </p>
	 *
	 * @param number_of_vertices the number_of_vertices
	 */
	public DataNode setNumber_of_verticesScalar(Long number_of_verticesValue);

	/**
	 * Number of edges for each face.
	 * Each entry represents the total number of edges for that face,
	 * irrespectively whether edges are shared across faces or not.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_he;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getNumber_of_edges();

	/**
	 * Number of edges for each face.
	 * Each entry represents the total number of edges for that face,
	 * irrespectively whether edges are shared across faces or not.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_he;
	 * </p>
	 *
	 * @param number_of_edgesDataset the number_of_edgesDataset
	 */
	public DataNode setNumber_of_edges(IDataset number_of_edgesDataset);

	/**
	 * Number of edges for each face.
	 * Each entry represents the total number of edges for that face,
	 * irrespectively whether edges are shared across faces or not.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_he;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getNumber_of_edgesScalar();

	/**
	 * Number of edges for each face.
	 * Each entry represents the total number of edges for that face,
	 * irrespectively whether edges are shared across faces or not.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_he;
	 * </p>
	 *
	 * @param number_of_edges the number_of_edges
	 */
	public DataNode setNumber_of_edgesScalar(Long number_of_edgesValue);

	/**
	 * Integer offset whereby the identifier of the first member
	 * of the vertices differs from zero.
	 * Identifier can be defined explicitly or implicitly.
	 * Inspect the definition of :ref:`NXcg_primitive` for further details.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIndex_offset_vertex();

	/**
	 * Integer offset whereby the identifier of the first member
	 * of the vertices differs from zero.
	 * Identifier can be defined explicitly or implicitly.
	 * Inspect the definition of :ref:`NXcg_primitive` for further details.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param index_offset_vertexDataset the index_offset_vertexDataset
	 */
	public DataNode setIndex_offset_vertex(IDataset index_offset_vertexDataset);

	/**
	 * Integer offset whereby the identifier of the first member
	 * of the vertices differs from zero.
	 * Identifier can be defined explicitly or implicitly.
	 * Inspect the definition of :ref:`NXcg_primitive` for further details.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getIndex_offset_vertexScalar();

	/**
	 * Integer offset whereby the identifier of the first member
	 * of the vertices differs from zero.
	 * Identifier can be defined explicitly or implicitly.
	 * Inspect the definition of :ref:`NXcg_primitive` for further details.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param index_offset_vertex the index_offset_vertex
	 */
	public DataNode setIndex_offset_vertexScalar(Long index_offset_vertexValue);

	/**
	 * Integer offset whereby the identifier of the first member
	 * of the edges differs from zero.
	 * Identifier can be defined explicitly or implicitly.
	 * Inspect the definition of :ref:`NXcg_primitive` for further details.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIndex_offset_edge();

	/**
	 * Integer offset whereby the identifier of the first member
	 * of the edges differs from zero.
	 * Identifier can be defined explicitly or implicitly.
	 * Inspect the definition of :ref:`NXcg_primitive` for further details.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param index_offset_edgeDataset the index_offset_edgeDataset
	 */
	public DataNode setIndex_offset_edge(IDataset index_offset_edgeDataset);

	/**
	 * Integer offset whereby the identifier of the first member
	 * of the edges differs from zero.
	 * Identifier can be defined explicitly or implicitly.
	 * Inspect the definition of :ref:`NXcg_primitive` for further details.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getIndex_offset_edgeScalar();

	/**
	 * Integer offset whereby the identifier of the first member
	 * of the edges differs from zero.
	 * Identifier can be defined explicitly or implicitly.
	 * Inspect the definition of :ref:`NXcg_primitive` for further details.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param index_offset_edge the index_offset_edge
	 */
	public DataNode setIndex_offset_edgeScalar(Long index_offset_edgeValue);

	/**
	 * Integer offset whereby the identifier of the first member
	 * of the faces differs from zero.
	 * Identifier can be defined explicitly or implicitly.
	 * Inspect the definition of :ref:`NXcg_primitive` for further details.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIndex_offset_face();

	/**
	 * Integer offset whereby the identifier of the first member
	 * of the faces differs from zero.
	 * Identifier can be defined explicitly or implicitly.
	 * Inspect the definition of :ref:`NXcg_primitive` for further details.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 *
	 * @param index_offset_faceDataset the index_offset_faceDataset
	 */
	public DataNode setIndex_offset_face(IDataset index_offset_faceDataset);

	/**
	 * Integer offset whereby the identifier of the first member
	 * of the faces differs from zero.
	 * Identifier can be defined explicitly or implicitly.
	 * Inspect the definition of :ref:`NXcg_primitive` for further details.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getIndex_offset_faceScalar();

	/**
	 * Integer offset whereby the identifier of the first member
	 * of the faces differs from zero.
	 * Identifier can be defined explicitly or implicitly.
	 * Inspect the definition of :ref:`NXcg_primitive` for further details.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 *
	 * @param index_offset_face the index_offset_face
	 */
	public DataNode setIndex_offset_faceScalar(Long index_offset_faceValue);

	/**
	 * The position of the vertices.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n_v; 2: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getPosition();

	/**
	 * The position of the vertices.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n_v; 2: d;
	 * </p>
	 *
	 * @param positionDataset the positionDataset
	 */
	public DataNode setPosition(IDataset positionDataset);

	/**
	 * The position of the vertices.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n_v; 2: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getPositionScalar();

	/**
	 * The position of the vertices.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n_v; 2: d;
	 * </p>
	 *
	 * @param position the position
	 */
	public DataNode setPositionScalar(Number positionValue);

	/**
	 * Identifier of the incident half-edge.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_v;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getVertex_incident_half_edge();

	/**
	 * Identifier of the incident half-edge.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_v;
	 * </p>
	 *
	 * @param vertex_incident_half_edgeDataset the vertex_incident_half_edgeDataset
	 */
	public DataNode setVertex_incident_half_edge(IDataset vertex_incident_half_edgeDataset);

	/**
	 * Identifier of the incident half-edge.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_v;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getVertex_incident_half_edgeScalar();

	/**
	 * Identifier of the incident half-edge.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_v;
	 * </p>
	 *
	 * @param vertex_incident_half_edge the vertex_incident_half_edge
	 */
	public DataNode setVertex_incident_half_edgeScalar(Long vertex_incident_half_edgeValue);

	/**
	 * Identifier of the (starting)/associated half-edge of the face.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_f;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getFace_half_edge();

	/**
	 * Identifier of the (starting)/associated half-edge of the face.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_f;
	 * </p>
	 *
	 * @param face_half_edgeDataset the face_half_edgeDataset
	 */
	public DataNode setFace_half_edge(IDataset face_half_edgeDataset);

	/**
	 * Identifier of the (starting)/associated half-edge of the face.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_f;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getFace_half_edgeScalar();

	/**
	 * Identifier of the (starting)/associated half-edge of the face.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_f;
	 * </p>
	 *
	 * @param face_half_edge the face_half_edge
	 */
	public DataNode setFace_half_edgeScalar(Long face_half_edgeValue);

	/**
	 * The identifier of the vertex from which this half-edge is outwards pointing.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_he;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getHalf_edge_vertex_origin();

	/**
	 * The identifier of the vertex from which this half-edge is outwards pointing.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_he;
	 * </p>
	 *
	 * @param half_edge_vertex_originDataset the half_edge_vertex_originDataset
	 */
	public DataNode setHalf_edge_vertex_origin(IDataset half_edge_vertex_originDataset);

	/**
	 * The identifier of the vertex from which this half-edge is outwards pointing.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_he;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getHalf_edge_vertex_originScalar();

	/**
	 * The identifier of the vertex from which this half-edge is outwards pointing.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_he;
	 * </p>
	 *
	 * @param half_edge_vertex_origin the half_edge_vertex_origin
	 */
	public DataNode setHalf_edge_vertex_originScalar(Long half_edge_vertex_originValue);

	/**
	 * Identifier of the associated oppositely pointing half-edge.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_he;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getHalf_edge_twin();

	/**
	 * Identifier of the associated oppositely pointing half-edge.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_he;
	 * </p>
	 *
	 * @param half_edge_twinDataset the half_edge_twinDataset
	 */
	public DataNode setHalf_edge_twin(IDataset half_edge_twinDataset);

	/**
	 * Identifier of the associated oppositely pointing half-edge.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_he;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getHalf_edge_twinScalar();

	/**
	 * Identifier of the associated oppositely pointing half-edge.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_he;
	 * </p>
	 *
	 * @param half_edge_twin the half_edge_twin
	 */
	public DataNode setHalf_edge_twinScalar(Long half_edge_twinValue);

	/**
	 * If the half-edge is a boundary half-edge the
	 * incident face identifier is NULL, i.e. 0.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_he;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getHalf_edge_incident_face();

	/**
	 * If the half-edge is a boundary half-edge the
	 * incident face identifier is NULL, i.e. 0.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_he;
	 * </p>
	 *
	 * @param half_edge_incident_faceDataset the half_edge_incident_faceDataset
	 */
	public DataNode setHalf_edge_incident_face(IDataset half_edge_incident_faceDataset);

	/**
	 * If the half-edge is a boundary half-edge the
	 * incident face identifier is NULL, i.e. 0.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_he;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getHalf_edge_incident_faceScalar();

	/**
	 * If the half-edge is a boundary half-edge the
	 * incident face identifier is NULL, i.e. 0.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_he;
	 * </p>
	 *
	 * @param half_edge_incident_face the half_edge_incident_face
	 */
	public DataNode setHalf_edge_incident_faceScalar(Long half_edge_incident_faceValue);

	/**
	 * Identifier of the next half-edge.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_he;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getHalf_edge_next();

	/**
	 * Identifier of the next half-edge.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_he;
	 * </p>
	 *
	 * @param half_edge_nextDataset the half_edge_nextDataset
	 */
	public DataNode setHalf_edge_next(IDataset half_edge_nextDataset);

	/**
	 * Identifier of the next half-edge.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_he;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getHalf_edge_nextScalar();

	/**
	 * Identifier of the next half-edge.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_he;
	 * </p>
	 *
	 * @param half_edge_next the half_edge_next
	 */
	public DataNode setHalf_edge_nextScalar(Long half_edge_nextValue);

	/**
	 * Identifier of the previous half-edge.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_he;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getHalf_edge_prev();

	/**
	 * Identifier of the previous half-edge.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_he;
	 * </p>
	 *
	 * @param half_edge_prevDataset the half_edge_prevDataset
	 */
	public DataNode setHalf_edge_prev(IDataset half_edge_prevDataset);

	/**
	 * Identifier of the previous half-edge.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_he;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getHalf_edge_prevScalar();

	/**
	 * Identifier of the previous half-edge.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_he;
	 * </p>
	 *
	 * @param half_edge_prev the half_edge_prev
	 */
	public DataNode setHalf_edge_prevScalar(Long half_edge_prevValue);

	/**
	 * Users are referred to the literature for the background of L. Weinberg's
	 * work about topological characterization of planar graphs:
	 * * `L. Weinberg 1966a, <https://dx.doi.org/10.1109/TCT.1964.1082216>`_
	 * * `L. Weinberg, 1966b, <https://dx.doi.org/10.1137/0114062>`_
	 * * `E. A. Lazar et al. <https://doi.org/10.1103/PhysRevLett.109.095505>`_
	 * and how this work can e.g. be applied in space-filling tessellations
	 * of microstructural objects like crystals/grains.
	 *
	 * @return  the value.
	 */
	public Dataset getWeinberg_vector();

	/**
	 * Users are referred to the literature for the background of L. Weinberg's
	 * work about topological characterization of planar graphs:
	 * * `L. Weinberg 1966a, <https://dx.doi.org/10.1109/TCT.1964.1082216>`_
	 * * `L. Weinberg, 1966b, <https://dx.doi.org/10.1137/0114062>`_
	 * * `E. A. Lazar et al. <https://doi.org/10.1103/PhysRevLett.109.095505>`_
	 * and how this work can e.g. be applied in space-filling tessellations
	 * of microstructural objects like crystals/grains.
	 *
	 * @param weinberg_vectorDataset the weinberg_vectorDataset
	 */
	public DataNode setWeinberg_vector(IDataset weinberg_vectorDataset);

	/**
	 * Users are referred to the literature for the background of L. Weinberg's
	 * work about topological characterization of planar graphs:
	 * * `L. Weinberg 1966a, <https://dx.doi.org/10.1109/TCT.1964.1082216>`_
	 * * `L. Weinberg, 1966b, <https://dx.doi.org/10.1137/0114062>`_
	 * * `E. A. Lazar et al. <https://doi.org/10.1103/PhysRevLett.109.095505>`_
	 * and how this work can e.g. be applied in space-filling tessellations
	 * of microstructural objects like crystals/grains.
	 *
	 * @return  the value.
	 */
	public String getWeinberg_vectorScalar();

	/**
	 * Users are referred to the literature for the background of L. Weinberg's
	 * work about topological characterization of planar graphs:
	 * * `L. Weinberg 1966a, <https://dx.doi.org/10.1109/TCT.1964.1082216>`_
	 * * `L. Weinberg, 1966b, <https://dx.doi.org/10.1137/0114062>`_
	 * * `E. A. Lazar et al. <https://doi.org/10.1103/PhysRevLett.109.095505>`_
	 * and how this work can e.g. be applied in space-filling tessellations
	 * of microstructural objects like crystals/grains.
	 *
	 * @param weinberg_vector the weinberg_vector
	 */
	public DataNode setWeinberg_vectorScalar(String weinberg_vectorValue);

}

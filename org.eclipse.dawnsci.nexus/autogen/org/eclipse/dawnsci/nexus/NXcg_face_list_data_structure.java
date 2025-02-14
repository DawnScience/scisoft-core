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
 * Computational geometry description of geometric primitives via a face and edge list.
 * Primitives must not be degenerated or self-intersect.
 * Such descriptions of primitives are frequently used for triangles and polyhedra
 * to store them on disk for visualization purposes. Although storage efficient,
 * such a description is not well suited for topological and neighborhood queries
 * of especially meshes that are built from primitives.
 * In this case, scientists may need a different view on the primitives which
 * is better represented for instance with a half_edge_data_structure instance.
 * The reason to split thus the geometric description of primitives, sets, and
 * specifically meshes of primitives is to keep the structure simple enough for
 * users without these computational geometry demands but also enable those more
 * computational geometry savy users the storing of the additionally relevant
 * data structure.
 * This is beneficial and superior over NXoff_geometry because for instance a
 * half_edge_data_structure instance can be immediately use to reinstantiate
 * the set without having to recompute the half_edge_structure from the vertex
 * and face-list based representation and thus offer a more efficient route
 * to serve applications where topological and graph-based operations are key.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>d</b>
 * The dimensionality, which has to be at least 2.</li>
 * <li><b>n_v</b>
 * The number of vertices.</li>
 * <li><b>n_e</b>
 * The number of edges.</li>
 * <li><b>n_f</b>
 * The number of faces.</li>
 * <li><b>n_total</b>
 * The total number of vertices of all faces. Faces are polygons.</li>
 * <li><b>n_weinberg</b>
 * The total number of Weinberg vector values of all faces.</li></ul></p>
 *
 */
public interface NXcg_face_list_data_structure extends NXobject {

	public static final String NX_DIMENSIONALITY = "dimensionality";
	public static final String NX_NUMBER_OF_VERTICES = "number_of_vertices";
	public static final String NX_NUMBER_OF_EDGES = "number_of_edges";
	public static final String NX_NUMBER_OF_FACES = "number_of_faces";
	public static final String NX_VERTEX_IDENTIFIER_OFFSET = "vertex_identifier_offset";
	public static final String NX_EDGE_IDENTIFIER_OFFSET = "edge_identifier_offset";
	public static final String NX_FACE_IDENTIFIER_OFFSET = "face_identifier_offset";
	public static final String NX_VERTEX_IDENTIFIER = "vertex_identifier";
	public static final String NX_EDGE_IDENTIFIER = "edge_identifier";
	public static final String NX_FACE_IDENTIFIER = "face_identifier";
	public static final String NX_VERTICES = "vertices";
	public static final String NX_EDGES = "edges";
	public static final String NX_FACES = "faces";
	public static final String NX_VERTICES_ARE_UNIQUE = "vertices_are_unique";
	public static final String NX_EDGES_ARE_UNIQUE = "edges_are_unique";
	public static final String NX_FACES_ARE_UNIQUE = "faces_are_unique";
	public static final String NX_WINDING_ORDER = "winding_order";
	/**
	 * Dimensionality.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDimensionality();

	/**
	 * Dimensionality.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param dimensionalityDataset the dimensionalityDataset
	 */
	public DataNode setDimensionality(IDataset dimensionalityDataset);

	/**
	 * Dimensionality.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getDimensionalityScalar();

	/**
	 * Dimensionality.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param dimensionality the dimensionality
	 */
	public DataNode setDimensionalityScalar(Long dimensionalityValue);

	/**
	 * Array which specifies of how many vertices each face is built.
	 * Each entry represent the total number of vertices for face, irrespectively
	 * whether vertices are shared among faces/are unique or not.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_f;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getNumber_of_vertices();

	/**
	 * Array which specifies of how many vertices each face is built.
	 * Each entry represent the total number of vertices for face, irrespectively
	 * whether vertices are shared among faces/are unique or not.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_f;
	 * </p>
	 *
	 * @param number_of_verticesDataset the number_of_verticesDataset
	 */
	public DataNode setNumber_of_vertices(IDataset number_of_verticesDataset);

	/**
	 * Array which specifies of how many vertices each face is built.
	 * Each entry represent the total number of vertices for face, irrespectively
	 * whether vertices are shared among faces/are unique or not.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_f;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getNumber_of_verticesScalar();

	/**
	 * Array which specifies of how many vertices each face is built.
	 * Each entry represent the total number of vertices for face, irrespectively
	 * whether vertices are shared among faces/are unique or not.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_f;
	 * </p>
	 *
	 * @param number_of_vertices the number_of_vertices
	 */
	public DataNode setNumber_of_verticesScalar(Long number_of_verticesValue);

	/**
	 * Number of edges.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getNumber_of_edges();

	/**
	 * Number of edges.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_edgesDataset the number_of_edgesDataset
	 */
	public DataNode setNumber_of_edges(IDataset number_of_edgesDataset);

	/**
	 * Number of edges.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getNumber_of_edgesScalar();

	/**
	 * Number of edges.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_edges the number_of_edges
	 */
	public DataNode setNumber_of_edgesScalar(Long number_of_edgesValue);

	/**
	 * Number of faces.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getNumber_of_faces();

	/**
	 * Number of faces.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_facesDataset the number_of_facesDataset
	 */
	public DataNode setNumber_of_faces(IDataset number_of_facesDataset);

	/**
	 * Number of faces.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getNumber_of_facesScalar();

	/**
	 * Number of faces.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_faces the number_of_faces
	 */
	public DataNode setNumber_of_facesScalar(Long number_of_facesValue);

	/**
	 * Integer which specifies the first index to be used for distinguishing
	 * identifiers for vertices. Identifiers are defined either implicitly
	 * or explicitly. For implicit indexing the identifiers are defined on the
	 * interval [identifier_offset, identifier_offset+c-1].
	 * For explicit indexing the identifier array has to be defined.
	 * The identifier_offset field can for example be used to communicate if
	 * identifiers are expected to start from 1 (referred to as Fortran-/Matlab-)
	 * or from 0 (referred to as C-, Python-style index notation) respectively.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getVertex_identifier_offset();

	/**
	 * Integer which specifies the first index to be used for distinguishing
	 * identifiers for vertices. Identifiers are defined either implicitly
	 * or explicitly. For implicit indexing the identifiers are defined on the
	 * interval [identifier_offset, identifier_offset+c-1].
	 * For explicit indexing the identifier array has to be defined.
	 * The identifier_offset field can for example be used to communicate if
	 * identifiers are expected to start from 1 (referred to as Fortran-/Matlab-)
	 * or from 0 (referred to as C-, Python-style index notation) respectively.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param vertex_identifier_offsetDataset the vertex_identifier_offsetDataset
	 */
	public DataNode setVertex_identifier_offset(IDataset vertex_identifier_offsetDataset);

	/**
	 * Integer which specifies the first index to be used for distinguishing
	 * identifiers for vertices. Identifiers are defined either implicitly
	 * or explicitly. For implicit indexing the identifiers are defined on the
	 * interval [identifier_offset, identifier_offset+c-1].
	 * For explicit indexing the identifier array has to be defined.
	 * The identifier_offset field can for example be used to communicate if
	 * identifiers are expected to start from 1 (referred to as Fortran-/Matlab-)
	 * or from 0 (referred to as C-, Python-style index notation) respectively.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getVertex_identifier_offsetScalar();

	/**
	 * Integer which specifies the first index to be used for distinguishing
	 * identifiers for vertices. Identifiers are defined either implicitly
	 * or explicitly. For implicit indexing the identifiers are defined on the
	 * interval [identifier_offset, identifier_offset+c-1].
	 * For explicit indexing the identifier array has to be defined.
	 * The identifier_offset field can for example be used to communicate if
	 * identifiers are expected to start from 1 (referred to as Fortran-/Matlab-)
	 * or from 0 (referred to as C-, Python-style index notation) respectively.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param vertex_identifier_offset the vertex_identifier_offset
	 */
	public DataNode setVertex_identifier_offsetScalar(Long vertex_identifier_offsetValue);

	/**
	 * Integer which specifies the first index to be used for distinguishing
	 * identifiers for edges. Identifiers are defined either implicitly
	 * or explicitly. For implicit indexing the identifiers are defined on the
	 * interval [identifier_offset, identifier_offset+c-1].
	 * For explicit indexing the identifier array has to be defined.
	 * The identifier_offset field can for example be used to communicate if
	 * identifiers are expected to start from 1 (referred to as Fortran-/Matlab-)
	 * or from 0 (referred to as C-, Python-style index notation) respectively.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getEdge_identifier_offset();

	/**
	 * Integer which specifies the first index to be used for distinguishing
	 * identifiers for edges. Identifiers are defined either implicitly
	 * or explicitly. For implicit indexing the identifiers are defined on the
	 * interval [identifier_offset, identifier_offset+c-1].
	 * For explicit indexing the identifier array has to be defined.
	 * The identifier_offset field can for example be used to communicate if
	 * identifiers are expected to start from 1 (referred to as Fortran-/Matlab-)
	 * or from 0 (referred to as C-, Python-style index notation) respectively.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param edge_identifier_offsetDataset the edge_identifier_offsetDataset
	 */
	public DataNode setEdge_identifier_offset(IDataset edge_identifier_offsetDataset);

	/**
	 * Integer which specifies the first index to be used for distinguishing
	 * identifiers for edges. Identifiers are defined either implicitly
	 * or explicitly. For implicit indexing the identifiers are defined on the
	 * interval [identifier_offset, identifier_offset+c-1].
	 * For explicit indexing the identifier array has to be defined.
	 * The identifier_offset field can for example be used to communicate if
	 * identifiers are expected to start from 1 (referred to as Fortran-/Matlab-)
	 * or from 0 (referred to as C-, Python-style index notation) respectively.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getEdge_identifier_offsetScalar();

	/**
	 * Integer which specifies the first index to be used for distinguishing
	 * identifiers for edges. Identifiers are defined either implicitly
	 * or explicitly. For implicit indexing the identifiers are defined on the
	 * interval [identifier_offset, identifier_offset+c-1].
	 * For explicit indexing the identifier array has to be defined.
	 * The identifier_offset field can for example be used to communicate if
	 * identifiers are expected to start from 1 (referred to as Fortran-/Matlab-)
	 * or from 0 (referred to as C-, Python-style index notation) respectively.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param edge_identifier_offset the edge_identifier_offset
	 */
	public DataNode setEdge_identifier_offsetScalar(Long edge_identifier_offsetValue);

	/**
	 * Integer which specifies the first index to be used for distinguishing
	 * identifiers for faces. Identifiers are defined either implicitly
	 * or explicitly. For implicit indexing the identifiers are defined on the
	 * interval [identifier_offset, identifier_offset+c-1].
	 * For explicit indexing the identifier array has to be defined.
	 * The identifier_offset field can for example be used to communicate if
	 * identifiers are expected to start from 1 (referred to as Fortran-/Matlab-)
	 * or from 0 (referred to as C-, Python-style index notation) respectively.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getFace_identifier_offset();

	/**
	 * Integer which specifies the first index to be used for distinguishing
	 * identifiers for faces. Identifiers are defined either implicitly
	 * or explicitly. For implicit indexing the identifiers are defined on the
	 * interval [identifier_offset, identifier_offset+c-1].
	 * For explicit indexing the identifier array has to be defined.
	 * The identifier_offset field can for example be used to communicate if
	 * identifiers are expected to start from 1 (referred to as Fortran-/Matlab-)
	 * or from 0 (referred to as C-, Python-style index notation) respectively.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param face_identifier_offsetDataset the face_identifier_offsetDataset
	 */
	public DataNode setFace_identifier_offset(IDataset face_identifier_offsetDataset);

	/**
	 * Integer which specifies the first index to be used for distinguishing
	 * identifiers for faces. Identifiers are defined either implicitly
	 * or explicitly. For implicit indexing the identifiers are defined on the
	 * interval [identifier_offset, identifier_offset+c-1].
	 * For explicit indexing the identifier array has to be defined.
	 * The identifier_offset field can for example be used to communicate if
	 * identifiers are expected to start from 1 (referred to as Fortran-/Matlab-)
	 * or from 0 (referred to as C-, Python-style index notation) respectively.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getFace_identifier_offsetScalar();

	/**
	 * Integer which specifies the first index to be used for distinguishing
	 * identifiers for faces. Identifiers are defined either implicitly
	 * or explicitly. For implicit indexing the identifiers are defined on the
	 * interval [identifier_offset, identifier_offset+c-1].
	 * For explicit indexing the identifier array has to be defined.
	 * The identifier_offset field can for example be used to communicate if
	 * identifiers are expected to start from 1 (referred to as Fortran-/Matlab-)
	 * or from 0 (referred to as C-, Python-style index notation) respectively.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param face_identifier_offset the face_identifier_offset
	 */
	public DataNode setFace_identifier_offsetScalar(Long face_identifier_offsetValue);

	/**
	 * Integer used to distinguish vertices explicitly.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_v;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getVertex_identifier();

	/**
	 * Integer used to distinguish vertices explicitly.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_v;
	 * </p>
	 *
	 * @param vertex_identifierDataset the vertex_identifierDataset
	 */
	public DataNode setVertex_identifier(IDataset vertex_identifierDataset);

	/**
	 * Integer used to distinguish vertices explicitly.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_v;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getVertex_identifierScalar();

	/**
	 * Integer used to distinguish vertices explicitly.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_v;
	 * </p>
	 *
	 * @param vertex_identifier the vertex_identifier
	 */
	public DataNode setVertex_identifierScalar(Long vertex_identifierValue);

	/**
	 * Integer used to distinguish edges explicitly.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_e;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getEdge_identifier();

	/**
	 * Integer used to distinguish edges explicitly.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_e;
	 * </p>
	 *
	 * @param edge_identifierDataset the edge_identifierDataset
	 */
	public DataNode setEdge_identifier(IDataset edge_identifierDataset);

	/**
	 * Integer used to distinguish edges explicitly.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_e;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getEdge_identifierScalar();

	/**
	 * Integer used to distinguish edges explicitly.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_e;
	 * </p>
	 *
	 * @param edge_identifier the edge_identifier
	 */
	public DataNode setEdge_identifierScalar(Long edge_identifierValue);

	/**
	 * Integer used to distinguish faces explicitly.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_f;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getFace_identifier();

	/**
	 * Integer used to distinguish faces explicitly.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_f;
	 * </p>
	 *
	 * @param face_identifierDataset the face_identifierDataset
	 */
	public DataNode setFace_identifier(IDataset face_identifierDataset);

	/**
	 * Integer used to distinguish faces explicitly.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_f;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getFace_identifierScalar();

	/**
	 * Integer used to distinguish faces explicitly.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_f;
	 * </p>
	 *
	 * @param face_identifier the face_identifier
	 */
	public DataNode setFace_identifierScalar(Long face_identifierValue);

	/**
	 * Positions of the vertices.
	 * Users are encouraged to reduce the vertices to unique set of positions
	 * and vertices as this supports a more efficient storage of the geometry data.
	 * It is also possible though to store the vertex positions naively in which
	 * case vertices_are_unique is likely False.
	 * Naively here means that one for example stores each vertex of a triangle
	 * mesh even though many vertices are shared between triangles and thus
	 * the positions of these vertices do not have to be duplicated.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n_v; 2: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getVertices();

	/**
	 * Positions of the vertices.
	 * Users are encouraged to reduce the vertices to unique set of positions
	 * and vertices as this supports a more efficient storage of the geometry data.
	 * It is also possible though to store the vertex positions naively in which
	 * case vertices_are_unique is likely False.
	 * Naively here means that one for example stores each vertex of a triangle
	 * mesh even though many vertices are shared between triangles and thus
	 * the positions of these vertices do not have to be duplicated.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n_v; 2: d;
	 * </p>
	 *
	 * @param verticesDataset the verticesDataset
	 */
	public DataNode setVertices(IDataset verticesDataset);

	/**
	 * Positions of the vertices.
	 * Users are encouraged to reduce the vertices to unique set of positions
	 * and vertices as this supports a more efficient storage of the geometry data.
	 * It is also possible though to store the vertex positions naively in which
	 * case vertices_are_unique is likely False.
	 * Naively here means that one for example stores each vertex of a triangle
	 * mesh even though many vertices are shared between triangles and thus
	 * the positions of these vertices do not have to be duplicated.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n_v; 2: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getVerticesScalar();

	/**
	 * Positions of the vertices.
	 * Users are encouraged to reduce the vertices to unique set of positions
	 * and vertices as this supports a more efficient storage of the geometry data.
	 * It is also possible though to store the vertex positions naively in which
	 * case vertices_are_unique is likely False.
	 * Naively here means that one for example stores each vertex of a triangle
	 * mesh even though many vertices are shared between triangles and thus
	 * the positions of these vertices do not have to be duplicated.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: n_v; 2: d;
	 * </p>
	 *
	 * @param vertices the vertices
	 */
	public DataNode setVerticesScalar(Number verticesValue);

	/**
	 * The edges are stored as a pairs of vertex identifier values.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_e; 2: 2;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getEdges();

	/**
	 * The edges are stored as a pairs of vertex identifier values.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_e; 2: 2;
	 * </p>
	 *
	 * @param edgesDataset the edgesDataset
	 */
	public DataNode setEdges(IDataset edgesDataset);

	/**
	 * The edges are stored as a pairs of vertex identifier values.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_e; 2: 2;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getEdgesScalar();

	/**
	 * The edges are stored as a pairs of vertex identifier values.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_e; 2: 2;
	 * </p>
	 *
	 * @param edges the edges
	 */
	public DataNode setEdgesScalar(Long edgesValue);

	/**
	 * Array of identifiers from vertices which describe each face.
	 * The first entry is the identifier of the start vertex of the first face,
	 * followed by the second vertex of the first face, until the last vertex
	 * of the first face. Thereafter, the start vertex of the second face, the
	 * second vertex of the second face, and so on and so forth.
	 * Therefore, summating over the number_of_vertices, allows to extract
	 * the vertex identifiers for the i-th face on the following index interval
	 * of the faces array: [$\sum_i = 0}^{i = n-1}$, $\sum_{i=0}^{i = n}$].
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_total;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getFaces();

	/**
	 * Array of identifiers from vertices which describe each face.
	 * The first entry is the identifier of the start vertex of the first face,
	 * followed by the second vertex of the first face, until the last vertex
	 * of the first face. Thereafter, the start vertex of the second face, the
	 * second vertex of the second face, and so on and so forth.
	 * Therefore, summating over the number_of_vertices, allows to extract
	 * the vertex identifiers for the i-th face on the following index interval
	 * of the faces array: [$\sum_i = 0}^{i = n-1}$, $\sum_{i=0}^{i = n}$].
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_total;
	 * </p>
	 *
	 * @param facesDataset the facesDataset
	 */
	public DataNode setFaces(IDataset facesDataset);

	/**
	 * Array of identifiers from vertices which describe each face.
	 * The first entry is the identifier of the start vertex of the first face,
	 * followed by the second vertex of the first face, until the last vertex
	 * of the first face. Thereafter, the start vertex of the second face, the
	 * second vertex of the second face, and so on and so forth.
	 * Therefore, summating over the number_of_vertices, allows to extract
	 * the vertex identifiers for the i-th face on the following index interval
	 * of the faces array: [$\sum_i = 0}^{i = n-1}$, $\sum_{i=0}^{i = n}$].
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_total;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getFacesScalar();

	/**
	 * Array of identifiers from vertices which describe each face.
	 * The first entry is the identifier of the start vertex of the first face,
	 * followed by the second vertex of the first face, until the last vertex
	 * of the first face. Thereafter, the start vertex of the second face, the
	 * second vertex of the second face, and so on and so forth.
	 * Therefore, summating over the number_of_vertices, allows to extract
	 * the vertex identifiers for the i-th face on the following index interval
	 * of the faces array: [$\sum_i = 0}^{i = n-1}$, $\sum_{i=0}^{i = n}$].
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_total;
	 * </p>
	 *
	 * @param faces the faces
	 */
	public DataNode setFacesScalar(Long facesValue);

	/**
	 * If true indicates that the vertices are all placed at different positions
	 * and have different identifiers, i.e. no points overlap or are counted twice.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getVertices_are_unique();

	/**
	 * If true indicates that the vertices are all placed at different positions
	 * and have different identifiers, i.e. no points overlap or are counted twice.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param vertices_are_uniqueDataset the vertices_are_uniqueDataset
	 */
	public DataNode setVertices_are_unique(IDataset vertices_are_uniqueDataset);

	/**
	 * If true indicates that the vertices are all placed at different positions
	 * and have different identifiers, i.e. no points overlap or are counted twice.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Boolean getVertices_are_uniqueScalar();

	/**
	 * If true indicates that the vertices are all placed at different positions
	 * and have different identifiers, i.e. no points overlap or are counted twice.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param vertices_are_unique the vertices_are_unique
	 */
	public DataNode setVertices_are_uniqueScalar(Boolean vertices_are_uniqueValue);

	/**
	 * If true indicates that no edge is stored twice. Users are encouraged to
	 * consider and use the half_edge_data_structure instead as this will work
	 * towards achieving a cleaner graph-based description if relevant and possible.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getEdges_are_unique();

	/**
	 * If true indicates that no edge is stored twice. Users are encouraged to
	 * consider and use the half_edge_data_structure instead as this will work
	 * towards achieving a cleaner graph-based description if relevant and possible.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param edges_are_uniqueDataset the edges_are_uniqueDataset
	 */
	public DataNode setEdges_are_unique(IDataset edges_are_uniqueDataset);

	/**
	 * If true indicates that no edge is stored twice. Users are encouraged to
	 * consider and use the half_edge_data_structure instead as this will work
	 * towards achieving a cleaner graph-based description if relevant and possible.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Boolean getEdges_are_uniqueScalar();

	/**
	 * If true indicates that no edge is stored twice. Users are encouraged to
	 * consider and use the half_edge_data_structure instead as this will work
	 * towards achieving a cleaner graph-based description if relevant and possible.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param edges_are_unique the edges_are_unique
	 */
	public DataNode setEdges_are_uniqueScalar(Boolean edges_are_uniqueValue);

	/**
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getFaces_are_unique();

	/**
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param faces_are_uniqueDataset the faces_are_uniqueDataset
	 */
	public DataNode setFaces_are_unique(IDataset faces_are_uniqueDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Boolean getFaces_are_uniqueScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param faces_are_unique the faces_are_unique
	 */
	public DataNode setFaces_are_uniqueScalar(Boolean faces_are_uniqueValue);

	/**
	 * Specifies for each face which winding order was used if any:
	 * * 0 - undefined
	 * * 1 - counter-clockwise (CCW)
	 * * 2 - clock-wise (CW)
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_f;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getWinding_order();

	/**
	 * Specifies for each face which winding order was used if any:
	 * * 0 - undefined
	 * * 1 - counter-clockwise (CCW)
	 * * 2 - clock-wise (CW)
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_f;
	 * </p>
	 *
	 * @param winding_orderDataset the winding_orderDataset
	 */
	public DataNode setWinding_order(IDataset winding_orderDataset);

	/**
	 * Specifies for each face which winding order was used if any:
	 * * 0 - undefined
	 * * 1 - counter-clockwise (CCW)
	 * * 2 - clock-wise (CW)
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_f;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getWinding_orderScalar();

	/**
	 * Specifies for each face which winding order was used if any:
	 * * 0 - undefined
	 * * 1 - counter-clockwise (CCW)
	 * * 2 - clock-wise (CW)
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_f;
	 * </p>
	 *
	 * @param winding_order the winding_order
	 */
	public DataNode setWinding_orderScalar(Long winding_orderValue);

}

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
 * Computational geometry of primitives via a face-and-edge-list data structure.
 * Primitives must neither be degenerated nor self-intersect but can have different
 * properties. A face-and-edge-list-based description of primitives is
 * frequently used for triangles and polyhedra to store them on disk for
 * visualization purposes (see OFF, PLY, VTK, or STL file formats).
 * Although this description is storage efficient, it is not well-suited for
 * topological analyses. In this case using a half-edge data structure is
 * an alternative.
 * Having an own base class for the data structure how primitives are stored is
 * useful to embrace both users with small or detailed specification demands.
 * Indices can be used as identifier and thus names for individual instances.
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
 * The total number of vertices of all faces. Faces are polygons.</li></ul></p>
 *
 */
public interface NXcg_face_list_data_structure extends NXcg_primitive {

	public static final String NX_NUMBER_OF_VERTICES = "number_of_vertices";
	public static final String NX_NUMBER_OF_EDGES = "number_of_edges";
	public static final String NX_NUMBER_OF_FACES = "number_of_faces";
	public static final String NX_INDEX_OFFSET_VERTEX = "index_offset_vertex";
	public static final String NX_INDEX_OFFSET_EDGE = "index_offset_edge";
	public static final String NX_INDEX_OFFSET_FACE = "index_offset_face";
	public static final String NX_INDICES_VERTEX = "indices_vertex";
	public static final String NX_INDICES_EDGE = "indices_edge";
	public static final String NX_INDICES_FACE = "indices_face";
	public static final String NX_VERTICES = "vertices";
	public static final String NX_EDGES = "edges";
	public static final String NX_FACES = "faces";
	public static final String NX_VERTICES_ARE_UNIQUE = "vertices_are_unique";
	public static final String NX_EDGES_ARE_UNIQUE = "edges_are_unique";
	public static final String NX_FACES_ARE_UNIQUE = "faces_are_unique";
	public static final String NX_WINDING_ORDER = "winding_order";
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
	 * <b>Dimensions:</b> 1: n_e;
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
	 * <b>Dimensions:</b> 1: n_e;
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
	 * <b>Dimensions:</b> 1: n_e;
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
	 * <b>Dimensions:</b> 1: n_e;
	 * </p>
	 *
	 * @param number_of_edges the number_of_edges
	 */
	public DataNode setNumber_of_edgesScalar(Long number_of_edgesValue);

	/**
	 * Number of faces of the primitives.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getNumber_of_faces();

	/**
	 * Number of faces of the primitives.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_facesDataset the number_of_facesDataset
	 */
	public DataNode setNumber_of_faces(IDataset number_of_facesDataset);

	/**
	 * Number of faces of the primitives.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getNumber_of_facesScalar();

	/**
	 * Number of faces of the primitives.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_faces the number_of_faces
	 */
	public DataNode setNumber_of_facesScalar(Long number_of_facesValue);

	/**
	 * Integer offset whereby the identifier of the first member
	 * of the vertices differs from zero.
	 * Identifier can be defined explicitly or implicitly.
	 * Inspect the definition of NXcg_primitive for further details.
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
	 * Inspect the definition of NXcg_primitive for further details.
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
	 * Inspect the definition of NXcg_primitive for further details.
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
	 * Inspect the definition of NXcg_primitive for further details.
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
	 * Inspect the definition of NXcg_primitive for further details.
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
	 * Inspect the definition of NXcg_primitive for further details.
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
	 * Inspect the definition of NXcg_primitive for further details.
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
	 * Inspect the definition of NXcg_primitive for further details.
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
	 * Inspect the definition of NXcg_primitive for further details.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIndex_offset_face();

	/**
	 * Integer offset whereby the identifier of the first member
	 * of the faces differs from zero.
	 * Identifier can be defined explicitly or implicitly.
	 * Inspect the definition of NXcg_primitive for further details.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param index_offset_faceDataset the index_offset_faceDataset
	 */
	public DataNode setIndex_offset_face(IDataset index_offset_faceDataset);

	/**
	 * Integer offset whereby the identifier of the first member
	 * of the faces differs from zero.
	 * Identifier can be defined explicitly or implicitly.
	 * Inspect the definition of NXcg_primitive for further details.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getIndex_offset_faceScalar();

	/**
	 * Integer offset whereby the identifier of the first member
	 * of the faces differs from zero.
	 * Identifier can be defined explicitly or implicitly.
	 * Inspect the definition of NXcg_primitive for further details.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param index_offset_face the index_offset_face
	 */
	public DataNode setIndex_offset_faceScalar(Long index_offset_faceValue);

	/**
	 * Integer identifier to distinguish all vertices explicitly.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_v;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIndices_vertex();

	/**
	 * Integer identifier to distinguish all vertices explicitly.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_v;
	 * </p>
	 *
	 * @param indices_vertexDataset the indices_vertexDataset
	 */
	public DataNode setIndices_vertex(IDataset indices_vertexDataset);

	/**
	 * Integer identifier to distinguish all vertices explicitly.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_v;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getIndices_vertexScalar();

	/**
	 * Integer identifier to distinguish all vertices explicitly.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_v;
	 * </p>
	 *
	 * @param indices_vertex the indices_vertex
	 */
	public DataNode setIndices_vertexScalar(Long indices_vertexValue);

	/**
	 * Integer used to distinguish all edges explicitly.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_e;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIndices_edge();

	/**
	 * Integer used to distinguish all edges explicitly.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_e;
	 * </p>
	 *
	 * @param indices_edgeDataset the indices_edgeDataset
	 */
	public DataNode setIndices_edge(IDataset indices_edgeDataset);

	/**
	 * Integer used to distinguish all edges explicitly.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_e;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getIndices_edgeScalar();

	/**
	 * Integer used to distinguish all edges explicitly.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_e;
	 * </p>
	 *
	 * @param indices_edge the indices_edge
	 */
	public DataNode setIndices_edgeScalar(Long indices_edgeValue);

	/**
	 * Integer used to distinguish all faces explicitly.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_f;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIndices_face();

	/**
	 * Integer used to distinguish all faces explicitly.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_f;
	 * </p>
	 *
	 * @param indices_faceDataset the indices_faceDataset
	 */
	public DataNode setIndices_face(IDataset indices_faceDataset);

	/**
	 * Integer used to distinguish all faces explicitly.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_f;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getIndices_faceScalar();

	/**
	 * Integer used to distinguish all faces explicitly.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_f;
	 * </p>
	 *
	 * @param indices_face the indices_face
	 */
	public DataNode setIndices_faceScalar(Long indices_faceValue);

	/**
	 * Positions of the vertices.
	 * Users are encouraged to reduce the vertices to a unique set as this may
	 * result in more efficient storage. Alternatively, storing vertex positions naively
	 * should be indicated with setting vertices_are_unique to False.
	 * Naively means that each vertex is stored even though many vertices may
	 * share the same positions.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n_v; 2: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getVertices();

	/**
	 * Positions of the vertices.
	 * Users are encouraged to reduce the vertices to a unique set as this may
	 * result in more efficient storage. Alternatively, storing vertex positions naively
	 * should be indicated with setting vertices_are_unique to False.
	 * Naively means that each vertex is stored even though many vertices may
	 * share the same positions.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n_v; 2: d;
	 * </p>
	 *
	 * @param verticesDataset the verticesDataset
	 */
	public DataNode setVertices(IDataset verticesDataset);

	/**
	 * Positions of the vertices.
	 * Users are encouraged to reduce the vertices to a unique set as this may
	 * result in more efficient storage. Alternatively, storing vertex positions naively
	 * should be indicated with setting vertices_are_unique to False.
	 * Naively means that each vertex is stored even though many vertices may
	 * share the same positions.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n_v; 2: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getVerticesScalar();

	/**
	 * Positions of the vertices.
	 * Users are encouraged to reduce the vertices to a unique set as this may
	 * result in more efficient storage. Alternatively, storing vertex positions naively
	 * should be indicated with setting vertices_are_unique to False.
	 * Naively means that each vertex is stored even though many vertices may
	 * share the same positions.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n_v; 2: d;
	 * </p>
	 *
	 * @param vertices the vertices
	 */
	public DataNode setVerticesScalar(Number verticesValue);

	/**
	 * The edges are stored as pairs of vertex identifier.
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
	 * The edges are stored as pairs of vertex identifier.
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
	 * The edges are stored as pairs of vertex identifier.
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
	 * The edges are stored as pairs of vertex identifier.
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
	 * The faces are stored as a concatenated array of vertex identifier tuples.
	 * The first entry is the identifier of the start vertex of the first face,
	 * followed by the second vertex of the first face, until the last vertex
	 * of the first face. Thereafter, the start vertex of the second face, the
	 * second vertex of the second face, and so on and so forth.
	 * Therefore, summating over the number_of_vertices, allows to extract
	 * the vertex identifiers for the i-th face on the following index interval
	 * of the faces array: :math:`[\sum_{i = 0}^{i = n-1}, \sum_{i=0}^{i = n}]`.
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
	 * The faces are stored as a concatenated array of vertex identifier tuples.
	 * The first entry is the identifier of the start vertex of the first face,
	 * followed by the second vertex of the first face, until the last vertex
	 * of the first face. Thereafter, the start vertex of the second face, the
	 * second vertex of the second face, and so on and so forth.
	 * Therefore, summating over the number_of_vertices, allows to extract
	 * the vertex identifiers for the i-th face on the following index interval
	 * of the faces array: :math:`[\sum_{i = 0}^{i = n-1}, \sum_{i=0}^{i = n}]`.
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
	 * The faces are stored as a concatenated array of vertex identifier tuples.
	 * The first entry is the identifier of the start vertex of the first face,
	 * followed by the second vertex of the first face, until the last vertex
	 * of the first face. Thereafter, the start vertex of the second face, the
	 * second vertex of the second face, and so on and so forth.
	 * Therefore, summating over the number_of_vertices, allows to extract
	 * the vertex identifiers for the i-th face on the following index interval
	 * of the faces array: :math:`[\sum_{i = 0}^{i = n-1}, \sum_{i=0}^{i = n}]`.
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
	 * The faces are stored as a concatenated array of vertex identifier tuples.
	 * The first entry is the identifier of the start vertex of the first face,
	 * followed by the second vertex of the first face, until the last vertex
	 * of the first face. Thereafter, the start vertex of the second face, the
	 * second vertex of the second face, and so on and so forth.
	 * Therefore, summating over the number_of_vertices, allows to extract
	 * the vertex identifiers for the i-th face on the following index interval
	 * of the faces array: :math:`[\sum_{i = 0}^{i = n-1}, \sum_{i=0}^{i = n}]`.
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
	 * If true, indicates that the vertices are all placed at different positions
	 * and have different identifiers, i.e. no points overlap or are counted more
	 * than once.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getVertices_are_unique();

	/**
	 * If true, indicates that the vertices are all placed at different positions
	 * and have different identifiers, i.e. no points overlap or are counted more
	 * than once.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param vertices_are_uniqueDataset the vertices_are_uniqueDataset
	 */
	public DataNode setVertices_are_unique(IDataset vertices_are_uniqueDataset);

	/**
	 * If true, indicates that the vertices are all placed at different positions
	 * and have different identifiers, i.e. no points overlap or are counted more
	 * than once.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Boolean getVertices_are_uniqueScalar();

	/**
	 * If true, indicates that the vertices are all placed at different positions
	 * and have different identifiers, i.e. no points overlap or are counted more
	 * than once.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param vertices_are_unique the vertices_are_unique
	 */
	public DataNode setVertices_are_uniqueScalar(Boolean vertices_are_uniqueValue);

	/**
	 * If true, indicates that no edge is stored more than once.
	 * Users are encouraged to consider using a half_edge_data_structure instead.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getEdges_are_unique();

	/**
	 * If true, indicates that no edge is stored more than once.
	 * Users are encouraged to consider using a half_edge_data_structure instead.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param edges_are_uniqueDataset the edges_are_uniqueDataset
	 */
	public DataNode setEdges_are_unique(IDataset edges_are_uniqueDataset);

	/**
	 * If true, indicates that no edge is stored more than once.
	 * Users are encouraged to consider using a half_edge_data_structure instead.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Boolean getEdges_are_uniqueScalar();

	/**
	 * If true, indicates that no edge is stored more than once.
	 * Users are encouraged to consider using a half_edge_data_structure instead.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param edges_are_unique the edges_are_unique
	 */
	public DataNode setEdges_are_uniqueScalar(Boolean edges_are_uniqueValue);

	/**
	 * If true, indicates that no face is stored more than once.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getFaces_are_unique();

	/**
	 * If true, indicates that no face is stored more than once.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param faces_are_uniqueDataset the faces_are_uniqueDataset
	 */
	public DataNode setFaces_are_unique(IDataset faces_are_uniqueDataset);

	/**
	 * If true, indicates that no face is stored more than once.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Boolean getFaces_are_uniqueScalar();

	/**
	 * If true, indicates that no face is stored more than once.
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

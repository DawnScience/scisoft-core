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

import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;

/**
 * Computational geometry description of a set of polylines in Euclidean space.
 * Each polyline is built from a sequence of vertices (points with identifiers).
 * Each polyline must have a start and an end point.
 * The sequence describes the positive traversal along the polyline when walking
 * from the start vertex to the end/last vertex.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>d</b>
 * The dimensionality, which has to be at least 1.</li>
 * <li><b>c</b>
 * The cardinality of the set, i.e. the number of polylines.</li>
 * <li><b>n_v</b>
 * The number of vertices, supporting the polylines.</li>
 * <li><b>n_total</b>
 * The total number of vertices traversed when visiting every polyline.</li></ul></p>
 *
 */
public interface NXcg_polyline_set extends NXobject {

	public static final String NX_DIMENSIONALITY = "dimensionality";
	public static final String NX_CARDINALITY = "cardinality";
	public static final String NX_NUMBER_OF_TOTAL_VERTICES = "number_of_total_vertices";
	public static final String NX_NUMBER_OF_VERTICES = "number_of_vertices";
	public static final String NX_IDENTIFIER_OFFSET = "identifier_offset";
	public static final String NX_IDENTIFIER = "identifier";
	public static final String NX_VERTICES = "vertices";
	public static final String NX_VERTICES_ARE_UNIQUE = "vertices_are_unique";
	public static final String NX_POLYLINES = "polylines";
	public static final String NX_LENGTH = "length";
	public static final String NX_IS_CLOSED = "is_closed";
	/**
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getDimensionality();

	/**
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param dimensionalityDataset the dimensionalityDataset
	 */
	public DataNode setDimensionality(IDataset dimensionalityDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getDimensionalityScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param dimensionality the dimensionality
	 */
	public DataNode setDimensionalityScalar(Long dimensionalityValue);

	/**
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getCardinality();

	/**
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param cardinalityDataset the cardinalityDataset
	 */
	public DataNode setCardinality(IDataset cardinalityDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getCardinalityScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param cardinality the cardinality
	 */
	public DataNode setCardinalityScalar(Long cardinalityValue);

	/**
	 * The total number of vertices, irrespective of their eventual uniqueness,
	 * i.e. the total number of vertices that have to be visited when walking
	 * along each polyline.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getNumber_of_total_vertices();

	/**
	 * The total number of vertices, irrespective of their eventual uniqueness,
	 * i.e. the total number of vertices that have to be visited when walking
	 * along each polyline.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_total_verticesDataset the number_of_total_verticesDataset
	 */
	public DataNode setNumber_of_total_vertices(IDataset number_of_total_verticesDataset);

	/**
	 * The total number of vertices, irrespective of their eventual uniqueness,
	 * i.e. the total number of vertices that have to be visited when walking
	 * along each polyline.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getNumber_of_total_verticesScalar();

	/**
	 * The total number of vertices, irrespective of their eventual uniqueness,
	 * i.e. the total number of vertices that have to be visited when walking
	 * along each polyline.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_total_vertices the number_of_total_vertices
	 */
	public DataNode setNumber_of_total_verticesScalar(Long number_of_total_verticesValue);

	/**
	 * Array which specifies of how many vertices each polyline is built.
	 * The number of vertices represent the total number of vertices for
	 * each polyline, irrespectively whether vertices are shared or not.
	 * See the docstring for polylines for further details about how
	 * a set with different polyline members should be stored.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getNumber_of_vertices();

	/**
	 * Array which specifies of how many vertices each polyline is built.
	 * The number of vertices represent the total number of vertices for
	 * each polyline, irrespectively whether vertices are shared or not.
	 * See the docstring for polylines for further details about how
	 * a set with different polyline members should be stored.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param number_of_verticesDataset the number_of_verticesDataset
	 */
	public DataNode setNumber_of_vertices(IDataset number_of_verticesDataset);

	/**
	 * Array which specifies of how many vertices each polyline is built.
	 * The number of vertices represent the total number of vertices for
	 * each polyline, irrespectively whether vertices are shared or not.
	 * See the docstring for polylines for further details about how
	 * a set with different polyline members should be stored.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getNumber_of_verticesScalar();

	/**
	 * Array which specifies of how many vertices each polyline is built.
	 * The number of vertices represent the total number of vertices for
	 * each polyline, irrespectively whether vertices are shared or not.
	 * See the docstring for polylines for further details about how
	 * a set with different polyline members should be stored.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param number_of_vertices the number_of_vertices
	 */
	public DataNode setNumber_of_verticesScalar(Long number_of_verticesValue);

	/**
	 * Reference to or definition of a coordinate system with
	 * which the qualifiers and polyline data are interpretable.
	 *
	 * @return  the value.
	 */
	public NXtransformations getTransformations();

	/**
	 * Reference to or definition of a coordinate system with
	 * which the qualifiers and polyline data are interpretable.
	 *
	 * @param transformationsGroup the transformationsGroup
	 */
	public void setTransformations(NXtransformations transformationsGroup);

	/**
	 * Get a NXtransformations node by name:
	 * <ul>
	 * <li>
	 * Reference to or definition of a coordinate system with
	 * which the qualifiers and polyline data are interpretable.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXtransformations for that node.
	 */
	public NXtransformations getTransformations(String name);

	/**
	 * Set a NXtransformations node by name:
	 * <ul>
	 * <li>
	 * Reference to or definition of a coordinate system with
	 * which the qualifiers and polyline data are interpretable.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param transformations the value to set
	 */
	public void setTransformations(String name, NXtransformations transformations);

	/**
	 * Get all NXtransformations nodes:
	 * <ul>
	 * <li>
	 * Reference to or definition of a coordinate system with
	 * which the qualifiers and polyline data are interpretable.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXtransformations for that node.
	 */
	public Map<String, NXtransformations> getAllTransformations();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Reference to or definition of a coordinate system with
	 * which the qualifiers and polyline data are interpretable.</li>
	 * </ul>
	 *
	 * @param transformations the child nodes to add
	 */

	public void setAllTransformations(Map<String, NXtransformations> transformations);


	/**
	 * Integer which specifies the first index to be used for distinguishing
	 * polylines. Identifiers are defined either implicitly
	 * or explicitly. For implicit indexing the identifiers are defined on the
	 * interval [identifier_offset, identifier_offset+c-1].
	 * For explicit indexing the identifier array has to be defined.
	 * The identifier_offset field can for example be used to communicate if the
	 * identifiers are expected to start from 1 (referred to as Fortran-/Matlab-)
	 * or from 0 (referred to as C-, Python-style index notation) respectively.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getIdentifier_offset();

	/**
	 * Integer which specifies the first index to be used for distinguishing
	 * polylines. Identifiers are defined either implicitly
	 * or explicitly. For implicit indexing the identifiers are defined on the
	 * interval [identifier_offset, identifier_offset+c-1].
	 * For explicit indexing the identifier array has to be defined.
	 * The identifier_offset field can for example be used to communicate if the
	 * identifiers are expected to start from 1 (referred to as Fortran-/Matlab-)
	 * or from 0 (referred to as C-, Python-style index notation) respectively.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param identifier_offsetDataset the identifier_offsetDataset
	 */
	public DataNode setIdentifier_offset(IDataset identifier_offsetDataset);

	/**
	 * Integer which specifies the first index to be used for distinguishing
	 * polylines. Identifiers are defined either implicitly
	 * or explicitly. For implicit indexing the identifiers are defined on the
	 * interval [identifier_offset, identifier_offset+c-1].
	 * For explicit indexing the identifier array has to be defined.
	 * The identifier_offset field can for example be used to communicate if the
	 * identifiers are expected to start from 1 (referred to as Fortran-/Matlab-)
	 * or from 0 (referred to as C-, Python-style index notation) respectively.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getIdentifier_offsetScalar();

	/**
	 * Integer which specifies the first index to be used for distinguishing
	 * polylines. Identifiers are defined either implicitly
	 * or explicitly. For implicit indexing the identifiers are defined on the
	 * interval [identifier_offset, identifier_offset+c-1].
	 * For explicit indexing the identifier array has to be defined.
	 * The identifier_offset field can for example be used to communicate if the
	 * identifiers are expected to start from 1 (referred to as Fortran-/Matlab-)
	 * or from 0 (referred to as C-, Python-style index notation) respectively.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param identifier_offset the identifier_offset
	 */
	public DataNode setIdentifier_offsetScalar(Long identifier_offsetValue);

	/**
	 * Integer used to distinguish polylines for explicit indexing.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getIdentifier();

	/**
	 * Integer used to distinguish polylines for explicit indexing.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param identifierDataset the identifierDataset
	 */
	public DataNode setIdentifier(IDataset identifierDataset);

	/**
	 * Integer used to distinguish polylines for explicit indexing.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getIdentifierScalar();

	/**
	 * Integer used to distinguish polylines for explicit indexing.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param identifier the identifier
	 */
	public DataNode setIdentifierScalar(Long identifierValue);

	/**
	 * Positions of the vertices which support the members of the polyline set.
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
	public IDataset getVertices();

	/**
	 * Positions of the vertices which support the members of the polyline set.
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
	 * Positions of the vertices which support the members of the polyline set.
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
	 * Positions of the vertices which support the members of the polyline set.
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
	 * If true indicates that the vertices are all placed at different
	 * positions and have different identifiers, i.e. no points overlap
	 * or are counted twice.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getVertices_are_unique();

	/**
	 * If true indicates that the vertices are all placed at different
	 * positions and have different identifiers, i.e. no points overlap
	 * or are counted twice.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param vertices_are_uniqueDataset the vertices_are_uniqueDataset
	 */
	public DataNode setVertices_are_unique(IDataset vertices_are_uniqueDataset);

	/**
	 * If true indicates that the vertices are all placed at different
	 * positions and have different identifiers, i.e. no points overlap
	 * or are counted twice.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Boolean getVertices_are_uniqueScalar();

	/**
	 * If true indicates that the vertices are all placed at different
	 * positions and have different identifiers, i.e. no points overlap
	 * or are counted twice.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param vertices_are_unique the vertices_are_unique
	 */
	public DataNode setVertices_are_uniqueScalar(Boolean vertices_are_uniqueValue);

	/**
	 * Sequence of vertex identifiers which describe each polyline.
	 * A trivial example is a set with two polylines with three vertices each.
	 * If the polylines meet in a junction, say the second vertex is shared
	 * and marking the junction between the two polylines, it is possible that
	 * there are only five unique positions suggesting five unique vertices.
	 * A non-trivial example is a set with several polylines, each of which with
	 * eventually different number of vertices. The array stores the vertex
	 * identifiers in the order how the polylines are visited:
	 * The first entry is the identifier of the start vertex of the first polyline,
	 * followed by the second vertex of the first polyline, until the last vertex
	 * of the polyline. Thereafter, the start vertex of the second polyline, and
	 * so on and so forth. Using the (cumulated) counts in number_of_vertices,
	 * the vertices of the n-th polyline can be accessed on the following
	 * array index interval:
	 * :math:`[\sum_{i=0}^{i=N-1}, \sum_{i=0}^{i=N}]`.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_total;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getPolylines();

	/**
	 * Sequence of vertex identifiers which describe each polyline.
	 * A trivial example is a set with two polylines with three vertices each.
	 * If the polylines meet in a junction, say the second vertex is shared
	 * and marking the junction between the two polylines, it is possible that
	 * there are only five unique positions suggesting five unique vertices.
	 * A non-trivial example is a set with several polylines, each of which with
	 * eventually different number of vertices. The array stores the vertex
	 * identifiers in the order how the polylines are visited:
	 * The first entry is the identifier of the start vertex of the first polyline,
	 * followed by the second vertex of the first polyline, until the last vertex
	 * of the polyline. Thereafter, the start vertex of the second polyline, and
	 * so on and so forth. Using the (cumulated) counts in number_of_vertices,
	 * the vertices of the n-th polyline can be accessed on the following
	 * array index interval:
	 * :math:`[\sum_{i=0}^{i=N-1}, \sum_{i=0}^{i=N}]`.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_total;
	 * </p>
	 *
	 * @param polylinesDataset the polylinesDataset
	 */
	public DataNode setPolylines(IDataset polylinesDataset);

	/**
	 * Sequence of vertex identifiers which describe each polyline.
	 * A trivial example is a set with two polylines with three vertices each.
	 * If the polylines meet in a junction, say the second vertex is shared
	 * and marking the junction between the two polylines, it is possible that
	 * there are only five unique positions suggesting five unique vertices.
	 * A non-trivial example is a set with several polylines, each of which with
	 * eventually different number of vertices. The array stores the vertex
	 * identifiers in the order how the polylines are visited:
	 * The first entry is the identifier of the start vertex of the first polyline,
	 * followed by the second vertex of the first polyline, until the last vertex
	 * of the polyline. Thereafter, the start vertex of the second polyline, and
	 * so on and so forth. Using the (cumulated) counts in number_of_vertices,
	 * the vertices of the n-th polyline can be accessed on the following
	 * array index interval:
	 * :math:`[\sum_{i=0}^{i=N-1}, \sum_{i=0}^{i=N}]`.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_total;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getPolylinesScalar();

	/**
	 * Sequence of vertex identifiers which describe each polyline.
	 * A trivial example is a set with two polylines with three vertices each.
	 * If the polylines meet in a junction, say the second vertex is shared
	 * and marking the junction between the two polylines, it is possible that
	 * there are only five unique positions suggesting five unique vertices.
	 * A non-trivial example is a set with several polylines, each of which with
	 * eventually different number of vertices. The array stores the vertex
	 * identifiers in the order how the polylines are visited:
	 * The first entry is the identifier of the start vertex of the first polyline,
	 * followed by the second vertex of the first polyline, until the last vertex
	 * of the polyline. Thereafter, the start vertex of the second polyline, and
	 * so on and so forth. Using the (cumulated) counts in number_of_vertices,
	 * the vertices of the n-th polyline can be accessed on the following
	 * array index interval:
	 * :math:`[\sum_{i=0}^{i=N-1}, \sum_{i=0}^{i=N}]`.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_total;
	 * </p>
	 *
	 * @param polylines the polylines
	 */
	public DataNode setPolylinesScalar(Long polylinesValue);

	/**
	 * The length of each polyline.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getLength();

	/**
	 * The length of each polyline.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param lengthDataset the lengthDataset
	 */
	public DataNode setLength(IDataset lengthDataset);

	/**
	 * The length of each polyline.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getLengthScalar();

	/**
	 * The length of each polyline.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param length the length
	 */
	public DataNode setLengthScalar(Number lengthValue);

	/**
	 * If true specifies that a polyline is closed, i.e.
	 * its end point is connected to the start point.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getIs_closed();

	/**
	 * If true specifies that a polyline is closed, i.e.
	 * its end point is connected to the start point.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param is_closedDataset the is_closedDataset
	 */
	public DataNode setIs_closed(IDataset is_closedDataset);

	/**
	 * If true specifies that a polyline is closed, i.e.
	 * its end point is connected to the start point.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Boolean getIs_closedScalar();

	/**
	 * If true specifies that a polyline is closed, i.e.
	 * its end point is connected to the start point.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param is_closed the is_closed
	 */
	public DataNode setIs_closedScalar(Boolean is_closedValue);

	/**
	 *
	 * @return  the value.
	 */
	public NXcg_unit_normal_set getVertex_normal();

	/**
	 *
	 * @param vertex_normalGroup the vertex_normalGroup
	 */
	public void setVertex_normal(NXcg_unit_normal_set vertex_normalGroup);

	/**
	 *
	 * @return  the value.
	 */
	public NXcg_unit_normal_set getEdge_normal();

	/**
	 *
	 * @param edge_normalGroup the edge_normalGroup
	 */
	public void setEdge_normal(NXcg_unit_normal_set edge_normalGroup);

}

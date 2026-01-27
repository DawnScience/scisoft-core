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
 * Computational geometry description of a set of polylines.
 * Each polyline is built from a sequence of vertices (points with identifiers).
 * Each polyline must have a start and an end point.
 * The sequence describes the traversal along the polyline when
 * walking from the first to the last vertex.
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
public interface NXcg_polyline extends NXcg_primitive {

	public static final String NX_NUMBER_OF_UNIQUE_VERTICES = "number_of_unique_vertices";
	public static final String NX_NUMBER_OF_TOTAL_VERTICES = "number_of_total_vertices";
	public static final String NX_NUMBER_OF_VERTICES = "number_of_vertices";
	public static final String NX_VERTICES = "vertices";
	public static final String NX_VERTICES_ARE_UNIQUE = "vertices_are_unique";
	public static final String NX_POLYLINES = "polylines";
	/**
	 * Reference to an instance of :ref:`NXcg_point` which defines the
	 * location of the vertices that are referred to in this
	 * NXcg_polyline instance.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDepends_on();

	/**
	 * Reference to an instance of :ref:`NXcg_point` which defines the
	 * location of the vertices that are referred to in this
	 * NXcg_polyline instance.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param depends_onDataset the depends_onDataset
	 */
	public DataNode setDepends_on(IDataset depends_onDataset);

	/**
	 * Reference to an instance of :ref:`NXcg_point` which defines the
	 * location of the vertices that are referred to in this
	 * NXcg_polyline instance.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getDepends_onScalar();

	/**
	 * Reference to an instance of :ref:`NXcg_point` which defines the
	 * location of the vertices that are referred to in this
	 * NXcg_polyline instance.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param depends_on the depends_on
	 */
	public DataNode setDepends_onScalar(String depends_onValue);

	/**
	 * The total number of vertices that have different positions.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getNumber_of_unique_vertices();

	/**
	 * The total number of vertices that have different positions.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_unique_verticesDataset the number_of_unique_verticesDataset
	 */
	public DataNode setNumber_of_unique_vertices(IDataset number_of_unique_verticesDataset);

	/**
	 * The total number of vertices that have different positions.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getNumber_of_unique_verticesScalar();

	/**
	 * The total number of vertices that have different positions.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_unique_vertices the number_of_unique_vertices
	 */
	public DataNode setNumber_of_unique_verticesScalar(Long number_of_unique_verticesValue);

	/**
	 * The total number of vertices, irrespective of their eventual uniqueness.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getNumber_of_total_vertices();

	/**
	 * The total number of vertices, irrespective of their eventual uniqueness.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_total_verticesDataset the number_of_total_verticesDataset
	 */
	public DataNode setNumber_of_total_vertices(IDataset number_of_total_verticesDataset);

	/**
	 * The total number of vertices, irrespective of their eventual uniqueness.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getNumber_of_total_verticesScalar();

	/**
	 * The total number of vertices, irrespective of their eventual uniqueness.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_total_vertices the number_of_total_vertices
	 */
	public DataNode setNumber_of_total_verticesScalar(Long number_of_total_verticesValue);

	/**
	 * The total number of vertices of each polyline, irrespectively
	 * whether vertices are shared by vertices or not.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getNumber_of_vertices();

	/**
	 * The total number of vertices of each polyline, irrespectively
	 * whether vertices are shared by vertices or not.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param number_of_verticesDataset the number_of_verticesDataset
	 */
	public DataNode setNumber_of_vertices(IDataset number_of_verticesDataset);

	/**
	 * The total number of vertices of each polyline, irrespectively
	 * whether vertices are shared by vertices or not.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getNumber_of_verticesScalar();

	/**
	 * The total number of vertices of each polyline, irrespectively
	 * whether vertices are shared by vertices or not.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param number_of_vertices the number_of_vertices
	 */
	public DataNode setNumber_of_verticesScalar(Long number_of_verticesValue);

	/**
	 * Positions of the vertices which support the members of the polyline set.
	 * Users are encouraged to reduce the vertices to unique positions and vertices
	 * as this often supports with storing geometry data more efficiently.
	 * It is also possible though to store the vertex positions naively
	 * in which case vertices_are_unique is likely False.
	 * Naively, here means that one stores each vertex of a triangle mesh
	 * even though many vertices are shared between triangles and thus
	 * storing multiple copies of their positions is redundant.
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
	 * Positions of the vertices which support the members of the polyline set.
	 * Users are encouraged to reduce the vertices to unique positions and vertices
	 * as this often supports with storing geometry data more efficiently.
	 * It is also possible though to store the vertex positions naively
	 * in which case vertices_are_unique is likely False.
	 * Naively, here means that one stores each vertex of a triangle mesh
	 * even though many vertices are shared between triangles and thus
	 * storing multiple copies of their positions is redundant.
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
	 * Positions of the vertices which support the members of the polyline set.
	 * Users are encouraged to reduce the vertices to unique positions and vertices
	 * as this often supports with storing geometry data more efficiently.
	 * It is also possible though to store the vertex positions naively
	 * in which case vertices_are_unique is likely False.
	 * Naively, here means that one stores each vertex of a triangle mesh
	 * even though many vertices are shared between triangles and thus
	 * storing multiple copies of their positions is redundant.
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
	 * Positions of the vertices which support the members of the polyline set.
	 * Users are encouraged to reduce the vertices to unique positions and vertices
	 * as this often supports with storing geometry data more efficiently.
	 * It is also possible though to store the vertex positions naively
	 * in which case vertices_are_unique is likely False.
	 * Naively, here means that one stores each vertex of a triangle mesh
	 * even though many vertices are shared between triangles and thus
	 * storing multiple copies of their positions is redundant.
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
	 * If true indicates that the vertices are all placed at different
	 * positions and have different identifiers, i.e. no points overlap
	 * or are counted several times.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getVertices_are_unique();

	/**
	 * If true indicates that the vertices are all placed at different
	 * positions and have different identifiers, i.e. no points overlap
	 * or are counted several times.
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
	 * or are counted several times.
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
	 * or are counted several times.
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param vertices_are_unique the vertices_are_unique
	 */
	public DataNode setVertices_are_uniqueScalar(Boolean vertices_are_uniqueValue);

	/**
	 * Sequence of identifier for vertices how they build each polyline.
	 * A trivial example is a set with two polylines with three vertices each.
	 * If the polylines meet at a vertex (assume for example that the second vertex
	 * is shared and marking the junction between the two polylines), it is possible
	 * that there are only five unique positions. This suggests to store five
	 * unique vertices.
	 * A non-trivial example is a set with several polylines. Assume that each
	 * has a different number of vertices. The array stores the identifier of
	 * the vertices in the sequence how the polylines are visited:
	 * The first entry is the identifier of the first vertex of the first polyline,
	 * followed by the second vertex of the first polyline, until the last vertex
	 * of the first polyline.
	 * Thereafter, the first vertex of the second polyline, and so on and so forth.
	 * Using the (cumulated) counts in number_of_vertices (:math:`n^v_i`),
	 * the vertices of the N-th polyline can be accessed on the array
	 * index interval :math:`[\sum_{i=0}^{i=N-1} n^v_i, \sum_{i=0}^{i=N} n^v_i]`.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_total;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getPolylines();

	/**
	 * Sequence of identifier for vertices how they build each polyline.
	 * A trivial example is a set with two polylines with three vertices each.
	 * If the polylines meet at a vertex (assume for example that the second vertex
	 * is shared and marking the junction between the two polylines), it is possible
	 * that there are only five unique positions. This suggests to store five
	 * unique vertices.
	 * A non-trivial example is a set with several polylines. Assume that each
	 * has a different number of vertices. The array stores the identifier of
	 * the vertices in the sequence how the polylines are visited:
	 * The first entry is the identifier of the first vertex of the first polyline,
	 * followed by the second vertex of the first polyline, until the last vertex
	 * of the first polyline.
	 * Thereafter, the first vertex of the second polyline, and so on and so forth.
	 * Using the (cumulated) counts in number_of_vertices (:math:`n^v_i`),
	 * the vertices of the N-th polyline can be accessed on the array
	 * index interval :math:`[\sum_{i=0}^{i=N-1} n^v_i, \sum_{i=0}^{i=N} n^v_i]`.
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
	 * Sequence of identifier for vertices how they build each polyline.
	 * A trivial example is a set with two polylines with three vertices each.
	 * If the polylines meet at a vertex (assume for example that the second vertex
	 * is shared and marking the junction between the two polylines), it is possible
	 * that there are only five unique positions. This suggests to store five
	 * unique vertices.
	 * A non-trivial example is a set with several polylines. Assume that each
	 * has a different number of vertices. The array stores the identifier of
	 * the vertices in the sequence how the polylines are visited:
	 * The first entry is the identifier of the first vertex of the first polyline,
	 * followed by the second vertex of the first polyline, until the last vertex
	 * of the first polyline.
	 * Thereafter, the first vertex of the second polyline, and so on and so forth.
	 * Using the (cumulated) counts in number_of_vertices (:math:`n^v_i`),
	 * the vertices of the N-th polyline can be accessed on the array
	 * index interval :math:`[\sum_{i=0}^{i=N-1} n^v_i, \sum_{i=0}^{i=N} n^v_i]`.
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
	 * Sequence of identifier for vertices how they build each polyline.
	 * A trivial example is a set with two polylines with three vertices each.
	 * If the polylines meet at a vertex (assume for example that the second vertex
	 * is shared and marking the junction between the two polylines), it is possible
	 * that there are only five unique positions. This suggests to store five
	 * unique vertices.
	 * A non-trivial example is a set with several polylines. Assume that each
	 * has a different number of vertices. The array stores the identifier of
	 * the vertices in the sequence how the polylines are visited:
	 * The first entry is the identifier of the first vertex of the first polyline,
	 * followed by the second vertex of the first polyline, until the last vertex
	 * of the first polyline.
	 * Thereafter, the first vertex of the second polyline, and so on and so forth.
	 * Using the (cumulated) counts in number_of_vertices (:math:`n^v_i`),
	 * the vertices of the N-th polyline can be accessed on the array
	 * index interval :math:`[\sum_{i=0}^{i=N-1} n^v_i, \sum_{i=0}^{i=N} n^v_i]`.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_total;
	 * </p>
	 *
	 * @param polylines the polylines
	 */
	public DataNode setPolylinesScalar(Long polylinesValue);

}

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
 * Computational geometry description of a set of polygons in Euclidean space.
 * Polygons are specialized polylines:
 * * A polygon is a geometric primitive that is bounded by a closed polyline
 * * All vertices of this polyline lay in the d-1 dimensional plane.
 * whereas vertices of a polyline do not necessarily lay on a plane.
 * * A polygon has at least three vertices.
 * Each polygon is built from a sequence of vertices (points with identifiers).
 * The members of a set of polygons may have a different number of vertices.
 * Sometimes a collection/set of polygons is referred to as a soup of polygons.
 * As three-dimensional objects, a set of polygons can be used to define the
 * hull of what is effectively a polyhedron; however users are advised to use
 * the specific :ref:`NXcg_polyhedron` base class if they wish to describe closed
 * polyhedra. Even more general complexes can be thought of. An example are the
 * so-called piecewise-linear complexes used in the TetGen library.
 * As these complexes can have holes though, polyhedra without holes are one
 * subclass of such complexes, users should rather design their own base class
 * e.g. NXcg_polytope to describe such even more complex primitives instead
 * of abusing this base class for such purposes.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>d</b>
 * The dimensionality, which has to be either 2 or 3.</li>
 * <li><b>c</b>
 * The cardinality of the set, i.e. the number of polygons.</li>
 * <li><b>n_total</b>
 * The total number of vertices when visiting every polygon.</li></ul></p>
 *
 */
public interface NXcg_polygon extends NXcg_primitive {

	public static final String NX_NUMBER_OF_TOTAL_VERTICES = "number_of_total_vertices";
	public static final String NX_EDGE_LENGTH = "edge_length";
	public static final String NX_INTERIOR_ANGLE = "interior_angle";
	/**
	 * The total number of vertices in the set.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getNumber_of_total_vertices();

	/**
	 * The total number of vertices in the set.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_total_verticesDataset the number_of_total_verticesDataset
	 */
	public DataNode setNumber_of_total_vertices(IDataset number_of_total_verticesDataset);

	/**
	 * The total number of vertices in the set.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getNumber_of_total_verticesScalar();

	/**
	 * The total number of vertices in the set.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_total_vertices the number_of_total_vertices
	 */
	public DataNode setNumber_of_total_verticesScalar(Long number_of_total_verticesValue);

	/**
	 * Combined storage of all primitives of all polygons.
	 *
	 * @return  the value.
	 */
	public NXcg_face_list_data_structure getPolygons();

	/**
	 * Combined storage of all primitives of all polygons.
	 *
	 * @param polygonsGroup the polygonsGroup
	 */
	public void setPolygons(NXcg_face_list_data_structure polygonsGroup);

	/**
	 * Individual storage of the mesh of each polygon.
	 *
	 * @return  the value.
	 */
	public NXcg_face_list_data_structure getPolygonid();

	/**
	 * Individual storage of the mesh of each polygon.
	 *
	 * @param polygonidGroup the polygonidGroup
	 */
	public void setPolygonid(NXcg_face_list_data_structure polygonidGroup);

	/**
	 * Individual storage of each polygon as a graph.
	 *
	 * @return  the value.
	 */
	public NXcg_half_edge_data_structure getPolygon_half_edgeid();

	/**
	 * Individual storage of each polygon as a graph.
	 *
	 * @param polygon_half_edgeidGroup the polygon_half_edgeidGroup
	 */
	public void setPolygon_half_edgeid(NXcg_half_edge_data_structure polygon_half_edgeidGroup);

	/**
	 * For each polygon its accumulated length along its edges.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getEdge_length();

	/**
	 * For each polygon its accumulated length along its edges.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param edge_lengthDataset the edge_lengthDataset
	 */
	public DataNode setEdge_length(IDataset edge_lengthDataset);

	/**
	 * For each polygon its accumulated length along its edges.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getEdge_lengthScalar();

	/**
	 * For each polygon its accumulated length along its edges.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param edge_length the edge_length
	 */
	public DataNode setEdge_lengthScalar(Number edge_lengthValue);

	/**
	 * Interior angles for each polygon. There are as many values per polygon
	 * as there are number_of_vertices.
	 * The angle is the angle at the specific vertex, i.e. between the adjoining
	 * edges of the vertex according to the sequence in the polygons array.
	 * Usually, the winding_order field is required to interpret the value.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: n_total;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getInterior_angle();

	/**
	 * Interior angles for each polygon. There are as many values per polygon
	 * as there are number_of_vertices.
	 * The angle is the angle at the specific vertex, i.e. between the adjoining
	 * edges of the vertex according to the sequence in the polygons array.
	 * Usually, the winding_order field is required to interpret the value.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: n_total;
	 * </p>
	 *
	 * @param interior_angleDataset the interior_angleDataset
	 */
	public DataNode setInterior_angle(IDataset interior_angleDataset);

	/**
	 * Interior angles for each polygon. There are as many values per polygon
	 * as there are number_of_vertices.
	 * The angle is the angle at the specific vertex, i.e. between the adjoining
	 * edges of the vertex according to the sequence in the polygons array.
	 * Usually, the winding_order field is required to interpret the value.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: n_total;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getInterior_angleScalar();

	/**
	 * Interior angles for each polygon. There are as many values per polygon
	 * as there are number_of_vertices.
	 * The angle is the angle at the specific vertex, i.e. between the adjoining
	 * edges of the vertex according to the sequence in the polygons array.
	 * Usually, the winding_order field is required to interpret the value.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANGLE
	 * <b>Dimensions:</b> 1: n_total;
	 * </p>
	 *
	 * @param interior_angle the interior_angle
	 */
	public DataNode setInterior_angleScalar(Number interior_angleValue);

	/**
	 * Curvature type:
	 * * 0 - unspecified,
	 * * 1 - convex,
	 * * 2 - concave
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getShape();

	/**
	 * Curvature type:
	 * * 0 - unspecified,
	 * * 1 - convex,
	 * * 2 - concave
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param shapeDataset the shapeDataset
	 */
	public DataNode setShape(IDataset shapeDataset);

	/**
	 * Curvature type:
	 * * 0 - unspecified,
	 * * 1 - convex,
	 * * 2 - concave
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getShapeScalar();

	/**
	 * Curvature type:
	 * * 0 - unspecified,
	 * * 1 - convex,
	 * * 2 - concave
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param shape the shape
	 */
	public DataNode setShapeScalar(Long shapeValue);

}

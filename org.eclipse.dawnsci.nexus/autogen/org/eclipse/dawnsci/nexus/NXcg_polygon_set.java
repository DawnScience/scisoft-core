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
 * Polygons are related are specialized polylines:
 * * A polygon is a geometric primitive that is bounded by a closed polyline
 * * All vertices of this polyline lay in the d-1 dimensional plane.
 * whereas vertices of a polyline do not necessarily lay on a plane.
 * * A polygon has at least three vertices.
 * Each polygon is built from a sequence of vertices (points with identifiers).
 * The members of a set of polygons may have a different number of vertices.
 * Sometimes a collection/set of polygons is referred to as a soup of polygons.
 * As three-dimensional objects, a set of polygons can be used to define the
 * hull of what is effectively a polyhedron; however users are advised to use
 * the specific NXcg_polyhedron_set base class if they wish to describe closed
 * polyhedra. Even more general complexes can be thought, for instance
 * piecewise-linear complexes, as these can have holes though, polyhedra without
 * holes are one subclass of such complexes, users should rather design an own
 * base class e.g. NXcg_polytope_set to describe such even more
 * complex primitives.
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
public interface NXcg_polygon_set extends NXobject {

	public static final String NX_DIMENSIONALITY = "dimensionality";
	public static final String NX_CARDINALITY = "cardinality";
	public static final String NX_NUMBER_OF_TOTAL_VERTICES = "number_of_total_vertices";
	public static final String NX_IDENTIFIER_OFFSET = "identifier_offset";
	public static final String NX_IDENTIFIER = "identifier";
	public static final String NX_AREA = "area";
	public static final String NX_EDGE_LENGTH = "edge_length";
	public static final String NX_INTERIOR_ANGLE = "interior_angle";
	public static final String NX_SHAPE = "shape";
	public static final String NX_CENTER = "center";
	/**
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>2</b> </li>
	 * <li><b>3</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDimensionality();

	/**
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>2</b> </li>
	 * <li><b>3</b> </li></ul></p>
	 * </p>
	 *
	 * @param dimensionalityDataset the dimensionalityDataset
	 */
	public DataNode setDimensionality(IDataset dimensionalityDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>2</b> </li>
	 * <li><b>3</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getDimensionalityScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>2</b> </li>
	 * <li><b>3</b> </li></ul></p>
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
	public Dataset getCardinality();

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
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getNumber_of_total_vertices();

	/**
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_total_verticesDataset the number_of_total_verticesDataset
	 */
	public DataNode setNumber_of_total_vertices(IDataset number_of_total_verticesDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getNumber_of_total_verticesScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_total_vertices the number_of_total_vertices
	 */
	public DataNode setNumber_of_total_verticesScalar(Long number_of_total_verticesValue);

	/**
	 * Integer which specifies the first index to be used for distinguishing
	 * polygons. Identifiers are defined either implicitly
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
	public Dataset getIdentifier_offset();

	/**
	 * Integer which specifies the first index to be used for distinguishing
	 * polygons. Identifiers are defined either implicitly
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
	 * polygons. Identifiers are defined either implicitly
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
	 * polygons. Identifiers are defined either implicitly
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
	 * Integer used to distinguish polygons for explicit indexing.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIdentifier();

	/**
	 * Integer used to distinguish polygons for explicit indexing.
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
	 * Integer used to distinguish polygons for explicit indexing.
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
	 * Integer used to distinguish polygons for explicit indexing.
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
	 * A simple approach to describe the entire set of polygons when the
	 * main intention is to store the shape of the polygons for visualization.
	 *
	 * @return  the value.
	 */
	public NXcg_face_list_data_structure getPolygons();

	/**
	 * A simple approach to describe the entire set of polygons when the
	 * main intention is to store the shape of the polygons for visualization.
	 *
	 * @param polygonsGroup the polygonsGroup
	 */
	public void setPolygons(NXcg_face_list_data_structure polygonsGroup);

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

	/**
	 *
	 * @return  the value.
	 */
	public NXcg_unit_normal_set getFace_normal();

	/**
	 *
	 * @param face_normalGroup the face_normalGroup
	 */
	public void setFace_normal(NXcg_unit_normal_set face_normalGroup);

	/**
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getArea();

	/**
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param areaDataset the areaDataset
	 */
	public DataNode setArea(IDataset areaDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getAreaScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param area the area
	 */
	public DataNode setAreaScalar(Number areaValue);

	/**
	 * The accumulated length of the polygon edge.
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
	 * The accumulated length of the polygon edge.
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
	 * The accumulated length of the polygon edge.
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
	 * The accumulated length of the polygon edge.
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
	 * Array of interior angles. There are many values per polygon as number_of_vertices.
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
	 * Array of interior angles. There are many values per polygon as number_of_vertices.
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
	 * Array of interior angles. There are many values per polygon as number_of_vertices.
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
	 * Array of interior angles. There are many values per polygon as number_of_vertices.
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

	/**
	 * The center of mass of each polygon.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getCenter();

	/**
	 * The center of mass of each polygon.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @param centerDataset the centerDataset
	 */
	public DataNode setCenter(IDataset centerDataset);

	/**
	 * The center of mass of each polygon.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getCenterScalar();

	/**
	 * The center of mass of each polygon.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @param center the center
	 */
	public DataNode setCenterScalar(Number centerValue);

	/**
	 * Axis-aligned or (approximate) (optimal) bounding boxes to each polygon.
	 *
	 * @return  the value.
	 */
	public NXcg_hexahedron_set getBounding_box();

	/**
	 * Axis-aligned or (approximate) (optimal) bounding boxes to each polygon.
	 *
	 * @param bounding_boxGroup the bounding_boxGroup
	 */
	public void setBounding_box(NXcg_hexahedron_set bounding_boxGroup);

}

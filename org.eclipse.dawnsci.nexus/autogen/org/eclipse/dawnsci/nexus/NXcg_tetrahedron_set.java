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
 * Computational geometry description of a set of tetrahedra in Euclidean space.
 * The tetrahedra do not have to be connected.
 * As tetrahedral elements they are among hexahedral elements one of the most
 * frequently used geometric primitive for meshing and describing volumetric
 * and surface descriptions of objects at the continuum scale.
 * A set of tetrahedra in 3D Euclidean space.
 * The tetrahedra do not have to be connected, can have different size,
 * can intersect, and be rotated.
 * Tetrahedra are the simplest and thus important geometrical primitive.
 * They are frequently used as elements in finite element meshing/modeling.
 * Tetrahedra have to be non-degenerated, closed, and built of triangles
 * which are not self-intersecting.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>c</b>
 * The cardinality of the set, i.e. the number of tetrahedra.</li></ul></p>
 *
 */
public interface NXcg_tetrahedron_set extends NXobject {

	public static final String NX_DIMENSIONALITY = "dimensionality";
	public static final String NX_CARDINALITY = "cardinality";
	public static final String NX_VOLUME = "volume";
	public static final String NX_CENTER = "center";
	public static final String NX_SURFACE_AREA = "surface_area";
	public static final String NX_FACE_AREA = "face_area";
	public static final String NX_EDGE_LENGTH = "edge_length";
	public static final String NX_IDENTIFIER_OFFSET = "identifier_offset";
	public static final String NX_IDENTIFIER = "identifier";
	/**
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>3</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getDimensionality();

	/**
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * <p><b>Enumeration:</b><ul>
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
	 * Interior volume
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_VOLUME
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getVolume();

	/**
	 * Interior volume
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_VOLUME
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param volumeDataset the volumeDataset
	 */
	public DataNode setVolume(IDataset volumeDataset);

	/**
	 * Interior volume
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_VOLUME
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getVolumeScalar();

	/**
	 * Interior volume
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_VOLUME
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param volume the volume
	 */
	public DataNode setVolumeScalar(Number volumeValue);

	/**
	 * Position of the geometric center, which often is but not necessarily
	 * has to be the center_of_mass of the tetrahedra.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getCenter();

	/**
	 * Position of the geometric center, which often is but not necessarily
	 * has to be the center_of_mass of the tetrahedra.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: 3;
	 * </p>
	 *
	 * @param centerDataset the centerDataset
	 */
	public DataNode setCenter(IDataset centerDataset);

	/**
	 * Position of the geometric center, which often is but not necessarily
	 * has to be the center_of_mass of the tetrahedra.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getCenterScalar();

	/**
	 * Position of the geometric center, which often is but not necessarily
	 * has to be the center_of_mass of the tetrahedra.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: 3;
	 * </p>
	 *
	 * @param center the center
	 */
	public DataNode setCenterScalar(Number centerValue);

	/**
	 * Total surface area as the sum of all four triangular faces.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getSurface_area();

	/**
	 * Total surface area as the sum of all four triangular faces.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param surface_areaDataset the surface_areaDataset
	 */
	public DataNode setSurface_area(IDataset surface_areaDataset);

	/**
	 * Total surface area as the sum of all four triangular faces.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getSurface_areaScalar();

	/**
	 * Total surface area as the sum of all four triangular faces.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param surface_area the surface_area
	 */
	public DataNode setSurface_areaScalar(Number surface_areaValue);

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
	public IDataset getFace_area();

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
	public IDataset getEdge_length();

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
	 * Reference to or definition of a coordinate system with
	 * which the qualifiers and mesh data are interpretable.
	 *
	 * @return  the value.
	 */
	public NXtransformations getTransformations();

	/**
	 * Reference to or definition of a coordinate system with
	 * which the qualifiers and mesh data are interpretable.
	 *
	 * @param transformationsGroup the transformationsGroup
	 */
	public void setTransformations(NXtransformations transformationsGroup);

	/**
	 * Get a NXtransformations node by name:
	 * <ul>
	 * <li>
	 * Reference to or definition of a coordinate system with
	 * which the qualifiers and mesh data are interpretable.</li>
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
	 * which the qualifiers and mesh data are interpretable.</li>
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
	 * which the qualifiers and mesh data are interpretable.</li>
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
	 * which the qualifiers and mesh data are interpretable.</li>
	 * </ul>
	 *
	 * @param transformations the child nodes to add
	 */

	public void setAllTransformations(Map<String, NXtransformations> transformations);


	/**
	 * Integer which specifies the first index to be used for distinguishing
	 * tetrahedra. Identifiers are defined either implicitly
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
	 * tetrahedra. Identifiers are defined either implicitly
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
	 * tetrahedra. Identifiers are defined either implicitly
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
	 * tetrahedra. Identifiers are defined either implicitly
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
	 * Integer used to distinguish tetrahedra for explicit indexing.
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
	 * Integer used to distinguish tetrahedra for explicit indexing.
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
	 * Integer used to distinguish tetrahedra for explicit indexing.
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
	 * Integer used to distinguish tetrahedra for explicit indexing.
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
	 * A simple approach to describe the entire set of tetrahedra when the
	 * main intention is to store the shape of the tetrahedra for visualization.
	 * should take the possibility to describe
	 *
	 * @return  the value.
	 */
	public NXcg_face_list_data_structure getTetrahedra();

	/**
	 * A simple approach to describe the entire set of tetrahedra when the
	 * main intention is to store the shape of the tetrahedra for visualization.
	 * should take the possibility to describe
	 *
	 * @param tetrahedraGroup the tetrahedraGroup
	 */
	public void setTetrahedra(NXcg_face_list_data_structure tetrahedraGroup);

	/**
	 * Disentangled representations of the mesh of specific tetrahedra.
	 *
	 * @return  the value.
	 */
	public NXcg_face_list_data_structure getTetrahedron();

	/**
	 * Disentangled representations of the mesh of specific tetrahedra.
	 *
	 * @param tetrahedronGroup the tetrahedronGroup
	 */
	public void setTetrahedron(NXcg_face_list_data_structure tetrahedronGroup);

	/**
	 * Disentangled representation of the planar graph that each tetrahedron
	 * represents. Such a description simplifies topological processing
	 * or analyses of mesh primitive operations and neighborhood queries.
	 *
	 * @return  the value.
	 */
	public NXcg_half_edge_data_structure getTetrahedron_half_edge();

	/**
	 * Disentangled representation of the planar graph that each tetrahedron
	 * represents. Such a description simplifies topological processing
	 * or analyses of mesh primitive operations and neighborhood queries.
	 *
	 * @param tetrahedron_half_edgeGroup the tetrahedron_half_edgeGroup
	 */
	public void setTetrahedron_half_edge(NXcg_half_edge_data_structure tetrahedron_half_edgeGroup);

}

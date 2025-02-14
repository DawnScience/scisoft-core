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
import org.eclipse.january.dataset.Dataset;

/**
 * Computational geometry description of a set of spheres in Euclidean space.
 * Each sphere can have a different radius.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>d</b>
 * The dimensionality, which has to be at least 2.</li>
 * <li><b>c</b>
 * The cardinality of the set, i.e. the number of circles or spheres.</li></ul></p>
 *
 */
public interface NXcg_sphere_set extends NXobject {

	public static final String NX_DIMENSIONALITY = "dimensionality";
	public static final String NX_CARDINALITY = "cardinality";
	public static final String NX_IDENTIFIER_OFFSET = "identifier_offset";
	public static final String NX_IDENTIFIER = "identifier";
	public static final String NX_CENTER = "center";
	public static final String NX_RADIUS = "radius";
	public static final String NX_RADII = "radii";
	public static final String NX_IS_CLOSED = "is_closed";
	public static final String NX_VOLUME = "volume";
	public static final String NX_SURFACE_AREA = "surface_area";
	/**
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDimensionality();

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
	 * Integer which specifies the first index to be used for distinguishing
	 * identifiers for spheres. Identifiers are defined either implicitly
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
	 * identifiers for spheres. Identifiers are defined either implicitly
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
	 * identifiers for spheres. Identifiers are defined either implicitly
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
	 * identifiers for spheres. Identifiers are defined either implicitly
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
	 * Integer used to distinguish spheres for explicit indexing.
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
	 * Integer used to distinguish spheres for explicit indexing.
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
	 * Integer used to distinguish spheres for explicit indexing.
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
	 * Integer used to distinguish spheres for explicit indexing.
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
	 * Geometric center of the spheres. This can be the center of mass.
	 * Dimensionality and cardinality of the point and sphere set have to match.
	 * The identifier_offset and identifier field of NXcg_point_set do not need
	 * to be used as they should be same as the identifier_offset and the
	 * identifier for the spheres.
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
	 * Geometric center of the spheres. This can be the center of mass.
	 * Dimensionality and cardinality of the point and sphere set have to match.
	 * The identifier_offset and identifier field of NXcg_point_set do not need
	 * to be used as they should be same as the identifier_offset and the
	 * identifier for the spheres.
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
	 * Geometric center of the spheres. This can be the center of mass.
	 * Dimensionality and cardinality of the point and sphere set have to match.
	 * The identifier_offset and identifier field of NXcg_point_set do not need
	 * to be used as they should be same as the identifier_offset and the
	 * identifier for the spheres.
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
	 * Geometric center of the spheres. This can be the center of mass.
	 * Dimensionality and cardinality of the point and sphere set have to match.
	 * The identifier_offset and identifier field of NXcg_point_set do not need
	 * to be used as they should be same as the identifier_offset and the
	 * identifier for the spheres.
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
	 * In the case that all spheres have the same radius.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getRadius();

	/**
	 * In the case that all spheres have the same radius.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param radiusDataset the radiusDataset
	 */
	public DataNode setRadius(IDataset radiusDataset);

	/**
	 * In the case that all spheres have the same radius.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getRadiusScalar();

	/**
	 * In the case that all spheres have the same radius.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 *
	 * @param radius the radius
	 */
	public DataNode setRadiusScalar(Number radiusValue);

	/**
	 * In the case that spheres have different radius use this
	 * instead of the radius field.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getRadii();

	/**
	 * In the case that spheres have different radius use this
	 * instead of the radius field.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param radiiDataset the radiiDataset
	 */
	public DataNode setRadii(IDataset radiiDataset);

	/**
	 * In the case that spheres have different radius use this
	 * instead of the radius field.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getRadiiScalar();

	/**
	 * In the case that spheres have different radius use this
	 * instead of the radius field.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param radii the radii
	 */
	public DataNode setRadiiScalar(Number radiiValue);

	/**
	 * Reference to or definition of a coordinate system with
	 * which the positions and directions are interpretable.
	 *
	 * @return  the value.
	 */
	public NXtransformations getTransformations();

	/**
	 * Reference to or definition of a coordinate system with
	 * which the positions and directions are interpretable.
	 *
	 * @param transformationsGroup the transformationsGroup
	 */
	public void setTransformations(NXtransformations transformationsGroup);

	/**
	 * Get a NXtransformations node by name:
	 * <ul>
	 * <li>
	 * Reference to or definition of a coordinate system with
	 * which the positions and directions are interpretable.</li>
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
	 * which the positions and directions are interpretable.</li>
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
	 * which the positions and directions are interpretable.</li>
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
	 * which the positions and directions are interpretable.</li>
	 * </ul>
	 *
	 * @param transformations the child nodes to add
	 */

	public void setAllTransformations(Map<String, NXtransformations> transformations);


	/**
	 * Are the spheres closed or hollow?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIs_closed();

	/**
	 * Are the spheres closed or hollow?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param is_closedDataset the is_closedDataset
	 */
	public DataNode setIs_closed(IDataset is_closedDataset);

	/**
	 * Are the spheres closed or hollow?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Boolean getIs_closedScalar();

	/**
	 * Are the spheres closed or hollow?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param is_closed the is_closed
	 */
	public DataNode setIs_closedScalar(Boolean is_closedValue);

	/**
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getVolume();

	/**
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param volumeDataset the volumeDataset
	 */
	public DataNode setVolume(IDataset volumeDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getVolumeScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param volume the volume
	 */
	public DataNode setVolumeScalar(Number volumeValue);

	/**
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSurface_area();

	/**
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param surface_areaDataset the surface_areaDataset
	 */
	public DataNode setSurface_area(IDataset surface_areaDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getSurface_areaScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param surface_area the surface_area
	 */
	public DataNode setSurface_areaScalar(Number surface_areaValue);

}

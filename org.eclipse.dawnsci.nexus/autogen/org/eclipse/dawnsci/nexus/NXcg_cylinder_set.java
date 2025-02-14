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
 * Computational geometry description of a set of cylinders in Euclidean space.
 * The members of the set can have different size. For each member the position
 * of the center and the height is mandatory. The radius can either be defined
 * in the radius field or by filling both the upper and the lower radius field.
 * The latter case can be used to represent truncated cones.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>c</b>
 * The cardinality of the set, i.e. the number of cylinders or cones.</li></ul></p>
 *
 */
public interface NXcg_cylinder_set extends NXobject {

	public static final String NX_DIMENSIONALITY = "dimensionality";
	public static final String NX_CARDINALITY = "cardinality";
	public static final String NX_IDENTIFIER_OFFSET = "identifier_offset";
	public static final String NX_IDENTIFIER = "identifier";
	public static final String NX_CENTER = "center";
	public static final String NX_HEIGHT = "height";
	public static final String NX_RADII = "radii";
	public static final String NX_UPPER_CAP_RADIUS = "upper_cap_radius";
	public static final String NX_LOWER_CAP_RADIUS = "lower_cap_radius";
	public static final String NX_VOLUME = "volume";
	public static final String NX_LATERAL_SURFACE_AREA = "lateral_surface_area";
	public static final String NX_CAP_SURFACE_AREA = "cap_surface_area";
	public static final String NX_SURFACE_AREA = "surface_area";
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
	public Dataset getDimensionality();

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
	 * identifiers for cylinders. Identifiers are defined either implicitly
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
	 * identifiers for cylinders. Identifiers are defined either implicitly
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
	 * identifiers for cylinders. Identifiers are defined either implicitly
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
	 * identifiers for cylinders. Identifiers are defined either implicitly
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
	 * Integer used to distinguish members for explicit indexing.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIdentifier();

	/**
	 * Integer used to distinguish members for explicit indexing.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param identifierDataset the identifierDataset
	 */
	public DataNode setIdentifier(IDataset identifierDataset);

	/**
	 * Integer used to distinguish members for explicit indexing.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getIdentifierScalar();

	/**
	 * Integer used to distinguish members for explicit indexing.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param identifier the identifier
	 */
	public DataNode setIdentifierScalar(Long identifierValue);

	/**
	 * The geometric center of each member.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getCenter();

	/**
	 * The geometric center of each member.
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
	 * The geometric center of each member.
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
	 * The geometric center of each member.
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
	 * A direction vector which is parallel to the cylinder/cone axis and
	 * whose magnitude is the height of the cylinder/cone.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getHeight();

	/**
	 * A direction vector which is parallel to the cylinder/cone axis and
	 * whose magnitude is the height of the cylinder/cone.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: 3;
	 * </p>
	 *
	 * @param heightDataset the heightDataset
	 */
	public DataNode setHeight(IDataset heightDataset);

	/**
	 * A direction vector which is parallel to the cylinder/cone axis and
	 * whose magnitude is the height of the cylinder/cone.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getHeightScalar();

	/**
	 * A direction vector which is parallel to the cylinder/cone axis and
	 * whose magnitude is the height of the cylinder/cone.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: 3;
	 * </p>
	 *
	 * @param height the height
	 */
	public DataNode setHeightScalar(Number heightValue);

	/**
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
	 * The radius of the upper circular cap.
	 * This field, combined with lower_cap_radius can be used to
	 * describe (eventually truncated) circular cones.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getUpper_cap_radius();

	/**
	 * The radius of the upper circular cap.
	 * This field, combined with lower_cap_radius can be used to
	 * describe (eventually truncated) circular cones.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param upper_cap_radiusDataset the upper_cap_radiusDataset
	 */
	public DataNode setUpper_cap_radius(IDataset upper_cap_radiusDataset);

	/**
	 * The radius of the upper circular cap.
	 * This field, combined with lower_cap_radius can be used to
	 * describe (eventually truncated) circular cones.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getUpper_cap_radiusScalar();

	/**
	 * The radius of the upper circular cap.
	 * This field, combined with lower_cap_radius can be used to
	 * describe (eventually truncated) circular cones.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param upper_cap_radius the upper_cap_radius
	 */
	public DataNode setUpper_cap_radiusScalar(Number upper_cap_radiusValue);

	/**
	 * The radius of the upper circular cap.
	 * This field, combined with lower_cap_radius can be used to
	 * describe (eventually truncated) circular cones.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getLower_cap_radius();

	/**
	 * The radius of the upper circular cap.
	 * This field, combined with lower_cap_radius can be used to
	 * describe (eventually truncated) circular cones.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param lower_cap_radiusDataset the lower_cap_radiusDataset
	 */
	public DataNode setLower_cap_radius(IDataset lower_cap_radiusDataset);

	/**
	 * The radius of the upper circular cap.
	 * This field, combined with lower_cap_radius can be used to
	 * describe (eventually truncated) circular cones.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getLower_cap_radiusScalar();

	/**
	 * The radius of the upper circular cap.
	 * This field, combined with lower_cap_radius can be used to
	 * describe (eventually truncated) circular cones.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param lower_cap_radius the lower_cap_radius
	 */
	public DataNode setLower_cap_radiusScalar(Number lower_cap_radiusValue);

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
	 * Interior volume of each cylinder
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_VOLUME
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getVolume();

	/**
	 * Interior volume of each cylinder
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
	 * Interior volume of each cylinder
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
	 * Interior volume of each cylinder
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
	 * Lateral surface area
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getLateral_surface_area();

	/**
	 * Lateral surface area
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param lateral_surface_areaDataset the lateral_surface_areaDataset
	 */
	public DataNode setLateral_surface_area(IDataset lateral_surface_areaDataset);

	/**
	 * Lateral surface area
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getLateral_surface_areaScalar();

	/**
	 * Lateral surface area
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param lateral_surface_area the lateral_surface_area
	 */
	public DataNode setLateral_surface_areaScalar(Number lateral_surface_areaValue);

	/**
	 * Area of the upper and the lower cap of each cylinder respectively.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c; 2: 2;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getCap_surface_area();

	/**
	 * Area of the upper and the lower cap of each cylinder respectively.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c; 2: 2;
	 * </p>
	 *
	 * @param cap_surface_areaDataset the cap_surface_areaDataset
	 */
	public DataNode setCap_surface_area(IDataset cap_surface_areaDataset);

	/**
	 * Area of the upper and the lower cap of each cylinder respectively.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c; 2: 2;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getCap_surface_areaScalar();

	/**
	 * Area of the upper and the lower cap of each cylinder respectively.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c; 2: 2;
	 * </p>
	 *
	 * @param cap_surface_area the cap_surface_area
	 */
	public DataNode setCap_surface_areaScalar(Number cap_surface_areaValue);

	/**
	 * Cap and lateral surface area for each cylinder.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSurface_area();

	/**
	 * Cap and lateral surface area for each cylinder.
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
	 * Cap and lateral surface area for each cylinder.
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
	 * Cap and lateral surface area for each cylinder.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_AREA
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param surface_area the surface_area
	 */
	public DataNode setSurface_areaScalar(Number surface_areaValue);

}

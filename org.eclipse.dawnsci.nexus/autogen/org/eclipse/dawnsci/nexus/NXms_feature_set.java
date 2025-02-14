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
 * Set of topological/spatial features in materials build from other features.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>d</b>
 * Dimensionality</li>
 * <li><b>c</b>
 * Cardinality/number of members/features in the feature set.</li>
 * <li><b>n_type_dict</b>
 * Number of types in the dictionary of human-readable types of features.</li>
 * <li><b>n_parent_ids</b>
 * Total number of parent identifier.</li></ul></p>
 *
 */
public interface NXms_feature_set extends NXobject {

	public static final String NX_ATTRIBUTE_DEPENDS_ON = "depends_on";
	public static final String NX_DIMENSIONALITY = "dimensionality";
	public static final String NX_CARDINALITY = "cardinality";
	public static final String NX_TYPE_DICT_KEYWORD = "type_dict_keyword";
	public static final String NX_TYPE_DICT_VALUE = "type_dict_value";
	public static final String NX_NUMBER_OF_PARENT_IDENTIFIER = "number_of_parent_identifier";
	public static final String NX_PARENT_IDENTIFIER = "parent_identifier";
	public static final String NX_IDENTIFIER_OFFSET = "identifier_offset";
	public static final String NX_IDENTIFIER = "identifier";
	/**
	 * Name (or link?) to another NXms_feature_set which defines features what are
	 * assumed as the parents/super_features of all members in this feature set.
	 * If depends_on is set to "." or this attribute is not defined in an instance
	 * of this application definition, assume that this feature_set instance
	 * represents the root feature_set of the feature tree/graph.
	 *
	 * @return  the value.
	 */
	public String getAttributeDepends_on();

	/**
	 * Name (or link?) to another NXms_feature_set which defines features what are
	 * assumed as the parents/super_features of all members in this feature set.
	 * If depends_on is set to "." or this attribute is not defined in an instance
	 * of this application definition, assume that this feature_set instance
	 * represents the root feature_set of the feature tree/graph.
	 *
	 * @param depends_onValue the depends_onValue
	 */
	public void setAttributeDepends_on(String depends_onValue);

	/**
	 * What is the best matching description how dimensional the feature is.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>0</b> </li>
	 * <li><b>1</b> </li>
	 * <li><b>2</b> </li>
	 * <li><b>3</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDimensionality();

	/**
	 * What is the best matching description how dimensional the feature is.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>0</b> </li>
	 * <li><b>1</b> </li>
	 * <li><b>2</b> </li>
	 * <li><b>3</b> </li></ul></p>
	 * </p>
	 *
	 * @param dimensionalityDataset the dimensionalityDataset
	 */
	public DataNode setDimensionality(IDataset dimensionalityDataset);

	/**
	 * What is the best matching description how dimensional the feature is.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>0</b> </li>
	 * <li><b>1</b> </li>
	 * <li><b>2</b> </li>
	 * <li><b>3</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getDimensionalityScalar();

	/**
	 * What is the best matching description how dimensional the feature is.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>0</b> </li>
	 * <li><b>1</b> </li>
	 * <li><b>2</b> </li>
	 * <li><b>3</b> </li></ul></p>
	 * </p>
	 *
	 * @param dimensionality the dimensionality
	 */
	public DataNode setDimensionalityScalar(Long dimensionalityValue);

	/**
	 * How many features/members are in this set?
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getCardinality();

	/**
	 * How many features/members are in this set?
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param cardinalityDataset the cardinalityDataset
	 */
	public DataNode setCardinality(IDataset cardinalityDataset);

	/**
	 * How many features/members are in this set?
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getCardinalityScalar();

	/**
	 * How many features/members are in this set?
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param cardinality the cardinality
	 */
	public DataNode setCardinalityScalar(Long cardinalityValue);

	/**
	 * The keywords of the dictionary of human-readable types of features.
	 * Using terms from a community-agreed upon controlled vocabulary, e.g.
	 * atom, solute, vacancy, monomer, molecule, anti-site, crowd_ion,
	 * quadruple junction, triple line, disconnection, dislocation,
	 * kink, jog, stacking_fault, homo_phase_boundary, hetero_phase_boundary,
	 * surface, thermal_groove_root, precipitate, dispersoid, pore, crack
	 * is recommended.
	 * <p>
	 * <b>Dimensions:</b> 1: n_type_dict;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getType_dict_keyword();

	/**
	 * The keywords of the dictionary of human-readable types of features.
	 * Using terms from a community-agreed upon controlled vocabulary, e.g.
	 * atom, solute, vacancy, monomer, molecule, anti-site, crowd_ion,
	 * quadruple junction, triple line, disconnection, dislocation,
	 * kink, jog, stacking_fault, homo_phase_boundary, hetero_phase_boundary,
	 * surface, thermal_groove_root, precipitate, dispersoid, pore, crack
	 * is recommended.
	 * <p>
	 * <b>Dimensions:</b> 1: n_type_dict;
	 * </p>
	 *
	 * @param type_dict_keywordDataset the type_dict_keywordDataset
	 */
	public DataNode setType_dict_keyword(IDataset type_dict_keywordDataset);

	/**
	 * The keywords of the dictionary of human-readable types of features.
	 * Using terms from a community-agreed upon controlled vocabulary, e.g.
	 * atom, solute, vacancy, monomer, molecule, anti-site, crowd_ion,
	 * quadruple junction, triple line, disconnection, dislocation,
	 * kink, jog, stacking_fault, homo_phase_boundary, hetero_phase_boundary,
	 * surface, thermal_groove_root, precipitate, dispersoid, pore, crack
	 * is recommended.
	 * <p>
	 * <b>Dimensions:</b> 1: n_type_dict;
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getType_dict_keywordScalar();

	/**
	 * The keywords of the dictionary of human-readable types of features.
	 * Using terms from a community-agreed upon controlled vocabulary, e.g.
	 * atom, solute, vacancy, monomer, molecule, anti-site, crowd_ion,
	 * quadruple junction, triple line, disconnection, dislocation,
	 * kink, jog, stacking_fault, homo_phase_boundary, hetero_phase_boundary,
	 * surface, thermal_groove_root, precipitate, dispersoid, pore, crack
	 * is recommended.
	 * <p>
	 * <b>Dimensions:</b> 1: n_type_dict;
	 * </p>
	 *
	 * @param type_dict_keyword the type_dict_keyword
	 */
	public DataNode setType_dict_keywordScalar(String type_dict_keywordValue);

	/**
	 * The integer identifier used to resolve of which type each feature is,
	 * i.e. the values of the dictionary of human-readable types of features.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_type_dict;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getType_dict_value();

	/**
	 * The integer identifier used to resolve of which type each feature is,
	 * i.e. the values of the dictionary of human-readable types of features.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_type_dict;
	 * </p>
	 *
	 * @param type_dict_valueDataset the type_dict_valueDataset
	 */
	public DataNode setType_dict_value(IDataset type_dict_valueDataset);

	/**
	 * The integer identifier used to resolve of which type each feature is,
	 * i.e. the values of the dictionary of human-readable types of features.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_type_dict;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getType_dict_valueScalar();

	/**
	 * The integer identifier used to resolve of which type each feature is,
	 * i.e. the values of the dictionary of human-readable types of features.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_type_dict;
	 * </p>
	 *
	 * @param type_dict_value the type_dict_value
	 */
	public DataNode setType_dict_valueScalar(Long type_dict_valueValue);

	/**
	 * For each feature in the set specify the associated number of identifier
	 * to parent features as are resolvable via depends_on.
	 * This array enables to compute the array interval from which details for
	 * specific features can be extracted as if they would be stored in an own
	 * group.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getNumber_of_parent_identifier();

	/**
	 * For each feature in the set specify the associated number of identifier
	 * to parent features as are resolvable via depends_on.
	 * This array enables to compute the array interval from which details for
	 * specific features can be extracted as if they would be stored in an own
	 * group.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param number_of_parent_identifierDataset the number_of_parent_identifierDataset
	 */
	public DataNode setNumber_of_parent_identifier(IDataset number_of_parent_identifierDataset);

	/**
	 * For each feature in the set specify the associated number of identifier
	 * to parent features as are resolvable via depends_on.
	 * This array enables to compute the array interval from which details for
	 * specific features can be extracted as if they would be stored in an own
	 * group.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getNumber_of_parent_identifierScalar();

	/**
	 * For each feature in the set specify the associated number of identifier
	 * to parent features as are resolvable via depends_on.
	 * This array enables to compute the array interval from which details for
	 * specific features can be extracted as if they would be stored in an own
	 * group.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param number_of_parent_identifier the number_of_parent_identifier
	 */
	public DataNode setNumber_of_parent_identifierScalar(Long number_of_parent_identifierValue);

	/**
	 * Concatenated array of parent identifier for each feature (in the sequence)
	 * according to identifier and how the identifier of features in the here
	 * described feature set are defined (implicitly from 0, to c-1 or via explicit
	 * identifier.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_parent_ids;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getParent_identifier();

	/**
	 * Concatenated array of parent identifier for each feature (in the sequence)
	 * according to identifier and how the identifier of features in the here
	 * described feature set are defined (implicitly from 0, to c-1 or via explicit
	 * identifier.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_parent_ids;
	 * </p>
	 *
	 * @param parent_identifierDataset the parent_identifierDataset
	 */
	public DataNode setParent_identifier(IDataset parent_identifierDataset);

	/**
	 * Concatenated array of parent identifier for each feature (in the sequence)
	 * according to identifier and how the identifier of features in the here
	 * described feature set are defined (implicitly from 0, to c-1 or via explicit
	 * identifier.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_parent_ids;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getParent_identifierScalar();

	/**
	 * Concatenated array of parent identifier for each feature (in the sequence)
	 * according to identifier and how the identifier of features in the here
	 * described feature set are defined (implicitly from 0, to c-1 or via explicit
	 * identifier.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_parent_ids;
	 * </p>
	 *
	 * @param parent_identifier the parent_identifier
	 */
	public DataNode setParent_identifierScalar(Long parent_identifierValue);

	/**
	 * Integer which specifies the first index to be used for distinguishing
	 * features. Identifiers are defined either implicitly
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
	 * features. Identifiers are defined either implicitly
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
	 * features. Identifiers are defined either implicitly
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
	 * features. Identifiers are defined either implicitly
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
	 * Integer used to distinguish features for explicit indexing.
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
	 * Integer used to distinguish features for explicit indexing.
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
	 * Integer used to distinguish features for explicit indexing.
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
	 * Integer used to distinguish features for explicit indexing.
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
	 * Assumptions and parameter to arrive at geometric primitives
	 * to locate zero-dimensional/point-(like) features which are
	 * discretized/represented by points.
	 *
	 * @return  the value.
	 */
	public NXprocess getPoints();

	/**
	 * Assumptions and parameter to arrive at geometric primitives
	 * to locate zero-dimensional/point-(like) features which are
	 * discretized/represented by points.
	 *
	 * @param pointsGroup the pointsGroup
	 */
	public void setPoints(NXprocess pointsGroup);
	// Unprocessed group:geometry
	// Unprocessed group:uncertainty

	/**
	 * Assumptions and parameters to arrive at geometric primitives
	 * to locate one-dimensional/line-like features which are
	 * discretized by polylines.
	 *
	 * @return  the value.
	 */
	public NXprocess getLines();

	/**
	 * Assumptions and parameters to arrive at geometric primitives
	 * to locate one-dimensional/line-like features which are
	 * discretized by polylines.
	 *
	 * @param linesGroup the linesGroup
	 */
	public void setLines(NXprocess linesGroup);
	// Unprocessed group:geometry
	// Unprocessed group:uncertainty

	/**
	 * Assumptions and parameters to arrive at geometric primitives
	 * to locate two-dimensional/interface features which are
	 * discretized by triangulated surface meshes.
	 *
	 * @return  the value.
	 */
	public NXprocess getInterfaces();

	/**
	 * Assumptions and parameters to arrive at geometric primitives
	 * to locate two-dimensional/interface features which are
	 * discretized by triangulated surface meshes.
	 *
	 * @param interfacesGroup the interfacesGroup
	 */
	public void setInterfaces(NXprocess interfacesGroup);
	// Unprocessed group:geometry
	// Unprocessed group:uncertainty

	/**
	 * Assumptions and parameters to arrive at geometric primitives
	 * to locate three-dimensional/volumetric features which are
	 * discretized by triangulated surface meshes of polyhedra.
	 *
	 * @return  the value.
	 */
	public NXprocess getVolumes();

	/**
	 * Assumptions and parameters to arrive at geometric primitives
	 * to locate three-dimensional/volumetric features which are
	 * discretized by triangulated surface meshes of polyhedra.
	 *
	 * @param volumesGroup the volumesGroup
	 */
	public void setVolumes(NXprocess volumesGroup);
	// Unprocessed group:geometry
	// Unprocessed group:uncertainty

}

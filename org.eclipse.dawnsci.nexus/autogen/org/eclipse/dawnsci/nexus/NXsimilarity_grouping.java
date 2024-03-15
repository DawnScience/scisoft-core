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

/**
 * Metadata to the results of a similarity grouping analysis.
 * Similarity grouping analyses can be supervised segmentation or machine learning
 * clustering algorithms. These are routine methods which partition the member of
 * a set of objects/geometric primitives into (sub-)groups, features of
 * different type. A plethora of algorithms have been proposed which can be applied
 * also on geometric primitives like points, triangles, or (abstract) features aka
 * objects (including categorical sub-groups).
 * This base class considers metadata and results of one similarity grouping
 * analysis applied to a set in which objects are either categorized as noise
 * or belonging to a cluster.
 * As the results of the analysis each similarity group, here called feature
 * aka object can get a number of numerical and/or categorical labels.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>c</b>
 * Cardinality of the set.</li>
 * <li><b>n_lbl_num</b>
 * Number of numerical labels per object.</li>
 * <li><b>n_lbl_cat</b>
 * Number of categorical labels per object.</li>
 * <li><b>n_features</b>
 * Total number of similarity groups aka features, objects, clusters.</li></ul></p>
 *
 */
public interface NXsimilarity_grouping extends NXobject {

	public static final String NX_CARDINALITY = "cardinality";
	public static final String NX_NUMBER_OF_NUMERIC_LABELS = "number_of_numeric_labels";
	public static final String NX_NUMBER_OF_CATEGORICAL_LABELS = "number_of_categorical_labels";
	public static final String NX_IDENTIFIER_OFFSET = "identifier_offset";
	public static final String NX_NUMERICAL_LABEL = "numerical_label";
	public static final String NX_CATEGORICAL_LABEL = "categorical_label";
	/**
	 * Number of members in the set which is partitioned into features.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getCardinality();

	/**
	 * Number of members in the set which is partitioned into features.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param cardinalityDataset the cardinalityDataset
	 */
	public DataNode setCardinality(IDataset cardinalityDataset);

	/**
	 * Number of members in the set which is partitioned into features.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getCardinalityScalar();

	/**
	 * Number of members in the set which is partitioned into features.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param cardinality the cardinality
	 */
	public DataNode setCardinalityScalar(Long cardinalityValue);

	/**
	 * How many numerical labels does each feature have.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getNumber_of_numeric_labels();

	/**
	 * How many numerical labels does each feature have.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_numeric_labelsDataset the number_of_numeric_labelsDataset
	 */
	public DataNode setNumber_of_numeric_labels(IDataset number_of_numeric_labelsDataset);

	/**
	 * How many numerical labels does each feature have.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getNumber_of_numeric_labelsScalar();

	/**
	 * How many numerical labels does each feature have.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_numeric_labels the number_of_numeric_labels
	 */
	public DataNode setNumber_of_numeric_labelsScalar(Long number_of_numeric_labelsValue);

	/**
	 * How many categorical labels does each feature have.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getNumber_of_categorical_labels();

	/**
	 * How many categorical labels does each feature have.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_categorical_labelsDataset the number_of_categorical_labelsDataset
	 */
	public DataNode setNumber_of_categorical_labels(IDataset number_of_categorical_labelsDataset);

	/**
	 * How many categorical labels does each feature have.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getNumber_of_categorical_labelsScalar();

	/**
	 * How many categorical labels does each feature have.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_categorical_labels the number_of_categorical_labels
	 */
	public DataNode setNumber_of_categorical_labelsScalar(Long number_of_categorical_labelsValue);

	/**
	 * Which identifier is the first to be used to label a cluster.
	 * The value should be chosen in such a way that special values can be resolved:
	 * * identifier_offset-1 indicates an object belongs to no cluster.
	 * * identifier_offset-2 indicates an object belongs to the noise category.
	 * Setting for instance identifier_offset to 1 recovers the commonly used
	 * case that objects of the noise category get values to -1 and unassigned points to 0.
	 * Numerical identifier have to be strictly increasing.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_lbl_num;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getIdentifier_offset();

	/**
	 * Which identifier is the first to be used to label a cluster.
	 * The value should be chosen in such a way that special values can be resolved:
	 * * identifier_offset-1 indicates an object belongs to no cluster.
	 * * identifier_offset-2 indicates an object belongs to the noise category.
	 * Setting for instance identifier_offset to 1 recovers the commonly used
	 * case that objects of the noise category get values to -1 and unassigned points to 0.
	 * Numerical identifier have to be strictly increasing.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_lbl_num;
	 * </p>
	 *
	 * @param identifier_offsetDataset the identifier_offsetDataset
	 */
	public DataNode setIdentifier_offset(IDataset identifier_offsetDataset);

	/**
	 * Which identifier is the first to be used to label a cluster.
	 * The value should be chosen in such a way that special values can be resolved:
	 * * identifier_offset-1 indicates an object belongs to no cluster.
	 * * identifier_offset-2 indicates an object belongs to the noise category.
	 * Setting for instance identifier_offset to 1 recovers the commonly used
	 * case that objects of the noise category get values to -1 and unassigned points to 0.
	 * Numerical identifier have to be strictly increasing.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_lbl_num;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getIdentifier_offsetScalar();

	/**
	 * Which identifier is the first to be used to label a cluster.
	 * The value should be chosen in such a way that special values can be resolved:
	 * * identifier_offset-1 indicates an object belongs to no cluster.
	 * * identifier_offset-2 indicates an object belongs to the noise category.
	 * Setting for instance identifier_offset to 1 recovers the commonly used
	 * case that objects of the noise category get values to -1 and unassigned points to 0.
	 * Numerical identifier have to be strictly increasing.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_lbl_num;
	 * </p>
	 *
	 * @param identifier_offset the identifier_offset
	 */
	public DataNode setIdentifier_offsetScalar(Long identifier_offsetValue);

	/**
	 * Matrix of numerical label for each member in the set.
	 * For classical clustering algorithms this can for instance
	 * encode the cluster_identifier.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c; 2: n_lbl_num;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getNumerical_label();

	/**
	 * Matrix of numerical label for each member in the set.
	 * For classical clustering algorithms this can for instance
	 * encode the cluster_identifier.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c; 2: n_lbl_num;
	 * </p>
	 *
	 * @param numerical_labelDataset the numerical_labelDataset
	 */
	public DataNode setNumerical_label(IDataset numerical_labelDataset);

	/**
	 * Matrix of numerical label for each member in the set.
	 * For classical clustering algorithms this can for instance
	 * encode the cluster_identifier.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c; 2: n_lbl_num;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getNumerical_labelScalar();

	/**
	 * Matrix of numerical label for each member in the set.
	 * For classical clustering algorithms this can for instance
	 * encode the cluster_identifier.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c; 2: n_lbl_num;
	 * </p>
	 *
	 * @param numerical_label the numerical_label
	 */
	public DataNode setNumerical_labelScalar(Long numerical_labelValue);

	/**
	 * Matrix of categorical attribute data for each member in the set.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <b>Dimensions:</b> 1: c; 2: n_lbl_cat;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getCategorical_label();

	/**
	 * Matrix of categorical attribute data for each member in the set.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <b>Dimensions:</b> 1: c; 2: n_lbl_cat;
	 * </p>
	 *
	 * @param categorical_labelDataset the categorical_labelDataset
	 */
	public DataNode setCategorical_label(IDataset categorical_labelDataset);

	/**
	 * Matrix of categorical attribute data for each member in the set.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <b>Dimensions:</b> 1: c; 2: n_lbl_cat;
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getCategorical_labelScalar();

	/**
	 * Matrix of categorical attribute data for each member in the set.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <b>Dimensions:</b> 1: c; 2: n_lbl_cat;
	 * </p>
	 *
	 * @param categorical_label the categorical_label
	 */
	public DataNode setCategorical_labelScalar(String categorical_labelValue);

	/**
	 * In addition to the detailed storage which members was grouped to which
	 * feature/group summary statistics are stored under this group.
	 *
	 * @return  the value.
	 */
	public NXprocess getStatistics();

	/**
	 * In addition to the detailed storage which members was grouped to which
	 * feature/group summary statistics are stored under this group.
	 *
	 * @param statisticsGroup the statisticsGroup
	 */
	public void setStatistics(NXprocess statisticsGroup);

}

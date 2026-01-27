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
 * Base class to store results obtained from applying a similarity grouping (clustering) algorithm.
 * Similarity grouping algorithms are segmentation or machine learning algorithms for
 * partitioning the members of a set of objects (e.g. geometric primitives) into
 * (sub-)groups aka features of different kind/type. A plethora of algorithms exists.
 * This base class considers metadata and results of having a similarity grouping
 * algorithm applied to a set in which objects are either categorized as noise
 * or belonging to a cluster, i.e. members of a cluster.
 * The algorithm assigns each similarity group (feature/cluster) at least one
 * identifier (numerical or categorical labels) to distinguish different cluster.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>c</b>
 * Cardinality of the set.</li>
 * <li><b>n_lbl_num</b>
 * Number of numerical labels per object.</li>
 * <li><b>n_lbl_cat</b>
 * Number of categorical labels per object.</li>
 * <li><b>n_features</b>
 * Total number of similarity groups aka features/clusters.</li></ul></p>
 *
 */
public interface NXsimilarity_grouping extends NXobject {

	public static final String NX_CARDINALITY = "cardinality";
	public static final String NX_NUMBER_OF_NUMERIC_LABELS = "number_of_numeric_labels";
	public static final String NX_NUMBER_OF_CATEGORICAL_LABELS = "number_of_categorical_labels";
	public static final String NX_INDEX_OFFSET = "index_offset";
	public static final String NX_NUMERICAL_LABEL = "numerical_label";
	public static final String NX_CATEGORICAL_LABEL = "categorical_label";
	/**
	 * Number of members in the set which gets partitioned into features.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getCardinality();

	/**
	 * Number of members in the set which gets partitioned into features.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param cardinalityDataset the cardinalityDataset
	 */
	public DataNode setCardinality(IDataset cardinalityDataset);

	/**
	 * Number of members in the set which gets partitioned into features.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getCardinalityScalar();

	/**
	 * Number of members in the set which gets partitioned into features.
	 * <p>
	 * <b>Type:</b> NX_POSINT
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
	public Dataset getNumber_of_numeric_labels();

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
	public Dataset getNumber_of_categorical_labels();

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
	 * Which numerical index is the first to be used to label a feature.
	 * The value should be chosen in such a way that special values can be resolved:
	 * * index_offset - 1 indicates that an object belongs to no cluster.
	 * * index_offset - 2 indicates that an object belongs to the noise category.
	 * Setting for instance index_offset to 1 recovers the commonly used
	 * case that objects of the noise category get values to -1 and unassigned
	 * points to 0. Numerical identifier have to be strictly increasing.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIndex_offset();

	/**
	 * Which numerical index is the first to be used to label a feature.
	 * The value should be chosen in such a way that special values can be resolved:
	 * * index_offset - 1 indicates that an object belongs to no cluster.
	 * * index_offset - 2 indicates that an object belongs to the noise category.
	 * Setting for instance index_offset to 1 recovers the commonly used
	 * case that objects of the noise category get values to -1 and unassigned
	 * points to 0. Numerical identifier have to be strictly increasing.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param index_offsetDataset the index_offsetDataset
	 */
	public DataNode setIndex_offset(IDataset index_offsetDataset);

	/**
	 * Which numerical index is the first to be used to label a feature.
	 * The value should be chosen in such a way that special values can be resolved:
	 * * index_offset - 1 indicates that an object belongs to no cluster.
	 * * index_offset - 2 indicates that an object belongs to the noise category.
	 * Setting for instance index_offset to 1 recovers the commonly used
	 * case that objects of the noise category get values to -1 and unassigned
	 * points to 0. Numerical identifier have to be strictly increasing.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getIndex_offsetScalar();

	/**
	 * Which numerical index is the first to be used to label a feature.
	 * The value should be chosen in such a way that special values can be resolved:
	 * * index_offset - 1 indicates that an object belongs to no cluster.
	 * * index_offset - 2 indicates that an object belongs to the noise category.
	 * Setting for instance index_offset to 1 recovers the commonly used
	 * case that objects of the noise category get values to -1 and unassigned
	 * points to 0. Numerical identifier have to be strictly increasing.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param index_offset the index_offset
	 */
	public DataNode setIndex_offsetScalar(Long index_offsetValue);

	/**
	 * Matrix of numerical label for each member in the set.
	 * For classical clustering algorithms this can for instance
	 * encode the indices_cluster.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c; 2: n_lbl_num;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getNumerical_label();

	/**
	 * Matrix of numerical label for each member in the set.
	 * For classical clustering algorithms this can for instance
	 * encode the indices_cluster.
	 * <p>
	 * <b>Type:</b> NX_INT
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
	 * encode the indices_cluster.
	 * <p>
	 * <b>Type:</b> NX_INT
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
	 * encode the indices_cluster.
	 * <p>
	 * <b>Type:</b> NX_INT
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
	public Dataset getCategorical_label();

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
	 * In addition to the detailed storage which objects were grouped to which
	 * feature/group summary statistics are stored under this group.
	 *
	 * @return  the value.
	 */
	public NXprocess getStatistics();

	/**
	 * In addition to the detailed storage which objects were grouped to which
	 * feature/group summary statistics are stored under this group.
	 *
	 * @param statisticsGroup the statisticsGroup
	 */
	public void setStatistics(NXprocess statisticsGroup);

}

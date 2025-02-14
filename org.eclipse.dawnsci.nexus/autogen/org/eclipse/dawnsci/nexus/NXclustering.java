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
 * Metadata to the results of a clustering analysis.
 * Clustering algorithms are routine tools to segment a set of objects/primitives
 * into groups, objects of different type. A plethora of algorithms have been
 * proposed for geometric primitives as objects, such as points, triangles,
 * or (abstract) objects.
 * This base class considers metadata and results of one clustering
 * applied to a set in which objects are either categorized as noise
 * or belonging to a cluster, specifically here only one cluster.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>n_lbl_num</b>
 * Number of numeral labels per object.</li>
 * <li><b>n_lbl_cat</b>
 * Number of categorical labels per object.</li>
 * <li><b>n_cluster</b>
 * Total number of clusters detected.</li></ul></p>
 *
 */
public interface NXclustering extends NXobject {

	public static final String NX_NUMBER_OF_NUMERIC_LABELS = "number_of_numeric_labels";
	public static final String NX_NUMBER_OF_CATEGORICAL_LABELS = "number_of_categorical_labels";
	public static final String NX_OBJECTS = "objects";
	public static final String NX_NUMERIC_LABEL = "numeric_label";
	public static final String NX_CATEGORICAL_LABEL = "categorical_label";
	public static final String NX_IDENTIFIER_OFFSET = "identifier_offset";
	public static final String NX_UNASSIGNED = "unassigned";
	public static final String NX_NOISE = "noise";
	public static final String NX_NUMBER_OF_CLUSTER = "number_of_cluster";
	public static final String NX_SIZE = "size";
	/**
	 * How many numeric labels does each object have.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getNumber_of_numeric_labels();

	/**
	 * How many numeric labels does each object have.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_numeric_labelsDataset the number_of_numeric_labelsDataset
	 */
	public DataNode setNumber_of_numeric_labels(IDataset number_of_numeric_labelsDataset);

	/**
	 * How many numeric labels does each object have.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getNumber_of_numeric_labelsScalar();

	/**
	 * How many numeric labels does each object have.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_numeric_labels the number_of_numeric_labels
	 */
	public DataNode setNumber_of_numeric_labelsScalar(Long number_of_numeric_labelsValue);

	/**
	 * How many categorical labels does each object have.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getNumber_of_categorical_labels();

	/**
	 * How many categorical labels does each object have.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_categorical_labelsDataset the number_of_categorical_labelsDataset
	 */
	public DataNode setNumber_of_categorical_labels(IDataset number_of_categorical_labelsDataset);

	/**
	 * How many categorical labels does each object have.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getNumber_of_categorical_labelsScalar();

	/**
	 * How many categorical labels does each object have.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_categorical_labels the number_of_categorical_labels
	 */
	public DataNode setNumber_of_categorical_labelsScalar(Long number_of_categorical_labelsValue);

	/**
	 * Reference to a set of objects investigated in a cluster analysis.
	 * Objects must have clear integer identifier.
	 *
	 * @return  the value.
	 */
	public Dataset getObjects();

	/**
	 * Reference to a set of objects investigated in a cluster analysis.
	 * Objects must have clear integer identifier.
	 *
	 * @param objectsDataset the objectsDataset
	 */
	public DataNode setObjects(IDataset objectsDataset);

	/**
	 * Reference to a set of objects investigated in a cluster analysis.
	 * Objects must have clear integer identifier.
	 *
	 * @return  the value.
	 */
	public String getObjectsScalar();

	/**
	 * Reference to a set of objects investigated in a cluster analysis.
	 * Objects must have clear integer identifier.
	 *
	 * @param objects the objects
	 */
	public DataNode setObjectsScalar(String objectsValue);

	/**
	 * Reference to numeric attribute data for each object.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getNumeric_label();

	/**
	 * Reference to numeric attribute data for each object.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @param numeric_labelDataset the numeric_labelDataset
	 */
	public DataNode setNumeric_label(IDataset numeric_labelDataset);

	/**
	 * Reference to numeric attribute data for each object.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getNumeric_labelScalar();

	/**
	 * Reference to numeric attribute data for each object.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @param numeric_label the numeric_label
	 */
	public DataNode setNumeric_labelScalar(Number numeric_labelValue);

	/**
	 * Reference to categorical attribute data for each object.
	 *
	 * @return  the value.
	 */
	public Dataset getCategorical_label();

	/**
	 * Reference to categorical attribute data for each object.
	 *
	 * @param categorical_labelDataset the categorical_labelDataset
	 */
	public DataNode setCategorical_label(IDataset categorical_labelDataset);

	/**
	 * Reference to categorical attribute data for each object.
	 *
	 * @return  the value.
	 */
	public String getCategorical_labelScalar();

	/**
	 * Reference to categorical attribute data for each object.
	 *
	 * @param categorical_label the categorical_label
	 */
	public DataNode setCategorical_labelScalar(String categorical_labelValue);

	/**
	 * Which identifier is the first to be used to label a cluster.
	 * The value should be chosen in such a way that special values can be resolved:
	 * * identifier_offset-1 indicates an object belongs to no cluster.
	 * * identifier_offset-2 indicates an object belongs to the noise category.
	 * Setting for instance identifier_offset to 1 recovers the commonly used
	 * case that objects of the noise category get values to -1 and unassigned points to 0.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getIdentifier_offset();

	/**
	 * Which identifier is the first to be used to label a cluster.
	 * The value should be chosen in such a way that special values can be resolved:
	 * * identifier_offset-1 indicates an object belongs to no cluster.
	 * * identifier_offset-2 indicates an object belongs to the noise category.
	 * Setting for instance identifier_offset to 1 recovers the commonly used
	 * case that objects of the noise category get values to -1 and unassigned points to 0.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
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
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
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
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param identifier_offset the identifier_offset
	 */
	public DataNode setIdentifier_offsetScalar(Long identifier_offsetValue);

	/**
	 * Total number of objects categorized as unassigned.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getUnassigned();

	/**
	 * Total number of objects categorized as unassigned.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param unassignedDataset the unassignedDataset
	 */
	public DataNode setUnassigned(IDataset unassignedDataset);

	/**
	 * Total number of objects categorized as unassigned.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getUnassignedScalar();

	/**
	 * Total number of objects categorized as unassigned.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param unassigned the unassigned
	 */
	public DataNode setUnassignedScalar(Long unassignedValue);

	/**
	 * Total number of objects categorized as noise.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getNoise();

	/**
	 * Total number of objects categorized as noise.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param noiseDataset the noiseDataset
	 */
	public DataNode setNoise(IDataset noiseDataset);

	/**
	 * Total number of objects categorized as noise.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getNoiseScalar();

	/**
	 * Total number of objects categorized as noise.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param noise the noise
	 */
	public DataNode setNoiseScalar(Long noiseValue);

	/**
	 * Total number of clusters (excluding noise and unassigned).
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getNumber_of_cluster();

	/**
	 * Total number of clusters (excluding noise and unassigned).
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_clusterDataset the number_of_clusterDataset
	 */
	public DataNode setNumber_of_cluster(IDataset number_of_clusterDataset);

	/**
	 * Total number of clusters (excluding noise and unassigned).
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getNumber_of_clusterScalar();

	/**
	 * Total number of clusters (excluding noise and unassigned).
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_cluster the number_of_cluster
	 */
	public DataNode setNumber_of_clusterScalar(Long number_of_clusterValue);

	/**
	 * Number of objects associated to each cluster. The labels are implicit,
	 * meaning the zeroth/first entry in the array belongs to the first cluster,
	 * the second entry to the second cluster and so on and so forth.
	 * The first cluster has the value of identifier_offset as its identifier.
	 * The second cluster has identifier_offset + 1, and so on and so forth.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_cluster;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getSize();

	/**
	 * Number of objects associated to each cluster. The labels are implicit,
	 * meaning the zeroth/first entry in the array belongs to the first cluster,
	 * the second entry to the second cluster and so on and so forth.
	 * The first cluster has the value of identifier_offset as its identifier.
	 * The second cluster has identifier_offset + 1, and so on and so forth.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_cluster;
	 * </p>
	 *
	 * @param sizeDataset the sizeDataset
	 */
	public DataNode setSize(IDataset sizeDataset);

	/**
	 * Number of objects associated to each cluster. The labels are implicit,
	 * meaning the zeroth/first entry in the array belongs to the first cluster,
	 * the second entry to the second cluster and so on and so forth.
	 * The first cluster has the value of identifier_offset as its identifier.
	 * The second cluster has identifier_offset + 1, and so on and so forth.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_cluster;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getSizeScalar();

	/**
	 * Number of objects associated to each cluster. The labels are implicit,
	 * meaning the zeroth/first entry in the array belongs to the first cluster,
	 * the second entry to the second cluster and so on and so forth.
	 * The first cluster has the value of identifier_offset as its identifier.
	 * The second cluster has identifier_offset + 1, and so on and so forth.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_cluster;
	 * </p>
	 *
	 * @param size the size
	 */
	public DataNode setSizeScalar(Number sizeValue);

}

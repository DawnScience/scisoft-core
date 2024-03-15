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

package org.eclipse.dawnsci.nexus.impl;

import java.util.Set;
import java.util.EnumSet;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * Metadata to the results of a clustering analysis.
 * Clustering algorithms are routine tools to segment a set of objects/primitives
 * into groups, objects of different type. A plethora of algorithms have been
 * proposed for geometric primitives as objects, such as points, triangles,
 * or (abstract) objects.
 * This base class considers metadata and results of one clustering
 * applied to a set in which objects are either categorized as noise
 * or belonging to a cluster, specifically here only one cluster.

 */
public class NXclusteringImpl extends NXobjectImpl implements NXclustering {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXclusteringImpl() {
		super();
	}

	public NXclusteringImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXclustering.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CLUSTERING;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public IDataset getNumber_of_numeric_labels() {
		return getDataset(NX_NUMBER_OF_NUMERIC_LABELS);
	}

	@Override
	public Long getNumber_of_numeric_labelsScalar() {
		return getLong(NX_NUMBER_OF_NUMERIC_LABELS);
	}

	@Override
	public DataNode setNumber_of_numeric_labels(IDataset number_of_numeric_labelsDataset) {
		return setDataset(NX_NUMBER_OF_NUMERIC_LABELS, number_of_numeric_labelsDataset);
	}

	@Override
	public DataNode setNumber_of_numeric_labelsScalar(Long number_of_numeric_labelsValue) {
		return setField(NX_NUMBER_OF_NUMERIC_LABELS, number_of_numeric_labelsValue);
	}

	@Override
	public IDataset getNumber_of_categorical_labels() {
		return getDataset(NX_NUMBER_OF_CATEGORICAL_LABELS);
	}

	@Override
	public Long getNumber_of_categorical_labelsScalar() {
		return getLong(NX_NUMBER_OF_CATEGORICAL_LABELS);
	}

	@Override
	public DataNode setNumber_of_categorical_labels(IDataset number_of_categorical_labelsDataset) {
		return setDataset(NX_NUMBER_OF_CATEGORICAL_LABELS, number_of_categorical_labelsDataset);
	}

	@Override
	public DataNode setNumber_of_categorical_labelsScalar(Long number_of_categorical_labelsValue) {
		return setField(NX_NUMBER_OF_CATEGORICAL_LABELS, number_of_categorical_labelsValue);
	}

	@Override
	public IDataset getObjects() {
		return getDataset(NX_OBJECTS);
	}

	@Override
	public String getObjectsScalar() {
		return getString(NX_OBJECTS);
	}

	@Override
	public DataNode setObjects(IDataset objectsDataset) {
		return setDataset(NX_OBJECTS, objectsDataset);
	}

	@Override
	public DataNode setObjectsScalar(String objectsValue) {
		return setString(NX_OBJECTS, objectsValue);
	}

	@Override
	public IDataset getNumeric_label() {
		return getDataset(NX_NUMERIC_LABEL);
	}

	@Override
	public Number getNumeric_labelScalar() {
		return getNumber(NX_NUMERIC_LABEL);
	}

	@Override
	public DataNode setNumeric_label(IDataset numeric_labelDataset) {
		return setDataset(NX_NUMERIC_LABEL, numeric_labelDataset);
	}

	@Override
	public DataNode setNumeric_labelScalar(Number numeric_labelValue) {
		return setField(NX_NUMERIC_LABEL, numeric_labelValue);
	}

	@Override
	public IDataset getCategorical_label() {
		return getDataset(NX_CATEGORICAL_LABEL);
	}

	@Override
	public String getCategorical_labelScalar() {
		return getString(NX_CATEGORICAL_LABEL);
	}

	@Override
	public DataNode setCategorical_label(IDataset categorical_labelDataset) {
		return setDataset(NX_CATEGORICAL_LABEL, categorical_labelDataset);
	}

	@Override
	public DataNode setCategorical_labelScalar(String categorical_labelValue) {
		return setString(NX_CATEGORICAL_LABEL, categorical_labelValue);
	}

	@Override
	public IDataset getIdentifier_offset() {
		return getDataset(NX_IDENTIFIER_OFFSET);
	}

	@Override
	public Long getIdentifier_offsetScalar() {
		return getLong(NX_IDENTIFIER_OFFSET);
	}

	@Override
	public DataNode setIdentifier_offset(IDataset identifier_offsetDataset) {
		return setDataset(NX_IDENTIFIER_OFFSET, identifier_offsetDataset);
	}

	@Override
	public DataNode setIdentifier_offsetScalar(Long identifier_offsetValue) {
		return setField(NX_IDENTIFIER_OFFSET, identifier_offsetValue);
	}

	@Override
	public IDataset getUnassigned() {
		return getDataset(NX_UNASSIGNED);
	}

	@Override
	public Long getUnassignedScalar() {
		return getLong(NX_UNASSIGNED);
	}

	@Override
	public DataNode setUnassigned(IDataset unassignedDataset) {
		return setDataset(NX_UNASSIGNED, unassignedDataset);
	}

	@Override
	public DataNode setUnassignedScalar(Long unassignedValue) {
		return setField(NX_UNASSIGNED, unassignedValue);
	}

	@Override
	public IDataset getNoise() {
		return getDataset(NX_NOISE);
	}

	@Override
	public Long getNoiseScalar() {
		return getLong(NX_NOISE);
	}

	@Override
	public DataNode setNoise(IDataset noiseDataset) {
		return setDataset(NX_NOISE, noiseDataset);
	}

	@Override
	public DataNode setNoiseScalar(Long noiseValue) {
		return setField(NX_NOISE, noiseValue);
	}

	@Override
	public IDataset getNumber_of_cluster() {
		return getDataset(NX_NUMBER_OF_CLUSTER);
	}

	@Override
	public Long getNumber_of_clusterScalar() {
		return getLong(NX_NUMBER_OF_CLUSTER);
	}

	@Override
	public DataNode setNumber_of_cluster(IDataset number_of_clusterDataset) {
		return setDataset(NX_NUMBER_OF_CLUSTER, number_of_clusterDataset);
	}

	@Override
	public DataNode setNumber_of_clusterScalar(Long number_of_clusterValue) {
		return setField(NX_NUMBER_OF_CLUSTER, number_of_clusterValue);
	}

	@Override
	public IDataset getSize() {
		return getDataset(NX_SIZE);
	}

	@Override
	public Number getSizeScalar() {
		return getNumber(NX_SIZE);
	}

	@Override
	public DataNode setSize(IDataset sizeDataset) {
		return setDataset(NX_SIZE, sizeDataset);
	}

	@Override
	public DataNode setSizeScalar(Number sizeValue) {
		return setField(NX_SIZE, sizeValue);
	}

}

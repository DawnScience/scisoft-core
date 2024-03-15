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

 */
public class NXsimilarity_groupingImpl extends NXobjectImpl implements NXsimilarity_grouping {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_PROCESS);

	public NXsimilarity_groupingImpl() {
		super();
	}

	public NXsimilarity_groupingImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXsimilarity_grouping.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_SIMILARITY_GROUPING;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public IDataset getCardinality() {
		return getDataset(NX_CARDINALITY);
	}

	@Override
	public Long getCardinalityScalar() {
		return getLong(NX_CARDINALITY);
	}

	@Override
	public DataNode setCardinality(IDataset cardinalityDataset) {
		return setDataset(NX_CARDINALITY, cardinalityDataset);
	}

	@Override
	public DataNode setCardinalityScalar(Long cardinalityValue) {
		return setField(NX_CARDINALITY, cardinalityValue);
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
	public IDataset getNumerical_label() {
		return getDataset(NX_NUMERICAL_LABEL);
	}

	@Override
	public Long getNumerical_labelScalar() {
		return getLong(NX_NUMERICAL_LABEL);
	}

	@Override
	public DataNode setNumerical_label(IDataset numerical_labelDataset) {
		return setDataset(NX_NUMERICAL_LABEL, numerical_labelDataset);
	}

	@Override
	public DataNode setNumerical_labelScalar(Long numerical_labelValue) {
		return setField(NX_NUMERICAL_LABEL, numerical_labelValue);
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
	public NXprocess getStatistics() {
		// dataNodeName = NX_STATISTICS
		return getChild("statistics", NXprocess.class);
	}

	@Override
	public void setStatistics(NXprocess statisticsGroup) {
		putChild("statistics", statisticsGroup);
	}

}

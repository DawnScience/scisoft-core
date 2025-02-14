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
import org.eclipse.january.dataset.Dataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * A set of nodes/vertices in space representing members of a graph.

 */
public class NXgraph_node_setImpl extends NXobjectImpl implements NXgraph_node_set {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXgraph_node_setImpl() {
		super();
	}

	public NXgraph_node_setImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXgraph_node_set.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_GRAPH_NODE_SET;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getDimensionality() {
		return getDataset(NX_DIMENSIONALITY);
	}

	@Override
	public Long getDimensionalityScalar() {
		return getLong(NX_DIMENSIONALITY);
	}

	@Override
	public DataNode setDimensionality(IDataset dimensionalityDataset) {
		return setDataset(NX_DIMENSIONALITY, dimensionalityDataset);
	}

	@Override
	public DataNode setDimensionalityScalar(Long dimensionalityValue) {
		return setField(NX_DIMENSIONALITY, dimensionalityValue);
	}

	@Override
	public Dataset getCardinality() {
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
	public Dataset getIdentifier_offset() {
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
	public Dataset getIdentifier() {
		return getDataset(NX_IDENTIFIER);
	}

	@Override
	public Long getIdentifierScalar() {
		return getLong(NX_IDENTIFIER);
	}

	@Override
	public DataNode setIdentifier(IDataset identifierDataset) {
		return setDataset(NX_IDENTIFIER, identifierDataset);
	}

	@Override
	public DataNode setIdentifierScalar(Long identifierValue) {
		return setField(NX_IDENTIFIER, identifierValue);
	}

	@Override
	public Dataset getIs_a() {
		return getDataset(NX_IS_A);
	}

	@Override
	public String getIs_aScalar() {
		return getString(NX_IS_A);
	}

	@Override
	public DataNode setIs_a(IDataset is_aDataset) {
		return setDataset(NX_IS_A, is_aDataset);
	}

	@Override
	public DataNode setIs_aScalar(String is_aValue) {
		return setString(NX_IS_A, is_aValue);
	}

	@Override
	public Dataset getLabel() {
		return getDataset(NX_LABEL);
	}

	@Override
	public String getLabelScalar() {
		return getString(NX_LABEL);
	}

	@Override
	public DataNode setLabel(IDataset labelDataset) {
		return setDataset(NX_LABEL, labelDataset);
	}

	@Override
	public DataNode setLabelScalar(String labelValue) {
		return setString(NX_LABEL, labelValue);
	}

}

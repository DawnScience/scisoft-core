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
 * A set of (eventually directed) edges which connect nodes/vertices of a graph.

 */
public class NXgraph_edge_setImpl extends NXobjectImpl implements NXgraph_edge_set {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXgraph_edge_setImpl() {
		super();
	}

	public NXgraph_edge_setImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXgraph_edge_set.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_GRAPH_EDGE_SET;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public IDataset getNumber_of_edges() {
		return getDataset(NX_NUMBER_OF_EDGES);
	}

	@Override
	public Long getNumber_of_edgesScalar() {
		return getLong(NX_NUMBER_OF_EDGES);
	}

	@Override
	public DataNode setNumber_of_edges(IDataset number_of_edgesDataset) {
		return setDataset(NX_NUMBER_OF_EDGES, number_of_edgesDataset);
	}

	@Override
	public DataNode setNumber_of_edgesScalar(Long number_of_edgesValue) {
		return setField(NX_NUMBER_OF_EDGES, number_of_edgesValue);
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
	public IDataset getIdentifier() {
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
	public IDataset getDirectionality() {
		return getDataset(NX_DIRECTIONALITY);
	}

	@Override
	public Long getDirectionalityScalar() {
		return getLong(NX_DIRECTIONALITY);
	}

	@Override
	public DataNode setDirectionality(IDataset directionalityDataset) {
		return setDataset(NX_DIRECTIONALITY, directionalityDataset);
	}

	@Override
	public DataNode setDirectionalityScalar(Long directionalityValue) {
		return setField(NX_DIRECTIONALITY, directionalityValue);
	}

	@Override
	public IDataset getNode_pair() {
		return getDataset(NX_NODE_PAIR);
	}

	@Override
	public Long getNode_pairScalar() {
		return getLong(NX_NODE_PAIR);
	}

	@Override
	public DataNode setNode_pair(IDataset node_pairDataset) {
		return setDataset(NX_NODE_PAIR, node_pairDataset);
	}

	@Override
	public DataNode setNode_pairScalar(Long node_pairValue) {
		return setField(NX_NODE_PAIR, node_pairValue);
	}

	@Override
	public IDataset getIs_a() {
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
	public IDataset getLabel() {
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

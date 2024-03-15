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


import org.eclipse.dawnsci.nexus.*;

/**
 * An instance of a graph.

 */
public class NXgraph_rootImpl extends NXobjectImpl implements NXgraph_root {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_GRAPH_NODE_SET,
		NexusBaseClass.NX_GRAPH_EDGE_SET);

	public NXgraph_rootImpl() {
		super();
	}

	public NXgraph_rootImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXgraph_root.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_GRAPH_ROOT;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public NXgraph_node_set getNodes() {
		// dataNodeName = NX_NODES
		return getChild("nodes", NXgraph_node_set.class);
	}

	@Override
	public void setNodes(NXgraph_node_set nodesGroup) {
		putChild("nodes", nodesGroup);
	}

	@Override
	public NXgraph_edge_set getRelation() {
		// dataNodeName = NX_RELATION
		return getChild("relation", NXgraph_edge_set.class);
	}

	@Override
	public void setRelation(NXgraph_edge_set relationGroup) {
		putChild("relation", relationGroup);
	}

}

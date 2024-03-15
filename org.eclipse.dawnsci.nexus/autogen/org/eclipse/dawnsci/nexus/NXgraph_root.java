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


/**
 * An instance of a graph.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul></ul></p>
 *
 */
public interface NXgraph_root extends NXobject {

	/**
	 *
	 * @return  the value.
	 */
	public NXgraph_node_set getNodes();

	/**
	 *
	 * @param nodesGroup the nodesGroup
	 */
	public void setNodes(NXgraph_node_set nodesGroup);

	/**
	 *
	 * @return  the value.
	 */
	public NXgraph_edge_set getRelation();

	/**
	 *
	 * @param relationGroup the relationGroup
	 */
	public void setRelation(NXgraph_edge_set relationGroup);

}

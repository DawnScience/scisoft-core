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

import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Dataset;

/**
 * Base class for reporting the description of the I/O of a computer.
 *
 */
public interface NXcs_storage extends NXcomponent {

	/**
	 *
	 * @return  the value.
	 */
	public NXcircuit getCircuit();

	/**
	 *
	 * @param circuitGroup the circuitGroup
	 */
	public void setCircuit(NXcircuit circuitGroup);

	/**
	 * Get a NXcircuit node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXcircuit for that node.
	 */
	public NXcircuit getCircuit(String name);

	/**
	 * Set a NXcircuit node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param circuit the value to set
	 */
	public void setCircuit(String name, NXcircuit circuit);

	/**
	 * Get all NXcircuit nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXcircuit for that node.
	 */
	public Map<String, NXcircuit> getAllCircuit();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param circuit the child nodes to add
	 */

	public void setAllCircuit(Map<String, NXcircuit> circuit);


}

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
 * Computer science description of a central processing unit (CPU) of a computer.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul></ul></p>
 *
 */
public interface NXcs_cpu extends NXobject {

	public static final String NX_NAME = "name";
	/**
	 * Given name of the CPU. Users should be as specific as possible.
	 *
	 * @return  the value.
	 */
	public Dataset getName();

	/**
	 * Given name of the CPU. Users should be as specific as possible.
	 *
	 * @param nameDataset the nameDataset
	 */
	public DataNode setName(IDataset nameDataset);

	/**
	 * Given name of the CPU. Users should be as specific as possible.
	 *
	 * @return  the value.
	 */
	public String getNameScalar();

	/**
	 * Given name of the CPU. Users should be as specific as possible.
	 *
	 * @param name the name
	 */
	public DataNode setNameScalar(String nameValue);

	/**
	 *
	 * @return  the value.
	 */
	public NXfabrication getFabrication();

	/**
	 *
	 * @param fabricationGroup the fabricationGroup
	 */
	public void setFabrication(NXfabrication fabricationGroup);

	/**
	 * Get a NXfabrication node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXfabrication for that node.
	 */
	public NXfabrication getFabrication(String name);

	/**
	 * Set a NXfabrication node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param fabrication the value to set
	 */
	public void setFabrication(String name, NXfabrication fabrication);

	/**
	 * Get all NXfabrication nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXfabrication for that node.
	 */
	public Map<String, NXfabrication> getAllFabrication();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param fabrication the child nodes to add
	 */

	public void setAllFabrication(Map<String, NXfabrication> fabrication);


}

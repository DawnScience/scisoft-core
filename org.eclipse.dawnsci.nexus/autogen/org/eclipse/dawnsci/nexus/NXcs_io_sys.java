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


/**
 * Computer science description of system of a computer.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul></ul></p>
 *
 */
public interface NXcs_io_sys extends NXobject {

	/**
	 *
	 * @return  the value.
	 */
	public NXcs_io_obj getCs_io_obj();

	/**
	 *
	 * @param cs_io_objGroup the cs_io_objGroup
	 */
	public void setCs_io_obj(NXcs_io_obj cs_io_objGroup);

	/**
	 * Get a NXcs_io_obj node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXcs_io_obj for that node.
	 */
	public NXcs_io_obj getCs_io_obj(String name);

	/**
	 * Set a NXcs_io_obj node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param cs_io_obj the value to set
	 */
	public void setCs_io_obj(String name, NXcs_io_obj cs_io_obj);

	/**
	 * Get all NXcs_io_obj nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXcs_io_obj for that node.
	 */
	public Map<String, NXcs_io_obj> getAllCs_io_obj();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param cs_io_obj the child nodes to add
	 */

	public void setAllCs_io_obj(Map<String, NXcs_io_obj> cs_io_obj);


}

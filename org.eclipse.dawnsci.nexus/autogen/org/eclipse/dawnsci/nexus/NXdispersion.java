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
 * A dispersion denoting a sum of different dispersions.
 * All NXdispersion_table and NXdispersion_function groups will be added together
 * to form a single dispersion.
 *
 */
public interface NXdispersion extends NXobject {

	public static final String NX_MODEL_NAME = "model_name";
	/**
	 * The name of the composite model.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getModel_name();

	/**
	 * The name of the composite model.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param model_nameDataset the model_nameDataset
	 */
	public DataNode setModel_name(IDataset model_nameDataset);

	/**
	 * The name of the composite model.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getModel_nameScalar();

	/**
	 * The name of the composite model.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param model_name the model_name
	 */
	public DataNode setModel_nameScalar(String model_nameValue);

	/**
	 *
	 * @return  the value.
	 */
	public NXdispersion_table getDispersion_table();

	/**
	 *
	 * @param dispersion_tableGroup the dispersion_tableGroup
	 */
	public void setDispersion_table(NXdispersion_table dispersion_tableGroup);

	/**
	 * Get a NXdispersion_table node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXdispersion_table for that node.
	 */
	public NXdispersion_table getDispersion_table(String name);

	/**
	 * Set a NXdispersion_table node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param dispersion_table the value to set
	 */
	public void setDispersion_table(String name, NXdispersion_table dispersion_table);

	/**
	 * Get all NXdispersion_table nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXdispersion_table for that node.
	 */
	public Map<String, NXdispersion_table> getAllDispersion_table();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param dispersion_table the child nodes to add
	 */

	public void setAllDispersion_table(Map<String, NXdispersion_table> dispersion_table);


	/**
	 *
	 * @return  the value.
	 */
	public NXdispersion_function getDispersion_function();

	/**
	 *
	 * @param dispersion_functionGroup the dispersion_functionGroup
	 */
	public void setDispersion_function(NXdispersion_function dispersion_functionGroup);

	/**
	 * Get a NXdispersion_function node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXdispersion_function for that node.
	 */
	public NXdispersion_function getDispersion_function(String name);

	/**
	 * Set a NXdispersion_function node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param dispersion_function the value to set
	 */
	public void setDispersion_function(String name, NXdispersion_function dispersion_function);

	/**
	 * Get all NXdispersion_function nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXdispersion_function for that node.
	 */
	public Map<String, NXdispersion_function> getAllDispersion_function();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param dispersion_function the child nodes to add
	 */

	public void setAllDispersion_function(Map<String, NXdispersion_function> dispersion_function);


}

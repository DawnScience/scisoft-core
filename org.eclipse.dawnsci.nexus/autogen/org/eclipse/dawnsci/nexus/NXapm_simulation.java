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
 * Base class for simulation of ion extraction from matter via laser and/or voltage
 * pulsing.
 *
 */
public interface NXapm_simulation extends NXobject {

	/**
	 *
	 * @return  the value.
	 */
	public NXprogram getNXProgram();

	/**
	 *
	 * @param programGroup the programGroup
	 */
	public void setNXProgram(NXprogram programGroup);

	/**
	 * Get a NXprogram node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXprogram for that node.
	 */
	public NXprogram getNXProgram(String name);

	/**
	 * Set a NXprogram node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param program the value to set
	 */
	public void setNXProgram(String name, NXprogram program);

	/**
	 * Get all NXprogram nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXprogram for that node.
	 */
	public Map<String, NXprogram> getAllNXProgram();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param program the child nodes to add
	 */

	public void setAllNXProgram(Map<String, NXprogram> program);


	/**
	 *
	 * @return  the value.
	 */
	public NXparameters getParameters();

	/**
	 *
	 * @param parametersGroup the parametersGroup
	 */
	public void setParameters(NXparameters parametersGroup);

	/**
	 * Get a NXparameters node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXparameters for that node.
	 */
	public NXparameters getParameters(String name);

	/**
	 * Set a NXparameters node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param parameters the value to set
	 */
	public void setParameters(String name, NXparameters parameters);

	/**
	 * Get all NXparameters nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXparameters for that node.
	 */
	public Map<String, NXparameters> getAllParameters();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param parameters the child nodes to add
	 */

	public void setAllParameters(Map<String, NXparameters> parameters);


	/**
	 *
	 * @return  the value.
	 */
	public NXprocess getProcess();

	/**
	 *
	 * @param processGroup the processGroup
	 */
	public void setProcess(NXprocess processGroup);

	/**
	 * Get a NXprocess node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXprocess for that node.
	 */
	public NXprocess getProcess(String name);

	/**
	 * Set a NXprocess node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param process the value to set
	 */
	public void setProcess(String name, NXprocess process);

	/**
	 * Get all NXprocess nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXprocess for that node.
	 */
	public Map<String, NXprocess> getAllProcess();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param process the child nodes to add
	 */

	public void setAllProcess(Map<String, NXprocess> process);


	/**
	 *
	 * @return  the value.
	 */
	public NXdata getData();

	/**
	 *
	 * @param dataGroup the dataGroup
	 */
	public void setData(NXdata dataGroup);

	/**
	 * Get a NXdata node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXdata for that node.
	 */
	public NXdata getData(String name);

	/**
	 * Set a NXdata node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param data the value to set
	 */
	public void setData(String name, NXdata data);

	/**
	 * Get all NXdata nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXdata for that node.
	 */
	public Map<String, NXdata> getAllData();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param data the child nodes to add
	 */

	public void setAllData(Map<String, NXdata> data);


}

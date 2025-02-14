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
 * Device to reduce an atmosphere to a controlled remaining pressure level.
 *
 */
public interface NXpump extends NXobject {

	public static final String NX_DESIGN = "design";
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


	/**
	 * Principle type of the pump.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>membrane</b> </li>
	 * <li><b>rotary_vane</b> </li>
	 * <li><b>roots</b> </li>
	 * <li><b>turbo_molecular</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDesign();

	/**
	 * Principle type of the pump.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>membrane</b> </li>
	 * <li><b>rotary_vane</b> </li>
	 * <li><b>roots</b> </li>
	 * <li><b>turbo_molecular</b> </li></ul></p>
	 * </p>
	 *
	 * @param designDataset the designDataset
	 */
	public DataNode setDesign(IDataset designDataset);

	/**
	 * Principle type of the pump.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>membrane</b> </li>
	 * <li><b>rotary_vane</b> </li>
	 * <li><b>roots</b> </li>
	 * <li><b>turbo_molecular</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getDesignScalar();

	/**
	 * Principle type of the pump.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>membrane</b> </li>
	 * <li><b>rotary_vane</b> </li>
	 * <li><b>roots</b> </li>
	 * <li><b>turbo_molecular</b> </li></ul></p>
	 * </p>
	 *
	 * @param design the design
	 */
	public DataNode setDesignScalar(String designValue);

}

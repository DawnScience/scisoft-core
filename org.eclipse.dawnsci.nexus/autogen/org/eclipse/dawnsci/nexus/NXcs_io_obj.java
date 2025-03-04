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
 * Computer science description of a storage object in an input/output system.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul></ul></p>
 *
 */
public interface NXcs_io_obj extends NXobject {

	public static final String NX_TECHNOLOGY = "technology";
	public static final String NX_MAX_PHYSICAL_CAPACITY = "max_physical_capacity";
	public static final String NX_NAME = "name";
	/**
	 * Qualifier for the type of storage medium used.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>solid_state_disk</b> </li>
	 * <li><b>hard_disk</b> </li>
	 * <li><b>tape</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getTechnology();

	/**
	 * Qualifier for the type of storage medium used.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>solid_state_disk</b> </li>
	 * <li><b>hard_disk</b> </li>
	 * <li><b>tape</b> </li></ul></p>
	 * </p>
	 *
	 * @param technologyDataset the technologyDataset
	 */
	public DataNode setTechnology(IDataset technologyDataset);

	/**
	 * Qualifier for the type of storage medium used.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>solid_state_disk</b> </li>
	 * <li><b>hard_disk</b> </li>
	 * <li><b>tape</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getTechnologyScalar();

	/**
	 * Qualifier for the type of storage medium used.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>solid_state_disk</b> </li>
	 * <li><b>hard_disk</b> </li>
	 * <li><b>tape</b> </li></ul></p>
	 * </p>
	 *
	 * @param technology the technology
	 */
	public DataNode setTechnologyScalar(String technologyValue);

	/**
	 * Total amount of data which the medium can hold.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getMax_physical_capacity();

	/**
	 * Total amount of data which the medium can hold.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @param max_physical_capacityDataset the max_physical_capacityDataset
	 */
	public DataNode setMax_physical_capacity(IDataset max_physical_capacityDataset);

	/**
	 * Total amount of data which the medium can hold.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getMax_physical_capacityScalar();

	/**
	 * Total amount of data which the medium can hold.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @param max_physical_capacity the max_physical_capacity
	 */
	public DataNode setMax_physical_capacityScalar(Number max_physical_capacityValue);

	/**
	 * Given name to the I/O unit.
	 *
	 * @return  the value.
	 */
	public Dataset getName();

	/**
	 * Given name to the I/O unit.
	 *
	 * @param nameDataset the nameDataset
	 */
	public DataNode setName(IDataset nameDataset);

	/**
	 * Given name to the I/O unit.
	 *
	 * @return  the value.
	 */
	public String getNameScalar();

	/**
	 * Given name to the I/O unit.
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

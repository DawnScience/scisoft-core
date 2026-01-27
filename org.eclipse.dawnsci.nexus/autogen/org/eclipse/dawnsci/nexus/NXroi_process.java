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
 * Base class to report on the characterization of an area or volume of material.
 * This area or volume of material is considered a region-of-interest (ROI).
 * This base class should be used when the characterization was achieved by
 * processing data from experiment or computer simulations into models of
 * the microstructure of the material and the properties of the material or its
 * crystal defects within this ROI. Microstructural features is a narrow synonym
 * for these crystal defects.
 * This base class can also be used to store data and metadata of the
 * representation of the ROI, i.e. its discretization and shape.
 * Methods from computational geometry are typically used for
 * defining a discretization of the area and volume.
 * Do not confuse this base class with :ref:`NXregion`. The purpose
 * of the :ref:`NXregion` base class is to document data access i.e.
 * I/O pattern on arrays. Therefore, concepts from :ref:`NXregion` operate
 * in data space rather than in real or simulated real space.
 *
 */
public interface NXroi_process extends NXprocess {

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


}

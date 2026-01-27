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
 * Base class for method-specific generic imaging with electron microscopes.
 * In the majority of cases simple d-dimensional regular scan patterns are used
 * to probe regions-of-interest (ROIs). Examples can be single point aka spot
 * measurements, line profiles, or (rectangular) surface mappings.
 * The latter pattern is the most frequently used.
 * For now the base class provides for scans for which the settings,
 * binning, and energy resolution is the same for each scan point.
 *
 */
public interface NXem_img extends NXprocess {

	/**
	 *
	 * @return  the value.
	 */
	public NXimage getImage();

	/**
	 *
	 * @param imageGroup the imageGroup
	 */
	public void setImage(NXimage imageGroup);

	/**
	 * Get a NXimage node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXimage for that node.
	 */
	public NXimage getImage(String name);

	/**
	 * Set a NXimage node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param image the value to set
	 */
	public void setImage(String name, NXimage image);

	/**
	 * Get all NXimage nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXimage for that node.
	 */
	public Map<String, NXimage> getAllImage();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param image the child nodes to add
	 */

	public void setAllImage(Map<String, NXimage> image);


}

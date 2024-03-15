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

/**
 * Container for reporting a set of annular dark field images.
 * Virtually the most important case is that spectra are collected in
 * a scanning microscope (SEM or STEM) for a collection of points.
 * The majority of cases use simple d-dimensional regular scan pattern,
 * such as single point, line profiles, or (rectangular) surface mappings.
 * The latter pattern is the most frequently used.
 * For now the base class provides for scans for which the settings,
 * binning, and energy resolution is the same for each scan point.
 * <p><b>Symbols:</b><ul>
 * <li><b>n_images</b>
 * Number of images in the stack.</li>
 * <li><b>n_y</b>
 * Number of pixel per image in the slow direction.</li>
 * <li><b>n_x</b>
 * Number of pixel per image in the fast direction.</li></ul></p>
 *
 */
public interface NXimage_set_em_adf extends NXobject {

	/**
	 * Details how (HA)ADF images were processed from the detector readings.
	 *
	 * @return  the value.
	 */
	public NXprocess getProcess();

	/**
	 * Details how (HA)ADF images were processed from the detector readings.
	 *
	 * @param processGroup the processGroup
	 */
	public void setProcess(NXprocess processGroup);

	/**
	 * Get a NXprocess node by name:
	 * <ul>
	 * <li>
	 * Details how (HA)ADF images were processed from the detector readings.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXprocess for that node.
	 */
	public NXprocess getProcess(String name);

	/**
	 * Set a NXprocess node by name:
	 * <ul>
	 * <li>
	 * Details how (HA)ADF images were processed from the detector readings.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param process the value to set
	 */
	public void setProcess(String name, NXprocess process);

	/**
	 * Get all NXprocess nodes:
	 * <ul>
	 * <li>
	 * Details how (HA)ADF images were processed from the detector readings.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXprocess for that node.
	 */
	public Map<String, NXprocess> getAllProcess();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Details how (HA)ADF images were processed from the detector readings.</li>
	 * </ul>
	 *
	 * @param process the child nodes to add
	 */

	public void setAllProcess(Map<String, NXprocess> process);


	/**
	 * Annular dark field image stack.
	 *
	 * @return  the value.
	 */
	public NXdata getStack();

	/**
	 * Annular dark field image stack.
	 *
	 * @param stackGroup the stackGroup
	 */
	public void setStack(NXdata stackGroup);

}

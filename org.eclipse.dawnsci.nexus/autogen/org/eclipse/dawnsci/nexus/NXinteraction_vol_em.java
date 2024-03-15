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
 * Base class for storing details about a modelled shape of interaction volume.
 * The interaction volume is mainly relevant in scanning electron microscopy
 * when the sample is thick enough so that the beam is unable to illuminate
 * through the specimen.
 * Computer models like Monte Carlo or molecular dynamics / electron beam
 * interaction simulations can be used to qualify and/or quantify the shape of
 * the interaction volume.
 * Explicit or implicit descriptions are possible.
 * * An implicit description is via a set of electron/specimen interactions
 * represented ideally as trajectory data from the computer simulation.
 * * An explicit description is via an iso-contour surface using either
 * a simulation grid or a triangulated surface mesh of the approximated
 * iso-contour surface evaluated at specific threshold values.
 * Iso-contours could be computed from electron or particle fluxes through
 * an imaginary control surface (the iso-surface).
 * Threshold values can be defined by particles passing through a unit control
 * volume (electrons) or energy-levels (e.g. the case of X-rays).
 * Details depend on the model.
 * * Another explicit description is via theoretical models which may
 * be relevant e.g. for X-ray spectroscopy
 * Further details on how the interaction volume can be quantified
 * is available in the literature for example:
 * * `S. Richter et al. <https://doi.org/10.1088/1757-899X/109/1/012014>`_
 * * `J. Bünger et al. <https://doi.org/10.1017/S1431927622000083>`_
 *
 */
public interface NXinteraction_vol_em extends NXobject {

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

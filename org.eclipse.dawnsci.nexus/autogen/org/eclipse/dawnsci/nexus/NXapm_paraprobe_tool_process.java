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

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Dataset;

/**
 * Base class documenting a processing step within a tool of the paraprobe-toolbox.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>n_mask</b>
 * The number of entries in the mask.</li></ul></p>
 *
 */
public interface NXapm_paraprobe_tool_process extends NXprocess {

	public static final String NX_DESCRIPTION = "description";
	/**
	 * Possibility for leaving a free-text description about this analysis.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDescription();

	/**
	 * Possibility for leaving a free-text description about this analysis.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param descriptionDataset the descriptionDataset
	 */
	public DataNode setDescription(IDataset descriptionDataset);

	/**
	 * Possibility for leaving a free-text description about this analysis.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getDescriptionScalar();

	/**
	 * Possibility for leaving a free-text description about this analysis.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param description the description
	 */
	public DataNode setDescriptionScalar(String descriptionValue);

	/**
	 * A bitmask which identifies all ions considered in the analysis.
	 *
	 * @return  the value.
	 */
	public NXcs_filter_boolean_mask getWindow();

	/**
	 * A bitmask which identifies all ions considered in the analysis.
	 *
	 * @param windowGroup the windowGroup
	 */
	public void setWindow(NXcs_filter_boolean_mask windowGroup);

}

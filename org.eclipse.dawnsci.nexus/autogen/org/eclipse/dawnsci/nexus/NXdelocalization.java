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
 * Base class of the configuration and results of a delocalization algorithm.
 * Delocalization is used to distribute point-like objects on a grid to obtain
 * e.g. smoother count, composition, or concentration values of scalar fields
 * and compute gradients of these fields.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>n_p</b>
 * Number of points/objects.</li>
 * <li><b>n_m</b>
 * Number of mark data per point/object.</li>
 * <li><b>n_atoms</b>
 * Number of atoms in the whitelist.</li>
 * <li><b>n_nuclides</b>
 * Number of isotopes in the whitelist.</li></ul></p>
 *
 */
public interface NXdelocalization extends NXobject {

	/**
	 * Details about the grid on which the delocalization is applied.
	 *
	 * @return  the value.
	 */
	public NXcg_grid getGrid();

	/**
	 * Details about the grid on which the delocalization is applied.
	 *
	 * @param gridGroup the gridGroup
	 */
	public void setGrid(NXcg_grid gridGroup);

	/**
	 * The weighting model specifies how mark data are mapped to a weight per
	 * point/object.
	 *
	 * @return  the value.
	 */
	public NXmatch_filter getWeighting_model();

	/**
	 * The weighting model specifies how mark data are mapped to a weight per
	 * point/object.
	 *
	 * @param weighting_modelGroup the weighting_modelGroup
	 */
	public void setWeighting_model(NXmatch_filter weighting_modelGroup);

}

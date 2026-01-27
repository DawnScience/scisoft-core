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
 * Base class to store the configuration when using the MTex/Matlab software.
 * MTex is a Matlab package for texture analysis used in the Materials and Earth Sciences.
 * See `R. Hielscher et al. <https://mtex-toolbox.github.io/publications>`_ and
 * the `MTex source code <https://github.com/mtex-toolbox>`_ for details.
 * <p><b>Symbols:</b><ul>
 * <li><b>n_def_color_map</b>
 * Number of entries in the default color map</li>
 * <li><b>n_color_map</b>
 * Number of entries in color map</li></ul></p>
 *
 */
public interface NXmicrostructure_mtex_config extends NXparameters {

	/**
	 * MTex reference frame and orientation conventions.
	 * Consult the `MTex docs <https://mtex-toolbox.github.io/EBSDReferenceFrame.html>`_ for details.
	 *
	 * @return  the value.
	 */
	public NXcollection getConventions();

	/**
	 * MTex reference frame and orientation conventions.
	 * Consult the `MTex docs <https://mtex-toolbox.github.io/EBSDReferenceFrame.html>`_ for details.
	 *
	 * @param conventionsGroup the conventionsGroup
	 */
	public void setConventions(NXcollection conventionsGroup);

	/**
	 * Settings relevant for generating plots.
	 *
	 * @return  the value.
	 */
	public NXcollection getPlotting();

	/**
	 * Settings relevant for generating plots.
	 *
	 * @param plottingGroup the plottingGroup
	 */
	public void setPlotting(NXcollection plottingGroup);

	/**
	 * Miscellaneous other settings of MTex.
	 *
	 * @return  the value.
	 */
	public NXcollection getMiscellaneous();

	/**
	 * Miscellaneous other settings of MTex.
	 *
	 * @param miscellaneousGroup the miscellaneousGroup
	 */
	public void setMiscellaneous(NXcollection miscellaneousGroup);

	/**
	 * Miscellaneous settings relevant for numerics.
	 *
	 * @return  the value.
	 */
	public NXcollection getNumerics();

	/**
	 * Miscellaneous settings relevant for numerics.
	 *
	 * @param numericsGroup the numericsGroup
	 */
	public void setNumerics(NXcollection numericsGroup);

	/**
	 * Miscellaneous settings relevant of the system where MTex runs.
	 *
	 * @return  the value.
	 */
	public NXcollection getSystem();

	/**
	 * Miscellaneous settings relevant of the system where MTex runs.
	 *
	 * @param systemGroup the systemGroup
	 */
	public void setSystem(NXcollection systemGroup);

	/**
	 * Collection of paths from where MTex reads information and code.
	 *
	 * @return  the value.
	 */
	public NXcollection getPath();

	/**
	 * Collection of paths from where MTex reads information and code.
	 *
	 * @param pathGroup the pathGroup
	 */
	public void setPath(NXcollection pathGroup);

}

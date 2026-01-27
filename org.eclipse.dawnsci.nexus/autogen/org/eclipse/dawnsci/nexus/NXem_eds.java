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
 * Base class method-specific for energy-dispersive X-ray spectroscopy (EDS/EDXS).
 * `IUPAC instead of Siegbahn notation <https://doi.org/10.1002/xrs.1300200308>`_ should be used.
 * X-ray spectroscopy is a surface-sensitive technique. Therefore, three-dimensional elemental
 * characterization requires typically a sequence of characterization and preparation of the
 * surface to expose new surface layer that can be characterized in the next acquisition.
 * In effect, the resulting three-dimensional elemental information mappings are truly the
 * result of a correlation and post-processing of several measurements which is the field
 * of correlative tomographic usage of electron microscopy.
 * <p><b>Symbols:</b><ul>
 * <li><b>n_photon_energy</b>
 * Number of X-ray photon energy (bins)</li>
 * <li><b>n_elements</b>
 * Number of identified elements</li>
 * <li><b>n_peaks</b>
 * Number of peaks detected</li>
 * <li><b>n_iupac_line_names</b>
 * Number of IUPAC line names</li></ul></p>
 *
 */
public interface NXem_eds extends NXprocess {

	/**
	 * Details about computational steps how peaks were indexed as elements.
	 *
	 * @return  the value.
	 */
	public NXprocess getIndexing();

	/**
	 * Details about computational steps how peaks were indexed as elements.
	 *
	 * @param indexingGroup the indexingGroup
	 */
	public void setIndexing(NXprocess indexingGroup);
	// Unprocessed group:
	// Unprocessed group:summary
	// Unprocessed group:
	// Unprocessed group:ELEMENT_SPECIFIC_MAP

}

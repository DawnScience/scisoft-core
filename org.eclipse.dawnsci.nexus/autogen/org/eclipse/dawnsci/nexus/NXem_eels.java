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
 * Base class method-specific for Electron Energy Loss Spectroscopy (EELS).
 *
 */
public interface NXem_eels extends NXprocess {

	/**
	 * Details about computational steps how the zero-loss peak was threaded.
	 *
	 * @return  the value.
	 */
	public NXprocess getZlp_correction();

	/**
	 * Details about computational steps how the zero-loss peak was threaded.
	 *
	 * @param zlp_correctionGroup the zlp_correctionGroup
	 */
	public void setZlp_correction(NXprocess zlp_correctionGroup);
	// Unprocessed group:

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
	// Unprocessed group:
	// Unprocessed group:

}

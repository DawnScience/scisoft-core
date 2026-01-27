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
 * Base class to store a pole figure (PF) computation.
 * A pole figure is the X-ray diffraction intensity for specific integrated
 * peaks for a hemispherical illumination of a real or virtual specimen.
 * <p><b>Symbols:</b><ul>
 * <li><b>n_y</b>
 * Number of pixel per pole figure in the slow direction.</li>
 * <li><b>n_x</b>
 * Number of pixel per pole figure in the fast direction.</li></ul></p>
 *
 */
public interface NXmicrostructure_pf extends NXprocess {

	/**
	 * Details about the algorithm that was used to compute the pole figure.
	 *
	 * @return  the value.
	 */
	public NXparameters getConfiguration();

	/**
	 * Details about the algorithm that was used to compute the pole figure.
	 *
	 * @param configurationGroup the configurationGroup
	 */
	public void setConfiguration(NXparameters configurationGroup);

	/**
	 * Pole figure.
	 *
	 * @return  the value.
	 */
	public NXdata getPf();

	/**
	 * Pole figure.
	 *
	 * @param pfGroup the pfGroup
	 */
	public void setPf(NXdata pfGroup);

}

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
 * Base class to store an orientation distribution function (ODF).
 * An orientation distribution function is a probability distribution that details how
 * much volume of material has a specific orientation. An ODF is computed from
 * pole figure data in a computational process called `pole figure inversion <https://doi.org/10.1107/S0021889808030112>`_.
 * <p><b>Symbols:</b><ul>
 * <li><b>n_varphi_two</b>
 * Number of pixel per varphi section plot along the :math:`\varphi_2` slow
 * direction.</li>
 * <li><b>n_capital_phi</b>
 * Number of pixel per varphi section plot along the :math:`\Phi` fast direction.</li>
 * <li><b>n_varphi_one</b>
 * Number of pixel per varphi section plot along the :math:`\varphi_1` fastest
 * direction.</li>
 * <li><b>k</b>
 * Number of local maxima evaluated in the component analysis.</li>
 * <li><b>n_pos</b>
 * Number of sampled positions in orientation space.</li></ul></p>
 *
 */
public interface NXmicrostructure_odf extends NXprocess {

	/**
	 * Details about the algorithm used for computing the ODF.
	 *
	 * @return  the value.
	 */
	public NXparameters getConfiguration();

	/**
	 * Details about the algorithm used for computing the ODF.
	 *
	 * @param configurationGroup the configurationGroup
	 */
	public void setConfiguration(NXparameters configurationGroup);

	/**
	 * Group to store descriptors for a rough classification of an ODF.
	 *
	 * @return  the value.
	 */
	public NXprocess getCharacteristics();

	/**
	 * Group to store descriptors for a rough classification of an ODF.
	 *
	 * @param characteristicsGroup the characteristicsGroup
	 */
	public void setCharacteristics(NXprocess characteristicsGroup);

	/**
	 * Group to store descriptors and summary statistics for extrema of the ODF.
	 *
	 * @return  the value.
	 */
	public NXprocess getKth_extrema();

	/**
	 * Group to store descriptors and summary statistics for extrema of the ODF.
	 *
	 * @param kth_extremaGroup the kth_extremaGroup
	 */
	public void setKth_extrema(NXprocess kth_extremaGroup);

	/**
	 * The ODF intensity values (weights) as sampled with a software.
	 *
	 * @return  the value.
	 */
	public NXprocess getSampling();

	/**
	 * The ODF intensity values (weights) as sampled with a software.
	 *
	 * @param samplingGroup the samplingGroup
	 */
	public void setSampling(NXprocess samplingGroup);

	/**
	 * Visualization of the ODF intensity as discretized orthogonal sections through
	 * orientation space parameterized using Bunge-Euler angles.
	 * This is one example of typical default plots used in the texture community in materials engineering.
	 * Mind that the orientation space is a distorted space when it using an Euler angle parameterization.
	 * Therefore, equivalent orientations show intensity contributions in eventually multiple locations.
	 *
	 * @return  the value.
	 */
	public NXdata getPhi_two_plot();

	/**
	 * Visualization of the ODF intensity as discretized orthogonal sections through
	 * orientation space parameterized using Bunge-Euler angles.
	 * This is one example of typical default plots used in the texture community in materials engineering.
	 * Mind that the orientation space is a distorted space when it using an Euler angle parameterization.
	 * Therefore, equivalent orientations show intensity contributions in eventually multiple locations.
	 *
	 * @param phi_two_plotGroup the phi_two_plotGroup
	 */
	public void setPhi_two_plot(NXdata phi_two_plotGroup);

}

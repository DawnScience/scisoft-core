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
 * Base class for the configuration and results of ranging definitions.
 * Ranging is a data post-processing step used in the research field of
 * atom probe during which elemental, isotopic, and/or molecular identities
 * are assigned to mass-to-charge-state ratios within certain intervals.
 * The documentation of these steps is based on ideas that
 * have been described in the literature:
 * * `M. K. Miller <https://doi.org/10.1002/sia.1719>`_
 * * `D. Haley et al. <https://doi.org/10.1017/S1431927620024290>`_
 * * `M. Kühbach et al. <https://doi.org/10.1017/S1431927621012241>`_
 *
 */
public interface NXapm_ranging extends NXprocess {

	/**
	 *
	 * @return  the value.
	 */
	public NXprogram getNXProgram();

	/**
	 *
	 * @param programGroup the programGroup
	 */
	public void setNXProgram(NXprogram programGroup);

	/**
	 * Get a NXprogram node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXprogram for that node.
	 */
	public NXprogram getNXProgram(String name);

	/**
	 * Set a NXprogram node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param program the value to set
	 */
	public void setNXProgram(String name, NXprogram program);

	/**
	 * Get all NXprogram nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXprogram for that node.
	 */
	public Map<String, NXprogram> getAllNXProgram();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param program the child nodes to add
	 */

	public void setAllNXProgram(Map<String, NXprogram> program);


	/**
	 *
	 * @return  the value.
	 */
	public NXnote getNote();

	/**
	 *
	 * @param noteGroup the noteGroup
	 */
	public void setNote(NXnote noteGroup);

	/**
	 * Get a NXnote node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXnote for that node.
	 */
	public NXnote getNote(String name);

	/**
	 * Set a NXnote node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param note the value to set
	 */
	public void setNote(String name, NXnote note);

	/**
	 * Get all NXnote nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXnote for that node.
	 */
	public Map<String, NXnote> getAllNote();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param note the child nodes to add
	 */

	public void setAllNote(Map<String, NXnote> note);


	/**
	 * Specifies the mass-to-charge-state ratio histogram.
	 *
	 * @return  the value.
	 */
	public NXprocess getMass_to_charge_distribution();

	/**
	 * Specifies the mass-to-charge-state ratio histogram.
	 *
	 * @param mass_to_charge_distributionGroup the mass_to_charge_distributionGroup
	 */
	public void setMass_to_charge_distribution(NXprocess mass_to_charge_distributionGroup);
	// Unprocessed group:
	// Unprocessed group:mass_spectrum

	/**
	 * Details of the background model that was used to
	 * correct the total counts per bin into counts.
	 *
	 * @return  the value.
	 */
	public NXprocess getBackground_quantification();

	/**
	 * Details of the background model that was used to
	 * correct the total counts per bin into counts.
	 *
	 * @param background_quantificationGroup the background_quantificationGroup
	 */
	public void setBackground_quantification(NXprocess background_quantificationGroup);
	// Unprocessed group:

	/**
	 * How were peaks in the mass-to-charge-state ratio histogram identified.
	 *
	 * @return  the value.
	 */
	public NXprocess getPeak_search_and_deconvolution();

	/**
	 * How were peaks in the mass-to-charge-state ratio histogram identified.
	 *
	 * @param peak_search_and_deconvolutionGroup the peak_search_and_deconvolutionGroup
	 */
	public void setPeak_search_and_deconvolution(NXprocess peak_search_and_deconvolutionGroup);
	// Unprocessed group:
	// Unprocessed group:

	/**
	 * Details about how peaks, with taking into account
	 * error models, were interpreted as ion types or not.
	 *
	 * @return  the value.
	 */
	public NXprocess getPeak_identification();

	/**
	 * Details about how peaks, with taking into account
	 * error models, were interpreted as ion types or not.
	 *
	 * @param peak_identificationGroup the peak_identificationGroup
	 */
	public void setPeak_identification(NXprocess peak_identificationGroup);
	// Unprocessed group:
	// Unprocessed group:

}

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
 * Base class to document the parameters, configuration, and results of a processing for recovering
 * the charge state and nuclide composition of an ion from ranging definitions as used in the research
 * field of atom probe microscopy.
 * A ranging definition classically reports only the mass-to-charge-state-ratio interval plus the
 * elemental composition, but not necessarily the nuclide that compose the ion.
 * As the mass-resolving-power in an atom probe instrument is finite and typically lower
 * than for cutting edge tandem mass spectrometry it is possible that different combinations of nuclides
 * are indistinguishable and thus multiple ions in eventually even different charge states can be valid
 * labels for a given mass-to-charge-state-ratio peak. Enumerating the possible combinations
 * is a programmatic approach that can help with peak identification.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>n_cand</b>
 * The number of ion candidates.</li>
 * <li><b>n_ivec_max</b>
 * Maximum number of allowed atoms per ion.</li>
 * <li><b>n_variable</b>
 * Number of entries</li></ul></p>
 *
 */
public interface NXapm_charge_state_analysis extends NXprocess {

	public static final String NX_CHARGE_STATE = "charge_state";
	public static final String NX_NUCLIDE_HASH = "nuclide_hash";
	public static final String NX_MASS = "mass";
	public static final String NX_NATURAL_ABUNDANCE_PRODUCT = "natural_abundance_product";
	public static final String NX_SHORTEST_HALF_LIFE = "shortest_half_life";
	/**
	 * Parameters for the algorithm used to recover which combinations of nuclides
	 * have a mass and charge that matches a set of constraints.
	 * Each parameter in this group is defines one constraint.
	 *
	 * @return  the value.
	 */
	public NXparameters getConfig();

	/**
	 * Parameters for the algorithm used to recover which combinations of nuclides
	 * have a mass and charge that matches a set of constraints.
	 * Each parameter in this group is defines one constraint.
	 *
	 * @param configGroup the configGroup
	 */
	public void setConfig(NXparameters configGroup);

	/**
	 * Signed charge, i.e. integer multiple of the elementary
	 * charge of each candidate.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_cand;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getCharge_state();

	/**
	 * Signed charge, i.e. integer multiple of the elementary
	 * charge of each candidate.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_cand;
	 * </p>
	 *
	 * @param charge_stateDataset the charge_stateDataset
	 */
	public DataNode setCharge_state(IDataset charge_stateDataset);

	/**
	 * Signed charge, i.e. integer multiple of the elementary
	 * charge of each candidate.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_cand;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getCharge_stateScalar();

	/**
	 * Signed charge, i.e. integer multiple of the elementary
	 * charge of each candidate.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_cand;
	 * </p>
	 *
	 * @param charge_state the charge_state
	 */
	public DataNode setCharge_stateScalar(Long charge_stateValue);

	/**
	 * Table of nuclide instances of which each candidate is composed.
	 * Each row vector is sorted in descending order.
	 * Unused entries in the matrix should be set to 0.
	 * Use the hashing rule that is defined in nuclide_hash of :ref:`NXatom`.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_cand; 2: n_ivec_max;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getNuclide_hash();

	/**
	 * Table of nuclide instances of which each candidate is composed.
	 * Each row vector is sorted in descending order.
	 * Unused entries in the matrix should be set to 0.
	 * Use the hashing rule that is defined in nuclide_hash of :ref:`NXatom`.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_cand; 2: n_ivec_max;
	 * </p>
	 *
	 * @param nuclide_hashDataset the nuclide_hashDataset
	 */
	public DataNode setNuclide_hash(IDataset nuclide_hashDataset);

	/**
	 * Table of nuclide instances of which each candidate is composed.
	 * Each row vector is sorted in descending order.
	 * Unused entries in the matrix should be set to 0.
	 * Use the hashing rule that is defined in nuclide_hash of :ref:`NXatom`.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_cand; 2: n_ivec_max;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getNuclide_hashScalar();

	/**
	 * Table of nuclide instances of which each candidate is composed.
	 * Each row vector is sorted in descending order.
	 * Unused entries in the matrix should be set to 0.
	 * Use the hashing rule that is defined in nuclide_hash of :ref:`NXatom`.
	 * <p>
	 * <b>Type:</b> NX_UINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_cand; 2: n_ivec_max;
	 * </p>
	 *
	 * @param nuclide_hash the nuclide_hash
	 */
	public DataNode setNuclide_hashScalar(Long nuclide_hashValue);

	/**
	 * Accumulated mass of the nuclides in each candidate.
	 * Not corrected for quantum effects.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_MASS
	 * <b>Dimensions:</b> 1: n_cand;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getMass();

	/**
	 * Accumulated mass of the nuclides in each candidate.
	 * Not corrected for quantum effects.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_MASS
	 * <b>Dimensions:</b> 1: n_cand;
	 * </p>
	 *
	 * @param massDataset the massDataset
	 */
	public DataNode setMass(IDataset massDataset);

	/**
	 * Accumulated mass of the nuclides in each candidate.
	 * Not corrected for quantum effects.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_MASS
	 * <b>Dimensions:</b> 1: n_cand;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getMassScalar();

	/**
	 * Accumulated mass of the nuclides in each candidate.
	 * Not corrected for quantum effects.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_MASS
	 * <b>Dimensions:</b> 1: n_cand;
	 * </p>
	 *
	 * @param mass the mass
	 */
	public DataNode setMassScalar(Double massValue);

	/**
	 * The product of the natural abundances of the nuclides for each candidate.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: n_cand;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getNatural_abundance_product();

	/**
	 * The product of the natural abundances of the nuclides for each candidate.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: n_cand;
	 * </p>
	 *
	 * @param natural_abundance_productDataset the natural_abundance_productDataset
	 */
	public DataNode setNatural_abundance_product(IDataset natural_abundance_productDataset);

	/**
	 * The product of the natural abundances of the nuclides for each candidate.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: n_cand;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getNatural_abundance_productScalar();

	/**
	 * The product of the natural abundances of the nuclides for each candidate.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: n_cand;
	 * </p>
	 *
	 * @param natural_abundance_product the natural_abundance_product
	 */
	public DataNode setNatural_abundance_productScalar(Double natural_abundance_productValue);

	/**
	 * For each candidate the half life of the nuclide that has the
	 * shortest half life.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * <b>Dimensions:</b> 1: n_cand;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getShortest_half_life();

	/**
	 * For each candidate the half life of the nuclide that has the
	 * shortest half life.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * <b>Dimensions:</b> 1: n_cand;
	 * </p>
	 *
	 * @param shortest_half_lifeDataset the shortest_half_lifeDataset
	 */
	public DataNode setShortest_half_life(IDataset shortest_half_lifeDataset);

	/**
	 * For each candidate the half life of the nuclide that has the
	 * shortest half life.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * <b>Dimensions:</b> 1: n_cand;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getShortest_half_lifeScalar();

	/**
	 * For each candidate the half life of the nuclide that has the
	 * shortest half life.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TIME
	 * <b>Dimensions:</b> 1: n_cand;
	 * </p>
	 *
	 * @param shortest_half_life the shortest_half_life
	 */
	public DataNode setShortest_half_lifeScalar(Double shortest_half_lifeValue);

}

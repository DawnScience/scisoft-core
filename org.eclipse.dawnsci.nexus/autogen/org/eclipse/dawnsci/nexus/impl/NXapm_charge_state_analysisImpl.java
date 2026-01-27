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

package org.eclipse.dawnsci.nexus.impl;

import java.util.Set;
import java.util.EnumSet;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Dataset;

import org.eclipse.dawnsci.nexus.*;

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

 */
public class NXapm_charge_state_analysisImpl extends NXprocessImpl implements NXapm_charge_state_analysis {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_PARAMETERS);

	public NXapm_charge_state_analysisImpl() {
		super();
	}

	public NXapm_charge_state_analysisImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXapm_charge_state_analysis.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_APM_CHARGE_STATE_ANALYSIS;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public NXparameters getConfig() {
		// dataNodeName = NX_CONFIG
		return getChild("config", NXparameters.class);
	}

	@Override
	public void setConfig(NXparameters configGroup) {
		putChild("config", configGroup);
	}

	@Override
	public Dataset getCharge_state() {
		return getDataset(NX_CHARGE_STATE);
	}

	@Override
	public Long getCharge_stateScalar() {
		return getLong(NX_CHARGE_STATE);
	}

	@Override
	public DataNode setCharge_state(IDataset charge_stateDataset) {
		return setDataset(NX_CHARGE_STATE, charge_stateDataset);
	}

	@Override
	public DataNode setCharge_stateScalar(Long charge_stateValue) {
		return setField(NX_CHARGE_STATE, charge_stateValue);
	}

	@Override
	public Dataset getNuclide_hash() {
		return getDataset(NX_NUCLIDE_HASH);
	}

	@Override
	public Long getNuclide_hashScalar() {
		return getLong(NX_NUCLIDE_HASH);
	}

	@Override
	public DataNode setNuclide_hash(IDataset nuclide_hashDataset) {
		return setDataset(NX_NUCLIDE_HASH, nuclide_hashDataset);
	}

	@Override
	public DataNode setNuclide_hashScalar(Long nuclide_hashValue) {
		return setField(NX_NUCLIDE_HASH, nuclide_hashValue);
	}

	@Override
	public Dataset getMass() {
		return getDataset(NX_MASS);
	}

	@Override
	public Double getMassScalar() {
		return getDouble(NX_MASS);
	}

	@Override
	public DataNode setMass(IDataset massDataset) {
		return setDataset(NX_MASS, massDataset);
	}

	@Override
	public DataNode setMassScalar(Double massValue) {
		return setField(NX_MASS, massValue);
	}

	@Override
	public Dataset getNatural_abundance_product() {
		return getDataset(NX_NATURAL_ABUNDANCE_PRODUCT);
	}

	@Override
	public Double getNatural_abundance_productScalar() {
		return getDouble(NX_NATURAL_ABUNDANCE_PRODUCT);
	}

	@Override
	public DataNode setNatural_abundance_product(IDataset natural_abundance_productDataset) {
		return setDataset(NX_NATURAL_ABUNDANCE_PRODUCT, natural_abundance_productDataset);
	}

	@Override
	public DataNode setNatural_abundance_productScalar(Double natural_abundance_productValue) {
		return setField(NX_NATURAL_ABUNDANCE_PRODUCT, natural_abundance_productValue);
	}

	@Override
	public Dataset getShortest_half_life() {
		return getDataset(NX_SHORTEST_HALF_LIFE);
	}

	@Override
	public Double getShortest_half_lifeScalar() {
		return getDouble(NX_SHORTEST_HALF_LIFE);
	}

	@Override
	public DataNode setShortest_half_life(IDataset shortest_half_lifeDataset) {
		return setDataset(NX_SHORTEST_HALF_LIFE, shortest_half_lifeDataset);
	}

	@Override
	public DataNode setShortest_half_lifeScalar(Double shortest_half_lifeValue) {
		return setField(NX_SHORTEST_HALF_LIFE, shortest_half_lifeValue);
	}

}

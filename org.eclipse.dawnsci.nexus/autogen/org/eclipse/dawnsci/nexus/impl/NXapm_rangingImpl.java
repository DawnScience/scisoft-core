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
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Dataset;

import org.eclipse.dawnsci.nexus.*;

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

 */
public class NXapm_rangingImpl extends NXprocessImpl implements NXapm_ranging {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_PROGRAM,
		NexusBaseClass.NX_NOTE,
		NexusBaseClass.NX_PROCESS,
		NexusBaseClass.NX_PROCESS,
		NexusBaseClass.NX_PROCESS,
		NexusBaseClass.NX_PROCESS);

	public NXapm_rangingImpl() {
		super();
	}

	public NXapm_rangingImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXapm_ranging.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_APM_RANGING;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public NXprogram getNXProgram() {
		// dataNodeName = NX_PROGRAM
		return getChild("program", NXprogram.class);
	}

	@Override
	public void setNXProgram(NXprogram programGroup) {
		putChild("program", programGroup);
	}

	@Override
	public NXprogram getNXProgram(String name) {
		return getChild(name, NXprogram.class);
	}

	@Override
	public void setNXProgram(String name, NXprogram program) {
		putChild(name, program);
	}

	@Override
	public Map<String, NXprogram> getAllNXProgram() {
		return getChildren(NXprogram.class);
	}

	@Override
	public void setAllNXProgram(Map<String, NXprogram> program) {
		setChildren(program);
	}

	@Override
	public NXnote getNote() {
		// dataNodeName = NX_NOTE
		return getChild("note", NXnote.class);
	}

	@Override
	public void setNote(NXnote noteGroup) {
		putChild("note", noteGroup);
	}

	@Override
	public NXnote getNote(String name) {
		return getChild(name, NXnote.class);
	}

	@Override
	public void setNote(String name, NXnote note) {
		putChild(name, note);
	}

	@Override
	public Map<String, NXnote> getAllNote() {
		return getChildren(NXnote.class);
	}

	@Override
	public void setAllNote(Map<String, NXnote> note) {
		setChildren(note);
	}

	@Override
	public NXprocess getMass_to_charge_distribution() {
		// dataNodeName = NX_MASS_TO_CHARGE_DISTRIBUTION
		return getChild("mass_to_charge_distribution", NXprocess.class);
	}

	@Override
	public void setMass_to_charge_distribution(NXprocess mass_to_charge_distributionGroup) {
		putChild("mass_to_charge_distribution", mass_to_charge_distributionGroup);
	}
	// Unprocessed group:
	// Unprocessed group: mass_spectrum

	@Override
	public NXprocess getBackground_quantification() {
		// dataNodeName = NX_BACKGROUND_QUANTIFICATION
		return getChild("background_quantification", NXprocess.class);
	}

	@Override
	public void setBackground_quantification(NXprocess background_quantificationGroup) {
		putChild("background_quantification", background_quantificationGroup);
	}
	// Unprocessed group:

	@Override
	public NXprocess getPeak_search_and_deconvolution() {
		// dataNodeName = NX_PEAK_SEARCH_AND_DECONVOLUTION
		return getChild("peak_search_and_deconvolution", NXprocess.class);
	}

	@Override
	public void setPeak_search_and_deconvolution(NXprocess peak_search_and_deconvolutionGroup) {
		putChild("peak_search_and_deconvolution", peak_search_and_deconvolutionGroup);
	}
	// Unprocessed group:
	// Unprocessed group:

	@Override
	public NXprocess getPeak_identification() {
		// dataNodeName = NX_PEAK_IDENTIFICATION
		return getChild("peak_identification", NXprocess.class);
	}

	@Override
	public void setPeak_identification(NXprocess peak_identificationGroup) {
		putChild("peak_identification", peak_identificationGroup);
	}
	// Unprocessed group:
	// Unprocessed group:

}

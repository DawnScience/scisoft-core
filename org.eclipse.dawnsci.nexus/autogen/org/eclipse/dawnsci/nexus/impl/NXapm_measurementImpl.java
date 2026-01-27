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
 * Base class for collecting a run with a real or a simulated atom probe or field-ion microscope.
 * The term run is understood as an exact synonym for session, i.e. the usage of a real or simulated
 * tomograph or microscope for a certain amount of time during which one characterizes a single specimen.
 * Research workflows for experiments and simulations of atom probe and related field-evaporation
 * evolve continuously and become increasingly connected with other methods used for material
 * characterization specifically electron microscopy. A few examples in this direction are:
 * * `T. Kelly et al. <https://doi.org/10.1017/S1431927620022205>`_
 * * `C. Fleischmann et al. <https://doi.org/10.1016/j.ultramic.2018.08.010>`_
 * * `W. Windl et al. <https://doi.org/10.1093/micmic/ozad067.294>`_
 * * `C. Freysoldt et al. <https://doi.org/10.1103/PhysRevLett.124.176801>`_
 * * `G. da Costa et al. <https://doi.org/10.1038/s41467-024-54169-2>`_
 * The majority of atom probe research is performed using the so-called Local Electrode Atom Probe (LEAP) instruments
 * from AMETEK/Cameca. In addition, several research groups have built their own instruments and shared different
 * aspects of the technical specifications and approaches including how these groups apply data processing e.g.:
 * * `M. Monajem et al. <https://doi.org/10.1017/S1431927622003397>`_
 * * `P. Stender et al. <https://doi.org/10.1017/S1431927621013982>`_
 * * `I. Dimkou et al. <https://doi.org/10.1093/micmic/ozac051>`_
 * to name but a few.

 */
public class NXapm_measurementImpl extends NXobjectImpl implements NXapm_measurement {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_APM_INSTRUMENT,
		NexusBaseClass.NX_APM_EVENT_DATA);

	public NXapm_measurementImpl() {
		super();
	}

	public NXapm_measurementImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXapm_measurement.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_APM_MEASUREMENT;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getStatus() {
		return getDataset(NX_STATUS);
	}

	@Override
	public String getStatusScalar() {
		return getString(NX_STATUS);
	}

	@Override
	public DataNode setStatus(IDataset statusDataset) {
		return setDataset(NX_STATUS, statusDataset);
	}

	@Override
	public DataNode setStatusScalar(String statusValue) {
		return setString(NX_STATUS, statusValue);
	}

	@Override
	public Dataset getQuality() {
		return getDataset(NX_QUALITY);
	}

	@Override
	public String getQualityScalar() {
		return getString(NX_QUALITY);
	}

	@Override
	public DataNode setQuality(IDataset qualityDataset) {
		return setDataset(NX_QUALITY, qualityDataset);
	}

	@Override
	public DataNode setQualityScalar(String qualityValue) {
		return setString(NX_QUALITY, qualityValue);
	}

	@Override
	public NXapm_instrument getApm_instrument() {
		// dataNodeName = NX_APM_INSTRUMENT
		return getChild("apm_instrument", NXapm_instrument.class);
	}

	@Override
	public void setApm_instrument(NXapm_instrument apm_instrumentGroup) {
		putChild("apm_instrument", apm_instrumentGroup);
	}

	@Override
	public NXapm_instrument getApm_instrument(String name) {
		return getChild(name, NXapm_instrument.class);
	}

	@Override
	public void setApm_instrument(String name, NXapm_instrument apm_instrument) {
		putChild(name, apm_instrument);
	}

	@Override
	public Map<String, NXapm_instrument> getAllApm_instrument() {
		return getChildren(NXapm_instrument.class);
	}

	@Override
	public void setAllApm_instrument(Map<String, NXapm_instrument> apm_instrument) {
		setChildren(apm_instrument);
	}

	@Override
	public NXapm_event_data getApm_event_data() {
		// dataNodeName = NX_APM_EVENT_DATA
		return getChild("apm_event_data", NXapm_event_data.class);
	}

	@Override
	public void setApm_event_data(NXapm_event_data apm_event_dataGroup) {
		putChild("apm_event_data", apm_event_dataGroup);
	}

	@Override
	public NXapm_event_data getApm_event_data(String name) {
		return getChild(name, NXapm_event_data.class);
	}

	@Override
	public void setApm_event_data(String name, NXapm_event_data apm_event_data) {
		putChild(name, apm_event_data);
	}

	@Override
	public Map<String, NXapm_event_data> getAllApm_event_data() {
		return getChildren(NXapm_event_data.class);
	}

	@Override
	public void setAllApm_event_data(Map<String, NXapm_event_data> apm_event_data) {
		setChildren(apm_event_data);
	}

}

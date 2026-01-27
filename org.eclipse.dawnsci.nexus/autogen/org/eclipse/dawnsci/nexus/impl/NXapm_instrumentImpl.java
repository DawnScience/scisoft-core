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
 * Base class for instrument-related details of a real or simulated
 * atom probe tomograph or field-ion microscope.
 * For collecting data and experiments which are simulations of an atom probe
 * microscope or a session with such instrument use the :ref:`NXapm` application definition
 * and the :ref:`NXapm_event_data` groups it provides.
 * This base class implements the concept of :ref:`NXapm` whereby (meta)data are distinguished
 * whether these typically change during a session, so-called dynamic, or not, so-called static metadata.
 * This design allows to store e.g. hardware related concepts only once instead of demanding
 * that each image or spectrum from the session needs to be stored also with the static metadata.

 */
public class NXapm_instrumentImpl extends NXinstrumentImpl implements NXapm_instrument {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_FABRICATION,
		NexusBaseClass.NX_COMPONENT,
		NexusBaseClass.NX_COMPONENT,
		NexusBaseClass.NX_COMPONENT,
		NexusBaseClass.NX_DETECTOR,
		NexusBaseClass.NX_COMPONENT,
		NexusBaseClass.NX_MANIPULATOR,
		NexusBaseClass.NX_COMPONENT,
		NexusBaseClass.NX_COMPONENT,
		NexusBaseClass.NX_COMPONENT,
		NexusBaseClass.NX_PUMP,
		NexusBaseClass.NX_PUMP,
		NexusBaseClass.NX_PUMP,
		NexusBaseClass.NX_PARAMETERS);

	public NXapm_instrumentImpl() {
		super();
	}

	public NXapm_instrumentImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXapm_instrument.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_APM_INSTRUMENT;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getType() {
		return getDataset(NX_TYPE);
	}

	@Override
	public String getTypeScalar() {
		return getString(NX_TYPE);
	}

	@Override
	public DataNode setType(IDataset typeDataset) {
		return setDataset(NX_TYPE, typeDataset);
	}

	@Override
	public DataNode setTypeScalar(String typeValue) {
		return setString(NX_TYPE, typeValue);
	}

	@Override
	public NXfabrication getFabrication() {
		// dataNodeName = NX_FABRICATION
		return getChild("fabrication", NXfabrication.class);
	}

	@Override
	public void setFabrication(NXfabrication fabricationGroup) {
		putChild("fabrication", fabricationGroup);
	}

	@Override
	public Dataset getLocation() {
		return getDataset(NX_LOCATION);
	}

	@Override
	public String getLocationScalar() {
		return getString(NX_LOCATION);
	}

	@Override
	public DataNode setLocation(IDataset locationDataset) {
		return setDataset(NX_LOCATION, locationDataset);
	}

	@Override
	public DataNode setLocationScalar(String locationValue) {
		return setString(NX_LOCATION, locationValue);
	}

	@Override
	public Dataset getFlight_path() {
		return getDataset(NX_FLIGHT_PATH);
	}

	@Override
	public Double getFlight_pathScalar() {
		return getDouble(NX_FLIGHT_PATH);
	}

	@Override
	public DataNode setFlight_path(IDataset flight_pathDataset) {
		return setDataset(NX_FLIGHT_PATH, flight_pathDataset);
	}

	@Override
	public DataNode setFlight_pathScalar(Double flight_pathValue) {
		return setField(NX_FLIGHT_PATH, flight_pathValue);
	}

	@Override
	public NXcomponent getReflectron() {
		// dataNodeName = NX_REFLECTRON
		return getChild("reflectron", NXcomponent.class);
	}

	@Override
	public void setReflectron(NXcomponent reflectronGroup) {
		putChild("reflectron", reflectronGroup);
	}

	@Override
	public NXcomponent getDecelerate_electrode() {
		// dataNodeName = NX_DECELERATE_ELECTRODE
		return getChild("decelerate_electrode", NXcomponent.class);
	}

	@Override
	public void setDecelerate_electrode(NXcomponent decelerate_electrodeGroup) {
		putChild("decelerate_electrode", decelerate_electrodeGroup);
	}

	@Override
	public NXcomponent getLocal_electrode() {
		// dataNodeName = NX_LOCAL_ELECTRODE
		return getChild("local_electrode", NXcomponent.class);
	}

	@Override
	public void setLocal_electrode(NXcomponent local_electrodeGroup) {
		putChild("local_electrode", local_electrodeGroup);
	}

	@Override
	public NXdetector getIon_detector() {
		// dataNodeName = NX_ION_DETECTOR
		return getChild("ion_detector", NXdetector.class);
	}

	@Override
	public void setIon_detector(NXdetector ion_detectorGroup) {
		putChild("ion_detector", ion_detectorGroup);
	}

	@Override
	public NXcomponent getPulser() {
		// dataNodeName = NX_PULSER
		return getChild("pulser", NXcomponent.class);
	}

	@Override
	public void setPulser(NXcomponent pulserGroup) {
		putChild("pulser", pulserGroup);
	}
	// Unprocessed group: fabrication
	// Unprocessed group: sourceID

	@Override
	public NXmanipulator getStage() {
		// dataNodeName = NX_STAGE
		return getChild("stage", NXmanipulator.class);
	}

	@Override
	public void setStage(NXmanipulator stageGroup) {
		putChild("stage", stageGroup);
	}
	// Unprocessed group: temperature_sensor

	@Override
	public NXcomponent getAnalysis_chamber() {
		// dataNodeName = NX_ANALYSIS_CHAMBER
		return getChild("analysis_chamber", NXcomponent.class);
	}

	@Override
	public void setAnalysis_chamber(NXcomponent analysis_chamberGroup) {
		putChild("analysis_chamber", analysis_chamberGroup);
	}
	// Unprocessed group: pressure_sensor

	@Override
	public NXcomponent getBuffer_chamber() {
		// dataNodeName = NX_BUFFER_CHAMBER
		return getChild("buffer_chamber", NXcomponent.class);
	}

	@Override
	public void setBuffer_chamber(NXcomponent buffer_chamberGroup) {
		putChild("buffer_chamber", buffer_chamberGroup);
	}

	@Override
	public NXcomponent getLoad_lock_chamber() {
		// dataNodeName = NX_LOAD_LOCK_CHAMBER
		return getChild("load_lock_chamber", NXcomponent.class);
	}

	@Override
	public void setLoad_lock_chamber(NXcomponent load_lock_chamberGroup) {
		putChild("load_lock_chamber", load_lock_chamberGroup);
	}

	@Override
	public NXpump getGetter_pump() {
		// dataNodeName = NX_GETTER_PUMP
		return getChild("getter_pump", NXpump.class);
	}

	@Override
	public void setGetter_pump(NXpump getter_pumpGroup) {
		putChild("getter_pump", getter_pumpGroup);
	}

	@Override
	public NXpump getRoughening_pump() {
		// dataNodeName = NX_ROUGHENING_PUMP
		return getChild("roughening_pump", NXpump.class);
	}

	@Override
	public void setRoughening_pump(NXpump roughening_pumpGroup) {
		putChild("roughening_pump", roughening_pumpGroup);
	}

	@Override
	public NXpump getTurbomolecular_pump() {
		// dataNodeName = NX_TURBOMOLECULAR_PUMP
		return getChild("turbomolecular_pump", NXpump.class);
	}

	@Override
	public void setTurbomolecular_pump(NXpump turbomolecular_pumpGroup) {
		putChild("turbomolecular_pump", turbomolecular_pumpGroup);
	}

	@Override
	public Dataset getComment() {
		return getDataset(NX_COMMENT);
	}

	@Override
	public String getCommentScalar() {
		return getString(NX_COMMENT);
	}

	@Override
	public DataNode setComment(IDataset commentDataset) {
		return setDataset(NX_COMMENT, commentDataset);
	}

	@Override
	public DataNode setCommentScalar(String commentValue) {
		return setString(NX_COMMENT, commentValue);
	}

	@Override
	public NXparameters getControl() {
		// dataNodeName = NX_CONTROL
		return getChild("control", NXparameters.class);
	}

	@Override
	public void setControl(NXparameters controlGroup) {
		putChild("control", controlGroup);
	}

}

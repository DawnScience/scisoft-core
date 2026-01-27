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
 * The scan box or scan controller is a component that is used to deflect a
 * beam of charged particles in a controlled manner.
 * The scan box is instructed by (an) instance(s) of :ref:`NXprogram`, some control software,
 * which is not necessarily the same program as the one controlling other parts of the instrument.
 * The scan box directs the probe of charged particles (electrons, ions)
 * to controlled locations according to a scan scheme and plan.

 */
public class NXscan_controllerImpl extends NXcomponentImpl implements NXscan_controller {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_DEFLECTOR,
		NexusBaseClass.NX_CIRCUIT);

	public NXscan_controllerImpl() {
		super();
	}

	public NXscan_controllerImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXscan_controller.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_SCAN_CONTROLLER;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getScan_schema() {
		return getDataset(NX_SCAN_SCHEMA);
	}

	@Override
	public String getScan_schemaScalar() {
		return getString(NX_SCAN_SCHEMA);
	}

	@Override
	public DataNode setScan_schema(IDataset scan_schemaDataset) {
		return setDataset(NX_SCAN_SCHEMA, scan_schemaDataset);
	}

	@Override
	public DataNode setScan_schemaScalar(String scan_schemaValue) {
		return setString(NX_SCAN_SCHEMA, scan_schemaValue);
	}

	@Override
	public Dataset getDwell_time() {
		return getDataset(NX_DWELL_TIME);
	}

	@Override
	public Number getDwell_timeScalar() {
		return getNumber(NX_DWELL_TIME);
	}

	@Override
	public DataNode setDwell_time(IDataset dwell_timeDataset) {
		return setDataset(NX_DWELL_TIME, dwell_timeDataset);
	}

	@Override
	public DataNode setDwell_timeScalar(Number dwell_timeValue) {
		return setField(NX_DWELL_TIME, dwell_timeValue);
	}

	@Override
	public Dataset getFlyback_time() {
		return getDataset(NX_FLYBACK_TIME);
	}

	@Override
	public Number getFlyback_timeScalar() {
		return getNumber(NX_FLYBACK_TIME);
	}

	@Override
	public DataNode setFlyback_time(IDataset flyback_timeDataset) {
		return setDataset(NX_FLYBACK_TIME, flyback_timeDataset);
	}

	@Override
	public DataNode setFlyback_timeScalar(Number flyback_timeValue) {
		return setField(NX_FLYBACK_TIME, flyback_timeValue);
	}

	@Override
	public NXdeflector getDeflector() {
		// dataNodeName = NX_DEFLECTOR
		return getChild("deflector", NXdeflector.class);
	}

	@Override
	public void setDeflector(NXdeflector deflectorGroup) {
		putChild("deflector", deflectorGroup);
	}

	@Override
	public NXdeflector getDeflector(String name) {
		return getChild(name, NXdeflector.class);
	}

	@Override
	public void setDeflector(String name, NXdeflector deflector) {
		putChild(name, deflector);
	}

	@Override
	public Map<String, NXdeflector> getAllDeflector() {
		return getChildren(NXdeflector.class);
	}

	@Override
	public void setAllDeflector(Map<String, NXdeflector> deflector) {
		setChildren(deflector);
	}

	@Override
	public NXcircuit getCircuit() {
		// dataNodeName = NX_CIRCUIT
		return getChild("circuit", NXcircuit.class);
	}

	@Override
	public void setCircuit(NXcircuit circuitGroup) {
		putChild("circuit", circuitGroup);
	}

	@Override
	public NXcircuit getCircuit(String name) {
		return getChild(name, NXcircuit.class);
	}

	@Override
	public void setCircuit(String name, NXcircuit circuit) {
		putChild(name, circuit);
	}

	@Override
	public Map<String, NXcircuit> getAllCircuit() {
		return getChildren(NXcircuit.class);
	}

	@Override
	public void setAllCircuit(Map<String, NXcircuit> circuit) {
		setChildren(circuit);
	}

}

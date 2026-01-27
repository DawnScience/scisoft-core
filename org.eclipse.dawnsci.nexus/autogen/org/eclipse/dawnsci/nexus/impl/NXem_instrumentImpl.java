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
 * Base class for instrument-related details of a real or simulated electron microscope.
 * For collecting data and experiments which are simulations of an electron
 * microscope (or such session) use the :ref:`NXem` application definition and
 * the :ref:`NXem_event_data` groups it provides.
 * This base class implements the concept of :ref:`NXem` whereby (meta)data are distinguished
 * whether these typically change during a session (dynamic) or not (static metadata).
 * This design allows to store e.g. hardware related concepts only once instead of demanding
 * that each image or spectrum from the session needs to be stored also with the static metadata.

 */
public class NXem_instrumentImpl extends NXinstrumentImpl implements NXem_instrument {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_FABRICATION,
		NexusBaseClass.NX_EBEAM_COLUMN,
		NexusBaseClass.NX_IBEAM_COLUMN,
		NexusBaseClass.NX_EM_OPTICAL_SYSTEM,
		NexusBaseClass.NX_DETECTOR,
		NexusBaseClass.NX_MANIPULATOR,
		NexusBaseClass.NX_MANIPULATOR,
		NexusBaseClass.NX_COMPONENT,
		NexusBaseClass.NX_PUMP,
		NexusBaseClass.NX_SENSOR,
		NexusBaseClass.NX_ACTUATOR);

	public NXem_instrumentImpl() {
		super();
	}

	public NXem_instrumentImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXem_instrument.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_EM_INSTRUMENT;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getName() {
		return getDataset(NX_NAME);
	}

	@Override
	public String getNameScalar() {
		return getString(NX_NAME);
	}

	@Override
	public DataNode setName(IDataset nameDataset) {
		return setDataset(NX_NAME, nameDataset);
	}

	@Override
	public DataNode setNameScalar(String nameValue) {
		return setString(NX_NAME, nameValue);
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
	public NXfabrication getFabrication(String name) {
		return getChild(name, NXfabrication.class);
	}

	@Override
	public void setFabrication(String name, NXfabrication fabrication) {
		putChild(name, fabrication);
	}

	@Override
	public Map<String, NXfabrication> getAllFabrication() {
		return getChildren(NXfabrication.class);
	}

	@Override
	public void setAllFabrication(Map<String, NXfabrication> fabrication) {
		setChildren(fabrication);
	}

	@Override
	public NXebeam_column getEbeam_column() {
		// dataNodeName = NX_EBEAM_COLUMN
		return getChild("ebeam_column", NXebeam_column.class);
	}

	@Override
	public void setEbeam_column(NXebeam_column ebeam_columnGroup) {
		putChild("ebeam_column", ebeam_columnGroup);
	}

	@Override
	public NXebeam_column getEbeam_column(String name) {
		return getChild(name, NXebeam_column.class);
	}

	@Override
	public void setEbeam_column(String name, NXebeam_column ebeam_column) {
		putChild(name, ebeam_column);
	}

	@Override
	public Map<String, NXebeam_column> getAllEbeam_column() {
		return getChildren(NXebeam_column.class);
	}

	@Override
	public void setAllEbeam_column(Map<String, NXebeam_column> ebeam_column) {
		setChildren(ebeam_column);
	}

	@Override
	public NXibeam_column getIbeam_column() {
		// dataNodeName = NX_IBEAM_COLUMN
		return getChild("ibeam_column", NXibeam_column.class);
	}

	@Override
	public void setIbeam_column(NXibeam_column ibeam_columnGroup) {
		putChild("ibeam_column", ibeam_columnGroup);
	}

	@Override
	public NXibeam_column getIbeam_column(String name) {
		return getChild(name, NXibeam_column.class);
	}

	@Override
	public void setIbeam_column(String name, NXibeam_column ibeam_column) {
		putChild(name, ibeam_column);
	}

	@Override
	public Map<String, NXibeam_column> getAllIbeam_column() {
		return getChildren(NXibeam_column.class);
	}

	@Override
	public void setAllIbeam_column(Map<String, NXibeam_column> ibeam_column) {
		setChildren(ibeam_column);
	}

	@Override
	public NXem_optical_system getEm_optical_system() {
		// dataNodeName = NX_EM_OPTICAL_SYSTEM
		return getChild("em_optical_system", NXem_optical_system.class);
	}

	@Override
	public void setEm_optical_system(NXem_optical_system em_optical_systemGroup) {
		putChild("em_optical_system", em_optical_systemGroup);
	}

	@Override
	public NXem_optical_system getEm_optical_system(String name) {
		return getChild(name, NXem_optical_system.class);
	}

	@Override
	public void setEm_optical_system(String name, NXem_optical_system em_optical_system) {
		putChild(name, em_optical_system);
	}

	@Override
	public Map<String, NXem_optical_system> getAllEm_optical_system() {
		return getChildren(NXem_optical_system.class);
	}

	@Override
	public void setAllEm_optical_system(Map<String, NXem_optical_system> em_optical_system) {
		setChildren(em_optical_system);
	}

	@Override
	public NXdetector getDetector() {
		// dataNodeName = NX_DETECTOR
		return getChild("detector", NXdetector.class);
	}

	@Override
	public void setDetector(NXdetector detectorGroup) {
		putChild("detector", detectorGroup);
	}

	@Override
	public NXdetector getDetector(String name) {
		return getChild(name, NXdetector.class);
	}

	@Override
	public void setDetector(String name, NXdetector detector) {
		putChild(name, detector);
	}

	@Override
	public Map<String, NXdetector> getAllDetector() {
		return getChildren(NXdetector.class);
	}

	@Override
	public void setAllDetector(Map<String, NXdetector> detector) {
		setChildren(detector);
	}

	@Override
	public NXmanipulator getStageid() {
		// dataNodeName = NX_STAGEID
		return getChild("stageid", NXmanipulator.class);
	}

	@Override
	public void setStageid(NXmanipulator stageidGroup) {
		putChild("stageid", stageidGroup);
	}

	@Override
	public NXmanipulator getNanoprobeid() {
		// dataNodeName = NX_NANOPROBEID
		return getChild("nanoprobeid", NXmanipulator.class);
	}

	@Override
	public void setNanoprobeid(NXmanipulator nanoprobeidGroup) {
		putChild("nanoprobeid", nanoprobeidGroup);
	}

	@Override
	public NXcomponent getGas_injector() {
		// dataNodeName = NX_GAS_INJECTOR
		return getChild("gas_injector", NXcomponent.class);
	}

	@Override
	public void setGas_injector(NXcomponent gas_injectorGroup) {
		putChild("gas_injector", gas_injectorGroup);
	}

	@Override
	public NXpump getPump() {
		// dataNodeName = NX_PUMP
		return getChild("pump", NXpump.class);
	}

	@Override
	public void setPump(NXpump pumpGroup) {
		putChild("pump", pumpGroup);
	}

	@Override
	public NXpump getPump(String name) {
		return getChild(name, NXpump.class);
	}

	@Override
	public void setPump(String name, NXpump pump) {
		putChild(name, pump);
	}

	@Override
	public Map<String, NXpump> getAllPump() {
		return getChildren(NXpump.class);
	}

	@Override
	public void setAllPump(Map<String, NXpump> pump) {
		setChildren(pump);
	}

	@Override
	public NXsensor getSensor() {
		// dataNodeName = NX_SENSOR
		return getChild("sensor", NXsensor.class);
	}

	@Override
	public void setSensor(NXsensor sensorGroup) {
		putChild("sensor", sensorGroup);
	}

	@Override
	public NXsensor getSensor(String name) {
		return getChild(name, NXsensor.class);
	}

	@Override
	public void setSensor(String name, NXsensor sensor) {
		putChild(name, sensor);
	}

	@Override
	public Map<String, NXsensor> getAllSensor() {
		return getChildren(NXsensor.class);
	}

	@Override
	public void setAllSensor(Map<String, NXsensor> sensor) {
		setChildren(sensor);
	}

	@Override
	public NXactuator getActuator() {
		// dataNodeName = NX_ACTUATOR
		return getChild("actuator", NXactuator.class);
	}

	@Override
	public void setActuator(NXactuator actuatorGroup) {
		putChild("actuator", actuatorGroup);
	}

	@Override
	public NXactuator getActuator(String name) {
		return getChild(name, NXactuator.class);
	}

	@Override
	public void setActuator(String name, NXactuator actuator) {
		putChild(name, actuator);
	}

	@Override
	public Map<String, NXactuator> getAllActuator() {
		return getChildren(NXactuator.class);
	}

	@Override
	public void setAllActuator(Map<String, NXactuator> actuator) {
		setChildren(actuator);
	}

}

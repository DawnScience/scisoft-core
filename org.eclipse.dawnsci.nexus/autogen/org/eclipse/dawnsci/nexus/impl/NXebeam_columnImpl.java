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
 * Base class for a set of components providing a controllable electron beam.
 * The idea behind defining :ref:`NXebeam_column` as an own base class vs. adding these
 * concepts in :ref:`NXem_instrument` is that the electron beam generating component
 * might be worthwhile to use also in other types of experiments.

 */
public class NXebeam_columnImpl extends NXcomponentImpl implements NXebeam_column {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_SOURCE,
		NexusBaseClass.NX_ELECTROMAGNETIC_LENS,
		NexusBaseClass.NX_APERTURE,
		NexusBaseClass.NX_DEFLECTOR,
		NexusBaseClass.NX_DEFLECTOR,
		NexusBaseClass.NX_MONOCHROMATOR,
		NexusBaseClass.NX_CORRECTOR_CS,
		NexusBaseClass.NX_COMPONENT,
		NexusBaseClass.NX_COMPONENT,
		NexusBaseClass.NX_COMPONENT,
		NexusBaseClass.NX_SENSOR,
		NexusBaseClass.NX_ACTUATOR,
		NexusBaseClass.NX_BEAM,
		NexusBaseClass.NX_COMPONENT,
		NexusBaseClass.NX_SCAN_CONTROLLER);

	public NXebeam_columnImpl() {
		super();
	}

	public NXebeam_columnImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXebeam_column.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_EBEAM_COLUMN;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getOperation_mode() {
		return getDataset(NX_OPERATION_MODE);
	}

	@Override
	public String getOperation_modeScalar() {
		return getString(NX_OPERATION_MODE);
	}

	@Override
	public DataNode setOperation_mode(IDataset operation_modeDataset) {
		return setDataset(NX_OPERATION_MODE, operation_modeDataset);
	}

	@Override
	public DataNode setOperation_modeScalar(String operation_modeValue) {
		return setString(NX_OPERATION_MODE, operation_modeValue);
	}

	@Override
	public NXsource getElectron_source() {
		// dataNodeName = NX_ELECTRON_SOURCE
		return getChild("electron_source", NXsource.class);
	}

	@Override
	public void setElectron_source(NXsource electron_sourceGroup) {
		putChild("electron_source", electron_sourceGroup);
	}

	@Override
	public NXelectromagnetic_lens getElectromagnetic_lens() {
		// dataNodeName = NX_ELECTROMAGNETIC_LENS
		return getChild("electromagnetic_lens", NXelectromagnetic_lens.class);
	}

	@Override
	public void setElectromagnetic_lens(NXelectromagnetic_lens electromagnetic_lensGroup) {
		putChild("electromagnetic_lens", electromagnetic_lensGroup);
	}

	@Override
	public NXelectromagnetic_lens getElectromagnetic_lens(String name) {
		return getChild(name, NXelectromagnetic_lens.class);
	}

	@Override
	public void setElectromagnetic_lens(String name, NXelectromagnetic_lens electromagnetic_lens) {
		putChild(name, electromagnetic_lens);
	}

	@Override
	public Map<String, NXelectromagnetic_lens> getAllElectromagnetic_lens() {
		return getChildren(NXelectromagnetic_lens.class);
	}

	@Override
	public void setAllElectromagnetic_lens(Map<String, NXelectromagnetic_lens> electromagnetic_lens) {
		setChildren(electromagnetic_lens);
	}

	@Override
	public NXaperture getAperture() {
		// dataNodeName = NX_APERTURE
		return getChild("aperture", NXaperture.class);
	}

	@Override
	public void setAperture(NXaperture apertureGroup) {
		putChild("aperture", apertureGroup);
	}

	@Override
	public NXaperture getAperture(String name) {
		return getChild(name, NXaperture.class);
	}

	@Override
	public void setAperture(String name, NXaperture aperture) {
		putChild(name, aperture);
	}

	@Override
	public Map<String, NXaperture> getAllAperture() {
		return getChildren(NXaperture.class);
	}

	@Override
	public void setAllAperture(Map<String, NXaperture> aperture) {
		setChildren(aperture);
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
	public NXdeflector getBlankerid() {
		// dataNodeName = NX_BLANKERID
		return getChild("blankerid", NXdeflector.class);
	}

	@Override
	public void setBlankerid(NXdeflector blankeridGroup) {
		putChild("blankerid", blankeridGroup);
	}

	@Override
	public NXmonochromator getMonochromator() {
		// dataNodeName = NX_MONOCHROMATOR
		return getChild("monochromator", NXmonochromator.class);
	}

	@Override
	public void setMonochromator(NXmonochromator monochromatorGroup) {
		putChild("monochromator", monochromatorGroup);
	}

	@Override
	public NXmonochromator getMonochromator(String name) {
		return getChild(name, NXmonochromator.class);
	}

	@Override
	public void setMonochromator(String name, NXmonochromator monochromator) {
		putChild(name, monochromator);
	}

	@Override
	public Map<String, NXmonochromator> getAllMonochromator() {
		return getChildren(NXmonochromator.class);
	}

	@Override
	public void setAllMonochromator(Map<String, NXmonochromator> monochromator) {
		setChildren(monochromator);
	}
	// Unprocessed group:

	@Override
	public NXcorrector_cs getCorrector_cs() {
		// dataNodeName = NX_CORRECTOR_CS
		return getChild("corrector_cs", NXcorrector_cs.class);
	}

	@Override
	public void setCorrector_cs(NXcorrector_cs corrector_csGroup) {
		putChild("corrector_cs", corrector_csGroup);
	}

	@Override
	public NXcorrector_cs getCorrector_cs(String name) {
		return getChild(name, NXcorrector_cs.class);
	}

	@Override
	public void setCorrector_cs(String name, NXcorrector_cs corrector_cs) {
		putChild(name, corrector_cs);
	}

	@Override
	public Map<String, NXcorrector_cs> getAllCorrector_cs() {
		return getChildren(NXcorrector_cs.class);
	}

	@Override
	public void setAllCorrector_cs(Map<String, NXcorrector_cs> corrector_cs) {
		setChildren(corrector_cs);
	}

	@Override
	public NXcomponent getCorrector_ax() {
		// dataNodeName = NX_CORRECTOR_AX
		return getChild("corrector_ax", NXcomponent.class);
	}

	@Override
	public void setCorrector_ax(NXcomponent corrector_axGroup) {
		putChild("corrector_ax", corrector_axGroup);
	}

	@Override
	public NXcomponent getBiprismid() {
		// dataNodeName = NX_BIPRISMID
		return getChild("biprismid", NXcomponent.class);
	}

	@Override
	public void setBiprismid(NXcomponent biprismidGroup) {
		putChild("biprismid", biprismidGroup);
	}

	@Override
	public NXcomponent getPhaseplateid() {
		// dataNodeName = NX_PHASEPLATEID
		return getChild("phaseplateid", NXcomponent.class);
	}

	@Override
	public void setPhaseplateid(NXcomponent phaseplateidGroup) {
		putChild("phaseplateid", phaseplateidGroup);
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

	@Override
	public NXbeam getBeam() {
		// dataNodeName = NX_BEAM
		return getChild("beam", NXbeam.class);
	}

	@Override
	public void setBeam(NXbeam beamGroup) {
		putChild("beam", beamGroup);
	}

	@Override
	public NXbeam getBeam(String name) {
		return getChild(name, NXbeam.class);
	}

	@Override
	public void setBeam(String name, NXbeam beam) {
		putChild(name, beam);
	}

	@Override
	public Map<String, NXbeam> getAllBeam() {
		return getChildren(NXbeam.class);
	}

	@Override
	public void setAllBeam(Map<String, NXbeam> beam) {
		setChildren(beam);
	}

	@Override
	public NXcomponent getComponent() {
		// dataNodeName = NX_COMPONENT
		return getChild("component", NXcomponent.class);
	}

	@Override
	public void setComponent(NXcomponent componentGroup) {
		putChild("component", componentGroup);
	}

	@Override
	public NXcomponent getComponent(String name) {
		return getChild(name, NXcomponent.class);
	}

	@Override
	public void setComponent(String name, NXcomponent component) {
		putChild(name, component);
	}

	@Override
	public Map<String, NXcomponent> getAllComponent() {
		return getChildren(NXcomponent.class);
	}

	@Override
	public void setAllComponent(Map<String, NXcomponent> component) {
		setChildren(component);
	}

	@Override
	public NXscan_controller getScan_controller() {
		// dataNodeName = NX_SCAN_CONTROLLER
		return getChild("scan_controller", NXscan_controller.class);
	}

	@Override
	public void setScan_controller(NXscan_controller scan_controllerGroup) {
		putChild("scan_controller", scan_controllerGroup);
	}

}

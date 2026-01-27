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
 * Basic class for describing an electron analyzer.
 * This concept is related to term `12.59`_ of the ISO 18115-1:2023 standard.
 * .. _12.59: https://www.iso.org/obp/ui/en/#iso:std:iso:18115:-1:ed-3:v1:en:term:12.59

 */
public class NXelectronanalyzerImpl extends NXcomponentImpl implements NXelectronanalyzer {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_RESOLUTION,
		NexusBaseClass.NX_RESOLUTION,
		NexusBaseClass.NX_RESOLUTION,
		NexusBaseClass.NX_RESOLUTION,
		NexusBaseClass.NX_DATA,
		NexusBaseClass.NX_COLLECTIONCOLUMN,
		NexusBaseClass.NX_ENERGYDISPERSION,
		NexusBaseClass.NX_SPINDISPERSION,
		NexusBaseClass.NX_DETECTOR,
		NexusBaseClass.NX_DEFLECTOR,
		NexusBaseClass.NX_ELECTROMAGNETIC_LENS,
		NexusBaseClass.NX_FABRICATION,
		NexusBaseClass.NX_RESOLUTION);

	public NXelectronanalyzerImpl() {
		super();
	}

	public NXelectronanalyzerImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXelectronanalyzer.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_ELECTRONANALYZER;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getDescription() {
		return getDataset(NX_DESCRIPTION);
	}

	@Override
	public String getDescriptionScalar() {
		return getString(NX_DESCRIPTION);
	}

	@Override
	public DataNode setDescription(IDataset descriptionDataset) {
		return setDataset(NX_DESCRIPTION, descriptionDataset);
	}

	@Override
	public DataNode setDescriptionScalar(String descriptionValue) {
		return setString(NX_DESCRIPTION, descriptionValue);
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
	public String getNameAttributeShort_name() {
		return getAttrString(NX_NAME, NX_NAME_ATTRIBUTE_SHORT_NAME);
	}

	@Override
	public void setNameAttributeShort_name(String short_nameValue) {
		setAttribute(NX_NAME, NX_NAME_ATTRIBUTE_SHORT_NAME, short_nameValue);
	}

	@Override
	public Dataset getWork_function() {
		return getDataset(NX_WORK_FUNCTION);
	}

	@Override
	public Double getWork_functionScalar() {
		return getDouble(NX_WORK_FUNCTION);
	}

	@Override
	public DataNode setWork_function(IDataset work_functionDataset) {
		return setDataset(NX_WORK_FUNCTION, work_functionDataset);
	}

	@Override
	public DataNode setWork_functionScalar(Double work_functionValue) {
		return setField(NX_WORK_FUNCTION, work_functionValue);
	}

	@Override
	public Dataset getVoltage_range() {
		return getDataset(NX_VOLTAGE_RANGE);
	}

	@Override
	public Double getVoltage_rangeScalar() {
		return getDouble(NX_VOLTAGE_RANGE);
	}

	@Override
	public DataNode setVoltage_range(IDataset voltage_rangeDataset) {
		return setDataset(NX_VOLTAGE_RANGE, voltage_rangeDataset);
	}

	@Override
	public DataNode setVoltage_rangeScalar(Double voltage_rangeValue) {
		return setField(NX_VOLTAGE_RANGE, voltage_rangeValue);
	}

	@Override
	public NXresolution getEnergy_resolution() {
		// dataNodeName = NX_ENERGY_RESOLUTION
		return getChild("energy_resolution", NXresolution.class);
	}

	@Override
	public void setEnergy_resolution(NXresolution energy_resolutionGroup) {
		putChild("energy_resolution", energy_resolutionGroup);
	}

	@Override
	public NXresolution getMomentum_resolution() {
		// dataNodeName = NX_MOMENTUM_RESOLUTION
		return getChild("momentum_resolution", NXresolution.class);
	}

	@Override
	public void setMomentum_resolution(NXresolution momentum_resolutionGroup) {
		putChild("momentum_resolution", momentum_resolutionGroup);
	}

	@Override
	public NXresolution getAngular_resolution() {
		// dataNodeName = NX_ANGULAR_RESOLUTION
		return getChild("angular_resolution", NXresolution.class);
	}

	@Override
	public void setAngular_resolution(NXresolution angular_resolutionGroup) {
		putChild("angular_resolution", angular_resolutionGroup);
	}

	@Override
	public NXresolution getSpatial_resolution() {
		// dataNodeName = NX_SPATIAL_RESOLUTION
		return getChild("spatial_resolution", NXresolution.class);
	}

	@Override
	public void setSpatial_resolution(NXresolution spatial_resolutionGroup) {
		putChild("spatial_resolution", spatial_resolutionGroup);
	}

	@Override
	public Dataset getFast_axes() {
		return getDataset(NX_FAST_AXES);
	}

	@Override
	public String getFast_axesScalar() {
		return getString(NX_FAST_AXES);
	}

	@Override
	public DataNode setFast_axes(IDataset fast_axesDataset) {
		return setDataset(NX_FAST_AXES, fast_axesDataset);
	}

	@Override
	public DataNode setFast_axesScalar(String fast_axesValue) {
		return setString(NX_FAST_AXES, fast_axesValue);
	}

	@Override
	public Dataset getSlow_axes() {
		return getDataset(NX_SLOW_AXES);
	}

	@Override
	public String getSlow_axesScalar() {
		return getString(NX_SLOW_AXES);
	}

	@Override
	public DataNode setSlow_axes(IDataset slow_axesDataset) {
		return setDataset(NX_SLOW_AXES, slow_axesDataset);
	}

	@Override
	public DataNode setSlow_axesScalar(String slow_axesValue) {
		return setString(NX_SLOW_AXES, slow_axesValue);
	}

	@Override
	public NXdata getTransmission_function() {
		// dataNodeName = NX_TRANSMISSION_FUNCTION
		return getChild("transmission_function", NXdata.class);
	}

	@Override
	public void setTransmission_function(NXdata transmission_functionGroup) {
		putChild("transmission_function", transmission_functionGroup);
	}

	@Override
	public NXcollectioncolumn getCollectioncolumn() {
		// dataNodeName = NX_COLLECTIONCOLUMN
		return getChild("collectioncolumn", NXcollectioncolumn.class);
	}

	@Override
	public void setCollectioncolumn(NXcollectioncolumn collectioncolumnGroup) {
		putChild("collectioncolumn", collectioncolumnGroup);
	}

	@Override
	public NXcollectioncolumn getCollectioncolumn(String name) {
		return getChild(name, NXcollectioncolumn.class);
	}

	@Override
	public void setCollectioncolumn(String name, NXcollectioncolumn collectioncolumn) {
		putChild(name, collectioncolumn);
	}

	@Override
	public Map<String, NXcollectioncolumn> getAllCollectioncolumn() {
		return getChildren(NXcollectioncolumn.class);
	}

	@Override
	public void setAllCollectioncolumn(Map<String, NXcollectioncolumn> collectioncolumn) {
		setChildren(collectioncolumn);
	}

	@Override
	public NXenergydispersion getEnergydispersion() {
		// dataNodeName = NX_ENERGYDISPERSION
		return getChild("energydispersion", NXenergydispersion.class);
	}

	@Override
	public void setEnergydispersion(NXenergydispersion energydispersionGroup) {
		putChild("energydispersion", energydispersionGroup);
	}

	@Override
	public NXenergydispersion getEnergydispersion(String name) {
		return getChild(name, NXenergydispersion.class);
	}

	@Override
	public void setEnergydispersion(String name, NXenergydispersion energydispersion) {
		putChild(name, energydispersion);
	}

	@Override
	public Map<String, NXenergydispersion> getAllEnergydispersion() {
		return getChildren(NXenergydispersion.class);
	}

	@Override
	public void setAllEnergydispersion(Map<String, NXenergydispersion> energydispersion) {
		setChildren(energydispersion);
	}

	@Override
	public NXspindispersion getSpindispersion() {
		// dataNodeName = NX_SPINDISPERSION
		return getChild("spindispersion", NXspindispersion.class);
	}

	@Override
	public void setSpindispersion(NXspindispersion spindispersionGroup) {
		putChild("spindispersion", spindispersionGroup);
	}

	@Override
	public NXspindispersion getSpindispersion(String name) {
		return getChild(name, NXspindispersion.class);
	}

	@Override
	public void setSpindispersion(String name, NXspindispersion spindispersion) {
		putChild(name, spindispersion);
	}

	@Override
	public Map<String, NXspindispersion> getAllSpindispersion() {
		return getChildren(NXspindispersion.class);
	}

	@Override
	public void setAllSpindispersion(Map<String, NXspindispersion> spindispersion) {
		setChildren(spindispersion);
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
	public NXresolution getResolution() {
		// dataNodeName = NX_RESOLUTION
		return getChild("resolution", NXresolution.class);
	}

	@Override
	public void setResolution(NXresolution resolutionGroup) {
		putChild("resolution", resolutionGroup);
	}

	@Override
	public NXresolution getResolution(String name) {
		return getChild(name, NXresolution.class);
	}

	@Override
	public void setResolution(String name, NXresolution resolution) {
		putChild(name, resolution);
	}

	@Override
	public Map<String, NXresolution> getAllResolution() {
		return getChildren(NXresolution.class);
	}

	@Override
	public void setAllResolution(Map<String, NXresolution> resolution) {
		setChildren(resolution);
	}

}

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
 * Subclass of NXinstrument to describe a photoelectron analyser.

 */
public class NXelectronanalyserImpl extends NXobjectImpl implements NXelectronanalyser {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_TRANSFORMATIONS,
		NexusBaseClass.NX_COLLECTIONCOLUMN,
		NexusBaseClass.NX_ENERGYDISPERSION,
		NexusBaseClass.NX_SPINDISPERSION,
		NexusBaseClass.NX_DETECTOR,
		NexusBaseClass.NX_DEFLECTOR,
		NexusBaseClass.NX_LENS_EM);

	public NXelectronanalyserImpl() {
		super();
	}

	public NXelectronanalyserImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXelectronanalyser.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_ELECTRONANALYSER;
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
	public Dataset getEnergy_resolution() {
		return getDataset(NX_ENERGY_RESOLUTION);
	}

	@Override
	public Double getEnergy_resolutionScalar() {
		return getDouble(NX_ENERGY_RESOLUTION);
	}

	@Override
	public DataNode setEnergy_resolution(IDataset energy_resolutionDataset) {
		return setDataset(NX_ENERGY_RESOLUTION, energy_resolutionDataset);
	}

	@Override
	public DataNode setEnergy_resolutionScalar(Double energy_resolutionValue) {
		return setField(NX_ENERGY_RESOLUTION, energy_resolutionValue);
	}

	@Override
	public Dataset getMomentum_resolution() {
		return getDataset(NX_MOMENTUM_RESOLUTION);
	}

	@Override
	public Double getMomentum_resolutionScalar() {
		return getDouble(NX_MOMENTUM_RESOLUTION);
	}

	@Override
	public DataNode setMomentum_resolution(IDataset momentum_resolutionDataset) {
		return setDataset(NX_MOMENTUM_RESOLUTION, momentum_resolutionDataset);
	}

	@Override
	public DataNode setMomentum_resolutionScalar(Double momentum_resolutionValue) {
		return setField(NX_MOMENTUM_RESOLUTION, momentum_resolutionValue);
	}

	@Override
	public Dataset getAngular_resolution() {
		return getDataset(NX_ANGULAR_RESOLUTION);
	}

	@Override
	public Double getAngular_resolutionScalar() {
		return getDouble(NX_ANGULAR_RESOLUTION);
	}

	@Override
	public DataNode setAngular_resolution(IDataset angular_resolutionDataset) {
		return setDataset(NX_ANGULAR_RESOLUTION, angular_resolutionDataset);
	}

	@Override
	public DataNode setAngular_resolutionScalar(Double angular_resolutionValue) {
		return setField(NX_ANGULAR_RESOLUTION, angular_resolutionValue);
	}

	@Override
	public Dataset getSpatial_resolution() {
		return getDataset(NX_SPATIAL_RESOLUTION);
	}

	@Override
	public Double getSpatial_resolutionScalar() {
		return getDouble(NX_SPATIAL_RESOLUTION);
	}

	@Override
	public DataNode setSpatial_resolution(IDataset spatial_resolutionDataset) {
		return setDataset(NX_SPATIAL_RESOLUTION, spatial_resolutionDataset);
	}

	@Override
	public DataNode setSpatial_resolutionScalar(Double spatial_resolutionValue) {
		return setField(NX_SPATIAL_RESOLUTION, spatial_resolutionValue);
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
	public Dataset getDepends_on() {
		return getDataset(NX_DEPENDS_ON);
	}

	@Override
	public String getDepends_onScalar() {
		return getString(NX_DEPENDS_ON);
	}

	@Override
	public DataNode setDepends_on(IDataset depends_onDataset) {
		return setDataset(NX_DEPENDS_ON, depends_onDataset);
	}

	@Override
	public DataNode setDepends_onScalar(String depends_onValue) {
		return setString(NX_DEPENDS_ON, depends_onValue);
	}

	@Override
	public NXtransformations getTransformations() {
		// dataNodeName = NX_TRANSFORMATIONS
		return getChild("transformations", NXtransformations.class);
	}

	@Override
	public void setTransformations(NXtransformations transformationsGroup) {
		putChild("transformations", transformationsGroup);
	}

	@Override
	public NXtransformations getTransformations(String name) {
		return getChild(name, NXtransformations.class);
	}

	@Override
	public void setTransformations(String name, NXtransformations transformations) {
		putChild(name, transformations);
	}

	@Override
	public Map<String, NXtransformations> getAllTransformations() {
		return getChildren(NXtransformations.class);
	}

	@Override
	public void setAllTransformations(Map<String, NXtransformations> transformations) {
		setChildren(transformations);
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
	public NXlens_em getLens_em() {
		// dataNodeName = NX_LENS_EM
		return getChild("lens_em", NXlens_em.class);
	}

	@Override
	public void setLens_em(NXlens_em lens_emGroup) {
		putChild("lens_em", lens_emGroup);
	}

	@Override
	public NXlens_em getLens_em(String name) {
		return getChild(name, NXlens_em.class);
	}

	@Override
	public void setLens_em(String name, NXlens_em lens_em) {
		putChild(name, lens_em);
	}

	@Override
	public Map<String, NXlens_em> getAllLens_em() {
		return getChildren(NXlens_em.class);
	}

	@Override
	public void setAllLens_em(Map<String, NXlens_em> lens_em) {
		setChildren(lens_em);
	}

}

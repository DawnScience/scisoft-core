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

import org.eclipse.dawnsci.nexus.*;

/**
 * Container for components to form a controlled electron beam.
 * 
 */
public class NXebeam_columnImpl extends NXobjectImpl implements NXebeam_column {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_MANUFACTURER,
		NexusBaseClass.NX_SOURCE,
		NexusBaseClass.NX_APERTURE_EM,
		NexusBaseClass.NX_LENS_EM,
		NexusBaseClass.NX_CORRECTOR_CS,
		NexusBaseClass.NX_STAGE_LAB,
		NexusBaseClass.NX_SENSOR,
		NexusBaseClass.NX_BEAM);

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
	public NXmanufacturer getManufacturer() {
		// dataNodeName = NX_MANUFACTURER
		return getChild("manufacturer", NXmanufacturer.class);
	}

	@Override
	public void setManufacturer(NXmanufacturer manufacturerGroup) {
		putChild("manufacturer", manufacturerGroup);
	}

	@Override
	public NXmanufacturer getManufacturer(String name) {
		return getChild(name, NXmanufacturer.class);
	}

	@Override
	public void setManufacturer(String name, NXmanufacturer manufacturer) {
		putChild(name, manufacturer);
	}

	@Override
	public Map<String, NXmanufacturer> getAllManufacturer() {
		return getChildren(NXmanufacturer.class);
	}
	
	@Override
	public void setAllManufacturer(Map<String, NXmanufacturer> manufacturer) {
		setChildren(manufacturer);
	}

	@Override
	public NXsource getElectron_gun() {
		// dataNodeName = NX_ELECTRON_GUN
		return getChild("electron_gun", NXsource.class);
	}

	@Override
	public void setElectron_gun(NXsource electron_gunGroup) {
		putChild("electron_gun", electron_gunGroup);
	}
	// Unprocessed group: 
	// Unprocessed group: 

	@Override
	public NXaperture_em getAperture_em() {
		// dataNodeName = NX_APERTURE_EM
		return getChild("aperture_em", NXaperture_em.class);
	}

	@Override
	public void setAperture_em(NXaperture_em aperture_emGroup) {
		putChild("aperture_em", aperture_emGroup);
	}

	@Override
	public NXaperture_em getAperture_em(String name) {
		return getChild(name, NXaperture_em.class);
	}

	@Override
	public void setAperture_em(String name, NXaperture_em aperture_em) {
		putChild(name, aperture_em);
	}

	@Override
	public Map<String, NXaperture_em> getAllAperture_em() {
		return getChildren(NXaperture_em.class);
	}
	
	@Override
	public void setAllAperture_em(Map<String, NXaperture_em> aperture_em) {
		setChildren(aperture_em);
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
	public NXstage_lab getStage_lab() {
		// dataNodeName = NX_STAGE_LAB
		return getChild("stage_lab", NXstage_lab.class);
	}

	@Override
	public void setStage_lab(NXstage_lab stage_labGroup) {
		putChild("stage_lab", stage_labGroup);
	}

	@Override
	public NXstage_lab getStage_lab(String name) {
		return getChild(name, NXstage_lab.class);
	}

	@Override
	public void setStage_lab(String name, NXstage_lab stage_lab) {
		putChild(name, stage_lab);
	}

	@Override
	public Map<String, NXstage_lab> getAllStage_lab() {
		return getChildren(NXstage_lab.class);
	}
	
	@Override
	public void setAllStage_lab(Map<String, NXstage_lab> stage_lab) {
		setChildren(stage_lab);
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

}

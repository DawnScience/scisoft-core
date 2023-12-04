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
 * Container for components of a focused-ion-beam (FIB) system.
 * FIB capabilities turn especially scanning electron microscopes
 * into specimen preparation labs. FIB is a material preparation
 * technique whereby portions of the sample are illuminated with a
 * focused ion beam with controlled intensity intense enough and with
 * sufficient ion momentum to remove material in a controllable manner.
 * The fact that an electron microscope with FIB capabilities has needs a
 * second gun with own relevant control circuits, focusing lenses, and
 * other components, warrants an own base class to group these components
 * and distinguish them from the lenses and components for creating and
 * shaping the electron beam.
 * For more details about the relevant physics and application examples
 * consult the literature, for example:
 * * `L. A. Giannuzzi et al. <https://doi.org/10.1007/b101190>`_
 * * `E. I. Preiß et al. <https://link.springer.com/content/pdf/10.1557/s43578-020-00045-w.pdf>`_
 * * `J. F. Ziegler et al. <https://www.sciencedirect.com/science/article/pii/S0168583X10001862>`_
 * * `J. Lili <https://www.osti.gov/servlets/purl/924801>`_
 * 
 */
public class NXibeam_columnImpl extends NXobjectImpl implements NXibeam_column {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_MANUFACTURER,
		NexusBaseClass.NX_SOURCE,
		NexusBaseClass.NX_APERTURE_EM,
		NexusBaseClass.NX_LENS_EM,
		NexusBaseClass.NX_SENSOR,
		NexusBaseClass.NX_BEAM);

	public NXibeam_columnImpl() {
		super();
	}

	public NXibeam_columnImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXibeam_column.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_IBEAM_COLUMN;
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
	public NXsource getIon_gun() {
		// dataNodeName = NX_ION_GUN
		return getChild("ion_gun", NXsource.class);
	}

	@Override
	public void setIon_gun(NXsource ion_gunGroup) {
		putChild("ion_gun", ion_gunGroup);
	}
	// Unprocessed group:  probe
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

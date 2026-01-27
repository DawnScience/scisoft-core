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
 * Base class for a corrector reducing (spherical) aberrations of an electron optical setup.
 * Different technology partners use different conventions and
 * models for quantifying the aberration coefficients.
 * Aberration correction components are especially important for (scanning)
 * transmission electron microscopy. Composed of multiple lenses and multipole stigmators,
 * their technical details are specific for the technology partner as well as
 * the microscope and instrument. Most technical details are proprietary knowledge.
 * If one component corrects for multiple types of aberrations (like it is the case
 * reported here `CEOS <https://www.ceos-gmbh.de/en/research/electrostat>`_) follow this
 * design when using corrector and monochromator in an application definition:
 * * Use :ref:`NXcorrector_cs` for spherical aberration
 * * Use :ref:`NXmonochromator` for energy filtering or chromatic aberration
 * * Use the group corrector_ax in :ref:`NXem` for axial astigmatism aberration
 * Although this base class currently provides concepts that are foremost used in
 * the field of electron microscopy using this base class is not restricted to this
 * research field. NXcorrector_cs can also serve as a container to detail, in
 * combination with :ref:`NXaberration`, about measured aberrations in classical optics.
 * In optics, though, the difference is that the design of the :ref:NXoptical_lens`
 * itself (e.g., using aspheric lenses or combinations of lenses) enables to
 * reduce spherical aberrations.

 */
public class NXcorrector_csImpl extends NXcomponentImpl implements NXcorrector_cs {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_PROCESS,
		NexusBaseClass.NX_ELECTROMAGNETIC_LENS,
		NexusBaseClass.NX_OPTICAL_LENS,
		NexusBaseClass.NX_APERTURE,
		NexusBaseClass.NX_DEFLECTOR);

	public NXcorrector_csImpl() {
		super();
	}

	public NXcorrector_csImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcorrector_cs.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CORRECTOR_CS;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getApplied() {
		return getDataset(NX_APPLIED);
	}

	@Override
	public Boolean getAppliedScalar() {
		return getBoolean(NX_APPLIED);
	}

	@Override
	public DataNode setApplied(IDataset appliedDataset) {
		return setDataset(NX_APPLIED, appliedDataset);
	}

	@Override
	public DataNode setAppliedScalar(Boolean appliedValue) {
		return setField(NX_APPLIED, appliedValue);
	}

	@Override
	public NXprocess getTableauid() {
		// dataNodeName = NX_TABLEAUID
		return getChild("tableauid", NXprocess.class);
	}

	@Override
	public void setTableauid(NXprocess tableauidGroup) {
		putChild("tableauid", tableauidGroup);
	}
	// Unprocessed group: imageID
	// Unprocessed group: c_1
	// Unprocessed group: a_1
	// Unprocessed group: b_2
	// Unprocessed group: a_2
	// Unprocessed group: c_3
	// Unprocessed group: s_3
	// Unprocessed group: a_3
	// Unprocessed group: b_4
	// Unprocessed group: d_4
	// Unprocessed group: a_4
	// Unprocessed group: c_5
	// Unprocessed group: s_5
	// Unprocessed group: r_5
	// Unprocessed group: a_6
	// Unprocessed group: c_1_0
	// Unprocessed group: c_1_2_a
	// Unprocessed group: c_1_2_b
	// Unprocessed group: c_2_1_a
	// Unprocessed group: c_2_1_b
	// Unprocessed group: c_2_3_a
	// Unprocessed group: c_2_3_b
	// Unprocessed group: c_3_0
	// Unprocessed group: c_3_2_a
	// Unprocessed group: c_3_2_b
	// Unprocessed group: c_3_4_a
	// Unprocessed group: c_3_4_b
	// Unprocessed group: c_4_1_a
	// Unprocessed group: c_4_1_b
	// Unprocessed group: c_4_3_a
	// Unprocessed group: c_4_3_b
	// Unprocessed group: c_4_5_a
	// Unprocessed group: c_4_5_b
	// Unprocessed group: c_5_0
	// Unprocessed group: c_5_2_a
	// Unprocessed group: c_5_2_b
	// Unprocessed group: c_5_4_a
	// Unprocessed group: c_5_4_b
	// Unprocessed group: c_5_6_a
	// Unprocessed group: c_5_6_b

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
	public NXoptical_lens getOptical_lens() {
		// dataNodeName = NX_OPTICAL_LENS
		return getChild("optical_lens", NXoptical_lens.class);
	}

	@Override
	public void setOptical_lens(NXoptical_lens optical_lensGroup) {
		putChild("optical_lens", optical_lensGroup);
	}

	@Override
	public NXoptical_lens getOptical_lens(String name) {
		return getChild(name, NXoptical_lens.class);
	}

	@Override
	public void setOptical_lens(String name, NXoptical_lens optical_lens) {
		putChild(name, optical_lens);
	}

	@Override
	public Map<String, NXoptical_lens> getAllOptical_lens() {
		return getChildren(NXoptical_lens.class);
	}

	@Override
	public void setAllOptical_lens(Map<String, NXoptical_lens> optical_lens) {
		setChildren(optical_lens);
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

}

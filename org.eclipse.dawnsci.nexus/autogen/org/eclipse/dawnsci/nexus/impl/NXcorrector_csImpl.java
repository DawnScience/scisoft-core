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
 * Corrector for aberrations in an electron microscope.
 * Different vendors use a different naming schemes for aberration coefficients.
 * It is the users responsibility to map to the best matching values.
 * In transmission electron microscopes the spherical aberration corrector is
 * a component that is controlled via instructing the microscope to achieve
 * set point values. The corrector is composed of multiple lenses and other
 * parts with vendor-specific details which are often undisclosed.
 * In the case of Nion Co. microscopes the coefficients of the corrector can be
 * retrieved via NionSwift, which is why currently the Nion convention for the
 * aberration coefficients is used.
 * 
 */
public class NXcorrector_csImpl extends NXobjectImpl implements NXcorrector_cs {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_MANUFACTURER,
		NexusBaseClass.NX_ABERRATION,
		NexusBaseClass.NX_LENS_EM,
		NexusBaseClass.NX_TRANSFORMATIONS);

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
	public IDataset getApplied() {
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
	public IDataset getName() {
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
	public IDataset getDescription() {
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
	public NXaberration getAberration() {
		// dataNodeName = NX_ABERRATION
		return getChild("aberration", NXaberration.class);
	}

	@Override
	public void setAberration(NXaberration aberrationGroup) {
		putChild("aberration", aberrationGroup);
	}

	@Override
	public NXaberration getAberration(String name) {
		return getChild(name, NXaberration.class);
	}

	@Override
	public void setAberration(String name, NXaberration aberration) {
		putChild(name, aberration);
	}

	@Override
	public Map<String, NXaberration> getAllAberration() {
		return getChildren(NXaberration.class);
	}
	
	@Override
	public void setAllAberration(Map<String, NXaberration> aberration) {
		setChildren(aberration);
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

}

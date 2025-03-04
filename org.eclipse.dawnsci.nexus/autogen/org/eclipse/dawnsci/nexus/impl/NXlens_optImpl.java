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
import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Dataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * Description of an optical lens.

 */
public class NXlens_optImpl extends NXobjectImpl implements NXlens_opt {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_SAMPLE,
		NexusBaseClass.NX_SAMPLE);

	public NXlens_optImpl() {
		super();
	}

	public NXlens_optImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXlens_opt.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_LENS_OPT;
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
	public Dataset getOther_type() {
		return getDataset(NX_OTHER_TYPE);
	}

	@Override
	public String getOther_typeScalar() {
		return getString(NX_OTHER_TYPE);
	}

	@Override
	public DataNode setOther_type(IDataset other_typeDataset) {
		return setDataset(NX_OTHER_TYPE, other_typeDataset);
	}

	@Override
	public DataNode setOther_typeScalar(String other_typeValue) {
		return setString(NX_OTHER_TYPE, other_typeValue);
	}

	@Override
	public Dataset getChromatic() {
		return getDataset(NX_CHROMATIC);
	}

	@Override
	public Boolean getChromaticScalar() {
		return getBoolean(NX_CHROMATIC);
	}

	@Override
	public DataNode setChromatic(IDataset chromaticDataset) {
		return setDataset(NX_CHROMATIC, chromaticDataset);
	}

	@Override
	public DataNode setChromaticScalar(Boolean chromaticValue) {
		return setField(NX_CHROMATIC, chromaticValue);
	}

	@Override
	public Dataset getLens_diameter() {
		return getDataset(NX_LENS_DIAMETER);
	}

	@Override
	public Number getLens_diameterScalar() {
		return getNumber(NX_LENS_DIAMETER);
	}

	@Override
	public DataNode setLens_diameter(IDataset lens_diameterDataset) {
		return setDataset(NX_LENS_DIAMETER, lens_diameterDataset);
	}

	@Override
	public DataNode setLens_diameterScalar(Number lens_diameterValue) {
		return setField(NX_LENS_DIAMETER, lens_diameterValue);
	}

	@Override
	public NXsample getSubstrate() {
		// dataNodeName = NX_SUBSTRATE
		return getChild("substrate", NXsample.class);
	}

	@Override
	public void setSubstrate(NXsample substrateGroup) {
		putChild("substrate", substrateGroup);
	}

	@Override
	public NXsample getCoating() {
		// dataNodeName = NX_COATING
		return getChild("coating", NXsample.class);
	}

	@Override
	public void setCoating(NXsample coatingGroup) {
		putChild("coating", coatingGroup);
	}

	@Override
	public Dataset getReflectance() {
		return getDataset(NX_REFLECTANCE);
	}

	@Override
	public String getReflectanceScalar() {
		return getString(NX_REFLECTANCE);
	}

	@Override
	public DataNode setReflectance(IDataset reflectanceDataset) {
		return setDataset(NX_REFLECTANCE, reflectanceDataset);
	}

	@Override
	public DataNode setReflectanceScalar(String reflectanceValue) {
		return setString(NX_REFLECTANCE, reflectanceValue);
	}

	@Override
	public Dataset getTransmission() {
		return getDataset(NX_TRANSMISSION);
	}

	@Override
	public String getTransmissionScalar() {
		return getString(NX_TRANSMISSION);
	}

	@Override
	public DataNode setTransmission(IDataset transmissionDataset) {
		return setDataset(NX_TRANSMISSION, transmissionDataset);
	}

	@Override
	public DataNode setTransmissionScalar(String transmissionValue) {
		return setString(NX_TRANSMISSION, transmissionValue);
	}

	@Override
	public Dataset getFocal_length() {
		return getDataset(NX_FOCAL_LENGTH);
	}

	@Override
	public Number getFocal_lengthScalar() {
		return getNumber(NX_FOCAL_LENGTH);
	}

	@Override
	public DataNode setFocal_length(IDataset focal_lengthDataset) {
		return setDataset(NX_FOCAL_LENGTH, focal_lengthDataset);
	}

	@Override
	public DataNode setFocal_lengthScalar(Number focal_lengthValue) {
		return setField(NX_FOCAL_LENGTH, focal_lengthValue);
	}

	@Override
	public Dataset getCurvature_radius_face() {
		return getDataset(NX_CURVATURE_RADIUS_FACE);
	}

	@Override
	public Number getCurvature_radius_faceScalar() {
		return getNumber(NX_CURVATURE_RADIUS_FACE);
	}

	@Override
	public DataNode setCurvature_radius_face(IDataset curvature_radius_faceDataset) {
		return setDataset(NX_CURVATURE_RADIUS_FACE, curvature_radius_faceDataset);
	}

	@Override
	public DataNode setCurvature_radius_faceScalar(Number curvature_radius_faceValue) {
		return setField(NX_CURVATURE_RADIUS_FACE, curvature_radius_faceValue);
	}

	@Override
	public Dataset getAbbe_number() {
		return getDataset(NX_ABBE_NUMBER);
	}

	@Override
	public Number getAbbe_numberScalar() {
		return getNumber(NX_ABBE_NUMBER);
	}

	@Override
	public DataNode setAbbe_number(IDataset abbe_numberDataset) {
		return setDataset(NX_ABBE_NUMBER, abbe_numberDataset);
	}

	@Override
	public DataNode setAbbe_numberScalar(Number abbe_numberValue) {
		return setField(NX_ABBE_NUMBER, abbe_numberValue);
	}

}

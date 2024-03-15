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

import org.eclipse.dawnsci.nexus.*;

/**
 * Subclass of NXprocess to describe post-processing distortion correction.

 */
public class NXdistortionImpl extends NXobjectImpl implements NXdistortion {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXdistortionImpl() {
		super();
	}

	public NXdistortionImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXdistortion.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_DISTORTION;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public IDataset getLast_process() {
		return getDataset(NX_LAST_PROCESS);
	}

	@Override
	public String getLast_processScalar() {
		return getString(NX_LAST_PROCESS);
	}

	@Override
	public DataNode setLast_process(IDataset last_processDataset) {
		return setDataset(NX_LAST_PROCESS, last_processDataset);
	}

	@Override
	public DataNode setLast_processScalar(String last_processValue) {
		return setString(NX_LAST_PROCESS, last_processValue);
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
	public IDataset getSymmetry() {
		return getDataset(NX_SYMMETRY);
	}

	@Override
	public Long getSymmetryScalar() {
		return getLong(NX_SYMMETRY);
	}

	@Override
	public DataNode setSymmetry(IDataset symmetryDataset) {
		return setDataset(NX_SYMMETRY, symmetryDataset);
	}

	@Override
	public DataNode setSymmetryScalar(Long symmetryValue) {
		return setField(NX_SYMMETRY, symmetryValue);
	}

	@Override
	public IDataset getOriginal_centre() {
		return getDataset(NX_ORIGINAL_CENTRE);
	}

	@Override
	public Double getOriginal_centreScalar() {
		return getDouble(NX_ORIGINAL_CENTRE);
	}

	@Override
	public DataNode setOriginal_centre(IDataset original_centreDataset) {
		return setDataset(NX_ORIGINAL_CENTRE, original_centreDataset);
	}

	@Override
	public DataNode setOriginal_centreScalar(Double original_centreValue) {
		return setField(NX_ORIGINAL_CENTRE, original_centreValue);
	}

	@Override
	public IDataset getOriginal_points() {
		return getDataset(NX_ORIGINAL_POINTS);
	}

	@Override
	public Double getOriginal_pointsScalar() {
		return getDouble(NX_ORIGINAL_POINTS);
	}

	@Override
	public DataNode setOriginal_points(IDataset original_pointsDataset) {
		return setDataset(NX_ORIGINAL_POINTS, original_pointsDataset);
	}

	@Override
	public DataNode setOriginal_pointsScalar(Double original_pointsValue) {
		return setField(NX_ORIGINAL_POINTS, original_pointsValue);
	}

	@Override
	public IDataset getCdeform_field() {
		return getDataset(NX_CDEFORM_FIELD);
	}

	@Override
	public Double getCdeform_fieldScalar() {
		return getDouble(NX_CDEFORM_FIELD);
	}

	@Override
	public DataNode setCdeform_field(IDataset cdeform_fieldDataset) {
		return setDataset(NX_CDEFORM_FIELD, cdeform_fieldDataset);
	}

	@Override
	public DataNode setCdeform_fieldScalar(Double cdeform_fieldValue) {
		return setField(NX_CDEFORM_FIELD, cdeform_fieldValue);
	}

	@Override
	public IDataset getRdeform_field() {
		return getDataset(NX_RDEFORM_FIELD);
	}

	@Override
	public Double getRdeform_fieldScalar() {
		return getDouble(NX_RDEFORM_FIELD);
	}

	@Override
	public DataNode setRdeform_field(IDataset rdeform_fieldDataset) {
		return setDataset(NX_RDEFORM_FIELD, rdeform_fieldDataset);
	}

	@Override
	public DataNode setRdeform_fieldScalar(Double rdeform_fieldValue) {
		return setField(NX_RDEFORM_FIELD, rdeform_fieldValue);
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

}

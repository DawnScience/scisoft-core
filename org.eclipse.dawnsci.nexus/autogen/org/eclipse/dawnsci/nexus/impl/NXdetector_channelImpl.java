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
 * Description and metadata for a single channel from a multi-channel detector.
 * Given an :ref:`NXdata` group linked as part of an NXdetector group that has an axis with named channels (see the
 * example in :ref:`NXdata </NXdata@default_slice-attribute>`), the NXdetector will have a series of NXdetector_channel groups, one for each
 * channel, named CHANNELNAME_channel.
 * Example, given these axes in the NXdata group::
 * @axes = ["image_id", "channel", ".", "."]
 * And this list of channels in the NXdata group::
 * channel = ["threshold_1", "threshold_2", "difference"]
 * The NXdetector group would have three NXdetector_channel groups::
 * detector:NXdetector
 * ...
 * threshold_1_channel:NXdetector_channel
 * threshold_energy = float
 * flatfield = float[i, j]
 * pixel_mask = uint[i, j]
 * flatfield_applied = bool
 * pixel_mask_applied = bool
 * threshold_2_channel:NXdetector_channel
 * threshold_energy = float
 * flatfield = float[i, j]
 * pixel_mask = uint[i, j]
 * flatfield_applied = bool
 * pixel_mask_applied = bool
 * difference_channel:NXdetector_channel
 * threshold_energy = float[2]

 */
public class NXdetector_channelImpl extends NXobjectImpl implements NXdetector_channel {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXdetector_channelImpl() {
		super();
	}

	public NXdetector_channelImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXdetector_channel.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_DETECTOR_CHANNEL;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getThreshold_energy() {
		return getDataset(NX_THRESHOLD_ENERGY);
	}

	@Override
	public Double getThreshold_energyScalar() {
		return getDouble(NX_THRESHOLD_ENERGY);
	}

	@Override
	public DataNode setThreshold_energy(IDataset threshold_energyDataset) {
		return setDataset(NX_THRESHOLD_ENERGY, threshold_energyDataset);
	}

	@Override
	public DataNode setThreshold_energyScalar(Double threshold_energyValue) {
		return setField(NX_THRESHOLD_ENERGY, threshold_energyValue);
	}

	@Override
	public Dataset getFlatfield_applied() {
		return getDataset(NX_FLATFIELD_APPLIED);
	}

	@Override
	public Boolean getFlatfield_appliedScalar() {
		return getBoolean(NX_FLATFIELD_APPLIED);
	}

	@Override
	public DataNode setFlatfield_applied(IDataset flatfield_appliedDataset) {
		return setDataset(NX_FLATFIELD_APPLIED, flatfield_appliedDataset);
	}

	@Override
	public DataNode setFlatfield_appliedScalar(Boolean flatfield_appliedValue) {
		return setField(NX_FLATFIELD_APPLIED, flatfield_appliedValue);
	}

	@Override
	public Dataset getFlatfield() {
		return getDataset(NX_FLATFIELD);
	}

	@Override
	public Number getFlatfieldScalar() {
		return getNumber(NX_FLATFIELD);
	}

	@Override
	public DataNode setFlatfield(IDataset flatfieldDataset) {
		return setDataset(NX_FLATFIELD, flatfieldDataset);
	}

	@Override
	public DataNode setFlatfieldScalar(Number flatfieldValue) {
		return setField(NX_FLATFIELD, flatfieldValue);
	}

	@Override
	public Dataset getFlatfield_errors() {
		return getDataset(NX_FLATFIELD_ERRORS);
	}

	@Override
	public Double getFlatfield_errorsScalar() {
		return getDouble(NX_FLATFIELD_ERRORS);
	}

	@Override
	public DataNode setFlatfield_errors(IDataset flatfield_errorsDataset) {
		return setDataset(NX_FLATFIELD_ERRORS, flatfield_errorsDataset);
	}

	@Override
	public DataNode setFlatfield_errorsScalar(Double flatfield_errorsValue) {
		return setField(NX_FLATFIELD_ERRORS, flatfield_errorsValue);
	}

	@Override
	public Dataset getPixel_mask_applied() {
		return getDataset(NX_PIXEL_MASK_APPLIED);
	}

	@Override
	public Boolean getPixel_mask_appliedScalar() {
		return getBoolean(NX_PIXEL_MASK_APPLIED);
	}

	@Override
	public DataNode setPixel_mask_applied(IDataset pixel_mask_appliedDataset) {
		return setDataset(NX_PIXEL_MASK_APPLIED, pixel_mask_appliedDataset);
	}

	@Override
	public DataNode setPixel_mask_appliedScalar(Boolean pixel_mask_appliedValue) {
		return setField(NX_PIXEL_MASK_APPLIED, pixel_mask_appliedValue);
	}

	@Override
	public Dataset getPixel_mask() {
		return getDataset(NX_PIXEL_MASK);
	}

	@Override
	public Long getPixel_maskScalar() {
		return getLong(NX_PIXEL_MASK);
	}

	@Override
	public DataNode setPixel_mask(IDataset pixel_maskDataset) {
		return setDataset(NX_PIXEL_MASK, pixel_maskDataset);
	}

	@Override
	public DataNode setPixel_maskScalar(Long pixel_maskValue) {
		return setField(NX_PIXEL_MASK, pixel_maskValue);
	}

	@Override
	public Dataset getSaturation_value() {
		return getDataset(NX_SATURATION_VALUE);
	}

	@Override
	public Number getSaturation_valueScalar() {
		return getNumber(NX_SATURATION_VALUE);
	}

	@Override
	public DataNode setSaturation_value(IDataset saturation_valueDataset) {
		return setDataset(NX_SATURATION_VALUE, saturation_valueDataset);
	}

	@Override
	public DataNode setSaturation_valueScalar(Number saturation_valueValue) {
		return setField(NX_SATURATION_VALUE, saturation_valueValue);
	}

	@Override
	public Dataset getUnderload_value() {
		return getDataset(NX_UNDERLOAD_VALUE);
	}

	@Override
	public Number getUnderload_valueScalar() {
		return getNumber(NX_UNDERLOAD_VALUE);
	}

	@Override
	public DataNode setUnderload_value(IDataset underload_valueDataset) {
		return setDataset(NX_UNDERLOAD_VALUE, underload_valueDataset);
	}

	@Override
	public DataNode setUnderload_valueScalar(Number underload_valueValue) {
		return setField(NX_UNDERLOAD_VALUE, underload_valueValue);
	}

}

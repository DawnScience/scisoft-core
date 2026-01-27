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
 * A window of a cryostat, heater, vacuum chamber or a simple glass slide.
 * This describes cryostat windows and other possible influences for ellipsometry
 * measurements.
 * For environmental measurements, the environment (liquid, vapor
 * etc.) is enclosed in a cell, which has windows both in the
 * direction of the source (entry window) and the detector (exit
 * window) (looking from the sample).
 * The windows also add a phase shift to the light altering the
 * measured signal. This shift has to be corrected based on measuring
 * a known sample (reference sample) or the actual sample of interest
 * in the environmental cell. State if a window correction has been
 * performed in 'window_effects_corrected'. Reference measurements should be
 * considered as a separate experiment (with a separate NeXus file), and
 * the reference data shall be :ref:`linked <Design-Links>` in
 * ``reference_data_link``.
 * The window is considered to be a part of the sample stage but also
 * beam path. Hence, its position within the beam path should be
 * defined by the 'depends_on' field.

 */
public class NXoptical_windowImpl extends NXapertureImpl implements NXoptical_window {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_PROCESS);

	public NXoptical_windowImpl() {
		super();
	}

	public NXoptical_windowImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXoptical_window.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_OPTICAL_WINDOW;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getWindow_effects_corrected() {
		return getDataset(NX_WINDOW_EFFECTS_CORRECTED);
	}

	@Override
	public Boolean getWindow_effects_correctedScalar() {
		return getBoolean(NX_WINDOW_EFFECTS_CORRECTED);
	}

	@Override
	public DataNode setWindow_effects_corrected(IDataset window_effects_correctedDataset) {
		return setDataset(NX_WINDOW_EFFECTS_CORRECTED, window_effects_correctedDataset);
	}

	@Override
	public DataNode setWindow_effects_correctedScalar(Boolean window_effects_correctedValue) {
		return setField(NX_WINDOW_EFFECTS_CORRECTED, window_effects_correctedValue);
	}

	@Override
	public Dataset getWindow_effects_type() {
		return getDataset(NX_WINDOW_EFFECTS_TYPE);
	}

	@Override
	public String getWindow_effects_typeScalar() {
		return getString(NX_WINDOW_EFFECTS_TYPE);
	}

	@Override
	public DataNode setWindow_effects_type(IDataset window_effects_typeDataset) {
		return setDataset(NX_WINDOW_EFFECTS_TYPE, window_effects_typeDataset);
	}

	@Override
	public DataNode setWindow_effects_typeScalar(String window_effects_typeValue) {
		return setString(NX_WINDOW_EFFECTS_TYPE, window_effects_typeValue);
	}

	@Override
	public NXprocess getWindow_correction() {
		// dataNodeName = NX_WINDOW_CORRECTION
		return getChild("window_correction", NXprocess.class);
	}

	@Override
	public void setWindow_correction(NXprocess window_correctionGroup) {
		putChild("window_correction", window_correctionGroup);
	}

	@Override
	public Dataset getMaterial() {
		return getDataset(NX_MATERIAL);
	}

	@Override
	public String getMaterialScalar() {
		return getString(NX_MATERIAL);
	}

	@Override
	public DataNode setMaterial(IDataset materialDataset) {
		return setDataset(NX_MATERIAL, materialDataset);
	}

	@Override
	public DataNode setMaterialScalar(String materialValue) {
		return setString(NX_MATERIAL, materialValue);
	}

	@Override
	public Dataset getMaterial_other() {
		return getDataset(NX_MATERIAL_OTHER);
	}

	@Override
	public String getMaterial_otherScalar() {
		return getString(NX_MATERIAL_OTHER);
	}

	@Override
	public DataNode setMaterial_other(IDataset material_otherDataset) {
		return setDataset(NX_MATERIAL_OTHER, material_otherDataset);
	}

	@Override
	public DataNode setMaterial_otherScalar(String material_otherValue) {
		return setString(NX_MATERIAL_OTHER, material_otherValue);
	}

	@Override
	public Dataset getThickness() {
		return getDataset(NX_THICKNESS);
	}

	@Override
	public Double getThicknessScalar() {
		return getDouble(NX_THICKNESS);
	}

	@Override
	public DataNode setThickness(IDataset thicknessDataset) {
		return setDataset(NX_THICKNESS, thicknessDataset);
	}

	@Override
	public DataNode setThicknessScalar(Double thicknessValue) {
		return setField(NX_THICKNESS, thicknessValue);
	}

	@Override
	public Dataset getOrientation_angle() {
		return getDataset(NX_ORIENTATION_ANGLE);
	}

	@Override
	public Double getOrientation_angleScalar() {
		return getDouble(NX_ORIENTATION_ANGLE);
	}

	@Override
	public DataNode setOrientation_angle(IDataset orientation_angleDataset) {
		return setDataset(NX_ORIENTATION_ANGLE, orientation_angleDataset);
	}

	@Override
	public DataNode setOrientation_angleScalar(Double orientation_angleValue) {
		return setField(NX_ORIENTATION_ANGLE, orientation_angleValue);
	}

}

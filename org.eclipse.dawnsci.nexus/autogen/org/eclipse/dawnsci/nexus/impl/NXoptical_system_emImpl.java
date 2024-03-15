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
 * A container for qualifying an electron optical system.

 */
public class NXoptical_system_emImpl extends NXobjectImpl implements NXoptical_system_em {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXoptical_system_emImpl() {
		super();
	}

	public NXoptical_system_emImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXoptical_system_em.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_OPTICAL_SYSTEM_EM;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public IDataset getCamera_length() {
		return getDataset(NX_CAMERA_LENGTH);
	}

	@Override
	public Number getCamera_lengthScalar() {
		return getNumber(NX_CAMERA_LENGTH);
	}

	@Override
	public DataNode setCamera_length(IDataset camera_lengthDataset) {
		return setDataset(NX_CAMERA_LENGTH, camera_lengthDataset);
	}

	@Override
	public DataNode setCamera_lengthScalar(Number camera_lengthValue) {
		return setField(NX_CAMERA_LENGTH, camera_lengthValue);
	}

	@Override
	public IDataset getMagnification() {
		return getDataset(NX_MAGNIFICATION);
	}

	@Override
	public Number getMagnificationScalar() {
		return getNumber(NX_MAGNIFICATION);
	}

	@Override
	public DataNode setMagnification(IDataset magnificationDataset) {
		return setDataset(NX_MAGNIFICATION, magnificationDataset);
	}

	@Override
	public DataNode setMagnificationScalar(Number magnificationValue) {
		return setField(NX_MAGNIFICATION, magnificationValue);
	}

	@Override
	public IDataset getDefocus() {
		return getDataset(NX_DEFOCUS);
	}

	@Override
	public Number getDefocusScalar() {
		return getNumber(NX_DEFOCUS);
	}

	@Override
	public DataNode setDefocus(IDataset defocusDataset) {
		return setDataset(NX_DEFOCUS, defocusDataset);
	}

	@Override
	public DataNode setDefocusScalar(Number defocusValue) {
		return setField(NX_DEFOCUS, defocusValue);
	}

	@Override
	public IDataset getSemi_convergence_angle() {
		return getDataset(NX_SEMI_CONVERGENCE_ANGLE);
	}

	@Override
	public Number getSemi_convergence_angleScalar() {
		return getNumber(NX_SEMI_CONVERGENCE_ANGLE);
	}

	@Override
	public DataNode setSemi_convergence_angle(IDataset semi_convergence_angleDataset) {
		return setDataset(NX_SEMI_CONVERGENCE_ANGLE, semi_convergence_angleDataset);
	}

	@Override
	public DataNode setSemi_convergence_angleScalar(Number semi_convergence_angleValue) {
		return setField(NX_SEMI_CONVERGENCE_ANGLE, semi_convergence_angleValue);
	}

	@Override
	public IDataset getField_of_view() {
		return getDataset(NX_FIELD_OF_VIEW);
	}

	@Override
	public Number getField_of_viewScalar() {
		return getNumber(NX_FIELD_OF_VIEW);
	}

	@Override
	public DataNode setField_of_view(IDataset field_of_viewDataset) {
		return setDataset(NX_FIELD_OF_VIEW, field_of_viewDataset);
	}

	@Override
	public DataNode setField_of_viewScalar(Number field_of_viewValue) {
		return setField(NX_FIELD_OF_VIEW, field_of_viewValue);
	}

	@Override
	public IDataset getWorking_distance() {
		return getDataset(NX_WORKING_DISTANCE);
	}

	@Override
	public Double getWorking_distanceScalar() {
		return getDouble(NX_WORKING_DISTANCE);
	}

	@Override
	public DataNode setWorking_distance(IDataset working_distanceDataset) {
		return setDataset(NX_WORKING_DISTANCE, working_distanceDataset);
	}

	@Override
	public DataNode setWorking_distanceScalar(Double working_distanceValue) {
		return setField(NX_WORKING_DISTANCE, working_distanceValue);
	}

	@Override
	public IDataset getBeam_current() {
		return getDataset(NX_BEAM_CURRENT);
	}

	@Override
	public Double getBeam_currentScalar() {
		return getDouble(NX_BEAM_CURRENT);
	}

	@Override
	public DataNode setBeam_current(IDataset beam_currentDataset) {
		return setDataset(NX_BEAM_CURRENT, beam_currentDataset);
	}

	@Override
	public DataNode setBeam_currentScalar(Double beam_currentValue) {
		return setField(NX_BEAM_CURRENT, beam_currentValue);
	}

	@Override
	public IDataset getBeam_current_description() {
		return getDataset(NX_BEAM_CURRENT_DESCRIPTION);
	}

	@Override
	public String getBeam_current_descriptionScalar() {
		return getString(NX_BEAM_CURRENT_DESCRIPTION);
	}

	@Override
	public DataNode setBeam_current_description(IDataset beam_current_descriptionDataset) {
		return setDataset(NX_BEAM_CURRENT_DESCRIPTION, beam_current_descriptionDataset);
	}

	@Override
	public DataNode setBeam_current_descriptionScalar(String beam_current_descriptionValue) {
		return setString(NX_BEAM_CURRENT_DESCRIPTION, beam_current_descriptionValue);
	}

}

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
 * Base class for qualifying an electron optical system.

 */
public class NXem_optical_systemImpl extends NXobjectImpl implements NXem_optical_system {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXem_optical_systemImpl() {
		super();
	}

	public NXem_optical_systemImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXem_optical_system.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_EM_OPTICAL_SYSTEM;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getCamera_length() {
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
	public Dataset getMagnification() {
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
	public Dataset getDefocus() {
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
	public Dataset getSemi_convergence_angle() {
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
	public Dataset getField_of_view() {
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
	public Dataset getWorking_distance() {
		return getDataset(NX_WORKING_DISTANCE);
	}

	@Override
	public Number getWorking_distanceScalar() {
		return getNumber(NX_WORKING_DISTANCE);
	}

	@Override
	public DataNode setWorking_distance(IDataset working_distanceDataset) {
		return setDataset(NX_WORKING_DISTANCE, working_distanceDataset);
	}

	@Override
	public DataNode setWorking_distanceScalar(Number working_distanceValue) {
		return setField(NX_WORKING_DISTANCE, working_distanceValue);
	}

	@Override
	public Dataset getProbe() {
		return getDataset(NX_PROBE);
	}

	@Override
	public Number getProbeScalar() {
		return getNumber(NX_PROBE);
	}

	@Override
	public DataNode setProbe(IDataset probeDataset) {
		return setDataset(NX_PROBE, probeDataset);
	}

	@Override
	public DataNode setProbeScalar(Number probeValue) {
		return setField(NX_PROBE, probeValue);
	}

	@Override
	public Dataset getProbe_current() {
		return getDataset(NX_PROBE_CURRENT);
	}

	@Override
	public Number getProbe_currentScalar() {
		return getNumber(NX_PROBE_CURRENT);
	}

	@Override
	public DataNode setProbe_current(IDataset probe_currentDataset) {
		return setDataset(NX_PROBE_CURRENT, probe_currentDataset);
	}

	@Override
	public DataNode setProbe_currentScalar(Number probe_currentValue) {
		return setField(NX_PROBE_CURRENT, probe_currentValue);
	}

	@Override
	public Dataset getDose_management() {
		return getDataset(NX_DOSE_MANAGEMENT);
	}

	@Override
	public String getDose_managementScalar() {
		return getString(NX_DOSE_MANAGEMENT);
	}

	@Override
	public DataNode setDose_management(IDataset dose_managementDataset) {
		return setDataset(NX_DOSE_MANAGEMENT, dose_managementDataset);
	}

	@Override
	public DataNode setDose_managementScalar(String dose_managementValue) {
		return setString(NX_DOSE_MANAGEMENT, dose_managementValue);
	}

	@Override
	public Dataset getDose_rate() {
		return getDataset(NX_DOSE_RATE);
	}

	@Override
	public Number getDose_rateScalar() {
		return getNumber(NX_DOSE_RATE);
	}

	@Override
	public DataNode setDose_rate(IDataset dose_rateDataset) {
		return setDataset(NX_DOSE_RATE, dose_rateDataset);
	}

	@Override
	public DataNode setDose_rateScalar(Number dose_rateValue) {
		return setField(NX_DOSE_RATE, dose_rateValue);
	}

	@Override
	public Dataset getRotation() {
		return getDataset(NX_ROTATION);
	}

	@Override
	public Number getRotationScalar() {
		return getNumber(NX_ROTATION);
	}

	@Override
	public DataNode setRotation(IDataset rotationDataset) {
		return setDataset(NX_ROTATION, rotationDataset);
	}

	@Override
	public DataNode setRotationScalar(Number rotationValue) {
		return setField(NX_ROTATION, rotationValue);
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
	public Dataset getTilt_correction() {
		return getDataset(NX_TILT_CORRECTION);
	}

	@Override
	public Boolean getTilt_correctionScalar() {
		return getBoolean(NX_TILT_CORRECTION);
	}

	@Override
	public DataNode setTilt_correction(IDataset tilt_correctionDataset) {
		return setDataset(NX_TILT_CORRECTION, tilt_correctionDataset);
	}

	@Override
	public DataNode setTilt_correctionScalar(Boolean tilt_correctionValue) {
		return setField(NX_TILT_CORRECTION, tilt_correctionValue);
	}

	@Override
	public Dataset getDynamic_focus_correction() {
		return getDataset(NX_DYNAMIC_FOCUS_CORRECTION);
	}

	@Override
	public Boolean getDynamic_focus_correctionScalar() {
		return getBoolean(NX_DYNAMIC_FOCUS_CORRECTION);
	}

	@Override
	public DataNode setDynamic_focus_correction(IDataset dynamic_focus_correctionDataset) {
		return setDataset(NX_DYNAMIC_FOCUS_CORRECTION, dynamic_focus_correctionDataset);
	}

	@Override
	public DataNode setDynamic_focus_correctionScalar(Boolean dynamic_focus_correctionValue) {
		return setField(NX_DYNAMIC_FOCUS_CORRECTION, dynamic_focus_correctionValue);
	}

	@Override
	public Dataset getDynamic_refocusing() {
		return getDataset(NX_DYNAMIC_REFOCUSING);
	}

	@Override
	public String getDynamic_refocusingScalar() {
		return getString(NX_DYNAMIC_REFOCUSING);
	}

	@Override
	public DataNode setDynamic_refocusing(IDataset dynamic_refocusingDataset) {
		return setDataset(NX_DYNAMIC_REFOCUSING, dynamic_refocusingDataset);
	}

	@Override
	public DataNode setDynamic_refocusingScalar(String dynamic_refocusingValue) {
		return setString(NX_DYNAMIC_REFOCUSING, dynamic_refocusingValue);
	}

}

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
 * Base class to detail a set of rotations, orientations, and disorientations.
 * For getting a more detailed insight into the discussion of the
 * parameterized description of orientations in materials science see:
 * * `H.-J. Bunge <https://doi.org/10.1016/C2013-0-11769-2>`_
 * * `T. B. Britton et al. <https://doi.org/10.1016/j.matchar.2016.04.008>`_
 * * `D. Rowenhorst et al. <https://doi.org/10.1088/0965-0393/23/8/083501>`_
 * * `A. Morawiec <https://doi.org/10.1007/978-3-662-09156-2>`_
 * Once orientations are defined, one can continue to characterize the
 * misorientation and specifically the disorientation. The misorientation describes
 * the rotation that is required to register the lattices of two oriented objects
 * (like crystal lattice) into a crystallographic equivalent orientation:
 * * `R. Bonnet <https://doi.org/10.1107/S0567739480000186>`_
 * The concepts of mis- and disorientation are relevant when analyzing the
 * crystallography of interfaces.

 */
public class NXrotationsImpl extends NXobjectImpl implements NXrotations {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXrotationsImpl() {
		super();
	}

	public NXrotationsImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXrotations.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_ROTATIONS;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getReference_frame() {
		return getDataset(NX_REFERENCE_FRAME);
	}

	@Override
	public String getReference_frameScalar() {
		return getString(NX_REFERENCE_FRAME);
	}

	@Override
	public DataNode setReference_frame(IDataset reference_frameDataset) {
		return setDataset(NX_REFERENCE_FRAME, reference_frameDataset);
	}

	@Override
	public DataNode setReference_frameScalar(String reference_frameValue) {
		return setString(NX_REFERENCE_FRAME, reference_frameValue);
	}

	@Override
	public Dataset getCrystal_symmetry() {
		return getDataset(NX_CRYSTAL_SYMMETRY);
	}

	@Override
	public String getCrystal_symmetryScalar() {
		return getString(NX_CRYSTAL_SYMMETRY);
	}

	@Override
	public DataNode setCrystal_symmetry(IDataset crystal_symmetryDataset) {
		return setDataset(NX_CRYSTAL_SYMMETRY, crystal_symmetryDataset);
	}

	@Override
	public DataNode setCrystal_symmetryScalar(String crystal_symmetryValue) {
		return setString(NX_CRYSTAL_SYMMETRY, crystal_symmetryValue);
	}

	@Override
	public Dataset getSample_symmetry() {
		return getDataset(NX_SAMPLE_SYMMETRY);
	}

	@Override
	public String getSample_symmetryScalar() {
		return getString(NX_SAMPLE_SYMMETRY);
	}

	@Override
	public DataNode setSample_symmetry(IDataset sample_symmetryDataset) {
		return setDataset(NX_SAMPLE_SYMMETRY, sample_symmetryDataset);
	}

	@Override
	public DataNode setSample_symmetryScalar(String sample_symmetryValue) {
		return setString(NX_SAMPLE_SYMMETRY, sample_symmetryValue);
	}

	@Override
	public Dataset getRotation_quaternion() {
		return getDataset(NX_ROTATION_QUATERNION);
	}

	@Override
	public Number getRotation_quaternionScalar() {
		return getNumber(NX_ROTATION_QUATERNION);
	}

	@Override
	public DataNode setRotation_quaternion(IDataset rotation_quaternionDataset) {
		return setDataset(NX_ROTATION_QUATERNION, rotation_quaternionDataset);
	}

	@Override
	public DataNode setRotation_quaternionScalar(Number rotation_quaternionValue) {
		return setField(NX_ROTATION_QUATERNION, rotation_quaternionValue);
	}

	@Override
	public Dataset getRotation_euler() {
		return getDataset(NX_ROTATION_EULER);
	}

	@Override
	public Number getRotation_eulerScalar() {
		return getNumber(NX_ROTATION_EULER);
	}

	@Override
	public DataNode setRotation_euler(IDataset rotation_eulerDataset) {
		return setDataset(NX_ROTATION_EULER, rotation_eulerDataset);
	}

	@Override
	public DataNode setRotation_eulerScalar(Number rotation_eulerValue) {
		return setField(NX_ROTATION_EULER, rotation_eulerValue);
	}

	@Override
	public Dataset getIs_antipodal() {
		return getDataset(NX_IS_ANTIPODAL);
	}

	@Override
	public Boolean getIs_antipodalScalar() {
		return getBoolean(NX_IS_ANTIPODAL);
	}

	@Override
	public DataNode setIs_antipodal(IDataset is_antipodalDataset) {
		return setDataset(NX_IS_ANTIPODAL, is_antipodalDataset);
	}

	@Override
	public DataNode setIs_antipodalScalar(Boolean is_antipodalValue) {
		return setField(NX_IS_ANTIPODAL, is_antipodalValue);
	}

	@Override
	public Dataset getOrientation_quaternion() {
		return getDataset(NX_ORIENTATION_QUATERNION);
	}

	@Override
	public Number getOrientation_quaternionScalar() {
		return getNumber(NX_ORIENTATION_QUATERNION);
	}

	@Override
	public DataNode setOrientation_quaternion(IDataset orientation_quaternionDataset) {
		return setDataset(NX_ORIENTATION_QUATERNION, orientation_quaternionDataset);
	}

	@Override
	public DataNode setOrientation_quaternionScalar(Number orientation_quaternionValue) {
		return setField(NX_ORIENTATION_QUATERNION, orientation_quaternionValue);
	}

	@Override
	public Dataset getOrientation_euler() {
		return getDataset(NX_ORIENTATION_EULER);
	}

	@Override
	public Number getOrientation_eulerScalar() {
		return getNumber(NX_ORIENTATION_EULER);
	}

	@Override
	public DataNode setOrientation_euler(IDataset orientation_eulerDataset) {
		return setDataset(NX_ORIENTATION_EULER, orientation_eulerDataset);
	}

	@Override
	public DataNode setOrientation_eulerScalar(Number orientation_eulerValue) {
		return setField(NX_ORIENTATION_EULER, orientation_eulerValue);
	}

	@Override
	public Dataset getMisorientation_quaternion() {
		return getDataset(NX_MISORIENTATION_QUATERNION);
	}

	@Override
	public Number getMisorientation_quaternionScalar() {
		return getNumber(NX_MISORIENTATION_QUATERNION);
	}

	@Override
	public DataNode setMisorientation_quaternion(IDataset misorientation_quaternionDataset) {
		return setDataset(NX_MISORIENTATION_QUATERNION, misorientation_quaternionDataset);
	}

	@Override
	public DataNode setMisorientation_quaternionScalar(Number misorientation_quaternionValue) {
		return setField(NX_MISORIENTATION_QUATERNION, misorientation_quaternionValue);
	}

	@Override
	public Dataset getMisorientation_angle() {
		return getDataset(NX_MISORIENTATION_ANGLE);
	}

	@Override
	public Number getMisorientation_angleScalar() {
		return getNumber(NX_MISORIENTATION_ANGLE);
	}

	@Override
	public DataNode setMisorientation_angle(IDataset misorientation_angleDataset) {
		return setDataset(NX_MISORIENTATION_ANGLE, misorientation_angleDataset);
	}

	@Override
	public DataNode setMisorientation_angleScalar(Number misorientation_angleValue) {
		return setField(NX_MISORIENTATION_ANGLE, misorientation_angleValue);
	}

	@Override
	public Dataset getMisorientation_axis() {
		return getDataset(NX_MISORIENTATION_AXIS);
	}

	@Override
	public Number getMisorientation_axisScalar() {
		return getNumber(NX_MISORIENTATION_AXIS);
	}

	@Override
	public DataNode setMisorientation_axis(IDataset misorientation_axisDataset) {
		return setDataset(NX_MISORIENTATION_AXIS, misorientation_axisDataset);
	}

	@Override
	public DataNode setMisorientation_axisScalar(Number misorientation_axisValue) {
		return setField(NX_MISORIENTATION_AXIS, misorientation_axisValue);
	}

	@Override
	public Dataset getDisorientation_quaternion() {
		return getDataset(NX_DISORIENTATION_QUATERNION);
	}

	@Override
	public Number getDisorientation_quaternionScalar() {
		return getNumber(NX_DISORIENTATION_QUATERNION);
	}

	@Override
	public DataNode setDisorientation_quaternion(IDataset disorientation_quaternionDataset) {
		return setDataset(NX_DISORIENTATION_QUATERNION, disorientation_quaternionDataset);
	}

	@Override
	public DataNode setDisorientation_quaternionScalar(Number disorientation_quaternionValue) {
		return setField(NX_DISORIENTATION_QUATERNION, disorientation_quaternionValue);
	}

	@Override
	public Dataset getDisorientation_angle() {
		return getDataset(NX_DISORIENTATION_ANGLE);
	}

	@Override
	public Number getDisorientation_angleScalar() {
		return getNumber(NX_DISORIENTATION_ANGLE);
	}

	@Override
	public DataNode setDisorientation_angle(IDataset disorientation_angleDataset) {
		return setDataset(NX_DISORIENTATION_ANGLE, disorientation_angleDataset);
	}

	@Override
	public DataNode setDisorientation_angleScalar(Number disorientation_angleValue) {
		return setField(NX_DISORIENTATION_ANGLE, disorientation_angleValue);
	}

	@Override
	public Dataset getDisorientation_axis() {
		return getDataset(NX_DISORIENTATION_AXIS);
	}

	@Override
	public Number getDisorientation_axisScalar() {
		return getNumber(NX_DISORIENTATION_AXIS);
	}

	@Override
	public DataNode setDisorientation_axis(IDataset disorientation_axisDataset) {
		return setDataset(NX_DISORIENTATION_AXIS, disorientation_axisDataset);
	}

	@Override
	public DataNode setDisorientation_axisScalar(Number disorientation_axisValue) {
		return setField(NX_DISORIENTATION_AXIS, disorientation_axisValue);
	}

}

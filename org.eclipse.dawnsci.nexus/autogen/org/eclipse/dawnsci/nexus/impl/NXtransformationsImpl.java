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
 * Collection of axis-based translations and rotations to describe a geometry.
 * May also contain axes that do not move and therefore do not have a transformation
 * type specified, but are useful in understanding coordinate frames within which
 * transformations are done, or in documenting important directions, such as the
 * direction of gravity.
 * A nested sequence of transformations lists the translation and rotation steps
 * needed to describe the position and orientation of any movable or fixed device.
 * There will be one or more transformations (axes) defined by one or more fields
 * for each transformation. The all-caps name ``AXISNAME`` designates the
 * particular axis generating a transformation (e.g. a rotation axis or a translation
 * axis or a general axis). The attribute ``units="NX_TRANSFORMATION"`` designates the
 * units will be appropriate to the ``transformation_type`` attribute:
 * * ``NX_LENGTH`` for ``translation``
 * * ``NX_ANGLE`` for ``rotation``
 * * ``NX_UNITLESS`` for axes for which no transformation type is specified
 * This class will usually contain all axes of a sample stage or goniometer or
 * a detector. The NeXus default McSTAS coordinate frame is assumed, but additional
 * useful coordinate axes may be defined by using axes for which no transformation
 * type has been specified.
 * The entry point (``depends_on``) will be outside of this class and point to a
 * field in here. Following the chain may also require following ``depends_on``
 * links to transformations outside, for example to a common base table. If
 * a relative path is given, it is relative to the group enclosing the ``depends_on``
 * specification.
 * For a chain of three transformations, where :math:`T_1` depends on :math:`T_2`
 * and that in turn depends on :math:`T_3`, the final transformation :math:`T_f` is
 * .. math:: T_f = T_3 T_2 T_1
 * In explicit terms, the transformations are a subset of affine transformations
 * expressed as 4x4 matrices that act on homogeneous coordinates, :math:`w=(x,y,z,1)^T`.
 * For rotation and translation,
 * .. math:: T_r &= \begin{pmatrix} R & o \\ 0_3 & 1 \end{pmatrix} \\ T_t &= \begin{pmatrix} I_3 & t + o \\ 0_3 & 1 \end{pmatrix}
 * where :math:`R` is the usual 3x3 rotation matrix, :math:`o` is an offset vector,
 * :math:`0_3` is a row of 3 zeros, :math:`I_3` is the 3x3 identity matrix and
 * :math:`t` is the translation vector.
 * :math:`o` is given by the ``offset`` attribute, :math:`t` is given by the ``vector``
 * attribute multiplied by the field value, and :math:`R` is defined as a rotation
 * about an axis in the direction of ``vector``, of angle of the field value.
 * NOTE
 * One possible use of ``NXtransformations`` is to define the motors and
 * transformations for a diffractometer (goniometer). Such use is mentioned
 * in the ``NXinstrument`` base class. Use one ``NXtransformations`` group
 * for each diffractometer and name the group appropriate to the device.
 * Collecting the motors of a sample table or xyz-stage in an NXtransformation
 * group is equally possible.
 * 
 */
public class NXtransformationsImpl extends NXobjectImpl implements NXtransformations {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXtransformationsImpl() {
		super();
	}

	public NXtransformationsImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXtransformations.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_TRANSFORMATIONS;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}
	

	@Override
	public IDataset getAxisname(String axisname) {
		return getDataset(axisname);
	}

	@Override
	public Number getAxisnameScalar(String axisname) {
		return getNumber(axisname);
	}

	@Override
	public DataNode setAxisname(String axisname, IDataset axisnameDataset) {
		return setDataset(axisname, axisnameDataset);
	}

	@Override
	public DataNode setAxisnameScalar(String axisname, Number axisnameValue) {
		return setField(axisname, axisnameValue);
	}

	@Override
	public Map<String, IDataset> getAllAxisname() {
		return getAllDatasets(); // note: returns all datasets in the group!
	}

	@Override
	public String getAxisnameAttributeTransformation_type(String axisname) {
		return getAttrString(axisname, NX_AXISNAME_ATTRIBUTE_TRANSFORMATION_TYPE);
	}

	@Override
	public void setAxisnameAttributeTransformation_type(String axisname, String transformation_typeValue) {
		setAttribute(axisname, NX_AXISNAME_ATTRIBUTE_TRANSFORMATION_TYPE, transformation_typeValue);
	}

	@Override
	public Number getAxisnameAttributeVector(String axisname) {
		return getAttrNumber(axisname, NX_AXISNAME_ATTRIBUTE_VECTOR);
	}

	@Override
	public void setAxisnameAttributeVector(String axisname, Number vectorValue) {
		setAttribute(axisname, NX_AXISNAME_ATTRIBUTE_VECTOR, vectorValue);
	}

	@Override
	public Number getAxisnameAttributeOffset(String axisname) {
		return getAttrNumber(axisname, NX_AXISNAME_ATTRIBUTE_OFFSET);
	}

	@Override
	public void setAxisnameAttributeOffset(String axisname, Number offsetValue) {
		setAttribute(axisname, NX_AXISNAME_ATTRIBUTE_OFFSET, offsetValue);
	}

	@Override
	public String getAxisnameAttributeOffset_units(String axisname) {
		return getAttrString(axisname, NX_AXISNAME_ATTRIBUTE_OFFSET_UNITS);
	}

	@Override
	public void setAxisnameAttributeOffset_units(String axisname, String offset_unitsValue) {
		setAttribute(axisname, NX_AXISNAME_ATTRIBUTE_OFFSET_UNITS, offset_unitsValue);
	}

	@Override
	public String getAxisnameAttributeDepends_on(String axisname) {
		return getAttrString(axisname, NX_AXISNAME_ATTRIBUTE_DEPENDS_ON);
	}

	@Override
	public void setAxisnameAttributeDepends_on(String axisname, String depends_onValue) {
		setAttribute(axisname, NX_AXISNAME_ATTRIBUTE_DEPENDS_ON, depends_onValue);
	}

	@Override
	public IDataset getEnd(String axisname) {
		return getDataset(axisname + NX_END_SUFFIX);
	}

	@Override
	public Number getEndScalar(String axisname) {
		return getNumber(axisname + NX_END_SUFFIX);
	}

	@Override
	public DataNode setEnd(String axisname, IDataset endDataset) {
		return setDataset(axisname + NX_END_SUFFIX, endDataset);
	}

	@Override
	public DataNode setEndScalar(String axisname, Number endValue) {
		return setField(axisname + NX_END_SUFFIX, endValue);
	}

	@Override
	public Map<String, IDataset> getAllEnd() {
		return getAllDatasets(); // note: returns all datasets in the group!
	}

	@Override
	public IDataset getIncrement_set(String axisname) {
		return getDataset(axisname + NX_INCREMENT_SET_SUFFIX);
	}

	@Override
	public Number getIncrement_setScalar(String axisname) {
		return getNumber(axisname + NX_INCREMENT_SET_SUFFIX);
	}

	@Override
	public DataNode setIncrement_set(String axisname, IDataset increment_setDataset) {
		return setDataset(axisname + NX_INCREMENT_SET_SUFFIX, increment_setDataset);
	}

	@Override
	public DataNode setIncrement_setScalar(String axisname, Number increment_setValue) {
		return setField(axisname + NX_INCREMENT_SET_SUFFIX, increment_setValue);
	}

	@Override
	public Map<String, IDataset> getAllIncrement_set() {
		return getAllDatasets(); // note: returns all datasets in the group!
	}

	@Override
	public String getAttributeDefault() {
		return getAttrString(null, NX_ATTRIBUTE_DEFAULT);
	}

	@Override
	public void setAttributeDefault(String defaultValue) {
		setAttribute(null, NX_ATTRIBUTE_DEFAULT, defaultValue);
	}

}

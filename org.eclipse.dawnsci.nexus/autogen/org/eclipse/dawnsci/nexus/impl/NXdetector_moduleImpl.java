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
 * Geometry and logical description of a detector module. When used, child group to NXdetector.
 * Many detectors consist of multiple
 * smaller modules. Sometimes it is important to know the exact position of such
 * modules.
 * This is the purpose of this group. It is a child group to NXdetector.
 * Note, the pixel size is given as values in the array fast_pixel_direction and slow_pixel_direction.
 * 
 */
public class NXdetector_moduleImpl extends NXobjectImpl implements NXdetector_module {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXdetector_moduleImpl() {
		super();
	}

	public NXdetector_moduleImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXdetector_module.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_DETECTOR_MODULE;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}
	

	@Override
	public IDataset getData_origin() {
		return getDataset(NX_DATA_ORIGIN);
	}

	@Override
	public Long getData_originScalar() {
		return getLong(NX_DATA_ORIGIN);
	}

	@Override
	public DataNode setData_origin(IDataset data_originDataset) {
		return setDataset(NX_DATA_ORIGIN, data_originDataset);
	}

	@Override
	public DataNode setData_originScalar(Long data_originValue) {
		return setField(NX_DATA_ORIGIN, data_originValue);
	}

	@Override
	public IDataset getData_size() {
		return getDataset(NX_DATA_SIZE);
	}

	@Override
	public Long getData_sizeScalar() {
		return getLong(NX_DATA_SIZE);
	}

	@Override
	public DataNode setData_size(IDataset data_sizeDataset) {
		return setDataset(NX_DATA_SIZE, data_sizeDataset);
	}

	@Override
	public DataNode setData_sizeScalar(Long data_sizeValue) {
		return setField(NX_DATA_SIZE, data_sizeValue);
	}

	@Override
	public IDataset getModule_offset() {
		return getDataset(NX_MODULE_OFFSET);
	}

	@Override
	public Number getModule_offsetScalar() {
		return getNumber(NX_MODULE_OFFSET);
	}

	@Override
	public DataNode setModule_offset(IDataset module_offsetDataset) {
		return setDataset(NX_MODULE_OFFSET, module_offsetDataset);
	}

	@Override
	public DataNode setModule_offsetScalar(Number module_offsetValue) {
		return setField(NX_MODULE_OFFSET, module_offsetValue);
	}

	@Override
	public String getModule_offsetAttributeTransformation_type() {
		return getAttrString(NX_MODULE_OFFSET, NX_MODULE_OFFSET_ATTRIBUTE_TRANSFORMATION_TYPE);
	}

	@Override
	public void setModule_offsetAttributeTransformation_type(String transformation_typeValue) {
		setAttribute(NX_MODULE_OFFSET, NX_MODULE_OFFSET_ATTRIBUTE_TRANSFORMATION_TYPE, transformation_typeValue);
	}

	@Override
	public Number getModule_offsetAttributeVector() {
		return getAttrNumber(NX_MODULE_OFFSET, NX_MODULE_OFFSET_ATTRIBUTE_VECTOR);
	}

	@Override
	public void setModule_offsetAttributeVector(Number vectorValue) {
		setAttribute(NX_MODULE_OFFSET, NX_MODULE_OFFSET_ATTRIBUTE_VECTOR, vectorValue);
	}

	@Override
	public Number getModule_offsetAttributeOffset() {
		return getAttrNumber(NX_MODULE_OFFSET, NX_MODULE_OFFSET_ATTRIBUTE_OFFSET);
	}

	@Override
	public void setModule_offsetAttributeOffset(Number offsetValue) {
		setAttribute(NX_MODULE_OFFSET, NX_MODULE_OFFSET_ATTRIBUTE_OFFSET, offsetValue);
	}

	@Override
	public String getModule_offsetAttributeOffset_units() {
		return getAttrString(NX_MODULE_OFFSET, NX_MODULE_OFFSET_ATTRIBUTE_OFFSET_UNITS);
	}

	@Override
	public void setModule_offsetAttributeOffset_units(String offset_unitsValue) {
		setAttribute(NX_MODULE_OFFSET, NX_MODULE_OFFSET_ATTRIBUTE_OFFSET_UNITS, offset_unitsValue);
	}

	@Override
	public String getModule_offsetAttributeDepends_on() {
		return getAttrString(NX_MODULE_OFFSET, NX_MODULE_OFFSET_ATTRIBUTE_DEPENDS_ON);
	}

	@Override
	public void setModule_offsetAttributeDepends_on(String depends_onValue) {
		setAttribute(NX_MODULE_OFFSET, NX_MODULE_OFFSET_ATTRIBUTE_DEPENDS_ON, depends_onValue);
	}

	@Override
	public IDataset getFast_pixel_direction() {
		return getDataset(NX_FAST_PIXEL_DIRECTION);
	}

	@Override
	public Number getFast_pixel_directionScalar() {
		return getNumber(NX_FAST_PIXEL_DIRECTION);
	}

	@Override
	public DataNode setFast_pixel_direction(IDataset fast_pixel_directionDataset) {
		return setDataset(NX_FAST_PIXEL_DIRECTION, fast_pixel_directionDataset);
	}

	@Override
	public DataNode setFast_pixel_directionScalar(Number fast_pixel_directionValue) {
		return setField(NX_FAST_PIXEL_DIRECTION, fast_pixel_directionValue);
	}

	@Override
	public String getFast_pixel_directionAttributeTransformation_type() {
		return getAttrString(NX_FAST_PIXEL_DIRECTION, NX_FAST_PIXEL_DIRECTION_ATTRIBUTE_TRANSFORMATION_TYPE);
	}

	@Override
	public void setFast_pixel_directionAttributeTransformation_type(String transformation_typeValue) {
		setAttribute(NX_FAST_PIXEL_DIRECTION, NX_FAST_PIXEL_DIRECTION_ATTRIBUTE_TRANSFORMATION_TYPE, transformation_typeValue);
	}

	@Override
	public Number getFast_pixel_directionAttributeVector() {
		return getAttrNumber(NX_FAST_PIXEL_DIRECTION, NX_FAST_PIXEL_DIRECTION_ATTRIBUTE_VECTOR);
	}

	@Override
	public void setFast_pixel_directionAttributeVector(Number vectorValue) {
		setAttribute(NX_FAST_PIXEL_DIRECTION, NX_FAST_PIXEL_DIRECTION_ATTRIBUTE_VECTOR, vectorValue);
	}

	@Override
	public Number getFast_pixel_directionAttributeOffset() {
		return getAttrNumber(NX_FAST_PIXEL_DIRECTION, NX_FAST_PIXEL_DIRECTION_ATTRIBUTE_OFFSET);
	}

	@Override
	public void setFast_pixel_directionAttributeOffset(Number offsetValue) {
		setAttribute(NX_FAST_PIXEL_DIRECTION, NX_FAST_PIXEL_DIRECTION_ATTRIBUTE_OFFSET, offsetValue);
	}

	@Override
	public String getFast_pixel_directionAttributeOffset_units() {
		return getAttrString(NX_FAST_PIXEL_DIRECTION, NX_FAST_PIXEL_DIRECTION_ATTRIBUTE_OFFSET_UNITS);
	}

	@Override
	public void setFast_pixel_directionAttributeOffset_units(String offset_unitsValue) {
		setAttribute(NX_FAST_PIXEL_DIRECTION, NX_FAST_PIXEL_DIRECTION_ATTRIBUTE_OFFSET_UNITS, offset_unitsValue);
	}

	@Override
	public String getFast_pixel_directionAttributeDepends_on() {
		return getAttrString(NX_FAST_PIXEL_DIRECTION, NX_FAST_PIXEL_DIRECTION_ATTRIBUTE_DEPENDS_ON);
	}

	@Override
	public void setFast_pixel_directionAttributeDepends_on(String depends_onValue) {
		setAttribute(NX_FAST_PIXEL_DIRECTION, NX_FAST_PIXEL_DIRECTION_ATTRIBUTE_DEPENDS_ON, depends_onValue);
	}

	@Override
	public IDataset getSlow_pixel_direction() {
		return getDataset(NX_SLOW_PIXEL_DIRECTION);
	}

	@Override
	public Number getSlow_pixel_directionScalar() {
		return getNumber(NX_SLOW_PIXEL_DIRECTION);
	}

	@Override
	public DataNode setSlow_pixel_direction(IDataset slow_pixel_directionDataset) {
		return setDataset(NX_SLOW_PIXEL_DIRECTION, slow_pixel_directionDataset);
	}

	@Override
	public DataNode setSlow_pixel_directionScalar(Number slow_pixel_directionValue) {
		return setField(NX_SLOW_PIXEL_DIRECTION, slow_pixel_directionValue);
	}

	@Override
	public String getSlow_pixel_directionAttributeTransformation_type() {
		return getAttrString(NX_SLOW_PIXEL_DIRECTION, NX_SLOW_PIXEL_DIRECTION_ATTRIBUTE_TRANSFORMATION_TYPE);
	}

	@Override
	public void setSlow_pixel_directionAttributeTransformation_type(String transformation_typeValue) {
		setAttribute(NX_SLOW_PIXEL_DIRECTION, NX_SLOW_PIXEL_DIRECTION_ATTRIBUTE_TRANSFORMATION_TYPE, transformation_typeValue);
	}

	@Override
	public Number getSlow_pixel_directionAttributeVector() {
		return getAttrNumber(NX_SLOW_PIXEL_DIRECTION, NX_SLOW_PIXEL_DIRECTION_ATTRIBUTE_VECTOR);
	}

	@Override
	public void setSlow_pixel_directionAttributeVector(Number vectorValue) {
		setAttribute(NX_SLOW_PIXEL_DIRECTION, NX_SLOW_PIXEL_DIRECTION_ATTRIBUTE_VECTOR, vectorValue);
	}

	@Override
	public Number getSlow_pixel_directionAttributeOffset() {
		return getAttrNumber(NX_SLOW_PIXEL_DIRECTION, NX_SLOW_PIXEL_DIRECTION_ATTRIBUTE_OFFSET);
	}

	@Override
	public void setSlow_pixel_directionAttributeOffset(Number offsetValue) {
		setAttribute(NX_SLOW_PIXEL_DIRECTION, NX_SLOW_PIXEL_DIRECTION_ATTRIBUTE_OFFSET, offsetValue);
	}

	@Override
	public String getSlow_pixel_directionAttributeOffset_units() {
		return getAttrString(NX_SLOW_PIXEL_DIRECTION, NX_SLOW_PIXEL_DIRECTION_ATTRIBUTE_OFFSET_UNITS);
	}

	@Override
	public void setSlow_pixel_directionAttributeOffset_units(String offset_unitsValue) {
		setAttribute(NX_SLOW_PIXEL_DIRECTION, NX_SLOW_PIXEL_DIRECTION_ATTRIBUTE_OFFSET_UNITS, offset_unitsValue);
	}

	@Override
	public String getSlow_pixel_directionAttributeDepends_on() {
		return getAttrString(NX_SLOW_PIXEL_DIRECTION, NX_SLOW_PIXEL_DIRECTION_ATTRIBUTE_DEPENDS_ON);
	}

	@Override
	public void setSlow_pixel_directionAttributeDepends_on(String depends_onValue) {
		setAttribute(NX_SLOW_PIXEL_DIRECTION, NX_SLOW_PIXEL_DIRECTION_ATTRIBUTE_DEPENDS_ON, depends_onValue);
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

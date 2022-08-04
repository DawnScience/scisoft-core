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
 * Logical grouping of detectors. When used, describes a group of detectors.
 * Each detector is represented as an NXdetector
 * with its own detector data array. Each detector data array
 * may be further decomposed into array sections by use of
 * NXdetector_module groups. Detectors can be grouped logically
 * together using NXdetector_group. Groups can be further grouped
 * hierarchically in a single NXdetector_group (for example, if
 * there are multiple detectors at an endstation or multiple
 * endstations at a facility). Alternatively, multiple
 * NXdetector_groups can be provided.
 * The groups are defined hierarchically, with names given
 * in the group_names field, unique identifying indices given
 * in the field group_index, and the level in the hierarchy
 * given in the group_parent field. For example if an x-ray
 * detector group, DET, consists of four detectors in a
 * rectangular array::
 * DTL DTR
 * DLL DLR
 * We could have::
 * group_names: ["DET", "DTL", "DTR", "DLL", "DLR"]
 * group_index: [1, 2, 3, 4, 5]
 * group_parent: [-1, 1, 1, 1, 1]
 * 
 */
public class NXdetector_groupImpl extends NXobjectImpl implements NXdetector_group {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXdetector_groupImpl() {
		super();
	}

	public NXdetector_groupImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXdetector_group.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_DETECTOR_GROUP;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}
	

	@Override
	public IDataset getGroup_names() {
		return getDataset(NX_GROUP_NAMES);
	}

	@Override
	public String getGroup_namesScalar() {
		return getString(NX_GROUP_NAMES);
	}

	@Override
	public DataNode setGroup_names(IDataset group_namesDataset) {
		return setDataset(NX_GROUP_NAMES, group_namesDataset);
	}

	@Override
	public DataNode setGroup_namesScalar(String group_namesValue) {
		return setString(NX_GROUP_NAMES, group_namesValue);
	}

	@Override
	public IDataset getGroup_index() {
		return getDataset(NX_GROUP_INDEX);
	}

	@Override
	public Long getGroup_indexScalar() {
		return getLong(NX_GROUP_INDEX);
	}

	@Override
	public DataNode setGroup_index(IDataset group_indexDataset) {
		return setDataset(NX_GROUP_INDEX, group_indexDataset);
	}

	@Override
	public DataNode setGroup_indexScalar(Long group_indexValue) {
		return setField(NX_GROUP_INDEX, group_indexValue);
	}

	@Override
	public IDataset getGroup_parent() {
		return getDataset(NX_GROUP_PARENT);
	}

	@Override
	public Long getGroup_parentScalar() {
		return getLong(NX_GROUP_PARENT);
	}

	@Override
	public DataNode setGroup_parent(IDataset group_parentDataset) {
		return setDataset(NX_GROUP_PARENT, group_parentDataset);
	}

	@Override
	public DataNode setGroup_parentScalar(Long group_parentValue) {
		return setField(NX_GROUP_PARENT, group_parentValue);
	}

	@Override
	public IDataset getGroup_type() {
		return getDataset(NX_GROUP_TYPE);
	}

	@Override
	public Long getGroup_typeScalar() {
		return getLong(NX_GROUP_TYPE);
	}

	@Override
	public DataNode setGroup_type(IDataset group_typeDataset) {
		return setDataset(NX_GROUP_TYPE, group_typeDataset);
	}

	@Override
	public DataNode setGroup_typeScalar(Long group_typeValue) {
		return setField(NX_GROUP_TYPE, group_typeValue);
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

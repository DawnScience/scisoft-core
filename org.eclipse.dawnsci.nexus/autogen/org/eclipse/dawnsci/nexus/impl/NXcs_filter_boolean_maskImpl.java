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
 * Base class for packing and unpacking booleans.
 * The field mask should be constructed from packing a vector of booleans
 * (a bitfield) into unsigned integers with bytesize bitdepth. Padding to
 * an integer number of such integers is assumed.
 * Thereby, this base class can be used to inform software about necessary modulo
 * operations to decode the mask to recover e.g. set membership of objects in sets
 * whose membership has been encoded as a vector of booleans.
 * This is useful e.g. when processing object sets such as point cloud data.
 * If e.g. a spatial filter has been applied to a set of points, we may wish to document
 * memory-space efficiently which points were analyzed. An array of boolean values
 * is one option to achieve this. A value is true if the point is included and false otherwise.

 */
public class NXcs_filter_boolean_maskImpl extends NXobjectImpl implements NXcs_filter_boolean_mask {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXcs_filter_boolean_maskImpl() {
		super();
	}

	public NXcs_filter_boolean_maskImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcs_filter_boolean_mask.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CS_FILTER_BOOLEAN_MASK;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getDepends_on() {
		return getDataset(NX_DEPENDS_ON);
	}

	@Override
	public String getDepends_onScalar() {
		return getString(NX_DEPENDS_ON);
	}

	@Override
	public DataNode setDepends_on(IDataset depends_onDataset) {
		return setDataset(NX_DEPENDS_ON, depends_onDataset);
	}

	@Override
	public DataNode setDepends_onScalar(String depends_onValue) {
		return setString(NX_DEPENDS_ON, depends_onValue);
	}

	@Override
	public Dataset getNumber_of_objects() {
		return getDataset(NX_NUMBER_OF_OBJECTS);
	}

	@Override
	public Long getNumber_of_objectsScalar() {
		return getLong(NX_NUMBER_OF_OBJECTS);
	}

	@Override
	public DataNode setNumber_of_objects(IDataset number_of_objectsDataset) {
		return setDataset(NX_NUMBER_OF_OBJECTS, number_of_objectsDataset);
	}

	@Override
	public DataNode setNumber_of_objectsScalar(Long number_of_objectsValue) {
		return setField(NX_NUMBER_OF_OBJECTS, number_of_objectsValue);
	}

	@Override
	public Dataset getBitdepth() {
		return getDataset(NX_BITDEPTH);
	}

	@Override
	public Long getBitdepthScalar() {
		return getLong(NX_BITDEPTH);
	}

	@Override
	public DataNode setBitdepth(IDataset bitdepthDataset) {
		return setDataset(NX_BITDEPTH, bitdepthDataset);
	}

	@Override
	public DataNode setBitdepthScalar(Long bitdepthValue) {
		return setField(NX_BITDEPTH, bitdepthValue);
	}

	@Override
	public Dataset getMask() {
		return getDataset(NX_MASK);
	}

	@Override
	public Long getMaskScalar() {
		return getLong(NX_MASK);
	}

	@Override
	public DataNode setMask(IDataset maskDataset) {
		return setDataset(NX_MASK, maskDataset);
	}

	@Override
	public DataNode setMaskScalar(Long maskValue) {
		return setField(NX_MASK, maskValue);
	}

}

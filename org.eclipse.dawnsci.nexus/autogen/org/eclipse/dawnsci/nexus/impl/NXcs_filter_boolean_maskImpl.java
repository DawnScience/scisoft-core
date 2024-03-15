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
 * Computer science base class for packing and unpacking booleans.
 * One use case is processing of object sets (like point cloud data).
 * When one applies e.g. a spatial filter to a set of points to define which
 * points are analyzed and which not, it is useful to document which points were
 * taken. One can store this information in a compact manner with an array of
 * boolean values. If the value is True the point is taken, else it is not.
 * If the points are identified by an array of integer identifiers and an
 * arbitrary spatial filtering, the boolean array will be filled with True and False
 * values in an arbitrary manner. Especially when the number of points is large,
 * for instance several thousands and more, some situations can be more efficiently
 * stored if one would not store the boolean array but just list the identifiers
 * of the points taken. For instance if within a set of 1000 points only one point is
 * taken, it would take (naively) 4000 bits to store the array but only 32 bits
 * to store e.g. the ID of that taken point. Of course the 4000 bit field is so
 * sparse that it could be compressed resulting also in a substantial reduction
 * of the storage demands. Therefore boolean masks are useful compact descriptions
 * to store information about set memberships in a compact manner.
 * In general it is true, though, that which representation is best, i.e.
 * most compact (especially when compressed) depends strongly on occupation of
 * the array.
 * This base class just bookkeeps metadata to inform software about necessary
 * modulo operations to decode the set membership of each object. This is useful
 * because the number of objects not necessarily is an integer multiple of the bit depth.

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
	public IDataset getNumber_of_objects() {
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
	public IDataset getBitdepth() {
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
	public IDataset getMask() {
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

	@Override
	public IDataset getIdentifier() {
		return getDataset(NX_IDENTIFIER);
	}

	@Override
	public Long getIdentifierScalar() {
		return getLong(NX_IDENTIFIER);
	}

	@Override
	public DataNode setIdentifier(IDataset identifierDataset) {
		return setDataset(NX_IDENTIFIER, identifierDataset);
	}

	@Override
	public DataNode setIdentifierScalar(Long identifierValue) {
		return setField(NX_IDENTIFIER, identifierValue);
	}

}

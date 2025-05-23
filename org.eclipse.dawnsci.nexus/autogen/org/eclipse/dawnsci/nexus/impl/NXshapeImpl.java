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
 * legacy class - (used by :ref:`NXgeometry`) - the shape and size of a component.
 * This is the description of the general shape and size of a
 * component, which may be made up of ``numobj`` separate
 * elements - it is used by the :ref:`NXgeometry` class

 */
public class NXshapeImpl extends NXobjectImpl implements NXshape {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXshapeImpl() {
		super();
	}

	public NXshapeImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXshape.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_SHAPE;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getShape() {
		return getDataset(NX_SHAPE);
	}

	@Override
	public String getShapeScalar() {
		return getString(NX_SHAPE);
	}

	@Override
	public DataNode setShape(IDataset shapeDataset) {
		return setDataset(NX_SHAPE, shapeDataset);
	}

	@Override
	public DataNode setShapeScalar(String shapeValue) {
		return setString(NX_SHAPE, shapeValue);
	}

	@Override
	public Dataset getSize() {
		return getDataset(NX_SIZE);
	}

	@Override
	public Double getSizeScalar() {
		return getDouble(NX_SIZE);
	}

	@Override
	public DataNode setSize(IDataset sizeDataset) {
		return setDataset(NX_SIZE, sizeDataset);
	}

	@Override
	public DataNode setSizeScalar(Double sizeValue) {
		return setField(NX_SIZE, sizeValue);
	}

	@Override
	public Dataset getDirection() {
		return getDataset(NX_DIRECTION);
	}

	@Override
	public String getDirectionScalar() {
		return getString(NX_DIRECTION);
	}

	@Override
	public DataNode setDirection(IDataset directionDataset) {
		return setDataset(NX_DIRECTION, directionDataset);
	}

	@Override
	public DataNode setDirectionScalar(String directionValue) {
		return setString(NX_DIRECTION, directionValue);
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

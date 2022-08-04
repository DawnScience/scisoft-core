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
 * A simple slit.
 * For more complex geometries, :ref:`NXaperture` should be used.
 * 
 */
public class NXslitImpl extends NXobjectImpl implements NXslit {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXslitImpl() {
		super();
	}

	public NXslitImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXslit.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_SLIT;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}
	

	@Override
	public IDataset getDepends_on() {
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
	public IDataset getX_gap() {
		return getDataset(NX_X_GAP);
	}

	@Override
	public Number getX_gapScalar() {
		return getNumber(NX_X_GAP);
	}

	@Override
	public DataNode setX_gap(IDataset x_gapDataset) {
		return setDataset(NX_X_GAP, x_gapDataset);
	}

	@Override
	public DataNode setX_gapScalar(Number x_gapValue) {
		return setField(NX_X_GAP, x_gapValue);
	}

	@Override
	public IDataset getY_gap() {
		return getDataset(NX_Y_GAP);
	}

	@Override
	public Number getY_gapScalar() {
		return getNumber(NX_Y_GAP);
	}

	@Override
	public DataNode setY_gap(IDataset y_gapDataset) {
		return setDataset(NX_Y_GAP, y_gapDataset);
	}

	@Override
	public DataNode setY_gapScalar(Number y_gapValue) {
		return setField(NX_Y_GAP, y_gapValue);
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

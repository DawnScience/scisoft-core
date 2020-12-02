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
 * constructive solid geometry NeXus class, using :ref:`NXquadric`
 * and :ref:`NXoff_geometry`.
 * 
 */
public class NXcsgImpl extends NXobjectImpl implements NXcsg {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_CSG,
		NexusBaseClass.NX_CSG);

	public NXcsgImpl() {
		super();
	}

	public NXcsgImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcsg.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CSG;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}
	

	@Override
	public IDataset getOperation() {
		return getDataset(NX_OPERATION);
	}

	@Override
	public String getOperationScalar() {
		return getString(NX_OPERATION);
	}

	@Override
	public DataNode setOperation(IDataset operationDataset) {
		return setDataset(NX_OPERATION, operationDataset);
	}

	@Override
	public DataNode setOperationScalar(String operationValue) {
		return setString(NX_OPERATION, operationValue);
	}

	@Override
	public NXcsg getA() {
		// dataNodeName = NX_A
		return getChild("a", NXcsg.class);
	}

	@Override
	public void setA(NXcsg aGroup) {
		putChild("a", aGroup);
	}

	@Override
	public NXcsg getB() {
		// dataNodeName = NX_B
		return getChild("b", NXcsg.class);
	}

	@Override
	public void setB(NXcsg bGroup) {
		putChild("b", bGroup);
	}

	@Override
	public IDataset getGeometry() {
		return getDataset(NX_GEOMETRY);
	}

	@Override
	public String getGeometryScalar() {
		return getString(NX_GEOMETRY);
	}

	@Override
	public DataNode setGeometry(IDataset geometryDataset) {
		return setDataset(NX_GEOMETRY, geometryDataset);
	}

	@Override
	public DataNode setGeometryScalar(String geometryValue) {
		return setString(NX_GEOMETRY, geometryValue);
	}

}

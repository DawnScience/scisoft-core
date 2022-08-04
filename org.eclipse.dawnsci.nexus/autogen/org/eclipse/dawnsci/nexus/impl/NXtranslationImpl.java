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
 * legacy class - (used by :ref:`NXgeometry`) - general spatial location of a component.
 * 
 */
public class NXtranslationImpl extends NXobjectImpl implements NXtranslation {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_GEOMETRY);

	public NXtranslationImpl() {
		super();
	}

	public NXtranslationImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXtranslation.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_TRANSLATION;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}
	

	@Override
	public NXgeometry getGeometry() {
		// dataNodeName = NX_GEOMETRY
		return getChild("geometry", NXgeometry.class);
	}

	@Override
	public void setGeometry(NXgeometry geometryGroup) {
		putChild("geometry", geometryGroup);
	}

	@Override
	public IDataset getDistances() {
		return getDataset(NX_DISTANCES);
	}

	@Override
	public Double getDistancesScalar() {
		return getDouble(NX_DISTANCES);
	}

	@Override
	public DataNode setDistances(IDataset distancesDataset) {
		return setDataset(NX_DISTANCES, distancesDataset);
	}

	@Override
	public DataNode setDistancesScalar(Double distancesValue) {
		return setField(NX_DISTANCES, distancesValue);
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

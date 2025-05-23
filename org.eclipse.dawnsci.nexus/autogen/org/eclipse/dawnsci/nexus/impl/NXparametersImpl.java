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
 * Container for parameters, usually used in processing or analysis.

 */
public class NXparametersImpl extends NXobjectImpl implements NXparameters {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXparametersImpl() {
		super();
	}

	public NXparametersImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXparameters.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_PARAMETERS;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getTerm() {
		return getDataset(NX_TERM);
	}

	@Override
	public String getTermScalar() {
		return getString(NX_TERM);
	}

	@Override
	public DataNode setTerm(IDataset termDataset) {
		return setDataset(NX_TERM, termDataset);
	}

	@Override
	public DataNode setTermScalar(String termValue) {
		return setString(NX_TERM, termValue);
	}

	@Override
	public String getTermAttributeUnits() {
		return getAttrString(NX_TERM, NX_TERM_ATTRIBUTE_UNITS);
	}

	@Override
	public void setTermAttributeUnits(String unitsValue) {
		setAttribute(NX_TERM, NX_TERM_ATTRIBUTE_UNITS, unitsValue);
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

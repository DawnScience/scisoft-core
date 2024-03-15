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
 * Base class to describe a software tool or library.

 */
public class NXprogramImpl extends NXobjectImpl implements NXprogram {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXprogramImpl() {
		super();
	}

	public NXprogramImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXprogram.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_PROGRAM;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public IDataset getProgram() {
		return getDataset(NX_PROGRAM);
	}

	@Override
	public String getProgramScalar() {
		return getString(NX_PROGRAM);
	}

	@Override
	public DataNode setProgram(IDataset programDataset) {
		return setDataset(NX_PROGRAM, programDataset);
	}

	@Override
	public DataNode setProgramScalar(String programValue) {
		return setString(NX_PROGRAM, programValue);
	}

	@Override
	public String getProgramAttributeVersion() {
		return getAttrString(NX_PROGRAM, NX_PROGRAM_ATTRIBUTE_VERSION);
	}

	@Override
	public void setProgramAttributeVersion(String versionValue) {
		setAttribute(NX_PROGRAM, NX_PROGRAM_ATTRIBUTE_VERSION, versionValue);
	}

	@Override
	public String getProgramAttributeUrl() {
		return getAttrString(NX_PROGRAM, NX_PROGRAM_ATTRIBUTE_URL);
	}

	@Override
	public void setProgramAttributeUrl(String urlValue) {
		setAttribute(NX_PROGRAM, NX_PROGRAM_ATTRIBUTE_URL, urlValue);
	}

}

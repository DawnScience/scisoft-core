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
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * Container for reporting a set of images taken.

 */
public class NXimage_setImpl extends NXobjectImpl implements NXimage_set {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_PROCESS,
		NexusBaseClass.NX_DATA);

	public NXimage_setImpl() {
		super();
	}

	public NXimage_setImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXimage_set.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_IMAGE_SET;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public NXprocess getProcess() {
		// dataNodeName = NX_PROCESS
		return getChild("process", NXprocess.class);
	}

	@Override
	public void setProcess(NXprocess processGroup) {
		putChild("process", processGroup);
	}

	@Override
	public NXprocess getProcess(String name) {
		return getChild(name, NXprocess.class);
	}

	@Override
	public void setProcess(String name, NXprocess process) {
		putChild(name, process);
	}

	@Override
	public Map<String, NXprocess> getAllProcess() {
		return getChildren(NXprocess.class);
	}

	@Override
	public void setAllProcess(Map<String, NXprocess> process) {
		setChildren(process);
	}
	// Unprocessed group:

	@Override
	public NXdata getStack() {
		// dataNodeName = NX_STACK
		return getChild("stack", NXdata.class);
	}

	@Override
	public void setStack(NXdata stackGroup) {
		putChild("stack", stackGroup);
	}

}
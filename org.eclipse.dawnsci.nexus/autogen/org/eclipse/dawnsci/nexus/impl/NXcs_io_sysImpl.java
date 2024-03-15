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


import org.eclipse.dawnsci.nexus.*;

/**
 * Computer science description of system of a computer.

 */
public class NXcs_io_sysImpl extends NXobjectImpl implements NXcs_io_sys {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_CS_IO_OBJ);

	public NXcs_io_sysImpl() {
		super();
	}

	public NXcs_io_sysImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcs_io_sys.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CS_IO_SYS;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public NXcs_io_obj getCs_io_obj() {
		// dataNodeName = NX_CS_IO_OBJ
		return getChild("cs_io_obj", NXcs_io_obj.class);
	}

	@Override
	public void setCs_io_obj(NXcs_io_obj cs_io_objGroup) {
		putChild("cs_io_obj", cs_io_objGroup);
	}

	@Override
	public NXcs_io_obj getCs_io_obj(String name) {
		return getChild(name, NXcs_io_obj.class);
	}

	@Override
	public void setCs_io_obj(String name, NXcs_io_obj cs_io_obj) {
		putChild(name, cs_io_obj);
	}

	@Override
	public Map<String, NXcs_io_obj> getAllCs_io_obj() {
		return getChildren(NXcs_io_obj.class);
	}

	@Override
	public void setAllCs_io_obj(Map<String, NXcs_io_obj> cs_io_obj) {
		setChildren(cs_io_obj);
	}

}

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
 * Computer science description of a main memory system of a computer.

 */
public class NXcs_mm_sysImpl extends NXobjectImpl implements NXcs_mm_sys {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXcs_mm_sysImpl() {
		super();
	}

	public NXcs_mm_sysImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcs_mm_sys.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CS_MM_SYS;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public IDataset getTotal_physical_memory() {
		return getDataset(NX_TOTAL_PHYSICAL_MEMORY);
	}

	@Override
	public Number getTotal_physical_memoryScalar() {
		return getNumber(NX_TOTAL_PHYSICAL_MEMORY);
	}

	@Override
	public DataNode setTotal_physical_memory(IDataset total_physical_memoryDataset) {
		return setDataset(NX_TOTAL_PHYSICAL_MEMORY, total_physical_memoryDataset);
	}

	@Override
	public DataNode setTotal_physical_memoryScalar(Number total_physical_memoryValue) {
		return setField(NX_TOTAL_PHYSICAL_MEMORY, total_physical_memoryValue);
	}

}

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
 * Base class to report on the characterization of an area or volume of material.
 * This area or volume of material is considered a region-of-interest (ROI).
 * This base class should be used when the characterization was achieved by
 * processing data from experiment or computer simulations into models of
 * the microstructure of the material and the properties of the material or its
 * crystal defects within this ROI. Microstructural features is a narrow synonym
 * for these crystal defects.
 * This base class can also be used to store data and metadata of the
 * representation of the ROI, i.e. its discretization and shape.
 * Methods from computational geometry are typically used for
 * defining a discretization of the area and volume.
 * Do not confuse this base class with :ref:`NXregion`. The purpose
 * of the :ref:`NXregion` base class is to document data access i.e.
 * I/O pattern on arrays. Therefore, concepts from :ref:`NXregion` operate
 * in data space rather than in real or simulated real space.

 */
public class NXroi_processImpl extends NXprocessImpl implements NXroi_process {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_PROCESS);

	public NXroi_processImpl() {
		super();
	}

	public NXroi_processImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXroi_process.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_ROI_PROCESS;
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

}

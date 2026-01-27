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
import org.eclipse.january.dataset.Dataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * Base class container for reporting a set of spectra.
 * The mostly commonly used scanning methods are supported. That is one-,
 * two-, three-dimensional ROIs discretized using regular Euclidean tilings.
 * Use stack for all other tilings.

 */
public class NXspectrumImpl extends NXobjectImpl implements NXspectrum {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_PROCESS,
		NexusBaseClass.NX_DATA,
		NexusBaseClass.NX_DATA,
		NexusBaseClass.NX_DATA,
		NexusBaseClass.NX_DATA,
		NexusBaseClass.NX_DATA,
		NexusBaseClass.NX_DATA,
		NexusBaseClass.NX_DATA);

	public NXspectrumImpl() {
		super();
	}

	public NXspectrumImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXspectrum.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_SPECTRUM;
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
	// Unprocessed group: input
	// Unprocessed group:

	@Override
	public NXdata getSpectrum_0d() {
		// dataNodeName = NX_SPECTRUM_0D
		return getChild("spectrum_0d", NXdata.class);
	}

	@Override
	public void setSpectrum_0d(NXdata spectrum_0dGroup) {
		putChild("spectrum_0d", spectrum_0dGroup);
	}

	@Override
	public NXdata getSpectrum_1d() {
		// dataNodeName = NX_SPECTRUM_1D
		return getChild("spectrum_1d", NXdata.class);
	}

	@Override
	public void setSpectrum_1d(NXdata spectrum_1dGroup) {
		putChild("spectrum_1d", spectrum_1dGroup);
	}

	@Override
	public NXdata getSpectrum_2d() {
		// dataNodeName = NX_SPECTRUM_2D
		return getChild("spectrum_2d", NXdata.class);
	}

	@Override
	public void setSpectrum_2d(NXdata spectrum_2dGroup) {
		putChild("spectrum_2d", spectrum_2dGroup);
	}

	@Override
	public NXdata getSpectrum_3d() {
		// dataNodeName = NX_SPECTRUM_3D
		return getChild("spectrum_3d", NXdata.class);
	}

	@Override
	public void setSpectrum_3d(NXdata spectrum_3dGroup) {
		putChild("spectrum_3d", spectrum_3dGroup);
	}

	@Override
	public NXdata getStack_0d() {
		// dataNodeName = NX_STACK_0D
		return getChild("stack_0d", NXdata.class);
	}

	@Override
	public void setStack_0d(NXdata stack_0dGroup) {
		putChild("stack_0d", stack_0dGroup);
	}

	@Override
	public NXdata getStack_2d() {
		// dataNodeName = NX_STACK_2D
		return getChild("stack_2d", NXdata.class);
	}

	@Override
	public void setStack_2d(NXdata stack_2dGroup) {
		putChild("stack_2d", stack_2dGroup);
	}

	@Override
	public NXdata getStack_3d() {
		// dataNodeName = NX_STACK_3D
		return getChild("stack_3d", NXdata.class);
	}

	@Override
	public void setStack_3d(NXdata stack_3dGroup) {
		putChild("stack_3d", stack_3dGroup);
	}

}

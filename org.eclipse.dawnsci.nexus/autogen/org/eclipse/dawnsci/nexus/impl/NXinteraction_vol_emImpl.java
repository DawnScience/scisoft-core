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
 * Base class for storing details about a modelled shape of interaction volume.
 * The interaction volume is mainly relevant in scanning electron microscopy
 * when the sample is thick enough so that the beam is unable to illuminate
 * through the specimen.
 * Computer models like Monte Carlo or molecular dynamics / electron beam
 * interaction simulations can be used to qualify and/or quantify the shape of
 * the interaction volume.
 * Explicit or implicit descriptions are possible.
 * * An implicit description is via a set of electron/specimen interactions
 * represented ideally as trajectory data from the computer simulation.
 * * An explicit description is via an iso-contour surface using either
 * a simulation grid or a triangulated surface mesh of the approximated
 * iso-contour surface evaluated at specific threshold values.
 * Iso-contours could be computed from electron or particle fluxes through
 * an imaginary control surface (the iso-surface).
 * Threshold values can be defined by particles passing through a unit control
 * volume (electrons) or energy-levels (e.g. the case of X-rays).
 * Details depend on the model.
 * * Another explicit description is via theoretical models which may
 * be relevant e.g. for X-ray spectroscopy
 * Further details on how the interaction volume can be quantified
 * is available in the literature for example:
 * * `S. Richter et al. <https://doi.org/10.1088/1757-899X/109/1/012014>`_
 * * `J. Bünger et al. <https://doi.org/10.1017/S1431927622000083>`_
 * 
 */
public class NXinteraction_vol_emImpl extends NXobjectImpl implements NXinteraction_vol_em {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_DATA,
		NexusBaseClass.NX_PROCESS);

	public NXinteraction_vol_emImpl() {
		super();
	}

	public NXinteraction_vol_emImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXinteraction_vol_em.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_INTERACTION_VOL_EM;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}
	

	@Override
	public NXdata getData() {
		// dataNodeName = NX_DATA
		return getChild("data", NXdata.class);
	}

	@Override
	public void setData(NXdata dataGroup) {
		putChild("data", dataGroup);
	}

	@Override
	public NXdata getData(String name) {
		return getChild(name, NXdata.class);
	}

	@Override
	public void setData(String name, NXdata data) {
		putChild(name, data);
	}

	@Override
	public Map<String, NXdata> getAllData() {
		return getChildren(NXdata.class);
	}
	
	@Override
	public void setAllData(Map<String, NXdata> data) {
		setChildren(data);
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

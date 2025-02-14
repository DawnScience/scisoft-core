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
 * Conventions for rotations and coordinate systems to interpret EBSD data.
 * This is the main issue which currently is not in all cases documented
 * and thus limits the interoperability and value of collected EBSD data.
 * Not communicating EBSD data with such contextual pieces of information
 * and the use of file formats which do not store this information is the
 * key unsolved problem.

 */
public class NXem_ebsd_conventionsImpl extends NXobjectImpl implements NXem_ebsd_conventions {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_PROCESS,
		NexusBaseClass.NX_PROCESS,
		NexusBaseClass.NX_PROCESS,
		NexusBaseClass.NX_PROCESS,
		NexusBaseClass.NX_PROCESS,
		NexusBaseClass.NX_PROCESS);

	public NXem_ebsd_conventionsImpl() {
		super();
	}

	public NXem_ebsd_conventionsImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXem_ebsd_conventions.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_EM_EBSD_CONVENTIONS;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public NXprocess getRotation_conventions() {
		// dataNodeName = NX_ROTATION_CONVENTIONS
		return getChild("rotation_conventions", NXprocess.class);
	}

	@Override
	public void setRotation_conventions(NXprocess rotation_conventionsGroup) {
		putChild("rotation_conventions", rotation_conventionsGroup);
	}

	@Override
	public NXprocess getProcessing_reference_frame() {
		// dataNodeName = NX_PROCESSING_REFERENCE_FRAME
		return getChild("processing_reference_frame", NXprocess.class);
	}

	@Override
	public void setProcessing_reference_frame(NXprocess processing_reference_frameGroup) {
		putChild("processing_reference_frame", processing_reference_frameGroup);
	}

	@Override
	public NXprocess getSample_reference_frame() {
		// dataNodeName = NX_SAMPLE_REFERENCE_FRAME
		return getChild("sample_reference_frame", NXprocess.class);
	}

	@Override
	public void setSample_reference_frame(NXprocess sample_reference_frameGroup) {
		putChild("sample_reference_frame", sample_reference_frameGroup);
	}

	@Override
	public NXprocess getDetector_reference_frame() {
		// dataNodeName = NX_DETECTOR_REFERENCE_FRAME
		return getChild("detector_reference_frame", NXprocess.class);
	}

	@Override
	public void setDetector_reference_frame(NXprocess detector_reference_frameGroup) {
		putChild("detector_reference_frame", detector_reference_frameGroup);
	}

	@Override
	public NXprocess getGnomonic_projection_reference_frame() {
		// dataNodeName = NX_GNOMONIC_PROJECTION_REFERENCE_FRAME
		return getChild("gnomonic_projection_reference_frame", NXprocess.class);
	}

	@Override
	public void setGnomonic_projection_reference_frame(NXprocess gnomonic_projection_reference_frameGroup) {
		putChild("gnomonic_projection_reference_frame", gnomonic_projection_reference_frameGroup);
	}

	@Override
	public NXprocess getPattern_centre() {
		// dataNodeName = NX_PATTERN_CENTRE
		return getChild("pattern_centre", NXprocess.class);
	}

	@Override
	public void setPattern_centre(NXprocess pattern_centreGroup) {
		putChild("pattern_centre", pattern_centreGroup);
	}

}

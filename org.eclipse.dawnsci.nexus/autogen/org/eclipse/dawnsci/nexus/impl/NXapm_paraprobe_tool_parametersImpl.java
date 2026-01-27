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
 * Base class documenting parameters for processing used by all tools of the
 * paraprobe-toolbox.

 */
public class NXapm_paraprobe_tool_parametersImpl extends NXparametersImpl implements NXapm_paraprobe_tool_parameters {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_NOTE,
		NexusBaseClass.NX_NOTE,
		NexusBaseClass.NX_NOTE,
		NexusBaseClass.NX_NOTE,
		NexusBaseClass.NX_SPATIAL_FILTER,
		NexusBaseClass.NX_SUBSAMPLING_FILTER,
		NexusBaseClass.NX_MATCH_FILTER,
		NexusBaseClass.NX_MATCH_FILTER);

	public NXapm_paraprobe_tool_parametersImpl() {
		super();
	}

	public NXapm_paraprobe_tool_parametersImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXapm_paraprobe_tool_parameters.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_APM_PARAPROBE_TOOL_PARAMETERS;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getIdentifier_analysis() {
		return getDataset(NX_IDENTIFIER_ANALYSIS);
	}

	@Override
	public Long getIdentifier_analysisScalar() {
		return getLong(NX_IDENTIFIER_ANALYSIS);
	}

	@Override
	public DataNode setIdentifier_analysis(IDataset identifier_analysisDataset) {
		return setDataset(NX_IDENTIFIER_ANALYSIS, identifier_analysisDataset);
	}

	@Override
	public DataNode setIdentifier_analysisScalar(Long identifier_analysisValue) {
		return setField(NX_IDENTIFIER_ANALYSIS, identifier_analysisValue);
	}

	@Override
	public Dataset getDescription() {
		return getDataset(NX_DESCRIPTION);
	}

	@Override
	public String getDescriptionScalar() {
		return getString(NX_DESCRIPTION);
	}

	@Override
	public DataNode setDescription(IDataset descriptionDataset) {
		return setDataset(NX_DESCRIPTION, descriptionDataset);
	}

	@Override
	public DataNode setDescriptionScalar(String descriptionValue) {
		return setString(NX_DESCRIPTION, descriptionValue);
	}

	@Override
	public NXnote getReconstruction() {
		// dataNodeName = NX_RECONSTRUCTION
		return getChild("reconstruction", NXnote.class);
	}

	@Override
	public void setReconstruction(NXnote reconstructionGroup) {
		putChild("reconstruction", reconstructionGroup);
	}

	@Override
	public NXnote getRanging() {
		// dataNodeName = NX_RANGING
		return getChild("ranging", NXnote.class);
	}

	@Override
	public void setRanging(NXnote rangingGroup) {
		putChild("ranging", rangingGroup);
	}

	@Override
	public NXnote getSurface() {
		// dataNodeName = NX_SURFACE
		return getChild("surface", NXnote.class);
	}

	@Override
	public void setSurface(NXnote surfaceGroup) {
		putChild("surface", surfaceGroup);
	}

	@Override
	public NXnote getSurface_distance() {
		// dataNodeName = NX_SURFACE_DISTANCE
		return getChild("surface_distance", NXnote.class);
	}

	@Override
	public void setSurface_distance(NXnote surface_distanceGroup) {
		putChild("surface_distance", surface_distanceGroup);
	}

	@Override
	public NXspatial_filter getSpatial_filter() {
		// dataNodeName = NX_SPATIAL_FILTER
		return getChild("spatial_filter", NXspatial_filter.class);
	}

	@Override
	public void setSpatial_filter(NXspatial_filter spatial_filterGroup) {
		putChild("spatial_filter", spatial_filterGroup);
	}

	@Override
	public NXsubsampling_filter getEvaporation_id_filter() {
		// dataNodeName = NX_EVAPORATION_ID_FILTER
		return getChild("evaporation_id_filter", NXsubsampling_filter.class);
	}

	@Override
	public void setEvaporation_id_filter(NXsubsampling_filter evaporation_id_filterGroup) {
		putChild("evaporation_id_filter", evaporation_id_filterGroup);
	}

	@Override
	public NXmatch_filter getIontype_filter() {
		// dataNodeName = NX_IONTYPE_FILTER
		return getChild("iontype_filter", NXmatch_filter.class);
	}

	@Override
	public void setIontype_filter(NXmatch_filter iontype_filterGroup) {
		putChild("iontype_filter", iontype_filterGroup);
	}

	@Override
	public NXmatch_filter getHit_multiplicity_filter() {
		// dataNodeName = NX_HIT_MULTIPLICITY_FILTER
		return getChild("hit_multiplicity_filter", NXmatch_filter.class);
	}

	@Override
	public void setHit_multiplicity_filter(NXmatch_filter hit_multiplicity_filterGroup) {
		putChild("hit_multiplicity_filter", hit_multiplicity_filterGroup);
	}

}

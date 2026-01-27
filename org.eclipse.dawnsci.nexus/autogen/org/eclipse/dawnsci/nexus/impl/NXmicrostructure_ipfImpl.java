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
 * Base class to store an inverse pole figure (IPF) mapping (IPF map).

 */
public class NXmicrostructure_ipfImpl extends NXprocessImpl implements NXmicrostructure_ipf {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_CG_GRID,
		NexusBaseClass.NX_CG_GRID,
		NexusBaseClass.NX_DATA,
		NexusBaseClass.NX_DATA);

	public NXmicrostructure_ipfImpl() {
		super();
	}

	public NXmicrostructure_ipfImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXmicrostructure_ipf.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_MICROSTRUCTURE_IPF;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getDepends_on() {
		return getDataset(NX_DEPENDS_ON);
	}

	@Override
	public String getDepends_onScalar() {
		return getString(NX_DEPENDS_ON);
	}

	@Override
	public DataNode setDepends_on(IDataset depends_onDataset) {
		return setDataset(NX_DEPENDS_ON, depends_onDataset);
	}

	@Override
	public DataNode setDepends_onScalar(String depends_onValue) {
		return setString(NX_DEPENDS_ON, depends_onValue);
	}

	@Override
	public Dataset getColor_model() {
		return getDataset(NX_COLOR_MODEL);
	}

	@Override
	public String getColor_modelScalar() {
		return getString(NX_COLOR_MODEL);
	}

	@Override
	public DataNode setColor_model(IDataset color_modelDataset) {
		return setDataset(NX_COLOR_MODEL, color_modelDataset);
	}

	@Override
	public DataNode setColor_modelScalar(String color_modelValue) {
		return setString(NX_COLOR_MODEL, color_modelValue);
	}

	@Override
	public Dataset getProjection_direction() {
		return getDataset(NX_PROJECTION_DIRECTION);
	}

	@Override
	public Number getProjection_directionScalar() {
		return getNumber(NX_PROJECTION_DIRECTION);
	}

	@Override
	public DataNode setProjection_direction(IDataset projection_directionDataset) {
		return setDataset(NX_PROJECTION_DIRECTION, projection_directionDataset);
	}

	@Override
	public DataNode setProjection_directionScalar(Number projection_directionValue) {
		return setField(NX_PROJECTION_DIRECTION, projection_directionValue);
	}

	@Override
	public String getProjection_directionAttributeDepends_on() {
		return getAttrString(NX_PROJECTION_DIRECTION, NX_PROJECTION_DIRECTION_ATTRIBUTE_DEPENDS_ON);
	}

	@Override
	public void setProjection_directionAttributeDepends_on(String depends_onValue) {
		setAttribute(NX_PROJECTION_DIRECTION, NX_PROJECTION_DIRECTION_ATTRIBUTE_DEPENDS_ON, depends_onValue);
	}

	@Override
	public NXcg_grid getInput_grid() {
		// dataNodeName = NX_INPUT_GRID
		return getChild("input_grid", NXcg_grid.class);
	}

	@Override
	public void setInput_grid(NXcg_grid input_gridGroup) {
		putChild("input_grid", input_gridGroup);
	}

	@Override
	public NXcg_grid getOutput_grid() {
		// dataNodeName = NX_OUTPUT_GRID
		return getChild("output_grid", NXcg_grid.class);
	}

	@Override
	public void setOutput_grid(NXcg_grid output_gridGroup) {
		putChild("output_grid", output_gridGroup);
	}

	@Override
	public Dataset getInterpolation() {
		return getDataset(NX_INTERPOLATION);
	}

	@Override
	public String getInterpolationScalar() {
		return getString(NX_INTERPOLATION);
	}

	@Override
	public DataNode setInterpolation(IDataset interpolationDataset) {
		return setDataset(NX_INTERPOLATION, interpolationDataset);
	}

	@Override
	public DataNode setInterpolationScalar(String interpolationValue) {
		return setString(NX_INTERPOLATION, interpolationValue);
	}

	@Override
	public NXdata getMap() {
		// dataNodeName = NX_MAP
		return getChild("map", NXdata.class);
	}

	@Override
	public void setMap(NXdata mapGroup) {
		putChild("map", mapGroup);
	}

	@Override
	public NXdata getLegend() {
		// dataNodeName = NX_LEGEND
		return getChild("legend", NXdata.class);
	}

	@Override
	public void setLegend(NXdata legendGroup) {
		putChild("legend", legendGroup);
	}

}

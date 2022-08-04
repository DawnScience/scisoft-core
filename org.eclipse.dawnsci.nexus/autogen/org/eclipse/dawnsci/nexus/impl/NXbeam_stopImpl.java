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
 * A device that blocks the beam completely, usually to protect a detector.
 * Beamstops and their positions are important for SANS
 * and SAXS experiments.
 * 
 */
public class NXbeam_stopImpl extends NXobjectImpl implements NXbeam_stop {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_GEOMETRY);

	public NXbeam_stopImpl() {
		super();
	}

	public NXbeam_stopImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXbeam_stop.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_BEAM_STOP;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}
	

	@Override
	public NXgeometry getGeometry() {
		// dataNodeName = NX_GEOMETRY
		return getChild("geometry", NXgeometry.class);
	}

	@Override
	public void setGeometry(NXgeometry geometryGroup) {
		putChild("geometry", geometryGroup);
	}

	@Override
	public NXgeometry getGeometry(String name) {
		return getChild(name, NXgeometry.class);
	}

	@Override
	public void setGeometry(String name, NXgeometry geometry) {
		putChild(name, geometry);
	}

	@Override
	public Map<String, NXgeometry> getAllGeometry() {
		return getChildren(NXgeometry.class);
	}
	
	@Override
	public void setAllGeometry(Map<String, NXgeometry> geometry) {
		setChildren(geometry);
	}

	@Override
	public IDataset getDescription() {
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
	public IDataset getSize() {
		return getDataset(NX_SIZE);
	}

	@Override
	public Double getSizeScalar() {
		return getDouble(NX_SIZE);
	}

	@Override
	public DataNode setSize(IDataset sizeDataset) {
		return setDataset(NX_SIZE, sizeDataset);
	}

	@Override
	public DataNode setSizeScalar(Double sizeValue) {
		return setField(NX_SIZE, sizeValue);
	}

	@Override
	public IDataset getX() {
		return getDataset(NX_X);
	}

	@Override
	public Double getXScalar() {
		return getDouble(NX_X);
	}

	@Override
	public DataNode setX(IDataset xDataset) {
		return setDataset(NX_X, xDataset);
	}

	@Override
	public DataNode setXScalar(Double xValue) {
		return setField(NX_X, xValue);
	}

	@Override
	public IDataset getY() {
		return getDataset(NX_Y);
	}

	@Override
	public Double getYScalar() {
		return getDouble(NX_Y);
	}

	@Override
	public DataNode setY(IDataset yDataset) {
		return setDataset(NX_Y, yDataset);
	}

	@Override
	public DataNode setYScalar(Double yValue) {
		return setField(NX_Y, yValue);
	}

	@Override
	public IDataset getDistance_to_detector() {
		return getDataset(NX_DISTANCE_TO_DETECTOR);
	}

	@Override
	public Double getDistance_to_detectorScalar() {
		return getDouble(NX_DISTANCE_TO_DETECTOR);
	}

	@Override
	public DataNode setDistance_to_detector(IDataset distance_to_detectorDataset) {
		return setDataset(NX_DISTANCE_TO_DETECTOR, distance_to_detectorDataset);
	}

	@Override
	public DataNode setDistance_to_detectorScalar(Double distance_to_detectorValue) {
		return setField(NX_DISTANCE_TO_DETECTOR, distance_to_detectorValue);
	}

	@Override
	public IDataset getStatus() {
		return getDataset(NX_STATUS);
	}

	@Override
	public String getStatusScalar() {
		return getString(NX_STATUS);
	}

	@Override
	public DataNode setStatus(IDataset statusDataset) {
		return setDataset(NX_STATUS, statusDataset);
	}

	@Override
	public DataNode setStatusScalar(String statusValue) {
		return setString(NX_STATUS, statusValue);
	}

	@Override
	public String getAttributeDefault() {
		return getAttrString(null, NX_ATTRIBUTE_DEFAULT);
	}

	@Override
	public void setAttributeDefault(String defaultValue) {
		setAttribute(null, NX_ATTRIBUTE_DEFAULT, defaultValue);
	}

}

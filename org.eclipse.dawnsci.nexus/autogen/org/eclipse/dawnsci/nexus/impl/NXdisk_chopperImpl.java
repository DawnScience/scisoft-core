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

import java.util.Date;
import java.util.Set;
import java.util.EnumSet;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * A device blocking the beam in a temporal periodic pattern.
 * A disk which blocks the beam but has one or more slits to periodically
 * let neutrons through as the disk rotates. Often used in pairs, one
 * NXdisk_chopper should be defined for each disk.
 * The rotation of the disk is commonly monitored by recording a timestamp for
 * each full rotation of disk, by having a sensor in the stationary disk housing
 * sensing when it is aligned with a feature (such as a magnet) on the disk.
 * We refer to this below as the "top-dead-center signal".
 * Angles and positive rotation speeds are measured in an anticlockwise
 * direction when facing away from the source.
 * 
 */
public class NXdisk_chopperImpl extends NXobjectImpl implements NXdisk_chopper {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_GEOMETRY);

	public NXdisk_chopperImpl() {
		super();
	}

	public NXdisk_chopperImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXdisk_chopper.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_DISK_CHOPPER;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}
	

	@Override
	public IDataset getType() {
		return getDataset(NX_TYPE);
	}

	@Override
	public String getTypeScalar() {
		return getString(NX_TYPE);
	}

	@Override
	public DataNode setType(IDataset typeDataset) {
		return setDataset(NX_TYPE, typeDataset);
	}

	@Override
	public DataNode setTypeScalar(String typeValue) {
		return setString(NX_TYPE, typeValue);
	}

	@Override
	public IDataset getRotation_speed() {
		return getDataset(NX_ROTATION_SPEED);
	}

	@Override
	public Double getRotation_speedScalar() {
		return getDouble(NX_ROTATION_SPEED);
	}

	@Override
	public DataNode setRotation_speed(IDataset rotation_speedDataset) {
		return setDataset(NX_ROTATION_SPEED, rotation_speedDataset);
	}

	@Override
	public DataNode setRotation_speedScalar(Double rotation_speedValue) {
		return setField(NX_ROTATION_SPEED, rotation_speedValue);
	}

	@Override
	public IDataset getSlits() {
		return getDataset(NX_SLITS);
	}

	@Override
	public Long getSlitsScalar() {
		return getLong(NX_SLITS);
	}

	@Override
	public DataNode setSlits(IDataset slitsDataset) {
		return setDataset(NX_SLITS, slitsDataset);
	}

	@Override
	public DataNode setSlitsScalar(Long slitsValue) {
		return setField(NX_SLITS, slitsValue);
	}

	@Override
	public IDataset getSlit_angle() {
		return getDataset(NX_SLIT_ANGLE);
	}

	@Override
	public Double getSlit_angleScalar() {
		return getDouble(NX_SLIT_ANGLE);
	}

	@Override
	public DataNode setSlit_angle(IDataset slit_angleDataset) {
		return setDataset(NX_SLIT_ANGLE, slit_angleDataset);
	}

	@Override
	public DataNode setSlit_angleScalar(Double slit_angleValue) {
		return setField(NX_SLIT_ANGLE, slit_angleValue);
	}

	@Override
	public IDataset getPair_separation() {
		return getDataset(NX_PAIR_SEPARATION);
	}

	@Override
	public Double getPair_separationScalar() {
		return getDouble(NX_PAIR_SEPARATION);
	}

	@Override
	public DataNode setPair_separation(IDataset pair_separationDataset) {
		return setDataset(NX_PAIR_SEPARATION, pair_separationDataset);
	}

	@Override
	public DataNode setPair_separationScalar(Double pair_separationValue) {
		return setField(NX_PAIR_SEPARATION, pair_separationValue);
	}

	@Override
	public IDataset getSlit_edges() {
		return getDataset(NX_SLIT_EDGES);
	}

	@Override
	public Double getSlit_edgesScalar() {
		return getDouble(NX_SLIT_EDGES);
	}

	@Override
	public DataNode setSlit_edges(IDataset slit_edgesDataset) {
		return setDataset(NX_SLIT_EDGES, slit_edgesDataset);
	}

	@Override
	public DataNode setSlit_edgesScalar(Double slit_edgesValue) {
		return setField(NX_SLIT_EDGES, slit_edgesValue);
	}

	@Override
	public IDataset getTop_dead_center() {
		return getDataset(NX_TOP_DEAD_CENTER);
	}

	@Override
	public Number getTop_dead_centerScalar() {
		return getNumber(NX_TOP_DEAD_CENTER);
	}

	@Override
	public DataNode setTop_dead_center(IDataset top_dead_centerDataset) {
		return setDataset(NX_TOP_DEAD_CENTER, top_dead_centerDataset);
	}

	@Override
	public DataNode setTop_dead_centerScalar(Number top_dead_centerValue) {
		return setField(NX_TOP_DEAD_CENTER, top_dead_centerValue);
	}

	@Override
	public Date getTop_dead_centerAttributeStart() {
		return getAttrDate(NX_TOP_DEAD_CENTER, NX_TOP_DEAD_CENTER_ATTRIBUTE_START);
	}

	@Override
	public void setTop_dead_centerAttributeStart(Date startValue) {
		setAttribute(NX_TOP_DEAD_CENTER, NX_TOP_DEAD_CENTER_ATTRIBUTE_START, startValue);
	}

	@Override
	public IDataset getBeam_position() {
		return getDataset(NX_BEAM_POSITION);
	}

	@Override
	public Double getBeam_positionScalar() {
		return getDouble(NX_BEAM_POSITION);
	}

	@Override
	public DataNode setBeam_position(IDataset beam_positionDataset) {
		return setDataset(NX_BEAM_POSITION, beam_positionDataset);
	}

	@Override
	public DataNode setBeam_positionScalar(Double beam_positionValue) {
		return setField(NX_BEAM_POSITION, beam_positionValue);
	}

	@Override
	public IDataset getRadius() {
		return getDataset(NX_RADIUS);
	}

	@Override
	public Double getRadiusScalar() {
		return getDouble(NX_RADIUS);
	}

	@Override
	public DataNode setRadius(IDataset radiusDataset) {
		return setDataset(NX_RADIUS, radiusDataset);
	}

	@Override
	public DataNode setRadiusScalar(Double radiusValue) {
		return setField(NX_RADIUS, radiusValue);
	}

	@Override
	public IDataset getSlit_height() {
		return getDataset(NX_SLIT_HEIGHT);
	}

	@Override
	public Double getSlit_heightScalar() {
		return getDouble(NX_SLIT_HEIGHT);
	}

	@Override
	public DataNode setSlit_height(IDataset slit_heightDataset) {
		return setDataset(NX_SLIT_HEIGHT, slit_heightDataset);
	}

	@Override
	public DataNode setSlit_heightScalar(Double slit_heightValue) {
		return setField(NX_SLIT_HEIGHT, slit_heightValue);
	}

	@Override
	public IDataset getPhase() {
		return getDataset(NX_PHASE);
	}

	@Override
	public Double getPhaseScalar() {
		return getDouble(NX_PHASE);
	}

	@Override
	public DataNode setPhase(IDataset phaseDataset) {
		return setDataset(NX_PHASE, phaseDataset);
	}

	@Override
	public DataNode setPhaseScalar(Double phaseValue) {
		return setField(NX_PHASE, phaseValue);
	}

	@Override
	public IDataset getDelay() {
		return getDataset(NX_DELAY);
	}

	@Override
	public Number getDelayScalar() {
		return getNumber(NX_DELAY);
	}

	@Override
	public DataNode setDelay(IDataset delayDataset) {
		return setDataset(NX_DELAY, delayDataset);
	}

	@Override
	public DataNode setDelayScalar(Number delayValue) {
		return setField(NX_DELAY, delayValue);
	}

	@Override
	public IDataset getRatio() {
		return getDataset(NX_RATIO);
	}

	@Override
	public Long getRatioScalar() {
		return getLong(NX_RATIO);
	}

	@Override
	public DataNode setRatio(IDataset ratioDataset) {
		return setDataset(NX_RATIO, ratioDataset);
	}

	@Override
	public DataNode setRatioScalar(Long ratioValue) {
		return setField(NX_RATIO, ratioValue);
	}

	@Override
	public IDataset getDistance() {
		return getDataset(NX_DISTANCE);
	}

	@Override
	public Double getDistanceScalar() {
		return getDouble(NX_DISTANCE);
	}

	@Override
	public DataNode setDistance(IDataset distanceDataset) {
		return setDataset(NX_DISTANCE, distanceDataset);
	}

	@Override
	public DataNode setDistanceScalar(Double distanceValue) {
		return setField(NX_DISTANCE, distanceValue);
	}

	@Override
	public IDataset getWavelength_range() {
		return getDataset(NX_WAVELENGTH_RANGE);
	}

	@Override
	public Double getWavelength_rangeScalar() {
		return getDouble(NX_WAVELENGTH_RANGE);
	}

	@Override
	public DataNode setWavelength_range(IDataset wavelength_rangeDataset) {
		return setDataset(NX_WAVELENGTH_RANGE, wavelength_rangeDataset);
	}

	@Override
	public DataNode setWavelength_rangeScalar(Double wavelength_rangeValue) {
		return setField(NX_WAVELENGTH_RANGE, wavelength_rangeValue);
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
	public String getAttributeDefault() {
		return getAttrString(null, NX_ATTRIBUTE_DEFAULT);
	}

	@Override
	public void setAttributeDefault(String defaultValue) {
		setAttribute(null, NX_ATTRIBUTE_DEFAULT, defaultValue);
	}

}

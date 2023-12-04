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
 * A neutron moderator
 * 
 */
public class NXmoderatorImpl extends NXobjectImpl implements NXmoderator {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_GEOMETRY,
		NexusBaseClass.NX_LOG,
		NexusBaseClass.NX_DATA,
		NexusBaseClass.NX_OFF_GEOMETRY,
		NexusBaseClass.NX_TRANSFORMATIONS);

	public NXmoderatorImpl() {
		super();
	}

	public NXmoderatorImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXmoderator.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_MODERATOR;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}
	

	@Override
	@Deprecated
	public NXgeometry getGeometry() {
		// dataNodeName = NX_GEOMETRY
		return getChild("geometry", NXgeometry.class);
	}

	@Override
	@Deprecated
	public void setGeometry(NXgeometry geometryGroup) {
		putChild("geometry", geometryGroup);
	}

	@Override
	@Deprecated
	public NXgeometry getGeometry(String name) {
		return getChild(name, NXgeometry.class);
	}

	@Override
	@Deprecated
	public void setGeometry(String name, NXgeometry geometry) {
		putChild(name, geometry);
	}

	@Override
	@Deprecated
	public Map<String, NXgeometry> getAllGeometry() {
		return getChildren(NXgeometry.class);
	}
	
	@Override
	@Deprecated
	public void setAllGeometry(Map<String, NXgeometry> geometry) {
		setChildren(geometry);
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
	public IDataset getPoison_depth() {
		return getDataset(NX_POISON_DEPTH);
	}

	@Override
	public Double getPoison_depthScalar() {
		return getDouble(NX_POISON_DEPTH);
	}

	@Override
	public DataNode setPoison_depth(IDataset poison_depthDataset) {
		return setDataset(NX_POISON_DEPTH, poison_depthDataset);
	}

	@Override
	public DataNode setPoison_depthScalar(Double poison_depthValue) {
		return setField(NX_POISON_DEPTH, poison_depthValue);
	}

	@Override
	public IDataset getCoupled() {
		return getDataset(NX_COUPLED);
	}

	@Override
	public Boolean getCoupledScalar() {
		return getBoolean(NX_COUPLED);
	}

	@Override
	public DataNode setCoupled(IDataset coupledDataset) {
		return setDataset(NX_COUPLED, coupledDataset);
	}

	@Override
	public DataNode setCoupledScalar(Boolean coupledValue) {
		return setField(NX_COUPLED, coupledValue);
	}

	@Override
	public IDataset getCoupling_material() {
		return getDataset(NX_COUPLING_MATERIAL);
	}

	@Override
	public String getCoupling_materialScalar() {
		return getString(NX_COUPLING_MATERIAL);
	}

	@Override
	public DataNode setCoupling_material(IDataset coupling_materialDataset) {
		return setDataset(NX_COUPLING_MATERIAL, coupling_materialDataset);
	}

	@Override
	public DataNode setCoupling_materialScalar(String coupling_materialValue) {
		return setString(NX_COUPLING_MATERIAL, coupling_materialValue);
	}

	@Override
	public IDataset getPoison_material() {
		return getDataset(NX_POISON_MATERIAL);
	}

	@Override
	public String getPoison_materialScalar() {
		return getString(NX_POISON_MATERIAL);
	}

	@Override
	public DataNode setPoison_material(IDataset poison_materialDataset) {
		return setDataset(NX_POISON_MATERIAL, poison_materialDataset);
	}

	@Override
	public DataNode setPoison_materialScalar(String poison_materialValue) {
		return setString(NX_POISON_MATERIAL, poison_materialValue);
	}

	@Override
	public IDataset getTemperature() {
		return getDataset(NX_TEMPERATURE);
	}

	@Override
	public Double getTemperatureScalar() {
		return getDouble(NX_TEMPERATURE);
	}

	@Override
	public DataNode setTemperature(IDataset temperatureDataset) {
		return setDataset(NX_TEMPERATURE, temperatureDataset);
	}

	@Override
	public DataNode setTemperatureScalar(Double temperatureValue) {
		return setField(NX_TEMPERATURE, temperatureValue);
	}

	@Override
	public NXlog getTemperature_log() {
		// dataNodeName = NX_TEMPERATURE_LOG
		return getChild("temperature_log", NXlog.class);
	}

	@Override
	public void setTemperature_log(NXlog temperature_logGroup) {
		putChild("temperature_log", temperature_logGroup);
	}

	@Override
	public NXdata getPulse_shape() {
		// dataNodeName = NX_PULSE_SHAPE
		return getChild("pulse_shape", NXdata.class);
	}

	@Override
	public void setPulse_shape(NXdata pulse_shapeGroup) {
		putChild("pulse_shape", pulse_shapeGroup);
	}

	@Override
	public NXoff_geometry getOff_geometry() {
		// dataNodeName = NX_OFF_GEOMETRY
		return getChild("off_geometry", NXoff_geometry.class);
	}

	@Override
	public void setOff_geometry(NXoff_geometry off_geometryGroup) {
		putChild("off_geometry", off_geometryGroup);
	}

	@Override
	public NXoff_geometry getOff_geometry(String name) {
		return getChild(name, NXoff_geometry.class);
	}

	@Override
	public void setOff_geometry(String name, NXoff_geometry off_geometry) {
		putChild(name, off_geometry);
	}

	@Override
	public Map<String, NXoff_geometry> getAllOff_geometry() {
		return getChildren(NXoff_geometry.class);
	}
	
	@Override
	public void setAllOff_geometry(Map<String, NXoff_geometry> off_geometry) {
		setChildren(off_geometry);
	}

	@Override
	public String getAttributeDefault() {
		return getAttrString(null, NX_ATTRIBUTE_DEFAULT);
	}

	@Override
	public void setAttributeDefault(String defaultValue) {
		setAttribute(null, NX_ATTRIBUTE_DEFAULT, defaultValue);
	}

	@Override
	public IDataset getDepends_on() {
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
	public NXtransformations getTransformations() {
		// dataNodeName = NX_TRANSFORMATIONS
		return getChild("transformations", NXtransformations.class);
	}

	@Override
	public void setTransformations(NXtransformations transformationsGroup) {
		putChild("transformations", transformationsGroup);
	}

	@Override
	public NXtransformations getTransformations(String name) {
		return getChild(name, NXtransformations.class);
	}

	@Override
	public void setTransformations(String name, NXtransformations transformations) {
		putChild(name, transformations);
	}

	@Override
	public Map<String, NXtransformations> getAllTransformations() {
		return getChildren(NXtransformations.class);
	}
	
	@Override
	public void setAllTransformations(Map<String, NXtransformations> transformations) {
		setChildren(transformations);
	}

}

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
 * A capillary lens to focus the X-ray beam.
 * Based on information provided by Gerd Wellenreuther (DESY).

 */
public class NXcapillaryImpl extends NXobjectImpl implements NXcapillary {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_DATA,
		NexusBaseClass.NX_DATA,
		NexusBaseClass.NX_TRANSFORMATIONS);

	public NXcapillaryImpl() {
		super();
	}

	public NXcapillaryImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcapillary.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CAPILLARY;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getType() {
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
	public Dataset getManufacturer() {
		return getDataset(NX_MANUFACTURER);
	}

	@Override
	public String getManufacturerScalar() {
		return getString(NX_MANUFACTURER);
	}

	@Override
	public DataNode setManufacturer(IDataset manufacturerDataset) {
		return setDataset(NX_MANUFACTURER, manufacturerDataset);
	}

	@Override
	public DataNode setManufacturerScalar(String manufacturerValue) {
		return setString(NX_MANUFACTURER, manufacturerValue);
	}

	@Override
	public Dataset getMaximum_incident_angle() {
		return getDataset(NX_MAXIMUM_INCIDENT_ANGLE);
	}

	@Override
	public Double getMaximum_incident_angleScalar() {
		return getDouble(NX_MAXIMUM_INCIDENT_ANGLE);
	}

	@Override
	public DataNode setMaximum_incident_angle(IDataset maximum_incident_angleDataset) {
		return setDataset(NX_MAXIMUM_INCIDENT_ANGLE, maximum_incident_angleDataset);
	}

	@Override
	public DataNode setMaximum_incident_angleScalar(Double maximum_incident_angleValue) {
		return setField(NX_MAXIMUM_INCIDENT_ANGLE, maximum_incident_angleValue);
	}

	@Override
	public Dataset getAccepting_aperture() {
		return getDataset(NX_ACCEPTING_APERTURE);
	}

	@Override
	public Double getAccepting_apertureScalar() {
		return getDouble(NX_ACCEPTING_APERTURE);
	}

	@Override
	public DataNode setAccepting_aperture(IDataset accepting_apertureDataset) {
		return setDataset(NX_ACCEPTING_APERTURE, accepting_apertureDataset);
	}

	@Override
	public DataNode setAccepting_apertureScalar(Double accepting_apertureValue) {
		return setField(NX_ACCEPTING_APERTURE, accepting_apertureValue);
	}

	@Override
	public NXdata getGain() {
		// dataNodeName = NX_GAIN
		return getChild("gain", NXdata.class);
	}

	@Override
	public void setGain(NXdata gainGroup) {
		putChild("gain", gainGroup);
	}

	@Override
	public NXdata getTransmission() {
		// dataNodeName = NX_TRANSMISSION
		return getChild("transmission", NXdata.class);
	}

	@Override
	public void setTransmission(NXdata transmissionGroup) {
		putChild("transmission", transmissionGroup);
	}

	@Override
	public Dataset getWorking_distance() {
		return getDataset(NX_WORKING_DISTANCE);
	}

	@Override
	public Double getWorking_distanceScalar() {
		return getDouble(NX_WORKING_DISTANCE);
	}

	@Override
	public DataNode setWorking_distance(IDataset working_distanceDataset) {
		return setDataset(NX_WORKING_DISTANCE, working_distanceDataset);
	}

	@Override
	public DataNode setWorking_distanceScalar(Double working_distanceValue) {
		return setField(NX_WORKING_DISTANCE, working_distanceValue);
	}

	@Override
	public Dataset getFocal_size() {
		return getDataset(NX_FOCAL_SIZE);
	}

	@Override
	public Double getFocal_sizeScalar() {
		return getDouble(NX_FOCAL_SIZE);
	}

	@Override
	public DataNode setFocal_size(IDataset focal_sizeDataset) {
		return setDataset(NX_FOCAL_SIZE, focal_sizeDataset);
	}

	@Override
	public DataNode setFocal_sizeScalar(Double focal_sizeValue) {
		return setField(NX_FOCAL_SIZE, focal_sizeValue);
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

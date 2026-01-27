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

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Dataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * Details about a component as it is defined by its manufacturer.

 */
public class NXfabricationImpl extends NXobjectImpl implements NXfabrication {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXfabricationImpl() {
		super();
	}

	public NXfabricationImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXfabrication.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_FABRICATION;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getVendor() {
		return getDataset(NX_VENDOR);
	}

	@Override
	public String getVendorScalar() {
		return getString(NX_VENDOR);
	}

	@Override
	public DataNode setVendor(IDataset vendorDataset) {
		return setDataset(NX_VENDOR, vendorDataset);
	}

	@Override
	public DataNode setVendorScalar(String vendorValue) {
		return setString(NX_VENDOR, vendorValue);
	}

	@Override
	public Dataset getModel() {
		return getDataset(NX_MODEL);
	}

	@Override
	public String getModelScalar() {
		return getString(NX_MODEL);
	}

	@Override
	public DataNode setModel(IDataset modelDataset) {
		return setDataset(NX_MODEL, modelDataset);
	}

	@Override
	public DataNode setModelScalar(String modelValue) {
		return setString(NX_MODEL, modelValue);
	}

	@Override
	public String getModelAttributeVersion() {
		return getAttrString(NX_MODEL, NX_MODEL_ATTRIBUTE_VERSION);
	}

	@Override
	public void setModelAttributeVersion(String versionValue) {
		setAttribute(NX_MODEL, NX_MODEL_ATTRIBUTE_VERSION, versionValue);
	}

	@Override
	public Dataset getSerial_number() {
		return getDataset(NX_SERIAL_NUMBER);
	}

	@Override
	public String getSerial_numberScalar() {
		return getString(NX_SERIAL_NUMBER);
	}

	@Override
	public DataNode setSerial_number(IDataset serial_numberDataset) {
		return setDataset(NX_SERIAL_NUMBER, serial_numberDataset);
	}

	@Override
	public DataNode setSerial_numberScalar(String serial_numberValue) {
		return setString(NX_SERIAL_NUMBER, serial_numberValue);
	}

	@Override
	public Dataset getConstruction_date() {
		return getDataset(NX_CONSTRUCTION_DATE);
	}

	@Override
	public Date getConstruction_dateScalar() {
		return getDate(NX_CONSTRUCTION_DATE);
	}

	@Override
	public DataNode setConstruction_date(IDataset construction_dateDataset) {
		return setDataset(NX_CONSTRUCTION_DATE, construction_dateDataset);
	}

	@Override
	public DataNode setConstruction_dateScalar(Date construction_dateValue) {
		return setDate(NX_CONSTRUCTION_DATE, construction_dateValue);
	}

	@Override
	public Dataset getCapability() {
		return getDataset(NX_CAPABILITY);
	}

	@Override
	public String getCapabilityScalar() {
		return getString(NX_CAPABILITY);
	}

	@Override
	public DataNode setCapability(IDataset capabilityDataset) {
		return setDataset(NX_CAPABILITY, capabilityDataset);
	}

	@Override
	public DataNode setCapabilityScalar(String capabilityValue) {
		return setString(NX_CAPABILITY, capabilityValue);
	}

}

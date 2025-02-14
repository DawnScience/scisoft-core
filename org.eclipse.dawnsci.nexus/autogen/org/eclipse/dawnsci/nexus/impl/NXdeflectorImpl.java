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
 * Deflectors as they are used e.g. in an electron analyser.

 */
public class NXdeflectorImpl extends NXobjectImpl implements NXdeflector {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_TRANSFORMATIONS);

	public NXdeflectorImpl() {
		super();
	}

	public NXdeflectorImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXdeflector.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_DEFLECTOR;
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
	public Dataset getName() {
		return getDataset(NX_NAME);
	}

	@Override
	public String getNameScalar() {
		return getString(NX_NAME);
	}

	@Override
	public DataNode setName(IDataset nameDataset) {
		return setDataset(NX_NAME, nameDataset);
	}

	@Override
	public DataNode setNameScalar(String nameValue) {
		return setString(NX_NAME, nameValue);
	}

	@Override
	public Dataset getManufacturer_name() {
		return getDataset(NX_MANUFACTURER_NAME);
	}

	@Override
	public String getManufacturer_nameScalar() {
		return getString(NX_MANUFACTURER_NAME);
	}

	@Override
	public DataNode setManufacturer_name(IDataset manufacturer_nameDataset) {
		return setDataset(NX_MANUFACTURER_NAME, manufacturer_nameDataset);
	}

	@Override
	public DataNode setManufacturer_nameScalar(String manufacturer_nameValue) {
		return setString(NX_MANUFACTURER_NAME, manufacturer_nameValue);
	}

	@Override
	public Dataset getManufacturer_model() {
		return getDataset(NX_MANUFACTURER_MODEL);
	}

	@Override
	public String getManufacturer_modelScalar() {
		return getString(NX_MANUFACTURER_MODEL);
	}

	@Override
	public DataNode setManufacturer_model(IDataset manufacturer_modelDataset) {
		return setDataset(NX_MANUFACTURER_MODEL, manufacturer_modelDataset);
	}

	@Override
	public DataNode setManufacturer_modelScalar(String manufacturer_modelValue) {
		return setString(NX_MANUFACTURER_MODEL, manufacturer_modelValue);
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
	public Dataset getVoltage() {
		return getDataset(NX_VOLTAGE);
	}

	@Override
	public Number getVoltageScalar() {
		return getNumber(NX_VOLTAGE);
	}

	@Override
	public DataNode setVoltage(IDataset voltageDataset) {
		return setDataset(NX_VOLTAGE, voltageDataset);
	}

	@Override
	public DataNode setVoltageScalar(Number voltageValue) {
		return setField(NX_VOLTAGE, voltageValue);
	}

	@Override
	public Dataset getCurrent() {
		return getDataset(NX_CURRENT);
	}

	@Override
	public Number getCurrentScalar() {
		return getNumber(NX_CURRENT);
	}

	@Override
	public DataNode setCurrent(IDataset currentDataset) {
		return setDataset(NX_CURRENT, currentDataset);
	}

	@Override
	public DataNode setCurrentScalar(Number currentValue) {
		return setField(NX_CURRENT, currentValue);
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

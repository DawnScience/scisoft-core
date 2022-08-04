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
 * Parameters for controlling external conditions
 * 
 */
public class NXenvironmentImpl extends NXobjectImpl implements NXenvironment {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_GEOMETRY,
		NexusBaseClass.NX_NOTE,
		NexusBaseClass.NX_SENSOR);

	public NXenvironmentImpl() {
		super();
	}

	public NXenvironmentImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXenvironment.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_ENVIRONMENT;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}
	

	@Override
	public IDataset getName() {
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
	public IDataset getShort_name() {
		return getDataset(NX_SHORT_NAME);
	}

	@Override
	public String getShort_nameScalar() {
		return getString(NX_SHORT_NAME);
	}

	@Override
	public DataNode setShort_name(IDataset short_nameDataset) {
		return setDataset(NX_SHORT_NAME, short_nameDataset);
	}

	@Override
	public DataNode setShort_nameScalar(String short_nameValue) {
		return setString(NX_SHORT_NAME, short_nameValue);
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
	public IDataset getProgram() {
		return getDataset(NX_PROGRAM);
	}

	@Override
	public String getProgramScalar() {
		return getString(NX_PROGRAM);
	}

	@Override
	public DataNode setProgram(IDataset programDataset) {
		return setDataset(NX_PROGRAM, programDataset);
	}

	@Override
	public DataNode setProgramScalar(String programValue) {
		return setString(NX_PROGRAM, programValue);
	}

	@Override
	public NXgeometry getPosition() {
		// dataNodeName = NX_POSITION
		return getChild("position", NXgeometry.class);
	}

	@Override
	public void setPosition(NXgeometry positionGroup) {
		putChild("position", positionGroup);
	}

	@Override
	public NXnote getNote() {
		// dataNodeName = NX_NOTE
		return getChild("note", NXnote.class);
	}

	@Override
	public void setNote(NXnote noteGroup) {
		putChild("note", noteGroup);
	}

	@Override
	public NXnote getNote(String name) {
		return getChild(name, NXnote.class);
	}

	@Override
	public void setNote(String name, NXnote note) {
		putChild(name, note);
	}

	@Override
	public Map<String, NXnote> getAllNote() {
		return getChildren(NXnote.class);
	}
	
	@Override
	public void setAllNote(Map<String, NXnote> note) {
		setChildren(note);
	}

	@Override
	public NXsensor getSensor() {
		// dataNodeName = NX_SENSOR
		return getChild("sensor", NXsensor.class);
	}

	@Override
	public void setSensor(NXsensor sensorGroup) {
		putChild("sensor", sensorGroup);
	}

	@Override
	public NXsensor getSensor(String name) {
		return getChild(name, NXsensor.class);
	}

	@Override
	public void setSensor(String name, NXsensor sensor) {
		putChild(name, sensor);
	}

	@Override
	public Map<String, NXsensor> getAllSensor() {
		return getChildren(NXsensor.class);
	}
	
	@Override
	public void setAllSensor(Map<String, NXsensor> sensor) {
		setChildren(sensor);
	}

}

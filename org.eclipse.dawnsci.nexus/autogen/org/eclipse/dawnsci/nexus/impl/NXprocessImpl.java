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
 * Document an event of data processing, reconstruction, or analysis for this data.
 * 
 */
public class NXprocessImpl extends NXobjectImpl implements NXprocess {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_NOTE);

	public NXprocessImpl() {
		super();
	}

	public NXprocessImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXprocess.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_PROCESS;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
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
	public IDataset getSequence_index() {
		return getDataset(NX_SEQUENCE_INDEX);
	}

	@Override
	public Long getSequence_indexScalar() {
		return getLong(NX_SEQUENCE_INDEX);
	}

	@Override
	public DataNode setSequence_index(IDataset sequence_indexDataset) {
		return setDataset(NX_SEQUENCE_INDEX, sequence_indexDataset);
	}

	@Override
	public DataNode setSequence_indexScalar(Long sequence_indexValue) {
		return setField(NX_SEQUENCE_INDEX, sequence_indexValue);
	}

	@Override
	public IDataset getVersion() {
		return getDataset(NX_VERSION);
	}

	@Override
	public String getVersionScalar() {
		return getString(NX_VERSION);
	}

	@Override
	public DataNode setVersion(IDataset versionDataset) {
		return setDataset(NX_VERSION, versionDataset);
	}

	@Override
	public DataNode setVersionScalar(String versionValue) {
		return setString(NX_VERSION, versionValue);
	}

	@Override
	public IDataset getDate() {
		return getDataset(NX_DATE);
	}

	@Override
	public Date getDateScalar() {
		return getDate(NX_DATE);
	}

	@Override
	public DataNode setDate(IDataset dateDataset) {
		return setDataset(NX_DATE, dateDataset);
	}

	@Override
	public DataNode setDateScalar(Date dateValue) {
		return setDate(NX_DATE, dateValue);
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
	public String getAttributeDefault() {
		return getAttrString(null, NX_ATTRIBUTE_DEFAULT);
	}

	@Override
	public void setAttributeDefault(String defaultValue) {
		setAttribute(null, NX_ATTRIBUTE_DEFAULT, defaultValue);
	}

}

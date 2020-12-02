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
import org.eclipse.january.dataset.DatasetFactory;

import org.eclipse.dawnsci.nexus.*;

/**
 * Any additional freeform information not covered by the other base classes.
 * This class can be used to store additional information in a
 * NeXus file e.g. pictures, movies, audio, additional text logs
 * 
 */
public class NXnoteImpl extends NXobjectImpl implements NXnote {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXnoteImpl() {
		super();
	}

	public NXnoteImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXnote.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_NOTE;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}
	

	@Override
	public IDataset getAuthor() {
		return getDataset(NX_AUTHOR);
	}

	@Override
	public String getAuthorScalar() {
		return getString(NX_AUTHOR);
	}

	@Override
	public DataNode setAuthor(IDataset authorDataset) {
		return setDataset(NX_AUTHOR, authorDataset);
	}

	@Override
	public DataNode setAuthorScalar(String authorValue) {
		return setString(NX_AUTHOR, authorValue);
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
	public IDataset getFile_name() {
		return getDataset(NX_FILE_NAME);
	}

	@Override
	public String getFile_nameScalar() {
		return getString(NX_FILE_NAME);
	}

	@Override
	public DataNode setFile_name(IDataset file_nameDataset) {
		return setDataset(NX_FILE_NAME, file_nameDataset);
	}

	@Override
	public DataNode setFile_nameScalar(String file_nameValue) {
		return setString(NX_FILE_NAME, file_nameValue);
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
	public IDataset getData() {
		return getDataset(NX_DATA);
	}

	@Override
	public Object getDataScalar() {
		return getDataNode(NX_DATA).getDataset();
	}

	@Override
	public DataNode setData(IDataset dataDataset) {
		return setDataset(NX_DATA, dataDataset);
	}

	@Override
	public DataNode setDataScalar(Object dataValue) {
		return setField(NX_DATA, dataValue);
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

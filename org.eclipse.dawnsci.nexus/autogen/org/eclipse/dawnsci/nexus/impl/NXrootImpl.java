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


import org.eclipse.dawnsci.nexus.*;

/**
 * Definition of the root NeXus group.
 * 
 */
public class NXrootImpl extends NXobjectImpl implements NXroot {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_ENTRY);

	public NXrootImpl() {
		super();
	}

	public NXrootImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXroot.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_ROOT;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}
	

	@Override
	public String getAttributeNx_class() {
		return getAttrString(null, NX_ATTRIBUTE_NX_CLASS);
	}

	@Override
	public void setAttributeNx_class(String nx_classValue) {
		setAttribute(null, NX_ATTRIBUTE_NX_CLASS, nx_classValue);
	}

	@Override
	public Date getAttributeFile_time() {
		return getAttrDate(null, NX_ATTRIBUTE_FILE_TIME);
	}

	@Override
	public void setAttributeFile_time(Date file_timeValue) {
		setAttribute(null, NX_ATTRIBUTE_FILE_TIME, file_timeValue);
	}

	@Override
	public String getAttributeFile_name() {
		return getAttrString(null, NX_ATTRIBUTE_FILE_NAME);
	}

	@Override
	public void setAttributeFile_name(String file_nameValue) {
		setAttribute(null, NX_ATTRIBUTE_FILE_NAME, file_nameValue);
	}

	@Override
	public Date getAttributeFile_update_time() {
		return getAttrDate(null, NX_ATTRIBUTE_FILE_UPDATE_TIME);
	}

	@Override
	public void setAttributeFile_update_time(Date file_update_timeValue) {
		setAttribute(null, NX_ATTRIBUTE_FILE_UPDATE_TIME, file_update_timeValue);
	}

	@Override
	public String getAttributeNexus_version() {
		return getAttrString(null, NX_ATTRIBUTE_NEXUS_VERSION);
	}

	@Override
	public void setAttributeNexus_version(String nexus_versionValue) {
		setAttribute(null, NX_ATTRIBUTE_NEXUS_VERSION, nexus_versionValue);
	}

	@Override
	public String getAttributeHdf_version() {
		return getAttrString(null, NX_ATTRIBUTE_HDF_VERSION);
	}

	@Override
	public void setAttributeHdf_version(String hdf_versionValue) {
		setAttribute(null, NX_ATTRIBUTE_HDF_VERSION, hdf_versionValue);
	}

	@Override
	public String getAttributeHdf5_version() {
		return getAttrString(null, NX_ATTRIBUTE_HDF5_VERSION);
	}

	@Override
	public void setAttributeHdf5_version(String hdf5_versionValue) {
		setAttribute(null, NX_ATTRIBUTE_HDF5_VERSION, hdf5_versionValue);
	}

	@Override
	public String getAttributeXml_version() {
		return getAttrString(null, NX_ATTRIBUTE_XML_VERSION);
	}

	@Override
	public void setAttributeXml_version(String xml_versionValue) {
		setAttribute(null, NX_ATTRIBUTE_XML_VERSION, xml_versionValue);
	}

	@Override
	public String getAttributeH5py_version() {
		return getAttrString(null, NX_ATTRIBUTE_H5PY_VERSION);
	}

	@Override
	public void setAttributeH5py_version(String h5py_versionValue) {
		setAttribute(null, NX_ATTRIBUTE_H5PY_VERSION, h5py_versionValue);
	}

	@Override
	public String getAttributeCreator() {
		return getAttrString(null, NX_ATTRIBUTE_CREATOR);
	}

	@Override
	public void setAttributeCreator(String creatorValue) {
		setAttribute(null, NX_ATTRIBUTE_CREATOR, creatorValue);
	}

	@Override
	public String getAttributeCreator_version() {
		return getAttrString(null, NX_ATTRIBUTE_CREATOR_VERSION);
	}

	@Override
	public void setAttributeCreator_version(String creator_versionValue) {
		setAttribute(null, NX_ATTRIBUTE_CREATOR_VERSION, creator_versionValue);
	}

	@Override
	public NXentry getEntry() {
		// dataNodeName = NX_ENTRY
		return getChild("entry", NXentry.class);
	}

	@Override
	public void setEntry(NXentry entryGroup) {
		putChild("entry", entryGroup);
	}

	@Override
	public NXentry getEntry(String name) {
		return getChild(name, NXentry.class);
	}

	@Override
	public void setEntry(String name, NXentry entry) {
		putChild(name, entry);
	}

	@Override
	public Map<String, NXentry> getAllEntry() {
		return getChildren(NXentry.class);
	}
	
	@Override
	public void setAllEntry(Map<String, NXentry> entry) {
		setChildren(entry);
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

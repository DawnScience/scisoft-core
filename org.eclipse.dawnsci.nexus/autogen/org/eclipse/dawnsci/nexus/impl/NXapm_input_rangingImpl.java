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
import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Dataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * Metadata to ranging definitions made for a dataset in atom probe microscopy.
 * Ranging is the process of labeling time-of-flight data with so-called iontypes
 * which ideally specify the most likely ion/molecular ion evaporated within a
 * given mass-to-charge-state-ratio value interval.
 * The paraprobe-toolbox uses the convention that the so-called UNKNOWNTYPE
 * iontype (or unranged ions) represents the default iontype.
 * The ID of this special iontype is always reserved as 0. Each ion
 * is assigned to the UNKNOWNTYPE by default. Iontypes are assigned
 * by checking if the mass-to-charge-state-ratio values of an ion matches
 * to any of the defined mass-to-charge-state-ratio intervals.

 */
public class NXapm_input_rangingImpl extends NXobjectImpl implements NXapm_input_ranging {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXapm_input_rangingImpl() {
		super();
	}

	public NXapm_input_rangingImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXapm_input_ranging.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_APM_INPUT_RANGING;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getFilename() {
		return getDataset(NX_FILENAME);
	}

	@Override
	public String getFilenameScalar() {
		return getString(NX_FILENAME);
	}

	@Override
	public DataNode setFilename(IDataset filenameDataset) {
		return setDataset(NX_FILENAME, filenameDataset);
	}

	@Override
	public DataNode setFilenameScalar(String filenameValue) {
		return setString(NX_FILENAME, filenameValue);
	}

	@Override
	public String getFilenameAttributeVersion() {
		return getAttrString(NX_FILENAME, NX_FILENAME_ATTRIBUTE_VERSION);
	}

	@Override
	public void setFilenameAttributeVersion(String versionValue) {
		setAttribute(NX_FILENAME, NX_FILENAME_ATTRIBUTE_VERSION, versionValue);
	}

	@Override
	public Dataset getGroup_name_iontypes() {
		return getDataset(NX_GROUP_NAME_IONTYPES);
	}

	@Override
	public String getGroup_name_iontypesScalar() {
		return getString(NX_GROUP_NAME_IONTYPES);
	}

	@Override
	public DataNode setGroup_name_iontypes(IDataset group_name_iontypesDataset) {
		return setDataset(NX_GROUP_NAME_IONTYPES, group_name_iontypesDataset);
	}

	@Override
	public DataNode setGroup_name_iontypesScalar(String group_name_iontypesValue) {
		return setString(NX_GROUP_NAME_IONTYPES, group_name_iontypesValue);
	}

}

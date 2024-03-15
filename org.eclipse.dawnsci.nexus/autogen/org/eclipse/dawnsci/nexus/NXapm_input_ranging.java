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

package org.eclipse.dawnsci.nexus;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;

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
 *
 */
public interface NXapm_input_ranging extends NXobject {

	public static final String NX_FILENAME = "filename";
	public static final String NX_FILENAME_ATTRIBUTE_VERSION = "version";
	public static final String NX_GROUP_NAME_IONTYPES = "group_name_iontypes";
	/**
	 * Path and name of the NeXus/HDF5 file which stores ranging definitions.
	 *
	 * @return  the value.
	 */
	public IDataset getFilename();

	/**
	 * Path and name of the NeXus/HDF5 file which stores ranging definitions.
	 *
	 * @param filenameDataset the filenameDataset
	 */
	public DataNode setFilename(IDataset filenameDataset);

	/**
	 * Path and name of the NeXus/HDF5 file which stores ranging definitions.
	 *
	 * @return  the value.
	 */
	public String getFilenameScalar();

	/**
	 * Path and name of the NeXus/HDF5 file which stores ranging definitions.
	 *
	 * @param filename the filename
	 */
	public DataNode setFilenameScalar(String filenameValue);

	/**
	 * Version identifier of the file (representing an at least SHA256 strong)
	 * hash. Such hashes serve reproducibility as they can be used for tracking
	 * provenance metadata in a workflow.
	 *
	 * @return  the value.
	 */
	public String getFilenameAttributeVersion();

	/**
	 * Version identifier of the file (representing an at least SHA256 strong)
	 * hash. Such hashes serve reproducibility as they can be used for tracking
	 * provenance metadata in a workflow.
	 *
	 * @param versionValue the versionValue
	 */
	public void setFilenameAttributeVersion(String versionValue);

	/**
	 * Name of the group (prefix to the individual ranging definitions) inside
	 * the file referred to by filename which points to the specific ranging
	 * definition to use.
	 * An HDF5 file can store multiple ranging definitions. Using an ID is
	 * the mechanism to distinguish which specific ranging (version) will
	 * be processed. Reconstruction and ranging IDs can differ.
	 * They specify different IDs.
	 *
	 * @return  the value.
	 */
	public IDataset getGroup_name_iontypes();

	/**
	 * Name of the group (prefix to the individual ranging definitions) inside
	 * the file referred to by filename which points to the specific ranging
	 * definition to use.
	 * An HDF5 file can store multiple ranging definitions. Using an ID is
	 * the mechanism to distinguish which specific ranging (version) will
	 * be processed. Reconstruction and ranging IDs can differ.
	 * They specify different IDs.
	 *
	 * @param group_name_iontypesDataset the group_name_iontypesDataset
	 */
	public DataNode setGroup_name_iontypes(IDataset group_name_iontypesDataset);

	/**
	 * Name of the group (prefix to the individual ranging definitions) inside
	 * the file referred to by filename which points to the specific ranging
	 * definition to use.
	 * An HDF5 file can store multiple ranging definitions. Using an ID is
	 * the mechanism to distinguish which specific ranging (version) will
	 * be processed. Reconstruction and ranging IDs can differ.
	 * They specify different IDs.
	 *
	 * @return  the value.
	 */
	public String getGroup_name_iontypesScalar();

	/**
	 * Name of the group (prefix to the individual ranging definitions) inside
	 * the file referred to by filename which points to the specific ranging
	 * definition to use.
	 * An HDF5 file can store multiple ranging definitions. Using an ID is
	 * the mechanism to distinguish which specific ranging (version) will
	 * be processed. Reconstruction and ranging IDs can differ.
	 * They specify different IDs.
	 *
	 * @param group_name_iontypes the group_name_iontypes
	 */
	public DataNode setGroup_name_iontypesScalar(String group_name_iontypesValue);

}

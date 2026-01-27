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

import java.util.Date;
import java.util.Map;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;


/**
 * The root of a NeXus file.
 * In the NeXus standard, only NXentry groups are allowed at the
 * root level of a file, although it is permitted to include
 * additional groups and fields that are not part of the NeXus
 * standard and will not be validated by NeXus tools. NeXus defines
 * a number of root-level attributes that can be used to annotate
 * the NeXus tree.
 * Note that NXroot is the only base class that does not inherit
 * from the NXobject class, since the latter permits the inclusion
 * of NeXus objects that are not allowed at the root level.
 *
 */
public interface NXroot extends NXobject {

	public static final String NX_ATTRIBUTE_FILE_TIME = "file_time";
	public static final String NX_ATTRIBUTE_FILE_NAME = "file_name";
	public static final String NX_ATTRIBUTE_FILE_UPDATE_TIME = "file_update_time";
	public static final String NX_ATTRIBUTE_NEXUS_VERSION = "nexus_version";
	public static final String NX_ATTRIBUTE_NEXUS_REPOSITORY = "nexus_repository";
	public static final String NX_ATTRIBUTE_NEXUS_RELEASE = "nexus_release";
	public static final String NX_ATTRIBUTE_HDF_VERSION = "hdf_version";
	public static final String NX_ATTRIBUTE_HDF5_VERSION = "hdf5_version";
	public static final String NX_ATTRIBUTE_XML_VERSION = "xml_version";
	public static final String NX_ATTRIBUTE_H5PY_VERSION = "h5py_version";
	public static final String NX_ATTRIBUTE_CREATOR = "creator";
	public static final String NX_ATTRIBUTE_CREATOR_VERSION = "creator_version";
	/**
	 * Date and time file was originally created
	 *
	 * @return  the value.
	 */
	public Date getAttributeFile_time();

	/**
	 * Date and time file was originally created
	 *
	 * @param file_timeValue the file_timeValue
	 */
	public void setAttributeFile_time(Date file_timeValue);

	/**
	 * File name of original NeXus file
	 *
	 * @return  the value.
	 */
	public String getAttributeFile_name();

	/**
	 * File name of original NeXus file
	 *
	 * @param file_nameValue the file_nameValue
	 */
	public void setAttributeFile_name(String file_nameValue);

	/**
	 * Date and time of last file change at close
	 *
	 * @return  the value.
	 */
	public Date getAttributeFile_update_time();

	/**
	 * Date and time of last file change at close
	 *
	 * @param file_update_timeValue the file_update_timeValue
	 */
	public void setAttributeFile_update_time(Date file_update_timeValue);

	/**
	 * Version of NeXus API used in writing the file.
	 * Note that this is different from the version of the
	 * base class or application definition version number.
	 *
	 * @deprecated NAPI is frozen.
	 * @return  the value.
	 */
	@Deprecated
	public String getAttributeNexus_version();

	/**
	 * Version of NeXus API used in writing the file.
	 * Note that this is different from the version of the
	 * base class or application definition version number.
	 *
	 * @deprecated NAPI is frozen.
	 * @param nexus_versionValue the nexus_versionValue
	 */
	@Deprecated
	public void setAttributeNexus_version(String nexus_versionValue);

	/**
	 * A repository containing the application definitions
	 * used for creating this file.
	 * If the ``NeXus_release`` attribute contains a commit distance and hash,
	 * this should refer to this repository.
	 *
	 * @return  the value.
	 */
	public String getAttributeNexus_repository();

	/**
	 * A repository containing the application definitions
	 * used for creating this file.
	 * If the ``NeXus_release`` attribute contains a commit distance and hash,
	 * this should refer to this repository.
	 *
	 * @param nexus_repositoryValue the nexus_repositoryValue
	 */
	public void setAttributeNexus_repository(String nexus_repositoryValue);

	/**
	 * The version of NeXus definitions used in writing the file. This can either be a date-based
	 * NeXus release (e.g., YYYY.MM), see https://github.com/nexusformat/definitions/releases or
	 * a version tag that includes additional development information, such as a commit distance and
	 * a Git hash. This is typically formatted as `vYYYY.MM.post1.dev<commit-distance>-g<git-hash>`,
	 * where `YYYY.MM` refers to the base version of the NeXus definitions. `post1.dev<commit-distance>`
	 * indicates that the definitions are based on a commit after the base version (post1), with
	 * `<commit-distance>` being the number of commits since that version. `g<git-hash>` is the
	 * abbreviated Git hash that identifies the specific commit of the definitions being used.
	 * If the version includes both a commit distance and a Git hash, the ``NeXus_repository``
	 * attribute must be included, specifying the URL of the repository containing that version.
	 *
	 * @return  the value.
	 */
	public String getAttributeNexus_release();

	/**
	 * The version of NeXus definitions used in writing the file. This can either be a date-based
	 * NeXus release (e.g., YYYY.MM), see https://github.com/nexusformat/definitions/releases or
	 * a version tag that includes additional development information, such as a commit distance and
	 * a Git hash. This is typically formatted as `vYYYY.MM.post1.dev<commit-distance>-g<git-hash>`,
	 * where `YYYY.MM` refers to the base version of the NeXus definitions. `post1.dev<commit-distance>`
	 * indicates that the definitions are based on a commit after the base version (post1), with
	 * `<commit-distance>` being the number of commits since that version. `g<git-hash>` is the
	 * abbreviated Git hash that identifies the specific commit of the definitions being used.
	 * If the version includes both a commit distance and a Git hash, the ``NeXus_repository``
	 * attribute must be included, specifying the URL of the repository containing that version.
	 *
	 * @param nexus_releaseValue the nexus_releaseValue
	 */
	public void setAttributeNexus_release(String nexus_releaseValue);

	/**
	 * Version of HDF (version 4) library used in writing the file
	 *
	 * @return  the value.
	 */
	public String getAttributeHdf_version();

	/**
	 * Version of HDF (version 4) library used in writing the file
	 *
	 * @param hdf_versionValue the hdf_versionValue
	 */
	public void setAttributeHdf_version(String hdf_versionValue);

	/**
	 * Version of HDF5 library used in writing the file.
	 * Note this attribute is spelled with uppercase "V",
	 * different than other version attributes.
	 *
	 * @return  the value.
	 */
	public String getAttributeHdf5_version();

	/**
	 * Version of HDF5 library used in writing the file.
	 * Note this attribute is spelled with uppercase "V",
	 * different than other version attributes.
	 *
	 * @param hdf5_versionValue the hdf5_versionValue
	 */
	public void setAttributeHdf5_version(String hdf5_versionValue);

	/**
	 * Version of XML support library used in writing the XML file
	 *
	 * @return  the value.
	 */
	public String getAttributeXml_version();

	/**
	 * Version of XML support library used in writing the XML file
	 *
	 * @param xml_versionValue the xml_versionValue
	 */
	public void setAttributeXml_version(String xml_versionValue);

	/**
	 * Version of h5py Python package used in writing the file
	 *
	 * @return  the value.
	 */
	public String getAttributeH5py_version();

	/**
	 * Version of h5py Python package used in writing the file
	 *
	 * @param h5py_versionValue the h5py_versionValue
	 */
	public void setAttributeH5py_version(String h5py_versionValue);

	/**
	 * facility or program where file originated
	 *
	 * @return  the value.
	 */
	public String getAttributeCreator();

	/**
	 * facility or program where file originated
	 *
	 * @param creatorValue the creatorValue
	 */
	public void setAttributeCreator(String creatorValue);

	/**
	 * Version of facility or program used in writing the file
	 *
	 * @return  the value.
	 */
	public String getAttributeCreator_version();

	/**
	 * Version of facility or program used in writing the file
	 *
	 * @param creator_versionValue the creator_versionValue
	 */
	public void setAttributeCreator_version(String creator_versionValue);

	/**
	 * entries
	 *
	 * @return  the value.
	 */
	public NXentry getEntry();

	/**
	 * entries
	 *
	 * @param entryGroup the entryGroup
	 */
	public void setEntry(NXentry entryGroup);

	/**
	 * Get a NXentry node by name:
	 * <ul>
	 * <li>
	 * entries</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXentry for that node.
	 */
	public NXentry getEntry(String name);

	/**
	 * Set a NXentry node by name:
	 * <ul>
	 * <li>
	 * entries</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param entry the value to set
	 */
	public void setEntry(String name, NXentry entry);

	/**
	 * Get all NXentry nodes:
	 * <ul>
	 * <li>
	 * entries</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXentry for that node.
	 */
	public Map<String, NXentry> getAllEntry();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * entries</li>
	 * </ul>
	 *
	 * @param entry the child nodes to add
	 */

	public void setAllEntry(Map<String, NXentry> entry);


}

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
import org.eclipse.january.dataset.Dataset;

/**
 * Base class for reporting the description of a computer
 *
 */
public interface NXcs_computer extends NXobject {

	public static final String NX_NAME = "name";
	public static final String NX_OPERATING_SYSTEM = "operating_system";
	public static final String NX_OPERATING_SYSTEM_ATTRIBUTE_VERSION = "version";
	public static final String NX_UUID = "uuid";
	/**
	 * Given name/alias to the computing system, e.g. MyDesktop.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getName();

	/**
	 * Given name/alias to the computing system, e.g. MyDesktop.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param nameDataset the nameDataset
	 */
	public DataNode setName(IDataset nameDataset);

	/**
	 * Given name/alias to the computing system, e.g. MyDesktop.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getNameScalar();

	/**
	 * Given name/alias to the computing system, e.g. MyDesktop.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param name the name
	 */
	public DataNode setNameScalar(String nameValue);

	/**
	 * Name of the operating system, e.g. Windows, Linux, Mac, Android.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getOperating_system();

	/**
	 * Name of the operating system, e.g. Windows, Linux, Mac, Android.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param operating_systemDataset the operating_systemDataset
	 */
	public DataNode setOperating_system(IDataset operating_systemDataset);

	/**
	 * Name of the operating system, e.g. Windows, Linux, Mac, Android.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getOperating_systemScalar();

	/**
	 * Name of the operating system, e.g. Windows, Linux, Mac, Android.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param operating_system the operating_system
	 */
	public DataNode setOperating_systemScalar(String operating_systemValue);

	/**
	 * Version plus build number, commit hash, or description of an ever
	 * persistent resource where the source code of the program and build
	 * instructions can be found so that the program can be configured in
	 * such a manner that the result file is ideally recreatable yielding
	 * the same results.
	 *
	 * @return  the value.
	 */
	public String getOperating_systemAttributeVersion();

	/**
	 * Version plus build number, commit hash, or description of an ever
	 * persistent resource where the source code of the program and build
	 * instructions can be found so that the program can be configured in
	 * such a manner that the result file is ideally recreatable yielding
	 * the same results.
	 *
	 * @param versionValue the versionValue
	 */
	public void setOperating_systemAttributeVersion(String versionValue);

	/**
	 * A globally unique persistent identifier of the computer, i.e.
	 * the Universally Unique Identifier (UUID) of the computing node.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getUuid();

	/**
	 * A globally unique persistent identifier of the computer, i.e.
	 * the Universally Unique Identifier (UUID) of the computing node.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param uuidDataset the uuidDataset
	 */
	public DataNode setUuid(IDataset uuidDataset);

	/**
	 * A globally unique persistent identifier of the computer, i.e.
	 * the Universally Unique Identifier (UUID) of the computing node.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getUuidScalar();

	/**
	 * A globally unique persistent identifier of the computer, i.e.
	 * the Universally Unique Identifier (UUID) of the computing node.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param uuid the uuid
	 */
	public DataNode setUuidScalar(String uuidValue);

	/**
	 * Multiple instances should be named processor1, processor2, etc.
	 *
	 * @return  the value.
	 */
	public NXcs_processor getProcessorid();

	/**
	 * Multiple instances should be named processor1, processor2, etc.
	 *
	 * @param processoridGroup the processoridGroup
	 */
	public void setProcessorid(NXcs_processor processoridGroup);

	/**
	 * Multiple instances should be named memory1, memory2, etc.
	 *
	 * @return  the value.
	 */
	public NXcs_memory getMemoryid();

	/**
	 * Multiple instances should be named memory1, memory2, etc.
	 *
	 * @param memoryidGroup the memoryidGroup
	 */
	public void setMemoryid(NXcs_memory memoryidGroup);

	/**
	 * Multiple instances should be named storage1, storage2, etc.
	 *
	 * @return  the value.
	 */
	public NXcs_storage getStorageid();

	/**
	 * Multiple instances should be named storage1, storage2, etc.
	 *
	 * @param storageidGroup the storageidGroup
	 */
	public void setStorageid(NXcs_storage storageidGroup);

}

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

import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Dataset;

/**
 * Computer science description of a set of computing nodes.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul></ul></p>
 *
 */
public interface NXcs_computer extends NXobject {

	public static final String NX_NAME = "name";
	public static final String NX_OPERATING_SYSTEM = "operating_system";
	public static final String NX_OPERATING_SYSTEM_ATTRIBUTE_VERSION = "version";
	public static final String NX_UUID = "uuid";
	/**
	 * Given name/alias to the computing system, e.g. MyDesktop.
	 *
	 * @return  the value.
	 */
	public Dataset getName();

	/**
	 * Given name/alias to the computing system, e.g. MyDesktop.
	 *
	 * @param nameDataset the nameDataset
	 */
	public DataNode setName(IDataset nameDataset);

	/**
	 * Given name/alias to the computing system, e.g. MyDesktop.
	 *
	 * @return  the value.
	 */
	public String getNameScalar();

	/**
	 * Given name/alias to the computing system, e.g. MyDesktop.
	 *
	 * @param name the name
	 */
	public DataNode setNameScalar(String nameValue);

	/**
	 * Name of the operating system, e.g. Windows, Linux, Mac, Android.
	 *
	 * @return  the value.
	 */
	public Dataset getOperating_system();

	/**
	 * Name of the operating system, e.g. Windows, Linux, Mac, Android.
	 *
	 * @param operating_systemDataset the operating_systemDataset
	 */
	public DataNode setOperating_system(IDataset operating_systemDataset);

	/**
	 * Name of the operating system, e.g. Windows, Linux, Mac, Android.
	 *
	 * @return  the value.
	 */
	public String getOperating_systemScalar();

	/**
	 * Name of the operating system, e.g. Windows, Linux, Mac, Android.
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
	 * Ideally a (globally) unique persistent identifier of the computer, i.e.
	 * the Universally Unique Identifier (UUID) of the computing node.
	 *
	 * @return  the value.
	 */
	public Dataset getUuid();

	/**
	 * Ideally a (globally) unique persistent identifier of the computer, i.e.
	 * the Universally Unique Identifier (UUID) of the computing node.
	 *
	 * @param uuidDataset the uuidDataset
	 */
	public DataNode setUuid(IDataset uuidDataset);

	/**
	 * Ideally a (globally) unique persistent identifier of the computer, i.e.
	 * the Universally Unique Identifier (UUID) of the computing node.
	 *
	 * @return  the value.
	 */
	public String getUuidScalar();

	/**
	 * Ideally a (globally) unique persistent identifier of the computer, i.e.
	 * the Universally Unique Identifier (UUID) of the computing node.
	 *
	 * @param uuid the uuid
	 */
	public DataNode setUuidScalar(String uuidValue);

	/**
	 * A list of physical processing units (can be multi-core chips).
	 *
	 * @return  the value.
	 */
	public NXcs_cpu getCs_cpu();

	/**
	 * A list of physical processing units (can be multi-core chips).
	 *
	 * @param cs_cpuGroup the cs_cpuGroup
	 */
	public void setCs_cpu(NXcs_cpu cs_cpuGroup);

	/**
	 * Get a NXcs_cpu node by name:
	 * <ul>
	 * <li>
	 * A list of physical processing units (can be multi-core chips).</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXcs_cpu for that node.
	 */
	public NXcs_cpu getCs_cpu(String name);

	/**
	 * Set a NXcs_cpu node by name:
	 * <ul>
	 * <li>
	 * A list of physical processing units (can be multi-core chips).</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param cs_cpu the value to set
	 */
	public void setCs_cpu(String name, NXcs_cpu cs_cpu);

	/**
	 * Get all NXcs_cpu nodes:
	 * <ul>
	 * <li>
	 * A list of physical processing units (can be multi-core chips).</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXcs_cpu for that node.
	 */
	public Map<String, NXcs_cpu> getAllCs_cpu();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * A list of physical processing units (can be multi-core chips).</li>
	 * </ul>
	 *
	 * @param cs_cpu the child nodes to add
	 */

	public void setAllCs_cpu(Map<String, NXcs_cpu> cs_cpu);


	/**
	 * A list of physical coprocessor/graphic cards/accelerator units.
	 *
	 * @return  the value.
	 */
	public NXcs_gpu getCs_gpu();

	/**
	 * A list of physical coprocessor/graphic cards/accelerator units.
	 *
	 * @param cs_gpuGroup the cs_gpuGroup
	 */
	public void setCs_gpu(NXcs_gpu cs_gpuGroup);

	/**
	 * Get a NXcs_gpu node by name:
	 * <ul>
	 * <li>
	 * A list of physical coprocessor/graphic cards/accelerator units.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXcs_gpu for that node.
	 */
	public NXcs_gpu getCs_gpu(String name);

	/**
	 * Set a NXcs_gpu node by name:
	 * <ul>
	 * <li>
	 * A list of physical coprocessor/graphic cards/accelerator units.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param cs_gpu the value to set
	 */
	public void setCs_gpu(String name, NXcs_gpu cs_gpu);

	/**
	 * Get all NXcs_gpu nodes:
	 * <ul>
	 * <li>
	 * A list of physical coprocessor/graphic cards/accelerator units.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXcs_gpu for that node.
	 */
	public Map<String, NXcs_gpu> getAllCs_gpu();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * A list of physical coprocessor/graphic cards/accelerator units.</li>
	 * </ul>
	 *
	 * @param cs_gpu the child nodes to add
	 */

	public void setAllCs_gpu(Map<String, NXcs_gpu> cs_gpu);


	/**
	 * Details about the memory sub-system.
	 *
	 * @return  the value.
	 */
	public NXcs_mm_sys getCs_mm_sys();

	/**
	 * Details about the memory sub-system.
	 *
	 * @param cs_mm_sysGroup the cs_mm_sysGroup
	 */
	public void setCs_mm_sys(NXcs_mm_sys cs_mm_sysGroup);

	/**
	 * Get a NXcs_mm_sys node by name:
	 * <ul>
	 * <li>
	 * Details about the memory sub-system.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXcs_mm_sys for that node.
	 */
	public NXcs_mm_sys getCs_mm_sys(String name);

	/**
	 * Set a NXcs_mm_sys node by name:
	 * <ul>
	 * <li>
	 * Details about the memory sub-system.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param cs_mm_sys the value to set
	 */
	public void setCs_mm_sys(String name, NXcs_mm_sys cs_mm_sys);

	/**
	 * Get all NXcs_mm_sys nodes:
	 * <ul>
	 * <li>
	 * Details about the memory sub-system.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXcs_mm_sys for that node.
	 */
	public Map<String, NXcs_mm_sys> getAllCs_mm_sys();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Details about the memory sub-system.</li>
	 * </ul>
	 *
	 * @param cs_mm_sys the child nodes to add
	 */

	public void setAllCs_mm_sys(Map<String, NXcs_mm_sys> cs_mm_sys);


	/**
	 * Details about the I/O sub-system.
	 *
	 * @return  the value.
	 */
	public NXcs_io_sys getCs_io_sys();

	/**
	 * Details about the I/O sub-system.
	 *
	 * @param cs_io_sysGroup the cs_io_sysGroup
	 */
	public void setCs_io_sys(NXcs_io_sys cs_io_sysGroup);

	/**
	 * Get a NXcs_io_sys node by name:
	 * <ul>
	 * <li>
	 * Details about the I/O sub-system.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXcs_io_sys for that node.
	 */
	public NXcs_io_sys getCs_io_sys(String name);

	/**
	 * Set a NXcs_io_sys node by name:
	 * <ul>
	 * <li>
	 * Details about the I/O sub-system.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param cs_io_sys the value to set
	 */
	public void setCs_io_sys(String name, NXcs_io_sys cs_io_sys);

	/**
	 * Get all NXcs_io_sys nodes:
	 * <ul>
	 * <li>
	 * Details about the I/O sub-system.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXcs_io_sys for that node.
	 */
	public Map<String, NXcs_io_sys> getAllCs_io_sys();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Details about the I/O sub-system.</li>
	 * </ul>
	 *
	 * @param cs_io_sys the child nodes to add
	 */

	public void setAllCs_io_sys(Map<String, NXcs_io_sys> cs_io_sys);


}

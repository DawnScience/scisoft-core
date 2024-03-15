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
 * Computer science description of a main memory system of a computer.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul></ul></p>
 *
 */
public interface NXcs_mm_sys extends NXobject {

	public static final String NX_TOTAL_PHYSICAL_MEMORY = "total_physical_memory";
	/**
	 * How much physical memory does the system provide.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getTotal_physical_memory();

	/**
	 * How much physical memory does the system provide.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @param total_physical_memoryDataset the total_physical_memoryDataset
	 */
	public DataNode setTotal_physical_memory(IDataset total_physical_memoryDataset);

	/**
	 * How much physical memory does the system provide.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getTotal_physical_memoryScalar();

	/**
	 * How much physical memory does the system provide.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * </p>
	 *
	 * @param total_physical_memory the total_physical_memory
	 */
	public DataNode setTotal_physical_memoryScalar(Number total_physical_memoryValue);

}

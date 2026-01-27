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
 * Device to reduce an atmosphere to a controlled pressure.
 *
 */
public interface NXpump extends NXcomponent {

	public static final String NX_DESIGN = "design";
	public static final String NX_BASE_PRESSURE = "base_pressure";
	public static final String NX_MEDIUM = "medium";
	/**
	 * Principle type of the pump.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>membrane</b> </li>
	 * <li><b>rotary_vane</b> </li>
	 * <li><b>roots</b> </li>
	 * <li><b>turbo_molecular</b> </li>
	 * <li><b>ion</b> </li>
	 * <li><b>cryo</b> </li>
	 * <li><b>diffusion</b> </li>
	 * <li><b>scroll</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDesign();

	/**
	 * Principle type of the pump.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>membrane</b> </li>
	 * <li><b>rotary_vane</b> </li>
	 * <li><b>roots</b> </li>
	 * <li><b>turbo_molecular</b> </li>
	 * <li><b>ion</b> </li>
	 * <li><b>cryo</b> </li>
	 * <li><b>diffusion</b> </li>
	 * <li><b>scroll</b> </li></ul></p>
	 * </p>
	 *
	 * @param designDataset the designDataset
	 */
	public DataNode setDesign(IDataset designDataset);

	/**
	 * Principle type of the pump.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>membrane</b> </li>
	 * <li><b>rotary_vane</b> </li>
	 * <li><b>roots</b> </li>
	 * <li><b>turbo_molecular</b> </li>
	 * <li><b>ion</b> </li>
	 * <li><b>cryo</b> </li>
	 * <li><b>diffusion</b> </li>
	 * <li><b>scroll</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getDesignScalar();

	/**
	 * Principle type of the pump.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>membrane</b> </li>
	 * <li><b>rotary_vane</b> </li>
	 * <li><b>roots</b> </li>
	 * <li><b>turbo_molecular</b> </li>
	 * <li><b>ion</b> </li>
	 * <li><b>cryo</b> </li>
	 * <li><b>diffusion</b> </li>
	 * <li><b>scroll</b> </li></ul></p>
	 * </p>
	 *
	 * @param design the design
	 */
	public DataNode setDesignScalar(String designValue);

	/**
	 * The minimum pressure achievable in a chamber after
	 * it has been pumped down for an extended period.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_PRESSURE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getBase_pressure();

	/**
	 * The minimum pressure achievable in a chamber after
	 * it has been pumped down for an extended period.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_PRESSURE
	 * </p>
	 *
	 * @param base_pressureDataset the base_pressureDataset
	 */
	public DataNode setBase_pressure(IDataset base_pressureDataset);

	/**
	 * The minimum pressure achievable in a chamber after
	 * it has been pumped down for an extended period.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_PRESSURE
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getBase_pressureScalar();

	/**
	 * The minimum pressure achievable in a chamber after
	 * it has been pumped down for an extended period.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_PRESSURE
	 * </p>
	 *
	 * @param base_pressure the base_pressure
	 */
	public DataNode setBase_pressureScalar(Double base_pressureValue);

	/**
	 * The material being moved by the pump.
	 * Pumps intending to create a vacuum should state "vacuum" as the medium,
	 * while pumps having the primary purpose of creating a flow or pressure
	 * of gas should state "gas" as the medium.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>vacuum</b> </li>
	 * <li><b>liquid</b> </li>
	 * <li><b>gas</b> </li>
	 * <li><b>slurry</b> </li>
	 * <li><b>powder</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getMedium();

	/**
	 * The material being moved by the pump.
	 * Pumps intending to create a vacuum should state "vacuum" as the medium,
	 * while pumps having the primary purpose of creating a flow or pressure
	 * of gas should state "gas" as the medium.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>vacuum</b> </li>
	 * <li><b>liquid</b> </li>
	 * <li><b>gas</b> </li>
	 * <li><b>slurry</b> </li>
	 * <li><b>powder</b> </li></ul></p>
	 * </p>
	 *
	 * @param mediumDataset the mediumDataset
	 */
	public DataNode setMedium(IDataset mediumDataset);

	/**
	 * The material being moved by the pump.
	 * Pumps intending to create a vacuum should state "vacuum" as the medium,
	 * while pumps having the primary purpose of creating a flow or pressure
	 * of gas should state "gas" as the medium.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>vacuum</b> </li>
	 * <li><b>liquid</b> </li>
	 * <li><b>gas</b> </li>
	 * <li><b>slurry</b> </li>
	 * <li><b>powder</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getMediumScalar();

	/**
	 * The material being moved by the pump.
	 * Pumps intending to create a vacuum should state "vacuum" as the medium,
	 * while pumps having the primary purpose of creating a flow or pressure
	 * of gas should state "gas" as the medium.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>vacuum</b> </li>
	 * <li><b>liquid</b> </li>
	 * <li><b>gas</b> </li>
	 * <li><b>slurry</b> </li>
	 * <li><b>powder</b> </li></ul></p>
	 * </p>
	 *
	 * @param medium the medium
	 */
	public DataNode setMediumScalar(String mediumValue);

}

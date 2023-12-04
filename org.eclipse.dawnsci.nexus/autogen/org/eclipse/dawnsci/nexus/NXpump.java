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
 * Device to reduce an atmosphere to a controlled remaining pressure level.
 * 
 */
public interface NXpump extends NXobject {

	public static final String NX_DESIGN = "design";
	/**
	 * Principle type of the pump.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>membrane</b> </li>
	 * <li><b>rotary_vane</b> </li>
	 * <li><b>roots</b> </li>
	 * <li><b>turbo_molecular</b> </li></ul></p>
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getDesign();
	
	/**
	 * Principle type of the pump.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>membrane</b> </li>
	 * <li><b>rotary_vane</b> </li>
	 * <li><b>roots</b> </li>
	 * <li><b>turbo_molecular</b> </li></ul></p>
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
	 * <li><b>turbo_molecular</b> </li></ul></p>
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
	 * <li><b>turbo_molecular</b> </li></ul></p>
	 * </p>
	 * 
	 * @param design the design
	 */
	public DataNode setDesignScalar(String designValue);

}

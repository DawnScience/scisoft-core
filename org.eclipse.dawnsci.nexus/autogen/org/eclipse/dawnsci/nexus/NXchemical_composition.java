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
 * (Chemical) composition of a sample or a set of things.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>n</b>
 * The number of samples or things.</li></ul></p>
 *
 */
public interface NXchemical_composition extends NXobject {

	public static final String NX_TOTAL = "total";
	/**
	 * Total based on which composition information is normalized.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getTotal();

	/**
	 * Total based on which composition information is normalized.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 *
	 * @param totalDataset the totalDataset
	 */
	public DataNode setTotal(IDataset totalDataset);

	/**
	 * Total based on which composition information is normalized.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getTotalScalar();

	/**
	 * Total based on which composition information is normalized.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n;
	 * </p>
	 *
	 * @param total the total
	 */
	public DataNode setTotalScalar(Number totalValue);

	/**
	 *
	 * @return  the value.
	 */
	public NXion getIon();

	/**
	 *
	 * @param ionGroup the ionGroup
	 */
	public void setIon(NXion ionGroup);

}

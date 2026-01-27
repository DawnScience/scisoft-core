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
 * A spin polarizer.
 *
 */
public interface NXpolarizer extends NXcomponent {

	public static final String NX_TYPE = "type";
	public static final String NX_COMPOSITION = "composition";
	public static final String NX_REFLECTION = "reflection";
	public static final String NX_EFFICIENCY = "efficiency";
	/**
	 * one of these values: "crystal", "supermirror", "3He"
	 *
	 * @return  the value.
	 */
	public Dataset getType();

	/**
	 * one of these values: "crystal", "supermirror", "3He"
	 *
	 * @param typeDataset the typeDataset
	 */
	public DataNode setType(IDataset typeDataset);

	/**
	 * one of these values: "crystal", "supermirror", "3He"
	 *
	 * @return  the value.
	 */
	public String getTypeScalar();

	/**
	 * one of these values: "crystal", "supermirror", "3He"
	 *
	 * @param type the type
	 */
	public DataNode setTypeScalar(String typeValue);

	/**
	 * description of the composition of the polarizing material
	 *
	 * @return  the value.
	 */
	public Dataset getComposition();

	/**
	 * description of the composition of the polarizing material
	 *
	 * @param compositionDataset the compositionDataset
	 */
	public DataNode setComposition(IDataset compositionDataset);

	/**
	 * description of the composition of the polarizing material
	 *
	 * @return  the value.
	 */
	public String getCompositionScalar();

	/**
	 * description of the composition of the polarizing material
	 *
	 * @param composition the composition
	 */
	public DataNode setCompositionScalar(String compositionValue);

	/**
	 * [hkl] values of nominal reflection
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getReflection();

	/**
	 * [hkl] values of nominal reflection
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @param reflectionDataset the reflectionDataset
	 */
	public DataNode setReflection(IDataset reflectionDataset);

	/**
	 * [hkl] values of nominal reflection
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getReflectionScalar();

	/**
	 * [hkl] values of nominal reflection
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: 3;
	 * </p>
	 *
	 * @param reflection the reflection
	 */
	public DataNode setReflectionScalar(Long reflectionValue);

	/**
	 * polarizing efficiency
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getEfficiency();

	/**
	 * polarizing efficiency
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * </p>
	 *
	 * @param efficiencyDataset the efficiencyDataset
	 */
	public DataNode setEfficiency(IDataset efficiencyDataset);

	/**
	 * polarizing efficiency
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getEfficiencyScalar();

	/**
	 * polarizing efficiency
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * </p>
	 *
	 * @param efficiency the efficiency
	 */
	public DataNode setEfficiencyScalar(Double efficiencyValue);

	/**
	 * .. todo::
	 * Add a definition for the reference point of a polarizer.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getDepends_on();

	/**
	 * .. todo::
	 * Add a definition for the reference point of a polarizer.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param depends_onDataset the depends_onDataset
	 */
	public DataNode setDepends_on(IDataset depends_onDataset);

	/**
	 * .. todo::
	 * Add a definition for the reference point of a polarizer.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getDepends_onScalar();

	/**
	 * .. todo::
	 * Add a definition for the reference point of a polarizer.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 *
	 * @param depends_on the depends_on
	 */
	public DataNode setDepends_onScalar(String depends_onValue);

}

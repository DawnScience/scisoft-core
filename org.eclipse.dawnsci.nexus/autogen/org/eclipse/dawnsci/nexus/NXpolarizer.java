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
 * A spin polarizer.
 * 
 */
public interface NXpolarizer extends NXobject {

	public static final String NX_TYPE = "type";
	public static final String NX_COMPOSITION = "composition";
	public static final String NX_REFLECTION = "reflection";
	public static final String NX_EFFICIENCY = "efficiency";
	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	/**
	 * one of these values: "crystal", "supermirror", "3He"
	 * 
	 * @return  the value.
	 */
	public IDataset getType();
	
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
	public IDataset getComposition();
	
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
	public IDataset getReflection();
	
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
	public IDataset getEfficiency();
	
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
	 * .. index:: plotting
	 * Declares which child group contains a path leading
	 * to a :ref:`NXdata` group.
	 * It is recommended (as of NIAC2014) to use this attribute
	 * to help define the path to the default dataset to be plotted.
	 * See https://www.nexusformat.org/2014_How_to_find_default_data.html
	 * for a summary of the discussion.
	 * 
	 * @return  the value.
	 */
	public String getAttributeDefault();
	
	/**
	 * .. index:: plotting
	 * Declares which child group contains a path leading
	 * to a :ref:`NXdata` group.
	 * It is recommended (as of NIAC2014) to use this attribute
	 * to help define the path to the default dataset to be plotted.
	 * See https://www.nexusformat.org/2014_How_to_find_default_data.html
	 * for a summary of the discussion.
	 * 
	 * @param defaultValue the defaultValue
	 */
	public void setAttributeDefault(String defaultValue);

}

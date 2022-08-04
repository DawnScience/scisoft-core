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
 * A spin flipper.
 * 
 */
public interface NXflipper extends NXobject {

	public static final String NX_TYPE = "type";
	public static final String NX_FLIP_TURNS = "flip_turns";
	public static final String NX_COMP_TURNS = "comp_turns";
	public static final String NX_GUIDE_TURNS = "guide_turns";
	public static final String NX_FLIP_CURRENT = "flip_current";
	public static final String NX_COMP_CURRENT = "comp_current";
	public static final String NX_GUIDE_CURRENT = "guide_current";
	public static final String NX_THICKNESS = "thickness";
	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>coil</b> </li>
	 * <li><b>current-sheet</b> </li></ul></p>
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getType();
	
	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>coil</b> </li>
	 * <li><b>current-sheet</b> </li></ul></p>
	 * </p>
	 * 
	 * @param typeDataset the typeDataset
	 */
	public DataNode setType(IDataset typeDataset);

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>coil</b> </li>
	 * <li><b>current-sheet</b> </li></ul></p>
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getTypeScalar();

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>coil</b> </li>
	 * <li><b>current-sheet</b> </li></ul></p>
	 * </p>
	 * 
	 * @param type the type
	 */
	public DataNode setTypeScalar(String typeValue);

	/**
	 * Linear density of turns (such as number of turns/cm) in flipping field coils
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_PER_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getFlip_turns();
	
	/**
	 * Linear density of turns (such as number of turns/cm) in flipping field coils
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_PER_LENGTH
	 * </p>
	 * 
	 * @param flip_turnsDataset the flip_turnsDataset
	 */
	public DataNode setFlip_turns(IDataset flip_turnsDataset);

	/**
	 * Linear density of turns (such as number of turns/cm) in flipping field coils
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_PER_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getFlip_turnsScalar();

	/**
	 * Linear density of turns (such as number of turns/cm) in flipping field coils
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_PER_LENGTH
	 * </p>
	 * 
	 * @param flip_turns the flip_turns
	 */
	public DataNode setFlip_turnsScalar(Double flip_turnsValue);

	/**
	 * Linear density of turns (such as number of turns/cm) in compensating field coils
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_PER_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getComp_turns();
	
	/**
	 * Linear density of turns (such as number of turns/cm) in compensating field coils
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_PER_LENGTH
	 * </p>
	 * 
	 * @param comp_turnsDataset the comp_turnsDataset
	 */
	public DataNode setComp_turns(IDataset comp_turnsDataset);

	/**
	 * Linear density of turns (such as number of turns/cm) in compensating field coils
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_PER_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getComp_turnsScalar();

	/**
	 * Linear density of turns (such as number of turns/cm) in compensating field coils
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_PER_LENGTH
	 * </p>
	 * 
	 * @param comp_turns the comp_turns
	 */
	public DataNode setComp_turnsScalar(Double comp_turnsValue);

	/**
	 * Linear density of turns (such as number of turns/cm) in guide field coils
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_PER_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getGuide_turns();
	
	/**
	 * Linear density of turns (such as number of turns/cm) in guide field coils
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_PER_LENGTH
	 * </p>
	 * 
	 * @param guide_turnsDataset the guide_turnsDataset
	 */
	public DataNode setGuide_turns(IDataset guide_turnsDataset);

	/**
	 * Linear density of turns (such as number of turns/cm) in guide field coils
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_PER_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getGuide_turnsScalar();

	/**
	 * Linear density of turns (such as number of turns/cm) in guide field coils
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_PER_LENGTH
	 * </p>
	 * 
	 * @param guide_turns the guide_turns
	 */
	public DataNode setGuide_turnsScalar(Double guide_turnsValue);

	/**
	 * Flipping field coil current in "on" state"
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getFlip_current();
	
	/**
	 * Flipping field coil current in "on" state"
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 * 
	 * @param flip_currentDataset the flip_currentDataset
	 */
	public DataNode setFlip_current(IDataset flip_currentDataset);

	/**
	 * Flipping field coil current in "on" state"
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getFlip_currentScalar();

	/**
	 * Flipping field coil current in "on" state"
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 * 
	 * @param flip_current the flip_current
	 */
	public DataNode setFlip_currentScalar(Double flip_currentValue);

	/**
	 * Compensating field coil current in "on" state"
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getComp_current();
	
	/**
	 * Compensating field coil current in "on" state"
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 * 
	 * @param comp_currentDataset the comp_currentDataset
	 */
	public DataNode setComp_current(IDataset comp_currentDataset);

	/**
	 * Compensating field coil current in "on" state"
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getComp_currentScalar();

	/**
	 * Compensating field coil current in "on" state"
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 * 
	 * @param comp_current the comp_current
	 */
	public DataNode setComp_currentScalar(Double comp_currentValue);

	/**
	 * Guide field coil current in "on" state"
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getGuide_current();
	
	/**
	 * Guide field coil current in "on" state"
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 * 
	 * @param guide_currentDataset the guide_currentDataset
	 */
	public DataNode setGuide_current(IDataset guide_currentDataset);

	/**
	 * Guide field coil current in "on" state"
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getGuide_currentScalar();

	/**
	 * Guide field coil current in "on" state"
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 * 
	 * @param guide_current the guide_current
	 */
	public DataNode setGuide_currentScalar(Double guide_currentValue);

	/**
	 * thickness along path of neutron travel
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getThickness();
	
	/**
	 * thickness along path of neutron travel
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param thicknessDataset the thicknessDataset
	 */
	public DataNode setThickness(IDataset thicknessDataset);

	/**
	 * thickness along path of neutron travel
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getThicknessScalar();

	/**
	 * thickness along path of neutron travel
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param thickness the thickness
	 */
	public DataNode setThicknessScalar(Double thicknessValue);

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

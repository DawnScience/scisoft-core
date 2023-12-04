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

/**
 * Component of an instrument to store or place objects and specimens.
 * 
 */
public interface NXchamber extends NXobject {

	public static final String NX_NAME = "name";
	public static final String NX_DESCRIPTION = "description";
	/**
	 * Given name/alias.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getName();
	
	/**
	 * Given name/alias.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param nameDataset the nameDataset
	 */
	public DataNode setName(IDataset nameDataset);

	/**
	 * Given name/alias.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getNameScalar();

	/**
	 * Given name/alias.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param name the name
	 */
	public DataNode setNameScalar(String nameValue);

	/**
	 * 
	 * @return  the value.
	 */
	public NXmanufacturer getManufacturer();
	
	/**
	 * 
	 * @param manufacturerGroup the manufacturerGroup
	 */
	public void setManufacturer(NXmanufacturer manufacturerGroup);

	/**
	 * Get a NXmanufacturer node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXmanufacturer for that node.
	 */
	public NXmanufacturer getManufacturer(String name);
	
	/**
	 * Set a NXmanufacturer node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param manufacturer the value to set
	 */
	public void setManufacturer(String name, NXmanufacturer manufacturer);
	
	/**
	 * Get all NXmanufacturer nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXmanufacturer for that node.
	 */
	public Map<String, NXmanufacturer> getAllManufacturer();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param manufacturer the child nodes to add 
	 */
	
	public void setAllManufacturer(Map<String, NXmanufacturer> manufacturer);
	

	/**
	 * Free-text field for describing details about the chamber.
	 * For example out of which material was the chamber built.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getDescription();
	
	/**
	 * Free-text field for describing details about the chamber.
	 * For example out of which material was the chamber built.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param descriptionDataset the descriptionDataset
	 */
	public DataNode setDescription(IDataset descriptionDataset);

	/**
	 * Free-text field for describing details about the chamber.
	 * For example out of which material was the chamber built.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getDescriptionScalar();

	/**
	 * Free-text field for describing details about the chamber.
	 * For example out of which material was the chamber built.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param description the description
	 */
	public DataNode setDescriptionScalar(String descriptionValue);

}

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
 * Description of an electro-magnetic lens or a compound lens.
 * For NXtransformations the origin of the coordinate system is placed
 * in the center of the lens
 * (its polepiece, pinhole, or another point of reference).
 * The origin should be specified in the NXtransformations.
 * For details of electro-magnetic lenses in the literature see e.g. `L. Reimer <https://doi.org/10.1007/978-3-540-38967-5>`_
 * 
 */
public interface NXlens_em extends NXobject {

	public static final String NX_TYPE = "type";
	public static final String NX_NAME = "name";
	public static final String NX_MANUFACTURER_NAME = "manufacturer_name";
	public static final String NX_MODEL = "model";
	public static final String NX_DESCRIPTION = "description";
	public static final String NX_VOLTAGE = "voltage";
	public static final String NX_CURRENT = "current";
	public static final String NX_DEPENDS_ON = "depends_on";
	/**
	 * Qualitative type of lens with respect to the number of pole pieces.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>single</b> </li>
	 * <li><b>double</b> </li>
	 * <li><b>quadrupole</b> </li>
	 * <li><b>hexapole</b> </li>
	 * <li><b>octupole</b> </li></ul></p>
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getType();
	
	/**
	 * Qualitative type of lens with respect to the number of pole pieces.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>single</b> </li>
	 * <li><b>double</b> </li>
	 * <li><b>quadrupole</b> </li>
	 * <li><b>hexapole</b> </li>
	 * <li><b>octupole</b> </li></ul></p>
	 * </p>
	 * 
	 * @param typeDataset the typeDataset
	 */
	public DataNode setType(IDataset typeDataset);

	/**
	 * Qualitative type of lens with respect to the number of pole pieces.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>single</b> </li>
	 * <li><b>double</b> </li>
	 * <li><b>quadrupole</b> </li>
	 * <li><b>hexapole</b> </li>
	 * <li><b>octupole</b> </li></ul></p>
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getTypeScalar();

	/**
	 * Qualitative type of lens with respect to the number of pole pieces.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>single</b> </li>
	 * <li><b>double</b> </li>
	 * <li><b>quadrupole</b> </li>
	 * <li><b>hexapole</b> </li>
	 * <li><b>octupole</b> </li></ul></p>
	 * </p>
	 * 
	 * @param type the type
	 */
	public DataNode setTypeScalar(String typeValue);

	/**
	 * Given name, alias, colloquial, or short name for the lens.
	 * For manufacturer names and identifiers use respective manufacturer fields.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getName();
	
	/**
	 * Given name, alias, colloquial, or short name for the lens.
	 * For manufacturer names and identifiers use respective manufacturer fields.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param nameDataset the nameDataset
	 */
	public DataNode setName(IDataset nameDataset);

	/**
	 * Given name, alias, colloquial, or short name for the lens.
	 * For manufacturer names and identifiers use respective manufacturer fields.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getNameScalar();

	/**
	 * Given name, alias, colloquial, or short name for the lens.
	 * For manufacturer names and identifiers use respective manufacturer fields.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param name the name
	 */
	public DataNode setNameScalar(String nameValue);

	/**
	 * Name of the manufacturer who built/constructed the lens.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getManufacturer_name();
	
	/**
	 * Name of the manufacturer who built/constructed the lens.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param manufacturer_nameDataset the manufacturer_nameDataset
	 */
	public DataNode setManufacturer_name(IDataset manufacturer_nameDataset);

	/**
	 * Name of the manufacturer who built/constructed the lens.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getManufacturer_nameScalar();

	/**
	 * Name of the manufacturer who built/constructed the lens.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param manufacturer_name the manufacturer_name
	 */
	public DataNode setManufacturer_nameScalar(String manufacturer_nameValue);

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
	 * Hardware name, hash identifier, or serial number that was given by the
	 * manufacturer to identify the lens.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getModel();
	
	/**
	 * Hardware name, hash identifier, or serial number that was given by the
	 * manufacturer to identify the lens.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param modelDataset the modelDataset
	 */
	public DataNode setModel(IDataset modelDataset);

	/**
	 * Hardware name, hash identifier, or serial number that was given by the
	 * manufacturer to identify the lens.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getModelScalar();

	/**
	 * Hardware name, hash identifier, or serial number that was given by the
	 * manufacturer to identify the lens.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param model the model
	 */
	public DataNode setModelScalar(String modelValue);

	/**
	 * Ideally an identifier, persistent link, or free text which gives further details
	 * about the lens.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getDescription();
	
	/**
	 * Ideally an identifier, persistent link, or free text which gives further details
	 * about the lens.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param descriptionDataset the descriptionDataset
	 */
	public DataNode setDescription(IDataset descriptionDataset);

	/**
	 * Ideally an identifier, persistent link, or free text which gives further details
	 * about the lens.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getDescriptionScalar();

	/**
	 * Ideally an identifier, persistent link, or free text which gives further details
	 * about the lens.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param description the description
	 */
	public DataNode setDescriptionScalar(String descriptionValue);

	/**
	 * Excitation voltage of the lens. For dipoles it is a single number. For higher
	 * orders, it is an array.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_VOLTAGE
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getVoltage();
	
	/**
	 * Excitation voltage of the lens. For dipoles it is a single number. For higher
	 * orders, it is an array.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_VOLTAGE
	 * </p>
	 * 
	 * @param voltageDataset the voltageDataset
	 */
	public DataNode setVoltage(IDataset voltageDataset);

	/**
	 * Excitation voltage of the lens. For dipoles it is a single number. For higher
	 * orders, it is an array.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_VOLTAGE
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Number getVoltageScalar();

	/**
	 * Excitation voltage of the lens. For dipoles it is a single number. For higher
	 * orders, it is an array.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_VOLTAGE
	 * </p>
	 * 
	 * @param voltage the voltage
	 */
	public DataNode setVoltageScalar(Number voltageValue);

	/**
	 * Excitation current of the lens. For dipoles it is a single number. For higher
	 * orders, it is an array.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getCurrent();
	
	/**
	 * Excitation current of the lens. For dipoles it is a single number. For higher
	 * orders, it is an array.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 * 
	 * @param currentDataset the currentDataset
	 */
	public DataNode setCurrent(IDataset currentDataset);

	/**
	 * Excitation current of the lens. For dipoles it is a single number. For higher
	 * orders, it is an array.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Number getCurrentScalar();

	/**
	 * Excitation current of the lens. For dipoles it is a single number. For higher
	 * orders, it is an array.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_CURRENT
	 * </p>
	 * 
	 * @param current the current
	 */
	public DataNode setCurrentScalar(Number currentValue);

	/**
	 * Specifies the position of the lens by pointing to the last transformation in the
	 * transformation chain in the NXtransformations group.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getDepends_on();
	
	/**
	 * Specifies the position of the lens by pointing to the last transformation in the
	 * transformation chain in the NXtransformations group.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param depends_onDataset the depends_onDataset
	 */
	public DataNode setDepends_on(IDataset depends_onDataset);

	/**
	 * Specifies the position of the lens by pointing to the last transformation in the
	 * transformation chain in the NXtransformations group.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getDepends_onScalar();

	/**
	 * Specifies the position of the lens by pointing to the last transformation in the
	 * transformation chain in the NXtransformations group.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param depends_on the depends_on
	 */
	public DataNode setDepends_onScalar(String depends_onValue);

	/**
	 * Collection of axis-based translations and rotations to describe the
	 * location and geometry of the lens as a component in the instrument.
	 * Typically, the components of a system should all be related relative to
	 * each other and only one component should relate to the reference
	 * coordinate system.
	 * 
	 * @return  the value.
	 */
	public NXtransformations getTransformations();
	
	/**
	 * Collection of axis-based translations and rotations to describe the
	 * location and geometry of the lens as a component in the instrument.
	 * Typically, the components of a system should all be related relative to
	 * each other and only one component should relate to the reference
	 * coordinate system.
	 * 
	 * @param transformationsGroup the transformationsGroup
	 */
	public void setTransformations(NXtransformations transformationsGroup);

	/**
	 * Get a NXtransformations node by name:
	 * <ul>
	 * <li>
	 * Collection of axis-based translations and rotations to describe the
	 * location and geometry of the lens as a component in the instrument.
	 * Typically, the components of a system should all be related relative to
	 * each other and only one component should relate to the reference
	 * coordinate system.</li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXtransformations for that node.
	 */
	public NXtransformations getTransformations(String name);
	
	/**
	 * Set a NXtransformations node by name:
	 * <ul>
	 * <li>
	 * Collection of axis-based translations and rotations to describe the
	 * location and geometry of the lens as a component in the instrument.
	 * Typically, the components of a system should all be related relative to
	 * each other and only one component should relate to the reference
	 * coordinate system.</li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param transformations the value to set
	 */
	public void setTransformations(String name, NXtransformations transformations);
	
	/**
	 * Get all NXtransformations nodes:
	 * <ul>
	 * <li>
	 * Collection of axis-based translations and rotations to describe the
	 * location and geometry of the lens as a component in the instrument.
	 * Typically, the components of a system should all be related relative to
	 * each other and only one component should relate to the reference
	 * coordinate system.</li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXtransformations for that node.
	 */
	public Map<String, NXtransformations> getAllTransformations();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Collection of axis-based translations and rotations to describe the
	 * location and geometry of the lens as a component in the instrument.
	 * Typically, the components of a system should all be related relative to
	 * each other and only one component should relate to the reference
	 * coordinate system.</li>
	 * </ul>
	 * 
	 * @param transformations the child nodes to add 
	 */
	
	public void setAllTransformations(Map<String, NXtransformations> transformations);
	

}

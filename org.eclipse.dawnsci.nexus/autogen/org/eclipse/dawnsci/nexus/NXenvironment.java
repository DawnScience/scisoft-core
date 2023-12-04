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
 * Parameters for controlling external conditions
 * 
 */
public interface NXenvironment extends NXobject {

	public static final String NX_NAME = "name";
	public static final String NX_SHORT_NAME = "short_name";
	public static final String NX_TYPE = "type";
	public static final String NX_DESCRIPTION = "description";
	public static final String NX_PROGRAM = "program";
	public static final String NX_DEPENDS_ON = "depends_on";
	/**
	 * Apparatus identification code/model number; e.g. OC100 011
	 * 
	 * @return  the value.
	 */
	public IDataset getName();
	
	/**
	 * Apparatus identification code/model number; e.g. OC100 011
	 * 
	 * @param nameDataset the nameDataset
	 */
	public DataNode setName(IDataset nameDataset);

	/**
	 * Apparatus identification code/model number; e.g. OC100 011
	 * 
	 * @return  the value.
	 */
	public String getNameScalar();

	/**
	 * Apparatus identification code/model number; e.g. OC100 011
	 * 
	 * @param name the name
	 */
	public DataNode setNameScalar(String nameValue);

	/**
	 * Alternative short name, perhaps for dashboard display like a present Seblock name
	 * 
	 * @return  the value.
	 */
	public IDataset getShort_name();
	
	/**
	 * Alternative short name, perhaps for dashboard display like a present Seblock name
	 * 
	 * @param short_nameDataset the short_nameDataset
	 */
	public DataNode setShort_name(IDataset short_nameDataset);

	/**
	 * Alternative short name, perhaps for dashboard display like a present Seblock name
	 * 
	 * @return  the value.
	 */
	public String getShort_nameScalar();

	/**
	 * Alternative short name, perhaps for dashboard display like a present Seblock name
	 * 
	 * @param short_name the short_name
	 */
	public DataNode setShort_nameScalar(String short_nameValue);

	/**
	 * Type of apparatus. This could be the SE codes in scheduling database; e.g. OC/100
	 * 
	 * @return  the value.
	 */
	public IDataset getType();
	
	/**
	 * Type of apparatus. This could be the SE codes in scheduling database; e.g. OC/100
	 * 
	 * @param typeDataset the typeDataset
	 */
	public DataNode setType(IDataset typeDataset);

	/**
	 * Type of apparatus. This could be the SE codes in scheduling database; e.g. OC/100
	 * 
	 * @return  the value.
	 */
	public String getTypeScalar();

	/**
	 * Type of apparatus. This could be the SE codes in scheduling database; e.g. OC/100
	 * 
	 * @param type the type
	 */
	public DataNode setTypeScalar(String typeValue);

	/**
	 * Description of the apparatus; e.g. 100mm bore orange cryostat with Roots pump
	 * 
	 * @return  the value.
	 */
	public IDataset getDescription();
	
	/**
	 * Description of the apparatus; e.g. 100mm bore orange cryostat with Roots pump
	 * 
	 * @param descriptionDataset the descriptionDataset
	 */
	public DataNode setDescription(IDataset descriptionDataset);

	/**
	 * Description of the apparatus; e.g. 100mm bore orange cryostat with Roots pump
	 * 
	 * @return  the value.
	 */
	public String getDescriptionScalar();

	/**
	 * Description of the apparatus; e.g. 100mm bore orange cryostat with Roots pump
	 * 
	 * @param description the description
	 */
	public DataNode setDescriptionScalar(String descriptionValue);

	/**
	 * Program controlling the apparatus; e.g. LabView VI name
	 * 
	 * @return  the value.
	 */
	public IDataset getProgram();
	
	/**
	 * Program controlling the apparatus; e.g. LabView VI name
	 * 
	 * @param programDataset the programDataset
	 */
	public DataNode setProgram(IDataset programDataset);

	/**
	 * Program controlling the apparatus; e.g. LabView VI name
	 * 
	 * @return  the value.
	 */
	public String getProgramScalar();

	/**
	 * Program controlling the apparatus; e.g. LabView VI name
	 * 
	 * @param program the program
	 */
	public DataNode setProgramScalar(String programValue);

	/**
	 * The position and orientation of the apparatus.
	 * Note, it is recommended to use NXtransformations instead.
	 * 
	 * @return  the value.
	 */
	public NXgeometry getPosition();
	
	/**
	 * The position and orientation of the apparatus.
	 * Note, it is recommended to use NXtransformations instead.
	 * 
	 * @param positionGroup the positionGroup
	 */
	public void setPosition(NXgeometry positionGroup);

	/**
	 * NeXus positions components by applying a set of translations and rotations
	 * to apply to the component starting from 0, 0, 0. The order of these operations
	 * is critical and forms what NeXus calls a dependency chain. The depends_on
	 * field defines the path to the top most operation of the dependency chain or the
	 * string "." if located in the origin. Usually these operations are stored in a
	 * NXtransformations group. But NeXus allows them to be stored anywhere.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getDepends_on();
	
	/**
	 * NeXus positions components by applying a set of translations and rotations
	 * to apply to the component starting from 0, 0, 0. The order of these operations
	 * is critical and forms what NeXus calls a dependency chain. The depends_on
	 * field defines the path to the top most operation of the dependency chain or the
	 * string "." if located in the origin. Usually these operations are stored in a
	 * NXtransformations group. But NeXus allows them to be stored anywhere.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param depends_onDataset the depends_onDataset
	 */
	public DataNode setDepends_on(IDataset depends_onDataset);

	/**
	 * NeXus positions components by applying a set of translations and rotations
	 * to apply to the component starting from 0, 0, 0. The order of these operations
	 * is critical and forms what NeXus calls a dependency chain. The depends_on
	 * field defines the path to the top most operation of the dependency chain or the
	 * string "." if located in the origin. Usually these operations are stored in a
	 * NXtransformations group. But NeXus allows them to be stored anywhere.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getDepends_onScalar();

	/**
	 * NeXus positions components by applying a set of translations and rotations
	 * to apply to the component starting from 0, 0, 0. The order of these operations
	 * is critical and forms what NeXus calls a dependency chain. The depends_on
	 * field defines the path to the top most operation of the dependency chain or the
	 * string "." if located in the origin. Usually these operations are stored in a
	 * NXtransformations group. But NeXus allows them to be stored anywhere.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param depends_on the depends_on
	 */
	public DataNode setDepends_onScalar(String depends_onValue);

	/**
	 * This is the group recommended for holding the chain of translation
	 * and rotation operations necessary to position the component within
	 * the instrument. The dependency chain may however traverse similar groups in
	 * other component groups.
	 * 
	 * @return  the value.
	 */
	public NXtransformations getTransformations();
	
	/**
	 * This is the group recommended for holding the chain of translation
	 * and rotation operations necessary to position the component within
	 * the instrument. The dependency chain may however traverse similar groups in
	 * other component groups.
	 * 
	 * @param transformationsGroup the transformationsGroup
	 */
	public void setTransformations(NXtransformations transformationsGroup);

	/**
	 * Get a NXtransformations node by name:
	 * <ul>
	 * <li>
	 * This is the group recommended for holding the chain of translation
	 * and rotation operations necessary to position the component within
	 * the instrument. The dependency chain may however traverse similar groups in
	 * other component groups.</li>
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
	 * This is the group recommended for holding the chain of translation
	 * and rotation operations necessary to position the component within
	 * the instrument. The dependency chain may however traverse similar groups in
	 * other component groups.</li>
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
	 * This is the group recommended for holding the chain of translation
	 * and rotation operations necessary to position the component within
	 * the instrument. The dependency chain may however traverse similar groups in
	 * other component groups.</li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXtransformations for that node.
	 */
	public Map<String, NXtransformations> getAllTransformations();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * This is the group recommended for holding the chain of translation
	 * and rotation operations necessary to position the component within
	 * the instrument. The dependency chain may however traverse similar groups in
	 * other component groups.</li>
	 * </ul>
	 * 
	 * @param transformations the child nodes to add 
	 */
	
	public void setAllTransformations(Map<String, NXtransformations> transformations);
	

	/**
	 * Additional information, LabView logs, digital photographs, etc
	 * 
	 * @return  the value.
	 */
	public NXnote getNote();
	
	/**
	 * Additional information, LabView logs, digital photographs, etc
	 * 
	 * @param noteGroup the noteGroup
	 */
	public void setNote(NXnote noteGroup);

	/**
	 * Get a NXnote node by name:
	 * <ul>
	 * <li>
	 * Additional information, LabView logs, digital photographs, etc</li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXnote for that node.
	 */
	public NXnote getNote(String name);
	
	/**
	 * Set a NXnote node by name:
	 * <ul>
	 * <li>
	 * Additional information, LabView logs, digital photographs, etc</li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param note the value to set
	 */
	public void setNote(String name, NXnote note);
	
	/**
	 * Get all NXnote nodes:
	 * <ul>
	 * <li>
	 * Additional information, LabView logs, digital photographs, etc</li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXnote for that node.
	 */
	public Map<String, NXnote> getAllNote();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Additional information, LabView logs, digital photographs, etc</li>
	 * </ul>
	 * 
	 * @param note the child nodes to add 
	 */
	
	public void setAllNote(Map<String, NXnote> note);
	

	/**
	 * 
	 * @return  the value.
	 */
	public NXsensor getSensor();
	
	/**
	 * 
	 * @param sensorGroup the sensorGroup
	 */
	public void setSensor(NXsensor sensorGroup);

	/**
	 * Get a NXsensor node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXsensor for that node.
	 */
	public NXsensor getSensor(String name);
	
	/**
	 * Set a NXsensor node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param sensor the value to set
	 */
	public void setSensor(String name, NXsensor sensor);
	
	/**
	 * Get all NXsensor nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXsensor for that node.
	 */
	public Map<String, NXsensor> getAllSensor();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param sensor the child nodes to add 
	 */
	
	public void setAllSensor(Map<String, NXsensor> sensor);
	

}

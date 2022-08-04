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
 * A neutron moderator
 * 
 */
public interface NXmoderator extends NXobject {

	public static final String NX_DISTANCE = "distance";
	public static final String NX_TYPE = "type";
	public static final String NX_POISON_DEPTH = "poison_depth";
	public static final String NX_COUPLED = "coupled";
	public static final String NX_COUPLING_MATERIAL = "coupling_material";
	public static final String NX_POISON_MATERIAL = "poison_material";
	public static final String NX_TEMPERATURE = "temperature";
	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	/**
	 * "Engineering" position of moderator
	 * 
	 * @return  the value.
	 */
	public NXgeometry getGeometry();
	
	/**
	 * "Engineering" position of moderator
	 * 
	 * @param geometryGroup the geometryGroup
	 */
	public void setGeometry(NXgeometry geometryGroup);

	/**
	 * Get a NXgeometry node by name:
	 * <ul>
	 * <li>
	 * "Engineering" position of moderator</li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXgeometry for that node.
	 */
	public NXgeometry getGeometry(String name);
	
	/**
	 * Set a NXgeometry node by name:
	 * <ul>
	 * <li>
	 * "Engineering" position of moderator</li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param geometry the value to set
	 */
	public void setGeometry(String name, NXgeometry geometry);
	
	/**
	 * Get all NXgeometry nodes:
	 * <ul>
	 * <li>
	 * "Engineering" position of moderator</li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXgeometry for that node.
	 */
	public Map<String, NXgeometry> getAllGeometry();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * "Engineering" position of moderator</li>
	 * </ul>
	 * 
	 * @param geometry the child nodes to add 
	 */
	
	public void setAllGeometry(Map<String, NXgeometry> geometry);
	

	/**
	 * Effective distance as seen by measuring radiation
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getDistance();
	
	/**
	 * Effective distance as seen by measuring radiation
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param distanceDataset the distanceDataset
	 */
	public DataNode setDistance(IDataset distanceDataset);

	/**
	 * Effective distance as seen by measuring radiation
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getDistanceScalar();

	/**
	 * Effective distance as seen by measuring radiation
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param distance the distance
	 */
	public DataNode setDistanceScalar(Double distanceValue);

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>H20</b> </li>
	 * <li><b>D20</b> </li>
	 * <li><b>Liquid H2</b> </li>
	 * <li><b>Liquid CH4</b> </li>
	 * <li><b>Liquid D2</b> </li>
	 * <li><b>Solid D2</b> </li>
	 * <li><b>C</b> </li>
	 * <li><b>Solid CH4</b> </li>
	 * <li><b>Solid H2</b> </li></ul></p>
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getType();
	
	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>H20</b> </li>
	 * <li><b>D20</b> </li>
	 * <li><b>Liquid H2</b> </li>
	 * <li><b>Liquid CH4</b> </li>
	 * <li><b>Liquid D2</b> </li>
	 * <li><b>Solid D2</b> </li>
	 * <li><b>C</b> </li>
	 * <li><b>Solid CH4</b> </li>
	 * <li><b>Solid H2</b> </li></ul></p>
	 * </p>
	 * 
	 * @param typeDataset the typeDataset
	 */
	public DataNode setType(IDataset typeDataset);

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>H20</b> </li>
	 * <li><b>D20</b> </li>
	 * <li><b>Liquid H2</b> </li>
	 * <li><b>Liquid CH4</b> </li>
	 * <li><b>Liquid D2</b> </li>
	 * <li><b>Solid D2</b> </li>
	 * <li><b>C</b> </li>
	 * <li><b>Solid CH4</b> </li>
	 * <li><b>Solid H2</b> </li></ul></p>
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getTypeScalar();

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>H20</b> </li>
	 * <li><b>D20</b> </li>
	 * <li><b>Liquid H2</b> </li>
	 * <li><b>Liquid CH4</b> </li>
	 * <li><b>Liquid D2</b> </li>
	 * <li><b>Solid D2</b> </li>
	 * <li><b>C</b> </li>
	 * <li><b>Solid CH4</b> </li>
	 * <li><b>Solid H2</b> </li></ul></p>
	 * </p>
	 * 
	 * @param type the type
	 */
	public DataNode setTypeScalar(String typeValue);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getPoison_depth();
	
	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param poison_depthDataset the poison_depthDataset
	 */
	public DataNode setPoison_depth(IDataset poison_depthDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getPoison_depthScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * </p>
	 * 
	 * @param poison_depth the poison_depth
	 */
	public DataNode setPoison_depthScalar(Double poison_depthValue);

	/**
	 * whether the moderator is coupled
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getCoupled();
	
	/**
	 * whether the moderator is coupled
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 * 
	 * @param coupledDataset the coupledDataset
	 */
	public DataNode setCoupled(IDataset coupledDataset);

	/**
	 * whether the moderator is coupled
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Boolean getCoupledScalar();

	/**
	 * whether the moderator is coupled
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 * 
	 * @param coupled the coupled
	 */
	public DataNode setCoupledScalar(Boolean coupledValue);

	/**
	 * The material used for coupling. Usually Cd.
	 * 
	 * @return  the value.
	 */
	public IDataset getCoupling_material();
	
	/**
	 * The material used for coupling. Usually Cd.
	 * 
	 * @param coupling_materialDataset the coupling_materialDataset
	 */
	public DataNode setCoupling_material(IDataset coupling_materialDataset);

	/**
	 * The material used for coupling. Usually Cd.
	 * 
	 * @return  the value.
	 */
	public String getCoupling_materialScalar();

	/**
	 * The material used for coupling. Usually Cd.
	 * 
	 * @param coupling_material the coupling_material
	 */
	public DataNode setCoupling_materialScalar(String coupling_materialValue);

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>Gd</b> </li>
	 * <li><b>Cd</b> </li></ul></p>
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getPoison_material();
	
	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>Gd</b> </li>
	 * <li><b>Cd</b> </li></ul></p>
	 * </p>
	 * 
	 * @param poison_materialDataset the poison_materialDataset
	 */
	public DataNode setPoison_material(IDataset poison_materialDataset);

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>Gd</b> </li>
	 * <li><b>Cd</b> </li></ul></p>
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getPoison_materialScalar();

	/**
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>Gd</b> </li>
	 * <li><b>Cd</b> </li></ul></p>
	 * </p>
	 * 
	 * @param poison_material the poison_material
	 */
	public DataNode setPoison_materialScalar(String poison_materialValue);

	/**
	 * average/nominal moderator temperature
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TEMPERATURE
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getTemperature();
	
	/**
	 * average/nominal moderator temperature
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TEMPERATURE
	 * </p>
	 * 
	 * @param temperatureDataset the temperatureDataset
	 */
	public DataNode setTemperature(IDataset temperatureDataset);

	/**
	 * average/nominal moderator temperature
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TEMPERATURE
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getTemperatureScalar();

	/**
	 * average/nominal moderator temperature
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_TEMPERATURE
	 * </p>
	 * 
	 * @param temperature the temperature
	 */
	public DataNode setTemperatureScalar(Double temperatureValue);

	/**
	 * log file of moderator temperature
	 * 
	 * @return  the value.
	 */
	public NXlog getTemperature_log();
	
	/**
	 * log file of moderator temperature
	 * 
	 * @param temperature_logGroup the temperature_logGroup
	 */
	public void setTemperature_log(NXlog temperature_logGroup);

	/**
	 * moderator pulse shape
	 * 
	 * @return  the value.
	 */
	public NXdata getPulse_shape();
	
	/**
	 * moderator pulse shape
	 * 
	 * @param pulse_shapeGroup the pulse_shapeGroup
	 */
	public void setPulse_shape(NXdata pulse_shapeGroup);

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

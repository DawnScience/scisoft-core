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
 * legacy class - recommend to use :ref:`NXtransformations` now
 * Description for a general orientation of a component - used by :ref:`NXgeometry`
 * 
 */
public interface NXorientation extends NXobject {

	public static final String NX_VALUE = "value";
	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	/**
	 * Link to another object if we are using relative positioning, else absent
	 * 
	 * @return  the value.
	 */
	public NXgeometry getGeometry();
	
	/**
	 * Link to another object if we are using relative positioning, else absent
	 * 
	 * @param geometryGroup the geometryGroup
	 */
	public void setGeometry(NXgeometry geometryGroup);

	/**
	 * Get a NXgeometry node by name:
	 * <ul>
	 * <li>
	 * Link to another object if we are using relative positioning, else absent</li>
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
	 * Link to another object if we are using relative positioning, else absent</li>
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
	 * Link to another object if we are using relative positioning, else absent</li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXgeometry for that node.
	 */
	public Map<String, NXgeometry> getAllGeometry();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Link to another object if we are using relative positioning, else absent</li>
	 * </ul>
	 * 
	 * @param geometry the child nodes to add 
	 */
	
	public void setAllGeometry(Map<String, NXgeometry> geometry);
	

	/**
	 * The orientation information is stored as direction cosines. The direction cosines will
	 * be between the local coordinate directions and the reference directions (to origin or
	 * relative NXgeometry). Calling the local unit vectors (x',y',z') and the reference unit
	 * vectors (x,y,z) the six numbers will be [x' dot x, x' dot y, x' dot z, y' dot x, y' dot
	 * y, y' dot z] where "dot" is the scalar dot product (cosine of the angle between the unit
	 * vectors). The unit vectors in both the local and reference coordinates are right-handed
	 * and orthonormal.
	 * The pair of groups NXtranslation and NXorientation together
	 * describe the position of a component.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: numobj; 2: 6;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getValue();
	
	/**
	 * The orientation information is stored as direction cosines. The direction cosines will
	 * be between the local coordinate directions and the reference directions (to origin or
	 * relative NXgeometry). Calling the local unit vectors (x',y',z') and the reference unit
	 * vectors (x,y,z) the six numbers will be [x' dot x, x' dot y, x' dot z, y' dot x, y' dot
	 * y, y' dot z] where "dot" is the scalar dot product (cosine of the angle between the unit
	 * vectors). The unit vectors in both the local and reference coordinates are right-handed
	 * and orthonormal.
	 * The pair of groups NXtranslation and NXorientation together
	 * describe the position of a component.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: numobj; 2: 6;
	 * </p>
	 * 
	 * @param valueDataset the valueDataset
	 */
	public DataNode setValue(IDataset valueDataset);

	/**
	 * The orientation information is stored as direction cosines. The direction cosines will
	 * be between the local coordinate directions and the reference directions (to origin or
	 * relative NXgeometry). Calling the local unit vectors (x',y',z') and the reference unit
	 * vectors (x,y,z) the six numbers will be [x' dot x, x' dot y, x' dot z, y' dot x, y' dot
	 * y, y' dot z] where "dot" is the scalar dot product (cosine of the angle between the unit
	 * vectors). The unit vectors in both the local and reference coordinates are right-handed
	 * and orthonormal.
	 * The pair of groups NXtranslation and NXorientation together
	 * describe the position of a component.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: numobj; 2: 6;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getValueScalar();

	/**
	 * The orientation information is stored as direction cosines. The direction cosines will
	 * be between the local coordinate directions and the reference directions (to origin or
	 * relative NXgeometry). Calling the local unit vectors (x',y',z') and the reference unit
	 * vectors (x,y,z) the six numbers will be [x' dot x, x' dot y, x' dot z, y' dot x, y' dot
	 * y, y' dot z] where "dot" is the scalar dot product (cosine of the angle between the unit
	 * vectors). The unit vectors in both the local and reference coordinates are right-handed
	 * and orthonormal.
	 * The pair of groups NXtranslation and NXorientation together
	 * describe the position of a component.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: numobj; 2: 6;
	 * </p>
	 * 
	 * @param value the value
	 */
	public DataNode setValueScalar(Double valueValue);

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

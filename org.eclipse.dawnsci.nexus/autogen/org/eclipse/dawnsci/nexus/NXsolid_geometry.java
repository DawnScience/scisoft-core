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


/**
 * the head node for constructively defined geometry
 * 
 */
public interface NXsolid_geometry extends NXobject {

	/**
	 * Instances of :ref:`NXquadric` making up elements of the geometry.
	 * 
	 * @return  the value.
	 */
	public NXquadric getQuadric();
	
	/**
	 * Instances of :ref:`NXquadric` making up elements of the geometry.
	 * 
	 * @param quadricGroup the quadricGroup
	 */
	public void setQuadric(NXquadric quadricGroup);

	/**
	 * Get a NXquadric node by name:
	 * <ul>
	 * <li>
	 * Instances of :ref:`NXquadric` making up elements of the geometry.</li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXquadric for that node.
	 */
	public NXquadric getQuadric(String name);
	
	/**
	 * Set a NXquadric node by name:
	 * <ul>
	 * <li>
	 * Instances of :ref:`NXquadric` making up elements of the geometry.</li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param quadric the value to set
	 */
	public void setQuadric(String name, NXquadric quadric);
	
	/**
	 * Get all NXquadric nodes:
	 * <ul>
	 * <li>
	 * Instances of :ref:`NXquadric` making up elements of the geometry.</li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXquadric for that node.
	 */
	public Map<String, NXquadric> getAllQuadric();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Instances of :ref:`NXquadric` making up elements of the geometry.</li>
	 * </ul>
	 * 
	 * @param quadric the child nodes to add 
	 */
	
	public void setAllQuadric(Map<String, NXquadric> quadric);
	

	/**
	 * Instances of :ref:`NXoff_geometry` making up elements of the geometry.
	 * 
	 * @return  the value.
	 */
	public NXoff_geometry getOff_geometry();
	
	/**
	 * Instances of :ref:`NXoff_geometry` making up elements of the geometry.
	 * 
	 * @param off_geometryGroup the off_geometryGroup
	 */
	public void setOff_geometry(NXoff_geometry off_geometryGroup);

	/**
	 * Get a NXoff_geometry node by name:
	 * <ul>
	 * <li>
	 * Instances of :ref:`NXoff_geometry` making up elements of the geometry.</li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXoff_geometry for that node.
	 */
	public NXoff_geometry getOff_geometry(String name);
	
	/**
	 * Set a NXoff_geometry node by name:
	 * <ul>
	 * <li>
	 * Instances of :ref:`NXoff_geometry` making up elements of the geometry.</li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param off_geometry the value to set
	 */
	public void setOff_geometry(String name, NXoff_geometry off_geometry);
	
	/**
	 * Get all NXoff_geometry nodes:
	 * <ul>
	 * <li>
	 * Instances of :ref:`NXoff_geometry` making up elements of the geometry.</li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXoff_geometry for that node.
	 */
	public Map<String, NXoff_geometry> getAllOff_geometry();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Instances of :ref:`NXoff_geometry` making up elements of the geometry.</li>
	 * </ul>
	 * 
	 * @param off_geometry the child nodes to add 
	 */
	
	public void setAllOff_geometry(Map<String, NXoff_geometry> off_geometry);
	

	/**
	 * The geometries defined, made up of instances of :ref:`NXquadric` and :ref:`NXoff_geometry`.
	 * 
	 * @return  the value.
	 */
	public NXcsg getCsg();
	
	/**
	 * The geometries defined, made up of instances of :ref:`NXquadric` and :ref:`NXoff_geometry`.
	 * 
	 * @param csgGroup the csgGroup
	 */
	public void setCsg(NXcsg csgGroup);

	/**
	 * Get a NXcsg node by name:
	 * <ul>
	 * <li>
	 * The geometries defined, made up of instances of :ref:`NXquadric` and :ref:`NXoff_geometry`.</li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXcsg for that node.
	 */
	public NXcsg getCsg(String name);
	
	/**
	 * Set a NXcsg node by name:
	 * <ul>
	 * <li>
	 * The geometries defined, made up of instances of :ref:`NXquadric` and :ref:`NXoff_geometry`.</li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param csg the value to set
	 */
	public void setCsg(String name, NXcsg csg);
	
	/**
	 * Get all NXcsg nodes:
	 * <ul>
	 * <li>
	 * The geometries defined, made up of instances of :ref:`NXquadric` and :ref:`NXoff_geometry`.</li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXcsg for that node.
	 */
	public Map<String, NXcsg> getAllCsg();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * The geometries defined, made up of instances of :ref:`NXquadric` and :ref:`NXoff_geometry`.</li>
	 * </ul>
	 * 
	 * @param csg the child nodes to add 
	 */
	
	public void setAllCsg(Map<String, NXcsg> csg);
	

}

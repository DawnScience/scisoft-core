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
 * A beamline aperture.
 * 
 */
public interface NXaperture extends NXobject {

	public static final String NX_MATERIAL = "material";
	public static final String NX_DESCRIPTION = "description";
	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	/**
	 * location and shape of aperture
	 * .. TODO: documentation needs improvement, contributions welcome
	 * * description of terms is poor and leaves much to interpretation
	 * * Describe what is meant by translation _here_ and ...
	 * * Similar throughout base classes
	 * * Some base classes do this much better
	 * * Such as where is the gap written?
	 * 
	 * @return  the value.
	 */
	public NXgeometry getGeometry();
	
	/**
	 * location and shape of aperture
	 * .. TODO: documentation needs improvement, contributions welcome
	 * * description of terms is poor and leaves much to interpretation
	 * * Describe what is meant by translation _here_ and ...
	 * * Similar throughout base classes
	 * * Some base classes do this much better
	 * * Such as where is the gap written?
	 * 
	 * @param geometryGroup the geometryGroup
	 */
	public void setGeometry(NXgeometry geometryGroup);

	/**
	 * Get a NXgeometry node by name:
	 * <ul>
	 * <li>
	 * location and shape of aperture
	 * .. TODO: documentation needs improvement, contributions welcome
	 * * description of terms is poor and leaves much to interpretation
	 * * Describe what is meant by translation _here_ and ...
	 * * Similar throughout base classes
	 * * Some base classes do this much better
	 * * Such as where is the gap written?</li>
	 * <li>
	 * location and shape of each blade</li>
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
	 * location and shape of aperture
	 * .. TODO: documentation needs improvement, contributions welcome
	 * * description of terms is poor and leaves much to interpretation
	 * * Describe what is meant by translation _here_ and ...
	 * * Similar throughout base classes
	 * * Some base classes do this much better
	 * * Such as where is the gap written?</li>
	 * <li>
	 * location and shape of each blade</li>
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
	 * location and shape of aperture
	 * .. TODO: documentation needs improvement, contributions welcome
	 * * description of terms is poor and leaves much to interpretation
	 * * Describe what is meant by translation _here_ and ...
	 * * Similar throughout base classes
	 * * Some base classes do this much better
	 * * Such as where is the gap written?</li>
	 * <li>
	 * location and shape of each blade</li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXgeometry for that node.
	 */
	public Map<String, NXgeometry> getAllGeometry();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * location and shape of aperture
	 * .. TODO: documentation needs improvement, contributions welcome
	 * * description of terms is poor and leaves much to interpretation
	 * * Describe what is meant by translation _here_ and ...
	 * * Similar throughout base classes
	 * * Some base classes do this much better
	 * * Such as where is the gap written?</li>
	 * <li>
	 * location and shape of each blade</li>
	 * </ul>
	 * 
	 * @param geometry the child nodes to add 
	 */
	
	public void setAllGeometry(Map<String, NXgeometry> geometry);
	

	/**
	 * location and shape of each blade
	 * 
	 * @return  the value.
	 */
	public NXgeometry getBlade_geometry();
	
	/**
	 * location and shape of each blade
	 * 
	 * @param blade_geometryGroup the blade_geometryGroup
	 */
	public void setBlade_geometry(NXgeometry blade_geometryGroup);

	/**
	 * Absorbing material of the aperture
	 * 
	 * @return  the value.
	 */
	public IDataset getMaterial();
	
	/**
	 * Absorbing material of the aperture
	 * 
	 * @param materialDataset the materialDataset
	 */
	public DataNode setMaterial(IDataset materialDataset);

	/**
	 * Absorbing material of the aperture
	 * 
	 * @return  the value.
	 */
	public String getMaterialScalar();

	/**
	 * Absorbing material of the aperture
	 * 
	 * @param material the material
	 */
	public DataNode setMaterialScalar(String materialValue);

	/**
	 * Description of aperture
	 * 
	 * @return  the value.
	 */
	public IDataset getDescription();
	
	/**
	 * Description of aperture
	 * 
	 * @param descriptionDataset the descriptionDataset
	 */
	public DataNode setDescription(IDataset descriptionDataset);

	/**
	 * Description of aperture
	 * 
	 * @return  the value.
	 */
	public String getDescriptionScalar();

	/**
	 * Description of aperture
	 * 
	 * @param description the description
	 */
	public DataNode setDescriptionScalar(String descriptionValue);

	/**
	 * describe any additional information in a note*
	 * 
	 * @return  the value.
	 */
	public NXnote getNote();
	
	/**
	 * describe any additional information in a note*
	 * 
	 * @param noteGroup the noteGroup
	 */
	public void setNote(NXnote noteGroup);

	/**
	 * Get a NXnote node by name:
	 * <ul>
	 * <li>
	 * describe any additional information in a note*</li>
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
	 * describe any additional information in a note*</li>
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
	 * describe any additional information in a note*</li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXnote for that node.
	 */
	public Map<String, NXnote> getAllNote();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * describe any additional information in a note*</li>
	 * </ul>
	 * 
	 * @param note the child nodes to add 
	 */
	
	public void setAllNote(Map<String, NXnote> note);
	

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

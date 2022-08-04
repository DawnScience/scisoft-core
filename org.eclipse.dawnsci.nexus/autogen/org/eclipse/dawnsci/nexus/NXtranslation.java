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
 * legacy class - (used by :ref:`NXgeometry`) - general spatial location of a component.
 * 
 */
public interface NXtranslation extends NXobject {

	public static final String NX_DISTANCES = "distances";
	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	/**
	 * Link to other object if we are relative, else absent
	 * 
	 * @return  the value.
	 */
	public NXgeometry getGeometry();
	
	/**
	 * Link to other object if we are relative, else absent
	 * 
	 * @param geometryGroup the geometryGroup
	 */
	public void setGeometry(NXgeometry geometryGroup);

	/**
	 * (x,y,z)
	 * This field describes the lateral movement of a component.
	 * The pair of groups NXtranslation and NXorientation together
	 * describe the position of a component.
	 * For absolute position, the origin is the scattering center (where a perfectly
	 * aligned sample would be) with the z-axis pointing downstream and the y-axis
	 * pointing gravitationally up. For a relative position the NXtranslation is
	 * taken into account before the NXorientation. The axes are right-handed and
	 * orthonormal.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: numobj; 2: 3;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getDistances();
	
	/**
	 * (x,y,z)
	 * This field describes the lateral movement of a component.
	 * The pair of groups NXtranslation and NXorientation together
	 * describe the position of a component.
	 * For absolute position, the origin is the scattering center (where a perfectly
	 * aligned sample would be) with the z-axis pointing downstream and the y-axis
	 * pointing gravitationally up. For a relative position the NXtranslation is
	 * taken into account before the NXorientation. The axes are right-handed and
	 * orthonormal.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: numobj; 2: 3;
	 * </p>
	 * 
	 * @param distancesDataset the distancesDataset
	 */
	public DataNode setDistances(IDataset distancesDataset);

	/**
	 * (x,y,z)
	 * This field describes the lateral movement of a component.
	 * The pair of groups NXtranslation and NXorientation together
	 * describe the position of a component.
	 * For absolute position, the origin is the scattering center (where a perfectly
	 * aligned sample would be) with the z-axis pointing downstream and the y-axis
	 * pointing gravitationally up. For a relative position the NXtranslation is
	 * taken into account before the NXorientation. The axes are right-handed and
	 * orthonormal.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: numobj; 2: 3;
	 * </p>
	 * 
	 * @return  the value.
	 */
	public Double getDistancesScalar();

	/**
	 * (x,y,z)
	 * This field describes the lateral movement of a component.
	 * The pair of groups NXtranslation and NXorientation together
	 * describe the position of a component.
	 * For absolute position, the origin is the scattering center (where a perfectly
	 * aligned sample would be) with the z-axis pointing downstream and the y-axis
	 * pointing gravitationally up. For a relative position the NXtranslation is
	 * taken into account before the NXorientation. The axes are right-handed and
	 * orthonormal.
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: numobj; 2: 3;
	 * </p>
	 * 
	 * @param distances the distances
	 */
	public DataNode setDistancesScalar(Double distancesValue);

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

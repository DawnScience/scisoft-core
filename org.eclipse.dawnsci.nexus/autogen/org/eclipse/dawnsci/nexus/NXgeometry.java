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
import org.eclipse.january.dataset.Dataset;

/**
 * legacy class - recommend to use :ref:`NXtransformations` now
 * It is recommended that instances of :ref:`NXgeometry` be converted to
 * use :ref:`NXtransformations`.
 * This is the description for a general position of a component.
 * It is recommended to name an instance of :ref:`NXgeometry` as "geometry"
 * to aid in the use of the definition in simulation codes such as McStas.
 * Also, in HDF, linked items must share the same name.
 * However, it might not be possible or practical in all situations.
 *
 * @deprecated as decided at 2014 NIAC meeting, convert to use :ref:`NXtransformations`
 */
@Deprecated
public interface NXgeometry extends NXobject {

	public static final String NX_DESCRIPTION = "description";
	public static final String NX_COMPONENT_INDEX = "component_index";
	public static final String NX_ATTRIBUTE_DEFAULT = "default";
	/**
	 * shape/size information of component
	 *
	 * @return  the value.
	 */
	public NXshape getShape();

	/**
	 * shape/size information of component
	 *
	 * @param shapeGroup the shapeGroup
	 */
	public void setShape(NXshape shapeGroup);

	/**
	 * Get a NXshape node by name:
	 * <ul>
	 * <li>
	 * shape/size information of component</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXshape for that node.
	 */
	public NXshape getShape(String name);

	/**
	 * Set a NXshape node by name:
	 * <ul>
	 * <li>
	 * shape/size information of component</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param shape the value to set
	 */
	public void setShape(String name, NXshape shape);

	/**
	 * Get all NXshape nodes:
	 * <ul>
	 * <li>
	 * shape/size information of component</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXshape for that node.
	 */
	public Map<String, NXshape> getAllShape();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * shape/size information of component</li>
	 * </ul>
	 *
	 * @param shape the child nodes to add
	 */

	public void setAllShape(Map<String, NXshape> shape);


	/**
	 * translation of component
	 *
	 * @return  the value.
	 */
	public NXtranslation getTranslation();

	/**
	 * translation of component
	 *
	 * @param translationGroup the translationGroup
	 */
	public void setTranslation(NXtranslation translationGroup);

	/**
	 * Get a NXtranslation node by name:
	 * <ul>
	 * <li>
	 * translation of component</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXtranslation for that node.
	 */
	public NXtranslation getTranslation(String name);

	/**
	 * Set a NXtranslation node by name:
	 * <ul>
	 * <li>
	 * translation of component</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param translation the value to set
	 */
	public void setTranslation(String name, NXtranslation translation);

	/**
	 * Get all NXtranslation nodes:
	 * <ul>
	 * <li>
	 * translation of component</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXtranslation for that node.
	 */
	public Map<String, NXtranslation> getAllTranslation();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * translation of component</li>
	 * </ul>
	 *
	 * @param translation the child nodes to add
	 */

	public void setAllTranslation(Map<String, NXtranslation> translation);


	/**
	 * orientation of component
	 *
	 * @return  the value.
	 */
	public NXorientation getOrientation();

	/**
	 * orientation of component
	 *
	 * @param orientationGroup the orientationGroup
	 */
	public void setOrientation(NXorientation orientationGroup);

	/**
	 * Get a NXorientation node by name:
	 * <ul>
	 * <li>
	 * orientation of component</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXorientation for that node.
	 */
	public NXorientation getOrientation(String name);

	/**
	 * Set a NXorientation node by name:
	 * <ul>
	 * <li>
	 * orientation of component</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param orientation the value to set
	 */
	public void setOrientation(String name, NXorientation orientation);

	/**
	 * Get all NXorientation nodes:
	 * <ul>
	 * <li>
	 * orientation of component</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXorientation for that node.
	 */
	public Map<String, NXorientation> getAllOrientation();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * orientation of component</li>
	 * </ul>
	 *
	 * @param orientation the child nodes to add
	 */

	public void setAllOrientation(Map<String, NXorientation> orientation);


	/**
	 * Optional description/label. Probably only present if we are
	 * an additional reference point for components rather than the
	 * location of a real component.
	 *
	 * @return  the value.
	 */
	public Dataset getDescription();

	/**
	 * Optional description/label. Probably only present if we are
	 * an additional reference point for components rather than the
	 * location of a real component.
	 *
	 * @param descriptionDataset the descriptionDataset
	 */
	public DataNode setDescription(IDataset descriptionDataset);

	/**
	 * Optional description/label. Probably only present if we are
	 * an additional reference point for components rather than the
	 * location of a real component.
	 *
	 * @return  the value.
	 */
	public String getDescriptionScalar();

	/**
	 * Optional description/label. Probably only present if we are
	 * an additional reference point for components rather than the
	 * location of a real component.
	 *
	 * @param description the description
	 */
	public DataNode setDescriptionScalar(String descriptionValue);

	/**
	 * Position of the component along the beam path. The sample is at 0, components upstream
	 * have negative component_index, components downstream have positive
	 * component_index.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Dataset getComponent_index();

	/**
	 * Position of the component along the beam path. The sample is at 0, components upstream
	 * have negative component_index, components downstream have positive
	 * component_index.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 *
	 * @param component_indexDataset the component_indexDataset
	 */
	public DataNode setComponent_index(IDataset component_indexDataset);

	/**
	 * Position of the component along the beam path. The sample is at 0, components upstream
	 * have negative component_index, components downstream have positive
	 * component_index.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getComponent_indexScalar();

	/**
	 * Position of the component along the beam path. The sample is at 0, components upstream
	 * have negative component_index, components downstream have positive
	 * component_index.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * </p>
	 *
	 * @param component_index the component_index
	 */
	public DataNode setComponent_indexScalar(Long component_indexValue);

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

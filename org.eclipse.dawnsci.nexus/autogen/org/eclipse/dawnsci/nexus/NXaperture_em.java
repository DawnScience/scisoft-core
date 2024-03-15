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
 * Details of an individual aperture for beams in electron microscopy.
 *
 */
public interface NXaperture_em extends NXobject {

	public static final String NX_NAME = "name";
	public static final String NX_VALUE = "value";
	public static final String NX_DESCRIPTION = "description";
	/**
	 * Given name/alias of the aperture.
	 *
	 * @return  the value.
	 */
	public IDataset getName();

	/**
	 * Given name/alias of the aperture.
	 *
	 * @param nameDataset the nameDataset
	 */
	public DataNode setName(IDataset nameDataset);

	/**
	 * Given name/alias of the aperture.
	 *
	 * @return  the value.
	 */
	public String getNameScalar();

	/**
	 * Given name/alias of the aperture.
	 *
	 * @param name the name
	 */
	public DataNode setNameScalar(String nameValue);

	/**
	 * Relevant value from the control software.
	 * This is not always just the diameter of (not even in the case)
	 * of a circular aperture. Usually it is a mode setting value which
	 * is selected in the control software.
	 * Which settings are behind the value should be defined
	 * for now in the description field, if these are known
	 * in more detail.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getValue();

	/**
	 * Relevant value from the control software.
	 * This is not always just the diameter of (not even in the case)
	 * of a circular aperture. Usually it is a mode setting value which
	 * is selected in the control software.
	 * Which settings are behind the value should be defined
	 * for now in the description field, if these are known
	 * in more detail.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param valueDataset the valueDataset
	 */
	public DataNode setValue(IDataset valueDataset);

	/**
	 * Relevant value from the control software.
	 * This is not always just the diameter of (not even in the case)
	 * of a circular aperture. Usually it is a mode setting value which
	 * is selected in the control software.
	 * Which settings are behind the value should be defined
	 * for now in the description field, if these are known
	 * in more detail.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getValueScalar();

	/**
	 * Relevant value from the control software.
	 * This is not always just the diameter of (not even in the case)
	 * of a circular aperture. Usually it is a mode setting value which
	 * is selected in the control software.
	 * Which settings are behind the value should be defined
	 * for now in the description field, if these are known
	 * in more detail.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * </p>
	 *
	 * @param value the value
	 */
	public DataNode setValueScalar(Number valueValue);

	/**
	 * Ideally, a (globally) unique persistent identifier, link, or text to a
	 * resource which gives further details. Alternatively a free-text field.
	 *
	 * @return  the value.
	 */
	public IDataset getDescription();

	/**
	 * Ideally, a (globally) unique persistent identifier, link, or text to a
	 * resource which gives further details. Alternatively a free-text field.
	 *
	 * @param descriptionDataset the descriptionDataset
	 */
	public DataNode setDescription(IDataset descriptionDataset);

	/**
	 * Ideally, a (globally) unique persistent identifier, link, or text to a
	 * resource which gives further details. Alternatively a free-text field.
	 *
	 * @return  the value.
	 */
	public String getDescriptionScalar();

	/**
	 * Ideally, a (globally) unique persistent identifier, link, or text to a
	 * resource which gives further details. Alternatively a free-text field.
	 *
	 * @param description the description
	 */
	public DataNode setDescriptionScalar(String descriptionValue);

	/**
	 *
	 * @return  the value.
	 */
	public NXfabrication getFabrication();

	/**
	 *
	 * @param fabricationGroup the fabricationGroup
	 */
	public void setFabrication(NXfabrication fabricationGroup);

	/**
	 * Get a NXfabrication node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXfabrication for that node.
	 */
	public NXfabrication getFabrication(String name);

	/**
	 * Set a NXfabrication node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param fabrication the value to set
	 */
	public void setFabrication(String name, NXfabrication fabrication);

	/**
	 * Get all NXfabrication nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXfabrication for that node.
	 */
	public Map<String, NXfabrication> getAllFabrication();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param fabrication the child nodes to add
	 */

	public void setAllFabrication(Map<String, NXfabrication> fabrication);


	/**
	 * Affine transformation which detail the arrangement in the
	 * microscope relative to the optical axis and beam path.
	 *
	 * @return  the value.
	 */
	public NXtransformations getTransformations();

	/**
	 * Affine transformation which detail the arrangement in the
	 * microscope relative to the optical axis and beam path.
	 *
	 * @param transformationsGroup the transformationsGroup
	 */
	public void setTransformations(NXtransformations transformationsGroup);

	/**
	 * Get a NXtransformations node by name:
	 * <ul>
	 * <li>
	 * Affine transformation which detail the arrangement in the
	 * microscope relative to the optical axis and beam path.</li>
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
	 * Affine transformation which detail the arrangement in the
	 * microscope relative to the optical axis and beam path.</li>
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
	 * Affine transformation which detail the arrangement in the
	 * microscope relative to the optical axis and beam path.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXtransformations for that node.
	 */
	public Map<String, NXtransformations> getAllTransformations();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Affine transformation which detail the arrangement in the
	 * microscope relative to the optical axis and beam path.</li>
	 * </ul>
	 *
	 * @param transformations the child nodes to add
	 */

	public void setAllTransformations(Map<String, NXtransformations> transformations);


}

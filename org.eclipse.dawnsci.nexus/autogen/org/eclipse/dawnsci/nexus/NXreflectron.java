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
 * Device for reducing flight time differences of ions in ToF experiments.
 * For atom probe the reflectron can be considered an energy_compensation
 * device, which can e.g. be realized technically as via a Poschenrieder lens
 * (see US patent 3863068 or US patents for the reflectron 6740872, or the curved reflectron 8134119 design).
 *
 */
public interface NXreflectron extends NXobject {

	public static final String NX_NAME = "name";
	public static final String NX_DESCRIPTION = "description";
	/**
	 * Given name/alias.
	 *
	 * @return  the value.
	 */
	public IDataset getName();

	/**
	 * Given name/alias.
	 *
	 * @param nameDataset the nameDataset
	 */
	public DataNode setName(IDataset nameDataset);

	/**
	 * Given name/alias.
	 *
	 * @return  the value.
	 */
	public String getNameScalar();

	/**
	 * Given name/alias.
	 *
	 * @param name the name
	 */
	public DataNode setNameScalar(String nameValue);

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
	 * Free-text field to specify further details about the reflectron.
	 * The field can be used to inform e. g. if the reflectron is flat or curved.
	 *
	 * @return  the value.
	 */
	public IDataset getDescription();

	/**
	 * Free-text field to specify further details about the reflectron.
	 * The field can be used to inform e. g. if the reflectron is flat or curved.
	 *
	 * @param descriptionDataset the descriptionDataset
	 */
	public DataNode setDescription(IDataset descriptionDataset);

	/**
	 * Free-text field to specify further details about the reflectron.
	 * The field can be used to inform e. g. if the reflectron is flat or curved.
	 *
	 * @return  the value.
	 */
	public String getDescriptionScalar();

	/**
	 * Free-text field to specify further details about the reflectron.
	 * The field can be used to inform e. g. if the reflectron is flat or curved.
	 *
	 * @param description the description
	 */
	public DataNode setDescriptionScalar(String descriptionValue);

	/**
	 * Affine transformation(s) which detail where the reflectron
	 * is located relative to e.g. the origin of the specimen space
	 * reference coordinate system.
	 * This group can also be used for specifying how the reflectron
	 * is rotated relative to the specimen axis.
	 * The purpose of these more detailed instrument descriptions
	 * is to support the creation of a digital twin of the instrument
	 * for computational science.
	 *
	 * @return  the value.
	 */
	public NXtransformations getTransformations();

	/**
	 * Affine transformation(s) which detail where the reflectron
	 * is located relative to e.g. the origin of the specimen space
	 * reference coordinate system.
	 * This group can also be used for specifying how the reflectron
	 * is rotated relative to the specimen axis.
	 * The purpose of these more detailed instrument descriptions
	 * is to support the creation of a digital twin of the instrument
	 * for computational science.
	 *
	 * @param transformationsGroup the transformationsGroup
	 */
	public void setTransformations(NXtransformations transformationsGroup);

	/**
	 * Get a NXtransformations node by name:
	 * <ul>
	 * <li>
	 * Affine transformation(s) which detail where the reflectron
	 * is located relative to e.g. the origin of the specimen space
	 * reference coordinate system.
	 * This group can also be used for specifying how the reflectron
	 * is rotated relative to the specimen axis.
	 * The purpose of these more detailed instrument descriptions
	 * is to support the creation of a digital twin of the instrument
	 * for computational science.</li>
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
	 * Affine transformation(s) which detail where the reflectron
	 * is located relative to e.g. the origin of the specimen space
	 * reference coordinate system.
	 * This group can also be used for specifying how the reflectron
	 * is rotated relative to the specimen axis.
	 * The purpose of these more detailed instrument descriptions
	 * is to support the creation of a digital twin of the instrument
	 * for computational science.</li>
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
	 * Affine transformation(s) which detail where the reflectron
	 * is located relative to e.g. the origin of the specimen space
	 * reference coordinate system.
	 * This group can also be used for specifying how the reflectron
	 * is rotated relative to the specimen axis.
	 * The purpose of these more detailed instrument descriptions
	 * is to support the creation of a digital twin of the instrument
	 * for computational science.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXtransformations for that node.
	 */
	public Map<String, NXtransformations> getAllTransformations();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Affine transformation(s) which detail where the reflectron
	 * is located relative to e.g. the origin of the specimen space
	 * reference coordinate system.
	 * This group can also be used for specifying how the reflectron
	 * is rotated relative to the specimen axis.
	 * The purpose of these more detailed instrument descriptions
	 * is to support the creation of a digital twin of the instrument
	 * for computational science.</li>
	 * </ul>
	 *
	 * @param transformations the child nodes to add
	 */

	public void setAllTransformations(Map<String, NXtransformations> transformations);


}

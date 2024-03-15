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
 * Corrector for aberrations in an electron microscope.
 * Different technology partners use different naming schemes and models
 * for quantifying the aberration coefficients.
 * The corrector in an electron microscope is composed of multiple lenses and
 * multipole stigmators with vendor-specific details which are often undisclosed.
 *
 */
public interface NXcorrector_cs extends NXobject {

	public static final String NX_APPLIED = "applied";
	public static final String NX_NAME = "name";
	public static final String NX_DESCRIPTION = "description";
	/**
	 * Was the corrector used?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getApplied();

	/**
	 * Was the corrector used?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param appliedDataset the appliedDataset
	 */
	public DataNode setApplied(IDataset appliedDataset);

	/**
	 * Was the corrector used?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @return  the value.
	 */
	public Boolean getAppliedScalar();

	/**
	 * Was the corrector used?
	 * <p>
	 * <b>Type:</b> NX_BOOLEAN
	 * </p>
	 *
	 * @param applied the applied
	 */
	public DataNode setAppliedScalar(Boolean appliedValue);

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
	 * Ideally, a (globally) unique persistent identifier, link,
	 * or text to a resource which gives further details. If this does not exist
	 * a free-text field to report further details about the corrector.
	 *
	 * @return  the value.
	 */
	public IDataset getDescription();

	/**
	 * Ideally, a (globally) unique persistent identifier, link,
	 * or text to a resource which gives further details. If this does not exist
	 * a free-text field to report further details about the corrector.
	 *
	 * @param descriptionDataset the descriptionDataset
	 */
	public DataNode setDescription(IDataset descriptionDataset);

	/**
	 * Ideally, a (globally) unique persistent identifier, link,
	 * or text to a resource which gives further details. If this does not exist
	 * a free-text field to report further details about the corrector.
	 *
	 * @return  the value.
	 */
	public String getDescriptionScalar();

	/**
	 * Ideally, a (globally) unique persistent identifier, link,
	 * or text to a resource which gives further details. If this does not exist
	 * a free-text field to report further details about the corrector.
	 *
	 * @param description the description
	 */
	public DataNode setDescriptionScalar(String descriptionValue);

	/**
	 * Specific information about the concrete alignment procedure which is a
	 * process during which the corrector is configured to enable a calibrated
	 * usage of the microscope.
	 *
	 * @return  the value.
	 */
	public NXprocess getZemlin_tableau();

	/**
	 * Specific information about the concrete alignment procedure which is a
	 * process during which the corrector is configured to enable a calibrated
	 * usage of the microscope.
	 *
	 * @param zemlin_tableauGroup the zemlin_tableauGroup
	 */
	public void setZemlin_tableau(NXprocess zemlin_tableauGroup);
	// Unprocessed group:

	/**
	 *
	 * @return  the value.
	 */
	public NXlens_em getLens_em();

	/**
	 *
	 * @param lens_emGroup the lens_emGroup
	 */
	public void setLens_em(NXlens_em lens_emGroup);

	/**
	 * Get a NXlens_em node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXlens_em for that node.
	 */
	public NXlens_em getLens_em(String name);

	/**
	 * Set a NXlens_em node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param lens_em the value to set
	 */
	public void setLens_em(String name, NXlens_em lens_em);

	/**
	 * Get all NXlens_em nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXlens_em for that node.
	 */
	public Map<String, NXlens_em> getAllLens_em();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param lens_em the child nodes to add
	 */

	public void setAllLens_em(Map<String, NXlens_em> lens_em);


	/**
	 *
	 * @return  the value.
	 */
	public NXtransformations getTransformations();

	/**
	 *
	 * @param transformationsGroup the transformationsGroup
	 */
	public void setTransformations(NXtransformations transformationsGroup);

	/**
	 * Get a NXtransformations node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXtransformations for that node.
	 */
	public NXtransformations getTransformations(String name);

	/**
	 * Set a NXtransformations node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param transformations the value to set
	 */
	public void setTransformations(String name, NXtransformations transformations);

	/**
	 * Get all NXtransformations nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXtransformations for that node.
	 */
	public Map<String, NXtransformations> getAllTransformations();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 *
	 * @param transformations the child nodes to add
	 */

	public void setAllTransformations(Map<String, NXtransformations> transformations);


}

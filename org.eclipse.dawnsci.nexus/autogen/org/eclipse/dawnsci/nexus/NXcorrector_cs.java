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
 * Different vendors use a different naming schemes for aberration coefficients.
 * It is the users responsibility to map to the best matching values.
 * In transmission electron microscopes the spherical aberration corrector is
 * a component that is controlled via instructing the microscope to achieve
 * set point values. The corrector is composed of multiple lenses and other
 * parts with vendor-specific details which are often undisclosed.
 * In the case of Nion Co. microscopes the coefficients of the corrector can be
 * retrieved via NionSwift, which is why currently the Nion convention for the
 * aberration coefficients is used.
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
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getName();
	
	/**
	 * Given name/alias.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param nameDataset the nameDataset
	 */
	public DataNode setName(IDataset nameDataset);

	/**
	 * Given name/alias.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getNameScalar();

	/**
	 * Given name/alias.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param name the name
	 */
	public DataNode setNameScalar(String nameValue);

	/**
	 * 
	 * @return  the value.
	 */
	public NXmanufacturer getManufacturer();
	
	/**
	 * 
	 * @param manufacturerGroup the manufacturerGroup
	 */
	public void setManufacturer(NXmanufacturer manufacturerGroup);

	/**
	 * Get a NXmanufacturer node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXmanufacturer for that node.
	 */
	public NXmanufacturer getManufacturer(String name);
	
	/**
	 * Set a NXmanufacturer node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param manufacturer the value to set
	 */
	public void setManufacturer(String name, NXmanufacturer manufacturer);
	
	/**
	 * Get all NXmanufacturer nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXmanufacturer for that node.
	 */
	public Map<String, NXmanufacturer> getAllManufacturer();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param manufacturer the child nodes to add 
	 */
	
	public void setAllManufacturer(Map<String, NXmanufacturer> manufacturer);
	

	/**
	 * Ideally, a (globally) unique persistent identifier, link,
	 * or text to a resource which gives further details. If this does not exist
	 * a free-text field to report further details about the corrector.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public IDataset getDescription();
	
	/**
	 * Ideally, a (globally) unique persistent identifier, link,
	 * or text to a resource which gives further details. If this does not exist
	 * a free-text field to report further details about the corrector.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param descriptionDataset the descriptionDataset
	 */
	public DataNode setDescription(IDataset descriptionDataset);

	/**
	 * Ideally, a (globally) unique persistent identifier, link,
	 * or text to a resource which gives further details. If this does not exist
	 * a free-text field to report further details about the corrector.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @return  the value.
	 */
	public String getDescriptionScalar();

	/**
	 * Ideally, a (globally) unique persistent identifier, link,
	 * or text to a resource which gives further details. If this does not exist
	 * a free-text field to report further details about the corrector.
	 * <p>
	 * <b>Type:</b> NX_CHAR
	 * </p>
	 * 
	 * @param description the description
	 */
	public DataNode setDescriptionScalar(String descriptionValue);

	/**
	 * 
	 * @return  the value.
	 */
	public NXaberration getAberration();
	
	/**
	 * 
	 * @param aberrationGroup the aberrationGroup
	 */
	public void setAberration(NXaberration aberrationGroup);

	/**
	 * Get a NXaberration node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXaberration for that node.
	 */
	public NXaberration getAberration(String name);
	
	/**
	 * Set a NXaberration node by name:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param name the name of the node
	 * @param aberration the value to set
	 */
	public void setAberration(String name, NXaberration aberration);
	
	/**
	 * Get all NXaberration nodes:
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @return  a map from node names to the NXaberration for that node.
	 */
	public Map<String, NXaberration> getAllAberration();
	
	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li></li>
	 * </ul>
	 * 
	 * @param aberration the child nodes to add 
	 */
	
	public void setAllAberration(Map<String, NXaberration> aberration);
	

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

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
 * Description of peaks, their functional form or measured support.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>n_support</b>
 * Number of support points</li></ul></p>
 *
 */
public interface NXpeak extends NXobject {

	public static final String NX_LABEL = "label";
	public static final String NX_PEAK_MODEL = "peak_model";
	public static final String NX_POSITION = "position";
	public static final String NX_INTENSITY = "intensity";
	/**
	 * Human-readable identifier to specify which concept/entity
	 * the peak represents/identifies.
	 *
	 * @return  the value.
	 */
	public IDataset getLabel();

	/**
	 * Human-readable identifier to specify which concept/entity
	 * the peak represents/identifies.
	 *
	 * @param labelDataset the labelDataset
	 */
	public DataNode setLabel(IDataset labelDataset);

	/**
	 * Human-readable identifier to specify which concept/entity
	 * the peak represents/identifies.
	 *
	 * @return  the value.
	 */
	public String getLabelScalar();

	/**
	 * Human-readable identifier to specify which concept/entity
	 * the peak represents/identifies.
	 *
	 * @param label the label
	 */
	public DataNode setLabelScalar(String labelValue);

	/**
	 * Is the peak described analytically via a functional form
	 * or is it empirically defined via measured/reported
	 * intensity/counts as a function of an independent variable.
	 * If the functional form is not empirical or gaussian, users
	 * should enter other for the peak_model and add relevant details
	 * in the NXcollection.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>empirical</b> </li>
	 * <li><b>gaussian</b> </li>
	 * <li><b>lorentzian</b> </li>
	 * <li><b>other</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getPeak_model();

	/**
	 * Is the peak described analytically via a functional form
	 * or is it empirically defined via measured/reported
	 * intensity/counts as a function of an independent variable.
	 * If the functional form is not empirical or gaussian, users
	 * should enter other for the peak_model and add relevant details
	 * in the NXcollection.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>empirical</b> </li>
	 * <li><b>gaussian</b> </li>
	 * <li><b>lorentzian</b> </li>
	 * <li><b>other</b> </li></ul></p>
	 * </p>
	 *
	 * @param peak_modelDataset the peak_modelDataset
	 */
	public DataNode setPeak_model(IDataset peak_modelDataset);

	/**
	 * Is the peak described analytically via a functional form
	 * or is it empirically defined via measured/reported
	 * intensity/counts as a function of an independent variable.
	 * If the functional form is not empirical or gaussian, users
	 * should enter other for the peak_model and add relevant details
	 * in the NXcollection.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>empirical</b> </li>
	 * <li><b>gaussian</b> </li>
	 * <li><b>lorentzian</b> </li>
	 * <li><b>other</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getPeak_modelScalar();

	/**
	 * Is the peak described analytically via a functional form
	 * or is it empirically defined via measured/reported
	 * intensity/counts as a function of an independent variable.
	 * If the functional form is not empirical or gaussian, users
	 * should enter other for the peak_model and add relevant details
	 * in the NXcollection.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>empirical</b> </li>
	 * <li><b>gaussian</b> </li>
	 * <li><b>lorentzian</b> </li>
	 * <li><b>other</b> </li></ul></p>
	 * </p>
	 *
	 * @param peak_model the peak_model
	 */
	public DataNode setPeak_modelScalar(String peak_modelValue);

	/**
	 * In the case of an empirical description of the peak and its shoulders,
	 * this array holds the position values for the independent variable.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n_support;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getPosition();

	/**
	 * In the case of an empirical description of the peak and its shoulders,
	 * this array holds the position values for the independent variable.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n_support;
	 * </p>
	 *
	 * @param positionDataset the positionDataset
	 */
	public DataNode setPosition(IDataset positionDataset);

	/**
	 * In the case of an empirical description of the peak and its shoulders,
	 * this array holds the position values for the independent variable.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n_support;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getPositionScalar();

	/**
	 * In the case of an empirical description of the peak and its shoulders,
	 * this array holds the position values for the independent variable.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n_support;
	 * </p>
	 *
	 * @param position the position
	 */
	public DataNode setPositionScalar(Number positionValue);

	/**
	 * In the case of an empirical description of the peak and its shoulders,
	 * this array holds the intensity/count values at each position.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n_support;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getIntensity();

	/**
	 * In the case of an empirical description of the peak and its shoulders,
	 * this array holds the intensity/count values at each position.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n_support;
	 * </p>
	 *
	 * @param intensityDataset the intensityDataset
	 */
	public DataNode setIntensity(IDataset intensityDataset);

	/**
	 * In the case of an empirical description of the peak and its shoulders,
	 * this array holds the intensity/count values at each position.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n_support;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getIntensityScalar();

	/**
	 * In the case of an empirical description of the peak and its shoulders,
	 * this array holds the intensity/count values at each position.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_ANY
	 * <b>Dimensions:</b> 1: n_support;
	 * </p>
	 *
	 * @param intensity the intensity
	 */
	public DataNode setIntensityScalar(Number intensityValue);

	/**
	 * In the case of an analytical description (or if peak_model is other) this
	 * collection holds parameter of (and eventually) the functional form.
	 * For example in the case of Gaussians mu, sigma, cut-off values,
	 * and background intensity are relevant parameter.
	 *
	 * @return  the value.
	 */
	public NXcollection getCollection();

	/**
	 * In the case of an analytical description (or if peak_model is other) this
	 * collection holds parameter of (and eventually) the functional form.
	 * For example in the case of Gaussians mu, sigma, cut-off values,
	 * and background intensity are relevant parameter.
	 *
	 * @param collectionGroup the collectionGroup
	 */
	public void setCollection(NXcollection collectionGroup);

	/**
	 * Get a NXcollection node by name:
	 * <ul>
	 * <li>
	 * In the case of an analytical description (or if peak_model is other) this
	 * collection holds parameter of (and eventually) the functional form.
	 * For example in the case of Gaussians mu, sigma, cut-off values,
	 * and background intensity are relevant parameter.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXcollection for that node.
	 */
	public NXcollection getCollection(String name);

	/**
	 * Set a NXcollection node by name:
	 * <ul>
	 * <li>
	 * In the case of an analytical description (or if peak_model is other) this
	 * collection holds parameter of (and eventually) the functional form.
	 * For example in the case of Gaussians mu, sigma, cut-off values,
	 * and background intensity are relevant parameter.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param collection the value to set
	 */
	public void setCollection(String name, NXcollection collection);

	/**
	 * Get all NXcollection nodes:
	 * <ul>
	 * <li>
	 * In the case of an analytical description (or if peak_model is other) this
	 * collection holds parameter of (and eventually) the functional form.
	 * For example in the case of Gaussians mu, sigma, cut-off values,
	 * and background intensity are relevant parameter.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXcollection for that node.
	 */
	public Map<String, NXcollection> getAllCollection();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * In the case of an analytical description (or if peak_model is other) this
	 * collection holds parameter of (and eventually) the functional form.
	 * For example in the case of Gaussians mu, sigma, cut-off values,
	 * and background intensity are relevant parameter.</li>
	 * </ul>
	 *
	 * @param collection the child nodes to add
	 */

	public void setAllCollection(Map<String, NXcollection> collection);


}

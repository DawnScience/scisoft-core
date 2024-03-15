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
 * Computational geometry description of a set of (oriented) unit normal vectors.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>d</b>
 * The dimensionality, which has to be at least 2.</li>
 * <li><b>c</b>
 * The cardinality of the set, i.e. the number of unit normals.</li></ul></p>
 *
 */
public interface NXcg_unit_normal_set extends NXobject {

	public static final String NX_DIMENSIONALITY = "dimensionality";
	public static final String NX_CARDINALITY = "cardinality";
	public static final String NX_NORMALS = "normals";
	public static final String NX_ORIENTATION = "orientation";
	/**
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getDimensionality();

	/**
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param dimensionalityDataset the dimensionalityDataset
	 */
	public DataNode setDimensionality(IDataset dimensionalityDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getDimensionalityScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param dimensionality the dimensionality
	 */
	public DataNode setDimensionalityScalar(Long dimensionalityValue);

	/**
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getCardinality();

	/**
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param cardinalityDataset the cardinalityDataset
	 */
	public DataNode setCardinality(IDataset cardinalityDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getCardinalityScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param cardinality the cardinality
	 */
	public DataNode setCardinalityScalar(Long cardinalityValue);

	/**
	 * Direction of each normal
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getNormals();

	/**
	 * Direction of each normal
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @param normalsDataset the normalsDataset
	 */
	public DataNode setNormals(IDataset normalsDataset);

	/**
	 * Direction of each normal
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Double getNormalsScalar();

	/**
	 * Direction of each normal
	 * <p>
	 * <b>Type:</b> NX_FLOAT
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @param normals the normals
	 */
	public DataNode setNormalsScalar(Double normalsValue);

	/**
	 * Qualifier how which specifically oriented normal to its primitive each
	 * normal represents.
	 * * 0 - undefined
	 * * 1 - outer
	 * * 2 - inner
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getOrientation();

	/**
	 * Qualifier how which specifically oriented normal to its primitive each
	 * normal represents.
	 * * 0 - undefined
	 * * 1 - outer
	 * * 2 - inner
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param orientationDataset the orientationDataset
	 */
	public DataNode setOrientation(IDataset orientationDataset);

	/**
	 * Qualifier how which specifically oriented normal to its primitive each
	 * normal represents.
	 * * 0 - undefined
	 * * 1 - outer
	 * * 2 - inner
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getOrientationScalar();

	/**
	 * Qualifier how which specifically oriented normal to its primitive each
	 * normal represents.
	 * * 0 - undefined
	 * * 1 - outer
	 * * 2 - inner
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param orientation the orientation
	 */
	public DataNode setOrientationScalar(Long orientationValue);

}
